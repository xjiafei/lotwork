//转换日期插件时间为UnixTimestamp
convertDate2UnixTimestamp = function(date) {
		return new Date(Date.parse(date.replace(/-/g, "/"))).getTime();
	};

//获取第n天的凌晨时间
getStartTimeOfNDay = function(n) {
	var exp =new Date(new Date()-0+86400000*n-(new Date().getHours()*60*60+new Date().getMinutes()*60+new Date().getSeconds())*1000);
    exp.setTime(exp.getTime());
    return exp.format('yyyy-MM-dd hh:mm:ss');
}

//转换UnixTimestamp为日期插件时间
formatDate = function(unixTime) {
	var tep=new Date();
	tep.setTime(unixTime);
	return tep.format('yyyy-MM-dd hh:mm:ss');
}



//格式化日期
Date.prototype.format =function(format)
{
var o = {
"M+" : this.getMonth()+1, //month
"d+" : this.getDate(), //day
"h+" : this.getHours(), //hour
"m+" : this.getMinutes(), //minute
"s+" : this.getSeconds(), //second
"q+" : Math.floor((this.getMonth()+3)/3), //quarter
"S" : this.getMilliseconds() //millisecond
}
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
(this.getFullYear()+"").substr(4- RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,
RegExp.$1.length==1? o[k] :
("00"+ o[k]).substr((""+ o[k]).length));
return format;
}