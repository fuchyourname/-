package com.and.music.controller;

import com.and.music.common.R;
import com.and.music.domain.Playlists;
import com.and.music.service.CFRecommenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class test {
    @Resource
    private CFRecommenderService recommender;

    @GetMapping("/{userId}")
    public R getRecommendations(
            @PathVariable int userId,
            @RequestParam(defaultValue = "10") int topN) {
            List<Playlists> result = recommender.recommend(userId, topN);
            return R.ok(result);
    }
}
