package ru.liga.kitchenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.liga.common","ru.liga.kitchenservice"})
public class KitchenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KitchenServiceApplication.class, args);
    }

}
