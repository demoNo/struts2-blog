<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Demone > 用户后台</title>
    
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
	<link rel="stylesheet" href="res/css/validationEngine.jquery.css" type="text/css" media="screen" title="no title" charset="utf-8" />  		
   <!--[if IE]><link rel="stylesheet" href="res/css/ie.css" type="text/css" media="screen, projection"><![endif]-->  
   <style type="text/css">
       table img {height:20px;width:20px} 
   </style>
  </head>

	<body>
	    <%
			if (session.getAttribute("login") == null)
				response.sendRedirect("login.jsp");
		%>
		<div id="headbar">
			<div></div>
			<div id="menu" style="margin-left: 150px">
				<ul>
					<li>
						<a href="index">首页</a>
					</li>
					<li>
						<a href="sitemap">站点地图</a>
					</li>
					<li>
						<a href="about">关于<span
							style="font-family:Georgia;">Demone</span>
						</a>
					</li>
				</ul>
			</div>
				<div id="menu" style="float: right; margin-right: 20px">
					<ul>
						<li><a style="font-family:Georgia;" href="user/${ login }">『${ login }』</a></li>
					    <li><a href="admin">控制面板</a></li>
					    <li><a href="logout">注销</a></li>
					</ul>
				</div>
		</div>
		<div class="container">
			<div class="span-24">
				<a href="index" ><span><img src="res/img/logo.gif"></span></a>
  		        <p id="logo_sum"><em>My first blog named demone namely one demo </em></p> 
			</div>
			<div class="span-15" align="left">
				<div class="box">
					<c:forEach items="${ sessionScope.usr }" var="usr">
					   <p><span>后台管理 > 用户资料更新 > ${ usr.username }</span></p><hr>
						<form id="form" action="updateUser.action" method="post">
							<div id="comment-author-info">
								<p>
									<label for="uname">
										用户名
									</label>
									<input type="text" id="uname" name="user.username"
										value="${ usr.username }" readonly required />
								</p>
								<p>
									<label for="email">
										邮箱
									</label>
									<input type="text"
										class="validate[required,custom[email]] text-input"
										name="user.email" value="${ usr.email }" id="email" />
								</p>
								<div style="padding: 4px; margin-left: 30px">
									<input type="radio" id="male" name="user.sex" value="男" />
									<label for="male">
										男
									</label>
									<input style="margin-left: 20px" type="radio" id="female"
									  name="user.sex" value="女" checked="checked" />
									<label for="female">
										女
									</label>
								</div>
								<hr>
								<br>
								<input style="padding: 4px; width: 70px; height: 30px;"
									class="btn primary" type="submit" value="确认更新" />
								<input
									style="padding: 4px; width: 70px; height: 30px; margin-left: 20px"
									class="btn primary" type="Reset" value="取消" />
							</div>
						</form>
					</c:forEach>
				</div>
				<div class="box">
				   <p><span>${ login } 所有发表的文章</span></p><hr>
				   <a target=”_blank” href="control/addEssay" style="color: #fff;" class="btn"><strong>发表文章</strong></a>
				   <c:if test="${empty sessionScope.uessay}"><br><br><p><span style="text-align:center;font-size:12px;color:#999">您还没有发表过文章</span></p></c:if>
				   <c:if test="${!empty sessionScope.uessay}">
				   <table>
							<tr>
								<td><strong>文章标题</strong></td>
								<td><strong>类别</strong></td>
								<td><strong>操作</strong></td>
							</tr>
							<c:forEach items="${sessionScope.uessay}" var="es">
								<tr>
									<td><a target=”_blank” href="main.action?id=${es.essayid}&date=${ es.date }">${ es.title }</a></td>
									<td>${ es.classification }</td>
									<td>
										<a target=”_blank” title='编辑' href="getEss.action?id=${ es.essayid }"><img src="res/img/edit.png"></a>
								    </td>
								</tr>
							</c:forEach>
					</table>
					</c:if>
				</div>
				<div class="box">
				  <p><span style="font-size:12px;color:#999"><strong>!如果不修改请将密码留空</strong></span></p><hr>
				  <form id="form1" action="updatePwd.action" method="post">
				    <div id="comment-author-info">
				                <p>
					 			    <label for="old">旧密码</label>
									<input class="validate[required] text-input" type="password" name="oldpwd" id="old" />
								</p>
								<c:if test="${isok == 'fail'}"><p><span style="color:red;fontsize:12px">密码错误</span></p></c:if>
								<p>
									<label for="new">新密码</label>
									<input class="validate[required,minSize[6],maxSize[20]] text-input" type="password" name="newpwd" id="new" />
								</p>
								<p>
									<label for="pwd">再次输入</label>
									<input class="validate[required,equals[new]] text-input" type="password" name="user.password" id="pwd" />
								</p>
								<c:if test="${isok == 'ok'}"><p><span style="color:green;fontsize:12px">更新成功</span></p></c:if>
								<hr>
								<br>
						<input style="padding: 4px; width: 70px; height: 30px;" class="btn primary" type="submit" value="确认" />
				    </div>
				  </form>
				</div>
			   </div>
			    <div class="span-8 last">
			    <div id="sidebar">
				<div style="margin-top: 0px" class="span-7 ">
					<span>文章搜索</span><hr>
					<form action="findtag.action">
						<input type="text" id="search"
							onfocus="if (this.value == '直接回车搜索') {this.value = '';}"
							onblur="if (this.value == '') {this.value = '直接回车搜索';}"
							value="直接回车搜索" name="search" />
					</form>
				</div>
				<div class="span-7 " id="link">友情链接<hr>
                   <ul>
                     <c:forEach items="${ sessionScope.link }" var="link" >
                      <li><a href='${ link.url }' target="_blank">${ link.urlname }</a></li>
                     </c:forEach>
                   </ul>
                </div>
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
	<script type="text/javascript" src="res/js/jquery-1.7.js"></script>
	<script src="res/js/jquery.validationEngine-zh_CN.js" type="text/javascript"></script> 
    <script src="res/js/jquery.validationEngine.js" type="text/javascript"></script>
    <script type="text/javascript">
      $(document).ready(function(){            
                 /*表单验证*/
                 $("#form1").validationEngine();
        })
    </script> 
    <script type="text/javascript">
      $(function(){$(function() {
           var offset = $('#sidebar').offset();
               $(window).scroll(function () {
               var scrollTop = $(window).scrollTop();
               if (offset.top <= scrollTop)
                  $('#sidebar').addClass('fixed');
               else $('#sidebar').removeClass('fixed');
                });
            });
        })
    </script>
	</body>
</html>
