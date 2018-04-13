<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
    {include file='/default/script-base.tpl'}
<body>
<!-- //////////////头部公共页面////////////// -->
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="g_33 common-main">
		<div class="g_5">
		<!-- //////////////左侧公共页面////////////// -->
			{include file='/default/ucenter/left.tpl'}
		<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">
					<div class="more">
						{if $smarty.session.datas.info.userLvl neq 0}
						<a href="/Service2/servicesup?unread={$unread}" class="current">给上级发信</a>
						{/if}
						{if $smarty.session.datas.info.userLvl >= 0}
						<a href="/Service2/servicesub?unread={$unread}">给下级发信</a>
						{/if}
					</div>	站内信(<span id="msgcount2">0</span>)
				</div>
				<form method="POST"     id="J-form">
				<div class="content">
					<div class="ui-tab">
						<div class="ui-tab-content">
							<ul class="ui-form form-mail">
								<li>
									<label class="ui-label" >收件人：</label>
									<span class="ui-text-info">上级代理</span>
								</li>
								<li>
									<label class="ui-label" for="text">正文：</label>								
									<textarea name="content"  class="textarea" id="TxtText" style=" width:420px;height:150px"  ></textarea>									
									<div class="ui-text-prompt">正文长度不得超过300字符</div>															
									<div class="ui-check"><i class="error"></i>请输入正文,长度不得超过300字符</div>
								</li>
								<li class="ui-btn">
									 <a href="javascript:void(0);" class="btn btn-disabled" id="subSendDisabled">发 送<b class="btn-inner"></b></a>
									<a href="javascript:void(0);" class="btn btn-important" style="display:none" id="subSend">发 送<b class="btn-inner"></b></a>
									<a href="javascript:void(0);" class="btn" id="CancelRrturn">取 消<b class="btn-inner"></b></a>
								</li>
							</ul>
						</div>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
	<div  class="pop w-4" id="DivDeleteok"  style="position:absolute;z-index:2; display:none">
		<i class="ico-success"></i>
		<h4 class="pop-text">发送成功</h4>
	</div>
	
	<div  class="pop w-4" id="DivDeleteno"  style="position:absolute;z-index:2; display:none">
		<i class="ico-error"></i>
		<h4 class="pop-text">发送失败，请重试</h4>
	</div>
<script type="text/javascript" src="{$path_js}/js/phoenix.Verification.js"></script>
{literal}
<script >     
  (function($){	
  		var TxtText,TxtTextPar,
		//表单检测错误数量
		errorTypes = ['TxtText'],
		errorHas = {},
		setErrorNum = function(name, num){
		if(typeof errorHas[name] != 'undefined'){
			errorHas[name] += num;
			errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
			}
		};
		$.each(errorTypes, function(){
			errorHas[this] = 0;
		});
		var checkContent = function(){
			var v2 =$.trim($("#TxtText").val());
			if(v2 == '')
			{
				setErrorNum('TxtText', 1);
			}else{
				setErrorNum('TxtText', -1);
			}
		};
		var checkForm = function(){
			checkContent();
			var err = 0;		
			$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
			});
		    if(err > 0){ 	
			  $('#subSend').hide();
			  $('#subSendDisabled').show();
			}else
			{
			  $('#subSend').show();
			  $('#subSendDisabled').hide();
			}
		};
		checkForm();	
                
		//正文验证
		TxtText = $('#TxtText');
		TxtTextPar = TxtText.parent();	
		TxtText.blur(function(){
			var v = $.trim(this.value);			
			v = v.replace(/[^\x00-\xff]/g, 'xx');
			if(v == '' || v.length>300){		
				TxtTextPar.find('.ui-check').css('display', 'inline');	
				TxtTextPar.find('.ui-text-prompt').css('display', 'none');
				setErrorNum('TxtText', 1);
			}else{				
				TxtTextPar.find('.ui-check').css('display', 'none');
				setErrorNum('TxtText', -1);
			}			
			checkForm();	
		}).focus(function(){
			TxtTextPar.find('.ui-check').css('display', 'none');
			TxtTextPar.find('.ui-text-prompt').css('display', 'inline');
			checkForm();	
		});	
		
    
		//操作后提示	 
		function fn(obj){
			var Idivdok = document.getElementById(obj);	
			Idivdok.style.display="block";						
			Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
			Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
		} 	
		
		function codefans(obj){	$('#'+obj).css("display","none");}			
		//取消返回上一页
		$('#CancelRrturn').click(function (){	window.location = "/Service2/inbox";	});    	
	
      //发送
	  $('#subSend').click(function(e){		  	
	    var err = 0;		
		$.each(errorTypes, function(){
		if(typeof errorHas[this] != 'undefined'){
			err += errorHas[this];
		}
		});
		if(err > 0){ e.preventDefault(); return false;	}
		var TxtText=$('#TxtText').val(),TxtText = TxtText.replace(/[^\x00-\xff]/g, 'xx'),istrue=true;	
	    if(TxtText == ''|| TxtText.length>300)  { TxtTextPar.find('.ui-check').css('display', 'inline'); TxtTextPar.find('.ui-text-prompt').css('display', 'none'); e.preventDefault();   istrue= false;  }			
		if(istrue==false){	e.preventDefault(); return false;}		
		$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:"/service2/sendmessage",						
				data:'content='+$("#TxtText").val().trim()+'&typeid='+2,	
				beforeSend:function(){
					//禁用发送												
					$('#subSendDisabled').text("发送中...");						 	
					$('#subSend').hide();
			        $('#subSendDisabled').show();				
				},	
				success:function(json){	
					if(json !=null){
						if(json['status']=="0"){	fn("DivDeleteok");	window.location = "/Service2/inbox";	}
						else{	fn("DivDeleteno"); setTimeout(function(){codefans("DivDeleteno");},1500);	}			
					}	
					else
					{
						fn("DivDeleteno"); setTimeout(function(){codefans("DivDeleteno");},1500);
					}	
					$('#subSendDisabled').text("发送"); 
				},
				error:function(xhr, type){	
				    fn("DivDeleteno"); 
					setTimeout(function(){codefans("DivDeleteno");},1500);	
				},
				complete:function(){
					$('#subSendDisabled').text("发送");
					$('#subSend').hide();
			        $('#subSendDisabled').show();
				}
		});		
	}); 	
  })(jQuery);
</script>
{/literal}
</body>
</html>