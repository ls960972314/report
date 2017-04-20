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
                                        <span class="query-item-inline">
                                        <label>报表标题：</label>
                                        <input type="text" class="form-control" style="width:180px" name="toolTitle" autocomplete="off" />
                                        </span>
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('publicList', 'searchForm');">查询</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="publicList"
						url="${pageContext.request.contextPath}/public/findPublicList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'false',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="id" data-options="hidden:'true'"></th>
								<th field="toolFlag" width="30">报表标志</th>
								<th field="toolTitle" width="30">报表标题</th>
								<th field="toolCColumn" width="10">中文列名</th>
								<th field="toolEColumn" width="30" >英文列名</th>
								<th field="toolGather" width="50" >汇总列</th>
								<th field="toolFormat" width="70">列格式化</th>
								<th field="staticRowNum" width="10">统计信息行数</th>
								<th field="staticSql" width="10">统计信息SQLID</th>
								<th field="staticCcolumn" width="30">统计信息列名</th>
								<th field="toolHSqlId" width="10">按时SQLID</th>
								<th field="toolDSqlId" width="10">按天SQLID</th>
								<th field="toolWSqlId" width="10">按周SQLID</th>
								<th field="toolMSqlId" width="10">按月SQLID</th>
								<th field="toolQSqlId" width="10">按季SQLID</th>
								<th field="toolYSqlId" width="10">按年SQLID</th>
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
                    onclick="editpublic()">编辑</a>
		</div>
	</div>

<!-- 对话框 -->
    <div id="publicUpdateDialog" class="easyui-dialog"
    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
        buttons="#public-buttons-update">
        <form id="publicForm2" class="form-horizontal" method="post" novalidate>
            <input name="id" type="hidden" id="id">
            <div class="form-group">
                <label>报表标志:</label>
                <input type="text" name="toolFlag" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>报表标题:</label>
                <input type="text" name="toolTitle" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>中文列名:</label>
                <input type="text" name="toolCColumn" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>英文列名:</label>
                <input type="text" name="toolEColumn" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>统计信息行数:</label>
                <input type="text" name="staticRowNum" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>统计信息SQLID:</label>
                <input type="text" name="staticSql" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>统计信息列名:</label>
                <input type="text" name="staticCcolumn" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>汇总列:</label>
                <input type="text" name="toolGather" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>列格式化:</label>
                <input type="text" name="toolFormat" class="form-control"  style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>时SQLID:</label>
                <input type="text" name="toolHSqlId" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>日SQLID:</label>
                <input type="text" name="toolDSqlId" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>周SQLID:</label>
                <input type="text" name="toolWSqlId" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>月SQLID:</label>
                <input type="text" name="toolMSqlId" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>季SQLID:</label>
                <input type="text" name="toolQSqlId" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
            <div class="form-group">
                <label>年SQLID:</label>
                <input type="text" name="toolYSqlId" class="form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="public-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('publicForm2','publicUpdateDialog','publicList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#publicUpdateDialog').dialog('close')">关闭</a>
    </div>

	<script type="text/javascript">
		$('#publicList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
		
		function editpublic(){
            var row = $('#publicList').datagrid('getSelected');
            updateObject('编辑公共信息','${pageContext.request.contextPath}/public/updatePublic.htm','publicList','publicUpdateDialog', 'publicForm2');
        };
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>