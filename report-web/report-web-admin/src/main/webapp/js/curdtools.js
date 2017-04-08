var varurl;
//成功标志
var succResult = 0;
/**
 * 增加信息
 * 
 * @param title
 * @param url
 * @param dgname
 *            对话框id
 * @param formname
 *            表单id
 */

function addObject(title, url, dgname, formname, listurl, tolistid, type) {
	var $formname = $('#' + formname);
	setComponentStyle(dgname, 2);
	var radioChecked = $formname.find('input[type=radio]:checked');
	var readOnlyEdit = $formname.find('input[data-readonly="edit"]');
	$('#' + dgname).dialog('open').dialog('setTitle', title).parent().addClass('dialog');
	$formname.form('clear');

	radioChecked.each(function(){
		$(this).prop("checked",true);
	});
	readOnlyEdit.each(function(){
		$(this).prop("readonly",false);
	});
	varurl = url;
	if (tolistid && listurl) {
		if (type == 'tree') {
			$('#' + tolistid).combotree({
				url : listurl
			});
		} else {
			$('#' + tolistid).combobox('reload', listurl);
		}

	}
	var callback = arguments[arguments.length-1];
	if(typeof callback === "function")callback($formname,'add');
	return;
}

/**
 * 
 * @param title
 * @param url
 * @param listname
 * @param dgname
 * @param formname
 * @param listurl
 *            下拉列表地址
 * @param tolistid
 *            下拉列表所属id
 * @param type
 *            类型 type = 'tree': 树形显示 否则是下拉列表
 */
function updateObject(title, url, listname, dgname, formname, listurl,tolistid, type) {
	$("#"+formname).form('clear');
	setComponentStyle(dgname, 2);
	var $formname = $('#' + formname);
	var readOnlyEdit = $formname.find('input[data-readonly="edit"]');
	var row = $('#' + listname).datagrid('getSelected');
	if (row) {
		if (tolistid && listurl) {
			if (type == 'tree') {
				$('#' + tolistid).combotree({
					url : listurl
				});
			} else {
				$('#' + tolistid).combobox('reload', listurl);
			}
		}
		$('#' + dgname).dialog('open').dialog('setTitle', title).parent().addClass('dialog');
		$formname.form('load', row);
		varurl = url;
	} else {
		tip('请选择一行！');
	}
	readOnlyEdit.each(function(){
		$(this).prop("readonly",true);
		$(this).css("background-color","gainsboro");
	});
	var callback = arguments[arguments.length-1];
	if(typeof callback === "function")callback($formname,'update');
	return;
}
/**
 * 保存
 * 
 * @param formname
 *            表格id
 * @param dgname
 *            对话框id
 * @param listname
 *            list id
 */
function saveObject(formname, dgname, listname, isTree) {
	$('#' + formname).form('submit', {
		url : varurl,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.status == succResult) {
				$('#' + dgname).dialog('close'); // close the dialog
				if (isTree) {
					$('#' + listname).treegrid('reload');
				} else {
					$('#' + listname).datagrid('reload'); // reload the user
					// data
				}
				tip('操作成功');
			}
			else {
				tip(result.errorInfo);
			}
		}
	});
}
/**
 * 删除
 * 
 * @param listname
 * @param url
 */
function deleteObject(listname, url, isTree) {
	var row = $('#' + listname).datagrid('getSelected');
	if (row) {
		$.messager.confirm('删除', '确定要删除吗?', function(r) {
			if (r) {
				$.ajax({
					type: "POST",
					url: url,
					data: {	id : row.id	},
					success: function(result) {
						if (result.status == succResult) {
							if (isTree) {
								$('#' + listname).treegrid('reload');
							} else {
								$('#' + listname).datagrid('reload'); // reload
								// the user
								// data
							}
							tip('操作成功');
						} else {
							tip(result.errorInfo);
						}
					},
					error: function(){
						tip('无法删除');
					}
				});
			}
		});
	} else {
		tip('请选择一行！');
	}
}

function showDetail(title, listname, dgname, formname, listurl,tolistid, type) {
	var $formname = $('#' + formname);
	var row = $('#' + listname).datagrid('getSelected');
	if (row) {
		if (tolistid && listurl) {
			if (type == 'tree') {
				$('#' + tolistid).combotree({
					url : listurl
				});
			} else {
				$('#' + tolistid).combobox('reload', listurl);
			}
		}
		setComponentStyle(dgname, 1);
		$('#' + dgname).dialog('open').dialog('setTitle', title).parent().addClass('dialog');
		$formname.form('load', row);
	} else {
		tip('请选择一行！');
	}
	
}

/**
 * 
 * @param dgname
 * @param style
 */
function setComponentStyle(dgname, style) {
	if(style == 1) {
		$('#' + dgname).find('input').attr("disabled","disabled");
		$('#' + dgname).find('checkbox').attr("disabled","disabled");
		$('#' + dgname).find('.easyui-combobox').combobox('disable');
		$('#' + dgname).find('.easyui-combotree').combotree('disable');
		$('#' + dgname).find('select').attr("disabled","disabled");
		$('#' + dgname).find('textarea').attr("disabled","disabled");
		$('#' + dgname).find('.dialog-button').attr("hidden","hidden");
		$('#' + dgname).find('input').css("background-color","gainsboro");
		$('#' + dgname).find('checkbox').css("background-color","gainsboro");
		$('#' + dgname).find('select').css("background-color","gainsboro");
		$('#' + dgname).find('textarea').css("background-color","gainsboro");
	} else if(style == 2) {
		
		$('#' + dgname).find('input').removeAttr("disabled");
		$('#' + dgname).find('checkbox').removeAttr("disabled");
		$('#' + dgname).find('.easyui-combobox').combobox('enable');
		$('#' + dgname).find('.easyui-combotree').combotree('enable');
		$('#' + dgname).find('select').removeAttr("disabled","disabled");
		$('#' + dgname).find('textarea').removeAttr("disabled","disabled");
		$('#' + dgname).find('.dialog-button').removeAttr("hidden","hidden");
		$('#' + dgname).find('input').css("background-color","");
		$('#' + dgname).find('checkbox').css("background-color","");
		$('#' + dgname).find('select').css("background-color","");
		$('#' + dgname).find('textarea').css("background-color","");
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
		width : 250,
		timeout : 2000,
		style : {
			right : '',
			bottom : ''
		}
	});
}

/**
 * 查询
 * @param dgname datagrid id
 * @param formname 变淡formname
 */
function searchFunc(datagridname, formname) {
    var data = $.serializeObject($("#" + formname).form());
    $("#" + datagridname).datagrid("load", data);
}

/**
 * 清空条件
 * @param dgname datagrid id
 * @param formname 变淡formname
 */
function clearSearch(datagridname, formname) {
    $("#" + datagridname).datagrid("load", {});
    $("#" + formname).find("input").val("");
}

$.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

function optFormater(value, row, index) {
	var id_code = row.id;
	var opt = '';
	var detail = '<a href="#" onclick="goDetail(' + row.id + ')">详细</a> |  ';
	var edit = '<a href="javascript:openDialog_edit(' + id_code
			+ ')">编辑</a> | ';
	var del = '<a href="#" onclick="doDel(' + row.id + ')">删除</a>';
	return opt + detail + edit + del;
};
