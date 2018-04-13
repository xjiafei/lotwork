(function($){
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#changeMenu').attr("class","current");
	
	//全选
	$('#J-select-all').click(function(){
		var inputs = $('#J-table').find('input[type="checkbox"]');
		if(this.checked){
			inputs.each(function(){
				this.checked = true;
			});
		}else{
			inputs.each(function(){
				this.checked = false;
			});
		}
	});
	
	
	
	$('#J-delete-all').click(function(e){
		e.preventDefault();
		var inputs = $('#J-table').find('input[type="checkbox"]'),isPass = false;
		var ids="";
		inputs.each(function(){
			if(this.checked){
				isPass = true;
				ids+=","+this.value;
			}
		});
		if(isPass && confirm('你确认要删除所选项吗？')){
			$('#ids').val(ids);
			$('#J-form').submit();
		}
	});
	
	
	$('#J-table').on('click', '[data-action]', function(){
		var el = $(this),action = $.trim(el.attr('data-action')),id = $.trim(el.attr('data-id')),text = $.trim(el.text());
		if(action && (action != '') && (id != '') && confirm('你确定要 '+ text +' 该条内容吗')){
			$.ajax({
				url:baseUrl+'/gameoa/operateGameLockAppraise?status=' + action + '&id=' + id,
				dataType:'json',
				success:function(data){
					if(Number(data['status']) == 1){
						location.href = baseUrl+'/gameoa/queryAllGameLockAppraise?lotteryid=' + $('#lotteryid').val();
					}else{
						alert(data['message']);
					}
				}
			});
		}
	});
	
	
	$('#J-select').change(function(){
		location.href = baseUrl+'/gameoa/queryAllGameLockAppraise?lotteryid=' + this.value;
	});
	
	
})(jQuery);