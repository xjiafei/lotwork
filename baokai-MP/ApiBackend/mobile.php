<?php


?>
<head>
	<meta charset="utf-8">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="./jUtil.js"></script>
</head>
<style>
.canvas
{
	width: 320px;
	height: 480px;	
	position: relative;
}
.mask
{
	line-height: 480px;
	vertical-align: middle;
	width: 320px;
	height: 480px;	
	position: absolute;
	background-color: #000000;
	z-index: 50;
}
.dialog
{
	display:table;
	line-height: 480px;
	vertical-align: middle;
	width: 320px;
	height: 480px;	
	position: absolute;
	z-index: 51;
}

.dialog_block
{
	display: table-cell;
	vertical-align: middle;
}

.block
{
	width: 160px;
	height: 160px;
	color: #008000;
	border: 1px solid #008000;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	background-color: #FFFFFF;
}

.page
{
	width: 320px;
	height: 480px;
	position: absolute;
	background-color: #F7F7F7;
	z-index: 0;
}

.title_bar
{
	width: 100%;
	height: 36px;
	line-height: 36px;
	position: relative;
	border-bottom: solid 1px #0F9982;
	text-align: center;
	
	color: #ffffff;
	background-color: #27B79F;
}


.div_btn
{
	width: 90%;
	line-height: 40px;
	margin: 10px 0 0 0;
	color: #3D998A;
	border: 1px solid #3D998A;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	background-color: #DEF2EE;
}

.div_btn:hover
{
	width: 90%;
	line-height: 40px;
	margin: 10px 0 0 0;
	color: #008000;
	border: 1px solid #008000;
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	background-color: #DEF2EE;
	cursor: pointer;
}

</style>
<body style="background-color: #000000;">

<table width="100%" height="100%">
<tr>
	<td align="center" valign="middle">
		<div class="canvas">
			<div id="mask" class="mask"></div>
			
			<div id="dialog" class="dialog">
				<div id="dialog_msg" class="dialog_block">
					<div class="block">
						<div id="dialog_msg_txt" style="line-height: 100px;">
						aaa
						</div>
						<div class="div_btn" onclick="$('#dialog_msg').hide();">
							OK
						</div>
					</div>
				</div>
				<div id="dialog_busy" class="dialog_block">
					<div class="block" style="height: 80px;">
						<div style="line-height: 80px;">
							<img src="./mobile/busy.gif" /> 处理中...
						</div>
					</div>
				</div>
			</div>
			<div id="page_login" class="page" style="z-index: 1;">
				<div class="title_bar login_bar">
					登陆	
				</div>
				<div style="margin: 10px 0 10px 0;">
					<img src="./mobile/header.png"/>
				</div>
				<div style="border-top: solid 1px #CCCCCC;border-bottom: solid 1px #CCCCCC;backgroud-color: #ffffff;">
					<div style="margin: 0 5px 0 5px;">
						<table width="100%">
							<tr height="30">
								<td width="20%">
									账户
								</td>
								<td width="70%">
									<input type="text" id="txtUsername" style="border: solid 0px;" />
								</td>
								<td width="10%">
									<img id="img_arrow" src="./mobile/arrow_down.png" />
								</td>
							</tr>
							<tr>
								<td colspan="3" height="1" style="border-bottom: solid 1px #CCCCCC;"></td>
							</tr>
							<tr height="30">
								<td width="20%">
									密码
								</td>
								<td width="70%">
									<input type="password" id="txtPassword" style="border: solid 0px;" />
								</td>
								<td width="10%">
									&nbsp;
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div style="text-align: left;margin: 5px 5px 5px 5px;line-height: 30px;">
					<table width="100%">
						<tr>
							<td width="20">
								<img id="imgRemember" src="./mobile/badge_unselected.png" width="20" height="20"/>
							</td>
							<td>
								
					 			记住账号
							</td>
						</tr>
					</table>
				</div>
				
				<div id="btn_login" class="div_btn">
					立即登录
				</div>
			</div>
			
			<div id="page_recommend" class="page">
				
			</div>
		</div>
	</td>
</tr>
</table>

<script>
$(document).ready(function(){
	$("#mask").css({ opacity: 0.5 }).hide();
	$("#dialog").hide();
	$("#dialog_msg").hide();
	$("#dialog_busy").hide();
	
	$("#img_arrow").click(function(){
			if($(this).attr("src") == "./mobile/arrow_down.png")
			{
				$(this).attr("src", "./mobile/arrow_up.png");
			}
			else
			{
				$(this).attr("src", "./mobile/arrow_down.png");
			}	
			
		});
	
	$("#imgRemember").click(function(){
			if($(this).attr("src") == "./mobile/badge_unselected.png")
			{
				$(this).attr("src", "./mobile/badge_selected.png");
			}
			else
			{
				$(this).attr("src", "./mobile/badge_unselected.png");
			}	
			
		});

	$("#btn_login").click(function(){
			if($("#txtUsername").val() == "" || $("#txtPassword").val() == "")
			{
				alert("请输入帐号或密码！");
			}
		});
		
	//switchPage($("#page_login"), $("#page_recommend"));
});

function switchPage(current, goto)
{
	//alert("C:" + $(current).css("z-index") + "-G:" + $(goto).css("z-index"));
	$(current).css("z-index", "1");
	$(goto).css("z-index", "0");
	$(current).animate({
			left: "+=320"
		}
		, 500
		, function() {
			$(current).css("z-index", "0");
			$(goto).css("z-index", "1");
			$(current).animate({
					left: "-=320"
				}
				, 500
				, function() {
					switchPage(goto, current);
					});
			});
}

</script>

</body>