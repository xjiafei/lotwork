<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>刮刮卡管理后台</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<?php echo $base_url;?>js/jUtil.js"></script>
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery.twbsPagination.min.js"></script>

<link rel="stylesheet" href="<?php echo $base_url;?>css/twbsPagination.css">
<style type="text/css">
.tblMenu {
	border-top: solid 1px #000000;
	border-left: solid 1px #000000;
	border-bottom: solid 1px #000000;
}
.tblMenu tr {
	
}
.tblMenu tr td {
	width: 120px;
	text-align: left;
	padding: 2px 2px 2px 2px;
	cursor: pointer;
}
.tblMain {
	border: solid 2px #000000;	
}
.tblMain tr {
	
}
.tblMain tr td {
	width: 120px;
	text-align: left;
	padding: 2px 2px 2px 2px;
}
.tblShow .colTop{
	background-color: #000000;color: #FFFFFF;
}
.tblShow .colLeft{
	text-align: left;
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
}
.tblShow .colRight{
	text-align: right;
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
}
.tblShow .colClick{
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	background-color: #83ACDF;
	cursor: pointer;
}
.tblShow .colTotal{
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
}

.tblShow tr td{
	white-space: nowrap;
}
.divBlack {
	display: inline;
	float: left;
	width: 100px;
	height: 20px;
	color: #FFFFFF;
	background-color: #000000;
}
</style>
</head>
<body>
	
<center>
	<br/><br/><br/><br/><br/>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
				<table class="tblMenu" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td id="toManager" style="border-bottom: solid 1px #000000;">使用者管理</td>
					</tr>
<?php if($status == 1 || $status == 3){?>
					<tr>
						<td id="toMain" style="border-bottom: solid 1px #000000;">每日销量上传</td>
					</tr>
<?php }?>
<?php if($status == 1 || $status == 2){?>
					<tr>
						<td id="toQuery" style="border-bottom: solid 1px #000000;background-color: #FFB900;">用户查询</td>
					</tr>
<?php }?>
					<tr>
						<td id="toLogout" style="">登出</td>
					</tr>
				</table>
			</td>
			<td>
				<table class="tblMain" border="0" cellspacing="0" cellpadding="0" width="800" height="600">
					<tr>
						<td valign="top" style="position: relative;">
							<div style="padding: 40px 40px 40px 40px;">
								<div style="display: inline;float: left;">
									用户查询
								</div>
								
								<div style="display: inline;float: left;margin: 0 0 0 60px;">
									<input type="text" id="txtKeyword" style="width: 120px;height: 16px;border: solid 1px #000000;"/>
								</div>
								<div style="display: inline;float: left;">
									<div id="btnSearch" style="border-top: solid 1px #000000;border-right: solid 1px #000000;border-bottom: solid 1px #000000;width: 60px;height: 18px;background-color: #83ACDF;cursor: pointer;">搜寻</div>
								</div>
								<div id="btnDownloadQuery" style="display: inline;float: left;margin: 0 0 0 60px;background-color: #83ACDF;cursor: pointer;border: solid 1px #000000;width: 120px;">
									下载表格
								</div>
								<div style="clear: both;"></div>
								<br/>
								<br/>
								<table id="tblQuery" class="tblShow" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="colTop">用户名称</td>
										<td class="colTop">累积销量</td>
										<td class="colTop">完成等级</td>
										<td class="colTop">已领取等级</td>
										<td class="colTop">最近领取金额</td>
										<td class="colTop">累积领取金额</td>
										<td class="colTop">&nbsp;</td>
									</tr>
								</table>
							</div>
							
							<div style="text-align: center;position: absolute; bottom: 0;width: 100%;">
								<ul id="pagination-demo" class="pagination-sm"></ul>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr id="trDetail" style="display: none;">
			<td></td>
			<td><br/>
				<table id="tblDetailArea" class="tblMain" border="0" cellspacing="0" cellpadding="0" width="800" height="600">
					<tr>
						<td valign="top" style="position: relative;">
							<div id="divDetail" style="padding: 40px 40px 40px 40px;">
								<div class="divBlack" style="border: solid 1px #000000;">
									用户名称
								</div>
								<div id="txtUserame" style="display: inline;float: left;width: 120px;height: 20px;border-top: solid 1px #000000;border-right: solid 1px #000000;border-bottom: solid 1px #000000;">
									
								</div>
								
								<div class="divBlack" style="border: solid 1px #000000;margin: 0 0 0 20px;">
									目前累积销量
								</div>
								<div id="txtTotal" style="display: inline;float: left;text-align: right;width: 120px;height: 20px;border-top: solid 1px #000000;border-right: solid 1px #000000;border-bottom: solid 1px #000000;">
									
								</div>
								<div id="btnDownloadDetail" style="display: inline;float: left;margin: 0 0 0 40px;background-color: #83ACDF;cursor: pointer;border: solid 1px #000000;width: 120px;">
									下载表格
								</div>
								
								
								<div id="divClose" style="display: inline;float: left;margin: 0 0 0 30px;cursor: pointer;border: solid 1px #000000;width: 16px;text-align: center;">
									X
								</div>
								
								
								<div style="clear: both;"></div>
								<br/>
								派奖记录<br/>
								<table id="tblDetail" class="tblShow" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="colTop">领奖等级</td>
										<td class="colTop">领奖销量</td>
										<td class="colTop">领取奖金</td>
										<td class="colTop">是否领取</td>
										<td class="colTop">累积领取</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</center>
<script>
var g_sno = "<?php echo $sno;?>";
var g_user_id = null;


$(document).ready(function(){
	var pagination = $('#pagination-demo').twbsPagination({
		totalPages: 1,
		startPage: 1,
		visiblePages: 5,
		onPageClick: function (event, page) {
			alert(page);
			$('#page-content').text('Page ' + page);
		}
	});
	
	$("#btnSearch").click(function(){
			getData(1, $("#txtKeyword").val());
		});
	
	$("#toMain").click(function(){
			url_redirect({url: '<?php echo $base_url;?>guaguacard/main',
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
		});
	$("#toQuery").click(function(){
			url_redirect({url: '<?php echo $base_url;?>guaguacard/query',
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
		});
	$("#toManager").click(function(){
			url_redirect({url: '<?php echo $base_url;?>guaguacard/manager',
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
		});
	$("#toLogout").click(function(){
			url_redirect({url: '<?php echo $base_url;?>guaguacard/login',
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
		});
		
	$("#btnDownloadQuery").click(function(){
			url_redirect({url: '<?php echo $base_url;?>guaguacard/queryDL',
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
		});	
	$("#btnDownloadDetail").click(function(){
			saveFile();
		});
		
		
	$("#divClose").click(function(){
		$("#trDetail").hide();
		});
	getData();
});

function getData(page, keyword)
{
	$.ajax({type: 'POST',
		url:'<?php echo $base_url;?>guaguacard/queryResult',
		dataType:'json',
		data: { "sno": g_sno, "page": page, "keyword": keyword},
		success:function(res){
			//alert(res);
			if(res["msg"] != null)
			{
				alert(res["msg"]);
			}else{
				createTbl(res);
			}
		}
	});
}

function getDetailData(user_id)
{
	$.ajax({type: 'POST',
		url:'<?php echo $base_url;?>guaguacard/queryDetail',
		dataType:'json',
		data: { "sno": g_sno, "user_id": user_id},
		success:function(res){
			//alert(res);
			if(res["msg"] != null)
			{
				alert(res["msg"]);
			}else{
				g_user_id = user_id;
				$("#trDetail").show();
				createDetailTbl(res);
			}
		}
	});
}

function createDetailTbl(json)
{
	$("#txtUserame").html(json["username"]);
	$("#txtTotal").html(json["total"]);
	
	var t = new $.HtmlTableMgr("#tblDetail", 0, 0, 4);
	t.Clear(1);
	var total = 0;
	for(var key in json["data"])
	{
		var status = json["data"][key]["status"];
		var txtStatus = "";
		if(status == -1){
			txtStatus = "未开启";
		}else if(status == 0){
			txtStatus = "否";
		}else if(status == 1){
			txtStatus = "是";
		}
		
		if(status == 1)
		{
			total += json["data"][key]["prize"] * 1;
		}
		t.AddTr()
			.AddTd().Attr("class", "colLeft").InnerHtml(json["data"][key]["lv_cnname"])
			.AddTd().Attr("class", "colRight").InnerHtml(json["data"][key]["amount"])
			.AddTd().Attr("class", "colRight").InnerHtml(json["data"][key]["prize"])
			.AddTd().Attr("class", "colLeft").InnerHtml(txtStatus)
			.AddTd().Attr("class", "colTotal").InnerHtml(total.toFixed(1));
	}
	var body = $("html, body");
	body.stop().animate({scrollTop:600}, '2000', 'swing');
}

function createTbl(json)
{
	if(json["data"].length == 0)
	{
		alert("查无资料");	
	}
	var pages = json["pages"]==0?1:json["pages"];
	var startPage = json["startPage"]==0?1:json["startPage"];
	
	$('#pagination-demo').twbsPagination("destroy");
	var pagination = $('#pagination-demo').twbsPagination({
		totalPages: pages,
		startPage: json["current_page"] * 1,
		visiblePages: 5,
		onPageClick: function (event, page) {
			getData(page);
		}
	});
	
	var t = new $.HtmlTableMgr("#tblQuery", 0, 0, 4);
	t.Clear(1);
	for(var key in json["data"])
	{
		var prize = json["data"][key]["prize"]!=null?json["data"][key]["prize"]:0;
		var last_lv_cnname = json["data"][key]["last_lv_cnname"]!=null?json["data"][key]["last_lv_cnname"]:"LV0";
		var last_prize = json["data"][key]["last_prize"]!=null?json["data"][key]["last_prize"]:0;
		t.AddTr()
			.AddTd().Attr("class", "colLeft").InnerHtml(json["data"][key]["username"])
			.AddTd().Attr("class", "colRight").InnerHtml(json["data"][key]["total"])
			.AddTd().Attr("class", "colLeft").InnerHtml(getLevel(json["data"][key]["total"]))
			.AddTd().Attr("class", "colLeft").InnerHtml(last_lv_cnname)
			.AddTd().Attr("class", "colRight").InnerHtml(last_prize)
			.AddTd().Attr("class", "colRight").InnerHtml(prize)
			.AddTd().Attr("class", "colClick").InnerHtml("详细查询");
		
		$(t.focusNode).data("user_id", json["data"][key]["user_id"]);
		$(t.focusNode).click(function(){
			getDetailData($(this).data("user_id"));
			});
	}
}

function getLevel(total)
{
	var lv = "";
	if(total >= 2500000){
		lv = "至尊刮刮乐";
	}else if(total >= 1180000){
		lv = "LV20";
	}else if(total >= 790000){
		lv = "LV19";
	}else if(total >= 525000){
		lv = "LV18";
	}else if(total >= 350000){
		lv = "LV17";
	}else if(total >= 233000){
		lv = "LV16";
	}else if(total >= 156000){
		lv = "LV15";
	}else if(total >= 103000){
		lv = "LV14";
	}else if(total >= 69000){
		lv = "LV13";
	}else if(total >= 46000){
		lv = "LV12";
	}else if(total >= 30700){
		lv = "LV11";
	}else if(total >= 20500){
		lv = "LV10";
	}else if(total >= 13500){
		lv = "LV9";
	}else if(total >= 9100){
		lv = "LV8";
	}else if(total >= 6100){
		lv = "LV7";
	}else if(total >= 4500){
		lv = "LV6";
	}else if(total >= 2700){
		lv = "LV5";
	}else if(total >= 1800){
		lv = "LV4";
	}else if(total >= 1200){
		lv = "LV3";
	}else if(total >= 500){
		lv = "LV2";
	}else if(total >= 100){
		lv = "LV1";
	}else{
		lv = "LV0";
	}
	return lv;
}

function url_redirect(options){
	var $form = $("<form />");
	
	$form.attr("action",options.url);
	$form.attr("method",options.method);
	
	for (var data in options.data)
	$form.append('<input type="hidden" name="'+data+'" value="'+options.data[data]+'" />');
	
	$("body").append($form);
	$form.submit();
}
function saveFile()
{
	var today = new Date();
	var txtToday = today.getFullYear() + "" + (today.getMonth() + 1).toFill(2) + "" + today.getDate();
	
	//var textToWrite = $("#divDetail").prop('outerHTML');//Your text input;
	var t = new $.HtmlTableMgr("#tblDetail", 1, 0, 4);
	var textToWrite = $("#tblDetailArea").html();//Your text input;
	var t = new $.HtmlTableMgr("#tblDetail", 0, 0, 4);
	var textFileAsBlob = new Blob([textToWrite], {type:'text/plain'});
	var fileNameToSaveAs = "Report_" + txtToday + ".xls";//Your filename;
	
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

</body>
</html>
