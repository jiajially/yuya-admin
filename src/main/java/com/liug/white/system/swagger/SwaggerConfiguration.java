package com.liug.white.system.swagger;

public class SwaggerConfiguration {}
/*

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket demoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("demo")
//                .genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(false)
                .pathMapping("/")
//                .select()
//                .build()
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("刘港", "", "1071245673@qq.com");
        ApiInfo apiInfo = new ApiInfo(
                "black-eye接口文档",//大标题
                "请勿随意测试删除类接口",//小标题
                "V1.0.0",//版本
                "",
                contact,//作者
                "Apache",//链接显示文字
                "http://www.apache.org/licenses/"//网站链接
        );
        return apiInfo;
    }
}
*/
