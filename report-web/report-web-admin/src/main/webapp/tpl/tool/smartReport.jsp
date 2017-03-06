<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="${pageContext.request.contextPath}/js/smartReport.js" type="text/javascript"></script>
<body>
<div id="content">
<input type="button" value="保存报表" onclick="saveReport()" id="saveReport" disabled="disabled">
    <div id="toolHead" style="width:100%;height:40%;">
    公共信息
        <table>
           	<tr>
	            <td>数据库列名</td><td><input type="text" id="toolEColumn" size="15"/></td>
	            <td>报表标志</td><td><input type="text" id="toolFlag" size="10"/></td>
	            <td>标题</td><td><input type="text" id="toolTitle" size="10" /></td>
	            <td colspan="2"><input type="button" value="生成数据库列名" onclick="initEcolumn()"/></td>
	            <td></td><td></td>
	            <th rowspan="3">
	            	<textarea style="width:480px;height:80px;" id="toolHSqlId" ></textarea>
	            </th>
	            <td><input type="button" id="toolFind" value="查看" onclick="queryData()"/></td>
	            <td><input type="button" id="toolSave" value="保存" onclick="saveSql()"/></td>
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
	            <td><input type="button" value="格式化" onclick="formatClick()"/></td><td></td>
	            <td></td><td></td><td></td>
	            <td></td><td></td><td></td>
            </tr>
        </table>
        
        <table id="reportList"></table>
        <div id="reportPager" ></div>
    </div>
    
    
    
    
    <div id="toolMiddle" style="width:100%;height:22%; margin-top: 40px;">
    条件
        <table id="ConTable">
            <tr><td>所属SQL条件</td><td><input type="text" id="conWhere" size="10"/></td>
            <td>第几行</td><td><select id="conRowNum"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></td>
            <td>条件类型</td><td><select id="conType"><option>文本</option><option>日期</option><option>模糊查询</option></select></td>
            <td>条件名称</td><td><input type="text" id="conName" size="10"/></td>
            <td>相关值</td><td><input type="text" id="conDefaultValue" size="10"/></td>
            <td>选项</td><td><select id="conOption" onchange="changeConType()"><option value="input">表单(input)</option><option value="select">单选(select)</option><option value="checkbox">多选(checkbox)</option></select></td>
            <td></td><td id="conValueTd"></td>
            <td><input type="button" value="增加" id="conAdd" onclick="addConShow()"/></td>
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
	   		<input type="radio" name="chartType" value="bar" checked="checked"/> 线状图/柱状图</span>
	        <input type="radio" name="chartType" value="pie" /> 饼图
	        <a href="http://echarts.baidu.com/doc/example.html" target="_blank">图表实例</a>
	        <a href="http://echarts.baidu.com/doc/doc.html" target="_blank">图表doc</a>
	    </div>
        <table id="chartTable">
            <tr>
                <td>图表名称</td><td><input type="text" id="chartName" size="10"/></td>
	            <td>图表Option</td><td><input type="text" id="chartOption" size="10"/></td>
	            <td>数据库列名与X轴对应关系</td><td><input type="text" id="columnVsX" size="20"/></td>
	            <td>数据库列名与legend对应关系</td><td><input type="text" id="columnVsLegend" size="30"/></td>
	            <td><input type="button" value="刷新图表" id="showChart" onclick="showChart()"/></td>
	            <td><input type="button" value="增加图表" id="addChart" onclick="addChart()"/></td>
	        </tr>
        </table>
        <div id="echart" style="height: 100%; width: 90%;"></div>
    </div>
</div>
</body>
