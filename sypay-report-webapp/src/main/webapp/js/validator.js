$.extend($.fn.validatebox.defaults.rules, {
	minLength : { // 判断最小长度 
        validator : function(value, param) {
            value = $.trim(value);	//去空格 
            return value.length >= param[0];
        },
        message : '最少输入 {0} 个字符。'
    },
	length:{
		validator:function(value,param){
			var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
        message:"输入长度介于{0}和{1}之间."
    },
    phone : {// 验证电话号码 
        validator : function(value) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message : '格式不正确,请使用下面格式:020-88888888'
    },
    mobile : {// 验证手机号码 
        validator : function(value,param) {
			var s = false;
			var baseValid = function(){
				if(/^0?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/i.test(value)){
					s = true;
				}else{
					$.fn.validatebox.defaults.rules.mobile.message = "手机号码格式不正确";
					s = false;
				}
			};
			if(!param){
				baseValid();
				return s;
			}else{
				baseValid();
				if(!s){
					return s;
				}else{
					var as = false;
					var postdata = {};
					postdata[param[1]] = value;
					$.ajax({  
						url: param[0],
						data: postdata,
						async: false,
						type: "post",
						success:function(data){
							if (!data.success) {
								$.fn.validatebox.defaults.rules.mobile.message = "此手机号码已存在！";
								as = false;
							}else{
								as = true;
							}  
						}
					});
					return as;
				}
			}
        },
        message : ''
    },
    idcard : {// 验证身份证 
        validator : function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message : '身份证号码格式不正确'
    },
	name : {// 验证姓名，可以是中文或英文 
        validator : function(value) {
            return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
        },
        message : '请输入正确的名称'
    },
    //用户名称验证(只能包括 _ 数字 字母) 
	username: {
		validator: function (value, lengthRanage) {
			if(value.replace(/(^\s*)|(\s*$)/g, "").length == 0)
			{
				$.fn.validatebox.defaults.rules.username.message = '用户名不能为空';
				return false;
			}
			
			if (value.length < lengthRanage[0] || value.length > lengthRanage[1]) {
				$.fn.validatebox.defaults.rules.username.message = '用户名长度必须在' + lengthRanage[0] + '至' + lengthRanage[1] + '范围';
				return false;
			} else {
				if (!/^[\w]+$/.test(value)) {
					$.fn.validatebox.defaults.rules.username.message = '用户名只能数字、字母、下划线组成.';
					return false;
				} else {
					return true;
				}
			}
		}, message: ''
	},
	//登录帐号验证(只能包括 _ 数字 字母) 
	// params[0]: 最小长度   params[1]: 最大长度
	accNo: {
		validator: function (value, params) {
			if(value.replace(/(^\s*)|(\s*$)/g, "").length == 0)
			{
				$.fn.validatebox.defaults.rules.accNo.message = '登录帐号不能为空';
				return false;
			}
			
			if (value.length < params[0] || value.length > params[1]) {
				$.fn.validatebox.defaults.rules.accNo.message = '登录帐号长度必须在' + params[0] + '至' + params[1] + '范围';
				return false;
			} else {
				if (!/^[\w@.]+$/.test(value)) {
					$.fn.validatebox.defaults.rules.accNo.message = '登录帐号只能数字、字母、下划线、点号、@组成.';
					return false;
				} else {
					return true;
				}
			}
		}, message: ''
	},
	// 密码校验
	password: {
		validator: function (pwd, param) {
			if(pwd.replace(/(^\s*)|(\s*$)/g, "").length == 0)
			{
				$.fn.validatebox.defaults.rules.password.message = '密码不能为空';
				return false;
			}
			
			if(!param && !param[1])
			{
				$.ajax({
					url: param[0],
					data: {"password": pwd},
					async: false,
					type: "post",
					success:function(data){
						if (!data.success) {
							$.fn.validatebox.defaults.rules.password.message = "密码不正确";
							return false;
						}else{
							return true;
						}  
					}
				});
			}
		}, message: ''
	},
	roleCode: {
		validator: function (value,state) {
			value = value.replace(/(^\s*)|(\s*$)/g, "");
			if(value.length == 0)
			{
				$.fn.validatebox.defaults.rules.roleCode.message = '不能为空';
				return false;
			}
			
			if(value.length < 1 || value.length > 64)
			{
				$.fn.validatebox.defaults.rules.roleCode.message = '长度必须在1至64范围';
				return false;
			}
			
			if(state == undefined){
				if (!/^[a-z,A-Z,0-9,_,\-]+$/i.test(value)) {
					$.fn.validatebox.defaults.rules.roleCode.message = '只能是字母、数字、下划线、横杆';
					return false;
				} else {
					return true;
				}
			}else{
				$.fn.validatebox.defaults.rules.roleCode.message = ""+state;
				return false;
			}
		}, message: ''
	},
	groupCode: {
		validator: function (value,state) {
			value = value.replace(/(^\s*)|(\s*$)/g, "");
			if(value.length == 0)
			{
				$.fn.validatebox.defaults.rules.groupCode.message = '不能为空';
				return false;
			}
			
			if(value.length < 1 || value.length > 24)
			{
				$.fn.validatebox.defaults.rules.groupCode.message = '长度必须在1至24范围';
				return false;
			}
			
			if(state == undefined){
				if (!/^[a-z,A-Z,0-9,_,\-]+$/i.test(value)) {
					$.fn.validatebox.defaults.rules.groupCode.message = '只能是字母、数字、下划线、横杆';
					return false;
				} else {
					return true;
				}
			}else{
				$.fn.validatebox.defaults.rules.groupCode.message = ""+state;
				return false;
			}
		}, message: ''
	},
	roleName: {
		validator: function (value, param) {
			if (!/^[0-9a-zA-Z\u0391-\uFFE5_\-]+/i.test(value)) {
				$.fn.validatebox.defaults.rules.roleName.message = '只能包含中文、英文字母、数字、下划线、横杆';
				return false;
			} else {
				return true;
			}
		}, message: ''
	},
	charter:{
		validator : function(value) {
            return /^\d{13,15}$/i.test(value);
        },
        message : '营业执照号格式不正确'
	},
	integer : {// 验证整数 
        validator : function(value) {
            return /^[+]?[1-9]+\d*$/i.test(value);
        },
        message : '请输入整数'
    },
	number: {
		validator: function (value, param) {
			return /^\d+$/.test(value);
		},
		message: '请输入数字'
	},
    chinese : {// 验证中文 
        validator : function(value) {
            return /^[\u0391-\uFFE5]+$/i.test(value);
        },
        message : '请输入中文'
    },
    english : {// 验证英语 
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入英文'
    },
    unnormal : {// 验证是否包含空格和非法字符 
        validator : function(value) {
            return !/^[`~!@#$%^&*()_—+<>?:"{}【】（）|：、？《》{},.·\/;'[\]]+$/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
	equals:{	
        validator : function(value, param){
        	return $(param).val() === value;
        },
        message : '两次输入的密码不一致！'
    },
    isAfter: {//时间区间验证
        validator: function(value, param){
            var dateA = $.fn.datebox.defaults.parser(value);
            var dateB = $.fn.datebox.defaults.parser($(param[0]).datebox('getValue'));
            return dateA>new Date() && dateA>dateB;
        },
        message: '结束时间不能小于开始时间'
    } ,
	isLaterToday: {
		validator: function(value, param){
			var date = $.fn.datebox.defaults.parser(value);
			return date>new Date();
		},
		message: '开始时间不能小于今天'
    },
	//ajax 验证唯一性
	//调用方法：validType="Unique_validation[/^0?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/,'手机号码格式不准确','Member/Index/checkusername','name','该手机号已经存在']" missingMessage="请输入手机！"
	Unique_validation: {
		validator: function(value, param) {
			var m_reg = new RegExp(param[0]); //传递过来的正则字符串中的"\"必须是"\\"  
			if (!m_reg.test(value)) {
				$.fn.validatebox.defaults.rules.Unique_validation.message = param[1];
				return false;
			}else{
				var s = false;
				var postdata = {};  
				postdata[param[3]] = value;  
				$.ajax({  
					url: param[2],  
					data: postdata,  
					async: false,  
					type: "post",
					success:function(data){
						if (!data.success) {
							$.fn.validatebox.defaults.rules.Unique_validation.message = param[4];
							s = false;
						}else{
							s = true;
						}  
					}
				});
				return s;
			}  
		},  
		message: ''
	},
	remove: function(jq, newposition){
        return jq.each(function(){    
            $(this).removeClass("validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');  
        });    
    }, 
});