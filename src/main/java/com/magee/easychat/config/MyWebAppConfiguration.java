package com.magee.easychat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 使用虚拟路径添加外部文件映射
 * 这里设置的是linux
 * 如果是window：
 * 格式为：registry .addResourceHandler("/Path/**").addResourceLocations("file:/F:/wechatProject/upload/");
 */
@Configuration
public class MyWebAppConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * @Description: 对文件的路径进行配置, 创建一个虚拟路径/Path/** ，即只要在<img src="/Path/picName.jpg" />便可以直接引用图片
         *这是图片的物理路径  "file:/+本地图片的地址"
         * @Date： Create in 14:08 2017/12/20
         */
        registry.addResourceHandler("/Path/**").addResourceLocations("file:/home/image/");
//      registry.addResourceHandler("/Path/**").addResourceLocations("file:/F:/desktop/upload/");
//        super.addResourceHandlers(registry);
    }

}