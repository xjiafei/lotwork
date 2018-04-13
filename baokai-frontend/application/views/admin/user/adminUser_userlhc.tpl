<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->

<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->

	<div class="col-main">
		<div class="col-main">
		<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt;
				<a href="/admin/user/list?search=0"><span id="menu2">客户列表</span></a>
				&gt; <sapn id="menu3">{$account}</sapn>
			</div>
		</div>

		<div class="col-content">
			<div class="col-main">
				<div class="main">
					<div class="ui-tab">
						<div class="ui-tab-title clearfix">
							<ul>
								<li><a href="/admin/user/userprizemode?id={$userid}&userLvl={$userLvl}&account={$account}&viplevel={$viplevel}&path_img={$path_img}">奖金返点模式</a></li>
								<li><a href="/admin/user/user2000point?id={$userid}&userLvl={$userLvl}&account={$account}&viplevel={$viplevel}&path_img={$path_img}">超级2000</a></li>
								<!-- {if "USER_MANAGE_LIST_LHC"|in_array:$smarty.session.datas.info.acls} -->							
									<li class="current">六合彩</li>
								<!-- {/if} -->
							</ul>
						</div>
						{if $status == "1"}
							{assign var="status_msg" value="已开启"}
						{else}
							{assign var="status_msg" value="关闭"}
						{/if}
						
						<div style="padding:30px;">
							用户名：{$account}<!-- {if $viplevel != '' && $viplevel != '0'}-->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$viplevel}.png"><!-- {/if} --><br><br>
							用户组：{$userLvl}<br><br>
							<div id="read_block">
								设置本人：{$status_msg}<br><br>
								<!-- {if "USER_MANAGE_LIST_LHC_UPDATE"|in_array:$smarty.session.datas.info.acls} -->
								<!--
								<input id="btn_mdf" type="button" class="btn btn-important" value="修改" onclick="mdf();">
								-->
								<a id="btn_mdf" class="btn btn-important" href="javascript:mdf();">修改</a>
								<!-- {/if} -->
							</div>
							<div id="set_block">
								设置本人：
								<input id="open" name="status" type="radio" value="1" {if $status == "1"} checked {/if}> 开　　
								<input id="close" name="status" type="radio" value="0" {if $status == "0"} checked {/if}> 关<br><br>
								<!-- {if $userLvl != '玩家'} -->
								<input id="include_down" type="checkbox"> 包含所有下级用户<br><br>
								<!-- {/if} -->
								<a id="btn_save" class="btn btn-important" href="javascript:save();">保存</a>
								<a id="btn_cancel" class="btn btn-important" href="javascript:cancel();">取消</a>
							</div>
						</div>
						<br>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script src="{$path_js}/js/phoenix.Common.js"></script>

{literal}
<script>
function mdf(){
	$('#read_block').hide();
	$('#set_block').show();
}

function save(){
	var status = $('input[name=status]:checked').val();
	var is_include_down = $('#include_down').prop('checked');
	$.ajax({
		type: "POST",
		data: {status:status, is_include_down:is_include_down},
		dataType: "text",
		success: function(data){
			window.location.reload();
		}
	});
}

function cancel(){
	$('#read_block').show();
	$('#set_block').hide();
}

(function($){
	//一、二级菜单选中样式加载
	selectMenu('MenuUser','MenuUserlist');
	$('#read_block').show();
	$('#set_block').hide();
})(jQuery);
</script>
{/literal}