function uploadResponse(str){
	$('#J-textarea-username').val(str);
	$('#J-textarea-username').focus();
}
$(document).ready(function(){
	$('.menu-list li:eq(9)').attr("class","current");
	var editor = $('#J-content').xheditor({upImgUrl:baseUrl+"/xheditorAdmin/uploadTemplateImg",upImgExt:"jpg,jpeg,gif,png",html5Upload:false});
	var textareaUsername = new phoenix.Input({el:'#J-textarea-username',defText:'多个用户名用；分开'});
	var InputTitle = new phoenix.Input({el:'#J-input-title',defText:'主题长度不得超过40字符'});
	var formKeys = ['user', 'title', 'content', 'time','date'];
	var formHas = {user:false,title:false,content:false,time:true,date:true};
	function WidthCheck(str){  
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
	}  
	var checkUser = function(){
		//检测是否有填写用户名
		var types = $('#J-radio-list').find('input[type="radio"]'),typesValue = '1',ipts,checkNum = 0,isPass = false;
		types.each(function(){
			var v = this.value;
			if(this.checked){
				typesValue = v;
				return false;
			}
		});
		if(typesValue == '1'){
			ipts = $('.J-panel-gourp').find('input[type="checkbox"]');
			ipts.each(function(){
				if(this.checked){
					checkNum += 1;
				}
			});
			if(checkNum < 1){
				$('.J-panel-gourp').find('.ui-check').show();
				isPass = false;
			}else{
				$('.J-panel-gourp').find('.ui-check').hide();
				isPass = true;
			}
		}else{
			var usernames = $('#J-textarea-username'),userValue = $.trim(usernames.val()).replace(textareaUsername.defConfig.defText, '');
			if(userValue == ''){
				usernames.parent().find('.ui-check').show();
				isPass = false;
			}else{
				usernames.parent().find('.ui-check').hide();
				isPass = true;
			}
		}
		formHas['user'] = isPass;
		checkForm();
	};
	$('.J-panel-gourp').find('input[type="checkbox"]').click(function(){
		if(this.checked){
			$('.J-panel-gourp').find('.ui-check').hide();
		}
		checkUser();
	});
	$('#J-textarea-username').blur(function(){
		checkUser();
	});
	
	
	
	var checkTitle = function(){
		//检测标题
		var title = $('#J-input-title'),titleValue = $.trim(title.val()).replace(InputTitle.defConfig.defText, ''),titleValue2 = titleValue.replace(/[^\x00-\xff]/g, 'xx');
		if(titleValue == ''){
			title.parent().find('.ui-check').html('<i></i>主题不能为空').show();
			isPass = false;
		}else{
			if(WidthCheck(titleValue2) > 40){
				title.parent().find('.ui-check').html('<i></i>主题长度不得超过40字符').show();
				isPass = false;
			}else{
				title.parent().find('.ui-check').hide();
				isPass = true;
			}
		}
		formHas['title'] = isPass;
		checkForm();
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
	
	editor.settings.blur=function(){
		checkContent();
		checkForm();
	};
	
	var checkContent = function(){
		var v = $.trim($('#J-content').val());
		if(v == ''){
			formHas['content'] = false;
		}else{
			formHas['content'] = true;
		}
		var v2 =$.trim($("#J-input-title").val());
		if(v2 == '' || v2 == InputTitle.defConfig.defText)
		{
	   	    formHas['title'] = false;
		}else{
			formHas['title'] = true;
		}
	};
	
	
	var checkTime = function(){
		var isPass = false;
		if($('#J-select-date').val() == '1' && $.trim($('#J-daatepicker').val()) == ''){
			$('#J-daatepicker').parent().find('.ui-check').show();
			isPass = false;
		}else{
			$('#J-daatepicker').parent().find('.ui-check').hide();
			isPass = true;
		}
		
		formHas['time'] = isPass;
		checkForm();
	};
	

	
	var checkForm = function(){
		var i = 0,len = formKeys.length;
		checkContent();
		for(;i < len;i++){
			if(!formHas[formKeys[i]]){
				$('#J-button-submit').removeClass('btn-important').addClass('btn-disabled');
				return false;
			}
		}
		$('#J-button-submit').removeClass('btn-disabled').addClass('btn-important');
		return true;
	};
	
	$('#J-radio-list input[type="radio"]').bind('click', function(){
		var v = $.trim(this.value);
		if(v == '1'){
			$('.J-panel-gourp').show();
			$('#J-panel-username').hide();
		}else{
			$('.J-panel-gourp').hide();
			$('#J-panel-username').show();
		}
		checkUser();
		$('#J-textarea-username').parent().find('.ui-check').hide();
	});

	
	/*$("#J-upload-file").change(function(){
		var ss = $("#J-upload-file").val().split(".");
		if(ss.length>1)
			{
				var end = ss[ss.length-1];
				if(end!='csv'&&end!='txt'&&end!='CSV'&&end!='TXT')
					{
					alert("文件只支持csv或txt格式");
					return;
					}
			}
		
		ajaxFileUpload();
	});*/
	
	var count = -1;
    $("#J-upload-file").live("change", function () {
        count++;
        var ss = $("#J-upload-file").val().split(".");
		if(ss.length>1)
			{
				var end = ss[ss.length-1];
				if(end!='csv'&&end!='txt'&&end!='CSV'&&end!='TXT')
					{
					alert("文件只支持csv或txt格式");
					return;
					}
			}
		
		ajaxFileUpload();
    $("#J-upload-file").replaceWith('<input name="file" type="file" id="J-upload-file" class="upload-file-button" size="40" hidefocus="true" value="导入" title='+count+'>');
})
	
	
	

	$('#J-input-time').keyup(function(){
		var v = this.value;
		this.value= v = v.replace(/[^\d|^\.]/g, '');
		
		if(v.length>0){
			if(v.substr(0,1) =='0'){
				v=v.substr(1,v.length);
			}
			this.value = v.replace(/[^\d]/g, '');
		}
		
	});
	$('#J-input-time').keyup(function(){
		var v = this.value;
		this.value= v = v.replace(/[^\d|^\.]/g, '');
		if(v.length>0){
			if(v.substr(0,1) =='0'){
				v=v.substr(1,v.length);
			}
			this.value = v.replace(/[^\d]/g, '');
		}
		
	});
	$('#J-input-time').blur(function(){
		var date=$('#J-input-time'),dateValue=date.val(),isPass=false;
		if(dateValue == ''){
			date.parent().find('.ui-check').html('<i></i>消息有效时间不能为空').show();
			isPass = false;
		}else{
			date.parent().find('.ui-check').hide();
			isPass = true;
		}
		formHas['date'] = isPass;
		checkForm();
	});
	
	$('#J-select-date').change(function(){
		var v = this.value;
		if(v == '1'){
			$('#J-daatepicker').show().focus();
		}else{
			$('#J-daatepicker').hide();
		}
		checkTime();
	});
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
	$('#J-daatepicker').focus(function(){
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
				//console.log(tempDate);
				/*if(tempDate < before || tempDate > dateUtil.now){
					it.addClass('day-disabled');
				}*/
			});
		}});
			dt.show();
			dt.addEvent('afterSetValue', function(){
				$('#J-daatepicker').parent().find('.ui-check').hide();
				checkTime();
			});
	});
	
	
	//表单预览
	$('#J-button-view').click(function(){
		var wd = window.open('');
		wd.document.write(editor.getSource());
	});
	//表单提交
	$('#J-button-submit').click(function(){
		if($(this).hasClass('btn-disabled')){
			return;
		}
		if(WidthCheck($.trim($("#J-content").val())) >2000)
		{
			$("#J-content").parent().find('.ui-check').show()
			return;
		}
		
		if(checkForm()){
		var str = getFormData('#J-form');
		jQuery.ajax({
			type: "POST",
			url: baseUrl+'/noticeAdmin/createMsg',
			data : str,
			dataType :"json",
			beforeSend:function(){
				$('#J-button-submit').removeClass('btn-important').addClass('btn-disabled');
			},
			success: function(data){
				//处理成功
				if(data.status == '1'){
					fnDiv('DivSuccessful');
					setTimeout(function(){ $('#DivSuccessful').css("display","none");
					var parment=$('#J-select-date').val() == '1'?"?msgType=1":"";
					$('#J-button-submit').removeClass('btn-disabled').addClass('btn-important');
					window.location.href=baseUrl+"/noticeAdmin/goSysMsgManager"+parment;
					},3000);
					
				}
				//处理失败
				else{
					fnDiv('DivFailed');
					setTimeout(function(){ $('#DivFailed').css("display","none");},3000); 
				}
			},
			complete:function(){
				//$('#J-button-submit').removeClass('btn-disabled').addClass('btn-important');
			},
			error: function(xhr,status,errMsg){
				alert(errMsg);
			}
			});
		}
	});
	editor.settings.focus=function(){
		$("#J-content").parent().find('.ui-check').hide();
	};
	
	//选择全部客户
	$('#J-checkbox-all').click(function(){
		var me = this,ipts = $('.J-panel-gourp').find('input[type="checkbox"]');
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
		checkUser();
	});
	
	
});

function getFormData(element){
	var select = $(element).find('select');
    var input = $(element).find('input');
    var inputs = $.merge(select,input);
    var data = {};
    var str="";
    $.each(inputs,function(){
        if($(this).attr('type') != undefined){
        if(!$(this).attr('disabled')){
          switch($(this).attr('type')){
            case 'checkbox':
            	if($(this).attr('checked')){
            		if(data[$(this).attr('name')]==undefined){
            			str+=$(this).attr('name')+'='+  $(this).val()+'&';
                	}else{
                		str+=$(this).attr('name')+'='+  $(this).val()+'&';
                	}
            	}
              break;
            case 'radio':
            	if($(this).attr('checked')){
            		str+=$(this).attr('name')+'='+  $(this).val()+'&';
            	}
               break;
            case 'select':
            	if($(this).attr('selected')){
            		str+=$(this).attr('name')+'='+  $(this).val()+'&';
            	}
               break;
            default:
				str+=$(this).attr('name')+'='+  encodeURIComponent($.trim($(this).val()))+'&';
              break;
          }
        }
    }})
    str+='receives='+ $("#J-textarea-username").val()+'&';
    str+='content='+ encodeURIComponent($.trim($("#J-content").val()))+'&';
    return str;
}
function fnDiv(obj){ 
    var Idivdok = document.getElementById(obj); 
    Idivdok.style.display="block"; 
    Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px"; 
    Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
}
