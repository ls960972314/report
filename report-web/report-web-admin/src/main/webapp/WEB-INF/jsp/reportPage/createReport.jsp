<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE HTML>
<html>
<head>
<title>新建报表</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script>
$(function() {
   $("#tabs").tabs();
   $("#dataBaseSource").selectmenu({height:"22px"});
   $("#timeSelect").selectmenu({height:"22px"});
   $("#columnName").selectmenu();
   $("#columnType").selectmenu();
   $( document ).tooltip({
     track: true
   });
});
</script>
</head>
  
<body>
  <label for="toolFlag" style="width:100px;display:inline-block;">报表标志</label>
  <input id="toolFlag" type="text" />
  <div id="tabs" style="margin-top:10px;">
    <ul>
      <li>
        <a href="#tabs-1">数据源</a>
      </li>
      <li>
        <a href="#tabs-3">控件</a>
      </li>
      <li>
        <a href="#tabs-2">表格</a>
      </li>
      <li>
        <a href="#tabs-4">图形</a>
      </li>
    </ul>
    <div id="tabs-1" style="height:500px;">
    	<select id="dataBaseSource" title="数据源">
          <option value="ods">报表库</option>
          <option value="sjck">数据仓库</option>
        </select>
        <select id="timeSelect" title="数据源所属时间维度">
          <option>时</option>
          <option>日</option>
          <option>周</option>
          <option>月</option>
          <option>季</option>
          <option>年</option>
        </select>
       	<input type="text" id="toolSqlName" title="SQL名" style="height:29px;"/>
        <a href="#" id="toolSave" class="ui-button ui-widget ui-corner-all" onclick="saveSql()">保存该维度SQL</a>
      	<textarea style="width:100%;height:200px;float:left;margin-top:10px;" id="toolHSqlId" title="数据源查询SQL"></textarea>
    </div>
    <div id="tabs-2" style="height:500px;">
    	<div style="width:100%;">
	        <!-- <a href="#" class="ui-button ui-widget ui-corner-all" onclick="initEcolumn()">生成数据库列名</a> -->
	        <a href="#" class="ui-button ui-widget ui-corner-all" onclick="addStatistics()">统计信息</a>
	        <a href="#" class="ui-button ui-widget ui-corner-all" onclick="queryData()">生成表格</a>
      	</div>
      	<!-- <div style="margin-top:10px;">
	        <input id="toolEColumn" type="text" title="数据库列名" style="width:100%;margin-top:5px;"/>
        </div> -->
        <div style="margin-top:10px;">
        	<input type="text" id="toolCColumn" title="中文列名" style="width:100%;margin-top:5px;"/>
      	</div>
      	<div style="width :100%; float: left;">
      		<p>
	      		<select id="columnName">
		          <option>请选择中文列名</option>
		        </select>
	      		<select id="columnType">
		          <option>正整数</option>
		          <option>小数</option>
		          <option>负数</option>
		          <option>百分比</option>
		        </select>
	        	<a href="#" class="ui-button ui-widget ui-corner-all" onclick="formatClick()">格式化</a>
	        </p>
	        <p>
	        	<label for="toolTitle" style="width:100px;display:inline-block;">标题</label>
	       		<input type="text" id="toolTitle" size="10" />
	        </p>
	      	<p>
	      		<label for="toolGather" style="width:100px;display:inline-block;">汇总列</label>
	        	<input type="text" id="toolGather"/>
	      	</p>
      	</div>
	    <table id="reportList"></table>
	    <div id="reportPager"></div>
    </div>
    <div id="tabs-3">
      <label>条件</label>
      <table id="ConTable">
        <tbody>
          <tr>
            <td>所属SQL条件</td>
            <td>
              <input type="text" id="conWhere" size="10" /></td>
            <td>第几行</td>
            <td>
              <select id="conRowNum">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option></select>
            </td>
            <td>条件类型</td>
            <td>
              <select id="conType">
                <option>文本</option>
                <option>日期</option>
                <option>模糊查询</option></select>
            </td>
            <td>条件名称</td>
            <td>
              <input type="text" id="conName" size="10" /></td>
            <td>相关值</td>
            <td>
              <input type="text" id="conDefaultValue" size="10" /></td>
            <td>选项</td>
            <td>
              <select id="conOption" onchange="changeConType()">
                <option value="input">表单(input)</option>
                <option value="select">单选(select)</option>
                <option value="checkbox">多选(checkbox)</option></select>
            </td>
            <td></td>
            <td id="conValueTd"></td>
            <td>
              <a href="#" class="ui-button ui-widget ui-corner-all" id="conAdd" onclick="addConShow()">增加</a></td>
          </tr>
        </tbody>
      </table>
      <div style='width: 100%' id="conShow"></div>
    </div>
    <div id="tabs-4">
      <label>图表</label>
      <div>
        <label>是否保存图形:</label>
        <input type="radio" name="chartFlag" value="true" checked="checked" />是
        <input type="radio" name="chartFlag" value="false" />否</div>
      <div>
        <span id="chartPanel"></span>
      </div>
      <div id="toolbar">
        <div style="margin-bottom: 5px">
          <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBar()">线形图/柱状图</a>
          <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPie()">饼图</a></div>
      </div>
      <!-- bar/line 新增框 -->
      <div id="addBarDialog" class="easyui-dialog" data-options="width : 1000,height:800, closable : true, modal : true, closed : true, shadow : false" buttons="#addBar-buttons">
        <form id="addBarForm" class="form-horizontal" method="post" novalidate>
          <div class="form-group">
            <label style="width: 200px;">图表名称:</label>
            <input type="text" id="chartName" name="chartName" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true">
            <label style="width: 200px;">图表Option:</label>
            <input type="text" id="chartOption" name="chartOption" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true"></div>
          <div class="form-group">
            <label style="width: 200px;">数据库列名与X轴对应关系:</label>
            <input type="text" id="columnVsX" name="columnVsX" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true">
            <label style="width: 200px;">数据库列名与legend对应关系:</label>
            <input type="text" id="columnVsLegend" name="columnVsLegend" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true"></div>
        </form>
        <div id="barEchart" style="height: 50%; width: 90%; margin-left: 20px; margin-top: 100px;"></div>
      </div>
      <div class="dialog_btnBox" id="addBar-buttons">
        <a href="javascript:void(0)" class="btn btn-primary" id="showChart" onclick="showChart('bar')">刷新柱形图</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="addChart" onclick="addChart('bar')">增加柱形图</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#addBarDialog').dialog('close')">关闭</a></div>
      <!-- pie新增框 -->
      <div id="addPieDialog" class="easyui-dialog" data-options="width : 1000,height:800, closable : true, modal : true, closed : true, shadow : false" buttons="#addPie-buttons">
        <form id="addPieForm" class="form-horizontal" method="post" novalidate>
          <div class="form-group">
            <label style="width: 100px;">图表名称:</label>
            <input type="text" id="pieChartName" name="pieChartName" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true">
            <label style="width: 100px;">名称:</label>
            <select id="pieOptionName" name="pieOptionName" data-options="required:true"></select>
            <label style="width: 100px;">取值:</label>
            <select id="pieOptionValue" name="pieOptionValue" data-options="required:true"></select>
          </div>
          <div class="form-group">
            <label style="width: 100px;">图表展示行数:</label>
            <input type="text" id="pieChartShowRowNum" name="pieChartShowRowNum" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true"></div>
        </form>
        <div id="pieEchart" style="height: 50%; width: 90%; margin-top: 100px; margin-left: 20px;"></div>
      </div>
      <div class="dialog_btnBox" id="addPie-buttons">
        <a href="javascript:void(0)" class="btn btn-primary" id="showChart" onclick="showChart('pie')">刷新饼状图</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="addChart" onclick="addChart('pie')">增加饼状图</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#addPieDialog').dialog('close')">关闭</a></div>
      <!-- 合计数据dialog -->
      <div id="staticDialog" class="easyui-dialog" data-options="width : 800,height:400, closable : true, modal : true, closed : true, shadow : false" buttons="#static-buttons">
        <form id="staticForm" class="form-horizontal" method="post" novalidate>
          <div class="form-group">
            <label style="width: 250px;">合计列名(逗号分隔):</label>
            <input type="text" id="staticCcolumn" name="staticCcolumn" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true">
            <label style="width: 100px;">显示行数:</label>
            <select id="staticRowNum" name="staticRowNum" data-options="required:true">
              <option value="1" selected="selected">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option></select>
          </div>
          <div class="form-group">
            <label style="width: 250px;">合计sql(查询结果集为一行):</label>
            <input type="text" id="staticSql" name="staticSql" class="easyui-validatebox form-control" style="width: 240px; height: 24px; background: gainsboro;" data-options="required:true"></div>
        </form>
        <div id="staticData" class="mod-body" style="height: 69px;"></div>
      </div>
      <div class="dialog_btnBox" id="static-buttons">
        <a href="javascript:void(0)" class="btn btn-primary" id="showChart" onclick="queryStatic()">查看合计数据</a>
        <a href="javascript:void(0)" class="btn btn-primary" id="showChart" onclick="saveStatic()">保存</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#staticDialog').dialog('close')">关闭</a></div>
    </div>
  </div>
</body>
</html>