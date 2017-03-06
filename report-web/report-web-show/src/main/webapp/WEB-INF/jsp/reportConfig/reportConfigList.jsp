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
<title>报表跑批动态配置管理</title>
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
                                        <label>rptCode：</label>
                                        <input type="text" class="form-control" style="width:180px" name="rptCode" autocomplete="off" />
                                        </span>
                                        <span class="query-item-inline">
                                        <label>配置名：</label>
                                        <input type="text" class="form-control" style="width:180px" name="rptName" autocomplete="off" />
                                        </span>
                                    </td>
                                    <td>
                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('reportConfigList', 'searchForm');">查询</a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="reportConfigList"
						url="${pageContext.request.contextPath}/reportConfig/findReportConfigList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'false',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="rptCode" width="50">rptCode</th>
								<th field="rptName" width="100">配置名</th>
								<th field="rptTableName" width="50">汇总表</th>
								<th field="rptColName" width="50">时间列</th>
								<th field="rptType" width="50">统计维度</th>
								<th field="rptSql" width="50">sql脚本</th>
								<th field="rptStatus" width="50">是否可用</th>
								<th field="rptVer" width="50">版本号</th>
								<th field="updateRemark" width="50">创建/修改</th>
								<th field="createTime" width="50">创建时间</th>
								<th field="updateTime" width="50">修改时间</th>
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
	                    onclick="addObject('新建','${pageContext.request.contextPath}/reportConfig/addReportConfig.htm','reportConfigDialog', 'reportConfigForm')">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                    onclick="editReportConfig()">编辑</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true"
				onclick="deleteRptConfig('reportConfigList', '${pageContext.request.contextPath}/reportConfig/deleteReportConfig.htm', false)">删除</a>
		</div>
	</div>
	<!-- 新建SQL -->
	<div id="reportConfigDialog" class="easyui-dialog"
	    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
	        buttons="#reportConfig-buttons">
        <form id="reportConfigForm" class="form-horizontal" method="post" novalidate>
            <div class="form-group">
                <label>rptCode:</label>
                <input type="text" name="rptCode" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>配置名:</label>
                <input type="text" name="rptName" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>汇总表</label>
                <input type="text" name="rptTableName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
			<div class="form-group">
                <label>时间列</label>
                <input type="text" name="rptColName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>            
            <div class="form-group">
                <label>统计维度</label>
                <input type="text" name="rptType" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>sql脚本</label>
                <input type="text" name="rptSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>是否可用</label>
                <input type="text" name="rptStatus" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>版本号</label>
                <input type="text" name="rptVer" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="reportConfig-buttons">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('reportConfigForm','reportConfigDialog','reportConfigList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#reportConfigDialog').dialog('close')">关闭</a>
    </div>


	<!-- 修改 -->
    <div id="reportConfigUpdateDialog" class="easyui-dialog"
    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
        buttons="#reportConfig-buttons-update">
        <form id="reportConfigForm2" class="form-horizontal" method="post" novalidate>
            <div class="form-group">
                <label>rptCode:</label>
                <input type="text" name="rptCode" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>配置名:</label>
                <input type="text" name="rptName" class="easyui-validatebox form-control" style="width: 240px;height: 24px; background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>汇总表</label>
                <input type="text" name="rptTableName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
			<div class="form-group">
                <label>时间列</label>
                <input type="text" name="rptColName" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>            
            <div class="form-group">
                <label>统计维度</label>
                <input type="text" name="rptType" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>sql脚本</label>
                <input type="text" name="rptSql" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>是否可用</label>
                <input type="text" name="rptStatus" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
            <div class="form-group">
                <label>版本号</label>
                <input type="text" name="rptVer" class="easyui-validatebox form-control" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true">
            </div>
        </form>
    </div>
    <div class="dialog_btnBox" id="reportConfig-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary"
            onclick="saveObject('reportConfigForm2','reportConfigUpdateDialog','reportConfigList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default"
            onclick="javascript:$('#reportConfigUpdateDialog').dialog('close')">关闭</a>
    </div>

	<script type="text/javascript">
		$('#reportConfigList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
		
		function editReportConfig(){
            var row = $('#reportConfigList').datagrid('getSelected');
            updateObject('编辑报表SQL','${pageContext.request.contextPath}/reportConfig/updateReportConfig.htm','reportConfigList','reportConfigUpdateDialog', 'reportConfigForm2');
        };
        /**
         * 删除
         * 
         * @param listname
         * @param url
         */
        function deleteRptConfig(listname, url, isTree) {
        	var row = $('#' + listname).datagrid('getSelected');
        	if (row) {
        		$.messager.confirm('删除', '确定要删除吗?', function(r) {
        			if (r) {
        				$.ajax({
        					type: "POST",
        					url: url,
        					data: {	rptCode : row.rptCode	},
        					success: function(result) {
        						if (result.status==1) {
        							if (isTree) {
        								$('#' + listname).treegrid('reload');
        							} else {
        								$('#' + listname).datagrid('reload'); // reload
        								// the user
        								// data
        							}
        							tip('操作成功');
        						} else {
        							tip(result.errorInfo);
        						}
        					},
        					error: function(){
        						tip('无法删除');
        					}
        				});
        			}
        		});
        	} else {
        		tip('请选择一行！');
        	}
        }
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>