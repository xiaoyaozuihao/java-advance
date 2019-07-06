package com.xyh.controller;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;
import java.net.URLDecoder;


public class FastDFSClient {
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;

    //测试上传
    public static void main(String[] args) throws Exception {
        FastDFSClient fastDFSClient=new FastDFSClient("fdfs_client.conf");
        String s = fastDFSClient.uploadFile("F:/index.png");
        System.out.println(s);
    }

    public FastDFSClient(String conf) throws Exception {
        if (conf.contains("classpath:")) {
            String path =
                    URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().toString(), "UTF-8");
            path = path.substring(6);
            conf = conf.replace("classpath:", URLDecoder.decode(path, "UTF-8"));
        }
        ClientGlobal.init(conf);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     *
     * @param fileName 文件全路径
     * @param extName  文件扩展名，不包含（.）
     * @param metas    文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) {
        String result = null;
        try {
            result = storageClient.upload_file1(fileName, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 上传文件,传fileName
     *
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @return null为失败
     */
    public String uploadFile(String fileName) {
        return uploadFile(fileName, null, null);
    }

    /**
     * @param fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * @param extName  文件的扩展名 如 txt jpg等
     * @return null为失败
     */
    public String uploadFile(String fileName, String extName) {
        return uploadFile(fileName, extName, null);
    }

}
