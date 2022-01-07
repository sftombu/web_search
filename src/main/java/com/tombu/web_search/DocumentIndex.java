package com.tombu.web_search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import sun.jvm.hotspot.debugger.remote.amd64.RemoteAMD64Thread;

import java.util.ArrayList;
import java.util.List;

public class DocumentIndex {
    private String name;
    Directory memoryIndex;
    StandardAnalyzer analyzer;
    IndexWriterConfig indexWriterConfig;
    IndexWriter writer;


    public DocumentIndex(String name) {
        this.name = name;
    }

    public void init() throws Exception {
        memoryIndex = new RAMDirectory();
        analyzer = new StandardAnalyzer();
        indexWriterConfig = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(memoryIndex, indexWriterConfig);
    }

    public void indexDocument(String id, String userId, String title, String url, String tags, String body) throws Exception {
        Document document = new Document();
        document.add(new TextField("id", id, Field.Store.YES));
        document.add(new TextField("user_id", userId, Field.Store.YES));
        document.add(new TextField("title", title, Field.Store.YES));
        document.add(new TextField("url", url, Field.Store.YES));
        tags = (tags == null) ? "" : tags;
        document.add(new TextField("tags", tags, Field.Store.YES));
        document.add(new TextField("body", body, Field.Store.NO));
        writer.addDocument(document);
        writer.commit();
        System.out.println("Success Indexing");
    }

    public void search(String searchPhrase) throws Exception {
        IndexReader reader = DirectoryReader.open(memoryIndex);;
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new QueryParser("body",analyzer).parse(searchPhrase);
        TopDocs topDocs = searcher.search(query,200);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            documents.add(searcher.doc(scoreDoc.doc));
        }
        System.out.println("found documents:" + documents.size());
    }
}
