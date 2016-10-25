<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<!DOCTYPE html>
<!--[if IE 7]>         <html class="no-js lt-ie10 lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie10 lt-ie9"> <![endif]-->
<!--[if IE 9]>         <html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>批量报表条件管理</title>
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
	                                        <label>条件标志：</label>
	                                        <input type="text" class="form-control" style="width:180px" name="conFlag" autocomplete="off" />
	                                        </span>
	                                    </td>
	                                    <td>
	                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('commonConList', 'searchForm');">查询</a>
	                                    </td>
	                                </tr>
	                            </tbody>
	                        </table>
                        </form>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="commonConList"
						url="${pageContext.request.contextPath}/commonCondition/findCommonConditionList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'false',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="id" data-options="hidden:'true'"></th>
									<th field="toolFlag" width="30">报表标志</th>
									<th field="conFlag" width="30">条件标志</th>
									<th field="conWhere" width="30">匹配sql条件</th>
									<th field="conValue" width="30" >参数值</th>
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
	                    onclick="addObject('新建','${pageContext.request.contextPath}/commonCondition/addCommonCondition.htm','commonConDialog', 'commonConForm')">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                    onclick="editCommonCon()">编辑</a>
		</div>
	</div>
<!-- 新建条件 -->
	<div id="commonConDialog" class="easyui-dialog"
	    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
	        buttons="#commonCon-button">
        <form id="commonConForm" class="form-horizontal" method="post" novalidate>
            <input name="id" type="hidden" id="id">
            <div class="form-group">
                <label>报表标志:</label>
                <input type="text" name="toolFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>条件标志:</label>
                <input type="text" name="conFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>匹配sql条件:</label>
                <input type="text" name="conWhere" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>参数值:</label>
                <input type="text" name="conValue" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="commonCon-button">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('commonConForm','commonConDialog','commonConList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#commonConDialog').dialog('close')">关闭</a>
    </div>
<!-- 对话框 -->
    <div id="commonConUpDialog" class="easyui-dialog"
    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
        buttons="#commonCon-buttons-update">
        <form id="commonConForm2" class="form-horizontal" method="post" novalidate>
            <input name="id" type="hidden" id="id">
            <div class="form-group">
                <label>报表标志:</label>
                <input type="text" name="toolFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>条件标志:</label>
                <input type="text" name="conFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>匹配sql条件:</label>
                <input type="text" name="conWhere" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>参数值:</label>
                <input type="text" name="conValue" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="commonCon-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('commonConForm2','commonConUpDialog','commonConList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#commonConUpDialog').dialog('close')">关闭</a>
    </div>

	<script type="text/javascript">
		$('#commonConList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
		
		function editCommonCon(){
			$("#commonConUpDialog").form('clear');
            var row = $('#commonConList').datagrid('getSelected');
            updateObject('编辑条件','${pageContext.request.contextPath}/commonCondition/updateCommonCondition.htm','commonConList','commonConUpDialog', 'commonConForm2');
        };
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>