<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
    <!-- {if "FUND_WITHDRAW_CONFIG_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_0" value="0">提现分流配置</li>
    <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_CONFIG_UPDOWN"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_1" value="1">提现上下限配置</li>
    <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_CONFIG_AVALIWITHDRAW"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_2" value="2">可提现额度配置</li>
     <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_CONFIG_RISK"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_3" value="3">风险提现-门槛设置</li>
    <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_DRAWTIPS"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_4" value="4">审核提示配置</li>
    <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_APPEALTIPS"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_5" value="5">申訴提示配置</li>
    <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_URGENCY"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_6" value="6">紧急提示</li>
    <!-- {/if} -->
    
    <!-- {if "FUND_WITHDRAW_SEPERATE"|in_array:$smarty.session.datas.info.acls} -->
    <li id="drawcfg_7" value="7">大额提现拆单配置</li>
    <!-- {/if} -->
</ul>


