package com.and.music.service;

public interface BehaviorInitializerService {

    void recordUserBehavior(int userId, int playlistId, String behaviorType);
}
