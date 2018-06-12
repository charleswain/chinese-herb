package com.cnblogs.jbelial.Register;

import java.io.IOException; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.User;

import com.cnblogs.jbelial.DBServlet.DB;

public class RegisterServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
//		设置字符集编码
		request.setCharacterEncoding("UTF-8") ; 
		response.setContentType("UTF-8");  
//		判断是否跳到【登录】
		if (request.getParameter("login") != null)
		{
			response.sendRedirect("login.jsp") ;
			return ; 
		}
		DB db = new DB() ; 
		String username  = null ;
//		设置result页面要跳转的页面
		String page = "login.jsp" ;
		try
		{ 
//			获取界面的用户名和密码参数
		    username = request.getParameter("username") ; 
			String password = request.getParameter("password") ;
			String validationCode = request.getParameter("validationCode") ; 
//			核对验证码
			if (!db.checkValidationCode(request, validationCode))  
				return ;   
			User user = db.checkUser(username,password) ; 
			if (user != null) 
			{
				request.setAttribute("info", username + "已被使用！") ;
				page = "register.jsp" ; 
			}
			else if (db.insertUser(username , password)) 
			{
//				定义result.jsp中使用的消息
				request.setAttribute("info" , "用户注册成功") ;
			} 
			request.setAttribute("page", page) ; 
		}
		catch(Exception e) { }
		finally
		{ 
//			跳转到result.jsp 
			RequestDispatcher rd = request.getRequestDispatcher("/result.jsp") ; 
			rd.forward(request, response) ; 
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response) ; 
	}

}
