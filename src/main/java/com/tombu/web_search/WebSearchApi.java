package com.tombu.web_search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController()
public class WebSearchApi {
    private static IndexService indexService = null;

    @Value("string value")
    public static String IndexPath = null;


    @CrossOrigin
    @GetMapping("/hello")
    public String hello() {
        return "World 2";
    }

    @CrossOrigin
    @GetMapping("/test_index")
    public String testIndex() throws Exception {
        IndexService.FindIndex("default").indexDocument("1", "tombu", "0",
                "test title", "https://cloud.google.com/compute/docs/containers/configuring-options-to-run-containers",
                "test what happens washington indiana");
        return "ok";
    }

    @CrossOrigin
    @GetMapping("/test_search")
    public List<ResultDocument>  testSearch() throws Exception {
        List<ResultDocument> result = IndexService.FindIndex("default").search("tombu", "washington");
        return result;
    }

    @CrossOrigin
    @PostMapping("/index_document")
    public String indexDocument(@RequestParam(name = "index_name") String indexName,
                                @RequestParam(name = "id") String id,
                                @RequestParam(name = "user_id") String userId,
                                @RequestParam(name = "created_date") String createdDate,
                                @RequestParam(name = "title") String title,
                                @RequestParam(name = "url") String url,
                                @RequestParam(name = "body") String body) throws Exception {
        IndexService.FindIndex(indexName).indexDocument(id, userId, createdDate, title, url,  body);
        return "Ok";
    }

    @CrossOrigin
    @PostMapping("/search_index")
    public List<ResultDocument> searchIndex(@RequestParam(name = "index_name") String indexName,
                                @RequestParam(name = "user_id") String userId,
                                @RequestParam(name = "search_phrase") String searchPhrase) throws Exception {
        List<ResultDocument> result = IndexService.FindIndex(indexName).search(userId, searchPhrase);
        return result;
    }

    @CrossOrigin
    @GetMapping("/delete_index")
    public void deleteIndex(@RequestParam(name = "index_name") String indexName) throws Exception {
        IndexService.FindIndex(indexName).delete();
    }


    public static void InitApi() throws Exception {
        indexService = new IndexService();
        indexService.init();
        System.out.println("InitApi");
    }
}
