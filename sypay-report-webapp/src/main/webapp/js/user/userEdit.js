$(function() {
	
	// 控制密码输入框是否显示
	$('input[name=changePw]').click(function() {
		if ($('input[name=changePw]')[0].checked) {
			$('#trpassWord').hide();
			$('#trMsg').hide();
		} else {
			$('#trpassWord').show();
			$('#trMsg').show();
		}
	});

	if ($('input[name=changePw]')[0].checked) {
		$('#trpassWord').hide();
		$('#trMsg').hide();
	} else {
		$('#trpassWord').show();
		$('#trMsg').show();
	}
});


function save() {
	var password = $('#passWord').val();
	var changePw = $("input[name='changePw']:checked").val();
	var roleId = $('#roleId').val();
	
	if(changePw == 'Y'){
		var patrn=/^[a-zA-Z0-9]{6,16}$/;  
	    if (!patrn.exec(password)){
	    	$("#message").html("6~16位数字或字母，区分大小写") // 赋值到span中
	    	$('#passWord').focus();
	    	return false;
	    }
	}
	
	if (roleId == '') {
		$("#message").html("请选择需要赋予的角色") // 赋值到span中
		$('#roleId').focus();
		return false;
	}

	// 防止重复提交
	var bt = document.getElementById("btEdit");
	bt.disabled = true;

	$('#myForm').submit();
	
}