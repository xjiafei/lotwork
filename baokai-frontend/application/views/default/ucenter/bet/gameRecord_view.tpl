<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/bet/bet.css" />
	{include file='/default/script-base.tpl'}
</head>
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
				<div class="title">方案详情</div>
				{if $username neq NULL}<div class="notice">您当前查看的是{$username}的双色球{$issue}期方案详情</div>{/if}
				<div class="content">
					<div class="bet-detail">
						<table class="table">
							<thead>
								<tr>
									<th colspan="3" class="highbig">{$lottery}</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>期号：{$issue}</td>
									<td>投注时间：{$boughtTime}</td>
									<td>{if $winNum|count gt 0}开奖号码：{foreach $winNum as $item}<i class="ico-lottery-num">{$item}</i>{/foreach}{/if}</td>
								</tr>
								<tr>
									<td>投注总金额：<strong class="price"><dfn>&yen;</dfn>{$amount}</strong>元</td>
									<td>总奖金：<strong class="price"><dfn>&yen;</dfn>{$wonAmount}</strong>元</td>
									<td rowspan="2">{if $status>0 && $status<3}<a href="javascript:void(0);" class="btn btn-primary" id="revSscheme">撤销方案<b class="btn-inner"></b></a>{else if $status eq -1}<strong class="high color-red" id="revSchemeOk">方案已被撤销</strong>{/if}</td>
								</tr>
								<tr>
									<td>方案编号：<span id="pid">{$pId}</span></td>
									<td>{if $isChas eq 1}<a href="/bet/cplanvew?pId={$pId}">相关追号记录</a>{/if}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<table class="table table-info">
						<thead>
							<tr>
								<th>玩法</th>
								<th>投注内容</th>
								<th>注数</th>
								<th>倍数</th>
								<th>投注金额</th>
								<th>模式</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
						{foreach $items as $item}
							<tr>
								<td>{$item.method}</td>
								<td>{$item.content}</td>
								<td>{$item.num}注</td>
								<td>{$item.times}倍</td>
								<td><strong class="price"><dfn>&yen;</dfn>{$item.amount}</strong></td>
								<td>{if $item.form eq 0}元{else if $item.form eq 1}角{/if}</td>
								<td>{if $item.wonInfo eq 0}等待开奖{else if $item.wonInfo eq -1}未中奖{else}<strong class="price color-red"><dfn>&yen;</dfn>{$item.wonInfo}</strong>{/if}</td>
							</tr>
						{/foreach}
						</tbody>
					</table>
					{if $pages}
					<div class="page-wrapper clearfix">
					<div class="page"> 
						{if $pages.pre && $pages.currpage.index!=1}
    							<a  class="prev" onClick="$('#fm_main').submit();" href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}">上一页</a>
    						{/if}
    					{foreach from=$pages.steps item=item}
    						{if $item.index == $pages.currpage.index}
    							<a class="current" href="javascript:void(0);">{$item.text}</a>
    						{else}
    							<a onClick="$('#fm_main').submit();" href="?cid={$pagevar.cid}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}">{$item.text}</a>
    						{/if}
    					{/foreach}
    					{if $pages.next && $pages.currpage.index!=$pages.max.index}
    						<a  class="next" onClick="$('#fm_main').submit();" href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}">下一页</a>
    					{/if}
					</div>
					</div>
					{/if}
				</div>
				</div>
			</div>
	</div>
	<!-- footer 不使用的情况下请自行删除 -->
	{include file='/default/ucenter/footer.tpl'}
	
	<div class="pop w-7" style="position:absolute;left:100px;display:none" id="divPrompt">
		<div class="hd"><span class="close" id="divclose"></span>温馨提示</div>
		<div class="bd">
			<h4 class="pop-title">您确定要撤销该方案？</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divCanceled">取 消<b class="btn-inner"></b></a>
				<a href="javascript:void(0);" class="btn btn-important " id="J-Submit">确 定<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	
	<div class="pop w-7" style="position:absolute;left:500px;display:none" id="divPromptFailure">
		<div class="hd"><i class="close" id="divPromptFailuren1"></i>温馨提示</div>
		<div class="bd">
			<h4 class="pop-title color-red">方案撤销失败，请检查网络或重试！</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divPromptFailuren2">关 闭<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	
	<div class="pop pop-success w-4" style="position:absolute;left:900px;display:none" id="divPromptOk">
			<i class="ico-success"></i><h4 class="pop-text">方案已被撤销</h4>
	</div>
{literal}
<script >
  (function($){	  
  
    //弹出提示框
	$('#revSscheme').click(function(){	fn("divPrompt");});	

	$('#divclose,#divCanceled').click(function(){ $('#divPrompt').css("display","none");});
	
  	//操作后提示	 
  	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
    } 	
	$('#J-Submit').click(function(){	
		$.ajax({
			type:'post',
			dataType:'json',
			contentType: "application/json",
			cache:false,
			url:"/bet/gplanvew",		
			data:'?repeal='+$('#pid').html(),
			beforeSend:function(){				
				$('#revSscheme').attr('disabled',false);	
			},
			success:function(json){				
			  if(json=="")
			  {		   
				 $('#revSscheme').hide();
				 $('#revSchemeOk').show();	
				 fn("divPromptOk");				
				 $('#divPrompt').css("display","none");
				 setTimeout("setFn('divPromptOk')",2500);			
				 //window.location = "/bet/plans";
			   }
			   else{
				fn("divPromptFailure");				
				$('#divPromptFailuren1,#divPromptFailuren2').click(function(){
					$('#divPrompt').css("display","none");
					$('#divPromptFailure').css("display","none");
				});	
				
			   }
               			
				
			},			  
			
			error:function(xhr, type){			
				fn("divPromptFailure");				
				$('#divPromptFailuren1,#divPromptFailuren2').click(function(){
					$('#divPrompt').css("display","none");
					$('#divPromptFailure').css("display","none");
				});	
			},
			complete:function(){
		
		    }
	     });
	});



})(jQuery);	

  function setFn(obj){	$('#'+obj).css("display","none");  }
  
</script>
{/literal}

</body>
</html>