package com.xyh.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xyh.service.IFdfsService;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

/**
 * @author xuyh
 * @date 2019/7/4
 */
@RestController
public class IndexController {
    @Autowired
    private IFdfsService fdfsService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @RequestMapping(value = "/token",method = RequestMethod.GET)
    public String getToken(String group,String filepath) throws UnsupportedEncodingException, NoSuchAlgorithmException, MyException {
        int ts = (int) (System.currentTimeMillis() / 1000);
        String token = ProtoCommon.getToken(filepath, ts, ClientGlobal.getG_secret_key());
        return "http://localhost:9090/download" + group + "/" + filepath + "?token=" + token + "&ts=" + ts;
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public void download(String group, String filePath, HttpServletResponse response){
//        byte[] bytes = fastFileStorageClient.downloadFile(group, filePath, new DownloadByteArray());
        byte[] bytes = fdfsService.download(group, filePath);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode("index.jpg", "UTF-8"));
            outputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file) throws IOException {
        return fdfsService.uploadFile(file);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public void delete(String filePath){
        fastFileStorageClient.deleteFile(filePath);
    }

    @RequestMapping(value = "/getTokenUrl",method = RequestMethod.GET)
    public String generateTokenUrl(String filePath){
        return fdfsService.generateTokenUrl(filePath);
    }

}
