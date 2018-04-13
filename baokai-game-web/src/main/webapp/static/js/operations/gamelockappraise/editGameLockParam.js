(function($){
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#changeMenu').attr("class","current");
	
	$('#J-input-down-money').keyup(function(){
		var el = $(this),v = $.trim(el.val());
		el.val(v.replace(/[^\d|\.]/g, ''));
	}).blur(function(){
		var v = this.value.replace(/[^\d|^\.]/g, ''),arr = [],last;
		arr = v.split('.');
		if(arr.length > 1){
			last = arr.pop();
			v = arr.join('').replace('.', '') + '.' + last;
		}
		this.value = phoenix.util.formatMoney(v);
	});
	
	
	$('#J-input-up-multiple').keyup(function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	
	$('#J-input-profit').keyup(function(){
		this.value = this.value.replace(/[^\d\-|\.]/g, '');
	});
	
	
	$('#J-input-down-time-start, #J-input-down-time-end, #J-input-up-time-start, #J-input-up-time-end').blur(function(e){
		checkTime(this);
	});
	
	var checkTime = function(dom){
		var el = $(dom),v = el.val().replace(/\s/g, ''),arr = v.split(':'),isPass = true;
		if(arr.length != 3){
			isPass = false;
			el.parent().find('.ui-check').html('<i></i>时间范围格式不正确').css('display', 'inline-block');
		}else if(Number(arr[0]) > 24 || Number(arr[1]) > 59 || Number(arr[2]) > 59 ){
			isPass = false;
			el.parent().find('.ui-check').html('<i></i>时间范围格式不正确').css('display', 'inline-block');
		}else if(Number(arr[0]) == 24 ){
			if( Number(arr[1]) > 0 || Number(arr[2]) > 0 ){
				isPass = false;
				el.parent().find('.ui-check').html('<i></i>时间范围格式不正确').css('display', 'inline-block');
			}
			
		}else{
			isPass = true;
			el.parent().find('.ui-check').hide();
		}
		return isPass;
	};
	
	
	
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		var isPass = true;
		$('#J-input-down-time-start, #J-input-down-time-end, #J-input-up-time-start, #J-input-up-time-end').each(function(){
			if(!checkTime(this)){
				isPass = false;
				return false;
			}
		});
		if(isPass){
			$('#minValProcess').val(Number($('#J-input-down-money').val().replace(",",''))*10000)
			if(Number($('#J-input-down-money').val().replace(",",''))<0||Number($('#J-input-down-money').val().replace(",",''))>1500){
				alert("极限下调奖金最小值的设置范围为0-1500");
				return false;
			}else{
				$(this).parent().find('.ui-check').hide();
			}
			
			$('#J-form').submit();
		}
	});
	
	
})(jQuery);