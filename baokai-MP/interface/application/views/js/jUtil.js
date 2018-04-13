/*
Base on jQuery-1.4.2.min.js
*/
$.DivMgr = function(selector){
	this.div = null;
	this.focusNode = null;
	this.Init(selector);
};
$.DivMgr.prototype = {
	Init: function(selector){
		this.div = $(selector)[0];
		this.focusNode = this.div;
	},
	AddDiv : function(){
		this.AddNode("div");
		return this;
	},
	AddUl : function(){
		this.AddNode("ul");
		return this;
	},
	AddLi : function(){
		this.AddNode("li");
		return this;
	},
	AddNode : function(nodeName){
		var tmpNode = document.createElement(nodeName);
		this.focusNode.appendChild(tmpNode);
		this.focusNode = tmpNode;
		return this;
	},
	ID : function(val){
		if(val != null)
		{
			$(this.focusNode).attr("id",val);
			return this;
		}else
		{
			return $(this.focusNode).attr("id");
		}
	},
	Attr : function(name,val){
		if(val != null)
		{
			$(this.focusNode).attr(name,val);
			return this;
		}
		else
		{
			return $(this.focusNode).attr(name);
		}
	},
	Style : function(name,val){
		if(val != null)
		{
			$(this.focusNode).css(name,val);
			return this;
		}
		else
		{
			return $(this.focusNode).css(name);
		}
	},
	InnerHtml : function(val){
		if(val != null)
		{
			val = val==""?"&nbsp;":val;
			$(this.focusNode).html(val);
			return this;
		}
		else
		{
			return $(this.focusNode).html();
		}
	},
	FocusParent : function(){
		this.focusNode = this.focusNode.parentNode;
		return this;
	},
	ToParent : function(){
		this.focusNode = this.focusNode.parentNode;
		return this;
	}
};
$.HtmlTableMgr = function(selector,border,spacing,padding){
	this.tbl = null;
	this.tr = null;
	this.td = null;
	this.focusNode = null;
	this.Init(selector,border,spacing,padding);
};
$.HtmlTableMgr.prototype = {
	Init : function(){
		var args = arguments;
		this.tbl = $(args[0])[0];
		this.focusNode = this.tbl;
		if(args[1] != null)
		{
			$(this.tbl).attr("border",args[1]);
		}
		if(args[2] != null)
		{
			$(this.tbl).attr("cellSpacing",args[2]);
		}
		if(args[3] != null)
		{
			$(this.tbl).attr("cellPadding",args[3]);
		}
	},
	GetHT : function(){
		return $(this.tbl);
	},
	SetHT : function(selector){
		this.tbl = $(selector)[0];
		this.focusNode = this.tbl;
		return this;
	},
	AddTr : function(){
		var no = arguments[0]!=null?arguments[0]:-1;
		this.tr = this.tbl.insertRow(no);
		this.focusNode = this.tr;
		return this;
	},
	AddTd : function(){
		var no = arguments[0]!=null?arguments[0]:-1;
		this.td = this.tr.insertCell(no);
		this.focusNode = this.td;
		return this;
	},
	AddTh : function(){
		var no = arguments[0]!=null?arguments[0]:-1;
		var th = document.createElement('th');
		this.tr.appendChild(th);
		this.td = th;
		this.focusNode = th;
		return this;
	},
	SetFocusRow : function(idx){
		this.tr = this.tbl.rows[idx];
		this.focusNode = this.tr;
		return this;
	},
	SetFocusCell : function(rIdx,cIdx){
		this.td = this.tbl.rows[rIdx].cells[cIdx];
		this.focusNode = this.td;
		return this;
	},
	ID : function(val){
		if(val != null)
		{
			$(this.focusNode).attr("id",val);
			return this;
		}else
		{
			return $(this.focusNode).attr("id");
		}
	},
	Attr : function(name,val){
		if(val != null)
		{
			$(this.focusNode).attr(name,val);
			return this;
		}
		else
		{
			return $(this.focusNode).attr(name);
		}
	},
	Style : function(name,val){
		if(val != null)
		{
			$(this.focusNode).css(name,val);
			return this;
		}
		else
		{
			return $(this.focusNode).css(name);
		}
	},
	InnerHtml : function(val){
		if(val != null)
		{
			$(this.focusNode).html(val);
			return this;
		}
		else
		{
			return $(this.focusNode).html();
		}
	},
	Append : function(obj){
		$(this.focusNode).append(obj);
		return this;
	},
	GetRows : function(){
		return $(this.tbl.rows);
	},
	GetRowSize : function(){
		return this.GetRows().size();
	},
	GetCols : function(){
		//args[0]	row index
		//args[1]	col index	if this is null,return all cols
		var args = arguments;
		if(args[1] != null)
		{
			return $(this.tbl.rows[args[0]].cells[args[1]]);
		}
		else
		{
			return $(this.tbl.rows[args[0]].cells);
		}
	},
	GetColSize : function(idx){
		return this.GetCols(idx).size();
	},
	DelRow : function(){
		var no = arguments[0]!=null?arguments[0]:-1;
		this.tbl.deleteRow(no);
	},
	Clear : function(){
		//args[0]		A few rows are remained.If this is null,the table will be empty.	default:0
		var args = arguments;
		var tmpEnd = args[0]!=null?args[0]:0;
		var len = this.tbl.rows.length;
		for(i = len;i > tmpEnd;i--){
			this.tbl.deleteRow(i-1);
		}
	}
};
$.IsMatch = function(val,reg){
	if(!reg.test(val)){
		return false;
	}
	return true;
};
$.IsEmail = function(val){
	var reg = /\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
        return $.IsMatch(val,reg);
};
$.GetValidId = function(id){
	while($("#" + id)[0] != null)
	{
		var ary = id.split("_");
		if(ary.length == 1)
		{
		alert(1);
			id = id + "_0";
		}
		else
		{
			var no = ary[1];
			no++;
			id = ary[0] + "_" + no;
		}
	}
	return id;
};
//===============================================================
Number.prototype.toDigit = function(how){
	return Math.round(this * Math.pow(10,how)) / Math.pow(10,how);
};
String.prototype.toFill = function(){
	//args[0]	the max length of string 	default: 0
	//args[1]	filled with char		default: "0"
	var args = arguments;
	var tmpLen = args[0]!=null?args[0]:0;
	var tmpChar = args[1]!=null?args[1]:"0";
	
	var tmpValue = this;
	tmpValue += "";
	if(tmpValue.length > tmpLen){
		tmpValue = tmpValue.substring(0,tmpLen);
	}else{
		while(tmpValue.length < tmpLen){
			tmpValue = tmpChar + "" + tmpValue;
		}
	}
	return tmpValue;
};
Number.prototype.toFill = String.prototype.toFill;
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g,"");
};
String.prototype.toInt = function(){
	//args[0]	default value	default:0
	//args[1]	radix		default:10
	var args = arguments;
	var defaultVal = args[0]!=null?args[0]:0;
	var radix = args[1]!=null?args[1]:10;
	
	var tmpStr = this + "";
	if(tmpStr.indexOf("0x") > -1){
		radix = 16;
	}
	var tmpVal = parseInt(this,radix);

	if(isNaN(tmpVal)){
		return defaultVal;
	}else{
		return tmpVal;
	}
};
Number.prototype.toInt = String.prototype.toInt;
String.prototype.toPow = function(){
	//args[0]	pow		default:2
	var args = arguments;
	var pow = args[0]!=null?args[0]:2;
	
	return Math.pow(pow,this.toInt());
};
Number.prototype.toPow = String.prototype.toPow;
String.prototype.replaceAll = function(str1,str2){
	return this.replace(new RegExp(str1,"gm"),str2);
};
String.prototype.nl2br = function(){
	//return this.replace(/([^>])\n/g, '$1<br/>\n');	//have some problems
	return this.replace(/\n/g,"<br/>");
};
String.prototype.toEncode = function(){
	return encodeURIComponent(this);
};
String.prototype.toDecode = function(){
	return decodeURI(this).replaceAll("%26","&");
};
String.prototype.toShort = function(max,end){
	var args = arguments;
	var max = args[0];
	var end = args[1]!=null?args[1]:"...";

	var len = this.length;
	var rs = 0;
	for(var i = 0;i < len;i++){
		var ch = this.charCodeAt(i);
		if((ch >= 48) && (ch <= 57)){			//0-9
	  		rs++;
		}else if((ch >= 65) && (ch <= 90)){		//A-Z
			rs += 2;
		}else if((ch >= 97) && (ch <= 122)){		//a-z
			rs++;
		}else if(!(ch >= 32 && ch <= 126)){		//
			rs += 2;	
		}else{						//symbol
			rs++;
		}
		if(rs > max){
			max = i;
			return this.substring(0,max) + end;
		}
	}
	return this;
};

$.cookie = function(c_name,value,expire_sec){
	if(value != null){
		var exdate = new Date();
		exdate.setTime(exdate.getTime() + (expire_sec!=null?expire_sec:1000*60*60*24));
		document.cookie = c_name+ "=" + escape(value) + ";expires=" + exdate.toGMTString();
	}else{
		if(document.cookie.length > 0){
			var c_start = document.cookie.indexOf(c_name + "=");
			if(c_start != -1){
				c_start = c_start + c_name.length + 1;
				c_end = document.cookie.indexOf(";",c_start);
				if(c_end == -1){
					c_end = document.cookie.length;
				}
				return unescape(document.cookie.substring(c_start,c_end));
			} 
		}
		return "";
	}
};