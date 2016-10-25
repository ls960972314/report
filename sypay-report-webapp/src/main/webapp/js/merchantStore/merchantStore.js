var contextPath = $("#script").attr("contextPath");

var _url;

//��ѯ
function searchFunc(){
	//��ȡ datagrid �Ĳ�ѯ����
	var params = $('#merchantStoreTable').datagrid('options').queryParams; 
	 //���л���ѯ�?Ԫ��ΪJSON����
	var fields = $('#queryFrom').serializeArray();
	$.each( fields, function(i, field){
		//���ò�ѯ����
		params[field.name] = field.value;
	
	}); 
	//��������������¼���
	$('#merchantStoreTable').datagrid('reload'); 
}

//����
function clearFunc(){
	$('#queryFrom').form('clear');
	searchFunc();
}


//�½��ŵ���ת
function addMchStore(title,formName,divName, url) {
	$('#'+divName).dialog('open').dialog('setTitle', title);
	$('#'+formName).form('clear');
	_url = url;
}

//�༭��ת
function updateMchStore(title,formName,divName,gridTable, url){
	var row = $('#' + gridTable).datagrid('getSelected');
	if (row) {
		$('#' + divName).dialog('open').dialog('setTitle', title);
		$('#' + formName).form('load', row);
		_url = url;
	} else {
		tip('��ѡ��һ�У�');
	}
}

//����
function saveMchStore(formName,divName,gridTable){
$('#merchantStoreForm').form('submit',{    
    url: _url,    
    onSubmit: function(){    
        return $(this).form('validate');    
    },    
    success: function(result){    
        var result = eval('('+result+')');  
        if (result.success){    
            $('#merchantStoreDialog').dialog('close');      // close the dialog    
            $('#merchantStoreTable').datagrid('reload');    // reload the user data    
        } else {    
            $.messager.show({    
                title: 'Error',    
                msg: result.msg    
            });    
        }    
    }    
});    
} 