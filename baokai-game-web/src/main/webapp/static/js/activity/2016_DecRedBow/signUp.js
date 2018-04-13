$("#regbtn").click(function(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/signup20161201',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, week:1 ,month:201612},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				alert("报名成功");
				$("#regbtn").css("display","none");
				$("#regbtn1").css("display","");
			}else if(data == 2){
				alert("已报名");
			}else{
				alert("报名失败");
			}
		}
	});
	
});
function querySignUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/querysignup20161201',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201612, week:1, actId:20161201},
		success:function(data){
			if(data == 0 ){   //可以報名
				$("#regbtn").css("display","");
				$("#regbtn1").css("display","none");
				
			}else if(data == 2){
				
				$("#regbtn").css("display","none");
				$("#regbtn1").css("display","");
			}
			else if(data == 4){
				
				$("#regbtn").css("display","none");
				$("#regbtn2").css("display","");
			}
		}
	});
}



(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	ga('create', 'UA-63536434-1', 'auto');
	ga('send', 'pageview');

	ga('send', 'event', '12月第一周活动', '打开活动页面');

	window.onload = function(){
		querySignUp();
		document.getElementById('regbtn').onclick= function(){
			ga('send', 'event', '12月第一周活动报名', '点击【我要报名】按钮');
		}
	}