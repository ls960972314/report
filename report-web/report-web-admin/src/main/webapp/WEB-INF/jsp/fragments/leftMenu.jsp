<%@ page language="java" pageEncoding="UTF-8"
     contentType="text/html;charset=UTF-8"%>
<div class="sidebar-left">
		<!--  导航内容 -->
		<ul class="nav nav-pills nav-stacked">
			<c:forEach var="menu" items="${menuList}" varStatus="status">
	            <li>
	            	<c:if test="${menu.url == 'editPassword.htm'}">
     			        	<a data-value="${status.count}" href="javascript:void(0);" class="rootItem" onclick="toChangePassword()">
     			        		<span>${menu.text}</span>
     			        	</a>
	            	</c:if>
	            	
	            	<c:if test="${menu.url != 'editPassword.htm'}">
		            	<a  data-value="${status.count}" class="rootItem" href="${pageContext.request.contextPath}/${menu.url}">
		            		<span>${menu.text}</span>
		            	</a>
	            	</c:if>
	            	
	            </li>
	         </c:forEach>
		</ul>
</div>
<script>
	debugger;
    var nullRecord = false;
    if(sessionStorage.getItem('navSelected') == null){
        sessionStorage.setItem('navSelected','[0,0]');
        nullRecord = true;
    };
    var arr = eval("("+sessionStorage.getItem('navSelected')+")");
    var rootItem = $(".sidebar-left>.nav>li");
    if(rootItem.length > 0){
        rootItem.eq(arr[0]).addClass('active');
        /* var getSubLi = $(".sidebar-left>.nav>li.active>.subList>li").eq(arr[1]);
            getSubLi.addClass('active'); */
        //跳转至初始页面
        if(nullRecord){
            var url = rootItem.children('a').attr('href');
            if(url.indexOf('javascript') < 0) { 
	            var root = (window.location.href).split('/')[2];
	            window.location.href = "http://"+root+url;
            }
            nullRecord = false;
        }
    }

    rootItem.click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        var pIndex = $(this).find('.rootItem').attr('data-value');
        sessionStorage.setItem('navSelected','['+ (pIndex-1) +']');
    });
    rootItem.find('.subList>li').click(function(){
        var subIndex =  $(this).parent().find("li").index($(this));
        var pIndex = $(this).parents('li[data-value]').attr('data-value');
        sessionStorage.setItem('navSelected','['+ (pIndex) +','+subIndex+']');
    });
</script>