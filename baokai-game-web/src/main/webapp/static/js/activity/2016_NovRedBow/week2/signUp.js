function signUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/redbowsignup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201611, week:2},
		success:function(data){
			if(data == 1 ){            //表示获取红包成功
				alert("报名成功");
				querySignUp();
			}else if(data == 2){
				alert("已报名");
			}else{
				alert("报名失败");
			}
		}
	});
}

function querySignUp(){
	var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	$.ajax({
		url:'/activity/redbowquerysignup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201611, week:2},
		success:function(data){
			if(data == 0 ){            //可以報名
				$("#nobaoming").css("display","");
				$("#baomingAlready").css("display","none");
				
			}else if(data == 2){
				$("#nobaoming").css("display","none");
				$("#baomingAlready").css("display","");
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

    $(function(){
      //google analytics 点击统计
      $(".apply").on('click',function(){
        ga('send', 'event', '十一月第二周活动', '报名按钮');
      });
    })