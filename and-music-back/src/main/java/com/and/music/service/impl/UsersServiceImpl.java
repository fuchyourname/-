package com.and.music.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.and.music.common.CommonConstants;
import com.and.music.common.R;
import com.and.music.config.MinioProperties;
import com.and.music.domain.Follow;
import com.and.music.domain.Playlists;
import com.and.music.domain.Users;
import com.and.music.dto.FileDto;
import com.and.music.dto.PageInfo;
import com.and.music.dto.UserDto;
import com.and.music.mapper.ArtistsMapper;
import com.and.music.mapper.FollowMapper;
import com.and.music.mapper.UsersMapper;
import com.and.music.service.UsersService;
import com.and.music.utils.MinioUtils;
import com.and.music.utils.PathUtils;
import com.and.music.utils.UserContext;
import com.and.music.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author and
 * @description 针对表【users】的数据库操作Service实现
 * @createDate 2024-10-14 15:31:31
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    @Resource
    private FollowMapper followMapper;
    @Resource
    private MinioUtils minioUtils;
    @Resource
    private MinioProperties minioProperties;

    @Override
    public R getFollowUserList() {

        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getUserId, UserContext.getUser().getUserId());
        queryWrapper.eq(Follow::getType, 2);
        List<Follow> followList = followMapper.selectList(queryWrapper);
        List<Integer> userIds = followList.stream().map(Follow::getFollowUserId).collect(Collectors.toList());
        List<Users> usersList = this.baseMapper.selectBatchIds(userIds);
        return R.ok(usersList);
    }

    @Override
    public R checkUser(UserDto userDto, HttpSession session) {

        if (ObjectUtil.isEmpty(userDto)) {
            return R.fail("参数错误");
        }
        String userName = userDto.getUserName();
        if (ObjectUtil.isNotEmpty(userName)) {
            Users user = getOne(new LambdaQueryWrapper<Users>().eq(Users::getUserName, userName));
            if (ObjectUtil.isNotEmpty(user) && user.getPassword().equals(userDto.getPassword())) {
                Long followCount = followMapper.selectCount(
                        new LambdaQueryWrapper<Follow>().eq(Follow::getUserId, user.getUserId()));
                Long fansCount = followMapper.selectCount(
                        new LambdaQueryWrapper<Follow>().eq(Follow::getFollowUserId, user.getUserId()));
                UserVo userVo = new UserVo()
                        .setUserId(user.getUserId())
                        .setUsername(user.getUserName())
                        .setAvatar(user.getPicUrl())
                        .setNationality(user.getNationality())
                        .setDescription(user.getDescription())
                        .setFansCount(fansCount)
                        .setFollowCount(followCount);
                if (StringUtils.equals(user.getUserName(), CommonConstants.ADMIN_USERNAME)) {
                    userVo.setIsAdmin(true);
                }
                session.setAttribute("user", user);
                return R.ok(userVo);
            }
            return R.fail("用户名或密码错误");
        }
        return R.fail("用户不存在");
    }

    @Override
    public R register(UserDto userDto) {

        Users user = new Users();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setCreateUser(userDto.getUserId());
        user.setUpdateUser(userDto.getUserId());

        if (save(user)) {
            return R.ok();
        }
        return R.fail("注册失败");
    }

    @Override
    public R saveUser(UserDto userDto) {

        Users users = new Users();
        users.setUserName(userDto.getUserName());
        users.setDescription(userDto.getDescription());
        users.setNationality(userDto.getNationality());
        users.setCreateUser(UserContext.getUser().getUserId());
        users.setType(userDto.getType());
        users.setEmail(userDto.getEmail());
        if (ObjectUtil.isNotEmpty(userDto.getPic())) {
            try {
                MultipartFile pl = userDto.getPic();
                if (pl != null) {
                    // 上传歌曲图片
                    FileDto fileDto = new FileDto();
                    fileDto.setMusicPic(pl);
                    fileDto.setGenreId(5);
                    String objectPath = PathUtils.getCoverPath(fileDto, users.getUserId());

                    String coverUrl = minioUtils.putObject(minioProperties.getBucket(),
                            objectPath, fileDto.getMusicPic());
                    users.setPicUrl(coverUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("上传失败");
            }
        }
        if (this.save(users)) {
            return R.ok(users);
        }
        return R.fail("添加失败");
    }

    @Override
    public R updateUser(UserDto userDto) {
        if (ObjectUtil.isEmpty(userDto)) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(userDto.getUserId())) {
            return R.fail("参数错误");
        }
        if (ObjectUtil.isEmpty(userDto.getUserId())) {
            return saveUser(userDto);
        }
        Users users = this.baseMapper.selectById(userDto.getUserId());
        users.setDescription(userDto.getDescription());
        users.setNationality(userDto.getNationality());
        users.setUpdateUser(UserContext.getUser().getUserId());
        users.setType(userDto.getType());
        users.setUserName(userDto.getUserName());
        users.setEmail(userDto.getEmail());
        if (ObjectUtil.isNotEmpty(userDto.getPic())) {
            try {
                // http://192.168.154.1:9000/music/cover/1/27_1732354594609.jpg
                String objectName = users.getPicUrl().split("/")[3];
                minioUtils.removeObject(minioProperties.getBucket(), objectName);
                MultipartFile pl = userDto.getPic();
                if (pl != null) {
                    // 上传歌曲图片
                    FileDto fileDto = new FileDto();
                    fileDto.setMusicPic(pl);
                    fileDto.setGenreId(5);
                    String objectPath = PathUtils.getCoverPath(fileDto, users.getUserId());

                    String coverUrl = minioUtils.putObject(minioProperties.getBucket(),
                            objectPath, fileDto.getMusicPic());
                    users.setPicUrl(coverUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("上传失败");
            }
        }
        this.baseMapper.updateById(users);
        return R.ok(users);
    }

    @Override
    public R getUserPage(PageInfo pageInfo) {

        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ObjectUtil.isNotEmpty(pageInfo.getName()), Users::getUserName, pageInfo.getName())
                .eq(ObjectUtil.isNotEmpty(pageInfo.getType()), Users::getType, pageInfo.getType())
                .orderByDesc(Users::getCreateTime);

        Page<Users> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        page(page, queryWrapper);
        return R.ok(page.getRecords(), page.getTotal());
    }
}




