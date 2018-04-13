<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
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
				<div class="title">表报查询</div>
				<div class="content">
					<!--start -->
					<form id="fm_main" name="fm_main" method="post" action="/Proxy/prsearch">
						<ul class="ui-search search-porxy clearfix">
							<li class="name">
								<label for="name" class="ui-label">用户名：</label>
								<input type="text"  id="name" name="userName" class="input"  />
							</li>
							<li class="type">
							{$lotteryType}
						
								<label class="ui-label">彩种：</label>
								<select name="lotteryType" class="ui-select">
									<option value="0" {if $lotteryType eq '0'}selected="selected"{/if}>全部彩种</option>
									<option value="1" {if $lotteryType eq '1'}selected="selected"{/if}>重庆时时彩</option>
									<option value="2" {if $lotteryType eq '2'}selected="selected"{/if}>天津时时彩</option>
									<option value="3" {if $lotteryType eq '3'}selected="selected"{/if}>重庆11选5</option>

								</select>
							</li>
							<li class="date">
									<label for="dateStar" class="ui-label">起始日期：</label>
									<input type="text" name="beginDate" id="J-date-start" class="input"> - <input type="text" name="endDate"  class="input" id="J-date-end" />
								</li>
							<li><a class="btn btn-important" href="javascript:void(0);" onClick="$('#fm_main').submit();">搜 索<b class="btn-inner"></b></a></li>
						</ul>
					<!--end -->
					<!--start -->
					<table class="table table-info">
						<thead>
							<tr>
								<th>用户名</th>
								<th>用户组</th>
								<th>总代购费</th>
								<th>返点</th>
								<th>实际总代购费</th>
								<th>中奖金额</th>
								<th>总结算</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							{foreach from=$items key=myId item=i}
							<tr>
								<td>{$i.username}({$i.num})</td>
								<td>{$i.pgroup}</td>
								<td>{$i.allfees}</td>
								<td>{$i.reback}</td>
								<td>{$i.reallfees}</td>
								<td>{$i.allbounes}</td>
								<td>{$i.alldeal}</td>
								<td><a href="/Proxy/prquery?uid={$i.username}">查看下级</a></td>
							</tr>
						{/foreach}
						
					</table>
				    {if $pages}
							<div class="m_page"> 
						{if $pages.pre && $pages.currpage.index!=1}
							<a href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}">上一页</a>
						{/if}
					{foreach from=$pages.steps item=item}
						{if $item.index == $pages.currpage.index}
							{$item.text}
						{else}
							<a href="?cid={$pagevar.cid}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}">{$item.text}</a>
						{/if}
					{/foreach}
					{if $pages.next && $pages.currpage.index!=$pages.max.index}
						<a href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}">下一页</a>
					{/if}
						到第<input type="text" value="{$pages.currpage.text}"/>/{$pages.count}页
						</div>
					{/if}
					
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