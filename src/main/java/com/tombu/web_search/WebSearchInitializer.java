package com.tombu.web_search;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class WebSearchInitializer implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("event: " + event);
        if (event instanceof ApplicationReadyEvent) {
            try {
                WebSearchApi.InitApi();
            } catch (Exception e) {
                System.exit(-1);
            }
            System.out.println("System ready 1");
        }
    }
}
