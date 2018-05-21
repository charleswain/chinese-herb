package com.cnherb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.Matches;
import org.w3c.dom.Attr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.sql.*;
import java.util.*;

public class  SpiderPrescription2 {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cnherb?useUnicode=true&characterEncoding=utf-8";
	static final String USER="root";
	static final String PASSWORD = "admin";
	

    public static void main(String[] args) throws  InterruptedException, Exception {
        String[] keywordArr = {
        		//"A",
        		//"B",
        		//"C",
        	//	"D","E","F","G","H",
        		"I",
        		"J","K","L","M","N",
        		"O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        String url = "http://zhongyaofangji.com/";
    	HashMap<String, HashMap<String,String>>result = new HashMap<String, HashMap<String,String>>();
    	HashMap<String, String>getResult = new HashMap<String,String>();
    	
         result = select();
        
        while(!result.isEmpty()) {
	        for(String key : result.keySet()) {
	            	System.out.println(key +":" +result.get(key));
	//            	String key="";
	//    			try {
	//    				key = URLEncoder.encode(keyword,"UTF-8");
	//    			} catch (UnsupportedEncodingException e) {
	//    				// TODO Auto-generated catch block
	//    				e.printStackTrace();
	//    			}
	            	url= result.get(key).get("url").toString();
	            	
	            	getResult = Get_Url(url ,"");
	            	getResult.put("id", key);
	                System.out.println(getResult);
	                update(getResult);
	              //  break;
	
	          // insert(data);
	//    	   break;
	        };
	        
	        
	       Thread.sleep(3000);
	       result = select();
        }

        System.out.println("end");


    }
    
   public static HashMap<String ,HashMap<String, String>> select(){
	     
	    HashMap<String , HashMap<String, String>> output = new  HashMap<String ,HashMap<String, String>>();
	   	Connection conn = null;
	   	Statement stmt = null;
	   	try {
	 		//注册JDBC驱动
			Class.forName(JDBC_DRIVER);
			//打开链接
			System.out.println("连接数据库");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			//执行查询
			System.out.println("实例化Statement对象");
			stmt = conn.createStatement();
		
			String sql;
	 		sql = "SELECT id,name, url  from prescription where length(content)<70 limit 10";
	 	
	 		ResultSet rs = stmt.executeQuery(sql);
	 		
	 		while(rs.next()) {
	 			HashMap<String, String> columns = new HashMap<String,String>();
				columns.put("name",rs.getString("name"));
				columns.put("url",rs.getString("url"));
	 	
	 			output.put(rs.getString("id"), columns) ;

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
		
	   return output;
   }
    
    public static void update(HashMap<String, String> data) {
    	if(data.isEmpty()) {
    		System.out.println("data is empty");
    		return;
    	}
    	
    	Connection conn = null;
    	Statement stmt = null;

    	try {
    		//注册JDBC驱动
    		Class.forName(JDBC_DRIVER);
    		//打开链接
    		System.out.println("连接数据库");
    		conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    		//执行查询
    		System.out.println("实例化Statement对象");
    		stmt = conn.createStatement();
    		String sql;
    	
    		sql = "UPDATE prescription set content='"+data.get("content") +"' where id='"+data.get("id")+"'";
    		
    		int rs = stmt.executeUpdate(sql);
    		System.out.println(rs);
    		
    		stmt.close();
    		conn.close();
			
    	}catch(SQLException se){
    		se.printStackTrace();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		//关闭资源
    		
    	}
    	System.out.println("GoodBye!");
    	
    }

    public static HashMap<String, String> Get_Url(String url, String key) {
    	
    	HashMap<String, String>output = new HashMap<String, String>();
    
        try {
        	System.out.println(url+key+"try to connect");
            Document doc = Jsoup.connect(url+key) 
                //.data("query", "Java")
                //          //.userAgent("头部")
                //                    //.cookie("auth", "token")
                //                              //.timeout(3000)
                //                                        //.post()
                .get(); 
            //得到html的所有东西
            Element content = doc.getElementById("divMain");
            if(content ==null) {
            	System.out.println("empty divMian");
            	return output;
            }
           
        	content = content.selectFirst("div.article");
        
            if(content ==null) { 
	            content = doc.getElementById("divMain").selectFirst("div.spider");
	            if(content ==null) { 
	            	System.out.println("empty spider");
	            	return output;
	            }
            }
            

            //分离出html下<a>...</a>之间的所有东西
         //   Elements links = content.children();
//            Element name = content.selectFirst("h2");
//            Element components = name.nextElementSibling();
//            Element madeMethod = components.nextElementSibling();
//            Element function = content.selectFirst("div.yfpzz");
//            Element use = function.nextElementSibling().nextElementSibling();
//            Element cite = use.nextElementSibling();
            
            output.put("content", content.text());
//            output.put("name", name.text());
//            output.put("components", components.text());
//            output.put("madeMethod", madeMethod.text());
//            output.put("function", function.text());
//            output.put("use", use.text());
//            output.put("cite", cite.text());
            
//            System.out.print(name.text() +" :" + components.text()+" "+ cite.text());
            
            
         //   System.out.println(links);
            // 扩展名为.png的图片
          //  Elements pngs = doc.select("img[src$=.png]");
            // class等于masthead的div标签
          //  Element masthead = doc.select("div.masthead").first();

          //  System.out.print(output);

        } catch (IOException e) {
            e.printStackTrace();

        }
        	
        return output;
    }

}
