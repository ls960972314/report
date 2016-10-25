/**
 * 下拉框联动
 * @return
 */
function change(){
	var proxyId = $('#proxyId').val();
	
	if(proxyId != ''){
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
				$("#roleId").append("<option value=''></option>");
				for(var i=0; i<json.length; i=i+1 ){
					$("#roleId").append("<option value='"+json[i].roleId+"'>"+json[i].roleName+"</option>");
				}
			}
		});
	}else{
		$('#roleId').html("");
		$("#roleId").append("<option value=''></option>");
	}
	
}


/**
 * 删除用户
 * @param rsId
 * @return
 */
function del(systemName){
	$.messager.confirm("系统提示", "您确定删除用户【"+systemName+"】吗?", function (data) {
        if (data) {
        	$.post("/rcmms/mmsSystemUserDel.htm",{ systemName:systemName }, 
            		function(data,textStatus) {
            	if(data=='S'){
            		$.messager.alert('系统提示', '用户【'+systemName+'】信息删除成功!' , 'info',function(){document.getElementById("btSel").click();});
            	}else if(data=='F'){
            		$.messager.alert('系统提示', '未指定用户或指定的用户不存在,请通过正常流程操作', 'error');
            	}else{
            		$.messager.alert('系统提示', '非常抱歉,后台数据异常', 'error');
            	}
            });
        }
    });

}


function add(){
	window.open('/rcmms/mmsSystemUserAddInit.htm','用户信息新增','width=632,height=480,top=64,left=200,toolbar=no, status=no,location=no, menubar=no, resizable=no, scrollbars=yes');
}


/**
 * 用户信息详情
 * @param systemName
 * @return
 */
function detail(systemName){
	window.open('/rcmms/mmsSystemUserDetail.htm?systemName='+systemName,'用户信息详情','width=632,height=480,top=64,left=200,toolbar=no, status=no,location=no, menubar=no, resizable=no, scrollbars=yes');
}

/**
 * 用户信息编辑
 * @param systemName
 * @return
 */
function edit(systemName){
	window.open('/rcmms/mmsSystemUserEditInit.htm?systemName='+systemName,'用户信息编辑','width=632,height=480,top=64,left=200,toolbar=no, status=no,location=no, menubar=no, resizable=no, scrollbars=yes');
}
