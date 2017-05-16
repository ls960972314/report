
# 代码结构说明
## 说明
使用 spring mvc,mysql,echarts,mybaits,hibernate,jQuery,jqGrid,easyui,redis等

web自动化配置报表平台(只需配置对应的oracle或者mysql语句,不需要写代码)

## 演示地址

(只提供了配置后的展示,配置页面暂时没有放开权限):http://123.207.227.237/report 账号 visitor密码123456（提供的测试报表，只有2016-10-26有数据（没有测试数据），选择时间范围时请注意，否则看不到效果）

 
## 项目结构
<img src="http://123.207.227.237/report/images/dispute-project.png" width="95%" />

<pre>
report-common-dal    数据层
report-common-util   工具类层
report-common-model  数据模型层
report-common-repository  基础数据查询层
report-biz-share    业务共享层
report-biz-admin    报表后台管理业务层
report-biz-query    报表查询业务层
report-facade    各系统接口层
report-web-admin    报表运行服务
report-web-query     报表查询后台服务
</pre>
report-web-admin中包含大部分操作,报表的查询和下载等处理放到report-web-query中,report-web-admin通过rpc调用report-web-query中的接口


## 正在优化

目前将代码拆分,从单个war包拆分为多个maven子工程,存在大量的耦合,代码不规范问题

# 报表操作说明
项目演示地址:http://123.207.227.237/report
账号:visitor   密码:123456

提供的测试报表,只有2016-10-26有数据(没有测试数据,拿的是resource表做测试),选择时间范围时请注意,否则看不到数据

**原文链接**：http://blog.csdn.net/u011506468/article/details/47682417
** git地址：https://github.com/ls960972314/report

因为之前公司对内的报表需求太多,开发太累,便写了一个自动化构建报表的项目,持续完善中.

配置化增加报表,支持一表多图的展示,支持导出报表,不需要写页面,只需要用以下的方法来配置即可.首先我准备了一些比较简单的数据来供测试用,下面的介绍都使用这个表做测试,如下图

![测试数据](http://img.blog.csdn.net/20170418092817578?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
# 新增条件
名词介绍

- 所属SQL条件:sql中要插入条件的条件名匹配符
- 第几行:条件所处的行数
- 条件类型:多媒体作用,此处有两个作用1.选择创建输入框的时候增加日期特效;2.模糊查询条件
- 条件名称:展示在界面上的条件名词
- 相关值:后面用到时讲
- 选项:条件类型

## 新增输入框类型条件
输入框有两种类型可供选择

- 正常的输入框,如下图中将选项选择为“表单(input)”,条件类型选择为“文本”
- 带日期控件的输入框,如下图中将选项选择为“表单(input)”,条件类型选择为“日期”,点击新增按钮后,可以新增一个输入框类型条件.
![新增输入框类型条件](http://img.blog.csdn.net/20170418094259083?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 新增固定单选列表类型条件
将选项选择为“单选(select)”,在值中输入想展示的数据,以逗号为分隔符,此处我输入1,2,3然后点击增加,可以增加一个单选列表的条件(可以将条件类型设置为模糊查询)
![新增固定单选列表类型条件](http://img.blog.csdn.net/20170418094649118?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 新增动态单选列表类型条件
和 *新增固定单选列表类型条件* 不同之处就是,此处可以根据用户输入的sql动态的展示单选条件
将选项选择为“单选(select)”,在值中输入
```
select '2017-01-03' from DUAL
UNION ALL
select '2017-01-02' from DUAL
UNION ALL
select '2017-01-01' from DUAL
```
然后点击增加,可以增加一个单选列表的条件(可以将条件类型设置为模糊查询)
![新增动态单选列表类型条件](http://img.blog.csdn.net/20170418095404950?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## 新增固定多选框类型条件
将选项选择为“多选(checkbox)”,在值中输入想展示的数据,以逗号为分隔符,此处我输入1,2,3然后点击增加,可以增加一个多选框的条件
![新增固定多选框类型条件](http://img.blog.csdn.net/20170418100028578?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 新增动态多选框类型条件
和 *新增固定多选框类型条件* 不同之处就是,此处可以根据用户输入的sql动态的展示多选条件
将选项选择为“多选(checkbox)”,在值中输入
```
select '2017-01-03' from DUAL
UNION ALL
select '2017-01-02' from DUAL
UNION ALL
select '2017-01-01' from DUAL
```
然后点击增加,可以增加一个多选框的条件,注意此处尽量不要展示太多的多选框,不美观(此功能用的较少,展示还需要优化)
![新增动态多选框类型条件](http://img.blog.csdn.net/20170418100650132?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 新增表格
名词解释:

- 数据库列名:sql中查出来的别名,
- 中文列名:sql中查出来的列名的中文解释
- 格式化列名:选择需要格式化的列名
- 格式化方式:选择格式化方法,有正整数,小数,负数,百分比
- 报表标志:保存报表的时候和资源管理中配置的页面url对应
- 汇总列:需要汇总的列,逗号分割(只对当前页汇总)
- 标题:报表标题
- sql名:rp_report_sql的sql配置表中sql名,无具体作用
- 时间维度:标志此sql属于该报表的哪一个时间维度,**可以点击多次保存sql来保存至不同的时间维度**,在查看报表的时候点击报表中间的按小时、按日、按周等时会动态转换数据
- 数据源:该报表查询的数据库列表,多数据源功能住要靠该选项

上面介绍过,条件中有个属性为“所属SQL条件”,该属性主要是在创建表格的时候和sql中的条件一 一对应,**如果条件中写的为{1},sql中请写:{1}**.此时我输入的sql为
```
SELECT
	id,
	user_name,
	ope_action,
	ope_id,
	waste_time,
	exception,
	create_time
FROM
	rptlog r
WHERE
	r.create_time >= str_to_date(:begintime, '%Y-%m-%d')
AND r.create_time < str_to_date(:endtime, '%Y-%m-%d') + 1
```
然后我新增了两个输入框为日期类型的条件“开始时间”和“结束时间”,分别对应sql的条件begintime和endtime,全部创建完成后,点击查看可出来结果.
![新增表格](http://img.blog.csdn.net/20170418102651660?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 新增图形
图形插件是使用百度的echarts,目前只实现了使用最多的柱状图,线型图和饼图.如果不想要保存图形,此步骤可以忽略,是否保存图形选择否即可.
## 饼图
饼图展示如下图
![新增饼图](http://img.blog.csdn.net/20170418103619783?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 线型图/柱状图
名词解释:
- 图表Option:echarts中的option(需要知道echarts如何使用),主要构造图形的标题、横坐标、纵坐标属性、legend、展示的数据属于哪些纵坐标等等属性.
- 数据库名与legend对应关系:上面表格的数据库列名和要展示的legend的对应关系,格式为“rp_date:日期,amt:金额,cnt:笔数”,**第一个请放横坐标**.

>可直接点击刷新柱状图,**默认展示规则为：从表格中第一列获取的数据为横坐标,后面按顺序展现为柱状图.最好不要使用默认的刷新图标,因为表格中的数据不可能每列都是要用来展示的,而且有的列会有中文.**
![新增柱状图](http://img.blog.csdn.net/20170418103956850?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
# 保存报表
保存报表是有顺序的,在上面保存表格成功后,请记得要把对应的sql保存,在没有保存sql的情况下,保存报表按钮是不允许点击的.原因：我设计的一张报表可以保存多个时间维度,所以一张报表可能对应多个sql,所以要先保存好sql,再将sqlid组装好一起存起来.

# 资源管理配置

此处保存报表成功后,只是将报表的一些元素存了起来,页面上并没有对应.需要去权限管理系统里的资源管理页面配置一个报表页面,然后分配权限给对应用户即可.
资源配置说明：配置的url格式为*tpl/tool/smartReportShow.jsp?reportFlag=报表标志*,报表标志即为你在新增报表页面填写的报表标志,请**严格按照格式配置**.
![资源管理](http://img.blog.csdn.net/20170418104251495?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 查看报表是否成功
到此大功告成,去你刚才配置的资源页面查看是否成功.下面展示些已经完成的报表效果,可能文档写的不是很详细,有时间再补上.

## 多条件展示
![多条件展示](http://img.blog.csdn.net/20170418105431637?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 柱状图展示
![柱状图展示](http://img.blog.csdn.net/20170418105456996?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 饼状图展示
![饼状图展示](http://img.blog.csdn.net/20170418105515856?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 二级表头展示
![二级表头展示](http://img.blog.csdn.net/20170418105536059?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 列格式化展示
![列格式化展示](http://img.blog.csdn.net/20170418105557715?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 当前页列汇总展示
![当前页列汇总展示](http://img.blog.csdn.net/20170418105628060?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 所有数据汇总展示
![所有数据汇总展示](http://img.blog.csdn.net/20170418105654325?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 多图展示
![多图展示](http://img.blog.csdn.net/20170418105722108?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 组合报表功能展示
![组合报表功能展示](http://img.blog.csdn.net/20170418105740405?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
## 文本功能展示
![文本功能展示](http://img.blog.csdn.net/20170418105801733?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMTUwNjQ2OA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


