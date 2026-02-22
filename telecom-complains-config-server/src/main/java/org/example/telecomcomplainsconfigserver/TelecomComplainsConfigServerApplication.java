package org.example.telecomcomplainsconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@EnableConfigServer
@SpringBootApplication
public class TelecomComplainsConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelecomComplainsConfigServerApplication.class, args);
    }

}
