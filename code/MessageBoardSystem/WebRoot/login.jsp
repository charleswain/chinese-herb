<%@ page language="java" pageEncoding="UTF-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户登录</title> 
  </head> 
 	<script type="text/javaScript">
 		function refresh()
 		{
 			var img = document.getElementById("imgValidationCode") ; 
 			img.src = "ValidationCode" ; 
 		}
 		function checkLogin()
 		{
 			var username = document.getElementById("username_id"); 
 			if (username.value == "" )
 			{
 				alert("请输入用户名!") ;
 				username.focus() ; 
 				return ; 
 			}
 			var password = document.getElementById("password_id") ;
 			if (password.value == "")
 			{
 				alert("密码不能为空");
 				password.focus() ; 
 				return ;
 			}
 			var validationCode = document.getElementById("validationCode_id") ; 
 			if ( validationCode.value == "")
 			{
 				alert("验证吗不能为空") ;
 				validationCode.focus() ;
				return ; 
 			}
 			login_form.submit() ; 
 		}
 	</script> 
  <body>
  	<center> 
	  	<h2>用户登录</h2>
	  	<form name = "login_form" action = "LoginServlet" method = "post" >
  			<table>
  				<tr>
  					<td>
  			 			用户名：
  			 		</td>
  			 		<td>
  			 			<input type = "text" id = "username_id" value = "${requestScope.username}" name = "username"  
  							size = "25" />${requestScope.userError}
  					</td>
  				</tr>
  				<tr>
  					<td>
  						密  码：
  					</td>
  				 	<td>
						<input type="password" id="password_id" name="password" size="25">
					</td>
				</tr>
				<tr>
					<td>
						验证码：
					</td>
					<td>
						<input type = "text" id = "validationCode_id" name = "validationCode"
							style = "width:60px;margin-top:10px"/>  
						<img id = "imgValidationCode" src = "ValidationCode"/>
						<input type = "button" value = "刷新"  onclick = "refresh()"/>
						${requestScope.codeError}
					</td>
				</tr> 
  			</table>
  			<input type = "button" value = "登录" onclick = "checkLogin()">
  			<input type = "submit" value = "注册" name = "register">
  		</form>
  	</center>
  </body>
</html>
