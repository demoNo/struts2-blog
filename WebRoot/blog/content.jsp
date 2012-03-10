<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    
    <title>Demone > 文章</title>
    
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
    <link rel="stylesheet" href="res/css/screen.css" type="text/css" media="screen, projection">
    <!--[if IE]><link rel="stylesheet" href="res/css/ie.css" type="text/css" media="screen, projection"><![endif]-->
    
    <script type="text/javascript">
     $(document).ready(function() {			
			$("#form").validationEngine();
			$("#form1").validationEngine();
			})
    </script>
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
					<li class="page_item page-item-146">
						<a href="about"  >关于<span style="font-family:Georgia;">Demone</span></a>
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
  <div class="span-15" align="left" >
    <c:forEach items="${requestScope.content}" var="co">
                <div class="box">
                <div id="esnav"><p>
                    <c:if test="${pre.title == null}">上一篇: 没了</c:if>
                    <c:if test="${pre.title != null}"><a href="essay/${ pre.date }/${ pre.essayid }">上一篇 :${ pre.title }</a></c:if>
                    <c:if test="${next.title == null}"><span style="float:right">下一篇 :没了</span></c:if>
                    <c:if test="${next.title != null}"><span style="float:right"><a href="essay/${ next.date }/${ next.essayid }">下一篇 :${ next.title }</a></span></c:if>
                </p></div>
    			<div><p><h3> ${ co.title } </h3></p></div>
    		 	<div id="post"><p>${ co.author } <span style="color: #95006D">post@</span> ${ co.date }</p></div>
    		 	<div><p> ${ co.html } </p></div>
    		 	<p>
    		 	 <span id="al_tag">标签: 
                   <c:forEach var="etag" items="${ co.tag }">
                     <a href="sear/${ etag.tag }">${ etag.tag }</a>
                   </c:forEach>
                 </span>
    		 	</p>
    		 	</div>
   </c:forEach>
   <c:if test="${empty requestScope.comment}">
   <div id="inner">
   <span>当前无回复</span>
   </div>
   </c:if>
   <c:if test="${!empty requestScope.comment}">
   <div class="box" style="height:auto">   
       <c:forEach items="${requestScope.comment}" var="co" varStatus="status">
						<table id="ucomment">
							<tr>
								<td style="width:1px" id="avatar"><a href="${ co.url }">
								<img style="width:45px;height:45px;border-radius: 3px;box-shadow: 0 1px 2px gray;" src="<%=path%>/upload/${ co.avatar }"/></a></td>
								<td valign=top>
								    <p style="float:right;font-size:12px;color:#999">#${fn:length(comment) - status.count + 1 } ${ co.cdate }</p>
								    <div><a id="coname" href="${ co.url }">${co.commname}</a> <a href="javascript:void(0)" onclick="reply('${ co.commname }')" style="color:#999">回复</a></div>
								    <div id="co_comm">${co.comment}</div>
								</td>
							</tr>
						</table><hr>
		</c:forEach>		
   </div>
   </c:if>
   <c:forEach items="${requestScope.content}" var="co">
   <c:if test="${co.status != null}">
   <div id="inner">
   <span>该文章已关闭评论!</span>
   </div>
   </c:if>
   <c:if test="${co.status == null}">
   <div id="comm" class="box">说俩句吧！<hr><br>
         <div id="co_up">
	          <form action="upload.action" method="post" target="hidden_frame" enctype="multipart/form-data">
	          <span id="fake">浏览...
	            <input id="up" name="upload" onchange="form.submit()" type="file">
	          </span>
	          <input type="hidden" name="up_flag" value="flag">
	          <iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
	        </form>
         </div>
			<form id="form" action="addcomm.action" method="post">
					<input type="hidden" name="ava" id="pic">
					<c:forEach items="${requestScope.content}" var="co">
				       <input type="hidden" name="id" value="${co.essayid}">
				       <input type="hidden" name="edate" value="${co.date}">
					</c:forEach>
						<div id="comment-author-info">
						<div id="co_avatar"><img src="res/img/avatar.png" alt="avatar.png"></div>
							<p>
								<label for="author">留个名哈</label>
								<input class="validate[required] text-input" type="text" name="commname" id="author" size="14"
									tabindex="1" />
								<small>*（必须）</small>
							</p>
							<p>
								<label for="email">写个邮件</label>
								<input class="validate[custom[email]] text-input" type="text" name="email" id="email" size="25"
									tabindex="2" />
								<small>（保密）</small>
							</p>
							<p>
								<label for="url">你的网站</label>
								<input class="validate[custom[url]] text-input" type="text" name="url" id="url" size="36"
									tabindex="3" />
							</p>
						</div>
						<div class="post-area">				
							<textarea class="validate[required] text-input" name="comment" id="comment"  tabindex="4"></textarea>
						</div>
						<div class="subcon">
							<input class="btn primary" type="submit" value="提交评论" />
							<input style="margin-left:30px" class="btn primary" type="reset" value="取消" />
						</div>
					</form>
				</div>
				</c:if>
				</c:forEach>
   </div>
   <div class="span-8 last">
   <div id="sidebar">
   <div style="margin-top:0px" class="span-7 ">
    <span>文章搜索</span><hr>
     <form action="findtag.action">
     <input type="text" id="search" onfocus="if (this.value == '直接回车搜索') {this.value = '';}" 
     onblur="if (this.value == '') {this.value = '直接回车搜索';}" value="直接回车搜索" name="search" />
     </form>
   </div>
   <c:if test="${empty requestScope.comment}"></c:if>
   <c:if test="${!empty requestScope.comment}">
   <div id="author" class="span-7">读者<hr>
     <c:forEach items="${ requestScope.comment }" var="com">
      <span>
      <a href="${ com.url }" >${ com.commname }</a>
	  </span>
     </c:forEach>
   </div>
   </c:if>
   <div class="span-7 ">分类查看<hr>
      <div id="tag">
        <c:forEach items="${sessionScope.detail}" var="ess">
			<span><a target=”_blank”
				title='${ ess.classification  }'
				href="class/${ ess.classification }">${ess.classification }
				<img src="res/img/tag.png" alt=${ess.classification }/></a>
				</span>
		</c:forEach>
      </div>
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
       <a href="javascript:void(0);" style="display:none" id="scroll">↑顶部</a>
   </div>
   </div>
    <script type="text/javascript">
      $(function(){$(function() {
           var offset = $('#sidebar').offset();
               $(window).scroll(function () {
               var scrollTop = $(window).scrollTop();
               if (offset.top <= scrollTop)
                  $('#sidebar').addClass('fixed');
               else $('#sidebar').removeClass('fixed');
               if(scrollTop > 300){
                    $('#scroll').fadeIn("slow");
                    $('#scroll').addClass('s_top');
                  }
               else if(scrollTop < 300){
                    $('#scroll').fadeOut("slow");    
                  }   
                });
            });
        })
        
        $('#scroll').click(function () {
          $( 'html, body' ).animate( { scrollTop: 0 }, 600 );
        });
        
        function callback(msg){    
            $('#co_avatar').html("<img src=<%=path%>/upload/"+msg+" >");
            $('#pic').val(msg);
        } 
    </script>
   <script type="text/javascript">
	   function reply(username){
	    replyContent = $("#comment");
		oldContent = replyContent.val();
		prefix = "@" + username + " ";
		newContent = ''
		if(oldContent.length > 0){
		    if (oldContent != prefix) {
		        newContent = prefix + oldContent;
		    }
		} else {
		    newContent = prefix
		}
		replyContent.focus();
		replyContent.val(newContent);
	}
   </script>
  </body>
</html>
