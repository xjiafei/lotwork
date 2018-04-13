$(function(){
	function fadeHover(trigger,panel,delay){
		var timer = null;
		$(trigger).on('mouseenter',function(){
			$(panel).fadeIn('fast');
			clearTimeout(timer);
			time = null;
		});
		$(trigger).on('mouseleave',function(){
			timer = setTimeout(function(){
				$(panel).fadeOut('fast');
			},delay)
		});
	}

	//顶部用户中心
	fadeHover($('#J-user-center'), $('#J-user-center .menu-nav'),300);
	//顶部用户消息
	fadeHover($('#J-top-user-message'), $('#J-top-user-message .msg-box'),300);
	// 彩种链接
	fadeHover($('#J-btn-lottery'), $('#J-btn-lottery .lottery-link'),300);
	//下载中心
	fadeHover($('#J-btn-download'), $('#J-btn-download .app-download'),300);

	// 顶部用户中心
	//new phoenix.Hover({
	//	triggers      :'#J-user-center',
	//	panels        :'#J-user-center .menu-nav',
	//	hoverDelayOut :300
	//});
	// 顶部用户消息
	//new phoenix.Hover({
	//	triggers      :'#J-top-user-message',
	//	panels        :'#J-top-user-message .msg-box',
	//	hoverDelayOut :300
	//});
	// 彩种链接
	//new phoenix.Hover({
	//	triggers      :'#J-btn-lottery',
	//	panels        :'#J-btn-lottery .lottery-link',
	//	hoverDelayOut :300
	//});
	// 彩种链接
	//new phoenix.Hover({
	//	triggers      :'#J-btn-download',
	//	panels        :'#J-btn-download .app-download',
	//	hoverDelayOut :300
	//});

	// 游戏记录tab切换
	new phoenix.Tab({
		par           : '#news-tab',
		triggers      : '.news-list-hd li',
		panels        : '.news-tab-con .news-list-bd',
		currClass     : 'active',
		currPanelClass: 'active'
	});
	// 游戏记录tab切换
	new phoenix.Tab({
		par           : '#menu-main',
		triggers      : '.menu-title li',
		panels        : '.menu-con .menu-type',
		currClass     : 'active',
		currPanelClass: 'active'
	});

	//读取，修改余额可见状态值
	var cookieAllball = $.cookie("showAllBall");
	if (cookieAllball == "1") {
		$('#hiddBall').css("display", "inline");
		$('#hiddenBall').css("display", "none");
	} else {
		$('#hiddBall').css("display", "none");
		$('#hiddenBall').css("display", "inline");
	}
	//显示余额
	$('#showAllBall').click(function() {
		$.cookie("showAllBall", "1", {
			expires: 7,
			path: '/'
		});
		$('#hiddBall').css("display", "inline");
		$('#hiddenBall').css("display", "none");
	});
	//隐藏余额
	$('#hiddBall').click(function() {
		$.cookie("showAllBall", "0", {
			expires: 7,
			path: '/'
		});
		$('#hiddBall').css("display", "none");
		$('#hiddenBall').css("display", "inline");
	});
	//金额刷新
	$('.refreshBall').click(function(event) {
		var spanBalls = $('#spanBall');
		var _this = this;
		try {
			//用户余额接口
			$.ajax({
				type: 'post',
				dataType: 'json',
				cache: false,
				url: '/index/getuserbal',
				data: '',
				beforeSend: function() {
					spanBalls.css('font-size', '11px').html('查询中...');
					$(_this).hide();
				},
				success: function(data) {
					if (data['status'] == "ok") {
						spanBalls.removeAttr('style').text(data["data"]);
						$(_this).show();
					}
				},
				complete: function() {
					$(_this).show();
				}
			});
		} catch(err) {
			console.log("网络异常，读取信件信息失败");
		}
		event.stopPropagation();
	});
	//余额刷新
	$('.amount-refresh').click(function() {
		var spanBalls = $('[name="spanBall"]'),
			userBalanceTitle = $('.amount-refresh');
		try {
			//用户余额接口
			$.ajax({
				type: 'post',
				dataType: 'json',
				cache: false,
				url: '/index/getuserbal',
				data: '',
				beforeSend: function() {
					userBalanceTitle.addClass('user-balance-refresh');
					spanBalls.css('text-align', 'center').html('查询中...');
				},
				success: function(data) {
					if (data['status'] == "ok") {
						setTimeout(function() {
								spanBalls.text(data["data"]);
							},
							500);

					}
				},
				complete: function() {
					setTimeout(function() {
							userBalanceTitle.removeClass('user-balance-refresh');
						},
						500);
				}
			});
		} catch(err) {

		}
	});
	//安全等级进度条
	$('.progress-wapper').addClass('progress-high');
	setTimeout(function() {
		$('.progress-wapper').removeClass('progress-high');
	},2000);


	//焦点图绑定更新事件
	$(document).on('cycle-update-view', '#focus', function(event, opts){
		var $dom = $(this);

		if(Number(opts['slideCount']) <= 1){
			$dom.find('.prev,.next,.cycle-pager-wrap').hide();
		}else{
			$dom.find('.prev,.next,.cycle-pager-wrap').show();
		}
	});
	var pressSlideshow = $('#focus').cycle();
	$('#focus').cycle('add','<div class="item"><a href="/index"><img src="images/common/slideshow1.jpg" alt=""></a></div>');
	$('#focus').cycle('add','<div class="item"><a href="/index"><img src="images/common/slideshow1.jpg" alt=""></a></div>');



	// 联系上级
	var $dialog = $('#J-dialog-qq');
	var $button = $('#J-button-qq');
	var $dialogClose = $('#J-dialogqq-close');
	var $sliderBarclose = $('#sliderBar_close');

	$button.click(function() {
		if ($dialog.is(':hidden')) {
			$dialog.show();
		} else {
			$dialog.hide();
		}
	});
	$sliderBarclose.click(function() {
		$('#sliderBar').hide();
	});
	$dialogClose.click(function() {
		$dialog.hide();
	});
	var win = $(window),
		float = $('#sliderBar'),
		top = parseInt(float.css('top')),
		sTop = win.scrollTop(),
		_sTop = sTop;
	setInterval(function() {
			_sTop = win.scrollTop();
			if (sTop != _sTop) {
				float.stop().animate(0, 250);
				sTop = _sTop;
			}
		},
	50);

	//中奖列表滚动
	(function(){
		var $dom = $('#winningScroll');
		var height = $dom.height();
		var allheight = $dom.find('ul').height();
		var now = 0;
		setInterval(function(){
			now++;
			$dom.find('ul').animate({'marginTop': -height*now},1000);
			if(now >= allheight/height){
				$dom.find('ul').css({'marginTop': 0}).stop();
				now = 0;
			}
		},15000);
	})()



});
//重庆时时彩 开奖倒计时
$(function(){
	var saveTime = {};
	var topTimer = new phoenix.CountDown({
		//结束时间
		'endTime' : 1406685600000,
		//开始时间
		'startTime': new Date().getTime(),
		//是否需要循环计时
		'isLoop' : false,
		//需要渲染的DOM结构
		'showDom' : '.deadline-number',
		expands:{
			//覆盖showTime渲染方法
			_showTime:function(time){
				var me = this,
					dom = $(me.defConfig.showDom),
					h = me.checkNum(time.h) + '',
					m = me.checkNum(time.m) + '',
					s = me.checkNum(time.s) + '';
				//渲染时间输出
				dom.find('.hour-left').text(h.substring(0,1));
				dom.find('.hour-right').text(h.substring(1,2));
				dom.find('.min-left').text(m.substring(0,1));
				dom.find('.min-right').text(m.substring(1,2));
				dom.find('.sec-left').text(s.substring(0,1));
				dom.find('.sec-right').text(s.substring(1,2));
				me.fireEvent('afterShowTime', time, me);
			}
		}
	});
	topTimer.addEvent('afterTimeFinish', function(){
		//定时器结束，当前期结束
		//定时器结束，请把逻辑写在这里 定时器完成时自动执行

	});
});
$(function(){
	var lotteryLogo = $('#lotteryList a');
	var l = lotteryLogo.length;
	var index = 0;
	
	function autoRun(){
		lotteryLogo.eq(index).addClass('current').siblings().removeClass('current');
		index++;
		if(index == l){
			index = 0;
		}
		
	};
	
	var mask = $('<div class="mask"></div>').appendTo('body');
	var superBar = $('#superBar');
                       $.ajax({
		url:'/index/super2000',
		dataType:'json',
		cache: false,
		success:function(res){
			//用户每日首次登陆后，会出现活动弹窗
			//用户领取红包,投注过超级2000的玩法
			if(Number(res['state']) == 1 || Number(res['state']) == 2){
				mask.show();
				superBar.show();

			}
			$('#superBarBtn a').click(function(){
				$('#superBarBtn').hide();
				$('#superBarSecond').fadeIn();
				setInterval(autoRun,800);
				//用户当日领取了红包并且确实投注过超级2000的玩法，投注金额≥8元
				if(Number(res['state']) == 2){
					$('#winningInfo').html('恭喜您第二次获得<strong id="winningNum">' + res['prize'] + '</strong>元');
					$('#winningNum').text(res['prizeSecond']);
				}else{
					$('#winningInfo').html('恭喜您第一次获得<strong id="winningNum">' + res['prize'] + '</strong>元');
					$('#winningNum').text(res['prizeFirst']);
				}
                                                                                           //write prize & activity log
                                                                                           $.ajax({
                                                                                               url:'/index/award?prize',
                                                                                               method:"post",
                                                                                               data:{prize:res['prize']},
                                                                                               success:function(){
                                                                                               },
                                                                                               error:function(){
                                                                                               },
                                                                                     });                
			})
		}
	});
	$('#sliderBarClose').click(function(){
                                             mask.hide();
		superBar.hide();
	})
});