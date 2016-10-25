<%@ page language="java" pageEncoding="utf-8"%>
<script src="${pageContext.request.contextPath}/js/reportShow.js" type="text/javascript"></script>
<style>
.digest-block {
	width: 20%;
	height: 70px;
	float: left;
	background: #f5f5f5;
	border-right: 1px solid #d2d2d2;
	border-bottom: 1px solid #d2d2d2;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
	position: relative;
	cursor: pointer;
}

.digest-block h4 {
	width: 85%;
	margin: 0 auto;
	margin-top: 10px;
	font-size: 14px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	text-align: center;
}

.digest-block h1 {
	text-align: center;
	font-size: 32px;
	margin-top: 10px;
}
</style>
<body>
	<div id='content'>
		<div id='head' style='font-size: initial; margin-bottom: 40px;'>
		</div>
		<div id="staticDiv" class="mod mod1" style=" width: 95%;">
			<div class="mod-header clearfix">
				<h2>
				统计数据栏
			    </h2>
			</div>
			<div id="staticData" class="mod-body" style="height:69px;">
			</div>
		</div>
		<div id="chartOption" class="mod mod1" style="height: 35%; width: 95%; padding-bottom: 30px;margin-top:20px;">
			<div class="mod-header clearfix">
				<h2>
			    </h2>
				<div class="option">
					<div class="particle js-um-tab" id="trends_period">
						<ul>
						</ul>
					</div>
				</div>
			</div>
			<div class="mod-body">
					<div id="echart" style="height: 100%; width: 98%; margin-left: auto; margin-right: auto;"></div>
			</div>
		</div>

		<div id='middle' style='height: 35%; width: 93%; float: left;'>
			<table id="list"></table>
			<div id="pager"></div>
		</div>
		<form action="exp/smartExpCsv" method="post" id="loadFormId">
			<input type="hidden" name="title" id="title" /> <input type="hidden"
				name="fileName" id="fileName" /> <input type="hidden" name="qid"
				id="qid" /> <input type="hidden" name="condition" id="condition" />
			<input type="hidden" name="formatCols" id="formatCols">
		</form>
	</div>
	<!-- <div id="tip_appDigest" class="tips" style="">
      <div class="corner"></div>
      <p><span class="highlight">累计用户：</span>截止到现在，启动过应用的所有独立用户（去重，以设备为判断标准）</p>
      <p><span class="highlight">过去7天活跃用户：</span>过去7天启动过应用的用户（去重），启动过一次的用户即视为活跃用户，包括新用户与老用户</p>
      <p><span class="highlight">过去30天活跃用户：</span>过去30天启动过应用的用户（去重），启动过一次的用户即视为活跃用户，包括新用户与老用户</p>
      <p><span class="highlight">过去7天平均日使用时长：</span>过去7天的平均日使用时长的均值</p>
      <p><span class="highlight">总错误率：</span>当日错误数/当日启动次数</p>
      <p><span class="highlight">累计付费用户：</span>所有有付费行为的设备总数</p>
      <p><span class="highlight">累计付费金额：</span>所有充值到该游戏的金额总数（人民币）</p>
      <p><span class="highlight">付费率：</span>当日付费用户/当日活跃用户</p>
      <p><span class="highlight">ARPU：</span>累计付费金额/累计用户</p>
      <p><span class="highlight">ARPPU：</span>累计付费金额/累计付费用户</p>
      <p><span class="highlight">变化率：</span>截至今日上个整点时刻的该日数据相对于昨日同时段的变化率</p>
    </div> -->
</body>
