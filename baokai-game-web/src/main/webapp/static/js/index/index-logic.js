// 顶部导航需要的脚本
(function($) {
	//$('[name="Page_firefrog_index"]').length > 0 ? $('.back-top-home').hide() : $('.back-top-home').show();
	// 顶部用户信息
	new phoenix.Hover({
		triggers: '#J-top-userinfo',
		panels: '#J-top-userinfo .menu-nav',
		hoverDelayOut: 300
	});

	// 顶部彩种菜单
	new phoenix.Hover({
		triggers: '#J-top-game-menu',
		panels: '#J-top-game-menu .game-menu-panel',
		hoverDelayOut: 300,
		currClass: 'game-menu-current'
	});

	// 顶部站内消息
	new phoenix.Hover({
		triggers: '#J-top-user-message',
		panels: '#J-top-user-message .msg-box',
		hoverDelayOut: 300
	});

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
	try {
		//自动查询此用户未读信件
		$.ajax({
			type: 'post',
			dataType: 'json',
			cache: false,
			url: '/service2/queryunreadmessage',
			data: '',
			success: function(json) {
				if (json.unreadCnt != 0) {
					var html = "";
					$.each(json.receives,
					function(i) {
                                            html += "<a href=\"/Service2/sysmessages?id=" + json.receives[i].id + "&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">" +json.receives[i].sendAccount+"(未读消息"+json.unreadCnt+"笔)"+ "</a>";
					});
					$("#readmsg").html(html);
					$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));

				} else {
					$("#readmsg").html("暂未收到新消息");
					$('#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");
					$('#readmsg').attr("style", "text-align:center; color:black;");
				}
			}
		});
	} catch(err) {

}

	//金额刷新
	$('.refreshBall').click(function(event) {
		var spanBalls = $('#spanBall');
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
					$('.refreshBall').hide();
				},
				success: function(data) {
					if (data['status'] == "ok") {
						spanBalls.removeAttr('style').text(data["data"]);
						$('.refreshBall').show();
					}
				},
				complete: function() {
					$('.refreshBall').show();
				}
			});
		} catch(err) {
			console.log("网络异常，读取信件信息失败");
		}
		event.stopPropagation();
	});
        $('.refreshBall').click();
        
	$('.user-balance-title').click(function() {
		var spanBalls = $('[name="spanBall"]'),
		userBalanceTitle = $('.user-balance-title');
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
					$('[name=spanBall]').css('text-align', 'center').html('查询中...');
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
        $('.user-balance-title').click();

	//读取，修改余额可见状态值
	var cookieAllball = $.cookie("showAllBall");
	if (cookieAllball == "1") {
		$('#hiddBall').css("display", "inline");
		$('#hiddenBall').css("display", "none");
	} else {
		$('#hiddBall').css("display", "none");
		$('#hiddenBall').css("display", "inline");
	}
})(jQuery);

$(function() {
	var saveTime = {};
	var topTimer = new phoenix.CountDown({
		//结束时间
		'endTime': sEndTime,
		//开始时间
		'startTime': sNowTime,
		//是否需要循环计时
		'isLoop': false,
		//需要渲染的DOM结构
		'showDom': '.deadline-number',
		expands: {
			//覆盖showTime渲染方法
			_showTime: function(time) {
				var me = this,
				dom = $(me.defConfig.showDom),
				h = me.checkNum(time.h) + '',
				m = me.checkNum(time.m) + '',
				s = me.checkNum(time.s) + '';
				//渲染时间输出
				dom.find('.hour-left').text(h.substring(0, 1));
				dom.find('.hour-right').text(h.substring(1, 2));
				dom.find('.min-left').text(m.substring(0, 1));
				dom.find('.min-right').text(m.substring(1, 2));
				dom.find('.sec-left').text(s.substring(0, 1));
				dom.find('.sec-right').text(s.substring(1, 2));
				//console.log(time);
				me.fireEvent('afterShowTime', time, me);
			}
		}
	});

	topTimer.addEvent('afterTimeFinish',
	function() {
		//定时器结束，当前期结束
		//定时器结束，请把逻辑写在这里 定时器完成时自动执行
		$.ajax({
			url: '/index/getgametimedown',
			dataType: 'JSON',
			cache: false,
			success: function(data) {
				if (data['status'] == "ok") {
					var result = data['data']['result'];

					var nowstoptime = JSON.stringify(result['saleEndTime']),
					nowtime = JSON.stringify(data['data']['nowTime']),
					nowNumber = JSON.stringify(result['currentIssue']);
					$('#nowNumber').html(nowNumber);

					topTimer.setStartTime(nowtime);
					topTimer.setEndTime(nowstoptime);
					topTimer.continueCount();
				}

			}
		});

	});

	$('.header_tools .user_action').hover(function() {
		$(this).addClass('user_action_active').find('.action_menus, .action_shadow').slideDown(200).end().find('.caret').addClass('active_caret');
	},
	function() {
		$(this).removeClass('user_action_active').find('.action_menus, .action_shadow').slideUp(200).end().find('.caret').removeClass('active_caret');
	});
	// 游戏记录tab切换
	new phoenix.Tab({
		par: '#game_log',
		triggers: '.log_tabs li',
		panels: '.log_panels .panel',
		eventType: 'click',
		currClass: 'active',
		currPanelClass: 'active'
	});

	var showWinners = function() {
		$.ajax({
			url: '/index/getgameprizelist',
			dataType: 'JSON',
			cache: false,
			success: function(data) {
				if (data['status'] == "ok") {
					var result = data['data'];

					if (result['99112']) { //秒秒彩							
						pushDateMoth(result['99112']['wins'], '99112', '.mmc-info', 'leftone');
					}
					if (result['99106']) { //乐利彩								
						pushDateMoth(result['99106']['wins'], '99106', '.lelicai-animate', 'lefttwo');
					}
					if (result['99305']) { //乐利115
						pushDateMoth(result['99305']['wins'], '99305', '.n115-animate', 'leftthree');
					}
					if (result['99111']) { //吉利分分彩
						pushDateMoth(result['99111']['wins'], '99111', '.jili-animate', 'leftfour');
					}

				}

			}
		});
	}
	setInterval(showWinners, 120000);
	//setInterval(showWinners, 5000); 
	var pushDateMoth = function(arrObj, lottryid, divHtml, leftLocation) {
		var htmls = '';
		$(divHtml).html('');
		if (!arrObj) {
			return;
		}
		for (var i = 0; i < arrObj.length; i++) {

			htmls += "<li>" + arrObj[i].username + "&nbsp;&nbsp;中奖" + arrObj[i].prize + "元</li>";
		}
		$(divHtml).html(htmls);
		scrollAnimate(divHtml, 'li', leftLocation, 'top');
	}

	var scrollAnimate = function(dom, cycleDom, name, animateType, minNum) {
		var $parentDom = $(dom),
		$child = $parentDom.find(cycleDom),
		height = $child.eq(0).outerHeight(),
		minNum = minNum || 1,
		time = 3;

		if ($child.size() > minNum) {

			if (typeof saveTime[name] == 'undefined') {
				saveTime[name] = {};
			} else {
				clearInterval(saveTime[name]);
			}
			//循环滚动显示
			saveTime[name] = setInterval(function() {
				dom = $parentDom.find(cycleDom);

				if (animateType == 'top') {

					dom.eq(0).animate({
						marginTop: -height
					},
					1000,
					function() {

						$parentDom.append(dom.eq(0));
						dom.eq(0).css('marginTop', 0);
						dom.removeClass('current');
						dom.eq(2).addClass('current');
					})
				} else if (animateType == 'bottom') {
					dom = dom.eq(dom.length - 1);

					$parentDom.prepend(dom);

					dom.css('marginTop', -height);
					dom.eq(0).animate({
						marginTop: 0
					},
					1000)
				}
			},
			time * 1000)
		}
	}

	//左侧滚动一
	scrollAnimate('.mmc-info', 'li', 'leftone', 'top', 3);
	//左侧滚动一
	scrollAnimate('.lelicai-animate', 'li', 'lefttwo', 'top');
	//左侧滚动一
	scrollAnimate('.n115-animate', 'li', 'leftthree', 'top');
	//左侧滚动一
	scrollAnimate('.jili-animate', 'li', 'leftfour', 'top');

	var $dialog = $('#J-dialog-qq');
	var $button = $('#J-button-qq');
	var $dialogClose = $('#J-dialogqq-close');
	var $sliderBarclose = $('#sliderBar_close');

	/*		$(".close").click(function(){
			$(".dialog-qqlist").hide();
			$(".slider-bar").hide();
		});*/

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

	//焦点图绑定更新事件
	$(document).on('cycle-update-view', '#focus',
	function(event, opts) {
		var $dom = $(this);

		if (Number(opts['slideCount']) <= 1) {
			$dom.find('.prev,.next,.cycle-pager-wrap').hide();
		} else {
			$dom.find('.prev,.next,.cycle-pager-wrap').show();
		}
	});

	//用JS方法初始化滚动图
	var pressSlideshow = $('#focus').cycle();

	//处理广告数据
	var reBuildAd = function(tpl, ad, w, h) {
		var me = tpl;
		ad['link'] = reBuildLink(ad['id'], ad['link']);
		ad['src'] = ad['src'];
		ad['width'] = w;
		ad['height'] = h;
		return ad;
	}
	var reBuildLink = function(id, link) {
		if (link != null && link.indexOf("{phoenixURL}") != -1) {
			link = link.replace('{phoenixURL}', '');
			link = window.location.host.replace('www', 'http://em') + link;
		}
		return link == null ? "": link;
	}

	$.ajax({
		url: '/api/jsonp/getAdverts?k=index_pos_center,index_beginner_guide,index_down_app&r=' + Math.random(),
		cache: false,
		dataType: 'jsonp',
		jsonp: "callBack",
		success: function(data) {
			if (Number(data['isSuccess']) == 1) {
				var me = data,
				list = me.data,
				len = list.length,
				listLen, dom, width, height, tplSingle, html = '';
				if (len > 0) {
					for (var i = 0; i < len; i++) {
						if (list[i]['name'] == 'index_pos_center') {
							html = '';
							width = 760;
							height = 360;
							dom = $('#focus');
							tplSingle = '<div class="item"><a href="<#=link#>" target="_blank"><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a></div>'
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									for (var j = 0; j < listLen; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html = phoenix.util.template(tplSingle, ad);
										dom.cycle('add', html);
									}

								} else {
									dom.cycle('add', '<div class="item"><a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/slideshow1.jpg" alt=""></a></div>');
								}
							}
						} else if (list[i]['name'] == 'index_beginner_guide') {
							html = '';
							width = 220;
							height = 190;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									for (var j = 0; j < listLen; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
								} else {
									dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/bg-beginner-guide.jpg" alt=""></a>');
								}
							}
						} else if (list[i]['name'] == 'index_down_app') {
							html = '';
							width = 220;
							height = 270;
							tplSingle = '<a href="<#=link#>" target="_blank" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a>';
							dom = $('#J-globalad-' + list[i]['name']);
							if (dom.size() > 0) {
								listLen = list[i]['list'].length;
								if (listLen > 0) {
									dom.css({
										width: width
									});
									for (var j = 0; j < listLen; j++) {
										ad = reBuildAd(tplSingle, list[i]['list'][j], width, height);
										ad['index'] = (j + 1);
										html += phoenix.util.template(tplSingle, ad);
									}
									dom.append(html);
								} else {
									dom.append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/common/bg-download-app.jpg" alt=""></a>');
								}
							}
						}
					}
				}
			} else {
				$('#focus').cycle('add', '<div class="item"><a href="/index" target="_blank" ><img src="' + global_path_url + '/images/common/slideshow1.jpg" alt=""></a></div>');
				$('#J-globalad-index_beginner_guide').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-beginner-guide.jpg" alt=""></a>');
				$('#J-globalad-index_down_app').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-download-app.jpg" alt=""></a>');
			}
		},
		error: function(xhr, type) {
			$('#focus').cycle('add', '<div class="item"><a href="/index" target="_blank" ><img src="' + global_path_url + '/images/common/slideshow1.jpg" alt=""></a></div>');
			$('#J-globalad-index_beginner_guide').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-beginner-guide.jpg" alt=""></a>');
			$('#J-globalad-index_down_app').append('<a href="/index" target="_blank"><img src="' + global_path_url + '/images/index/bg-download-app.jpg" alt=""></a>');
		}
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
	// egg - start
	var eggPop = $('#eggPop');
	var eggBar = $('#eggBar');
	var eggClose = $('#eggClose');
	var oEgg = $('.egg-list li');
	var length = oEgg.length;
	var number = length;
	var mask = phoenix.Mask.getInstance();
	function eggClick(obj) {
		var _this = obj;
		oEgg.unbind('click');
		oEgg.unbind('mousemove');
		$.getJSON("/activity/dailybonus",
		function(res) {
			$(".hammer").addClass('hammer-current');
			setTimeout(function() {
				$(".hammer").removeClass('hammer-current');
			},
			100);
			if (res.msg == 0) {
				alert('您今天已经参与过砸蛋活动了！');
				window.location.reload(1);
				return false;
			}
			if ( - [1, ]) {
				var audio = new Audio('images/common/eggclick/egg.mp3'); //路径
				audio.play();
			}
			setTimeout(function() {
				_this.addClass('current'); //蛋碎效果
				$('.egg-tips').removeClass('animation');
				$('.egg-light').fadeIn(); //金花四溅
				$(".hammer").hide();
				if (res.msg == 1) {
					$("#result").html(res.prize);
				} else {
					//$("#result").html("很遗憾,您没能中奖!");
				}
				$('.egg-tip').fadeIn(200).animate({
					top: '150px'
				},
				300);
				$('.egg-btn').delay(400).fadeIn(200).animate({
					top: '280px'
				},
				300);
				setTimeout(function() {
					window.location.reload(1);
				},
				5000);
			},
			300);
		});
	}
	oEgg.click(function() {
		number--;
		if ($(this).hasClass("current")) {
			//alert("蛋都碎了，别砸了！刷新再来.");
			return false;
		}
		if (number >= length - 1) {
			eggClick($(this));
		} else {
			//alert("您的机会已经用完！请明天再来.");
			return false;
		}
	});
	oEgg.mousemove(function(e) {
		var l = $('.egg-pop').offset().left;
		var t = $('.egg-pop').offset().top;
		var x = e.pageX;
		var y = e.pageY;
		var w = $("#hammer").outerWidth();
		var h = $("#hammer").outerHeight();
		if (!oEgg.hasClass("current")) {
			$("#hammer").css({
				left: x - l + 10,
				top: y - t - h - 10
			});
		}
	});
	function popShow() {
		mask.show();
		eggPop.fadeIn();
		$('.egg-bar-tip').removeClass('animation');
	}
	function popHide() {
		mask.hide();
		eggPop.fadeOut();
		$('.egg-bar-tip').addClass('animation');
	}
	$('.egg-btn').click(function() {
		eggBar.fadeOut();
		popHide();
	});
	eggBar.click(function() {
		popShow();
	});
	eggClose.click(function() {
		popHide();
	});
	// egg -- end
});
var prostyle = $('.progress-inner').attr('style');
$('.progress-inner').attr('style', 'width:100%');
setTimeout(function() {
	$('.progress-inner').attr('style', prostyle);
},
2000);

var path = global_path_url + '/images/index/';
var param = {
	imgPath: path,
	ajaxUrl: '/index/endguide'
};