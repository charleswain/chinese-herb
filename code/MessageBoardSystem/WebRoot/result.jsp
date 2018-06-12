<%@ page language="java"  pageEncoding="UTF-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  	<form name = "result_form" action = "${requestScope.page}" , method = "post"> 
  	  ${requestScope.info} 
  	  	<input type = "submit" value = "确定" >  
  	</form>
  </body>
</html>
