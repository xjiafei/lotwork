<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	{include file='/default/script-base.tpl'}
    <script type="text/javascript" src="{$path_js}/js/jquery.zclip.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Common.js"></script>
</head>
<body>
	{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->		
		</div>
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要充值</div>
				<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
					 {include file='/default/ucenter/fund/recharge_index_title.tpl'}
				</div>
				<div class="content bank-select">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td><div class="con">1.选择银行并填写金额</div></td>
									<td class="current"><div class="tri"><div class="con">2.确认信息充值</div></div></td>
									<td><div class="tri"><div class="con">3.登录网上银行充值</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="bank-check payee-info">
						<!-- <div class="bank-title">收款方信息<span class="info">以下信息是确保您充值到账的重要信息</span></div> -->
						<ul class="ui-form">
							<li>
								<a href="javascript:;" id="J-receive-person" class="copy">复制</a>
								<label class="ui-label">收款人姓名：</label>
								<span id="J-text-person" class="ui-singleline color-red">{$revAccName}</span>
							</li>
							<li>
								<a href="javascript:;" class="copy">复制</a>
								<label class="ui-label">收款财付通帐号：</label>
								<span class="ui-singleline color-red">{$rcvAccNum}</span>
							</li>
							<li>
								<a href="javascript:;" class="copy">复制</a>
								<label class="ui-label">充值金额：</label>
								<span class="ui-singleline color-red">{$chargeamount}</span> 元
							</li>
							<li>
								<a href="javascript:;" class="copy">复制</a>
								<label class="ui-label">附言：</label>
								<span class="ui-singleline color-red charge-memo">{$chargeMemo}</span>
								<i class="ico-ps"></i>
							</li>
							<li>
								<div class="prompt-ps">请复制附言填写入网页版财付通 "备注" 栏，请务必正确填写此项信息，填写错误或不填写会影响充值到帐</div>
							</li>
							<li class="line-solid"></li>
							<li>
								<label class="ui-label w-1" style="width:130px !important;"></label>
								<a href="{$url}" class="btn btn-primary" target="_blank">去财付通充值<b class="btn-inner"></b></a><br>
								<span class="prompt-ps-cft" style="">请您使用网页版财付通, 手机版财付通暂无法到帐, 有问题请 <a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" title="客服">联系客服</a></span>
							</li>
						</ul>
						<div class="payment-help">
							<div class="bank-check">
								<div class="bank-title">付款帮助：<a href="/help/queryGeneralDetail?helpId=950" target="_blank">图片帮助</a><a href=""><!-- 视频帮助 --></a></div>
								<div id="J-time" class="time">--:--</div>
                                <input type="hidden" id ="longTime"  value="{$longTime|default:'30'}" />
								<div class="text">为保证充值成功，请在<span class="color-red">{$longTime|default:'30'}</span>分钟之内完成支付</div>
							</div>
						</div>
					</div>
					<dl class="prompt">
						<dt>注意事项：</dt>
						<dd>1、务必复制“附言”到财付通汇款页面的“附言”栏中进行粘帖(键盘 CTRL+V)，否则充值将无法到账。</dd>
						<dd>2、附言为随机生成，一个附言只能充值一次，过期或重复使用充值将无法到账。</dd>
						<dd>3、收款账户名和财付通账号会不定期更换，请在获取最新信息后充值，否则充值将无法到账。</dd>
						<dd>4、“订单金额”与网银转账金额不符，充值将无法到账。</dd>
					</dl>
					<div class="help-text">更多帮助 <a target="_blank" href="/help/goIndex">进入帮助中心</a></div>
				</div>
			</div>
		</div>
		
	</div>
    <div id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-success"></i><h4 class="pop-text">复制成功</h4>
    </div>
    <div id="DivFailed" class="pop pop-error w-4" style="position:absolute;z-index:2; display:none">
        <i class="ico-error"></i><h4 class="pop-text">复制失败，请重试</h4>
    </div>
    <div class="pop-ps" style="left: 637.5px; top: 230px; display: none;"><img alt="" src="{$path_img}/images/funds/ps-cft.png"></div>
    <!-- //////////////底侧公共页面////////////// -->
    {include file='/default/ucenter/footer.tpl'}
    <!-- /////////////底侧公共页面/////////////// -->
    {literal}
	<script>
    $(function(){
        //充值有效时间倒计时
        var seconds =  $('#longTime').val()*60,
            timer,
            mm,
            ss;
        timer = setInterval(function(){
            if(seconds <= 0){
                clearInterval(timer);
                location.href = '/fund/checkchargeitem?type=2';
            }
            mm = Math.floor(seconds/60%60);
            ss = Math.floor(seconds%60);
            mm = mm < 10 ? '0' + mm : mm;
            ss = ss < 10 ? '0' + ss : ss;
            $('#J-time').text(mm + ':' + ss);
            seconds--;
        }, 1000);
        
        //操作后提示	 
        function fnDiv(obj){		
            var Idivdok = document.getElementById(obj);	
            var winScroll = document.documentElement.scrollTop || document.body.scrollTop;
            Idivdok.style.display="block";		
            Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
            Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+winScroll-40+"px";
        }
        
        $('.copy').each(function(index, element) {
            $(this).zclip({
                path:global_path_url+'/js/ZeroClipboard.swf', //记得把ZeroClipboard.swf引入到项目中 
                copy:function(){
                    return $(this).parent().find("span").text();
                },
                afterCopy:function(){
                    fnDiv('DivSuccessful');
                    setTimeout(function(){ $('#DivSuccessful').css("display","none");},1500);
                }
            })
        });

		    	/* jmpinfo */
			var jmpTimer = null;
			var oIcon = $(".ico-ps");
			var oPop = $(".pop-ps");
			var curLeft = 0, curTop = 0;
			curLeft += oIcon.offset().left;
			curTop += oIcon.offset().top;
			oIcon.mouseout(function(){
			jmpTimer = setTimeout(function(){
				oPop.fadeOut();
				} ,300);
			});
			oIcon.mouseover(function(){
				clearTimeout(jmpTimer);
				oPop.css("left", curLeft + oIcon.width() + 5 + 'px')
				oPop.css("top", curTop - oPop.outerHeight() + oIcon.height() + 'px')
				oPop.fadeIn();
			});
			oPop.mouseover(function(){
				clearTimeout(jmpTimer);
			});
			oPop.mouseout(function(){
				jmpTimer = setTimeout(function(){
				oPop.fadeOut();
			} ,300);
			});
    
    });
    </script>
    {/literal}
