$(document).ready(function() {
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		table = $('#J-table'),
		panel = $('#J-search-panel'),
		Tip = phoenix.Tip.getInstance(),
		inputStart = $('#J-time-start'),
		inputEnd = $('#J-time-end'),
		selectLotteryId = $('#J-select-lotteryid'),
		inputParamCode = $('#J-input-paramCode'),
		selectTime = $('#J-select-time'),
		optionTime = selectTime.find('option'),
		inputIssueAward = $('#J-input-issueAward'),
		inputIssueWinsRatio = $('#J-input-issueWinsRatio'),
		inputBetAward = $('#J-input-betAward'),
		inputBetWinsRatio = $('#J-input-betWinsRatio'),
		inputWinsTime = $('#J-input-winsTime'),
		selectStatus = $('#J-select-status'),
		inputPageCount = $('#J-input-pageCount'),
		setTimeFn = null,
		setSelect = null;
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(5)').attr("class","current");
	
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
	
	selectTime.change(function(){
		var v = Number(this.value);
		if(optionTime.get(optionTime.size() -1).value == this.value){
			inputStart.focus();
		}else{
			if(v > 0){
				setTimeFn(v);
			}
		}
	});
	setTimeFn = function(day){
		var now = new Date(),y = now.getFullYear(),m = now.getMonth(),d = now.getDate(),h = now.getHours(),mm = now.getMinutes(),s = now.getSeconds(),newDate;
		newDate = new Date(y, m, d - day + 1, 0, 0, 1);
		
		inputStart.val(newDate.getFullYear()+ '-' + (newDate.getMonth()+1) + '-'+  newDate.getDate() +'  00:00:00');
		inputEnd.val(y+ '-' + (m+1) + '-'+  d +'  '+ h +':'+ mm +':'+ s);
	};
	setSelect = function(v){
		var i = 0,len = optionTime.size();
		for(;i < len;i++){
			if(Number(v) == Number(optionTime[i].value)){
				optionTime[i].setAttribute('selected', 'selected');
			}else{
				optionTime[i].removeAttribute('selected');
			}
		}

	};
	setTimeFn(Number(selectTime.val()));
	
	//panel.on('keyup', '.input-money', function(){
	//	var v = this.value;
	//	v = v.replace(/[^\d^\.]/g, '').split('.');
	//	if(v.length > 2){
	//		v.pop();
	//	}
	//	v = v.join('.');
	//	this.value = v;
	//}).on('blur', '.input-money', function(){
	//	var v = this.value.replace(/[^\d^\.]/g, '');
	//	if($.trim(v) != ''){
	//		this.value = phoenix.util.formatMoney(v);
	//	}
	//}).on('keyup', '.input-num', function(){
	//	this.value = this.value.replace(/[^\d]/g, '');
	//});
	panel.on('keyup', '.input-num', function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	//保持搜索条件
	if ($('#lotteryid').val() != '') {
		selectLotteryId.val($('#lotteryid').val());
	}
	if ($('#paramCode').val() != ''){
		inputParamCode.val($('#paramCode').val());
	}
	if ($('#selectTimeMode').val() != ''){
		selectTime.val($('#selectTimeMode').val());
	}
	var containSubChecked = document.getElementById("J-select-containSub");
	if($('#containSub').val() != ''){
		containSubChecked.checked = $('#containSub').val()==1?true:false;
	}
	if ($('#startCreateTime').val() != '') {
		inputStart.val(formatDate(new Date(Number($('#startCreateTime').val()))));
	}
	if ($('#endCreateTime').val() != '') {
		inputEnd.val(formatDate(new Date(Number($('#endCreateTime').val()))));
	}
	if ($('#issueAward').val() != '') {
		inputIssueAward.val($('#issueAward').val());
	}
	if ($('#issueWinsRatio').val() != '') {
		inputIssueWinsRatio.val($('#issueWinsRatio').val());
	}
	if ($('#betAward').val() != '') {
		inputBetAward.val($('#betAward').val());
	}
	if ($('#betWinsRatio').val() != '') {
		inputBetWinsRatio.val($('#betWinsRatio').val());
	}
	if ($('#winsTime').val() != '') {
		inputWinsTime.val($('#winsTime').val());
	}
	if ($('#status').val() != '') {
		selectStatus.val($('#status').val());
	}
	if ($('#pageCount').val() != '') {
		inputPageCount.val($('#pageCount').val());
	}
	setTimeFn(1);
	
	$('#J-button-submit').click(function(){
		var selectLotteryId = $('#J-select-lotteryid'),
		inputParamCode = $('#J-input-paramCode'),
		selectTime = $('#J-select-time'),
		optionTime = selectTime.find('option'),
		inputIssueAward = $('#J-input-issueAward'),
		inputIssueWinsRatio = $('#J-input-issueWinsRatio'),
		inputBetAward = $('#J-input-betAward'),
		inputBetWinsRatio = $('#J-input-betWinsRatio'),
		inputWinsTime = $('#J-input-winsTime'),
		selectStatus = $('#J-select-status'),
		inputPageCount = $('#J-input-pageCount');
		if (selectLotteryId.val() != '') {
			$('#lotteryid').val(selectLotteryId.val());
		}else{
			$('#lotteryid').val(null);
		}
		if (inputParamCode.val() != '') {
			$('#paramCode').val(inputParamCode.val());
		}else{
			$('#paramCode').val("");
		}
		if ($('#J-select-containSub').val() != '') {
			if(containSubChecked.checked == true){
				$('#containSub').val(1);
			}else{
				$('#containSub').val(0);
			}
		}
		if(selectTime.val() != ''){
			var mode = selectTime.val();
			$('#selectTimeMode').val(mode);
			
			if(mode == -1){
				$('#startCreateTime').val("");
				$('#endCreateTime').val("");
			}else{
				if (inputStart.val() != '') {
					var time = convertDate2UnixTimestamp(inputStart.val());
					$('#startCreateTime').val(time);
				}
				if (inputEnd.val() != '') {
					var time = convertDate2UnixTimestamp(inputEnd.val());
					$('#endCreateTime').val(time);
				} 
			}
		}
		if(inputIssueAward.val() != ''){
			$('#issueAward').val(inputIssueAward.val());
		}else{
			$('#issueAward').val(null);
		}
		if(inputIssueWinsRatio.val() != ''){
			$('#issueWinsRatio').val(inputIssueWinsRatio.val());
		}else{
			$('#issueWinsRatio').val(null);
		}
		if(inputBetAward.val() != ''){
			$('#betAward').val(inputBetAward.val());
		}else{
			$('#betAward').val(null);
		}
		if(inputBetWinsRatio.val() != ''){
			$('#betWinsRatio').val(inputBetWinsRatio.val());
		}else{
			$('#betWinsRatio').val(null);
		}
		if(inputWinsTime.val() != ''){
			$('#winsTime').val(inputWinsTime.val());
		}else{
			$('#winsTime').val(null);
		}
		if(selectStatus.val() != ''){
			$('#status').val(selectStatus.val());
		}else{
			$('#status').val(null);
		}
		if(inputPageCount.val() != ''){
			$('#pageCount').val(inputPageCount.val());
		}else{
			$('#pageCount').val(5);
		}
		
		$('#J-search-form').submit();
	});

	$('#J-button-export').click(function(){
		var ipts = $('#J-form-hidden').find('input[type="hidden"]'),data = {},htmlStr = '';
		ipts.each(function(){
			var name = this.getAttribute('name');
			data[name] = this.value;
		});
		htmlStr = phoenix.util.template($('#J-tpl-export').text(), data);
		
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:htmlStr,
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$('#J-download-form').submit();
				Wd.hide();
			},
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				var rows = Wd.win.dom.find('li[data-data]');
				rows.each(function(){
					var dom = $(this);
					if($.trim(dom.attr('data-data')) == ''){
						dom.remove();
					}
				});
			}
		});
	});	
	
	$('#J-th-question').hover(function(){
		Tip.setText('用户在连续中奖期数中的所有中奖次数加和，<br />一个方案中的任一注单中奖即记为一次');
		Tip.show(-260, -54, this);
	},function(){
		Tip.hide();
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

	
	table.on('click', '.button-action', function(){
		var dom = $(this);
		var orderId = $(this).attr('name');
		
		//未审核
		if(dom.hasClass('button-noaudit')){
			Wd.show({
				mask:true,
				content:$('#J-tpl-noaudit').text(),
				confirmIsShow:true,
				confirmText:'通过',
				confirmFun:function(){
					$.ajax({
						url:baseUrl + '/gameRisk/auditGameRiskOrder?ids='  + orderId + '&status=1' + '&disposeMemo=审核操作', 
						dataType:'json',
						success:function(data){
							if(Number(data['status']) == 1){
								dom.removeClass('button-noaudit').html('<span style="color:#999;">已通过</span>');
								Wd.hide();
							}else{
								Wd.show({
									mask:true,
									content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
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
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确定该方案审核不通过吗？</h4></div></div>',
						confirmIsShow:true,
						confirmText:'确定',
						confirmFun:function(){
							$.ajax({
								url:baseUrl + '/gameRisk/auditGameRiskOrder?ids=' + orderId + '&status=2' + '&disposeMemo=审核操作', 
								dataType:'json',
								success:function(data){
									if(Number(data['status']) == 1){
										dom.removeClass('button-noaudit').html('<span style="color:red;">未通过</span>');
										Wd.hide();
									}else{
										Wd.show({
											mask:true,
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
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

	
});