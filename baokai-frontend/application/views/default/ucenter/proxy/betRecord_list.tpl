<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	
</head>
<body>
{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">投注查询</div>
				<div class="content">
				<form id="fm_main" name="fm_main" method="GET">
					<!-- start -->
					<ul class="ui-search search-proxy clearfix">
						<li class="date">
							<label class="ui-label" for="dateStar" >起始日期：</label>
							<input type="text" class="input" id="J-date-start" name="dateBegin" value="{$dateBegin}"> - <input type="text" class="input" id="J-date-end" name="dateEnd" value="{$dateEnd}">
						</li>
						<li class="status">
							<label class="ui-label">状态：</label>
							<select class="ui-select" name="winState">
								<option value="0" {if $winState eq 0}selected="selected"{/if}>全部状态</option>
								<option value="1" {if $winState eq 1}selected="selected"{/if}>中奖</option>
								<option value="2" {if $winState eq 2}selected="selected"{/if}>未中奖</option>
								<option value="3" {if $winState eq 3}selected="selected"{/if}>等待开奖</option>
								<option value="4" {if $winState eq 4}selected="selected"{/if}>已撤销</option>
							</select>
						</li>
						<li class="type">
							<label class="ui-label">彩种：</label>
							<select class="ui-select" name="lottery">
								<option value="0">全部彩种</option>
								{foreach from=$lotteryList item=item}
        							<option value="{$item.Id}" {if $lottery eq $item.Id}selected="selected"{/if}>{$item.title}</option>
        						{/foreach}
							</select>
						</li>
						<li class="chase">
							<label class="ui-label">追号：</label>
							<select class="ui-select" name="isChas">
								<option value="0" {if $isChas eq 0}selected="selected"{/if}>全部</option>
								<option value="1" {if $isChas eq 1}selected="selected"{/if}>是</option>
								<option value="2" {if $isChas eq 2}selected="selected"{/if}>否</option>
							</select>
						</li>
						<li class="name">
							<label class="ui-label" for="name">用户名：</label>
							<input type="text" class="input" id="username" name="username" value="{$username}" maxlength="16">
						</li>
						<li class="lower"><label class="ui-label"><input type="checkbox" class="checkbox" id="includeSub" name="includeSub" {if $includeSub eq 'on'}checked="checked"{/if}>包含下级</label></li>
						<li class="number">
							<label class="ui-label" for="number">方案编号：</label>
							<input type="text" class="input" id="number" name="number" value="{$number}">
						</li>
						<li class="button"><a href="javascript:void(0);" class="btn btn-important" onClick="$('#fm_main').submit();">查询<b class="btn-inner"></b></a></li>
					</ul>
					<!-- end -->
					<!-- start -->
					<table class="table table-info">
						<thead>
							<tr>
								<th>用户名</th>
								<th>彩种</th>
								<th>投注金额</th>
								<th>状态</th>
								<th>追号</th>
								<th>投单时间</th>
								<th>详情</th>
							</tr>
						</thead>
						<tbody>
							{foreach from=$items item=item}
    							<tr>
								<td>{$item.username}</td>
								<td>{$item.lottery}</td>
								<td><span class="price"><dfn>&yen;</dfn>{$item.amount}</span></td>
								<td>{if $item.state eq '1'}中奖{elseif $item.state eq '2'}未中奖{elseif $item.state eq '3'}等待开奖{elseif $item.state eq '4'}已撤销{/if}</td>
								<td>{if $item.chas eq '0'}否{elseif $item.chas eq '1'}是{/if}</td>
								<td>{$item.betTime}</td>
								<td><a {if $item.chas eq 0}href="/bet/gplanvew?pId={$item.pId}"{else if $item.chas eq 1}href="/bet/cplanvew?pId={$item.pId}"{/if} target="_blank">查看</a></td>
							</tr>
    						{/foreach}
						</tbody>
					</table>
					<!-- end -->
					<!-- start -->
					{if $pages}
					<div class="page-wrapper clearfix">
					<div class="page"> 
						{if $pages.pre && $pages.currpage.index!=1}
    							<a  class="prev" onClick="$('#fm_main').submit();" href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}&username={$username}&dateBegin={$dateBegin}&dateEnd={$dateEnd}&includeSub={$includeSub}&winState={$winState}&isChas={$isChas}&number={$number}&lottery={$lottery}">上一页</a>
    						{/if}
    					{foreach from=$pages.steps item=item}
    						{if $item.index == $pages.currpage.index}
    							<a class="current" href="javascript:void(0);">{$item.text}</a>
    						{else}
    							<a onClick="$('#fm_main').submit();" href="?cid={$pagevar.cid}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}&username={$username}&dateBegin={$dateBegin}&dateEnd={$dateEnd}&includeSub={$includeSub}&winState={$winState}&isChas={$isChas}&number={$number}&lottery={$lottery}">{$item.text}</a>
    						{/if}
    					{/foreach}
    					{if $pages.next && $pages.currpage.index!=$pages.max.index}
    						<a  class="next" onClick="$('#fm_main').submit();" href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}&username={$username}&dateBegin={$dateBegin}&dateEnd={$dateEnd}&includeSub={$includeSub}&winState={$winState}&isChas={$isChas}&number={$number}&lottery={$lottery}">下一页</a>
    					{/if}
					</div>
					</div>
					{/if}
<!--					<div class="page-wrapper clearfix">-->
<!--						<div class="page">-->
<!--							<a href="#" class="prev">上一步</a>-->
<!--							<a href="#">1</a>-->
<!--							<a href="#">2</a>-->
<!--							<a href="#" class="current">3</a>-->
<!--							<a href="#">4</a>-->
<!--							<a href="#">5</a>-->
<!--							<a href="#">6</a>-->
<!--							<a href="#">7</a>-->
<!--							<a href="#">8</a>-->
<!--							<a href="#">9</a>-->
<!--							<a href="#">...</a>-->
<!--							<a href="#" class="next">下一步</a>-->
<!--						</div>-->
<!--					</div>-->
					<!-- end -->
					<!-- start -->
					</form>
				</div>
			</div>
		</div>
	</div>
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->



{literal}
<script>
(function(){
	var inputStart = $('#J-date-start'),
		inputEnd = $('#J-date-end');
	inputStart.focus(function(){
		var dt = new phoenix.DatePicker({input:this});
			dt.show();
	});
	inputEnd.focus(function(){
		var dt = new phoenix.DatePicker({input:this});
			dt.show();
	});
	

})();
</script>	
{/literal}








	
</body>
</html>