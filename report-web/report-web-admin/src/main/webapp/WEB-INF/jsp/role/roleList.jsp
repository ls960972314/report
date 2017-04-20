<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<jsp:include page="../fragments/script.jsp" />
<%@include file="../fragments/bodyHeader.jsp"%>
    <table class="container" style="height: 592px;">
        <tbody>
	        <tr>
	        	<td width="190" valign="top">
	                <!--左侧导航 Start-->
	                <%@include file="../fragments/leftMenu.jsp"%>
	            </td>
	            <td valign="top">
		            <div class="extendDiv-toggle">
			            <form id="searchForm">
			                <table width="100%" class="queryFormPanel">
			                    <tbody style="vertical-align:top;">
				                    <tr>
				                        <td>
				                            <span class="query-item-inline">
				                                <label>角色编码：</label>
				                                <input type="text" class="form-control" style="width:180px" name="roleCode" autocomplete="off" />
				                            </span>
				                            <span class="query-item-inline">
				                                <label>角色名：</label>
				                                <input type="text" class="form-control" style="width:180px" name="roleName" autocomplete="off" />
				                            </span>
				                        </td>
				                        <td>
				                        	<a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('roleList', 'searchForm');">查询</a>
	                          				<a class="btn btn-danger btn-w90" href="javascript:void(0);" onclick="clearSearch('roleList', 'searchForm');">清空条件</a>
	                          			</td>
				                    </tr>
			                    </tbody>
			                </table>
			            </form>
		            </div>
	            
					<!-- 数据列表 -->
					<table id="roleList" 
						url="${pageContext.request.contextPath}/role/findRoleListByCriteria.htm" toolbar="#toolbar"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fitColumns:'true',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="id" data-options="hidden:'true'"></th>
								<th field="roleCode" width="60" sortable="true">角色编码</th>
								<th field="name" width="50" sortable="true">角色名</th>
<!-- 								<th field="sysCode" width="50" sortable="true">系统编码</th> -->
								<th field="status" width="30px"  formatter="commonformatterObjectStatus" sortable="true">状态</th>
								<th field="description" width="50">描述</th>
								<th field="createrAccNo" width="50">创建人账号</th>
								<th field="modifierAccNo" width="50">修改人账号</th>
								<th field="createTime" width="50" sortable="true" formatter="formatDate">创建时间</th>
								<th field="updateTime" width="50" sortable="true" formatter="formatDate">更新时间</th>
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
				onclick="addObject('新建角色','${pageContext.request.contextPath}/role/addOrUpdate.htm?roleType=0','roleDialog', 'roleForm','${pageContext.request.contextPath}/system/findSystemList.htm','selectSysCode')">新建</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
					plain="true"
					onclick="updateObject('编辑角色','${pageContext.request.contextPath}/role/addOrUpdate.htm?roleType=0','roleList','roleDialog', 'roleForm')">编辑</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove"	plain="true"
					onclick="deleteRole('roleList', '${pageContext.request.contextPath}/role/del.htm')">删除</a>
				<a href="#" class="easyui-linkbutton" plain="true" onclick="showDetail('角色详情','roleList','roleDialog', 'roleForm')">详情</a>
				|<a href="#" class="easyui-linkbutton" plain="true"
					onclick="distriResources(1,'${pageContext.request.contextPath}/resource/getPartialDistriResources.htm', '查看资源')">查看资源</a>
				|<a href="#" class="easyui-linkbutton" plain="true"
					onclick="distriResources(2,'${pageContext.request.contextPath}/resource/getDistriResources.htm', '分配权限')">分配资源</a>
		</div>
	</div>
	
	<!-- 对话框 -->
	<div id="roleDialog" class="easyui-dialog"
    data-options="width : 480, closable : true, modal : true, closed : true, shadow : false"
		buttons="#role-buttons">
		<form id="roleForm" class="form-horizontal" method="post">
			<input name="id" type="hidden">
            <div class="form-group">
                <label>角色编码:</label>
                <input name="roleCode" class="easyui-validatebox" required="true" validType="roleCode" style="width: 240px" data-readonly="edit" data-options="validType:'roleCode'">
            </div>
            <div class="form-group">
                <label>角色名称:</label>
                <input name="name" class="easyui-validatebox" required="true" style="width: 240px" data-options="validType:'roleName'">
            </div>
            <div class="form-group">
                <label>状态:</label>
                <input name="status" type="radio" required="true" value="1" checked="checked">启用&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="status" type="radio" required="true" value="0">停用
            </div>
            <div class="form-group">
                <label>描　述:</label>
                <textarea name="description" style="height: 100px; width: 240px"></textarea>
            </div>
		</form>
	</div>
	<div class="dialog_btnBox" id="role-buttons">
		<a href="javascript:void(0)" class="btn btn-primary"
			onclick="saveObject('roleForm','roleDialog','roleList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-primary"
			onclick="javascript:$('#roleDialog').dialog('close')">关闭</a>
	</div>


	<!-- 对话框 -->
	<div id="distriDialog" class="easyui-dialog"
		data-options="width : 450, height: 420, closable : true, modal : true, closed : true, shadow : false"
		buttons="#distri-buttons">
		<ul id="distriTree" style="padding:10px 0 0 30px"></ul>
	</div>
	<div id="distri-buttons">
		<a href="javascript:void(0)" class="btn btn-primary" iconCls="icon-ok"
			onclick="saveRoleResource('${pageContext.request.contextPath}/role/saveRoleResources.htm', 'roleList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default" iconCls="icon-cancel"
			onclick="javascript:$('#distriDialog').dialog('close')">关闭</a>
	</div>
	<div id="distriDialog2" class="easyui-dialog"
		data-options="width : 450, height: 420, closable : true, modal : true, closed : true, shadow : false"
		buttons="#distri-buttons2">
		<ul id="distriTree2"></ul>
	</div>
	<div id="distri-buttons2">
		<a href="javascript:void(0)" class="btn btn-default"
			iconCls="icon-cancel"
			onclick="javascript:$('#distriDialog2').dialog('close')">关闭</a>
	</div>
	
	<script type="text/javascript">
		$('#roleList').datagrid({
	        onLoadSuccess:function(){
	            $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
	            $('.datagrid-btable td:last-child').css('padding-right','30px');
	        }
	    });
	
		function deleteRole(list, url) {
			var row = $('#' + list).datagrid('getSelected');
			if (!row) {
				tip('请选择一行');
				return;
			}
			deleteObject(list, url);
		}
		function distriResources(type,url, title) {
			var row = $('#roleList').datagrid('getSelected');
			if (!row) {
				tip('请选择一行');
				return;
			}
			var distriTree = "distriTree";
			var dialogName = "distriDialog";
			if(type == 1) {
				distriTree = "distriTree2";
				dialogName = "distriDialog2";
			} 
			$('#' + dialogName).dialog('open').dialog('setTitle', title).parent().addClass('dialog');
			$('#' + distriTree).tree({
				animate : true,
				cascadeCheck : true,
				checkbox : true,
				url : url + "?roleCode=" + row.roleCode
			});
		}

		var parentIds = "";
		// 保存授权
		function saveRoleResource(url, dg) {
			var nodes = $('#distriTree').tree('getChecked');
			var ids = '';
			for ( var i = 0; i < nodes.length; i++) {
				if (ids != '') {
					ids += ',';
				}
				ids += nodes[i].id;
			}
			var nodes2 = $('#distriTree').tree('getChecked', 'indeterminate');
			for ( var i = 0; i < nodes2.length; i++) {
				if (ids != '') {
					ids += ',';
				}
				ids += nodes2[i].id;
			}
			var row = $('#' + dg).datagrid('getSelected');
			var params = {
				resourceIds : ids,
				id : row.id
			};
			$.post(url, params, function() {
				$('#distriDialog').dialog('close');
				var ids = '';
				$('#' + dg).datagrid('reload');
			});
		}
	</script>
	<%@include file="../fragments/footer.jsp"%>
</body>
</html>

