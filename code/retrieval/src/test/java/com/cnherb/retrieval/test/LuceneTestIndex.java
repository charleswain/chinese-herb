package com.cnherb.retrieval.test;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;
import org.junit.Assert;

import com.cnherb.retrieval.lucene.*;
	
public class LuceneTestIndex {
   private String indexDir = "E:/Lucene/Index";
   private String dataDir = "E:/Lucene/Data";
   private Indexer indexer;

	@Test
   public void createIndex() throws IOException{
      indexer = new Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis();	
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      long endTime = System.currentTimeMillis();
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "
         +(endTime-startTime)+" ms");		
      Assert.assertTrue(true);
   }
}