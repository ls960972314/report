<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 7]>         <html class="no-js lt-ie10 lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie10 lt-ie9"> <![endif]-->
<!--[if IE 9]>         <html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js">
<!--<![endif]-->
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>新建报表</title>
	


	<link href="${pageContext.request.contextPath}/ui/main.css" media="screen" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/ui/redmond/jquery-ui-1.9.2.custom.min.css"  rel="stylesheet"></link>
    <link href="${pageContext.request.contextPath}/ui/ui.jqgrid.css" rel="stylesheet"></link>
    <link href="${pageContext.request.contextPath}/ui/ui.multiselect.css" rel="stylesheet"></link>
    <link href="${pageContext.request.contextPath}/ui/reveal.css" rel="stylesheet"></link>
    <link href="${pageContext.request.contextPath}/ui/button-css/gh-buttons.css" rel="stylesheet"></link>
    <link href="${pageContext.request.contextPath}/ui/button-css/prettify.css" rel="stylesheet"></link>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.5/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/jquery-easyui-1.5/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
	
	
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/esl.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.layout-latest.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/json2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.reveal.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/smartReport.js" type="text/javascript"></script>
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.5/easyloader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/curdtools.js"></script>
	
	<style>
	.digest-block {
		width: 20%;
		height: 70px;
		float: left;
		background: #f5f5f5;
		border-right: 1px solid #d2d2d2;
		border-bottom: 1px solid #d2d2d2;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		position: relative;
		cursor: pointer;
	}
	
	.digest-block h4 {
		width: 85%;
		margin: 0 auto;
		margin-top: 10px;
		font-size: 14px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		text-align: center;
	}
	
	.digest-block h1 {
		text-align: center;
		font-size: 32px;
		margin-top: 10px;
	}
	</style>
	
</head>
<script type="text/javascript">
	easyloader.locale = "zh_CN";
	easyloader.base = '${pageContext.request.contextPath}/js/jquery-easyui-1.5/';
	using(['dialog','combobox','combotree','form','messager','datebox'], function(){   
	});
</script>
<body>
<div id="header" class="navbar navbar-fixed-top">
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveReport()" id="saveReport">保存报表</a>
</div>
<div id="content" class="container" style="margin-left:20px;">
    <div id="toolHead" style="width:100%;height:40%;">
    公共信息<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addStatistics()">统计信息</a>
        <table style="width:100%;">
           	<tr>
	            <td>数据库列名</td><td><input type="text" id="toolEColumn" size="15"/></td>
	            <td>报表标志</td><td><input type="text" id="toolFlag" size="10"/></td>
	            <td>标题</td><td><input type="text" id="toolTitle" size="10" /></td>
	            <td colspan="2"><a href="#" class="easyui-linkbutton" onclick="initEcolumn()">生成数据库列名</a></td>
	            <td></td><td></td>
	            <th rowspan="3">
	            	<textarea style="width:480px;height:80px;" id="toolHSqlId" ></textarea>
	            </th>
	            <td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="queryData()">查看</a></td>
	            <td><a href="#" id="toolSave" class="easyui-linkbutton" iconCls="icon-save" onclick="saveSql()">保存</a></td>
	            
            </tr>
            <tr>
	            <td>中文列名</td><td><input type="text" id="toolCColumn" size="15"/></td>
	            <td>汇总列</td><td><input type="text" id="toolGather" size="10" value=""/></td>
	            <td>sql名</td><td><input type="text" id="toolSqlName" size="10"/></td>
	            <td>时间维度</td><td><select id="timeSelect"><option>时</option><option>日</option><option>周</option><option>月</option><option>季</option><option>年</option></select></td>
	            <td></td><td></td><td></td>
	            <td></td><td></td><td></td>
            </tr>
            <tr>
	            <td>格式化列名</td><td><select id="columnName"><option>请先填写中文列名</option></select></td>
	            <td>格式化方式</td><td><select id="columnType"><option>正整数</option><option>小数</option><option>负数</option><option>百分比</option></select></td>
	            <td>数据源</td><td>
	            	<select id="dataBaseSource">
						<option value="ods">ODS</option>
						<option value="pos">POS</option>
						<option value="tms">TMS</option>
						<option value="cod">COD</option>
						<option value="tmshis">TMSHIS</option>
						<option value="lc">理财/小贷通</option>
						<option value="bj">保价/保价理赔</option>
						<option value="sjck">数据仓库</option>
					</select>
				</td>
	            <td><a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="formatClick()">格式化</a></td><td></td>
	            <td></td><td></td><td></td>
	            <td></td><td></td><td></td>
            </tr>
        </table>
        
        <table id="reportList"></table>
        <div id="reportPager" ></div>
    </div>
    
    
    
    
    <div id="toolMiddle" style="width:100%;height:22%; margin-top: 40px;">
   		<label> 条件</label>
        <table id="ConTable" >
        	<tbody>   
	            <tr>
		            <td>所属SQL条件</td><td><input type="text" id="conWhere" size="10"/></td>
		            <td>第几行</td><td><select id="conRowNum"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></td>
		            <td>条件类型</td><td><select id="conType"><option>文本</option><option>日期</option><option>模糊查询</option></select></td>
		            <td>条件名称</td><td><input type="text" id="conName" size="10"/></td>
		            <td>相关值</td><td><input type="text" id="conDefaultValue" size="10"/></td>
		            <td>选项</td><td><select id="conOption" onchange="changeConType()"><option value="input">表单(input)</option><option value="select">单选(select)</option><option value="checkbox">多选(checkbox)</option></select></td>
		            <td></td><td id="conValueTd"></td>
		            <td><a href="#" class="easyui-linkbutton" iconCls="icon-add" id="conAdd" onclick="addConShow()">增加</a></td>
	            </tr>
            </tbody>
        </table>
        <div style='width:100%' id="conShow"></div>
        
    </div>
    
    
    
    <div id="toolBottom" style="width:100%;height:30%;">
		<label> 图表</label>
		<div>
	        <label>是否保存图形:</label>
	        <input type="radio" name="chartFlag" value="true" checked="checked"/> 是
	        <input type="radio" name="chartFlag" value="false" /> 否
        </div>
        <div>
        	<span id="chartPanel">
        	</span>
        </div>
		<div id="toolbar">
			<div style="margin-bottom: 5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		                    onclick="addBar()">线形图/柱状图</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
	                    onclick="addPie()">饼图</a>
			</div>
		</div>
	    <!-- bar/line 新增框  -->
	    <div id="addBarDialog" class="easyui-dialog" data-options="width : 1000,height:800, closable : true, modal : true, closed : true, shadow : false"
		        buttons="#addBar-buttons">
	        <form id="addBarForm" class="form-horizontal" method="post" novalidate>
	            <div class="form-group">
	                <label style="width:200px;">图表名称:</label>
	                <input type="text" id="chartName" name="chartName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
	                <label style="width:200px;">图表Option:</label>
	                <input type="text" id="chartOption" name="chartOption" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
	            </div>
	            <div class="form-group">
	                <label style="width:200px;">数据库列名与X轴对应关系:</label>
	                <input type="text" id="columnVsX" name="columnVsX" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
	                <label style="width:200px;">数据库列名与legend对应关系:</label>
	                <input type="text" id="columnVsLegend" name="columnVsLegend" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
	            </div>
	        </form>
	        <div id="barEchart" style="height: 50%; width: 90%; margin-left:20px;margin-top:100px;"></div>
	    </div>
	    <div class="dialog_btnBox" id="addBar-buttons">
	        <a href="javascript:void(0)" class="btn btn-primary"
	            id="showChart" onclick="showChart('bar')">刷新柱形图</a>
	        <a href="javascript:void(0)" class="btn btn-primary"
	            id="addChart" onclick="addChart('bar')">增加柱形图</a>
	        <a href="javascript:void(0)" class="btn btn-default"
	            onclick="javascript:$('#addBarDialog').dialog('close')">关闭</a>
	    </div>
	    <!-- pie新增框 -->
	    <div id="addPieDialog" class="easyui-dialog" data-options="width : 1000,height:800, closable : true, modal : true, closed : true, shadow : false"
		        buttons="#addPie-buttons">
	        <form id="addPieForm" class="form-horizontal" method="post" novalidate>
	            <div class="form-group">
	            	<label style="width:100px;">图表名称:</label>
	                <input type="text" id="pieChartName" name="pieChartName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
	                <label style="width:100px;">名称:</label>
	                <select id="pieOptionName" name="pieOptionName" data-options="required:true"></select>
	                <label style="width:100px;">取值:</label>
	                <select id="pieOptionValue" name="pieOptionValue" data-options="required:true"></select>
	            </div>
	            <div class="form-group">
	            	<label style="width:100px;">图表展示行数:</label>
	                <input type="text" id="pieChartShowRowNum" name="pieChartShowRowNum" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
	            </div>
	        </form>
	        <div id="pieEchart" style="height: 50%; width: 90%;margin-top:100px;margin-left:20px;"></div>
	    </div>
	    <div class="dialog_btnBox" id="addPie-buttons">
	        <a href="javascript:void(0)" class="btn btn-primary"
	            id="showChart" onclick="showChart('pie')">刷新饼状图</a>
	        <a href="javascript:void(0)" class="btn btn-primary"
	            id="addChart" onclick="addChart('pie')">增加饼状图</a>
	        <a href="javascript:void(0)" class="btn btn-default"
	            onclick="javascript:$('#addPieDialog').dialog('close')">关闭</a>
	    </div>
	    
	    <!-- 合计数据dialog -->
	    <div id="staticDialog" class="easyui-dialog" data-options="width : 800,height:400, closable : true, modal : true, closed : true, shadow : false"
		        buttons="#static-buttons">
	        <form id="staticForm" class="form-horizontal" method="post" novalidate>
	            <div class="form-group">
	            	<label style="width:250px;">合计列名(逗号分隔):</label>
	                <input type="text" id="staticCcolumn" name="staticCcolumn" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
	                <label style="width:100px;">显示行数:</label>
	                <select id="staticRowNum" name="staticRowNum" data-options="required:true"><option value="1" selected="selected">1</option><option value="2">2</option><option value="3">3</option><option value="4">4</option></select>
	            </div>
	            <div class="form-group">
	            	<label style="width:250px;">合计sql(查询结果集为一行):</label>
	                <input type="text" id="staticSql" name="staticSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
	            </div>
	        </form>
	        <div id="staticData" class="mod-body" style="height:69px;"></div>
	    </div>
	    <div class="dialog_btnBox" id="static-buttons">
	        <a href="javascript:void(0)" class="btn btn-primary"
	            id="showChart" onclick="queryStatic()">查看合计数据</a>
	        <a href="javascript:void(0)" class="btn btn-primary"
	            id="showChart" onclick="saveStatic()">保存</a>
	        <a href="javascript:void(0)" class="btn btn-default"
	            onclick="javascript:$('#staticDialog').dialog('close')">关闭</a>
	    </div>
    </div>
    
    
</div>
</body>
</html>
