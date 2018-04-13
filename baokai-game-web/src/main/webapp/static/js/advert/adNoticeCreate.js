$(document).ready(function(){
	$('.menu-list li:eq(6)').attr("class","current");
	$('.col-side .nav dd:eq(5)').attr("class","current");
	var editor = $('#J-content').xheditor({upImgUrl:baseUrl+"/xheditorAdmin/uploadTemplateImg",upImgExt:"jpg,jpeg,gif,png",html5Upload:false	
	});
	var Wd = phoenix.Message.getInstance(),timeStart = $('#J-input-time-start'),timeEnd = $('#J-input-time-end'),form = $('#J-form');
	timeStart.focus(function(){
		var dt  = new phoenix.DatePicker({input:timeStart,isShowTime:true,});
		dt.addEvent('afterSetValue', function(){
			checkTime();
		});
		dt.show();
	});
	timeEnd.focus(function(){
		var dt  = new phoenix.DatePicker({input:timeEnd,isShowTime:true,});
		dt.addEvent('afterSetValue', function(){
			timeEnd.attr('data-old-time', dt.input.val());
			checkTime();
		});
		dt.show();
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
	//是否为紧急公告
	var isUrgent = function(){
		return !!!$('#J-panel-radio').find('input[type="radio"]').get(0).checked;
	};
	
	//类型切换
	$('#J-panel-radio').find('input[type="radio"]').click(function(){
		var oldtime;
		if(this.value == '1'){
			form.find('.panel-field-urgent').hide();
			timeEnd.attr('disabled', true).val('不限时间').addClass('input-disable');
			$('#J-input-title').parent().find('.ui-prompt').html('(限40个字)');
			$('#J-row-push-proxy').show();
			var dom = $('#J-input-title'),domv = $.trim(dom.val())
			if(!(domv=="")){
			   checkTitle();
			}
		}else{
			oldtime = timeEnd.attr('data-old-time');
			oldtime = !!oldtime ? oldtime : '';
			form.find('.panel-field-urgent').show();
			timeEnd.attr('disabled', false).val(oldtime).removeClass('input-disable');
			$('#J-input-title').parent().find('.ui-prompt').html('(限80个字)');
			$('#J-row-push-proxy').hide();
			var dom = $('#J-input-title'),domv = $.trim(dom.val())
			if(!(domv=="")){
				checkTitle();
			}
		}
	});
	
	
	
	
	//检测页面
	var checkPage = function(){
		var dom = $('#J-select-page'),domv = $.trim(dom.val()),isPass = false;
		if(!isUrgent()){
			dom.parent().find('.ui-check').hide();
			return isPass = true;
		}
		if(domv == ''){
			dom.parent().find('.ui-check').show();
			isPass = false;
		}else{
			dom.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	$('#J-select-page').change(function(){
		checkPage();
	});
	
	
	//检测用户组
	var checkGroup = function(){
		var	ipts = $('.J-panel-group').find('input[type="checkbox"]'),isPass = false,checkNum = 0;
			ipts.each(function(){
				if(this.checked){
					checkNum += 1;
				}
			});
			if(checkNum < 1){
				$('.J-panel-group').find('.ui-check').show();
				isPass = false;
			}else{
				$('.J-panel-group').find('.ui-check').hide();
				isPass = true;
			}
		return isPass;
	};
	$('.J-panel-group').click('input[type="checkbox"]', function(){
		checkGroup();
	});
	$('.J-panel-group').find('input[type="checkbox"]').click(function(){
		if(this.checked){
			$('.J-panel-group').find('.ui-check').hide();
		}
		checkGroup();
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
	
	
	
	
	//检测标题
	var checkTitle = function(){
		var len=isUrgent()?160:80;
		var num=isUrgent()?80:40;
		var dom = $('#J-input-title'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().find('.ui-check').html('<i></i>公告标题不能为空').show();
			isPass = false;
		}else if(WidthCheck(domv) > len){
			dom.parent().find('.ui-check').html('<i></i>公告标题长度不能超过'+num+'个字').show();
			isPass = false;
		}else{
			dom.parent().find('.ui-check').hide();
			isPass = true;
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
	
	//检测生效期
	var checkTime = function(){
		var dom = $('#J-input-time-start'),domv = $.trim(dom.val()),isPass = false,
			dom2 = $('#J-input-time-end'),dom2v = $.trim(dom2.val()),
			start=new Date(domv.replace("-", "/").replace("-", "/")),
			end=new Date(dom2v.replace("-", "/").replace("-", "/"));
		if(isUrgent()){
			if(domv == '' || dom2v == ''){
				dom.parent().find('.ui-check').html('<i></i>请选择生效时间').show();
				isPass = false;
			}else{
			   if(end<start){  
			       dom.parent().find('.ui-check').html('<i></i>开始日期不能够大于结束日期').show();  
			       isPass = false; 
                }else{
					dom.parent().find('.ui-check').hide();
					isPass = true;
				}
			}
		}else{
			if(domv == ''){
				dom.parent().find('.ui-check').html('<i></i>请选择生效时间').show();
				isPass = false;
			}else{
				dom.parent().find('.ui-check').hide();
				isPass = true;
			}
		}
		return isPass;
	};
	
	
	
	//检测内容
	var checkContent = function(){
		var dom = $('#J-content'),domv = $.trim(dom.val()),isPass = false;
		if(domv == ''){
			dom.parent().find('.ui-check').show();
			isPass = false;
		}else{
			dom.parent().find('.ui-check').hide();
			isPass = true;
		}
		return isPass;
	};
	
	
	
	$('#J-button-submit').click(function(){
		if(checkPage() && checkGroup() && checkTitle() && checkTime() && checkContent()){
			submitForm(1);
		}
		
	});
	$('#J-button-save').click(function(){
		if(checkGroup() && checkTitle() && checkTime() && checkContent()){
			submitForm(2);
		}
	});
	
	
	var submitForm = function(createType){
		var typev,pagev,groupv,titlev,contentv,starttime,endtime,ispush,noticelevel;
		
		typev = isUrgent() ? '2' : '1';
		
		pagev = $('#J-select-page').val();
		
		groupv = [];
		$('.J-panel-group').find('input[type="checkbox"]').each(function(){
			if(this.checked){
				groupv.push(this.value);
			}
		});
		groupv = groupv.join(',');
		
		titlev = $.trim($('#J-input-title').val());
		
		contentv = $('#J-content').val();
		noticelevel = $('#J-select-level').val();
		ispush = $('#J-checkbox-proxy').get(0).checked ? '1' : '';
		starttime = timeStart.val();
		endtime = timeEnd.val();
		if(endtime=='不限时间')
		{
			endtime = '';
		}
			$.ajax({
				type:"post",
				url:baseUrl+'/adAdmin/createNotice',
				data:{type:typev,showPages:pagev,groups:groupv,title:titlev,content:contentv,gmtEffectBeginStr:starttime,gmtEffectEndStr:endtime,isPush:ispush,createType:createType,noticelevel:noticelevel},
				dataType:'json',
				success:function(data){
					//data = {status:1,msg:'',data:{}};
					if(Number(data['status']) == 1){
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
								location.href = baseUrl+'/adAdmin/goNoticeListPublish';
							},
							callback:function(){
							}
						});
					}else{
						alert(data["message"]);
					}
					
				}
			});
	};
})