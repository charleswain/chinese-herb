package com.cnherb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Attr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.sql.*;
import java.util.*;

public class  SpiderPrescription {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cnherb?useUnicode=true&characterEncoding=utf-8";
	static final String USER="root";
	static final String PASSWORD = "admin";
	

    public static void main(String[] args){
        String[] keywordArr = {
        		//"A",
        		//"B",
        		//"C",
        	//	"D","E","F","G","H",
        		"I",
        		"J","K","L","M","N",
        		"O","P","Q","R","S","T","U","V","W","X","Y","Z"
        };
        HashMap<String, String> result = new HashMap<String, String>();
        
        String url = "http://zhongyaofangji.com/";
        for(String keyword : keywordArr) {
        	System.out.println(keyword);
//        	String key="";
//			try {
//				key = URLEncoder.encode(keyword,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            result = Get_Url(url ,keyword);

//            for(String e: result) {
//            	System.out.print(e +" ");
//            }
    
            insert(result);
          //  break;
       
        }
        
        System.out.println("end");


    }
    
    public static void insert(HashMap<String, String> data) {
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
    		for(String key : data.keySet()) {
    		//	key = URLEncoder.encode(key,"UTF-8");
	    		sql = "SELECT id  from prescription where name='"+key+"'";
	    		ResultSet rs = stmt.executeQuery(sql);
	    		if(!rs.next()) {
	    			//插入数据
	    			sql = "INSERT INTO prescription(name, url) values('"+key+"','"+data.get(key)+
	    					"')";
	    			
	    			System.out.println(sql);
	    			//stmt = conn.createStatement();
	    			int result = stmt.executeUpdate(sql);
	    			System.out.println(result);
	   
	    		}else {
	    			System.out.println(key +"exsits");
	    		}

    		//展开结果集
//    		while(rs.next()) {
//    			int id  = rs.getInt("book_id");
//    			String name = rs.getString("name");
//    			int number = rs.getInt("number");
//    			
//    			//输出数据
//    			System.out.println(id+ " " + name + " " + number);
//    		}
    		
//    		rs.close();
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
    	System.out.println("GoodBye!");
    	
    }

    public static HashMap<String, String> Get_Url(String url, String key) {
    	
    	HashMap<String, String>output = new HashMap<String, String>();
    
        try {
        	System.out.println(url+key);
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
            	return output;
            }
            content = content.selectFirst("ul.uzyc");
            if(content ==null) {
            	return output;
            }
            //分离出html下<a>...</a>之间的所有东西
         //   Elements links = content.children();
            Elements links = content.select("a[href]");
         //   System.out.println(links);
            // 扩展名为.png的图片
          //  Elements pngs = doc.select("img[src$=.png]");
            // class等于masthead的div标签
          //  Element masthead = doc.select("div.masthead").first();

           for (Element a : links) {
 
//               System.out.println(link.html() +":test");
//                Element a =  link.child(0);
                 System.out.println(a.text());
                 output.put(a.text(), a.attr("href"));
                 
//                Element sideContent = doc.selectFirst("div.side-content");
//                Element pic = sideContent.selectFirst("div.summary-pic").selectFirst("img[src$=.jpg]");
//                output[2] = pic.attr("src");
//                //System.out.println(pic.attr("src"));
//
//                Element basicInfo = doc.selectFirst("div.basic-info");
//                Elements dtNameArr = basicInfo.getElementsByTag("dt");
//                Elements dtValueArr = basicInfo.getElementsByTag("dd");
//             //   output[2] = basicInfo.text();
//                //System.out.println(basicInfo.text());
//                
//	            for(int i=0;i<dtNameArr.size();i++){
//	            	String keyname = dtNameArr.get(i).text();
//	            	String valuename = dtValueArr.get(i).text();
//	               // System.out.println(keyname + " " + valuename);                	
         }                
//                System.out.println("end");
//
//
////            }
            System.out.print(output);

        } catch (IOException e) {
            e.printStackTrace();

        }
        	
        return output;
    }

}
