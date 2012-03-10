<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Demone > 用户登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="res/img/favico.ico" type="image/x-icon" />
    <link rel="stylesheet" href="res/css/custom.css" type="text/css">
    <!--[if IE]>
    <style>
    .wrap p input{height: 40px;}
    .btn{width: 100px;text-align: center;}
    .wrap{border: 2px solid #f3f3f3;}
    </style>
    <![endif]--> 
    <style type="text/css">
     #nav ul li a:hover{
	 background: #f9f9f9;
	 color: #373737;
     }
     </style>
	</head> 
  <body>
		<%
			String name1;
			Cookie[] cookies = request.getCookies();
			if (cookies != null && session.getAttribute("flag") == null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("userlog")) {
						String uname = cookies[i].getValue().split("-")[0];
					    String passward = cookies[i].getValue().split("-")[1];
						request.setAttribute("uname", uname);
						request.setAttribute("pass", passward);
					}
				}
			}
			if (session.getAttribute("flag") != null) {
				name1 = "<img style='vertical-align: middle' src=res/img/warn.png />用户名或密码错误";
				session.removeAttribute("fail");
			} else
				name1 = "";
		%>
		<div class="headbar">
     <div id="nav" style="margin-left:150px">
				<ul>
					<li class="">
						<a href="index">首页</a>
					</li>
					<li class="">
						<a href="sitemap">站点地图</a>
					</li>
					<li class="page_item page-item-146">
						<a href="about" >关于<span style="font-family:Georgia;">Demone</span></a>
					</li>
				</ul>
		</div>
  </div>
<div id="logo">	
   <a href="index" ><img src="res/img/logo.gif"></a>		      
</div>
  <div class="wrap">
    <form action="signin" method="post">
				<p>
				    <label for="username">用户名</label><br>
				    <input id="username" onpropertychange="check(this.value)" oninput="check(this.value)" type="text" value="${ uname }" name="user.username" placeholder="用户名" required/>					
				</p>
				<p>
				    <label for="password">密码</label><br>
				    <input id="password" type="password" value="${ pass }" name="user.password" placeholder="密码" required/>  
				</p>
				<div><span style="color:red;font-size:11px"><%=name1%></span></div>
				<div><input style="padding:7px 30px;font-size:17px" class="btn1 primary" type="submit" value="登录"/></div>
				<br>
				<input type="checkbox" id="remember" name="remember"/><label class="rem" for="remember">记住我!</label>
				<a href="register">注册</a>
				<a href="">忘记密码?</a>
	</form>	
    </div>
    <script type="text/javascript">
      var name = "";
      var pass = "";
      window.onload = function() { 
         name = "${uname}";  
         pass = "${pass}";
      }
      function check(text){
         if(name !== text){
          return document.getElementById("password").value = "";
          }
         else  if(name == text){
          return document.getElementById("password").value = pass;
          }
      }
    </script>
  </body>
</html>
