package com.xyh.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author xuyh
 * @date 2019/7/4
 */
public interface IFdfsService {

    /**
     * 上传fdfs
     * @param file 文件路径
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * 下载fdfs文件
     * @param group 文件组
     * @param fileId 文件路径
     */
    byte[] download(String group, String fileId);

    /**
     * 删除fdfs文件(单个文件)
     * @param group
     * @param fileId
     */
    void deleteFdfs(String group, String fileId);

    /**
     * 生成带有过期时间的url
     * @param filePath
     * @return
     */
    String generateTokenUrl(String filePath);

}
