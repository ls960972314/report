<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="loadScript">
	<script type="text/javascript">
	var qid = 10671;
	var conData = [];
	var mychart10671 = null;
	var hsql = "";
	var dsql = 10671;
	var wsql = "";
	var msql = "";
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		navgChange ();
		pieInit(10671);
		var obj = new Object();
		obj.name = 'endtime';
		obj.value = $("#endtime10671").val();
		obj.option = '日期';
		obj.conName = '结束时间';
		obj.type = "input";
		conData.push(obj);
		pieImgInit();
		pieTime(10671);
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
	});
	
	function timePickInit () {
		pieDatePickInit($("#endtime10671"), '结束时间');
	}
	var option10671 = {
		    title : {
		        text: '用户性别分布饼状图',
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
		            name:'性别',
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
	                    
	function changeLine10671(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/201511161518.jsp') != -1) {
			pieTime(10671);
			pieLook();
		}
	}
	
	function pieLook() {
		if ($("#endtime10671").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		conData[0].value = $("#endtime10671").val();
		$("#list10671").jqGrid('GridUnload');//重新构造
        initJqGrid10671();
	}
	
	
	function initJqGrid10671() {
		var gatheJson = "";
		jQuery("#list10671").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['所属性别','用户数','用户数占比'],
		   	colModel:[
		   		{name:'gender',index:'gender', width:100},
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
		   	pager: '#pager10671',
		   	sortname: 'gender',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"用户性别分布报表",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	var sum_cnt = 0;
		    	var keyList = jQuery("#list10671").jqGrid('getCol','gender');
		    	keyList = keyList.reverse();
		    	
		    	var valueList = jQuery("#list10671").jqGrid('getCol','cnt');
		    	valueList = valueList.reverse();
		    	for(var i=0;i<valueList.length;i++){
		    		valueList[i] = valueList[i] * 1;
		    		sum_cnt = sum_cnt + valueList[i];
		    	}
		    	option10671.legend.data = keyList;
		    	var pieData = [];
		    	for (var i=0; i<keyList.length; i++) {
		    		var o = new Object();
		    		o.name = keyList[i];
		    		o.value = valueList[i];
		    		pieData.push(o);
		    	}
		    	option10671.series[0].data=pieData;
		    	
			    myChart10671.setOption(option10671, true);
			    gatheJson = "{gender:'汇总',cnt:" + sum_cnt + "}";
			    
			    jQuery("#list10671").jqGrid(
						'footerData',
						'set',
						eval('(' + gatheJson + ')') );	
		    }
		});
		jqGridPager();
	}

	$(window).resize(function() {
		$("#content10671").css("width",$(this).width()*0.8);
		myChart10671.resize();
		$("#list10671").setGridWidth($(window).width() * 0.75);
	});
	function pieLoadReport () {
		if ($("#endtime10671").val() == "") {
			jAlert('请选择正确的时间','警告');
			return ;
		}
		
		conData[0].value = $("#endtime10671").val();
		var conditions = JSON.stringify(conData);
		
		$("#title").val('所属性别,用户数,用户数占比');
		$("#fileName").val('用户性别分布报表');
		$("#qid").val(qid);
		$("#condition").val(conditions);
		$("#formatCols").val('用户数:正整数,用户数占比:百分比');
		$("#loadFormId").submit();
	}
	</script>
	</c:set>
<body>
        <div id='content10671'>
        <div id='head10671' style='font-size: initial;margin-bottom: 30px;'>
            <div id="head1" style="height:20%;text-align: left; margin-top: 6px;">
            	结束时间<span style="margin-left: 10px;"></span>
            	<input type="text" name="endtime" id="endtime10671" class="hasDatepicker">
            	<span style="margin-left: 10px;"></span>
           </div>
           <div id="head2" style="height:20%;text-align: left; margin-top: 6px;">
	           <input type="button" onclick="pieLook()" value="查看">
	           <span style="margin-left: 10px;"></span>
	           <input type="button" onclick="pieLoadReport()" value="导出Excel">
           </div>
        </div>
        
        <div id="chartOption10671" class="mod mod1" style="height:35%;width:95%;padding-bottom: 30px;">
	            
			<div class="mod-header radius clearfix">
				<h2></h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period10671">
						<ul>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="mod-body">
			   <div id="echart10671" style="height: 100%; width: 98%; margin-left: auto; margin-right: auto;"></div>
		   </div>
		</div>
		
		<div id='middle10671' style='height:35%; width:93%;float: left;'>
			<table id="list10671"></table>
			<div id="pager10671" ></div>
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
<rp:pie  loadScript="${loadScript}" index="10671"></rp:pie>