<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>本草中国信息检索系统</title>
</head>
<body>
<div>
<a style="float:right;" href="">如需留言请登录；没有账号？点击这里注册。<a>
	<input id="search" type="text" value="${name}"  />
	<button  id="searchbtn" type="button">搜索</button>
	<div style="margin-top:20px;">
	<a id="back" href="prescription?name=">试试方剂全文检索？</a>
	</div>
</div>
    <center>
         <h2>
            ${name} 
         </h2>
         <c:choose>
         	<c:when  test="${descript!=null}">
		         <span>
		           ${descript}
		         </span>
         		<img src="${image}" />
         	</c:when>
            <c:otherwise>
          	  	<span> 您要查找的页面不存在！</span>
          	 </c:otherwise>
          </c:choose>
    </center>
    	<script>
		var btn = document.getElementById("searchbtn");
		var txt = document.getElementById("search");
		var ahref = document.getElementById("back");
		
		ahref.href="prescription?name="+txt.value;
		
		btn.onclick = function(){
	    	window.location.assign(encodeURI("search?name="+txt.value));
		}
		txt.onkeydown = function(e){

			if(e.keyCode == 13){
				window.location.assign(encodeURI("search?name="+txt.value));
			}
		};
	</script>
</body>
</html>