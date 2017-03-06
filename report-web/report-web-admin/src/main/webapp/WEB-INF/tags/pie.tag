<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="index" required="true" rtexprvalue="true" description="" %>
<%@ attribute name="loadScript" required="true" rtexprvalue="true" description="" %>

<script type="text/javascript">

function pieInit(num) {
	$("#content" + num).css("width", $(window).width() * 0.785);
	$("#content" + num).css("height", $(window).height() * 1.3);
}

/* echarts图表初始化 */
function pieImgInit() {
	require.config({
				paths : {
					'echarts' : '${pageContext.request.contextPath}/js/echarts', //echarts.js的路径
					'echarts/chart/pie' : '${pageContext.request.contextPath}/js/echarts',
					'echarts/chart/funnel' : '${pageContext.request.contextPath}/js/echarts',
					'echarts/chart/line' : '${pageContext.request.contextPath}/js/echarts',
				    'echarts/chart/bar' : '${pageContext.request.contextPath}/js/echarts'
				}
			});
	require([ 'echarts','echarts/chart/pie','echarts/chart/funnel','echarts/chart/line', 'echarts/chart/bar' ], pieDrawEChart);
	//渲染ECharts图表
}

/* 图表回调函数 */
function pieDrawEChart(ec) {
	/* 图表渲染的容器对象 */
	var chartContainer${index} = document.getElementById("echart${index}");
	/* 加载图表 */
	myChart${index} = ec.init(chartContainer${index});
	//myChart.setOption(option);
	/* 动态添加默认不显示的数据 */
	
}

//为日期选择相应维度控件
function pieDatePickInit (conNode, conName) {
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

function pieTime(num) {
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
