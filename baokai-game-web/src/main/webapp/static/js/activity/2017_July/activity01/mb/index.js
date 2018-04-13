/*
 *  活動的四種狀態 
 *  已報名活動未開始   btn_before
 *  我要報名          btn_reg
 *  已報名前往投注     btn_joined
 *  活動已結束        btn_fin
 */

var settings = {
    finishedDays:[0,0,0,0,0,0,0],//每个元素按顺序对于一周的每一天,1代表完成目标,0代表为完成目标
    bonus:0//奖金, 如果未完成则为0
}
$(document).ready(function () {
    var pokerToggled = false
    var lightUp = false
    var $signUp = $('#sign-up')
    var $popup = $("#popup")
    var $mask = $('#mask')
    
    var status = $('#status').val();
	var signStatus = $('#signStatus').val();
	var awardStatus = $('#awardStatus').val();
    
     var tmpArray = $('#statusArray').val().replace("[","").replace("]","");

    var tmpArray2  = tmpArray.split(",");
    var result = new Array(tmpArray2.length);
    for (var i = 0; i < tmpArray2.length; i++) { 
        result[i] = Number(tmpArray2[i]);
    }
    //設定setting
    settings.finishedDays = result;
    settings.bonus = $('#extraAward').val();
    
    $('.popup-close').bind('click', function () {
        $popup.hide()
        $mask.hide()
    })
    function showPopup(){
        if(settings.bonus > 0){
            $('#popup-info').html(' <div>魔术师当前累计的加奖奖金为: <div id="bonus">'+ settings.bonus+'</div></div> <div>请再爆发您的洪荒之力吧！</div>')
        }else{
            $('#popup-info').html(' <div>魔术之路充满荆棘，险象环生，</div><div>您要越挫越勇，再接再厉！</div>')
        }
        $popup.show().addClass('scale-popup')
        $mask.show()
    }
    //震动扑克
    function shake(){
        var i=0
        function iter(){
            setTimeout(function(){
                $('#poker-item' + i +' .poker').addClass('shake')
                i+=1
                if(i<6){
                    iter()
                }
            },50)
        }
        iter()
    }
    shake()
    function init(){
        //扑克变亮动画
        function activeDays (){
            var i= 0
            function iter(){
                if(i>6) return
                if(settings.finishedDays[i] && !isNaN(settings.finishedDays[i])){
                    setTimeout(function(){
                        $('#poker-item' + i +' .poker').addClass('poker-l').removeClass('shake')
                        $('#poker-item' + i +' .pokerDate').css({opacity: 1})
                        i+=1
                        iter()
                    },500)
                }else{
                    i+=1
                    iter()
                }

            }
            iter()
            lightUp = true
        }
        
        //绑定点击领取时间
        if(awardStatus!=-3){
            $('#getBonusInfo').html('点击查看修炼加奖').addClass('bounce')
            
            $('#getBonus').bind('click',function(){
            $('.poker').removeClass('shake').removeClass('poker_h');
            $('.pokerDate').hide()
            if(pokerToggled){
                showPopup()
            }else{
                setTimeout(function(){
                    showPopup()
                },1800)
            }
            for(var i = 0;i < 6;i++){
                $('#poker-item' + i).css({
                    top: 23+ i * 0.5 +'px',
                    left: 320+ i * 0.5 +'px',
                    transform: 'rotate(0deg)'
                })
            }
                pokerToggled = true
            }).css({
                cursor:'pointer'
            })      
        }

        //屏幕滚动850px开始展示动画
        $( window ).scroll(function(){
            var scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
            if( scrollTop >= 850 && !lightUp) {
                activeDays()
            }
        })

    }

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
                //初始化
                init();
                $('#sign-up').unbind('click');
				$('#sign-up').removeClass().addClass('btn_before');
            }else{
                //初始化
                init();
                $('#sign-up').removeClass().addClass('btn_joined');
                $('#sign-up').bind('click',function(){
                     window.location.href  = "phlotto://go_recharge";    
                });
            }
		}
	}
    
    $('#sign-up').bind('click', function () {
    
    if(signStatus!=-22){
          return;
    }
       
    var source;
	if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
			source = "moblie";
	}else{
			source = "web";
	}
	console.log("mobile>>>"+source);
	
	   $.ajax({
			url:'/activitymb/activity20170701signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201707, week:1,token : $("#token").val()},
			success:function(data){
				 
                if(data == -3){
                    $('#sign-up').removeClass().addClass('btn_before');
                    $('#sign-up').unbind('click');
                    //alert('报名成功 ');
                 }else if(data == 20){
                    //alert('报名完成 ');
                     
                    //初始化
                    init();
                    $('#sign-up').removeClass().addClass('btn_joined');
                    $('#sign-up').bind('click',function(){
                        window.location  = "phlotto://go_recharge";    
                    });
                 }else if(data == -23 || data == -20){
                    //alert('已报名 ');
                    
                   //初始化
                    init();
                    $('#sign-up').removeClass().addClass('btn_joined');
                    $('#sign-up').bind('click',function(){
                        window.location  = "phlotto://go_recharge";    
                    });
				 }else{
                     //alert("报名失败");
                 }
                  
			}
		});
    })
    
    //固定弹窗
    function positionFixd(obj) {
        var $box = $(obj);

        function move() {
            var height = $box.height(),
                width = $box.width(),
                wH = $(window).height(),
                wW = $(window).width(),
                scroll = $(document).scrollTop();
            $box.css({left: (wW - width) / 2, top: scroll + (wH - height) / 2}, "fast")

        }

        $(document).ready(function () {
            setTimeout(move(), 100);
        })
        $(window).resize(function () {
            setTimeout(move(), 100);
        })
        $(window).scroll(function () {
            setTimeout(move(), 100);
        })

    }
    positionFixd('#popup');
    
    (function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
        new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-PKBGG3G');
     
})
