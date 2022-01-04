package com.tombu.web_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.HashSet;

@ServletComponentScan
@SpringBootApplication
public class WebSearchApplication {

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication();
        sa.addListeners(new WebSearchInitializer());
        sa.setSources(new HashSet(Arrays.asList(WebSearchApplication.class)));
        sa.run(args);
    }

}
