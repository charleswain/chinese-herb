package com.cnherb.retrieval.test;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerTest {
	
	 @Test
	 public void  index()  throws IOException {
        String ID;
        String content;

        ID = "1231";
        content = "BuzzFeed has compiled an amazing array of " +
                "ridiculously strange bridesmaid snapshots, courtesy of Awkward Family Photos. ";
        indexPost(ID, content);

        ID = "1234";
        content = "Lucene是apache软件基金会4 jakarta项目组的一个子项目，是一个开放源代码的全文检索引擎工具包";
        indexPost(ID, content);

        ID = "1235";
        content = "Lucene不是一个完整的全文索引应用，而是是一个用Java写的全文索引引擎工具包，它可以方便的嵌入到各种应用中实现";
        indexPost(ID, content);
	 }
	
  public static void indexPost(String ID, String content)  throws IOException {
        File indexDir = new File("E:/Lucene/index1");

        Analyzer analyzer = new IKAnalyzer();

        TextField postIdField = new TextField("id", ID, Store.YES);  // 不要用StringField
        TextField postContentField = new TextField("content", content, Store.YES);

        Document doc = new Document();
        doc.add(postIdField);
        doc.add(postContentField);
        IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_4_10_1, analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        try {
            Directory fsDirectory = FSDirectory.open(indexDir);
            IndexWriter indexWriter = new IndexWriter(fsDirectory, iwConfig);
            indexWriter.addDocument(doc);
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        search();
    }

    public static void search() {
        Analyzer analyzer = new IKAnalyzer();
        File indexDir = new File("E:/Lucene/index1");
        try {
            Directory fsDirectory = FSDirectory.open(indexDir);
            DirectoryReader ireader = DirectoryReader.open(fsDirectory);
            IndexSearcher isearcher = new IndexSearcher(ireader);


            QueryParser qp = new QueryParser(Version.LUCENE_4_10_1,"content", analyzer);         //使用QueryParser查询分析器构造Query对象
            qp.setDefaultOperator(QueryParser.AND_OPERATOR);
            Query query = qp.parse("索引擎工具");     // 搜索Lucene
            TopDocs topDocs = isearcher.search(query , 5);      //搜索相似度最高的5条记录
            System.out.println("命中:" + topDocs.totalHits);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (int i = 0; i < topDocs.totalHits; i++){
                Document targetDoc = isearcher.doc(scoreDocs[i].doc);
                System.out.println("内容:" + targetDoc.toString());
            }

        } catch (Exception e) {

        }
    }
    


	public  void analyzer1() throws IOException {
	     // TODO Auto-generated method stub
        //建立索引
        String text1 = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切" +
                "分算法。";
        String text2 = "中文分词工具包可以和lucene是一起使用的";
        String text3 = "中文分词,你妹";
        String fieldName = "contents";
        Analyzer analyzer = new IKAnalyzer();
        RAMDirectory directory = new RAMDirectory();
        IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_34, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
        Document document1 = new Document();
        document1.add(new Field("ID", "1", Field.Store.YES, Field.Index.NOT_ANALYZED));
        document1.add(new Field(fieldName, text1, Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(document1);

        Document document2 = new Document();
        document2.add(new Field("ID", "2", Field.Store.YES, Field.Index.NOT_ANALYZED));
        document2.add(new Field(fieldName, text2, Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(document2);

        Document document3 = new Document();
        document3.add(new Field("ID", "2", Field.Store.YES, Field.Index.NOT_ANALYZED));
        document3.add(new Field(fieldName, text3, Field.Store.YES, Field.Index.ANALYZED));
        indexWriter.addDocument(document3);
        indexWriter.close();


        //搜索
        IndexReader indexReader = IndexReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(indexReader);
        String request = "中文分词工具包";
        QueryParser parser = new QueryParser(Version.LUCENE_40, fieldName, analyzer);
        parser.setDefaultOperator(QueryParser.AND_OPERATOR);
        try {
            Query query = parser.parse(request);
            TopDocs topDocs = searcher.search(query, 5);
            System.out.println("命中数:"+topDocs.totalHits);
            ScoreDoc[] docs = topDocs.scoreDocs;
            for(ScoreDoc doc : docs){
                Document d = searcher.doc(doc.doc);
                System.out.println("内容:"+d.get(fieldName));
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(indexReader != null){
                try{
                    indexReader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(directory != null){
                try{
                    directory.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public void analyzer() {
		 String keyWord = "IKAnalyzer的分词效果到底怎么样呢，我们来看一下吧：这里是简单的数据";  
	        //创建IKAnalyzer中文分词对象  
	        IKAnalyzer analyzer = new IKAnalyzer(true);  
	        // 使用智能分词  
	        //analyzer.setUseSmart(true);  
	        // 打印分词结果  
	        try {  
	            printAnalysisResult(analyzer, keyWord);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		
	}
	
    /** 
     * 打印出给定分词器的分词结果 
     *  
     * @param analyzer 
     *            分词器 
     * @param keyWord 
     *            关键词 
     * @throws Exception 
     */  
    private static void printAnalysisResult(Analyzer analyzer, String keyWord)  
            throws Exception {  
        System.out.println("["+keyWord+"]分词效果如下");  
        TokenStream tokenStream = analyzer.tokenStream("content",  
                new StringReader(keyWord));  
        tokenStream.addAttribute(CharTermAttribute.class);  
        tokenStream.reset();
        while (tokenStream.incrementToken()) {  
            CharTermAttribute charTermAttribute = tokenStream  
                    .getAttribute(CharTermAttribute.class);  
            System.out.println(charTermAttribute.toString());  
  
        }  
    }  
}
