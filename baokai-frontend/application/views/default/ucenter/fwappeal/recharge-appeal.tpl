<!DOCTYPE HTML>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>充值申诉</title>
        <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
        <link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
        <link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
        {include file='/default/script-base.tpl'}
        <style>
            .bankHeight{
                min-height:45px;
            }
            .btn-mini{
                height:23px;
                line-height: 23px;
                font-size: 12px;
                padding-left: 5px;
                padding-right: 5px;
                margin-left:10px;
                vertical-align:bottom;
            }
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
                    <div class="title">充值申诉</div>
                    <dl class="prompt" style="margin-bottom:10px;">
                        <dt>申诉小提示：</dt>
                        <dd>1、假如您的充值有订单号，请选择对应的充值订单进行申诉，可以提高申诉处理速度。</dd>
                        <dd>2、如果工行用户充值未到账，请先使用平台的电子回单码功能进行申诉到账。</dd>
                        <dd>3、请提上传完整的汇款汇款回执单，切记不要掩盖任何信息，这样有助于快速审核通过。</dd>
                        <dd>4、充值前请先发起申请，获取最新充值卡的信息，如充值到旧卡将无法到账。</dd>
                    </dl>
                    <div class="content">
                        <!-- //////////////网银汇款////////////// -->
                        {include file='/default/ucenter/fwappeal/recharge-appeal-bank.tpl'}
                        <!-- /////////////网银汇款/////////////// -->
                        <!-- /////////////快捷充值////////////// -->
                        {include file='/default/ucenter/fwappeal/recharge-appeal-fast.tpl'}
                        <!-- /////////////快捷充值/////////////// -->
                        <!-- /////////////财付通/////////////// -->
                        {include file='/default/ucenter/fwappeal/recharge-appeal-tenpay.tpl'}
                        <!-- /////////////财付通/////////////// --> 
                        <!-- /////////////银联充值/////////////// --> 
                        {include file='/default/ucenter/fwappeal/recharge-appeal-unionpay.tpl'}
                        <!-- /////////////银联充值/////////////// --> 
                        <!-- /////////////支付宝/////////////// --> 
                        {include file='/default/ucenter/fwappeal/recharge-appeal-alipay.tpl'}
                        <!-- /////////////支付宝/////////////// --> 
                        <ul class="ui-form">
                            <li class="ui-btn">
                                <a class="btn btn-important" id="btnSubmit">提交<b class="btn-inner"></b></a>
                                <a class="btn" id="btnBack" href="/fundappeal/appealrechargelist" >返回<b class="btn-inner"></b></a>
                                <!--{if $cdSec gt 0}-->
                                <span style="color:red;">(充值申诉冷时间：<cdTime></cdTime>)</span>
                                <input type="hidden" id="cdSec" value="{$cdSec}"/>
                                <!--{/if}-->
                            </li>
                        </ul>  
                    </div>
                    <dl class="fund-info-supplement">
                        <dt>说明：充值未到账记录保存时间为15天，您可以查询最近15天的记录</dt>
                        <input type="hidden" id="status" name="status" value="{$status}"/>
                        <a id="pop-success" style="display:none"/>
                        <a id="pop-error" style="display:none"/>
                    </dl>
                </div>
            </div>
        </div>
        <script type="text" id="msgSuccessContent">
            <div class="bd text-center">
            <i class="ico-success"></i>
            <h4 class="pop-text">您的申诉申请已经提交，相关部门正在处理中</h4>
            <h4 class="pop-text">请您耐心等待，谢谢！</h4>
            </div>
        </script>
        <script type="text" id="msgErrorContent">
            <div class="bd text-center">
            <i class="ico-error"></i>
            <h4 class="pop-text">您的充值订单已经到账了，无法提交申诉信息</h4>
            <h4 class="pop-text">请查看您的充值记录！</h4>
            </div>
        </script>
        <!-- //////////////底侧公共页面////////////// -->
        {include file='/default/ucenter/footer.tpl'}
        <!-- /////////////底侧公共页面/////////////// -->
    </body>
    <script src="{$path_js}/js/inputCommon.js" type="text/javascript" ></script>
    <script src="{$path_js}/js/datePickerUtil.js" type="text/javascript" ></script>
    <script src="{$path_js}/js/userCenter/uploadFile.js" type="text/javascript" ></script>
    <script src="{$path_js}/js/userCenter/rechargeAppeal.js" type="text/javascript" ></script>
</html>