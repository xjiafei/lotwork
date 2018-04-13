<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 
			{if $typepage eq '1'}
			 <a href="/admin/user/list?search=0"><span id="menu2">客户列表</span></a> 
			{else if $typepage eq '2'}
			 <a href="/admin/proxy/index?search=0"><span id="menu2">总代管理</span></a> 
			{else}
			 <a href="/admin/user/accomplaints?search=1"><span id="menu2">账号申诉管理</span></a> 
			{/if}
			&gt; <span id="menu3">{$userAccount}</span>
			</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
								<!-- {if ("USER_MANAGE_LIST_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_USERINFO"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
									<li ><a href="/admin/user/userdetail?id={$id}&typepage={$typepage}">基本资料</a></li>
								<!-- {/if} -->
									<li class="current">查看银行卡</li>
								<!-- {if ("USER_MANAGE_LIST_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_PRIZE"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
									<li><a href="/admin/user/bonusrebate?id={$id}&account={$userAccount}&typepage={$typepage}">奖金返点</a></li>
								<!-- {/if} -->
								<!-- {if ("USER_MANAGE_LIST_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='1') or ("USER_MANAGE_TOPAGENT_LIST_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='2') or ("USER_EXCEPTION_APPEAL_INFO_MOBILETOKEN"|in_array:$smarty.session.datas.info.acls and $typepage=='3')} -->
								<li><a href="/admin/user/getphonesecurity?id={$id}&account={$userAccount}&typepage={$typepage}">手机令牌</a></li>
							<!-- {/if} -->
								</ul>
							</div>
							<div class="">
								<h3 class="ui-title">绑定的银行卡</h3>
								<input type="hidden" name="typepage" value="{$typepage|default:1}" />
								<table class="table table-info">
									<thead>
										<tr>
											<th>用户名</th>
											<th>开户人姓名</th>
											<th>银行账号</th>
											<th>银行名称</th>
											<th>黑名单</th>
											<th>省份</th>
											<th>城市</th>
											<th>支行名称</th>
											<th>绑定时间</th>
										</tr>
									</thead>
									<tbody>
									<!-- {foreach from=$bankStruc item=data} -->
										<tr>
											<td>{$userAccount}</td>
											<td>{$data.bankAccount}</td>
											<td>{$data.bankNumber}</td>
											<td>{$data.bankName}</td>
											<td>{if ($data.isBlackList)}是{else}否{/if}</td>
											<td>{$data.province}</td>
											<td>{$data.city}</td>
											<td>{$data.branchName}</td>
											<td>{$data.bindDate}</td>
										</tr>
									<!-- {/foreach} -->	
									</tbody>
								</table>
								<h3 class="ui-title">解绑的银行卡</h3>
								<table class="table table-info">
									<thead>
										<tr>
											<th>用户名</th>
											<th>开户人姓名</th>
											<th>银行账号</th>
											<th>银行名称</th>
											<th>黑名单</th>
											<th>省份</th>
											<th>城市</th>
											<th>支行名称</th>
											<th>解绑时间</th>
										</tr>
									</thead>
									<tbody>
										<!-- {foreach from=$bankDelHistory item=data} -->
										<tr>
											<td>{$userAccount}</td>
											<td>{$data.bankAccount}</td>
											<td>{$data.bankNumber}</td>
											<td>{$data.bankName}</td>
											<td>{if ($data.isBlackList)}是{else}否{/if}</td>
											<td>{$data.province}</td>
											<td>{$data.city}</td>
											<td>{$data.branchName}</td>
											<td>{$data.bindDate}</td>
										</tr>
										<!-- {/foreach} -->
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	{include file='/admin/script-base.tpl'}
	{literal}
	<script > 
	(function($){	
		//一级菜单选中样式加载
		var type = $('input[name="typepage"]').val();
		if(type!=3){
			type = type -1;
		}
		if(type==0)
		{
			menuName="MenuUserlist";
		}
		else if(type==1)
		{
			menuName="MenuUsermanage";
		}else if(type==3)
		{
			menuName="MenuUseraccomplaints";
		}
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser',menuName);
	})(jQuery);
	</script>
	{/literal}
</body>
</html>