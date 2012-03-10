<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Demone > 添加文章</title>
    
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
	    <link rel="stylesheet" type="text/css" href="res/css/wmd.css"/>
		<script src="res/js/wmd.js"></script>
		<script src="res/js/showdown.js"></script>
		<style type="text/css" media="screen">
textarea {
	font-family: verdana;
	font-size: 14px;
	width: 100%;
	margin: 0;
}

textarea {
	padding: 10px;
	border: #ccc 1px solid;
	height: 500px;
}

#notes-preview,#bimg  {
    margin-top: 20px;
    border: 2px dotted #E1E1E1;
	padding: 10px;
	overflow-x: hidden;
	height: auto
}
</style>
		<!--[if IE]>
        <style>
        #notes-preview {text-align:left}
        </style>
        <![endif]-->
	</head>
  <body>
    <%  
	    if(session.getAttribute("login") == null) 
	    response.sendRedirect(path+"/login"); 
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
					<li><a style="font-family:Georgia;" href="user/${ login }">『${ login }』</a></li>
					<li><a href="admin">控制面板</a></li>
					<li><a href="logout">注销</a></li>
				</ul>
			</div>
     </div>
    	<div style="margin: 0 auto;padding:10px;width:1100px;">	
    	 <div class="span-13">
    	 <form action="control/insertart" method="post">
    	    <h2><strong>添加文章</strong></h2>
    	    <div id="es_opt">
    	    <input type="checkbox" id="sta" name="essay.status" value="false"><label for="sta">关闭评论(选中后文章将不能被评论!)</label> 
    	    <input id="title" onfocus="if (this.value == '文章题目') {this.value = '';}" onblur="if (this.value == '') {this.value = '文章题目';}" value="文章题目" type="text" name="essay.title">
    	    <input id="class" onfocus="if (this.value == '文章分类') {this.value = '';}" onblur="if (this.value == '') {this.value = '文章分类';}" value="文章分类" type="text" name="essay.classification">
			<textarea id="es_sum" onfocus="if (this.value == '文章描述(限制100个字符)') {this.value = '';}" onblur="if (this.value == '') {this.value = '文章描述(限制100个字符)';}" value="文章描述(限制100个字符)" maxlength="120" name="essay.summary">文章描述(限制100个字符)</textarea>
			</div>
			<input type="hidden" name="image" id="pic">
			<div id="es_edit">
			  <div style="float: left;" id="notes-button-bar"></div>
			  <p style="margin-top: 5px;"><input type="checkbox" onclick="select(this)" id="fix"><label style="color: red" for="fix">固定编辑框</label></p>
			   <textarea name="essay.content" id="notes"></textarea>
			   <label for="tagin" style="color: red;">(输入文章标签，用空格分开，最多输入5个标签。)</label>
			   <div id="faketag"><div id="tagout"></div><input type="text" id="tagin"></div>
			   <input type="hidden" id="tag_real" name="tag"> 
			 </div>  
			   <div id="es_foot" style="text-align:center">
    	       <input type="submit" style="margin:20px 30px;font-size:14px" value="提交" class="btn primary">
    	       <input type="reset" style="margin:20px 30px;font-size:14px" value="取消" class="btn primary">
    	     </div>
    	    </form>
		   </div>		
		 <div class="span-14 last" style="float: right">
			<h2><strong>预览</strong></h2>
			  <div id="es_preview">
				<div>
					<form action="upload.action"  method="post" target="hidden_frame" enctype="multipart/form-data">				
						<div id="es_file">
						<input type="file" id="upload" onchange="form.submit()" name="upload" />
						</div>
						<input checked id="small" type="radio" name="imgpre" value="small"><label for="small">上传文章预览图</label>
						<input style="margin-left:20px" id="big" type="radio" name="imgpre" value="big"><label for="big">上传文章配图</label>		
						<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>						
					</form>
				</div>
				<div id="simg"></div>
				<div id="bimg"></div>
				<div id="notes-preview"></div>
			   </div>
		 </div>
		<script type="text/javascript">
			new WMDEditor({
				input: "notes",
				button_bar: "notes-button-bar",
				preview: "notes-preview",
				output: "copy_html",
				buttons: "bold italic link  image ol ul  heading undo redo",
				modifierKeys: false,
				autoFormatting: false
			}); 
		</script>
		<script type="text/javascript">
		//图片上传后回调函数
		function callback(msg){  
		    if(document.getElementById('small').checked){ 
		      var str1 = "<img src=<%=path%>/upload/";
		      var str2 = "> 文章预览图";
              document.getElementById("simg").innerHTML = str1+msg+str2;
              document.getElementById("pic").value = msg;
            }
            else if(document.getElementById('big').checked){
              document.getElementById("bimg").innerHTML += "<img title='点击图片插入到文章' onclick='insert(this)' src=<%=path%>/upload/${login}/"+msg+" >";
           }
        } 
        function insert(obj) {   
               var newsrc = obj.src.replace("small","");
               if(document.getElementById('notes').value.indexOf(newsrc) == -1){                
               var flag = newsrc.lastIndexOf("/");
               var tmp = newsrc.substring(flag+1,newsrc.length);
               var insrc = "!["+tmp+"]("+newsrc+")";
               InsertString('notes',insrc);
               }
        }
        //限制文章描述80个字符
        /*function limit(obj){
            if(obj.value.length > 80)
              obj.value = obj.value.substring(0,80);
        }*/
        //在光标处插入图片.
        function InsertString(tbid, str){
           var tb = document.getElementById(tbid);
           tb.focus();
          if (document.all){
            var r = document.selection.createRange();
            document.selection.empty();
            r.text = str;
            r.collapse();
            r.select();
        }
        else{
           var newstart = tb.selectionStart+str.length;
           tb.value=tb.value.substr(0,tb.selectionStart)+str+tb.value.substring(tb.selectionEnd);
           tb.selectionStart = newstart;
           tb.selectionEnd = newstart;
          }
        }
		</script>
		<script type="text/javascript" src="res/js/jquery-1.7.js"></script>
        <script type="text/javascript">
         $(function(){$(function() {
            $('#fix').click(function(){
               if(this.checked){
                    $('#es_edit').addClass('fixedes');
                    $('#es_foot').addClass('fixedfoot');
               }else if(!this.checked){
                   $('#es_edit').removeClass('fixedes');
                   $('#es_foot').removeClass('fixedfoot');
               }
            })
           });
        })
        </script>
        <script type="text/javascript">
     var i = 0;
     var tem = "";
     var real = "";
       $('#tagin').bind("keydown",function(e){
           $('#tagin').val($('#tagin').val().replace(" ","")); 
           //响应退格键删除最后一个标签
           if(e.which == 8 && i > 0 && $('#tagin')[0].selectionStart == 0){
              var str = "<span>"+$('#'+i+'').parent().html()+"</span>";
              var del =  $('#'+i+'').parent().text().replace(" ×","");
              real = real.replace(del+",","");
              str = str.replace(/\"/g, '\'');
              tem = tem.replace(str,"");
              $('#'+i+'').parent().remove(); 
              i--;
              $('#tag_real').val(real);
             }
            //响应粘贴键
            if ( e.ctrlKey && (e.which == 86 || e.which==118) ){
              //todo....
            } 
       });
       $('#faketag').click(function(){
          $('#tagin').focus();
       });
     
     $('#tagin').bind("keyup",function(){  
         //限制5个标签.
         if(i >= 5)
           $('#tagin').val("");
         //每次输入空格时转换成标签.   
         if($('#tagin').val().indexOf(" ") != -1 && $.trim($('#tagin').val()).length >= 1){
           //如果有重复的禁止输入.    
           if($('#tagout').text() !="" && $('#tagout').text().indexOf($('#tagin').val()) != -1){
             $('#tagin').val("");
             return false;
            }  
           i++;
           tem += "<span>"+$('#tagin').val()+"<span id='"+i+"' class='close'>×</span></span>";
           real += $.trim($('#tagin').val())+",";
           $('#tagout').html(tem);
           $('#tagin').val("");
           //为每个新加的标签添加点击事件.
           $('.close').bind("click", function(){
              var del =  $(this).parent().text().replace(" ×","");
              var str = "<span>"+$(this).parent().html()+"</span>";
              real = real.replace(del+",","");
              str = str.replace(/\"/g, '\'');
              tem = tem.replace(str,"");
              $(this).parent().remove(); 
              i--;
              $('#tag_real').val(real);
             });
         } 
         $('#tag_real').val(real);
     });
  </script>
  </body>
</html>
