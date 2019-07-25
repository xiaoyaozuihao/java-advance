package com.xyh;

import com.xyh.fdfs.IOssService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * @author xuyh
 * @date 2019/7/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FdfsApplication.class)
public class FastDfsClientTest {

    @Autowired
    private IOssService ossService;
    @Test
    public void testUpload(){
        String path = ossService.uploadOss("group1", "", new File("F:/index.png"));
        System.out.println(path);
    }
}