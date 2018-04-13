	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">提现相关配置</span></div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							{include file='/admin/funds/draw/base/drawTip.tpl'}
							<ul class="ui-form ui-tab-content ui-tab-content-current"  id="DivRules">
                            	<li> 
                            		<form action="/admin/Fundconfig/index?parma=wcf1" method="post" id="subForm">
                                    <table class="table table-info table-border">
                                        <thead>
                                            <tr>
                                                <th colspan="2" class="text-left">提现上下限配置</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                                            <tr>
                                                <td class="text-left w-4" >每日提现次数</td>
                                                <td class="text-left">
                                                    <span class="ui-info">普通用户：</span>
                                                    <select class="ui-select w-1" name="time">
                                                    {foreach from=$downArray item=data}
                                                        <option value="{$data}" {if $res.user.time == $data}selected{/if}>{$data}</option>
                                                    {/foreach}
                                                        <option value="-1" {if $res.user.time eq '-1'}selected{/if}>无限</option>
                                                    </select>
                                                    <span class="ui-info">次</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <span class="ui-text-info">VIP用户：</span>
                                                    <select class="ui-select w-1" name="viptime">
                                                        {foreach from=$downArray item=data}
                                                        <option value="{$data}" {if $res.vip.time == $data}selected{/if}>{$data}</option>
                                                    	{/foreach}
                                                        <option value="-1" {if $res.vip.time == '-1'}selected{/if}>无限</option>
                                                    </select>
                                                    <span class="ui-info">次</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="text-left">提现金额</td>
                                                <td class="text-left">
                                                    <dl class="set-list">
                                                        <dt>普通用户</dt>
                                                        <dd>
                                                            <span class="ui-info">最低：</span>
                                                            <input type="text" maxlength="8" value="{$res.user.lowLimit}" class="input w-1" name="lowlimit">
                                                            <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span class="ui-info">最高：</span>
                                                            <input type="text" maxlength="8" value="{$res.user.upLimit}" class="input w-1" name="uplimit">
                                                            <span class="ui-info">元</span>
                                                        </dd>
                                                        <dt>VIP用户</dt>
                                                        <dd>
                                                            <span class="ui-info">最低：</span>
                                                            <input type="text" maxlength="8" value="{$res.vip.lowLimit}" class="input w-1" name="viplowlimit">
                                                            <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span class="ui-info">最高：</span>
                                                            <input type="text" maxlength="8" value="{$res.vip.upLimit}" class="input w-1" name="vipuplimit">
                                                            <span class="ui-info">元</span>
                                                        </dd>
                                                    </dl>
                                                </td>
                                            </tr>
											<!-- {if "FUND_WITHDRAW_CONFIG_UPDOWN_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                            <tr>
                                                <td></td>
                                                <td class="text-left ui-btn">
                                                    <a href="javascript:void(0);" class="btn btn-important" id="btnsubmit">保 存<b class="btn-inner"></b></a>
                                                    <a href="javascript:void(0);" class="btn">撤销编辑<b class="btn-inner"></b></a>
                                                </td>
                                            </tr>
                                            <!-- {/if} -->
                                        </tbody>
                                    </table>
									</form>
									</li>
                                </ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {	
	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuWithdrawalsConfig');
	var tipIndex = $("#tipIndex").val();
    $("#drawcfg_"+tipIndex).addClass("current");
                       
	$("#btnsubmit").click(function(){
		$("#subForm").submit();
	});
	$('.ui-tab-title li').click(function(){
		var indexs = $(this).val();
        window.location.href="/admin/Fundconfig/index?parma=wcf"+(parseInt(indexs));
	});
	$("input[type='text']").on("keyup",function()
	{
		$(this).val($(this).val().replace(/[^0-9]/g,''));
	}).on("blur",function(){
		$(this).val($(this).val().replace(/[^0-9]/g,''));
	});

})();	
</script>
{/literal}
</body>
</html>