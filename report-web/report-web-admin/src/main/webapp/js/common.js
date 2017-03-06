var iBox = {};
    iBox.action = {};
    iBox.data = {};
    iBox.event = {};
    iBox.element = {};

/**
 * 单数字前加0
 */
function singleNum2Db(date) {
	var num = date;
	if (num.toString().length == 1)
		num = '0' + num.toString();
	return num;
}

/**
 * export Excel
 * 
 * @param queryFrom
 * @param url
 */
function exportExcelByParams(queryFrom, url) {
	var params = '?';
	var fields = $('#' + queryFrom).serializeArray();
	$.each(fields, function(i, field) {
		if (null != field.value && '' != field.value)
			params += field.name + '=' + field.value + '&';
	});
	if (params == '?')
		params = '';
	else
		params = params.substring(0, params.length - 1);
	window.location.href = url + params;
}



function formatterNumber(value, jd) {
	if (value == null || typeof (value) == 'undefined')
		return "";

	return val.toFixed(jd);
}

//格式化时间
function formatDate(d){
	if(!d)
		return "";
    d = new Date(d);
    var get = function(num){
        return num<10 ? '0'+num : num;
    }
    return d.getFullYear()+'-'+get((d.getMonth()+1))+'-'+get(d.getDate())+' '+ get(d.getHours()) +':' +get(d.getMinutes()) +':' +get(d.getSeconds());
}
/**
 * 取消冒泡
 * @param ev(当前event事件)
 */
function cancelBubble(ev){
    var e = window.event || ev;
    if(e.stopPropagation){
        e.stopPropagation();
    }else{
        e.cancelBubble = true;
    }
}
/**
 * 模拟下拉层(子类列表)
 * @param ev(当前event事件)
 */
function dropdownToggle(ev){
    var _this = $(this);
    var $parent = $(this).parent();
    var dropList = $parent.children('.dropdown-menu');
    var dropLi = dropList.children();
    $parent.toggleClass("open").siblings().removeClass("open");
    cancelBubble(ev);

    var dropLiSel = function(ev,now){
        var model = dropList.attr('data-model');
        switch(model){
            case 'multi-select':
                $(now).toggleClass('selected');
                cancelBubble(ev);
                break
            case 'single-select':
                $(now).addClass('selected').siblings().removeClass('selected');
                _this.children('.txt').html($(now).text());
                break
            case 'keep-panel':
                cancelBubble(ev);
                break
            default:
                $(now).addClass('selected').siblings().removeClass('selected');
                break
        }
    };
    //给document加上关闭事件
    document.onclick = function(){
        $parent.removeClass("open");
    };
    //li绑定点击事件
    dropLi.each(function(){
        $(this)[0].onclick = function(ev){
            dropLiSel(ev,this);
        }
    })
};

//大图查看
function mediaViewLayer(whxy,imgSrc){
    var layer = $('<div class="media-pop-layer"></div>').appendTo('body');
    var inner = $('<div class="inner"></div>');
    if(whxy.length==2){
        var x = ($(window).width() - whxy[0]) / 2;
        var y = ($(window).height() - whxy[1]) / 2;
        whxy[2] = x;
        whxy[3] = y;
    }
    layer.css({width:whxy[0]+"px",height:whxy[1]+"px",left:whxy[2]+"10px",top:whxy[3]+"px"}).draggable();
    var layerClose = $('<div class="layer-close"></div>').appendTo(layer);

    var idCard = new Image();
    idCard.src = imgSrc;
    idCard.width = whxy[0];
    idCard.height = whxy[1];
    inner.append($(idCard)).appendTo(layer);
    $(layerClose).bind('click',function(){layer.remove()});
};

//显示提示
function showFormPrompt(target, txt,type) {
	var getType = (type==undefined) ? "danger" : type;
    if ($(target).children('.form-prompt')[0]) {
        $(target).children('.form-prompt').html(txt).show();
    } else {
        $(target).prepend('<div class="form-prompt '+getType+'">' + txt + '</div>');
    }
}
// 关闭dialog时，询问是否关闭操作
function reset_dialog(){
    var dialogBody = $(this).closest('.panel').find('.easyui-dialog');
    if(!dialogBody.find('.closeFormConfirm')[0]){
        var confirm = $('<div class="closeFormConfirm">' + '<span>强行关闭将重置所填数据，确定要关闭?</span>' + '</div>');
        var cancel = $('<a class="label label-success pull-right" href="javascript:">取消</a>').appendTo(confirm);
        var sure = $('<a class="label label-danger pull-right" href="javascript:" style="margin:0 5px">确定</a>').appendTo(confirm);
        dialogBody.prepend(confirm);
        sure.click(function() {
            location.reload();
        });
        cancel.click(function() {
            confirm.remove();
        });
    }
    return false;
};

//插入对话框关闭重置
function pushResetBtn(id){
    var p_tool = $(id).parent().find('.panel-tool');
    if(!p_tool.find('.icon-reset')[0]){
        var p_reset = $('<a class="icon-reset" href="javascript:void(0)"></a>').bind('click',reset_dialog);
        p_tool.prepend(p_reset).find('.panel-tool-close').hide();
    }
};

// 表单验证检查
function isValidCheck(obj,target,scope) {
    var validFlag = true;
    var oScope = (scope==undefined)?".submit-form":scope;
    var checkItem = obj.closest(oScope).find('.validatebox-text');

    checkItem.each(function (index) {
        //检测验证项是否为空
        var isEmpty = checkItem.eq(index).val() == '';

        //console.log(checkItem.eq(index).attr('name')+" : "+(isEmpty || !checkItem.eq(index).validatebox('isValid')));
        if (isEmpty || !checkItem.eq(index).validatebox('isValid')) {
            validFlag = false;
            return;
        }
    });
    if (validFlag) {
        $(target).prop('disabled',false);
    }else{
        $(target).prop('disabled',true);
    };
    //console.info('-------------------------');
}

function dialog_complete(container,obj){
    var dialog = $('<div></div>');
    var wrap = $('<div class="prompt-callback">' +
        '<div class="icon-xl '+ obj.status +'"><i>&nbsp;</i></div>' +
        '</div>').appendTo(dialog);
    var prompt = obj.status == "success"?$('<h4 class="text-center">'+obj.title+'</h4>'):
        $('<h4>'+obj.title+'</h4><p>'+obj.content+'</p>');
    prompt.appendTo(wrap);
    $(container).dialog('close');//关闭原对话框
    dialog.dialog({    //新建提示对话框
        width:550,
        title:"提示",
        modal : true,
        shadow : false
    }).parent().addClass('dialog');
    //自动关闭
    if(obj.status == "success"){
        setTimeout(function(){dialog.dialog('close')},3000);
    }
};

function toggle_dialogStatus(obj,callback) {
    var target = $('[data-value=' + obj.get + ']');
    var change = {};
        change.status = obj.locked; //target.hasClass('locked-edit')?true:false
        change.thumb = function($this){  //图片状态切换
            var parent = $this.parent();
            if(this.status){
                if(!parent.hasClass('upload-module')){
                    parent.addClass('upload-module');
                };
                $this.removeClass('locked-edit');
                if($this.find('.viewimg').attr('src') != ""){
                    $this.find('.upload-trigger').attr('class', 'reupload-trigger').text('重新上传');
                }
            }else{
                parent.removeClass('upload-module');
                $this.addClass('locked-edit');
            }
        };
        change.input = function($this){ //文本状态切换
            if(this.status){
                $this.removeClass('locked-edit').find("input[readonly]").removeAttr('readonly');
                $this.find('.easyui-combobox').combobox('enable');
            }else{
                $this.addClass('locked-edit').find('input').attr('readonly');
                $this.find('.easyui-combobox').combobox('disable');
            }
        };
        change.btn = function(){    //头部按钮
            if(this.status){
                obj["btn"].after(obj["newBtn"]).hide();
                callback();
            }else{
                obj["btn"].show();
                obj["btn"].next('.btn').remove();
            }
        }

    change.btn();
    target.each(function(index){
        if($(this).hasClass('thumbnail')){  //针对上传组件的处理
            change.thumb($(this));
        }else{
            change.input($(this));
        }
    })
}

//倒计时恢复
function count_down(id, secs, txt) {
    !txt? txt = $(id).text():"";
    //插入读秒
    $(id).prop('disabled',true).html("等待 <span class='secs'></span> 秒");
    $(id).find('.secs').text(secs);
    if (--secs >= 0) {
        setTimeout("count_down('" + id + "'," + secs + ",'" + txt + "')", 1000);
    } else {
        $(id).html(txt).prop('disabled',false);
    }
}
//数据表格为空提示
function datagrid_empty(target,txt){
    var text = (typeof txt)=='string'?txt:'暂无相关数据！';
    var body = $(target).datagrid('getPanel');
    var pager = $(target).datagrid('getPager');

    if($(target).datagrid('getData').total == 0){
        body.addClass('data_empty_prompt').find('.datagrid-view2 .datagrid-body').html('<p class="prompt-txt">'+text+'</p>');
        pager.hide();
    }else{
        body.removeClass('data_empty_prompt')
        pager.show();
    }
}

/**
 * 提示
 * 
 * @param msg
 */
function tip(msg) {
	$.messager.show({
		msg : msg,
		showType : 'fade',
		height : 50,
		width : 200,
		timeout : 1000,
		style : {
			right : '',
			bottom : ''
		}
	});
}

/**
 * 过滤post参数中null
 * @param val
 * @returns {String}
 */
function trimNull(val){
	var params = "";
	var arr = val.split('&');
	var len = val.split('&').length;
	for(var i=0;i<len;i++){
		var indexKey = arr[i].toString().split('=')[0];
		var indexValue = arr[i].toString().split('=')[1];
		if(null==indexValue||indexValue=='null')
			indexValue = '';
		params += indexKey+'='+indexValue+'&';
	}
	params = params.substring(0, params.length-1);
	return params;
}
/**
 * 根据传入数字获取字符串
 * @param string 字符串 用法如 getTypeValue('你好-1&你不好-2', 1, '&') 默认按照&分割
 * @param value 传入值
 * @param type 分割类型
 * @returns tempArray
 */
function getTypeValue(string, value, type) {
    var typeArray;
    if (type) {
        typeArray = string.split(type);
    } else {
        typeArray = string.split('&');
    }
    var count = 0;
    var tempArray;
    for (; count < typeArray.length; count++) {
        tempArray = typeArray[count].split('-');
        if (tempArray[1] == value) {
            return tempArray[0];
        }
    }
}

/**
 * 工具提示
 * @param obj.type 提示框类型(如：错误-tips-danger)
 * @param obj.on 位置(如：在上 onTop)
 */
function tooltip_view(obj){
    $('[data-tooltip]').mouseenter(function(){
        var layer = $('<div class="tooltip '+ obj.type +' '+ obj.on +'"><p class="body">'+ $(this).attr('data-tooltip') +'</p></div>');
        var offset = $(this).offset();
        var pos = [];
        //显示到页面
        $('body').append(layer);

        var el_attr = {
            w:$(layer).innerWidth(),
            h:$(layer).outerHeight()
        }
        if(obj.on == 'onTop'){
            pos = {
                x:offset.left - 5,
                y:offset.top - el_attr.h - 5
            }
        }
        //增加定位
        $(layer).css({top:pos.y+'px',left:pos.x+'px'})
    }).mouseout(function(){
            $('body>.tooltip').remove();
        })
}
//处理IE10以下输入框不支持placeholder
$(function(){
    if($('html').hasClass('lt-ie10')){
        $('input, textarea').placeholder();
//        $('input[placeholder]').each(function(){
//            var _this = $(this);
//            var txt = $(this).attr('placeholder');
//            if($(this).val() == ""){
//                $(this).addClass('placeholder').val(txt);
//            }
//            $(this).focus(function(){
//                if(_this.hasClass('placeholder')){
//                    _this.val('').removeClass('placeholder');
//                }
//            }).blur(function(){
//                if(_this.val() == ""){
//                    _this.val(txt).addClass('placeholder');
//                }
//            })
//        })
    }
});

function commonformatterObjectStatus(status) {
	if(status ==0) {
		return "<span style=\"color: red; \">无效</span>";
	} else if(status == 1) {
		return "有效";
	}
}