$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(6)').attr("class","current");
	var form = $('#J-form');
	
	//检测标题
	var checkTitle = function(){
		var dom = $('#J-input-title'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().find('.ui-check').html('<i></i>标题不能为空').show();
			isPass = false;
		}else{
			if(domv.length > 40){
				dom.parent().find('.ui-check').html('<i></i>标题长度不能超过40个字符').show();
				isPass = false;
			}else{
				dom.parent().find('.ui-check').hide();
				isPass = true;
			}

		}
		return isPass;
	};
	$('#J-input-title').blur(function(){
		checkTitle();
	});
	
	
	//检测分类
	var checkType = function(){
		var dom = $('#J-select-type'),domv = $.trim(dom.val()),isPass = false,text = '';
		if(domv == ''){
			dom.parent().find('.ui-check').html('<i></i>请选择或新建一个类别').show();
			isPass = false;
		}else{
			if(Number(domv) < 0){
				text = $.trim($('#J-type-text').val());
				if(text == ''){
					dom.parent().find('.ui-check').html('<i></i>类别名称不能为空').show();
					isPass = false;
				}else if(text.length > 20){
					dom.parent().find('.ui-check').html('<i></i>类别名称不能超过20个字符').show();
					isPass = false;
				}else{
					dom.parent().find('.ui-check').hide();
					isPass = true;
				}
			}else{
				dom.parent().find('.ui-check').hide();
				isPass = true;
			}

		}
		return isPass;
	};
	$('#J-select-type').change(function(){
		checkType();
	});
	$('#J-type-text').blur(function(){
		checkType();
	});
	$('#J-select-type').change(function(){
		if(Number(this.value) < 0){
			$('#J-type-text').show();
		}else{
			$('#J-type-text').hide();
		}
	});
	
	
	
	//增加链接
	$('#J-button-link-add').click(function(){
		var par = $(this).parent();
		var num = $('#linkNum').val();
		if(num >= 10){
			return;
		}
		var txt="<li class='ui-other'><input name='<#=name#>' type='text' class='input' value='' /><span class='ui-check'><i></i></span><a class='ui-text-info row-delete' href='javascript:void(0)'>&nbsp;删除</a></li>";
		$(txt.replace(/<#=name#>/g, 'link' + num)).insertAfter(par);
		num = Number(num) + Number(1);
		$('#linkNum').val(num);
	});
	//删除链接
	form.on('click', '.row-delete', function(){
		$(this).parent().remove();
		var linksNum = $('#linkNum').val();
		linksNum -= 1;
		$('#linkNum').val(linksNum);
	});
	
	
	$('#J-button-submit').click(function(){
		if(checkTitle() && checkType()){
			form.submit();
		}
	});
})