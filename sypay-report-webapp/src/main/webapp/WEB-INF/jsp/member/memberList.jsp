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
<title>用户管理</title>
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
						<table width="100%" class="queryFormPanel">
							<tbody style="vertical-align: top;">
								<tr>
									<td><span class="query-item-inline"> <label>登录帐号：</label>
											<input type="text" class="form-control" style="width: 180px"
											id="accNoCriteria" autocomplete="off" />
									</span> <span class="query-item-inline"> <label>用户名：</label> <input
											type="text" class="form-control" style="width: 180px"
											id="nameCriteria" autocomplete="off" />
									</span>
<!-- 										<div class="query-item-inline"> -->
<!-- 											<label>组别：</label> -->
<!-- 											<div class="btn-group inline-box"> -->
<!-- 												<button type="button" -->
<!-- 													class="btn btn-default dropdown-toggle" -->
<!-- 													data-toggle="dropdown"> -->
<!-- 													所有组别 <span class="caret"></span> -->
<!-- 												</button> -->
<!-- 												<ul class="dropdown-menu checklist" -->
<!-- 													data-model="multi-select" role="menu" id="groupNameList" -->
<!-- 													style="width: 220px"></ul> -->
<!-- 											</div> -->
<!-- 										</div></td> -->
									<td width="100">
										<button id="member-search" class="btn btn-primary btn-w90">查询</button>
									</td>
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									<td><span class="query-item-inline"> <label>已选组别：</label> -->
<!-- 											<span id="queryItemShow" class="select-label"> <span -->
<!-- 												class="body"></span> -->
<!-- 										</span> -->
<!-- 									</span></td> -->
<!-- 									<td width="100"> -->
<!-- 										<button class="btn btn-danger btn-w90" style="margin: 10px 0;" -->
<!-- 											onclick="location.reload()">清空条件</button> -->
<!-- 									</td> -->
								</tr>
							</tbody>
						</table>
					</div> <!--左侧导航 End --> <!-- 数据列表 -->
					<table id="memberList"
						url="${pageContext.request.contextPath}/member/findMemberListByCriteria.htm"
						toolbar="#toolbar" rownumbers="true"
						data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fit:true,fitColumns:'true',pagination:'true',loadMsg:'数据加载中...'">
						<thead>
							<tr>
								<th field="id" data-options="hidden:'true'"></th>
								<th field="accNo" width="20" sortable="true">登录帐号</th>
								<th field="name" width="20" sortable="true">用户名称</th>
								<!-- <th field="casType" width="50" sortable="true" formatter="formatCasType">类型</th> -->
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


	<!-- tool -->
	<div id="toolbar">
       <c:if test="${isPerAdmin}"> 
		<div style="margin-bottom: 5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="addObject('新建人员','${pageContext.request.contextPath}/member/addOrUpdateMember.htm','memberDialog', 'memberForm')">新建</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit"
				plain="true"
				onclick="updateObject('编辑人员','${pageContext.request.contextPath}/member/addOrUpdateMember.htm','memberList','memberDialog2', 'memberForm2')">编辑</a>
			<a href="#" class="easyui-linkbutton" plain="true"
				onclick="showDetail('人员详情', 'memberList','memberDialog2', 'memberForm2')">详情</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
				plain="true"
				onclick="deleteObject('memberList', '${pageContext.request.contextPath}/member/deleteMember.htm', false)">删除</a>
			<a href="#" class="easyui-linkbutton" plain="true"
				onclick="resetPassword()">重置密码</a> 
<!-- 				<a href="#" -->
<!-- 				class="easyui-linkbutton" plain="true" -->
<%-- 				onclick="distributeTempResource('${pageContext.request.contextPath}/memberTempPriv/distriTempResource.htm')">分配临时权限</a> --%>
		</div>
       </c:if>
	</div>

	<!-- 对话框 -->
	<div id="memberDialog" class="easyui-dialog"
		data-options="width : 550, closable : true, modal : true, closed : true, shadow : false"
		buttons="#member-buttons">
		<form id="memberForm" class="form-horizontal" method="post" novalidate>
			<input name="id" type="hidden">
			<div class="form-group">
				<label>登录帐号:</label> <input name="accNo" class="easyui-validatebox"
					required="true" style="width: 240px; height: 24px"
					data-options="validType:'accNo(this.value, new Array(1,30))'">
			</div>

			<div class="form-group">
				<label>新密码:</label> <input name="password" id="password"
					type="password" class="easyui-validatebox" required="true"
					style="width: 240px; height: 24px"
					data-options="validType:'minLength[3]'">
			</div>
			<div class="form-group">
				<label>用户名称:</label> <input name="name" class="easyui-validatebox"
					required="true" style="width: 240px; height: 24px">
			</div>
			<div class="form-group">
				<label>状态:</label> <input name="status" type="radio" required="true"
					value="1" checked="checked">正常&nbsp;&nbsp;&nbsp;&nbsp; <input
					name="status" type="radio" required="true" value="0">停用
			</div>
			<div class="form-group">
				<label>用户组:</label> <input class="easyui-combobox" name="groupCode"
					style="width: 253px; height: 30px"
					data-options="editable:false,url:'${pageContext.request.contextPath}/group/findAllGroupNames.htm',method:'get',valueField:'groupCode',textField:'groupName',panelHeight:'auto'" />
			</div>
		</form>
	</div>
	<div class="dialog_btnBox" id="member-buttons">
		<a href="javascript:void(0)" class="btn btn-primary"
			onclick="saveObject('memberForm','memberDialog','memberList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default"
			onclick="javascript:$('#memberDialog').dialog('close')">关闭</a>
	</div>

	<!-- 编辑人员所使用的对话框 -->
	<div id="memberDialog2" class="easyui-dialog"
		data-options="width : 550, closable : true, modal : true, closed : true, shadow : false"
		buttons="#create-member-buttons">
		<form id="memberForm2" class="form-horizontal" method="post"
			novalidate>
			<input name="id" type="hidden">
			<div class="form-group">
				<label>登录帐号:</label> <input name="accNo" class="easyui-validatebox"
					required="true" style="width: 240px; height: 24px"
					data-readonly="edit"
					data-options="validType:'accNo(this.value, [1,30,\'${pageContext.request.contextPath}/member/isAccNoExists.htm\'])'">
			</div>
			<div class="form-group">
				<label>用户名称:</label> <input name="name" class="easyui-validatebox"
					required="true" style="width: 240px; height: 24px">
			</div>
			<div class="form-group">
				<label>状态:</label> <input name="status" type="radio" required="true"
					value="1" checked="checked">正常&nbsp;&nbsp;&nbsp;&nbsp; <input
					name="status" type="radio" required="true" value="0">停用
			</div>
			<div class="form-group">
				<label>用户组:</label> <input class="easyui-combobox" name="groupCode"
					style="width: 253px; height: 30px"
					data-options="editable:false,url:'${pageContext.request.contextPath}/group/findAllGroupNames.htm',method:'get',valueField:'groupCode',textField:'groupName',panelHeight:'auto'" />
			</div>
		</form>
	</div>
	<div class="dialog_btnBox" id="create-member-buttons">
		<a href="javascript:void(0)" class="btn btn-primary"
			onclick="saveObject('memberForm2','memberDialog2','memberList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default"
			onclick="javascript:$('#memberDialog2').dialog('close')">关闭</a>
	</div>

	<!-- 对话框 -->
	<div id="distriTempPrivDialog" class="easyui-dialog"
		data-options="width : 450, height: 420, closable : true, modal : true, closed : true, shadow : false"
		buttons="#distriTempPriv-buttons">
		<ul id="distriTempPrivTree" style="padding: 10px 0 0 30px"></ul>
	</div>
	<div id="distriTempPriv-buttons">
		<a href="javascript:void(0)" class="btn btn-primary" iconCls="icon-ok"
			onclick="saveMemberTempResource('${pageContext.request.contextPath}/memberTempPriv/saveMemberTempPriv.htm', 'roleList')">保存</a>
		<a href="javascript:void(0)" class="btn btn-default"
			iconCls="icon-cancel"
			onclick="javascript:$('#distriTempPrivDialog').dialog('close')">关闭</a>
	</div>

	<script type="text/javascript">
		$('#memberList').datagrid(
				{
					onLoadSuccess : function() {
						$('.datagrid-body:eq(1)').css({
							'overflow-x' : 'hidden',
							'overflow-y' : 'auto'
						});
						$('.datagrid-btable td:last-child').css(
								'padding-right', '30px');
					}
				});

		function resetPassword(that) {
			var row = $('#memberList').datagrid('getSelected');
			if (!row) {
				tip('请选择一行！');
			}
			if (confirm('确定要重置密码？')) {
				var memberId = row.id;
				$
						.ajax({
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
						})
			} else {
				return;
			}
		}

		function searchMembersByName() {
			var name = $('#name_search').val().replace(/(^\s*)|(\s*$)/g, "");
			if (name.length > 0) {
				var url = "${pageContext.request.contextPath}/member/findMemberListByName.htm";
				$.post(url, {
					"name" : name
				}, function(data) {
					$('#memberList').datagrid({
						"url" : url,
						queryParams : {
							"name" : name
						}
					});
				});
			}
		}

		function changeOriginalPassword(formname, type) {
			var target = formname.find('[name=originalPassword]')
			if (type === "add") {
				target.validatebox({
					required : false
				});
				target.parent().hide();
			} else if (type === "update") {
				target.validatebox({
					required : true
				});
				target.parent().show();
			}
		}

		function queryItemShow(id, li) {
			var queryItemShow = $('#queryItemShow .body');
			var num = li.attr('data-value');
			var isSelected = $('html').hasClass('lt-ie9') ? li
					.hasClass('selected') : !li.hasClass('selected');
			if (isSelected) {
				queryItemShow
						.append('<span data-linkage='+num+' class="cell wipe">'
								+ li.text() + '</span>');
			} else {
				queryItemShow.find('.cell[data-linkage=' + num + ']').remove();
			}
			queryItemShow.find('.wipe').bind(
					'click',
					function() {
						var num = $(this).attr('data-linkage');
						$(id).find('li[data-value=' + num + ']').removeClass(
								'selected');
						$(this).remove();
					});
		}

		// 获取下拉框中选中的门店
		function getGroupCodes(id) {
			// 选中门店列表
			var groupCodes = "";
			$(id).find('li.selected').each(function(index) {
				groupCodes = groupCodes + $(this).attr("data-value") + ",";
			});
			if (groupCodes.length > 0) {
				groupCodes = groupCodes.substring(0, groupCodes.length - 1);
			}
			return groupCodes;
		}

		// 查询
		function search(callback) {
			// 获取 datagrid 的查询参数
			var params = $('#memberList').datagrid('options').queryParams;

			// 选中门店列表
			var queryGroupCode = getGroupCodes('#groupNameList');
			params['groupCodeListStr'] = queryGroupCode;
			params['accNo'] = $('#accNoCriteria').val();
			params['name'] = $('#nameCriteria').val();

			//执行回调
			callback();
		};

		/**
		 * =================== 页面加载完成后执行函数 =====================================
		 */

		// 根据上次查询条件重置查询条件输入框
		function reset() {
			$("#startDate").datebox("setValue", $("#hiddenStartDate").val());
			$("#endDate").datebox("setValue", $("#hiddenEndDate").val());
			$('#waterNo').val($("#hiddenWaterNo").val());
			$('#iboxId').val($("#hiddenIboxId").val());
			$('#cardNo').val($("#hiddenCardNo").val());
			$("#tranTypeSelect").combo('setValue', $("#hiddenTranType").val());
			$("#tranStatusSelect").combo('setValue',
					$("#hiddenTranStatus").val());
			$("#payMethodSelect")
					.combo('setValue', $("#hiddenPayMethod").val());

			var storeId = $("#hiddenStore").val();
			if (undefined != storeId && "" != storeId) {
				var storeIds = storeId.split(",");
				$("#StoresSelect").combobox("setValues", storeIds);
			}

			var memberId = $("#hiddenMember").val();
			if (undefined != memberId && "" != memberId) {
				var memberIds = memberId.split(",");
				$("#memberSelect").combobox("setValues", memberIds);
			}
		}

		$(function() {
			//绑定下拉筛选
			$("[data-toggle=dropdown]").click(dropdownToggle);

			//载入筛选店铺名
			$
					.post(
							"${pageContext.request.contextPath}/group/findAllGroupNames.htm",
							function(data) {
								data = eval('(' + data + ')');
								$
										.each(
												data,
												function(key, val) {
													var $li = $('<li data-value="'+val.groupCode+'">'
															+ val.groupName
															+ '</li>');
													$li
															.click(function() {
																queryItemShow(
																		'#groupNameList',
																		$(this));
															});
													$("#groupNameList").append(
															$li);
												});
							});

			//开始查询
			$("#member-search").click(
					function() {
						search(function(merchantIds) {
							$('#memberList').datagrid('getPager').pagination(
									'select', 1);
						});
					});

		});

		function distributeTempResource(url) {
			var row = $('#memberList').datagrid('getSelected');
			if (!row) {
				tip('请选择一行');
				return;
			}
			$('#distriTempPrivDialog').dialog('open').dialog('setTitle',
					"分配临时权限").parent().addClass('dialog');
			$('#distriTempPrivTree').tree({
				animate : true,
				cascadeCheck : true,
				checkbox : true,
				url : url + "?memberId=" + row.id
			});
		}

		function saveMemberTempResource(url) {
			var nodes = $('#distriTempPrivTree').tree('getChecked');
			var ids = '';
			for (var i = 0; i < nodes.length; i++) {
				if (ids != '') {
					ids += ',';
				}
				ids += nodes[i].id;
			}
			var nodes2 = $('#distriTempPrivTree').tree('getChecked',
					'indeterminate');
			for (var i = 1; i < nodes2.length; i++) {
				if (ids != '') {
					ids += ',';
				}
				ids += nodes2[i].id;
			}
			var row = $('#memberList').datagrid('getSelected');
			var params = {
				memberId : row.id,
				resourceIds : ids
			};
			$.post(url, params, function() {
				$('#distriTempPrivDialog').dialog('close');
				var ids = '';
				$('#memberList').datagrid('reload');
			});
		}
	</script>


	<%@include file="../fragments/footer.jsp"%>
</body>
</html>