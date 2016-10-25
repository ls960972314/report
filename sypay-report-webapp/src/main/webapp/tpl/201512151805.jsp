<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10867;
	var conData = [];
	var mychart10867 = null;
	var hsql = "";
	var dsql = 10867;
	var wsql = "";
	var msql = "";
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		var obj = new Object();
		obj.name = 'begintime';
		obj.value = $("#begintime10867").val();
		obj.option = '日期';
		obj.conName = '开始时间';
		obj.type = "input";
		conData.push(obj);
		
		var obj1 = new Object();
		obj1.name = 'endtime';
		obj1.value = $("#endtime10867").val();
		obj1.option = '日期';
		obj1.conName = '结束时间';
		obj1.type = "input";
		conData.push(obj1);
		
		var obj2 = new Object();
		obj2.name = 'areaCode';
		obj2.value = $("#areaCode10867").val();
		obj2.option = '文本';
		obj2.conName = '区部编码';
		obj2.type = "input";
		conData.push(obj2);
		
		var obj3 = new Object();
		obj3.name = 'memberNo';
		obj3.value = $("#memberNo10867").val();
		obj3.option = '文本';
		obj3.conName = '会员号';
		obj3.type = "checkbox";
		conData.push(obj3);
		
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		pieTime(10867);
		
	});
	
	function timePickInit () {
		pieDatePickInit($("#begintime10867"), '开始时间');
		pieDatePickInit($("#endtime10867"), '结束时间');
	}
	                    
	function changeLine10867(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201512151805.jsp') != -1) {
			pieTime(10867);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#begintime10867").val() == "" || $("#endtime10867").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#memberNo10867").val() == "" && $("#areaCode10867").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10867").val();
		conData[1].value = $("#endtime10867").val();
		conData[2].value = $("#areaCode10867").val();
		conData[3].value = replaceDot($("#memberNo10867").val());
		$("#list10867").jqGrid('GridUnload');//重新构造
        initJqGrid10867();
	}
	
	
	function initJqGrid10867() {
		var gatheJson = "";
		jQuery("#list10867").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['日期','区部编码','分点部编码','工号','职务','会员号','会员名','支付订单号','运单号','运费(元)','顺丰金(元)'],
		   	colModel:[
		   		{name:'TRADE_DATE',index:'TRADE_DATE', width:100},
		   		{name:'AREA_CODE',index:'AREA_CODE', width:80},
		   		{name:'ORG_CODE',index:'ORG_CODE', width:80},
		   		{name:'EMP_NO',index:'EMP_NO', width:80},
		   		{name:'POSITION_NAME',index:'POSITION_NAME', width:80},
		   		{name:'MEMBER_NO',index:'MEMBER_NO', width:80},
		   		{name:'MENBER_NAME',index:'MENBER_NAME', width:80},
		   		{name:'PAY_BUSINESS_NO',index:'PAY_BUSINESS_NO', width:80},
		   		{name:'EXPRESS_SN',index:'EXPRESS_SN', width:80},
		   		{name:'YD_AMT',index:'YD_AMT', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'PREF_AMT',index:'PREF_AMT', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}}
		   	],
		   	height : $(window).height() * 0.25,
			width : $(window).width() * 0.75,
	 		postData : {
				qid : qid,
				condition :JSON.stringify(conData)
			},
		   	//autowidth:true,
		   	rowNum:1000,
		   	rowList:[10,20,30,50,1000],
		   	pager: '#pager10867',
		   	sortname: 'gender',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"顺丰金赠送明细报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var sum_cnt1 = 0;
		    	var valueList = jQuery("#list10867").jqGrid('getCol','YD_AMT');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	
		    	var valueList1 = jQuery("#list10867").jqGrid('getCol','PREF_AMT');
		    	valueList1 = valueList1.reverse();
		    	for(var i=0;i<valueList1.length;i++){
		    		valueList1[i] = valueList1[i] * 1;
		    		sum_cnt1 = sum_cnt1 + valueList1[i];
		    	}
		    	sum_cnt = sum_cnt.toFixed(2);
		    	sum_cnt1 = sum_cnt1.toFixed(2);
			    gatheJson = "{TRADE_DATE:'汇总',YD_AMT:" + sum_cnt + ",PREF_AMT:"+sum_cnt1+"}";
			    
			    jQuery("#list10867").jqGrid(
						'footerData',
						'set',
						eval('(' + gatheJson + ')') );	
		    }
		});
		jqGridPager();
	}
	function replaceDot(str) {
		var oldValue = str;
		while (oldValue.indexOf("，") != -1) {
			str = oldValue.replace("，", ",");
			oldValue = str;
		}
		return oldValue;
	}
	$(window).resize(function() {
		$("#content10867").css("width",$(this).width()*0.8);
		myChart10867.resize();
		$("#list10867").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#begintime10867").val() == "" || $("#endtime10867").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#memberNo10867").val() == "" && $("#areaCode10867").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10867").val();
		conData[1].value = $("#endtime10867").val();
		conData[2].value = $("#areaCode10867").val();
		conData[3].value = replaceDot($("#memberNo10867").val());
		var conditions = JSON.stringify(conData);
		
		$("#title").val('日期,区部编码,分点部编码,工号,职务,会员号,会员名,支付订单号,运单号,运费(元),顺丰金(元)');
		$("#fileName").val('顺丰金赠送明细报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10867'>
        <div id='head10867' style='font-size: initial;margin-bottom: 30px;height: 20%;'>
            <div id="head1" style="height:25%;text-align: left; margin-top: 6px;">
            	开始时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="begintime" id="begintime10867" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10867" class="hasDatepicker">
           </div>
           <div id="head2" style="height:25%; text-align: left; margin-top: 6px;">
            	<label style="float:left; ">区部编码</label><span style="margin-left: 10px;"></span>
            	<input type="text" name="areaCode" id="areaCode10867" />
           </div>
           <div id="head3" style="height:25%; text-align: left; margin-top: 6px;">
            	<label style="float:left; ">会员号</label><span style="margin-left: 28px;"></span>
            	<textarea rows="3" cols="100" id="memberNo10867" name="memberNo"></textarea>
           </div>
           <div id="head4" style="height:25%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
		<div id='middle10867' style='height:35%; width:93%;float: left;'>
			<table id="list10867"></table>
			<div id="pager10867" ></div>
		</div>
        <form action="exp/smartExpCsv" method="post" id="loadFormId">
            <input type="hidden" name="title" id="title"/>
            <input type="hidden" name="fileName" id="fileName"/>
            <input type="hidden" name="qid" id="qid"/>
            <input type="hidden" name="condition" id="condition"/>
            <input type="hidden" name="formatCols" id ="formatCols">
        </form>
    </div>
</body>
<rp:pie  loadScript="${loadScript}" index="10867"></rp:pie>