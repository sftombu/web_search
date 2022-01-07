package com.tombu.web_search;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController()
public class WebSearchApi {
    private static IndexService indexService = null;

    @CrossOrigin
    @PostMapping("/index_document")
    public String indexDocument(@RequestParam(name = "index_name") String indexName,
                                @RequestParam(name = "id") String id,
                                @RequestParam(name = "user_id") String userId,
                                @RequestParam(name = "title") String title,
                                @RequestParam(name = "url") String url,
                                @RequestParam(name = "tags", required = false) String tags,
                                @RequestParam(name = "body") String body) throws Exception {
        IndexService.FindIndex(indexName).indexDocument(id, userId, title, url, tags, body);
        return "Ok";
    }

    @CrossOrigin
    @PostMapping("/search_index")
    public String searchIndex(@RequestParam(name = "index_name") String indexName,
                                @RequestParam(name = "search_phrase") String searchPhrase) throws Exception {
        IndexService.FindIndex(indexName).search(searchPhrase);
        return "Ok";
    }

    public static void InitApi() throws Exception {
        indexService = new IndexService();
        indexService.init();
        System.out.println("InitApi");
    }
}
