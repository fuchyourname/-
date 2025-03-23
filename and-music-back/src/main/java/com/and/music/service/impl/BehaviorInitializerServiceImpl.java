package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.domain.UserBehavior;
import com.and.music.mapper.UserBehaviorMapper;
import com.and.music.service.BehaviorInitializerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BehaviorInitializerServiceImpl implements BehaviorInitializerService {

    @Resource
    private UserBehaviorMapper userBehaviorMapper;

    // 插入行为数据时设置原始值
    public void recordUserBehavior(int userId, int playlistId, String behaviorType) {
        // 根据行为类型设置原始值权重
        int originalValue = switch (behaviorType) {
            case "BROWSE" -> 1;
            case "PLAY" -> 2;
            case "COLLECT" -> 3;
            case "UNCOLLECTED" -> -3; // 取消收藏时减少3
            case "SHARE" -> 4;
            default -> 0;
        };

        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId)
                .eq(UserBehavior::getPlaylistId, playlistId);

        UserBehavior existingBehavior = userBehaviorMapper.selectOne(queryWrapper);

        if (existingBehavior == null) {
            // 如果不存在记录，则插入新记录
            UserBehavior behavior = new UserBehavior();
            behavior.setUserId(userId);
            behavior.setPlaylistId(playlistId);
            behavior.setBehaviorType(behaviorType);
            behavior.setOriginalValue(originalValue);
            behavior.setNormalizedValue(0.0); // 初始化为0，等待归一化处理
            userBehaviorMapper.insert(behavior);
        } else {
            // 如果存在记录，则更新原始值
            int currentOriginalValue = existingBehavior.getOriginalValue();
            int newOriginalValue = currentOriginalValue + originalValue;

            // 处理取消收藏的情况
            if (behaviorType.equals("UNCOLLECTED")) {
                newOriginalValue = Math.max(0, newOriginalValue);
            }

            existingBehavior.setOriginalValue(newOriginalValue);
            existingBehavior.setBehaviorType(behaviorType);
            userBehaviorMapper.updateById(existingBehavior);
        }
    }
}
