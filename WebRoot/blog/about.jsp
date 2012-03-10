<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>demone > about me</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="shortcut icon" href="res/img/favico.ico" type="image/x-icon" />
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="res/css/screen.css" type="text/css"
			media="screen, projection">
   <!--[if IE]><link rel="stylesheet" href="res/css/ie.css" type="text/css" media="screen, projection"><![endif]-->

  </head>
  
  <body>
    <div id="headbar">
        <div></div>
			<div id="menu" style="margin-left:150px">
				<ul>
					<li class="">
						<a href="index">首页</a>
					</li>
					<li class="">
						<a href="sitemap">站点地图</a>
					</li>
				</ul>
			</div>
			<div id="menu" style="float:right;margin-right:20px">
			    <c:if test="${login == null }">
			     <ul>
			      <li><a href="login">登录</a></li>
			      <li><a href="register">注册</a></li>
			     </ul>
		    	</c:if>
		    	<c:if test="${ login !=null }">
				<ul>
					<li><a style="font-family:Georgia;" href="user/${ login }">『${ login }』</a></li>
					<li><a href="admin">控制面板</a></li>
					<li><a href="logout">注销</a></li>
				</ul>
				</c:if>
			</div>
     </div>
     <div class="container">
		    <div class="span-24">
		      <a href="index" ><span><img src="res/img/logo.gif"></span></a>
  		      <p id="logo_sum"><em>My first blog named demone namely one demo </em></p>
		    </div>
			<div class="span-24">
				<div class="box">
				    <p id="about">About Me</p>
                </div>
			</div>
			<div id="footer" class="span-24">
			 <ul>
        <li><a href="about">关于本站</a></li>
        <li><a href="sitemap">网站导航</a></li>
        <li><a href="">联系我们</a></li>
        <li><a href="">开发计划</a></li>
       </ul>
       <span id="pb"><span style="font-family: Georgia; font-size: 20px; font-style: italic; font-weight: bold">P</span>owered 
       by <span style="font-family: Georgia; font-size: 20px; font-style: italic; font-weight: bold">J</span>ava struts2</span>
			</div>
		</div>
  </body>
</html>
