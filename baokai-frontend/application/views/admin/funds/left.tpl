<div class="col-side">
    <dl class="nav">
        <!-- {if "FUND_RECHARGE"|in_array:$smarty.session.datas.info.acls} -->
        <dt>充值相关</dt>
        <!-- {/if} -->
        <!-- {if "FUND_RECHARGE_EXCEPTION"|in_array:$smarty.session.datas.info.acls} -->
        <dd ><a class='MenuRechargemange' href="/admin/Rechargemange/index?parma=sv1&swithval=1">异常充值处理</a></dd>
        <!-- {/if} -->
        <!-- {if "FUND_RECHARGE_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
            <!-- {if "FUND_RECHARGE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
            <dd><a class='MenuRechargemangeConfig' href="/fundAdmin/Rechargemange/index?parma=bypass">充值相关配置</a></dd>
            <!--{else}-->
            <dd><a class='MenuRechargemangeConfig' href="/admin/Rechargemange/index?parma=sv2">充值相关配置</a></dd>
            <!-- {/if} -->
        <!-- {/if} -->
        <!-- {if "FUND_RECHARGE_APPEAL_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
        <dd><a class='MenuFundRechargeAppealConfig' href="/admin/Rechargemange/index?parma=abtf">申诉相关配置</a></dd>
        <!-- {/if} -->
        <!-- {if "FUND_RECHARGE_REMARK"|in_array:$smarty.session.datas.info.acls} -->
        <dd><a class='MenuRemark' href="/admin/Remark/index?parma=sv1">唯一附言管理</a></dd>
        <!-- {/if} -->
        <!-- {if "FUND_RECHARGE_APPREAL"|in_array:$smarty.session.datas.info.acls} -->
        <dd><a class='MenuRechargeAppreal' id="mnRechargeAppeal" href="/admin/Rechargemange/index?parma=aprluntreated">充值申诉处理<img id="redPoint" src="{$path_img}/images/admin/red.png" width="10" height="10" style="display:none"/></a></dd> 
        <!-- {/if} -->                               
        <!-- {if "FUND_WITHDRAW"|in_array:$smarty.session.datas.info.acls} -->
        <dt>提现相关</dt>
        <!-- {/if} -->
        <!-- {if "FUND_WITHDRAW_RISK"|in_array:$smarty.session.datas.info.acls} -->
        <dd><a class='MenuWithdrawals' id="mnWithdraw" href="/admin/Rechargemange/index?parma=sv3">风险提现处理<img id="wdBillRedPoint" src="{$path_img}/images/admin/red.png" width="10" height="10" style="display:none"/></a></dd>
        <!-- {/if} -->
        <!-- {if "FUND_WITHDRAW_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
            <!-- {if "FUND_WITHDRAW_CONFIG_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
            <dd><a class='MenuWithdrawalsConfig' href="/admin/Fundconfig?parma=wcf0">提现相关配置</a></dd>
            <!--{else}-->
            <dd><a class='MenuWithdrawalsConfig' href="/admin/Fundconfig?parma=wcf1">提现相关配置</a></dd>
            <!--{/if}-->
        <!-- {/if} -->
        <!-- {if "FUND_WITHDRAW_WHITELIST"|in_array:$smarty.session.datas.info.acls} -->
        <dd><a class='MenuFundConfig' href="/admin/Fundconfig/index?parma=sv4">可提现额度白名单</a></dd>
        <!-- {/if} -->
	<!-- {if "FUND_WITHDRAW_APPEAL"|in_array:$smarty.session.datas.info.acls} -->	
		<dd><a class='DrawArgueList' id="mnWithdrawAppeal" href="/admin/Fundconfig/index?parma=sv11">提现申诉处理<img id="wdRedPoint" src="{$path_img}/images/admin/red.png" width="10" height="10" style="display:none"/></a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_MANUAL"|in_array:$smarty.session.datas.info.acls} -->
		<dt>人工资金操作</dt>
	<!-- {/if} -->
	<!-- {if "FUND_MANUAL_PROCEDURE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuOpterators' href="/admin/Opterators/index?parma=sv1">人工资金操作审核流程</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_MANUAL_BATCH"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuAllOpterators' href="/admin/Opterators/index?parma=multilist">人工批量操作</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_MANUAL_AWARDS"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuAwardsOpterators' href="/admin/Opterators/index?parma=aas">人工活动派奖</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_BANKCARD"|in_array:$smarty.session.datas.info.acls} -->
		<dt>银行卡相关</dt>
	<!-- {/if} -->
	<!-- {if "FUND_BANKCARD_BANKMANAGE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuBanks' href="/admin/Bankcardsmanage/index?parma=sv41">银行管理</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_BANKCARD_BANKCARDMANAGE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuBankcards' href="/admin/Bankcardsmanage/index?parma=sv45">银行卡管理</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_BANKCARD_BINDMANAGE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuBankcardsManage' href="/admin/Bankcardsmanage/index?parma=sv42">用户银行卡绑定管理</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_BANKCARD_BLACKLIST"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenubankNameManage' href="/admin/Bankcardsmanage/index?parma=sv43">银行卡黑名单管理</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT"|in_array:$smarty.session.datas.info.acls} -->
		<dt>统计报表</dt>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT_CHARGE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuReport' id="mnRecharge" href="/admin/Reporting/index?parma=sv51">充值明细表<img id="cgBillRedPoint" src="{$path_img}/images/admin/red.png" width="10" height="10" style="display:none"/></a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT_WITHDRAW"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuReportDetail' href="/admin/Reporting/index?parma=sv52">提现明细表</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT_CHARGEWITHDRAW"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuGeneration' href="/admin/Reporting/index?parma=sv53">充提报表(总代)</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT_GAMEDETAIL"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuGenerationDetail' href="/admin/Reporting/index?parma=sv54">游戏币明细(总代)</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT_FUNDDETAIL"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuAccountDetail' href="/admin/Reporting/index?parma=sv55">账户明细表</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_REPORT_TOPWINLOSE"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuProfitLoss' href="/admin/Reporting/index?parma=sv56">总代盈亏报表</a></dd>
	<!-- {/if} -->
	<!-- {if "FUND_TRANSFER"|in_array:$smarty.session.datas.info.acls} -->
		<dt>转账相关</dt>
	<!-- {/if} -->
	<!-- {if "FUND_TRANSFER_LIST"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuTransferDetail' href="/admin/Transfer/index?parma=sv61">转账明细</a></dd> 
	<!-- {/if} -->
	<!-- {if "FUND_TRANSFER_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
		<dd><a class='MenuTransferConfig' href="/admin/Transfer/index?parma=sv62">转账限额配置</a></dd>
	<!-- {/if} -->
	</dl>
</div>
<audio id="chongzhiAudio"><source src="{$path_img}/audios/chongzhi.mp3" type="audio/mp3"></audio>
<audio id="shenshuAudio"><source src="{$path_img}/audios/shenshu.mp3" type="audio/mp3"></audio>
<audio id="tixianAudio"><source src="{$path_img}/audios/tixian.mp3" type="audio/mp3"></audio>
<script>
    var initTitle = document.title;
    var needShowTitle = "false";
    function showTitle(flag){
        needShowTitle = flag;
    }


    function titler() {
        if (!document.all && !document.getElementById)
            return;

        $.ajax({
            url: '/admin/Rechargemange/index?parma=prop',
            method: "post",
            success: function (data) {
                var result = JSON.parse(data);
                var title = "";
                if(result.rc_purview){
                    if( result.rcCount > 0 ){
                        $("#redPoint").show();
                           document.getElementById("shenshuAudio").play(); //播放声音
                        title += "（" + result.rcCount + "）充值申诉";
                    }else{
		         $('#redPoint').hide();
			  if(title !=""){
				title=initTitle
			  }
		    }
                }
                if(result.wd_purview){
                    if(result.wdCount > 0){
                        $("#wdRedPoint").show();
                       document.getElementById("shenshuAudio").play();  //播放声音
                        if(title !=""){
                            title += "，（" +result.wdCount + "）提现申诉"
                        }else{
                            title += "（" +result.wdCount + "）提现申诉"
                        }
                    }else{
		         $('#wdRedPoint').hide();
			  if(title !=""){
				title=initTitle
			  }
		    }
                }
		if(result.wd_bill_purview){
                    if(result.wdBillCount > 0){
                        $("#wdBillRedPoint").show();
                         document.getElementById("tixianAudio").play();//播放声音
                        if(title !=""){
                            title += "，（" +result.wdBillCount + "）提现申请"
                        }else{
                            title += "（" +result.wdBillCount + "）提现申请"
                        }
                    }else{
		         $('#wdBillRedPoint').hide();
			  if(title !=""){
				title=initTitle
			  }
		    }
                }
               /* if(result.cg_bill_purview){
                    if(result.cgBillCount > 0){
                        $("#cgBillRedPoint").show();
                          document.getElementById("chongzhiAudio").play(); //播放声音
                        if(title !=""){
                            title += "，（" +result.cgBillCount + "）充值"
                        }else{
                            title += "（" +result.cgBillCount + "）充值"
                        }
                   }else{
		         $('#cgBillRedPoint').hide();
			  if(title !=""){
				title=initTitle
			  }
		    }
                }
                if(title != ""&&needShowTitle){
                    document.title = title;
                }else{
                    document.title = initTitle;
                }
		*/
            }
        });
        setTimeout("titler()", 30000);	   //Restarts. Remove line for no-repeat.
    }
    setTimeout("titler()", 5000);
    //window.onload = titler;


    //点击去除红点 byAllen
    //风险提现处理
    document.getElementById('mnWithdraw').onClick=function(){
        $('#wdBillRedPoint').hide();
    };

    //充值申诉处理
    document.getElementById('mnRechargeAppeal').onClick=function(){
        $('#redPoint').hide();
    };

    //
    //充值明细表
    document.getElementById('mnRecharge').onClick=function(){
        $('#cgBillRedPoint').hide();
    };

</script>
