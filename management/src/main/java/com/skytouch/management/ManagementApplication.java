package com.skytouch.management;

import com.skytouch.commonlibrary.config.QueuesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@SpringBootApplication(scanBasePackages = "com.skytouch", exclude = {RabbitAutoConfiguration.class})
@Import(QueuesConfig.class)
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }
}
