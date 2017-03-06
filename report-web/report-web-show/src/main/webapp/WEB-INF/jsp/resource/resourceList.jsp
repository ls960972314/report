<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<title>资源登录</title>
<jsp:include page="../fragments/script.jsp" />
<script type="text/javascript">
	function setResourceType(value) {
		if (value == 'module') {
			return "系统模块";
		} else if(value=='menu') {
			return "菜单";
		} else if(value=='op') {
			return "操作";
		} else if(value=='select') {
			return "页面下拉框";
		} else if(value=='button') {
			return "按钮";
		}
	}
	
	function setActionInfo(value) {
		if (value == 'no') {
			return "";
		} else {
			return value;
		}
	}
	
	function beforeExpandAction(row) {
		var rowid = row.id;
		var params = {"parentId": rowid};
		var url = "";
		// ajax 请求
		$.post(url, params, function(result) {
			// 数据贴上
			$('#resourceList').treegrid('append',{
				parent: rowid,  
				data: result
			});
		});
	}
	
	$(function(){
		$('#cc').combobox({
		    url:'combobox_data.json',
		    valueField:'id',
		    textField:'text'
		});
	});
	
</script>
</head>
<body>
        <%@include file="../fragments/bodyHeader.jsp"%>
    	<table class="container">
	        <tbody>
		        <tr>
		            <td width="190" valign="top">
		                <!--左侧导航 Start-->
		                <%@include file="../fragments/leftMenu.jsp"%>
		            </td>
		            <!------ 主体内容开始 -------->
		            <td valign="top">
						<!-- 数据列表  ,onLoadSuccess: function () {  $('#resourceList').treegrid('collapseAll');}-->
						<table id="resourceList" class="easyui-treegrid"
							url="<%=basePath%>/resource/findResourceList.htm" toolbar="#toolbar"
							data-options="loadMsg:'数据加载中',rownumbers:true,animate:true,singleSelect:true,fitColumns:'true',idField : 'id',treeField : 'name'">
							<thead>
								<tr>
									<th field="id" data-options="hidden:'true'"></th>
									<th field="name" width="70">资源名称</th>
									<th field="resourceType" width="30" formatter="setResourceType">资源类型</th>
									<th field="resourceAction" width="50" formatter="setActionInfo">资源Action</th>
									<th field="sysCode" width="30">菜单类型</th>
									<th field="orderBy" width="10">排序</th>
									<th field="description" width="55">说明</th>
									<th field="resourceCode" width="32">资源编码</th>
									<th field="createTime" width="30" formatter="formatDate">创建时间</th>
									<th field="updateTime" width="30" formatter="formatDate">更新时间</th>
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
					onclick="addResourceObject('新建资源','<%=basePath%>/resource/addOrUpdate.htm','resourceDialog', 'resourceForm', '<%=basePath%>/resource/findTreeMenu.htm','topResource','tree')">新建</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="updateObject('编辑资源','<%=basePath%>/resource/addOrUpdate.htm','resourceList','resourceDialog', 'resourceForm','<%=basePath%>/resource/findTreeMenu.htm','topResource','tree')">编辑</a>
				<a href="#" class="easyui-linkbutton"  plain="true"
					onclick="showDetail('资源详情','resourceList','resourceDialog', 'resourceForm', '<%=basePath%>/resource/findTreeMenu.htm','topResource','tree')">详情</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="deleteResourceFun()">删除</a>
		</div>
	</div>
	<!-- 对话框 -->
	<div id="resourceDialog" class="easyui-dialog"
		style="width: 650px; height: auto; padding-top: 5px" closed="true" modal="true" shadow="false"
		buttons="#role-buttons">
		<form id="resourceForm" method="post" class="form-horizontal" novalidate>
			<input name="id" type="hidden">
			<div class="form-group">
				<label>菜单类型:</label>
                <select  name="sysCode"
                    class="easyui-combobox" required="true" editable="false"
                    style="width: 370px;">
                        <option value="omp">报表菜单(omp)</option>
                        <option value="per">权限菜单(per)</option>
                </select>
			</div>
			<div class="form-group">
				<label>资源Action:</label>
				<input name="resourceAction" class="easyui-validatebox"
					style="width: 370px">
			</div>
			<div class="form-group">
				<label>资源编码:</label>
				<input name="resourceCode" class="easyui-validatebox"  style="width: 370px">
			</div>
			<div class="form-group">
				<label>资源名称:</label>
				<input name="name" class="easyui-validatebox"
						required="true" style="width: 370px">
			</div>
			<div class="form-group">
				<label>所属导航:</label>
				<input class="easyui-combotree" name="pId"
						editable="false" id="topResource" style="width: 370px;"  data-options="valueField:'id',textField:'name'"/>
			</div>
			<div class="form-group">
				<label>资源类型:</label>
				<select id="resourceType" name="resourceType"
					class="easyui-combobox" required="true" editable="false"
					style="width: 370px;">
						<option value="module">系统模块</option>
						<option value="menu">菜单</option>
						<option value="op">操作</option>
						<option value="select">页面下拉框</option>
						<option value="button">按钮</option>
				</select>
			</div>
			<div class="form-group">
				<label>顺序:</label>
				<input name="orderBy" class="easyui-numberspinner" style="width:80px;"></input>&nbsp;&nbsp;&nbsp;<span style="color: red">注意修改顺序</span>
			</div>
			<div class="form-group">
				<label>说明:</label>
				<textarea name="description" placeholder="请填写说明" cols="70" rows="7" style="width: 370px;" data-options="validType:'length[1,80]'"></textarea>
			</div>
		</form>
	</div>
	<div class="dialog_btnBox dialog-button" id="role-buttons">
		<a href="javascript:void(0)" class="btn btn-primary"
			iconCls="icon-ok"
			onclick="saveObject('resourceForm','resourceDialog','resourceList','tree')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default"
			iconCls="icon-cancel"
			onclick="javascript:$('#resourceDialog').dialog('close')">关闭</a>
	</div>


	<script type="text/javascript">
			
		function addResourceObject(title,url,dialog,form,treemenu,topResourceId,type) {
			addObject(title,url,dialog, form, treemenu,topResourceId,type);
			var row = $('#resourceList').datagrid('getSelected');
			if(row) {
				$('#' + form).form('load', {
					sysCode: row.sysCode,
					pId: row.id,
					resourceType: 'menu',
					orderBy:1
				});
			} else {
				$('#' + form).form('load', {
					sysCode:'per',
					resourceType: 'menu',
					orderBy:1
				});
			}
		}
		function deleteResourceFun() {
			var row = $('#resourceList').datagrid('getSelected');
			deleteObject('resourceList', '<%=basePath%>/resource/del.htm',true);
		}
	</script>
	<%@include file="../fragments/footer.jsp"%>
</body>
</html>

