$(function() {
			//顶部用户中心
			new phoenix.Hover({
				triggers: '#J-user-center',
				panels: '#J-user-center .menu-nav',
				hoverDelayOut: 300
			});
			//顶部用户消息
			new phoenix.Hover({
				triggers: '#J-top-user-message',
				panels: '#J-top-user-message .msg-box',
				hoverDelayOut: 300
			});
			//彩种链接
			new phoenix.Hover({
				triggers: '#J-btn-lottery',
				panels: '#J-btn-lottery .lottery-link',
				hoverDelayOut: 300
			});
			//彩种链接
			new phoenix.Hover({
				triggers: '#J-btn-download',
				panels: '#J-btn-download .app-download',
				hoverDelayOut: 300
			});

			// 彩种链接
			new phoenix.Hover({
				triggers: '#J-btn-elegame',
				panels: '#J-btn-elegame .ele-game',
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
			try {
			//自动查询此用户未读信件
			$.ajax({
				type: 'post',
				dataType: 'json',
				cache: false,
				url: '/service/queryunreadmessage',
				data: '',
				success: function(json) {
					if (json.unreadCnt != 0) {
						var html = "";
						$.each(json.receives,
						function(i) {
							if (i == 4) {
								html += "<a href=\"/Service/sysmessages?id=" + json.receives[i].id + "&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">" + json.receives[i].title + "..." + "</a>";
							} else {
								html += "<a href=\"/Service/sysmessages?id=" + json.receives[i].id + "&msgTpye=uMsg&unread=1&pro=" + json.unreadCnt + "\">" + json.receives[i].title + "</a>";
							}
						});
						$("#readmsg").html(html);
						$("#msgcount,#msg-title,#noreadmsg,#msgcount2,#noreadmsg2").html(parseInt(json.unreadCnt));

					} else {
						$("#readmsg").html("暂未收到新消息");
						$('#msg-title,#noreadmsg,#msgcount2,#noreadmsg2').html("0");
						$('#readmsg').attr("style", "text-align:center; color:black;");
					}
				}
			});
			} catch(err) {

			}
		});