
<form selectType="1" action="/fundappeal/rechargeappealsubmit" method="post" {if $type!='1'}style="display:none"{/if}>
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
                <option value="1" selected >网银汇款</option>
                <option value="2">快捷充值</option>
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
                    <input type="hidden" id="rechargeBank" name="bank"/>
                    <div class="bank-list" id="banklistinfo">
                        {foreach from=$banks item=data}
                            <span class="ico-bank {$data.logo}" name="{$data['logo']}" value="{$data.id}" helpId="{$data.helpId}" text="{$data.name}" helpId2="{$data.helpId2}" ></span>
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
        <li>
            <label class="ui-label">充值附言：</label>
            <input type="text" class="input w-4" name="rechargeMemo" value="{$rechargeMemo}">
            <span class="ui-text-prompt">（实际汇款输入的附言）</span>
        </li>
        <!--{/if}-->
    </ul>
    <ul class="ui-form icbcBankInput">
        <li>
            <label class="ui-label">选择提交方式：</label>
            <span class="radio-list">
                <label class="label" for="electronic" style="display:none">
                <input type="radio" class="radio" name="fieldType" id="electronic" value="electronic">电子回单号码
                </label>
                <label class="label" for="transactionNum">
                <input type="radio" class="radio" name="fieldType"  value="transactionNum">指令序号
                </label>
                <label class="label" for="remittance">
                <input type="radio" class="radio" name="fieldType" id="remittance" value="remittance">汇款回执单
                </label>
            </span>
            <span class="ui-text-prompt">（*任选择一种方式提交即可）</span>
        </li>
      
        	<li class="transactionNumInput">
            <label class="ui-label"></label>
            <input type="text" class="input w-4 formatCardNum" style="font-size:15px" id="transactionNum" name="transactionNum"  value="{$transactionNum}">
            <span class="ui-text-prompt">(请输入指令序号)</span>
            &nbsp&nbsp<a class="ui-text-info helpLink" id="helpLink1-1" helpId="">工行指令序号查询演练</a>
        	</li>
        
       
        <li class="electronicInput">
            <label class="ui-label"></label>
            <input type="text" class="input w-1 numberOnly" name="etcNumber" maxlength="4" value="">
            <input type="text" class="input w-1 numberOnly" name="etcNumber" maxlength="4" value="">
            <input type="text" class="input w-1 numberOnly" name="etcNumber" maxlength="4" value="">
            <input type="text" class="input w-1 numberOnly" name="etcNumber" maxlength="4" value="">
            <input type="hidden" name="electronicNumber" value="">
            <span class="ui-text-prompt">（请输入16位电子回单号码）</span>
            <a class="ui-text-info helpLink" id="helpLink1">电子回单查询演练</a>
        </li>
        <li class="remittanceInput">
	            <label class="ui-label"></label>
	            <span id="uploadFile1"></span>
	            <span class="ui-text-prompt">（*最多上传3张照片）</span>
	            <a class="ui-text-info helpLink" id="helpLink1" helpId=""><bankName>请选择汇款银行</bankname></a>
	    </li>
	   
	    <li id="photoBox1" style="height:100px; display:none">
	            <label class="ui-label"></label>
	    </li>
        
    </ul>
    <ul class="ui-form otherBankInput">
        <li>
            <label class="ui-label">付款人姓名：</label>
            <input type="text" class="input w-4 cnEnNameOnly" maxlength="30" name="rechargeName" value="{$rechargeName}">
            <span class="ui-text-prompt">(*实际付款人姓名)</span>
        </li>
        <li>
            <label class="ui-label">付款人卡号：</label>
            <input type="text" class="input w-4 formatCardNum" style="font-size:15px" name="rechargeCard" maxlength="23" value="{$rechargeCard}">
            <span class="ui-text-prompt">(*实际付款人卡号)</span>
        </li>
        <li>
            <label class="ui-label">回执单截图：</label>
            <span id="uploadFile1_1"></span>
            <span class="ui-text-prompt">（*最多上传3张照片）</span>
            <a class="ui-text-info helpLink" id="helpLink1" helpId=""><bankName>请选择汇款银行</bankname></a>
        </li>
        <li id="photoBox1_1" style="height:100px; display:none">
            <label class="ui-label"></label>
        </li>
    </ul>
</form>