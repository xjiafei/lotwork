	<ul class="charge-label-ul">
		{foreach from=$bankInfo item=data}
			{if $data.code <= 15 && $data.other ==1}
				{assign var=quickOpen value=1}
			{/if}			
		{/foreach}
		{if $quickOpen ==1}
			<li  class="{if $type ==1}current{/if}" id="expressRecharge"><img src="{$path_img}/images/funds/quick.png">&nbsp;快捷充值</li>	
		{/if}	
		<li  class="{if $type ==0}current{/if}" id="netRemit">网银汇款</li>
		{foreach from=$bankInfo item=data}
			{if $data.code ==31 && $data.deposit ==1}
				<li class="{if $type ==2}current{/if}" id="tenpayRecharge" >财付通充值</li>
			{else if $data.code ==31 && $data.deposit ==0}
				<li style="display:none"></li>
			{/if}
			{if $data.code ==51 && $data.deposit ==1}
				<li class="{if $type ==3}current{/if}" id="unionpayRecharge">银联充值</li>
			{else if $data.code ==51 && $data.deposit ==0}
				<li style="display:none"></li>
			{/if}
		{/foreach}
		{foreach from=$bankInfo item=data}
			{if $data.code ==30 && $data.deposit ==1 && $isAilOpen=='Y'}
				{if $data.version ==0}
					<li class="{if $type ==4}current{/if} " style="padding-right:5px" id="alipay">支付宝充值
					<img src='{$path_img}/images/common/new-icon.gif' style="vertical-align:top;" id="alipayNew"/>
					<span class="popuptextAlipay" id="myPopupAlipay">暂不可用哦~请明日0点后再尝试，建议先用其他支付方式</span>
					<input type="hidden" id="alipayDepositMode" value="6">
					</li>
				{/if}
				{if $data.version ==1}
					<li class="{if $type ==4}current{/if} " style="padding-right:5px" id="alipay">支付宝充值
					<img src='{$path_img}/images/common/new-icon.gif' style="vertical-align:top;" id="alipayNew"/>
					<span class="popuptextAlipay" id="myPopupAlipay">暂不可用哦~请明日0点后再尝试，建议先用其他支付方式</span>
					<input type="hidden" id="alipayDepositMode" value="8">
					</li>
				{/if}
			{else if $data.code ==30 && $data.deposit ==0}
				<li style="display:none"></li>
			{/if}
		{/foreach}
		{foreach from=$bankInfo item=data}
			{if $data.code ==40 && $data.deposit ==1}
				<li class="{if $type ==5}current{/if}" id="wechat">微信充值<img src='{$path_img}/images/common/new-icon.gif' style="vertical-align:top;"/></li>
			{else if $data.code ==40 && $data.deposit ==0}
				<li style="display:none"></li>
			{/if}
		{/foreach}
		{foreach from=$bankInfo item=data}
			{if $data.code ==35 && $data.deposit ==1 && $isQQOpen=='Y'}
				<li class="{if $type ==6}current{/if}" id="qq">QQ钱包充值<img src='{$path_img}/images/common/new-icon.gif' style="vertical-align:top;"/></li>
			{else if $data.code ==35 && $data.deposit ==0}
				<li style="display:none"></li>
			{/if}
		{/foreach}
	</ul>

<!-- <label class="appeal-link"> 充值未到账？<a href="/fundappeal/appealrechargelist">点这里</a></label> -->
<script>
$('.ui-tab-title ul li').click(function(){
	var indexs = $(this).index();
	if($(this).attr('class') !='current'){
		var labelId= $(this).attr('id');
        if(labelId=='expressRecharge')
        {
            indexs=1;
        }
        else if(labelId=='netRemit')
        {
            indexs=0;
        }else if(labelId=='tenpayRecharge')
        {
            indexs=2;
        }else if(labelId=='unionpayRecharge')
        {
            indexs=3;
        }else if(labelId=='alipay')
        {
            indexs=4;
        }else if(labelId=='wechat')
        {
            indexs=5;
	}else if(labelId=='qq')
        {
            indexs=6;
        }
		window.location.href="/fund/index?type="+indexs;
	}

});

$(function() {
		
	//驗證是否超過單日金額限制
	var checkIsOverDayLimit = function(){
		var returnResult=false;
		var depositMode = $('#alipayDepositMode').val();
		$.ajax({
			url:'/fund/checkBankDayLimit',
			type:'POST',
			dataType:'text',
			data:"depositMode="+depositMode,
			async: false,
			success:function(data){
				var result = JSON.parse(data);
				if(result['dayLimit']==0){
					$('#alipay').css('background-color', '#CCCCCC');
					$('#alipay').css('color', '#999999');
					$('#alipay').css('padding-right', '15px');					
					$('#alipayNew').hide();
					$("#alipay").unbind("click");
					$("#alipay").click(function(){
						document.getElementById('myPopupAlipay').classList.toggle('showAlipay');
					});	
				}
			},
		});	
	};
		
	if($('#alipay').length){ //當支付寶被開啟
		checkIsOverDayLimit();
		
			
	}
});
	
</script>
<style>
/* --------------------alipay check popup--------------------*/
/* Popup container - can be anything you want */
#alipay {
    position: relative;
    display: inline-block;
    cursor: pointer;
    user-select: none;
}

/* The actual popup */
#alipay .popuptextAlipay {
    visibility: hidden;
    width: 230px;
    background-color: #DBDBDB;
    color: #999999;
    text-align: left;
    border-radius: 6px;
    padding: 8px 5px;
    position: absolute;
    z-index: 3;
    bottom: 125%;
    left: 165%;
    margin-left: -80px;
}

/* Popup arrow */
#alipay .popuptextAlipay::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 5%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #DBDBDB transparent transparent transparent;
} 

/* Toggle this class - hide and show the popup */
#alipay .showAlipay {
    visibility: visible;
    animation: fadeIn 1s;
}
/* --------------------alipay check popup--------------------*/

</style>
