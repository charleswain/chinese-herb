package com.cnblogs.jbelial.addServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.LeaveMessageTable;
import Common.User;

import com.cnblogs.jbelial.DBServlet.DB;

public class addMessageServlet extends HttpServlet {

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
		doPost(request,response) ; 
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
		// 设置请求编码
		request.setCharacterEncoding("UTF-8");
		// 设置响应编码
		response.setContentType("UTF-8");
		// 获取title内容
		String title = request.getParameter("title");
		// 获取content内容
		String content = request.getParameter("content");
		// 从session中取出当前用户对象
		User user=(User) request.getSession().getAttribute("user");
		// 建立留言表对应JavaBean对象，把数据封装进去
		LeaveMessageTable ly = new LeaveMessageTable();
		ly.setUserId(user.getId());
		// 参数为获取的当前时间
		ly.setDate(new Date(System.currentTimeMillis()));
		ly.setTitle(title);
		ly.setContent(content);
		// 调DB类中的方法判断是否插入成功
		if(new DB().addInfo(ly)){
			response.sendRedirect("main.jsp");
		}
	}

}
