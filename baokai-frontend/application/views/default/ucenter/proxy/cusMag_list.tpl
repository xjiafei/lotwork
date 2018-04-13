<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/proxy/proxy.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />	
 {include file='/default/script-base.tpl'}
	
</head>
<body>

{include file='/default/ucenter/header.tpl'}
	<div class="g_33 common-main">
		<div class="g_5">
    		<!-- //////////////左侧公共页面////////////// -->
    			{include file='/default/ucenter/left.tpl'}
    		<!-- /////////////左侧公共页面/////////////// -->	
    	</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">客户管理</div>
				<ul class="ui-tab-title clearfix" style="margin-bottom:10px;display:none"  id='_showTag'>
					<li class="current">彩票</li>
					<li><a href="{$ptgame_server}/pt/proxy/cusmag">老虎机</a></li>
				</ul>
				<div class="content">
				<form id="fm_main" name="fm_main" method="post">
					<ul class="ui-search search-porxy clearfix">
						<li class="name">
							<label for="name" class="ui-label">用户名：</label>
							<input type="text" name="username" id="username" value="{$username}" class="input" maxlength="16" />
						</li>
						<li class="funds" id="J-money-bound">
							<label for="funds" class="ui-label">余额：</label>
							<input type="text" name="moneyFrom" value="{$moneyFrom}" id="funds" class="input" maxlength="20" /> 至 <input name="moneyTo" id="funds2" type="text" value="{$moneyTo}" class="input" maxlength="20" />
						</li>
						<li class="time">
							<label class="ui-label">登录时间：</label>
							<select id="lastLogin" name="lastLogin" class="ui-select">
								<option value="0" {if $lastLogin eq 0}selected="selected"{/if}>默认</option>
								<option value="1" {if $lastLogin eq 1}selected="selected"{/if}>七天内</option>
								<option value="2" {if $lastLogin eq 2}selected="selected"{/if}>一个月内</option>
								<option value="3" {if $lastLogin eq 3}selected="selected"{/if}>三个月内</option>
								<option value="4" {if $lastLogin eq 4}selected="selected"{/if}>六个月内</option>
								<option value="5" {if $lastLogin eq 5}selected="selected"{/if}>更早以前</option>
							</select>
						</li>
						<li><a class="btn btn-important" href="javascript:void(0);" onClick="$('#fm_main').submit();">搜 索<b class="btn-inner"></b></a></li>
					</ul>
					{if $lvlPath}
					<div class="crumbs"><strong>客户层级：</strong>
					<a href="/proxy/cusmag">客户管理</a> 
					{foreach $lvlPath as $path}
					&gt; {if $path@last}{$path.name}{else}<a href="javascript:void(0);" onClick='smtByUser({$path.uId},"{$path.name}");'>{$path.name}</a>{/if}
					{/foreach}
					</div>
					{/if}
					<div class="ui-tab">
						{if $isSearch neq 1}
						<div class="ui-tab-title tab-title-bg clearfix">
							<ul>
								<li {if $type eq '0'}class="current"{/if} onClick="$('#type').val(0);$('#fm_main').submit();">{$proxyTitle} {if $proxyCount}({$proxyCount}){/if}</li>
								<li {if $type ne '0'}class="current"{/if} onClick="$('#type').val(1);$('#fm_main').submit();">玩家{if $userCount}({$userCount}){/if}</li>
							</ul>
						</div>
						{/if}
						<div class="ui-tab-content-current">
							<table class="table table-info table-group">
							{if $list|@count != 0}
								<thead>
									<tr>
										<th>用户名</th>
										<th>客户类型</th>
										<th class="table-toggle" onClick="if($('#moneySort').val()==0)$('#moneySort').val(1);else $('#moneySort').val(0);$('#sortType').val(0); $('#fm_main').submit();">余额<i class="{if $moneySort eq 0}ico-down{else}ico-up{/if}"></th>
										<th class="table-toggle" onClick="if($('#lastLoginSort').val()==0)$('#lastLoginSort').val(1);else $('#lastLoginSort').val(0);$('#sortType').val(1); $('#fm_main').submit();">最后登录<i class="{if $lastLoginSort eq 0}ico-down{else}ico-up{/if}"></th>
										<th>操作</th>
									</tr>
								</thead>
							{/if}
								<tbody>
								{foreach $list as $li}
									<tr>
										{if $li.userLvl neq -1 && $li.teamCount}
											<td><a href="javascript:void(0);" onClick='smtByUser({$li.uId},"{$li.username}");'>{$li.username}({$li.teamCount})</a></td>
										{else}
											<td > <span pro='{$li.uId}'>{$li.username}</span></td>
										{/if}
										
										<td>{$li.cusType}</td>
										<td>{$li.balance}</td>
										<td>{$li.lastLogin}</td>
										<td class="table-tool">
											<a target="_blank" href="{$game_server}/gameUserCenter/queryOrdersManagement?userId={$li.uId}&account={$li.username}" title="投注记录" class="ico-note"></a>
											<a href="/bet/fuddetail?userName={$li.username}&type=1" title="账户明细" class="ico-info"></a>
											{if $li.userType eq '1'}
											<a href="javascript:void(0);" title="发送信息" class="ico-msg" name="{$li.uId}"></a>
											{/if}
											<a href="/transfer/?id={$li.uId}&name={$li.username}" title="充值" class="ico-recharge"></a>
											{if $li.userType eq '1'}
											<a href="/applycenter/displaymodifyprize?id={$li.uId}&name={$li.username}" title="修改返点" class="ico-change"></a>
											{/if}
										</td>
									</tr>
								{foreachelse}
									<div class="alert alert-waring">
                						<i></i>
                						<div class="txt">
                							<h4>暂时没有下级客户</h4>
                						</div>
                					</div>
            					{/foreach}
								</tbody>
							</table>
							
							
							
							
							{if $pages}
							<div class="page-wrapper clearfix">
								<span class="">共{$pages.count}个队员</span>
							<div class="page page-count page-right">
								<span class="page-few">每页显示：</span>
								<select type="input" id="pageSize" name="pageSize" onchange="javascript:smtByPager(1);" class="input w-1">
									<option {if $pageSize eq '10'}selected{/if}>10</option>
									<option {if $pageSize eq '20'}selected{/if}>20</option>
									<option {if $pageSize eq '50'}selected{/if}>50</option>
									<option {if $pageSize eq '100'}selected{/if}>100</option>
								</select>
							</div>
							<div class="page page-right"> 
        						{if $pages.pre && $pages.currpage.index!=1}
            							<a  class="prev" onClick="smtByPager({$pages.prev.index});" href="javascript:void(0);">上一页</a>
            						{/if}
            					{foreach from=$pages.steps item=item}
            						{if $item.index == $pages.currpage.index}
            							<a class="current" href="javascript:void(0);">{$item.text}</a>
            						{else}
            							<a onClick="smtByPager({$item.index});"  href="javascript:void(0);">{$item.text}</a>
            						{/if}
            					{/foreach}
            					{if $pages.next && $pages.currpage.index!=$pages.max.index}
            						<a  class="next" onClick="smtByPager({$pages.next.index});"  href="javascript:void(0);">下一页</a>
            					{/if}
            					<span class="page-few">到第 <input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页</span>
            					<input type="button" value="确 认" class="page-btn" onClick="$('#fm_main').submit();">
    						</div>
							</div>
        					{/if}
							
							
							
							
						</div>
					</div>
					<input type="hidden" id="rqstId" name="rqstId" value="" />
					<input type="hidden" id="uName" name="uName" value="" />
					<input type="hidden" id="moneySort" name="moneySort" value="{$moneySort}" />
					<input type="hidden" id="lastLoginSort" name="lastLoginSort" value="{$lastLoginSort}" />
					<input type="hidden" id="sortType" name="sortType" value="{$sortType}" />
					<input type="hidden" id="type" name="type" value="{$type}" />
					<input type="hidden" id="lvlPath" name="lvlPath" value='{$ajxLvlPath}' />
					</form>
				</div>
			</div>
		</div>
	</div>
	
	<div class="pop pop-search" id="Idivs" style="display:none;  height:300px" >  
		<div class="hd"><i class="close" id="CloseDiv"></i>发送站内信息</div>
			<div>
				<ul class="ui-form form-mail">
				<li>
				<label class="ui" for="title">主题：</label>
				<input id="Txttitle" class="input" type="text" style=" width:420px" maxlength="30" name="title">
                <span class="ui-check"><i class="error"></i></span>
				</li>
				<li>
				<label class="ui" for="text">正文：</label>
				<textarea id="TxtText" class="input" maxlength="300" style="width: 425px; height: 82px;vertical-align: top;" name="content" ></textarea>
                 <span class="ui-check"><i class="error"></i></span>
				</li>
					
				</ul>
				<div class="pop-btn">			
					<input  type="button" class="btn btn-important" value="发 送" tabindex="4" id="subSend"/>					
					<a class="btn  " href="javascript:void(0);" id="colseDiv">取 消<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	
	
	<div class="pop pop-error w-4" style="position:absolute;left:900px;display:none" id="divOperatSuccess">
	<i class="ico-success"></i><h4 class="pop-text">发送成功</h4>
	</div>
	<div class="pop pop-success w-4" style="position:absolute;left:1200px;display:none" id="divOperatFailure">
	<i class="ico-error"></i><h4 class="pop-text">发送失败，请重试</h4>
	</div>
<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
	
<!-- <script  src="{$path_js}/js/jquery-1.9.1.min.js"></script> -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>
{literal}

<script>
function smtByUser(id, name){
	
    $("#username").val('');
    $("#funds").val('');
    $("#funds2").val('');
    $("#lastLogin").val('0');
    $("#rqstId").val(id);
	$("#uName").val(name);
	$("#fm_main").submit();
}
function smtByPager(pageIndex){
	$("#page").val(pageIndex);
	$("#fm_main").submit();
}
function changePageSize(){
	smtByPager(1);
}

(function(){
	
	var inputs = $('#J-money-bound').find('input'),checkFn,box = new LightBox("Idivs"),txtuserid="",inputTitle=null,inputText=null;	
	//数字校验，自动矫正不符合数学规范的数学数字
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}
	};
	inputs.keyup(checkFn);
	inputs.blur(checkFn);
	function codefans(obj){
			$('#'+obj).css("display","none");
	};
	
	jQuery.ajax({
		type:  "get",
		url: '/pt/index/checkuserstatus',
		dataType:'json', 
		contentType: "application/json; charset=utf-8",
		data: '',
		cache: false,
		success:function(data){
			if(data.status==1){
				$('#_showTag').show();
				
			}
		},
		error: function(er){
			console.log(er);
		}
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
	
	//验证发送邮件	
	$('#Txttitle').blur(function(){
		if($('#Txttitle').val().trim()==inputTitle.defConfig.defText){ $('#Txttitle').css('border', '1px solid red');}
		else{ $('#Txttitle').css('border','1px solid #CACACA'); }
	}).focus(function(){
		$(this).parent().find('.ui-check').hide();
		$(this).css('border','1px solid #CACACA');
	});
		
	
	$('#TxtText').blur(function(){
		if($('#TxtText').val().trim()==inputText.defConfig.defText){ $('#TxtText').css('border', '1px solid red');}
		else{ $('#TxtText').css('border','1px solid #CACACA'); }
	}).focus(function(){
		$(this).parent().find('.ui-check').hide();
		$(this).css('border','1px solid #CACACA');
	});
	jQuery('.ico-msg').click(function(){
		txtuserid=$(this).attr("name");
		box.Over = true; box.OverLay.Color = "#336666" ;  box.OverLay.Opacity = 50;  box.Fixed = true;	 box.Center = true; 	box.Show();		
		inputTitle=new phoenix.Input({el:$("#Txttitle"),defText:"主题不能超过30字符"}),inputText=new phoenix.Input({el:$("#TxtText"),defText:"正文不能超过300字符"});
	});	
	
	//发送站内信息	
	jQuery('#subSend').click(function(e){ 
	
		if($('#Txttitle').val().trim()=='' || $('#Txttitle').val().trim()==inputTitle.defConfig.defText)
		{
			 var me=$('#Txttitle'),v=me.val(),par=me.parent();
			 me.css('border', '1px solid red'); 
			 par.find('.ui-check').css('padding-right',"300px");
			 par.find('.ui-check').css('line-height',"0px");
			 par.find('.ui-check').html('<i></i>主题不能为空');
			 par.find('.ui-check').show();
			 e.preventDefault(); 
			 return false;
		}else if(WidthCheck($('#Txttitle').val().trim()) > 30)
		{
			var me=$('#Txttitle'),v=me.val(),par=me.parent();
			 me.css('border', '1px solid red'); 
			 par.find('.ui-check').css("padding-right","270px");
			 par.find('.ui-check').css('line-height',"0px");
			 par.find('.ui-check').html('<i></i>主题不能超过30字符');
			 par.find('.ui-check').show();
			 e.preventDefault(); 
			 return false;
		}
		if($('#TxtText').val().trim()=='' || $('#TxtText').val().trim()==inputText.defConfig.defText)
		{ 
		     var me=$('#TxtText'),v=me.val(),par=me.parent();
		     me.css('border', '1px solid red'); 
			 par.find('.ui-check').css("padding-right","300px");
			 par.find('.ui-check').css('line-height',"0px");
			 par.find('.ui-check').html('<i></i>正文不能为空');
			 par.find('.ui-check').show();
			 e.preventDefault(); 
			 return false;
	    }else if(WidthCheck($('#TxtText').val().trim()) > 300)
		{
			var me=$('#TxtText'),v=me.val(),par=me.parent();
		     me.css('border', '1px solid red'); 
			 par.find('.ui-check').css("padding-right","270px");
			 par.find('.ui-check').css('line-height',"0px");
			 par.find('.ui-check').html('<i></i>正文不能超过300字符');
			 par.find('.ui-check').show();
			 e.preventDefault(); 
			 return false;
		}
		var istrue=true,txtName,ckeckText;
		$('.ico-msg').css("border", "0"); 		
		//发送站内信息
		jQuery.ajax({
		type:'post',
		dataType:'json',					
		cache:false,
		url:'/Service2/sendcustmessage',		
		data:'subId='+txtuserid+'&title='+$('#Txttitle').val()+'&content='+$('#TxtText').val()+'&typeid='+1,
		beforeSend:function(){
			//禁用发送								
			$('#subSend').val("发送中...");						 	
			$("#subSend").css("color", "#CACACA").css("background-color", "#CACACA");　			
			$("#subSend").attr("disabled","disabled");		
	
		},						
		success:function(json){				
			if(json['status']=="0") {	
				$("#divOperatSuccess > h4").html(json['data']);
				fn("divOperatSuccess");
				setTimeout(function(){codefans("divOperatSuccess");},1500);	
				cleanTest();
				e.preventDefault();
				box.Close();				
				txtuserid="";
			}else{		
				 $("#divOperatFailure > h4").html(json['data']);
				 fn("divOperatFailure");
				 setTimeout(function(){codefans("divOperatFailure");},1500);	
			}		
		},
		error:function(xhr, type){
			setTimeout(function(){$('#subSend').css("display","inile");	},2000);	
		},
		complete:function(){
			$('#subSend').val("发送");
			$("#subSend").css("color", "#fff").css("background-color", "#0ab191");　		
			$('#subSend').removeAttr("disabled"); 	
			txtuserid="";
		}
	});
	});	
	
	jQuery('#colseDiv,#CloseDiv').click(function(e){		
		cleanTest();
		e.preventDefault();
		box.Close();				
		txtuserid="";
		});
		
	//操作后提示	 
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";						
		Idivdok.style.zIndex="2000";						
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	

	//清空邮件内容
	var cleanTest=function(){ $('#TxtText').val(''); $('#Txttitle').val(''); $('#Txttitle').css('border','1px solid #CACACA');  $('#TxtText').css('border','1px solid #CACACA'); }
	
})($);
</script>	
{/literal}

	
</body>
</html>