package com.magee.easychat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
public class MySwagger2Config {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("接口测试文档")
                        .description("描述：-------------暂无描述----------------")
                        .contact(new Contact("magee",null,"18219349847@163.com"))
                        .version("版本号:1.0")
                        .build())
                .select()
                //配置扫描包
                .apis(RequestHandlerSelectors.basePackage("com.magee.easychat"))
                .paths(PathSelectors.any())
                .build();
    }
}
