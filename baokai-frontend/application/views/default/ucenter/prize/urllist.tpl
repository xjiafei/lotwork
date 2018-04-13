<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>开户中心</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
    <script type="text/javascript" src="{$path_js}/js/phoenix.Common.js"></script>
    <script type="text/javascript" src="{$path_js}/js/clipboard.min.js"></script>
<body>
{include file='/default/ucenter/header.tpl'}

	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<form action="/applycenter/managerurl/" method="post" id="J-form">
		
		<div class="g_28 g_last">
			<div class="common-article" style="position:relative;">
				<div class="title">开户中心</div>
				<div class="content">
					<div class="ui-tab">
						<div class="ui-tab-title tab-title-bg clearfix">
							<ul>
								<li><a href="/applycenter/index/">链接开户</a></li>
								<li class="current">链接管理</li>
							</ul>
						</div>
						<div class="">
                        <div  class="rebate-list-setup rebate-list-ico">
								<span class="ico-tab ico-tab-current" id="userAgent">代理({$agentCnt|default:'0'})</span>
								<span class="ico-tab" id="userPlayer">玩家({$userCnt|default:'0'})</span>
						</div>
                         {if $result|@count neq 0}
							<!-- start -->
							
                           
							<table class="table table-info">
								<thead>
									<tr>
										<th style="width:200px;">注册链接</th>
										<th>联系QQ</th>
										<th style="width:180px;">备注</th>
										<th>生成时间</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
								<!-- {foreach from=$result item=data} -->
									<tr>
										<td>
											<input type="input" class="input w-3" value="{$data.url}">
											<a href="javascript:void(0)" id="copylink" name="a-copy">复制</a>
										</td>
										<td>
											{$data.qq}
										</td>
										<td><a href="javascript:void(0)" title="点击修改备注" {if $data.memo eq ''} style="display:none;" {/if} class="text-modify"><span>{$data.memo}</span><i name="i-ico" class="ico-change"></i></a><a href="javascript:void(0)" {if $data.memo neq ''} style="display:none;"{/if} name="a-remark" title="点击添加备注" remark="{$data.id}">未设置</a><input type="input" remark="{$data.id}" name="J-input-remark" class="input w-3" style="display:none"></td>
										<td>{$data.gmtCreated}</td>
										<td>
											
											<!-- {if $data.registers <= 0}  -->
												<!-- {if $data.valiable eq '1'} -->
													未使用
												<!-- {else} -->
													<a class="link-expired">已过期(0)</a>
												<!-- {/if} -->
											<!-- {else} -->
												<!-- {if $data.valiable eq '1'} -->
													<a href="javascript:void(0)" remark="{$data.id}" name="a-userinfo">已注册({$data.registers})</a>
												<!-- {else} -->
													<a href="javascript:void(0)" remark="{$data.id}"  class="link-expired" name="a-userinfo">已过期({$data.registers})</a>
												<!-- {/if} -->
											<!-- {/if} -->
										</td>
										<td>
											<a href="/applycenter/geturlbyid?id={$data.id}" >详情</a>
											<a name="a-userdelete" href="javascript:void(0);"  remark={$data.id}>删除</a>
										</td>
									</tr>
									<!-- {/foreach} -->
								</tbody>
							</table>
                             
							<!-- start -->
							{if $pages}
								<div class="page-wrapper">
									<span class="page-text">共{$pages.count}条记录</span>
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
							{/if}
							<!-- end -->
                            {else}
                                <div class="alert alert-waring">
								<i></i>
								<div class="txt">
									<h4>暂未生成任何注册链接</h4>
								</div>
							</div>
                            {/if}
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</form>
	</div> 

<textarea id="AuditTip" style="display:none;" remarkid="">
    <div class="bd">
        <div class="pop-title">
            <i class="ico-waring"></i>
            <h4 class="pop-text"><input type="text" class="input" id="txtAuditTip" maxlength="16"></h4>
            <div class="ui-check"><i class="error"></i>备注的字符长度为4-16字符</div>
        </div>
        <div class="pop-btn">
            <a href="#" class="btn btn-important " id="J-submit-Audit1">修改<b class="btn-inner"></b></a>
        </div>
    </div>
</textarea>
<textarea id="CreateTip" style="display:none;" remarkid="">
    <div class="bd">
        <div class="pop-title">
            <i class="ico-waring"></i>
            <h4 class="pop-text"><input type="text" class="input" id="txtCreateTip" maxlength="16"></h4>
            <div class="ui-check"><i class="error"></i>备注的字符长度为4-16字符</div>
        </div>
        <div class="pop-btn">
            <a href="#" class="btn btn-important " id="J-submit-Create">设置<b class="btn-inner"></b></a>
        </div>
    </div>
</textarea>
<textarea id="userinfo" style="display:none;">
    <div class="bd">
        <div class="pop-title">
            <i class="ico-waring"></i>
            <h4 class="pop-text"><span id="spanUserInfo"></span></h4>
        </div>
        <div class="pop-btn">
            <a href="#" class="btn btn-important " id="J-btn-close">关闭<b class="btn-inner"></b></a>
        </div>
    </div>
</textarea>
<textarea id="userDelete" style="display:none;" remarkid="">
    <div class="bd">
        <div class="pop-title">
            <i class="ico-waring"></i>
            <h4 class="pop-text"><span>你确定删除该条注册链接？</span></h4>
        </div>
        <div class="pop-btn">
            <a href="#" class="btn btn-important " id="J-btn-Confirm">确定<b class="btn-inner"></b></a>
        </div>
    </div>
</textarea>

<div id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-success"></i><h4 class="pop-text">复制成功</h4>
</div>
<div id="DivFailed" class="pop pop-error w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-error"></i><h4 class="pop-text">复制失败，请重试</h4>
</div>
<div id="DivSuccessful2" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-success"></i><h4 class="pop-text">操作成功</h4>
</div>
<div id="DivFailed2" class="pop pop-error w-4" style="position:absolute;z-index:2; display:none">
	<i class="ico-error"></i><h4 class="pop-text">操作失败，请重试</h4>
</div>
<script type="text/javascript">
(function($){
	
    var minWindow,mask,initFn,minWindow = new phoenix.MiniWindow(),mask = phoenix.Mask.getInstance();
	
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	
	
	if(phoenix.util.getParam("type")==0|| phoenix.util.getParam("type")=="0#")
	{
		$("#userPlayer").addClass("ico-tab-current");
		$("#userAgent").removeClass("ico-tab-current");
	}else 
	{
		$("#userAgent").addClass("ico-tab-current");
		$("#userPlayer").removeClass("ico-tab-current");
	}
	
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		var winScroll = document.documentElement.scrollTop || document.body.scrollTop;
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+winScroll-40+"px";
	}

		
	$("#userPlayer").click(function(){
		window.location="/applycenter/managerurl/?type=0";
		$(this).addClass("ico-tab-current");
		$("#userAgent").removeClass("ico-tab-current");
	});
	$("#userAgent").click(function(){
		window.location="/applycenter/managerurl/?type=1";
		$(this).addClass("ico-tab-current");
		$("#userPlayer").removeClass("ico-tab-current");
	});

	$("[name='a-copy']").click(function(){
		var linkid=$(this).parent().find("input");
		var clipboard = new Clipboard('#copylink', {
      		text: function() {
           		return linkid.val();
     		}
   		});
    	clipboard.on('success', function(e) {
    		fnDiv('DivSuccessful');
    		setTimeout(function(){ $('#DivSuccessful').css("display","none");},1500);
   		});
    	clipboard.on('error', function(e) {
        	fnDiv('DivFailed');
    		setTimeout(function(){ $('#DivFailed').css("display","none");},1500);
    	});
	});

    $(document).on("click","[name='a-remark']",function(e){
		var remarkid=$(this).attr("remark");
		$("#CreateTip").attr("remarkid",remarkid);
		e.preventDefault();		
		minWindow.setTitle('添加备注');
		minWindow.setContent($('#CreateTip').val());
		minWindow.show();
	});
	
	$(document).on("click","[name='i-ico']",function(e){
		var remarkid=$(this).parent().parent().find("input").attr("remark");
		$("#AuditTip").attr("remarkid",remarkid);
		e.preventDefault();		
		minWindow.setTitle('修改备注');
		minWindow.setContent($('#AuditTip').val());
		minWindow.show();
	});
	
	$(document).on("click","#J-btn-close",function(e){
		minWindow.hide();
	});
	
	$(document).on("click","[name='a-userinfo']",function(e){
		var remarkid=$(this).attr("remark");
		 $.ajax({
			url:'/applycenter/getregisters',
			dataType:'json',
			method:'post',
			data:"id="+remarkid,					
			success:function(data){
				e.preventDefault();		
		        minWindow.setTitle('当前链接已注册用户列表');
		        minWindow.setContent($('#userinfo').val());
		        minWindow.show();
				var sMessage=data.data;
				sMessage="<textarea style='width:400px;height:80px;' readonly='readonly'>"+sMessage+"</textarea>";
				$("#spanUserInfo").html(sMessage);
			},
			error: function()
			{
				minWindow.hide();
				fnDiv('DivFailed2');
			}				
		});	
	});
	var WidthCheck=function(str){  
		var w = 0;  
		var tempCount = 0; 
		for (var i=0; i<str.length; i++) {  
		   var c = str.charCodeAt(i);  
		   //单字节加1  
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			w++;  
		  
		   }else {     
			w+=2;
		   }  
		 }
		return w;
	};  
	
	$(document).on("blur","#txtAuditTip",function(e){
		if(WidthCheck($(this).val())< 4 || WidthCheck($(this).val()) >16)
		{
			$(this).parent().parent().find('.ui-check').show();
		}
		else
		{
			$(this).parent().parent().find('.ui-check').hide();
		}
	});
	
	$(document).on("blur","#txtCreateTip",function(e){
		if(WidthCheck($(this).val())< 4 || WidthCheck($(this).val()) >16)
		{
			$(this).parent().parent().find('.ui-check').show();
		}
		else
		{
			$(this).parent().parent().find('.ui-check').hide();
		}
	});
	
	$(document).on("click","#J-submit-Create",function(e){
		var otxtAudit=$("#txtCreateTip");
		if(WidthCheck(otxtAudit.val())< 4 || WidthCheck(otxtAudit.val()) >16)
		{
			otxtAudit.parent().parent().find('.ui-check').show();
			return;
		}
		var sdata="id="+$("#CreateTip").attr("remarkid")+"&memo="+otxtAudit.val();
		 $.ajax({
			url:'/applycenter/modifymemo',				
			dataType:'json',
			method:'post',
			data:sdata,					
			success:function(data){
				if(data.status=="error"){					
					fnDiv('DivFailed2');	
				}
				else{
					fnDiv('DivSuccessful2');
					location.reload();
				}
			},
			error: function()
			{
				fnDiv('DivFailed2');
				
			},complete: function()
			{
				minWindow.hide();
			}
		});	
		
	});
	
	
	$(document).on("click","#J-submit-Audit1",function(e){
		var otxtAudit=$("#txtAuditTip");
		if(WidthCheck(otxtAudit.val())< 4 || WidthCheck(otxtAudit.val()) >16)
		{
			otxtAudit.parent().parent().find('.ui-check').show();
			return;
		}
		var sdata="id="+$("#AuditTip").attr("remarkid")+"&memo="+otxtAudit.val();
		 $.ajax({
			url:'/applycenter/modifymemo',				
			dataType:'json',
			method:'post',
			data:sdata,					
			success:function(data){
				if(data.status=="error"){					
					fnDiv('DivFailed2');	
				}
				else{
					fnDiv('DivSuccessful2');
					location.reload();
				}
			},
			error: function()
			{
				fnDiv('DivFailed2');
				
			},complete: function()
			{
				minWindow.hide();
			}
		});	
		
	});
	
	$(document).on("click","[name='a-userdelete']",function(e){
		  var remarkid=$(this).attr("remark");
		  $("#userDelete").attr("remarkid",remarkid);
		  minWindow.setTitle('温馨提示');
		  minWindow.setContent($('#userDelete').val());
		  minWindow.show();
	});
	$(document).on("click","#J-btn-Confirm",function(e){
		var sdata="id="+$("#userDelete").attr("remarkid");
			$.ajax({
				url:'/applycenter/deleteurlbyid',				
				dataType:'json',
				method:'post',
				data:sdata,					
				success:function(data){
					if(data.status=="ok"){	
					   fnDiv('DivSuccessful2');
					   location.reload();
					}
					else{
						fnDiv('DivFailed2');
					}
					
				},
				error: function()
				{
					fnDiv('DivFailed2');
				},complete: function()
				{
					 minWindow.hide();
				}					
		});	
		
	});
})(jQuery);
</script>
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->	