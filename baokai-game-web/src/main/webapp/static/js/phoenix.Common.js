	//Description:  银行卡号Luhm校验
	//Luhm校验规则：16位银行卡号（19位通用）:
	//国内的银行卡号是一串根据Luhm校验算法计算出来的数字，Luhm校验规则：16位银行卡号（19位通用）
	//1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
	//2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
	//3.将加法和加上校验位能被 10 整除。
	//bankno为银行卡号 
	function luhmCheck(bankno){
		var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）
	 
		var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
		var newArr=new Array();
		for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
			newArr.push(first15Num.substr(i,1));
		}
		var arrJiShu=new Array();  //奇数位*2的积 <9
		var arrJiShu2=new Array(); //奇数位*2的积 >9
		 
		var arrOuShu=new Array();  //偶数位数组
		for(var j=0;j<newArr.length;j++){
			if((j+1)%2==1){//奇数位
				if(parseInt(newArr[j])*2<9)
				arrJiShu.push(parseInt(newArr[j])*2);
				else
				arrJiShu2.push(parseInt(newArr[j])*2);
			}
			else //偶数位
			arrOuShu.push(newArr[j]);
		}
		 
		var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
		var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
		for(var h=0;h<arrJiShu2.length;h++){
			jishu_child1.push(parseInt(arrJiShu2[h])%10);
			jishu_child2.push(parseInt(arrJiShu2[h])/10);
		}       
		 
		var sumJiShu=0; //奇数位*2 < 9 的数组之和
		var sumOuShu=0; //偶数位数组之和
		var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
		var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
		var sumTotal=0;
		for(var m=0;m<arrJiShu.length;m++){
			sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
		}
		 
		for(var n=0;n<arrOuShu.length;n++){
			sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
		}
		 
		for(var p=0;p<jishu_child1.length;p++){
			sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
			sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
		}     
		//计算总和
		sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
		 
		//计算Luhm值
		var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;       
		var luhm= 10-k;		 
		if(lastNum==luhm){
			//$("#banknoInfo").html("Luhm验证通过");
			return true;
		}
		else{
			//$("#banknoInfo").html("银行卡号必须符合Luhm校验");
			return false;
		}       
	}

//对字符串对象增加trim去掉前后空格的方法
String.prototype.trim = function () {
	var str = this ,
	str = str.replace(/^\s\s*/, '' ),
	ws = /\s/,
	i = str.length;
	while (ws.test(str.charAt(--i)));
	return str.slice(0, i + 1);
} 
//全选，取消全选
function selectAll(obj){
	jQuery(":checkbox[id!='"+obj+"']").attr("checked",jQuery("#"+obj).attr("checked"));
}

//用户名验证(由0-9,a-z,A-Z组成的6~16个字符组成)
function validateUserName( str ){
	var patrn = /^[0-9a-zA-Z]{6,16}$/;
	if( patrn.exec(str) ){
		return true;	
	}else{
		return false;
	}
}

//密码验证(6－16位数字和字母，不能只是数字，或者只是字母，不能连续三位相同)
function validateUserPss( str ){
	var patrn = /^[0-9a-zA-Z]{6,16}$/;
	if( !patrn.exec(str) ){
		return false;
	}
	patrn = /^\d+$/;
	if( patrn.exec(str) ){
		return false;
	}
	patrn = /^[a-zA-Z]+$/;
	if( patrn.exec(str) ){
		return false;
	}
	patrn = /(.)\1{2,}/;
	if( patrn.exec(str) ){
		return false;
	}
	return true;
}

//呢称验证
function validateNickName( str ){
	var patrn = /^(.){2,6}$/;	
	if( patrn.exec(str) ){
		return true;	
	}else{
		return false;
	}
}

// 支行名称验证
function validateBranch( str ){
	var patrn = /^(.){2,24}$/;
	if( patrn.exec(str) ){
		return true;	
	}else{
		return false;
	}
}

//日期输入验证
// str : 要验证的日期字符串[格式包括 Y-M-D|Y/M/D|YMD [H[:I][:S]]]
function validateInputDate( str ){
	str = str.trim();
	if( str == "" || str == null ){
		return true;
	}
	var tempArr = str.split(" ");
	var dateArr = new Array();
	var timeArr = new Array();
	if( tempArr[0].indexOf("-") != -1 ){//2009-06-12
		dateArr = tempArr[0].split("-");
	}else if( tempArr[0].indexOf("/") != -1 ){//2009/06/12
		dateArr = tempArr[0].split("/");
	}else{// 20090612
		if( tempArr[0].toString().length < 8 ){
			return false;
		}
		dateArr[0] = tempArr[0].substring(0,4);
		dateArr[1] = tempArr[0].substring(4,6);
		dateArr[2] = tempArr[0].substring(6,8);
	}
	if( tempArr[1] == undefined || tempArr[1] == null ){
		tempArr[1] = "00:00:00";
	}
	if( tempArr[1].indexOf(":") != -1 ){
		timeArr = tempArr[1].split(":");
	}
	if( dateArr[2] != undefined && ( dateArr[0] == "" || dateArr[1] == "" ) ){
		return false;
	}
	if( dateArr[1] != undefined && dateArr[0] == "" ){
		return false;
	}
	if( timeArr[2] != undefined && ( timeArr[0] == "" || timeArr[1] == "" ) ){
		return false;
	}
	if( timeArr[1] != undefined && timeArr[0] == "" ){
		return false;
	}
	dateArr[0]  = (dateArr[0]==undefined || dateArr[0] == "") ? 1970 : dateArr[0];
	dateArr[1]  = (dateArr[1]==undefined || dateArr[1] == "") ? 0 : (dateArr[1]-1);
	dateArr[2]  = (dateArr[2]==undefined || dateArr[2] == "") ? 0 : dateArr[2];
	timeArr[0]  = (timeArr[0]==undefined || timeArr[0] == "") ? 0 : timeArr[0];
	timeArr[1]  = (timeArr[1]==undefined || timeArr[1] == "") ? 0 : timeArr[1];
	timeArr[2]  = (timeArr[2]==undefined || timeArr[2] == "") ? 0 : timeArr[2];
	var newDate = new Date(dateArr[0],dateArr[1],dateArr[2],timeArr[0],timeArr[1],timeArr[2]); 
	if( newDate.getFullYear()==dateArr[0] && newDate.getMonth()==dateArr[1] && newDate.getDate()==dateArr[2] && newDate.getHours()==timeArr[0] && newDate.getMinutes()==timeArr[1] && newDate.getSeconds()==timeArr[2] ){
		return true;
	}else{
		return false;
	}
	return true;
}

//onkeyup:限制用户资金输入只能输入浮点数，并且小数点后只能跟四位
function checkMoney( obj ){
	obj.value = formatFloat(obj.value);
}

//onkeyup:根据用户输入的资金做检测并自动转换中文大写金额(用于充值和提现)
//obj:检测对象元素，chineseid:要显示中文大小写金额的ID，maxnum：最大能输入金额
function checkWithdraw( obj,chineseid,maxnum ){
	obj.value = formatFloat(obj.value);
	if( parseFloat(obj.value) > parseFloat(maxnum) ){
		//alert("输入金额超出了可用余额");
		obj.value = maxnum;
	}
	jQuery("#"+chineseid).html( changeMoneyToChinese(obj.value) );
}

function checkWithdraw2( obj,chineseid,maxnum, available ){
	obj.value = formatFloat(obj.value);
	var temp = 0.00;
	if (parseFloat(available) > parseFloat(maxnum)){
		temp = maxnum;
	} else {
		temp = available;
	}
	if( parseFloat(obj.value) > parseFloat(maxnum) &&  parseFloat(maxnum) < parseFloat(available)){
		//alert("提现金额不能超过系统设定值");
		obj.value = temp;
	}
	if( parseFloat(obj.value) > parseFloat(available) ){
		//alert("提现金额超出了可提现限额");
		obj.value = temp;
	}
	jQuery("#"+chineseid).html( changeMoneyToChinese(obj.value) );
}

function checkemailWithdraw( obj,chineseid,maxnum ){
	obj.value = formatFloat(obj.value);
	if( parseFloat(obj.value) > parseFloat(maxnum) ){
		alert("充值金额不能高于最高充值限额");
		obj.value = maxnum;
	}
	jQuery("#"+chineseid).html( changeMoneyToChinese(obj.value) );
}

function checkOnlineWithdraw( obj,maxnum ){
	obj.value = formatFloat(obj.value);
	if( parseFloat(obj.value) > parseFloat(maxnum) ){
		alert("提现金额超出了可提现限额");
		obj.value = maxnum;
		obj.focus();
	}
}

//同上，只是做整数限制
function checkIntWithdraw( obj,chineseid,maxnum ){
	obj.value = parseInt(obj.value,10);
	obj.value = isNaN(obj.value) ? 0 : obj.value;
	if( parseFloat(obj.value) > parseFloat(maxnum) ){
		alert("输入金额超出了可用余额");	
		obj.value = parseInt(maxnum,10);
	}
	jQuery("#"+chineseid).html( changeMoneyToChinese(obj.value) );
}


//金额逗号分隔格式化
function moneyFormat(num){
	sign = Number(num) < 0 ? "-" : "";
	num = num.toString().replace(/[^\d.]/g,'');
	num = num.replace(/\.{2,}/g,'.');
	num = num.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	if( num.indexOf(".") != -1 ){
		var data = num.split('.');
		data[0]  = data[0].substr(0,15);
		var newnum = [];
		for( i=data[0].length; i>0; i-=3 ){
			newnum.unshift(data[0].substring(i,i-3));
		}
		data[0] = newnum.join(",");
		num = data[0]+'.'+(data[1].substr(0,2));
	}else{
		num = num.substr(0,15);
		var newnum = [];
		for( i=num.length; i>0; i-=3 ){
			newnum.unshift(num.substring(i,i-3));
		}
		num = newnum.join(",")+".00";
	}
	return sign+num;
}

//格式化浮点数形式(只能输入正浮点数，且小数点后只能跟四位,总体数值不能大于999999999999999共15位:数值999兆)
function formatFloat( num ){
	num = num.replace(/^[^\d]/g,'');
	num = num.replace(/[^\d.]/g,'');
	num = num.replace(/\.{2,}/g,'.');
	num = num.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	if( num.indexOf(".") != -1 ){
		var data = num.split('.');
		num = (data[0].substr(0,15))+'.'+(data[1].substr(0,2));
	}else{
		num = num.substr(0,15);
	}
	return num;
}

function moneyFormat(num){
	sign = Number(num) < 0 ? "-" : "";
    num = num.toString();
	if( num.indexOf(".") == -1 )
	{
		num = "" + num + ".00";
	}
	var data = num.split('.');
	data[0] = data[0].toString().replace(/[^\d]/g,"").substr(0,15);
	data[0] = Number(data[0]).toString();
	var newnum = [];
	for( i=data[0].length; i>0; i-=3 ){
		newnum.unshift(data[0].substring(i,i-3));
	}
	data[0] = newnum.join(",");
	data[1] = data[1].toString().substr(0,2);
	return sign+""+data[0] + "." + data[1];
}

//自动转换数字金额为大小写中文字符,返回大小写中文字符串，最大处理到999兆
function changeMoneyToChinese( money )
{
	var cnNums	= new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");	//汉字的数字
	var cnIntRadice = new Array("","拾","佰","仟");	//基本单位
	var cnIntUnits = new Array("","万","亿","兆");	//对应整数部分扩展单位
	var cnDecUnits = new Array("角","分","毫","厘");	//对应小数部分单位
	var cnInteger = "整";	//整数金额时后面跟的字符
	var cnIntLast = "元";	//整型完以后的单位
	var maxNum = 999999999999999.9999;	//最大处理的数字
	
	var IntegerNum;		//金额整数部分
	var DecimalNum;		//金额小数部分
	var ChineseStr="";	//输出的中文金额字符串
	var parts;		//分离金额后用的数组，预定义
	
	if( money == "" ){
		return "";	
	}
	
	money = parseFloat(money);
	//alert(money);
	if( money >= maxNum ){
		alert('超出最大处理数字');
		return "";
	}
	if( money == 0 ){
		ChineseStr = cnNums[0]+cnIntLast+cnInteger;
		//document.getElementById("show").value=ChineseStr;
		return ChineseStr;
	}
	money = money.toString(); //转换为字符串
	if( money.indexOf(".") == -1 ){
		IntegerNum = money;
		DecimalNum = '';
	}else{
		parts = money.split(".");
		IntegerNum = parts[0];
		DecimalNum = parts[1].substr(0,4);
	}
	if( parseInt(IntegerNum,10) > 0 ){//获取整型部分转换
		zeroCount = 0;
		IntLen = IntegerNum.length;
		for( i=0;i<IntLen;i++ ){
			n = IntegerNum.substr(i,1);
			p = IntLen - i - 1;
			q = p / 4;
            m = p % 4;
			if( n == "0" ){
				zeroCount++;
			}else{
				if( zeroCount > 0 ){
					ChineseStr += cnNums[0];
				}
				zeroCount = 0;	//归零
				ChineseStr += cnNums[parseInt(n)]+cnIntRadice[m];
			}
			if( m==0 && zeroCount<4 ){
				ChineseStr += cnIntUnits[q];
			}
		}
		ChineseStr += cnIntLast;
	//整型部分处理完毕
	}
	if( DecimalNum!= '' ){//小数部分
		decLen = DecimalNum.length;
		for( i=0; i<decLen; i++ ){
			n = DecimalNum.substr(i,1);
			if( n != '0' ){
				ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
			}
		}
	}
	if( ChineseStr == '' ){
		ChineseStr += cnNums[0]+cnIntLast+cnInteger;
	}
	else if( DecimalNum == '' ){
		ChineseStr += cnInteger;
	}
	return ChineseStr;
	
}
function replaceHTML( str ){
	str = str.replace(/[&]/g,'&amp;');
	str = str.replace(/[\"]/g,'&quot;');
	str = str.replace(/[\']/g,'&#039;');
	str = str.replace(/[<]/g,'&lt;');
	str = str.replace(/[>]/g,'&gt;');
	str = str.replace(/[ ]/g,'&nbsp;');
	return str;
}

//转换HTML标准代码为显示代码（类似PHP的htmlspecialchars_decode函数）
function replaceHTML_DECODE( str ){
	str = str.replace(/&amp;/g,'&');
	str = str.replace(/&quot;/g,'"');
	str = str.replace(/&#039;/g,'\'');
	str = str.replace(/&lt;/g,'<');
	str = str.replace(/&gt;/g,'>');
	str = str.replace(/&nbsp;/g,' ');
	return str;
}

//复制内容到剪贴板
function copyToClipboard(obj)
{
	txt = jQuery("#"+obj).html();
	if(window.clipboardData) {
		window.clipboardData.clearData();
		window.clipboardData.setData("Text", txt);
	}else if(navigator.userAgent.indexOf("Opera") != -1) {
		window.location = txt;
	}else if (window.netscape) {
		try {
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		} catch (e) {
			alert("您的firefox安全限制限制您进行剪贴板操作，请在地址栏中输入“about:config”将“signed.applets.codebase_principal_support”设置为“true”之后重试");
			return false;
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
		return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		if (!trans)
		return;
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		var copytext = txt;
		str.data = copytext;
		trans.setTransferData("text/unicode",str,copytext.length*2);
		var clipid = Components.interfaces.nsIClipboard;
		if (!clip)
		return false;
		clip.setData(trans,null,clipid.kGlobalClipboard);
	}
}


//-----------------------------------居中弹层开始（兼容IE6及遮盖select控件）-----------------------------------------
var isIE = (document.all) ? true : false;
var Sys = {};
var ua = navigator.userAgent.toLowerCase();
var s;
(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

var isIE6 = isIE && (Sys.ie=="6.0");

var abs = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};

var Class = {
	create: function() {
		return function() { this.initialize.apply(this, arguments); }
	}
}

var Extend = function(destination, source) {
	for (var property in source) {
		destination[property] = source[property];
	}
}

var Bind = function(object, fun) {
	return function() {
		return fun.apply(object, arguments);
	}
}

var Each = function(list, fun){
	for (var i = 0, len = list.length; i < len; i++) { fun(list[i], i); }
};

var Contains = function(a, b){
	return a.contains ? a != b && a.contains(b) : !!(a.compareDocumentPosition(b) & 16);
}


var OverLay = Class.create();
OverLay.prototype = {
  initialize: function(options) {

	this.SetOptions(options);
	
	this.Lay = abs(this.options.Lay) || document.body.insertBefore(document.createElement("div"), document.body.childNodes[0]);
	
	this.Color = this.options.Color;
	this.Opacity = parseInt(this.options.Opacity);
	this.zIndex = parseInt(this.options.zIndex);
	
	with(this.Lay.style){ display = "none"; zIndex = this.zIndex; left = top = 0; position = "fixed"; width = height = "100%"; }
	
	if(isIE6){
		this.Lay.style.position = "absolute";
		//ie6设置覆盖层大小程序
		this._resize = Bind(this, function(){
			this.Lay.style.width = Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth) + "px";
			this.Lay.style.height = Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) + "px";
		});
		//遮盖select
		this.Lay.innerHTML = '<iframe style="position:absolute;top:0;left:0;width:100%;height:100%;filter:alpha(opacity=0);"></iframe>'
	}
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
		Lay:		null,//覆盖层对象
		Color:		"#fff",//背景色
		Opacity:	50,//透明度(0-100)
		zIndex:		1000//层叠顺序
    };
    Extend(this.options, options || {});
  },
  //显示
  Show: function() {
	//兼容ie6
	if(isIE6){ this._resize(); window.attachEvent("onresize", this._resize); }
	//设置样式
	with(this.Lay.style){
		//设置透明度
		isIE ? filter = "alpha(opacity:" + this.Opacity + ")" : opacity = this.Opacity / 100;
		backgroundColor = this.Color; display = "block";
	}
  },
  //关闭
  Close: function() {
	this.Lay.style.display = "none";
	if(isIE6){ window.detachEvent("onresize", this._resize); }
  }
};



var LightBox = Class.create();
LightBox.prototype = {
  initialize: function(box, options) {
	
	this.Box = abs(box);//显示层
	
	this.OverLay = new OverLay(options);//覆盖层
	
	this.SetOptions(options);
	
	this.Fixed = !!this.options.Fixed;
	this.Over = !!this.options.Over;
	this.Center = !!this.options.Center;
	this.onShow = this.options.onShow;
	
	this.Box.style.zIndex = this.OverLay.zIndex + 1;
	this.Box.style.display = "none";
	
	//兼容ie6用的属性
	if(isIE6){
		this._top = this._left = 0; this._select = [];
		this._fixed = Bind(this, function(){ this.Center ? this.SetCenter() : this.SetFixed(); });
	}
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
		Over:	true,//是否显示覆盖层
		Fixed:	false,//是否固定定位
		Center:	false,//是否居中
		onShow:	function(){}//显示时执行
	};
    Extend(this.options, options || {});
  },
  //兼容ie6的固定定位程序
  SetFixed: function(){
	this.Box.style.top = document.documentElement.scrollTop - this._top + this.Box.offsetTop + "px";
	this.Box.style.left = document.documentElement.scrollLeft - this._left + this.Box.offsetLeft + "px";
	
	this._top = document.documentElement.scrollTop; this._left = document.documentElement.scrollLeft;
  },
  //兼容ie6的居中定位程序
  SetCenter: function(){
	this.Box.style.marginTop = document.documentElement.scrollTop - this.Box.offsetHeight / 2 + "px";
	this.Box.style.marginLeft = document.documentElement.scrollLeft - this.Box.offsetWidth / 2 + "px";
  },
  //显示
  Show: function(options) {
	//固定定位
	this.Box.style.position = this.Fixed && !isIE6 ? "fixed" : "absolute";

	//覆盖层
	this.Over && this.OverLay.Show();
	
	this.Box.style.display = "block";
	
	//居中
	if(this.Center){
		this.Box.style.top = this.Box.style.left = "50%";
		//设置margin
		if(this.Fixed){
			this.Box.style.marginTop = - this.Box.offsetHeight / 2 + "px";
			this.Box.style.marginLeft = - this.Box.offsetWidth / 2 + "px";
		}else{
			this.SetCenter();
		}
	}
	
	//兼容ie6
	if(isIE6){
		if(!this.Over){
			//没有覆盖层ie6需要把不在Box上的select隐藏
			this._select.length = 0;
			Each(document.getElementsByTagName("select"), Bind(this, function(o){
				if(!Contains(this.Box, o)){ o.style.visibility = "hidden"; this._select.push(o); }
			}))
		}
		//设置显示位置
		this.Center ? this.SetCenter() : this.Fixed && this.SetFixed();
		//设置定位
		this.Fixed && window.attachEvent("onscroll", this._fixed);
	}
	
	this.onShow();
  },
  //关闭
  Close: function() {
	this.Box.style.display = "none";
	this.OverLay.Close();
	if(isIE6){
		window.detachEvent("onscroll", this._fixed);
		Each(this._select, function(o){ o.style.visibility = "visible"; });
	}
  }
};
//-----------------------------------居中弹层结束------------------------------------------

//cookie操作
//$.cookie("name"); //根据cookie名字取到cookie值
//$.cookie("name", "value");//设置cookie 名字，值
//$.cookie("name", value, { expires: 7 }); //设置cookie 名字，值，生命周期等属性
//$.cookie("name", null); //根据cookie名移除cookie
jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') {
	   options = options || {};
	   if (value === null) {
		value = '';
		options = $.extend({}, options);
		options.expires = -1;
	   }
	   var expires = '';
	   if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
		var date;
		if (typeof options.expires == 'number') {
		 date = new Date();
		 date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
		} else {
		 date = options.expires;
		}
		expires = '; expires=' + date.toUTCString();
	   }
	   var path = options.path ? '; path=' + (options.path) : '';
	   var domain = options.domain ? '; domain=' + (options.domain) : '';
	   var secure = options.secure ? '; secure' : '';
	   document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
	} else {
	   var cookieValue = null;
	   if (document.cookie && document.cookie != '') {
		var cookies = document.cookie.split(';');
		for (var i = 0; i < cookies.length; i++) {
		 var cookie = jQuery.trim(cookies[i]);
		 if (cookie.substring(0, name.length + 1) == (name + '=')) {
		  cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
		  break;
		 }
		}
	   }
	   return cookieValue;
	}
};

//各浏览器高效识别
//以下进行测试
//if (Sys.ie) document.write('IE: ' + Sys.ie);
//if (Sys.firefox) document.write('Firefox: ' + Sys.firefox);
//if (Sys.chrome) document.write('Chrome: ' + Sys.chrome);
//if (Sys.opera) document.write('Opera: ' + Sys.opera);
//if (Sys.safari) document.write('Safari: ' + Sys.safari);
function Sysnavigator(){

	var Sys = {};
	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
	(s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
	(s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	(s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
	(s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;

   
}

function countDown(sys_second,day_elem,hour_elem,minute_elem,second_elem){	
		
	var timer = setInterval(function(){
		if (sys_second > 1) {
			sys_second -= 1;
			var day = Math.floor((sys_second / 3600) / 24);
			var hour = Math.floor((sys_second / 3600) % 24);
			var minute = Math.floor((sys_second / 60) % 60);
			var second = Math.floor(sys_second % 60);
			day_elem && $(day_elem).text(day);//计算天
			$(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
			$(minute_elem).text(minute<10?"0"+minute:minute);//计算分
			$(second_elem).text(second<10?"0"+second:second);// 计算秒
		} else { 
			clearInterval(timer);
			location.href="/fund/checkchargeitem/";
		}
	}, 1000);
}		
//操作后提示	 
function fnshowdiv(obj){
	var Idivdok = document.getElementById(obj);	
	Idivdok.style.display="block";						
	Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
	Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
} 
//外部动态加载js
//loadscript("xxx.js");
 function loadscript(url) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = url;
    document.body.appendChild(script);
}

//外部动态加载css
//loadStyles("styles.css");
function loadStyles(url) {
    var link = document.createElement("link");
    link.rel = "stylesheet";
    link.type = "text/css";
    link.href = url;
    var head = document.getElementsByTagName("head")[0];
    head.appendChild(link);
}

//表格样式
//参数：1：按id定位（如：J-table-data2>tbody），2：合并列数（如：17），3:字体色（如1加载提示：“#009B7D”,2为异常处理色:"red"），4：相应描述（如：没有相应数据）
//例子：TableStyle("J-table-data>tbody",19,2,"没有相应数据");
function TableStyle(obj,count,colors,description){
	var contentHeight = $(".ui-form").height();
    var newHeight = $("html").height() - $(".menu").height() - $(".crumbs").height() -$(".ui-tab-title").height()-20; 
	if($('.main').length>0){
		$('.main').css('overflow-x', 'visible ');			
	}
	if(colors ==1){
		colors="#009B7D";
		$("#"+obj).html("<tr><td colspan='"+count+"' style='height:120px;text-align:center;color:"+colors+"; font-size:14px;font-weight:600;'>"+description+" <img src='"+global_path_url+"/images/admin/ui-admin-loading1.gif'/></td></tr>");
	}else if(colors==2){
		colors="red";
		$("#"+obj).html("<tr><td colspan='"+count+"' style='height:120px;text-align:center;color:"+colors+"; font-size:14px;font-weight:600;'>"+description+"</td></tr>");
	}
	
}

//截取字符串长度
function getSubString(old){
			   return (old=="")?"":((old.length > 10)?"<a title='"+old+"'>"+old.substring(0,10)+"</a>":old); 			   
};

function selectMenu(class1,class2)
{
	var menu1='.menu-list .'+class1;
	var menu2='.col-side .nav .'+class2;
	$(menu1).parent().attr("class","current");
	$(menu2).parent().attr("class","current");
};


