
/*
 *  活動的四種狀態 
 *  已報名活動未開始   btn_before
 *  我要報名          btn_reg
 *  已報名前往投注     btn_joined
 *  活動已結束        btn_fin
 */


var settings = {
    hasGot: true,//是否已领取奖励
}

$(document).ready(function () {
    var $progress = $('#progress-f')
    var $progressF = $('.progress-f')
    var $float = $('#float')
    var $crystal = $('#crystal')
    var $popUpInfo = $('#popup-info')
    var scalePX = [30, 203, 425, 635, 855, 1020]
    var scale = [1200, 12000, 60000, 120000, 240000]
    var $popup = $("#popup")
    var $mask = $('#mask')
    
    var gameServer = $('#gameServer').val();
    var status = $('#status').val();
	var signStatus = $('#signStatus').val();
	var awardStatus = $('#awardStatus').val();
    var award= 0;
    
    if(awardStatus == 1){
       settings.hasGot = false; 
    }
    
    $('#popup-close').bind('click', function () {
        $popup.hide()
        $mask.hide()
    })
    
    function showPopup() {
        $popup.show()
        $mask.show()
    }
    
    //進度條
    var bet = $('#bet').val();
    if(bet == ""){
       bet = 0.00
    }
    
    //进度条动画
    function showProgress(len) {
        var width = 0, display = false, timer;
        var step = function () {
            width = width + 20
            if (width > len) {
                width = len;
            }
            $progressF.css('width', width + 'px')
            if (width > 0 && width < len) {
                timer = setTimeout(step, 40);
            }
            if (width >= len) {
                setTimeout(function () {
                    $float.show().css('left', -58 + width + 'px').html('<div id="float-info">当日累计销量</div><div><span>为</span><span id="float-sum">' + bet + '</span></div>')
                }, 200);

            }
        };
        if (timer) clearTimeout(timer)
        step();
    }

    //初始化进度条 初始化水晶
    function initiate() {
        var len = 0

        if (bet < scale[0]) {
            len = scalePX[0]
        }
        else if (bet >= scale[0] && bet < scale[1]) {
            len = scalePX[1]
        }
        else if (bet >= scale[1] && bet < scale[2]) {
            len = scalePX[2]
        }
        else if (bet >= scale[2] && bet < scale[3]) {
            len = scalePX[3]
        }
        else if (bet >= scale[3] && bet < scale[4]) {
            len = scalePX[4]
        }
        else if (bet >= scale[4]) {
            len = scalePX[5]
        } else {
            len = scalePX[0]
            bet = 0
        }
        //显示进度条动画
        showProgress(len);
        if(bet < 1200) return;
        $crystal.css('cursor', 'pointer')
        //今日已领取
        if (settings.hasGot) {
            $crystal.bind('click', function () { 
                $popUpInfo.html('<div>今日已领取，</div><div>英雄您明日起早哟！</div>')
                showPopup()
            })
        } else {
            $crystal.addClass('spin-crystal').bind('click', function () {
                $('#crystal-shine').show().addClass('scale-crystal')
                //等待水晶发光效果结束
                setTimeout(function(){
                    $('#crystal-shine').hide()
                    $popUpInfo.html('<div>英雄您可以投注领取更大 </div><div>奖金，确定要现在领么</div><div id="ImSure">确定</div>')
                    //点击确认领取
                    $('#ImSure').bind('click', function () {
                        processAward();
                    })
                    showPopup()
                },600)

            })
        }
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
                bet="";
                initiate();
                $('#sign-up').unbind('click');
				$('#sign-up').removeClass().addClass('btn_before');
            }else{
                //初始化進度條
                initiate();
                $('#sign-up').removeClass().addClass('btn_joined');
                $('#sign-up').bind('click',function(){
                     window.location.href  = gameServer+"/gameBet/cqssc/";    
                });
            }
		}
	}

    $('#sign-up').bind('click', function () {
       var source;
       if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
				source = "moblie";
       }else{
				source = "web";
       }
        
	   $.ajax({
			url:'/activity/activity20170603signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201706, week:3},
			success:function(data){
				console.log("data"+data);
                if(data == -3){
                    $('#sign-up').removeClass().addClass('btn_before');
                    $('#sign-up').unbind('click');
                    //alert('报名成功 ');
                 }else if(data == 20){
                    //alert('报名完成 ');

                    //初始化進度條
                    initiate();
                    $('#sign-up').removeClass().addClass('btn_joined');
                    $('#sign-up').bind('click',function(){
                        
                        window.location.href  = gameServer+"/gameBet/cqssc/";    
                    });  
                 }else if(data == -23 || data == -20){
                    alert('已报名 ');
                   
                    //初始化進度條
                    initiate();
                    $('#sign-up').removeClass().addClass('btn_joined');
                    $('#sign-up').bind('click',function(){
                        window.location.href  = gameServer+"/gameBet/cqssc/";    
                    });
				 }else{
                     //alert("报名失败");
                 }
                
			}
		});
	})
    
    function processAward(){
        var source;
        if(/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)){
            source = "moblie";
        }else{
            source = "web";
        }
    
        $.ajax({
            url:'/activity/activity20170603award',
            type:'POST',
            dataType:'json',
            cache: false,
            data:{source:source, month:201706, week:3},
            success:function(data){

            console.log("data award"+data.award);
            console.log("data "+data);
            console.log("data noAward"+data.noAward);
            award = data.award;
            noAward = data.noAward;
                if(noAward==-5){
                    $('#ImSure').unbind('click')
                    $crystal.unbind('click').removeClass('spin-crystal')
                    $('#popup-info').html('<div>今日已领取，</div><div>英雄您明日起早哟！</div>')
                    $crystal.bind('click', function () {
//                        $popUpInfo.html('<div>今日已领取，</div><div>英雄您明日起早哟！</div>')
                        showPopup();
                    })
                }else{
                    $('#ImSure').unbind('click')
                    $('#popup-info').html('<div>恭喜您获得奖金!!</div> <span id="sum">' + award + '</span> <span id="yuan">元</span>')
                    $crystal.unbind('click').removeClass('spin-crystal')
                    $crystal.bind('click', function () {
                        $popUpInfo.html('<div>今日已领取，</div><div>英雄您明日起早哟！</div>')
                        showPopup();
                    })
                }    
            }
        });
    }
    
    //弹出框居中
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
})


