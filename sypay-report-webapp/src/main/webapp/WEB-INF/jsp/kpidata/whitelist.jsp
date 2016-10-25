<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<!DOCTYPE html>
<head>
</head>
<body>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>角色管理</title>
<jsp:include page="../fragments/script.jsp" />
<%@include file="../fragments/bodyHeader.jsp"%>
    <table class="container" style="height: 592px;">
        <tbody>
	        <tr>
	        	<td width="190" valign="top">
	                <!--左侧导航 Start-->
	                <%@include file="../fragments/leftMenu.jsp"%>
	            </td>
	            <td valign="top">
	            <iframe   frameborder=0   border=0   width=940   height=590   src="${url}" mce_src="${url}"></iframe>
				</td>
	        </tr>
        </tbody>
    </table>
</body>

