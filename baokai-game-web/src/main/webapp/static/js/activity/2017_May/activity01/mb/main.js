
//活动有四种状态 未开始notStarted 未报名notSelected 已抽奖selected 已过期closed 內容空的emptyContent
var settings = {
    status: "notSelected",// ["notStarted" , 'notSelected', 'selected', 'closed']
    accumulation: 0,//当日累计
    rate: 0,//兑换比例
    times: 0,//倍数
    prize: 0//奖金

}

//弹窗固定
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
function countdown(times, cb) {
    var i = 0

    function startCount() {
        $('#times').html(i.toString().slice(0, 3))
        i += 0.1
        if (i > times) {
            cb()
            return
        }
        setTimeout(startCount, 50)
    }

    startCount()
}
function animaDialog() {
	
    var last = 0
    var isStop = true

    function startInterval() {
        isStop = false
        return setInterval(function () {
            var index = Math.floor(Math.random() * 5)
            if (last === index) {
                index = (last + 1) % 5
            }
            last = index
            $('.dialog').hide()
            $('#dialog' + index).show()
            // 可以调节气泡窗的切换速度
        }, 1000)
    }

    var interval
    var stop = function () {
        if (isStop) return
        isStop = true
        clearInterval(interval)
        $('.dialog').hide()
    }
    var start = function () {
        if (!isStop) return
        interval = startInterval()
    }

    return {
        stop: stop,
        start: start
    }

}

$(document).ready(function () {
	var status = $('#status').val();
	var signStatus = $('#signStatus').val();
	var common = $('#common').val();
	console.log('common:'+common);
	var dt = new Date();
	var month = dt.getMonth() +1;
	var date = dt.getDate();
	console.log(month);
	console.log(date);
	
	//console.log(status);
	
	if(status == -4){//-4, "活动尚未开始，请晚些时候再来。"
	 $('#welcome').removeClass('notSelected').addClass('notStarted');	
		settings.status = 'notStarted';
	
		$( "#sign-up").prop( "disabled", true ).unbind( "click" );
		console.log('活动尚未开始，请晚些时候再来。');
		
	}else if(status == -5){//-5, "本次活动已经结束，请多多关注我们的其他活动。"
		
		$('#welcome').removeClass('notSelected').addClass('closed');
		settings.status = 'closed';		
		console.log('活动已经结束。');
	}else if(status == -1 || status == -3){//-1, "取得初始资料错误。" -3, "用戶错误。"
		$('#welcome').removeClass('notSelected')
			console.log('取得初始资料错误。');
	}else{
		if(signStatus == -22){//-22, "尚未報名"
		console.log('尚未報名。');
		settings.status = 'hidePeople';
			if(awardStatus == -3){
				
				$('#welcome').removeClass('notSelected').addClass('notStarted');	
				settings.status = 'notStarted';
				$('#sign-up').addClass('before-sign');
				console.log('尚未報名預熱2');
					
			}
		}else if(signStatus == -20 || signStatus == -23){//-20, -23 "已報名"
			console.log('已報名');
			if(awardStatus == -3){
				
					$('#welcome').removeClass('notSelected').addClass('notStarted');	
					settings.status = 'notStarted';
					$('#sign-up').removeClass('before-sign').addClass('signed');
					console.log('已報名預熱');	
				
			}else {
				console.log('activity20170501rate');
				processAward();
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
		   
			url:'/activitymb/activity20170501signup',
			type:'POST',
			dataType:'json',
			cache: false,
			data:{source:source, month:201705, week:1, token:$('#token').val()},
			success:function(data){
				console.log("data"+data);
				if(data == 20 ){        
					console.log('報名成功');
					$('#sign-up').removeClass('before-sign').addClass('signed');									
					$( "#sign-up").unbind( "click" );
					
					$(".person").prop("disabled", true).unbind('click');
					settings.status = 'hidePeople';
					processAward();
					
				}else if(data == -23 || data == -20){
					console.log('已報名');
					$('#sign-up').removeClass('before-sign').addClass('signed');
					settings.status = 'hidePeople';
					$( "#sign-up").unbind( "click" );
					processAward();
					alert("已报名");

				}else{
					//alert("报名失败");
				}
				
			}
		});
	})
	
    

    function notStarted() {
        $('#welcome').addClass('notStarted')
    }

    function notSelected() {
		console.log('notSelected2222');
        $('#sign-up').addClass('before-sign')
        $('#welcome').addClass('notSelected')
        $('.before-sign').bind('click', function () {
            //模拟请求
            setTimeout(function () {
                //回调 将返回值传入
                settings.accumulation = accumulation
                settings.rate = rate
                settings.times = times
                settings.prize = prize
                $('#accumulation').html(settings.accumulation)
                $('#rate').html(settings.rate + '%')
                $('#sign-up').removeClass('before-sign').addClass('signed')
                $('.person')
                    .bind({
                        mouseenter: function () {
                            var i = $(this)[0].id[6]
                            dialog.stop()
                            $('.dialog').hide()
                            $('#dialog' + i).show()
                        },
                        mouseleave: function () {
                            dialog.start()
                        },
                        click: function () {
                            $('#popup').show()
                            $('#mask').show()
                            dialog.stop()
                        }
                    }).css('cursor', 'pointer')
                dialog.start()
            }, 1000)
        })
    }
	
	function hidePeople() {
		console.log('hidePeople');
        $('#sign-up').addClass('before-sign')
        $('#welcome').addClass('notSelected')     
    }
    function selected(){
        $('#sign-up').addClass('signed')
        $('#welcome').addClass('selected')

    }
    function closed(){
        $('#welcome').addClass('closed')
    }
	console.log('case:' + settings.status);
	
    switch (settings.status) {
		
        case "notStarted":
		console.log('case:notStarted' + status);
            notStarted()
            break
        case 'notSelected':
		console.log('case:notSelected' + status);
            notSelected()
            break
        case 'selected':
		console.log('case:selected' + status);
            selected()
            break
        case 'closed':
		console.log('case:closed' + status);
            closed()
            break
		case 'hidePeople':
		console.log('case:hidePeople' + status);
			hidePeople();
            break
        default:
		console.log('case:default' + status);
            notSelected()
            break
    }
	var accumulation = "";	//當日累計投注量
	var rate = "";			//兌換比例			
	var prize = "";			//倍數

	
	function processAward(){
			
				console.log('activity20170501processAward');
				var awardStatus = $('#awardStatus').val();	
							
				$('#sign-up').removeClass('before-sign').addClass('signed');
					
				var dialog = animaDialog();
				
				$.ajax({		   
					url:'/activitymb/activity20170501rate',
					type:'POST',
					dataType:'json',
					cache: false,
					data:{token:$('#token').val()},
					success:function(data){															
						if(data.awardStatus == -1){//獎金已領取
							console.log('rate獎金已領取')
							$('#welcome').removeClass('notSelected').addClass('selected');
							var dataStr = data.memo;
							var jsdata = jQuery.parseJSON(dataStr);
							console.log(jsdata);
							accumulation = jsdata.bet;
							rate = jsdata.rate;
							times = jsdata.multiple;
							prize = jsdata.award;
							
							$('#accumulation').html(accumulation);
							$('#rate').html(rate + '%');
							$('#times').html(times);
							$('#prize').html(prize);
							
							$( "#sign-up").unbind( "click" );
							$('#sign-up').removeClass('before-sign').addClass('signed');
							//alert("奖金已领取");
							return;
						}else if(data.awardStatus == 0){
							console.log('rate無獎金');
							$('#accumulation').html(data.bet);
							$('#rate').html('0%');
							// $('#times').html(data.multiple);
							// $('#prize').html(data.award);
							$( "#sign-up").unbind( "click" );
							$('#sign-up').removeClass('before-sign').addClass('signed');
							
						}else if(data.awardStatus == 1){
							console.log('rate有獎金');		

							accumulation = data.bet;
							rate = data.rate;
							prize = data.award;
							if(data.multiple > 0.1){
								times = data.multiple + 0.1;
							}else{
								times = data.multiple;
							}		
							
							//模拟请求
							setTimeout(function () {
								//回调 将返回值传入
								settings.accumulation = accumulation;
								settings.rate = rate;
								settings.times = times;
								settings.prize = prize;
								$('#accumulation').html(settings.accumulation)
								$('#rate').html(settings.rate + '%')
							
								$('.person')
									.bind({
										mouseenter: function () {
											var i = $(this)[0].id[6]
											dialog.stop()
											$('.dialog').hide()
											$('#dialog' + i).show()
										},
										mouseleave: function () {
											dialog.start()
										},
										click: function () {
											$('#popup').show()
											$('#mask').show()
											dialog.stop()
										}
									}).css('cursor', 'pointer')
								dialog.start()
							}, 1000)
								$('#popup-no').bind('click', function () {
									console.log('#popup-no');
									$('#popup').hide()
									$('#mask').hide()
									dialog.start()
								})	
							$( "#sign-up").unbind( "click" );
							$('#sign-up').removeClass('before-sign').addClass('signed');	
						}else if(data.awardStatus == -2){
							console.log('rate無法點擊人民');																
						}			
											
					}
				});
				
					$('#popup-yes').bind('click', function () {
					console.log('#popup-yes');
					var bet = accumulation;
					var rate = $('#rate').html();
					var multiple = times -0.1;
					var award = prize;
					
					console.log(bet);
					console.log(rate);
					console.log(multiple);
					console.log(award);
					$.ajax({		   
						url:'/activitymb/activity20170501award',
						type:'POST',
						dataType:'json',
						cache: false,
						data:{token:$('#token').val(),bet:bet, rate:rate, multiple:multiple, award:award},
						
						success:function(data){
							if(data.awardStatus == -1){//獎金已領取
							console.log('award獎金已領取');	
							
								$('#welcome').removeClass('notSelected').addClass('selected');
								var dataStr = data.memo;
								var jsdata = jQuery.parseJSON(dataStr);
								console.log(jsdata);
								accumulation = jsdata.bet;
								rate = jsdata.rate;
								times = jsdata.multiple;
								prize = jsdata.award;
								
								$('#accumulation').html(accumulation);
								$('#rate').html(rate + '%');
								$('#times').html(times);
								$('#prize').html(prize);
								alert("奖金已领取");
								$('#popup').hide()
								$('#mask').hide()
								$('.person').css('cursor', 'default').unbind('click').unbind('mouseenter').unbind('mouseleave')
								dialog.stop()
								$('#popup-no').bind('click', function () {
									console.log('#popup-no');
									$('#popup').hide()
									$('#mask').hide()
									dialog.start()
								})	
								return;
								
							}else if(data.awardStatus == 0){
								console.log('award無獎金');
								$( "#sign-up").unbind( "click" );
									
							}else if(data.awardStatus == 1){
								console.log('award有獎金');	
								accumulation = data.bet;
								rate = data.rate;
								times = data.multiple;
								prize = data.award;							
								console.log(times)
								$('#welcome').removeClass('notSelected').addClass('selected');
								$('#times').html(times)
								
								$('#popup').hide()
								$('#mask').hide()
								$('.person').css('cursor', 'default').unbind('click').unbind('mouseenter').unbind('mouseleave')
								dialog.stop()
								countdown(settings.times, function () {
									setTimeout(function () {
										$('#times').html(times)
										$('#prize').html(settings.prize)
										$('#welcome').removeClass('notSelected').addClass('selected')
									}, 500)

								})								
								$('#popup-no').bind('click', function () {
									console.log('#popup-no');
									$('#popup').hide()
									$('#mask').hide()
									dialog.start()
								})		
							}else if(data.awardStatus == -2){
								console.log('award無法點擊人民');
								$( "#sign-up").unbind( "click" );	
							}
								
						}
					});
			
					
				})
			
			positionFixd('#popup')	
			
	}
})



