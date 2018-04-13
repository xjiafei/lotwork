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
                        
                        <ul  style="overflow: hidden;">
                            <li>
                                <table class="table table-info table-border">
                                    <thead>
                                        <tr>
                                            <th colspan="3" class="text-left">提现分流设置</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <input type="hidden" id="tipIndex" name="tipIndex" value="{$tipIndex}"/>
                                    <form id="settingForm">
                                        <tr>
                                            <td class="text-left w-0">分流开关</td>
                                            <td class="text-left w-6">DP&nbsp;&nbsp;    
                                                <!--{if {$dpSetting.isOpen} eq 'Y'}-->
                                                <span id="dpSwitch" onclick="dpSwitchChange();" class='bypassOpen' alt="开"></span>
                                                <!--{else}-->
                                                <span id="dpSwitch" onclick="dpSwitchChange();" class='bypassClose' alt="关"></span>
                                                <!--{/if}-->
                                            </td>
                                        <input type="hidden" id="dpId" name="dpId" value="{$dpSetting.id}"/>
                                        <input type="hidden" id="dpSwitchStatus" name="dpSwitchStatus" value="{$dpSetting.isOpen}"/>
                                        <td class="text-left w-6">通汇&nbsp;&nbsp;     
                                            <!--{if {$thSetting.isOpen} eq 'Y'}-->
                                            <span id="thSwitch" onclick="thSwitchChange();" class='bypassOpen' alt="开"/></span>
                                            <!--{else}-->
                                            <span id="thSwitch" onclick="thSwitchChange();" class='bypassClose' alt="关"/></span>
                                            <!--{/if}-->
                                        </td>
                                        <input type="hidden" id="thId" name="thId" value="{$thSetting.id}"/>
                                        <input type="hidden" id="thSwitchStatus" name="thSwitchStatus" value="{$thSetting.isOpen}">
                                        </tr>
                                        <tr>
                                            <td class="text-left w-0">分流条件</td>
                                            <td class="text-left w-6">
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="dpLowLimit" class="input w-1" value="{$dpSetting.singleLowlimit}"/>
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="dpUpLimit" class="input w-1" value="{$dpSetting.singleUplimit}"/>
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                </dl>
                                            </td>
                                            <td>
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="thLowLimit" class="input w-1" value="{$thSetting.singleLowlimit}">
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="thUpLimit" class="input w-1" value="{$thSetting.singleUplimit}">
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                </dl>
                                            </td>
                                        </tr>
                                    </form>
                                    <tr>
                                        <td class="text-left w-2"></td>
                                        <td colspan="2">
                                            <!-- {if "FUND_WITHDRAW_SAVE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
                                            <input type="button" id="saveBtn" class="btn btn-important"  value="保存"  style="width:80px;"/>
                                            <input type="button" id="cancelBtn" class="btn" name="" value="撤销编辑"  style="width:100px;"/>
                                            <!-- {/if} -->
                                        </td>
                                    </tr>
                                    
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
        $("#saveBtn").bind("click", function () {
            $.ajax({
                url: "/admin/Rechargemange/index?parma=saveBypassCfg",
                data: $('#settingForm').serialize(),
                method: 'post',
                success: function () {
                    window.location = "/admin/Fundconfig/index?parma=wcf0";
                }
            });
        });

        $("#cancelBtn").bind("click", function () {
            window.location = "/admin/Fundconfig/index?parma=wcf0";
        });

        function dpSwitchChange() {
            var switchType = $("#dpSwitchStatus").val();
            if (switchType == 'Y') {
                $("#dpSwitch").attr('class', 'bypassClose');
                $("#dpSwitchStatus").val('N');
            } else {
                $("#dpSwitch").attr('class', 'bypassOpen');
                $("#dpSwitchStatus").val('Y');
            }
        }

        function thSwitchChange() {
            var switchType = $("#thSwitchStatus").val();
            if (switchType == 'Y') {
                $("#thSwitch").attr('class', 'bypassClose');
                $("#thSwitchStatus").val('N');
            } else {
                $("#thSwitch").attr('class', 'bypassOpen');
                $("#thSwitchStatus").val('Y');
            }
        }
        
        (function () {
            //一、二级菜单选中样式加载	
            selectMenu('Menufunds', 'MenuWithdrawalsConfig');
            var tipIndex = $("#tipIndex").val();
            $("#drawcfg_"+tipIndex).addClass("current");
            
            $("#btnsubmit").click(function () {
                $("#subForm").submit();
            });
            $('.ui-tab-title li').click(function () {
                var indexs = $(this).val();
                window.location.href = "/admin/Fundconfig/index?parma=wcf" + (parseInt(indexs));
            });
            $("input[type='text']").on("keyup", function ()
            {
                $(this).val($(this).val().replace(/[^0-9]/g, ''));
            }).on("blur", function () {
                $(this).val($(this).val().replace(/[^0-9]/g, ''));
            });

        })();
    </script>
{/literal}
</body>
</html>