<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="rp" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js" type="text/javascript"></script>
<c:set var="loadScript">
	<script type="text/javascript">
	var fileName = "";
	var oTimer = null;
	var qid = 10827;
	var conData = [];
	var mychart10827 = null;
	var hsql = "";
	var dsql = 10827;
	var wsql = "";
	var msql = "";
	var qsql = "";
	var ysql = "";
	var showSqlId = "";
	/* 初始化 */
	$(function() {
		window.document.getElementById("fileToUpload").disabled = false;
		navgChange ();
		pieTime(10827);
		topNavInit (hsql,dsql,wsql,msql,qsql,ysql);
		initJqGrid10827();
		
	});
	
	function getProgress() {
		var now = new Date();
	    $.ajax({
	        type: "post", 
	        url : "impExcelFile/uploadProgress", 
	        dataType:'json',
	        data: {now:now
	     	},
	        success: function(data) {
	        	data = data.data;
	        	$("#progress_percent").text(data.percent);
	            $("#progress_bar").width(data.percent);
	            $("#has_upload").text(data.mbRead);
	            $("#upload_speed").text(data.speed);
	            $("#upload_count").text(data.importNum);
	        },
	        error: function(err) {
	        	$("#progress_percent").text("Error");
	        }
	    });
	}

	/**
	 * 提交上传文件
	 */
	function fSubmit() {
		if ($("#batchNo").val() == "") {
			jAlert("请填写批次号","警告");
			return;
		}
		if ($("#fileToUpload").val() == "") {
			jAlert("请选择文件","警告");
			return;
		}
		$("#process").show();
		$("#cancel").show();
		$("#info").show();
		$("#success_info").hide();
	    //文件名
	   	fileName = $("#fileToUpload").val().split('/').pop().split('\\').pop();
	    //进度和百分比
	    $("#progress_percent").text("0%");
	    $("#progress_bar").width("0%");
	    $("#progress_all").show();
	    oTimer = setInterval("getProgress()", 1000);
	    ajaxFileUpload();
	    //document.getElementById("upload_form").submit();
	    window.document.getElementById("fileToUpload").disabled = true;
	}

	/**
	 * 上传文件
	 */
	function ajaxFileUpload() {
	    $.ajaxFileUpload({
	        url: 'impExcelFile/upload',
	        secureuri: false,
	        fileElementId: 'fileToUpload',
	        dataType: 'json',
	        data: {
	        	batchNo: $("#batchNo").val()
	        },
	        success: function(data, status) {
	        	getProgress();
	           	window.clearInterval(oTimer);
	            if (data.code == '0') {
	            	
	            	$("#info").hide();
	            	$("#success_info").show();
	            	$("#success_info").text(fileName + "\t 上传成功");
	            	$("#process").hide();
	            	$("#cancel").hide();
	            	$("#fileToUpload").val("");
	            	window.document.getElementById("fileToUpload").disabled = false;
	            	//上传进度和上传速度清0
	            	$("#has_upload").text("0");
	                $("#upload_speed").text("0");
	                $("#upload_count").text("0");
	                $("#progress_percent").text("0%");
	                $("#progress_bar").width("0%");
	            } else{
	             	$("#progress_all").hide();
	             	$("#fileToUpload").val("");
	             	
	             	jAlert("上传错误！","警告");
	            }
	        },
	        error: function(data, status, e) {
	        	window.clearInterval(oTimer);
	            jAlert(e);
	        }
	    })
	    return false;
	}
	//显示弹框 
	function showCont(){
		$("#TB_overlayBG").css({
			display:"block",height:$(document).height()
		});
		$(".yxbox").css({
			left:($("body").width()-$(".yxbox").width())/2-20+"px",
			top:($(window).height()-$(".yxbox").height())/2+$(window).scrollTop()+"px",
			display:"block"
		});
		$("#progress_all").hide();
	}
	// 关闭弹框 
	function closeCont(){
		$("#TB_overlayBG").hide();
		$(".yxbox").hide();
		$("#list10827").jqGrid('GridUnload');//重新构造
		initJqGrid10827();
	}
	
	function timePickInit () {
	}
	                    
	function changeLine10827(selected) {
	}
	
	function showChangeReport(file) {
		if (file.indexOf('tpl/importExcel.jsp') != -1) {
			pieTime(10827);
			pieLook();
		}
	}

	function initJqGrid10827() {
		jQuery("#list10827").jqGrid({
		   	url:'report/reportShowQueryData',
			datatype: "json",
			mtype:'post',
			colNames:['序号','上传时间','批次号','动作','用户名','上传条数','消耗时间(毫秒)'],
		   	colModel:[
				  {name:'rn',index:'rn', width:15},
				  {name:'rp_date',index:'rp_date', width:80},
				  {name:'batch_no',index:'batch_no', width:80},
				  {name:'ope_action',index:'ope_action', width:100},
		   	      {name:'user_name',index:'user_name', width:100},
				  {name:'sum_cnt',index:'sum_cnt', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}},
		          {name:'waste_time',index:'waste_time', width:80,formatter:'integer', formatoptions:{thousandsSeparator: ","}}
		   	],
		   	height : $(window).height() * 0.25,
			width : $(window).width() * 0.75,
	 		postData : {
				qid : qid,
				condition : JSON.stringify(conData)
			},
		   	//autowidth:true,
		   	rowNum:10,
		   	rowList:[10,20,30,50],
		   	pager: '#pager10827',
		   	sortname: 'age_range',
		    viewrecords: true,
		    sortorder: "desc",
		    caption:"上传文件信息",
		    footerrow:true,
		    userDataOnFooter : true,
		    loadComplete:function(){
		    	
		    }
		});
		jqGridPager();
	}

	$(window).resize(function() {
		$("#content10827").css("width",$(this).width()*0.8);
		myChart10827.resize();
		$("#list10827").setGridWidth($(window).width() * 0.75);
	});
	</script>
</c:set>
<body>
	<div id='content10827'>
		<div id='head10827' style='font-size: initial; margin-bottom: 30px;'>
			<div id="head1" style="height: 20%; text-align: left; margin-top: 6px;">
			<input type="button" value="上传" onclick="showCont()" id= "upload_button"/>
				 <span style="margin-left: 10px;"></span>
			</div>
		</div>
		<div id='middle10827' style='height: 35%; width: 93%; float: left;'>
			<table id="list10827"></table>
			<div id="pager10827"></div>
		</div>
	</div>
	
	<div class="yxbox">
	    <h2><a href="#" class="fr" onclick="closeCont();">关闭</a>上传文件(超过1G文件上传同步较慢)</h2>
	    <div class="pd15">
	    	<form name="uploadForm" id="upload_form"  action="#" method="post" enctype="multipart/form-data">
		    	<p class="mb20">
		    	批次号：<input type="text" id="batchNo" name= "batchNo" size="20"/><input type="button" value="提交" onclick="fSubmit()"/>
		    		<input type="file"  name="file" id="fileToUpload" title="请选择要上传的文件">
		    	</p>
		        <div class="br"  style="display:none;" id="progress_all">
		        	<ul>
		            	<li><h1><a href="#" class="fr" id="cancel">取消</a></h1>
		                	<div class="process clearfix" id="process">
								<span class="progress-box">
									<span class="progress-bar" style="width: 0%;" id="progress_bar"></span>
								</span>
		                        <span id="progress_percent">0%</span>
		                    </div>
		                    <div class="info" id="info">
		                    	已上传：<span id="has_upload">0</span>MB  速度：<span id="upload_speed">0</span>KB/s<br/>
		                    	已上传：<span id="upload_count">0</span>条
		                    </div>
		                    
		                    <div class="info" id="success_info" style="display: none;"></div>
		                </li>
		            </ul>
		        </div>
	        </form>
	    </div>
	</div>
	<div id="TB_overlayBG">&nbsp;</div>
</body>
<rp:pie loadScript="${loadScript}" index="10827"></rp:pie>