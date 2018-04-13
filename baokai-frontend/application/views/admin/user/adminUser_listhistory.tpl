<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->		
	
	<div class="col-content">

		<div class="col-main">
            <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 冻结用户管理</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="/admin/user/freezeuserhistorylist?search=1" method="get" id="fm_main"  name="fm_main" >
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
								<!-- {if "USER_EXCEPTION_FREEZEUSER_LIST"|in_array:$smarty.session.datas.info.acls} -->
									<li><a href="/admin/user/freezeuserlist">冻结名单</a></li>
								<!-- {/if} -->
									<li class="current">解冻记录</li>
								</ul>
							</div>
							<div>
								<ul class="ui-search clearfix">
									<li class="name">
										<label class="ui-label" for="name" id="Name">用户名：</label>
									<input type="text" class="input" id="userName" value="{$username|default:'请输入您的用户名'}" name="userName">
									</li>
									<li>
									<!-- <a href="/admin/user/freezeuserhistorylist" class="btn btn-important" id="J-button-submit">搜 索<b class="btn-inner"></b></a> -->
									<!-- {if "USER_EXCEPTION_FREEZEUSER_LIST_SEARCH"|in_array:$smarty.session.datas.info.acls} -->
                                     <input type="submit" value="搜索" id="J-button-submit" onclick="($('#userName').val()=='请输入您的用户名'||$('#userName').val()=='请输入邮箱')?$('#userName').val(''):$('#userName').val($('#userName').val());" class="btn btn-important"></li>
									<!-- {/if} -->
								</ul>
							<!-- {if $freezeuserlist} -->
								<table class="table table-info">
									<thead>
										<tr>
											<th>用户名</th>
											<th>用户组</th>
											<th>余额</th>
											<th>冻结时间</th>
											<th>冻结原因</th>
											<th>冻结人</th>
											<th>解冻时间</th>
											<th>解冻原因</th>
											<th>解冻人</th>
										</tr>
									</thead>
									<tbody>
                                      <!-- {foreach from=$freezeuserlist item=ful} -->
										<tr>
											<td>{$ful.userName}<!-- {if $ful.vipLvl != '' && $ful.vipLvl != '0'}-->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$ful.vipLvl}.png"><!-- {/if} --></td>
											<td>{$ful.userLvl}</td>
											<td>{$ful.restBal}元</td>
											<td>{$ful.frozenDate}</td>
											<td>
												<div style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
													<a style="cursor:hand;cursor:pointer;color:#555;text-decoration:none;" title="{$ful.memo}">
														{$ful.memo}
													</a>
												</div>
											</td>
											<td>{$ful.freezeAccount}</td>
											<td>{$ful.freeDate}</td>
											<td>
                                            <div style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><a style="cursor:hand;cursor:pointer;color:#555;text-decoration:none;" title="{$ful.reason}">{$ful.reason}</a></div></td>
											<td>{if $ful.operator<0}系统管理员{else}{$ful.operator}{/if}</td>
										</tr>
									  <!-- {/foreach} -->
								</table>
                                <!-- {if $pages} -->
								<div class="page-wrapper">
									<span class="page-text">共{$pages.count}条记录</span>
									<div class="page page-right">
                                    <!-- {if $pages.pre && $pages.currpage.index!=1} -->
										<a href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}&userName={$username}" class="prev">上一步</a>
                                   <!--  {/if} -->
                                    <!-- {foreach from=$pages.steps item=item} -->
                                            <!-- {if $item.index == $pages.currpage.index} -->
                                                <a href="#" class="current">{$item.text}</a>
                                            <!-- {else} -->
                                                <a href="?cid={$pagevar.cid}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}&userName={$username}">{$item.text}</a>
                                            <!-- {/if} -->
                                   <!--  {/foreach} -->
                                    <!-- {if $pages.next && $pages.currpage.index!=$pages.max.index} -->
                                        <a class="next" href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}&userName={$username}">下一页</a>
                                    <!-- {/if} -->
								<span class="page-few">
                                                                                                到第<input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页
								<input type="button" value="确 认" class="page-btn" onClick="($('#userName').val()=='请输入您的用户名'||$('#userName').val()=='请输入邮箱')?$('#userName').val(''):$('#userName').val($('#userName').val());$('#fm_main').submit();">
									</div>
								</div>
                             <!-- {/if} -->
							<!-- {else} -->
								<div class="alert alert-waring">
								<i></i>
								<div class="txt">
								<h4>没有符合条件的记录，请更改查询条件！</h4>
								</div>
								</div>
							<!-- {/if} -->	
							</div>
						</div>
                        </form>
					</div>
                </div>
			</div>
         </div>
	</div>
        
{include file='/admin/script-base.tpl'}
{literal}
<script > 
(function($){	
	//一、二级菜单选中样式加载	
		selectMenu('MenuUser','MenuUserfreeze');
	$('#userName').focus(function(){		
		if($('#userName')[0].value=='请输入您的用户名'){ $("#userName")[0].value='';}				
		}).blur(function(){
		var v = $.trim(this.value);
		if(v == ''){
			 $("#userName")[0].value='请输入您的用户名';		
		}
	
	});
	
	//表单提交校验
	$('#J-button-submit').click(function(){
		if($('#userName')[0].value=='请输入您的用户名'){$('#userName')[0].value=''}	
		return true;
	});
	
	
})(jQuery);
</script>
{/literal}




