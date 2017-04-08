<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@include file="../fragments/htmlHead.jsp"%>
<title>用户管理</title>
<jsp:include page="../fragments/script.jsp" />
</head>
<body style="overflow-y: hidden">
	<%@include file="../fragments/bodyHeader.jsp"%>
	<table class="container" style="height: 592px;">
		<tbody>
			<tr>
				<td width="190" valign="top">
					<%@include file="../fragments/leftMenu.jsp"%>
				</td>
				<!------ 主体内容开始 -------->
				<td valign="top">
					<div class="extendDiv-toggle">
						<table width="100%" class="queryFormPanel">
							<tbody style="vertical-align: top;">
								<tr>
									<td>
										<span class="query-item-inline"> 
											<label>登录帐号：</label>
											<input type="text" class="form-control" style="width: 180px" id="accNoCriteria" autocomplete="off" />
										</span> 
										<span class="query-item-inline">
											<label>用户名：</label>
											<input type="text" class="form-control" style="width: 180px" id="nameCriteria" autocomplete="off" />
										</span>
										<button id="member-search" class="btn btn-primary btn-w90">查询</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<table id="memberList" url="${pageContext.request.contextPath}/member/findMemberList.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'true',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="id" data-options="hidden:'true'"></th>
								<th field="accNo" width="20" sortable="true">登录帐号</th>
								<th field="name" width="20" sortable="true">用户名称</th>
								<th field="groupName" width="30" sortable="true"
									formatter="formatGroup">组别</th>
								<th field="status" width="10" sortable="true"
									formatter="commonformatterObjectStatus">状态</th>
								<th field="createrAccNo" width="20" sortable="true">创建人帐号</th>
								<th field="modifierAccNo" width="20" sortable="true">修改人帐号</th>
								<th field="createTime" width="20" sortable="true" formatter="formatDate">创建时间</th>
								<th field="updateTime" width="20" sortable="true" formatter="formatDate">修改时间</th>
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</tbody>
	</table>


	<div id="toolbar">
		<div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addObject('新建人员','${pageContext.request.contextPath}/member/addOrUpdateMember.htm','memberDialog', 'memberForm')">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
				onclick="updateObject('编辑人员','${pageContext.request.contextPath}/member/addOrUpdateMember.htm','memberList','memberDialog2', 'memberForm2')">编辑</a>
			<a href="#" class="easyui-linkbutton" plain="true"
				onclick="showDetail('人员详情', 'memberList','memberDialog2', 'memberForm2')">详情</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
				onclick="deleteObject('memberList', '${pageContext.request.contextPath}/member/deleteMember.htm', false)">删除</a>
			<a href="#" class="easyui-linkbutton" plain="true"
				onclick="resetPassword()">重置密码</a> 
		</div>
	</div>

	<!-- 新增用户对话框 -->
	<div id="memberDialog" class="easyui-dialog" data-options="width : 550, closable : true, modal : true, closed : true, shadow : false" buttons="#member-buttons">
		<form id="memberForm" class="form-horizontal" method="post" novalidate>
			<input name="id" type="hidden">
			<div class="form-group">
				<label>登录帐号:</label> 
				<input name="accNo" class="easyui-validatebox" required="true" style="width: 240px; height: 24px"
					data-options="validType:'accNo(this.value, new Array(1,30))'">
			</div>

			<div class="form-group">
				<label>新密码:</label>
				<input name="password" id="password" type="password" class="easyui-validatebox" required="true"
					style="width: 240px; height: 24px" data-options="validType:'minLength[3]'">
			</div>
			
			<div class="form-group">
				<label>用户名称:</label> 
				<input name="name" class="easyui-validatebox" required="true" style="width: 240px; height: 24px">
			</div>
			
			<div class="form-group">
				<label>状态:</label>
				<input name="status" type="radio" required="true" value="1" checked="checked"/>正常
				<input name="status" type="radio" required="true" value="0" style="margin-left:5px;"/>停用
			</div>
			
			<div class="form-group">
				<label>用户组:</label>
				<input class="easyui-combobox" name="groupCode" style="width: 253px; height: 30px"
					data-options="editable:false,url:'${pageContext.request.contextPath}/group/findAllGroupNames.htm',method:'get',valueField:'groupCode',textField:'groupName',panelHeight:'auto'" />
			</div>
		</form>
	</div>
	<div class="dialog_btnBox" id="member-buttons">
		<a href="javascript:void(0)" class="btn btn-primary" onclick="saveObject('memberForm','memberDialog','memberList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#memberDialog').dialog('close')">关闭</a>
	</div>

	<!-- 编辑人员所使用的对话框 -->
	<div id="memberDialog2" class="easyui-dialog" data-options="width : 550, closable : true, modal : true, closed : true, shadow : false" buttons="#create-member-buttons">
		<form id="memberForm2" class="form-horizontal" method="post" novalidate>
			<input name="id" type="hidden">
			<div class="form-group">
				<label>登录帐号:</label> 
				<input name="accNo" class="easyui-validatebox" required="true" style="width: 240px; height: 24px"
					data-readonly="edit" data-options="validType:'accNo(this.value, [1,30,\'${pageContext.request.contextPath}/member/isAccNoExists.htm\'])'">
			</div>
			
			<div class="form-group">
				<label>用户名称:</label>
				<input name="name" class="easyui-validatebox"
					required="true" style="width: 240px; height: 24px">
			</div>
			
			<div class="form-group">
				<label>状态:</label>
				<input name="status" type="radio" required="true" value="1" checked="checked">正常&nbsp;&nbsp;&nbsp;&nbsp; <input
					name="status" type="radio" required="true" value="0">停用
			</div>
			
			<div class="form-group">
				<label>用户组:</label>
				<input class="easyui-combobox" name="groupCode" style="width: 253px; height: 30px"
					data-options="editable:false,url:'${pageContext.request.contextPath}/group/findAllGroupNames.htm',method:'get',valueField:'groupCode',textField:'groupName',panelHeight:'auto'" />
			</div>
		</form>
	</div>
	<div class="dialog_btnBox" id="create-member-buttons">
		<a href="javascript:void(0)" class="btn btn-primary" onclick="saveObject('memberForm2','memberDialog2','memberList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#memberDialog2').dialog('close')">关闭</a>
	</div>


	<script type="text/javascript">
		$(function() {
			$("[data-toggle=dropdown]").click(dropdownToggle);
			$("#member-search").click(function() {
				search( function(merchantIds) {
					$('#memberList').datagrid('getPager').pagination('select', 1);
				});
			});
		});
		
		$('#memberList').datagrid({onLoadSuccess: function() {
			$('.datagrid-body:eq(1)').css({
				'overflow-x' : 'hidden',
				'overflow-y' : 'auto'
			});
			$('.datagrid-btable td:last-child').css('padding-right', '30px');
		}});

		function resetPassword(that) {
			var row = $('#memberList').datagrid('getSelected');
			if (!row) {
				tip('请选择一行！');
			}
			if (confirm('确定要重置密码？')) {
				var memberId = row.id;
				$.ajax({
					type : 'post',
					url : '${pageContext.request.contextPath}/member/resetPassword.htm',
					data : {
						"memberId" : memberId
					},
					success : function(data) {
						if (data.status) {
							tip('成功将密码重置为:123456');
						} else {
							tip(data.errorInfo);
						}
					}
				});
			} else {
				return;
			}
		}


		function search(callback) {
			var params = $('#memberList').datagrid('options').queryParams;
			params['accNo'] = $('#accNoCriteria').val();
			params['name'] = $('#nameCriteria').val();
			callback();
		};
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>