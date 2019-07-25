package com.xyh.fdfs;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by tony on 17-5-31.
 */
public interface IOssService {


    /**
     *@description  上传文件
     *@param bucket
     *@param objKey
     *@param file
     *@return  java.lang.String
     *@author  xuyh
     *@create  2019/7/24 13:54
     */
    String uploadOss(String bucket, String objKey, File file);

    /**
     * 下载OSS文件
     * @param filePath 文件路径
     * @param bucket
     * @param objKey
     */
    void downloadOss(String bucket, String objKey, String filePath);

    /**
     * 获取文件流
     * @param bucket
     * @param objKey
     * @return
     */
    InputStream getFileStream(String bucket, String objKey);
    /**
     * 删除OSS文件(单个文件)
     * @param bucket
     * @param objKey
     */
    void deleteOss(String bucket, String objKey);

    /**
     * 删除OSS文件(多个文件)
     * @param bucket
     * @param objKeys
     */
    void batchDeleteOss(String bucket, List<String> objKeys);

    /**
     * 生成访问链接
     * @param bucket
     * @param objKey
     * @return
     */
    String generatePresignedUrl(String bucket, String objKey, String style);
}
