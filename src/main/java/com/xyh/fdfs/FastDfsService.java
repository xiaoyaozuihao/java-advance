package com.xyh.fdfs;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ProtoCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author xuyh
 * @date 2019/7/8
 */
public class FastDfsService implements IOssService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Value("${fdfs.web-server-url}")
    private String webServerUrl;

    @Value("${fdfs.http.secret_key}")
    private String secretKey;
    @Autowired
    private FastFileStorageClient storageClient;

    @Override
    public String uploadOss(String bucket, String objKey, File file) {
        try(InputStream in=new FileInputStream(file)){
            StorePath storePath = storageClient.uploadFile(in,file.length(), FilenameUtils.getExtension(file.getName()),null);
            return storePath.getPath();
//            return generatePresignedUrl(storePath.getGroup(),storePath.getPath(),"");
        }catch (Exception e){
            logger.error("upload fastdfs fail,objkey:{},errormsg:{}",objKey,e);
        }
        return null;
    }

    @Override
    public void downloadOss(String bucket, String objKey, String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            try (OutputStream out = new FileOutputStream(filePath)) {
                byte[] bytes = storageClient.downloadFile(bucket, objKey, new DownloadByteArray());
                out.write(bytes);
            } catch (IOException e) {
                logger.error("download pic from fastdfs fail {}", objKey);
            }
        }
    }

    @Override
    public InputStream getFileStream(String bucket, String objKey) {
        byte[] bytes = storageClient.downloadFile(bucket, objKey, new DownloadByteArray());
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void deleteOss(String bucket, String objKey) {
        try {
            storageClient.deleteFile(bucket, objKey);
        } catch (Exception e) {
            logger.error("delete pic from fastdfs fail {}", objKey);
        }
    }

    @Override
    public void batchDeleteOss(String bucket, List<String> objKeys) {
        for (String objKey : objKeys) {
            deleteOss(bucket,objKey);
        }
    }

    @Override
    public String generatePresignedUrl(String bucket, String objKey,String style) {
        return webServerUrl+bucket+File.separator+objKey;
//        return getToken(bucket+ File.separator+objKey, secretKey,webServerUrl);
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
