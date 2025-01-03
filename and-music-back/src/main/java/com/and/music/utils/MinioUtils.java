package com.and.music.utils;

import com.and.music.config.MinioProperties;
import com.and.music.vo.FileVo;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

/**
 * Minio工具类
 */
@Component
public class MinioUtils {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    /*
    下载文件
     */
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    删除文件
     */
    public void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    获取所有图片的路径、名称、大小
    */
    public List<FileVo> getAllFiles(String bucketName, String prefix) {
        List<FileVo> fileInfoList = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs
                            .builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(true)
                            .build());
            for (Result<Item> result : results) {
                Item item = result.get();
                FileVo fileInfo = new FileVo();
                String objectName = item.objectName();
                fileInfo.setFileName(objectName.split("/")[2]);
                Long size = item.size() / 1024 / 1024;
                String fileSize = "";
                if (size == 0) {
                    size = item.size() / 1024;
                    fileSize = size + "KB";
                } else {
                    fileSize = item.size() / 1024 / 1024 + "MB";
                }
                fileInfo.setFileSize(fileSize);
                fileInfo.setFile(minioProperties.getUrl() + "/" + bucketName + "/" + item.objectName());
                fileInfoList.add(fileInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
       }
        return fileInfoList;
    }


    /**
     * 判断桶是否存在
     *
     */
    public Boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("检查桶是否存在失败!", e);
        }
    }

    /**
     * 创建bucket
     *
     */
    public void createBucket(String bucketName) {
        if (!this.bucketExists(bucketName)) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                throw new RuntimeException("创建桶失败!", e);
            }
        }
    }

    /**
     * 上传MultipartFile文件到指定的文件桶下
     *
     */
    public String putObject(String bucketName, String objectName, MultipartFile file) {
        // 先创建桶
        this.createBucket(bucketName);
        // 给文件名添加时间戳防止重复
        objectName = getFileName(objectName);
        // 开始上传
        this.putMultipartFile(bucketName, objectName, file);
        return minioProperties.getUrl() + "/" + bucketName + "/" + objectName;
    }

    /**
     * 上传MultipartFile通用方法
     *
     */
    private void putMultipartFile(String bucketName, String objectName, MultipartFile file) {
        InputStream stream = null;
        try {
            stream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("文件流获取错误", e);
        }

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .contentType(file.getContentType())
                            .stream(stream, stream.available(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 生成唯一ID
     *
     */
    private static String getFileName(String objectName) {
        //判断文件最后一个点所在的位置
        int lastIndexOf = objectName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return String.format("%s_%s", objectName, System.currentTimeMillis());
        } else {
            // 获取文件前缀,已最后一个点进行分割
            String filePrefix = objectName.substring(0, objectName.lastIndexOf("."));
            // 获取文件后缀,已最后一个点进行分割
            String fileSuffix = objectName.substring(objectName.lastIndexOf(".") + 1);
            // 组成唯一文件名
            return String.format("%s_%s.%s", filePrefix, System.currentTimeMillis(), fileSuffix);
        }
    }
}
