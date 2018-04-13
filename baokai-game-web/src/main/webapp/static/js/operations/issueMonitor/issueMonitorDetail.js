(function($){
	
	var Tip = phoenix.Tip.getInstance(),table = $('#J-table'),Wd = phoenix.Message.getInstance();
	
	$('#J-panel-container').on('mouseover', '.icon-question', function(){
		var dom = $(this),text = dom.parent().find('.ico-question-text').html();
		Tip.setText(text);
		Tip.show(dom.width(), dom.height()*-1-10, dom);
	}).on('mouseout', '.icon-question', function(){
		Tip.hide();
	});
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(1)').attr("class","current");
	
	var lotteryId = $('#lotteryId').val();
	var issueCode = $('#issueCode').val();
	
	table.on('click', '.ico-control', function(){
		var dom = $(this),cls1 = 'ico-unfold',cls2 = 'ico-fold',tr = dom.parent().parent().next();
		if(dom.hasClass(cls1)){
			dom.removeClass(cls1).addClass(cls2);
			tr.show();
		}else{
			dom.removeClass(cls2).addClass(cls1);
			tr.hide();
		}
	});
	
	
	$('#J-button-all').click(function(){
		var dom = $(this),text = $.trim(dom.text()),cls = 'open';
		if(dom.hasClass(cls)){
			dom.removeClass(cls);
			dom.html('展开<b class="btn-inner"></b>');
			table.find('.ico-control').each(function(){
				var el = $(this),tr = el.parent().parent().next();
				el.removeClass('ico-fold').addClass('ico-unfold');
				tr.hide();
			});
		}else{
			dom.addClass(cls);
			dom.html('收起<b class="btn-inner"></b>');
			table.find('.ico-control').each(function(){
				var el = $(this),tr = el.parent().parent().next();
				el.removeClass('ico-unfold').addClass('ico-fold');
				tr.show();
			});
		}

	});
	
	
	$('#reTry').click(function(){
			
		
		$.ajax({
			url:baseUrl + '/gameoa/reTry?issueCode=' + issueCode + '&lotteryid='+lotteryId,
			dataType:'json',
			success:function(data){
				if(data.status==0){ 
					alert("重做启动成功");
				}else{
					alert("重做启动失败");
				}
				
			}
		});
		
	
	});
	
	table.on('click', '.button-action', function(){
		var dom = $(this);
		var orderCode = $(this).attr('name');
		//未审核
		if(dom.hasClass('button-noaudit')){
			Wd.show({
				mask:true,
				content:$('#J-tpl-noaudit').text(),
				confirmIsShow:true,
				confirmText:'通过',
				confirmFun:function(){
					$.ajax({
						url:baseUrl + '/gameoa/auditOrder?orderCode=' + orderCode + '&status=1',
						dataType:'json',
						success:function(data){
							if(Number(data['status']) == 1){
								dom.parent().removeClass('button-noaudit').html('<span style="color:#999;">已通过</span>');
								Wd.hide();
							}else{
								Wd.show({
									mask:true,
									content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['msg'] +'</h4></div></div>',
									cancelIsShow:true,
									cancelText:'关闭',
									cancelFun:function(){
										Wd.hide();
									}
								});
							}
						}
					});
				},
				cancelIsShow:true,
				cancelText:'不通过',
				cancelFun:function(){
					
					Wd.show({
						mask:true,
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定该方案审核不通过吗？<br />审核不通过后，该方案将同时记为恶意方案。<br />奖金保持被冻结状态。</h4></div></div>',
						confirmIsShow:true,
						confirmText:'确定',
						confirmFun:function(){
							$.ajax({
								url:baseUrl + '/gameoa/auditOrder?orderCode=' + orderCode + '&status=2',
								dataType:'json',
								success:function(data){
									if(Number(data['status']) == 1){
										dom.parent().removeClass('button-noaudit').html('<span style="color:red;">未通过</span>');
										Wd.hide();
									}else{
										Wd.show({
											mask:true,
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['msg'] +'</h4></div></div>',
											cancelIsShow:true,
											cancelText:'关闭',
											cancelFun:function(){
												Wd.hide();
											}
										});
									}
								}
							});
						},
						cancelIsShow:true,
						cancelText:'取消',
						cancelFun:function(){
							Wd.hide();
						}
					});
					

				}
			});
		}
	});
	
	
	$('#J-button-passall').click(function(){
		var arr = [];
		table.find('.record-hidden-id').each(function(){
			arr.push(this.value);
		});
		if(arr.length < 1){
			alert('没有未通过审核的奖期');
			return;
		}
		
		Wd.show({
			mask:true,
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定要审核通过所有未审核的方案吗？</h4></div></div>',
			confirmIsShow:true,
			confirmText:'确定',
			confirmFun:function(){
				$.ajax({
//					url:'data.php?ids=' + arr.join(','),
					url:baseUrl + '/gameoa/auditIssue?lotteryId=' + lotteryId +'&issueCode=' + issueCode ,
					dataType:'json',
					success:function(data){
						if(Number(data['status 	']) == 1){
							location.href = location.href;
						}else{
							Wd.show({
								mask:true,
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['msg'] +'</h4></div></div>',
								cancelIsShow:true,
								cancelText:'关闭',
								cancelFun:function(){
									Wd.hide();
								}
							});
						}
					}
				});
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			}
		});
		
		
	});
	
	
	//输入官方实际开奖时间
	$('#J-button-addtime').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-addtime').text(),
			confirmIsShow:true,
			confirmText:'确定',
			confirmFun:function(){
				var date = $.trim($('#J-input-date').val()),
					text = $('#J-input-text').val();
				
				if(date == ''){
					alert('时间不能为空');
					$('#J-input-date').focus();
					return;
				}
				$.ajax({
					url:'data.php',
					method:'post',
					dataType:'json',
					data:{'time':date,'text':text},
					success:function(data){
						if(Number(data['isSuccess']) == 1){
							location.href = location.href;
						}else{
							Wd.show({
								mask:true,
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['msg'] +'</h4></div></div>',
								cancelIsShow:true,
								cancelText:'关闭',
								cancelFun:function(){
									Wd.hide();
								}
							});
						}
					}
				});
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				$('#J-input-date').focus(function(){
					(new phoenix.DatePicker({input:'#J-input-date',cls:'pop-window-datepicker',isShowTime:true})).show();
				});
				Wd.win.dom.css('marginTop', -100);
				$('#J-input-date').focus();
			}
		});
		
	});
	
	
	
	
	
	//输入官方实际开奖号码
	$('#J-button-addnumber').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-addnumber').text(),
			confirmIsShow:true,
			confirmText:'开始处理',
			confirmFun:function(){
				var number = $.trim($('#J-input-number1').val()),
					standard = $.trim($('#J-standard-number1').text()).replace(/\d/g, '0').replace(/[^\d]/g, ','),
					text = $('#J-input-memo1').val(),
					match = $.trim($('#J-input-number1').val()).replace(/,/g,'');
				
				if(number == ''){
					alert('开奖号码不能为空');
					$('#J-input-number1').focus();
					return;
				}
				//双色球开奖号码格式校验处理
				if(match.indexOf("+")>=0 && match.indexOf("+")==12){
					match=match.replace(/\+/g,'');
				}
				if(match.replace(/^[0-9]{3,40}$/,'')!=''){
					alert('开奖号码格式不正确\n请参照提示中的范例输入');
					$('#J-input-number1').focus();
					return;
				}
				
				var url = baseUrl+"/gameoa/modifyNumberRecord";
				var data = '{"lotteryid":'+lotteryId+',"issueCode":'+issueCode+',"numberRecord":'+'"'+number+'"'+',"disposeMemo":"'+text+'"}';
				sendUrl(url, data);
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			}
		});
		
	});
	
	
	
	//暂缓派奖
	$('#J-button-putoff').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-putoff').text(),
			confirmIsShow:true,
			confirmText:'开始处理',
			confirmFun:function(){
				var text = $('#J-input-memo2').val();
				
				var url = baseUrl+"/gameoa/pauseIssue";
				var data = '{"lotteryid":'+lotteryId+',"issueCode":'+issueCode+',"disposeMemo":"'+text+'"}';
				sendUrl(url, data);
			},
			cancelIsShow:true,
			cancelText:'取消',

			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				$('#J-input-memo2').focus();
			}
		});
		
	});
	
	
	
	//继续派奖
	$('#J-button-puton').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-puton').text(),
			confirmIsShow:true,
			confirmText:'开始处理',
			confirmFun:function(){
				var text = $('#J-input-memo3').val();
				
				var url = baseUrl+"/gameoa/continueIssue";
				var data = '{"lotteryid":'+lotteryId+',"issueCode":'+issueCode+',"disposeMemo":"'+text+'"}';
				sendUrl(url, data);
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				$('#J-input-memo3').focus();
			}
		});
		
	});
	
	
	
	//撤销本期方案
	$('#J-button-cancel').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-cancel').text(),
			confirmIsShow:true,
			confirmText:'开始处理',
			confirmFun:function(){
				var text = $('#J-input-memo4').val();
				
				var url = baseUrl+"/gameoa/cancelCurrentPackage";
				var data = '{"lotteryid":'+lotteryId+',"issueCode":'+issueCode+',"disposeMemo":"'+text+'"}';
				sendUrl(url, data);
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				$('#J-input-memo4').focus();
			}
		});
		
	});
	
	
	$('#J-button-export').click(function(){
		var canUpdate = $('#canUpdate').val();
		if(canUpdate!='false'){
			$('#J-downLoad-form').submit();
		}else{
			alert("未到下载时间，请于"+$('#saleEndTime').val()+"之后下载");
			return;
		}
	});
	
	//撤销后期追号
	$('#J-button-cancel-more').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-cancel-more').text(),
			confirmIsShow:true,
			confirmText:'开始处理',
			confirmFun:function(){
				var text = $('#J-input-memo5').val(),
					start = $('#J-input-number-start').val(),
					end = $('#J-input-number-end').val();
				if($.trim(start) == ''){
					alert('开始期号不能为空');
					$('#J-input-number-start').focus();
					return;
				}
				
				var url = baseUrl+"/gameoa/cancelFollowPlan";
				var data = '{"lotteryid":'+lotteryId+',"issueCode":'+issueCode+',"startIssueCode":"'+start+'","endIssueCode":"'+end+'","disposeMemo":"'+text+'"}';
				sendUrl(url, data);
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				$('#J-input-number-start').focus();
			}
		});
		
	});
	



	//输入开奖号码
	$('#J-button-addnumber-self').click(function(){
		Wd.show({
			mask:true,
			content:$('#J-tpl-addnumber-self').text(),
			confirmIsShow:true,
			confirmText:'开始处理',
			confirmFun:function(){
				var number = $.trim($('#J-input-number').val()),
					standard = $.trim($('#J-standard-number').text()).replace(/\d/g, '0').replace(/[^\d]/g, ','),
					text = $('#J-input-memo').val(),
					match = $.trim($('#J-input-number').val()).replace(/,/g,'');
				
				if(number == ''){
					alert('开奖号码不能为空');
					$('#J-input-number').focus();
					return;
				}
				//双色球开奖号码格式校验处理
				if(match.indexOf("+")>=0 && match.indexOf("+")==12){
					match=match.replace(/\+/g,'');
				}
				if(match.replace(/^[0-9]{3,40}$/,'')!=''){
					alert('开奖号码格式不正确\n请参照提示中的范例输入');
					$('#J-input-number').focus();
					return;
				}
				
				var url = baseUrl+"/gameoa/addNumberRecord";
				var data = '{"lotteryid":'+lotteryId+',"issueCode":'+issueCode+',"numberRecord":'+'"'+number+'"'+',"disposeMemo":"'+text+'"}';
				sendUrl(url, data);
				
			},
			cancelIsShow:true,
			cancelText:'取消',
			cancelFun:function(){
				Wd.hide();
			}
		});
		
	});
	
	function sendUrl(url, data){
		Wd.hide();
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			success:function(data){
				Wd.show({
					mask:true,
					content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data.message +'</h4></div></div>',
					confirmIsShow:false,
					cancelIsShow:true,
					cancelText:'关闭',
					cancelFun:function(){
						Wd.hide();
						location.reload();
					}
				});
			},
			error: function(){
				alert("error");
			}
		});
	}
	
})(jQuery);
