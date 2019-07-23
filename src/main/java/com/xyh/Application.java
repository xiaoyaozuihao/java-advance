package com.xyh;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author xuyh
 * @date 2019/7/4
 */
@SpringBootApplication
@Import(FdfsClientConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
