package com.example.generatenumberapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executor;

@EnableWebMvc
@Configuration
@EnableAsync
@EnableScheduling
@EnableSwagger2
public class Core {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public Docket getDocket () {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.generatenumberapp.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo(
                "Generate Random App",
                "app to random value",
                "1.0",
                "http",
                new Contact("Grzegorz", "url", "mymail"),
                "my licence",
                "new URL",
                Collections.emptyList()
        );
    }
}