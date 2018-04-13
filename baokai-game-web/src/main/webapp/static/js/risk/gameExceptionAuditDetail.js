(function($){
	
	var Tip = phoenix.Tip.getInstance(),
	table = $('#J-table'),
	tbody=table.find("tbody"),
	Wd = phoenix.Message.getInstance();
	
	var lotteryId = $('#lotteryId').val();
	var issueCode = $('#issueCode').val();
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(4)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	
	$('#J-panel-container').on('mouseover', '.icon-question', function(){
		var dom = $(this),text = dom.parent().find('.ico-question-text').html();
		Tip.setText(text);
		Tip.show(dom.width(), dom.height()*-1-10, dom);
	}).on('mouseout', '.icon-question', function(){
		Tip.hide();
	});
	
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
	
	$('#J-table1').on('click', '.ico-control', function(){
		var dom = $(this),cls1 = 'ico-unfold',cls2 = 'ico-fold',tr = dom.parent().parent().next();
		if(dom.hasClass(cls1)){
			dom.removeClass(cls1).addClass(cls2);
			tr.show();
		}else{
			dom.removeClass(cls2).addClass(cls1);
			tr.hide();
		}
	});
	
	$('#selectAll').click(function(){
		if($(this).is(":checked")==true){
			 $(".selectUserAll").each(function(){				
				if($(this).is(":checked")==false){
				 $(this).click();
				}
			 });

			
		}else{
			 $(".selectUserAll").removeAttr("checked");
			 $(".selectedOrder").removeAttr("checked");
		}
	});
	

	tbody.on('click','.selectUserAll',function(){
		var dom = $(this),me=this,tr = dom.parent().parent().next();
		tr.find('.selectedOrder').each(function(){
			this.checked = me.checked;
		});
	});
		
	
	$('#J-button-all').click(function(){
		var dom = $(this),text = $.trim(dom.text()),cls = 'open';
		if(dom.hasClass(cls)){
			dom.removeClass(cls);
			dom.html('展开<b class="btn-inner"></b>');
			$('#J-table1').find('.ico-control').each(function(){
				var el = $(this),tr = el.parent().parent().next();
				el.removeClass('ico-fold').addClass('ico-unfold');
				tr.hide();
			});
		}else{
			dom.addClass(cls);
			dom.html('收起<b class="btn-inner"></b>');
			$('#J-table1').find('.ico-control').each(function(){
				var el = $(this),tr = el.parent().parent().next();
				el.removeClass('ico-unfold').addClass('ico-fold');
				tr.show();
			});
		}

	});
	
		
	table.on('click', '.button-action', function(){
		var dom = $(this);
		var orderId = $(this).attr('name');
		Wd.hide();
		//未审核
		if(dom.hasClass('button-noaudit')){
			
			Wd.show({
				mask:true,
				content:$('#J-tpl-noaudit').text(),
				confirmIsShow:true,
				confirmText:'通过',
				confirmFun:function(){
				var canSend = false;
					if (canSend) return;
					var btn = $(this);					
					$.ajax({
						url:baseUrl + '/gameRisk/auditGameRiskOrder?ids=' + orderId + '&status=1' + '&disposeMemo=' +$('#disposeMemo').val(),
						dataType:'json',
						beforeSend: function () {						
							$('.confirm').hide();
						},
						success:function(data){
							if(Number(data['status']) == 1){
								Wd.hide();
								Wd.show({
									mask:true,
									content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">审核通过成功!</h4></div></div>',
									cancelIsShow:true,
									cancelText:'关闭',
									cancelFun:function(){
										canSend = true;										
										dom.parent().removeClass('button-noaudit').html('<span style="color:#999;">已通过</span>');
										Wd.hide();
									}
								});
										
							}else{
								Wd.hide();
								Wd.show({
									mask:true,
									content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
									cancelIsShow:true,
									cancelText:'关闭',
									cancelFun:function(){
										canSend = false;
										Wd.hide();
									}
								});
							}
						},
						complete: function(){	
							//$('.confirm').show();
						}
					});
				},
				cancelIsShow:true,
				cancelText:'不通过',
				cancelFun:function(){
					var disposeMemo = $('#disposeMemo').val();					
					Wd.show({
						mask:true,
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定该方案审核不通过吗？<br />审核不通过后，该方案将同时记为恶意方案。<br />奖金保持被冻结状态。</h4></div></div>',
						confirmIsShow:true,
						confirmText:'确定',
						confirmFun:function(){
							var canSend = false;
								if (canSend) return;
								var btn = $(this);	
							$.ajax({
								url:baseUrl + '/gameRisk/auditGameRiskOrder?ids=' + orderId + '&status=2' + '&disposeMemo=' +disposeMemo,
								dataType:'json',
								beforeSend: function () {						
									$('.confirm').hide();
								},
								success:function(data){
									if(Number(data['status']) == 1){	
										Wd.hide();
										Wd.show({
											mask:true,
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">操作成功，该方案将同时记为恶意方案。<br />奖金保持被冻结状态。</h4></div></div>',
											cancelIsShow:true,
											cancelText:'关闭',
											cancelFun:function(){
												canSend = true;		
												Wd.hide();
												location.href="/gameRisk/queryGameExceptionAuditDetail?lotteryId="+lotteryId+"&issueCode="+issueCode+"&status=1";
											}
										});
									}else{
										Wd.hide();
										Wd.show({
											mask:true,
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
											cancelIsShow:true,
											cancelText:'关闭',
											cancelFun:function(){
												canSend = false;
												Wd.hide();
											}
										});
									}
								},
								complete: function(){		
									//$('.confirm').show();	
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
		Wd.hide();
		Wd.show({
			mask:true,
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定要审核通过所有未审核的方案吗？</h4></div></div>',
			confirmIsShow:true,
			confirmText:'确定',
			confirmFun:function(){
				var canSend = false;
					if (canSend) return;
					var btn = $(this);	
				$.ajax({
					url:baseUrl + '/gameoa/auditIssue?lotteryId=' + lotteryId +'&issueCode=' + issueCode ,
					dataType:'json',
					beforeSend: function () {
						$('.confirm').hide();
                    },
					success:function(data){
						if(Number(data['status 	']) == 1){
							Wd.hide();
							Wd.show({
								mask:true,
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">审核通过成功!</h4></div></div>',
								cancelIsShow:true,
								cancelText:'关闭',
								cancelFun:function(){
									canSend = true;	
									Wd.hide();
									location.href = location.href;
								}
							});
							
							
						}else{
							Wd.hide();
							Wd.show({
								mask:true,
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
								cancelIsShow:true,
								cancelText:'关闭',
								cancelFun:function(){
									canSend = false;	
									Wd.hide();
								}
							});
						}
					},
					complete: function(){		
						//$('.confirm').show();
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
	
	
	$("input[name='audit']").click(function(){
		var arr = [];
		table.find('.selectedOrder').each(function(){
			if($(this).is(":checked")==true){
				arr.push(this.value);
			}
		});
		if(arr.length < 1){
			alert('没有选中未通过审核的订单');
			return;
		}
		Wd.hide();
		Wd.show({
			mask:true,
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定要审核通过所有选中未审核的方案吗？</h4></div></div>',
			confirmIsShow:true,
			confirmText:'确定',
			confirmFun:function(){
				var canSend = false;
					if (canSend) return;
					var btn = $(this);	
				$.ajax({
					url:baseUrl + '/gameRisk/auditGameRiskOrder?ids=' + arr.join(',')  + '&status=1' + '&disposeMemo=批量审核操作', 
					dataType:'json',
					cache: false,
					beforeSend: function () {							
						$('.confirm').hide();
                    },
					success:function(data){
						if(Number(data['status']) == 1){
							Wd.hide();
							Wd.show({
								mask:true,
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">审核成功!</h4></div></div>',
								cancelIsShow:true,
								cancelText:'关闭',
								cancelFun:function(){
									canSend = true;
									Wd.hide();
									location.href="/gameRisk/queryGameExceptionAuditDetail?lotteryId="+lotteryId+"&issueCode="+issueCode+"&status=1";
								}
							});
							
						}else{
							Wd.hide();
							Wd.show({
								mask:true,
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
								cancelIsShow:true,
								cancelText:'关闭',
								cancelFun:function(){
									canSend = false;
									Wd.hide();
								}
							});
						}
					},
					complete: function(){	
						//$('.confirm').show();
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
	
	
})(jQuery);


