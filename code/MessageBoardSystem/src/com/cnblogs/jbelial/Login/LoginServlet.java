package com.cnblogs.jbelial.Login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Common.User;

import com.cnblogs.jbelial.DBServlet.DB;

public class LoginServlet extends HttpServlet {

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
			throws ServletException, IOException {
 
//		设置字符集编码
		request.setCharacterEncoding("UTF-8") ; 
		response.setContentType("UTF-8");  
//		判断是否跳到【注册】
		if (request.getParameter("register") != null)
		{
			response.sendRedirect("register.jsp") ;
			return ; 
		}
		DB db = new DB() ;
//		设置跳转界面
		String page = "login.jsp" ;  
		String username = null ;
		try
		{
//			获取请求页面的参数
			username = request.getParameter("username") ; 
			String password = request.getParameter("password") ; 
			String validationCode = request.getParameter("validationCode") ; 
//			验证码检测
			if (!db.checkValidationCode(request, validationCode))
					return ; 
			User user = db.checkUser(username,password) ; 
			if (user == null)
				request.setAttribute("userError", "用户名或密码错误") ; 
			if (user != null)
			{
//				如果根据检查，user不为空，表示用户名正确和密码正确，进行下一步操作。
				ArrayList arrayList = new ArrayList() ; 
				arrayList = db.findLyInfo() ; 
				request.setAttribute("arrayList", arrayList) ;
//				设置跳转到主界面
				page = "main.jsp" ;
				request.getSession().setAttribute("user", user) ; 
			}
	
			
		}
		catch(Exception e){}
		finally
		{
			request.setAttribute("username", username) ; 
			RequestDispatcher rd = request.getRequestDispatcher("/"+page) ; 
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
