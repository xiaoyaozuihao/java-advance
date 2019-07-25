package com.xyh;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author xuyh
 * @description:
 * @date 2019/7/24
 */
@SpringBootApplication
@Import(FdfsClientConfig.class)
public class FdfsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdfsApplication.class,args);
    }
}
