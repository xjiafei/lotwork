function gatherDftImgs(){
	var resutlStr ="";
	//$("#J-input-placeholder-li").find("[name=J-input-placeholder-img]").each(function(){
	//	if($(this).val() !=''){
	//		resutlStr+=$(this).val()+";"
	//	}
	//})
	return resutlStr;
}
function showImgBlank(msg){
	$(msg).attr('href',$("#pic1").attr('src'));
}
$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(2)').attr("class","current");
	//修改时初始化数据属性
	//初始化链接打开方式
	if($("#J-input-check-urlTarget").val() == 2){
		$("#J-radio-target-self").attr("checked","checked");
	}
	//初始化站位图
	if($("#J-input-check-isDftUsed").val() == 1){
		$("#J-radio-position-open").attr("checked","checked");
		//$('#J-iframe-placeholder').attr('src', baseUrl+'/adAdmin/forwardSpaceHolder');
		$("#J-panel-img-position").show();
	}
	
	
	var Wd = phoenix.Message.getInstance();
	
	//图片尺寸的高度校验
	$('#J-input-img-h').keyup(function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
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
	
	//选择广告页面
	$('#J-button-select-page').click(function(){
		var iptids = $('#J-input-page-id').val();
		Wd.show({
			mask:true,
			title:'选择页面',
			content:$('#J-tpl-selectpages').html(),
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				var ipts = $('#J-panel-pages').find('.checkbox'),ids = [],names = [];
				ipts.each(function(){
					if(this.checked){
						ids.push(this.value);
						names.push($(this).parent().find('.pageTitle').text());
					}
				});
				if(ids.length < 1){
					return;
				}
				$("#J-input-page-id").val(ids[0])
				$("#J-input-page-title").val(names[0]);
				Wd.hide();
			},
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				var ipts,id,v;
				if(iptids.length > 0){
					ipts = $('#J-panel-pages').find('.checkbox');
					ipts.each(function(){
						me=this,v = me.value;
						if(v==iptids)
						{
							me.checked=true;
						}
					});
				}
			}
		});
	});
	
	
	//选择广告参数
	$('#J-button-select-params').click(function(){
		var iptids = $('#J-input-page-id').val();
		var ipparams=$('#J-input-param-id').val();
		var selectparamslist="";
		if(iptids == ''){
				Wd.show({
					mask:true,
					title:'提示',
					content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">必须先选择页面，才能选择参数</h4></div></div>',
					confirmIsShow:true,
					cancelIsShow:true,
					confirmFun:function(){
						Wd.hide();
					},
					cancelFun:function(){
						Wd.hide();
					},
					callback:function(){
					}
				});
			return;
		}
		
		$.ajax({
			url:baseUrl+'/adAdmin/getParamList',
			dataType:'json',
			type:'get',
			async:false,
			success:function(data2){
				var strArr = [];
				if(false){
					$('#J-input-params').val('');
					$('#J-input-params-title').val('');
				}
			
					Wd.hide();
					$.each(data2.body.result, function(){
						var w = this.width < 0 ? '不限制' : this.width,
							h = this.height < 0 ? '不限制' : this.height;
						strArr.push('<tr><td><input name="radioParam" type="radio" class="radio" value="'+ this.id +'"></td><td><span class="paramTitle">'+ this.name +'</span></td><td><span class="paramWidth">'+ w +'</span>*<span class="paramHeight">'+ h +'</span></td><td>'+ this.position +'</td><td>'+ this.pageMemo +'</td></tr>');
					});
					selectparamslist=$('#J-tpl-selectparams').html().replace(/<#=list#>/g, strArr.join(''));
					//$('#J-tpl-selectparams-list').html($('#J-tpl-selectparams').html().replace(/<#=list#>/g, strArr.join('')));
			}
		});
		
		Wd.show({
			mask:true,
			title:'选择参数',
			content:selectparamslist,//$('#J-tpl-selectparams-list').html(),
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				var ipts = $('#J-panel-params').find('.radio'),ids = [],names = [],ws = [],hs = [];
				ipts.each(function(){
					if(this.checked){
						ids.push(this.value);
						names.push($(this).parent().parent().find('.paramTitle').text());
						ws.push($(this).parent().parent().find('.paramWidth').text());
						hs.push($(this).parent().parent().find('.paramHeight').text())
					}
				});
				if(ids.length < 1){
					return;
				}
				
				Wd.hide();
				$('#J-input-params-title').val(names.join(';'));
				$('#J-input-param-id').val(ids.join(','));
				$('#J-input-img-w').val(ws.join(','));
				$('#J-input-img-h').val(hs.join(','));
				checkParam();
			},
			cancelFun:function(){
				Wd.hide();
			},
			callback:function(){
				var ipts,id,v;
				if(iptids.length > 0){
					ipts = $('#J-panel-params').find('.radio');
					ipts.each(function(){
						me=this,v = me.value;
						if(v==ipparams)
						{
							me.checked=true;
						}
					});
				}
			}
		});
	});
	
	$('#J-panel-radio-position').find('input[type="radio"]').click(function(){
		var v = this.value,param;
		if(v == '1'){
			param = $.trim($('#J-input-param-id').val());
			if(param != ''){
				var widthSrc=$("#J-input-img-w").val();
				var heighSrc=$("#J-input-img-h").val();
				if(widthSrc =='' || heighSrc ==''){
					
					Wd.show({
						mask:true,
						title:'提示',
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">必须填写广告位尺寸，才能载入占位符图片</h4></div></div>',
						confirmIsShow:true,
						cancelIsShow:true,
						confirmFun:function(){
							Wd.hide();
						},
						cancelFun:function(){
							Wd.hide();
						},
						callback:function(){
						}
					});
					return false;
				}
				//$('#J-iframe-placeholder').attr('src', baseUrl+'/adAdmin/forwardSpaceHolder');
				$('#J-panel-img-position').show();
				
			}else{
				Wd.show({
					mask:true,
					title:'提示',
					content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">必须先选择"广告位参数"，才能载入占位符图片</h4></div></div>',
					confirmIsShow:true,
					cancelIsShow:true,
					confirmFun:function(){
						Wd.hide();
					},
					cancelFun:function(){
						Wd.hide();
					},
					callback:function(){
					}
				});
				return false;
			}
		}else{
			$('#J-panel-img-position').hide();
		}
	});
	
	//检测页面
	var checkPage = function(){
		var dom = $('#J-input-page-title'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().find('.ui-check').show();
			isPass = false;
		}else{
			dom.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-page-title').blur(function(){
		checkPage();
	});
	
	//检测标题
	var checkTitle = function(){
		var dom = $('#J-input-title'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().find('.ui-check').html('<i></i>广告位置名称不能为空').show();
			isPass = false;
		}else if(WidthCheck(domv) > 20){
			dom.parent().find('.ui-check').html('<i></i>广告位置名称不能超过20个字符').show();
			isPass = false;
		}else{
			dom.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-title').blur(function(){
		checkTitle();
	}).keyup(function(){
		var me=this,v=me.value;
		me.value = v = v.replace(/[^\a-\z\A-\Z0-9_\u4E00-\u9FA5]/g,'');
	});
	
	//检测广告参数
	var checkParam = function(){
		var dom = $('#J-input-params-title'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			$('#J-input-params-title').parent().find('.ui-check').show();
			isPass = false;
		}else{
			$('#J-input-params-title').parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	
	//检测图片高
	var checkHeight = function(){
		var dom = $('#J-input-img-h'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().parent().find('.ui-check').show();
			isPass = false;
		}else{
			dom.parent().parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-img-h').blur(function(){
		checkHeight();
	});
	
	$('#J-input-img-h').blur(function(){
		checkImg();
	});
	
	//检测预览图
	var checkImg = function(){
		var dom = $('#J-input-img-id'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			$('#J-panel-upload').find('.ui-check').show();
			isPass = false;
		}else{
			$('#J-panel-upload').find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	
	//检测占位图
	var checkPlaceholder = function(){
		var dom = $('#J-input-placeholder'),isPass = false,isNeed = false;
		$('#J-panel-radio-position').find('input[type="radio"]').each(function(){
			if(this.checked && this.value == '1'){
				isNeed = true;
				return false;
			}
		});
		if(!isNeed){
			$('#J-panel-img-position').find('.ui-check').hide();
			return true;
		}
		if(gatherDftImgs() == ''){
			$('#J-panel-img-position').find('.ui-check').html("<i></i>请选择或上传一个占位图");
			$('#J-panel-img-position').find('.ui-check').show();
			isPass = false;
		}else{
			$('#J-panel-img-position').find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	
	//提交表单
	$('#J-button-submit').click(function(){
		if(checkPage() && checkTitle() && checkParam() && checkHeight() && checkImg() ){
			//获取广告位的各种属性
			var id=$("#J-input-id").val();
			var width=$("#J-input-img-w").val();
			var height=$("#J-input-img-h").val();
			var paramId=$("#J-input-param-id").val();
			var name=$("#J-input-title").val();
			var urlTarget=$("[name = radioTarget]:checked").val();
			var dftImg=$("#J-input-img-id").val();
			var isUsed=1;
			var isDftUsed=$("[name = radioPosition]:checked").val();
			var pageId=$("#J-input-page-id").val();
			
			var dftImgs = gatherDftImgs()
			
			$.ajax({
				url:baseUrl+'/adAdmin/saveEditAdSpace',
				dataType:'json',
				type: "post",
				data:{id:id, width:width, height:height, paramId:paramId,name:name,urlTarget:urlTarget,dftImg:dftImg,isUsed:isUsed,dftImgs:dftImgs,isDftUsed:isDftUsed,pageId:pageId},
				success:function(data){
					if(Number(data['status']) == 1){
						Wd.show({
							mask:true,
							title:'提示',
							confirmText:'继续创建',
							cancelText:'进入管理',
							content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-dtext">创建成功</h4></div></div>',
							confirmIsShow:true,
							cancelIsShow:true,
							confirmFun:function(){
								Wd.hide();
								location.href = baseUrl+'/adAdmin/editAdSpace';
							},
							cancelFun:function(){
								Wd.hide();
								location.href = baseUrl+'/adAdmin/queryAllAdSpace';
							},
							callback:function(){
							}
						});
						
					}else{
						alert(data['msg']);
					}
				}
			});
		}
	});	
	
	$("#J-file-placeholder-href").click(function(){
		var fileName = $("#J-file-placeHolder").val();
		if(fileName == ''){
			alert("请选择文件后在上传")
			return;
		}else{
			//hideMessage($("#J-file-href"));
		}
		var ss = $("#J-file-placeHolder").val().split(".");
		ajaxFileUpload(2);
	});
	
	$('#J-upload-file').click(function(e){
		if($.trim($('#J-input-param-id').val()) == ''){
			Wd.show({
				mask:true,
				title:'提示',
				content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">必须先选择"广告位参数"，才能进行图片上传操作</h4></div></div>',
				confirmIsShow:true,
				cancelIsShow:true,
				confirmFun:function(){
					Wd.hide();
				},
				cancelFun:function(){
					Wd.hide();
				},
				callback:function(){
				}
			});
			e.preventDefault();
		}
		
	});
	
	//文件上传按钮
	$('#J-upload-file').bind('propertychange',function(){
		$("#J-form-upload").submit()
	}).bind('change',function(){
		$("#J-form-upload").submit()
	});
	
	//文件上传按钮
	$('#J-upload-file-placeholder').bind('propertychange',function(){
		$("#imgHeigh").val($("#J-input-img-h").val());
		$("#imgWidth").val($("#J-input-img-w").val());
		$(this).parent().submit();
	}).bind('change',function(){
		if($("#J-iframe-placeholder").contents().find('img').length >=4)
		{
			$('#J-panel-img-position').find('.ui-check').html("<i></i>占位图最多可上传4张");
			$('#J-panel-img-position').find('.ui-check').show();
			return;
		}
		$("#imgHeigh").val($("#J-input-img-h").val());
		$("#imgWidth").val($("#J-input-img-w").val());
		$(this).parent().submit();
	});
	
	var table = $('#J-panel-img-position');
	//缩略图
	var Tip = phoenix.Tip.getInstance();
	var widthSrc=$("#J-input-img-w").val();
	var heighSrc=$("#J-input-img-h").val();
	table.on('mouseenter', '.text-left', function(){
		var dom = $(this),src = dom.attr('src');
		Tip.setText('<img width="'+widthSrc+'" height="'+heighSrc+'" src="'+ src +'" /><div style="text-align:center;font-size:12px;color:#333;line-height:180%;"></div>');
		Tip.show(dom.width(), 0, this);
		dom.parent().addClass('bg-disable');
	}).on('mouseleave', '.text-left', function(){
		Tip.hide();
		$(this).parent().removeClass('bg-disable');
	});
	
})