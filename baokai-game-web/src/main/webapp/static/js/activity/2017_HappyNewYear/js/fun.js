/**
 * Created by user on 17/1/5.
 */
var fastActivity,twoActivity,thirdActivity,vipActivity;
//自定义方法
$(function(){
    //自定义弹框
    window.showmessage = function(msg,flag){

        $('.alert').find('.contenttext').html(msg);
        $('.mask').fadeIn();
        $('.alert').show();
        if(flag) {
            $('.alert').find('.charge').show().css({"display":"inline-block"});
        }else {   
			$('.alert').find('.charge').hide()
		}
    }
    $('.alertclose,.confirm').on('click',function(){
        $('.mask').fadeOut();
        $(this).parents('.alert').fadeOut();
    });
    //金额格式化
    window.toThousands = function(num) {
        return (num || 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
    }

    //活动未开始按钮弹框
    window.checkBtn = function(obj){
        if($(obj).hasClass('notStart')){
            showmessage("活动暂未开始");
            return true;
        }else {
            return false
        }
    }
})




$(function(){
    // 游戏详细说明弹框
    $('.more').on('click',function(){
        var num = $(this).data('num');
        $('.mask').fadeIn();
        $('.popbox').eq(num).fadeIn().css({'top': Number(document.body.scrollTop)});
    });

    $('.popclose, .confirmbtn .btn').on('click',function(){
        $('.mask').fadeOut();
        $(this).parents('.popbox').fadeOut();
    });
})

//活動時間查詢
$(function(){
	$.ajax({
        url:'/activity/newyeargroupdate',
        dataType: 'JSON',
        cache: false,
        type:'post',
		data: {token:$("#token").val()},
		async:false,
        success:function(data){
				var status = data.activityCNYDateStatus;
				fastActivity = status[0];
				twoActivity = status[1];
				thirdActivity = status[2];
				vipActivity = status[3];

				if(fastActivity == 2){
					$('.btn.recharge').removeClass("notStart");
					
				}else if (fastActivity == 1){
					$('.btn.recharge').attr("href", "/fund");
				}
				if(thirdActivity == 2){
					$(".startRed").removeClass("notStart");
				} else if (thirdActivity == 1) {
					$(".startCharge").attr("href", "/fund");
				}
            }
    });
})




//活动一
$(function(){

    //活动一**************************
	if(fastActivity != 0){
		$('.btn.recharge').removeClass("notStart");
		//活动一数据初始化
		$.ajax({
			url:'/activity/newyearchargedoubleinit',
			dataType: 'JSON',
			cache: false,
			type:'post',
			success:function(data){
				if(data == null){
					return;
				}
				if(data.state == 1){           //表示获取数据成功
					var $dom = $('.act1');
					$dom.find('.timeStatues').addClass("status"+data.stage); //游戏状态
					$dom.find('.act1Money').html(toThousands(data.deposit)); //存款
					$dom.find('.act1Money').html(toThousands(data.deposit)); //流水需求金额
					$dom.find('.totalAmount').html(data.totalAmount); //流水需求金额
					$dom.find('.userAmount').html(data.userAmount); // 用户实际流水
					if(data.stage == 2){
						$dom.addClass("s2");
					}
					
					if(Number(data.userAmount)/ Number(data.totalAmount) >= 1) {
						$dom.find('.lineInside').addClass('completed');
						$dom.find('.lineInside').css({"width": "100%"});
						$dom.find('.userAmount').css({"left": "100%"});
					}else {
						if(data.totalAmount <= 0){
							$dom.find('.lineInside').css({"width": "0%"});
							$dom.find('.userAmount').css({"left": "0%"})
						}else{
							$dom.find('.lineInside').css({"width": Number(data.userAmount)/ Number(data.totalAmount)*100+"%"});
							$dom.find('.userAmount').css({"left": Number(data.userAmount)/ Number(data.totalAmount)*100+"%"})
						}
					}
					
					var percent=Number(data.userAmount)/ Number(data.totalAmount)*100;
					if(percent>100){
						percent=100;
					}
					
					
				}else{
					//showmessage(data.msg);  //如果获取失败请返回msg
				}
			}
		});
	}
	
	$('.btn.recharge').on('click',function(){
		if(checkBtn($(this))){
			return false;
		}else if (fastActivity == 2){			
		    showmessage("本次活动已经结束，请多多关注我们的其他活动。");
			return true;
		}
	});
	
    $('.btn.getmoney').on('click',function(){
       // if(checkBtn($(this))) return false;
		
		
		$.ajax({
        url:'/activity/newyearchargedoublegetaward',
        dataType: 'JSON',
        cache: false,
        type:'post',
        success:function(data){
            if(data.state == 1){           //表示获取数据成功
                var $dom = $('.act1');
                $dom.find('.timeStatues').addClass("status"+data.stage); //游戏状态
                $dom.find('.act1Money').html(toThousands(data.deposit)); //存款
                $dom.find('.act1Money').html(toThousands(data.deposit)); //流水需求金额
                $dom.find('.totalAmount').html(data.totalAmount); //流水需求金额
                $dom.find('.userAmount').html(data.userAmount); // 用户实际流水
				if(data.totalAmount <= 0){
					$dom.find('.lineInside').css({"width": "0%"});
				}else{
					$dom.find('.lineInside').css({"width": Number(data.userAmount)/ Number(data.totalAmount)*100+"%"});
				}
                if(Number(data.userAmount)/ Number(data.totalAmount) >= 1) {
                    $dom.find('.lineInside').addClass('completed')
                }
				var percent=Number(data.userAmount)/ Number(data.totalAmount)*100;
				if(percent>100){
					percent=100;
				}
                $dom.find('.userAmount').css({"left": percent+"%"})
				showmessage(data.msg);  //如果获取成功返回msg
            }else{
                showmessage(data.msg);  //如果获取失败请返回msg
            }
        }
    });
		

    });

	//中奖名单滚动条
	function startMarquee(){
		var $ul = $('.winnerList ul'),
			$li = $ul.find('li'),
			length = $li.length,
			num = 0;
		if($li.length>6){
			$ul.append($li.clone());
			setInterval(function(){
				num++;
				if(num > length) {
					num = 0;
					$ul.css({'marginTop': 0});
				}
				$ul.animate({'marginTop': -$li.height()*num},1000);
			},3000)
		}
	}

    //活动二**************************
	if(twoActivity != 0){
		//倒计时
		function redCount(obj) {
			this.timer = null;
			this.$dom = $('#redCount');
			this.init(obj);
		}
		redCount.prototype.init = function(obj) {
			$.extend(this, obj);

			this.remainObj = this.remain();
			clearTimeout(this.timer);
			this.cutdown();
			//显示倒计时
			if(this.remainObj) {
			}
		}
		redCount.prototype.remain = function() {
			var now = Date.now();
			var timeObj = null;
			var me = this;
			this.LotteryTime.forEach(function(week, i) {
				var result = week.forEach(function(time, index) {
					var gap = new Date(time).getTime() - now;
					if (gap > 0 && gap < 60 * 60 * 1000) {
						timeObj = {
							gap: gap,
							week: i,
							point: index
						};
						return ;
					}else {
						me.$dom.html('60:00')
					}
				});
			});
			return timeObj;
		}
		redCount.prototype.cutdown = function() {
			if (!this.remainObj) {
				this.setTime();
				this.remainObj = this.remain();
			} else {
				var timeString = this.LotteryTime[this.remainObj.week][this.remainObj.point];
				var gap = new Date(timeString).getTime() - Date.now();
				if (gap < 1) {
					this.remainObj = this.remain();
					//移除
					$('.startMove').removeClass('notStart');
				} else {
					this.setTime(gap);
				}
			}
			var self = this;
			timer = setTimeout(function() {
				self.cutdown();
			}, 1000);
		}
		redCount.prototype.setTime = function(time) {
			var $dom = this.$dom;
			if (!time) {
			} else {
				time = time / 1000 | 0;
				var minute = Math.floor(time / 60);
				var seconds = time % 60;
				minute = minute < 10 ? '0' + minute : minute;
				seconds = seconds < 10 ? '0' + seconds : seconds;
			}
			$dom.html(minute+":"+seconds);
		}
		
		

		/*new redCount({
			'LotteryTime':[
				['2017-01-05 15:48', '2017-01-05 16:48', '2017-01-17 20:05', '2017-01-17 21:00'],
				['2017-01-18 18:00', '2017-01-18 19:00', '2017-01-18 20:00', '2017-01-18 21:00'],
				['2017-01-19 18:00', '2017-01-19 19:00', '2017-01-19 20:00', '2017-01-19 21:00'],
				['2017-01-20 18:00', '2017-01-20 19:00', '2017-01-20 20:00', '2017-01-20 21:00']
			]
		});*/
		
		$.ajax({
		url:'/activity/newyearlotteryinit',
		dataType: 'JSON',
		cache: false,
		type:'post',
		success:function(data){
				if(data == null){
					return;
				}
				if(data.status == 1){           //表示获取数据成功
					
					//中獎名單
					if(data.winnerList != null){
						var winnerContent = "<ul>";
						for(var i =0 ;i<data.winnerList.length;i++){
							winnerContent += "<li>"+data.winnerList[i]+"</li>";
						}
						winnerContent += "</ul>";
						
						$(".winnerList").html(winnerContent);
						startMarquee();
					}
					
					//抽獎次數
					$(".prizeTimes").html(data.lotteryTotal);
					
					//中獎清單
					if(data.prizeTotalList != null){
						var $dom = $('.act2');
						$.each(data.prizeTotalList,function(k,v){
							var $span = $dom.find('.prizeNum span').eq(Number(k));
							$span.html(Number(v));
						});
					}
					
					//倒數計時
					new redCount({
						'LotteryTime':[
							data.lotteryTimes
						]
					});
				}else{
					showmessage(data.message);  //如果获取失败请返回msg
				}
			}
		});
	}
	

    //抽取奖品
    var prizeMap = {
		"0": 0,
        "1": 4,
        "2": 6,
        "3": 9,
        "4": 7,
        "5": 10,
        "6": 8,
        "7": 2,
        "8": 3,
        "9": 5,
        "10": 1
    }
	var elem = document.createElement('textarea');
    $('.startMove').on('click',function(){
        if(checkBtn($(this))) return;
        $.ajax({
            url:'/activity/newyearlotterylottery',
            dataType: 'JSON',
            cache: false,
            type:'post',
            success:function(data){
                if(data.status == 1){           //表示获取数据成功
                    var $dom = $('.act2');
                    $dom.find('.picMove').css({"width": 1892*7,"left":0});
                    $dom.find('.picMove').animate({"left":-1892*5-172*(prizeMap[data.prize]-1)},4000,"swing",function(){
                        var $span = $dom.find('.prizeNum span').eq(Number(data.prize)),
                            $tims = $dom.find('.prizeTimes');
                        $span.html(Number($span.html())+1).addClass('przieAni');
                        $tims.html(Number(data.lotteryTotal));

						//提示中獎訊息
						elem.innerHTML = data.message;
						var decoded = elem.value;
						showmessage(decoded);
						//中獎名單
						if(data.winnerList != null){
							var winnerContent = "<ul>";
							for(var i =0 ;i<data.winnerList.length;i++){
								winnerContent += "<li>"+data.winnerList[i]+"</li>";
							}
							winnerContent += "</ul>";
							
							$(".winnerList").html(winnerContent);
							startMarquee();
						}
                    }); //展示抽奖动画
                }else{
					//提示中獎訊息
					elem.innerHTML = data.message;
					var decoded = elem.value;
                    showmessage(decoded);  //如果获取失败请返回msg
                }
            }
        });
    });

    //活动三**************************
	
	//活動初始化
	if(thirdActivity != 0){
		$('.startRed').removeClass("notStart");
		$('.startCharge').removeClass("notStart");
		initThirdActive();
	}
	
	function initThirdActive(){
		$.ajax({
            url:'/activity/newyearchargegiveinit',
            dataType: 'JSON',
            cache: false,
            type:'post',
			async:false,
            success:function(data){
				if(data == null){
					return;
				}
                if(data.status == 1){           //表示获取数据成功
                    $(".act3p1 .obtain").html(data.todayRedBow);
					$(".obtainLeft").html(data.todayNowRedBow);
					$("#chargeSn").val(data.sn);
                }
            }
        });
	}
		
    //抽取红包
    $('.startRed').on('click',function(){
		
        $.ajax({
            url:'/activity/newyearchargegiveredbowaward',
            dataType: 'JSON',
            cache: false,
            type:'post',
			data: {sn:$("#chargeSn").val()},
			async:false,
            success:function(data){
				$(".act3p1 .obtain").html(data.todayRedBow);
				$(".obtainLeft").html(data.todayNowRedBow);
				var sn = $("#chargeSn").val(data.sn);
                if(data.status > 0){           //表示获取数据成功
                    showmessage("恭喜您，获得了"+data.amount+"元");
                }else{
					//showmessage(data.msg);
					if(data.status == -6){
						showmessage(data.msg, true);
					} else {
						showmessage(data.msg);  //如果获取失败请返回msg
					}
                }
            }
        });
    });


    //活动四**************************

    //活动4数据初始化
    $.ajax({
        url:'/activity/newyearvipinit',
        dataType: 'JSON',
        cache: false,
        type:'post',
        success:function(data){
			if(data == null){
				return;
			}
            if(data.status == 1){           //表示获取数据成功
                var $dom = $('.act4');
                //渲染today图标
                if(data.today){
                    $dom.find('.bettingDay li').eq(Number(data.today)-1).addClass('today');
                }
                //渲染投注日
                $.each(data.date,function(n,val){
                    $dom.find('.bettingDay li').eq(Number(n)).addClass(val == 0?"":"betted");
                });

                //渲染红包状态
                var $red = $dom.find('.redpacketList .redpacket');

                
				if(data.redbowstatus[0] != 0) {
					$red.eq(0).removeClass('disable');
					
					//maybe onclick
					if(data.redbowstatus[0] == 2){
						var $result = $('<div class="result">恭喜获得<h2>'+data.redbowprize[0] +'元</h2></div>');
						$red.eq(0).html($result).find('.result').show();
					}
				}
				for(var i=1;i<data.redbowstatus.length;i++){
					if(data.redbowstatus[i] != 0) {
						$red.eq(Number(i)).removeClass('disable');
						if(data.redbowstatus[i] == 2){
							var $result = $('<div class="result">恭喜获得<h2>'+data.redbowprize[i] +'元</h2></div>');
							$red.eq(Number(i)).html($result).find('.result').show();
						}
						//maybe onclick
						//var $result = $('<div class="result">恭喜获得<h2>'+data.redPacket.newyear.result +'</h2></div>');
						//$red.eq(Number(i)+1).html($result).find('.result').show();
						
					}
				}
				
                
            }else{
                //showmessage(data.msg);  //如果获取失败请返回msg
            }
        }
    });


    //抽取红包除夕紅包
    $('#yearEvePacket').on('click',function(){
        var $this = $(this)
        $.ajax({
            url:'/activity/newyearvipredbowsave',
            dataType: 'JSON',
            cache: false,
            type:'post',
			data:{activityCode:"2017_CNY_4-1"},
            success:function(data){
                if(data.status == 1){           //表示获取数据成功
                    //var $result = $('<div class="result">恭喜获得<h2>'+data.result +'</h2></div>');
                    //$this.html($result);
                    //$result.fadeIn();
					if(data.msg != null){
						if(data.isopen ==0 ){
							showmessage(data.msg);
						}else{
							showmessage(data.msg);
							var $result = $('<div class="result">恭喜获得<h2>'+data.prize +'元</h2></div>');
							$("#yearEvePacket").html($result).find('.result').show();
							$result.fadeIn();
						}
						
					}
					
                }else{
                    //showmessage(data.msg);  //如果获取失败请返回msg
                }
            }
        });
    });
	
	//抽取红包新春紅包1
    $('#yearPacketLv1').on('click',function(){
        var $this = $(this)
        $.ajax({
            url:'/activity/newyearvipredbowsave',
            dataType: 'JSON',
            cache: false,
            type:'post',
			data:{activityCode:"2017_CNY_4-2",newyeartype:1},
            success:function(data){
                if(data.status == 1){           //表示获取数据成功
                    //var $result = $('<div class="result">恭喜获得<h2>'+data.result +'</h2></div>');
                    //$this.html($result);
                    //$result.fadeIn();
					if(data.msg != null){
						if(data.isopen ==0 ){
							showmessage(data.msg);
						}else{
							var $result = $('<div class="result">恭喜获得<h2>'+data.prize +'元</h2></div>');
							showmessage(data.msg);
							$("#yearPacketLv1").html($result).find('.result').show();
							$result.fadeIn();
						}
					}
                }else{
                    //showmessage(data.msg);  //如果获取失败请返回msg
                }
            }
        });
    });
	
	//抽取红包新春紅包2
    $('#yearPacketLv2').on('click',function(){
        var $this = $(this)
        $.ajax({
            url:'/activity/newyearvipredbowsave',
            dataType: 'JSON',
            cache: false,
            type:'post',
			data:{activityCode:"2017_CNY_4-2",newyeartype:2},
            success:function(data){
                if(data.status == 1){           //表示获取数据成功
                    //var $result = $('<div class="result">恭喜获得<h2>'+data.result +'</h2></div>');
                    //$this.html($result);
                    //$result.fadeIn();
					if(data.msg != null){
						if(data.isopen ==0 ){
							showmessage(data.msg);
						}else{
							var $result = $('<div class="result">恭喜获得<h2>'+data.prize +'元</h2></div>');
							showmessage(data.msg);
							$("#yearPacketLv2").html($result).find('.result').show();
							$result.fadeIn();
						}
					}
                }else{
                    //showmessage(data.msg);  //如果获取失败请返回msg
                }
            }
        });
    });
	
	//抽取红包新春紅包3
    $('#yearPacketLv3').on('click',function(){
        var $this = $(this)
        $.ajax({
            url:'/activity/newyearvipredbowsave',
            dataType: 'JSON',
            cache: false,
            type:'post',
			data:{activityCode:"2017_CNY_4-2",newyeartype:3},
            success:function(data){
                if(data.status == 1){           //表示获取数据成功
                    //var $result = $('<div class="result">恭喜获得<h2>'+data.result +'</h2></div>');
                    //$this.html($result);
                    //$result.fadeIn();
						if(data.msg != null){
							if(data.isopen ==0 ){
								showmessage(data.msg);
							}else{
								var $result = $('<div class="result">恭喜获得<h2>'+data.prize +'元</h2></div>');
								showmessage(data.msg);
								$("#yearPacketLv3").html($result).find('.result').show();
								$result.fadeIn();
							}
						}
                }else{
                    //showmessage(data.msg);  //如果获取失败请返回msg
                }
            }
        });
    });
	
	
	//抽取红包財神紅包4
    $('#yearPacketLv4').on('click',function(){
        var $this = $(this)
        $.ajax({
            url:'/activity/newyearvipredbowsave',
            dataType: 'JSON',
            cache: false,
            type:'post',
			data:{activityCode:"2017_CNY_4-3"},
            success:function(data){
                if(data.status == 1){           //表示获取数据成功
                    //var $result = $('<div class="result">恭喜获得<h2>'+data.result +'</h2></div>');
                    //$this.html($result);
                    //$result.fadeIn();
					if(data.msg != null){
						showmessage(data.msg);
					}
                }else{
                    //showmessage(data.msg);  //如果获取失败请返回msg
                }
            }
        });
    });
});
