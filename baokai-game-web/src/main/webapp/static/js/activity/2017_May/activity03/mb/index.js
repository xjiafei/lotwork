$(function(){
	var status = $('#status').val();
	var signStatus = $('#signStatus').val();
    var awardStatus = $('#awardStatus').val();

	//console.log(status);
	if(status == -4){//-4, "活动尚未开始，请晚些时候再来。"
	
        if(awardStatus==-3){
          $('.btn-before').hide();
          $('.btn-done').hide();
		  $('.btn-fin').hide();
          $('.btn-reg').hide();
           
        }else{
          $('.btn-done').hide();
		  $('.btn-fin').hide();
		  $('.btn-reg').hide();
          $('.btn-joined').hide();
            
        }
	}else if(status == -5){//-5, "本次活动已经结束，请多多关注我们的其他活动。"
		$('.btn-before').hide();
		$('.btn-done').hide();
        $('.btn-reg').hide();
        $('.btn-joined').hide();
         
	}else if(status == -1 || status == -3){//-1, "取得初始资料错误。" -3, "用戶错误。"
		$('.btn-before').hide();
		$('.btn-done').hide();
        $('.btn-fin').hide();
        $('.btn-reg').hide();
        $('.btn-joined').hide();
         
	}else{
		if(signStatus == -22){//-22, "尚未報名"
            $('.btn-done').hide();
            $('.btn-fin').hide();  
            $('.btn-before').hide();
            $('.btn-joined').hide();
             
		}else if(signStatus == -20 || signStatus == -23){
			if(awardStatus == -3){
                
                //已報名活動未開始
			    $('.btn-done').hide();
                $('.btn-fin').hide();  
                $('.btn-before').hide();
                $('.btn-reg').hide();
                
            }else{
                $('.btn-joined').hide();
                $('.btn-before').hide();
			    $('.btn-reg').hide();
                $('.btn-fin').hide();
    
            }
		}
		
	}
	
   
    document.getElementById('reg').onclick = function () {
        
        var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	console.log("mobile>>>"+source);
	
	   $.ajax({
			url:'/activitymb/activity20170503signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201705, week:3,token : $("#token").val()},
			success:function(data){
				 
                 if(data == -3){
                    //預熱時期
                    $('.btn-before').hide();
                    $('.btn-done').hide();
                    $('.btn-fin').hide();
					$('.btn-joined').show();
                    $('.btn-reg').hide();
                 }else if(data == 20){
                    alert("报名成功");
					$('.btn-before').hide();
                    $('.btn-done').show();
                    $('.btn-fin').hide();
					$('.btn-reg').hide();
                    $('.btn-joined').hide();
                 }else if(data == -23 || data == -20){
				    alert("已报名");
                    $('.btn-before').hide();
                    $('.btn-done').show();
                    $('.btn-fin').hide();
					$('.btn-reg').hide();
                    $('.btn-joined').hide();
				 }else{
                     alert("报名失败");
                 }
                  
			}
		});
    }
  
});
