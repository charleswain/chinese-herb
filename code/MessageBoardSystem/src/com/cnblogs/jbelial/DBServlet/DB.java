package com.cnblogs.jbelial.DBServlet;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import Common.Encrypter;
import Common.LeaveMessageTable; 
import Common.User;

public class DB{
	
//	�������ݿ����ӱ���
	private Connection connection ;  
//	�������
	private String sql_driver = "com.mysql.jdbc.Driver" ; 
	private String sql_url = "jdbc:mysql://localhost:3306/web_ly_table" ; 
	private String sql_username;

	{
		sql_username = "root";
	}

	public DB()
	{
		try
		{
   			//�������ݿ������
   			Class.forName(sql_driver) ;
			String sql_password = "hezuoan";
			connection = DriverManager.getConnection(sql_url,sql_username, sql_password) ;
   		}
		catch(Exception e)
		{
			e.printStackTrace() ; 
		}
	}
	
//	����ִ�и���SQL���ķ���
	private ResultSet execSQL(String sql , Object ...args) 
			throws SQLException
	{
		PreparedStatement pstmt = connection.prepareStatement(sql) ;
//		ΪPreparedStatement��������SQL����
		for (int i = 0 ; i < args.length ; ++ i)
		{
			pstmt.setObject(1 + i, args[i]) ; 
		}
//		����
		pstmt.execute() ; 
		return  pstmt.getResultSet(); 
	}
//	�˶��û��������֤���Ƿ���ȷ
	public boolean checkValidationCode(HttpServletRequest request , 
			String validationCode)
	{
		String validationCodeSession = (String) request.getSession().getAttribute("validationCode") ; 
		if(validationCodeSession == null)
		{
//			��result.jsp������Ϣ
			request.setAttribute("info" , "��֤�����") ;
//			��login.jsp������Ϣ
			request.setAttribute("codeError", "��֤�����") ;
			return false ; 
		}
		if(!validationCode.equalsIgnoreCase(validationCodeSession))
		{
			request.setAttribute("info","��֤�����") ;
			request.setAttribute("codeError", "��֤�����") ;
			return false ; 
		}
		return true; 
	}
//	����û���¼��Ϣ
	public User checkUser(String username , String password)
	{
		try
		{
			String sql = "select * from user_table where username = ? and  password = ?" ;
			ResultSet rs = execSQL(sql,username,password) ; 
			User user = new User() ; 
			while (rs.next())
			{	
				user.setId(rs.getInt("id")) ; 
				user.setUsername(rs.getString("username")) ; 
				user.setPassword(rs.getString("password")) ; 
				return user ;
			} 
			return null ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null ; 
		}
	}
//	��������
	public boolean addInfo(LeaveMessageTable ly){
		try{
			String sql = "insert into ly_table(userId,data,title,content) values(?,?,?,?)" ; 
			execSQL(sql , ly.getUserId(), ly.getDate(),ly.getTitle(),ly.getContent());
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
//	�����û�
	public boolean insertUser(String username,String password){
		try{
			String sql = "insert into user_table(username,password) values(?,?)" ; 
			execSQL(sql ,username , password) ;  
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
//	��ȡ������Ϣ��
	public ArrayList findLyInfo()
	{
		 ArrayList arrayList = new ArrayList() ; 
		 try 
		 {
			 String sql = "select * from ly_table" ; 
			 ResultSet rs = execSQL(sql) ; 
			 while(rs.next())
			 {
				 LeaveMessageTable ly = new LeaveMessageTable() ; 
				 ly.setId(rs.getInt("id")) ; 
				 ly.setUserId(rs.getInt("userId")) ;
				 ly.setDate(rs.getDate("data")) ; 
				 ly.setTitle(rs.getString("title")) ;
				 ly.setContent(rs.getString("content")) ; 
				 arrayList.add(ly) ; 
			 }
			 return arrayList ; 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace() ; 
			 return null ; 
		 }
	}
//	��ȡ�û���
	public String getUserName(int id)
	{
		String username = null;
		try{
			String sql = "select username from user_table where id = ?" ;
			ResultSet rs= execSQL(sql , new Object[]{id});
			while(rs.next()){
				 username=rs.getString(1);
			}
			return username;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
