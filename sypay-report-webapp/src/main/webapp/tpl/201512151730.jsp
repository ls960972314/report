<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10865;
	var conData = [];
	var mychart10865 = null;
	var hsql = "";
	var dsql = "";
	var wsql = "";
	var msql = 10865;
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		var obj = new Object();
		obj.name = 'begintime';
		obj.value = $("#begintime10865").val();
		obj.option = '日期';
		obj.conName = '开始时间';
		obj.type = "input";
		conData.push(obj);
		
		var obj1 = new Object();
		obj1.name = 'endtime';
		obj1.value = $("#endtime10865").val();
		obj1.option = '日期';
		obj1.conName = '结束时间';
		obj1.type = "input";
		conData.push(obj1);
		
		var obj3 = new Object();
		obj3.name = 'memberNo';
		obj3.value = $("#memberNo10865").val();
		obj3.option = '文本';
		obj3.conName = '会员号';
		obj3.type = "checkbox";
		conData.push(obj3);
		
		var obj4 = new Object();
		obj4.name = 'empNum';
		obj4.value = $("#empNum10865").val();
		obj4.option = '文本';
		obj4.conName = '工号';
		obj4.type = "input";
		conData.push(obj4);
		
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		pieTime(10865);
		
	});
	
	function timePickInit () {
		pieDatePickInit($("#begintime10865"), '开始时间');
		pieDatePickInit($("#endtime10865"), '结束时间');
	}
	                    
	function changeLine10865(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201512151730.jsp') != -1) {
			pieTime(10865);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#begintime10865").val() == "" || $("#endtime10865").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#memberNo10865").val() == "" && $("#empNum10865").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10865").val();
		conData[1].value = $("#endtime10865").val();
		conData[2].value = replaceDot($("#memberNo10865").val());
		conData[3].value = $("#empNum10865").val();
		$("#list10865").jqGrid('GridUnload');//重新构造
        initJqGrid10865();
	}
	
	
	function initJqGrid10865() {
		var gatheJson = "";
		jQuery("#list10865").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['日期','会员号','金额(元)','工号','职位名称','区编码','区名称','组织编码','组织名称','机构代码','机构名称','离职标记'],
		   	colModel:[
		   		{name:'trade_month',index:'trade_month', width:80},
		   		{name:'member_no',index:'member_no', width:80},
		   		{name:'amt_sum',index:'amt_sum', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'emp_num',index:'emp_num', width:80},
		   		{name:'position_name',index:'position_name', width:80},
		   		{name:'area_code',index:'area_code', width:80},
		   		{name:'area_name',index:'area_name', width:80},
		   		{name:'org_code',index:'org_code', width:80},
		   		{name:'org_name',index:'org_name', width:80},
		   		{name:'account_code',index:'account_code', width:80},
		   		{name:'account_name',index:'account_name', width:80},
		   		{name:'cancel_flag',index:'cancel_flag', width:80}
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
		   	pager: '#pager10865',
		   	sortname: 'gender',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"内部员工运费金额报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var valueList = jQuery("#list10865").jqGrid('getCol','amt_sum');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++) {
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	sum_cnt = sum_cnt.toFixed(2);
			    gatheJson = "{trade_month:'汇总',amt_sum:" + sum_cnt + "}";
			    
			    jQuery("#list10865").jqGrid(
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
		$("#content10865").css("width",$(this).width()*0.8);
		myChart10865.resize();
		$("#list10865").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#begintime10865").val() == "" || $("#endtime10865").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#memberNo10865").val() == "" && $("#empNum10865").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10865").val();
		conData[1].value = $("#endtime10865").val();
		conData[2].value = replaceDot($("#memberNo10865").val());
		conData[3].value = $("#empNum10865").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('日期,会员号,金额,工号,职位名称,区编码,区名称,组织编码,组织名称,机构代码,机构名称,离职标记');
		$("#fileName").val('内部员工运费金额报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10865'>
        <div id='head10865' style='font-size: initial;margin-bottom: 30px;height: 20%;'>
            <div id="head1" style="height:25%;text-align: left; margin-top: 6px;">
            	开始时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="begintime" id="begintime10865" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10865" class="hasDatepicker">
           </div>
           <div id="head2" style="height:25%; text-align: left; margin-top: 6px;">
            	工号<span style="margin-left: 40px;"></span>
            	<input type="text" name="empNum" id="empNum10865"/>
           </div>
           <div id="head3" style="height:25%; text-align: left; margin-top: 6px;">
            	<label style="float:left; ">会员号</label><span style="margin-left: 28px;"></span>
            	<textarea rows="3" cols="100" id="memberNo10865" name="memberNo"></textarea>
           </div>
           <div id="head4" style="height:25%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
		<div id='middle10865' style='height:35%; width:93%;float: left;'>
			<table id="list10865"></table>
			<div id="pager10865" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10865"></rp:pie>