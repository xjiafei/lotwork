(function($){
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#changeMenu').attr("class","current");
	
	$('#J-select').change(function(){
		location.href = baseUrl+'/gameoa/toGameLockParam?lotteryid=' + this.value;
	});
	
	$('#J-button-publish').click(function(e){
		e.preventDefault();
		$('#J-form').submit();
	});
	
	
})(jQuery);