
## 说明
使用 spring mvc,mysql,echarts,mybaits,hibernate,jQuery,jqGrid,easyui,redis等

web自动化配置报表平台(只需配置对应的oracle或者mysql语句,不需要写代码)

##演示地址

(只提供了配置后的展示,配置页面暂时没有放开权限):http://123.207.227.237/report 账号 visitor密码123456（提供的测试报表，只有2016-10-26有数据（没有测试数据），选择时间范围时请注意，否则看不到效果）

 
##项目结构
report-common-dal    数据层
report-common-util   工具类层
report-common-model  数据模型层
report-common-repository  基础数据查询层

report-biz-share    业务共享层
report-biz-admin    报表后台管理业务层
report-biz-query    报表查询业务层

report-facade    各系统接口层
report-web-admin    报表运行服务
report-web-query     报表查询后台服务

##正在优化

目前将代码拆分,从单个war包拆分为多个maven子工程,存在大量的耦合,代码不规范问题
