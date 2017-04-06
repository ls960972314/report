<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@include file="../fragments/htmlHead.jsp"%>
<title>组别管理</title>
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
	                    <form id="searchForm">
	                        <table width="100%" class="queryFormPanel">
	                            <tbody style="vertical-align:top;">
	                                <tr>
	                                    <td>
	                                        <span class="query-item-inline">
	                                        	<label>组编码：</label>
	                                        	<input type="text" class="form-control" style="width:180px" name="groupCode" autocomplete="off" />
	                                        </span>
	                                        <span class="query-item-inline">
	                                        	<label>组名称：</label>
	                                        	<input type="text" class="form-control" style="width:180px" name="groupName" autocomplete="off" />
	                                        </span>
	                                        <span class="query-item-inline">
	                                        	<label>状态：</label>
		                                        <select  class="query-item-inline easyui-combobox" name="status" style="width:200px;height:31px">
			                                        <option value="">请选择</option>
			                                        <option value="1">有效</option>
			                                        <option value="0">无效</option>
		                                        </select>
	                                        </span>
	                                    </td>
	                                    <td>
	                                        <a class="btn btn-primary btn-w90" href="javascript:void(0);" onclick="searchFunc('groupList', 'searchForm');">查询</a>
	                                        <a class="btn btn-danger btn-w90" href="javascript:void(0);" onclick="clearSearch('groupList', 'searchForm');">清空条件</a>
	                                    </td>
	                                </tr>
	                            </tbody>
	                        </table>
	                    </form>
	               </div>
	                
	               <table id="groupList" url="${pageContext.request.contextPath}/group/findGroupList.htm" toolbar="#toolbar"
	                   data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fitColumns:'true',pagination:'true',loadMsg:'数据加载中...'">
	                   <thead>
	                       <tr>
	                           <th field="id" data-options="hidden:'true'"></th>
	                           <th field="groupCode" width="60px" sortable="true">组编码</th>
	                           <th field="groupName" width="60px" sortable="true">组名称</th>
	                           <th field="status" width="20px"  formatter="commonformatterObjectStatus">状态</th>
	                           <th field="createrAccNo" width="50px">创建人账号</th>
	                           <th field="modifierAccNo" width="50px">修改人账号</th>
	                           <th field="createTime" width="50px" formatter="formatDate">创建时间</th>
	                           <th field="updateTime" width="50px" formatter="formatDate">更新时间</th>
	                       </tr>
	                   </thead>
	               </table>
	            </td>
	        </tr>
        </tbody>
    </table>

    <div id="toolbar">
        <div style="margin-bottom: 5px">
	        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addObject('新建组','${pageContext.request.contextPath}/group/addOrUpdateGroup.htm','groupDialog', 'groupForm')">新建</a>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editGroup()">编辑</a>
	        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteObject('groupList', '${pageContext.request.contextPath}/group/deleteGroup.htm', false)">删除</a>
	        <a href="#" class="easyui-linkbutton" plain="true" onclick="showDetailForGroup()">详情</a>
	        <a href="#" class="easyui-linkbutton" plain="true" onclick="showRoleList()">查看角色</a>
        </div>
    </div>
    
    <div id="groupDialog" class="easyui-dialog" data-options="width : 480, closable : true, modal : true, closed : true, shadow : false" buttons="#group-buttons">
        <form id="groupForm" class="form-horizontal" method="post" novalidate>
            <input name="id" type="hidden" id="groupId">
            <div class="form-group">
                <label>组编码:</label>
                <input type="text" name="groupCode" class="easyui-validatebox form-control" required="true" style="width: 240px;height: 24px"  data-options="required:true,validType:'groupCode'">
            </div>
            <div class="form-group">
                <label>组名称:</label>
                <input type="text" name="groupName" class="easyui-validatebox" required="true" style="width: 240px;height: 24px" data-options="validType:'groupName(this.value, new Array(1,30))'">
            </div>
            <div class="form-group">
                <label>状态:</label>
                <input name="status" type="radio" required="true" value="1" checked="checked">启用&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="status" type="radio" required="true" value="0">停用
            </div>
            <div class="form-group">
                <label>角色列表:</label>
                <select id="cc" class="easyui-combotree" name="roleCodes" style="width:253px;height:30px"
                    data-options="url:'${pageContext.request.contextPath}/role/findAllRoles.htm',method:'get',cascadeCheck:true,valueField:'id',textField:'text'"
                    multiple style="width:200px;"></select>
            </div>
        </form>
    </div>
    
    <div id="groupUpdateDialog" class="easyui-dialog" data-options="width : 480, closable : true, modal : true, closed : true, shadow : false" buttons="#group-buttons-update">
        <form id="groupForm2" class="form-horizontal" method="post" novalidate>
            <input name="id" type="hidden" id="groupId">
            <div class="form-group">
                <label>组编码:</label>
                <input type="text" name="groupCode" class="easyui-validatebox form-control" readonly="true" style="width: 240px;height: 24px;background: gainsboro;" data-options="required:true,validType:'groupCode'">
            </div>
            <div class="form-group">
                <label>组名称:</label>
                <input type="text" name="groupName" class="easyui-validatebox" required="true" style="width: 240px;height: 24px" data-options="validType:'groupName(this.value, new Array(1,30))'">
            </div>
            <div class="form-group">
                <label>状态:</label>
                <input name="status" type="radio" required="true" value="1" checked="checked">启用&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="status" type="radio" required="true" value="0">停用
            </div>
            <div class="form-group">
                <label>角色列表:</label>
                <select id="roleListForEditting" class="easyui-combotree" name="roleCodes" style="width:253px;height:30px" multiple style="width:200px;"
                    data-options="method:'get',cascadeCheck:true,valueField:'id',textField:'text'"></select>
            </div>
        </form>
    </div>
    
    <div id="roleListDialog" class="easyui-dialog" data-options="width : 480, closable : true, modal : true, closed : true, shadow : false">
        <table id="groupRoleList" class="easyui-datagrid"
            data-options="rownumbers:true,cls:'tableSty',singleSelect:true,fitColumns:'true'">
            <thead>
                <tr>
                    <th field="roleCode" width="100px">角色编码</th>
                    <th field="roleName" width="100px">角色名称</th>
                </tr>
            </thead>
        </table>
    </div>
    
    <div class="dialog_btnBox" id="group-buttons">
        <a href="javascript:void(0)" class="btn btn-primary" onclick="saveObject('groupForm','groupDialog','groupList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#groupDialog').dialog('close')">关闭</a>
    </div>
    <div class="dialog_btnBox" id="group-buttons-update">
        <a href="javascript:void(0)" class="btn btn-primary" onclick="saveObject('groupForm2','groupUpdateDialog','groupList')">保存</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="javascript:$('#groupUpdateDialog').dialog('close')">关闭</a>
    </div>
    
    <div id="distriDialog" class="easyui-dialog" style="width: 400px; height: 600px" closed="true" buttons="#distri-buttons">
        <ul id="distriTree"></ul>
    </div>
    <div id="distri-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRoleResource('${pageContext.request.contextPath}/role/saveRoleResources.htm', 'groupRoleList')">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#distriDialog').dialog('close')">关闭</a>
    </div>
    <script type="text/javascript">
        $('#groupList').datagrid({
            onLoadSuccess:function(){
                $('.datagrid-body:eq(1)').css({'overflow-x':'hidden','overflow-y':'auto'});
                $('.datagrid-btable td:last-child').css('padding-right','30px');
            }
        });
        
        function searchGroupsByName()
        {
            var name = $('#name_search').val().replace(/(^\s*)|(\s*$)/g, "");
            if(name.length > 0)
            {
                var url = "${pageContext.request.contextPath}/group/findGroupListByName.htm";
                $.post(url, {"name": name}, function(data){
                    $('#groupList').datagrid({
                        "url": url,
                        queryParams:{"name": name}
                    });
                });
            }
        }
        
        function formatterGroupStatus(status) {
            if(status ==0) {
                return "无效";
            } else if(status == 1) {
                return "有效";
            }
        }
            
        function editGroup(){
            var row = $('#groupList').datagrid('getSelected');
            var urlForGettingRoleListByGroupCode = '${pageContext.request.contextPath}/role/getRoleListByGroupCode.htm?groupCode=' + (row ? row.groupCode : '');
            updateObject('编辑组','${pageContext.request.contextPath}/group/addOrUpdateGroup.htm','groupList','groupUpdateDialog', 'groupForm2', urlForGettingRoleListByGroupCode, 'roleListForEditting', 'tree');
        };
        
        function showDetailForGroup()
        {
            var row = $('#groupList').datagrid('getSelected');
            var urlForGettingRoleListByGroupCode = '${pageContext.request.contextPath}/role/getRoleListByGroupCode.htm?groupCode=' + row.groupCode;
            showDetail('组详情','groupList','groupUpdateDialog', 'groupForm2', urlForGettingRoleListByGroupCode, 'roleListForEditting', 'tree');
        }
        
        function showRoleList()
        {
            var row = $('#groupList').datagrid('getSelected');
            if(row)
            {
                var url = '${pageContext.request.contextPath}/role/getRoleCodeAndNameList.htm';
                $.post(url, {"groupCode": row.groupCode}, function(data){
                    $('#groupRoleList').datagrid({
                        data: data 
                    });
                });
                $('#roleListDialog').dialog('open').dialog('setTitle', '角色列表').parent().addClass('dialog');
            }
            else 
            {
                tip('请选择一行！');
            }
        }
    </script>
    <%@include file="../fragments/footer.jsp"%>
</body>
</html>