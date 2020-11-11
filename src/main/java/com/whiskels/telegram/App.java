package com.whiskels.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

//Spring boot annotation that includes @Configuration, @EnableAutoConfiguration, @ComponentScan
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Necessary code to start bot as in
        // https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter
        ApiContextInitializer.init();

        SpringApplication.run(App.class, args);
    }
}
