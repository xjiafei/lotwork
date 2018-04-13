$(function(){
	
	function HideContent(id) {
		document.getElementById(id).style.display = "none";
		}
	function ShowContent(id) {
		document.getElementById(id).style.display = "block";
	}	
	
	var status = $('#status').val();
	var signStatus = $('#signStatus').val();
	var awardStatus = $('#awardStatus').val();
    
	if(status == -4){//-4, "活动尚未开始，请晚些时候再来。"
        
        if(awardStatus == -3){
				
            HideContent('before');
            HideContent('done');
	        HideContent('fin');
			HideContent('reg');
        }else{
            
            HideContent('done');
            HideContent('fin');
            HideContent('reg');
            HideContent('joined');
            //alert('活动尚未开始，请晚些时候再来。');    
        }
        
	}else if(status == -5){//-5, "本次活动已经结束，请多多关注我们的其他活动。"
		
		HideContent('before');
        HideContent('done');
        HideContent('reg');
        HideContent('joined');
        
	}else if(status == -1 || status == -3){//-1, "取得初始资料错误。" -3, "用戶错误。"
		
		HideContent('before');
        HideContent('done');
	    HideContent('fin');
        HideContent('reg');
        HideContent('joined');
        
	}else{
		if(signStatus == -22){//-22, "尚未報名"
            
			HideContent('before');
            HideContent('done');
	        HideContent('fin');
            HideContent('joined');
            
		}else if(signStatus == -20 || signStatus == -23){
            
            if(awardStatus == -3){
				//已報名活動未開始
                HideContent('done');
                HideContent('fin');
                HideContent('reg');
			    HideContent('before');
                
            }else{
                HideContent('joined');
                HideContent('before');
                HideContent('fin');
                HideContent('reg');
    
                
            }
        
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
    document.getElementById('reg').onclick = function () {
        ga('send', 'event', '点击报名参加五月活动', 'click(点击)');
        var source;
		if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
				source = "moblie";
		}else{
				source = "web";
		}
	   $.ajax({
		   
			url:'/activity/activity20170503signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201705, week:3},
			success:function(data){
				
                if(data == -3){
                    //預熱時期
                    ShowContent('joined')
                    HideContent('before');
                    HideContent('done');
                    HideContent('fin');
                    HideContent('reg');
                 }else if(data == 20){
                    alert("报名成功");
					
                    ShowContent('done');
                    HideContent('before');
                    HideContent('fin');
                    HideContent('reg');
                    HideContent('joined');
                 }else if(data == -23 || data == -20){
				    alert("报名成功");
					
                    ShowContent('done');
                    HideContent('before');
                    HideContent('fin');
                    HideContent('reg');
                    HideContent('joined');
				 }else{
                     alert("报名失败");
                 }
                
			}
		});
    }
  
});
