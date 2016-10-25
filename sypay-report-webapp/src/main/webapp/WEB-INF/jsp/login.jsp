<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<% 
String path = request.getContextPath(); 
String basePath = path + "/"; 

response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>用户登录</title>
  <jsp:include page="fragments/script.jsp" />
  <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src='<%=basePath%>js/user/main.js'> </script>
</head>

<body>
  <div class="title"></div>
  <div class="login_box">
    <div class="top">用户登录</div> 
    <form id="loginForm" method="post" action="<%=basePath%>doLogin.htm">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
      
      
      <span id="message" style="color: red;">${erroMsg}</span>
      <div class="text_input">
        <div>&nbsp;&nbsp;用户名：</div>
        <p><input id="systemName" name="loginName" type="text" value="${loginName}"/></p>
      </div>
      <div class="text_input">
        <div>&nbsp;&nbsp;密 &nbsp;码：</div>
        <p><input id="systemPW" name="password" type="password" value=""/></p>
      </div>
      <div class="login_button"><!--
        <div class="jz"><label><input name="" type="checkbox" value=""/> 记住账号</label></div>
        --><div class="but"><a id="btnEp" href="#" onclick="javascript:void(0)"></a></div>
      </div>
    </form>
  </div>
</body>
</html>

