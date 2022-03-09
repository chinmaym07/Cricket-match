package src.com.cricketgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@SpringBootApplication
@EnableAutoConfiguration
public class CricketGameApp {
    public static void main(String[] args) {
        SpringApplication.run(CricketGameApp.class,args);
    }
}
