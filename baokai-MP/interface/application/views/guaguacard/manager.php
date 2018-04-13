<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>刮刮卡管理后台</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<?php echo $base_url;?>js/jUtil.js"></script>
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
#tblModify .colTop{
	border: solid 1px #000000;
	text-align: center;
}
#tblModify .colLeft{
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	text-align: center;
}
#tblModify .colRight{
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	text-align: center;
}
#tblModify .colBottom{
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
	border-right: solid 1px #000000;
	text-align: center;
}

#tblReset .colTop{
	border-top: solid 1px #000000;
	border-left: solid 1px #000000;	
	border-bottom: solid 1px #000000;
	text-align: left;
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
						<td id="toManager" style="border-bottom: solid 1px #000000;background-color: #FFB900;">使用者管理</td>
					</tr>
<?php if($status == 1 || $status == 3){?>
					<tr>
						<td id="toMain" style="border-bottom: solid 1px #000000;">每日销量上传</td>
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
								<table id="tblModify" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="colTop" colspan="2" style="background-color: #000000;color: #FFFFFF;">修改密码</td>
									</tr>
									<tr>
										<td class="colLeft">旧密码</td>
										<td class="colRight"><input type="password" id="txtOldPwd" /></td>
									</tr>
									<tr>
										<td class="colLeft">新密码</td>
										<td class="colRight"><input type="password" id="txtNewPwd" /></td>
									</tr>
									<tr>
										<td class="colBottom" colspan="2"><input type="button" id="btnModify" value="修改" /></td>
									</tr>
								
								</table>
<?php if($status == 1){?>
								<br/><br/>
								<table id="tblReset" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="colTop" style="background-color: #000000;color: #FFFFFF;">上传管理者</td>
										<td class="colTop">帐号(updateman)</td>
										<td id="resetUpdateman" class="colTop" style="border-right: solid 1px #000000;background-color: #83ACDF;cursor: pointer;">重置密码</td>
										
									</tr>
									<tr>
										<td colspan="3">&nbsp;</td>
									</tr>
									<tr>
										<td class="colTop" style="background-color: #000000;color: #FFFFFF;">资料查询管理者</td>
										<td class="colTop">帐号(queryman)</td>
										<td id="resetQueryman" class="colTop" style="border-right: solid 1px #000000;background-color: #83ACDF;cursor: pointer;">重置密码</td>
										
									</tr>
								</table>
								<script>
								$(function(){
									$("#resetUpdateman").click(function(){
										$.ajax({type: 'POST',
											url:'<?php echo $base_url;?>guaguacard/resetPwd',
											dataType:'json',
											data: { "sno": g_sno, "target": 1},
											success:function(rs){
												//alert(JSON.stringify(rs));
												alert(rs["msg"]);
											}
										});
									});
									$("#resetQueryman").click(function(){
										$.ajax({type: 'POST',
											url:'<?php echo $base_url;?>guaguacard/resetPwd',
											dataType:'json',
											data: { "sno": g_sno, "target": 2},
											success:function(rs){
												//alert(JSON.stringify(rs));
												alert(rs["msg"]);
											}
										});
									});
								});
								</script>
<?php }?>
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


$(document).ready(function(){
	$("#btnModify").click(function(){
			$("#txtMsg").html("");
			$.ajax({type: 'POST',
				url:'<?php echo $base_url;?>guaguacard/modifyPwd',
				dataType:'json',
				data: { "sno": g_sno, "oldPwd": $("#txtOldPwd").val(), "newPwd": $("#txtNewPwd").val()},
				success:function(rs){
					//alert(JSON.stringify(rs));
					alert(rs["msg"]);
					$("#txtOldPwd").val("");
					$("#txtNewPwd").val("");
					if(rs["status"] == -1 || rs["status"] == 1){
						url_redirect({url: '<?php echo $base_url;?>guaguacard/login'});
					}	
				}
			});
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
});

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
