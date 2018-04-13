<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>刮刮卡管理后台</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
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
.fileUpload {
	position: relative;
	overflow: hidden;
	padding: 0 20px 0 20px;
	width: 180px;
	border: solid 1px #000000;
	background-color: #8BABD2;
	text-align: left;
}
.fileUpload input.upload {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity=0);
}
#tblRecord .colTop{
	border-bottom: solid 1px #000000;
}
#tblRecord .colLeft{
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	text-align: center;
}
#tblRecord .colMiddle{
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	text-align: center;
}
#tblRecord .colRight{
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	text-align: left;
	cursor: pointer;
	background-color: #8BABD2;
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
						<td id="toMain" style="border-bottom: solid 1px #000000;background-color: #FFB900;">每日销量上传</td>
					</tr>
<?php }?>
<?php if($status == 1 || $status == 2){?>
					<tr>
						<td id="toQuery" style="border-bottom: solid 1px #000000;">用户查询</td>
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
						<td valign="top">
							<div style="padding: 40px 40px 40px 40px;">
								上传资料表<br/>
								<br/>
								<br/>
								<form action="<?php echo $base_url;?>guaguacard/main" method="post" enctype="multipart/form-data">
									<div>
										<input id="fileUpload" placeholder="Choose File" style="display: inline;width: 426px;border:solid 1px #000000;margin: 0px 0px 0px 0px;" disabled="disabled" />
										<div class="fileUpload" style="display: inline;">
											<span>浏览</span>
											<input id="btnUpload" type="file" id="file" name="file" class="upload" />
										</div>
										
										<div style="text-align: right;margin: 0 0 0 20px;display: inline;line-height: 20px;">
											<input type="button" id="btnDownloadDemo" style="width: 80px;background-color: #8BABD2;border: solid 1px #000000;cursor: pointer;" value="下载范例" />
										</div>
										
										<br/>
										<br/>
										<div style="text-align: right;margin: 0 200px 0 0;">
											<input type="hidden" id="sno" name="sno" value="" />
											<input type="submit" id="btnSubmit" style="width: 80px;background-color: #8BABD2;border: solid 1px #000000;cursor: pointer;" value="确认上传" />
										</div>
										<br/>
										<br/>
										<div style="color: #FF0000;">
											＊请记得先将档案转换为UTF8格式
										</div>
									</div>
								</form>
								<br/>
								<br/>
								<br/>
								<br/>
								历史上传资料表<br/>
								<br/>
								<br/>
								<table id="tblRecord" border="0" cellspacing="0" cellpadding="0" width="500">
									<tr>
										<td class="colTop" style="width: 400px;">上传时间</td>
										<td class="colTop" style="width: 400px;">操作用户</td>
										<td class="colTop" style="width: 100px;"></td>
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
	
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<?php echo $base_url;?>js/jUtil.js"></script>
<script>
var g_sno = "<?php echo $sno;?>";
var g_msg = "<?php echo $msg;?>";

$(document).ready(function(){
	$("#sno").val(g_sno);
	if(g_msg != null && g_msg != "")
	{
		alert(g_msg);
	}
	/*
	$("#btnSubmit").click(function(){
			$("#txtMsg").html("");
			$.ajax({type: 'POST',
				url:'<?php echo $base_url;?>guaguacard/verify',
				dataType:'json',
				data: { "username": $("#txtUsername").val(), "pwd": $("#txtPassword").val()},
				success:function(rs){
					alert(JSON.stringify(rs));
					if(rs["msg"] != "")
					{
						$("#txtMsg").html(rs["msg"]);
					}else{
						url_redirect({url: '<?php echo $base_url;?>guaguacard/main',
						                  method: "post",
						                  data: {'sno': rs["sno"]}
						                 });
					}	
				}
			});

		});
	*/
	$("#btnUpload").change(function(){
		$("#fileUpload").val($(this).val());
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
	$("#btnDownloadDemo").click(function(){
			url_redirect({url: '<?php echo $base_url;?>file/guaguacard_demo.csv',
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
		});
	getUploadData();
});

function getUploadData()
{
	$.ajax({type: 'POST',
		url:'<?php echo $base_url;?>guaguacard/uploadData',
		dataType:'json',
		data: { "sno": g_sno},
		success:function(res){
			if(res["msg"] != null)
			{
				alert(res["msg"]);
			}else{
				createTbl(res);
			}
		}
	});
}

function createTbl(json){
	var t = new $.HtmlTableMgr("#tblRecord", 0, 0, 4);
	t.Clear(1);
	for(var key in json["data"])
	{
		t.AddTr()
			.AddTd().Attr("class", "colLeft").InnerHtml(json["data"][key]["upload_time"])
			.AddTd().Attr("class", "colMiddle").InnerHtml(json["data"][key]["username"])
			.AddTd().Attr("class", "colRight").InnerHtml("下载");
		$(t.focusNode).data("filename", json["data"][key]["filename"]);
		$(t.focusNode).click(function(){
			url_redirect({url: '<?php echo $base_url;?>file/' + $(this).data("filename"),
			                  method: "post",
			                  data: {'sno': g_sno}
			                 });
			});
	}
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

</script>

</body>
</html>
