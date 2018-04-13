$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	var start = $('#J-time-start'),
		end = $('#J-time-end'),
		table = $('#J-data-table'),
		Wd = phoenix.Message.getInstance();
		
	start.focus(function(){
		(new phoenix.DatePicker({input:start,isShowTime:true})).show();
	});
	end.focus(function(){
		(new phoenix.DatePicker({input:end,isShowTime:true})).show();
	});
	
	
	$('#J-button-submit').click(function(){
		$('#J-form').submit();
	});
	
	
	table.on('click', '.button-cancel', function(e){
		var dom = $(this),id = dom.attr('data-id');
		e.preventDefault();
		Wd.show({
			mask:true,
			title:'撤销提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要撤销该条站内信吗？撤销后用户将不能在前台看到该条站内信。</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/noticeAdmin/modifySysMsgStatus",
					dataType:'json',
					data:{id:id,status:3},
					success:function(data){
						if(Number(data['status']) == 1){
							location.href = baseUrl+"/noticeAdmin/goSysMsgManager";
						}else{
							alert(data['msg']);
						}
						Wd.hide();
						
						location.href = location.href;
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	
	table.on('click', '.button-del', function(e){
		var dom = $(this),id = dom.attr('data-id');
		e.preventDefault();
		Wd.show({
			mask:true,
			title:'删除提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除该条待发站内信吗？删除后将不能恢复。</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/noticeAdmin/modifySysMsgStatus",
					dataType:'json',
					data:{id:id,status:4},
					success:function(data){
						if(Number(data['status']) == 1){
							location.href = baseUrl+"/noticeAdmin/goSysMsgManager";
						}else{
							alert(data['msg']);
						}
						Wd.hide();
						
						location.href = location.href;
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});	
});