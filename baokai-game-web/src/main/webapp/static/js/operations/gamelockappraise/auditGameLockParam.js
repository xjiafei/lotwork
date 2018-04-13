(function($){
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#changeMenu').attr("class","current");
	
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		if(text){
			inputTip.setText('原值：' + dom.attr('data-showtip'));
			inputTip.show(dom.outerWidth() + 4, dom.outerHeight()*-1, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});
	
	$('#J-button-pass').click(function(e){
		e.preventDefault();
		$('#status').val(2);
		$('#J-form').submit();
	});
	$('#J-button-nopass').click(function(e){
		e.preventDefault();
		$('#status').val(3);
		$('#J-form').submit();
	});
	
	
})(jQuery);