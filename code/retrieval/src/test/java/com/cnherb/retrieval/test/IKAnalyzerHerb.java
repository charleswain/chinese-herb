package com.cnherb.retrieval.test;

import org.junit.Test;
import org.junit.Assert;

import java.io.StringReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.sql.Connection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IKAnalyzerHerb {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";
	static final String USER="root";
	static final String PASSWORD = "wx1fb381ba6beed623";
	
	/*
	 * 从数据库中取出方剂组成
	 * 
	 */
	
	public void divideWord() {
//	    HashMap<String , HashMap<String, String>> output = new  HashMap<String ,HashMap<String, String>>();

	   	try {
	 		//注册JDBC驱动
			Class.forName(JDBC_DRIVER);
			System.out.println("连接数据库");
			//打开链接
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = null;
			//执行查询
			System.out.println("实例化Statement对象");
			stmt = conn.createStatement();
		
			while(true) {
				String sql;
		 		sql = "SELECT id,compose from prescription where comDivide is null limit 0,50";
		 		ResultSet rs = stmt.executeQuery(sql);
		 		if(rs == null) {
		 			break;
		 		}
		 		while(rs.next()) {
		 			Statement stmtUpdate = null;
		 			String data=rs.getString("compose");
		 			
	 				Pattern rm = Pattern.compile("([\\(\\（][^\\\\(\\\\（\\\\)\\\\）]+[\\)\\）])");
	 				Matcher matcher = rm.matcher(data);
	 				String filterData = matcher.replaceAll(" ");
	 				
		 			String[] dataArr = filterData.split(",");
		 			if(dataArr.length ==1) {
		 				dataArr = filterData.split("，");
		 			}
		 			if(dataArr.length ==1) {
		 				dataArr = filterData.split("、");
		 			}
		 			if(dataArr.length ==1) {
		 				dataArr = filterData.split(" ");
		 			}
	//	 			String[] composeArr ;
		 			String compose="";
		 			for(int i=0;i<dataArr.length;i++) {
		 				Pattern pattern = Pattern.compile("(\\D*)([\\d]+)(.*)");
		 				Matcher mt = pattern.matcher(dataArr[i]);
		 				if(mt.find()) {
		 					compose += ","+mt.group(1) ;
		 				}else {
		 					compose += ","+dataArr[i];
		 				}
		 				
		 			}
		 			//去掉头部逗号
		 			compose = compose.substring(1);
		 			
		 			
		 			stmtUpdate = conn.createStatement();
	//	 			String data = analyzer(rs.getString("compose"));
		 			String updateSql = "Update prescription Set comDivide='"+ compose +"' where id="+rs.getShort("id")+"";
//		 			System.out.println(updateSql);
		    		int result = stmtUpdate.executeUpdate(updateSql);
		    		System.out.println(rs.getString("id") +"result:"+result);
		    		stmtUpdate.close();
		 		}
			}
	 		stmt.close();
    		conn.close();
    	}catch(SQLException se){
    		se.printStackTrace();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		//关闭资源
    	}
	}
	
	public String analyzer(String keyWord) {
//		String keyWord = "阿胶（炒）1钱，熟地1钱，艾叶2钱，川芎8分，大枣3枚。  ";
		String result="";
	        //创建IKAnalyzer中文分词对象  
	        IKAnalyzer analyzer = new IKAnalyzer(true);  
	        // 使用智能分词  
	        //analyzer.setUseSmart(true);  
	        // 打印分词结果  
	        try {  
	             result = printAnalysisResult(analyzer, keyWord);  
	           // System.out.println(result);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
		return result;
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
    private static String printAnalysisResult(Analyzer analyzer, String keyWord)  
            throws Exception {  
        System.out.println("["+keyWord+"]分词效果如下");  
        TokenStream tokenStream = analyzer.tokenStream("content",  
                new StringReader(keyWord));  
        tokenStream.addAttribute(CharTermAttribute.class);  
        tokenStream.reset();
        String result="";
        while (tokenStream.incrementToken()) {  
            CharTermAttribute charTermAttribute = tokenStream  
                    .getAttribute(CharTermAttribute.class);  
            result+=charTermAttribute.toString() +"\n";
           // System.out.println(charTermAttribute.toString());  
  
        } 
        return result;
    } 
    
    
}
