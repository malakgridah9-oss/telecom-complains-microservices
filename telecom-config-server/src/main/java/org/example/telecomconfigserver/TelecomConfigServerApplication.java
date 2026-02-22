package org.example.telecomconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TelecomConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelecomConfigServerApplication.class, args);
    }

}
