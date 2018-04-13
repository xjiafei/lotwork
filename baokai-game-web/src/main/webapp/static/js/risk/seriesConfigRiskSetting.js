

(function(){
	var buttona = $('.numa-in'),
		buttonb = $('.numb-in');
		
    $('.menu-list li:eq(4)').attr("class","current"); 
	$('.col-side .nav dd:eq(1)').attr("class","current");
	buttona.bind('input',function(){
		$('.numa-out').html($(this).val().replace(/\D/g, '').replace(/^0/g, ''));
	});
	buttona.bind('propertychange',function(){
		$('.numa-out').html($(this).val().replace(/\D/g, '').replace(/^0/g, ''));	
	});
	buttonb.bind('input',function(){
		$('.numb-out').html($(this).val().replace(/\D/g, '').replace(/^0/g, ''));
	});
	buttonb.bind('propertychange',function(){
		$('.numb-out').html($(this).val().replace(/\D/g, '').replace(/^0/g, ''));
	});
	
	//数字校验，自动矫正不符合数学规范的数学
	var inputs2 = $('.ui-tab input'),checkFn2;				
	checkFn2 = function(){
		var v = this.value.replace(/\D/g, '');
		this.value = v;
	};
	inputs2.keyup(checkFn2);
	inputs2.blur(checkFn2);
	
	var minWindow,mask,initFn,isture=false;	
	minWindow = new phoenix.MiniWindow({cls:'pop'});
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	
	//加载定位彩种
	$("#lotteryId").val($.trim(lotteryId));
	
	//下拉选择彩种加载事件
	$("#lotteryId").change(function(){
		var _lotteryId = $('#lotteryId').val();
		window.location.href = currentContextPath + '/gameRisk/toSeriesConfigRisk?lotteryid=' + _lotteryId; 
	});
	
	//修改跳转处理
	$('#J-button-Update').click(function(){
		var _lotteryId = $('#lotteryId').val();
		window.location.href = currentContextPath + '/gameRisk/toModifySeriesConfigRisk?lotteryid=' + _lotteryId; 
	});
	
	//返回跳转处理
	$('#J-button-back').click(function(){
		var _lotteryId = $('#lotteryId').val();
		window.location.href = currentContextPath + '/gameRisk/toSeriesConfigRisk?lotteryid=' + _lotteryId; 
	});
	
	//保存处理
	$('#J-button-modify').click(function(e){
		var _lotteryId = $('#lotteryId').val();
		$.ajax({
				url:'/gameRisk/modifySeriesConfigRisk',
				dataType:'json',
				method:'post',
				data:$("#modifyForm").serialize(),
				success:function(data){
					if(data.status > 0){
						alert("游戏审核设置成功");
						location.href = "/gameRisk/toSeriesConfigRisk?lotteryid="+_lotteryId;
					}else
					{
						alert("游戏审核设置失败");
					}
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 
				}
			});		
	});
	
	//$('#J-button-back').click(function(e){
	//	location.href = "/gameRisk/toSeriesConfigRisk?lotteryid="+lotteryid;
	//	return false;
	//});
	
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
	
	$("[name='backoutRatio']").blur(function(){
		if($(this).val()>100)
		{
		   $(this).val(100);
		}
	});
	
})();
