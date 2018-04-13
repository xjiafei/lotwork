    function global_img(data){
    	$('#view').remove();
	if(data['status'] == 1){
		$('#J-input-img-w').val(data['width']);
		$('#J-input-img-h').val(data['height']);
		$('#J-input-img-src').val(data['fileUrl']);
		//$('#view').attr("src",$("#imageServerVisit").val()+''+data.fileUrl);
		$('#J-msg-upload-tip').hide();
	}else{
		$('#J-input-img-w').val('');
		$('#J-input-img-h').val('');
		$('#J-input-img-src').val('');
		//$('#view').attr("src",'');
		$('#J-msg-upload-tip').html('<i></i>' + data['message']).show();
	}
}
var isLoad=false;

$(document).ready(function(){
	//弹窗
	var Wd = phoenix.Message.getInstance(),
	 	start = $('#J-time-start'),
		end = $('#J-time-end');
	start.focus(function(){
		(new phoenix.DatePicker({input:start,isShowTime:true,expands:{show:function(){
			var me = this,pos;
			if(me.input && me.input.size() > 0){
				pos = me.input.offset();
				me.dom.css({left:pos.left,top:pos.top + (me.dom.height()*-1)});
			}
			me.effectShow();
		}}})).show();
	});
	end.focus(function(){
		(new phoenix.DatePicker({input:end,isShowTime:true,expands:{show:function(){
			var me = this,pos;
			if(me.input && me.input.size() > 0){
				pos = me.input.offset();
				me.dom.css({left:pos.left,top:pos.top + (me.dom.height()*-1)});
			}
			me.effectShow();
		}}})).show();
	});

	//用户组
	var checkGroup = function(e){
		var par = $('.ui-form li'),ipts = par.find('input[type="checkbox"]'),num = 0,isPass = false,groupIpts = $('.J-panel-group').find('input[type="checkbox"]'),checkNum = 0,checkNotNum = 0;
		ipts.each(function(){
			if(this.checked){
				num += 1;
			}
		});
		if(num > 0){
			isPass = true;
		}else{
			isPass = false;
			par.find('.ui-check').show();
		}
		if(isPass){
			par.find('.ui-check').hide();
		}
		
		groupIpts.each(function(){
			if(this.checked){
				checkNum += 1;
			}else{
				checkNotNum += 1;
			}
		});
		
		if(e && e.target != ipts.get(0)){
			if(checkNum == groupIpts.size()){
				ipts.get(0).checked = true;
			}
			if(checkNum < groupIpts.size()){
				ipts.get(0).checked = false;
			}
		}
		
		return isPass;
	};
	$('.checkbox-list').on('click', 'input[type="checkbox"]', function(e){
		checkGroup(e);
	});
	$('.checkbox-list').find('input[type="checkbox"]').click(function(){
		if(this.checked){
			$('.J-panel-group').find('.ui-check').hide();
		}
		checkGroup();
	});
	
	
	
	function GetFunction()
	{
		var swidth=0;
	    var sheight=0;
		var arryList=$("#J-panel-ad-position").find(".checkbox");
		for(var i=0;i<arryList.length;i++){
			var obj=$(arryList[i]);
			if(obj.is(":checked"))
			{
				var list= obj.parent().text().replace(")","").split("-")[1].split("*");
				swidth=list[0];
				sheight=list[1];
				isLoad=true;
				break;
			}
		}
		if(isLoad)
		{
			for(var i=0;i<arryList.length;i++){
				var obj=$(arryList[i]);
				var list= obj.parent().text().replace(")","").split("-")[1].split("*");
				if(swidth != list[0] || sheight!=list[1])
				{
					$(arryList[i]).attr("disabled","disabled");
				}
			}
		}
	};
	GetFunction();
	
		
	
	$("#J-panel-ad-position").on('click','.checkbox',function(e){
		$('#J-input-img-src').val("");
		$("#view").html("");
		var arry=$(this).parent().text().replace(")","").split("-")[1].split("*");
		var width=arry[0];
		var height=arry[1];
		var arryList=$("#J-panel-ad-position").find(".checkbox");
		var isCheck=true;
		for(var i=0;i<arryList.length;i++){
			var obj=$(arryList[i]);
			var list= obj.parent().text().replace(")","").split("-")[1].split("*");
			if(obj.is(":checked"))
			{
				isCheck=false;
			}
			if(width != list[0] || height!=list[1])
			{
				obj.attr("disabled","disabled");
			}
		}
		if(isCheck)
		{
			for(var i=0;i<arryList.length;i++){
			 $(arryList[i]).removeAttr("disabled");
			}
		}
	});

	
	
	//选择全部客户
	$('#J-checkbox-all').click(function(){
		var me = this,ipts = $('.J-panel-group').find('input[type="checkbox"]');
		if(this.checked){
			ipts.each(function(){
				if(me != this){
					this.checked = true;
				}
			});
		}else{
			ipts.each(function(){
				if(me != this){
					this.checked = false;
				}
			});
		}
		checkGroup();
	});
	
	
	
	//广告位
	var checkPostion = function(){
		var par = $('#J-panel-ad-position'),ipts = par.find('input[type="checkbox"]'),num = 0,isPass = false;
		ipts.each(function(){
			if(this.checked){
				num += 1;
			}
		});
		if(num > 0){
			isPass = true;
		}else{
			isPass = false;
			par.find('.ui-check').show();
		}
		if(isPass){
			par.find('.ui-check').hide();
		}
		return isPass;
	};
	$('#J-panel-ad-position').on('click', 'input[type="checkbox"]', function(){
		checkPostion();
	});
	
	
	//标题
	var checkTitle = function(){
		var title = $('#J-input-title'),titleValue = $.trim(title.val()),isPass = false;
		if(titleValue == ''){
			title.parent().find('.ui-check').html('<i></i>广告名称不能为空').show();
			isPass = false;
		}else if(titleValue.length > 20){
			title.parent().find('.ui-check').html('<i></i>广告名称不能超过20个字符').show();
			isPass = false;
		}else{
			title.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-title').blur(function(){
		checkTitle();
	});
	
	
	//图片
	var checkImage = function(){
		var image = $('#J-input-img-src'),imagev = $.trim(image.val()),isPass = false;
		if(imagev == ''){
			$('#J-panel-upload').find('.ui-check').show();
			isPass = false;
		}else{
			$('#J-panel-upload').find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	
	
	//有效期
	var checkTime = function(){
		//检测标题
		var time = $('#J-time-start'),timev = $.trim(time.val()),isPass = false,
			time2 = $('#J-time-end'),timev2 = $.trim(time2.val());
			start=new Date(timev.replace("-", "/").replace("-", "/")),
			end=new Date(timev2.replace("-", "/").replace("-", "/"));
		if(timev == '' || timev2 == ''){
			time.parent().find('.ui-check').html('<i></i>请选择广告有效期').show();
			isPass = false;
		}else{
			if(end<start){  
			    time.parent().find('.ui-check').html('<i></i>开始日期不能够大于结束日期').show();  
			    isPass = false; 
            }else{
				time.parent().find('.ui-check').hide();
				isPass = true;
			}
		}
		return isPass;
	};
	
	var doSubmit = function(status){
		 if(checkGroup() && checkPostion() && checkTitle() && checkImage() && checkTime()){
				var groupv='',positionv='',titlev,imgv,linkv,starttime,endtime;
				$('.J-panel-group').find('input[type="checkbox"]').each(function(){
					if(this.checked){
						groupv = groupv + $(this).val()+",";
					}
				});
				$('#ralAllGroup').find('input[type="checkbox"]').each(function(){
					if(this.checked){
						groupv = groupv + $(this).val()+",";
					}
				});
				$('#J-panel-ad-position').find('input[type="checkbox"]').each(function(){
					if(this.checked){
						positionv = positionv + $(this).val()+",";
					}
				});
				titlev = $.trim($('#J-input-title').val());
				imgv = $('#J-input-img-src').val();
				linkv = $('#J-input-link').val();
				starttime = $('#J-time-start').val();
				endtime = $('#J-time-end').val(); 
				starttime = starttime.replace(/-/g,"/");
				endtime = endtime.replace(/-/g,"/");
				var id=$("#id").val();
				var startDate = new Date(starttime);
				var endDate = new Date(endtime);
				var url = baseUrl + '/adAdmin/updateAdvert?ids='+ groupv +'&positionv=' + positionv;
				var data = 'name='+titlev+'&targetUrl='+linkv+'&gmtEffectBegin='+startDate.getTime()+'&gmtEffectEnd='+endDate.getTime()+'&num='+Math.random()+'&status='+status+'&id='+id+'&imgUrl='+imgv;//+'&ids='+groupv+'&positionv='+positionv;
				 jQuery.ajax({
					type: "post",
					url: url,
					data : data,
					dataType :"json",
					success: function(data){
						if(data.status == 1){
							Wd.show({
								mask:true,
								title:'提示',
								confirmText:'继续创建',
								cancelText:'进入管理',
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">修改成功</h4></div></div>',
								confirmIsShow:true,
								cancelIsShow:true,
								confirmFun:function(){
									Wd.hide();
									location.href = baseUrl + '/adAdmin/toAddAdvert';
								},
								cancelFun:function(){
									Wd.hide();
									location.href = baseUrl +'/adAdmin/queryPublishAdPage';
								},
								callback:function(){
								}
							});
						}else{
							alert(data.message);
						}
					},
					error: function(xhr,status,errMsg){
					alert(errMsg);
					}
					});
			}
	}
	
	var selectSpaces = $("#selectSpaces").val();
	if(selectSpaces != '' && selectSpaces!= null){
		var ids = selectSpaces.split(',');
		for(var i=0;i<ids.length;i++){
			$("input[name='spaceCheckBox']").each(function(){
				   if($(this).val() == ids[i])
				   $(this).attr("checked",true);
				  }); 
		}
	}

	
	//文件上传按钮
	$('#J-upload-file').bind('propertychange',function(){
		var ids = '';
		$('#J-panel-ad-position').find('input[type="checkbox"]').each(function(){
			if(this.checked){
				ids = ids + $(this).val()+",";
			}
		});
		if(ids ==''){
			$('#J-msg-upload-tip').html('<i></i>' + '请先选择广告位').show();
			return;
		}
		$("#J-form-upload").attr('action',baseUrl + '/adAdmin/uploadImg?ids='+ids+'&num=' + Math.random());
		$(this).parent().submit();
		
	}).bind('change',function(){
		var ids = '';
		$('#J-panel-ad-position').find('input[type="checkbox"]').each(function(){
			if(this.checked){
				ids = ids + $(this).val()+",";
			}
		});
		if(ids ==''){
			$('#J-msg-upload-tip').html('<i></i>' + '请先选择广告位').show();
			return;
		}
		$("#J-form-upload").attr('action',baseUrl + '/adAdmin/uploadImg?ids='+ids+'&num=' + Math.random());
		$(this).parent().submit();
	});

	/*function ajaxFileUpload() {
		var ids = '';
		$('#J-panel-ad-position').find('input[type="checkbox"]').each(function(){
			if(this.checked){
				ids = ids + $(this).val()+",";
			}
		});
	    $.ajaxFileUpload
		    (  
		        {  
		            url: baseUrl + '/adAdmin/uploadImg?ids='+ids+'&num=' + Math.random(),
		            secureuri: false,  
		            fileElementId: 'J-upload-file',  
		            dataType:'json',  
		            beforeSend: function() {
		            },  
		            complete: function() { 
		            },  
		            success: function(data, status) {
		            	global_img(data);  
		            },  
		            error: function(data, status, e) {  
		                alert(e);  
		            }  
		        }  
		    )  
	    return false;  
	}
	$("#J-file-href").click(function(){
		var fileName = $("#J-upload-file").val();
		if(fileName == ''){
			return;
		}
		var ss = $("#J-upload-file").val().split(".");
		if(ss.length>1)
			{
				var end = ss[ss.length-1];
				if(end!='JPG'&&end!='GIF'&&end!='PNG'&&end!='jpg'&&end!='gif'&&end!='png')
					{
					alert("图片只支持JPG、GIF、PNG格式");
					return;
					}
			}
		ajaxFileUpload();
	});*/
		
	//提交表单
	$('#J-button-submit').click(function(){
		doSubmit(1);
	}); 
	
	//提交表单
	$('#J-button-save').click(function(){
		doSubmit(4);
	});
	
});