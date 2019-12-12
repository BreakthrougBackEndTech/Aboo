package com.aboo.movie.springcloud;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-12-11 13:21
 **/
@SpringBootApplication
@EnableAdminServer
public class BootAdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootAdminServerApplication.class, args);
    }
}

