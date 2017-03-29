if (this != window.top) {
	window.top.location.href = this.location.href;
}

function login() {
	var username = $('#username').val();
	var password = $('#password').val();

	if (username == '') {
		$.messager.alert('系统提示', '请输入用户名', 'warning');
		return false;
	}
	if (password == '') {
		$.messager.alert('系统提示', '请输入密码', 'warning');
		return false;
	}

	$('#loginForm').submit();
}

//修改密码
function editPW() {
	var oldpass = $('#oldpassword').val();
	var newPass = $('#newpassword').val();
	var rePass = $('#repassword').val();

	if (oldpass == '') {
		$.messager.alert('系统提示', '请输入原始密码', 'warning');
		return false;
	}
	if (newPass == '') {
		$.messager.alert('系统提示', '请输入新密码', 'warning');
		return false;
	}
	if (rePass == '') {
		$.messager.alert('系统提示', '请确认新密码', 'warning');
		return false;
	}

	if (newPass != rePass) {
		$.messager.alert('系统提示', '两次密码不一至,请重新输入', 'warning');
		return false;
	}

	if (newPass == oldpass) {
		$.messager.alert('系统提示', '原始密码与新密码一致', 'warning');
		return false;
	}

	var patrn = /^[a-zA-Z0-9]{6,16}$/;
	if (!patrn.exec(newPass)) {
		$.messager.alert('系统提示', '用户密码为6-16位数字或字母', 'warning');
		return false;
	}

	$.post($('#basePath').val() + "systemEditPassword.htm", {
		password : oldpass,
		newpassword : rePass
	}, function(data, textStatus) {
		if (data == 'S') {
			$.messager.alert('系统提示', '恭喜,密码修改成功', 'info');
			$('#oldpassword').val('');
			$('#newpassword').val('');
			$('#repassword').val('');
			closePW();
		} else if (data == 'D') {
			$.messager.alert('系统提示', '非常抱歉,原始密码输入错误', 'warning');
			$('#oldpassword').val('');
			$('#newpassword').val('');
			$('#repassword').val('');
		} else if (data == 'F') {
			$.messager.alert('系统提示', '非常抱歉,存在为空的输入栏', 'warning');
			$('#oldpassword').val('');
			$('#newpassword').val('');
			$('#repassword').val('');
		} else {
			$.messager.alert('系统提示', '非常抱歉,后台数据保存异常', 'warning');
			$('#oldpassword').val('');
			$('#newpassword').val('');
			$('#repassword').val('');
		}
	});

}

//关闭密码修改窗口
function closePW() {
	$('#editPW').window('close');
}

$(function() {

	// 登录了才显示导航条,不然点击导航条时页面会有问题
	var loginFlag = $('#loginFlag').val();
	if (loginFlag) {
		outLookInit();
	}

	// 关闭密码修改窗口
	closePW();

	// 登录
	$('#btnEp').click(function() {
		login();
	});

	// 回车键登录
	document.onkeydown = function(){
		var ev = window.event ? event : arguments[0];
		var keyCode = ev.which || ev.keyCode;
		// 保险起见这样写 实际上window对象会被摧毁
		var ableToEnter = !$("div.msg_flag") || ($("div.msg_flag") && $("div.msg_flag").css("display") != "block");	
		if(Number(keyCode) === 13 && ableToEnter){
			login();
		}
	};

	// 密码修改
	$('#editpass').click(function() {
		$('#editPW').window({  
		    width:320,  
		    height:280,  
		    modal:true  
		});
		
		$('#editPW').window('open');
	});

	// 密码修改
	$('#btnPW').click(function() {
		editPW();
	});

});

function Confirm(msg, control) {
	$.messager.confirm("系统提示", msg, function (r) { 
		if (r) {
			location.href = control.toString();
			return true;
		}
	});
	return false;  
}

var _menus;

/*function outLookInit(basePath) {
	// 从后台取导航信息
	$.post(basePath + "/mmsSystemUsermenus.htm", function(data, json) {
		// 引入json.js 把String型的json转换成json对象
		_menus = JSON.parse(data);
		InitLeftMenu();
		tabClose();
		tabCloseEven();
	});
}*/

function outLookInit() {
        InitLeftMenu();
        tabClose();
        tabCloseEven();
}

// 初始化左侧
function InitLeftMenu() {

	//$(".easyui-accordion").empty();
	/*var menulist = "";

    $.each(_menus.menus, function(i, n) {
        menulist += '<div title="' + n.menuname + '"  icon="' + n.icon
                + '" style="overflow:auto;">';
        menulist += '<ul>';
        $.each(n.menus, function(j, o) {
        	var aToStr=JSON.stringify(o);
        	//alert(aToStr);
            menulist += '<li><div><a id="'+o.menuid+'" target="' + o.menuid + '" href="'
                    + o.url + '" ><span class="icon ' + o.icon + '" ></span>'
                    + o.menuname + '</a></div></li> ';
        });
        menulist += '</ul></div>';
    });*/

	//$(".easyui-accordion").append(menulist);

	$('.easyui-accordion li a').click(function(){
		
		var tabTitle = $(this).text();
		var url = $(this).attr("href");
		var id=$(this).attr("id");
		addTab(id,tabTitle,url);
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});

	$(".easyui-accordion").accordion();
}

function addTab(id,subtitle, url) {
	//alert(subtitle);
	if (!$('#tabs').tabs('exists', subtitle)) {
		//alert('aaaa');
		$('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(id,subtitle, url),
			closable : true,
			width : $('#mainPanle').width(),
			height : $('#mainPanle').height()
		});
	} else {
		$('#tabs').tabs('select', subtitle);
	}
	tabClose();
}

function createFrame(id,subtitle, url) {
	var s = '<iframe name="'
			+ id
			+ '" scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose()
{
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('close', subtitle);
	});

	$(".tabs-inner").bind('contextmenu',function(e){
		$('#mm').menu('show', {
			left: e.pageX,
			top: e.pageY
		});

		var subtitle = $(this).children("span").text();
		$('#tabs').tabs('select', subtitle);
		$('#mm').data("currtab", subtitle);

		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven()
 {
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});	
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			if (t != currtab_title)
				$('#tabs').tabs('close', t);
		});
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			// msgShow('系统提示','后边没有啦~~','error');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

// 更改messager的按钮文字, see http://blog.csdn.net/zzh87615/article/details/5912144
$.extend($.messager.defaults, {
	ok:"确定",
	cancel:"取消"
});

