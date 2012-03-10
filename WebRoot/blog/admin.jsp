<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="orz.xblog.task.HandleUsers"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
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
    
    <title>Demone > 管理员后台</title>
    
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
		<link rel="stylesheet" href="res/css/screen.css" type="text/css"
			media="screen, projection">
		<!--[if IE]><link rel="stylesheet" href="res/css/ie.css" type="text/css" media="screen, projection"><![endif]-->
		<style type="text/css">
          textarea {
	         display: block;
          }
        </style>
		<script type="text/javascript">
     $(document).ready(function()
        {            
                 /*表单验证*/
                 $("#form1").validationEngine();
                 $("#form2").validationEngine(); 
                 $("#form3").validationEngine();
                 
                 /*控制显示*/
                 
                 /*全选*/
                 $("input[name='select']").click( function (){ 
                    if($(this).attr('checked'))
                      $("input[name='eid']").attr("checked", true);  
                    else
                      $("input[name='eid']").attr("checked", false); 
                 });
                 $("input[name='cselect']").click( function (){ 
                    if($(this).attr('checked'))
                      $("input[name='id']").attr("checked", true);  
                    else
                      $("input[name='id']").attr("checked", false); 
                 });
                 $("input[name='urcheck']").click( function (){ 
                    if($(this).attr('checked'))
                      $("input[name='id']").attr("checked", true);  
                    else
                      $("input[name='id']").attr("checked", false); 
                 });
                 $("input[name='ucheck']").click( function (){ 
                    if($(this).attr('checked'))
                      $("input[name='uname']").attr("checked", true);  
                    else
                      $("input[name='uname']").attr("checked", false); 
                 });
        })       
</script>
	</head>
	<body>
		<% 
			HandleUsers hu = new HandleUsers(); 
			if (session.getAttribute("login") == null) 
				response.sendRedirect("login");		 
			else if (!hu.isAdmin(session.getAttribute("login").toString())) 
				response.sendRedirect("blog/users.jsp"); 
		%>
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
					<li class="page_item page-item-146">
						<a href="about" >关于<span style="font-family:Georgia;">Demone</span></a>
					</li>
				</ul>
			</div>
			<div id="menu" style="float:right;margin-right:20px">
				<ul>
					<li><a href="user/${ login }" style="font-family: Georgia;">『${ login }』</a></li>
					<li><a href="logout">注销</a></li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="span-24">
			   <a href="index" ><span><img src="res/img/logo.gif"></span></a>
  		       <p id="logo_sum"><em>My first blog named demone namely one demo </em></p>
			</div>
			<div class="span-7">
				<div id="Nav">
					<ul>
						<li>
							<a href="control/efirst"><img src="res/img/edit.png" alt/>管理文章</a>
						</li>
						<li>
							<a href="control/addEssay"><img src="res/img/addes.png" alt/>添加文章</a>
						</li>
						<li>
							<a href="control/cfirst"><img src="res/img/comment.png" alt/>管理评论</a>
						</li>
						<li>
							<a href="control/urfirst"><img src="res/img/link.png" alt/>管理友情链接</a>
						</li>
						<li>
							<a href="javascript:void(0)" id="addur"><img src="res/img/addlink.png" alt/>添加友链</a>
						</li>
						<li>
							<a href="control/ufirst"><img src="res/img/users.png" alt/>管理用户</a>
						</li>
						<li>
							<a href="logout"><img src="res/img/logoff.png" alt/>注销登录</a>
						</li>
						<li>
							<a href="index"><img src="res/img/home.png" alt/>返回首页</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="span-15 last" style="margin-top:10px;height:auto">
			<c:if test="${isok == 'ok'}"><span style="color:green;fontsize:12px">更新成功</span></c:if>
			<c:if test="${isok == 'fail'}"><span style="color:red;fontsize:12px">密码错误</span></c:if>
				<form name="deless" action="delEss.action" method="post">
				<div id="essay">
				<c:if test="${!empty requestScope.list}">
					<div class="box" id="artical" >
					<span>后台管理 > 文章管理</span><hr>
					<div id="del_es">
					<a href="control/addEssay" class="btn">发表文章</a>
					<a style="margin-left:50px" id="es_del" onclick="return confirm('是否删除?')" href="javascript:document.deless.submit();" class="btn primary">批量删除</a> 
					</div>	
						<table id="es">
							<tr>
							    <td><input type="checkbox" name="select" id="sel"><strong><label for="sel">全选</label></strong></td>
								<td><strong>文章标题</strong></td>
								<td><strong>作者</strong></td>
								<td><strong>类别</strong></td>
								<td><strong>操作</strong></td>
							</tr>
							<c:forEach items="${requestScope.list}" var="es">
								<tr>
								    <td><input type="checkbox" name="eid" value="${ es.essayid }"></td>
									<td><a target=”_blank” href="essay/${ es.date }/${ es.essayid }">${ es.title }</a></td>
									<td>${ es.author }</td>
									<td>${ es.classification }</td>
									<td><a title='删除' onclick="return confirm('是否删除?')" href="delEss.action?eid=${ es.essayid }"><img
										src="res/img/delete.png"></a>
										<a title='编辑' href="getEssay/${ es.essayid }"><img src="res/img/edit.png"></a>
								    </td>
								</tr>
							</c:forEach>
						</table>
						<div id="pages"><p>
							<s:url id="url_first" value="control/efirst"></s:url>
							<s:url id="url_pre" value="epre.action">
								<s:param name="pageNow" value="page.pageNow-1"></s:param>
							</s:url>
							<s:url id="url_next" value="enext.action">
								<s:param name="pageNow" value="page.pageNow+1"></s:param>
							</s:url>
							<s:url id="url_last" value="control/elast"></s:url>
							<s:if test="page.pageNow != 1">
							<s:a cssClass="page" href="%{url_first}">首页</s:a>
							<s:a cssClass="page" href="%{url_pre}"><上一页</s:a>
							</s:if>
							<s:if test="page.lastPage != page.pageNow">
							<s:a cssClass="page" href="%{url_next}">下一页></s:a>
							<s:a cssClass="page" href="%{url_last}">最后一页 </s:a>
							</s:if>
							<span id="pfoot">共<s:property value="page.totalNum"/>篇文章     
					    	共<s:property value="page.lastPage"/>页        
					         第<s:property value="page.pageNow"/>页 </span>
						</p></div><hr>
					</div>
					</c:if>
				</div>
				</form>
				<form action="delCom.action" method="post">
				<div id="comm">
				<c:if test="${!empty requestScope.comment}">
					<div class="box">
					    <span>后台管理 > 评论管理</span>
					    <hr>
					    <input type="submit" value="批量删除" class="btn primary" onclick="return confirm('是否删除?')"/>
						<table id="co">
							<tr>
							    <td><input type="checkbox" name="cselect" id="cs"><label for="cs">全选</label></td>
							    <td>评论者</td>
								<td>评论内容</td>
								<td>评论文章</td>
								<td>操作</td>
							</tr>							
							<c:forEach items="${requestScope.comment}" var="co">
							<tr>
							    <td><input type="checkbox" name="id" value="${ co.commid }"></td>
							    <td>${ co.commname }</td>
							    <td>${fn:substring( co.comment, 0, 20 )}</td>
								<td>${ co.title }</td>
								<td><a title='删除' onclick="return confirm('是否删除?')" href="delCom.action?id=${ co.commid }"><img
										src="res/img/delete.png"></a></td>
							</tr>
							</c:forEach>
						</table>
						<div id="pages"><p>
							<s:url id="url_first" value="control/cfirst"></s:url>
							<s:url id="url_pre" value="cpre.action">
								<s:param name="pageNow" value="page.pageNow-1"></s:param>
							</s:url>
							<s:url id="url_next" value="cnext.action">
								<s:param name="pageNow" value="page.pageNow+1"></s:param>
							</s:url>
							<s:url id="url_last" value="control/clast"></s:url>
							<s:if test="page.pageNow != 1">
							<s:a cssClass="page" href="%{url_first}">首页</s:a>
							<s:a cssClass="page" href="%{url_pre}"><上一页</s:a>
							</s:if>
							<s:if test="page.lastPage != page.pageNow">
							<s:a cssClass="page" href="%{url_next}">下一页></s:a>
							<s:a cssClass="page" href="%{url_last}">最后一页 </s:a>
                            </s:if>
							<span id="pfoot">共<s:property value="page.totalNum"/>条评论    
					    	共<s:property value="page.lastPage"/>页        
					         第<s:property value="page.pageNow"/>页</span> 
						</p></div><hr>
					</div>
					</c:if>
				</div>
				</form>
				<div id="_url">
				<c:if test="${!empty requestScope.url}">
				<form action="delUrl.action" method="post">
					<div class="box">
					<span>后台管理 > 友链管理</span><hr>
					<input type="submit" value="批量删除" class="btn" onclick="return confirm('是否删除?')"/>
						<table id="ur">
							<tr>
							    <td><input type="checkbox" id="uri" name="urcheck"><label for="uri">全选</label></td>
							    <td>网站</td>
								<td>网址</td>
								<td>操作</td>
							</tr>							
							<c:forEach items="${requestScope.url}" var="ur">
							<tr>
							    <td><input type="checkbox" name="id" value="${ ur.urlname }"></td>
							    <td>${ ur.urlname }</td>
							    <td>${ ur.url }</td>
								<td><a title='删除' onclick="return confirm('是否删除?')" href="delUrl.action?id=${ ur.urlname }"><img
										src="res/img/delete.png"></a></td>
							</tr>
							</c:forEach>
						</table>
						<div id="pages"><p>
							<s:url id="url_first" value="control/urfirst"></s:url>
							<s:url id="url_pre" value="urpre.action">
								<s:param name="pageNow" value="page.pageNow-1"></s:param>
							</s:url>
							<s:url id="url_next" value="urnext.action">
								<s:param name="pageNow" value="page.pageNow+1"></s:param>
							</s:url>
							<s:url id="url_last" value="control/urlast"></s:url>
							<s:if test="page.pageNow != 1">
							<s:a cssClass="page" href="%{url_first}">首页</s:a>
							<s:a cssClass="page" href="%{url_pre}"><上一页</s:a>
							</s:if>
							<s:if test="page.pageNow != page.pageNow">
							<s:a cssClass="page" href="%{url_next}">下一页></s:a>
							<s:a cssClass="page" href="%{url_last}">最后一页 </s:a>
							</s:if>
							<span id="pfoot">共<s:property value="page.totalNum"/>条友链   
					    	共<s:property value="page.lastPage"/>页       
					         第<s:property value="page.pageNow"/>页 </span>
						</p></div><hr>						
					</div>
					</form>
					</c:if>
					<div class="box" id="addurl" style="display:none">
					<form id="form1" action="addurl.action" method="post">
					  <div id="comment-author-info">
							<p>
								<label for="urlname">网站名称</label>
								<input class="validate[required] text-input" type="text" name="url.urlname" id="urlname" size="14" required/>
							</p>
							<p>
								<label for="url">网址</label>
								<input value="http://" class="validate[required,custom[url]] text-input" type="text" name="url.url" id="url" size="25" required/>
							</p>
							<div class="subcon">
							<input style="width:70px;height:30px" class="btn primary" type="submit" id="submit" value="提交" />
							<input style="margin-left:30px;width:70px;height:30px" class="btn primary" type="reset" id="submit" value="取消" />
					        </div>
					  </div>
					</form>
					</div>
				</div>
				<div id="User">
				 <c:if test="${!empty requestScope.users}">
				   <form action="delUser.action" method="post">			   
				    <div class="box">
				    <span>后台管理 > 用户管理</span><hr>
				    <input type="submit" value="批量删除" class="btn primary" onclick="return confirm('是否删除?')"/>
						<table id="us">
							<tr>
							    <td><input type="checkbox" name="ucheck" id="uc"><label for="uc">全选</label></td>
							    <td>用户名</td>
								<td>性别</td>
								<td>邮箱</td>
								<td>操作</td>
							</tr>
							<c:forEach items="${requestScope.users}" var="user">
							<tr>
							    <td><input type="checkbox" name="uname" value="${ user.username }"></td>
							    <td>${ user.username }<br></td>
							    <td>${ user.sex }<br></td>
							    <td>${ user.email }<br></td>
								<td>&nbsp;<a title='删除' onclick="return confirm('是否删除?')" href="delUser.action?uname=${ user.username }"><img
										src="res/img/delete.png"></a><br></td>
							</tr>
							</c:forEach>
						</table>
						<div id="pages"><p>
							<s:url id="url_first" value="control/ufirst"></s:url>
							<s:url id="url_pre" value="upre.action">
								<s:param name="pageNow" value="page.pageNow-1"></s:param>
							</s:url>
							<s:url id="url_next" value="unext.action">
								<s:param name="pageNow" value="page.pageNow+1"></s:param>
							</s:url>
							<s:url id="url_last" value="control/ulast"></s:url>
							<s:if test="page.pageNow != 1">
							<s:a cssClass="page" href="%{url_first}">首页</s:a>
							<s:a cssClass="page" href="%{url_pre}"><上一页</s:a>
							</s:if>
							<s:if test="page.pageNow != page.lastPage">
							<s:a cssClass="page" href="%{url_next}">下一页></s:a>
							<s:a cssClass="page" href="%{url_last}">最后一页 </s:a>
							</s:if>
							<span id="pfoot">共<s:property value="page.totalNum"/>个用户   
					    	共<s:property value="page.lastPage"/>页       
					         第<s:property value="page.pageNow"/>页 </span>
						</p></div><hr>
					</div> 
					</form>
					<div id="userdetail">
					  <div class="box">
					  <c:forEach items="${ requestScope.usr }" var="usr">
					  <span>当前为管理员登录</span><hr>
					    <form id="form2" action="updateUser.action" method="post">
							<div id="comment-author-info">
								<p>
									<label for="uname">
										用户名
									</label>
									<input type="text" id="uname" name="user.username" readonly value="${ usr.username }" required />
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
				  <span style="font-size:12px;color:#999"><strong>!如果不修改请将密码留空</strong></span><hr>
				  <form id="form3" action="updatePwd.action" method="post">
				     <div id="comment-author-info">
				                <p>
					 			<label for="old">旧密码</label>
									<input class="validate[required] text-input" type="password" name="oldpwd" id="old" />
								</p>
								<p>
									<label for="new">新密码</label>
									<input class="validate[required,minSize[6],maxSize[20]] text-input" type="password" name="newpwd" id="new" />
								</p>
								<p>
									<label for="pwd">再次输入</label>
									<input class="validate[required,equals[new]] text-input" type="password" name="user.password" id="pwd" />
								</p>
								<hr>
								<br>
						<input style="padding: 4px; width: 70px; height: 30px;" class="btn primary" type="submit" value="确认" />
				     </div>
				  </form>
				 </div>
		    	</div>
		    	</c:if>
				</div>
			</div>	
			<div class="span-24" id="footer">
				   <ul>
                       <li><a href="about">关于本站</a></li>
                       <li><a href="sitemap">网站导航</a></li>
                       <li><a href="">联系我们</a></li>
                       <li><a href="">开发计划</a></li>
                   </ul>
                   <span id="pb"><span style="font-family: Georgia; font-size: 20px; font-style: italic; font-weight: bold">P</span>owered 
                    by <span style="font-family: Georgia; font-size: 20px; font-style: italic; font-weight: bold">J</span>ava struts2</span>
			</div>		
			<script type="text/javascript">
            $("#addur").click(function () {
             if($("#ur").find("tr").length!=1)
               $("#addurl").slideToggle("slow");
             });
             </script>
</body>
</html>
