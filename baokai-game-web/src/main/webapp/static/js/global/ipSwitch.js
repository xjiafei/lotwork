$(document).ready(function(){
	$('.menu-list li:eq(1)').attr("class","current");
	//弹窗
	var Wd = phoenix.Message.getInstance();
	if($('#J-switch').children().eq(0).attr("class")=="switch-on")
	{
		$(".input.w-1").removeAttr("disabled");
	}else
	{
		$(".input.w-1").attr("disabled","disabled");
	}
	
	//全选
	$('#J-select-all').click(function(){
		var inputs = $('#J-table-list').find('input[type="checkbox"]');
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
	
	//开关
	$('#J-switch').click(function(e){
		var dom = $(this).children().eq(0),cls = '',clsOn = 'switch-on',clsOff = 'switch-off',type = dom.find('.switch-type'),input = dom.find('.switch-value'),value;
		cls = dom.hasClass(clsOn) ? clsOff : clsOn;
		value = cls == clsOn ? 1 : 0;
		
		$.ajax({
			url:baseUrl+"/globeAdmin/ipSwitch",
			dataType:'json',
			cache:false,
			data:{switchStatus:value,type:type.val()},
			success:function(data){
				if(Number(data['status']) == 1){
					dom.removeClass();
					dom.addClass(cls);
					dom.get(0).className=cls;
					if(cls == clsOn){
						input.val(1);
						$(".input.w-1").removeAttr("disabled");
					}else{
						input.val(0);
						$(".input.w-1").attr("disabled","disabled");
					}
					window.location=location;
				}else{
					Wd.show({
						mask:true,
						title:'温馨提示',
						content:'<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">只能开启任意一个模式，不能同时开启，<br/>关闭其中一个模式才能开启另外一个；<br/>可以同时关闭两个模式.</h4></div>',
						confirmIsShow:true,
						confirmFun:function(){
							Wd.hide();
						}
					});
				}
			}
		});
		

		
	});
	
	//ip输入
	$('#J-input-ip').on('keyup', 'input[type="text"]', function(){
		var v = this.value.replace(/[^\d]/g, '')||this.value.replace(/[^\*]/g, '');
		    v = this.value.replace(/[^\*]/g, '')||this.value.replace(/[^\d]/g, '');
		this.value = v;
		if(v == ''){
			return;
		}
		
		if(Number(v) > 255){
			this.value = 255;
		}
	});

	
	//天数
	$('#J-days').keyup(function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	//表单提交
	$('#J-submit').click(function(){
		var ips = $('#J-input-ip').find('input[type="text"]'),days = $('#J-days'),i = 0;
		var ip='';
		var type = $('#type').val();
		ips.each(function(){
			if($.trim(this.value) == ''){
				alert('ip地址段不能为空');
				this.focus();
				return false;
			}
			i++;
			ip=ip+$.trim(this.value)+".";
		});
		if(i > 3 && $.trim(days.val()) == ''){
			alert('请填写天数');
			days.focus();
			return;
		}
		if(i < 4){
			return;
		}
		$.ajax({
			url:baseUrl+"/globeAdmin/checkCreateIp",
			dataType:'json',
			method:'post',
			data:{ip:ip,type:type},
			success:function(data){
				if(Number(data['status']) == 1){
					$('#J-form').submit();
				}else{
					alert(data['message']);
					return;
				}
			}
		});
		
	});
	
	
	//批量删除
	$('#J-delete-all').click(function(){
		var inputs = $('#J-table-list').find('input[type="checkbox"]'),result = "";
		inputs.each(function(){
			if(this.checked){
				result = result+this.value+","
			}
		});
		if(result.length < 1){
			return;
		}
		Wd.show({
			mask:true,
			title:'提示',
			content:'你确定要删除列表中所选的ip记录吗？',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/globeAdmin/deleteIp",
					dataType:'json',
					method:'post',
					data:{ids:result},
					success:function(data){
						if(Number(data['isSuccess']) == 1){
							Wd.hide();
							location.href = location.href;
						}else{
							alert('msg');
							Wd.hide();
						}
					},
					complete: function(){
						location.href = baseUrl+"/globeAdmin/goIpSwitch";
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	
	
	//列表删除单行
	$('#J-table-list').on('click', '.ip-list-delete', function(){
		var id = Number($(this).attr('data-id'));
		Wd.show({
			mask:true,
			title:'提示',
			content:'你确定要删除该条ip记录吗？',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/globeAdmin/deleteIp",
					data:{ids:id},
					dataType:'json',
					success:function(data){
						if(Number(data['isSuccess']) == 1){
							location.href = location.href;
						}else{
							alert('msg');
						}
					},
					complete: function(){
						location.href = baseUrl+"/globeAdmin/goIpSwitch";
					}

				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
})