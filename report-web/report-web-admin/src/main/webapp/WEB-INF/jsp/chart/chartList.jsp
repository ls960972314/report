<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<jsp:include page="../fragments/script.jsp" />
</head>
<body style="overflow-y: hidden">
	<%@include file="../fragments/bodyHeader.jsp"%>
	<table class="container" style="height: 592px;">
		<tbody>
			<tr>
				<td width="190" valign="top">
					<!--左侧导航 Start--> <%@include file="../fragments/leftMenu.jsp"%>
				</td>
				<!------ 主体内容开始 -------->
				<td valign="top" class="panel-noscroll">
					<div class="extendDiv-toggle">
						<form id="searchForm">
							<table width="100%" class="queryFormPanel">
	                            <tbody style="vertical-align:top;">
	                                <tr>
	                                    <td>
	                                        <span class="query-item-inline">
	                                        <label>报表标志：</label>
	                                        <input type="text" class="form-control" style="width:180px" name="toolFlag" autocomplete="off" />
	                                        </span>
	                                        <span class="query-item-inline">
	                                        <label>图形名称：</label>
	                                        <input type="text" class="form-control" style="width:180px" name="chartName" autocomplete="off" />
	                                        </span>
	                                    </td>
	                                    <td>
	                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('chartList', 'searchForm');">查询</a>
	                                    </td>
	                                </tr>
	                            </tbody>
	                        </table>
                        </form>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="chartList"
						url="${pageContext.request.contextPath}/chart/findchartList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'false',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="id" data-options="hidden:'true'"></th>
									<th field="toolFlag" width="30">报表标志</th>
									<th field="chartName" width="30">图形名称</th>
									<th field="chartType" width="10">图形类型</th>
									<th field="dataVsLe" width="30" >数据库列名与X轴对应关系</th>
									<th field="dataVsX" width="50" >数据库列名与legend对应关系</th>
									<th field="chartOption" width="70">图形Option</th>
									<th field="showRowNum" width="10">图表展示行数</th>
									<th field="chartOrder" width="10">排序</th>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</tbody>
	</table>


	<!-- tool -->
	<div id="toolbar">
		<div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                    onclick="editChart()">编辑</a>
		</div>
	</div>

<!-- 对话框 -->
    <div id="chartUpdateDialog" class="easyui-dialog"
    data-options="width : 600, closable : true, modal : true, closed : true, shadow : false"
        buttons="#chart-buttons-update">
        <form id="chartForm2" class="form-horizontal" method="post" novalidate>
            <input name="id" type="hidden" id="id">
            <div class="form-group">
                <label>图形名称:</label>
                <input type="text" name="chartName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>chartType:</label>
                <input type="text" name="chartType" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>报表标志:</label>
                <input type="text" name="toolFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>数据库列名与X轴对应关系:</label>
                <input type="text" name="dataVsX" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>数据库列名与legend对应关系:</label>
                <input type="text" name="dataVsLe" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>图形Option:</label>
                <input type="text" name="chartOption" class="easyui-validatebox form-control"  style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>图表展示行数:</label>
                <input type="text" name="showRowNum" class="form-control"  style="width: 240px;height: 24px;background: gainsboro;">
            </div>
            <div class="form-group">
                <label>排序:</label>
                <input type="text" name="chartOrder" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="chart-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('chartForm2','chartUpdateDialog','chartList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#chartUpdateDialog').dialog('close')">关闭</a>
    </div>

	<script type="text/javascript">
		$('#chartList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
		
		function editChart(){
			$("#chartUpdateDialog").form('clear');
            var row = $('#chartList').datagrid('getSelected');
            updateObject('编辑图形','${pageContext.request.contextPath}/chart/updateChart.htm','chartList','chartUpdateDialog', 'chartForm2');
        };
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>