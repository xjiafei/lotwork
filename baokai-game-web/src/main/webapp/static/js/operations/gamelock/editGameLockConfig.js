(function($){
	var input = $('#upValProcessView');
	var input2 = $('#upValProcessView2');
	var input3 = $('#upValProcessView3');
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#lockMenu').attr("class","current");
	
	input.keyup(function(){
		this.value = this.value.replace(/[^\d|\.]/g, '');
	});
	input2.keyup(function(){
		this.value = this.value.replace(/[^\d|\.]/g, '');
	});
	input3.keyup(function(){
		this.value = this.value.replace(/[^\d|\.]/g, '');
	});
	
	$('#J-button-submit').click(function(e){
		$('#upValProcess').val(Number($('#upValProcessView').val().replace(",",''))*10000);
		if(Number($('#upValProcessView').val().replace(",",'')) <= 0){
			alert("封锁值必须大于0");
			return;
		}
		if($('#upValProcessView2').val() != ''){
			$('#upValProcess2').val(Number($('#upValProcessView2').val().replace(",",''))*10000);
			if(Number($('#upValProcessView2').val().replace(",",'')) <= 0){
				alert("封锁值必须大于0");
				return;
			}
		}
		if($('#upValProcessView3').val() != ''){
			$('#upValProcess3').val(Number($('#upValProcessView3').val().replace(",",''))*10000);
			if(Number($('#upValProcessView3').val().replace(",",'')) <= 0){
				alert("封锁值必须大于0");
				return;
			}
		}
		e.preventDefault();
		$('#J-form').submit();
	});
	
})(jQuery);