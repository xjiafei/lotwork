

(function(){
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		if(text){
			inputTip.setText(dom.attr('data-showtip'));
			inputTip.show(dom.outerWidth() + 4, dom.outerHeight()*-1, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	 }
	
	function operationSuccess(toUrl){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
//		location.reload();
		location.href = toUrl;
	}
	if($('#modifyType').val() == '1'){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
		$('#modifyType').val('0');
	}
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#configMenu').attr("class","current");
	
	var buttona = $('.numa-in'),
		buttonb = $('.numb-in'),
		buttonc = $('.numc-in'),
		editConfirm = new phoenix.EditConfirm();

	buttona.bind('input',function(){
		$('.numa-out').html($(this).val().replace(/\D/g, '').replace(/^0[0-9]+/g, ''));
	});
	buttona.bind('propertychange',function(){
		$('.numa-out').html($(this).val().replace(/\D/g, '').replace(/^0[0-9]+/g, ''));	
	});
	buttonb.bind('input',function(){
		$('.numb-out').html($(this).val().replace(/\D/g, '').replace(/^0[0-9]+/g, ''));
	});
	buttonb.bind('propertychange',function(){
		$('.numb-out').html($(this).val().replace(/\D/g, '').replace(/^0[0-9]+/g, ''));
	});
	buttonc.bind('input',function(){
		$('.numc-out').html($(this).val().replace(/\D/g, '').replace(/^0[0-9]+/g, ''));
	});
	buttonc.bind('propertychange',function(){
		$('.numc-out').html($(this).val().replace(/\D/g, '').replace(/^0[0-9]+/g, ''));
	});
	
	//数字校验，自动矫正不符合数学规范的数学
	var inputs2 = $('.ui-tab input').not('#email'),checkFn2;				
	checkFn2 = function(){		
		var v = this.value.replace(/\D/g, '').replace(/^0[0-9]+/g, '');
		this.value = v;		
	};
	
	inputs2.keyup(checkFn2);
	
	var minWindow,mask,initFn,isture=false;	
	minWindow = new phoenix.MiniWindow({cls:'pop'});
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	$('#J-button-modify').click(function(e){
		editConfirm.isFlag = false;
		$('#modifyForm').submit();
	});
	
	var lotteryid = $('#lotteryid').val();
	
	$('#J-button-audit').click(function(e){
		editConfirm.isFlag = false;
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确认审核通过当前内容吗？</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a></div>');
		minWindow.show();
		
		$('#J-button-pop-confirm').click(function(){
			var url = currentContextPath + "/gameoa/auditSeriesConfig";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"auditType":1';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toReleaseSeriesConfig?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl,1);
		});
		$('#J-button-pop-cancel').click(function(){
			minWindow.hide();
		});
	});
	
	$('#J-button-audit2').click(function(e){
		editConfirm.isFlag = false;
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确认审核不通过当前内容吗？</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a></div>');
		minWindow.show();
		
		$('#J-button-pop-confirm').click(function(){
			var url = currentContextPath + "/gameoa/auditSeriesConfig";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"auditType":2';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toSeriesConfig?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl,0);
		});
		$('#J-button-pop-cancel').click(function(){
			minWindow.hide();
		});
	});
	
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		
		if(text){
			inputTip.setText(dom.attr('data-showtip'));
			inputTip.show(dom.width() + 12, dom.outerHeight()*-1 + 4, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});


	$('#J-button-publish').click(function(e){
		editConfirm.isFlag = false;
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确认发布当前内容吗？</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a></div>');
		minWindow.show();
		
		$('#J-button-pop-confirm').click(function(){
			var url = currentContextPath + "/gameoa/releaseGameSeriesConfig";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"publishType":1';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toSeriesConfig?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl,0);
		});
		$('#J-button-pop-cancel').click(function(){
			minWindow.hide();
		});
	});
	
	$('#J-button-publish2').click(function(e){
		editConfirm.isFlag = false;
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确认发布不通过当前内容吗？</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a></div>');
		minWindow.show();
		
		$('#J-button-pop-confirm').click(function(){
			var url = currentContextPath + "/gameoa/releaseGameSeriesConfig";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"publishType":2';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toSeriesConfig?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl,0);
		});
		$('#J-button-pop-cancel').click(function(){
			minWindow.hide();
		});
	});
	
	//修跳转跳
	$('#_toModifySeriesConfig,#_toModifySeriesConfig2').click(function(){
		editConfirm.isFlag = false;
		location.href = currentContextPath + '/gameoa/toModifySeriesConfig?lotteryid=' + lotteryid;	
		
	});
	
	
	
	function sendUrl(url, data, toUrl,isAudit){
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			cache: false,
			success:function(data){
				if(data.status==1){
					//alert(data.message);
					minWindow.hide();
					if(isAudit == 1){
						operationSuccess(toUrl);
					}else{location.href = toUrl;}
				}else{
					alert(data.message);
				}
			},
			error: function(){
				
			}
		});
	}
	$("[name='backoutRatio']").blur(function(){
		if($(this).val()>100)
		{
		   $(this).val(100);
		}
	});
	
})();
