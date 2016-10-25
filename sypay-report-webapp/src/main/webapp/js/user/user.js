

$(function() {
	var proxyId = $('#proxyId').val();
	
	if(proxyId == 1){
		$('#trproxyAdminFlag').hide();
	}else{
		$('#trproxyAdminFlag').show();
	}
});
/**
 * 校验用户名是否存在
 * @return
 */
function chk() {
	var systemName = $('#systemName').val();
	
	if (systemName != '' ) {
		var pm = /^[a-zA-Z]{1}[a-zA-Z0-9_]{3,18}$/;
		
		if (!pm.exec(systemName)) {
			$("#message").html("指定的登录账号不符合规则") // 赋值到span中
			$('#systemName').focus();
		} else {
			$.post("/rcmms/mmsSystemUserAddChk.htm", {
				systemName : systemName
			}, function(data, textStatus) {
				if (data == 'S') {
					$("#message").html("登录账号已存在") // 赋值到span中
					$('#systemName').focus();
				} else {
					// 标记已经验证
					$('#addChk').val(true);
					$("#message").html("") // 赋值到span中
				}
			});
		}
	}else{
		$("#message").html("请指定登录账号") // 赋值到span中
		$('#systemName').focus();
	}
}

/**
 * 下拉框联动
 * @return
 */
function change(){
	var proxyId = $('#proxyId').val();
	
	if(proxyId == 1){
		$('#trproxyAdminFlag').hide();
	}else{
		$('#trproxyAdminFlag').show();
	}
	
	$.post("/rcmms/mmsSystemUserAddGetId.htm", {
		proxyId : proxyId
	}, function(data) {
		// 当没有返回时
		if(data == ''){
			$("#message").html("指定的代理机构不存在角色信息,请先新建角色") // 赋值到span中
			$('#roleId').html("");
		}else{
			$('#roleId').html("");
			// 把data转换成类数组
			var json = eval(data);
			for(var i=0; i<json.length; i=i+1 ){
				$("#roleId").append("<option value='"+json[i].roleId+"'>"+json[i].roleName+"</option>");
			}
		}
	});
}

function add() {
	var systemName = $('#systemName').val();
	var password = $('#passWord').val();
	var proxyId = $('#proxyId').val();
	var roleId = $('#roleId').val();
	
	var pm = /^[a-zA-Z]{1}[a-zA-Z0-9_]{3,18}$/;
	if (!pm.exec(systemName)) {
		$("#message").html("指定的登录账号不符合规则") // 赋值到span中
		$('#systemName').focus();
		return false;
	}

	if (proxyId == '') {
		$("#message").html("请选择代理机构") // 赋值到span中
		$('#proxyId').focus();
		return false;
	}
	
	var patrn=/^[a-zA-Z0-9]{6,16}$/;  
    if (!patrn.exec(password)){
    	$("#message").html("6~16位数字或字母，区分大小写") // 赋值到span中
    	$('#passWord').focus();
    	return false;
    }
	
	if (roleId == '') {
		$("#message").html("请选择需要赋予的角色") // 赋值到span中
		$('#roleId').focus();
		return false;
	}

	// 防止重复提交
	var bt = document.getElementById("btAdd");
	bt.disabled = true;

	$('#myForm').submit();

}