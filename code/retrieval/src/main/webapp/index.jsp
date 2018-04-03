<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<html lang="zh-cn">
<head>
</head>
<body>
<h2>欢迎来到本草中国信息检索系统</h2>
<div>
	<input id="search" type="text" value=""  />
	<button  id="searchbtn" type="button">搜索</button>
</div>

<div style="margin-top:20px;"> * 尝试用 “人参” 搜索，看能得到什么？</div>
     <!--   <h3>
            <a href="hello?name=zhangsan">click to jump</a>
        </h3> -->

	<script>
		var btn = document.getElementById("searchbtn");
		var txt = document.getElementById("search");
		
		btn.onclick = function(){
	    	window.location.assign("hello?name="+txt.value);
		}
		txt.onkeydown = function(e){
			if(e.keyCode == 13){
				window.location.assign("hello?name="+txt.value);
			}
		};
	</script>
</body>
</html>
