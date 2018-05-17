package com.cnherb.retrieval.test;

import java.io.IOException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.junit.Assert;

import com.cnherb.retrieval.lucene.*;
	
public class LuceneTester {
   private String indexDir = "E:/Lucene/Index";
//   private String dataDir = "E:/Lucene/Data";
   private Searcher searcher;

   
   @Test
   public void searchNotFound() throws IOException,ParseException{
	  String searchQuery = "信息";

      searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
//      QueryParser parser = new QueryParser(Version.LUCENE_42);
//      Query query = parser.parse(searchQuery);
      
      TopDocs hits = searcher.search(searchQuery);
      long endTime = System.currentTimeMillis();
   
      System.out.println(hits.totalHits +
         " documents found. Time :" + (endTime - startTime));
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "
            + doc.get(LuceneConstants.FILE_PATH));
      }
      searcher.close();
      Assert.assertTrue(true);
   }
	
	@Test
	 public void searchFound() throws IOException, ParseException{
		  String searchQuery = "hello world";
	      searcher = new Searcher(indexDir);
	      long startTime = System.currentTimeMillis();
	      TopDocs hits = searcher.search(searchQuery);
	      long endTime = System.currentTimeMillis();
	   
	      System.out.println(hits.totalHits +
	         " documents found. Time :" + (endTime - startTime));
	      for(ScoreDoc scoreDoc : hits.scoreDocs) {
	         Document doc = searcher.getDocument(scoreDoc);
	            System.out.println("File: "
	            + doc.get(LuceneConstants.FILE_PATH));
	      }
	      searcher.close();
	      Assert.assertTrue(true);
	  }
}