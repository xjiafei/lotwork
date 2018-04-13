<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 账号申诉管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
                       <form action="accomplaints" method="post" id="fm_main">
                             <ul class="ui-search clearfix">
							<li class="name">
								<label class="ui-label">用户名：</label>
                                <input type="text" class="input" name="username" id="userName" value="{$username|default:'请输入用户名'}">
							</li>
							<li class="name">
								<label class="ui-label">审核人：</label>
                                 <input type="text" class="input" id="" name="checker" value="{$checker}" />
							</li>
							<li>
								<label class="ui-label">状态：</label>
                                <select class="ui-select" name="status">
									<option value="0" {if $status eq 0}selected="selected"{/if}>所有状态</option>
                                    <option value="1" {if $status eq 1}selected="selected"{/if}>通过</option>
									<option value="2" {if $status eq 2}selected="selected"{/if}>未通过</option>
								    <option value="3" {if $status eq 3}selected="selected"{/if}>未审核</option>
								</select>
							</li>
							<li>
								<label class="ui-label">申诉类型：</label>
								<select class="ui-select" name="pleadtype">
									<option value="0" {if $pleadtype eq 0}selected="selected"{/if}>所有类型</option>
									<option value="1" {if $pleadtype eq 1}selected="selected"{/if}>安全邮箱</option>
									<option value="2" {if $pleadtype eq 2}selected="selected"{/if}>安全信息</option>
								</select>
							</li>
							<!-- {if "USER_EXCEPTION_APPEAL_SEARCH"|in_array:$smarty.session.datas.info.acls} -->
                            <li><input type="submit" value="搜索" class="btn btn-important" id="J-button-submit"></li>
                            <!-- {/if} -->
						</ul>
						{if $pleadeuserlist}
								<table class="table table-info">
							<thead>
								<tr>
									<th>用户名</th>
									<th>申诉类型</th>
									<th>申诉时间</th>
									<th>审核时间</th>
									<th>操作</th>
									<th>状态</th>
									<th>备注</th>
									<th>审核人</th>
								</tr>
							</thead>
							<tbody>
                             {foreach from=$pleadeuserlist item=pl}
								<tr>
									<td>{$pl.account}
									<!-- {if $pl.vipLvl != '' && $pl.vipLvl != '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$pl.viplevel}.png"><!-- {/if} --></td>
									<td>{if $pl.appealType ==1}安全邮箱{else if $pl.appealType ==2}安全信息{/if}</td>
									<td>{$pl.createDate}</td>
									<td>{$pl.passDate}</td>
									<td class="table-tool">
									<!-- {if "USER_EXCEPTION_APPEAL_INFO"|in_array:$smarty.session.datas.info.acls} -->
										<a class="ico-info" title="详情"  href="/admin/user/pleaddetailinfo?typepage=3&ac={$pl.account}"></a>
									<!-- {/if} -->
									<!-- {if "USER_EXCEPTION_APPEAL_VERIFY"|in_array:$smarty.session.datas.info.acls} -->
										<a class="ico-check" title="审核" href="/admin/user/plead?id={$pl.id}"></a>
									<!-- {/if} -->
									</td>
                                    {if $pl.passed==1}
                                    <td class="color-green">通过</td>
                                    {else if $pl.passed==2}
                                    <td class="color-red">未通过</td>
                                    {else}
                                    <td>未审核</td>
                                    {/if}
									<td>{if $pl.memo !=''}
                                       
                                        <div style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><a style="cursor:hand;cursor:pointer;color:#555;text-decoration:none;" title="{$pl.memo}">{$pl.memo}</a></div>
                                       
                                        
                                        {else}无{/if}</td>
									<td>{if $pl.operater!=''}{$pl.operater}{else}无{/if}</td>
								</tr>
                                                            {/foreach}
							
						</table>
						{if $pages}
						<div class="page-wrapper">
							<span class="page-text">共{$pages.count}条记录</span>
							<div class="page page-right">
                               {if $pages.pre && $pages.currpage.index!=1}
                                  <a href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}&username={$username}&checker={$checker}&status={$status}&pleadtype={$pleadtype}" class="prev">上一页</a>
                               {/if}                      
                               {foreach from=$pages.steps item=item}
                                   {if $item.index == $pages.currpage.index}
                                       <a href="#" class="current">{$item.text}</a>
                                   {else}
                                       <a href="?cid={$pagevar.cid}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}&username={$username}&checker={$checker}&status={$status}&pleadtype={$pleadtype}">{$item.text}</a>
                                   {/if}
                               {/foreach}
                               {if $pages.next && $pages.currpage.index!=$pages.max.index}
                                   <a class="next" href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}&username={$username}&checker={$checker}&status={$status}&pleadtype={$pleadtype}">下一页</a>
                               {/if}
								<span class="page-few">
                                                                    到第<input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页
                          <input type="button" value="确 认" id="submit_sure" class="page-btn" onClick="$('#userName').val() == '请输入用户名'?$('#userName').val(''):$('#userName').val($('#userName').val());$('#fm_main').submit();">
							</div>
						</div>
                         {/if}
						{else}
							<div class="alert alert-waring">
							<i></i>
							<div class="txt">
							<h4>没有符合条件的记录，请更改查询条件！</h4>
							</div>
							</div>
						{/if}	
										
					
                                            </form>
					</div>
				</div>
			</div>
		</div>
	</div>
<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script src="{$path_js}/js/phoenix.Common.js" type="text/javascript" ></script>
{literal}
<script > 
	(function($){	
	//一、二级菜单选中样式加载	
	selectMenu('MenuUser','MenuUseraccomplaints');
		
	$('#userName').focus(function(){		
		if($('#userName')[0].value=='请输入用户名'){ $("#userName")[0].value='';}	
		}).blur(function(){
		var v = $.trim(this.value);
		if(v == ''){
			 $("#userName")[0].value='请输入用户名';		
		}
	
	});
	
	
	//表单提交校验
	$('#J-button-submit').click(function(){
		if($('#userName')[0].value=='请输入用户名'){	$('#userName')[0].value='';}
		return true;
	});
	})(jQuery);
</script>
{/literal}
</body>
</html>