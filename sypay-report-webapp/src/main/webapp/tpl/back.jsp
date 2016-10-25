<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${pageContext.request.contextPath}/js/report-util.js" type="text/javascript"></script>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid877=877;
	$(function() { 
		time877();
	});
	function time877 () {
		var time = $('.currentItem a').attr('time');
		if (time == '时') {
			$('#mainContainer').show();
			qid877 = 833;
		} else if (time == '日') {
			$('#mainContainer').show();
			qid877 = 877;
		} else if (time == '周') {
			$('#mainContainer').show();
			qid877 = 878;
		} else if (time == '月') {
			$('#mainContainer').show();
			qid877 = 879;
		}
	}
	function showChangeReport (fileName) {
		if (fileName == 'tpl/merchant_total_turnover_d_rp.jsp?time=d'||fileName == 'tpl/merchant_total_turnover_d_rp.jsp?time=w'||fileName == 'tpl/merchant_total_turnover_d_rp.jsp?time=m') {
			time877();
			onc877(tid877);
		}
	}
	var option877 = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:[]
	    },
	    toolbox: {
	        show : true,
	        orient: 'vertical',
	        x: 'right',
	        y: 'center',
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            data : [],
	            axisLabel:{interval:0,  rotate:45}
	        }
	    ],
	    yAxis : [
           {
	            type : 'value',
	            name : '水量',
	            axisLabel : {
	                formatter: '{value} ml'
	            }
	        },
	        {
	            type : 'value',
	            name : '占比',
	            axisLabel : {
	                formatter: '{value} %'
	            }
	        }
	    ],
	    color: ['#32cd32','#FFFF00','#FF3030','#B3B3B3','#87cefa','#6495ed','#ba55d3'],
	    series : [
	        {
	            name:'直接访问',
	            type:'bar',
	            stack: '广告',
	            data:[320, 332, 301, 334, 390, 330, 320]
	        },
	        {
	            name:'邮件营销',
	            type:'bar',
	            stack: '广告',
	            data:[120, 132, 101, 134, 90, 230, 210]
	        },
	        {
	            name:'联盟广告',
	            type:'bar',
	            stack: '广告',
	            data:[220, 182, 191, 234, 290, 330, 310]
	        },
	        {
	            name:'视频广告',
	            type:'bar',
	            stack: '广告',
	            data:[150, 232, 201, 154, 190, 330, 410]
	        },
	        {
	            name:'搜索引擎',
	            type:'bar',
	            stack: '广告',
	            data:[862, 1018, 964, 1026, 1679, 1600, 1570]
	        },
	        {
	            name:'搜索引擎',
	            type:'bar',
	            stack: '广告',
	            data:[862, 1018, 964, 1026, 1679, 1600, 1570]
	        },
	        {
	            name:'搜索引擎',
	            type:'bar',
	            yAxisIndex: 1,
	            data:[862, 1018, 964, 1026, 1679, 1600, 1570]
	        }
	    ]
	};
	                    
	function changeLine877(selected) {
	    if (selected['占比']) {
	        run877(selected);
	    } else if (!selected['占比']) {
	        run877(selected);
	    }
	}
	
	function run877(selected) {
	        
            statDateList = option877.xAxis[0].data;
		    successList = option877.series[0].data;
		    unknownList = option877.series[1].data;
		    failList = option877.series[2].data;
		    rejectList = option877.series[3].data;
		    riskList = option877.series[4].data;
		    totalList = option877.series[5].data;
		    rateList = option877.series[6].data;
		    
	    	option877.legend.data = new Array('成功金额','未知金额','失败金额','拒绝金额','风控金额','总金额','占比');
	    	option877.xAxis[0].data = statDateList;
	    	option877.xAxis[0].axisLabel.interval=0;
		    option877.yAxis[0].name = "金额";
		    option877.yAxis[0].axisLabel.formatter="{value} 笔";
		    
		    option877.series[0].name="成功金额";
		    option877.series[0].stack = "堆叠1";
		    option877.series[0].type="bar";
		    option877.series[0].data = successList;
		    
		    option877.series[1].name="未知金额";
		    option877.series[1].type="bar";
		    option877.series[1].stack = "堆叠1";
		    option877.series[1].data = unknownList;
		    
		    option877.series[2].name="失败金额";
		    option877.series[2].type="bar";
		    option877.series[2].stack = "堆叠1";
		    option877.series[2].data = failList;
		     
		    
		    option877.series[3].name="拒绝金额";
		    option877.series[3].type="bar";
		    option877.series[3].stack="堆叠1";
		    option877.series[3].data = rejectList;
		    
		    option877.series[4].name="风控金额";
		    option877.series[4].type="bar";
		    option877.series[4].stack="堆叠1";
		    option877.series[4].data = riskList;

		    option877.series[5].name="总金额";
		    option877.series[5].type="bar";
		    option877.series[5].stack="堆叠2";
		    option877.series[5].data = totalList;
		    
	    	var len = option877.series.length-1;
	    	var devide = 0;
	    	var rateList = [];
	    	for (var i=0;i<statDateList.length;i++) {
	    	    devide = 0;
	    	    len = option877.series.length-1;
	    	    if (selected['成功金额']) {
	                devide = successList[i] + devide;
	            }
	            if (selected['未知金额']) {
	                devide = unknownList[i] + devide;
	            }
	            if (selected['失败金额']) {
	                devide = failList[i] + devide;
	            }
	            if (selected['拒绝金额']) {
	                devide = rejectList[i] + devide;
	            }
	            if (selected['风控金额']) {
	                devide = riskList[i] + devide;
	            }
                rateList.push((devide*100/totalList[i]).toFixed(2));
	    	}
	    	 
		    option877.series[6].name="占比";
		    option877.series[6].type="line";
		    option877.series[6].data=rateList;
		    myChart877.setOption(option877);
	}
	
	var tid877 = 1;
	var initFilters877 = function(){
	    var beginTime = $("#beginTime877").val();
	    var endTime = $("#endTime877").val();
	    var error_reason = $('#error_reason877').val();
	    if (beginTime == '') {
	        beginTime = getDayBeginTime();
	        $("#beginTime877").val(getDayBeginTime1());
	    } else {
	        beginTime=beginTime.replace(/[-]/g,"");
	    }
	    if (endTime == '') {
		   endTime = getDayEndTime();
		   $("#endTime877").val(getDayEndTime1);
	    } else {
	        endTime=endTime.replace(/[-]/g,"");
	    }
	    if ($('.currentItem a').attr('time') != '时') {
		    if (tid877==1) {
		         if (error_reason == 'total') {
		             var filters = {groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime}],
			                 other:{groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime}]}};
		         } else {
		             var filters = {groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime}],
			                 other:{groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime},
			                  {field:"error_reason",op:"eq",data:error_reason}]}};
		         }
		    } else {
		        if (error_reason == 'total') {
		            var filters = {groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime},
			                  {field:"wg",op:"eq",data:tid877}
			                  ],
			                 other:{groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime},
			                  {field:"wg",op:"eq",data:tid877}]}};
		        } else {
		        var filters = {groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime},
			                  {field:"wg",op:"eq",data:tid877}],
			                 other:{groupOp:"and",rules:[
			                  {field:"rp_date",op:"ge",data:beginTime},
			                  {field:"rp_date",op:"le",data:endTime},
			                  {field:"wg",op:"eq",data:tid877},
			                  {field:"error_reason",op:"eq",data:error_reason}]}};
			    }
		    }
	    } else {
	    	if (tid877==1) {
	            if (error_reason == 'total') {
	                var filters = {groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime}],
	   	                 other:{groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime}]}};
	            } else {
	                var filters = {groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime}],
	   	                 other:{groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime},
	   	                  {field:"error_reason",op:"eq",data:error_reason}]}};
	            }
	       } else {
	           if (error_reason == 'total') {
	               var filters = {groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime}],
	   	                 other:{groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime},
	   	                  {field:"wg",op:"eq",data:tid877}]}};
	           } else {
	           var filters = {groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime},
	   	                  {field:"wg",op:"eq",data:tid877}],
	   	                 other:{groupOp:"and",rules:[
	   	                  {field:"substr(rp_date,1,8)",op:"ge",data:beginTime},
	   	                  {field:"substr(rp_date,1,8)",op:"le",data:endTime},
	   	                  {field:"wg",op:"eq",data:tid877},
	   	                  {field:"error_reason",op:"eq",data:error_reason}]}};
	   	       }
	       }
	    }
		return JSON.stringify(filters);
	};
	function onc877(id) {
	    tid877 = id;
		var postData = $("#list877").jqGrid("getGridParam", "postData");  
		$.extend(postData, {"filters": initFilters877(),qid:qid877});  
		$("#list877").jqGrid("setGridParam", {search: true}).trigger("reloadGrid", [{page:1}]);
		return true;
	}
	$.ajax({
		url: "/omp/report?qid=1132&rows=1000&page=1",
		type: "GET",
		cache:false,
		dataType: "json",
		success: function (data) {
			    var rows = data.rows;
				$("#navigation877").html("");
				for(var i=0;i<rows.length;i++){
					$("#navigation877").append("<li style=\"float:right;\"><a href=\"#\" onclick=\"onc877("+rows[i][0]+")\">"+rows[i][2]+"</a></li>");
		    	}
		}
	});
	
	jQuery("#list877").jqGrid({
	   	url:'/omp/report',
		datatype: "json",
		mtype:'post',
		colNames:['统计日期','成功交易金额(万元)','未知金额(万元)','失败金额(万元)','拒绝金额(万元)','风控金额(万元)','总金额(万元)'],
	   	colModel:[
	   		{name:'rp_date',index:'rp_date', width:100,sorttype:'date', searchoptions:{sopt:['eq','ne','le','lt','gt','ge']}},
	   		{name:'success_total_turnover',index:'success_total_turnover', width:100},
	   		{name:'unknown_total_turnover',index:'unknown_total_turnover', width:80},
	   		{name:'fail_total_turnover',index:'fail_total_turnover', width:80},
	   		{name:'rejected_total_turnover',index:'rejected_total_turnover', width:80},
	   		{name:'risk_total_turnover',index:'risk_total_turnover', width:80},
	   		{name:'total_total_turnover',index:'total_total_turnover',formatter:'integer', formatoptions:{thousandsSeparator: ','}}
	   	],
	   	height: $(window).height()*0.25,
	   	width:$(window).width()*0.8,
	   	postData:{filters:initFilters877(),
	   		      qid:qid877},
	   	//autowidth:true,
	   	rowNum:10,
	   	rowList:[10,20,30,50],
	   	pager: '#pager877',
	   	sortname: 'rp_date',
	    viewrecords: true,
	    sortorder: "desc",
	    caption:"交易金额统计报表",
	    loadComplete:function(){
	    	var statDateList = jQuery("#list877").jqGrid('getCol','rp_date');
	    	statDateList = statDateList.reverse();
	    	
	    	var successList = jQuery("#list877").jqGrid('getCol','success_total_turnover');
	    	successList = successList.reverse();
	    	for(var i=0;i<successList.length;i++){
	    		successList[i] = successList[i] * 1;
	    	}
	    	var unknownList = jQuery("#list877").jqGrid('getCol','unknown_total_turnover');
	    	unknownList = unknownList.reverse();
	    	for(var i=0;i<unknownList.length;i++){
	    		unknownList[i] = unknownList[i] * 1;
	    	}
	    	var failList = jQuery("#list877").jqGrid('getCol','fail_total_turnover');
	    	failList = failList.reverse();
	    	for(var i=0;i<failList.length;i++){
	    		failList[i] = failList[i] * 1;
	    	}
	    	var rejectList = jQuery("#list877").jqGrid('getCol','rejected_total_turnover');
	    	rejectList = rejectList.reverse();
	    	for(var i=0;i<rejectList.length;i++){
	    		rejectList[i] = rejectList[i] * 1;
	    	}
	    	var riskList = jQuery("#list877").jqGrid('getCol','risk_total_turnover');
	    	riskList = riskList.reverse();
	    	for(var i=0;i<riskList.length;i++){
	    		riskList[i] = riskList[i] * 1;
	    	}
	    	var totalList = jQuery("#list877").jqGrid('getCol','total_total_turnover');
	    	totalList = totalList.reverse();
	    	for(var i=0;i<totalList.length;i++){
	    		totalList[i] = totalList[i] * 1;
	    	}
			    
	    	option877.legend.data = new Array('成功金额','未知金额','失败金额','拒绝金额','风控金额','总金额','占比');
	    	option877.xAxis[0].data = statDateList;
	    	option877.xAxis[0].axisLabel.interval=0;
		    option877.yAxis[0].name = "金额";
		    option877.yAxis[0].axisLabel.formatter="{value} 笔";
		    
		    option877.series[0].name="成功金额";
		    option877.series[0].stack = "堆叠1";
		    option877.series[0].type="bar";
		    option877.series[0].data = successList;
		    
		    option877.series[1].name="未知金额";
		    option877.series[1].type="bar";
		    option877.series[1].stack = "堆叠1";
		    option877.series[1].data = unknownList;
		    
		    option877.series[2].name="失败金额";
		    option877.series[2].type="bar";
		    option877.series[2].stack = "堆叠1";
		    option877.series[2].data = failList;
		    
		    option877.series[3].name="拒绝金额";
		    option877.series[3].type="bar";
		    option877.series[3].stack="堆叠1";
		    option877.series[3].data = rejectList;
		    
		    option877.series[4].name="风控金额";
		    option877.series[4].type="bar";
		    option877.series[4].stack="堆叠1";
		    option877.series[4].data = riskList;
		    
		    option877.series[5].name="总金额";
		    option877.series[5].type="bar";
		    option877.series[5].stack="堆叠2";
		    option877.series[5].data = totalList;
		    
	    	var len = option877.series.length-1;
	    	var devide = 0;
	    	var rateList = [];
	    	for (var i=0;i<statDateList.length;i++) {
	    	    devide = 0;
	    	    len = option877.series.length-1;
	    	    while (len--) {
	    	        if (option877.series[len].name == '成功金额') {
		                devide = successList[i] + devide;
		            }
		            if (option877.series[len].name == '未知金额') {
		                devide = unknownList[i] + devide;
		            }
		            if (option877.series[len].name == '失败金额') {
		                devide = failList[i] + devide;
		            }
		            if (option877.series[len].name == '拒绝金额') {
		                devide = rejectList[i] + devide;
		            }
		            if (option877.series[len].name == '风控金额') {
		                devide = riskList[i] + devide;
		            }
                }
                rateList.push((devide*100/totalList[i]).toFixed(2));
	    	}
	    	
		    option877.series[6].name="占比";
		    option877.series[6].type="line";
		    option877.series[6].data=rateList;
		    if (statDateList.length != 0) {
		        myChart877.clear();
		        myChart877.setOption(option877);
		    }
	    }
	});

	$(window).resize(function() {
		$("#content877").css("width",$(this).width()*0.8);
		$("#content877").css("height",$(this).height()*0.9);
	});
	</script>
</c:set>
<body>
        <input type='hidden' id='bz' db='88' da='0' wb='88' wa='1' mb='88' ma='2'/>
        <div id='content877'>
        <div id='head877' style='height:15%'>
        	<div id='head8771' style='height:33%;text-align: right;'>
        	                     开始时间：<input type="text" id="beginTime877"/>
			                     结束时间：<input type="text" id="endTime877"/>
			        <select name="error_reason" id="error_reason877">
				        <option value = "total" >所有</option>
				        <option value = "sys" >系统</option>
					    <option value = "pass">通道</option>
						<option value = "user">用户</option>
			        </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type='button' onclick='look877()' value='查看'/>
                    <input type='button' onclick='loadReport877()' value='导出Excel'/>
        	</div>
        	<div id='head8773' style='height:66%;text-align: right;' class='menu'>
        	    <ul style='list-style:none;  margin: 0px; padding: 0px; width: auto;' id='navigation877'>
        	    </ul>
        	</div>
        </div>
        <div id='middle877' style='height:35%;'>
	        <table id="list877"></table>
			<div id="pager877" ></div>
        </div>
        <div class="mod mod1">
			
			<div class="mod-header radius clearfix">
				<h2>交易金额报表</h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period877">
						<ul>
				          <li particle="-7"></li>
				          <li particle="-30" default="on" class="on"></li>
				          <li particle="-90"></li>
				          <li particle="-180"></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="mod-body">
               <div id="echart877" style="height: 100%; width: 98%;"></div>
		   </div>
		  
		  <div class="mod-bottom">
		  	<div class="showdetails" style="border-top-width: 0px;">
		  		<a href="#"></a>
		    </div>
		  </div>
		</div>
		
    </div>
</body>
<rp:echart_d  loadScript="${loadScript}" index="877" fileTitle="统计日期,成功交易金额,未知金额,失败金额,拒绝金额,风控金额" fileName="交易金额统计报表"></rp:echart_d>