
<form selectType="2" action="/fundappeal/rechargeappealsubmit" method="post" {if $type!='2'}style="display:none"{/if}>
    <ul class="ui-form">
        <!--{if $hasSn}-->
        <li>
            <label class="ui-label">充值订单号：</label>
            <span class="ui-info">{$sn}<input type="hidden" name="sn" value="{$sn}"/></span>
        </li>
        <!--{/if}-->
        <li>
            <label class="ui-label">平台账户：</label>
            <!--{if $hasSn}-->
            {$account}<input type="hidden" name="account" value="{$account}">
            <span class="ui-text-prompt">（*您的游戏账户）</span>
            <!--{else}-->
            <input type="text" class="input w-4 cnEnNumOnly" maxlength="16" name="account" value="{$account}">
            <span class="ui-text-prompt">（*需要申诉的平台账户名）</span>
            <!--{/if}-->
        </li>
        <li>
            <label class="ui-label">充值金额：</label>
            <input type="text" class="input w-4 numberDotOnly" maxsizelength="6" name="amount" value="{$amount}">
            <span class="ui-text-prompt">（*实际充值金额）</span>
        </li>
        <li>
            <label class="ui-label">充值方式：</label>
            <select class="ui-select w-3 bankHeight" id="selectType" name="type" value="{$type}">
                <option value="1">网银汇款</option>
                <option value="2" selected>快捷充值</option>
                {if $tenpayBank.canRechargeAppeal == 1}<option value="3">财付通</option>{/if}
                {if $unionpayBank.canRechargeAppeal == 1}<option value="5">银联充值</option>{/if}
                {if $alipayBank.canRechargeAppeal == 1}<option value="6">支付宝</option>{/if}
            </select>
            <div class="bank-more bankHeight">
                <div class="bank-label">
                    <span class="bank-drop-text"  id="selectBank">- 请选择银行 -</span>
                    <a class="bank-drop"><i class="ico-down" id="icoName"></i></a>
                </div>
                <div class="bank-more-content" style="display:none;">
                    <input type="hidden" name="defaultBank" value="{$bank}" />
                    <input type="hidden" name="bank"/>
                    <div class="bank-list" id="banklistinfo">
                        {foreach from=$fastBanks item=data}
                            <span class="ico-bank {$data.logo}" name="{$data['logo']}" value="{$data.id}" helpId="{$data.helpId}" text="{$data.name}" ></span>
                        {/foreach}								
                    </div>
                </div>
            </div>
            <span class="ui-text-prompt">（*选择您的充值方式）</span>
        </li>
        <!--{if !$hasSn}-->
        <li>
            <label class="ui-label">充值具体时间：</label>
            <input type="text" class="input w-4 dateOnly" name="rechargeTime" value="{$rechargeTime}">
            <span class="ui-text-prompt">（点击输入框选择充值的时间点）</span>
        </li>
        <!--{/if}-->
        <li>
            <label class="ui-label">回执单截图：</label>
            <span id="uploadFile2"></span>
            <span class="ui-text-prompt">（*最多上传3张照片）</span>
            <a class="ui-text-info helpLink" id="helpLink2" helpId=""><bankName>请选择汇款银行</bankname></a>
        </li>
        <li id="photoBox2" style="height:100px; display:none">
            <label class="ui-label"></label>
        </li>
    </ul>
</form>