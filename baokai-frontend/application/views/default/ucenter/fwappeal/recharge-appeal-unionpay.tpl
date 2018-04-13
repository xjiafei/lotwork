
<form selectType="5" action="/fundappeal/rechargeappealsubmit" method="post" {if $type!='5'}style="display:none"{/if}>
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
                <option value="2">快捷充值</option>
                 {if $tenpayBank.canRechargeAppeal == 1}<option value="3">财付通</option>{/if}
                <option value="5" selected>银联充值</option>
                {if $alipayBank.canRechargeAppeal == 1}<option value="6">支付宝</option>{/if}
            </select>
            <span class="ui-text-prompt">（*选择您的充值方式）</span>
            <input type="hidden" name="bank" value="{$unionpayBank.id}"/>
        </li>
        <li>
            <label class="ui-label">付款卡卡号：</label>
             <input type="text" class="input w-4 formatCardNum" style="font-size:15px" name="rechargeCard" maxlength="23" value="{$rechargeCard}">
            <span class="ui-text-prompt">（*真实付款人账号）</span>
        </li>
        <li>
            <label class="ui-label">付款人姓名：</label>
            <input type="text" class="input w-4 cnEnNameOnly" maxlength="30" name="rechargeName" value="{$rechargeName}">
            
            <span class="ui-text-prompt">（*真实付款人姓名）</span>
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
            <span id="uploadFile5"></span>
            <span class="ui-text-prompt">（*最多上传3张照片）</span>
            <a class="ui-text-info helpLink" helpId="{$unionpayBank.helpId}">{$unionpayBank.name}回单查询演练</a>
        </li>
        <li id="photoBox5" style="height:100px; display:none">
            <label class="ui-label"></label>
        </li>

    </ul>
</form>