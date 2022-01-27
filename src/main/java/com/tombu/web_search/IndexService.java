package com.tombu.web_search;

public class IndexService {
    private DocumentIndex index;

    IndexService() {
        _service = this;
    }

    public void init() throws Exception {
        index = new DocumentIndex("default");
        index.init();
    }

    public static DocumentIndex FindIndex(String name) {
        return _service.index;
    }

    private static IndexService _service = null;
}
