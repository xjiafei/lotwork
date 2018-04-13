<!DOCTYPE HTML>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>催到账</title>
        <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
        <link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
        <link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
        {include file='/default/script-base.tpl'}
        <style>
            .showText label{	
                display:none;
                color:#fff;
                background:rgba(51,51,51,0.75);
                padding:20px;
                border-radius:4px;
                -moz-border-radius:4px;
                -webkit-border-radius:4px;
                width: 300px;
                white-space:pre-wrap;
                position: absolute;
                z-index:10;
                overflow:auto;
                text-align:left; 
            }
            .showText { display: inline-block;}
            .showText:hover label{ display:block; }
            .showText1 label{  
                display:none;
                color:#fff;
                background:rgba(51,51,51,0.75);
                padding:20px;
                border-radius:4px;
                -moz-border-radius:4px;
                -webkit-border-radius:4px;
                width: 300px;
                white-space:pre-wrap;
                position: absolute;
                z-index:10;
                overflow:auto;
                text-align:left; 
            }
            .showText1 { display: inline-block;}
        </style>
    </head>
    <body>
        <!-- header start -->
        {include file='/default/ucenter/header.tpl'}
        <!-- header end -->

        <div class="g_33 common-main">
            <div class="g_5">
                <!-- //////////////左侧公共页面////////////// -->
                {include file='/default/ucenter/left.tpl'}
                <!-- /////////////左侧公共页面/////////////// -->
            </div>
            <div class="g_28 g_last">
                <div class="common-article">
                    <div class="title">催到账</div>
                    <dl class="prompt" style="margin-bottom:10px;">
                        <dt>小提示：</dt>
                        <dd>1、申诉发起成功后，相关部门会在30分钟内处理完毕您的申请。</dd>
						<dd>2、如若您的充值有违规，平台将会进行退款处理，请您查看您的个人银行卡账户信息</dd>
						<dd>3、提现进度查询入口：投注管理-账户明细-提现记录-操作项-查看</dd>
						<dd>4、点击处理结果可查看申诉订单详情</dd>
                    </dl>
                    <form action="/fundappeal/appealstatuslist" id="F-query" method="post">
                        <div class="content">
                            <div class="ui-tab-title tab-title-bg clearfix">
                                <ul>
                                    <li><a href="/fundappeal/appealrechargelist">充值申诉</a></li>
                                    <li><a href="/fundappeal/appealwithdrawlist">提现申诉</a></li>
                                    <li class="current">申诉进度查询</li>
                                </ul>
                            </div>
                            <ul class="ui-search clearfix">
                                <li class="date">
                                    <label for="dateStar" class="ui-label">起始日期：</label>
                                    <input type="text" class="input" id="J-date-start" name="fromDate" value="{$fromDate}"> 
                                    - 
                                    <input type="text" class="input" id="J-date-end" name="toDate" value="{$toDate}">
                                </li>
                                <li class="state">
                                    <label class="ui-label">状态：</label>
                                    <span class="ico-tab {if $status==''}ico-tab-current{/if}" value="">全部</span>
                                    <span class="ico-tab {if $status=='1'}ico-tab-current{/if}" value="1">处理中</span>
                                    <span class="ico-tab {if $status=='2'}ico-tab-current{/if}" value="2">申诉成功</span>
                                    <span class="ico-tab {if $status=='3'}ico-tab-current{/if}" value="3">申诉失败</span>
                                    <span class="ico-tab {if $status=='4'}ico-tab-current{/if}" value="4">待定</span>
                                    <input type="hidden" id="status" name="status" value="{$status}" />
                                     <a class="btn btn-important" id="J-btn-search">查 询<b class="btn-inner"></b></a>
                                </li>
                               
                            </ul>
                            <!-- {if $pages.count gt '0'} -->
                            <table class="table table-info">
                                <thead>
                                    <tr>
                                        <th>申诉编号</th>
                                        <th>申诉类型</th>
                                        <th>申诉时间</th>
                                        <th>处理结果</th>
                                        <th>处理状态</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- {foreach from=$content item=data} -->
                                    <tr>
                                        <td {if $data.isSeperate eq 'Y'}class="sp-td" {/if}>
                                            {if $data.showNewStatusPoint}<img id="wdRedPoint" src="{$path_img}/images/admin/red.png" width="10" height="10"/>{/if}
                                            <a class="showText1" ><div style="word-wrap:break-word"><label>{$data.seperateTip}</label></div>{$data.appealSn}</a>
                                            <input type="hidden" id="dataSn" value="{$data.appealSn}"/>
                                        </td>
                                        <td>
                                            <a class="showText item_popup">
                                                {if $data.type=='WITHDRAW'}提现未到账<input type="hidden" id="dataType" value="提现未到账"/>{/if}
                                                {if $data.type=='RECHARGE'}充值未到账<input type="hidden" id="dataType" value="充值未到账"/>{/if}
                                            </a>
                                        </td>
                                        <td>{$data.argueTime}<input type="hidden" id="dataArgueTime" value="{$data.argueTime}"/></td>
                                        <td><a class="showText"><label><div style="word-wrap:break-word">{$data.memo}</div></label>{$data.memoStr}</a><input type="hidden" id="dataMemo" value="{$data.memo}"/></td>
                                        <td>
                                            {if $data.status=='1'}处理中<input type="hidden" id="dataStatus" value="处理中"/>{/if}
                                            {if $data.status=='2'}申诉成功<input type="hidden" id="dataStatus" value="申诉成功"/>{/if}
                                            {if $data.status=='3'}申诉失败<input type="hidden" id="dataStatus" value="申诉失败"/>{/if}
                                            {if $data.status=='4'}待定<input type="hidden" id="dataStatus" value="待定"/>{/if}
                                        </td>
                                        <td style="display:none">
                                           <input type="hidden" id="dataAppealType" value="{$data.type}"/>
                                           <input type="hidden" id="dataAccount" value="{$data.account}"/>
                                           <input type="hidden" id="dataCreator" value="{$data.appealCreator}"/>
                                           <input type="hidden" id="dataAppealTime" value="{$data.appealTime}"/>
                                           <input type="hidden" id="dataFundSn" value="{$data.fundSn}"/>
                                           <input type="hidden" id="dataFundAmt" value="{$data.fundAmt}"/>
                                           <input type="hidden" id="dataFundTime" value="{$data.fundTime}"/>
                                           <input type="hidden" id="dataFundCard" value="{$data.fundCard}"/>
                                           <input type="hidden" id="dataFundCardUser" value="{$data.fundCardUser}"/>
                                           <input type="hidden" id="dataDepositeMode" value="{$data.depositeMode}"/>
                                           <input type="hidden" id="dataBankName" value="{$data.bankName}"/>
                                           <input type="hidden" id="dataTenpayAccount" value="{$data.tenpayAccount}"/>
                                           <input type="hidden" id="dataTenpayName" value="{$data.tenpayName}"/>
                                           <input type="hidden" id="dataChargeMemo" value="{$data.chargeMemo}"/>
                                        </td>
                                    </tr>
                                    <!-- {/foreach} -->
                                </tbody>
                            </table>
                            <!-- {else} -->
                            <div class="alert alert-waring">
                                <i></i>
                                <div class="txt">
                                    <h4>没有符合条件的记录！</h4>
                                </div>
                            </div>
                            <!-- {/if} -->
                            <!-- {if $pages} -->
                            <div class="page-wrapper">
                                <span class="page-text">共{$pages.count}条记录</span>
                                <div class="page page-right">
                                    <!-- {if $pages.pre && $pages.currpage.index!=1} -->
                                    <a class="pageBtn prev" page="{$pages.pre.index}">上一页</a>
                                    <!-- {/if} -->                            
                                    <!-- {foreach from=$pages.steps item=item} -->
                                    <!-- {if $item.index == $pages.currpage.index} -->
                                    <a class="current">{$item.text}</a>
                                    <!-- {else} -->
                                    <a class="pageBtn" page="{$item.index}">{$item.text}</a>
                                    <!-- {/if} -->
                                    <!-- {/foreach} -->
                                    <!-- {if $pages.next && $pages.currpage.index!=$pages.max.index} -->
                                    <a class="pageBtn next" page="{$pages.next.index}">下一页</a>
                                    <!-- {/if} -->
                                    <span class="page-few">
                                        到第<input type="text" id="txt-page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页
                                        <input type="button" value="确 认" class="pageBtn page-btn">
                                        <input type="hidden" id="page" name="page" value="{$pages.currpage.text}"/>
                                        <input type="hidden" id="total" name="total" value="{$pages.count}"/>
                                        <input type="hidden" id="pageSize" name="pageSize" value="{$pages.pageSize}"/>
                                    </span>
                                </div>
                            </div>
                            <!-- {/if} -->
                        </div>
                    </form>
                    <dl class="fund-info-supplement">
                        <dt>说明：申诉进度列表记录保存15天，您可以查询到最近15天的申诉进度列表记录</dt>
                    </dl>
                </div>
            </div>

        </div>
        <!-- //////////////底侧公共页面////////////// -->
        {include file='/default/ucenter/footer.tpl'}
        <!-- /////////////底侧公共页面/////////////// -->

        <!--弹框-->
        <script type="text" id="item_popup_box">
            <table class="ui-table">
            <tr>
            <td>
            <span>申诉编号：</span>
            <span>[[sn]]</span>
            </td>
            <td>
            <span>申诉类型：</span>
            <span>[[type]]</span>
            </td>
            </tr>
            <tr>
            <td>
            <span>申诉时间：</span>
            <span>[[createDate]]</span>
            </td>
            <td>
            <span>处理时间：</span>
            <span>[[updateDate]]</span>
            </td>
            </tr>
            <tr>
            <td>
            <span>处理结果：</span>
            <span class="color-red">[[status]]</span>
            </td>
            <td></td>
            </tr>
            </table>
            <div class="ui-description">
            <h3 class="ui-des-t">申诉内容</h3>
            <div class="ui-des-c">
            <div class="us-des-scroll">
            [[content]]
            </div>
            </div>
            </div>
            <div class="ui-description">
            <h3 class="ui-des-t">客服处理描述</h3>
            <div class="ui-des-c">
            [[memo]]
            </div>
            </div>
        </script>
    </body>
    {literal}
        <script>
            (function () {
                var op =false;
                $(document).on('click', function(){
                    if(op){
                       op = false;
                       return;
                   }
                    $(".showText1 label").hide();
                });

                $(".showText1").on('click',  function(){
                    $(".showText1 label").hide();
                    $(this).children().children().show();
                    op = true;
                });
                var inputStart = $('#J-date-start'),
                        inputEnd = $('#J-date-end');
                inputStart.focus(function () {
                    var dt = new phoenix.DatePicker({input: this});
                    dt.show();
                });
                inputEnd.focus(function () {
                    var dt = new phoenix.DatePicker({input: this});
                    dt.show();
                });
                $('.ico-tab').bind('click', function () {
                    $('.ico-tab').removeClass('ico-tab-current');
                    $(this).addClass('ico-tab-current');
                    var status = $(this).attr('value');
                    $('#status').val(status);
                });
                $('#J-btn-search').bind('click', function () {
                    $('#F-query').submit();
                });
                $('.pageBtn').bind('click', function () {
                    var p = $(this).attr('page');
                    if (p == null)
                        p = $('#txt-page').val();
                    $('#page').val(p);
                    $('#F-query').submit();
                });
                var prepaidDataContent = function(item){
                    var content = '';
                    var appealType = item.find('[id="dataAppealType"]').val();
                    switch(appealType){
                        case 'WITHDRAW':
                            content = prepaidWithdrawContent(item);
                            break;
                        case 'RECHARGE':
                            content = prepaidRechargeContent(item);
                            break;
                    }
                    return content;
                };
                var prepaidWithdrawContent = function(item){
                      var content = '';
                    var account = item.find('[id="dataAccount"]').val();
                    var amount = item.find('[id="dataFundAmt"]').val();
                    var userName = item.find('[id="dataFundCardUser"]').val();
                    var card = item.find('[id="dataFundCard"]').val();
                    var fundTime = item.find('[id="dataFundTime"]').val();
                    var appealType = item.find('[id="dataAppealType"]').val();
                    var amountText = appealType=='WITHDRAW'?'提现金额：':'充值金额：';
                    var nameText = appealType=='WITHDRAW'?'绑定卡姓名：':'付款人姓名：';
                    var cardText = appealType=='WITHDRAW'?'绑定卡号：':'付款人卡号：';
                    var timeText = appealType=='WITHDRAW'?'提现具体时间：':'充值具体时间：';
                    if(account!=null && account.length>0)content+='平台账户：'+account+'<br>';
                    if(amount!=null && amount.length>0)content+=amountText+amount+'<br>';
                    if(userName!=null && userName.length>0)content+=nameText+userName+'<br>';
                    if(card!=null && card.length>0)content+=cardText+card+'<br>';
                    if(fundTime!=null && fundTime.length>0)content+=timeText+fundTime+'<br>';
                    return content;
                };
                var prepaidRechargeContent = function(item){
                    var content = '';
                    var account = item.find('[id="dataAccount"]').val();
                    var amount = item.find('[id="dataFundAmt"]').val();
                    var userName = item.find('[id="dataFundCardUser"]').val();
                    var card = item.find('[id="dataFundCard"]').val();
                    var fundTime = item.find('[id="dataFundTime"]').val();
                    var depositeMode = item.find('[id="dataDepositeMode"]').val();
                    var bankName = item.find('[id="dataBankName"]').val();
                    var tenpayAccount = item.find('[id="dataTenpayAccount"]').val();
                    var tenpayName = item.find('[id="dataTenpayName"]').val();
                    var chargeMemo = item.find('[id="dataChargeMemo"]').val();
                    
                    if(account!=null && account.length>0)content+='平台账户：'+account+'<br>';
                    if(amount!=null && amount.length>0)content+='充值金额：'+amount+'<br>';
                    if(userName!=null && userName.length>0)content+='付款人姓名：'+userName+'<br>';
                    if(card!=null && card.length>0)content+='付款人卡号：'+card+'<br>';
                    if(fundTime!=null && fundTime.length>0)content+='充值具体时间：'+fundTime+'<br>';
                    if(tenpayAccount!=null && tenpayAccount.length>0)content+='财付通帐号：'+tenpayAccount+'<br>';
                    if(tenpayName!=null && tenpayName.length>0)content+='财付通姓名：'+tenpayName+'<br>';
                    if(depositeMode!=null && depositeMode.length>0)content+='充值方式：'+depositeMode+' '+bankName+'<br>';
                    if(chargeMemo!=null && chargeMemo.length>0)content+='充值附言：'+chargeMemo+'<br>';
                    return content;
                };
                var prepaidContent = function(item){
                    var content = $('#item_popup_box').text();
                    content = content.replace('[[sn]]', item.find('[id="dataSn"]').val());
                    content = content.replace('[[type]]', item.find('[id="dataType"]').val());
                    content = content.replace('[[createDate]]', item.find('[id="dataArgueTime"]').val());
                    content = content.replace('[[updateDate]]', item.find('[id="dataAppealTime"]').val());
                    content = content.replace('[[memo]]', item.find('[id="dataMemo"]').val());
                    content = content.replace('[[status]]', item.find('[id="dataStatus"]').val());
                    content = content.replace('[[content]]', prepaidDataContent(item));
                    return content;
                };
                $('.item_popup').bind('click', function () {
                    var Wd = phoenix.Message.getInstance();
                    var item = $(this).parent().parent();
                    Wd.show({
                        mask: true,
                        title: '申诉详细',
                        content: prepaidContent(item),
                        confirmIsShow: true,
                        cancelIsShow: false,
                        confirmFun: function () {
                            Wd.hide();
                        },
                        cancelFun: function () {
                            //nothing to do.
                        }
                    });
                });
            })();
        </script>
    {/literal}
</html>