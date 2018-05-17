package com.cnherb.retrieval.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class Searcher {
	
   IndexSearcher indexSearcher;
   QueryParser queryParser;
   Query query;
   
public Searcher(String indexDirectoryPath) throws IOException{
	  File indexDir = new File(indexDirectoryPath);
      Directory indexDirectory = FSDirectory.open(indexDir);
      DirectoryReader iReader = DirectoryReader.open(indexDirectory);
      
      indexSearcher = new IndexSearcher(iReader);
      queryParser = new QueryParser(Version.LUCENE_4_10_1,
         LuceneConstants.CONTENTS,
         new IKAnalyzer()
      );
     // queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
   }
   
   public TopDocs search( String searchQuery) 
      throws IOException, ParseException{
      query = queryParser.parse(searchQuery);
      return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
   }

   public Document getDocument(ScoreDoc scoreDoc) 
      throws CorruptIndexException, IOException{
      return indexSearcher.doc(scoreDoc.doc);	
   }

   public void close() throws IOException{
    //  indexSearcher.close();
   }
}