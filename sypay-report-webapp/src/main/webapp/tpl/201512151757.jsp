<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10866;
	var conData = [];
	var mychart10866 = null;
	var hsql = "";
	var dsql = "";
	var wsql = "";
	var msql = 10866;
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		var obj = new Object();
		obj.name = 'begintime';
		obj.value = $("#begintime10866").val();
		obj.option = '日期';
		obj.conName = '开始时间';
		obj.type = "input";
		conData.push(obj);
		
		var obj1 = new Object();
		obj1.name = 'endtime';
		obj1.value = $("#endtime10866").val();
		obj1.option = '日期';
		obj1.conName = '结束时间';
		obj1.type = "input";
		conData.push(obj1);
		
		var obj3 = new Object();
		obj3.name = 'memberNo';
		obj3.value = $("#memberNo10866").val();
		obj3.option = '文本';
		obj3.conName = '会员号';
		obj3.type = "checkbox";
		conData.push(obj3);
		
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		pieTime(10866);
		
	});
	
	function timePickInit () {
		pieDatePickInit($("#begintime10866"), '开始时间');
		pieDatePickInit($("#endtime10866"), '结束时间');
	}
	                    
	function changeLine10866(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201512151757.jsp') != -1) {
			pieTime(10866);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#begintime10866").val() == "" || $("#endtime10866").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#memberNo10866").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10866").val();
		conData[1].value = $("#endtime10866").val();
		conData[2].value = replaceDot($("#memberNo10866").val());
		$("#list10866").jqGrid('GridUnload');//重新构造
        initJqGrid10866();
	}
	
	
	function initJqGrid10866() {
		var gatheJson = "";
		jQuery("#list10866").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['日期','会员号','赠送金额(元)'],
		   	colModel:[
		   		{name:'trade_date',index:'trade_date', width:100},
		   		{name:'member_no',index:'member_no', width:100},
		   		{name:'pref_amt',index:'pref_amt', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}}
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
		   	pager: '#pager10866',
		   	sortname: 'trade_date',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"顺丰金赠送汇总报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var valueList = jQuery("#list10866").jqGrid('getCol','pref_amt');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	sum_cnt = sum_cnt.toFixed(2);
			    gatheJson = "{trade_date:'汇总',pref_amt:" + sum_cnt + "}";
			    
			    jQuery("#list10866").jqGrid(
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
		$("#content10866").css("width",$(this).width()*0.8);
		myChart10866.resize();
		$("#list10866").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#begintime10866").val() == "" || $("#endtime10866").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#memberNo10866").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10866").val();
		conData[1].value = $("#endtime10866").val();
		conData[2].value = replaceDot($("#memberNo10866").val());
		var conditions = JSON.stringify(conData);
		
		$("#title").val('日期,会员号,赠送金额(元)');
		$("#fileName").val('顺丰金赠送汇总报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10866'>
        <div id='head10866' style='font-size: initial;margin-bottom: 30px;height: 20%;'>
            <div id="head1" style="height:25%;text-align: left; margin-top: 6px;">
            	开始时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="begintime" id="begintime10866" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10866" class="hasDatepicker">
           </div>
           <div id="head3" style="height:25%; text-align: left; margin-top: 6px;">
            	<label style="float:left; ">会员号</label><span style="margin-left: 28px;"></span>
            	<textarea rows="3" cols="100" id="memberNo10866" name="memberNo"></textarea>
           </div>
           <div id="head4" style="height:25%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
		<div id='middle10866' style='height:35%; width:93%;float: left;'>
			<table id="list10866"></table>
			<div id="pager10866" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10866"></rp:pie>