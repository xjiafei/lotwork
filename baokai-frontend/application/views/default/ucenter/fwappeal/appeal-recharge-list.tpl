<!DOCTYPE HTML>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <title>催到账</title>
        <link rel="stylesheet" href="{$path_img}/images/common/base.css" />
		<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
        <link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
        {include file='/default/script-base.tpl'}
        <script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
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
                        <dt>申诉小提示：</dt>
                        <dd>1、完成充值后耐心等待{$waitTime|default:5}分钟。</dd>
                        <dd>2、请选择对应充值未到账的订单进行申诉，这样有助于您申诉成功。</dd>
                        <dd>3、如您未找到您的充值订单，请点击右侧无充值订单号进行自助服务申诉。</dd>
                        <dd>4、跨行转账到账的时间取决于官方银行转账的工作效率。</dd>
                        <dd>5、如您有任何不明白的问题，请咨询在线客服。</dd>
                    </dl>
                    <form action="/fundappeal/appealrechargelist" id="F-query" method="post">
                        <div class="content">
                            <div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
                                <ul>
                                    <li class="current">充值申诉</li>
                                    <li><a href="/fundappeal/appealwithdrawlist">提现申诉</a></li>
                                    <li><a href="/fundappeal/appealstatuslist">申诉进度查询</a></li>
                                </ul>
                                <label class="appeal-link"> 无充值订单号？<a href="/fundappeal/rechargeappeal">点这里</a></label>
                            </div>
                            <!-- {if $pages.count gt 0} -->
                            <table class="table table-info">
                                <thead>
                                    <tr>
                                        <th>充值订单号</th>
                                        <th>充值方式</th>
                                        <th>附言</th>
                                        <th>充值金额</th>
                                        <th>充值发起时间</th>
                                        <th>状态</th>
                                        <th>申诉</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <!-- {foreach from=$content item=data} -->
                                    <tr>
                                        <td>
                                            {$data.sn}
                                            <input type="hidden" id="rechargeSn" value="{$data.sn}"/>
                                            <input type="hidden" id="rechargeAccount" value="{$data.rechargeAccount}"/>
                                        </td>
                                        <td>
                                            {$data.rechargeTypeStr}
                                            {if {$data.rechargeBankId} != 51 && {$data.rechargeBankId} != 30}[{$data.rechargeBank}] {/if}
                                            <input type="hidden" id="rechargeType" value="{$data.rechargeType}"/>
                                            <input type="hidden" id="rechargeBank" value="{$data.rechargeBank}"/>
                                            <input type="hidden" id="rechargeBankId" value="{$data.rechargeBankId}"/>
                                            <input type="hidden" id="rechargeCard" value="{$data.rechargeCard}"/>
                                        </td>
                                        <td>{$data.rechargeMemo}<input type="hidden" id="rechargeMemo" value="{$data.rechargeMemo}"/></td>
                                        <td>{$data.rechargeAmt}<input type="hidden" id="rechargeAmt" value="{$data.rechargeAmt}"/></td>
                                        <td>{$data.rechargeTime}<input type="hidden" id="rechargeTime" value="{$data.rechargeTime}"/></td>
                                        <td>{$data.statusStr}<input type="hidden" id="status" value="{$data.status}"/></td>
                                        <!-- {if $data.hasBeenAppeal} -->
                                        <td nowrap><a class="btn btn-small btn-disable">处理中</a><input type="hidden" id="appealStatus" value="{$data.appealStatus}"/></td>
                                       <!--depositeMode 网银充值  1   快捷充值   2 else  财付通 3, 银联充值 5, 支付寶 6-->
                                        <!-- {else if $data.isWaiting || 
                                        (($data.depositeMode ==3 ||$data.depositeMode ==5 ||$data.depositeMode ==6) && $data.canRechargeAppeal==0)} -->
                                        <td nowrap><a class="btn btn-small btn-disable">申诉</a><input type="hidden" id="appealStatus" value="{$data.appealStatus}"/></td>	
                                        <!-- {else} -->
                                        <td nowrap><a class="btn btn-small btnSnAppeal">申诉</a><input type="hidden" id="appealStatus" value="{$data.appealStatus}"/></td>		
                                        <!-- {/if} -->
                                    </tr>							
                                    <!-- {/foreach} -->							
                                </tbody>
                            </table>		
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
                            <!-- {else} -->	
                            <div class="alert alert-waring">
                                <i></i>
                                <div class="txt">
                                    <h4>没有符合条件的记录！</h4>
                                </div>
                            </div>
                            <!-- {/if} -->
                        </div>
                    </form>
                    <form action="/fundappeal/rechargeappeal" id="F-appeal" method="post">
                        <input type="hidden" id="hasSn" name="hasSn"/>
                        <input type="hidden" id="appealSn" name="sn"/>
                        <input type="hidden" id="appealAccount" name="account"/>
                        <input type="hidden" id="appealAmt" name="amount"/>
                        <input type="hidden" id="appealType" name="type"/>
                        <input type="hidden" id="appealBank" name="bank"/>
                        <input type="hidden" id="appealBankCard" name="card"/>
                    </form>
                    <dl class="fund-info-supplement">
                        <dt>说明：充值未到账记录保存时间为15天，您可以查询最近15天的记录</dt>
                    </dl>
                </div>
            </div>
        </div>
        <input type="hidden" id="currentUserAccount" value="{$smarty.session.datas.info.account}">
        <script type="text/tpl" id="J-template-newFntips">
            <div>
                <div class="newFntips"></div>
                <p class="newFntext">如果您的充值超过5分钟未到账，请催一下，我们将全心全意为您服务！</p>
            </div>
        </script>
        <!-- //////////////底侧公共页面////////////// -->
        {include file='/default/ucenter/footer.tpl'}
        <!-- /////////////底侧公共页面/////////////// -->
    </body>
    {literal}
    <script>
    (function () {
        	$.ajax({
				type:'post',
				dataType:'json',
				async:false,
				url:'/fundappeal/userdetail',
				data:'',	
				success:function(data){
					if(data['appealNewFunc']=="1")
					{			
						    var minWindow = new phoenix.MiniWindow({cls: 'ui-newFntips'}),
	                        mask = phoenix.Mask.getInstance();
			                minWindow.dom.css({
			                    width: 360
			                });
			                minWindow.addEvent('beforeShow', function () {
			                    mask.show();
			                });
			                minWindow.addEvent('afterHide', function () {
			                    mask.hide();
			                    saveuserdetail();
			                });
			                minWindow.showCloseButton();
			                minWindow.doClose = function () {
			                	minWindow.hide();
			                    saveuserdetail();
			                };
			                var str = $('#J-template-newFntips').html();
			                minWindow.setTitle('新功能介绍');
			                minWindow.setContent(str);
			                minWindow.show();			
					
					}	
				}
				
			});


            
            /*var userName = $('#currentUserAccount').val();
            var cookieKey = 'fundappeal_tip_' + userName;
            if (!$.cookie(cookieKey)) {
              
                $.cookie(cookieKey, true);
           }
			*/
            $('.pageBtn').bind('click', function () {
                var p = $(this).attr('page');
                if (p == null)
                    p = $('#txt-page').val();
                $('#page').val(p);
                $('#F-query').submit();
            });
            $('.btnSnAppeal').bind('click', function () {
                var item = $(this).parent().parent();
                $('#hasSn').val(true);
                $('#appealSn').val(item.find('[id="rechargeSn"]').val());
                $('#appealType').val(item.find('[id="rechargeType"]').val());
                $('#appealAmt').val(item.find('[id="rechargeAmt"]').val());
                $('#appealAccount').val(item.find('[id="rechargeAccount"]').val());
                $('#appealBank').val(item.find('[id="rechargeBankId"]').val());
                $('#appealBankCard').val(item.find('[id="rechargeCard"]').val());
                $('#F-appeal').submit();
            });


            var saveuserdetail = function(){
            	$.ajax({
    				type:'post',
    				dataType:'json',
    				async:false,
    				url:'/fundappeal/saveuserdetail',
    				data:'appealNewFunc=0',	
    				success:function(data){
						
    				}

            	});




            }



            
        })();
    </script>
    {/literal}
</html>