<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$result[0].title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/service/service.css" />
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
						<a href="/Service/servicesup?unread={$unread}">给上级发信</a>
						{/if}
						{if $smarty.session.datas.info.userLvl >= 0}
						<a href="/Service/servicesub?unread={$unread}">给下级发信</a>
						{/if}
					</div>	站内信(<span id="msgcount2">0</span>)
				</div>
				<div class="content">
					<div class="ui-tab">
						<div class="ui-tab-content" style="height:600px;overflow-y:auto;position:relative;">
							<ul class="ui-form form-mail-info">
								<li>
									<label for="" class="ui-label">主题：</label>
									<span class="ui-text-info" id="title">{$result[0].title}</span>
								</li>
								<li>
									<label for="" class="ui-label">发件人：</label>

									<span class="ui-text-info" id="sender">
									{if $parentaccount eq $result[0].sendAccount}
										上级
									{else}
										{if $result[0].sendAccount eq $smarty.session.datas.info.account}
											我
										{else}
											{$result[0].sendAccount}
										{/if}
									{/if}						
									</span>
								</li>
								<li>
									<label for="" class="ui-label">时间：</label>

									<span class="ui-text-info">{$result[0].sendTime}</span>


								</li>
								<li>
									<label for="" class="ui-label">收件人：</label>
									<span class="ui-multiline" id="receiver" style="word-wrap:break-word;">
									{if $parentaccount eq $result[0].receiveAccount}
										上级
									{else}
										{if $result[0].receiveAccount eq $smarty.session.datas.info.account}
											我
										{else}
											{$result[0].receiveAccount}
										{/if}
									{/if}
									</span>

								</li>
							</ul>
							
							<input type="hidden" name="sendMsgRout" id="sendMsgRout" value="{$result[0].sendMsgRout}">
							<input type="hidden" name="rootId" id="rootId" value="{$result[0].id}">
													
							<!-- {if $result[0].sender gt 0} -->	
							<div class="dia-list" id="divApendto">
							{foreach from=$result key=k item=v}
								{if $v.sendAccount eq $result[0].sendAccount}		
										
									<dl class="dia-list-left">
									<dt><strong>
									{if ($v.sendAccount neq $smarty.session.datas.info.parentAccount ) and ( $v.sendAccount neq $smarty.session.datas.info.account)}
											{$v.sendAccount}
									{else}
										{if $v.sendAccount eq $smarty.session.datas.info.parentAccount}
											上级代理
										{else}
											{if $v.sendAccount eq $smarty.session.datas.info.account}
												我
											{/if}
											
										{/if}
									{/if}
									</strong> {$v.sendTime}</dt>
									<dd><i pids={$v.id} class="tri"></i>{$v.content}</dd>
								</dl>
								{else}
							
								<dl class="dia-list-right" id="DivMessage">
									<dt><strong>
									
									{if ($v.sendAccount neq $smarty.session.datas.info.parentAccount ) and ( $v.sendAccount neq $smarty.session.datas.info.account)}
											{$v.sendAccount}
									{else}
										{if $v.sendAccount eq $smarty.session.datas.info.parentAccount}
											上级代理
										{else}
											{if $v.sendAccount eq $smarty.session.datas.info.account}
												我
											{/if}
										{/if}
									{/if}	
									</strong> {$v.sendTime}</dt>
									<dd ><i pids={$v.id} class="tri"></i>{$v.content} </dd>
								</dl>			
								{/if}
								
							{/foreach}		 
							</div>
							<div  class="dia-list"  id="isDivShow">
								<div class="dia-input">
								<div class="textarea"   id="openDIv">
								<textarea id="txttarea" >点击回复</textarea>
								</div>
								<div class="dia-btn">
								</div>
								<div class="dia-input dia-input-current"  >
									
									<div class="textarea"  id="showDiv" style=" display:none">
										<textarea id="clickTos"></textarea>
									</div>
									<div class="dia-btn">
										<a class="btn btn-important" href="javascript:void(0);" id="btnReply">回 复<b class="btn-inner"></b></a>
										<a class="btn" href="javascript:void(0);" id="btnDelete">删 除<b class="btn-inner"></b></a>
									</div>
								</div>
							</div>
						</div>
						<!-- {else} -->
						<div class="form-mail-text"  style="word-break:break-all">{$result[0].content}</div>
						<!-- {/if} -->
					</div>
				</div>
			</div>
		</div>
</div></div>
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
	<div style="position:absolute;left:100px;display:none" class="pop w-4">
		<i class="ico-success"></i>
		<h4 class="pop-text">删除成功</h4>
	</div>
	
	<div style="position:absolute;left:400px;display:none" class="pop w-4">
		<i class="ico-error"></i>
		<h4 class="pop-text">删除失败，请重试</h4>
	</div>
	

{literal}
<script> 
    //获取上级对象    
    (function($){
	
		// FirstLoad();
		 var arg = GetRequest(),ids,pros;
		 var myDate=GetDateT();//当前时间
		 
		 //获取URL传参数据
		 if (arg != undefined) { var aid = arg["id"];var pro=arg["pro"]; if (aid != undefined) {	ids=aid;}	if(pro != undefined){pros=pro;}	}
		 
		//回复是否显示判断
		var receivers= $.trim($('#receiver').html());
		var counts=receivers.replace(/[^,]/g,'').length;		
		if(receivers=='' || counts>0){	$('#isDivShow').css("display","none"); return false;}
		
		
		//点击回复展开
		$('#txttarea').focus(function(){	$('#showDiv').show();	$('#openDIv').hide();	$('#clickTos').focus();	});
		
		//删除信件
		$('#btnDelete').click(function (e){					
			if(!confirm("您确定要删除该组站内信吗？\n删除后将不能恢复")) { 
				e.preventDefault();
				window.e.returnValue = false;
			}
			
			if(ids==''){	e.preventDefault();return false;}				
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:"/Service/deletemessages",							
				data:'id='+ids+'&pro='+pros,
				beforeSend:function(){
					//禁用发送								
					$('#btnDelete').val("删除中...");						 	
					$("#btnDelete").css("color", "#CACACA").css("background-color", "#CACACA");　			
					$("#btnDelete").attr("disabled","disabled");
				},	
				success:function(json){						
					if(json['status']=="0"){	window.location = "/Service/inbox";	}			
					else{	alert("删除信息失败");	}
				},
				complete:function(){	$('#btnDelete').removeAttr("disabled"); 	}
			});
			
			
			
			
		});
	
		//发送信件
	    $('#btnReply').click(function (e){		
				
			$('#Txttitle').css('border','1px solid #CACACA'); 			
			if($('#clickTos').val()==''){	$('#clickTos').css('border', '1px solid red');	e.preventDefault();	return false;}		
			try{ sendData();  }
			catch(ex){	
			}				
		});
		

		//获取url中"?"符后的字串
		function GetRequest() {
			var url = location.search;   
			var json = {};
			if (url.indexOf("?") != -1) {
				var str = url.substr(1);
				strs = str.split("&");
				for (var i = 0; i < strs.length; i++) {
					json[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
				}
			}
			return json;
		}
		
		//取当前时间，格式为,yyyy-mm-dd hh:mm:ss
		function GetDateT()
		{
		  var d,s;
		  d = new Date();
		  s = d.getYear() + "-";             
		  s = s + (d.getMonth() + 1) + "-";
		  s += d.getDate() + " ";        
		  s += d.getHours() + ":";      
		  s += d.getMinutes() + ":";   
		  s += d.getSeconds();        	   
		  return(s);  		  
		  
		} 
		
		//发送信件方法
		function sendData()
		{			
			$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:"/Service/replymessage",							
			data:{'rootId':$.trim($('#rootId').val()),'content':$.trim($("#clickTos").val()),'sendAccount':$.trim($('#sender').html()),'receiveAccount':$.trim($('#receiver').html())},
			beforeSend:function(){
				//禁用发送								
				$('#btnReply').val("回复中...");						 	
				$("#btnReply").css("color", "#CACACA").css("background-color", "#CACACA");　			
				$("#btnReply").attr("disabled","disabled");
			},	
			success:function(json){	
			
				if(json['status']=="0"){	
					//发送成功后，在层里无刷新注入					   
					window.location.reload(); 
					$("#clickTos").val('');					
				}			
				else{ alert(json['data']);}
			},
			error:function(xhr, type){	alert("信息发送失败");	},
			complete:function(){
				$('#btnReply').val("回 复");
				$('#btnReply').removeAttr("disabled"); 	
				$('#clickTos').css('border','1px solid #CACACA'); 
			}
			});
		}
		
    })(jQuery);
</script>

{/literal}
</body>
</html>
