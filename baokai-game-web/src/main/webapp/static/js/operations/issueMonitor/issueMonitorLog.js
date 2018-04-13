(function($){
	var start = $('#J-time-start'),
		end = $('#J-time-end');
	start.focus(function(){
		(new phoenix.DatePicker({input:start})).show();
	});
	end.focus(function(){
		(new phoenix.DatePicker({input:end})).show();
	});
	
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(2)').attr("class","current");
	
	$('a[id=dates]').each(function(){
		
		$(this).click(function(){
			$('a[id=dates]').removeClass();
			var _name = $(this).attr('name');
			var _value = $('#dateType').val();
			
			$('#dateType').attr('value',_name);
			$(this).removeClass().addClass('current');
			
		});
	});
	
	$('select#selectLottery').change(function(){
		var _value = $(this).val();
		$('#lotteryid').attr('value',_value);
	});
	
	$('select#selectWarnType').change(function(){
		var _value = $(this).val();
		$('#warnType').attr('value',_value);
	});
	
	//回显
	//日期
	$('a[id=dates]').each(function(){
		
		var _name = $(this).attr('name');
		var dateType = $('#dateType').val();
		
		if(_name == dateType){
			$(this).removeClass().addClass('current');
		}
	});
	
	var lotteryId = $('#lotteryid').val();
	$("#selectLottery option[value='"+lotteryId+"']").attr("selected", true);   
	
	var warnType = $('#warnType').val();
	$("#selectWarnType option[value='"+warnType+"']").attr("selected", true);
	
	$('#J-button-submit').click(function(){
		$('#J-form').submit();
	});
	
	var Tip = new phoenix.Tip.getInstance();
	$('#J-data-table').on('click', '.tip-detail', function(e){
		var dom = $(this);
		Tip.setText(dom.parent().find('.tip-hidden').text());
		Tip.show(Tip.dom.outerWidth()*-1, Tip.dom.outerHeight()*-1, dom);
	}).on('mouseout', '.tip-detail', function(){
		Tip.hide();
	});
	
	
})(jQuery);
