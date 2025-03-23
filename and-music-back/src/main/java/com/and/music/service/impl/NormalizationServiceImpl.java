package com.and.music.service.impl;

import com.and.music.domain.UserBehavior;
import com.and.music.mapper.PlaylistsMapper;
import com.and.music.service.NormalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class NormalizationServiceImpl implements NormalizationService {

    @Resource
    private PlaylistsMapper playlistMapper;


    public void normalizeUserBehaviors() {
        // 获取所有原始数据（包含原始值）
        List<UserBehavior> allBehaviors = playlistMapper.getAllRawBehaviors();

        // 按用户分组
        Map<Integer, List<UserBehavior>> userGroups = allBehaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getUserId));

        // 为每个用户计算归一化值
        userGroups.forEach((userId, behaviors) -> {
            DoubleSummaryStatistics stats = behaviors.stream()
                    .mapToDouble(UserBehavior::getOriginalValue)
                    .summaryStatistics();

            // 避免除零错误
            if (stats.getMax() > stats.getMin()) {
                behaviors.forEach(behavior -> {
                    double normalized = (behavior.getOriginalValue() - stats.getMin())
                            / (stats.getMax() - stats.getMin());
                    behavior.setNormalizedValue(normalized);
                    playlistMapper.updateNormalizedValue(behavior); // 更新到数据库
                });
            }
        });
    }
}
