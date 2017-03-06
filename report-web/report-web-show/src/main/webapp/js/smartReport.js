/******************************************************************************************************************************/
var formatDatas = [];
var ccolumn = [];
var saveFormatDatas = "";
/* 保存sql */
var sqlIds = "";
/* toolMiddle js */
var funStr = "";
var i = 1;
var conArr = [];
/* echarts图表初始化 */
var myChart;
/* 保存图标 */
var chartOrder = 1;
var saveReportCharts = [];

var staticRowNum = "";
var staticSql = "";
var staticDataBaseSource = "";
var staticCcolumn = "";

/******************************************************************************************************************************/
/******************************************************************************************************************************/
$(function() {
	$("#content").css("width", $(window).width() * 0.95);
	$("#content").css("height", $(window).height() * 0.9);
	
	require.config({
		paths: {
			'echarts': '../js/echarts',
			//echarts.js的路径
			'echarts/chart/line': '../js/echarts',
			//echarts.js的路径
			'echarts/chart/bar': '../js/echarts',
			'echarts/chart/pie': '../js/echarts'
		}
	});
	
	/* 填写完中文列名,失去焦点时触发该事件 */
	$("#toolCColumn").blur(function() {
		$("#columnName").empty(); /* 解析中文列名,重新构造列表 */
		$("#pieOptionName").empty();
		$("#pieOptionValue").empty();
		var toolCColumn = $("#toolCColumn").val();
		toolCColumn = replaceDot(toolCColumn);
		toolCColumn = toolCColumn.split(",");
		var toolEColumn = $("#toolEColumn").val();
		toolEColumn = toolEColumn.split(",");
		for (var i = 0; i < toolCColumn.length; i++) {
			if (toolCColumn[i].indexOf(":") != -1 && toolCColumn[i].indexOf("{") != -1 && toolCColumn[i].indexOf("}") != -1) {
				tmpArr = toolCColumn[i].substring(toolCColumn[i].indexOf("{") + 1, toolCColumn[i].indexOf("}")).split("|");
				for (var j = 0; j < tmpArr.length; j++) {
					$("#columnName").append("<option value='" + tmpArr[j] + "'>" + tmpArr[j] + "</option>");
					$("#pieOptionName").append("<option value='" + toolEColumn[j] + "'>" + tmpArr[j] + "</option>");
					$("#pieOptionValue").append("<option value='" + toolEColumn[j] + "'>" + tmpArr[j] + "</option>");
				}
			} else {
				$("#columnName").append("<option value='" + toolCColumn[i] + "'>" + toolCColumn[i] + "</option>");
				$("#pieOptionName").append("<option value='" + toolEColumn[i] + "'>" + toolCColumn[i] + "</option>");
				$("#pieOptionValue").append("<option value='" + toolEColumn[i] + "'>" + toolCColumn[i] + "</option>");
			}
		}
	});
});

/* 查询数据 */
function queryData() {
	var conData = [];
	ccolumn = [];
	var toolTitle = $("#toolTitle").val();
	var toolGather = $("#toolGather").val();
	var toolCColumn = $("#toolCColumn").val();
	toolCColumn = replaceDot(toolCColumn);
	toolCColumn = toolCColumn.split(",");
	var toolHSqlId = $("#toolHSqlId").val();
	//$("#toolEColumn").val(initToolEColumn(toolHSqlId));
	/* 从sql中截取数据库展示字段 */
	var toolEColumn = $("#toolEColumn").val();
	toolEColumn = toolEColumn.split(","); /* 封装colModel */
	var ecolumn = [];
	$.each(toolEColumn, function(n, value) {
		var obj = new Object();
		obj.name = value;
		obj.index = value;
		ecolumn.push(obj);
	});
	$.each(conArr, function(n, value) {
		var obj = new Object();
		var str = "";
		var chk_value = "";
		switch (value.option) {
		case "input":
			str = $("input[name='" + value.where + "']").val();
			obj.name = value.where; //{1}
			obj.value = str;
			obj.type = "input";
			conData.push(obj);
			break;
		case "select":
			if (value.type == '模糊查询') {
				if ($("input[name='input" + value.where + "']").val() == "") {
					str = $("input[name='input" + value.where + "']").attr("placeholder");
				} else {
					str = $("input[name='input" + value.where + "']").val();
				}

				obj.name = value.where;
				obj.value = str;
				obj.type = "select";
				conData.push(obj);
			} else {
				str = $("select[name='" + value.where + "'] :selected").val();
				obj.name = value.where;
				obj.value = str;
				obj.type = "select";
				conData.push(obj);
			}
			break;
		case "checkbox":
			$("input[name='" + value.where + "']:checked").each(function() {
				chk_value = chk_value + $(this).val() + ",";
			});
			obj.name = value.where;
			obj.value = chk_value;
			obj.type = "checkbox";
			conData.push(obj);
			break;
		}
	});
	jqgrid(JSON.stringify(conData), toolTitle, toolCColumn, ecolumn, toolHSqlId, toolGather);
}

/* jqGird表格显示数据 */
function jqgrid(conData, toolTitle, toolCColumn, ecolumn, toolHSqlId, toolGather) { /*参数： conData:条件数据,toolTitile:标题,toolCColumn:中文列名,ecolumn:数据库列名,toolHSqlId:sql内容,toolGather:汇总数据的列 */
	/* 处理二级表头   e.g [日期,金额:{成功金额|失败金额},笔数:{成功笔数|失败笔数}]*/
	var groupHeader = [];
	var groupFlag = false;
	var ecindex = 1;
	for (var i = 0; i < toolCColumn.length; i++) {
		if (toolCColumn[i].indexOf(":") != -1 && toolCColumn[i].indexOf("{") != -1 && toolCColumn[i].indexOf("}") != -1) {
			groupFlag = true; /* 拼装groupHeader */
			var obj = new Object();
			obj.titleText = toolCColumn[i].substring(0, toolCColumn[i].indexOf(":"));
			obj.startColumnName = ecolumn[ecindex].name;
			obj.numberOfColumns = toolCColumn[i].substring(toolCColumn[i].indexOf("{"), toolCColumn[i].indexOf("}")).split("|").length;
			groupHeader.push(obj);
			ecindex = ecindex + obj.numberOfColumns; /* 拼装colNames */
			var tmpArr = [];
			tmpArr = toolCColumn[i].substring(toolCColumn[i].indexOf("{") + 1, toolCColumn[i].indexOf("}")).split("|");
			ccolumn = $.merge(ccolumn, tmpArr);
		} else {
			ccolumn.push(toolCColumn[i]);
		}
	} 
	/* 格式化表格 */
	if (formatDatas.length > 0) {
		for (var i = 0; i < formatDatas.length; i++) {
			for (var j = 0; j < ccolumn.length; j++) {
				if (formatDatas[i].name == ccolumn[j]) {
					var obj = new Object();
					obj.name = ecolumn[j].name;
					obj.index = ecolumn[j].index;
					obj.formatter = formatDatas[i].formatter;
					obj.formatoptions = formatDatas[i].formatoptions;
					ecolumn[j] = obj;
				}
			}
		}
	}

	/* 重新构造表格 */
	$("#reportList").jqGrid('GridUnload'); /* 输出表格 */
	jQuery("#reportList").jqGrid({
		url: 'reportMakeQueryData',
		datatype: "json",
		mtype: 'post',
		colNames: ccolumn,
		colModel: ecolumn,
		ajaxGridOptions:{timeout : 5 * 60 * 1000},
		postData: {
			baseSql: toolHSqlId,
			condition: conData,
			dataBaseSource: $("#dataBaseSource").val()
		},
		height: $(window).height() * 0.15,
		width: $(window).width() * 0.8,
		//autowidth:true,
		rowNum: 10,
		rowList: [10, 20, 30, 50, 1000],
		pager: '#reportPager',
		sortname: '',
		viewrecords: true,
		sortorder: '',
		caption: toolTitle,
		footerrow: true,
		userDataOnFooter: true,
		altRows: true,
		loadError: function(xhr,status,error){  
			location.href = 'report/';
		},
		loadComplete: function(data) {
			if (data.data != undefined) {
				jQuery.messager.alert('提示:', data.data, 'warning');
			}
		},
		gridComplete: function() {
			/* 汇总功能 */
			if (toolGather != null && toolGather != "") {
				var gathers = toolGather.split(",");
				var gatheJson = "";
				if (gathers.length > 0) {
					for (var j = 0; j < gathers.length; j++) {
						for (var k = 0; k < ccolumn.length; k++) {
							if (ccolumn[k] == gathers[j]) {
								var gatherJsonKey = ecolumn[k].name;
								var dataList = jQuery("#reportList").jqGrid('getCol', gatherJsonKey);
								gatheJson = gatheJson + gatherJsonKey + ":" + getTotleNum(dataList) + ",";
							}
						}
					}
					gatheJson = "{" + gatheJson.substring(0, gatheJson.length - 1) + "}";
					jQuery("#reportList").jqGrid('footerData', 'set', eval('(' + gatheJson + ')'));
				}
			}
		}
	});
	jQuery("#reportList").jqGrid('navGrid', '#reportPager', {
		edit: false,
		add: false,
		del: false
	}, {}, {}, {}, {
		multipleSearch: true,
		multipleGroup: false
	});

	/* 二级表头 */
	if (groupFlag) {
		jQuery("#reportList").jqGrid('setGroupHeaders', {
			useColSpanStyle: false,
			groupHeaders: groupHeader
		});
	}

}

//增加按钮操作
function addConShow() {
	$('#conShow').append("<div style='float:left;width:300px;' id='conShow" + i + "'></div>");
	$("#conShow" + i).append($("#conName").val());
	var conOption = getConOption();
	var conValueArry = "";
	if (conOption == 2 || conOption == 3) {
		conValueArry = getConValueByArry();
	}
	var conWhere = $('#conWhere').val();
	var obj = new Object();
	switch (conOption) {
	case 1:
		$("#conShow" + i).append("<input type='text' style='width:150px;' name='" + conWhere + "'/>");
		if ($("#conType").val() == "日期") {
//			$("input[name='" + conWhere + "']").datepicker();
			$("input[name='" + conWhere + "']").datebox({});
		}
		obj.where = conWhere;
		obj.type = $("#conType").val();
		obj.name = $("#conName").val();
		obj.option = $("#conOption").val();
		obj.conValue = "";
		obj.conDefaultValue = $("#conDefaultValue").val();
		obj.orderNum = i;
		obj.rowNum = $("#conRowNum").val();
		obj.dataBaseSource = $("#dataBaseSource").val();
		conArr.push(obj);
		break;
	case 2:
		if ($("#conType").val() == "模糊查询") {
			$("#conShow" + i).append("<div class='dynamicDiv'><input type='text' style='width:200px;' name='input" + conWhere + "'><ul class='dynamicUl' name='ul" + conWhere + "'></ul></div>");
			$("input[name='input" + conWhere + "']").attr("placeholder", conValueArry[0]);
			$("input[name='input" + conWhere + "']").focus(function() {
				if ($("input[name='input" + conWhere + "']").val() == "" && $("ul[name='ul" + conWhere + "'] li").size() == 0) {
					for (var i = 0; i < conValueArry.length; i++) {
						if (conValueArry[i].length >= 12) {
							$("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('" + conValueArry[i] + "','" + conWhere + "')\">" + conValueArry[i].substring(0, 10) + "...</a></li>");
						} else $("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('" + conValueArry[i] + "','" + conWhere + "')\">" + conValueArry[i] + "</a></li>");
					}
				}
				$("ul[name='ul" + conWhere + "']").show();
			});

			$("input[name='input" + conWhere + "']").bind('input propertychange', function() {
				var getconValueArry = $("input[name='input" + conWhere + "']").val();
				$("ul[name='ul" + conWhere + "']").html("");
				for (var i = 0; i < conValueArry.length; i++) {
					if (conValueArry[i] != null && conValueArry[i].indexOf(getconValueArry) > -1) {
						if (conValueArry[i].length >= 12) {
							$("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('" + conValueArry[i] + "','" + conWhere + "')\">" + conValueArry[i].substring(0, 10) + "...</a></li>");
						} else $("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('" + conValueArry[i] + "','" + conWhere + "')\">" + conValueArry[i] + "</a></li>");
					}
				}
			});
			obj.where = conWhere;
			obj.type = $("#conType").val();
			obj.name = $("#conName").val();
			obj.option = $("#conOption").val();
			obj.conValue = $("#conValue").val();
			obj.conDefaultValue = $("#conDefaultValue").val();
			obj.orderNum = i;
			obj.rowNum = $("#conRowNum").val();
			obj.dataBaseSource = $("#dataBaseSource").val();
			conArr.push(obj);
		} else if ($("#conType").val() == "文本") {
			$("#conShow" + i).append("<select id='conValueSelect" + i + "' name='" + conWhere + "'></select>");
			$.each(conValueArry, function(n, value) {
				$("#conValueSelect" + i).append("<option value='" + value + "'>" + value + "</option>");
			});
			obj.where = conWhere;
			obj.type = $("#conType").val();
			obj.name = $("#conName").val();
			obj.option = $("#conOption").val();
			obj.conValue = $("#conValue").val();
			obj.conDefaultValue = $("#conDefaultValue").val();
			obj.orderNum = i;
			obj.rowNum = $("#conRowNum").val();
			obj.dataBaseSource = $("#dataBaseSource").val();
			conArr.push(obj);
		}

		break;
	case 3:
		$.each(conValueArry, function(n, value) {
			$("#conShow" + i).append("<input type='checkbox' name='" + conWhere + "' value='" + value + "'>" + value + "</input>");
		});
		obj.where = conWhere;
		obj.type = $("#conType").val();
		obj.name = $("#conName").val();
		obj.option = $("#conOption").val();
		obj.conValue = $("#conValue").val();
		obj.conDefaultValue = $("#conDefaultValue").val();
		obj.orderNum = i;
		obj.rowNum = $("#conRowNum").val();
		obj.dataBaseSource = $("#dataBaseSource").val();
		conArr.push(obj);
		break;
	}

	$("#conShow" + i).append("<input type='button' value='移除' onclick='removeCon(" + i + "," + JSON.stringify(obj) + ")'/>");
	i++;
}

/**
 * 增加图表
 * @param chartType bar pie
 */
function addChart(chartType) {
	if (chartType == 'bar') {
		var saveReportChart = new Object();
		saveReportChart.dataVsX = $("#columnVsX").val();
		saveReportChart.dataVsLe = $("#columnVsLegend").val();
		saveReportChart.chartOption = $("#chartOption").val();
		saveReportChart.chartOrder = chartOrder;
		saveReportChart.chartName = $("#chartName").val();
		saveReportChart.chartType = chartType;
		if (saveReportChart.dataVsX == "" || saveReportChart.dataVsLe == "" || saveReportChart.chartOption == "" || saveReportChart.chartOrder == "" || saveReportChart.chartName == "" || saveReportChart.chartType == "") {
			jQuery.messager.alert('提示:', '请填写完所有表单', 'warning');
			return;
		}
		saveReportCharts.push(saveReportChart);
		chartOrder++;
		jQuery.messager.alert('提示:', "添加柱状图/线形图" + saveReportChart.chartName + "成功", 'info');
		$('#addBarDialog').dialog('close');
	} else if (chartType == 'pie') {
		var saveReportChart = new Object();
		saveReportChart.dataVsX = $("#pieOptionName").combobox('getValue');
		saveReportChart.dataVsLe = $('#pieOptionValue').combobox('getValue');
		saveReportChart.chartOption = pieOption;
		saveReportChart.chartOrder = chartOrder;
		saveReportChart.chartName = $("#pieChartName").val();
		saveReportChart.chartType = chartType;
		saveReportChart.showRowNum = $("#pieChartShowRowNum").val();
		if (saveReportChart.dataVsX == "" || saveReportChart.dataVsLe == "" || saveReportChart.chartOption == "" || saveReportChart.chartOrder == "" || saveReportChart.chartName == "" || saveReportChart.chartType == "" || saveReportChart.showRowNum == "") {
			jQuery.messager.alert('提示:', '请填写完所有表单', 'warning');
			return;
		}
		saveReportCharts.push(saveReportChart);
		chartOrder++;
		jQuery.messager.alert('提示:', "添加饼状图" + saveReportChart.chartName + "成功", 'info');
		$('#addPieDialog').dialog('close');
	}
	var chartNames = "";
	if (saveReportCharts.length > 0) {
		for (var x in saveReportCharts) {
			chartNames = chartNames + "<a href='#' class='easyui-linkbutton'>" + saveReportCharts[x].chartName + "</a>";
		}
		$("#chartPanel").html("已添加图形：" + chartNames);
	} else {
		$("#chartPanel").html("");
	}
	
	
} 

/**
 * 展现图表
 * @param chartType bar pie
 */
function showChart(chartType) {
	if (chartType == "bar") {
		imgInit('bar');
		var chartOption = $("#chartOption").val();
		var columnVsLegend = $("#columnVsLegend").val();
		var columnVsX = $("#columnVsX").val();
		var toolEColumns = $("#toolEColumn").val();
		var ccols = ccolumn;
		var ecols = toolEColumns.split(",");
		if (columnVsLegend == "") {
	
			for (var j = 1; j < ccols.length; j++) {
				columnVsLegend = columnVsLegend + ecols[j] + ":" + ccols[j] + ",";
			}
			columnVsLegend = columnVsLegend.substring(0, columnVsLegend.length - 1);
		}
	
		/* columnVsX为空则根据表格自动生成 */
		if (columnVsX == "") {
			columnVsX = ecols[0] + ":" + ccols[0];
		}
		
		/* option为空则根据已有的柱状图option自动生成 */
		if (chartOption != "") {
			if (isJson(chartOption)) {
				barOption = chartOption;
			} else {
				try {
					barOption = eval('(' + chartOption + ')');
				} catch (e) {
					jQuery.messager.alert('提示:','图表option语法错误','error');
					return;
				}
			}
		}
		
		/* e.g tx_date:日期,result:结果,sumAmt:金额,sumcnt:笔数 */
		var columnVsLegends = columnVsLegend.split(",");
	
		var optionLegend = [];
	
		/* 若为第一次加载,series为空则拼接barOption.series和legend */
		if (barOption.series.length == 0) {
			$.each(columnVsLegends, function(n, value) {
				var strs = value.split(":");
				var serie = "{\"name\":\"" + strs[1] + "\",\"type\":\"bar\",\"data\":[]}";
				serie = JSON.parse(serie);
				barOption.series.push(serie);
			});
		}
	
	
	
		/* barOption.series赋值 */
		$.each(columnVsLegends, function(n, value) { /* amt:金额,cnt:笔数,amt/cnt:测试除法 */
			/* {"name":"笔数","type":"bar","data":[104,103]} */
			var strs = value.split(":");
			$.each(barOption.series, function(n, value) {
				if (value.name == strs[1]) {
					var tempArr = jQuery("#reportList").jqGrid('getCol', strs[0]).reverse();
					for (var i = 0; i < tempArr.length; i++) {
						tempArr[i] = Number(tempArr[i]);
					}
					value.data = tempArr;
					optionLegend.push(strs[1]);
				}
			});
		}); /* X轴数据封装 */
		var xdatas = columnVsX.split(":");
		if (barOption.xAxis.length == 1) {
			barOption.xAxis[0].data = jQuery("#reportList").jqGrid('getCol', xdatas[0]).reverse();
		} else if (barOption.xAxis.length == 2) {
			barOption.xAxis[0].data = jQuery("#reportList").jqGrid('getCol', xdatas[0]).reverse();
			barOption.xAxis[1].data = jQuery("#reportList").jqGrid('getCol', xdatas[0]).reverse();
		}
		barOption.legend.data = optionLegend;
	
		
		$("#chartOption").val(JSON.stringify(barOption));
		$("#columnVsLegend").val(columnVsLegend);
		$("#columnVsX").val(columnVsX);
		
		myChart.setOption(barOption, true);
	} else if (chartType == "pie") {
		imgInit('pie');
		var pieChartName = $("#pieChartName").val();
		var pieOptionName = $('#pieOptionName').combobox('getValue');
		var pieOptionValue = $('#pieOptionValue').combobox('getValue');
		var pieChartShowRowNum = $("#pieChartShowRowNum").val();
		var pieKey = jQuery("#reportList").jqGrid('getCol', pieOptionName);
		var pieValue = jQuery("#reportList").jqGrid('getCol', pieOptionValue);
		pieOption.title.text = pieChartName;
		pieOption.legend.data = pieKey;
		var pieSeriesData = [];
		var otherSumName = "其他";
		var otherSumValue = 0;
		var otherShow = false;
		for (var x in pieKey) {
			if (x < pieChartShowRowNum * 1) {
				var obj = new Object();
				obj.name = pieKey[x];
				obj.value = pieValue[x];
				pieSeriesData.push(obj);
			} else {
				otherShow = true;
				otherSumValue += pieValue[x] * 1;
			}
		}
		if (pieKey.length > pieChartShowRowNum * 1) {
			pieOption.legend.data.push('其他');
		}
		if (otherShow) {
			pieSeriesData.push({name: otherSumName, value: otherSumValue});
		}
		pieOption.series[0].data = pieSeriesData;
		myChart.setOption(pieOption, true);
	}
	
}

/* 保存SQL */
function saveSql() {
	var timeSelect = $("#timeSelect  option:selected").text();
	var toolSqlName = $("#toolSqlName").val();
	$.ajax({
		type: "post",
		url: "saveReportSql",
		dataType: 'json',
		data: {
			baseSql: $("#toolHSqlId").val(),
			timeSelect: timeSelect,
			sqlIds: sqlIds,
			sqlName: toolSqlName,
			dataBaseSource: $("#dataBaseSource").val()
		},
		success: function(data) {
			if (data.code == 0) {
				sqlIds = sqlIds.concat("\"" + timeSelect + "\"" + ":" + "\"" + data.data + "\"").concat(" ,");
				jQuery.messager.alert('提示:', '保存SQL成功', 'info');
			} else {
				jQuery.messager.alert('提示:', data.msg, 'info');
				return;
			}
		}
	});
}

function addBar () {
	var $formname = $('#addBarForm');
	setComponentStyle('addBarDialog', 2);
	var radioChecked = $formname.find('input[type=radio]:checked');
	var readOnlyEdit = $formname.find('input[data-readonly="edit"]');
	$('#addBarDialog').dialog('open').dialog('setTitle', "新建线形图").parent().addClass('dialog');
	$formname.form('clear');
	
	return;
}

function addPie() {
	$("#pieOptionName").combobox({});
	$("#pieOptionValue").combobox({});
	var $formname = $('#addPieForm');
	setComponentStyle('addPieDialog', 2);
	var radioChecked = $formname.find('input[type=radio]:checked');
	var readOnlyEdit = $formname.find('input[data-readonly="edit"]');
	$('#addPieDialog').dialog('open').dialog('setTitle', "新建饼状图").parent().addClass('dialog');
	$formname.form('clear');
	
	return;
}
/* 打开统计信息窗口 */
function addStatistics() {
	var $formname = $('#staticForm');
	setComponentStyle('staticDialog', 2);
	$('#staticDialog').dialog('open').dialog('setTitle', "新增统计信息").parent().addClass('dialog');
	return;
}
/* 查询统计信息 */
function queryStatic () {
	if ($("#staticCcolumn").val() == "") {
		jQuery.messager.alert('提示:', '请填写合计列名', 'warning');
		return;
	}
	if ($("#staticSql").val() == "") {
		jQuery.messager.alert('提示:', '请填写sql', 'warning');
		return;
	}
	if ($("#staticRowNum").val() == "") {
		jQuery.messager.alert('提示:', '请选择行数', 'warning');
		return;
	}
	var conData = [];
	$.each(conArr, function(n, value) {
		var obj = new Object();
		var str = "";
		var chk_value = "";
		switch (value.option) {
		case "input":
			str = $("input[name='" + value.where + "']").val();
			obj.name = value.where; //{1}
			obj.value = str;
			obj.type = "input";
			conData.push(obj);
			break;
		case "select":
			if (value.type == '模糊查询') {
				if ($("input[name='input" + value.where + "']").val() == "") {
					str = $("input[name='input" + value.where + "']").attr("placeholder");
				} else {
					str = $("input[name='input" + value.where + "']").val();
				}

				obj.name = value.where;
				obj.value = str;
				obj.type = "select";
				conData.push(obj);
			} else {
				str = $("select[name='" + value.where + "'] :selected").val();
				obj.name = value.where;
				obj.value = str;
				obj.type = "select";
				conData.push(obj);
			}
			break;
		case "checkbox":
			$("input[name='" + value.where + "']:checked").each(function() {
				chk_value = chk_value + $(this).val() + ",";
			});
			obj.name = value.where;
			obj.value = chk_value;
			obj.type = "checkbox";
			conData.push(obj);
			break;
		}
	});
	
	$.ajax({
		url: "reportMakeQueryData",
		type: "post",
		dataType: "json",
		data: {
			baseSql: $("#staticSql").val(),
			condition: JSON.stringify(conData),
			dataBaseSource: $("#dataBaseSource").val(),
			page:1,
			rows:100
		},
		success: function(data) {
			var staticData = [];
			var staticCcolumns = $("#staticCcolumn").val().split(",");
			var widthPer = 100/Math.ceil(staticCcolumns.length/$("#staticRowNum").val());
			if (data.total > 0) {
				staticData = data.rows[0];
			}
			if (staticData != []) {
				var staticDivHtml = "";
				for (var x in staticCcolumns) {
					staticDivHtml = staticDivHtml + "<div class='digest-block' style='width:" + widthPer + "%;'><h4>" + staticCcolumns[x] + "</h4><h1 style='font-size: 32px;'>" + staticData[x] + "</h1></div>";
				}
				$("#staticData").css("height", 69*staticRowNum+"px");
				$("#staticData").html(staticDivHtml);
			}
		}
	});
}
/* 保存统计信息 */
function saveStatic () {
	if ($("#staticCcolumn").val() == "") {
		jQuery.messager.alert('提示:', '请填写合计列名', 'warning');
		return;
	}
	if ($("#staticSql").val() == "") {
		jQuery.messager.alert('提示:', '请填写sql', 'warning');
		return;
	}
	if ($("#staticRowNum").val() == "") {
		jQuery.messager.alert('提示:', '请选择行数', 'warning');
		return;
	}
	staticRowNum = $("#staticRowNum").val();
	staticSql = $("#staticSql").val();
	staticDataBaseSource = $("#dataBaseSource").val();
	staticCcolumn = $("#staticCcolumn").val();
	$('#staticDialog').dialog('close');
	jQuery.messager.alert('提示:', '保存统计信息成功', 'info');
}
/* 保存报表 */
function saveReport() {
	var chartFlag = $('input[name="chartFlag"]:checked').val();
	if (sqlIds == "") {
		jQuery.messager.alert('提示:', '请先保存sql', 'warning');
		return;
	}
	$.ajax({
		url: "saveReport",
		type: "post",
		cache: false,
		dataType: "json",
		data: {
			saveReportFlag: $("#toolFlag").val(),
			saveReportTitle: $("#toolTitle").val(),
			saveReportCColumn: $("#toolCColumn").val(),
			saveReportEColumn: $("#toolEColumn").val(),
			saveReportSqlId: "{" + sqlIds.substring(0, sqlIds.length - 1) + "}",
			saveReportToolGather: $("#toolGather").val(),
			saveReportFormatDatas: saveFormatDatas,

			saveReportCondition: JSON.stringify(conArr),

			saveChartFlag: chartFlag,
			saveReportChart: JSON.stringify(saveReportCharts),
			staticRowNum: staticRowNum,
			staticSql: staticSql,
			staticDataBaseSource: staticDataBaseSource,
			staticCcolumn: staticCcolumn
			//saveReportChartOption:$("#chartOption").val(),
			//saveReportColumnVsLegend:$("#columnVsLegend").val()
		},
		success: function(rs) {
			if (rs.code == 0) {
				jQuery.messager.alert('提示:', '保存报表成功', 'info');
			} else {
				jQuery.messager.alert('提示:', rs.msg, 'info');
				return;
			}
		},
		error: function(rs) {}
	});
}
/******************************************************************************************************************************/
/******************************************************************************************************************************/


/* 点击格式化的时候触发该事件，准备格式化数据 */
function formatClick() {
	var formatSelect = $("#columnName  option:selected").text();
	var columnType = $("#columnType  option:selected").text();
	var flag = true;
	var obj = new Object();
	var fmtObj = new Object();
	var saveFormatData = "";
	if (columnType == "正整数") {
		obj.name = formatSelect;
		obj.formatter = "integer";
		fmtObj.thousandsSeparator = ",";
		fmtObj.defaultValue = "0";
		obj.formatoptions = fmtObj;
		saveFormatData = formatSelect + ":" + "正整数,";
	} else if (columnType == "小数") {
		obj.name = formatSelect;
		obj.formatter = "number";
		fmtObj.decimalSeparator = ".";
		fmtObj.thousandsSeparator = ",";
		fmtObj.decimalPlaces = 2;
		fmtObj.defaultValue = "0.00";
		obj.formatoptions = fmtObj;
		saveFormatData = formatSelect + ":" + "小数,";
	} else if (columnType == "负数") {
		obj.name = formatSelect;
		obj.formatter = "currency";
		fmtObj.decimalSeparator = ".";
		fmtObj.thousandsSeparator = ",";
		fmtObj.decimalPlaces = "2";
		fmtObj.prefix = "-";
		fmtObj.defaultValue = "-0.00";
		obj.formatoptions = fmtObj;
		saveFormatData = formatSelect + ":" + "负数,";
	} else if (columnType == "百分比") {
		obj.name = formatSelect;
		obj.formatter = "currency";
		fmtObj.decimalSeparator = ".";
		fmtObj.thousandsSeparator = ",";
		fmtObj.decimalPlaces = "2";
		fmtObj.defaultValue = "0.00%";
		fmtObj.suffix = "%";
		obj.formatoptions = fmtObj;
		saveFormatData = formatSelect + ":" + "百分比,";
	}
	/* 不插入重复的格式化数据 */
	for (var i = 0; i < formatDatas.length; i++) {
		if (formatDatas[i].name == obj.name) {
			flag = false;
		}
	}
	if (!flag) {
		formatDatas.splice($.inArray(obj, formatDatas), 1);
		if (saveFormatDatas.indexOf(formatSelect) != -1) {
			var beginIndex = saveFormatDatas.indexOf(formatSelect);
			var endIndex = saveFormatDatas.indexOf(",", beginIndex);
			endIndex = (endIndex == -1 ? saveFormatDatas.length : endIndex);
			saveFormatDatas = saveFormatDatas.substring(0, beginIndex) + saveFormatDatas.substring(endIndex + 1);
		}
	}
	formatDatas.push(obj);
	saveFormatDatas = saveFormatDatas + saveFormatData;
	queryData();
}

/* 生成toolEColumn */
function initEcolumn() {
	var toolHSqlId = $("#toolHSqlId").val();
	$("#toolEColumn").val(initToolEColumn(toolHSqlId));
}


/* 取数组的和 */
function getTotleNum(list) {
	var sum = 0;
	for (var i = 0; i < list.length; i++) {
		sum = sum + list[i] * 1;
	}
	return sum;
}


/* 获取操作条件，返回1,2,3等 */
function getConOption() {
	var conOption = $("#conOption  option:selected").text();
	var returnCode = "";
	if (conOption == '表单(input)') {
		returnCode = 1;
	}
	if (conOption == '单选(select)') {
		returnCode = 2;
	}
	if (conOption == '多选(checkbox)') {
		returnCode = 3;
	}
	return returnCode;
}

/* 替换逗号 */
function replaceDot(str) {
	var oldValue = str;
	while (oldValue.indexOf("，") != -1) //寻找每一个中文逗号，并替换
	{
		str = oldValue.replace("，", ",");
		oldValue = str;
	}
	return oldValue;
}

/* 获取条件值，并已数组形式返回，用于生成下拉列表，多选框 */
function getConValueByArry() {
	var conValue = $("#conValue").val();
	var dataBaseSource = $("#dataBaseSource").val();
	if (conValue.indexOf("select") != -1) {
		$.ajax({
			type: "post",
			url: "getConValue",
			dataType: 'json',
			async: false,
			data: {
				selectSql : conValue,
				dataBaseSource : dataBaseSource
			},
			success: function(data) {
				if (data.code == 0) {
					conValue = data.data;
				} else {
					jQuery.messager.alert('提示:', data.msg, 'info');
					return;
				}
			}
		});
	}
	conValue = replaceDot(conValue);
	var conValueArray = new Array();
	conValueArray = conValue.split(',');
	return conValueArray;
}

/* 移除按钮 */
function removeCon(i, obj) {
	$("#conShow" + i).remove();
	conArr.splice($.inArray(obj, conArr), 1);
}

/* 分情况得到选中的值 */
function getConRealValu(conShowName) {
	if ($("input[name='" + conShowName + "']").attr("type") == "text") {
		return $("input[name='" + conShowName + "']").val();
	} else if ($("input[name='" + conShowName + "']").attr("type") == "checkbox") {
		var chk_value = "";
		$("input[name='" + conShowName + "']:checked").each(function() {
			chk_value += $(this).val() + ",";
		});
		var rtchk_value = chk_value.substring(0, chk_value.length - 1);
		return rtchk_value;
	} else {
		return $("select[name='" + conShowName + "'] option:selected").val();
	}
}

/* 下拉列表是否显示日期 */
function changeConType() {
	if ($("#conOption option:selected").text() != '表单(input)') {
		$("#conType").html("<option>文本</option><option>模糊查询</option>");
		$("#conValueTd").html("值<input id='conValue' type='text' size='10'/>");

	} else if ($("#conOption option:selected").text() == '表单(input)') {
		$("#conType").html("<option>文本</option><option>日期</option>");
		$("#conValueTd").html("");
	}
}

/* 初始化数据库列名 */
function initToolEColumn(str) {
	var strmid = str.toLowerCase();
	var firstSelectIndex = getFirstSelectIndex(strmid);
	str = str.substring(firstSelectIndex);
	if (str.indexOf('from') != -1) {
		str = str.substring(0, str.indexOf('from'));
	}
	while (str.indexOf("(") != -1) {
		var removeStr = str.substring(str.indexOf("("), str.indexOf(")") + 1);
		if (removeStr.lastIndexOf("(") != -1) {
			removeStr = removeStr.substring(removeStr.lastIndexOf("("));
		}
		str = str.replace(removeStr, " ");
	}
	var arry = str.split(',');
	for (var i = 0; i < arry.length; i++) {
		arry[i] = $.trim(arry[i]);
		if (arry[i].lastIndexOf(' ') != -1) {
			arry[i] = arry[i].substring(arry[i].lastIndexOf(' ') + 1, arry[i].length);
		}
	}
	return arry.toString();
}

/* sql中若有with等开头的情况下,找到第一个正确的select的位置 */
function getFirstSelectIndex(baseSql) {
	var firstWithIndex = baseSql.indexOf("with");
	var firstSelectIndex = baseSql.indexOf("select"); /* 如果(和)在str中出现的次数不相等 */
	while (firstWithIndex != -1 && firstWithIndex < firstSelectIndex && !isSameCnt(baseSql.substring(firstWithIndex, firstSelectIndex), "(", ")")) {
		firstSelectIndex = baseSql.indexOf("select", firstSelectIndex + 1);
		if (firstSelectIndex == -1) {
			return -1;
		}
	}
	return firstSelectIndex;
}

/* 判断两个字符a,b在一个字符串str中出现的次数是否相同 */
function isSameCnt(str, a, b) {
	var anum = getCharCnt(str, a);
	var bnum = getCharCnt(str, b);
	if (anum == bnum) {
		return true;
	}
	return false;
} 
/* 得到某个字符在某个字符串中的个数 */
function getCharCnt(str, targetStr) {
	var cnt = 0;
	for (var x = 0; x < str.length; x++) {
		if (str[x] == targetStr) {
			cnt++;
		}
	}
	return cnt;
}


/* bottom */
function imgInit(chartType) {
	require(['echarts', 'echarts/chart/line', 'echarts/chart/bar', 'echarts/chart/pie'],
	//回调函数
	function (ec) {
		var chartContainer = null;
		if (chartType == 'bar') {
			chartContainer = document.getElementById("barEchart");
			myChart = ec.init(chartContainer);
			myChart.setOption(barOption, true);
		} else if (chartType == 'pie') {
			chartContainer = document.getElementById("pieEchart");
			myChart = ec.init(chartContainer);
			myChart.setOption(pieOption, true);
		}
	} );
	//渲染ECharts图表

}

/* 图表回调函数 */

function DrawEChart(chartType, ec) { /* 图表渲染的容器对象 */
	var chartContainer = null;
	if (chartType == 'bar') {
		chartContainer = document.getElementById("barEchart");
		myChart = ec.init(chartContainer);
		myChart.setOption(barOption, true);
	} else if (chartType == 'pie') {
		chartContainer = document.getElementById("pieEchart");
		myChart = ec.init(chartContainer);
		//myChart.setOption(pieOption, true);
	}
}


function isJson(obj) {
	var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
	return isjson;
}

/* 迭代计算算法 */
function jscalculate(param) {
}

/* 基本计算处理 */
function calculate(param1, param2, rule) {
	var result = 0;
	switch (rule) {
	case "+":
		result = param1 + param2;
		break;
	case "-":
		result = param1 - param2;
		break;
	case "*":
		result = param1 * param2;
		break;
	case "/":
		result = param1 / param2;
		break;
	}
	return result;
}

/* 柱状图option */
var barOption = {
	title: {
		text: '图标标题',
		subtext: '图标解释'
	},
	tooltip: {
		trigger: 'axis'
	},
	legend: {
		data: ['结果', '金额', '笔数']
	},
	toolbox: {
		show: true,
		feature: {
			mark: {
				show: true
			},
			dataView: {
				show: true,
				readOnly: false
			},
			magicType: {
				show: true,
				type: ['line', 'bar']
			},
			restore: {
				show: true
			},
			saveAsImage: {
				show: true
			}
		}
	},
	calculable: true,
	xAxis: [{
		type: 'category',
		data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
	}, {
		type: 'category',
		axisLine: {
			show: false
		},
		axisTick: {
			show: false
		},
		axisLabel: {
			show: false
		},
		splitArea: {
			show: false
		},
		splitLine: {
			show: false
		},
		data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
	}],
	yAxis: [{
		type: 'value'
	}],
	series: []
};

var pieOption = {
	    title : {
	        text: '',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: '{a} <br/>{b} : {c} ({d}%)'
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'left',
	        data:[]
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            itemStyle : {
	                normal : {
	                    label : {
	                      formatter : function (params){
	                    	  return params.name + '(' + params.percent + '%)'
	                      },
	                      show: true,
	   					 position:'outer'
	                    },
	                    labelLine : {
	                        show : true
	                    }
	                }
	            },
	            data:[]
	        }
	    ]
	};
