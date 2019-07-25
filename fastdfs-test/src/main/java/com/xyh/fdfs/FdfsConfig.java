package com.xyh.fdfs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuyh
 * @date 2019/7/9
 */
@Configuration
public class FdfsConfig {

    @Value("${fdfs.enabled}")
    private boolean fdfsEnabled;
    @Bean
    public IOssService ossService(){
        if(fdfsEnabled){
            return new FastDfsService();
        }else{
            return null;
        }
    }

    public boolean isFdfsEnabled() {
        return fdfsEnabled;
    }

    public void setFdfsEnabled(boolean fdfsEnabled) {
        this.fdfsEnabled = fdfsEnabled;
    }
}
