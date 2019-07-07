package com.xyh.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xyh.service.IFdfsService;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ProtoCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author xuyh
 * @date 2019/7/4
 */
@Service
public class FdfsService implements IFdfsService {
    @Autowired
    private FastFileStorageClient storageClient;

    @Value("${fdfs.web-server-url}")
    private String fastdfsUrl;

    @Value("${fdfs.http.secret_key}")
    private String fastdfsToken;


    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(),file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()),null);
        return generateTokenUrl(storePath.getFullPath());
    }

    @Override
    public byte[] download(String bucket, String objKey) {
        return storageClient.downloadFile(bucket, objKey, new DownloadByteArray());
    }

    @Override
    public void deleteFdfs(String bucket, String objKey) {
        storageClient.deleteFile(bucket, objKey);
    }

    @Override
    public String generateTokenUrl(String filePath) {
        return getToken(filePath,fastdfsToken,fastdfsUrl);
    }

    /**
     * 获取访问服务器的token，拼接到地址后面
     *
     * @param fid 文件路径 group1/M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg
     * @param secret_key 密钥
     * @return 返回token，如： token=078d370098b03e9020b82c829c205e1f&ts=1508141521
     */
    public static String getToken(String fid, String secret_key, String IP){
        String substring = fid.substring(fid.indexOf("/")+1);
        //unix时间戳 以秒为单位
        int ts = (int) (System.currentTimeMillis() / 1000);
        String token= "";
        try {
            token= ProtoCommon.getToken(substring, ts, secret_key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(IP);
        sb.append(fid);
        sb.append("?token=").append(token);
        sb.append("&ts=").append(ts);
        return sb.toString();
    }
}
