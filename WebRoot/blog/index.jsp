<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

		<title>Demone</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="shortcut icon" href="res/img/favico.ico" type="image/x-icon" />
		<link rel="stylesheet" href="res/css/screen.css" type="text/css" media="screen, projection">
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
					<li class="">
						<a href="about" >关于<span style="font-family: Georgia;">Demone</span></a>
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
	<div class="span-15" align="left">
	 <c:if test="${empty requestScope.list}">
       <span>当前无文章</span>
     </c:if> 
     <c:if test="${!empty requestScope.list}"> 
     
		<c:forEach items="${requestScope.list}" var="es">
		     <div class="box">
    			 <div id="es_title"><p><h2><a title='${ es.title  }' href="essay/${ es.date }/${ es.essayid }"> ${ es.title  } </a>
    			 <span style="float:right"><a id="com" title="${ es.comments }条评论" href="essay/${ es.date }/${ es.essayid }">${ es.comments } </a></span>
    			 </h2></p></div>
    			 <div id="post"><p>   ${ es.author  } <span style="color: #95006D">post@</span> ${ es.date }  </p></div>
    		 	 <div><div id="sumy">  ${ es.summary }</div>
    		 	 <div id="esthumb">
    		 	 <c:if test="${ es.thumbnail == null||es.thumbnail == ''}"><img src="<%=path%>/upload/example.jpg" alt="example.jpg"></c:if>
    		 	 <c:if test="${ es.thumbnail != null&&es.thumbnail != ''}"><img src="<%=path%>/upload/${ es.thumbnail }" alt="${ es.thumbnail }"></c:if>
    		 	 </div></div>
                 <div id="esfoot"><p>分类: <span id="es_cls"><a href="class/${ es.classification }" >${ es.classification }</a></span>
                 <span id="al_tag">标签: 
                   <c:forEach var="etag" items="${ es.tag }">
                     <a href="sear/${ etag.tag }">${ etag.tag }</a>
                   </c:forEach>
                 </span>
                 <span id="readmore" style="float:right"><a href="essay/${ es.date }/${ es.essayid }">查看全文...</a></span></p></div>    		 
    		 <hr>
    		 </div>
    	 </c:forEach>
    	 
     </c:if>
        <div id="pages">
    	 <p><span style="float: right;">
    	 <s:url id="url_first" value="page/first"></s:url>    	 
         <s:url id="url_pre" value="pre.action">     
           <s:param name="pageNow" value="page.pageNow-1"></s:param>     
         </s:url>        
         <s:url id="url_next" value="next.action">     
            <s:param name="pageNow" value="page.pageNow+1"></s:param>     
         </s:url>          
         <s:url id="url_last" value="page/last"></s:url> 
         <s:if test="page.pageNow != 1">
         <s:a cssClass="page" href="%{url_first}" >首页</s:a>  
         <s:a cssClass="page" href="%{url_pre}" ><上一页</s:a> 
         </s:if>
         <s:if test="page.lastPage != page.pageNow"><s:a cssClass="page" href="%{url_next}" >下一页></s:a>   
         <s:a cssClass="page" href="%{url_last}">最后一页 </s:a></s:if> 
         </span>
             <span id="ifoot"><span style="font-family: Helvetica; font-size:17px;"> Page </span><s:property value="page.pageNow"/> 
             <span style="font-weight:normal; font-family: Georgia; font-style: italic"> of </span><s:property value="page.lastPage"/></span>
         </p>
        </div>  
    </div>
    <!-- 边栏 -->
    <div class="span-8 last">
    <div id="sidebar">
    <div style="margin-top:0px" class="span-7 ">
    <span>文章搜索</span><hr>
     <form action="searchres">
     <input type="text" id="search" onfocus="if (this.value == '直接回车搜索') {this.value = '';}" 
     onblur="if (this.value == '') {this.value = '直接回车搜索';}" value="直接回车搜索" name="search" />
     </form>
    </div>
    <div class="span-7 ">分类查看<hr>
      <div id="tag">
        <c:forEach items="${sessionScope.detail}" var="ess">
			<span><a target=”_blank”
				title='${ ess.classification  }'
				href="class/${ ess.classification }"> ${ess.classification }
				<img src="res/img/tag.png" alt=${ess.classification }/></a>		
				</span>
		</c:forEach>
      </div>
    </div>
    <div class="span-7" id="es_tags">标签云<hr>
      <c:forEach items="${ tags }" var="tags">
        <span><a target=”_blank”
				title='${ tags.tag  }'
				href="sear/${ tags.tag }"> ${ tags.tag }
				<img src="res/img/tags.png">
		     </a>		
		</span>
      </c:forEach>
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
    <!-- 底部 -->
    <div  id="footer" class="span-24">
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
    <script type="text/javascript" src="res/js/jquery-1.7.js"></script>
    <script type="text/javascript">
      $(function(){$(function() {
           var offset = $('#sidebar').offset();
               $(window).scroll(function () {
               var scrollTop = $(window).scrollTop();
               if (offset.top < scrollTop)
                  $('#sidebar').addClass('fixed');
               else 
                  $('#sidebar').removeClass('fixed');
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
    </script>
	</body>
</html>
