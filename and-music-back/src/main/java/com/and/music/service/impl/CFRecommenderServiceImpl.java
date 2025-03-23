package com.and.music.service.impl;

import com.and.music.domain.Playlists;
import com.and.music.domain.UserBehavior;
import com.and.music.mapper.PlaylistsMapper;
import com.and.music.service.CFRecommenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CFRecommenderServiceImpl implements CFRecommenderService {

    @Resource
    private PlaylistsMapper playlistMapper;
    @Resource
    private NormalizationServiceImpl normalizationService;

    public List<Playlists> recommend(int userId, int topN) {

        normalizationService.normalizeUserBehaviors();
        // 获取目标用户行为向量（歌单ID → 归一化值）
        Map<Integer, Double> targetVector = playlistMapper.getUserBehaviorVector(userId);

        // 冷启动处理
        if (targetVector.isEmpty()) {
            return playlistMapper.getHotPlaylists(topN);
        }

        Map<Integer, Map<Integer, Double>> userMatrix = buildUserMatrix();
        Map<Integer, Double> similarities = calculateSimilarities(userId, userMatrix);

        return generateRecommendations(targetVector, similarities, userMatrix, topN);
    }

    private Map<Integer, Map<Integer, Double>> buildUserMatrix() {
        // 获取所有用户行为数据
        List<UserBehavior> allBehaviors = playlistMapper.getAllUserBehaviors();

        // 按用户ID分组
        Map<Integer, List<UserBehavior>> groupedBehaviors = allBehaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getUserId));

        // 转换为用户矩阵：Map<用户ID, Map<歌单ID, 归一化值>>
        Map<Integer, Map<Integer, Double>> userMatrix = groupedBehaviors.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,  // 用户ID
                        entry -> entry.getValue().stream()  // 用户的行为列表
                                .collect(Collectors.toMap(
                                        UserBehavior::getPlaylistId,  // 歌单ID
                                        UserBehavior::getNormalizedValue  // 归一化值
                                ))
                ));

        // 打印用户矩阵
        log.info("User Matrix: {}", userMatrix);

        return userMatrix;
    }

    private Map<Integer, Double> calculateSimilarities(int userId,
                                                       Map<Integer, Map<Integer, Double>> matrix) {
        Map<Integer, Double> target = matrix.get(userId);
        Map<Integer, Double> similarities = matrix.entrySet().stream()
                .filter(e -> e.getKey() != userId)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> cosineSimilarity(target, e.getValue())
                ));

        // 打印用户相似度
        log.info("User Similarities for User {}: {}", userId, similarities);

        return similarities;
    }

    private double cosineSimilarity(Map<Integer, Double> v1, Map<Integer, Double> v2) {
        Set<Integer> intersection = new HashSet<>(v1.keySet());
        intersection.retainAll(v2.keySet());

        double dotProduct = intersection.stream()
                .mapToDouble(k -> v1.get(k) * v2.get(k)).sum();

        double norm1 = Math.sqrt(v1.values().stream().mapToDouble(v -> v*v).sum());
        double norm2 = Math.sqrt(v2.values().stream().mapToDouble(v -> v*v).sum());

        // 打印 norm1 和 norm2
        log.info("Norm1: {}, Norm2: {}", norm1, norm2);
        return norm1 * norm2 == 0 ? 0 : dotProduct / (norm1 * norm2);
    }

    private List<Playlists> generateRecommendations(Map<Integer, Double> targetVector,
                                                    Map<Integer, Double> similarities,
                                                    Map<Integer, Map<Integer, Double>> matrix,
                                                    int topN) {
        Map<Integer, Double> scores = new HashMap<>();

        similarities.entrySet().stream()
//                .filter(e -> e.getValue() > 0)
                .forEach(e -> {
                    Integer similarUser = e.getKey();
                    Double similarity = e.getValue();

                    matrix.get(similarUser).forEach((playlistId, rating) -> {

                            scores.merge(playlistId, similarity * rating, Double::sum);

                    });
                });

        return scores.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(topN)
                .map(e -> playlistMapper.selectById(e.getKey()))
                .collect(Collectors.toList());
    }
}
