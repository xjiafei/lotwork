<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/bet/bet.css" />
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
				<div class="title">追号详情</div>
				{if $username neq NULL}<div class="notice">您当前查看的是{$username}的双色球{$issue}期方案详情</div>{/if}
				<div class="content">
					<div class="chas-detail">
						<table class="table">
							<tbody>
								<tr>
									<td rowspan="3" class="table-title">{$lottery}</td>
									<td>起始期号：{$startIssue}</td>
									<td colspan="2">追号时间：{$chasTime}</td>
								</tr>
								<tr>
									<td>进度：已追{$finishedNoChas}期/总{$totNoChas}期</td>
									<td>已追号金额：<span class="price"><dfn>&yen;</dfn>{$finishedMoenyChas}</span>元</td>
									<td>追号方案金额：<span class="price"><dfn>&yen;</dfn>{$chasMoney}</span>元</td>
								</tr>
								<tr>
									<td>终止追号条件：累计追中 <strong>{$stopCondi}次</strong> 停止</td>
									<td>已获奖金：<span class="price"><dfn>&yen;</dfn>{$wonMoney}</span>元</td>
									<td>追号编号：{$chasId}</td>
								</tr>
							</tbody>
						</table>
						<table class="table">
							<tbody>
								<tr>
									<td class="table-title">追号方案</td>
									<td>
										<div class="chas-detail-table">
											<table class="table">
												<thead>
													<tr>
														<th>玩法</th>
														<th>投注内容</th>
														<th>注数</th>
														<th>倍数</th>
														<th>模式</th>
													</tr>
												</thead>
												<tbody>
												{foreach $items as $item}
													<tr>
                        								<td>{$item.method}</td>
                        								<td>{$item.content}</td>
                        								<td>{$item.num}注</td>
                        								<td>{$item.times}倍</td>
                        								<td>{if $item.form eq 0}元{else if $item.form eq 1}角{/if}</td>
                        							</tr>
												{/foreach}
												</tbody>
											</table>
										</div>
									</td>
									<td class="vertical-bottom"><a class="btn btn-primary" href="javascript:void(0);"
									 id="J-Terminate">终止追号<b class="btn-inner"></b></a></td>
								</tr>
							
						</table>
					</div>
					<table class="table table-info">
						<thead>
							<tr>
								<th></th>
								<th>期号</th>
								<th>追号倍数</th>
								<th>投注金额</th>
								<th>当期开奖号码</th>
								<th>状态</th>
								<th>操作项</th>
							</tr>
						</thead>
						<tbody>
						{foreach $items1 as $item}
							<tr>
								<td></td>
    							<td>{$item.issue}</td>
    							<td>{$item.times}</td>
    							<td><span class="price"><dfn>&yen;</dfn>{$item.amount}</span></td>
    							<td>{$item.prizeNum}</td>
    							<td>{if $item.wonstate eq 0}{if $item.issue eq $issue}等待开始{else}未开始{/if}{else if $item.wonstate eq -1}未中奖{else if $item.wonstate eq -2}已撤销{else}<span class="price color-red"><dfn>&yen;</dfn>{$item.wonstate}</span>{/if}</td>
    							<td><a href="/bet/gplanvew?pId={$item.pId}">查看</a></td>
    						</tr>
						{/foreach}
						
					</table>
				</div>
			</div>
		</div>
	</div>
	<!-- footer 不使用的情况下请自行删除 -->
	{include file='/default/ucenter/footer.tpl'}
<!-- start -->
<div style="position:absolute;left:100px;display:none" class="pop w-7"  id="divIsChase">
	<div class="hd"><i class="close"  id="divclose"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title">您确定要终止整个追号计划吗？</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);"  id="divCanceled">取 消<b class="btn-inner"></b></a>
			<a class="btn btn-important " href="javascript:void(0);"  id="J-Submit">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;display:none" class="pop w-7" id="divIsChaseFailure">
	<div class="hd"><i class="close" id="endchasFailure"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">追号终止失败，请检查网络或重试！</h4>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="endChasSuccessNo">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
<!-- start -->
<div style="position:absolute;display:none" class="pop w-7" id="divIsChaseTermination">
	<div class="hd"><i class="close" id="endchasSuccess"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title color-red">您的追号已被终止！</h4>
		<p class="pop-text">如果您需要撤销当前期的投注方案，您可以在对应方案详情中进行操作！</p>
		<div class="pop-btn">
			<a class="btn" href="javascript:void(0);" id="endChasSuccessok">关 闭<b class="btn-inner"></b></a>
		</div>
	</div>
</div>
<!-- end -->
{literal}
<script > 
  (function($){	
	//操作后提示	 
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
    } 	
	$('#J-Terminate').click(function(){	fn("divIsChase");});	
	$('#divclose,#divCanceled').click(function(){ $('#divIsChase').css("display","none");});
	
	$('#J-Submit').click(function(){	
		$.ajax({
			type:'post',
			dataType:'json',			
			cache:false,
			url:"/bet/endchas",		
			data:'',
			beforeSend:function(){			
			
			},				
			success:function(json){	
			  if(json)
			  {		    
				 $('#divIsChase').hide();	
				 fn("divIsChaseTermination");					
				 $('#endchasSuccess,#endChasSuccessok').click(function(){ $('#divIsChaseTermination').css("display","none");});
				 
			   }
			   else{
			    $('#divIsChase').hide();	
				fn("divIsChaseFailure");
				$('#endchasFailure,#endChasSuccessNo').click(function(){ $('#divIsChaseFailure').css("display","none");});				
				
			   }
               			
				
			},			  
			
			error:function(xhr, type){			
				$('#divIsChase').hide();	
				fn("divIsChaseFailure");
				$('#endchasFailure,#endChasSuccessNo').click(function(){ $('#divIsChaseFailure').css("display","none");});
			},
			complete:function(){
		
		    }
	     });
	});
	
  })(jQuery);	
  
	
</script>
{/literal}
</body>
</html>