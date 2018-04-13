<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>宝开七月活动</title>
	<meta name="description" content="宝开七月活动">
	<meta name="keywords" content="宝开 宝开彩票 宝开彩票 返利">
	<link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/july/index/css/style.css">
</head>
<body>
	<header>
		<div class="banner"></div>
	</header>
	<article>
		<div class="content">
			<div class="time time1"></div>
			<div class="time time2"></div>
			<div class="time time3"></div>
			<a href="/ht/july/active1.html" target="blank" class="link link1"></a>
			<a href="/ht/july/active2.html" target="blank" class="link link2"></a>
			<a href="/ht/july/active3.html" target="blank" class="link link3"></a>
		</div>
	</article>
	<footer class="footer">
		<div class="copy-right">&copy;2003-2016 宝开彩票 All Rights Reserved</div>
	</footer>
	<script type="text/javascript" src="{$path_img}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	function calculateTime(){
		var now = new Date();
		var time1 = new Date('2016/07/12 06:00');
		var spantime = (time1.getTime()-now.getTime())/1000;
		var d1 = Math.floor(spantime / (24 * 3600));
        var h1 = Math.floor((spantime % (24*3600))/3600);
        var m1 = Math.floor((spantime % 3600)/(60));
        if(spantime<0){
			$('.time1').html('活动已开始');
	    }else{
			$('.time1').html(d1 + '天' + h1 + '时'+ m1 +'分');   
		}

		var time2 = new Date('2016/07/18 00:00');
		spantime = (time2.getTime()-now.getTime())/1000;
		var d2 = Math.floor(spantime / (24 * 3600));
        var h2 = Math.floor((spantime % (24*3600))/3600);
        var m2 = Math.floor((spantime % 3600)/(60));
        if(spantime<0){
			$('.time2').html('活动已开始');
	    }else{
			$('.time2').html(d2 + '天' + h2 + '时'+ m2 +'分');
		}


		var time3 = new Date('2016/07/25 00:00');
		spantime = (time3.getTime()-now.getTime())/1000;
		var d3 = Math.floor(spantime / (24 * 3600));
        var h3 = Math.floor((spantime % (24*3600))/3600);
        var m3 = Math.floor((spantime % 3600)/(60));
        if(spantime<0){
			$('.time3').html('活动已开始');
	    }else{
			$('.time3').html(d3 + '天' + h3 + '时'+ m3 +'分');
		}

		setTimeout(calculateTime,60000);
	}

	calculateTime();
		
	</script>
</body>
</html>