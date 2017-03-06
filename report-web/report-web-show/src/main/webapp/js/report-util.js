function inDynamicName(dynamicName,conWhere) {
	   $("input[name='input" + conWhere + "']").val(dynamicName);
	   $("ul[name='ul" + conWhere + "']").hide();
   }
//获得一个一维数组中的最大值
function getArrMax(arr) {
	var max = 0;
	for (var i=0;i<arr.length;i++) {
		if (arr[i]*1 > max) {
			max = arr[i]*1;
		}
	}
	return max;
}
//获得一个一维数组中的最小值
function getArrMin(arr) {
	var min = 0;
	for (var i=0;i<arr.length;i++) {
		if (arr[i]*1 < min) {
			min = arr[i]*1;
		}
	}
	return min;
}
//返回正确的运算符
function getOperation(oldOpe) {
    if (oldOpe == 'gt') {
        return '>';
    } else if (oldOpe == 'ge') {
        return '>=';
    } else if (oldOpe == 'lt') {
        return '<';
    } else if (oldOpe == 'le') {
        return '<=';
    } else if (oldOpe == 'eq') {
        return '=';
    } else if (oldOpe == 'nq') {
        return '<>';
    } else if (oldOpe == 'like') {
    	return 'like';
    }
}

//去掉左右空格
function trim(str){ //删除左右两端的空格   
    return str.replace(/(^\s*)|(\s*$)/g, "");  
}
//判断是否为空字符串
function isNull(userText) {
    return (userText == null || userText.replace(/[ ]/g,"") == "")?true:false;
}
//月的开始时间为最后一天
function dayPickerBeginInit (conNode) {
	var nowDate = conNode.val();
	conNode.datepicker();
	if (nowDate.length==7) {
		conNode.val(nowDate + "-01");
	}
}

//月的结束时间为最后一天
function dayPickerEndInit (conNode) {
	var nowDate = conNode.val();
	conNode.datepicker();
	if (nowDate.length==7) {
		var lastd = getCountDays(nowDate);
		conNode.val(nowDate + "-" + lastd);
	}
}

//周开始时间控件初始化
function weekPickerBeginInit (timeNode) {
	var endDate=new Date();
    var startDate=new Date();
    curDate=startDate.getDate();
    curDay=startDate.getDay();
    startDate.setDate(curDate-curDay-7);
    endDate.setDate(curDate-curDay-1);
    //选择控件时控件如何处理
    timeNode.datepicker( {
    	showOtherMonths: true,
        selectOtherMonths: true,
        onSelect: function(dateText, inst) { 
            var date = $(this).datepicker('getDate');
            startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay());
            endDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 6);
            var dateFormat = inst.settings.dateFormat || $.datepicker._defaults.dateFormat;
            window.setTimeout(function () {
            	timeNode.find('.ui-datepicker-current-day a').addClass('ui-state-active')
            }, 1);
            timeNode.val( $.datepicker.formatDate( 'yy-mm-dd', startDate, inst.settings ));
        },
        beforeShowDay: function(date) {
            var cssClass = '';
            if(date >= startDate && date <= endDate)
                cssClass = 'ui-datepicker-current-day';
            return [true, cssClass];
        },
        onChangeMonthYear: function(year, month, inst) {
            window.setTimeout(function () {
            	timeNode.find('.ui-datepicker-current-day a').addClass('ui-state-active')
            }, 1);
        }
    });
    
    //选择时间维度时，显示日期变化
    var nowDate = timeNode.val();
    if (nowDate != "") {
        nowDate = new Date(nowDate);
        nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate() - nowDate.getDay());
        timeNode.val(nowDate.getFullYear() + "-" + ((nowDate.getMonth()+1)<10?("0"+(nowDate.getMonth()+1)):(nowDate.getMonth()+1)) + "-" + (nowDate.getDate()<10?"0"+nowDate.getDate():nowDate.getDate()));
    }
}

//周结束时间控件初始化
function weekPickerEndInit (timeNode) {
	var endDate=new Date();
    var startDate=new Date();
    curDate=startDate.getDate();
    curDay=startDate.getDay();
    startDate.setDate(curDate-curDay-7);
    endDate.setDate(curDate-curDay-1);
    //选择控件时控件如何处理
    timeNode.datepicker( {
    	showOtherMonths: true,
        selectOtherMonths: true,
        onSelect: function(dateText, inst) { 
            var date = $(this).datepicker('getDate');
            startDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay());
            endDate = new Date(date.getFullYear(), date.getMonth(), date.getDate() - date.getDay() + 6);
            var dateFormat = inst.settings.dateFormat || $.datepicker._defaults.dateFormat;
            window.setTimeout(function () {
            	timeNode.find('.ui-datepicker-current-day a').addClass('ui-state-active')
            }, 1);
            timeNode.val( $.datepicker.formatDate( 'yy-mm-dd', endDate, inst.settings ));
        },
        beforeShowDay: function(date) {
            var cssClass = '';
            if(date >= startDate && date <= endDate)
                cssClass = 'ui-datepicker-current-day';
            return [true, cssClass];
        },
        onChangeMonthYear: function(year, month, inst) {
            window.setTimeout(function () {
            	timeNode.find('.ui-datepicker-current-day a').addClass('ui-state-active')
            }, 1);
        }
    });
    //选择时间维度时，显示日期变化
    var nowDate = timeNode.val();
    if (nowDate != "") {
    	if (nowDate.length==7) {
    		var lastd = getCountDays(nowDate);
    		nowDate = nowDate + "-" + lastd;
    	}
        nowDate = new Date(nowDate);
        nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate() - nowDate.getDay() + 6);
        timeNode.val(nowDate.getFullYear() + "-" + ((nowDate.getMonth()+1)<10?("0"+(nowDate.getMonth()+1)):(nowDate.getMonth()+1)) + "-" + (nowDate.getDate()<10?"0"+nowDate.getDate():nowDate.getDate()));
    }
}


//月控件初始化
function monthPickerInit (timeNode) {
	//选择控件时控件如何处理
    timeNode.datepicker( {
    	showOtherMonths: true,
        selectOtherMonths: true,
        onSelect: function(dateText, inst) { 
            var date = $(this).datepicker('getDate');
            var dateFormat = inst.settings.dateFormat || $.datepicker._defaults.dateFormat;
            window.setTimeout(function () {
            	timeNode.find('.ui-datepicker-current-day a').addClass('ui-state-active')
            }, 1);
            timeNode.val( $.datepicker.formatDate( 'yy-mm', date, inst.settings ));
        },
        beforeShowDay: function(date) {
            var cssClass = 'ui-datepicker-current-day';
            return [true, cssClass];
        },
        onChangeMonthYear: function(year, month, inst) {
            window.setTimeout(function () {
            	timeNode.find('.ui-datepicker-current-day a').addClass('ui-state-active')
            }, 1);
        }
    });
    //选择时间维度时，显示日期变化
    var nowDate = timeNode.val();
    if (nowDate != "") {
        nowDate = new Date(nowDate);
        timeNode.val(nowDate.getFullYear() + "-" + ((nowDate.getMonth()+1)<10?("0"+(nowDate.getMonth()+1)):(nowDate.getMonth()+1)));
    }
}

// 得到一个月有多少天
function getCountDays(d) {
	var curDate = new Date(d);
	/* 获取当前月份 */
	var curMonth = curDate.getMonth();
	/* 生成实际的月份: 由于curMonth会比实际月份小1, 故需加1 */
	curDate.setMonth(curMonth + 1);
	/* 将日期设置为0, 这里为什么要这样设置, 我不知道原因, 这是从网上学来的 */
	curDate.setDate(0);
	/* 返回当月的天数 */
	return curDate.getDate();
}
/* 将数字加上千分位 */
function fmoney(s, n){   
    n = n >= 0 && n <= 20 ? n : 2;   
    
    if(Object.prototype.toString.apply(s) == '[object String]') {
        s = s * 1;
    }
    s = parseFloat((s + "")).toFixed(n) + ""; 

    var l = s.split(".")[0].split(""),   
        r = s.split(".")[1];  
        
        t = "";   
   for(i = 0; i < l.length; i ++ ){   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }
   
   return t.split("").join("") +( r == undefined?"":"."+r);   
}
/* 判断是否是小数 */
function isLittleNum(s) {
	if (s.indexOf("-") != -1) {
		s = s.replace("-","");
	}
	var regu = "^([0-9]*[.0-9])$";
	var re = new RegExp(regu);
	if (s.search(re) != -1)
		return false;
	else
		return true;
}
