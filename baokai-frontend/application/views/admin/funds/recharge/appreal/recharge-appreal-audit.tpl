
<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
        <div class="col-content" >
            <!-- //////////////头部公共页面////////////// -->
            {include file='/admin/funds/left.tpl'}
            <!-- /////////////头部公共页面/////////////// -->
            <div class="col-main">
                <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <a href="/admin/Rechargemange/?parma={$type}"> {$title}</a> &gt; {$title_text}</div></div>
                <div class="col-content">
                    <div class="col-main">
                        <div class="main">
                            <div class="main-half">
                                <h3 class="ui-title">用户申诉资料：</h3>
                                <ul class="ui-form">
                                    <li>
                                        <label class="ui-label">用户名：</label>
                                        <span class="ui-singleline">{$argueAcct}</span>
                                        <!-- {if {$vipLvl} != '' &&  {$vipLvl} != 0} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$vipLvl}.png"/><!-- {/if} -->
                                        <input id="userId" type="hidden" value="{$userId}"/>
                                    </li>
                                    
                                    <li>
                                        <label class="ui-label">充值金额：</label>
                                        <span class="ui-singleline">{$chargeAmt}</span>
                                    </li>
                                    <!-- {if $bankId != 51} -->
                                    <li>
                                        <label class="ui-label">充值方式：</label>
                                        <span class="ui-singleline">{$bankName}</span>
                                    </li>
                                    <!--{/if}-->
                                    <!-- {if $hasChargeData eq 'N'} -->
                                    <li>
                                        <label class="ui-label">充值时间：</label>
                                        <span class="ui-singleline">{$chargeTime}</span>
                                    </li>
                                    <!--{/if}-->
                                    <li>
                                    <!-- {if $chargeUserName neq ''} --> 
                                    <!-- {if $bankId == 31} -->
                                        <label class="ui-label">财付通姓名：</label>
                                    <!--{else}-->
                                        <label class="ui-label">付款人姓名：</label>
                                    <!--{/if}-->
                                        <span class="ui-singleline">{$chargeUserName}</span>
                                    </li>
                                    <!--{/if}-->
                                    
                                    <!-- {if $bankCardNumber neq ''} --> 
                                    <li>
                                    <!-- {if $bankId == 31} -->
                                        <label class="ui-label">财付通帐号：</label>
                                    <!--{else}-->
                                        <label class="ui-label">付款人卡号：</label>
                                    <!--{/if}-->
                                        <span class="ui-singleline">{$bankCardNumber}</span>
                                   </li>
                                   <!--{/if}-->
                                   
                                   <li>
                                        <label class="ui-label">申请渠道：</label>
                                        <span class="ui-singleline">{$depositeMode}</span>
                                   </li>
                                    <!-- {if $chargeMemo != ''} -->
                                    <li>
                                        <label class="ui-label">平台附言：</label>
                                        <span class="ui-singleline">{$chargeMemo}</span>
                                    </li>
                                    <!--{/if}-->
                                    <li>
                                        <!-- {if $electronicNumber neq ''} -->
                                            <label class="ui-label">电子回单号码：</label>
                                        <!-- {else if $transactionNum neq ''} -->
                                       		<label class="ui-label">交易流水号：</label>
                                        <!--{else}-->    
                                            <label class="ui-label">汇款回执单：</label>
                                        <!--{/if}-->
                                        <span class="ui-multiline">
                                            <!-- {if $electronicNumber eq '' and $transactionNum eq ''} -->
                                            <table>
                                                <tr>
                                                    {foreach $urlAry as $image}
                                                        <td><a href="{$dynamicroot}/upload/images/{$image->url}" download><img src="{$dynamicroot}/upload/images/{$image->url}" width='42' height='42'/></a></td>
                                                    {/foreach}
                                                </tr>
                                                <tr>
                                                    {foreach $urlAry as $image}
                                                        <td>{$image->name}</td>
                                                    {/foreach}        
                                                </tr>
                                                 
                                            </table>
                                            <!-- {else if $electronicNumber eq ''} -->
                                            	{$transactionNum}
                                            <!--{else}-->
                                                {$electronicNumber}
                                            <!--{/if}-->
                                        
                                        </span>
                                    </li>

                                </ul>
                            </div>
                            
                            <div class="main-half">
                                <h3 class="ui-title">平台充值订单信息：</h3>
                                <ul class="ui-form">
                                    <li>
                                        <label class="ui-label">充值订单号：</label>
                                        <span class="ui-singleline">{$sn}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">用户名：</label>
                                        <span class="ui-singleline">{$userAct}</span>
                                        <!-- {if {$vipLvl} != ''&& $userAct!='' &&  {$vipLvl} != 0} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$vipLvl}.png"/><!-- {/if} -->
                                       
                                    </li>
                                    <li>
                                        <label class="ui-label">所属总代：</label>
                                        <span class="ui-singleline">{$topVip}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">申请时间：</label>
                                        <span class="ui-singleline">{$applyTime}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">申请金额：</label>
                                        <span class="ui-singleline">{$realChargeAmt}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">申请充值银行：</label>
                                        <span class="ui-singleline">{$payBankId}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">申请渠道：</label>
                                        <span class="ui-singleline">{$depositMode}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">申请平台：</label>
                                        <span class="ui-singleline">{$platfom}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">收款银行：</label>
                                        <span class="ui-singleline">{$payBankId}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">收款卡账户名：</label>
                                        <span class="ui-singleline">{$accountHolder}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">收款卡：</label>
                                        <span class="ui-singleline">{$bankCardNo}</span>
                                    </li>
                                   
                                    <li>
                                        <label class="ui-label">平台附言：</label>
                                        <span class="ui-singleline">{$memo}</span>
                                    </li>
                                   
                                </ul>
                                    <br>
                                <font size="2" color="gray">*该区域信息仅提供参考，具体详情麻烦查询该用户平台订单讯息</font>
                            </div>
                            
                            <!-- {if $isReview eq 'Y'} -->
                            <div>
                            <input type='hidden' id='appealSn' value='{$appealSn}'/>
                             <ul class="ui-form">
                                <h3 class="ui-title">客服填写审核资料：</h3>
                                <li>
                                    <label class="ui-label">选择提交方式：</label>
                                    <span class="radio-list">
                                        <label class="label" for="type1">
                                            <input id="type1" name="submitType" type="radio" class="radio" value="2" checked>审核通过
                                        </label>
                                        <label class="label" for="type2">
                                            <input id="type2" name="submitType" type="radio" class="radio" value="3">审核不通过
                                        </label>
                                    </span>

                                </li>
                                <li>
                                    <label class="ui-label">说明：</label>
                                    <div class="radio-box w-10">
                                        <div id="showTips">
                                            {foreach from=$tips item=data}
                                                <label class="label" for="reason">
                                                    <input id="reason1" name="tips" type="radio" class="radio" value="{$data.id}"/>{$data.tipsMemo} &nbsp;<a title="{$data.tipsContext}">{mb_substr($data.tipsContext, 0, 32,'utf-8')}</a>
                                                </label><br/>
                                            {/foreach}
                                        </div>
                                        <label class="label" for="reason">
                                            <input id="reason4" name="tips" type="radio" class="radio" value="0" checked>其他
                                            <div class="textarea w-8">
                                                <textarea id="content" name="content" form="addForm" rows="10" cols="100"></textarea>
                                            </div>
                                        </label>
                                        <p class="color-red">
                                            以上信息内容将会推送给客户
                                        </p>
                                    </div>
                                </li>
                                <li>
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea id='memo' onkeyup='wordCount()'></textarea>
                                    </div>
                                    <span class="ui-text-prompt" id='words'>0</span><span class="ui-text-prompt">/100</span>
                                </li>
                                <li class="ui-btn">
                                    <a href="javascript:void(0);" id='review' class="btn btn-important">确 定<b class="btn-inner"></b></a>
                                    <a href="javascript:void(0);" id='back' class="btn">返 回<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                            </div>
                            <!--{else}-->
                            <div>
                            <input type='hidden' id='appealSn' value='{$appealSn}'/>
                                <ul class="ui-form">
                                     <h3 class="ui-title">审核结果：</h3>
                                    <li>
                                        <label class="ui-label">审核情况：</label>
                                        <span class="ui-singleline">{$appealStatus}</span>
                                    </li>
                                    <li>
                                        <label class="ui-label">审核内容：</label>
                                        <span class="ui-singleline"><a title="{$appealMemo}">{mb_substr({$appealMemo}, 0, 32,'utf-8')}</a></span>
                                    </li>
                                    <li>
                                        <label class="ui-label">备注：</label>
                                        <span class="ui-singleline">{$reviewMemo}</span>
                                    </li>
                                </ul>
                            </div>
                            <!--{/if}-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        {include file='/admin/script-base.tpl'} 
        {literal}
            <script type="text/javascript">
                function wordCount() {
                    var total = $('#memo').val().length;
                    if(total > 100){
                        alert("內容已達最大上限數");
                        var content = $('#memo').val().substr(0,100);
                        $('#memo').val(content);
                        return;
                    }
                    $('#words').text(total);
                }
                (function () {
                    var form = $('#J-form'),msg = new phoenix.Message(), msg1 = new phoenix.Message();
                    var isShowCell = false, group = new phoenix.SuperSearchGroup(), isLock = true;
                    //Tab	
                    var sindex = 0;
                    new phoenix.Tab({triggers: '.ui-tab-title2 li', panels: '.ui-tab-content', eventType: 'click', currPanelClass: 'ui-tab-content-current', index: sindex});
                    //一、二级菜单选中样式加载	
                    selectMenu('Menufunds', 'MenuRechargeAppreal');
                                 
                    /*$("input[name='tips']").bind("click",function(){
                        var tips = $('input[name=tips]:checked').val();
                        alert(tips);
                        if(tips!=0){
                            
                            $("#content").attr('disabled', true);
                        }else{
                            $("#content").attr('disabled', false);
                        }
                    });*/
                    //返回
                    $("#back").bind('click',function(){
                        window.location = "/admin/Rechargemange?parma=aprluntreated";
                    });
                   
                    //審核
                    $('#review').click(function () {
                        var appealSn = $('#appealSn').val();
                        var submitType = $('input[name=submitType]:checked').val();
                        var tipid = $('input[name=tips]:checked').val() ;
                        var tips;
                        if(tipid != 0){
                            tips = $('input[name=tips]:checked').next().attr('title').trim();
                            //tips = $('input[name=tips]:checked').parent().text().trim();
                        }else{
                            tips = $('#content').val().trim();
                            if(tips == ""){
                                alert("请填入说明文字!");
                                retrurn;
                            }
                        }
                        var memo = $('#memo').val().trim();
                        var userId = $('#userId').val();
                        var obj = new Object();
                        obj.appealSn = appealSn;
                        obj.appealStatus = submitType;
                        obj.appealMemo = tips;
                        obj.reviewMemo = memo;
                        obj.userId = userId;
                        $.ajax({
                            url:'/admin/Rechargemange/index?parma=ar',
                            method:"post",
                            data:{data:obj},
                            success:function(){
                                window.location = "/admin/Rechargemange?parma=aprluntreated";
                            }
                        });                        
                        
                    });
                    
                    $("input[name='submitType']").bind("click",function(){
                        var submitType = $('input[name=submitType]:checked').val();
                        var obj = new Object();
                        if(submitType == 2 ){
                            obj.tipsGroupb = 1;
                        }else{
                            obj.tipsGroupb = 0;
                        }
                        
                        obj.tipsModel= '0';
                        //obj.tipsGroupa = '0';
                        //obj.tipsGroupb = submitType;
                        $.ajax({
                            url:'/admin/Rechargemange/index?parma=qt',
                            dataType: 'json',
                            method:"post",
                            data:{data:obj},
                            success:function(data){
                                 if (data.length > 0) {
                                     var resultAll = eval(data);
                                     var html = "";
                                     $.each(resultAll, function (i) {
                                                                                                               //<input id="reason1" name="tips" type="radio" class="radio" value="{$data.id}"/>{$data.tipsMemo} &nbsp;<a title="{$data.tipsContext}">{mb_substr($data.tipsContext, 0, 32,'utf-8')}</a>
                                         html +="<label class='label' for='reason'><input id='reason' name='tips' type='radio' class='radio' value='"+ resultAll[i].id+"'>" + resultAll[i].tipsMemo  + " &nbsp;<a title='" + resultAll[i].tipsContext + "'>"+ resultAll[i].tipsContext.substr(0,32) + "</a></label><br/>";
                                     });
                                     $("#showTips").html(html);
                                 }
                            }
                        });
                    });
                })();
          </script>
        {/literal}
