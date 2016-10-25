<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10670;
	var conData = [];
	var mychart10670 = null;
	var hsql = "";
	var dsql = 10670;
	var wsql = "";
	var msql = "";
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		pieInit(10670);
		var obj = new Object();
		obj.name = 'endtime';
		obj.value = $("#endtime10670").val();
		obj.option = '日期';
		obj.conName = '结束时间';
		obj.type = "input";
		conData.push(obj);
		pieImgInit();
		pieTime(10670);
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
	});
	
	function timePickInit () {
		pieDatePickInit($("#endtime10670"), '结束时间');
	}
	var option10670 = {
		    title : {
		        text: '用户年龄分布饼状图',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'年龄',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            itemStyle : {
		                normal : {
		                    label : {
		                      formatter : function (params){
		                    	  return params.name + " (" + params.percent + '%)'
		                      },
		                      show: true,
		   					 position:'outer'
		                    },
		                    labelLine : {
		                        show : true
		                    }
		                }
		            },
		            data:[
		                {value:335, name:'直接访问'},
		                {value:310, name:'邮件营销'},
		                {value:234, name:'联盟广告'},
		                {value:135, name:'视频广告'},
		                {value:1548, name:'搜索引擎'}
		            ]
		        }
		    ]
		};
	                    
	function changeLine10670(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201511121337.jsp') != -1) {
			pieTime(10670);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#endtime10670").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		conData[0].value = $("#endtime10670").val();
		$("#list10670").jqGrid('GridUnload');//重新构造
        initJqGrid10670();
	}
	
	
	function initJqGrid10670() {
		var gatheJson = "";
		jQuery("#list10670").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['所属年龄范围','用户数','用户数占比'],
		   	colModel:[
		   		{name:'age_range',index:'age_range', width:100},
		   		{name:'cnt',index:'cnt', width:100,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		   		{name:'cnt_per',index:'cnt_per', width:80,formatter:'currency', formatoptions:{suffix: "% "}}
		   	],
		   	height : $(window).height() * 0.25,
			width : $(window).width() * 0.75,
	 		postData : {
				qid : qid,
				condition :JSON.stringify(conData)
			},
		   	//autowidth:true,
		   	rowNum:10,
		   	rowList:[10,20,30,50],
		   	pager: '#pager10670',
		   	sortname: 'age_range',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"用户年龄分布报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var keyList = jQuery("#list10670").jqGrid('getCol','age_range');
		    	keyList = keyList.reverse();
		    	
		    	var valueList = jQuery("#list10670").jqGrid('getCol','cnt');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	option10670.legend.data = keyList;
		    	var pieData = [];
		    	for (var i=0; i<keyList.length; i++) {
		    		var o = new Object();
		    		o.name = keyList[i];
		    		o.value = valueList[i];
		    		pieData.push(o);
		    	}
		    	option10670.series[0].data=pieData;
		    	
			    myChart10670.setOption(option10670, true);
			    gatheJson = "{age_range:'汇总',cnt:" + sum_cnt + "}";
			    
			    jQuery("#list10670").jqGrid(
						'footerData',
						'set',
						eval('(' + gatheJson + ')') );	
		    }
		});
		jqGridPager();
	}

	$(window).resize(function() {
		$("#content10670").css("width",$(this).width()*0.8);
		myChart10670.resize();
		$("#list10670").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#endtime10670").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		
		conData[0].value = $("#endtime10670").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('所属年龄范围,用户数,用户数占比');
		$("#fileName").val('用户年龄分布报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#formatCols").val('用户数:正整数,用户数占比:百分比');
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10670'>
        <div id='head10670' style='font-size: initial;margin-bottom: 30px;'>
            <div id="head1" style="height:20%;text-align: left; margin-top: 6px;">
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10670" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
           </div>
           <div id="head2" style="height:20%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
        <div id="chartOption10670" class="mod mod1" style="height:35%;width:95%;padding-bottom: 30px;">
	            
			<div class="mod-header radius clearfix">
				<h2></h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period10670">
						<ul>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="mod-body">
			   <div id="echart10670" style="height: 100%; width: 98%; margin-left: auto; margin-right: auto;"></div>
		   </div>
		</div>
		
		<div id='middle10670' style='height:35%; width:93%;float: left;'>
			<table id="list10670"></table>
			<div id="pager10670" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10670"></rp:pie>