<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10864;
	var conData = [];
	var mychart10864 = null;
	var hsql = "";
	var dsql = 10864;
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
		obj.value = $("#begintime10864").val();
		obj.option = '日期';
		obj.conName = '开始时间';
		obj.type = "input";
		conData.push(obj);
		
		var obj1 = new Object();
		obj1.name = 'endtime';
		obj1.value = $("#endtime10864").val();
		obj1.option = '日期';
		obj1.conName = '结束时间';
		obj1.type = "input";
		conData.push(obj1);
		
		var obj2 = new Object();
		obj2.name = 'loginName';
		obj2.value = $("#loginName10864").val();
		obj2.option = '文本';
		obj2.conName = '手机号';
		obj2.type = "input";
		conData.push(obj2);
		
		var obj3 = new Object();
		obj3.name = 'memberNo';
		obj3.value = $("#memberNo10864").val();
		obj3.option = '文本';
		obj3.conName = '会员号';
		obj3.type = "checkbox";
		conData.push(obj3);
		
		var obj4 = new Object();
		obj4.name = 'empNo';
		obj4.value = $("#empNo10864").val();
		obj4.option = '文本';
		obj4.conName = '工号';
		obj4.type = "input";
		conData.push(obj4);
		
		var obj5 = new Object();
		obj5.name = 'orgCode';
		obj5.value = $("#orgCode10864").val();
		obj5.option = '文本';
		obj5.conName = '机构代码';
		obj5.type = "input";
		conData.push(obj5);
		
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		pieTime(10864);
		
	});
	
	function timePickInit () {
		pieDatePickInit($("#begintime10864"), '开始时间');
		pieDatePickInit($("#endtime10864"), '结束时间');
	}
	                    
	function changeLine10864(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201512151501.jsp') != -1) {
			pieTime(10864);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#begintime10864").val() == "" || $("#endtime10864").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#loginName10864").val() == "" && $("#memberNo10864").val() == ""
				&& $("#empNo10864").val() == "" && $("#orgCode10864").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10864").val();
		conData[1].value = $("#endtime10864").val();
		conData[2].value = $("#loginName10864").val();
		conData[3].value = replaceDot($("#memberNo10864").val());
		conData[4].value = $("#empNo10864").val();
		conData[5].value = $("#orgCode10864").val();
		$("#list10864").jqGrid('GridUnload');//重新构造
        initJqGrid10864();
	}
	
	
	function initJqGrid10864() {
		var gatheJson = "";
		jQuery("#list10864").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['支付业务流水号','交易结束时间','会员号','个人会员名称','个人登录名','商品名称','金额(元)','币种','组织编码','组织名称','区编码','区名称','员工姓名','速运运单号'],
		   	colModel:[
		   		{name:'pay_business_no',index:'pay_business_no', width:100},
		   		{name:'trade_date',index:'trade_date', width:100},
		   		{name:'member_no',index:'member_no', width:80},
		   		{name:'member_name',index:'member_name', width:80},
		   		{name:'login_name',index:'login_name', width:80},
		   		{name:'order_name',index:'order_name', width:80},
		   		{name:'amt_sum',index:'amt_sum', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'ccy',index:'ccy', width:80},
		   		{name:'org_code',index:'org_code', width:80},
		   		{name:'org_name',index:'org_name', width:80},
		   		{name:'area_code',index:'area_code', width:80},
		   		{name:'area_name',index:'area_name', width:80},
		   		{name:'emp_name',index:'emp_name', width:80},
		   		{name:'express_sn',index:'express_sn', width:80}
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
		   	pager: '#pager10864',
		   	sortname: 'gender',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"用户运费明细报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var valueList = jQuery("#list10864").jqGrid('getCol','amt_sum');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	sum_cnt = sum_cnt.toFixed(2);
			    gatheJson = "{pay_business_no:'汇总',amt_sum:" + sum_cnt + "}";
			    
			    jQuery("#list10864").jqGrid(
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
		$("#content10864").css("width",$(this).width()*0.8);
		myChart10864.resize();
		$("#list10864").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#begintime10864").val() == "" || $("#endtime10864").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		if ($("#loginName10864").val() == "" && $("#memberNo10864").val() == ""
				&& $("#empNo10864").val() == "" && $("#orgCode10864").val() == "") {
			jAlert('请至少填写一个条件','警告');
			return ;
		}
		conData[0].value = $("#begintime10864").val();
		conData[1].value = $("#endtime10864").val();
		conData[2].value = $("#loginName10864").val();
		conData[3].value = replaceDot($("#memberNo10864").val());
		conData[4].value = $("#empNo10864").val();
		conData[5].value = $("#orgCode10864").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('支付业务流水号,交易结束时间,会员号,个人会员名称,个人登录名,商品名称,金额(元),币种,组织编码,组织名称,区编码,区名称,员工姓名,速运运单号');
		$("#fileName").val('用户运费明细报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10864'>
        <div id='head10864' style='font-size: initial;margin-bottom: 30px;height: 20%;'>
            <div id="head1" style="height:25%;text-align: left; margin-top: 6px;">
            	开始时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="begintime" id="begintime10864" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10864" class="hasDatepicker">
           </div>
           <div id="head2" style="height:25%; text-align: left; margin-top: 6px;">
           		手机号<span style="margin-left: 26px;"></span>
            	<input type="text" name="loginName" id="loginName10864"/>
            	<span style="margin-left: 10px;"></span>
            	工号<span style="margin-left: 42px;"></span>
            	<input type="text" name="empNo" id="empNo10864"/>
            	<span style="margin-left: 10px;"></span>
            	机构代码<span style="margin-left: 10px;"></span>
            	<input type="text" name="orgCode" id="orgCode10864"/>
           </div>
           <div id="head3" style="height:25%; text-align: left; margin-top: 6px;">
            	<label style="float:left; ">会员号</label><span style="margin-left: 28px;"></span>
            	<textarea rows="3" cols="100" id="memberNo10864" name="memberNo"></textarea>
           </div>
           <div id="head4" style="height:25%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
		<div id='middle10864' style='height:35%; width:93%;float: left;'>
			<table id="list10864"></table>
			<div id="pager10864" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10864"></rp:pie>