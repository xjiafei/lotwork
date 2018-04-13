<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
		<!-- /////////////头部公共页面/////////////// -->
		
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 总代管理 </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
		<div class="ui-tab-title clearfix">
			<ul>
				<li class="current">总代列表</li>
				<!-- {if "USER_MANAGE_TOPAGENT_CREATETOPAGENT"|in_array:$smarty.session.datas.info.acls} -->
				<li><a href="/admin/proxy/account">总代开户</a></li>
                <!--{else}-->
                <li style="display:none;"></li>
				<!-- {/if} -->
			</ul>
		</div>
		<div >
           <form action="index?search=1" method="get" id="fm_main">
                <ul class="ui-search clearfix">
				<li class="name">
                	 <select class="ui-select" name="searchtype" id="searchtype">
						<option value="username" {if $searchtype == 'username'}selected{/if}>用户名</option>
						<option value="email" {if $searchtype == 'email'}selected{/if}>邮箱</option>
					</select>
                 <input type="text" class="input" id="userName" name="typeN" value="{if $typeN}{$typeN}{else}请输入您的用户名{/if}">
				</li>
				<li>
					<label class="ui-label">注册时间：</label>
                    <select class="ui-select" name="regtime">
                        <option value="">默认排序</option>
						<option value="7" {if $regtime eq '7'}selected="selected"{/if}>7天内</option>
						<option value="15" {if $regtime eq '15'}selected="selected"{/if}>15天内</option>
						<option value="30" {if $regtime eq '30'}selected="selected"{/if}>1个月内</option>
						<option value="90" {if $regtime eq '90'}selected="selected"{/if}>3个月内</option>
						<option value="180" {if $regtime eq '180'}selected="selected"{/if}>半年内</option>
						<option value="365" {if $regtime eq '365'}selected="selected"{/if}>1年内</option>
						<option value="730" {if $regtime eq '730'}selected="selected"{/if}>更久以前</option>
					</select>
				</li>
				<li class="date">
					<label class="ui-label" for="">团队余额：</label>
                    <input type="text" class="input" id="amountfrom"  name="fromBal" value="{$fromBal}"> 至 <input type="text" id="amountend"  name="toBal" class="input" value="{$toBal}">
				</li>
                <li><input type="submit" value="搜索" onClick="($('#userName').val()=='请输入您的用户名'||$('#userName').val()=='请输入邮箱')?$('#userName').val(''):$('#userName').val($('#userName').val());" class="btn btn-important"></li>
			</ul>
          <!--  {if not $proxyuser} -->
			
						<div class="alert alert-waring">
							<i></i>
							<div class="txt">
							<h4>没有符合条件的记录，请更改查询条件！</h4>
							</div>
							</div>
			<!-- {else} -->
			
			<table class="table table-info">
				<thead>
					<tr>
						<th>用户名</th>
						<th>团队</th>
						<th>可用余额</th>
						<th>团队余额</th>
						<th>开户配额</th>
						<th>注册时间/IP</th>
						<th>操作</th>
					</tr>
				</thead>

				<tbody>


                 <!-- {foreach from=$proxyuser item=pu} -->
					<tr class="table-tr">
					
						<td>{$pu.account}<!-- {if "USER_MANAGE_TOPAGENT_LIST_CHANGEVIP"|in_array:$smarty.session.datas.info.acls} -->
							<!-- {if $pu.vipLvl != ''} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$pu.vipLvl}.png"/><!-- {/if} -->
						<!-- {/if} --><!-- {if "USER_MANAGE_TOPAGENT_LIST_FREZE"|in_array:$smarty.session.datas.info.acls} -->
							<!-- {if $pu.isFreeze neq '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/freeze.png"/><!-- {/if} -->
						<!-- {/if} --></td>
						<!-- {if $pu.teamACount+$pu.teamUCount} -->
                        	<td><a href="/admin/user/getchildlist?id={$pu.id}">{$pu.teamACount+$pu.teamUCount}</a>人</td>
                        <!-- {else} -->
                        	<td>{$pu.teamACount+$pu.teamUCount}人</td>
                        <!-- {/if} -->
						<td>{if $pu.availBal !=''}{$pu.availBal}{else}0{/if}元</td>
						<td>{if $pu.teamBal}{$pu.teamBal}{else}0{/if}元</td>
						<td>{if $pu.teamACount+$pu.teamUCount}{$pu.teamACount+$pu.teamUCount}{else}0{/if}/{$pu.agentlimit}</td>
						<td>{$pu.registerDate}<br />[{$pu.registerIp}]</td>
						<td class="table-tool">
						<!-- {if "USER_MANAGE_TOPAGENT_LIST_CHANGEVIP"|in_array:$smarty.session.datas.info.acls} -->
							<span> <a class="ico-vip" name="vip" id="setvip" href="javascript:setvip({$pu.id});"></a></span>
											  
											 
											    <span id="viplevel{$pu.id}" name="{$pu.id}" style="display : none" > 
												    <div style="height:5px"></div>
												    <ul>
													    <li>
														    
												            <span onclick="setlevel({$pu.id},0)" class="ico-vip" name="ico-vip-0pen0" id="setVip0" title="关闭VIP"  sid="{$pu.id}" status="0"><img src="{$path_img}/images/admin/vip/vip0.png"/></span>
													        <span onclick="setlevel({$pu.id},1)" class="ico-vip" name="ico-vip-0pen1" id="setVip1" title="开启VIP1" sid="{$pu.id}" status="1"><img src="{$path_img}/images/admin/vip/vip1.png"/></span>
													        <span onclick="setlevel({$pu.id},2)" class="ico-vip" name="ico-vip-0pen2" id="setVip2" title="开启VIP2" sid="{$pu.id}" status="2"><img src="{$path_img}/images/admin/vip/vip2.png"/></span>
													        <span onclick="setlevel({$pu.id},3)" class="ico-vip" name="ico-vip-0pen3" id="setVip3" title="开启VIP3" sid="{$pu.id}" status="3"><img src="{$path_img}/images/admin/vip/vip3.png"/></span>
												            <span onclick="setlevel({$pu.id},4)" class="ico-vip" name="ico-vip-0pen4" id="setVip4" title="开启VIP4" sid="{$pu.id}" status="4"><img src="{$path_img}/images/admin/vip/vip4.png"/></span>
															
														</li>
													</ul>
												</span>
						<!-- {/if} -->
						<!-- {if "USER_MANAGE_TOPAGENT_LIST_INFO"|in_array:$smarty.session.datas.info.acls} -->
							<a class="ico-detail" title="详情" target="_Blank"  href="/admin/user/userdetail?id={$pu.id}&typepage=2"></a>
						<!-- {/if} -->
						<!-- {if "USER_MANAGE_TOPAGENT_LIST_FREZE"|in_array:$smarty.session.datas.info.acls} -->
							<!-- {if $pu.isFreeze == 1} -->
								<a class="ico-unlock" title="解冻" href="/admin/user/unfreezeuser?id={$pu.id}&typepage=2&viplevel={$pu.vipLvl}&path_img={$path_img}"></a>
							<!-- {elseif $pu.isFreeze == 2} -->
								<a class="ico-disable" title="禁止操作" href="#"></a> 
							<!-- {else} -->
								<a class="ico-lock" title="冻结" href="/admin/user/freezeuser?id={$pu.id}&typepage=2&viplevel={$pu.vipLvl}&path_img={$path_img}"></a>
							<!-- {/if} -->
						<!-- {/if} -->
						<!-- {if "USER_MANAGE_TOPAGENT_LIST_ACCOUNTDETAIL"|in_array:$smarty.session.datas.info.acls} -->
							<a class="ico-info" title="账户明细" target="_Blank" href="/admin/Reporting/index?parma=sv55&UserName={$pu.account}"></a>
						<!-- {/if} -->
						<!-- {if "USER_MANAGE_TOPAGENT_LIST_CATHECTICHISTORY"|in_array:$smarty.session.datas.info.acls} -->
							<a class="ico-note" title="投注记录" target="_Blank" href="/gameoa/queryOrderList?paramCode={$pu.account}"></a>
						<!-- {/if} -->
						<!-- {if "USER_MANAGE_TOPAGENT_LIST_CREATEUSERCNT"|in_array:$smarty.session.datas.info.acls} -->
							<a class="ico-quota" title="开户配额" href="javascript:void(0);" pro="{$pu.id}" prs="{$pu.account}"></a>
						<!-- {/if} -->
						</td>
					</tr>
                  <!-- {/foreach} -->
                                     

	
                  <!--  {foreach from=$proxychild item=pc} -->
					<tr class="table-tr">
						<td>{$pc.account}</td>
                        <td>{$pc.teamACount+$pc.teamUCount}人</td>
						<td>{if $pc.availBal !=''}{$pc.availBal}{else}0{/if}元</td>
						<td>{$pc.teamBal}元</td>
						<td>{$pc.add_num}/{$pu.agentlimit}</td>
						<td>{$pc.registerDate}<br />[{$pc.registerIp}]</td>
						<td class="table-tool">
							<a class="ico-detail" title="详情" href="/admin/user/userdetail?id={$pu.id}&typepage=2"></a>
							<!-- {if $pu.isFreeze == 1} -->
								<a class="ico-unlock" title="解冻" href="/admin/user/unfreezeuser?id={$pu.id}&typepage=2"></a>
							<!-- {elseif $pu.isFreeze == 2} -->
								<a class="ico-disable" title="禁止操作" href="#"></a> 
							<!-- {else} -->
								<a class="ico-lock" title="冻结" href="/admin/user/freezeuser?id={$pu.id}&typepage=2"></a>
							<!-- {/if} -->
							<a class="ico-info" title="账户明细" href="/admin/Reporting/index?parma=sv55&UserName={$pu.account}"></a>
							<a class="ico-note" title="投注记录" href="/gameoa/queryOrderList?paramCode={$pu.account}"></a>
							<a class="ico-quota" title="开户配额" href="javascript:void(0);"></a>
						</td>
					</tr>
                 <!--  {/foreach} -->
                     				
			</table>
			<!-- {/if} -->
			{if $pages}
                            <div class="page-wrapper">
                                    <span class="page-text">共{$pages.count}条记录</span>
                                    <div class="page page-right">
                                        {if $pages.pre && $pages.currpage.index!=1}
                                            <a href="?page={$pages.pre.index}&regtime={$regtime}&fromBal={$fromBal}&toBal={$toBal}&typeN={$typeN}" class="prev">上一页</a>
                                        {/if}

                                        {foreach from=$pages.steps item=item}
                                            {if $item.index == $pages.currpage.index}
                                                <a href="#" class="current">{$item.text}</a>
                                            {else}
                                                <a href="?page={$item.index}&regtime={$regtime}&fromBal={$fromBal}&toBal={$toBal}&typeN={$typeN}">{$item.text}</a>
                                            {/if}
                                        {/foreach}
                                        {if $pages.next && $pages.currpage.index!=$pages.max.index}
                                            <a class="next" href="?page={$pages.next.index}&regtime={$regtime}&fromBal={$fromBal}&toBal={$toBal}&typeN={$typeN}">下一页</a>
                                        {/if}
                                            <span class="page-few">
                                                                    到第<input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页
								<input type="button" value="确 认" class="page-btn" onClick="($('#userName').val()=='请输入您的用户名'||$('#userName').val()=='请输入邮箱')?$('#userName').val(''):$('#userName').val($('#userName').val());$('#fm_main').submit();">
                                    </div>
                            </div>
                        {/if}
                        </form>
		</div>
	</div>

	<div class="pop w-8" style="position:absolute;left:700px; display:none" id="divS">
            <form action="createaccount" method="post">
                <div class="hd"><i class="close" id="divClose"></i>修改开户配额</div>
		<div class="bd">
			<ul class="ui-form">
				<li>当前开户配额为：<span id="openAccount"></span>，已开<span id="openedAccount"></span>，剩余配额<span id="surplusAccount"></span>；</li>
				<li>调整剩余配额数：<input type="text" value="" class="input" name="availablequota" id="txtMoeny"/><div class="ui-check" id="ui-divs"><i class="error"></i>不能为空或小数</div></li>
				
			</ul>
			<div class="pop-btn">
				<a class="btn btn-important " href="javascript:void(0);" id="btn-Updat">修 改<b class="btn-inner"></b></a>
				<a class="btn" href="javascript:void(0);" id="btnNo">取 消<b class="btn-inner"></b></a>
			</div>
		</div>
            </form>
	</div>



					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
<script  src="{$path_js}/js/jquery.json-2.4.min.js"></script>
{literal}
<script > 
(function($){	
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser','MenuUsermanage');
		
		var box = new LightBox("divS");
		//数字校验，自动矫正不符合数学规范的数学
		var inputs = $('.date').find('[type="text"]'),checkFn,updatecount=$('#txtMoeny'),checkUp;				
		checkFn = function(){
			var me = this,v = me.value,index;
			//先把非数字的都替换掉，除了数字和.
		  	me.value = me.value.replace(/[^\d.]/g,"");
		  	//必须保证第一个为数字而不是.
		  	me.value = me.value.replace(/^\./g,"");
		  	//保证只有出现一个.而没有多个.
		  	me.value = me.value.replace(/\.{2,}/g,".");
		  	//保证.只出现一次，而不能出现两次以上
		  	me.value = me.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
							
		};
		//不能为小数
		checkUp = function(){
			var me = this,v = me.value,index;
			me.value = v = v.replace(/^\.$/g, '');
			
			index = v.indexOf('.');
			
			me.value = v = v.replace(/[^\d|^\.]/g, '');	
			if(index >= 1){
				me.value = v = v.substring(0,me.value.length-1);			
			}
					
		};
		
		inputs.keyup(checkFn);	inputs.blur(checkFn);
		updatecount.keyup(checkUp);	updatecount.blur(checkUp);
	
	 //弹窗居中显示层
	 function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
    } 		
	$('#searchtype').change(function(){		
		$('#userName').val('');
		var p1=$(this).children('option:selected').val();
		
		if(p1=='username'){$('#userName').val('请输入您的用户名');}
		else{	$('#userName').val('请输入邮箱');}
		
	
	});	
	$('#userName').focus(function(){		
		if($('#userName')[0].value=='请输入您的用户名'){ $("#userName")[0].value='';}	
		if($('#userName')[0].value=='请输入邮箱'){ $("#userName")[0].value='';}	
		}).blur(function(){
		var v = $.trim(this.value);
		if(v == ''){
			if($('#searchtype').children('option:selected').val()=='username'){ $("#userName")[0].value='请输入您的用户名';}
			else{$("#userName")[0].value='请输入邮箱';}
		}
	
	});	
	var message = phoenix.Message.getInstance();
	$('[name="ico-vip-close"]').bind('click', function(){
		var name = $(this).parent().parent().find('td:first').text();
		var id=$(this).attr("sid");
		var status=$(this).attr("status");
		var surl="/admin/user/setvip?id="+id+"&status="+status;
		message.show({
			content : ['<div class="bd text-center">',
									'<div class="pop-title">',
										'<i class="ico-waring"></i>',
										'<h4 class="pop-text">你确定要取消 '+name+' VIP身份吗？</h4>',
									'</div>',
								'</div>'].join(''),
			confirmIsShow: true,
			mask: true,
			confirmFun : function(){
				$.ajax({
					url: surl,
					type:'GET',
					success: function(data){
						window.location.reload();
					}
				})
			},
			cancelIsShow: true,
			cancelFun: function(){
				this.hide();
			}
		})
	})
	
	$('[name="ico-vip-open"]').bind('click', function(){
		var name = $(this).parent().parent().find('td:first').text();
		var id=$(this).attr("sid");
		var status=$(this).attr("status");
		var surl="/admin/user/setvip?id="+id+"&status="+status;
		message.show({
			content : ['<div class="bd text-center">',
									'<div class="pop-title">',
										'<i class="ico-waring"></i>',
										'<h4 class="pop-text">你确定要将 '+name+' 设置为VIP吗？</h4>',
									'</div>',
								'</div>'].join(''),
			confirmIsShow: true,
			mask: true,
			confirmFun : function(){
				$.ajax({
					url: surl,
					type:'GET',
					success: function(data){
						window.location.reload();
					}
				})
			},
			cancelIsShow: true,
			cancelFun: function(){
				this.hide();
			}
		})
	})
	//点击开户配额	
	$(".table-tr .ico-quota").click(function(e) {		
	
		box.Over = true; box.OverLay.Color = "#fff" ;  box.OverLay.Opacity = 50;  box.Fixed = true;	 box.Center = true; 	box.Show();	
		CleanText();
		var inputMoney=$('.ui-form').find('[type="text"]');
		inputMoney.keyup(checkFn);	inputMoney.blur(checkFn);
		//获取当前选中的用户id 
		txtName=$(this).attr("pro");	
        txtPrs=$(this).attr("prs");	
		if(txtName==''){e.preventDefault(); return false;}
		$.ajax({
			type:'get',
			dataType:'json',
			cache:false,
			url:'/admin/proxy/createaccount',		
			data:'userid='+txtName+'&account='+encodeURI(txtPrs),
			success:function(data){
			
                            if(data !='') {

                                //有返回数据将开户配额加载						
                                var result = eval(data);
                               // debugger
                                var teamAcount=result.teamACount==null?"0":parseInt(result.teamACount);
								var teamUcount=result.teamUCount==null?"0":parseInt(result.teamUCount);
                                //var teamUCount=result.body.result.teamUCount==null?"0":parseInt(result.body.result.teamUCount);
                                var agentlimit=result.agentlimit==null?"0":parseInt(result.agentlimit);
                                var sunacount=parseInt(agentlimit); //parseInt(teamAcount)+parseInt(teamUCount)+
                                var openedcount=parseInt(teamAcount)+parseInt(teamUcount); //+parseInt(teamUCount)
                                var scount=(parseInt(sunacount) -parseInt(openedcount))<=0?"0":parseInt(sunacount) -parseInt(openedcount);
                                $('#openAccount').html(sunacount);
                                $('#openedAccount').html(openedcount);
                                $('#surplusAccount').html(scount);

                             } else {
                                     //有误将关闭窗口
                                     alert("数据有误");
                                     $('#divS').css("display","none");
                             }
			}
		});
	});
	
	//修改提交
	$('#btn-Updat').click(function(e){		
		if($('#txtMoeny').val()=='' ){
			e.preventDefault();
			$('#txtMoeny').css("border", "solid 1px red"); 
			$('#ui-divs').css('display', 'inline');		
			return false;
		}
		else
		{
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/admin/proxy/createaccount',					
				data:'userid='+txtName+'&account='+txtPrs+'&availablequota='+$('#txtMoeny').val(),						
				success:function(data){	
					var result = eval(data);
					if(data['isSuccess']=="1")
					{					
						//有返回数据将开户配额加载						
						$('#txtMoeny').val('');
						$('#txtMoeny').css("border", "solid 1px #CECECE"); 
						$('#ui-divs').css('display', 'none');		
						alert("调整剩余配额数成功");
						$('#divS').css("display","none");
						window.location = "/admin/proxy/index";
					}
					else
					{
						//有误将关闭窗口,异常待后台传回
						alert("调整剩余配额失败");
						$('#divS').css("display","none");	
						box.Close();					
					}
				}
			});
		}
		
	});	
	
	//关闭弹出层
	$('#divClose,#btnNo').click(function (){			
		box.Close();
	});
	
	//清空当前文本值
	var CleanText=function(){
	$('#txtMoeny').val('');
	$('#txtMoeny').css("border", "solid 1px #CECECE"); 	
	$('#ui-divs').css('display', 'none');			
	$('#openAccount').html('');
	$('#openedAccount').html('');
	$('#surplusAccount').html('');
	}

})(jQuery);

	function setvip(uid)
	{
	
	  
	    $('#viplevel'+uid).toggle();
		
	}
	
	
	function setlevel(uid,level){
	
	   var message = phoenix.Message.getInstance();	
		
		var surl="/admin/user/setvip?id="+uid+"&status="+level;
		
		if(level=="0")
		{
		    message.show({
				    content : ['<div class="bd text-center">',
										'<div class="pop-title">',
											'<i class="ico-waring"></i>',
											
											    '<h4 class="pop-text">你确定要取消VIP身份吗？</h4>',
											
										'</div>',
									'</div>'].join(''),
				confirmIsShow: true,
				mask: true,
				confirmFun : function(){
					$.ajax({
						url: surl,
						type:'GET',
						success: function(data){
							window.location.reload();
						}
					})
				},
				cancelIsShow: true,
				cancelFun: function(){
					this.hide();
				}
			});
		}
		
		else
		{
		     message.show({
				    content : ['<div class="bd text-center">',
										'<div class="pop-title">',
											'<i class="ico-waring"></i>',
											
											   '<h4 class="pop-text">你确定要设置为VIP '+level+' 吗？</h4>',
											
										'</div>',
									'</div>'].join(''),
				confirmIsShow: true,
				mask: true,
				confirmFun : function(){
					$.ajax({
						url: surl,
						type:'GET',
						success: function(data){
							window.location.reload();
						}
					})
				},
				cancelIsShow: true,
				cancelFun: function(){
					this.hide();
				}
			});
		}
			
			$('#viplevel'+uid).hide();
			
	}

</script>
{/literal}
</body>
</html>