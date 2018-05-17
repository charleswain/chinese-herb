package com.cnherb.retrieval.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class Indexer {

   private IndexWriter writer;

   public Indexer(String indexDirectoryPath) throws IOException{
      //this directory will contain the indexes
      Directory indexDirectory = 
         FSDirectory.open(new File(indexDirectoryPath));

      //create the indexer
//      writer = new IndexWriter(indexDirectory, 
//         new StandardAnalyzer(Version.LUCENE_36),true,
//         IndexWriter.MaxFieldLength.UNLIMITED);
      Analyzer  analyzer = new IKAnalyzer(true);
      
      IndexWriterConfig writeConfig = new IndexWriterConfig(Version.LUCENE_4_10_1, analyzer);
      writeConfig.setMaxBufferedDocs(9);
      writeConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
      writer = new IndexWriter(indexDirectory,writeConfig);
      
   }
  

   public void close() throws CorruptIndexException, IOException{
      writer.close();
   }

   private Document getDocument(File file) throws IOException{
      Document document = new Document();

      //index file contents
      Field contentField = new TextField(LuceneConstants.CONTENTS, 
         new FileReader(file));
      //index file name
      Field fileNameField = new TextField(LuceneConstants.FILE_NAME,
         file.getName(),
         Field.Store.YES);
      //index file path
      Field filePathField = new TextField(LuceneConstants.FILE_PATH,
         file.getCanonicalPath(),
         Field.Store.YES);

      document.add(contentField);
      document.add(fileNameField);
      document.add(filePathField);

      return document;
   }   

   private void indexFile(File file) throws IOException{
      System.out.println("Indexing "+file.getCanonicalPath());
      Document document = getDocument(file);
      writer.addDocument(document);
   }

   public int createIndex(String dataDirPath, FileFilter filter) 
      throws IOException{
      //get all files in the data directory
      File[] files = new File(dataDirPath).listFiles();

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file)
         ){
            indexFile(file);
         }
      }
      return writer.numDocs();
   }
}