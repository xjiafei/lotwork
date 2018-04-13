<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>催到账</title>
    <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />	
	
	<script type="text/javascript" src="{$path_img}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.base.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.util.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="{$path_img}/js/phoenix.Message.js"></script>
	   {include file='/default/script-base.tpl'}
</head>
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
<body>
	
	<!-- toolbar start -->
	<!-- toolbar end -->
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
                                <dt>提现未到账小提示：</dt>
                                <dd>1、审核待定可申诉的提现订单会进入到提现申诉页签中。</dd>
                                <dd>2、鼠标移入每笔订单待定原因后，可以在浮层中查看待定原因的详情。</dd>
                            </dl>
				<div class="content">
                        <div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
						<ul>
                                                <li><a href="/fundappeal/appealrechargelist">充值申诉</a></li>
                                                <li class="current">提现申诉</li>
                                                <li><a href="/fundappeal/appealstatuslist">申诉进度查询</a></li>
						</ul>
                        <label class="appeal-link">查看提现记录？<a href="/withdraw/history">点这里</a></label>
					</div>
								
					<!-- {if $total>0} -->
					<table class="table table-info">
						<thead>
							<tr>
								<th>交易流水号</th>
								<th>发起时间</th>
								<th>申请提现</th>
								<th>提现银行</th>
								<th>卡片尾号</th>
                                    <th>待定原因</th>
								<th>申诉</th>
							</tr>
						</thead>
						<tbody>
						<!-- {foreach from=$content item=data}-->
							<tr>
                                {if $data.isSeperate eq 'Y'}
								    <td class="sp-td"><a class="showText1" ><div style="word-wrap:break-word"><label>{$data.seperateTip}</label></div>{$data.sn}</a>
                                        <input type="hidden" value="{$data.sn}"/></td>
                                {else}
                                    <td>{$data.sn}<input type="hidden" value="{$data.sn}"/></td>
                                {/if}
								<td>{$data.applyTime}</td>
								<td>{$data.withdrawAmt}</td>
								<td>{$data.bankName}</td>
								<td>{$data.cardNumber}</td>
                                <td><a class="showText"><div style="word-wrap:break-word"><label>{$data.appealTips}</label></div>{$data.appealMemoSubStr}</a>
                                 <input type="hidden" id="dataMemo" value="{$data.appealTips}"/></td>
								<!-- {if $data.appealStatus==0 || $data.appealStatus==3} -->
								<td><a href="javascript:;" class="btn btn-small" name="startappeal">申诉</a></td>
								<!-- {else} -->
								<td><a href="javascript:;" class="btn btn-small btn-disable">处理中</a></td>								
								<!-- {/if} -->
							</tr>							
						<!-- {/foreach} -->							
						</tbody>
					</table>		
								<div class="page-wrapper">
									<span class="page-text">共{$total}条记录</span>
									<div class="page page-right">
										{if $pages.pre && $pages.currpage.index!=1}
				    							<a  class="prev" onClick="$('#fm_main').submit();" href="?page={$pages.pre.index}&sn={$_POST.sn}&fromDate={$_POST.fromDate}&toDate={$_POST.toDate}&status={$_POST.status}">上一页</a>
				    						{/if}
				    					{foreach from=$pages.steps item=item}
				    						{if $item.index == $pages.currpage.index}
				    							<a class="current" href="javascript:void(0);">{$item.text}</a>
				    						{else}
				    							<a onClick="$('#fm_main').submit();" href="?page={$item.index}&sn={$_POST.sn}&fromDate={$_POST.fromDate}&toDate={$_POST.toDate}&status={$_POST.status}">{$item.text}</a>
				    						{/if}
				    					{/foreach}
				    					{if $pages.next && $pages.currpage.index!=$pages.max.index}
				    						<a  class="next" onClick="$('#fm_main').submit();" href="?page={$pages.next.index}&sn={$_POST.sn}&fromDate={$_POST.fromDate}&toDate={$_POST.toDate}&status={$_POST.status}">下一页</a>
				    					{/if}
										<span class="page-few">到第 <input type="text" name="page" class="input" value="{$pages.currpage.index}"> /{$pages.max.index}页</span>
										<input type="button" class="page-btn" onClick="$('#fm_main').submit();" value="确 认">
									</div>
								</div>
					<!-- {else} -->	
					<div class="alert alert-waring">
						<i></i>
						<div class="txt">
							<h4>没有符合条件的记录！</h4>
						</div>
					</div>
					<!-- {/if} -->

				</div>			
				
				<dl class="fund-info-supplement">
					<dt>说明：提现未到帐记录保存时间为15天，您可以查询最近15天的记录</dt>
				</dl>
			</div>
		</div>
		
	</div>

</body>



	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->

<script>
(function(){
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
	//申訴按鈕
	$(document).on('click', '[name="startappeal"]', function(e){
		var oTr=$(this).parent().parent();		
		var sdata="sn="+$.trim(oTr.find('input[type=hidden]').val());
               
		//window.open("/withdraw/queryprocess");
		window.location="/fundappeal/appealself"+"?"+sdata;
		/*var w = window.open();
		$.post("/withdraw/queryprocess", {
		sn : sdata
		},
	
		function(data) {
		$(w.document.body).html(data);

		});*/
		
		//alert($.trim(oTr.find("th:eq(0)").text()));
		//alert(sdata);
		

		
		
	/*	$.ajax({
			url:'/withdraw/queryprocess',	
			dataType:'html',
			method:'post',
			data:sdata,					
			success:function(data){				
				alert("oh ya");				
				$(w.document.body).html(data);
			
			},	
			error:function(xhr, ajaxOptions, thrownError){
                    alert("NO");
                 }
				
		});	*/
	});
	
	
	
	
	
})();
</script>





</html>