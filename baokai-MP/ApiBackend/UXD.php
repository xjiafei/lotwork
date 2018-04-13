<?php




?>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="jquery-ui.css">
	<link href="BetMgr.css" rel="stylesheet" type="text/css" />
	<script src="jquery-1.11.0.min.js"></script>
	<script src="jquery-ui.js"></script>
	<script src="./jUtil.js"></script>
	<script src="Chart.js"></script>
</head>
<style>
.tbltd
{
	width: 20%;
	border: solid 1px #A9A9A9;
	color: #ff0000;
}
#div_api div
{
	padding: 0 4px;
	clear: both;
}
#div_api div ul
{
	list-style-type: none;
	margin: 0 0 0 0;
}
#div_api div ul li button
{
	float:left; 
}
#div_api div ul li button
{
	margin: 2px 2px 2px 2px;
}

body.normal
{
	
}
body.black
{
	background-color: #000000;
	color: #00FF00;
}
body.black input
{
	background-color: transparent;
	color: #00ff00;
}
body.black textarea
{
	background-color: transparent;
	color: #00ff00;
}
body.pink
{
	background-color: #FEF6FA;
	color: #BB2642;
}
body.pink input
{
	background-color: transparent;
	color: #BB2642;
}
body.pink textarea
{
	background-color: transparent;
	color: #BB2642;
}
body.normal .btn
{
	border: solid 1px #000000;
}
body.black .btn
{
	border: solid 1px #000000;
}
body.pink .btn
{
	border: solid 1px #FE8BCC;
	background-color: #FFE8E5;
	color: #FF006B;
}
body.normal .tab_on
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #CCCCCC;
	border-right: solid 1px #CCCCCC;
	border-left: solid 1px #CCCCCC;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	background-color: #CCCCCC;
	color: #000000;
}
body.normal .tab_off
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #CCCCCC;
	border-right: solid 1px #CCCCCC;
	border-left: solid 1px #CCCCCC;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	cursor: pointer;
}
body.black .tab_on
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFFFFF;
	border-right: solid 1px #FFFFFF;
	border-left: solid 1px #FFFFFF;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	background-color: #CCCCCC;
	color: #000000;
}
body.black .tab_off
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFFFFF;
	border-right: solid 1px #FFFFFF;
	border-left: solid 1px #FFFFFF;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	cursor: pointer;
}
body.pink .tab_on
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFC0CB;
	border-right: solid 1px #FFC0CB;
	border-left: solid 1px #FFC0CB;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	background-color: #FFC0CB;
	color: #FF006B;
}
body.pink .tab_off
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFC0CB;
	border-right: solid 1px #FFC0CB;
	border-left: solid 1px #FFC0CB;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	cursor: pointer;
}
.scroll
{
	padding: 0px 10px 0px 10px;
	height: 720px;
	overflow-y: auto;
}
#div_scroll1::-webkit-scrollbar-thumb{
	background-color: #CCCCCC;
	border: 1px solid #333333;
	border-radius: 5px;
}
#div_scroll1::-webkit-scrollbar-thumb:hover{
	background-color: #CCCCCC;
	border: 1px solid #333333;
}
#div_scroll1::-webkit-scrollbar-thumb:active{
	background-color: #CCCCCC;
	border: 1px solid #333333;
} 
#div_scroll1::-webkit-scrollbar{
	width: 8px;
	background-color: transparent;
} 
#div_scroll1::-webkit-scrollbar-track{
	border: 0px gray solid;
	border-radius: 10px;
	-webkit-box-shadow: 0 0 6px gray inset;
}

#div_scroll2::-webkit-scrollbar-thumb{
	background-color: #CCCCCC;
	border: 1px solid #333333;
	border-radius: 5px;
}
#div_scroll2::-webkit-scrollbar-thumb:hover{
	background-color: #CCCCCC;
	border: 1px solid #333333;
}
#div_scroll2::-webkit-scrollbar-thumb:active{
	background-color: #CCCCCC;
	border: 1px solid #333333;
} 
#div_scroll2::-webkit-scrollbar{
	width: 8px;
	background-color: transparent;
} 
#div_scroll2::-webkit-scrollbar-track{
	border: 0px gray solid;
	border-radius: 10px;
	-webkit-box-shadow: 0 0 6px gray inset;
} 
</style>

<script>
var BetMgr = null;
//var BASE_URL = "http://10.3.7.168:8080/mobileapi/index.php";
var mIsProduct = 0;
var BASE_URL = "http://ios1.phl5b.org";

var PLATFORM_3 = 1;
var PLATFORM_ADMIN = 2;
var PLATFORM_4 = 3;
var PLATFORM_BM = 4;
var PLATFORM_BM_AGENT = 5;
var gLatestName = "";

var COLORS = ["#800000", "#C71585", "#4B0082", "#8B4513", "#B8860B", "#2F4F4F", "#191970", "#FF0000", "#FF00FF", "#9370DB", "#F4A460", "#BDB76B", "#00FF00", "#B0C4DE"];

var API_URL = {
		"ADMIN":{
			"GET_DAILY_REPORT": {"name": "昨日销量", "url" : "#DOMAIN#/admin/getDailyReport/#APPID#", "data_admin": '{"day":"#DAY#"}'},
			"GET_MONTHLY_DL_REPORT": {"name": "下载量", "url" : "#DOMAIN#/admin/getMonthlyDownloadReport/#APPID#", "data_admin": '{"start":"#START#", "end":"#END#"}'},
			"GET_MONTHLY_IOS_BUY_REPORT": {"name": "ios销售量", "url" : "#DOMAIN#/admin/getMonthlyBuyReport/#APPID#", "data_admin": '{"app_id":"9", "start":"#START#", "end":"#END#"}'},
			"GET_MONTHLY_ANDROID_BUY_REPORT": {"name": "android销售量", "url" : "#DOMAIN#/admin/getMonthlyBuyReport/#APPID#", "data_admin": '{"app_id":"10", "start":"#START#", "end":"#END#"}'},
			"GET_MONTHLY_MOBILE_BUY_REPORT": {"name": "移动端销售量", "url" : "#DOMAIN#/admin/getMonthlyMobileReport/#APPID#", "data_admin": '{"start":"#START#", "end":"#END#"}'},
			"GET_MONTHLY_RECHARGE_REPORT": {"name": "申请充值金额", "url" : "#DOMAIN#/admin/getMonthlyRechargeReport/#APPID#", "data_admin": '{"start":"#START#", "end":"#END#"}'},
			"GET_MONTHLY_COUNT_REPORT": {"name": "投注人数", "url" : "#DOMAIN#/admin/getMonthlyCountReport/#APPID#", "data_admin": '{"start":"#START#", "end":"#END#"}'}
			}
		};
		
$(document).ready(function(){
	var d = new Date();
	var today = new Date();
	var yesterday = new Date(today);
	yesterday.setDate(today.getDate() - 1);
	var txtDay = yesterday.getFullYear() + "-" + (yesterday.getMonth() + 1) + "-" + yesterday.getDate();
	$("#txtDay").val(txtDay);
	
	$("#selAppId").change(function(){
		EnableFuncBtns();
		});
	$("#selEnv").change(function(){
		SelectEnv();
		});
	SelectEnv();
	
	var d = new $.DivMgr('#div_api');
	
	var iCount;
	for(var key in API_URL)
	{
		d.AddDiv().InnerHtml("").ToParent();
		
		iCount = 0;
		d.AddDiv().AddUl();
		for(var key2 in API_URL[key])
		{
			var btn = $('<button/>',
				    {
				    	id: key + "__" + key2 + "__API",
				    	class: "btn",
				        text: API_URL[key][key2]["name"],
				        click: function(){
				        		ClearAll();
				        		var obj = this;
				        		var key = obj.id.split("__")[0];
				        		var func = obj.id.split("__")[1];
				        		
							gLatestName = $(this).html();
				        		
				        		var url = HanldePostUrl(API_URL[key][func]["url"]);
				        		
				        		$("#txtUrl").val(url);
							$("#txtRequest").val(function(){
									if(GetPlatForm() == PLATFORM_ADMIN)
									{
							    			return HanldePostData(API_URL[key][func]["data_admin"]);
							    		}
							    		else
							    		{
							    			return HanldePostData(API_URL[key][func]["data"]);
							    		}
								});
							if($("#txtRequest").val())
							{
								$.post("api.php"
									, { "action": "var_dump", "data": $("#txtRequest").val() }
									,function(result){
										$("#txtVarDump").val(result);
									});
								
								$.post("api.php"
									, { "action": "Aes128Encode", "is_product": mIsProduct,"data": $("#txtRequest").val(), "app_id": $("#selAppId").val()}
									,function(result){
										//result = "d7215fd2d952ce8a3305ac4ca38fe487ec68aec0b26afccae30cb20b5c015284fd18a1ff22decea80b9a5ec5f46d0f0d136e35654146e807972cba40a55f20d6df45a3f33b349d0b26cb5150fba28f27e19d0d57811bbaaab9af6352fa234111";
										$("#txtAES128EncodeRequest").val(result);
										SendRequest(url, result);
									});
							}
				        	 }
				    });
			//tbl.AddTd().InnerHtml(btn);
			d.AddLi().InnerHtml(btn).ToParent();
			iCount++;
		}
		d.ToParent().ToParent();
	}
	
	$("#btnResend").click(function(){		
		$("#txtAES128EncodeRequest").val("");
		$("#txtVarDump").val("");
		$("#txtResponse").val("");
		$("#txtAES128DecodeResponse").val("");
		
		$.post("api.php"
			, { "action": "var_dump", "data": $("#txtRequest").val() }
			,function(result){
				$("#txtVarDump").val(result);
			});
			
		$.post("api.php"
			, { "action": "Aes128Encode", "is_product": mIsProduct, "data": $("#txtRequest").val(), "app_id": $("#selAppId").val()}
			,function(result){
				$("#txtAES128EncodeRequest").val(result);
				SendRequest($("#txtUrl").val(), result);
			});
	});
	
	EnableFuncBtns();
	
	$("#btnSave").click(function(){
				saveFile();
			});
			
	var today = new Date();
	var yesterday = new Date();
	var oneMonthAgo = new Date();
	yesterday.setDate(today.getDate() - 1);
	oneMonthAgo.setDate(today.getDate() - 31);
	
	$( "#datepicker_end").datepicker({
			dateFormat: "yy-mm-dd",
			defaultDate: 0,
			maxDate: "-1d",
			minDate: "-3m"
			});
	$( "#datepicker_end").val($.datepicker.formatDate( "yy-mm-dd", yesterday ));
			
	$( "#datepicker_start").datepicker({
			dateFormat: "yy-mm-dd",
			defaultDate: -31,
			maxDate: "-1d",
			minDate: "-3m"
			});
	$( "#datepicker_start").val($.datepicker.formatDate( "yy-mm-dd", oneMonthAgo ));
		
	$("div[id^=div_req_tab]").click(function(){
				var tab_id = $(this).attr("id").split("_")[3];
				//alert(tab_id);
				$("div[id^=div_req_tab").each(function(){
						$(this).attr("class", "tab_off");
					});
				$("#div_req_tab_" + tab_id).attr("class", "tab_on");
				
				$("#txtRequest").css("display", "none");
				$("#txtVarDump").css("display", "none");
				$("#txtAES128EncodeRequest").css("display", "none");
				if(tab_id == 1)
				{
					$("#txtRequest").css("display", "block");
				}else if(tab_id == 2){
					$("#txtVarDump").css("display", "block");
				}else if(tab_id == 3){
					$("#txtAES128EncodeRequest").css("display", "block");
				}
			});
	$("div[id^=div_resp_tab]").click(function(){
				var tab_id = $(this).attr("id").split("_")[3];
				//alert(tab_id);
				$("div[id^=div_resp_tab").each(function(){
						$(this).attr("class", "tab_off");
					});
				$("#div_resp_tab_" + tab_id).attr("class", "tab_on");
				
				$("#txtAES128DecodeResponse").css("display", "none");
				$("#txtResponse").css("display", "none");
				if(tab_id == 1)
				{
					$("#txtAES128DecodeResponse").css("display", "block");
				}else if(tab_id == 2){
					$("#txtResponse").css("display", "block");
				}
			});
});

function SwitchDebug()
{
	var rtn = $("#txtRequest").val();
	if(rtn != "")
	{
		var json = $.parseJSON(rtn);
		if($("#chk_debug").is(':checked'))
		{
			json.debug = 1;
		}
		else
		{
			delete json.debug;
		}
		$("#txtRequest").val(JSON.stringify(json));
	}
}

function SelectEnv()
{
	var i = $("#selEnv").val();
	if(i == 1)
	{
		mIsProduct = 0;
		$("#txtDomain").val("http://10.3.7.168:8080/mobileapi/index.php");
		$.cookie("DOMAIN", $("#txtDomain").val());
		$.cookie("IS_PRODUCT", mIsProduct);
	}
	else if(i == 2)
	{
		mIsProduct = 0;
		$("#txtDomain").val("http://ios1.phl5b.org");
		$.cookie("DOMAIN", $("#txtDomain").val());
		$.cookie("IS_PRODUCT", mIsProduct);
	}
	else if(i == 3)
	{
		mIsProduct = 1;
		
		var appId = $("#selAppId").val();
		if(appId == "13" || appId == "14")
		{
			$("#txtDomain").val("http://www.bomao21.com:6060");
		}else{
			$("#txtDomain").val("http://mobile.ios188.com:6060");
		}
		$.cookie("DOMAIN", $("#txtDomain").val());
		$.cookie("IS_PRODUCT", mIsProduct);
	}
	$.cookie("ENV", i);
}

function EnableFuncBtns()
{
	var platform = GetPlatForm();
	
	$("button[id$=__API]").each(function(){
			var btnId = $(this).attr("id");
			var keys = btnId.split("__");
		
			if(platform == PLATFORM_3 && API_URL[keys[0]][keys[1]]["data"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_ADMIN && API_URL[keys[0]][keys[1]]["data_admin"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_4 && API_URL[keys[0]][keys[1]]["data_4"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_BM && API_URL[keys[0]][keys[1]]["data_bm"])
			//else if(platform == PLATFORM_BM && API_URL[keys[0]][keys[1]]["data"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_BM_AGENT && API_URL[keys[0]][keys[1]]["data_bm_agent"])
			{
				$(this).attr("disabled", false).show();
			}
			else
			{
				$(this).attr("disabled", true).hide();
			}
		});
}
	
function SendRequest(url, data)
{
	$.post("api.php"
		, { "action": "req", "url": url, "data": data}
		,function(result){
			ClearAll();
			
			var rtn = jQuery.parseJSON(result);
			//rtn["data"] = "673388f474d43b1e763c156e828b6cc9bfe2e19ac44da3bad2249f3d7948130ec2def2bc4478cb6d58af5e9a4e3c8ce6bf15555a88f3af2e19b0823cf8fcc1bc4e77225def60bf2192275dcfbb6f0f086c5dfed58c5ace66a48bbe5a6c2c2db5a113c9d8c143fd065807964bbb5e6c70";
			$("#divRequestTime").text(rtn["start"]);
			$("#divResponseTime").text(rtn["end"]);
			$("#txtResponse").val(rtn["data"]);
			$.post("api.php"
				, { "action": "Aes128Decode", "is_product": mIsProduct, "data": rtn["data"], "app_id": $("#selAppId").val()}
				,function(result){
					var rs = UnicodeDecode(result);
					$("#txtAES128DecodeResponse").val(rs);

					var json = JSON.parse(rs.replaceAll("\0",""));
					if(url.indexOf("getDailyReport") > -1){
						CreateDailyReport(json);
					}else if(url.indexOf("getMonthlyDownloadReport") > -1){
						CreateMonthlyDownloadReport(json);
					}else if(url.indexOf("getMonthlyBuyReport") > -1){
						CreateMonthlyBuyReport(json);
					}else if(url.indexOf("getMonthlyMobileReport") > -1){	
						CreateMonthlyMobileReport(json);
					}else if(url.indexOf("getMonthlyRechargeReport") > -1){
						CreateMonthlyRechargeReport(json)
					}else if(url.indexOf("getMonthlyCountReport") > -1){
						CreateMonthlyCountReport(json)
					}
					
					
				});
		});
}

function ClearAll()
{
	$("#txtRequest").val("");
	$("#txtAES128EncodeRequest").val("");
	$("#txtResponse").val("");
	$("#txtAES128DecodeResponse").val("");
	$("#divRequestTime").text("");
	$("#divResponseTime").text("");
}
function UnicodeEncode(data) 
{ 
	return escape(data).replace(/%/g,"\\").toLowerCase(); 
}
function UnicodeDecode(data)
{
	return unescape(data.replace(/\\/g, "%")); 	
}

function GetToken()
{
	return $("#txtToken").val();
}

function HanldePostUrl(url)
{
	return url.replace("#DOMAIN#", $("#txtDomain").val()).replace("#APPID#", $("#selAppId").val());
}

function HanldePostData(data)
{
	var rtn;
	if(data === undefined)
	{
		return "";	
	}
	
	rtn = data.replace("#TOKEN#", $("#txtToken").val())
		.replace("#APP_ID#", $("#selAppId").val())
		.replace("#DAY#", $("#txtDay").val())
		.replace("#START#", $("#datepicker_start").val())
		.replace("#END#", $("#datepicker_end").val() + " 23:59:59");
	
	if($("#chk_debug").is(':checked'))
	{
		var json = $.parseJSON(rtn);
		json.debug = 1;
		//alert(json.username);
		rtn = JSON.stringify(json);
		//alert(rtn);
	}
	
	return rtn;
}

function GetPlatForm()
{
	var ary_platform_3 = ["1", "2", "3", "4", "5", "6"];
	var ary_platform_admin = ["7"];
	var ary_platform_4 = ["9", "10"];
	var ary_platform_bm = ["13", "14"];
	var ary_platform_bm_agent = ["8", "11", "12"];
	if($.inArray($("#selAppId").val(), ary_platform_3) != -1)
	{
		return PLATFORM_3;
	}
	else if($.inArray($("#selAppId").val(), ary_platform_admin) != -1)
	{
		return PLATFORM_ADMIN;
	}
	else if($.inArray($("#selAppId").val(), ary_platform_4) != -1)
	{
		return PLATFORM_4;	
	}
	else if($.inArray($("#selAppId").val(), ary_platform_bm) != -1)
	{
		return PLATFORM_BM;	
	}
	else if($.inArray($("#selAppId").val(), ary_platform_bm_agent) != -1)
	{
		return PLATFORM_BM_AGENT;	
	}
}

function CreateDailyReport(json)
{
	$("#tbl_report").show();
	$("#btnSave").show();
	$("#tbl_chart").show();
	var t = new $.HtmlTableMgr("#tbl_report", 1, 0, 10);
	t.Clear();
	t.AddTr()
		.AddTd().InnerHtml("彩种")
		.AddTd().InnerHtml("总销量");
	
	var total = 0;
	
	for(var i = 0;i < json.length;i++)
	{
		json[i]["color"] = COLORS[i];
		t.AddTr()
			.AddTd().InnerHtml(json[i]["lot_name"])
			.AddTd().InnerHtml(Math.floor(json[i]["total"]));
		total += json[i]["total"] * 1;
	}
	t.AddTr()
		.AddTd().InnerHtml("合计")
		.AddTd().InnerHtml(Math.floor(total));
	CreateDailyReportChart(json);
}

function CreateDailyReportChart(json)
{
	$("#div_canvas").html('<canvas id="canvas_report" width="1000" height="400"></canvas>');
	
	var t = new $.HtmlTableMgr("#tbl_chart", 0, 0, 10);
	t.Clear();
	t.AddTr().AddTd().Attr("width", "20").InnerHtml("");
	
	
	var ctx = $("#canvas_report").get(0).getContext("2d");
	var data = new Array();
	for(var i = 0;i < json.length;i++)
	{
		var ary = new Array();
		ary["value"] = json[i]["total"] * 1;
		ary["color"] = json[i]["color"];
		
		t.AddTd().Attr("width", "20").InnerHtml(json[i]["lot_name"])
			.AddTd().Attr("width", "20").Style("background-color", COLORS[i]).InnerHtml("");
		
		data.push(ary);
	}
	
	new Chart(ctx).Pie(data);
}

function CreateMonthlyDownloadReport(json)
{
	$("#tbl_report").show();
	$("#btnSave").show();
	$("#tbl_chart").show();
	
	var t = new $.HtmlTableMgr("#tbl_chart", 0, 0, 10);
	t.Clear();
	t.AddTr().AddTd().Attr("width", "20").InnerHtml("")
		.AddTd().Attr("width", "20").InnerHtml("ios")
		.AddTd().Attr("width", "20").Style("background-color", "#DCDCDC").InnerHtml("")
		.AddTd().Attr("width", "20").InnerHtml("android")
		.AddTd().Attr("width", "20").Style("background-color", "#97BBCD").InnerHtml("");
	
	var t = new $.HtmlTableMgr("#tbl_report", 1, 0, 10);
	t.Clear();
	t.AddTr()
		.AddTd().InnerHtml("手机")
		.AddTd().InnerHtml("下载量")
		.AddTd().InnerHtml("日期");
	
	for(var i = 0;i < json.length;i++)
	{
		t.AddTr()
			.AddTd().InnerHtml(json[i]["app_id"]==9?"ios":"android")
			.AddTd().InnerHtml(json[i]["num"])
			.AddTd().InnerHtml(json[i]["time"]);
	}
	CreateMonthlyDownloadReportChart(json);
}

function CreateMonthlyDownloadReportChart(json)
{
	$("#div_canvas").html('<canvas id="canvas_report" width="1200" height="400"></canvas>');
	
	var ctx = $("#canvas_report").get(0).getContext("2d");
	//This will get the first returned node in the jQuery collection.
	
	var aryLabel = new Array();
	var aryIos = new Array();
	var aryAndroid = new Array();
	for(var i = 0;i < json.length;i++)
	{
		if($.inArray(json[i]["time"], aryLabel) == -1)
		{
			aryLabel.push(json[i]["time"]);
		}
		if(json[i]["app_id"] == 9)
		{
			aryIos.push(json[i]["num"] * 1);
		}
		else if(json[i]["app_id"] == 10)
		{
			aryAndroid.push(json[i]["num"] * 1);
		}
	}
	
	var data = {
		labels : aryLabel,
		datasets : [
			{
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				data : aryIos
			},
			{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : aryAndroid
			}
		]
	}
	new Chart(ctx).Line(data);
}

function CreateMonthlyBuyReport(json)
{
	$("#tbl_report").show();
	$("#btnSave").show();
	$("#tbl_chart").hide();
	
	var t = new $.HtmlTableMgr("#tbl_report", 1, 0, 10);
	t.Clear();
	t.AddTr()
		.AddTd().InnerHtml("彩种")
		.AddTd().InnerHtml("金额")
		.AddTd().InnerHtml("日期");
	var total = 0;
	for(var i = 0;i < json.length;i++)
	{
		t.AddTr()
			.AddTd().InnerHtml(json[i]["lot_name"])
			.AddTd().InnerHtml(Math.floor(json[i]["money"]))
			.AddTd().InnerHtml(json[i]["time"]);
		total += json[i]["money"] * 1;
	}
	
	t.AddTr()
		.AddTd().InnerHtml("合计")
		.AddTd().Attr("colspan", "2").Attr("align", "center").InnerHtml(Math.floor(total));
	CreateMonthlyBuyReportChart(json);
}

function CreateMonthlyBuyReportChart(json)
{
	var htm = "";
	var lot_id = "";
	
	var aryLottery = new Array();
	
	for(var i = 0;i < json.length;i++)
	{
		if(lot_id != json[i]["lot_id"])
		{
			lot_id = json[i]["lot_id"];
			htm += json[i]["lot_name"] + "<br/>";
			htm += '<canvas id="canvas_' + lot_id + '" width="1200" height="400"></canvas><br/>';
			aryLottery[lot_id] = new Array();
			aryLottery[lot_id]["money"] = new Array();
			aryLottery[lot_id]["time"] = new Array();
		}
		
		aryLottery[lot_id]["money"].push(json[i]["money"] * 1);
		aryLottery[lot_id]["time"].push(json[i]["time"]);
	}
	//alert(aryLottery[lot_id]["money"]);
	$("#div_canvas").html(htm);
	for(var k in aryLottery) {
		CreateSimpleLineChart("canvas_" + k, aryLottery[k]["time"], aryLottery[k]["money"]);
	}
}

function CreateMonthlyMobileReport(json)
{
	$("#tbl_report").show();
	$("#btnSave").show();
	$("#tbl_chart").show();
	
	var t = new $.HtmlTableMgr("#tbl_chart", 0, 0, 10);
	t.Clear();
	t.AddTr().AddTd().Attr("width", "20").InnerHtml("")
		.AddTd().Attr("width", "60").InnerHtml("总销量")
		.AddTd().Attr("width", "20").Style("background-color", "#CCCCCC").InnerHtml("")
		.AddTd().Attr("width", "20").InnerHtml("ios")
		.AddTd().Attr("width", "20").Style("background-color", "#00FF00").InnerHtml("")
		.AddTd().Attr("width", "20").InnerHtml("android")
		.AddTd().Attr("width", "20").Style("background-color", "#0000FF").InnerHtml("");
		
	var t = new $.HtmlTableMgr("#tbl_report", 1, 0, 10);
	t.Clear();
	t.AddTr()
		.AddTd().InnerHtml("手机")
		.AddTd().InnerHtml("金额")
		.AddTd().InnerHtml("日期");
	var total = 0;
	for(var i = 0;i < json.length;i++)
	{
		t.AddTr()
			.AddTd().InnerHtml(json[i]["app_id"]==9?"ios":"android")
			.AddTd().InnerHtml(Math.floor(json[i]["money"]))
			.AddTd().InnerHtml(json[i]["time"]);
		total += json[i]["money"] * 1;
	}
	
	t.AddTr()
		.AddTd().InnerHtml("合计")
		.AddTd().Attr("colspan", "2").Attr("align", "center").InnerHtml(Math.floor(total));
	CreateMonthlyMobileReportChart(json);
}

function CreateMonthlyMobileReportChart(json)
{
	$("#div_canvas").html('<canvas id="canvas_report" width="1200" height="400"></canvas>');
	
	var ctx = $("#canvas_report").get(0).getContext("2d");
	//This will get the first returned node in the jQuery collection.
	
	var aryLabel = new Array();
	var aryTotal = new Array();
	var aryIos = new Array();
	var aryAndroid = new Array();
	for(var i = 0;i < json.length;i++)
	{
		if($.inArray(json[i]["time"], aryLabel) == -1)
		{
			aryLabel.push(json[i]["time"]);
		}
		if(json[i]["app_id"] == 9)
		{
			aryIos.push(json[i]["money"] * 1);
		}
		else if(json[i]["app_id"] == 10)
		{
			aryAndroid.push(json[i]["money"] * 1);
		}
	}
	for(var i = 0;i < aryIos.length;i++)
	{
		aryTotal.push(aryIos[i] + aryAndroid[i]);
	}
	
	var data = {
		labels : aryLabel,
		datasets : [
			{
				fillColor : "rgba(200,200,200,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : aryTotal
			},
			{
				fillColor : "rgba(0,255,0,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				data : aryIos
			},
			{
				fillColor : "rgba(0,0,255,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : aryAndroid
			}
		]
	}
	new Chart(ctx).Line(data);
}

function CreateMonthlyCountReport(json)
{
	$("#tbl_report").show();
	$("#btnSave").show();
	$("#tbl_chart").show();
	
	var t = new $.HtmlTableMgr("#tbl_chart", 0, 0, 10);
	t.Clear();
	t.AddTr().AddTd().Attr("width", "20").InnerHtml("")
		.AddTd().Attr("width", "60").InnerHtml("总人数")
		.AddTd().Attr("width", "20").Style("background-color", "#CCCCCC").InnerHtml("")
		.AddTd().Attr("width", "20").InnerHtml("ios")
		.AddTd().Attr("width", "20").Style("background-color", "#00FF00").InnerHtml("")
		.AddTd().Attr("width", "20").InnerHtml("android")
		.AddTd().Attr("width", "20").Style("background-color", "#0000FF").InnerHtml("");
		
	var t = new $.HtmlTableMgr("#tbl_report", 1, 0, 10);
	t.Clear();
	t.AddTr()
		.AddTd().InnerHtml("手机")
		.AddTd().InnerHtml("人数")
		.AddTd().InnerHtml("日期");
	var total = 0;
	for(var i = 0;i < json.length;i++)
	{
		t.AddTr()
			.AddTd().InnerHtml(json[i]["app_id"]==9?"ios":"android")
			.AddTd().InnerHtml(json[i]["num"])
			.AddTd().InnerHtml(json[i]["time"]);
		total += json[i]["num"] * 1;
	}
	
	t.AddTr()
		.AddTd().InnerHtml("合计")
		.AddTd().Attr("colspan", "2").Attr("align", "center").InnerHtml(total);
	CreateMonthlyCountReportChart(json);
}

function CreateMonthlyCountReportChart(json)
{
	$("#div_canvas").html('<canvas id="canvas_report" width="1200" height="400"></canvas>');
	
	var ctx = $("#canvas_report").get(0).getContext("2d");
	//This will get the first returned node in the jQuery collection.
	
	var aryLabel = new Array();
	var aryTotal = new Array();
	var aryIos = new Array();
	var aryAndroid = new Array();
	for(var i = 0;i < json.length;i++)
	{
		if($.inArray(json[i]["time"], aryLabel) == -1)
		{
			aryLabel.push(json[i]["time"]);
		}
		if(json[i]["app_id"] == 9)
		{
			aryIos.push(json[i]["num"] * 1);
		}
		else if(json[i]["app_id"] == 10)
		{
			aryAndroid.push(json[i]["num"] * 1);
		}
	}
	for(var i = 0;i < aryIos.length;i++)
	{
		aryTotal.push(aryIos[i] + aryAndroid[i]);
	}
	
	var data = {
		labels : aryLabel,
		datasets : [
			{
				fillColor : "rgba(200,200,200,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : aryTotal
			},
			{
				fillColor : "rgba(0,255,0,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				data : aryIos
			},
			{
				fillColor : "rgba(0,0,255,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : aryAndroid
			}
		]
	}
	new Chart(ctx).Line(data);
}

function CreateSimpleLineChart(target_id, aryLabel, aryValue)
{
	var ctx = $("#" + target_id).get(0).getContext("2d");
	var data = {
		labels : aryLabel,
		datasets : [
			{
				fillColor : "rgba(220,220,220,0.5)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				data : aryValue
			}
		]
	}
	new Chart(ctx).Line(data);
}

function CreateMonthlyRechargeReport(json)
{
	$("#tbl_report").show();
	$("#btnSave").show();
	$("#tbl_chart").hide();
	
	var t = new $.HtmlTableMgr("#tbl_report", 1, 0, 10);
	t.Clear();
	t.AddTr()
		.AddTd().InnerHtml("次数")
		.AddTd().InnerHtml("总额")
		.AddTd().InnerHtml("日期");
	
	var aryTime = new Array();
	var aryTotal = new Array();
	
	for(var i = 0;i < json.length;i++)
	{
		aryTime.push(json[i]["time"]);
		aryTotal.push(Math.floor(json[i]["total"] * 1));
		
		t.AddTr()
			.AddTd().InnerHtml(json[i]["num"])
			.AddTd().InnerHtml(Math.floor(json[i]["total"]))
			.AddTd().InnerHtml(json[i]["time"]);
	}
	
	$("#div_canvas").html('<canvas id="canvas_report" width="1200" height="400"></canvas>');
	CreateSimpleLineChart("canvas_report", aryTime, aryTotal);
}

function ClearAll()
{
	$("#tbl_report").hide();
	$("#btnSave").hide();
	$("#tbl_chart").hide();
	$("#div_canvas").html("");
}

function saveFile()
{
	var today = new Date();
	var txtToday = today.getFullYear() + "" + (today.getMonth() + 1).toFill(2) + "" + today.getDate();
	
	var textToWrite = $("#tbl_report").prop('outerHTML');//Your text input;
	var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
	var fileNameToSaveAs = gLatestName + "_" + txtToday + ".xls";//Your filename;
	
	var downloadLink = document.createElement("a");
	downloadLink.download = fileNameToSaveAs;
	downloadLink.innerHTML = "Download File";
	if (window.webkitURL != null)
	{
		// Chrome allows the link to be clicked
		// without actually adding it to the DOM.
		downloadLink.href = window.webkitURL.createObjectURL(textFileAsBlob);
	}
	else
	{
		// Firefox requires the link to be added to the DOM
		// before it can be clicked.
		downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
		//downloadLink.onclick = destroyClickedElement;
		downloadLink.style.display = "none";
		document.body.appendChild(downloadLink);
	}
	downloadLink.click();
}
</script>

<body class="black">
<div style="display: none;">
	<div style="margin: 4px 0 4px 0;">
		<select id="selAppId">
			<option value="7">7.admin(web)</option>
		</select>
		<select id="selEnv">
			<option value ="1">内部环境</option>
			<option value ="2">测试环境</option>
			<option value="3" selected>生产环境</option>
		</select>
		<input id="txtDomain" style="width: 300px;" value="http://10.3.7.168:8080/mobileapi/index.php"/>
		
	</div>
</div>

<div style="margin: 4px 0 4px 0;display: none;">
	查询日期：<input type="text" id="txtDay"/>
	<button id="btnResend" class="btn" style="display: none;">Resend</button>
</div>	

<div>
	<div id="div_api"></div>
</div>
<div style="clear: both;"></div>
<div id="div_picker" style="margin: 5px 5px 5px 5px;">
	Start:<input type="text" id="datepicker_start" style="width: 80px;border: solid 1px #CCCCCC;">&nbsp;&nbsp;End:<input type="text" id="datepicker_end" style="width: 80px;border: solid 1px #CCCCCC;">
</div>
<div style="clear: both;height: 10px;"></div>

<div style="display: none;">
	URL:<input id="txtUrl"  style="width: 90%; margin-bottom: 5px;border: solid 0px #000000;font-weight: bold;"/>
	
	<div>
		<div style="float: left; width: 48%; height: 40%;">
			<div id="div_req_tab_1" class="tab_on">Request</div>
			<div id="div_req_tab_2" class="tab_off">var_dump</div>
			<div id="div_req_tab_3" class="tab_off">AES128EncodeRequest</div>
			<div style="clear: both;"></div>
			<textarea id="txtRequest" style="width: 100%;height: 90%;"></textarea>
			<textarea id="txtVarDump" style="width: 100%;height: 90%;display: none;" readonly="readonly"></textarea>
			<textarea id="txtAES128EncodeRequest" style="width: 100%;height: 90%;display: none;" readonly="readonly"></textarea>
		</div>
		<div style="float: left; width: 2%; height: 40%;"></div>
		<div style="float: left; width: 48%; height: 40%;">
			<div id="div_resp_tab_1" class="tab_on">AES128DecodeResponse</div>
			<div id="div_resp_tab_2" class="tab_off">Response</div>
			<div style="clear: both;"></div>
			<textarea id="txtAES128DecodeResponse" style="width: 100%;height: 90%;" readonly="readonly"></textarea>
			<textarea id="txtResponse" style="width: 100%;height: 90%;display: none;" readonly="readonly"></textarea>
		</div>
	</div>
</div>
<table id="tbl">
	<tr>
		<td valign="top" align="center" style="width: 25%;">
			<button id="btnSave" style="display: none;margin: 5px 5px 5px 5px;">下载档案</button>
			<div id="div_scroll1" class="scroll">
				<table id="tbl_report" style="display: none;"></table>
			</div>
		</td>
		<td valign="top" style="width: 75%;">
			<table id="tbl_chart" style="display: none;">
				<tr>
					<td width="20">&nbsp;</td>
					<td width="40">ios</td>
					<td width="40" style="background-color:#DCDCDC;"></td>
				</tr>
				<tr>
					<td width="20">&nbsp;</td>
					<td width="40">android</td>
					<td width="40" style="background-color:#97BBCD;"></td>
				</tr>
			</table>
			
			<div id="div_scroll2" class="scroll">
				<div id="div_canvas"></div>
			</div>
		</td>
	</tr>
</table>






</body>