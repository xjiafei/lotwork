<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
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
				<div class="title">通知设置</div>
				
				<div class="content">
					<table class="table table-info">
							<thead>
								<tr>
									<th colspan="3" class="big">勾选订阅类型后将会收到对应形式的通知</th>
								</tr>
							</thead>
							<form action="/Service/shownoticetask/" method="post" id="J-form1">
							<tbody>
								<!-- {foreach from=$result item=preData} -->
								
								<!-- {foreach from=$preData key=key item=data} -->
								<!-- {if $key neq 'cnt'} -->
								<tr>
									<!-- {if $key eq '1'} -->
									<td rowspan="{$preData.cnt}">{$data.module}</td>
									<!-- {/if} -->
									<td class="text-left"><label for="e" class="label"><input type="checkbox" value="{$data.id}" {if $data.actived eq '1'}checked{/if} class="checkbox" name="actived_{$data.id}">{$data.task} </label></td>
									<td class="checkbox-list" style="text-align:left;">
										<!-- {if $data.emailUsed eq '1'} -->
										<label for="e" class="label"><input type="checkbox" value="{$data.id}" {if $data.emailActived eq '1'}checked{/if} class="checkbox" name="email_{$data.id}">邮箱</label>
										<!-- {/if} -->
										<!-- {if $data.innerMsgUsed eq '1'} -->
										<label for="e" class="label"><input type="checkbox" value="{$data.id}" {if $data.innerMsgActived eq '1'}checked{/if} class="checkbox" name="innerMsg_{$data.id}">站内信</label>
										<!-- {/if} -->
										<!-- {if $data.noteUsed eq '1'} -->
										<label for="e" class="label"><input type="checkbox" value="{$data.id}" {if $data.noteActived eq '1'}checked{/if} class="checkbox" name="note_{$data.id}">桌面通知</label>
										<!-- {/if} -->
									</td>
								</tr>
								<!-- {/if} -->
								<!-- {/foreach} -->
								<!-- {/foreach} -->
								<tr>
									<td class="text-center" colspan="4"><a class="btn btn-important" href="javascript:void(0);" id="J-Submit">保存<b class="btn-inner"></b></a></td>
								</tr>
							</tbody>
							</form>
						</table>
				</div>
			</div>
		</div>
		
		</div>
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
	<div class="pop pop-success w-4" style="position:absolute;left:900px;display:none" id="divOperatSuccess">
		<i class="ico-success"></i><h4 class="pop-text">修改成功</h4>
	</div>
	<div class="pop pop-error w-4" style="position:absolute;left:1200px;display:none" id="divOperatFailure">
		<i class="ico-error"></i><h4 class="pop-text">修改失败，请重试</h4>
	</div>
	

{literal}
<script> 
//var box = new LightBox("divIsEmpty"),box2=new LightBox("divOperatSuccess"),box4=new LightBox("divNoType");	
function fn(obj){
	var Idivdok = document.getElementById(obj);	
	
	Idivdok.style.display="block";
	Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";
	if(document.body.scrollTop == 0){
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
	} else {
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.body.scrollTop-40+"px";	
	}
} 
$('tr').find('.text-left,checkbox').click(function(){
	var me=this,ckleft=$(me).find('.checkbox'),cklist=$(me).parent().find('.checkbox-list').find('.checkbox');
	if(ckleft.get(0).checked)
	{
		for(var i=0;i<cklist.length;i++)
		{
			cklist[i].checked=true;
		}
	}else
	{
		for(var i=0;i<cklist.length;i++)
		{
			cklist[i].checked=false;
		}
	}
});	
	
function divConfig(obj){	
	$('#'+obj).css("display","none");
}
var form1 = $('#J-form1');
$('#J-Submit').click(function(){
	//form1.submit();	
	var str = '{';
	var sp = '';
	$("[type='checkbox']").each(function(){
		str += sp+$(this).attr('name');
		sp = ','
		if($(this).is(':checked')){
			str += ":1";
		} else {
			str += ":0";
		}
	});
	str += '}';
	str = eval('(' + str + ')');
	$.ajax({
		type:'post',
		dataType:'json',
		cache:false,
		url:'/Service/updatenoticetask',
		data: str ,
		beforeSend:function(){
			isLoading = true;
		},
		success:function(data){
			if(data["status"]=="1"){
				fn("divOperatSuccess");
				setTimeout(function(){$('#divOperatSuccess').css("display","none");},3000);
			} else {
				//失败提示后隐藏
				$("#divOperatFailure h4").html('修改失败，请重试');
				fn("divOperatFailure");
				setTimeout(function(){$('#divOperatFailure').css("display","none");location.reload();},3000);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			$("#divOperatFailure h4").html('网络错误,请稍后重试!');
			fn("divOperatFailure");
				 setTimeout(function(){$('#divOperatFailure').css("display","none");
				 },3000);
		},
		complete:function(){
			isLoading = false;
		}
	}); 

});
</script>
{/literal}
</body>
</html>
