<%@ page language="java" pageEncoding="utf-8"%>
<body>
	<div id="categoryToc"></div>
	<div id="showReportComment"></div>
</body>

<script type="text/javascript">
var padding=[10,40,80,120,160];
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
				var generateTocFlag = data.data.indexOf("[toc]") != -1;
				$("#showReportComment").html(marked(data.data.replace("[toc]","")));
				generateToc(generateTocFlag);
			} else {
				$("#showReportComment").html("系统有错误,请联系管理员");
			}
		}
	});
});

/**
 * 有[toc]标志生成目录
 * @param generateTocFlag 是否生成目录
 * 
 */
function generateToc(generateTocFlag) {
	if (generateTocFlag) {
		$("#categoryToc").append("<p class='MsoToc2' align='center' style='margin-left:0cm;text-align:center'><b><span style='font-size:16.0pt;font-family:宋体;text-transform:uppercase'>目录</span></b></p>");
	    $("#showReportComment").find('h1,h2,h3,h4,h5').each(function(index, item){
	        $('<a name="c' + index + '"></a>').insertBefore($(this));
	        var headerText = $(this).text();
	        var tagName = $(this)[0].tagName.toLowerCase();
	        var tagIndex = parseInt(tagName.charAt(1))-1;
	        //设置不同等级header的排列及缩进样式
	        $("#categoryToc").append($('<p><a href="#c' + index + '" style="padding-left:' + padding[tagIndex] + 'px;"><span>' + headerText + '</span></a></p>'));
	    });
	    $("#categoryToc").append("<hr>");
	}
}
</script>
