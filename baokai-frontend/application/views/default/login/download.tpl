<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
	<title>下载APP</title>
	<link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
	<style type="text/css">
		.download_page{
			height:1096px;
			background:url({$path_img}/images/wap/login/download_bg.jpg) no-repeat center top;
		}
		.clickA{
			width: 273px;
    		height: 90px;
    		position: absolute;
    		left: 164px;
    		top: 355px;
		}

	</style>
</head>
<body>
<!--download.html begin-->
	<div class="container">
		<div class="page download_page">
			<a href="itms-services://?action=download-manifest&url=https://m.phoenix668.com/app/jiaoge_br.plist" class="clickA"></a>						<!--ios app下载链接-->
			<a href="http://dlfhhd.b0.upaiyun.com/mobile/andorid/Caipiao.apk" class="clickA" style="top:480px;"></a>	<!--android 下载链接-->
		</div>
	</div>
	<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
	<script type="text/javascript">
		$('.container').css({
			'width': window.innerWidth + 'px',
			'height': window.innerHeight + 'px'
		});

		$('.container .page').css('zoom', window.innerWidth / 640);
		$('.container').show();
	</script>
<!--download.html end-->
</body>
</html>