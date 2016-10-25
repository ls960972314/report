<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10892;
	var conData = [];
	var mychart10892 = null;
	var hsql = "";
	var dsql = 10892;
	var wsql = 10893;
	var msql = 10894;
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		pieInit(10892);
		var obj = new Object();
		obj.name = 'endtime';
		obj.value = $("#endtime10892").val();
		obj.option = '日期';
		obj.conName = '结束时间';
		obj.type = "input";
		conData.push(obj);
		pieImgInit();
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		pieTime(10892);
		$('#chartOption10892').on('click', '.particle li', function() {
			//debugger;
			$('.particle li').removeClass('on');
			//$(this).addClass('on');
			$(this).addClass('on');
			currentChartName = $(this).text();
			showChart10892($(this).text());
		});
	});
	
	function sortNumber(a,b) 
	{
		var arr1 = a.split(":");
		var arr2 = b.split(":");
	return arr1[1] - arr2[1] 
	}
	
	function showChart10892(chartName) {
		if (chartName == '交易用户数占比图') {
			var legendArr = [];
			legendArr.push('交易用户数占比');
			var keyList = jQuery("#list10892").jqGrid('getCol','biz_type');
	    	keyList = keyList.reverse();
	    	var sum = jQuery("#list10892").jqGrid('getCol','sum_user_cnt');
	    	
	    	var valueList = jQuery("#list10892").jqGrid('getCol','user_per');
	    	valueList = valueList.reverse();
	    	for(var i=0;i<valueList.length;i++) {
	    		valueList[i] = valueList[i] * 1;
	    	}
	    	/* 排序 */
	    	var conArr = [];
	    	for (var i=0; i < keyList.length; i++) {
	    		conArr.push(keyList[i]+":"+valueList[i]);
	    	}
	    	conArr = conArr.sort(sortNumber);
	    	var newKeyList = [];
	    	var newValueList = [];
	    	for (var j=0; j < conArr.length; j++) {
	    		var cons = conArr[j].split(":");
    			newKeyList.push(cons[0]);
    			newValueList.push(cons[1]);
	    	}
	    	
	    	option10892.grid.x=80;
	    	option10892.title.text = '交易用户数占比图';
	    	option10892.title.subtext = '交易总用户数: ' + sum[0];
	    	option10892.legend.data = legendArr;
	    	option10892.yAxis[0].data = newKeyList;
	    	option10892.series[0].name='交易用户数占比';
	    	option10892.series[0].data=newValueList;
	    	
		    myChart10892.setOption(option10892, true);
		} else if (chartName == '顺丰员工交易用户数占比图') {
			var legendArr = [];
			legendArr.push('顺丰员工交易用户数占比');
			var keyList = jQuery("#list10892").jqGrid('getCol','biz_type');
	    	keyList = keyList.reverse();
	    	var sum = jQuery("#list10892").jqGrid('getCol','sum_emp_user_cnt');
	    	var valueList = jQuery("#list10892").jqGrid('getCol','user_emp_per');
	    	valueList = valueList.reverse();
	    	for(var i=0;i<valueList.length;i++){
	    		valueList[i] = valueList[i] * 1;
	    	}
	    	/* 排序 */
	    	var conArr = [];
	    	for (var i=0; i < keyList.length; i++) {
	    		conArr.push(keyList[i]+":"+valueList[i]);
	    	}
	    	conArr = conArr.sort(sortNumber);
	    	var newKeyList = [];
	    	var newValueList = [];
	    	for (var j=0; j < conArr.length; j++) {
	    		var cons = conArr[j].split(":");
    			newKeyList.push(cons[0]);
    			newValueList.push(cons[1]);
	    	}
	    	option10892.grid.x=80;
	    	option10892.title.text = '顺丰员工交易用户数占比图';
	    	option10892.title.subtext = '顺丰员工总交易用户数: ' + sum[0];
	    	option10892.legend.data = legendArr;
	    	option10892.yAxis[0].data = newKeyList;
	    	option10892.series[0].name='顺丰员工交易用户数占比';
	    	option10892.series[0].data=newValueList;
	    	
		    myChart10892.setOption(option10892, true);
		}
	}
	function timePickInit () {
		pieDatePickInit($("#endtime10892"), '结束时间');
	}
	var option10892 = {
		    title: {
		        text: '各业务交易用户占比报表',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {
		            type : 'shadow' 
		        },
		        formatter : '{b}:{c0}%'
		    },
		    legend: {
		        y: 55,
		        data:['GML']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    grid: {
		    	x:100,
		        y: 80,
		        y2: 30
		    },
		    xAxis : [
		        {
		            type : 'value',
		            position: 'top',
		            splitLine: {show: false},
		            axisLabel: {show: false}
		        }
		    ],
		    yAxis : [
		        {
		            type : 'category',
		            splitLine: {show: false},
		            data : ['重庆', '天津', '上海', '北京'],
		            axisLabel: {
		                show: true,
		                interval: 0
		            }
		        }
		    ],
		    series : [
		        {
		            name:'GML',
		            type:'bar',
		            stack: '总量',
		            itemStyle : { 
					    normal: {
					        label : {
					            show: true,
					            position: 'right',
					            formatter: '{c}%',
					            textStyle: {
		                            color: 'black'
		                        }
					        }
					    }
					},
		            data:[38, 50, 33, 72]
		        }
		    ]
		};
	                    
	function changeLine10892(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201512221520.jsp') != -1) {
			pieTime(10892);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#endtime10892").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		conData[0].value = $("#endtime10892").val();
		$("#list10892").jqGrid('GridUnload');//重新构造
        initJqGrid10892();
	}
	
	
	function initJqGrid10892() {
		var gatheJson = "";
		jQuery("#list10892").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['序号','业务类型','交易总用户数','该业务交易用户数','交易用户数占比','顺丰员工总交易用户数','该业务顺丰员工交易用户数','顺丰员工交易用户数占比'],
		   	colModel:[
		   		{name:'rw',index:'rw', width:100},
		   		{name:'biz_type',index:'biz_type', width:100},
		   		{name:'sum_user_cnt',index:'sum_user_cnt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'user_cnt',index:'user_cnt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'user_per',index:'user_per', width:80,formatter:'currency', formatoptions:{suffix: "% "}},
		   		{name:'sum_emp_user_cnt',index:'sum_emp_user_cnt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'suc_emp_cnt',index:'suc_emp_cnt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'user_emp_per',index:'user_emp_per', width:80,formatter:'currency', formatoptions:{suffix: "% "}}
		   	],
		   	height : $(window).height() * 0.25,
			width : $(window).width() * 0.75,
	 		postData : {
				qid : qid,
				condition :JSON.stringify(conData)
			},
		   	//autowidth:true,
		   	rowNum:20,
		   	rowList:[10,20,30,50],
		   	pager: '#pager10892',
		   	sortname: 'age_range',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"各业务交易用户占比报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var legendArr = [];
				legendArr.push('交易用户数占比');
				var keyList = jQuery("#list10892").jqGrid('getCol','biz_type');
				for(var i=0;i<keyList.length;i++){
					keyList[i] = trim(keyList[i]);
		    	}
		    	keyList = keyList.reverse();
		    	var sum = jQuery("#list10892").jqGrid('getCol','sum_user_cnt');
		    	var valueList = jQuery("#list10892").jqGrid('getCol','user_per');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	
		    	/* 排序 */
		    	var conArr = [];
		    	for (var i=0; i < keyList.length; i++) {
		    		conArr.push(keyList[i]+":"+valueList[i]);
		    	}
		    	conArr = conArr.sort(sortNumber);
		    	var newKeyList = [];
		    	var newValueList = [];
		    	for (var j=0; j < conArr.length; j++) {
		    		var cons = conArr[j].split(":");
	    			newKeyList.push(cons[0]);
	    			newValueList.push(cons[1]);
		    	}
		    	option10892.grid.x=80;
		    	option10892.title.subtext = '交易总用户数: ' + sum[0];
		    	option10892.title.text = '交易用户数占比图';
		    	option10892.legend.data = legendArr;
		    	option10892.yAxis[0].data = newKeyList;
		    	option10892.series[0].name='交易用户数占比';
		    	option10892.series[0].data=newValueList;
		    	sum_cnt = sum_cnt.toFixed(2);
			    
			    myChart10892.setOption(option10892, true);
		    }
		});
		jqGridPager();
	}

	$(window).resize(function() {
		$("#content10892").css("width",$(this).width()*0.8);
		myChart10892.resize();
		$("#list10892").setGridWidth($(window).width() * 0.75);
	});
	
	function pieLoadReport () {
		if ($("#endtime10892").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		
		conData[0].value = $("#endtime10892").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('序号,业务类型,交易总用户数,该业务交易用户数,交易用户数占比,顺丰员工总交易用户数,该业务顺丰员工交易用户数,顺丰员工交易用户数占比');
		$("#fileName").val('各业务交易用户占比报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#formatCols").val('交易用户数占比:百分比,顺丰员工交易用户数占比:百分比');
		$("#loadFormId").submit();
	}
	
	</script>
	</c:set>
<body>
        <div id='content10892'>
        <div id='head10892' style='font-size: initial;margin-bottom: 30px;'>
            <div id="head1" style="height:20%;text-align: left; margin-top: 6px;">
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10892" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
           </div>
           <div id="head2" style="height:20%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
        <div id="chartOption10892" class="mod mod1" style="height:65%;width:95%;padding-bottom: 30px;">
	            
			<div class="mod-header radius clearfix">
				<h2></h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period10892">
						<ul>
						<li class="on" particle="-30" default="on">交易用户数占比图</li>
						<li particle="-30" >顺丰员工交易用户数占比图</li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="mod-body">
			   <div id="echart10892" style="height: 100%; width: 98%; margin-left: auto; margin-right: auto;"></div>
		   </div>
		</div>
		
		<div id='middle10892' style='height:35%; width:93%;float: left;'>
			<table id="list10892"></table>
			<div id="pager10892" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10892"></rp:pie>