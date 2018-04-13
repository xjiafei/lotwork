$(function(){
	
	function HideContent(id) {
		document.getElementById(id).style.display = "none";
		}
	function ShowContent(id) {
		document.getElementById(id).style.display = "block";
	}	
	
	var status = $('#status').val();
	var signStatus = $('#signStatus').val();
	//console.log(status);
	
	if(status == -4){//-4, "活动尚未开始，请晚些时候再来。"
		HideContent('join');
		HideContent('joined');
		HideContent('finished');
		
		alert('活动尚未开始，请晚些时候再来。');
	}else if(status == -5){//-5, "本次活动已经结束，请多多关注我们的其他活动。"
		
		HideContent('join');
		HideContent('joined');
	}else if(status == -1 || status == -3){//-1, "取得初始资料错误。" -3, "用戶错误。"
		
		HideContent('join');
		HideContent('joined');
		HideContent('finished');
	}else{
		if(signStatus == -22){//-22, "尚未報名"
			HideContent('joined');
			HideContent('finished');
		}else if(signStatus == -20 || signStatus == -23){
			// $('.btn-join').hide();	
			// $('.btn-finished').hide();		
			HideContent('join');
			HideContent('finished');			
		}
	}
	
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
        ga('send', 'event', '点击报名参加四月活动', 'click(点击)');
        var source;
		if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
				source = "moblie";
		}else{
				source = "web";
		}
	   $.ajax({
		   
			url:'/activity/activity20170404signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201704, week:4},
			success:function(data){
				
				if(data == 20 ){        	
					HideContent('join');
					HideContent('finished');									
					alert("报名成功");
					
				}else if(data == -23 || data == -20){
					HideContent('finished');		
					alert("已报名");
				}else{
					alert("报名失败");
				}
				location.reload();
			}
		});
    }
  
});
