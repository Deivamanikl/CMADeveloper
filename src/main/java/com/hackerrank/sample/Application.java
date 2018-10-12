package com.hackerrank.sample;

/**
 * .
 */
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class Application {
    public static void main(String[] args) {
        Flyway flyway=new Flyway();
        flyway.setDataSource("jdbc:h2:mem:testdb","sa",null);
        flyway.migrate();
        SpringApplication.run(Application.class, args);
    }
}
