<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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

		<title>Demone > 用户注册</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="res/img/favico.ico" type="image/x-icon" />
		<script src="res/js/jquery-1.7.js" type="text/javascript"></script>
		<script src="res/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script> 
        <script src="res/js/jquery.validationEngine.js" type="text/javascript"></script>
		<!--
	    <link rel="stylesheet" type="text/css" href="styles.css">
	    -->
	    <link rel="stylesheet" href="res/css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="utf-8" />  
        <link rel="stylesheet" href="res/css/template.css" type="text/css" media="screen" title="no title" charset="utf-8" /> 
		<link rel="stylesheet" href="res/css/custom.css" type="text/css">
    <!--[if IE]><link rel="stylesheet" href="res/css/ie.css" type="text/css" media="screen, projection"><![endif]-->
	<!--[if IE]>
    <style>
    .wrap p input{height: 40px;}
    .btn{width: 100px;text-align: center;}
    .wrap{border: 2px solid #f3f3f3; padding-bottom:1em}
     #nav ul li a:hover{
	 background: #f9f9f9;
	 color: #373737;
     }
    </style>
    <![endif]-->
    <script type="text/javascript">
     $(document).ready(function() {			
			$("#form").validationEngine(); 
			})
    </script>
	</head>
	<body>
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
			<form id="form" action="singup" method="post">
			   <p style="margin-left:16px">
				    <label for="username">用户名</label>
				    <input id="username" class="validate[required,custom[onlyLetterNumber],ajax[ajaxUserCall]] text-input" type="text" name="user.username" required/>					
				</p>
				<p style="margin-left:32px">
				    <label for="password">密码</label>
				    <input id="password" class="validate[required,minSize[6],maxSize[12]] text-input" type="password" name="user.password" required/>  
				</p>
				<p>
				    <label for="pwd">确认密码</label>
				    <input type="password" class="validate[required,equals[password]] text-input" id="pwd" name="confirm" label="确认密码" />
				</p>
				<div style="padding:4px;margin-left:30px">
				    <span>性别</span>
				    <input type="radio" id="male" name="user.sex" value="男"/><label for="male">男</label>
		  		    <input style="margin-left:20px" type="radio" id="female" name="user.sex" name="sex" value="女" checked="checked"/><label for="female">女</label>
				</div> 
				<p>
				    <label for="email">邮箱地址</label>
				    <input class="validate[required,custom[email]] text-input" id="email" type="text" name="user.email" />
		        </p>
		        <div style="text-align:center">
				    <input class="btn1" type="submit" value="确认注册">
			        <input style="margin-left: 30px" class="btn1" type="reset" value="取消">
			    </div>
			</form>
		</div>
	</body>
</html>
