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
                                <ul class="ui-form ui-tab-content ui-tab-content-current">
									<li>
                                        <table class="table table-info table-border">
                                            <thead>
                                                <tr>
                                                    <th colspan="3" class="text-left">{$title}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                                                <form action="/admin/Fundconfig/index?parma=wcf2" method="post" id='subForm'>
                                                <tr>
                                                
                                                    <td class="text-left w-4">可提现系数</td>
                                                    <td>
                                                        <input type="text" value="{$res.val}" maxlength="10" class="input w-1" name="bel">
                                                    </td>
                                                  
                                                </tr>
											    <!-- {if "FUND_WITHDRAW_CONFIG_AVALIWITHDRAW_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
                                                <tr>
                                                
                                                    <td></td>
                                                    <td class="text-left">
                                                        <a href="javascript:void(0)"  class="btn btn-important" id="btn-submit">保 存<b class="btn-inner"></b></a>
                                                        <a href="javascript:void(0);" value="{$res['val'][$key-1]['charge_ratio']}" class="btn">撤销编辑<b class="btn-inner"></b></a>
                                                    </td>
                                                </tr>
                                                <!-- {/if} -->
                                                </form>
                                            </tbody>
                                        </table>
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
	$("a[class='btn btn-important']").click(function(){
		$("#subForm").submit();
	});
	
	$('.ui-tab-title li').click(function(){
		var indexs = $(this).val();
        window.location.href="/admin/Fundconfig/index?parma=wcf"+(parseInt(indexs));
	});
	
	
	$("a[class='btn']").click(function(){
		var tr=$(this).parent().parent();
		$(tr.find("input")[1]).val($(this).attr("value"));
		//tr.find(".ui-select").find("option[defultValue]").attr("selected",true);
		tr.find(".ui-select").find("option[defultValue]").get(0).selected=true
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