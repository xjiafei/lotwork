$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(8)').attr("class","current");
	var WidthCheck=function(str){  
	var w = 0;  
	var tempCount = 0; 
	for (var i=0; i<str.length; i++) {  
	   var c = str.charCodeAt(i);  
	   //单字节加1  
	   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
		w++;  
	  
	   }else {     
		w+=2;
	   }  
	 }
	return w;
	}; 
	var form = $('#J-form'),linksNum = 1;
	
	//检测标题
	var checkTitle = function(){
		var dom = $('#J-input-title'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().find('.ui-check').html('<i></i>标题不能为空').show();
			isPass = false;
		}else{
			if(WidthCheck(domv) > 80){
				dom.parent().find('.ui-check').html('<i></i>标题长度不能超过40个字').show();
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
	/*$('#J-input-title').blur(function(){
		checkTitle();
	}).keyup(function(){
		var me=this,v=me.value;
		me.value = v = v.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'');
	});*/
	
	
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
				}else if(WidthCheck(text) > 20){
					dom.parent().find('.ui-check').html('<i></i>限制字符：1－20个').show();
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
		if(linksNum >= 10){
			return;
		}
		var txt="<li class='ui-other'><input name='<#=name#>' type='text' class='input' value='' /><span class='ui-check'><i></i></span><a class='ui-text-info row-delete' href='javascript:void(0)'>&nbsp;删除</a></li>";
		$(txt.replace(/<#=name#>/g, 'link' + linksNum)).insertAfter(par);
		linksNum += 1;
	});
	//删除链接
	form.on('click', '.row-delete', function(){
		$(this).parent().remove();
		linksNum -= 1;
	});
	
	
	$('#J-button-submit').click(function(){
		if(checkTitle() && checkType()){
			var dom = $('#J-select-type'),domv = $.trim(dom.val()),isPass = false,text = '';
			$.ajax({
					url:baseUrl+"/adAdmin/haveTopicName",
					dataType:'json',
					data:{title:$.trim($("#J-input-title").val()),cateId:-1},//判断标题是否重复
					success:function(data){
						//data.status ==1 表示已经有了 ，0表示没有可以添加
						if(data.status==1)
						{
							$("#J-input-title").parent().find(".ui-check").html("<i></i>标题【"+$("#J-input-title").val()+"】已经存在").show();
						}
						else
						{
							if(Number(domv) ==-2)
							{
								var text = $.trim($('#J-type-text').val());
								if(text == ''){
									dom.parent().find('.ui-check').html('<i></i>类别名称不能为空').show();
									return;
								}else if(WidthCheck(text) > 20){
									dom.parent().find('.ui-check').html('<i></i>限制字符：1－20个').show();
									return;
								}
								$.ajax({
									url:baseUrl+"/adAdmin/haveTopicName",
									dataType:'json',
									data:{title:$.trim($("#J-type-text").val()),cateId:-2},//这个是用户选的，新建类别cateId:-2
									success:function(data){
										//data.status ==1 表示已经有了 ，0表示没有可以添加
										if(data.status==1)
										{
											$("#J-type-text").parent().find(".ui-check").html("<i></i>类别【"+$("#J-type-text").val()+"】已经存在").show();
										}
										else
										{
											form.submit();
										}
									},
									error: function(data){ //失败
										
									}
								}); 
							}else
							{
						    	form.submit();
							}
						}
					},
					error: function(data){ //失败
						
					}
				}); 
		}
		
	});
		
	
	
})