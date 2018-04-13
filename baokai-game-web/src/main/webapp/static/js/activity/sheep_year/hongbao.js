$(function() {
	var saveTime = null;
	var currentTimeSave = null;
	var rewards = function() {

		this.panel = $('#J-reward-panel');
		this.init();
	};
	rewards.prototype = {

		//初始化
		init: function() {
			var me = this;

			me.cache = {
				htmlCache: {},
				dom: {}
			};
		},

		//获取数据
		getData: function(defer) {
			var me = this;

			$.ajax({
					url: '/sheepYear/rewards',
					type: 'GET',
					cache:false,
					dataType: 'json'
				})
				.done(function(res) {
					//读取成功
						me.renderDom(res);
				})
				.fail(function() {
					alert('红包丢了～请刷新页面～');
				})
				.always(function() {

				});
		},

		//渲染红包
		renderDom: function(res) {
			var me = this,
				html = '',
				warp, current, step,
				data = res['data']['rewards'];

			me['panel'].find('.redpacket').remove();

			for (var i = 0; i < data.length; i++) {
				me.bulidDom(data[i]['step'], i, res);
			};
		},

		bulidDom: function(step, currentNum, res) {
			var me = this,
				warp;

			warp = $('<div class="redpacket"></div>');
			warp.addClass('rp-step-' + step);
			warp.css('bottom', -470);
			warp.attr('id', 'redpacket' + (currentNum + 1));
			warp.html(me.getHtml(step));
			me['cache']['dom'][currentNum] = warp;
			me['panel'].append(warp);

			setTimeout(function() {
				warp.animate({
					'bottom': -57},
					500, function() {
					/* stuff to do after animation is complete */
				})
				//warp.css('bottom', -57);
			}, 500 * currentNum * 0.9);

			//已经完成红包任务
			if (step >= 7) {
				me.changeRedPaketStatus(currentNum);
			}

			//执行动作
			me['action' + step] && me['action' + step](warp, step, currentNum, res);
		},

		action2: function(warp, step, currentNum) {
			var $contentDom,
				me = this,
				num = 0,
				dom = $('<div id="redpacket' + (currentNum + 1) + '" class="redpacket"></div>'),
				button = warp.find('.rp-button');
			warp.html(me.getHtml(2)).removeClass('rp-step-1').addClass('rp-step-2');
			$contentDom = warp.find('.rp-content');
			$contentDom.css('opacity', 0);
			setTimeout(function() {
				$contentDom.css('opacity', 1);
			}, 100);
			warp.find('.rp-button').click(function() {
				$.get('/sheepYear/rewards', {
					'model': 2,
					'stamp': Math.random()
				}, function(res) {
					//读取成功
					if (Number(res['status']) === 0) {
						num = 4;
						dom.addClass('rp-step-' + num);
						dom.html(me.getHtml(num));
						warp.replaceWith(dom);
						me['action' + num](dom, num, currentNum, res);
					}
				}, 'json');
			});
		},

		tipsShow: function(dom, type) {
			var me = this,
				count = 6,
				tipsDom = dom.find('.tips-limit');

			if (me.saveTime || type) {
				clearInterval(me.saveTime);
				me.saveTime = null;
			}

			if (type) {
				return;
			}

			me.saveTime = setTimeout(function() {
				me.animateTips = setInterval(function() {

					if (count == 0) {
						clearInterval(me.animateTips);
						me.animateTips = null;
					}

					if (count % 2 == 0) {
						tipsDom.css('visibility', 'visible');
					} else {
						tipsDom.css('visibility', 'hidden');
					}

					count--;
				}, 100);
			}, 500);
		},

		action4: function(dom, step, i, res) {
			var me = this,
				lock = false,
				amountNum = 0,
				$dialog = $('#J-messages-timeout'),
				moneyDom = dom.find('.money-amount'),
				errorDom = dom.find('.input-error'),
				minNum = Number(res['data']['minNum']),
				maxNum = Number(res['data']['maxNum']),
				input = dom.find('.amount');

			setTimeout(function() {
				dom.addClass('animated').removeClass('rp-step-2');
			}, 0);
			moneyDom.text(me.formatNum(minNum) + '-' + me.formatNum(maxNum));
			dom.on('input propertychange', '.amount', function(event) {
				$(this).val(this.value.replace(/\D/g, ''));
				var val = Number(this.value);
				if (val > maxNum || val < minNum) {
					lock = false;
					me.tipsShow(dom);
				} else {
					lock = true;
					amountNum = val;
					me.tipsShow(dom, 'stop');
				}
			});

			dom.on('click', '.submit-amount', function(event) {
				var val,
					$button = $('#J-messages-ensure');

				if (lock) {

					showModal($('#J-messages-ensure'));
					showModal($overlayShadow);

					$button.find('.cancel').one('click', function() {
						hideModal($('#J-messages-ensure'));
						hideModal($overlayShadow);
					});

					$button.find('.enter').one('click', function() {
						hideModal($('#J-messages-ensure'));
						hideModal($overlayShadow);

						$.get('/sheepYear/rewards', {
							'index':i+1,
							'model': 3,
							'stamp': Math.random(),
							'amount':$.trim($('.amount').val())
						}, function(res) {
							//读取成功
							if (Number(res['status']) === 0) {
								me.action5(dom, step, i, res);
							} else if (Number(res['status']) === 1 && Number(res['typeId']) === 1) {
								showModal($dialog);
								showModal($overlayShadow);
							}
						}, 'json');
					});

				} else {
					dom.find('.amount').select();
					me.tipsShow(dom);
				}
			});
		},

		action3: function(dom, step, i, res) {
			var me = this,
				$contentDom,
				data = res['data'],
				index = data['index'];

			dom.html(me.getHtml(3, data)).removeClass('.rp-step-8').addClass('rp-step-7');
			$contentDom = dom.find('.rp-content');
			$contentDom.css('opacity', 0);
			setTimeout(function() {
				dom.addClass('animated');
				$contentDom.css('opacity', 1);
			}, 100);
		},

		action5: function(dom, step, i, res) {
			var me = this,
				$contentDom,
				$dialog = $('#J-messages-giveup'),
				data = res['data'],
				process = (Number(data['lastBet'])/10000) / (Number(data['expected']) / 100),
				deadline = data['deadline'],
				nowtime = data['nowTime'];

			data['rewardsNum'] = me.formatNum(data['rewardsNum']);
			data['lastBet'] = me.formatNum(Number(data['lastBet'])/10000);
			data['expected'] = me.formatNum(data['expected']);
			dom.html(me.getHtml(5, data)).remove('rp-step-4').addClass('rp-step-5');
			$contentDom = dom.find('.rp-content');
			$contentDom.css('opacity', 0);
			setTimeout(function() {
				$contentDom.css('opacity', 1);
			}, 100);
			
			//如果截止时间晚于当前时间处理如下
//			if(deadline > nowtime){	
			
				_fresh(nowtime, deadline, function() {
					window.location.reload();
				});

				saveTime = setInterval(function() {
					_fresh(nowtime, deadline, function() {
						window.location.reload();
					});
				}, 1000);

				setTimeout(function() {
					dom.find('.rp-progress').css('width', process + '%');
				}, 0);

				dom.on('click', '.give-up', function(event) {
					//显示中奖结果
					showModal($dialog);
					//显示遮罩层
					showModal($overlayShadow);
				});

				$dialog.find('.cancel').one('click', function() {
					hideModal($dialog);
					hideModal($overlayShadow);
				});

				$dialog.find('.enter').one('click', function() {
					$.get('/sheepYear/rewards', {
						'model': 4,
						'stamp': Math.random(),
						'index': i + 1
					}, function(res) {
						var data = res['data'],
							index = data['index'];

						//读取成功
						if (Number(res['status']) === 0) {
							if (index < data['total']) {
								me.action2(me['cache']['dom'][index], 2, index);
								clearInterval(saveTime);
								saveTime = null;
							};
							me.changeRedPaketStatus(i);
							me.action3(dom, step, i, res);
							hideModal($dialog);
							hideModal($overlayShadow);
						}
					}, 'json');
				});
//			}
		},

		action6: function(dom, step, i, res) {
			var me = this,
				data = res['data'],
				index = data['index'];
			data['rewardsNum'] = me.formatNum(data['rewardsNum']);
			dom.html(me.getHtml(6, data));
			dom.on('click', '.rp-button', function(event) {
				$.get('/sheepYear/rewards', {
					'index':i+1,
					'stamp': Math.random(),
					'model': 5
				}, function(res) {
					me.action7(dom, step, i, res);
					me.action2(me['cache']['dom'][index], 2, index);
					me.changeRedPaketStatus(i);
				}, 'json');
			});
		},

		action7: function(dom, step, i, res) {
			var $contentDom,
				me = this,
				data = res['data'],
				index = data['index'];

			dom.html(me.getHtml(7, data)).removeClass('rp-step-6').addClass('rp-step-7');
			$contentDom = dom.find('.rp-content');
			$contentDom.css('opacity', 0);
			setTimeout(function() {
				$contentDom.css('opacity', 1);
			}, 100);
		},

		action8: function(dom, step, i, res) {
			var $contentDom,
				me = this,
				data = res['data'],
				index = data['index'];

			dom.html(me.getHtml(7, data)).removeClass('rp-step-6').addClass('rp-step-7');
			$contentDom = dom.find('.rp-content');
			$contentDom.css('opacity', 0);
			setTimeout(function() {
				$contentDom.css('opacity', 1);
			}, 100);
		},
		
		action9: function(dom, step, i, res) {
			var $contentDom,
				me = this,
				data = res['data'],
				index = data['index'];

			dom.html(me.getHtml(7, data)).removeClass('rp-step-6').addClass('rp-step-7');
			$contentDom = dom.find('.rp-content');
			$contentDom.css('opacity', 0);
			setTimeout(function() {
				$contentDom.css('opacity', 1);
			}, 100);
		},

		changeRedPaketStatus: function(num) {
			var me = this,
				num = num || 0,
				dom = $('#J-red-paket').find('li');

			dom.eq(num).addClass('over');
		},

		formatNum: function(num) {
			var num = Number(num),
				re = /(-?\d+)(\d{3})/;

			if (Number.prototype.toFixed) {
				num = (num).toFixed(2);
			} else {
				num = Math.round(num * 100) / 100
			}
			num = '' + num;
			while (re.test(num)) {
				num = num.replace(re, "$1,$2");
			}
			return num;
		},

		//获取数据
		getHtml: function(step, data) {
			var me = this,
				originalHtml = me['cache']['htmlCache'][step] ? me['cache']['htmlCache'][step] : me['cache']['htmlCache'][step] = $('#J-step-' + step).val();

			if (data) {
				for (var name in data) {
					originalHtml = originalHtml.replace('{{' + name + '}}', data[name]);
				}
			};

			return originalHtml;
		}
	};

	//倒计时
	//当前时间, 结束时间, 回调函数
	function _fresh(currentTime, endTime, callback) {
		var endtime = endTime || new Date("2011/11/06,12:20:12").getTime();
		var nowtime = currentTime || new Date();
		var leftsecond = currentTimeSave != null ? currentTimeSave : parseInt((endtime - nowtime) / 1000);

		__d = parseInt(leftsecond / 3600 / 24);
		__h = parseInt((leftsecond / 3600) % 24);
		__m = parseInt((leftsecond / 60) % 60);
		__s = parseInt(leftsecond % 60);

		//document.getElementById("times").innerHTML=__d+"天 "+__h+"小时"+__m+"分"+__s+"秒";  
		$('.rp-count-down').html('<span>' + __d + "</span> 天 <span>" + __h + "</span> 小时 <span>" + __m + "</span> 分 <span>" + __s + "</span> 秒");

		//剩余时间
		if (leftsecond <= 0) {

			callback && callback();
			clearInterval(saveTime);
			saveTime = null;
		}

		currentTimeSave = currentTimeSave != null ? currentTimeSave - 1 : leftsecond - 1;
	};


	var app = new rewards();

	app.getData();
});