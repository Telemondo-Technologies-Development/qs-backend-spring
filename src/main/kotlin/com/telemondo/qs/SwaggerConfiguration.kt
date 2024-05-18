//package com.telemondo.qs.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import springfox.documentation.builders.ApiInfoBuilder
//import springfox.documentation.builders.PathSelectors.regex
//import springfox.documentation.builders.RequestHandlerSelectors
//import springfox.documentation.spi.DocumentationType
//import springfox.documentation.spring.web.plugins.Docket
//import springfox.documentation.service.Contact
//import springfox.documentation.swagger2.annotations.EnableSwagger2
//
//@Configuration
//@EnableSwagger2
//class SwaggerConfig {
//
//    @Bean
//    fun postsApi(): Docket {
//        return Docket(DocumentationType.SWAGGER_2)
//            .groupName("public-api")
//            .apiInfo(apiInfo())
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.telemondo.qs"))
//            .paths(postPaths())
//            .build()
//    }
//
//    private fun postPaths() = regex("/api/posts.*").or(regex("/api/comments.*"))
//
//    private fun apiInfo() = ApiInfoBuilder()
//        .title("KBQS")
//        .description("Queuing System API's")
//        .termsOfServiceUrl("http://hantsy.blogspot.com")
//        .contact(Contact("Hantsy Bai", "http://hantsy.blogspot.com", "hantsy@example.com"))
//        .license("Apache License Version 2.0")
//        .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
//        .version("2.0")
//        .build()
//}