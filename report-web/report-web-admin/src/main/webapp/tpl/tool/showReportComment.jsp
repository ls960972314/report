<%@ page language="java" pageEncoding="utf-8"%>
<body>
	<div id="showReportComment"></div>
</body>

<script type="text/javascript">

/* 初始化 */
$(function() {
	$("#topNav").hide();
	/* main.jsp 菜单栏点击后改变css样式 */
	navgChange();
	/* 取reportFlag */
	var tempStr = file.substring(file.indexOf("?") + 1, file.length);
	var flagStr = tempStr.split("=");
	/* 根据URL后面的报表标志从数据库中找到存储的public，condition，chart信息 */
	$.ajax({
		url: "reportComment/getComment",
		type: "POST",
		cache: false,
		data: { 
			toolFlag: flagStr[1]
		},
		dataType: "json",
		success: function(data) {
			if (data.code == "0") {
				$("#showReportComment").html(marked(data.data));
			} else {
				$("#showReportComment").html("系统有错误,请联系管理员");
			}
		}
	});
});
</script>
