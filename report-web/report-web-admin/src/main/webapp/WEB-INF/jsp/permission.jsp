<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 7]>         <html class="no-js lt-ie10 lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie10 lt-ie9"> <![endif]-->
<!--[if IE 9]>         <html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js"> <!--<![endif]-->
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>主界面</title>
<jsp:include page="fragments/script.jsp" />
<%@include file="fragments/bodyHeader.jsp"%>
    <table class="container" style="height: 592px;">
        <tbody>
	        <tr>
	        	<td width="190" valign="top">
	                <!--左侧导航 Start-->
	                <%@include file="fragments/leftMenu.jsp"%>
	            </td>
	            <td style="vertical-align:top;">
	            	<div style="width:100%; height:480px; margin-top:7px;  box-shadow:0 1px 3px rgba(0,0,0,0.2);"></div>
	            </td>
	        </tr>
        </tbody>
    </table>

	<%@include file="fragments/footer.jsp"%>
</body>
</html>

