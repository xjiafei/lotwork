$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(2)').attr("class","current");
	var table = $('#J-table');
	//弹窗
	var Tip = phoenix.Tip.getInstance();
	
	//缩略图
	table.on('mouseenter', '.text-left', function(){
		var dom = $(this),src = dom.attr('data-img');
		Tip.setText('<img width="200" src="'+ src +'" /><div style="text-align:center;font-size:12px;color:#333;line-height:180%;"></div>');
		Tip.show(dom.width(), 0, this);
		dom.parent().addClass('bg-disable');
	}).on('mouseleave', '.text-left', function(){
		Tip.hide();
		$(this).parent().removeClass('bg-disable');
	});
});