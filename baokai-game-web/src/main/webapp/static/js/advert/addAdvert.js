function global_img(data){
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
$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
	//弹窗
	var Wd = phoenix.Message.getInstance(),
	 	start = $('#J-time-start'),
		end = $('#J-time-end');
		
	var time_now,
	dateUtil = {},
	time_now = new Date();
	time_now.setDate(time_now.getDate()-1);
	dateUtil = {
		now:time_now,
		//获取当前日期前后n秒的日期
		getOneDateTime:function(now, n){
			var now_ms = now.getTime(),n = n || 0,d_n = now_ms + n * 1000,d2 = new Date();
			d2.setTime(d_n)
			return d2;
		},
		getYestodayBound:function(){
			var me = this,now = me.now,result = [],d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate() - 1);
			result.push(me.formatDateToString(d, true));
			result.push(me.formatDateToString(d, false));
			return result;
		},
		getTodayBound:function(){
			var me = this,now = me.now,result = [],d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate());
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
			return result;
		},
		//前一周时间
		//7天前的 00:01 + 今天已过的时间
		//今天当成1天计算
		getBeforeWeekBound:function(){
			var me = this,now = me.now,result = [],d = new Date();
			d.setFullYear(now.getFullYear());
			d.setMonth(now.getMonth());
			d.setDate(now.getDate() - 7);
			result.push(me.formatDateToString(d, true));
			result.push(now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate());
			return result;
		},
		formatDateToString:function(d, isFirst){
			var str = isFirst ? '00:01' : '23:59';
			return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate();
		}
	};
	
	start.focus(function(){
		var dt = new phoenix.DatePicker({input:this,isShowTime:true,setDisabled:function(){
			var me = this,tds = me.getContent().find('td'),it,tempDate,_y,_m,_d;
				//n天前的某个日期
				before = dateUtil.getOneDateTime(time_now, 1*3600*24*Number(3650));
				
			tds.each(function(){
				it = $(this);
				_y = Number(it.attr('data-year'));
				_m = Number(it.attr('data-month'));
				_d = Number(it.text());
				tempDate = new Date(_y, _m, _d);
				if(tempDate < dateUtil.now){
					it.addClass('day-disabled');
				}
			});
		}});
			dt.show();
			dt.addEvent('afterSetValue', function(){
				$(this).parent().find('.ui-check').hide();
			});
	});
	end.focus(function(){
		var dt = new phoenix.DatePicker({input:this,isShowTime:true,setDisabled:function(){
			var me = this,tds = me.getContent().find('td'),it,tempDate,_y,_m,_d;
				//n天前的某个日期
				before = dateUtil.getOneDateTime(time_now, 1*3600*24*Number(3650));
				
			tds.each(function(){
				it = $(this);
				_y = Number(it.attr('data-year'));
				_m = Number(it.attr('data-month'));
				_d = Number(it.text());
				tempDate = new Date(_y, _m, _d);
				if(tempDate < dateUtil.now){
					it.addClass('day-disabled');
				}
			});
		}});
			dt.show();
			dt.addEvent('afterSetValue', function(){
				$(this).parent().find('.ui-check').hide();
			});
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
	$("#J-panel-ad-position").on('click','.checkbox',function(e){
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
					this.disabled = true;
					this.checked = true;
				}
			});
		}else{
			ipts.each(function(){
				if(me != this){
					this.disabled = false;
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
		}else if(WidthCheck(titleValue) > 40){
			title.parent().find('.ui-check').html('<i></i>广告名称不能超过20个字').show();
			isPass = false;
		} else{
			title.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-input-title').blur(function(){
		checkTitle();
	})
	
	/*$('#J-input-title').blur(function(){
		checkTitle();
	}).keyup(function(){
		var me=this,v=me.value;
		me.value = v = v.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'');
	});*/
	
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
			time2 = $('#J-time-end'),timev2 = $.trim(time2.val()),
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
				var startDate = new Date(starttime);
				var endDate = new Date(endtime);
				var url = baseUrl + '/adAdmin/addAdvert?ids='+ groupv +'&positionv=' + positionv;
				var data = 'name='+titlev+'&targetUrl='+linkv+'&gmtEffectBegin='+startDate.getTime()+'&gmtEffectEnd='+endDate.getTime()+'&num='+Math.random()+'&status='+status+'&imgUrl='+imgv;//+'&ids='+groupv+'&positionv='+positionv;
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
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">创建成功</h4></div></div>',
								confirmIsShow:true,
								cancelIsShow:true,
								confirmFun:function(){
									Wd.hide();
									location.href = location.href;
								},
								cancelFun:function(){
									Wd.hide();
									location.href = baseUrl +'/adAdmin/queryPublishAdPage';
								},
								callback:function(){
								}
							});
                        } else if(data.status == 100320) {
                            alert(data.message);
                        } else{
							alert(data.message);
						}
					},
					error: function(xhr,status,errMsg){
					alert(errMsg);
					}
					});
			}
	};
	var checkURL=function() {
            var strRegex = "^((https|http|ftp|rtsp|mms)://)[a-z0-9A-Z]{3}\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$";
            var re = new RegExp(strRegex);
            if (re.test($("#J-input-link").val())) {
				$("#J-input-link").parent().find(".ui-check").hide();
                isPass = true;
            } else {
				$("#J-input-link").parent().find(".ui-check").show();
                isPass = false;
            }
			return isPass;
     }
	 
	$("#J-input-link").focus(function(){
		$(this).parent().find(".ui-check").hide();
	});
	
	/*$("#J-input-link").blur(function(){
		   checkURL();
	}).focus(function(){
		$(this).parent().find(".ui-check").hide();
	});*/
	
		
	//提交表单
	$('#J-button-submit').click(function(){
		doSubmit(1);
	}); 
	
	//提交表单
	$('#J-button-save').click(function(){
		doSubmit(4);
	});

	//文件上传按钮
	$('#J-upload-file').on('propertychange',function(){
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
		
	}).on('change',function(){
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
});