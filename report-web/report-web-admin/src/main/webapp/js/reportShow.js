/******************************************************************************************************************************/
/* 共用toolFlag时的时间标志 */
var commonCon = "";
var showSqlId = "";
var resizeOption;
var effect = [ 'spin', 'bar', 'ring', 'whirling', 'dynamicLine', 'bubble' ];
var qid = "";
var myChart = null;
var oldChartList = [];
/* colModel */
var ecolumn = [];
/* colName */
var ccolumn = [];

var toolCColumn = [];
var toolEColumn = [];
var withRequire = false;
/* 汇总列 */
var toolGather = [];
/* 二级表头 */
var groupHeader = [];
var groupFlag = false;
/* 图形对象数组 */
var chartList = [];
var currentChartName = "";
var chartShowName = [];
var reportTitle = "";
var loadTitle = "";
var conData = [];
var formatCols = "";
var staticRowNum = "";
var staticSql = "";
var staticCcolumn = "";
/* 查看统计数据 */

function queryStatic () {
	if (staticRowNum != "" && staticSql != "" && staticCcolumn != "") {
		var staticCcolumns = staticCcolumn.split(",");
		var widthPer = 100/Math.ceil(staticCcolumns.length/staticRowNum);
		var staticData = [];
		/* 日，周时间格式没问题，月的时间需要转换 */
		for ( var i in conData) {
			if (conData[i].type == "input") {
				if (conData[i].conName.indexOf("开始时间") != -1 || conData[i].conName.indexOf("开始日期") != -1) {
					var nowDate = $("input[name='" + conData[i].name + "']").val();
					if (nowDate.length==7) {
						conData[i].value = nowDate + "-01"; 
					}
				} else if (conData[i].conName.indexOf("结束时间") != -1 || conData[i].conName.indexOf("结束日期") != -1) {
					var nowDate = $("input[name='" + conData[i].name + "']").val();
					if (nowDate.length==7) {
						var lastd = getCountDays(nowDate);
						conData[i].value = nowDate + "-" + lastd;
					}
				}
			}
		}
		$.ajax({
			url: "report/reportShowQueryData",
			type: "POST",
			data: {
				qid : staticSql,
				condition : JSON.stringify(conData),
				page:1,
				rows:100
			},
			dataType: "json",
			success: function(data) {
				if (data.total > 0) {
					staticData = data.rows[0];
				}
				if (staticData != []) {
					var staticDivHtml = "";
					for (var x in staticCcolumns) {
						var staticDataStr = staticData[x]==null?"":staticData[x].toString();
						
						if (isLittleNum(staticDataStr) && staticDataStr.indexOf("%") == -1) {
							staticDataStr = fmoney(staticDataStr, 2);
						} else if (!isLittleNum(staticDataStr) && staticDataStr.indexOf("%") == -1) {
							staticDataStr = fmoney(staticDataStr, 0);
						}
						
						staticDivHtml = staticDivHtml + "<div class='digest-block' style='width:" + widthPer + "%;'><h4>" + staticCcolumns[x] + "</h4><h1 style='font-size: 32px;'>" + staticDataStr + "</h1></div>";
					}
					$("#staticData").css("height", 69*staticRowNum+"px");
					$("#staticData").html(staticDivHtml);
					$("#staticDiv").show();
				}
			}
		});
	} else {
		$("#staticDiv").hide();
	}
}
/******************************************************************************************************************************/
/******************************************************************************************************************************/
/* 初始化 */
$(function() {
	/* 初始化展示区大小（展示图必须先初始化出一个区域） */
	$("#content").css("width", $(window).width() * 0.785);
	$("#content").css("height", $(window).height() * 1.3);
	/* main.jsp 菜单栏点击后改变css样式 */
	navgChange();
	/* 取reportFlag */
	var tempStr = file.substring(file.indexOf("?") + 1, file.length);
	var strs = tempStr.split("|");
	var flagStr = [];
	for (var i in strs) {
		if (strs[i].indexOf("reportFlag") != -1) {
			flagStr = strs[i].split("=");
		}
		if (strs[i].indexOf("conFlag") != -1) {
			var commonCons = strs[i].split("=");
			commonCon = commonCons[1];
		}
	} 
	/* 根据URL后面的报表标志从数据库中找到存储的public，condition，chart信息 */
	$.ajax({
		url: "report/queryAll",
		type: "POST",
		cache: false,
		data: { 
			reportFlag: flagStr[1],
			conFlag: commonCon
		},
		dataType: "json",
		success: function(data) {
			if (data.code == 0) {
				data = data.data; 
				/* 展示图形列表 */
				chartList = data.reportChartList == undefined ? [] : data.reportChartList;
				oldChartList = chartList;
				/* 多张图时展示第一张 */
				if (chartList.length > 0) {
					$(".particle").empty();
					for (var i = 0; i < chartList.length; i++) {
						if (i == 0) {
							$(".particle").append("<li class=\"on\" particle=\"-30\" default=\"on\">" + chartList[i].chartName + "</li>");
							currentChartName = chartList[i].chartName;
						} else {
							$(".particle").append("<li particle=\"-30\">" + chartList[i].chartName + "</li>");
						}
					}
				} else {
					$("#chartOption").hide();
				}
				/* jqGrid使用 */
				ecolumn = [];
				ccolumn = [];
				
				toolEColumn = data.reportPublic.toolEColumn;
				toolCColumn = data.reportPublic.toolCColumn;
				formatCols = data.reportPublic.toolFormat;
				toolGather = data.reportPublic.toolGather;
				loadTitle = data.reportPublic.toolCColumn;
				reportTitle = data.reportPublic.toolTitle;
				
				hsql = (data.reportPublic.toolHSqlId == undefined ? "" : data.reportPublic.toolHSqlId);
				dsql = (data.reportPublic.toolDSqlId == undefined ? "" : data.reportPublic.toolDSqlId);
				wsql = (data.reportPublic.toolWSqlId == undefined ? "" : data.reportPublic.toolWSqlId);
				msql = (data.reportPublic.toolMSqlId == undefined ? "" : data.reportPublic.toolMSqlId);
				qsql = (data.reportPublic.toolQSqlId == undefined ? "" : data.reportPublic.toolQSqlId);
				ysql = (data.reportPublic.toolYSqlId == undefined ? "" : data.reportPublic.toolYSqlId);
				
				toolEColumn = toolEColumn.split(","); 
				toolCColumn = toolCColumn.split(",");
				
				/* 格式化colModel */
				$.each(toolEColumn, function(n, value) {
					var obj = new Object();
					obj.name = value;
					obj.index = value;
					ecolumn.push(obj);
				});

				var ecindex = 0;
				for (var i = 0; i < toolCColumn.length; i++) {
					if (toolCColumn[i].indexOf(":") != -1 && toolCColumn[i].indexOf("{") != -1 && toolCColumn[i].indexOf("}") != -1) {
						groupFlag = true; /* 拼装groupHeader */
						var obj = new Object();
						obj.titleText = toolCColumn[i].substring(0, toolCColumn[i].indexOf(":"));
						obj.numberOfColumns = toolCColumn[i].substring(toolCColumn[i].indexOf("{"), toolCColumn[i].indexOf("}")).split("|").length;
						/* 拼装colNames */
						var tmpArr = [];
						tmpArr = toolCColumn[i].substring(
						toolCColumn[i].indexOf("{") + 1, toolCColumn[i].indexOf("}")).split("|");
						obj.startColumnName = ecolumn[ecindex].name;
						groupHeader.push(obj);
						ccolumn = $.merge(ccolumn, tmpArr);
						ecindex = ecindex + obj.numberOfColumns;
					} else {
						ccolumn.push(toolCColumn[i]);
						ecindex++;
					}
				}
				//0901新增，表格列宽度自动扩展
				if (ccolumn.join("").length * 20 > $(window).width() * 0.75) {
					withRequire = true;
				}
				$.each(ecolumn, function(n, value) {
					if (withRequire) {
						value.width = ccolumn[n].length * 20;
					} else {
						value.width = $(window).width() * 0.75 / ccolumn.length;
					}
				});

				topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
				
				/* 格式化数据 */
				var formatDatas = [];
				var toolFormat = data.reportPublic.toolFormat;
				if (toolFormat != undefined && toolFormat != "") {
					toolFormat = toolFormat.split(","); /* 金额:负数,笔数:百分比, */
					for (var k = 0; k < toolFormat.length; k++) {
						if (toolFormat[k] != "") {
							var tmpFmt = toolFormat[k].split(":");
							var formatSelect = tmpFmt[0];
							var columnType = tmpFmt[1];
							var obj = new Object();
							var fmtObj = new Object();
							if (columnType == "正整数") {
								obj.name = formatSelect;
								obj.formatter = "integer";
								fmtObj.thousandsSeparator = ",";
								fmtObj.defaultValue = "0";
								obj.formatoptions = fmtObj;
							} else if (columnType == "小数") {
								obj.name = formatSelect;
								obj.formatter = "number";
								fmtObj.decimalSeparator = ".";
								fmtObj.thousandsSeparator = ",";
								fmtObj.decimalPlaces = 2;
								fmtObj.defaultValue = "0.00";
								obj.formatoptions = fmtObj;
							} else if (columnType == "负数") {
								obj.name = formatSelect;
								obj.formatter = "currency";
								fmtObj.decimalSeparator = ".";
								fmtObj.thousandsSeparator = ",";
								fmtObj.decimalPlaces = "2";
								fmtObj.prefix = "-";
								fmtObj.defaultValue = "-0.00";
								obj.formatoptions = fmtObj;
							} else if (columnType == "百分比") {
								obj.name = formatSelect;
								obj.formatter = "currency";
								fmtObj.decimalSeparator = ".";
								fmtObj.thousandsSeparator = ",";
								fmtObj.decimalPlaces = "2";
								fmtObj.defaultValue = "0.00%";
								fmtObj.suffix = "%";
								obj.formatoptions = fmtObj;
							}
							formatDatas.push(obj);
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
									obj.width = ecolumn[j].width;
									obj.formatter = formatDatas[i].formatter;
									obj.formatoptions = formatDatas[i].formatoptions;
									ecolumn[j] = obj;
								}
							}
						}
					}

				}
				/* 拼条件 */
				var conList = data.reportConditionList;
				
				var maxRowNum = 0;
				/* 每个条件行占%的高度 */
				var headHeightPx = 3;
				/* 计算行数 */
				for (i in conList) {
					if (maxRowNum < conList[i].rowNum) {
						maxRowNum = conList[i].rowNum;
					}
				}
				/* 初始化每行的div */
				$("#head").css("height" , (maxRowNum+1) * headHeightPx + "%");
				/* 每个head行所占的高度百分比 */
				var conHeadHighPer = 100 / (maxRowNum+1);
				for (var m = 1; m <= maxRowNum+1; m++) {
					$("#head").append("<div id='head" + m + "' style='height:" + conHeadHighPer + "%;text-align: left; margin-top: 10px;'></div>");
				}
				
				for (i in conList) {
					var conValueArry = new Array();
					/* 若select和checkBox是sql则先查询该sql的结果 */
					if (conList[i].conOption == "select" || conList[i].conOption == "checkbox") {
						var conValue = conList[i].conMuti;
						if (conValue.indexOf("select") != -1) {
							$.ajax({
								type: "post",
								url: "report/getConValue",
								dataType: 'json',
								async: false,
								data: {
									selectSql: conValue,
									dataBaseSource : conList[i].dataBaseSource
								},
								success: function(data) {
									if (data.code == 0) {
										conValue = data.data;
									} else {
										jAlert(data.msg, '警告')
									}
								}
							});
						}
						conValue = replaceDot(conValue);
						conValueArry = conValue.split(',');
					}

					/* 显示页面条件 */
					if (conList[i].conOption == "input") {
						if (conList[i].display == "Y") {
							$("#head" + conList[i].rowNum).append(conList[i].conName + "<span style='margin-left: 10px;'></span><input type='text' class='input' name='" + conList[i].conWhere + "'/><span style='margin-left: 10px;'></span>");
						} else {
							$("#head").append("<input type='hidden'  name='" + conList[i].conWhere + "' value='"+ conList[i].value +"'/>");
						}
						
						if (conList[i].conType == "日期" && conList[i].display == "Y") {
							datePickInit($("input[name='" + conList[i].conWhere + "']"), conList[i].conName);
						}
						// input设置默认值，用select和checkbox值那一列
						if (conList[i].conMuti != undefined && conList[i].conMuti != "") {
							$("input[name='" + conList[i].conWhere + "']").val(conList[i].conMuti);
						}
						
						var obj = new Object();
						obj.name = conList[i].conWhere;
						obj.value = $("input[name='" + conList[i].conWhere + "']").val();
						obj.option = conList[i].conType;
						obj.conName = conList[i].conName;
						obj.type = "input";
						obj.conDefaultValue = conList[i].conDefaultValue;
						conData.push(obj);
					} else if (conList[i].conOption == "select") {
						if (conList[i].conType == "模糊查询") {
							$("#head" + conList[i].rowNum).append(conList[i].conName + "<span style='margin-left: 10px;'/><div class='dynamicDiv'><span style='margin-left: 10px;'></span><input type='text' style='width:200px;' name='input"+conList[i].conWhere+"'><ul class='dynamicUl' name='ul"+conList[i].conWhere+"'></ul></div>");
				        	var dynamicName = conValueArry;
				        	var conWhere = conList[i].conWhere;
				        	$("input[name='input" + conWhere + "']").attr("placeholder",dynamicName[0]);
				        	$("input[name='input" + conWhere + "']").focus(function() {
				        		if ($("input[name='input" + conWhere + "']").val() == "" && $("ul[name='ul" + conWhere + "'] li").size() == 0) {
				        			for(var i=0;i<dynamicName.length;i++){
				        				if (dynamicName[i].length >= 12) {
				        					$("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('"+dynamicName[i]+"','"+ conWhere +"')\">"+dynamicName[i].substring(0,10)+"...</a></li>");	
				        				} else
				        					$("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('"+dynamicName[i]+"','"+ conWhere +"')\">"+dynamicName[i]+"</a></li>");
				        			}
				        		}
				        		$("ul[name='ul" + conWhere + "']").show();
				        	});
				        	
				        	$("input[name='input" + conWhere + "']").bind('input propertychange', function() {
				        		var getDynamicName = $("input[name='input" + conWhere + "']").val(); 
				        		$("ul[name='ul" + conWhere + "']").html("");
				        		for(var i=0;i<dynamicName.length;i++){
				        			if (dynamicName[i] != null && dynamicName[i].indexOf(getDynamicName) > -1) {
				        				if (dynamicName[i].length >= 12) {
				        					$("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('"+dynamicName[i]+"','"+ conWhere +"')\">"+dynamicName[i].substring(0,10)+"...</a></li>");
				        				} else 
				        					$("ul[name='ul" + conWhere + "']").append("<li><a href=\"#\" style=\"color:black\" onclick=\"inDynamicName('"+dynamicName[i]+"','"+ conWhere +"')\">"+dynamicName[i]+"</a></li>");
				        			}
				        		}
				        	});
				        	var obj = new Object();
							obj.name = conList[i].conWhere;
							obj.option = "模糊查询";
							obj.conName = conList[i].conName;
							obj.value = $("input[name='" + conList[i].conWhere + "']").val();
							obj.type = "select";
							conData.push(obj);
						} else {
							$("#head" + conList[i].rowNum).append(
									conList[i].conName + "<span style='margin-left: 10px;'></span><select id='conValueSelect" + i + "' name='" + conList[i].conWhere + "'></select>");
									$.each(conValueArry, function(n, value) {
										$("#conValueSelect" + i).append("<option value='" + value + "'>" + value + "</option>");
									});
									var obj = new Object();
									obj.name = conList[i].conWhere;
									obj.option = "文本";
									obj.conName = conList[i].conName;
									obj.value = $("select[name='" + conList[i].conWhere + "'] :selected").val();
									obj.type = "select";
									conData.push(obj);
						}
						
					} else if (conList[i].conOption == "checkbox") {
						$("#head" + conList[i].rowNum).append(
						conList[i].conName);
						$.each(
						conValueArry, function(n, value) {
							$("#head" + conList[i].rowNum).append("<span style='margin-left: 10px;'></span><input type='checkbox' name='" + conList[i].conWhere + "' value='" + value + "'>" + value + "</input>");
						});
						var chk_value_show = "";
						$("input[name='" + conList[i].conWhere + "']:checked").each(

						function() {
							chk_value_show = chk_value_show + $(this).val() + ",";
						});
						var obj = new Object();
						obj.name = conList[i].conWhere;
						obj.conName = conList[i].conName;
						obj.option = "文本";
						obj.value = chk_value_show;
						obj.type = "checkbox";

						obj.checkboxName = conList[i].conName;
						conData.push(obj);
					}
				}
				var conHeadNum = maxRowNum+1;
				$("#head" + conHeadNum).append("<a href='#' class='constr borders' onclick='look()'>查看</a><span style='margin-left: 10px;'></span>");
				$("#head" + conHeadNum).append("<a href='#' class='constr borders' onclick='loadReport()'>导出Excel</a>");
				/* 拼图 */
				imgInit();
				time();
				/* 合计列0815新增 */
				staticRowNum = (data.reportPublic.staticRowNum == undefined ? "" : data.reportPublic.staticRowNum);
				staticSql = (data.reportPublic.staticSql == undefined ? "" : data.reportPublic.staticSql);
				staticCcolumn = (data.reportPublic.staticCcolumn == undefined ? "" : data.reportPublic.staticCcolumn);
				if (staticRowNum != "" && staticSql != "" && staticCcolumn != "") {
					var staticCcolumns = staticCcolumn.split(",");
					var widthPer = 100/Math.ceil(staticCcolumns.length/staticRowNum);
					var staticDivHtml = "";
					for (var x in staticCcolumns) {
						staticDivHtml = staticDivHtml + "<div class='digest-block' style='width:" + widthPer + "%;'><h4>" + staticCcolumns[x] + "</h4><h1 style='font-size: 32px;'></h1></div>";
					}
					$("#staticData").css("height", 69*staticRowNum+"px");
					$("#staticData").html(staticDivHtml);
					$("#staticDiv").show();
				} else {
					$("#staticDiv").hide();
				} 
			} else {
				$("#mainContainer").html("请求错误");
			}
		}
	});
});


/* 查看报表 */
function look() {
	var mapObjs = [];
	var begintime = 1;
	var endtime = 1;
	for ( var i in conData) {
		if (conData[i].type == "input") {
			if (conData[i].conName.indexOf("开始时间") != -1 || conData[i].conName.indexOf("开始日期") != -1) {
				begintime = $("input[name='" + conData[i].name + "']")
						.val();
				begintime = begintime.replace(/[-]/g, "");
			} else if (conData[i].conName.indexOf("结束时间") != -1 || conData[i].conName.indexOf("结束日期") != -1) {
				endtime = $("input[name='" + conData[i].name + "']").val();
				endtime = endtime.replace(/[-]/g, "");
			}
		}
	}
	if ((begintime != 1 && endtime != 1 && begintime > endtime)
			|| begintime == '' || endtime == '') {
		jAlert('请选择正确的时间！', '警告');
		return;
	}
	for ( var i in conData) {
		if (conData[i].type == "input") {
			conData[i].value = $("input[name='" + conData[i].name + "']").val();

			/* 判断时间是否超过限制 */
			if (conData[i].conDefaultValue != null
					&& conData[i].conDefaultValue != "") {

				if (objIsInArr(conData[i].conDefaultValue, mapObjs)) {
					/* 时间差 */
					var timeC = (getObjInArr(conData[i].conDefaultValue,
							mapObjs) - parseISO8601(conData[i].value))
							/ (24 * 60 * 60 * 1000);
					if (timeC < 0) {
						timeC = -timeC;
					}
					timeC++;
					var dftDatas = conData[i].conDefaultValue.split(",");

					if (timeC < dftDatas[0]) {
						jAlert('请选择合理的查询时间范围（' + dftDatas[0] + '至'
								+ dftDatas[1] + '天）！', '警告');
						return;
					}

					if (timeC > dftDatas[1]) {
						jAlert('请选择合理的查询时间范围（' + dftDatas[0] + '至'
								+ dftDatas[1] + '天）！', '警告');
						return;
					}
				} else {
					var mapObj = new Object();
					mapObj.name = conData[i].conDefaultValue;
					mapObj.value = parseISO8601(conData[i].value);
					mapObjs.push(mapObj);
				}
			}

		} else if (conData[i].type == "select") {
			if (conData[i].option=='模糊查询') {
				if ($("input[name='input" + conData[i].name + "']").val()=="") {
					conData[i].value = $("input[name='input" + conData[i].name + "']").attr("placeholder");
				} else {
					conData[i].value = $("input[name='input" + conData[i].name + "']").val();
				}
			} else {
				conData[i].value = $("select[name='" + conData[i].name + "'] :selected").val();
			}
		} else if (conData[i].type == "checkbox") {
			var chk_value_show = "";
			$("input[name='" + conData[i].name + "']:checked").each(
					function() {
						chk_value_show = chk_value_show + $(this).val()
								+ ",";
					});

			if (chk_value_show == "") {
				jAlert("请勾选要查询的" + conData[i].checkboxName + "！", "警告");
				return;
			}
			conData[i].value = chk_value_show;
		}
	}
	myChart.showLoading({
		text : '数据查询中',
		effect : effect[3],
		textStyle : {
			fontSize : 20
		}
	});
	$("#list").jqGrid('GridUnload');//重新构造
	
	initJqGrid(toolCColumn, toolEColumn, queryStatic);
}

/* 导出报表 */
function loadReport() {
	var mapObjs = [];
	var begintime = 1;
	var endtime = 1;
	for ( var i in conData) {
		if (conData[i].type == "input") {
			if (conData[i].conName.indexOf("开始时间") != -1 || conData[i].conName.indexOf("开始日期") != -1) {
				begintime = $("input[name='" + conData[i].name + "']")
						.val();
				begintime = begintime.replace(/[-]/g, "");
			} else if (conData[i].conName.indexOf("结束时间") != -1 || conData[i].conName.indexOf("结束日期") != -1) {
				endtime = $("input[name='" + conData[i].name + "']").val();
				endtime = endtime.replace(/[-]/g, "");
			}
		}
	}
	if ((begintime != 1 && endtime != 1 && begintime > endtime)
			|| begintime == '' || endtime == '') {
		jAlert('请选择正确的时间！', '警告');
		return;
	}
	for ( var i in conData) {
		if (conData[i].type == 'input') {
			conData[i].value = $("input[name='" + conData[i].name + "']")
					.val();
			/* 判断时间是否超过限制 */
			if (conData[i].conDefaultValue != null
					&& conData[i].conDefaultValue != "") {
				if (conData[i].value == "") {
					jAlert('请选择要查询的时间范围！', '警告');
					return;
				}
				if (objIsInArr(conData[i].conDefaultValue, mapObjs)) {
					/* 时间差 */
					var timeC = (getObjInArr(conData[i].conDefaultValue,
							mapObjs) - parseISO8601(conData[i].value))
							/ (24 * 60 * 60 * 1000);
					if (timeC < 0) {
						timeC = -timeC;
					}
					timeC++;
					var dftDatas = conData[i].conDefaultValue.split(",");

					if (timeC < dftDatas[0]) {
						jAlert('请选择合理的查询时间范围（' + dftDatas[0] + '至'
								+ dftDatas[1] + '天）！', '警告');
						return;
					}

					if (timeC > dftDatas[1]) {
						jAlert('请选择合理的查询时间范围（' + dftDatas[0] + '至'
								+ dftDatas[1] + '天）！', '警告');
						return;
					}
				} else {
					var mapObj = new Object();
					mapObj.name = conData[i].conDefaultValue;
					mapObj.value = parseISO8601(conData[i].value);
					mapObjs.push(mapObj);
				}
			}

		} else if (conData[i].type == 'select') {
			if (conData[i].option=='模糊查询') {
				if ($("input[name='input" + conData[i].name + "']").val()=="") {
					conData[i].value = $("input[name='input" + conData[i].name + "']").attr("placeholder");
				} else {
					conData[i].value = $("input[name='input" + conData[i].name + "']").val();
				}
			} else {
				conData[i].value = $("select[name='" + conData[i].name + "'] :selected").val();
			}
		} else if (conData[i].type == 'checkbox') {
			var chk_value_show = "";
			$("input[name='" + conData[i].name + "']:checked").each(
					function() {
						chk_value_show = chk_value_show + $(this).val()
								+ ",";
					});
			if (chk_value_show == "") {
				jAlert("请勾选要查询的" + conData[i].checkboxName + "！", "警告");
				return;
			}
			conData[i].value = chk_value_show;
		}
	}
	var title = loadTitle;
	var fileName = reportTitle;
	var conditions = JSON.stringify(conData);

	$("#title").val(title);
	$("#fileName").val(fileName);
	$("#qid").val(qid);
	$("#condition").val(conditions);
	$("#formatCols").val(formatCols);
	$("#loadFormId").submit();
}


/* 查询表格数据 */
function initJqGrid(toolCColumn, toolEColumn, callback) {
	chartList = objClone(oldChartList);
	jQuery("#list").jqGrid(
					{
						url : 'report/reportShowQueryData',
						datatype : "json",
						mtype : 'post',
						ajaxGridOptions:{timeout : 5 * 60 * 1000},
						colNames : ccolumn,
						colModel : ecolumn,
						height : $(window).height() * 0.25,
						width : $(window).width() * 0.75,
						shrinkToFit : false,
						autowidth : true,
						postData : {
							qid : qid,
							condition : JSON.stringify(conData)
						},
						rowNum : 200,
						rowList : [ 20, 30, 50 ,100, 200 ],
						pager : '#pager',
						sortname : '',
						viewrecords : true,
						sortorder : "",
						caption : reportTitle,
						footerrow : true,
						userDataOnFooter : true,
						altRows : true,
						loadError : function(xhr, status, error) {
							myChart.hideLoading();
							myChart.clear();
							jAlert("该报表出现问题,已记录,我们会尽快修复", "提示");
						},
						loadComplete : function() {
							myChart.hideLoading();
							var re_records = $("#list").getGridParam('records');
							if (re_records != null && re_records != "") {
								reconstruct();
								if (chartList.length > 0) {
									showChart(currentChartName);
								}
								/* 汇总功能 */
								if (toolGather != null && toolGather != "") {
									var gathers = toolGather.split(",");
									var gatheJson = "";
									if (gathers.length > 0) {
										for (var j = 0; j < gathers.length; j++) {
											for (var k = 0; k < ccolumn.length; k++) {
												if (ccolumn[k] == gathers[j]) {
													var gatherJsonKey = ecolumn[k].name;
													var dataList = jQuery("#list").jqGrid('getCol', gatherJsonKey);
													gatheJson = gatheJson
															+ gatherJsonKey
															+ ":"
															+ getTotleNum(dataList).toFixed(2)
															+ ",";
												} else if (gathers[j].indexOf("|") != -1) {
													sumColumns = gathers[j]
															.split("|");
													if (ccolumn[k] == sumColumns[0]) {
														var gatherJsonKey = ecolumn[k].name;
														gatheJson = gatheJson
																+ gatherJsonKey
																+ ":'"
																+ sumColumns[1]
																+ "',";
													}
												}
											}
										}
										gatheJson = "{" + gatheJson.substring(0, gatheJson.length - 1) + "}";
										jQuery("#list").jqGrid('footerData', 'set', eval('('+ gatheJson+ ')'));
									}
								}
							} else {
								myChart.clear();
							}
							// 执行成功后，执行回调函数（查统计数）
							callback();
						},
						gridComplete : function() {

						}
					});
	/* 页数显示 */
	jQuery("#list").jqGrid('navGrid', '#pager', {
		edit : false,
		add : false,
		del : false
	}, {}, {}, {}, {
		multipleSearch : true,
		multipleGroup : false
	});
	
	jQuery("#list").jqGrid('gridResize',{minWidth : $(window).width() * 0.75, maxWidth : $(window).width() * 0.75, minHeight : $(window).height() * 0.25, maxHeight : $(window).height() * 3});
	/* 二级表头 */
	if (groupFlag) {
		jQuery("#list").jqGrid('setGroupHeaders', {
			useColSpanStyle : true,
			groupHeaders : groupHeader
		});
	}
}

/* 展现图表 */
function showChart(chartName) {
	if (chartList.length > 0) {
		for (var i = 0; i < chartList.length; i++) {
			/*展示选中的图*/
			if (chartList[i].chartName == chartName) {
				if (chartList[i].chartType == 'bar') {
					var columnVsLegend = chartList[i].dataVsLe;
					var columnVsX = chartList[i].dataVsX;
					var option = chartList[i].chartOption;
					if (option.length > 0) {
						if (!isJson(option)) {
							option = eval('(' + option + ')');
						}
					}
					/* e.g result:结果,sumAmt:金额,sumcnt:笔数 */
					var columnVsLegends = columnVsLegend.split(",");
					/* option.series赋值  */
					$.each(columnVsLegends, function(n, value) {
						var strs = value.split(":");
						$.each(option.series, function(n, value) {
							if (value.name == strs[1]) {
								var tempArr = jQuery("#list").jqGrid('getCol', strs[0]).reverse();
								for (var i = 0; i < tempArr.length; i++) {
									tempArr[i] = Number(tempArr[i]);
								}
								value.data = tempArr;
							}
						});
					});

					/* tx_date:日期   tx_date:日期|tran_type */
					var xdatas = columnVsX.split(":");
					
					if (option.xAxis.length == 1) {
						option.xAxis[0].data = jQuery("#list").jqGrid(
								'getCol', xdatas[0]).reverse();
					} else if (option.xAxis.length == 2) {
						option.xAxis[0].data = jQuery("#list").jqGrid(
								'getCol', xdatas[0]).reverse();
						option.xAxis[1].data = jQuery("#list").jqGrid(
								'getCol', xdatas[0]).reverse();
					}
					resizeOption = option;
					myChart.setOption(option, true);
				} else if (chartList[i].chartType == 'pie') {
					var columnVsLegend = chartList[i].dataVsLe;
					var columnVsX = chartList[i].dataVsX;
					var option = chartList[i].chartOption;
					var showRowNum = chartList[i].showRowNum;
					if (option.length > 0) {
						if (!isJson(option)) {
							option = eval('(' + option + ')');
						}
					}
					var pieKey = jQuery("#list").jqGrid('getCol', columnVsX);
					
					var pieValue = jQuery("#list").jqGrid('getCol', columnVsLegend);
					option.legend.data = pieKey;
					var pieSeriesData = [];
					var otherSumName = "其他";
					var otherSumValue = 0;
					var otherShow = false;
					for (var x in pieKey) {
						if (x < showRowNum * 1) {
							var obj = new Object();
							obj.name = pieKey[x];
							obj.value = pieValue[x];
							pieSeriesData.push(obj);
						} else {
							otherShow = true;
							otherSumValue += pieValue[x] * 1;
						}
					}
					if (pieKey.length > showRowNum * 1) {
						option.legend.data.push('其他');
					}
					if (otherShow) {
						pieSeriesData.push({name: otherSumName, value: otherSumValue});
					}
					option.series[0].data = pieSeriesData;
					myChart.setOption(option, true);
				}
			}
		}
	}
}
/******************************************************************************************************************************/
/******************************************************************************************************************************/

/* 为日期选择相应维度控件 */
function datePickInit (conNode, conName) {
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

/* 替换中文逗号 */
function replaceDot(str) {
	var oldValue = str;
	while (oldValue.indexOf("，") != -1) {
		str = oldValue.replace("，", ",");
		oldValue = str;
	}
	return oldValue;
}
/* 克隆 */
function objClone(obj) {
	var newobj, str;
	if (typeof obj !== 'object') {
		return;
	} else if (window.JSON) {
		str = JSON.stringify(obj), //系列化对象
		newobj = JSON.parse(str); //还原
	} else {
		for ( var i in obj) {
			newobj[i] = typeof obj[i] === 'object' ? cloneObj(obj[i])
					: obj[i];
		}
	}
	return newobj;
};

/* 判断该对象是否在数组中，模仿map,ie8不支持map */
function objIsInArr(mapObj, mapObjs) {
	for (var i = 0; i < mapObjs.length; i++) {
		if (mapObjs[i].name == mapObj) {
			return true;
		}
	}
	return false;
}
/* 根据name得到该对象的value,模仿map[name],ie8不支持map */
function getObjInArr(name, mapObjs) {
	for (var i = 0; i < mapObjs.length; i++) {
		if (mapObjs[i].name == name) {
			return mapObjs[i].value;
		}
	}
	return null;
}

/* 兼容ie8 new Date('yyyy-mm-dd')*/
function parseISO8601(dateStringInRange) {
	var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/, date = new Date(NaN), month, parts = isoExp
			.exec(dateStringInRange);
	if (parts) {
		month = +parts[2];
		date.setFullYear(parts[1], month - 1, parts[3]);
		if (month != date.getMonth() + 1) {
			date.setTime(NaN);
		}
	}
	return date;
}



/* echarts图表初始化 */
function imgInit() {
	require.config({
				paths : {
					'echarts' : 'js/echarts', //echarts.js的路径
					'echarts/chart/line' : 'js/echarts', //echarts.js的路径
					'echarts/chart/bar' : 'js/echarts',
					'echarts/chart/pie' : 'js/echarts'
				}
			});
	require([ 'echarts', 'echarts/chart/line', 'echarts/chart/bar', 'echarts/chart/pie'],
			DrawEChart);
}

/* 图表回调函数 */
function DrawEChart(ec) {
	/* 图表渲染的容器对象 */
	var chartContainer = document.getElementById("echart");
	/* 加载图表 */
	myChart = ec.init(chartContainer);
	//myChart.setOption(option);
	/* 动态添加默认不显示的数据 */
	
}
/* 判断是否为Json格式 */
function isJson(obj) {
	var isjson = typeof (obj) == "object"
			&& Object.prototype.toString.call(obj).toLowerCase() == "[object object]"
			&& !obj.length;
	return isjson;
}
/* 多图时点击图片名称的事件 */
$('#chartOption').on('click', '.particle li', function() {
	$('.particle li').removeClass('on');
	$(this).addClass('on');
	currentChartName = $(this).text();
	showChart($(this).text());
});



/* 判断一个字符串在该数组最后一次出现的位置,兼容ie8.不能用lastIndexOf */
function lastIndex(str, list) {
	var lastIndexNum = -1;
	for (var i = 0; i < list.length; i++) {
		if (list[i] == str) {
			lastIndexNum = i;
		}
	}
	return lastIndexNum;
}
/* 时间维度没有对应数据源时的样式 */
function topNavANotHave (nav,arg1) {
	$('#'+ nav +'Nav a').replaceWith("<p> <span class= \"icon icons1 \"></span> <span>" + arg1 + "</span> <span class=\"bot\"></span></p>");
    $('#'+ nav +'Nav span').css("color","gray");
}
/* 时间维度有对应数据源时的样式 */
function topNavAHave (nav,arg1,arg2) {
	$('#'+ nav +'Nav p').replaceWith("<a href=\"javascript:\" time=\""+arg1+"\"> <span class=\"icon icons1\"></span> <span>"+arg2+"</span> <span class=\"bot\"></span> </a>");
    $('#'+ nav +'Nav span').css("color","black");
}
/* 根据是否包含各维度的SQLID来控制各维度按钮的css样式, 同时展示展示最左侧拥有的时间维度为已选择样式 */
function topNavInit (hsql,dsql,wsql,msql,qsql,ysql) {
	if (ysql == "") {
		topNavANotHave ('y','按年');
	} else {
		topNavAHave('y','年','按年');
		showSqlId = "y";
	}
	
	if (qsql == "") {
		topNavANotHave ('q','按季');
	} else {
		topNavAHave('q','季','按季');
		showSqlId = "q";
	}
	
	if (msql == "") {
		topNavANotHave ('m','按月');
	} else {
		topNavAHave('m','月','按月');
		showSqlId = "m";
	}
	
	if (wsql == "") {
		topNavANotHave ('w','按周');
	} else {
		topNavAHave('w','周','按周');
		showSqlId = "w";
	}
	
	if (dsql == "") {
		topNavANotHave ('d','按日');
	} else {
		topNavAHave('d','日','按日');
		showSqlId = "d";
	}
	
	if (hsql == "") {
		topNavANotHave ('h','按小时');
	} else {
		topNavAHave('h','时','按小时');
		showSqlId = "h";
	}
	if ($(".currentItem p").size() > 0) {
		$('.currentItem').removeClass('currentItem');
		$("#" + showSqlId + "Nav").addClass("currentItem");
	}
	/* main.jsp 绑定时间维度的点击事件，点击后查询报表 */
	topNavAClick();
}

/* 根据当前时间维度和拥有的数据源来判断当前页面是否要展示，如果有时间插件，根据时间维度切换时间插件的维度 */
function time() {

	for (var i = 0; i < conData.length; i++) {
		if (conData[i].type == "input" && conData[i].option == "日期") {
			datePickInit($("input[name='" + conData[i].name + "']"),
					conData[i].conName);
		}
	}
	var time = $('.currentItem a').attr('time');

	if (time == '时') {
		if (hsql != "") {
			$('#mainContainer').show();
			qid = hsql;
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
	if (time == '周') {
		if (wsql != "") {
			$('#mainContainer').show();
			qid = wsql;
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
	if (time == '季') {
		if (qsql != "") {
			$('#mainContainer').show();
			qid = qsql;
		} else {
			$('#mainContainer').hide();
		}
	}
	if (time == '年') {
		if (ysql != "") {
			$('#mainContainer').show();
			qid = ysql;
		} else {
			$('#mainContainer').hide();
		}
	}
}

function showChangeReport(file) {
	if (file.indexOf('tpl/tool/smartReportShow.jsp') != -1) {
		time();
		if (doLast()) {
			return;
		}
		look();
	}
}
/* 如果开始和结束时间都没有选择的时候，切换选项卡不执行查询 */
function doLast() {
	var begintime = 1;
	var endtime = 1;
	for ( var i in conData) {
		if (conData[i].type == "input") {
			if (conData[i].conName.indexOf("开始时间") != -1 || conData[i].conName.indexOf("开始日期") != -1) {
				begintime = $("input[name='" + conData[i].name + "']").val();
				begintime = begintime.replace(/[-]/g, "");
			} else if (conData[i].conName.indexOf("结束时间") != -1 || conData[i].conName.indexOf("结束日期") != -1) {
				endtime = $("input[name='" + conData[i].name + "']").val();
				endtime = endtime.replace(/[-]/g, "");
			}
		}
	}
	if (begintime == '' && endtime == '') {
		return 1;
	}
	return 0;
}
function changeLine(selected) {

}

/* 判断是否需要重新构造 */
function reconstruct() {
	for (var i = 0; i < chartList.length; i++) {
		if (chartList[i].chartName == currentChartName) {
			var columnVsLegend = chartList[i].dataVsLe;
			var columnVsX = chartList[i].dataVsX;
			var option = chartList[i].chartOption;
			if (option.length > 0) {
				if (!isJson(option)) {
					option = eval('(' + option + ')');
				}
			}

			/* 去重复得到需要重新构造的列数据,显示在图形界面上 */
			var reColumns = columnVsX.split("|");
			var reconstructColumn = jQuery("#list").jqGrid('getCol',
					reColumns[1]);
			reconstructColumn = arrUnique(reconstructColumn);

			if (chartList.length > 0) {
				$(".particle").empty();
				for (var i = 0; i < chartList.length; i++) {
					if (i == 0) {
						$(".particle").append(
								"<li class=\"on\" particle=\"-30\" default=\"on\">"
										+ chartList[i].chartName + "</li>");
						currentChartName = chartList[i].chartName;
					} else {
						$(".particle").append(
								"<li particle=\"-30\">"
										+ chartList[i].chartName + "</li>");
					}
				}
			}

			for (var i = 0; i < reconstructColumn.length; i++) {
				var newOption = new Object();
				for ( var n in chartList[i]) {
					newOption[n] = chartList[i][n];
				}
				$(".particle").append(
						"<li particle=\"-30\">" + reconstructColumn[i]
								+ "</li>");
				newOption.chartName = reconstructColumn[i];
				/* 组装option的数据,显示图形名称列表,除了ChartName不一样,其余都和之前一样,数据在显示图形的时候组装*/
				if ($.inArray(newOption, chartList) == -1) {
					chartList.push(newOption);
				}
			}
		}
	}
}
/* 数组去重 */
function arrUnique(arr) {
	var res = [], hash = {};
	for (var i = 0, elem; (elem = arr[i]) != null; i++) {
		if (!hash[elem]) {
			res.push(elem);
			hash[elem] = true;
		}
	}
	return res;
}

/* 取数组的和 */
function getTotleNum(list) {
	var sum = 0;
	for (var i = 0; i < list.length; i++) {
		sum = sum + list[i] * 1;
	}
	return sum;
}
/******************************************************************************************************************************/
/* 窗口自适应 */
$(window).resize(function() {
	$("#content").css("width", $(window).width() * 0.785);
	myChart.resize();
	$("#list").setGridWidth($(window).width() * 0.75);
});
