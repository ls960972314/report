<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="index" required="true" rtexprvalue="true" description="" %>
<%@ attribute name="fileName" required="true" rtexprvalue="true" description="" %>
<%@ attribute name="fileTitle" required="true" rtexprvalue="true" description="" %>
<%@ attribute name="loadScript" required="true" rtexprvalue="true" description="" %>
<style type="text/css">
		/* 所有class为menu的div中的ul样式 */
		div.menu ul
		{
		    list-style:none; /* 去掉ul前面的符号 */
		    margin: 0px; /* 与外界元素的距离为0 */
		    padding: 0px; /* 与内部元素的距离为0 */
		    width: auto; /* 宽度根据元素内容调整 */
		}
		/* 所有class为menu的div中的ul中的li样式 */
		div.menu ul li
		{
		    float:left; /* 向左漂移，将竖排变为横排 */
		}
		/* 所有class为menu的div中的ul中的a样式(包括尚未点击的和点击过的样式) */
		div.menu ul li a, div.menu ul li a:visited
		{
		    background-color: #465c71; /* 背景色 */
		    border: 1px #4e667d solid; /* 边框 */
		    color: #dde4ec; /* 文字颜色 */
		    display: block; /* 此元素将显示为块级元素，此元素前后会带有换行符 */
		    line-height: 1.35em; /* 行高 */
		    padding: 4px 20px; /* 内部填充的距离 */
		    text-decoration: none; /* 不显示超链接下划线 */
		    white-space: nowrap; /* 对于文本内的空白处，不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。 */
		}
		/* 所有class为menu的div中的ul中的a样式(鼠标移动到元素中的样式) */
		div.menu ul li a:hover
		{
		    background-color: #bfcbd6; /* 背景色 */
		    color: #465c71; /* 文字颜色 */
		    text-decoration: none; /* 不显示超链接下划线 */
		}
		/* 所有class为menu的div中的ul中的a样式(鼠标点击元素时的样式) */
		div.menu ul li a:active
		{
		    background-color: #5599FF; /* 背景色 */
		    color: #cfdbe6; /* 文字颜色 */
		    text-decoration: none; /* 不显示超链接下划线 */
		}
		.mod1{
		  height:45%;
		  margin-top:40px;
		}
		
		.mod{
			position:relative;
		}
		.radius{
		  border-top-left-radius:8px;
		  border-top-right-radius:8px;
		}
		.mod .mod-header{
		  height:10%;
		  line-height:43px;
		  padding:0 23px;
		  background: #e4e4e4;
		  border:1px solid #b4b4b4;
		  text-align:right;
		}
		.mod .mod-header h2{
		  float:left;
		  font-size: 15px;
		  color: #333;
		  font-weight: normal;
		  margin: 0;
		  padding: 0;
		  outline: 0;
		}
		.mod .mod-bottom {
		   height:10%;
		}
		.mod .mod-body {
		height:80%;
		border: 1px solid #b4b4b4;
		border-top: 0px none;
		padding-bottom: 1px;
		}
		.particle{
		  display:inline-block;
		  zoom:1;
		  height:30px;
		  line-height:30px;
		}
		.particle li{
		  padding:0 3px;
		  margin-right:22px;
		  cursor:pointer;
		  display:inline-block;
		  zoom:1;
		}
		.particle li.on{
		  background:url(${pageContext.request.contextPath}/ui/images/backg.png) repeat-x 0 -256px;
		}
		.particle li.off{
			color:#999;
			cursor:default;
		}
		
		.showdetails {
		border: 1px solid #b4b4b4;
		padding-bottom: 1px;
		border-top: 0px;
		}
		.showdetails a {
		display: block;
		background: #e4e4e4;
		padding-left: 23px;
		height: 42px;
		line-height: 42px;
		text-decoration: underline;
		text-decoration:none;color:#2a5295;cursor:pointer;
		}
        
		body {
		    margin-top:0px;    /* 去除网页上边空白 */
		}
		/* 大容器，包含下面所有层 */
		.container {
		    width:90%;
		    margin: auto;    /* 居中 */
		}
		/* 头部 */
		.header {
		    width:100%;
		    height:20px;
		}
		/* 中部，包括左边区(sider)和右边内容区(content) */
		.middleBody {
		    width:100%;
		}
		/* 左边 */
		.sider {
		    width:15%;
		    float:left;
		}
		/* 右边主内容区 */
		.content {
		    /* 此两行是#content自适应宽度的关键，旨在与右边界对齐，且不被挤到下面去 */
		    padding-right:10000px;
		    margin-right:-10000px;
		    float:left;
		}
		/* #sider和#content共同属性，此为自适应高度的关键，旨在#sider和#content下边界对齐，且不会露白*/
		.column {
		    padding-bottom:20000px;
		    margin-bottom:-20000px;
		}
		/* 底部 */
		.footer{
		    clear:left;    /* 防止float:left对footer的影响 */
		    width:100%;
		}
	</style>
<form action="exp/expCsv" id="loadForm${index}" method="post">
	<input type="hidden" id="qid${index}" name="qid"/>
	<input type="hidden" id="title${index}" name="title"/>
	<input type="hidden" id="fileName${index}" name="fileName"/>
	<input type="hidden" id="filters${index}" name="filters"/>
</form>
<script type="text/javascript">
$(function() {
$("#content"+${index}).css("width",$(window).width()*0.8);
$("#content"+${index}).css("height",$(window).height()*0.9);
    var myChart${index};
	require.config({
	    paths: {
	        'echarts': '${pageContext.request.contextPath}/js/echarts', //echarts.js的路径
	        'echarts/chart/line' : '${pageContext.request.contextPath}/js/echarts', //echarts.js的路径
	        'echarts/chart/bar' : '${pageContext.request.contextPath}/js/echarts'
	    }
	});
	require(
	[
	    'echarts',
	    'echarts/chart/line',
	    'echarts/chart/bar'
	],
	//回调函数
	DrawEChart${index}
	);
	//渲染ECharts图表
	dayPickerInit('beginTime${index}','endTime${index}');
});

function DrawEChart${index}(ec) {
     //图表渲染的容器对象
     var chartContainer${index} = document.getElementById("echart${index}");
     //加载图表
     myChart${index} = ec.init(chartContainer${index});
     //option
     //myChart${index}.setOption(option);
     // 动态添加默认不显示的数据
	 
}
</script>

${loadScript}         
              
<script type="text/javascript">
function look${index}() {
	lookDiagram ('list${index}',initFilters${index}());
}
jQuery("#list${index}").jqGrid('navGrid','#pager${index}',
{edit:false,add:false,del:false},
{},
{},
{},
{multipleSearch:true, multipleGroup:false}
);

$("#inputBankName${index}").focus(function(){
	if ($("#inputBankName${index}").val() == "") {
		for(var i=0;i<bankName.length;i++){
			$("#ulBankName${index}").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inBankName${index}('"+bankName[i][2]+"')\">"+bankName[i][2]+"</a></li>");
		}
	}
	$("#ulBankName${index}").show();
});

$('#inputBankName${index}').bind('input propertychange', function() {
	var getBankName = $("#inputBankName${index}").val(); 
	$("#ulBankName${index}").html("");
	for(var i=0;i<bankName.length;i++){
		if (bankName[i][2] != null && bankName[i][2].indexOf(getBankName) > -1) {
			$("#ulBankName${index}").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inBankName${index}('"+bankName[i][2]+"')\">"+bankName[i][2]+"</a></li>");
		}
	}
});

function inBankName${index}(bankName) {
	$("#inputBankName${index}").val(bankName);
	$("#ulBankName${index}").hide();
}
function loadReport${index}() {
	//var title = encodeURI("${fileTitle}");
	var title = "${fileTitle}";
	filters=initFilters${index}();
	//filters = encodeURI(filters);
	//var fileName = encodeURI("${fileName}");
	var fileName = "${fileName}";
	if (qid${index} != undefined) {
		$("#qid${index}").val(qid${index});
	} else {
		$("#qid${index}").val("${index}");
	}
	$("#fileName${index}").val(fileName);
	$("#title${index}").val(title);
	$("#filters${index}").val(filters);
	$("#loadForm${index}").submit();
}
</script>