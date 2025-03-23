package com.and.music.mapper;

import com.and.music.domain.Playlists;
import com.and.music.domain.UserBehavior;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
* @author and
* @description 针对表【playlists】的数据库操作Mapper
* @createDate 2024-10-14 15:31:30
* @Entity .domain.Playlists
*/
@Mapper
public interface PlaylistsMapper extends BaseMapper<Playlists> {

    // 新增方法：获取所有用户行为数据
    List<UserBehavior> getAllUserBehaviors();

    // 获取包含原始值的完整行为数据
    @Select("SELECT user_id, playlist_id, original_value FROM user_behavior")
    List<UserBehavior> getAllRawBehaviors();

    // 返回歌单ID → 归一化值的映射
    @MapKey("playlist_id")
    Map<Integer, Double> getUserBehaviorVector(@Param("userId") int userId);

    // 获取热门歌单（解决冷启动）
//    @Select("SELECT p.* FROM playlists p " +
//            "ORDER BY p.play_count " +
//            "LIMIT #{limit}")
    @Select("SELECT * FROM playlists where type > 0 ORDER BY play_count LIMIT #{limit}")
    List<Playlists> getHotPlaylists(@Param("limit") int limit);

    // 更新归一化值
    @Update("UPDATE user_behavior SET normalized_value = #{normalizedValue} " +
            "WHERE user_id = #{userId} AND playlist_id = #{playlistId}")
    void updateNormalizedValue(UserBehavior behavior);
}




