<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    	<title>留言板</title>
</head>
<body>
  	<center>
  		<h2>留言板</h2>
  	  	<form action="addMessageServlet" method="post">
  			<table>
  		    	<tr><td>留言标题</td>
  				<td><input type="text" name="title"/></td></tr>
  			<tr><td>留言内容</td>
  				<td><textarea name="content" rows="5" cols="35"></textarea></td>
			</tr>
  			</table>
  		<input type="submit" value="提交"/>
  		<input type="reset" value="重置"/>
  	</form>
	</center>
</body>
</html>
