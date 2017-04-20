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
                                        <label>sqlId：</label>
                                        <input type="text" class="form-control" style="width:180px" name="sqlId" autocomplete="off" />
                                        </span>
                                        <span class="query-item-inline">
                                        <label>sql名：</label>
                                        <input type="text" class="form-control" style="width:180px" name="rptname" autocomplete="off" />
                                        </span>
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('reportSqlList', 'searchForm');">查询</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="reportSqlList"
						url="${pageContext.request.contextPath}/reportSql/findReportSqlList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'false',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="sqlId" width="20">sqlId</th>
								<th field="dataBaseSource" width="20">数据源</th>
								<th field="rptname" width="20">sql名</th>
								<th field="baseSql" width="50">内容sql</th>
								<th field="baseCountSql" width="50">数量sql</th>
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
	                    onclick="addObject('新建','${pageContext.request.contextPath}/reportSql/addReportSql.htm','reportSqlDialog', 'reportSqlForm')">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                    onclick="editReportSql()">编辑</a>
		</div>
	</div>
	<!-- 新建SQL -->
	<div id="reportSqlDialog" class="easyui-dialog"
	    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
	        buttons="#reportSql-buttons">
        <form id="reportSqlForm" class="form-horizontal" method="post" novalidate>
            <input name="sqlId" type="hidden" id="sqlId">
            <div class="form-group">
                <label>sql名:</label>
                <input type="text" name="rptname" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>数据源:</label>
                <input type="text" name="dataBaseSource" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>内容sql:</label>
                <input type="text" name="baseSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>数量sql:</label>
                <input type="text" name="baseCountSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="reportSql-buttons">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('reportSqlForm','reportSqlDialog','reportSqlList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#reportSqlDialog').dialog('close')">关闭</a>
    </div>


	<!-- 修改 -->
    <div id="reportSqlUpdateDialog" class="easyui-dialog"
    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
        buttons="#reportSql-buttons-update">
        <form id="reportSqlForm2" class="form-horizontal" method="post" novalidate>
            <input name="sqlId" type="hidden" id="sqlId">
            <div class="form-group">
                <label>sql名:</label>
                <input type="text" name="rptname" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>数据源:</label>
                <input type="text" name="dataBaseSource" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>内容sql:</label>
                <input type="text" name="baseSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>数量sql:</label>
                <input type="text" name="baseCountSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="reportSql-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('reportSqlForm2','reportSqlUpdateDialog','reportSqlList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#reportSqlUpdateDialog').dialog('close')">关闭</a>
    </div>

	<script type="text/javascript">
		$('#reportSqlList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
		
		function editReportSql(){
            var row = $('#reportSqlList').datagrid('getSelected');
            updateObject('编辑报表SQL','${pageContext.request.contextPath}/reportSql/updateReportSql.htm','reportSqlList','reportSqlUpdateDialog', 'reportSqlForm2');
        };
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>