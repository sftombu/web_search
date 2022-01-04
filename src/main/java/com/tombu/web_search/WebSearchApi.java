package com.tombu.web_search;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController()
public class WebSearchApi {
    @CrossOrigin
    @GetMapping("/index_doc")
    public String indexDoc() {
        return "Hello World";
    }

    public static void InitApi() {
        System.out.println("InitApi");
    }
}
