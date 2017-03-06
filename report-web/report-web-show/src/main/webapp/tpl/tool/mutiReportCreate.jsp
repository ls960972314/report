<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
</style>
<body>
	<div id='create_model_panel' style="padding-left:10%;width:100%;padding-bottom:100px;">
		<label style="">报告名称</label>
		<div class='dynamicDiv'>
			<span style='margin-left: 10px;'></span>
			<input type='text' style='width:200px;' id="updateBgName">
			<ul class='dynamicUl' name='ulUpdateReport'></ul>
		</div>
		<span style="margin-left:443px;"></span>
		<input type="button" value="新增" onclick="createPanelShow()"/>
		<input type="button" value="修改" onclick="updatePanelShow()"/>
		<input type="button" value="删除" onclick="deletePanelShow()"/>
		<hr style="margin-left: 0px;margin-top:20px;width:800px;height:1px;border:none;border-top:1px solid gray;" />
		
		<div id="operatorPanel" style="display:none;">
			<div style="margin-top:20px;"></div>
			<label style="">报告名称</label>
			<input type="text" id="bgName" style="margin-left:10px;"/>
			<span style="margin-left:588px;"></span>
			<input type="button" id="saveModel" value="保存" onclick="saveModel()"/>
			<input type="button" id="deleteModel" value="删除" onclick="deleteModel()"/>
			
			<div style="margin-top:20px;"></div>
			<label style="">报告标题</label>
			<input type="text" id="bgTitle" style="margin-left:10px;"/>
			
			<div style="margin-top:20px;"></div>
			<label style="">查询条件</label>
			<div style="margin-top:10px;"></div>
			<input type="checkbox" name="modelCon" id="begintimeCon" value="开始时间"/>开始时间
			<input type="checkbox" name="modelCon" id="endtimeCon" value="结束时间"/>结束时间
			
			<div style="margin-top:20px;"></div>
			<label style="">邮箱地址</label>
			<div style="margin-top:5px;"></div>
			<textarea id="emailAddrs" style="width:800px;height:100px;"></textarea>
			
			
			<hr style="margin-left: 0px;margin-top:20px;width:800px;height:1px;border:none;border-top:1px solid gray;" />
			
			<div style="margin-top:20px;"></div>
			
			
			<label style="">报告主题</label>
			<input id="rptTitle" style="margin-left:10px;width:722px;"/>
			<div style="margin-top:20px;"></div>
			<label style="">报告内容</label>
			<div style="margin-top:5px;"></div>
			<textarea id="rptContent" placeholder="报表文字说明" style="width:800px;height:100px;"></textarea>
			
			<div style="margin-top:20px;"></div>
			
			<div style="height:30px;">
				<label style="">报表名称</label>
				<span style='margin-left: 10px;'></span>
				<div class='dynamicDiv'>
					<span style='margin-left: 10px;'></span>
					<input type='text' style='width:200px;' id="reportName">
					<ul class='dynamicUl' name='ulAddReport'></ul>
				</div>
				<input type="radio" name="rptTime" value="d"/>日报
				<input type="radio" name="rptTime" value="w"/>周报
				<input type="radio" name="rptTime" value="m"/>月报
				<span style="margin-left:50px;"></span>
				<input type="checkbox" name="tableShow" value="true"/>显示表
				<input type="checkbox" name="chartShow" value="true"/>显示图
				<span style="width:200px; display:-moz-inline-box; display:inline-block;"><select id="chartList" style="display:none;"></select></span>
				<input type="button" id="addReport" value="添加报表" onclick="addReport()"/>
			</div>
			<div id="rptShowPanel">
			</div>			
		</div>
	</div>
</body>

<script type="text/javascript">
var reportFlag = "";
var reportConFlag = "";
var modelRptList = [];
var modelList = [];
var modelId = "";
var saveOrUpdate = "save";

// 初始化
$(function() {
	$("input[name='chartShow']").click(function () {
		chooseChart();
	});
	
	$.ajax({
		url: "reportModel/queryModelList",
		type: "POST",
		data: {
			flag : 'report'
		},
		dataType: "json",
		async: false,
		success: function(data) {
			if (data.code == '0') {
				modelList = eval('(' + data.data + ')');
			}
		}
	});
	// 报告名称下拉框updateBgName
	if (modelList.length > 0) {
		$("#updateBgName").attr("placeholder",modelList[0].modelName);
		$("#updateBgName").focus(function(){
			if ($("#updateBgName").val() == "") {
				$("ul[name='ulUpdateReport']").html("");
				for(var i=0; i<modelList.length; i++) {
					if (modelList[i].modelName.length >= 22) {
						$("ul[name='ulUpdateReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showupdateBgName('" + modelList[i].id + "','"+ modelList[i].modelName +"')\" >" + modelList[i].modelName.substring(0,20) + "...</a></li>");	
					} else
						$("ul[name='ulUpdateReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showupdateBgName('" + modelList[i].id + "','"+ modelList[i].modelName +"')\">" + modelList[i].modelName + "</a></li>");
				}
			}
			$("ul[name='ulUpdateReport']").show();
		});
		
		$("#updateBgName").bind('input propertychange', function() {
			var getreportList = $("#updateBgName").val(); 
			$("ul[name='ulUpdateReport']").html("");
			for(var i=0; i<modelList.length; i++) {
				if (modelList[i].modelName.length >= 22) {
					$("ul[name='ulUpdateReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showupdateBgName('" + modelList[i].id + "','"+ modelList[i].modelName +"')\" >" + modelList[i].modelName.substring(0,20) + "...</a></li>");	
				} else
					$("ul[name='ulUpdateReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showupdateBgName('" + modelList[i].id + "','"+ modelList[i].modelName +"')\">" + modelList[i].modelName + "</a></li>");
			}
		});
	}
	
	
	// 报表名称下拉框
	$("#reportName").attr("placeholder",reportList[0]);
	$("#reportName").focus(function(){
		if ($("#reportName").val() == "") {
			$("ul[name='ulAddReport']").html("");
			for(var i=0;i<reportList.length;i++){
				if (reportList[i].length >= 12) {
					$("ul[name='ulAddReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showReportName('" + reportList[i] + "','"+ resourceList[i] +"','" + rptConList[i] + "')\" >"+reportList[i].substring(0,10)+"...</a></li>");	
				} else
					$("ul[name='ulAddReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showReportName('" + reportList[i] + "','"+ resourceList[i] +"','" + rptConList[i] + "')\">"+reportList[i]+"</a></li>");
			}
		}
		$("ul[name='ulAddReport']").show();
	});
	
	$("#reportName").bind('input propertychange', function() {
		var getreportList = $("#reportName").val(); 
		$("ul[name='ulAddReport']").html("");
		for(var i=0;i<reportList.length;i++){
			if (reportList[i] != null && reportList[i].indexOf(getreportList) > -1) {
				if (reportList[i].length >= 12) {
					$("ul[name='ulAddReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showReportName('" + reportList[i] + "','"+ resourceList[i] +"','" + rptConList[i] + "')\">"+reportList[i].substring(0,10)+"...</a></li>");
				} else 
					$("ul[name='ulAddReport']").append("<li><a href=\"###\" style=\"color:black\" onclick=\"showReportName('" + reportList[i] + "','"+ resourceList[i] +"','" + rptConList[i] + "')\">"+reportList[i]+"</a></li>");
			}
		}
	});
});

function showupdateBgName (id, name) {
	$("#updateBgName").val(name);
	modelId = id;
	$("ul[name='ulUpdateReport']").hide();
}
// 展示选择的报表名称
function showReportName(name, rptFlag, conFlag) {
	reportFlag = rptFlag;
	reportConFlag = conFlag;
	$("#reportName").val(name);
	$("ul[name='ulAddReport']").hide();
	chooseChart();
}
// 新增报告的页面展示
function createPanelShow() {
	saveOrUpdate = "save";
	$("#operatorPanel").show();
	$("#deleteModel").hide();
	$("#saveModel").show();
	$("#addReport").show();
	
	$("#bgName").removeAttr("readonly");
	$("#reportName").removeAttr("readonly");
	$("#rptContent").removeAttr("readonly");
	$("#emailAddrs").removeAttr("readonly");
	$("#bgTitle").removeAttr("readonly");
	$("input[name='modelCon']").removeAttr("disabled");
	$("input[name='rptTime']").removeAttr("disabled");
	$("input[name='tableShow']").removeAttr("disabled");
	$("input[name='chartShow']").removeAttr("disabled");
	
	clearModelPage();
	
}
// 修改报告的页面展示
function updatePanelShow() {
	saveOrUpdate = "update";
	$("#operatorPanel").show();
	$("#deleteModel").hide();
	$("#saveModel").show();
	$("#addReport").show();
	
	//禁用表单
	$("#bgName").attr("readonly", "true");
	//$("#reportName").attr("readonly", "true");
	//$("#rptContent").removeAttr("readonly");
	$("input[name='modelCon']").attr("disabled", "true");
	$("#emailAddrs").removeAttr("readonly");
	$("#bgTitle").removeAttr("readonly");
	updateShow ();
}
// 删除报告的页面展示
function deletePanelShow() {
	$("#operatorPanel").show();
	$("#deleteModel").show();
	$("#saveModel").hide();
	$("#addReport").hide();
	
	//禁用表单
	$("#bgName").attr("readonly", "true");
	$("#reportName").attr("readonly", "true");
	$("#rptContent").attr("readonly", "true");
	$("#emailAddrs").attr("readonly", "true");
	$("#bgTitle").attr("readonly", "true");
	$("#reportName").attr("readonly", "true");
	
	$("input[name='modelCon']").attr("disabled", "true");
	$("input[name='rptTime']").attr("disabled", "true");
	$("input[name='tableShow']").attr("disabled", "true");
	$("input[name='chartShow']").attr("disabled", "true");
	deleteShow();
}

// 删除模板时展示
function deleteShow() {
	$("#rptShowPanel").html("");
	$.ajax({
		url: "reportModel/queryUpdateModelDetail",
		type: "POST",
		cache: false,
		data: {
			id: modelId
		},
		dataType: "json",
		success: function(data) {
			var rptModel = data.data.reportModel;
			var rptPublishList = data.data.reportPublishList;
			$("#bgName").val(rptModel.modelTitle);
			$("#bgTitle").val(rptModel.modelName);
			$("#emailAddrs").val(rptModel.sendUsernames);
			$("input[name='modelCon']").each(function(){
				if (rptModel.conname.indexOf("开始时间") != -1 && $(this).val()=="开始时间") {
					$("#begintimeCon").attr("checked","true");
				}
				if (rptModel.conname.indexOf("结束时间") != -1 && $(this).val()=="结束时间") {
					$("#endtimeCon").attr("checked","true");
				}
			});
			modelRptList = rptPublishList;
			$.each(rptPublishList, function(n, obj) {
				
				$("#rptShowPanel").append("<div style='margin-top:10px;' id='rpt" + obj.id + "'></div>");
				$("#rpt" + obj.id).append("<label style='width:325px;display: inline-block;vertical-align: top;'>" + obj.reportName + "</label>");
				
				if (obj.rptTime == 'd') {
					$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>日报</label>")
				} else if (obj.rptTime == 'w') {
					$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>周报</label>")
				} else if (obj.rptTime == 'm') {
					$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>月报</label>")
				}
				
				if (obj.tableShow == "Y") {
					$("#rpt" + obj.id).append("<label style='width:80px;display: inline-block;vertical-align: top;'>显示表</label>")
				} else {
					$("#rpt" + obj.id).append("<label style='width:80px;display: inline-block;vertical-align: top;'>不显示表</label>")
				}
				
				if (obj.chartShow == "Y") {
					if (obj.chartName != undefined && obj.chartName != null && obj.chartName != "") {
						$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>" + obj.chartName + "</label>")
					} else {
						$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>显示图</label>")	
					}
				} else {
					$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>不显示图</label>")
				}
									
			});
		}
	});
}
// 将页面内容置空
function clearModelPage() {
	$("#bgName").val("");
	$("#bgTitle").val("");
	$("#emailAddrs").val("");
	$("#rptContent").val("");
	modelRptList = [];
	$("input[name='modelCon']").each(function(){
		$(this).removeAttr("checked");
	});
	$("input[name='rptTime']").each(function(){
		$(this).removeAttr("checked");
	});
	$("input[name='tableShow']").each(function(){
		$(this).removeAttr("checked");
	});
	$("input[name='chartShow']").each(function(){
		$(this).removeAttr("checked");
	});
	$("#rptShowPanel").html("");
}
// 保存或修改模板
function saveModel() {
	if ($("#bgName").val() == "") {
		jAlert("请填写报告名称", "警告");
		return;
	}
	if ($("#bgTitle").val() == "") {
		jAlert("请填写报告标题", "警告");
		return;
	}
	if ($("input[name='modelCon']:checked").length==0) {
		jAlert("请选择开始时间或结束时间", "警告");
		return;
	}
	if ($("#emailAddrs").val() == "") {
		jAlert("请填写邮箱地址", "警告");
		return;
	}
	if (modelRptList.length == 0) {
		jAlert("请配置要发送的报表", "警告");
		return;
	}
	var modelConStr = "";
	$("input[name='modelCon']:checkbox").each(function() { 
         if($(this).attr("checked")) {
         	modelConStr += $(this).val()+","
         }
     });
	modelConStr = modelConStr.substring(0, modelConStr.length-1);
	$.ajax({
		url: "reportModel/saveModelDetail",
		type: "POST",
		cache: false,
		data: {
			saveOrUpdate : saveOrUpdate,
			modelId : modelId,
			saveType : 'report',
			bgName: $("#bgName").val(),
			bgTitle: $("#bgTitle").val(),
			modelCon: modelConStr,
			emailAddrs : $("#emailAddrs").val(),
			modelRptList : JSON.stringify(modelRptList)
		},
		dataType: "json",
		success: function(data) {
			if (data.code == "0") {
				if (saveOrUpdate == "save") {
					jAlert("保存成功","提示");
					clearModelPage();
				} else {
					jAlert("修改成功","提示");
					clearModelPage();
				}
			} else {
				if (saveOrUpdate == "save") {
					jAlert("保存失败","警告");
				} else {
					jAlert("修改失败","警告");
				}
			}
		}
	});
}
// 删除模板
function deleteModel() {
	$.ajax({
		url: "reportModel/deleteModelDetail",
		type: "POST",
		cache: false,
		data: {
			modelId: modelId
		},
		dataType: "json",
		success: function(data) {
			if (data.code == "0") {
				jAlert("删除成功", "提示");
				clearModelPage();
			} else {
				jAlert("删除失败", "警告");
			}
		}
	});
}

// 新增报表至模板
function addReport() {
	if ($("input[name='modelCon']:checked").length==0) {
		jAlert("请选择开始时间或结束时间","警告");
		return;
	}
	if ($("input[name='rptTime']:checked").length==0) {
		jAlert("请选择报表时间维度","警告");
		return;
	}
	if ($("input[name='tableShow']:checked").length==0 && $("input[name='chartShow']:checked").length==0) {
		jAlert("请选择是否保存图表","警告");
		return;
	}
	
	var obj = new Object();
	obj.toolFlag = reportFlag;
	obj.conFlag = reportConFlag;
	obj.id = obj.toolFlag + getRandom(100);
	obj.reportName = $("#reportName").val();
	obj.rptTime = $("input[name='rptTime']:checked").val();
	obj.rptTitle = $("#rptTitle").val();
	obj.rptContent = $("#rptContent").val();
	if ($("input[name='tableShow']:checked").length == 0) {
		obj.tableShow = "N";
	} else {
		obj.tableShow = "Y";
	}
	
	if ($("input[name='chartShow']:checked").length == 0) {
		obj.chartShow = "N";
		obj.chartId = "";
		obj.chartName = "";
	} else {
		obj.chartShow = "Y";
		obj.chartId = $("#chartList").val();
		obj.chartName = $("#chartList").find("option:selected").text();
	}
	
	//参数存储
	modelRptList.push(obj);
	$("input[name='modelCon']").attr("disabled", "true");
	// 界面显示
	addShow();
	$("#rptTitle").val("");
	$("#rptContent").val("");
}

// 新增报表编辑区展现
function addShow() {
	$("#rptShowPanel").html("");
	$.each(modelRptList, function(n, obj) {
		$("#rptShowPanel").append("<div style='margin-top:10px;' id='rpt" + obj.id + "'></div>");
		$("#rpt" + obj.id).append("<label style='width:325px;display: inline-block;vertical-align: top;'>" + obj.reportName + "</label>");
		
		if (obj.rptTime == 'd') {
			$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>日报</label>")
		} else if (obj.rptTime == 'w') {
			$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>周报</label>")
		} else if (obj.rptTime == 'm') {
			$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>月报</label>")
		}
		
		if (obj.tableShow == "Y") {
			$("#rpt" + obj.id).append("<label style='width:80px;display: inline-block;vertical-align: top;'>显示表</label>")
		} else {
			$("#rpt" + obj.id).append("<label style='width:80px;display: inline-block;vertical-align: top;'>不显示表</label>")
		}
		
		if (obj.chartShow == "Y") {
			if (obj.chartName != undefined && obj.chartName != null && obj.chartName != "") {
				$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>" + obj.chartName + "</label>")
			} else {
				$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>显示图</label>")	
			}
		} else {
			$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>不显示图</label>")
		}
							
		$("#rpt" + obj.id).append("<span><a href='#' class='gButton gIcon remove' style='width:5px;' onclick=\"deleteTmpReport('" + obj.id + "' , '" + obj.toolFlag + "')\"></a></span>");
		
		if (n != 0) {
			$("#rpt" + obj.id).append("<span><a href='###' class='gButton gIcon arrowup' style='width:6px; margin-left:10px;' onclick=\"up('" + obj.id + "')\"></a></span>");
		}
		
		if (n != modelRptList.length-1) {
			$("#rpt" + obj.id).append("<span><a href='###' class='gButton gIcon arrowdown' style='width:6px; margin-left:10px;' onclick=\"down('" + obj.id + "')\"></a></span>");
		}
	});
	
}

//修改报表编辑区展现
function updateShow () {
	$("#rptShowPanel").html("");
	$.ajax({
		url: "reportModel/queryUpdateModelDetail",
		type: "POST",
		cache: false,
		data: {
			id: modelId
		},
		dataType: "json",
		success: function(data) {
			var rptModel = data.data.reportModel;
			var rptPublishList = data.data.reportPublishList;
			$("#bgName").val(rptModel.modelName);
			$("#bgTitle").val(rptModel.modelTitle);
			$("#emailAddrs").val(rptModel.sendUsernames);
			$("input[name='modelCon']").each(function(){
				if (rptModel.conname.indexOf("开始时间") != -1 && $(this).val()=="开始时间") {
					$("#begintimeCon").attr("checked","true");
				}
				if (rptModel.conname.indexOf("结束时间") != -1 && $(this).val()=="结束时间") {
					$("#endtimeCon").attr("checked","true");
				}
			});
			modelRptList = rptPublishList;
			$.each(rptPublishList, function(n, obj) {
				$("#rptShowPanel").append("<div style='margin-top:10px;height:27px;' id='rpt" + obj.id + "'></div>");
				$("#rpt" + obj.id).append("<label style='width:325px;display: inline-block;vertical-align: top;'>" + obj.reportName + "</label>");
				
				if (obj.rptTime == 'd') {
					$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>日报</label>")
				} else if (obj.rptTime == 'w') {
					$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>周报</label>")
				} else if (obj.rptTime == 'm') {
					$("#rpt" + obj.id).append("<label style='width:136px;display: inline-block;vertical-align: top;'>月报</label>")
				}
				
				if (obj.tableShow == "Y") {
					$("#rpt" + obj.id).append("<label style='width:80px;display: inline-block;vertical-align: top;'>显示表</label>")
				} else {
					$("#rpt" + obj.id).append("<label style='width:80px;display: inline-block;vertical-align: top;'>不显示表</label>")
				}
				
				if (obj.chartShow == "Y") {
					if (obj.chartName != undefined && obj.chartName != null && obj.chartName != "") {
						$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>" + obj.chartName + "</label>")
					} else {
						$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>显示图</label>")	
					}
				} else {
					$("#rpt" + obj.id).append("<label style='width:142px;display: inline-block;vertical-align: top;'>不显示图</label>")
				}
									
				$("#rpt" + obj.id).append("<span><a href='###' class='gButton gIcon remove' style='width:5px;' onclick=\"deleteTmpReport('" + obj.id + "' , '" + obj.toolFlag + "')\"></a></span>");
				
				if (n != 0) {
					$("#rpt" + obj.id).append("<span><a href='###' class='gButton gIcon arrowup' style='width:6px; margin-left:10px;' onclick=\"up('" + obj.id + "')\"></a></span>");
				}
				
				if (n != modelRptList.length-1) {
					$("#rpt" + obj.id).append("<span><a href='###' class='gButton gIcon arrowdown' style='width:6px; margin-left:10px;' onclick=\"down('" + obj.id + "')\"></a></span>");
				}
			});
		}
	});
}
// 删除新增的报表
function deleteTmpReport (id, rflg) {
	$("#" + id).remove();
	for (var i=0; i<modelRptList.length; i++) {
		var o = modelRptList[i];
		if (o.id == id) {
			modelRptList.splice(i,1);
		}
	}
	addShow();
	if (modelRptList.length == 0) {
		$("input[name='modelCon']").removeAttr("disabled");
	}
}
//选择图形事件
function chooseChart () {
	$("input[name='chartShow']").removeAttr("disabled");
	if ($("input[name='chartShow']:checked").length > 0) {
		if (reportFlag == "") {
			jAlert("请选择报表", "警告");
			return;
		}
		$.ajax({
			url: "chart/findChartNameList.htm",
			type: "POST",
			dataType: "json",
			data: {
				reportFlag: reportFlag
			},
			async: false,
			success: function(data) {
				$("#chartList").html("");
				if (data.code == '0') {
					var chartList = data.data;
					if (chartList.length > 0) {
						for (var x in chartList) {
							$("#chartList").append("<option value='" + chartList[x].id + "'>" + chartList[x].chartName + "</option>");
						}
						$("#chartList").show();
					} else {
						jAlert("该报表没有配置图形", "警告");
						
						$("input[name='chartShow']").each(function(){
							$(this).removeAttr("checked");
						});
						
						$("input[name='chartShow']").attr("disabled", "true");
						$("#chartList").hide();
					}
				}
			}
		});
	} else {
		$("#chartList").hide();
	}
}
// 元素上移
function up (id) {
	for (var i=0; i<modelRptList .length; i++) {
		var o = modelRptList [i];
		if (o.id == id) {
			modelRptList.splice(i,1);
			modelRptList.splice(i-1,0,o);
		}
	}
	addShow();
}

// 元素下移
function down (id) {
	for (var i=0; i<modelRptList .length; i++) {
		var o = modelRptList [i];
		if (o.id == id) {
			modelRptList.splice(i, 1);
			modelRptList.splice(i+1, 0, o);
			break;
		}
	}
	addShow();
}

// 获取随机数
function getRandom(n) {
	return Math.floor(Math.random()*n+1);
}
</script>
