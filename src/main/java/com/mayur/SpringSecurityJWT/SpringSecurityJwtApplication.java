package com.mayur.SpringSecurityJWT;

import java.sql.SQLException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.h2.tools.Server;

@SpringBootApplication
public class SpringSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);

    }

    @Bean
    public CommandLineRunner startH2Console() throws SQLException {
        return args -> {
            Server h2Console = Server.createWebServer("-web", "-webAllowOthers").start();
            System.out.println("H2 Console started at: " + h2Console.getURL());
        };

    }
}
