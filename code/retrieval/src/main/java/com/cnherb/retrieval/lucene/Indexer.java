package com.cnherb.retrieval.lucene;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
//import java.io.BufferedReader;

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
//import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
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
      Analyzer  analyzer = new IKAnalyzer();
      
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
      TextField contentField = new TextField(LuceneConstants.CONTENTS, 
         new FileReader(file));
      //index file name
      TextField fileNameField = new TextField(LuceneConstants.FILE_NAME,
         file.getName(),
         Store.YES);
      //index file path
      TextField filePathField = new TextField(LuceneConstants.FILE_PATH,
         file.getCanonicalPath(),
         Store.YES);

      document.add(contentField);
      document.add(fileNameField);
      document.add(filePathField);

      return document;
   }   

   private void indexFile(File file) throws IOException{
      System.out.println("Indexing "+file.getCanonicalPath());
      Document document = getDocument(file);
//     //test---------->>
//      document = new Document();
//      String ID ="123";
//      String content = "哈哈哈哈数据hell";
//      FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
//      BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
//      StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
//      String s = "";
//      while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
//          sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
//          System.out.println(s);
//      }
//      bReader.close();
//      content = sb.toString();
//      
//      TextField postIdField = new TextField("id", ID, Store.YES);  // 不要用StringField
//      TextField postContentField = new TextField(LuceneConstants.CONTENTS, content, Store.NO);
//      //index file name
//      TextField fileNameField = new TextField(LuceneConstants.FILE_NAME,
//         file.getName(),
//         Store.YES);
//      //index file path
//      TextField filePathField = new TextField(LuceneConstants.FILE_PATH,
//         file.getCanonicalPath(),
//         Store.YES);
//
//      document.add(fileNameField);
//      document.add(filePathField);
//      document.add(postContentField);
//      document.add(postIdField);
//      //--------------<<
      
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