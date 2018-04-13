<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">申訴相关配置</span></div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div class="ui-tab">
                        <ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">

                            <!-- {if "FUND_APPEAL_TIPS_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
                            <span><a href="/admin/Rechargemange/index?parma=abtf"><li>申诉提示配置</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_APPEAL_BANKS_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
                            <span><a href="/admin/Rechargemange/index?parma=abcf"><li>申诉银行配置</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_APPEAL_BANKS_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
                            <span><a href="/admin/Rechargemange/index?parma=pabcf"><li class='current'>移动端申诉银行配置</li></a></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->

                            <input type="hidden" name="step" value="{$step}"/>
                            <input type="hidden" name="type" value="{$type}"/>
                        </ul>

                        <ul class="ui-form"  id="DivRules">
                            <li>  
                                <form action="/admin/Rechargemange/index?parma=pabcf&step=0" method="post" id="J-form">
                                    <table class="table table-info table-function">
                                        <thead>
                                            <tr>
                                                <th colspan="2">时间配置：</th>
                                            </tr>
                                        </thead>
                                        <tbody>                                    
                                            <tr>
                                                <th>充值申诉等待时间：<input type="text" name="waitTime" size="12" value="{$waitTime}">   分钟<input type="hidden" name="orgWaitTime" size="12" value="{$waitTime}"></th>
                                                <th>充值申诉冷却时间：<input type="text" name="cdTime" size="12" value="{$cdTime}">   分钟<input type="hidden" name="orgCdTime" size="12" value="{$cdTime}"></th>
                                            <tr>
                                        </tbody>    
                                    </table>
                                    <table class="table table-info table-function card-setting">
                                        <thead>
                                            <tr>
                                                <th colspan="2">支付宝</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <label>
                                                      	 支付宝
                                                    </label>
                                                </td>
                                                <td>
                                                    {foreach from=$res key=key item=data}
                                                        {if $data.id eq 30}
                                                            <label style="width:120px;">
                                                                <input type="checkbox" {if $data.moveCanRechargeAppeal eq '1'}checked{/if} name="third_bank[]" value="{$data.id}" > {$data.name}
                                                            </label>
                                                        {/if}
                                                    {/foreach}
                                                </td>
                                                <td>

                                                </td>
                                            </tr>
                                        </tbody>     
                                    </table>
                                    <a class="btn btn-important w-1 card-setting-savebutton" id="J-Submit-Button">保存</a>
                                    <!--<a class="btn card-setting-cancelbutton">撤销编辑</a>-->
                                    
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
        (function () {
            var form = $('#J-form'), minWindow, mask, initFn, isture = false;
            minWindow = new phoenix.MiniWindow();
            mask = phoenix.Mask.getInstance();
            minWindow.addEvent('beforeShow', function () {
                mask.show();
            });
            minWindow.addEvent('afterHide', function () {
                mask.hide();
            });
            //Tab
            //var indexs = $('.ui-tab-title li').index();
            
            //new phoenix.Tab({triggers: '.ui-tab-title2 li', panels: '.ui-tab-content', eventType: 'click', currPanelClass: 'ui-tab-content-current', index: indexs});
            //一、二级菜单选中样式加载	
            selectMenu('Menufunds', 'MenuFundRechargeAppealConfig');

            //充值上下限配置置(表单提交校验)
            $('#J-Submit-Button').click(function () {
                form.submit();
            });
        })();
    </script>
{/literal}
</body>
</html>