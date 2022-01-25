package com.tombu.web_search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DocumentIndex {
    private String name;
    Directory index;
    StandardAnalyzer analyzer;
    IndexWriterConfig indexWriterConfig;
    IndexWriter writer;

    public DocumentIndex(String name) {
        this.name = name;
    }

    public void init() throws Exception {
        String indexPath = System.getProperty("lucene_path");
        indexPath = (indexPath == null) ? "/luceneindex" : indexPath;
        index = FSDirectory.open(Paths.get(indexPath));
        analyzer = new StandardAnalyzer();
        indexWriterConfig = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(index, indexWriterConfig);
    }

    public void indexDocument(String id, String userId, String createdDate, String title, String url,  String body) throws Exception {
        Document document = new Document();
        document.add(new TextField("id", id, Field.Store.YES));
        document.add(new TextField("user_id", userId, Field.Store.YES));
        document.add(new TextField("created_date", createdDate, Field.Store.YES ));
        document.add(new TextField("title", title, Field.Store.YES));
        document.add(new TextField("url", url, Field.Store.YES));
        document.add(new TextField("body", body, Field.Store.NO));
        writer.addDocument(document);
        writer.commit();
        System.out.println("Success Indexing");
    }

    public List<ResultDocument> search(String userId, String searchPhrase) throws Exception {
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        Query userIdQuery = new QueryParser("user_id",analyzer).parse(userId);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        String[] fields = {"title", "url", "body"};
        BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
        Query query = MultiFieldQueryParser.parse(searchPhrase, fields, flags, analyzer);
        builder.add(userIdQuery, BooleanClause.Occur.FILTER);
        builder.add(query, BooleanClause.Occur.MUST);
        BooleanQuery fullQuery = builder.build();
        TopDocs topDocs = searcher.search(fullQuery,20);
        List<ResultDocument> result = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document document = searcher.doc(scoreDoc.doc);
            result.add(new ResultDocument(document.get("title"),document.get("url"),document.get("created_date"),
                    document.get("user_id")));
        }
        System.out.println("found documents:" + result.size());
        return result;
    }
}
