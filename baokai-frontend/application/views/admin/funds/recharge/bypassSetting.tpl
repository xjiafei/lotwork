<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; <span id="titleName">充值相关配置</span></div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">
                    <div class="ui-tab">
                        <ul class="ui-tab-title ui-tab-title2 clearfix">
                            <!-- {if "FUND_RECHARGE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
                            <span><li id="bypass" class="current">充值分流设置</li></span>
                            <!--{else}-->
                             <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_CONFIG_UPTO"|in_array:$smarty.session.datas.info.acls} -->
                            <span><li><a href="/admin/Rechargemange/index?parma=sv2&index=0">充值上下限配置</a></li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_RECHARGE_CONFIG_RETURN"|in_array:$smarty.session.datas.info.acls} -->
                            <span><li><a href="/admin/Rechargemange/index?parma=sv2&index=1">充值返送手续费配置</a></li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                        </ul>
                        <div class="ui-tab tab-left">
                            <div class="ui-tab-content ui-tab-content-current" id="DivRules">
                                <div id="J-side-menu" class="ui-tab-title clearfix">
                                    <ul>
										<!--{if {$tab} eq 'appUni'}-->
                                        <li>
                                            <a href="javascript:void(0);">快捷充值</a>
                                        </li> 	
										<li class="current">
                                            <a href="javascript:void(0);">APP银联充值</a>
                                        </li> 
                                        <li>
                                            <a href="javascript:void(0);">微信充值</a>
                                        </li> 
                                        <!--{else if {$tab} eq 'Wechat'}-->
                                        <li>
                                            <a href="javascript:void(0);">快捷充值</a>
                                        </li>   
                                        <li>
                                            <a href="javascript:void(0);">APP银联充值</a>
                                        </li> 
                                         <li class="current">
                                            <a href="javascript:void(0);">微信充值</a>
                                        </li> 
										<!--{else} -->
                                         <li class="current">
                                            <a href="javascript:void(0);">快捷充值</a>
                                        </li> 	
										<li>
                                            <a href="javascript:void(0);">APP银联充值</a>
                                        </li> 
                                         <li>
                                            <a href="javascript:void(0);">微信充值</a>
                                        </li> 
										<!--{/if} -->
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <ul  style="overflow-y: hidden;" id="chargeSeparate">
                            <li>
								<input type="hidden" id="tab" value="{$tab}">
								<!--{if {$tab} eq 'appUni' or {$tab} eq 'Wechat'}-->
								<ul  style="display: none;">
								<!--{else} -->
								<ul>
								<!--{/if} -->
								<li>
                                <table class="table table-info table-border">
                                    <thead>
                                        <tr>
                                            <th colspan="4" class="text-left">充值分流设置</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <form id="settingForm">
                                        <tr>
                                            <td class="text-left w-0">分流开关</td>
                                            <td class="text-left w-6">DP&nbsp;&nbsp;    
                                                <!--{if {$dpSetting.isOpen} eq 'Y'}-->
                                                <span id="dpSwitch" onclick="dpSwitchChange();" class='bypassOpen' alt="开"></span>
                                                <!--{else}-->
                                                <span id="dpSwitch" onclick="dpSwitchChange();" class='bypassClose' alt="关"></span>
                                                <!--{/if}-->
                                                <!--{if {$dpIsOver} eq 'Y'}-->
                                                <font color="red">（已关闭，隔日自动恢复）</font>
                                                <!--{/if}-->
                                            </td>
											<input type="hidden" id="dpId" name="dpId" value="{$dpSetting.id}"/>
											<input type="hidden" id="dpSwitchStatus" name="dpSwitchStatus" value="{$dpSetting.isOpen}"/>
											<td class="text-left w-6">通汇&nbsp;&nbsp;     
												<!--{if {$thSetting.isOpen} eq 'Y'}-->
												<span id="thSwitch" onclick="thSwitchChange();" class='bypassOpen' alt="开"/></span>
												<!--{else}-->
												<span id="thSwitch" onclick="thSwitchChange();" class='bypassClose' alt="关"/></span>
												<!--{/if}-->
												<!--{if {$thIsOver} eq 'Y'}-->
													<font color="red">（已关闭，隔日自动恢复）</font>
													<!--{/if}-->
											</td>
											<input type="hidden" id="thId" name="thId" value="{$thSetting.id}"/>
											<input type="hidden" id="thSwitchStatus" name="thSwitchStatus" value="{$thSetting.isOpen}">
											<td class="text-left w-6">汇博&nbsp;&nbsp;     
												<!--{if {$hbSetting.isOpen} eq 'Y'}-->
												<span id="hbSwitch" onclick="hbSwitchChange();" class='bypassOpen' alt="开"/></span>
												<!--{else}-->
												<span id="hbSwitch" onclick="hbSwitchChange();" class='bypassClose' alt="关"/></span>
												<!--{/if}-->
												<!--{if {$hbIsOver} eq 'Y'}-->
													<font color="red">（已关闭，隔日自动恢复）</font>
													<!--{/if}-->
											</td>
											<input type="hidden" id="hbId" name="hbId" value="{$hbSetting.id}"/>
											<input type="hidden" id="hbSwitchStatus" name="hbSwitchStatus" value="{$hbSetting.isOpen}">
                                        </tr>
                                        <tr>
                                            <td class="text-left w-0">分流条件</td>
                                            <td class="text-left w-6">
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="dpLowLimit" class="input w-1 checkLimit" value="{$dpSetting.singleLowlimit}"/>
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="dpUpLimit" class="input w-1 checkLimit" value="{$dpSetting.singleUplimit}"/>
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="dpDailyLimit" class="input w-3 checkLimit" value="{$dpSetting.dailyUplimit}"/>
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$dpIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$dpSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$dpSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
                                            <td>
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="thLowLimit" class="input w-1 checkLimit" value="{$thSetting.singleLowlimit}">
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="thUpLimit" class="input w-1 checkLimit" value="{$thSetting.singleUplimit}">
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="thDailyLimit" class="input w-3 checkLimit" value="{$thSetting.dailyUplimit}">
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$thIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$thSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$thSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
											<td>
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="hbLowLimit" class="input w-1 checkLimit" value="{$hbSetting.singleLowlimit}">
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="hbUpLimit" class="input w-1 checkLimit" value="{$hbSetting.singleUplimit}">
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="hbDailyLimit" class="input w-3 checkLimit" value="{$hbSetting.dailyUplimit}">
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$hbIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$hbSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$hbSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
                                        </tr>
                                    </form>
                                    <tr>
                                        <td class="text-left w-2"></td>
                                        <td colspan="3">
                                            <!-- {if "FUND_SAVE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
                                            <input id="saveBtn" type="button" class="btn btn-important"  value="保存"  style="width:80px;"/>
                                            <input id="cancelBtn" type="button" class="btn" name="" value="撤销编辑"  style="width:100px;"/>
                                            <!-- {/if} -->
                                        </td>
                                    </tr>
                                </tbody>
                                </table>
                            </li><br/><br/>
                            <li>
                                <table class="table table-info table-border">
                                    <thead>
                                        <tr>
                                            <th colspan="3" class="text-left">白名單设置</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="text-left w-1">白名单</td>
                                            <td>
                                                <table id="J-table-data0" class="table table-info table-border">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-left w-0">账号</th>
                                                            <th class="text-left w-0">操作</th>
                                                            <th class="text-left w-0" >添加时间</th>
                                                            <th class="text-left w-0">备注</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="showInfo0">
                                                    </tbody>
                                                </table>
                                                <br/>
                                                <span>
                                                <!-- {if "FUND_RECHARGE_SAVEITEM"|in_array:$smarty.session.datas.info.acls} -->
                                                <input id="addItem0" type="button" name="02" class="btn" value="添加DP白名单"  style="width:120;"/>
                                                <!-- {/if} -->
                                                </span>
												<!--{if {$tab} eq 'appUni'}-->
												<div class="pagination Pagination0" style="display: none;"></div>
												<div class="pagination Pagination2"></div>
												<!--{else} -->
												<div class="pagination Pagination0" ></div>
												<div class="pagination Pagination2" style="display: none;"></div>
												<!--{/if} -->
                                            </td>
                                            <td>
                                                <table id="J-table-data1" class="table table-info table-border">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-left">账号</th>
                                                            <th class="text-left">操作</th>
                                                            <th class="text-left">添加时间</th>
                                                            <th class="text-left">备注</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="showInfo1">
                                                    </tbody>
                                                </table>
                                                <br/>
                                                <span>
                                                <!-- {if "FUND_RECHARGE_SAVEITEM"|in_array:$smarty.session.datas.info.acls} -->
                                                <input class="btn" type="button" id="addItem1" name="12" value="添加通汇白名单"  style="width:120;"/>
                                                <!-- {/if} -->
                                                </span>
												<!--{if {$tab} eq 'appUni'}-->
												<div class="pagination Pagination1" ></div>
												<div class="pagination Pagination3" style="display: none;"></div>
												<!--{else} -->
												<div class="pagination Pagination1" style="display: none;"></div>
												<div class="pagination Pagination3"></div>
												<!--{/if} -->
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </li></ul></li>
							<!--  -----------------------------------------------------APP銀聯充值------------------------------------------------------------------- -->
							 <li>
							<!--{if {$tab} eq 'appUni'}-->
								<ul>
							<!--{else} -->
								<ul  style="display: none;">
							<!--{/if} -->
								<li>
									<table class="table table-info table-border">
										<thead>
											<tr>
												<th colspan="3" class="text-left">充值分流设置</th>
											</tr>
										</thead>
										<tbody>
										<form id="settingForm2">
											<tr>
												<td class="text-left w-0" style='width:5%;'>分流开关</td>
												<td class="text-left w-6">DP&nbsp;&nbsp;    
													<!--{if {$appDpSetting.isOpen} eq 'Y'}-->
														<span id="dpSwitchForUnipay" onclick="dpForUnipaySwitchChange();" class='bypassOpen' alt="开"></span>
													<!--{else}-->
														<span id="dpSwitchForUnipay" onclick="dpForUnipaySwitchChange();" class='bypassClose' alt="关"></span>
													<!--{/if}-->
													<!--{if {$appDpIsOver} eq 'Y'}-->
														<font color="red">（已关闭，隔日自动恢复）</font>
													<!--{/if}-->
												<input type="hidden" id="dpForUnipayId" name="dpForUnipayId" value="{$appDpSetting.id}"/>
												<input type="hidden" id="dpForUnipaySwitchStatus" name="dpForUnipaySwitchStatus" value="{$appDpSetting.isOpen}"/>
												<td class="text-left w-6">汇潮&nbsp;&nbsp;     
													<!--{if {$appECPSSSetting.isOpen} eq 'Y'}-->
														<span id="ecpssSwitchForUnipay" onclick="ecpssForUnipaySwitchChange();" class='bypassOpen' alt="开"/></span>
													<!--{else}-->
														<span id="ecpssSwitchForUnipay" onclick="ecpssForUnipaySwitchChange();" class='bypassClose' alt="关"/></span>
													<!--{/if}-->
													<!--{if {$appECPSSIsOver} eq 'Y'}-->
														<font color="red">（已关闭，隔日自动恢复）</font>												
													<!--{/if}-->
												<input type="hidden" id="ecpssForUnipayId" name="ecpssForUnipayId" value="{$appECPSSSetting.id}"/>
												<input type="hidden" id="ecpssForUnipaySwitchStatus" name="ecpssForUnipaySwitchStatus" value="{$appECPSSSetting.isOpen}"/>
												</td>
											</tr>
											<tr>
                                            <td class="text-left w-0">分流条件</td>
                                            <td class="text-left w-6">
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="dpForUnipayLowLimit" class="input w-1 checkLimit" value="{$appDpSetting.singleLowlimit}"/>
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="dpForUnipayUpLimit" class="input w-1 checkLimit" value="{$appDpSetting.singleUplimit}"/>
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="dpForUnipayDailyLimit" class="input w-3 checkLimit" value="{$appDpSetting.dailyUplimit}"/>
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$appDpIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$appDpSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$appDpSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
                                            <td>
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="ecpssForUnipayLowLimit" class="input w-1 checkLimit" value="{$appECPSSSetting.singleLowlimit}">
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="ecpssForUnipayUpLimit" class="input w-1 checkLimit" value="{$appECPSSSetting.singleUplimit}">
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="ecpssForUnipayDailyLimit" class="input w-3 checkLimit" value="{$appECPSSSetting.dailyUplimit}">
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$appECPSSIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$appECPSSSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$appECPSSSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
                                        </tr>
										<input id="chooseAPPUni" type="hidden" name='chooseAPPUni' value="appUni"/>	
										</form>
										<tr>
											<td class="text-left w-2"></td>
											<td colspan="2">
												<!-- {if "FUND_SAVE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
												<input id="saveBtn2" type="button" class="btn btn-important"  value="保存"  style="width:80px;"/>
												<input id="cancelBtn2" type="button" class="btn" name="" value="撤销编辑"  style="width:100px;"/>
												<!-- {/if} -->
											</td>
										</tr>	
									</tbody>
									</table>
								</li><br/><br/>
								<li>
                                <table class="table table-info table-border">
                                    <thead>
                                        <tr>
                                            <th colspan="3" class="text-left">白名單设置</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td class="text-left w-1">白名单</td>
                                            <td>
                                                <table id="J-table-data2" class="table table-info table-border">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-left w-0">账号</th>
                                                            <th class="text-left w-0">操作</th>
                                                            <th class="text-left w-0" >添加时间</th>
                                                            <th class="text-left w-0">备注</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="showInfo2">
                                                    </tbody>
                                                </table>
                                                <br/>
                                                <span>
                                                <!-- {if "FUND_RECHARGE_SAVEITEM"|in_array:$smarty.session.datas.info.acls} -->
                                                <input id="addItem0" type="button" name="05" class="btn" value="添加APP DP白名单"  style="width:120;"/>
                                                <!-- {/if} -->
                                                </span>
												<!--{if {$tab} eq 'appUni'}-->
												<div class="pagination Pagination2" ></div>
												<div class="pagination Pagination0" style="display: none;"></div>
												<!--{else} -->
												<div class="pagination Pagination2" style="display: none;"></div>
												<div class="pagination Pagination0"></div>
												<!--{/if} -->
                                            </td>
                                            <td>
                                                <table id="J-table-data3" class="table table-info table-border">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-left">账号</th>
                                                            <th class="text-left">操作</th>
                                                            <th class="text-left">添加时间</th>
                                                            <th class="text-left">备注</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="showInfo3">
                                                    </tbody>
                                                </table>
                                                <br/>
                                                <span>
                                                <!-- {if "FUND_RECHARGE_SAVEITEM"|in_array:$smarty.session.datas.info.acls} -->
                                                <input class="btn" type="button" id="addItem" name="35" value="添加白名单"  style="width:120;"/>
                                                <!-- {/if} -->
                                                </span>
												<!--{if {$tab} eq 'appUni'}-->
												<div class="pagination Pagination3" ></div>
												<div class="pagination Pagination1" style="display: none;"></div>
												<!--{else} -->
												<div class="pagination Pagination3" style="display: none;"></div>
												<div class="pagination Pagination1"></div>
												<!--{/if} -->
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </li></ul></li>
							<!--  -----------------------------------------------------銀聯充值------------------------------------------------------------------- -->

                            <!--  -----------------------------------------------------web微信充值------------------------------------------------------------------- -->
                             <li>
                            <!--{if {$tab} eq 'Wechat'}-->
                                <ul>
                            <!--{else} -->
                                <ul  style="display: none;">
                            <!--{/if} -->
                                <li>
                                    <table class="table table-info table-border">
                                        <thead>
                                            <tr>
                                                <th colspan="3" class="text-left">充值分流设置</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <form id="settingForm3">
                                            <tr>
                                                <td class="text-left w-0" style='width:5%;'>分流开关</td>
                                                <td class="text-left w-6">DP&nbsp;&nbsp;    
                                                    <!--{if {$wechatDpSetting.isOpen} eq 'Y'}-->
                                                        <span id="dpSwitchForWechat" onclick="dpForWechatSwitchChange();" class='bypassOpen' alt="开"></span>
                                                    <!--{else}-->
                                                        <span id="dpSwitchForWechat" onclick="dpForWechatSwitchChange();" class='bypassClose' alt="关"></span>
                                                    <!--{/if}-->
                                                    <!--{if {$wechatDpIsOver} eq 'Y'}-->
                                                        <font color="red">（已关闭，隔日自动恢复）</font>
                                                    <!--{/if}-->
                                                <input type="hidden" id="dpForWechatId" name="dpForWechatId" value="{$wechatDpSetting.id}"/>
                                                <input type="hidden" id="dpForWechatSwitchStatus" name="dpForWechatSwitchStatus" value="{$wechatDpSetting.isOpen}"/>
                                                <td class="text-left w-6">华势&nbsp;&nbsp;     
                                                    <!--{if {$wechatWorthSetting.isOpen} eq 'Y'}-->
                                                        <span id="worthSwitchForWechat" onclick="worthForWechatSwitchChange();" class='bypassOpen' alt="开"/></span>
                                                    <!--{else}-->
                                                        <span id="worthSwitchForWechat" onclick="worthForWechatSwitchChange();" class='bypassClose' alt="关"/></span>
                                                    <!--{/if}-->
                                                    <!--{if {$wechatWorthIsOver} eq 'Y'}-->
                                                        <font color="red">（已关闭，隔日自动恢复）</font>                                               
                                                    <!--{/if}-->
                                                <input type="hidden" id="worthForWechatId" name="worthForWechatId" value="{$wechatWorthSetting.id}"/>
                                                <input type="hidden" id="worthForWechatSwitchStatus" name="worthForWechatSwitchStatus" value="{$wechatWorthSetting.isOpen}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                            <td class="text-left w-0">分流条件</td>
                                            <td class="text-left w-6">
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="dpForWechatLowLimit" class="input w-1 checkLimit" value="{$wechatDpSetting.singleLowlimit}"/>
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="dpForWechatUpLimit" class="input w-1 checkLimit" value="{$wechatDpSetting.singleUplimit}"/>
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="dpForWechatDailyLimit" class="input w-3 checkLimit" value="{$wechatDpSetting.dailyUplimit}"/>
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$wechatDpIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$wechatDpSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$wechatDpSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
                                            <td>
                                                <dl class="set-list">
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">金额</span>      
                                                        <input type="text" name="worthForWechatLowLimit" class="input w-1 checkLimit" value="{$wechatWorthSetting.singleLowlimit}">
                                                        <span class="ui-text-info"></span>-<span class="ui-text-info"></span>
                                                        <input type="text" name="worthForWechatUpLimit" class="input w-1 checkLimit" value="{$wechatWorthSetting.singleUplimit}">
                                                        <span class="ui-info" style="width:80px;">元</span>
                                                    </dd>
                                                    <dd>
                                                        <span class="ui-info" style="width:80px;">上限</span>      
                                                        <input type="text" name="worthForWechatDailyLimit" class="input w-3 checkLimit" value="{$wechatWorthSetting.dailyUplimit}">
                                                        <span class="ui-info" style="width:30px;">元 </span><span class="ui-info" >
                                                            {if {$wechatWorthIsOver} eq 'Y'}
                                                            <font color="red">今日充值金额：{$wechatWorthSetting.dailyCharge} 元</font></span>
                                                            {else}
                                                            今日充值金额：{$wechatWorthSetting.dailyCharge} 元</span>
                                                            {/if}
                                                        </br>
                                                        <span class="ui-info" style="width:80px;"></span>
                                                        成功充值的金额超过上限时将会自动关闭该渠道，次日0时自动恢复。
                                                    </dd>
                                                </dl>
                                            </td>
                                        </tr>
                                        <input id="chooseWechat" type="hidden" name='chooseWechat' value="Wechat"/> 
                                        </form>
                                        <tr>
                                            <td class="text-left w-2"></td>
                                            <td colspan="2">
                                                <!-- {if "FUND_SAVE_BYPASS"|in_array:$smarty.session.datas.info.acls} -->
                                                <input id="saveBtn3" type="button" class="btn btn-important"  value="保存"  style="width:80px;"/>
                                                <input id="cancelBtn3" type="button" class="btn" name="" value="撤销编辑"  style="width:100px;"/>
                                                <!-- {/if} -->
                                            </td>
                                        </tr>   
                                    </tbody>
                                    </table>
                                </li><br/><br/>
                                <li>
                            </li></ul></li>
                            <!--  -----------------------------------------------------web微信充值------------------------------------------------------------------- -->
							
                        </ul>                   
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--添加白名单-->
    <div class="pop w-6" id="AddTipsWindow" style="display:none;">
        <div class="hd">
            <i class="close" name="closeIcoDiv1"></i>
            <h3 align="center">新增充值渠道白名单</h3>
        </div>
        <div class="bd">
            <ul class="ui-form ui-form-small">
                <li>
                    <form id="addForm">
                        <label class="ui-label w-auto" align="right">用戶名：</label>
                        <input id="account" type="text" id="account" name="account">
                        <br>

                        <label class="ui-label w-auto" align="right">备注：</label>			
                        <input type="text" id="memo" name="memo" form="addForm" onkeyup='wordCount()'/>
                        <input type="hidden" id="saveType" name="saveType" value=""/>
						<input type="hidden" id="saveChargeWaySet" name="saveChargeWaySet" value=""/><span class="ui-text-prompt" id='words'>0</span><span class="ui-text-prompt">/100</span>

                    </form>    
                <li> 
                    <a href="javascript:saveItem();" class="btn btn-important" >添加<b class="btn-inner"></b></a>	
                    <input class="btn" type="button" name="closeIcoDiv1" value="返回"  style="width:80px;"/>
                </li>

                </li>
            </ul>
        </div>
    </div>
</div>
{include file='/admin/script-base.tpl'} 
{literal}
    <script>
        var per_page_num = 10;
        function wordCount() {
            var total = $('#memo').val().length;
            if (total > 100) {
                alert("內容已達最大上限數");
                var content = $('#memo').val().substr(0, 100);
                $('#memo').val(content);
                return;
            }
            $('#words').text(total);
        }

        function deleteWhitelist(id, type) {
            var r = confirm("确认删除?");
            if (r != true) {
                return;
            }
            if (id > 0) {
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=deleteBypassWhiteListData',
                    method: "POST",
                    data: {deleteId: id},
					async: false ,
					success: function () {
						if (type == 0) {
							queryWhiteList0("0");						
						}else if(type == 1){
							queryWhiteList1("0");
						}else if(type == 2){
							queryWhiteList2("0");
							$(".Pagination2").show();
						}else {                        
							queryWhiteList3("0");
							$(".Pagination3").show();
						}
					}
                });
            }

        }
		
        $("#saveBtn").bind("click", function () {
            $.ajax({
                url: "/admin/Rechargemange/index?parma=saveBypassCfg",
                data: $('#settingForm').serialize(),
                method: 'post',
                success: function () {
					if(tab == 'appUni'){
						window.location = "/admin/Rechargemange/index?parma=bypass&tab=appUni";
					}if(tab == 'Wechat'){
                        window.location = "/admin/Rechargemange/index?parma=bypass&tab=Wechat";
                    }else{
					    window.location = "/admin/Rechargemange/index?parma=bypass";	
					}
                }
            });
        });

        $("#cancelBtn").bind("click", function () {
            window.location = "/admin/Rechargemange/index?parma=bypass";
        });

        function dpSwitchChange() {
            var switchType = $("#dpSwitchStatus").val();
            if (switchType == 'Y') {
                $("#dpSwitch").attr('class', 'bypassClose');
                $("#dpSwitchStatus").val('N');
            } else {
                $("#dpSwitch").attr('class', 'bypassOpen');
                $("#dpSwitchStatus").val('Y');
            }
        }

        function thSwitchChange() {
            var switchType = $("#thSwitchStatus").val();
            if (switchType == 'Y') {
                $("#thSwitch").attr('class', 'bypassClose');
                $("#thSwitchStatus").val('N');
            } else {
                $("#thSwitch").attr('class', 'bypassOpen');
                $("#thSwitchStatus").val('Y');
            }
        }
		
		function hbSwitchChange() {
            var switchType = $("#hbSwitchStatus").val();
            if (switchType == 'Y') {
                $("#hbSwitch").attr('class', 'bypassClose');
                $("#hbSwitchStatus").val('N');
            } else {
                $("#hbSwitch").attr('class', 'bypassOpen');
                $("#hbSwitchStatus").val('Y');
            }
        }
		//==============================銀聯分流用設置==============================
		function dpForUnipaySwitchChange() {
            var switchType = $("#dpForUnipaySwitchStatus").val();
            if (switchType == 'Y') {
                $("#dpSwitchForUnipay").attr('class', 'bypassClose');
                $("#dpForUnipaySwitchStatus").val('N');
            } else {
                $("#dpSwitchForUnipay").attr('class', 'bypassOpen');
                $("#dpForUnipaySwitchStatus").val('Y');
            }
        }

        function ecpssForUnipaySwitchChange() {
            var switchType = $("#ecpssForUnipaySwitchStatus").val();
            if (switchType == 'Y') {
                $("#ecpssSwitchForUnipay").attr('class', 'bypassClose');
                $("#ecpssForUnipaySwitchStatus").val('N');
            } else {
                $("#ecpssSwitchForUnipay").attr('class', 'bypassOpen');
                $("#ecpssForUnipaySwitchStatus").val('Y');
            }
        }
		
		$("#saveBtn2").bind("click", function () {
            $.ajax({
                url: "/admin/Rechargemange/index?parma=saveBypassCfg",
                data: $('#settingForm2').serialize(),
                method: 'post',
                success: function () {
                    window.location = "/admin/Rechargemange/index?parma=bypass&tab=appUni";
                }
            });
        });

        $("#cancelBtn2").bind("click", function () {
            window.location = "/admin/Rechargemange/index?parma=bypass&tab=appUni";
        });
		
		
		
		//==============================銀聯分流用設置==============================
		//==============================微信分流用設置==============================
        function dpForWechatSwitchChange() {
            var switchType = $("#dpForWechatSwitchStatus").val();
            if (switchType == 'Y') {
                $("#dpSwitchForWechat").attr('class', 'bypassClose');
                $("#dpForWechatSwitchStatus").val('N');
            } else {
                $("#dpSwitchForWechat").attr('class', 'bypassOpen');
                $("#dpForWechatSwitchStatus").val('Y');
            }
        }

        function worthForWechatSwitchChange() {
            var switchType = $("#worthForWechatSwitchStatus").val();
            if (switchType == 'Y') {
                $("#worthSwitchForWechat").attr('class', 'bypassClose');
                $("#worthForWechatSwitchStatus").val('N');
            } else {
                $("#worthSwitchForWechat").attr('class', 'bypassOpen');
                $("#worthForWechatSwitchStatus").val('Y');
            }
        }
        
        $("#saveBtn3").bind("click", function () {
            $.ajax({
                url: "/admin/Rechargemange/index?parma=saveBypassCfg",
                data: $('#settingForm3').serialize(),
                method: 'post',
                success: function () {
                    window.location = "/admin/Rechargemange/index?parma=bypass&tab=Wechat";
                }
            });
        });

        $("#cancelBtn3").bind("click", function () {
            window.location = "/admin/Rechargemange/index?parma=bypass&tab=Wechat";
        });
        
        //==============================微信分流用設置==============================


        function saveItem() {
            if ($('#account').val() == "") {
                alert("请填写用戶名");
                return;
            }
            if ($('#memo').val() == "") {
                alert("请填写备注!");
                return;
            }
            var type = $("#saveType").val();
			var chargeWaySet = $("#saveChargeWaySet").val();
            $.ajax({
                url: "/admin/Rechargemange/index?parma=saveItem",
                data: $('#addForm').serialize(),
                method: 'post',
                success: function (data) {
                    if (data.length > 0) {
                        if (data == 'Y') {
                            alert("用户名已存在，请勿重复添加!");
                            return;
                        }
                    }
                    if (type == 0 && chargeWaySet ==2) {//快捷DP白名單
                        queryWhiteList0("0");						
                    }else if(type == 1 && chargeWaySet ==2){//快捷通匯
						queryWhiteList1("0");
					}else if(type == 0 && chargeWaySet ==5){//APP银联DP
						queryWhiteList2("0");
						$(".Pagination2").show();
					}else {       //APP银联匯潮              
						queryWhiteList3("0");
						$(".Pagination3").show();
                    }
                    box2.Close();
                    $("#memo").val('');
                    $("#account").val('');

                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    TableStyle("showInfo" + type, 17, 2, "数据异常");
                }
            });
        }
		//快捷DP白名單
        function queryWhiteList0(pages) {
            //放入group对象中(当前页)
            $("#J-table-data0>tbody").html("");
            $(".Pagination0").hide();
            var url = "/admin/Rechargemange/index?parma=whitelist&chargeChannel=0&chargeWaySet=2&pageNo=" + pages;
            $.ajax({
                url: url,
                dataType: 'json',
                method: 'get',
                beforeSend: function () {
                    isLock = false;
                    TableStyle("showInfo0", 17, 1, "查询中");
                },
                success: function (data) {
                    if (data.text.length > 0) {

                        $(".Pagination0").show();
                        var resultAll = eval(data);
                        var re = resultAll.text;
                        var recordNum = 0;
                        recordNum = resultAll.count[0].recordNum;
                        //分页回调				 
                        $(".Pagination0").pagination(recordNum, {
                            num_edge_entries: 2,
                            num_display_entries: 8,
                            current_page: pages,
                            items_per_page: per_page_num,
                            callback: queryWhiteList0
                        });
                        var html = "";
                        //数据填充
                        $.each(re, function (i) {
                            html += "<tr id='item_" + re[i].id + "'><td>" + re[i].userAccount + "</td>";
                            if(resultAll.delPermission == 'Y'){
                                html += "<td><a href='javascript:deleteWhitelist(" + re[i].id + ",0);' style='position:initial' ><font color='#25a38a'>删除</font></a></td>";
                            }else{
                                html += "<td>&nbsp;</td>";
                            }
                            html += "<td>" + re[i].createTime + "</td>";
                            html += "<td>" + re[i].memo + "</td></tr>";
                        });
                        $("#J-table-data0>tbody").html(html);
                    } else {
                        $(".Pagination0").hide();
                        TableStyle("showInfo0", 17, 2, "暂未添加白名单");
                    }
                },
                complete: function ()
                {
                    isLock = true;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    TableStyle("showInfo0", 17, 2, "数据异常");
                }
            });
        }
		//快捷通匯白名單
        function queryWhiteList1(pages) {
            //放入group对象中(当前页)
            $("#J-table-data1>tbody").html("");
            $(".Pagination1").hide();
            var url = "/admin/Rechargemange/index?parma=whitelist&chargeChannel=1&chargeWaySet=2&&pageNo=" + pages;
            $.ajax({
                url: url,
                dataType: 'json',
                method: 'get',
                beforeSend: function () {
                    isLock = false;
                    TableStyle("showInfo1", 17, 1, "查询中");
                },
                success: function (data) {
                    if (data.text.length > 0) {

                        $(".Pagination1").show();
                        var resultAll = eval(data);
                        var re = resultAll.text;
                        var recordNum = 0;
                        recordNum = resultAll.count[0].recordNum;
                        //分页回调				 
                        $(".Pagination1").pagination(recordNum, {
                            num_edge_entries: 2,
                            num_display_entries: 8,
                            current_page: pages,
                            items_per_page: per_page_num,
                            callback: queryWhiteList1
                        });
                        var html = "";
                        //数据填充
                        $.each(re, function (i) {
                            html += "<tr id='item_" + re[i].id + "'><td>" + re[i].userAccount + "</td>";
                            if(resultAll.delPermission == 'Y'){
                                html += "<td><a href='javascript:deleteWhitelist(" + re[i].id + ",1);' style='position:initial' ><font color='#25a38a'>删除</font></a></td>";
                            }else{
                                html += "<td>&nbsp;</td>";
                            }
                            html += "<td>" + re[i].createTime + "</td>";
                            html += "<td>" + re[i].memo + "</td>";
                        });
                        $("#J-table-data1>tbody").html(html);
                    } else {
                        $(".Pagination1").hide();
                        TableStyle("showInfo1", 17, 2, "暂未添加白名单");
                    }
                },
                complete: function ()
                {
                    isLock = true;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    TableStyle("showInfo1", 17, 2, "数据异常");
                }
            });
        }
		//APP银联DP
		function queryWhiteList2(pages) {
            //放入group对象中(当前页)
            $("#J-table-data2>tbody").html("");
            $(".Pagination2").hide();
            var url = "/admin/Rechargemange/index?parma=whitelist&chargeChannel=0&chargeWaySet=5&pageNo=" + pages;
            $.ajax({
                url: url,
                dataType: 'json',
                method: 'get',
                beforeSend: function () {
                    isLock = false;
                    TableStyle("showInfo2", 17, 1, "查询中");
                },
                success: function (data) {
                    if (data.text.length > 0) {

						$(".Pagination2").show();
                        var resultAll = eval(data);
                        var re = resultAll.text;
                        var recordNum = 0;
                        recordNum = resultAll.count[0].recordNum;
                        //分页回调				 
                        $(".Pagination2").pagination(recordNum, {
                            num_edge_entries: 2,
                            num_display_entries: 8,
                            current_page: pages,
                            items_per_page: per_page_num,
                            callback: queryWhiteList2
                        });
                        var html = "";
                        //数据填充
                        $.each(re, function (i) {
                            html += "<tr id='item_" + re[i].id + "'><td>" + re[i].userAccount + "</td>";
                            if(resultAll.delPermission == 'Y'){
                                html += "<td><a href='javascript:deleteWhitelist(" + re[i].id + ",2);' style='position:initial' ><font color='#25a38a'>删除</font></a></td>";
                            }else{
                                html += "<td>&nbsp;</td>";
                            }
                            html += "<td>" + re[i].createTime + "</td>";
                            html += "<td>" + re[i].memo + "</td>";
                        });
                        $("#J-table-data2>tbody").html(html);
                    } else {
                        $(".Pagination2").hide();
                        TableStyle("showInfo2", 17, 2, "暂未添加白名单");
                    }
                },
                complete: function ()
                {
                    isLock = true;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    TableStyle("showInfo2", 17, 2, "数据异常");
                }
            });
        }
		//APP银联匯潮
		function queryWhiteList3(pages) {
            //放入group对象中(当前页)
            $("#J-table-data3>tbody").html("");
            $(".Pagination3").hide();
            var url = "/admin/Rechargemange/index?parma=whitelist&chargeChannel=3&chargeWaySet=5&pageNo=" + pages;
            $.ajax({
                url: url,
                dataType: 'json',
                method: 'get',
                beforeSend: function () {
                    isLock = false;
                    TableStyle("showInfo3", 17, 1, "查询中");
                },
                success: function (data) {
                    if (data.text.length > 0) {

						$(".Pagination3").show();
                        var resultAll = eval(data);
                        var re = resultAll.text;
                        var recordNum = 0;
                        recordNum = resultAll.count[0].recordNum;
                        //分页回调				 
                        $(".Pagination3").pagination(recordNum, {
                            num_edge_entries: 2,
                            num_display_entries: 8,
                            current_page: pages,
                            items_per_page: per_page_num,
                            callback: queryWhiteList3
                        });
                        var html = "";
                        //数据填充
                        $.each(re, function (i) {
                            html += "<tr id='item_" + re[i].id + "'><td>" + re[i].userAccount + "</td>";
                            if(resultAll.delPermission == 'Y'){
                                html += "<td><a href='javascript:deleteWhitelist(" + re[i].id + ",3);' style='position:initial' ><font color='#25a38a'>删除</font></a></td>";
                            }else{
                                html += "<td>&nbsp;</td>";
                            }
                            html += "<td>" + re[i].createTime + "</td>";
                            html += "<td>" + re[i].memo + "</td>";
                        });
                        $("#J-table-data3>tbody").html(html);
                    } else {
                        $(".Pagination3").hide();
                        TableStyle("showInfo3", 17, 2, "暂未添加白名单");
                    }
                },
                complete: function ()
                {
                    isLock = true;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    TableStyle("showInfo3", 17, 2, "数据异常");
                }
            });
        }

        (function () {
			
			$('#J-side-menu > ul').find('li').click(function(){
		
				var indexs = $(this).index();
				$(this).siblings('li').removeClass('current');
				$(this).addClass('current');
				$('#J-side-menu').parent().parent().next('ul').find('ul').css("display","none");
				$('#J-side-menu').parent().parent().next('ul').find('ul').eq(indexs).css("display","inline");
				
				if(indexs == 1){ //APP銀聯
					queryWhiteList2("0");
					queryWhiteList3("0");				
					$(".Pagination0").hide();
					$(".Pagination1").hide();
				}else if(indexs == 0){ //快捷
					queryWhiteList0("0");
					queryWhiteList1("0");			
					$(".Pagination2").hide();
					$(".Pagination3").hide();
				}
				
			});
			
			
			
			
            selectMenu('Menufunds', 'MenuRechargemangeConfig');

            option = {zIndex: 350},
            box2 = new LightBox("AddTipsWindow", option),
                    $(document).on('click', "input[id^='addItem']", function () {
                box2.OverLay.Color = "rgb(51, 51, 51)";
                box2.Over = true;
                box2.OverLay.Opacity = 50;
                box2.Fixed = true;
                box2.Center = true;
                box2.Show();
				var str = this.name.split("",2);
                $("#saveType").val(str[0]);
				$("#saveChargeWaySet").val(str[1]);
				$("#words").html("0");
            });

            $(document).on('click', '[name="closeIcoDiv1"]', function (e) {
                box2.Close();
                $("#memo").val('');
                $("#account").val('');
            });

            var isLock = true;
            //查詢白名單 0:DP 1:通匯 2:APP銀聯DP 3:APP銀聯匯潮
			var tab = $("#tab").val();
			console.log('tab is='+tab);
			if(tab == 'appUni'){
				queryWhiteList2("0");
				queryWhiteList3("0");
			}else{
			    queryWhiteList0("0");
				queryWhiteList1("0");	
			}
        })();
	
		(function (){
			//数字校验，自动矫正不符合数学规范的数学(小数两位)
			var inputs =  $('.checkLimit'),checkFn;				
			checkFn = function(){
				var me = this,v = me.value,index;
				me.value = v = v.replace(/^\.$/g, '');		
				index = v.indexOf('.');
				if(index >0){
					me.value=v.substring(0, index);
				}else{
					me.value= v;
				}
				
				if(me.value.length >=9){
					me.value=me.value.substring(0,9);
				}	
			};
			inputs.keyup(checkFn);
			inputs.blur(checkFn);
		})();

    </script>
{/literal}
</body>
</html>