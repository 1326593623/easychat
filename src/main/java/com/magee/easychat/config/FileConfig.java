package com.magee.easychat.config;


import com.magee.easychat.util.ConstUtils;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileConfig {

    /**
     //     * 文件类需要修改Location信息否则无法注入
     //     * @return
     //     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(ConstUtils.LOCAL_PATH);
        return factory.createMultipartConfig();
    }

}
