package com.and.music.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Wang
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * 服务地址
     */
    private String url;

    /**
     * 认证账户
     */
    private String accessKey;

    /**
     * 认证密码
     */
    private String secretKey;

    /**
     * 桶名称, 优先级最低
     */
    private String bucket;

    /**
     * 桶不在的时候是否新建桶
     */
    private boolean createBucket = true;

    /**
     * 启动的时候检查桶是否存在
     */
    private boolean checkBucket = true;

    /**
     * 设置HTTP连接、写入和读取超时。值为0意味着没有超时
     * HTTP连接超时，以毫秒为单位。
     */
    private long connectTimeout;

    /**
     * 设置HTTP连接、写入和读取超时。值为0意味着没有超时
     * HTTP写超时，以毫秒为单位。
     */
    private long writeTimeout;

    /**
     * 设置HTTP连接、写入和读取超时。值为0意味着没有超时
     * HTTP读取超时，以毫秒为单位。
     */
    private long readTimeout;

}
