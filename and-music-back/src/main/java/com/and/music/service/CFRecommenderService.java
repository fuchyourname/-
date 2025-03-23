package com.and.music.service;

import com.and.music.domain.Playlists;

import java.util.List;

public interface CFRecommenderService {

    public List<Playlists> recommend(int userId, int topN);
}
