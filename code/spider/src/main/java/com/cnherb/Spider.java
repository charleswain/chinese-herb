package com.cnherb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.sql.*;

public class  Spider {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/cnherb";
	static final String USER="root";
	static final String PASSWORD = "admin";
	

    public static void main(String[] args){
        String[] keywordArr = {
        		"人参","甘草","麻黄","桂枝","生姜","香薷","紫苏","荆芥","薄荷","桑叶"
        };
        String[] result = new String[2];
        String url = "https://baike.baidu.com/item/";
        for(String keyword : keywordArr) {
        	System.out.println(keyword);
        	String key="";
			try {
				key = URLEncoder.encode(keyword,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            result = Get_Url(url + key);
            result[0] = keyword;
//            for(String e: result) {
//            	System.out.print(e +" ");
//            }
            insert(result);
        }
        

        


    }
    
    public static void insert(String[] data) {
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
    		sql = "SELECT id  from herb where name='"+data[0]+"'";
    		ResultSet rs = stmt.executeQuery(sql);
    		if(!rs.next()) {
    			//插入数据
    			sql = "INSERT INTO herb(name, descript,image) values('"+data[0]+"','"+data[1]+"','"+data[2]
    					+"')";
    			
    			System.out.println(sql);
    			 stmt = conn.createStatement();
    			 int result = stmt.executeUpdate(sql);
    			 System.out.println(result);
   
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
    		
    		rs.close();
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

    public static String[] Get_Url(String url) {
    	String[]  output = new String[3];
        try {
            Document doc = Jsoup.connect(url) 
                //.data("query", "Java")
                //          //.userAgent("头部")
                //                    //.cookie("auth", "token")
                //                              //.timeout(3000)
                //                                        //.post()
                .get(); 
            //得到html的所有东西
            Element content = doc.selectFirst("div.main-content");
            //分离出html下<a>...</a>之间的所有东西
       //     Elements links = content.getElementsByClass("lemma-summary");
            //Elements links = doc.select("a[href]");
         //   System.out.println(links);
            // 扩展名为.png的图片
          //  Elements pngs = doc.select("img[src$=.png]");
            // class等于masthead的div标签
          //  Element masthead = doc.select("div.masthead").first();

//            for (Element content : contents) {
                Element link = content.selectFirst("div.lemma-summary");
                output[1] = link.text();
                //System.out.println(link.text());  
                
                Element sideContent = doc.selectFirst("div.side-content");
                Element pic = sideContent.selectFirst("div.summary-pic").selectFirst("img[src$=.jpg]");
                output[2] = pic.attr("src");
                //System.out.println(pic.attr("src"));

                Element basicInfo = doc.selectFirst("div.basic-info");
                Elements dtNameArr = basicInfo.getElementsByTag("dt");
                Elements dtValueArr = basicInfo.getElementsByTag("dd");
             //   output[2] = basicInfo.text();
                //System.out.println(basicInfo.text());
                
	            for(int i=0;i<dtNameArr.size();i++){
	            	String keyname = dtNameArr.get(i).text();
	            	String valuename = dtValueArr.get(i).text();
	               // System.out.println(keyname + " " + valuename);                	
	            }                
                System.out.println("end");


//            }

        } catch (IOException e) {
            e.printStackTrace();

        }
        	
        return output;
    }

}
