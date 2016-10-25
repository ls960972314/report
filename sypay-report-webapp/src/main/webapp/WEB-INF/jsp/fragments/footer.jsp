 <div id="changePasswordDialog" class="easyui-dialog" 
    data-options="width : 550, closable : true, modal : true, closed : true, shadow : false" style="left: 300px;"
		buttons="#changePassword-buttons">
		<form id="changePasswordForm" class="form-horizontal" method="post" novalidate>
            <div class="form-group">
                <label>原密码:</label>
                <input name="originPassword" id="originPassword" type="password" class="easyui-validatebox" required="true" style="width: 240px;height: 24px" />
            </div>
            <div class="form-group">
                <label>新密码:</label>
                <input name="newPassword" id="newPassword" type="password" class="easyui-validatebox" required="true" style="width: 240px;height: 24px" data-options="validType:'minLength[3]'">
            </div><div class="form-group">
                <label>确认密码:</label>
                <input type="password" id="duplicatePassword" class="easyui-validatebox" required="true" style="width: 240px;height: 24px" data-options="validType:'equals(this.value, \'#newPassword\')'">
            </div>
		</form>
	</div>
	<div class="dialog_btnBox" id="changePassword-buttons">
		<a href="javascript:void(0)" class="btn btn-primary"
			onclick="changePassword()">保存</a>
		<a href="javascript:void(0)" class="btn btn-default"
			onclick="javascript:$('#changePasswordDialog').dialog('close');">取消</a>
	</div>
    
    <script type="text/javascript">
	    function changePassword()
		{
			$('#changePasswordForm').form('submit', {
				url : '${pageContext.request.contextPath}/member/changePassword.htm',
				data: $(this).serialize(),
				success : function(result) {
					$('#changePasswordDialog').dialog('close');
					result = eval('(' + result + ')');
					if (result.status == 1) {
						tip('密码修改成功');
					}
					else {
						tip(result.errorInfo);
					}
				}
			});
		}
	 	
	 	function toChangePassword()
	 	{
	 		$('#changePasswordForm').form('clear');
	 		$('#changePasswordDialog').dialog('open').dialog('setTitle', '修改密码').parent().addClass('dialog');
	 		$('#changePasswordDialog .easyui-validatebox:eq(0)').focus();
	 	}
    </script>
<div class="page-footer">
	<div class="inner">
		<span class="copyright"></span>
	</div>
</div>


