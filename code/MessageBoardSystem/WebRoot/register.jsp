<%@ page language="java" pageEncoding="UTF-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户注册</title>
    <script type="text/javaScript">
 		function refresh()
 		{
 			var img = document.getElementById("imgValidationCode") ; 
 			img.src = "ValidationCode"+Math.random() ; 
 		}
 		function checkRegister()
 		{
 			var username = document.getElementById("username") ;
 			if(username.value == "" )
 			{
 				alert("请输入用户名！") ; 
 				//把焦点放到username输入文本上
 				username.focus() ; 
 				return ;
 			}
 			var password = document.getElementById("password") ;
 			if(password.value == "" )
 			{
 				alert("请输入密码！");
 				password.focus() ;
 				return ; 
 			}
 			var re_password = document.getElementById("re_password") ;
 			if(re_password.value != password.value)
 			{
 				alert("输入的密码不一致！") ; 
 				re_password.focus() ; 
 				return ; 
 			}
 			var validationCode = document.getElementById("validationCode") ;
 			if(validatoinCode.value == "")
 			{
 				alert("请输入验证码！");
 				validatoinCode.focus() ;
 				return ;
 			}
 			register_form.submit() ; 
 		}
 	</script> 
  </head>
  
  <body> 
  	<center>
  		<h2>用户注册</h2>
  		<form  name = "register_form" action = "RegisterServlet" method = "post">
  			<table>
  				<tr>
  					<td>
  						用户名：
  					</td>
  					<td>
  						<input type = "text" id = "username" name = "username" 
  							size = "25">
  					</td>
  				</tr>
  				<tr>
  					<td>
  						密码：
  					</td>
  					<td>
  						<input type = "password" id = "password" name = "password"
  							size = "25">
  					</td>
  				</tr>
  				<tr>
  					<td>
  						再次输入密码:
  					</td>
  					<td>
  					<input type = "password" id = "re_password" name = "re_password"
  							size = "25">
  					</td>
  				</tr>
  				<tr>
  					<td>
  						验证码：
  					</td>
  					<td>
						<input type = "text" id = "validationCode" name = "validationCode"
							style = "width:60px;margin-top:10px"/>  
						<img id = "imgValidationCode" src = "ValidationCode"/>
						<input type = "button" value = "刷新"  onclick = "refresh()"/>
					</td>
  				</tr>
  			</table>
  			<input type = "button" value = "注册" onclick = "checkRegister()"/>
  			<input type = "submit" value = "登录" name = "login">
  		</form>
  	</center>
  </body>
</html>
