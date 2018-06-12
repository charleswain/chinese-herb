<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import = "com.cnblogs.jbelial.DBServlet.*" %>
<%@ page import = "Common.LeaveMessageTable;"%>
<html>
  <head>
    <title>留言版</title>
  </head> 
  <body> 
  	<center>
  		<h2>留言板</h2>
  	<form action="LeaveMessage.jsp" method = "post">
  		<table border="1"> 
  			<tr>
  				<th>用户名
  				<th>留言时间</th>
  				<th>标题</th>
  				<th>留言内容</th>
  			</tr>
		<%
			ArrayList arrayList = (ArrayList)session.getAttribute("arrayList");
		  			if( arrayList == null) arrayList = new DB().findLyInfo() ; 
		  			//创建迭代器
		  			Iterator iter = arrayList.iterator();
		  			while(iter.hasNext()){
		  				LeaveMessageTable ly=(LeaveMessageTable)iter.next();
		%>
  				<tr><td><%= new DB().getUserName(ly.getUserId()) %></td>
  					<td><%= ly.getDate().toString() %></td>
  					<td><%= ly.getTitle() %></td>
  					<td><%= ly.getContent() %></td></tr>
  		<% 
  			}
  		%>
  		</table>
  		<input type = "submit" value = "留言">
  	</form>
  	</center>
  </body>
</html>