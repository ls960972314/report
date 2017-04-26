<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<% 
String path = request.getContextPath(); 
String basePath = path + "/"; 
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>自动化报表平台登录</title>
  <link href="${pageContext.request.contextPath}/images/title.ico" rel="icon" type="image/x-icon">
  <jsp:include page="fragments/script.jsp" />
  <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src='<%=basePath%>js/login.js'> </script>
</head>

<body>
  <div class="login_box">
    <div class="top">
    	用户登录
    </div>
    
    <form id="loginForm" method="post" action="<%=basePath%>doLogin.htm">
      <span id="message" style="color: red;margin-left:15px;">${erroMsg}</span>
      <div class="text_input">
        <div>用户名：</div>
        <p><input id="username" name="username" type="text" value="visitor"/></p>
      </div>
      <div class="text_input">
        <div>密 码：</div>
        <p><input id="password" name="password" type="password" value="123456"/></p>
      </div>
      <div class="login_button">
      	<div class="but">
      		<a id="btnEp" href="#" onclick="javascript:void(0)"></a>
      	</div>
      </div>
    </form>
  </div>
</body>
</html>

