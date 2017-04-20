<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
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
				<td valign="top">
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
	                                    </td>
	                                    <td>
	                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('conditionList', 'searchForm');">查询</a>
	                                    </td>
	                                </tr>
	                            </tbody>
	                        </table>
                        </form>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="conditionList"
						url="${pageContext.request.contextPath}/condition/findConditionList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'false',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="Id" data-options="hidden:'true'"></th>
								<th field="toolFlag" width="30">报表标志</th>
								<th field="conName" width="30">条件名</th>
								<th field="conMuti" width="30">多选或者单选时的值(可为sql)</th>
								<th field="conType" width="10">多媒体类型</th>
								<th field="conWhere" width="30" >匹配sql条件</th>
								<th field="conDefaultValue" width="70">默认值</th>
								<th field="conOption" width="10">条件类型</th>
								<th field="dataBaseSource" width="10">数据源</th>
								<th field="orderNum" width="10">排序</th>
								<th field="rowNum" width="10">行数</th>
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
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
	                    onclick="addObject('新建','${pageContext.request.contextPath}/condition/addCondition.htm','conditionAddDialog', 'conditionForm')">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                    onclick="editCondition()">编辑</a>
		</div>
	</div>
<!-- 新建条件 -->
	<div id="conditionAddDialog" class="easyui-dialog"
	    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
	        buttons="#conditionAdd-buttons">
        <form id="conditionForm" class="form-horizontal" method="post" novalidate>
            <div class="form-group">
                <label>条件名:</label>
                <input type="text" name="conName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>多选或单选的值:</label>
                <input type="text" name="conMuti" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>报表标志:</label>
                <input type="text" name="toolFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>多媒体类型:</label>
                <input type="text" name="conType" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>条件类型:</label>
                <input type="text" name="conOption" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>匹配sql条件:</label>
                <input type="text" name="conWhere" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>默认值:</label>
                <input type="text" name="conDefaultValue" class="form-control"  style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>数据源:</label>
                <input type="text" name="dataBaseSource" class="form-control"  style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>排序:</label>
                <input type="text" name="orderNum" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>行数:</label>
                <input type="text" name="rowNum" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="conditionAdd-buttons">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('conditionForm','conditionAddDialog','conditionList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#conditionAddDialog').dialog('close')">关闭</a>
    </div>
<!-- 对话框 -->
    <div id="conditionUpdateDialog" class="easyui-dialog"
    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
        buttons="#condition-buttons-update">
        <form id="conditionForm2" class="form-horizontal" method="post" novalidate>
            <input name="Id" type="hidden" id="id">
            <div class="form-group">
                <label>条件名:</label>
                <input type="text" name="conName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>多选或单选的值:</label>
                <input type="text" name="conMuti" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>报表标志:</label>
                <input type="text" name="toolFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>多媒体类型:</label>
                <input type="text" name="conType" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>条件类型:</label>
                <input type="text" name="conOption" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>匹配sql条件:</label>
                <input type="text" name="conWhere" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>默认值:</label>
                <input type="text" name="conDefaultValue" class="form-control"  style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>数据源:</label>
                <input type="text" name="dataBaseSource" class="form-control"  style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>排序:</label>
                <input type="text" name="orderNum" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>行数:</label>
                <input type="text" name="rowNum" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="condition-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('conditionForm2','conditionUpdateDialog','conditionList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#conditionUpdateDialog').dialog('close')">关闭</a>
    </div>

	<script type="text/javascript">
		$('#conditionList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
		
		function editCondition(){
            var row = $('#conditionList').datagrid('getSelected');
            updateObject('编辑条件','${pageContext.request.contextPath}/condition/updateCondition.htm','conditionList','conditionUpdateDialog', 'conditionForm2');
        };
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>