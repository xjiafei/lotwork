<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content" >
		<!-- //////////////头部公共页面////////////// -->
			{include file='/admin/left.tpl'}
	
		<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main" style='height:100%'>		
	
			<div class="col-crumbs" ><div class="crumbs"><strong>当前位置：</strong><a href="/admin/user/">用户中心</a> &gt; 客户列表</div></div>
			
			<div class="col-main" style='height:100%'>
			<div class="col-content" style='height:100%' >
				<div class="col-tree">
					<div class="tree" style='overflow:scroll;height:100%'>
						<div class="dtree" >
							<div class="dTreeNode" >							
								<span>客户管理</span>
							</div>
						<div class="zTreeDemoBackground left">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						
						</div>
					</div>
					
				</div>
				<div class="col-drag" >
                	<div class="drag" ><a class="tri-left"></a></div>	
				</div>

				<div class="col-main">
					<div class="main">
						<form action="/admin/user/list?search=1" method="POST" id="J-form"  name="J-form" >
						<ul class="ui-search">
							<li class="name">
								<select class="ui-select"  name="type" id="searchtype">
									<option value="username" {if $type eq 'username'}selected{/if}>用户名</option>
									<option value="email" {if $type eq 'email'}selected{/if}>邮箱</option>
								</select>
								<input type="text" class="input w-3" name="typeN" id="userName" {if $typeN neq ''} value="{$typeN}" {else} value="请输入您的用户名" {/if}>
							</li>
							<li>
								<label class="ui-label">用户组：</label>
                                    <select class="ui-select" name="usergroup">
										<option value="" {if $usergroup eq ''}selected{/if}>全部客户</option>
										<option value=-11 {if $usergroup eq '-11'}selected{/if}>VIP-1</option>
										<option value=-12 {if $usergroup eq '-12'}selected{/if}>VIP-2</option>
										<option value=-13 {if $usergroup eq '-13'}selected{/if}>VIP-3</option>
										<option value=-14 {if $usergroup eq '-14'}selected{/if}>VIP-4</option>
										<option value=-99 {if $usergroup eq '-99'}selected{/if}>VIP</option>
										<!-- {foreach from=$aUserGroup key=key item=data} -->
										<option value="{$key}" {if $usergroup eq $key and $usergroup neq null}selected{/if}>{$data}</option>
										<!-- {/foreach} -->
									</select>
							</li>
							<li>
								<label class="ui-label">注册时间：</label>
                                    <select class="ui-select" name="regtime">
									<option value="0" {if $regtime eq '0'}selected{/if}>默认排序</option>
									<option value="7" {if $regtime eq '7'}selected{/if}>7天内</option>
									<option value="15" {if $regtime eq '15'}selected{/if}>15天内</option>
									<option value="30" {if $regtime eq '30'}selected{/if}>1个月内</option>
									<option value="90" {if $regtime eq '90'}selected{/if}>3个月内</option>
									<option value="180" {if $regtime eq '180'}selected{/if}>半年内</option>
									<option value="365" {if $regtime eq '365'}selected{/if}>1年内</option>
									<option value="730" {if $regtime eq '730'}selected{/if}>更久以前</option>
								</select>
							</li>
							<li class="date">
								<label class="ui-label" for="">余额：</label>
								<input type="text" class="input" name="bMoney"  value="{$bMoney}"> 至 <input type="text" name="eMoney" class="input" value="{$eMoney}">
							</li>
							<li>
								<label class="ui-label">来源：</label>
								<select name="device" class="ui-select">
									<option value="" {if $device eq ''}selected{/if}>全部</option>
									<option value="web" {if $device eq 'web'}selected{/if}>WEB</option>
									<option value="ios" {if $device eq 'ios'}selected{/if}>IOS</option>
									<option value="android" {if $device eq 'android'}selected{/if}>ANDROID</option>
								</select>
							</li>
							
							<!-- {if "USER_MANAGE_LIST_SEARCH"|in_array:$smarty.session.datas.info.acls} -->
							<li>
								<a href="javascript:void(0);" class="btn btn-important" id="J-button-submit">搜索<b class="btn-inner"></b></a>
							</li>
							<!-- {/if} -->
							<!-- {if "USER_MANAGE_LIST_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
							<li>
							 	<a class="btn btn-small " id="J-Download-Report" href="javascript:void(0);" style="float:left;">下载报表<b class="btn-inner"></b></a>
								<!-- <input id="checkwithchild" type="checkbox" value="Y">包含下级<br> -->
							</li>
							<!--<li>
							 	<a class="btn btn-small " id="J-Download-CReport" href="javascript:void(0);" style="float:left;">下载报表(下载下级)<b class="btn-inner"></b></a>
							</li>-->
							
							<!-- {/if} -->
						</ul>
						<div style="margin-top:7px;margin-left:10px;">
						<!-- {if $userChain} -->
						用户层级
							<!-- {foreach from=$userChain item=data} -->&gt;
								<!-- {if $topName eq $data} -->
									{$data}
								<!-- {else} -->
									<a style="TEXT-DECORATION:none;" href="/admin/user/getchildlist?topname={$data}">{$data}</a>
								<!-- {/if} -->
							<!-- {/foreach} -->
						<!-- {/if} -->
						</div>
						<!-- {if $proxyuser} -->
							<table class="table table-info">
							<thead>
								<tr>
									<th>用户名</th>
									<th>团队</th>
									<th>用户组</th>
									<th>可用余额</th>
									<th>注册时间/IP</th>
									<th>来源</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
                            <!-- {foreach from=$proxyuser item=pu} -->
								<tr>
                                    <td>{if $pu.id neq $id}<a href="/admin/user/getchildlist?id={$pu.id}">{$pu.account}{else}{$pu.account}{/if}<!-- {if $pu.vipLvl != '' && $pu.vipLvl != '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/vip/vip{$pu.vipLvl}.png"/><!-- {/if} --><!-- {if $pu.freezeMethod neq '0'} -->&nbsp;<img src="{$path_img}/images/ucenter/safe/freeze.png"/><!-- {/if} --></a></td>
                                    <td>{if ($pu.teamACount+$pu.teamUCount)>0 && $pu.id neq $id}<a href="/admin/user/getchildlist?id={$pu.id}">{$pu.teamACount+$pu.teamUCount}</a>{else}{$pu.teamACount+$pu.teamUCount}{/if}人</td>
									<td>{$pu.userLvl}</td>
									<td>{if $pu.availBal !=''}{$pu.availBal}{else}0{/if}元</td>
									<td>{$pu.registerDate}<br />[{$pu.registerIp}]</td>
									<td>{$pu.device}</td>
									<td class="table-tool">
									<!-- {if "USER_MANAGE_LIST_CHANGEVIP"|in_array:$smarty.session.datas.info.acls} -->
									     	<span> <a class="ico-vip" name="vip" id="setvip" href="javascript:setvip({$pu.id});"></a></span>
											  
											 
											    <span id="viplevel{$pu.id}" name="viplevel" style="display : none"> 
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
									
									<!-- {if "USER_MANAGE_LIST_SHOWPRIZEMODE"|in_array:$smarty.session.datas.info.acls} -->
											<!-- {assign var=userLvl value={$pu.userLvl|urlencode}} -->
											<a class="ico-poing" title="奖金模式" sid="{$pu.id}" target="_Blank" href="/admin/user/userprizemode?id={$pu.id}&userLvl={$userLvl}&account={$pu.account}&viplevel={$pu.vipLvl}&path_img={$path_img}"></a>
									<!-- {/if} -->
									
									
									
									<!-- {if "USER_MANAGE_LIST_INFO"|in_array:$smarty.session.datas.info.acls} -->
										<a class="ico-detail" title="详情" target="_Blank" href="/admin/user/userdetail?id={$pu.id}&typepage=1"></a>
									<!-- {/if} -->
									<!-- {if "USER_MANAGE_LIST_FREEZE"|in_array:$smarty.session.datas.info.acls} -->
										<!-- {if $pu.freezeMethod == 1 or $pu.freezeMethod ==2 or $pu.freezeMethod ==3  or $pu.freezeMethod ==4} -->
											<a class="ico-unlock" title="解冻" href="/admin/user/unfreezeuser?id={$pu.id}&typepage=1&viplevel={$pu.vipLvl}&path_img={$path_img}"></a>
										<!-- {elseif $pu.freezeMethod ==5} -->
											<a class="ico-disable" title="禁止操作" href="#"></a> 
										<!-- {else} -->
											<a class="ico-lock" title="冻结" href="/admin/user/freezeuser?id={$pu.id}&typepage=1&viplevel={$pu.vipLvl}&path_img={$path_img}"></a>
										<!-- {/if} -->
									<!-- {/if} -->
									<!-- {if "USER_MANAGE_LIST_ACCOUNTDETAIL"|in_array:$smarty.session.datas.info.acls} -->
										<a class="ico-info" title="账户明细" target="_Blank" href="/admin/Reporting/index?parma=sv55&UserName={$pu.account}"></a>
									<!-- {/if} -->
									<!-- {if "USER_MANAGE_LIST_CATHECTICHISTORY"|in_array:$smarty.session.datas.info.acls} -->
										<a class="ico-note" title="投注记录" target="_Blank" href="/gameoa/queryOrderList?paramCode={$pu.account}"></a>
									<!-- {/if} -->
									</td>
								</tr>
                             <!-- {/foreach} -->

							
						</table>
                        <!-- {if $pages} -->
						<div class="page-wrapper">
							<span class="page-text">共{$pages.count}条记录</span>
							<div class="page page-right">
                               <!-- {if $pages.pre && $pages.currpage.index!=1} -->
								<a href="?cid={$pagevar.cid}&id={$id}&page={$pages.pre.index}&order={$pagevar.order}&by={$pagevar.by}&type={$type}&typeN={$typeN}&usergroup={$usergroup}&regtime={$regtime}&bMoney={$bMoney}&eMoney={$eMoney}" class="prev">上一页</a>
                               <!-- {/if} -->
                                                            
                               <!-- {foreach from=$pages.steps item=item} -->
                                   <!-- {if $item.index == $pages.currpage.index} -->
                                       <a href="#" class="current">{$item.text}</a>
                                   <!-- {else} -->
                                   
                                       <a href="?cid={$pagevar.cid}&id={$id}&page={$item.index}&order={$pagevar.order}&by={$pagevar.by}&type={$type}&typeN={$typeN}&usergroup={$usergroup}&regtime={$regtime}&bMoney={$bMoney}&eMoney={$eMoney}">{$item.text}</a>
                                   <!-- {/if} -->
                               <!-- {/foreach} -->
                               <!-- {if $pages.next && $pages.currpage.index!=$pages.max.index} -->
                                   <a class="next" href="?cid={$pagevar.cid}&id={$id}&page={$pages.next.index}&order={$pagevar.order}&by={$pagevar.by}&type={$type}&typeN={$typeN}&usergroup={$usergroup}&regtime={$regtime}&bMoney={$bMoney}&eMoney={$eMoney}">下一页</a>
                               <!-- {/if} -->
								<span class="page-few">
                                                                    到第<input type="text" name="page" id="page" value="{$pages.currpage.text}" class="input"> /{$pages.max.text}页
								<input type="button" value="确 认" class="page-btn" onClick="($('#userName').val()=='请输入您的用户名'||$('#userName').val()=='请输入邮箱')?$('#userName').val(''):$('#userName').val($('#userName').val()); $('#J-form').attr('action','/admin/user/list?search=1');$('#J-form').submit();">
							</div>
						</div>
                        <!-- {/if} -->
					
						<!-- {else} -->
						
							<div class="alert alert-waring">
							<i></i>
							<div class="txt">
							<h4>没有符合条件的记录，请更改查询条件！</h4>
							</div>
							</div>
						<!-- {/if} -->

											</form>
					</div>
				</div>
			
			</div>	
		</div>
	</div>
	
<link rel="stylesheet" href="{$path_img}/css/zTreeStyle/zTreeStyle.css" >
{include file='/admin/script-base.tpl'}
<script  src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script  src="{$path_js}/js/jquery.json-2.4.min.js"></script>
<script  src="{$path_js}/js/jquery.ztree.core-3.5.js"></script>	
<script  src="{$path_js}/js/jquery.cookie.js"></script>
{literal}
	<script>
	(function($){
		//一、二级菜单选中样式加载	
		selectMenu('MenuUser','MenuUserlist');
		
		var message = phoenix.Message.getInstance();
		

		//--------------------------------------------------------------------树形结构加载开始-----------------------------------------------------------------------
		//Zree异步加载菜单数据
		var setting = {
			async: {
				enable: true,
				url:"/admin/user/querysubuser",
				//url:"/User/list",
				autoParam:["id=userid"],
				otherParam:{"name":""},
				dataFilter: filter

			},//不加载图标
			view: {
				showIcon: showIconForTree
			},
			callback: {			
				onClick: onClick 
				//onExpand:zTreeOnExpand,
				//onAsyncSuccess:succ,
			}
		};		
		
		function succ(event, treeId, treeNode, msg){
			var treeLength = eval(msg).length;
			for(i=0 ; i<treeLength ; i++){
				if(eval(msg)[i].isParent){
					parentNodes.push(eval(msg)[i]);
				}
			}
		}
		function zTreeOnExpand(event, treeId, treeNode){
			var str = treeNode.name;
			var queueCookies = $.cookie('queue');
			poolNodes = queueCookies.concat('\\',str.split("(")[0]);
			$.cookie('queue',poolNodes,{expires: 7});
		}
		function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
		}
		
		
		//点击跳转传相应参数
		function onClick(event, treeId, treeNode, clickFlag){
		
			if(clickFlag===1)
			{
				window.location.href='/admin/user/getchildlist?id='+treeNode.id +'';
				//window.location.href='/admin/user/getchildlist?id='+treeNode.id +'&poolUrls='+$.cookie('queue');			
			}			
		}	
		
		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				if(!childNodes[i]){break;}
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		//$.fn.zTree.init($("#treeDemo"), setting);			
	
		//-------------------------------------------------------------------------树形结构加载结束-------------------------------------------------------
		
		//判断树形菜单是或是展开,1:展开，0：收起
		var zreename=$.cookie("zreename");
		if(zreename=="0"){ 	
			$('.col-tree').css("display","none"); 		
			$('.drag a').removeClass('tri-left').addClass('tri-right');
		}
		else{ 	
			$('.col-tree').css("display","block");
			$('.drag a').removeClass('tri-right').addClass('tri-left');
			$.fn.zTree.init($("#treeDemo"), setting);	
		}
		
		//用户树收起的状态Subm提交状态将保存(cookie),收起时树形将不加载数据
		$('.col-drag').click(function(){	
			if($(".col-tree").css('display') != 'none'){
				$('.col-tree').css("display","none");			
				$('.drag a').removeClass('tri-left').addClass('tri-right');		
				$.cookie("zreename", "0", { expires: 7 }); 
			}		
			else{
				$('.col-tree').css("display","block");						
				$('.drag a').removeClass('tri-right').addClass('tri-left');	
				$.cookie("zreename", "1", { expires: 7 });
				$.fn.zTree.init($("#treeDemo"), setting);	
			}					
		});
		
		
		
		//数字校验，自动矫正不符合数学规范的数学
		var inputs = $('.date').find('[type="text"]'),checkFn;				
		checkFn = function(){
			var me = this;
			//先把非数字的都替换掉，除了数字和.
		  	me.value = me.value.replace(/[^\d.]/g,"");
		  	//必须保证第一个为数字而不是.
		  	me.value = me.value.replace(/^\./g,"");
		  	//保证只有出现一个.而没有多个.
		  	me.value = me.value.replace(/\.{2,}/g,".");
		  	//保证.只出现一次，而不能出现两次以上
		  	me.value = me.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
		};
		
		inputs.keyup(checkFn);
		inputs.blur(checkFn);
		

		
	$('#searchtype').change(function(){
		$('#userName').val('');
		var p1=$(this).children('option:selected').val();//这就是selected的值
		
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
		
		

		//表单提交校验
		$('#J-button-submit').click(function(){
			$('#J-form').attr('action','/admin/user/list?search=1');  
			/*if($("#userName")[0].value=='请输入您的用户名'){
			 alert("请输入用户名");
			 $("#userName").focus(); 
			 return false;
			 }*/
			$('#J-form').removeAttr('target');
			$('#J-form').submit();
		});	
		
		$('#J-Download-Report').click(function(){
		/*	if($("#checkwithchild").is(':checked')){
			 $('#J-form').attr('action','/admin/user/downchildlist');
			 }
			else{
				$('#J-form').attr('action','/admin/user/downlist');  原本的action  2015/10/20
			}*/			 
			 $('#J-form').attr('action','/admin/user/downchildlist');
			 if($("#userName")[0].value=='请输入您的用户名'){
			 alert("请输入用户名");
			 $("#userName").focus(); 
			 return false;
			 }	
			 $('#J-form').attr('target','_blank');
	         $('#J-form').submit();
		});
		
		/*$('#J-Download-CReport').click(function(){
			 $('#J-form').attr('action','/admin/user/downchildlist');
			 if($("#userName")[0].value=='请输入您的用户名'){
			 alert("请输入用户名");
			 $("#userName").focus(); 
			 return false;
			 }	
			 $('#J-form').attr('target','_blank');
	         $('#J-form').submit();
		});*/
			 
		
		$('#J-form').submit(function(e){
			if($("#userName")[0].value=='请输入您的用户名'){$("#userName")[0].value=''; }	//e.preventDefault(); return false;
			if($("#userName")[0].value=='请输入邮箱'){$("#userName")[0].value='';}	
				
			return true;
		});
		
	
		/*$('#setVip').click(function(){
			$.ajax({
				url:'/admin/user/setvip?id=250&status=1',
				dataType:'json',
				cache:false,
				method:'post',
				data:'mownecumId='+$.trim($('#MOWCode').val())+'&name='+$.trim(BankName)+'&code='+$.trim($('#BankCode').val())+'&url='+$.trim($('#OnlineBankAdd').val())+"&logo="+bankLogo,
				beforeSend:function(){				
				},
				success:function(data){						
					if(data){
						minWindow.hide();
						fnshowdiv('DivSuccessful');
						setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
						location.reload();	
					}else{
						minWindow.hide();
						fnshowdiv('DivFailed');
						setTimeout(function (){$("#DivFailed").css("display","none");},1500);	
					}
				},
				complete:function(){				
				}
			});	
		});*/
		
		/*$('.table tr td span').hover(function(){
	        alert('aaa');    
	    })*/


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





