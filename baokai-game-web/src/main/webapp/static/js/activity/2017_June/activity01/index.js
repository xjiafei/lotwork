/*
 *  活動的四種狀態 
 *  已報名活動未開始   btn_before
 *  我要報名          btn_reg
 *  已報名前往投注     btn_joined
 *  活動已結束        btn_fin
 */

$(document).ready(function () {
     
    var status = $('#status').val();
	var signStatus = $('#signStatus').val();
	var awardStatus = $('#awardStatus').val();
    var gameServer = $('#gameServer').val();

    if(status == -4){//-4, "活动尚未开始，请晚些时候再来。"
        
        alert('活动尚未开始，请晚些时候再来。');
        
	}else if(status == -5){//-5, "本次活动已经结束，请多多关注我们的其他活动。"
		
        $('#sign-up').removeClass().addClass('btn_fin');
        
	}else if(status == -1 || status == -3){//-1, "取得初始资料错误。" -3, "用戶错误。"
        
        $('#sign-up').removeClass();
    
	}else{
		if(signStatus == -22){//-22, "尚未報名"
            $('#sign-up').removeClass().addClass('btn_reg');
		}else if(signStatus == -20 || signStatus == -23){
            if(awardStatus == -3){//-3,預熱期
                $('#sign-up').unbind('click');
				$('#sign-up').removeClass().addClass('btn_before');
            }else{
                $('#sign-up').removeClass().addClass('btn_joined');
                $('#sign-up').bind('click',function(){
                        window.location  = gameServer+"/gameBet/cqssc/";    
                });
            }
		}
	}
    
    console.log('signStatus:'+signStatus)

    $('#sign-up').bind('click', function () {
			var source;
       if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
				source = "moblie";
       }else{
				source = "web";
       }
        
	   $.ajax({
			url:'/activity/activity20170601signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201706, week:1},
			success:function(data){
				console.log("data"+data);
                
                if(data == -3){
                    $('#sign-up').removeClass().addClass('btn_before');
                    $('#sign-up').unbind('click');
                    //alert('报名成功 ');
                 }else if(data == 20){
                    alert('报名完成 ');
                    $('#sign-up').removeClass().addClass('btn_joined');
                    $('#sign-up').bind('click',function(){
                        window.location  = gameServer+"/gameBet/cqssc/";    
                    });  
                 }else if(data == -23 || data == -20){
                    alert('已报名 ');
                    $('#sign-up').removeClass().addClass('btn_joined');
                    $('#sign-up').bind('click',function(){
                        window.location  = gameServer+"/gameBet/cqssc/";    
                    });
				 }else{
                     //alert("报名失败");
                 }
                
			}
		});
	})
})