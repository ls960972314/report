function formatterDate(value) {
	var date = new Date(value);
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hours = date.getHours();
	var minutes = date.getMinutes();
	var seconds = date.getSeconds();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	if (hours < 10) {
		hours = "0" + hours;
	}

	if (minutes < 10) {
		minutes = "0" + minutes;
	}

	if (seconds < 10) {
		seconds = "0" + seconds;
	}
	return date.getFullYear() + '-' + month + '-'
			+ day + " " + hours + ":" + minutes + ":" + seconds;
}

function formatterNumber(value, jd) {
	if (value == null || typeof (value) == 'undefined')
		return "";

	return val.toFixed(jd);
}

function formatStatus(val, row)
{
	return val == 1 ? "正常" : "停用";
}

function formatCasType(val, row)
{
	if(val == 1)
	{
		return "内部";
	}
	else
	{
		return "外部";
	}
}

function formatterRoleNames(val, row)
{
	var namesStr = "";
	for(var i = 0; i < val.length; i++)
	{
		var tmp = val[i]["roleName"];
		if(tmp != "null")
		{
			namesStr += tmp;
		}
		
		if(i < val.length - 1)
		{
			namesStr += ";";
		}
	}
	return namesStr;
}

function formatGroup(val, row)
{
	return val == 'null' ? '' : val;
}