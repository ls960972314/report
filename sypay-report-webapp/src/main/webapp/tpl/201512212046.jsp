<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10895;
	var conData = [];
	var mychart10895 = null;
	var hsql = "";
	var dsql = 10895;
	var wsql = 10896;
	var msql = 10897;
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		pieInit(10895);
		var obj = new Object();
		obj.name = 'endtime';
		obj.value = $("#endtime10895").val();
		obj.option = '日期';
		obj.conName = '结束时间';
		obj.type = "input";
		conData.push(obj);
		pieImgInit();
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		pieTime(10895);
	});
	
	function timePickInit () {
		pieDatePickInit($("#endtime10895"), '结束时间');
	}
	var option10895 = {
		    title: {
		        text: '各业务交易笔数占比图',
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
	                    
	function changeLine10895(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201512212046.jsp') != -1) {
			pieTime(10895);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#endtime10895").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		conData[0].value = $("#endtime10895").val();
		$("#list10895").jqGrid('GridUnload');//重新构造
        initJqGrid10895();
	}
	
	
	function initJqGrid10895() {
		var gatheJson = "";
		jQuery("#list10895").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['序号','业务类型','交易总金额','该业务交易金额','交易金额占比'],
		   	colModel:[
		   		{name:'rw',index:'rw', width:100},
		   		{name:'biz_type',index:'biz_type', width:100},
		   		{name:'sum_amount',index:'sum_amount', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'suc_amt',index:'suc_amt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'user_per',index:'user_per', width:80,formatter:'currency', formatoptions:{suffix: "% "}}
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
		   	pager: '#pager10895',
		   	sortname: 'age_range',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"各业务交易金额占比报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var legendArr = [];
				legendArr.push('各业务交易金额占比');
				var keyList = jQuery("#list10895").jqGrid('getCol','biz_type');
				for(var i=0;i<keyList.length;i++){
					keyList[i] = trim(keyList[i]);
		    	}
		    	keyList = keyList.reverse();
		    	var sum = jQuery("#list10895").jqGrid('getCol','sum_amount');
		    	var valueList = jQuery("#list10895").jqGrid('getCol','user_per');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    	}
		    	var sucList = jQuery("#list10895").jqGrid('getCol','suc_amt');
		    	sucList = sucList.reverse();
		    	for(var i=0;i<sucList.length;i++){
		    		sucList[i] = sucList[i] * 1;
		    		sum_cnt = sum_cnt + sucList[i];
		    	}
		    	
		    	option10895.grid.x=80;
		    	option10895.title.subtext = '交易总金额: ' + sum[0];
		    	option10895.title.text = '各业务交易金额占比图';
		    	option10895.legend.data = legendArr;
		    	option10895.yAxis[0].data = keyList;
		    	option10895.series[0].name='各业务交易金额占比';
		    	option10895.series[0].data= valueList;
		    	sum_cnt = sum_cnt.toFixed(2);
			    
			    myChart10895.setOption(option10895, true);
			    gatheJson = "{rw:'汇总',suc_amt:" + sum_cnt + "}";
			    
			    jQuery("#list10895").jqGrid(
						'footerData',
						'set',
						eval('(' + gatheJson + ')') );	
		    }
		});
		jqGridPager();
	}

	$(window).resize(function() {
		$("#content10895").css("width",$(this).width()*0.8);
		myChart10895.resize();
		$("#list10895").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#endtime10895").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		
		conData[0].value = $("#endtime10895").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('序号,业务类型,交易总金额,该业务交易金额,交易金额占比');
		$("#fileName").val('各业务交易金额占比报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#formatCols").val('交易金额占比:百分比');
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10895'>
        <div id='head10895' style='font-size: initial;margin-bottom: 30px;'>
            <div id="head1" style="height:20%;text-align: left; margin-top: 6px;">
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10895" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
           </div>
           <div id="head2" style="height:20%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
        <div id="chartOption10895" class="mod mod1" style="height:65%;width:95%;padding-bottom: 30px;">
	            
			<div class="mod-header radius clearfix">
				<h2></h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period10895">
						<ul>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="mod-body">
			   <div id="echart10895" style="height: 100%; width: 98%; margin-left: auto; margin-right: auto;"></div>
		   </div>
		</div>
		
		<div id='middle10895' style='height:35%; width:93%;float: left;'>
			<table id="list10895"></table>
			<div id="pager10895" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10895"></rp:pie>