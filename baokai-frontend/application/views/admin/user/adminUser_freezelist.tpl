<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 冻结用户管理  </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current"><a href=""></a>冻结名单</li>
								<!-- {if "USER_EXCEPTION_FREEZEUSER_HISTORY"|in_array:$smarty.session.datas.info.acls} -->
									<li><a href="/admin/user/freezeuserhistorylist?search=0">解冻记录</a></li>
                                     <!--{else}-->
                                    <li style="display:none;"></li>
								<!-- {/if} -->
								</ul>
							</div>
							<div >
                             <form action="freezeuserlist?search=0" method="get" id="fm_main">
                                 <input type="hidden" name="frsearch" value="1">
                                 <ul class="ui-search clearfix">
									<li class="name">
									<label class="ui-label" for="name" > 用户名:&nbsp; </label>
										     <!--   <select class="ui-select" name="searchtype" id="searchtype">
                                                                                            <option value="username"  {if $searchtype eq 'username'}selected="selected"{/if} >用户名</option>
						<option value="email" {if $searchtype eq 'email'}selected="selected"{/if}>邮箱</option>
					</select> -->
                                    <input type="text" class="input" id="userName" name="searchtypetxt" value="{$searchtypetxt|default:'请输入您的用户名'}">
									</li>
									<!-- {if "USER_EXCEPTION_FREEZEUSER_HISTORY_SEARCH"|in_array:$smarty.session.datas.info.acls} -->
                                     <li><input type="submit" value="搜索" class="btn btn-important" id="J-button-submit"/></li>
                                     <!-- {/if} -->
								</ul>
                                 
                                {if $freezeuserlist}
									<table class="table table-info">
									<thead>
										<tr>
											<th>用户名</th>
											<th>用户组</th>
											<th>余额</th>
											<th>冻结时间</th>
											<!-- {if "USER_EXCEPTION_FREEZEUSER_LIST_UNFREEZE"|in_array:$smarty.session.datas.info.acls} -->
											<th>操作</th>
											<!-- {/if} -->
                                            <th>冻结说明</th> 
											<th>冻结原因</th>
											<th>操作人</th>
										</tr>
									</thead>
									<tbody>
                                      <!-- {foreach from=$freezeuserlist item=ful} -->
										<tr>
											<td>{$ful.account}<!-- {if $ful.vipLvl != '' && $ful.vipLvl != '0'}-->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$ful.vipLvl}.png"><!-- {/if} --></td>
											<td>{$ful.userLvl}</td>
											<td>{if $ful.availBal<>''}{$ful.availBal}{else}0{/if}元</td>
											<td>{$ful.freezeDate}</td>
											<!-- {if "USER_EXCEPTION_FREEZEUSER_LIST_UNFREEZE"|in_array:$smarty.session.datas.info.acls} -->
												<td class="table-tool">
		                                            <!-- {if $ful.freezeMethod==5 or $ful.freezeAccount<0} -->
		                                            	<a class="ico-disable" title="禁止解冻" href="#"></a>
		                                            <!-- {else} -->
		                                            	<a class="ico-unlock" title="解冻"  href="/admin/user/unfreezeuser?ac={$ful.account}&id={$ful.id}&fc={$ful.freezeAccount}&typepage=3&viplevel={$ful.vipLvl}&&path_img={$path_img}"></a>
		                                            <!-- {/if} -->
												</td>
											<!-- {/if} -->
                                            <td>{if $ful.freezeMethod==1}完全冻结（冻结后，用户将不能登录平台）{else if $ful.freezeMethod==2}可登录，不可投注，不可充提{else if $ful.freezeMethod==3}不可投注，可充提{else if $ful.freezeMethod==4}不可转账，不可提现{else}用户帐号申诉成功，系统冻结中{/if}</td>
											<td><div style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><a style="cursor:hand;cursor:pointer;color:#555;text-decoration:none;" title="{$ful.freezeMemo}">{$ful.freezeMemo}</a></div></td>
											<td>{if $ful.freezeAccount<0}系统{else}{$ful.freezeAccount}{/if}</td>
										</tr>
                                      <!-- {/foreach} -->
									
								</table>
							</div>
						</div>
						{if $pages}
						<div class="page-wrapper">
							<span class="page-text">共{$pages.count}条记录</span>
							<div class="page page-right">
                            {if $pages.pre && $pages.currpage.index!=1}
								<a href="?cid={$pagevar.cid}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}&searchtype={$searchtype}&searchtypetxt={$searchtypetxt}" class="prev">上一步</a>
                            {/if}
                                
                            {foreach from=$pages.steps item=item}
                                {if $item.index == $pages.currpage.index}
                                    <a href="#" class="current">{$item.text}</a>
                                {else}
                                    <a href="?cid={$pagevar.cid}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}&searchtype={$searchtype}&searchtypetxt={$searchtypetxt}">{$item.text}</a>
                                {/if}
                            {/foreach}
                            {if $pages.next && $pages.currpage.index!=$pages.max.index}
                                <a class="next" href="?cid={$pagevar.cid}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}&searchtype={$searchtype}&searchtypetxt={$searchtypetxt}">下一页</a>
                            {/if}
								<span class="page-few">
                                                                    到第<input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页
								<input type="button" value="确 认" class="page-btn" onClick="($('#userName').val()=='请输入您的用户名'||$('#userName').val()=='请输入邮箱')?$('#userName').val(''):$('#userName').val($('#userName').val());$('#fm_main').submit();">
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
	$('#J-button-submit').click(function(e){
		if($('#userName')[0].value=='请输入您的用户名'){$('#userName')[0].value=''}		
	
		return true;
	});
	
	
})(jQuery);
</script>
{/literal}
</body>
</html>