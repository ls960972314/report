<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="index" required="true" rtexprvalue="true" description="" %>
<%@ attribute name="loadScript" required="true" rtexprvalue="true" description="" %>

<script type="text/javascript">

function mapInit(num) {
	$("#content" + num).css("width", $(window).width() * 0.785);
	$("#content" + num).css("height", $(window).height() * 1.3);
}

/* echarts图表初始化 */
function mapImgInit() {
	require.config({
				paths : {
					'echarts' : '${pageContext.request.contextPath}/js/echarts', //echarts.js的路径
					'echarts/chart/map' : '${pageContext.request.contextPath}/js/echarts-original-map'
				}
			});
	require([ 'echarts','echarts/chart/map' ], mapDrawEChart);
	//渲染ECharts图表
}

/* 图表回调函数 */
function mapDrawEChart(ec) {
	/* 图表渲染的容器对象 */
	var chartContainer${index} = document.getElementById("echart${index}");
	$.get('${pageContext.request.contextPath}/js/china.json', function (chinaJson) {
	        	ec.registerMap('china', chinaJson);
	        	/* 加载图表 */
	    		myChart${index} = ec.init(chartContainer${index});
	    		myChart${index}.setOption(option${index}, true);
		});
	
}

//为日期选择相应维度控件
function mapDatePickInit (conNode, conName) {
	var time = $('.currentItem a').attr('time');
	conNode.removeClass("hasDatepicker");
	if (time == "日") {
		if (conName.indexOf("开始") != -1 || conName.indexOf("begin") != -1) {
			dayPickerBeginInit(conNode);
		} else if (conName.indexOf("结束") != -1 || conName.indexOf("end") != -1) {
			dayPickerEndInit(conNode);
		}
	} else if (time == "周") {
		if (conName.indexOf("开始") != -1 || conName.indexOf("begin") != -1) {
			weekPickerBeginInit(conNode);
		} else if (conName.indexOf("结束") != -1 || conName.indexOf("end") != -1) {
			weekPickerEndInit(conNode);
		}
	} else if (time == "月") {
		monthPickerInit(conNode);
	}
}

function mapTime(num) {
	timePickInit();
	var time = $('.currentItem a').attr('time');
	if (time == '年') {
		if (ysql != "") {
			$('#mainContainer').show();
			qid = ysql;
		} else {
			$('#mainContainer').hide();
		}
	}
	if (time == '季') {
		if (qsql != "") {
			$('#mainContainer').show();
			qid = qsql;
		} else {
			$('#mainContainer').hide();
		}
	}
	if (time == '月') {
		if (msql != "") {
			$('#mainContainer').show();
			qid = msql;
		} else {
			$('#mainContainer').hide();
		}
	}
	if (time == '周') {
		if (wsql != "") {
			$('#mainContainer').show();
			qid = wsql;
		} else {
			$('#mainContainer').hide();
		}
	}
	if (time == '日') {
		if (dsql != "") {
			$('#mainContainer').show();
			qid = dsql;
		} else {
			$('#mainContainer').hide();
		}
	}
	if (time == '时') {
		if (hsql != "") {
			$('#mainContainer').show();
			qid = hsql;
		} else {
			$('#mainContainer').hide();
		}
	}
}

function jqGridPager() {
	jQuery("#list${index}").jqGrid('navGrid','#pager${index}',
			{edit:false,add:false,del:false},
			{},
			{},
			{},
			{multipleSearch:true, multipleGroup:false}
			);
}
</script>

${loadScript}
