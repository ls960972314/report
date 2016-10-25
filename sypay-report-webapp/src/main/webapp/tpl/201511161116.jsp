<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10672;
	var conData = [];
	var mychart10672 = null;
	var hsql = "";
	var dsql = 10672;
	var wsql = "";
	var msql = "";
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		mapInit(10672);
		var obj = new Object();
		obj.name = 'endtime';
		obj.value = $("#endtime10672").val();
		obj.option = '日期';
		obj.conName = '结束时间';
		obj.type = "input";
		conData.push(obj);
		mapTime(10672);
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
	});
	
	function timePickInit () {
		mapDatePickInit($("#endtime10672"), '结束时间');
	}
	var option10672 = {
		    title : {
		        text: '用户地域分布',
				        x:'center'
		    },
		    tooltip : {
		        trigger: 'item'
		    },
		    legend: {
		        orient: 'vertical',
		        x:'left',
		        data:['用户数']
		    },
		    dataRange: {
		        min: 0,
		        max: 2500,
		        x: 'left',
		        y: 'bottom',
		        text:['高','低'],           // 文本，默认为数值文本
		        calculable : true
		    },
		    toolbox: {
		        show: true,
		        orient : 'vertical',
		        x: 'right',
		        y: 'center',
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    roamController: {
		        show: true,
		        x: 'right',
		        mapTypeControl: {
		            'china': true
		        }
		    },
		    series : [
		        {
		            name: '用户数',
		            type: 'map',
		            mapType: 'china',
		            roam: false,
		            itemStyle:{
		                normal:{label:{show:true}},
		                emphasis:{label:{show:true}}
		            },
		            data:[]
		        }
		    ]
		};
	                    
	function changeLine10672(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201511161116.jsp') != -1) {
			mapTime(10672);
			mapLook();
		}
	}
	
	function mapLook() {
		if ($("#endtime10672").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		conData[0].value = $("#endtime10672").val();
		$("#list10672").jqGrid('GridUnload');//重新构造
        initJqGrid10672();
	}
	
	
	function initJqGrid10672() {
		var gatheJson = "";
		jQuery("#list10672").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['所属省份','用户数','用户数占比'],
		   	colModel:[
		   		{name:'province',index:'province', width:100},
		   		{name:'cnt',index:'cnt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'cnt_per',index:'cnt_per', width:100,formatter:'currency', formatoptions:{decimalSeparator:",", thousandsSeparator: ",", decimalPlaces: 2, suffix: "% "}}
		   	],
		   	height : $(window).height() * 0.25,
			width : $(window).width() * 0.75,
	 		postData : {
				qid : qid,
				condition :JSON.stringify(conData)
			},
		   	//autowidth:true,
		   	rowNum:100,
		   	rowList:[10,20,50,100],
		   	pager: '#pager10672',
		   	sortname: 'age_range',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"用户地域分布报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	mapImgInit();
		    	var max_cnt = 0;
		    	var sum_cnt = 0;
		    	var keyList = jQuery("#list10672").jqGrid('getCol','province');
		    	keyList = keyList.reverse();
		    	for (var i=0; i<keyList.length; i++) {
		    		keyList[i] = keyList[i].replace("省","");
		    		keyList[i] = keyList[i].replace("市","");
		    	}
		    	
		    	var valueList = jQuery("#list10672").jqGrid('getCol','cnt');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    		if (valueList[i] > max_cnt) {
		    			max_cnt = valueList[i];
		    		}
		    	}
		    	var mapData = [];
		    	for (var i=0; i<keyList.length; i++) {
		    		var o = new Object();
		    		o.name = keyList[i];
		    		o.value = valueList[i];
		    		mapData.push(o);
		    	}
		    	var splitNumber = option10672.dataRange.splitNumber;
		    	max_cnt = max_cnt + (50000 - max_cnt%50000);
		    	option10672.series[0].data=mapData;
		    	option10672.dataRange.max=max_cnt;
		    	
		    	try {
		    		myChart10672.setOption(option10672, true);	
				} catch (e) {
					mapImgInit();
					myChart10672.setOption(option10672, true);
					console.log("异步调用未执行完");
				}
			    
			    gatheJson = "{age_range:'汇总',cnt:" + sum_cnt + "}";
			    
			    jQuery("#list10672").jqGrid(
						'footerData',
						'set',
						eval('(' + gatheJson + ')') );	
		    }
		});
		jqGridPager();
	}
	
	function mapLoadReport () {
		if ($("#endtime10672").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		
		conData[0].value = $("#endtime10672").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('所属省份,用户数,用户数占比');
		$("#fileName").val('用户地域分布报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#formatCols").val('用户数:正整数,用户数占比:百分比');
		$("#loadFormId").submit();
	}
	
	$(window).resize(function() {
		$("#content10672").css("width",$(this).width()*0.8);
		myChart10672.resize();
		$("#list10672").setGridWidth($(window).width() * 0.75);
	});
	</script>
	</c:set>
<body>
        <div id='content10672'>
        <div id='head10672' style='font-size: initial;margin-bottom: 30px;'>
            <div id="head1" style="height:20%;text-align: left; margin-top: 6px;">
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" class="input" id="endtime10672" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
           </div>
           <div id="head2" style="height:20%;text-align: left; margin-top: 6px;">
	           <input type="button" class="constr borders" onclick="mapLook()" value="查看">
	           <span style="margin-left:10px;"></span>
	           <input type="button"class='constr borders' onclick="mapLoadReport()" value="导出Excel">
           </div>
        </div>
        
        <div id="chartOption10672" class="mod mod1" style="height:40%;width:95%;padding-bottom: 30px;">
	            
			<div class="mod-header radius clearfix">
				<h2></h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period10672">
						<ul>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="mod-body">
			   <div id="echart10672" style="height: 100%; width: 98%; margin-left: auto; margin-right: auto;"></div>
		   </div>
		</div>
		
		<div id='middle10672' style='height:35%; width:93%;float: left;'>
			<table id="list10672"></table>
			<div id="pager10672" ></div>
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
<rp:map  loadScript="${loadScript}" index="10672"></rp:map>