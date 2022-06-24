package src.com.cricketgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSwagger2
public class CricketGameApp {
    public static void main(String[] args) {
        SpringApplication.run(CricketGameApp.class, args);
    }

    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("src.com.cricketgame")).paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Cricket Game Rest API's",
                "Using these apis you can start match between any two existing teams",
                "v1.0",
                "",
                new Contact("Chinmay Mehta", "https://github.com/chinmaym07","chinmay.mehta07@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());

    }
}

