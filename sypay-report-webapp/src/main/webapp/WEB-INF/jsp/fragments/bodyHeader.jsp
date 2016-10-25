
                
	<div id="header" class="navbar navbar-fixed-top">
    <div class="container clearfix">
        <div class="navbar-header">
            <a  href="#"></a>
        </div>
        <div class="collapse navbar-collapse" role="navigation">
        	<ul class="nav navbar-nav">
        		<c:forEach var="item" items="${navigateList}">
	            	<li <c:if test="${item.sysCode=='per'}"> class="active"</c:if>><a href="${item.url}" target="blank">${item.sysName}</a>
	            </li>
	         </c:forEach>
   			</ul>
            <!--头部用户信息-->
            <div class="welcome">
                <c:if test="${name != null}">
                <div class="pull-right">
                   <%--  <a href="javascript:void(0)" id="editpass">修改密码</a> --%>
                   <span class="link-pink">
                   		<a href="${pageContext.request.contextPath}/logout" id="loginOut" onclick="return Confirm('您确定要退出吗?', this);">安全退出</a>
                   </span>
                	<br>
                	<span class="link-blue">
                    	<a href="#" id="loginOut" onclick="toChangePassword()">修改密码</a>
                    </span>
                </div>
                <div class="name">
                	<strong>${name}</strong><br>
                </div>
                </c:if>
            </div>
        </div>
    </div>
    
</div>