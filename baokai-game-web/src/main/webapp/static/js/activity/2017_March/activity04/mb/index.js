$(function(){
   //GA
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-63536434-1', 'auto');
    ga('send', 'pageview');

    //google analytics 点击统计
    document.getElementById('join').onclick = function () {
        ga('send', 'event', '点击报名参加三月活动', 'click(点击)');
        var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}

                       $.ajax({
		url:'/activitymb/activity20170304signup',
		type:'POST',
		dataType:'json',
		cache: false,
		data:{source:source, month:201703, week:4,token : $("#token").val()},
		success:function(data){
			if(data == 20 ){            //表示获取红包成功
				alert("报名成功");
                                                                                           document.getElementById('join').className = "btn btn_finish";
                                                                                           document.getElementById('join').href = 'phlotto://go_lottolobby';
                                                                                           document.getElementById('join').removeAttribute('id') ;
                                                                    }else if(data == -23 || data == -20){
				alert("已报名");
			}else{
				alert("报名失败");
			}
		}
	});
    }
  
});
