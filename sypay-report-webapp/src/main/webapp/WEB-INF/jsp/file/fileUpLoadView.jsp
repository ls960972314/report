<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<!DOCTYPE html>
<!--[if IE 7]>         <html class="no-js lt-ie10 lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie10 lt-ie9"> <![endif]-->
<!--[if IE 9]>         <html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>上传文件</title>
<jsp:include page="../fragments/script.jsp" />
</head>
<body style="overflow-y: hidden">
	<%@include file="../fragments/bodyHeader.jsp"%>
	<table class="container" style="height: 592px;">
		<tbody>
			<tr>
				<td width="190" valign="top">
					<!--左侧导航 Start--> <%@include file="../fragments/leftMenu.jsp"%>
				</td>
				<!------ 主体内容开始 -------->
				<td valign="top">
					<div class="extendDiv-toggle">
						
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<form name="userForm2" action="${pageContext.request.contextPath}/file/upload.htm" enctype="multipart/form-data" method="post"">
				 		上传JSP路径(e.g /tpl/)：<input type="text" name= "upPath" size="20"/>
				 		<div id="newUpload2">
							<input type="file" name="file">
						</div>
						<input type="button" id="btn_add2" value="增加一行" >
						<input type="submit" value="上传" >
						
						
				 	</form> 
				</td>
			</tr>
		</tbody>
	</table>
 	



		<script type="text/javascript">
	j = 1;
	$(document).ready(function(){
		
		$("#btn_add2").click(function(){
			document.getElementById("newUpload2").innerHTML+='<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';
			  j = j + 1;
		});
	});

	function del_2(o){
		 document.getElementById("newUpload2").removeChild(document.getElementById("div_"+o));
	}

</script>



	<%@include file="../fragments/footer.jsp"%>
</body>
</html>


