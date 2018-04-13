$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	var table = $('#J-table'),
		Wd = phoenix.Message.getInstance();
	
	table.on('click', '.row-open', function(e){
		var dom = $(this),id = dom.attr('data-id');
		e.preventDefault();
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要启用该通知任务吗？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+'/noticeAdmin/openNoticeTask?id=' + id+"&num=" + Math.random(),
					dataType:'json',
					success:function(data){
						if(data.status == 1){
							dom.removeClass('row-open').addClass('row-cancel').text('禁用');		
						}else{
							alert(data.message);
						}
						Wd.hide();
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	table.on('click', '.row-cancel', function(e){
		var dom = $(this),id = dom.attr('data-id');
		e.preventDefault();
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要禁用该通知任务吗？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+'/noticeAdmin/closeNoticeTask?id=' + id+"&num=" + Math.random(),
					dataType:'json',
					success:function(data){
						if(data.status == 1){
							dom.removeClass('row-cancel').addClass('row-open').text('启用');							
						}else{
							alert(data.message);
						}
						Wd.hide();
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	
	$(".btn-important").click(function(){
		$("#j-from").submit();
	});
	
});