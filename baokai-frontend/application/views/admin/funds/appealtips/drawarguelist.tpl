<!-- //////////////头部公共页面////////////// -->
<!-- //////////////头部公共页面////////////// -->

{include file='/admin/header.tpl'}
<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
<!-- /////////////头部公共页面/////////////// -->
<div class="col-content">
    <!-- //////////////头部公共页面////////////// -->
    {include file='/admin/funds/left.tpl'}
    <!-- /////////////头部公共页面/////////////// -->
    <div class="col-main">
        <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;提现申诉处理</div></div>
        <div class="col-content">
            <div class="col-main">
                <div class="main">

                    <div class="ui-tab">
                        <ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
                            <!-- {if "FUND_WITHDRAW_APPEAL_ALL"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="AllData"><li id="AllData">全部</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_WITHDRAW_APPEAL_UNCHECK"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="UntreatedData"><li >未处理</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_WITHDRAW_APPEAL_PASS"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="PendingReviewData"><li>审核通过</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_WITHDRAW_APPEAL_UNPASS"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="ProcessingData"><li >审核未通过</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->
                            <!-- {if "FUND_WITHDRAW_APPEAL_WAIT"|in_array:$smarty.session.datas.info.acls} -->
                            <span id="WaitData"><li >待定</li></span>
                            <!--{else}-->
                            <span><li style="display:none;"></li></span>
                            <!-- {/if} -->						
                            <a>每页记录数:</a>
                            <select class="ui-select w-2" id="per_page">
                                <option  value="25">25</option>
                                <option value="50" selected="">50</option>
                                <option value="100">100</option>
                                <option value="200">200</option>	
                            </select>			
                        </ul>
                    </div>
                    <ul class="ui-form ui-tab-content"  id="DivRules">
                        <li>  
                            <!-- {if "FUND_WITHDRAW_RISK_ALL_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
                            <h3 class="ui-title"><a class="btn btn-small " id="J-Download-Report" href="javascript:void(0);" style="float:left;">下载报表<b class="btn-inner"></b></a></h3>
                            <!-- {/if} -->
                            <table  id="J-table-data" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <!--<th>申诉单号</th>-->
                                        <th id="J-sp-serial" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>         
                                        <!--<th>提现用户名</th>-->
                                        <th id="J-sp-drawname" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提现用户名</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 
                                        <!--<th>提款金额</th>-->
                                        <th id="J-sp-drawmoney" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款金额</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                    <li>
                                                        <div class="input-append">
                                                            <input id="J-input-drawmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-drawmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>
                                        <!--<th>提款时间</th>-->
                                        <th id="J-sp-userapply-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userapply-1"> - <input type="text" id="sp-input-userapply-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>												
										<!-- <th>申诉发起时间</th> -->
										<th id="J-sp-userargue-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉发起时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userargue-1"> - <input type="text" id="sp-input-userargue-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>												
                                        <!--<th>是否VIP</th>-->
                                        <th id="J-sp-isvip" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">是否VIP</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        <li data-select-id="1"><a href="#">是</a></li>
                                                        <li data-select-id="0"><a href="#">否</a></li>                                                   
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>  
                                        <!--//0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败-->
                                        <!--<th>状态</th>-->
                                        <th id="J-sp-status" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">状态</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        {foreach from=$aWithDrawStatus key=key item=data}
                                                               {if $key gt 0}
                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                                                {/if}
                                                            {/foreach}
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 
                                        <th>操作</th>
                                        <th>备注</th>
                                        <th>审核人</th>
                                        <th>审核时间</th>
                                    </tr>
                                </thead>
                                <tbody id="showInfo">
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="22">
                                            <div id="Pagination" class="pagination" ></div>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </li>
                    </ul>

                    <ul class="ui-form ui-tab-content">
                        <li>
                            <table id="J-table-data2" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <!--<th>申诉单号</th>-->
                                        <th id="J-sp-serial2" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>    
                                        <!--<th>提现用户名</th>-->
                                        <th id="J-sp-drawname2" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提现用户名</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>
                                        <!--<th>提款金额</th>-->
                                        <th id="J-sp-drawmoney2" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款金额</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                    <li>
                                                        <div class="input-append">
                                                            <input id="J-input2-drawmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input2-drawmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 												
                                        <!--<th>提款时间</th>-->
                                        <th id="J-sp-userapply2-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userapply2-1"> - <input type="text" id="sp-input-userapply2-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>  
										<!-- <th>申诉发起时间</th> -->
										<th id="J-sp-userargue2-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉发起时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userargue2-1"> - <input type="text" id="sp-input-userargue2-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>												
                                        <!--<th>是否VIP</th>-->
                                        <th id="J-sp-isvip2" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">是否VIP</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        <li data-select-id="1"><a href="#">是</a></li>
                                                        <li data-select-id="0"><a href="#">否</a></li>                                                   
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>                                   												
                                        <th>状态</th>
                                        <th>操作</th>
                                        <th>备注</th>
                                        <!-- {if "FUND_WITHDRAW_RISK_UNTREATED_PASS"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="UNTREATED_PASS" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="UNTREATED_PASS" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_UNTREATED_REFUSE"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="UNTREATED_REFUSE" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="UNTREATED_REFUSE" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_UNTREATED_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="UNTREATED_REVIEW" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="UNTREATED_REVIEW" value="0"/>
                                <!-- {/if} -->
                                </tr>
                                </thead>
                                <tbody id="showInfo2">
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="22">
                                            <div id="Pagination2" class="pagination" ></div>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </li>
                    </ul>

                    <ul class="ui-form ui-tab-content">
                        <li>
                            <table id="J-table-data3" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <!--<th>申诉单号</th>-->
                                        <th id="J-sp-serial3" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>  
                                        <!--<th>提现用户名</th>-->
                                        <th id="J-sp-drawname3" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提现用户名</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>
                                        <!--<th>提款金额</th>-->
                                        <th id="J-sp-drawmoney3" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款金额</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                    <li>
                                                        <div class="input-append">
                                                            <input id="J-input3-drawmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input3-drawmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 
                                        <!--<th>提款时间</th>-->
                                        <th id="J-sp-userapply3-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userapply3-1"> - <input type="text" id="sp-input-userapply3-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>
										<!-- <th>申诉发起时间</th> -->
										<th id="J-sp-userargue3-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉发起时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userargue3-1"> - <input type="text" id="sp-input-userargue3-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>												
                                        <!--<th>是否VIP</th>-->
                                        <th id="J-sp-isvip3" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">是否VIP</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        <li data-select-id="1"><a href="#">是</a></li>
                                                        <li data-select-id="0"><a href="#">否</a></li>                                                   
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 

                                        <th>状态</th>

                                        <th>操作</th>
                                        <th>审核人</th>
                                        <th>审核时间</th>
                                        <th>备注</th>
                                        <!-- {if "FUND_WITHDRAW_RISK_REVIEW_PASS"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="REVIEW_PASS" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="REVIEW_PASS" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_REVIEW_REFUSE"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="REVIEW_REFUSE" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="REVIEW_REFUSE" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_REVIEW_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="REVIEW_REVIEW" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="REVIEW_REVIEW" value="0"/>
                                <!-- {/if} -->
                                </tr>
                                </thead>
                                <tbody id="showInfo3">
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="22">
                                            <div id="Pagination3" class="pagination" ></div>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </li>
                    </ul>

                    <ul class="ui-form ui-tab-content">
                        <li>
                            <table id="J-table-data4" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <!--<th>申诉单号</th>-->
                                        <th id="J-sp-serial4" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 
                                        <!--<th>提现用户名</th>-->
                                        <th id="J-sp-drawname4" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提现用户名</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 	

                                        <!--<th>提款金额</th>-->
                                        <th id="J-sp-drawmoney4" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款金额</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                    <li>
                                                        <div class="input-append">
                                                            <input id="J-input4-drawmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input4-drawmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>  

                                        <!--<th>提款时间</th>-->
                                        <th id="J-sp-userapply4-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userapply4-1"> - <input type="text" id="sp-input-userapply4-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>		
                                        </th> 
										<!-- <th>申诉发起时间</th> -->
										<th id="J-sp-userargue4-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉发起时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userargue4-1"> - <input type="text" id="sp-input-userargue4-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>												
                                        <!--<th>是否VIP</th>-->
                                        <th id="J-sp-isvip4" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">是否VIP</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        <li data-select-id="1"><a href="#">是</a></li>
                                                        <li data-select-id="0"><a href="#">否</a></li>                                                   
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 
                                        <!--//0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败-->
                                        <!--<th>状态</th>-->
                                        <th id="J-sp-status" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">状态</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        {foreach from=$aWithDrawStatus key=key item=data}
                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                                            {/foreach}
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>	
                                        <th>操作</th>	
                                        <th>备注</th>
                                        <th>审核人</th>
                                        <th>审核时间</th>			
                                        <th>备注</th>
                                        <!-- {if "FUND_WITHDRAW_RISK_HANDLING_GETSTATUS"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="HANDLING_GETSTATUS" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="HANDLING_GETSTATUS" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_HANDLING_REFUND"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="HANDLING_REFUND" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="HANDLING_REFUND" value="0"/>
                                <!-- {/if} -->
                                </tr>
                                </thead>
                                <tbody id="showInfo4">
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="22">
                                            <div id="Pagination4" class="pagination" ></div>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </li>
                    </ul>
                    
                    
                    
                    
                    <ul class="ui-form ui-tab-content">
                        <li>
                            <table id="J-table-data5" class="table table-info table-function">
                                <thead>
                                    <tr>
                                        <!--<th>申诉单号</th>-->
                                        <th id="J-sp-serial5" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>    
                                        <!--<th>提现用户名</th>-->
                                        <th id="J-sp-drawname5" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提现用户名</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>
                                        <!--<th>提款金额</th>-->
                                        <th id="J-sp-drawmoney5" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款金额</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                    <li>
                                                        <div class="input-append">
                                                            <input id="J-input5-drawmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input2-drawmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th> 												
                                        <!--<th>提款时间</th>-->
                                        <th id="J-sp-userapply5-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">提款时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="4" class="input w-2" id="sp-input-userapply5-1"> - <input type="text" id="sp-input-userapply5-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>  
										<!-- <th>申诉发起时间</th> -->
										<th id="J-sp-userargue5-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">申诉发起时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-userargue5-1"> - <input type="text" id="sp-input-userargue5-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>												
                                        <!--<th>是否VIP</th>-->
                                        <th id="J-sp-isvip5" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">是否VIP</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                    <ul>
                                                        <li data-select-id="1"><a href="#">是</a></li>
                                                        <li data-select-id="0"><a href="#">否</a></li>                                                   
                                                    </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>                                   												
                                        <th>状态</th>
                                        <th>操作</th>
                                        <th>备注</th>
                                        <!-- {if "FUND_WITHDRAW_RISK_UNTREATED_PASS"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="UNTREATED_PASS" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="UNTREATED_PASS" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_UNTREATED_REFUSE"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="UNTREATED_REFUSE" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="UNTREATED_REFUSE" value="0"/>
                                <!-- {/if} -->
                                <!-- {if "FUND_WITHDRAW_RISK_UNTREATED_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
                                <input type="hidden" id="UNTREATED_REVIEW" value="1"/>
                                <!-- {else} -->
                                <input type="hidden" id="UNTREATED_REVIEW" value="0"/>
                                <!-- {/if} -->
                                </tr>
                                </thead>
                                <tbody id="showInfo5">
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <td colspan="22">
                                            <div id="Pagination5" class="pagination" ></div>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </li>
                    </ul>				

                </div>  
            </div>

        </div>
    </div>
</div>        
<div class="pop w-8" id="CheckDetailPromp" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv1"></i>
        <h3>查看詳情</h3>
    </div>
    <div class="bd">
        <ul class="ui-form ui-form-small">
            <li>         
                <span class="ui-info" id="sp-li-appealCStatus" name="appealtype"></span>
            </li>
            <li>
                <label class="ui-label w-auto">申诉单号：</label>            
                <span class="ui-info" id="sp-li-appealSid" name="appealsid"></span>
            </li>
            <li>
                <label class="ui-label w-auto">內容：</label>
                <span class="ui-info" id="sp-li-userName" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawTime" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawAmt" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawBank" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawNumber" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawCName" name="drawName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">回执单：</label>
                <span class="ui-info" id="sp-li-drawBack" name="drawNumber"></span>
            </li>
            <li>
                <label class="ui-label w-auto">客户提示：</label>
                <span class="ui-info" id="sp-li-appealTips" name="drawMoney"></span>
            </li>
            <li>
                <label class="ui-label w-auto">备注：</label>
                <div class="textarea w-5" style="margin-top: 20px;">
                    <textarea id="sp-li-txtRemark" maxlength="100" name="applyMemo" disabled="disabled"></textarea>
                </div>
                                             
            </li>
            <li>
                <div class="pop-btn ">
                    <input class="btn btn-important" name="closeIcoDiv1" id="submit1" value="确认"  style="width:80px;"/>
                
                </div>
            </li>
        </ul>

    </div>
</div>


<div class="pop w-8" id="AppealGo" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv2"></i>
        <h3>提现申诉 - 审核</h3>
    </div>
      <div class="bd"  >
        <ul class="ui-form ui-form-small">
            <li>         
                <span class="ui-info" id="sp-li-appealCStatus2" name="appealtype"></span>
            </li>
            <li>
                <label class="ui-label w-auto">申诉单号：</label>            
                <span class="ui-info" id="sp-li-appealSid2" name="appealsid"></span>
            </li>
            <li>
                <label class="ui-label w-auto">內容：</label>
                <span class="ui-info" id="sp-li-userName2" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawTime2" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawAmt2" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawBank2" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawNumber2" name="drawName"></span>
                <span class="ui-info" id="sp-li-drawCName2" name="drawName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">回执单：</label>
                <span class="ui-info" id="sp-li-drawBack2" name="drawNumber"></span>
            </li>
            <li style = "overflow-y:auto;overflow-auto;;height:190px">
                <hr/>
                <span class="radio-list w-6" id="sp-li-appealTips2-pass" >
                    <label class="w-13">客户提示-通过:</label>
                    <hr/>
                    <!-- {foreach from=$allTips['passTips'] key=key item=data} -->
                    <div style="word-break:break-all"><label class="label w-13"><input type="radio" name="appealTips2" eventType="{$data.tipsGroupb}" value="{$data.tipsContext}"/>{$data.tipsContext}</label></div>
                    <!-- {/foreach} -->
                </span>
                <hr/>
                <span class="radio-list w-6" id="sp-li-appealTips2-reject">
                    <label class="w-13">客户提示-不通过：</label>
                    <hr/>
                    <!-- {foreach from=$allTips['rejectTips'] key=key item=data} -->
                    <div style="word-break:break-all"><label class="label w-13"><input type="radio" name="appealTips2" eventType="{$data.tipsGroupb}" value="{$data.tipsContext}"/>{$data.tipsContext}</label></div>
                    <!-- {/foreach} -->
                </span>
                <hr/>
                
                <span class="radio-list w-6" id="sp-li-appealTips2-wait" >
                    <label class="w-13">客户提示-待定：</label>
               <hr/>
               <!-- {foreach from=$allTips['waitTips'] key=key item=data} -->
                    <div style="word-break:break-all"><label class="label w-13" ><input type="radio" name="appealTips2" eventType="{$data.tipsGroupb}" value="{$data.tipsContext}"/>{$data.tipsContext}</label></div>
                    <!-- {/foreach} -->
                </span>
            </li>
            <li>
                <label class="ui-label w-auto">备注：</label>
                <div class="textarea w-5" style="margin-top: 20px;">
                    <textarea id="sp-li-txtRemark2" maxlength="100" ></textarea>
                    <input type="hidden" id="sp-li-userId">
                </div>
                                             
            </li>
            <li>
                <div class="pop-btn ">
                    <input class="btn btn-important btn-disable" name="appealWait" id="appealWait" value="待定"  style="width:30px;"/>
                    <input class="btn btn-important btn-disable" name="appealStart" id="appealStart" value="审核通过"  style="width:60px;" disabled/>
                    <input class="btn btn-important btn-disable" name="appealStop" id="appealStop" value="审核不通过"  style="width:80px;" disabled/>
                </div>
            </li>
        </ul>

    </div>
</div>

<div class="pop w-8" id="ReviewOperDiv" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv3"></i>
        <h3>风险提现单 - 待复审</h3>
    </div>
    <div class="bd">
        <ul class="ui-form ui-form-small">
            <li>
                <label class="ui-label w-auto">提现银行：</label>
                <input type="hidden"  id="sp-li-drawbankID3" name="drawbankID" value=""/>
                <span class="ui-info" id="sp-li-drawbank3" name="drawbank"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现账户名：</label>
                <span class="ui-info" id="sp-li-drawName3" name="drawName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现卡号：</label>
                <span class="ui-info" id="sp-li-drawNumber3" name="drawNumber"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现金额：</label>
                <span class="ui-info" id="sp-li-drawMoney3" name="drawMoney"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现用户名：</label>
                <span class="ui-info" id="sp-li-drawUserName3" name="drawUserName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">风险类型：</label>
                <span class="ui-info" id="sp-li-drawType3" name="drawType"></span>
            </li>
            <li>
                <form id="upload_form3" action="/admin/Rechargemange/upload" method="post" enctype="multipart/form-data">
                    <label class="ui-label w-auto">附件：</label>
                    <fieldset style=" text-align:left; margin-left:35px">					
                        <input type="file" id="id3Card0" name="idCard0" value="添加附件1"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; ">
                    </fieldset>
                    <!--<input type="file" value="添加附件" class="file" accept="/frontend/upload/images">-->
                    <fieldset style=" text-align:left; margin-left:35px">					
                        <input type="file" id="id3Card1" name="idCard1" value="添加附件2"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>						
                    </fieldset>
                    <fieldset style=" text-align:left; margin-left:35px">					
                        <input type="file" id="id3Card2" name="idCard2" value="添加附件3"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>	
                        <input type="submit" name="submit" value="上传所有相片" id="submit3" />
                    </fieldset>
                    <!-- <fieldset style=" text-align:left; margin-left:35px"></fieldset>-->
                    <fieldset style=" text-align:left; margin-left:35px">
                        支持rar、jpg、png格式，大小不超过2M，最多可添加三条
                    </fieldset>
                </form>

            </li>
            <li>
                <div class="ui-des-c">
                    複審原因：
                    <input style=" text-align:left; margin-left:35px" type="radio" name="tips1" >提示1    *必選項<br>
                    <input style=" text-align:left; margin-left:35px" type="radio" name="tips1" >提示2 	<br>						
                    <input style=" text-align:left; margin-left:35px" type="radio" name="sex" >提示3    <br>
                    <br>
                    是否開啟申訴：	
                    <input  type="submit" value="開啟" style="width:80px;"/>
                    <input  type="submit" value="不開啟"  style="width:80px;"/>
                    <br>
                    以上信息會返回給用戶									
                </div>  

            </li>
            <li>
                <label class="ui-label w-auto">备注：</label>
                <div class="textarea w-5" style="margin-top: 20px;">
                    <textarea id="txtRemark3" maxlength="100" name="applyMemo"></textarea>
                </div>
                <span class="ui-text-prompt" name="spancount" style="margin-top: 20px;">0/100</span>    

            </li>
            <li>
                <div class="pop-btn ">
                    <input class="btn btn-important" id="btnSubmit3" value="提交复审" style="width:80px;"/>
                    <input class="btn" name="closeDiv3" value="取 消"  style="width:80px;"/>
                </div>
            </li>
        </ul>

    </div>
</div>     

  

<!--新增加审核通过 -->          
<div class="pop w-8" id="ThroughDiv2" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv4"></i>
        <h3>风险提现单 - 审核通过</h3>
    </div>
    <div class="bd">
        <ul class="ui-form">
            <li>
                <label class="ui-label w-auto">提现银行：</label>
                <input type="hidden"  id="sp-li-drawbankID4" name="drawbankID" value=""/>
                <span class="ui-info" id="sp-li-drawbank4" name="drawbank"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现账户名：</label>
                <span class="ui-info" id="sp-li-drawName4" name="drawName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现卡号：</label>
                <span class="ui-info" id="sp-li-drawNumber4" name="drawNumber"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现金额：</label>
                <span class="ui-info" id="sp-li-drawMoney4" name="drawMoney"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现用户名：</label>
                <span class="ui-info" id="sp-li-drawUserName4" name="drawUserName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">风险类型：</label>
                <span class="ui-info" id="sp-li-drawType4" name="drawType"></span>
                <input type="hidden" name="status" value="1"/>
            </li>
            <li>
                <div class="ui-des-c">
                    拒絕原因：
                    <input style=" text-align:left; margin-left:35px" type="radio" name="tips1" >提示1 <br>	
                    以上信息會返回給用戶									
                </div>  

            </li>
            <li>
                <label class="ui-label w-auto">备注：</label>
                <div class="textarea w-5">
                    <textarea id="txtRemark4" maxlength="100" name="applyMemo"></textarea>
                </div>
                <span class="ui-text-prompt" name="spancount">0</span>/100                                      

            </li>
            <li>
                <div class="pop-btn ">
                    <input class="btn btn-important" id="btnThroughDiv2" value="确认通过"  style="width:80px;"/>
                    <input class="btn" name="closeDiv4" value="取 消"  style="width:80px;"/>
                </div>
            </li>
        </ul>
    </div>
</div>


<div class="pop w-8" id="RefuseOperDiv2" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv5"></i>
        <h3>风险提现单 - 审核拒绝</h3>
    </div>
    <div class="bd">
        <ul class="ui-form">
            <li>
                <label class="ui-label w-auto">提现银行：</label>
                <input type="hidden"  id="sp-li-drawbankID5" name="drawbankID" value=""/>
                <span class="ui-info" id="sp-li-drawbank5" name="drawbank"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现账户名：</label>
                <span class="ui-info" id="sp-li-drawName5" name="drawName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现卡号：</label>
                <span class="ui-info" id="sp-li-drawNumber5" name="drawNumber"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现金额：</label>
                <span class="ui-info" id="sp-li-drawMoney5" name="drawMoney"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现用户名：</label>
                <span class="ui-info" id="sp-li-drawUserName5" name="drawUserName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">风险类型：</label>
                <span class="ui-info" id="sp-li-drawType5" name="drawType"></span>
            </li>
            <li>
                <div class="ui-des-c">
                    拒絕原因：
                    <input style=" text-align:left; margin-left:35px" type="radio" name="tips1" >提示1    *必選項<br>
                    <input style=" text-align:left; margin-left:35px" type="radio" name="tips1" >提示2 	<br>						
                    <input style=" text-align:left; margin-left:35px" type="radio" name="sex" >提示3    <br>
                    <br>
                    <br>
                    以上信息會返回給用戶									
                </div>  

            </li>
            <li>
                <label class="ui-label w-auto">备注：</label>
                <div class="textarea w-5">
                    <textarea id="txtRemark5" maxlength="100" name="applyMemo"></textarea>
                </div>
                <span class="ui-text-prompt" name="spancount">0/100</span>      
            </li>
            <li>
                <div class="pop-btn ">
                    <input class="btn btn-important" id="btnRefuseOperDiv2" value="确认拒绝"  style="width:80px;"/>
                    <input class="btn" name="closeDiv5" value="取 消"  style="width:80px;"/>
                </div>
            </li>
        </ul>

    </div>
</div>


<div class="pop w-8" id="RefuseOperDiv3" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv6"></i>
        <h3>风险提现单 - 退款</h3>
    </div>
    <div class="bd">
        <ul class="ui-form ui-form-small">
            <li>
                <label class="ui-label w-auto">提现银行：</label>
                <input type="hidden"  id="sp-li-drawbankID6" name="drawbankID" value=""/>
                <span class="ui-info" id="sp-li-drawbank6" name="drawbank"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现账户名：</label>
                <span class="ui-info" id="sp-li-drawName6" name="drawName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现卡号：</label>
                <span class="ui-info" id="sp-li-drawNumber6" name="drawNumber"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现金额：</label>
                <span class="ui-info" id="sp-li-drawMoney6" name="drawMoney"></span>
            </li>
            <li>
                <label class="ui-label w-auto">提现用户名：</label>
                <span class="ui-info" id="sp-li-drawUserName6" name="drawUserName"></span>
            </li>
            <li>
                <label class="ui-label w-auto">风险类型：</label>
                <span class="ui-info" id="sp-li-drawType6" name="drawType"></span>
                <input type="hidden" name="status" value="1"/>
            </li>
            <li>
                <form id="upload_form4" action="/admin/Rechargemange/upload" method="post" enctype="multipart/form-data">
                    <label class="ui-label w-auto">附件：</label>
                    <fieldset style=" text-align:left; margin-left:35px">					
                        <input type="file" id="id4Card0" name="idCard0" value="添加附件1"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; ">
                    </fieldset>
                    <!--<input type="file" value="添加附件" class="file" accept="/frontend/upload/images">-->
                    <fieldset style=" text-align:left; margin-left:35px">					
                        <input type="file" id="id4Card1" name="idCard1" value="添加附件2"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>						
                    </fieldset>
                    <fieldset style=" text-align:left; margin-left:35px">					
                        <input type="file" id="id4Card2" name="idCard2" value="添加附件3"  style=" border-color:#CCCCCC;border-style:solid;border-width: 1px; "/>	
                        <input type="submit" name="submit" value="上传所有相片" id="submit4" />
                    </fieldset>
                    <!-- <fieldset style=" text-align:left; margin-left:35px"></fieldset>-->
                    <fieldset style=" text-align:left; margin-left:35px">
                        支持rar、jpg、png格式，大小不超过2M，最多可添加三条
                    </fieldset>
                </form>
                <!--<label class="ui-label w-auto">附件：</label>
                <input type="file" class="file" value="添加附件">
                <span class="ui-text-prompt">支持rar、jpg、png格式，大小不超过2M，最多可添加三条</span>-->

            </li>
            <li>
                <label class="ui-label w-auto">备注：</label>
                <div class="textarea w-5" style="margin-top: 20px;">
                    <textarea id="txtRemark6" maxlength="100" name="applyMemo"></textarea>
                </div>
                <span class="ui-text-prompt" name="spancount" style="margin-top: 20px;">0/100</span>                                     

            </li>
            <li>
                <div class="pop-btn ">
                    <input class="btn btn-important" id="btnRefuseOperDiv3" value="确认退款"  style="width:80px;"/>
                    <input class="btn" name="closeDiv6" value="取 消"  style="width:80px;"/>
                </div>
            </li>
        </ul>

    </div>
</div>

<!--獲得狀態 -->
<div class="pop w-8" id="CheckWithDrawStatus" style="display:none;">
    <div class="hd">
        <i class="close" name="closeIcoDiv7"></i>
        <h3 align="center">状态详情</h3>
    </div>
    <div class="bd">
        <ul class="ui-form ui-form-small">
            <li>
                <input type="hidden" id="sp-li-drawstatus7" />
                <div align="center"  id="sp-li-drawbank7" name="drawbank"></div>
            </li>
            <li>
                <div  align="center"  id="sp-li-drawName7" name="drawName"></div>
            </li>

            <li>
                <div class="pop-btn ">
                    <input class="btn" name="closeIcoDiv7" value="确定"  style="width:80px;"/>
                </div>
            </li>
        </ul>

    </div>
</div>


<input type="hidden" id="pagetype" value="1"/>
{include file='/admin/script-base.tpl'}
<script type="text/javascript" src="{$path_js}/js/phoenix.Verification.js"></script>
<script type="text/javascript" src="{$path_js}/js/jquery.uploadprogress.0.3.js"></script>
{literal}
    <script>
        var msg = new phoenix.Message();
        jQuery(function () {
            showTitle(true);
            jQuery('#upload_form').uploadProgress({
                progressURL: '/admin/Rechargemange/upload',
                displayFields: ['kb_uploaded', 'kb_average', 'est_sec'],
                success: function (data) {
                    if ($('#idCard0').val() != '' || $('#idCard1').val() != '' || $('#idCard2').val() != '') {
                        if (data.displayFields[0]) {
                            jQuery('input[type=submit]', this).val('上传成功');
                            jQuery('input[type=submit]', this).attr("disabled", "disabled");
                        } else
                        {
                            jQuery('input[type=submit]', this).val('上传失败');
                            jQuery('input[type=submit]', this).removeAttr("disabled");
                        }

                    } else
                    {
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请选择要上传扫描件</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                            }
                        });
                        return false;
                    }

                }
            });
            jQuery('#upload_form2').uploadProgress({
                progressURL: '/admin/Rechargemange/upload',
                displayFields: ['kb_uploaded', 'kb_average', 'est_sec'],
                success: function (data) {
                    if ($('#id2Card0').val() != '' || $('#id2Card1').val() != '' || $('#id2Card2').val() != '') {
                        if (data.displayFields[0]) {
                            jQuery('#submit2', this).val('上传成功');

                            jQuery('#submit2', this).attr("disabled", "disabled");
                        } else
                        {
                            jQuery('#submit2', this).val('上传失败');
                            jQuery('#submit2', this).removeAttr("disabled");
                        }

                    } else
                    {
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请选择要上传扫描件</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                            }
                        });
                        return false;
                    }

                }
            });
            jQuery('#upload_form3').uploadProgress({
                progressURL: '/admin/Rechargemange/upload',
                displayFields: ['kb_uploaded', 'kb_average', 'est_sec'],
                success: function (data) {
                    if ($('#id3Card0').val() != '' || $('#id3Card1').val() != '' || $('#id3Card2').val() != '') {
                        if (data.displayFields[0]) {
                            jQuery('#submit3', this).val('上传成功');
                            jQuery('#submit3', this).attr("disabled", "disabled");
                        } else
                        {
                            jQuery('#submit3', this).val('上传失败');
                            jQuery('#submit3', this).removeAttr("disabled");
                        }

                    } else
                    {
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请选择要上传扫描件</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                            }
                        });
                        return false;
                    }

                }
            });

            jQuery('#upload_form4').uploadProgress({
                progressURL: '/admin/Rechargemange/upload',
                displayFields: ['kb_uploaded', 'kb_average', 'est_sec'],
                success: function (data) {
                    if ($('#id4Card0').val() != '' || $('#id4Card1').val() != '' || $('#id4Card2').val() != '') {
                        if (data.displayFields[0]) {
                            jQuery('#submit4', this).val('上传成功');
                            jQuery('#submit4', this).attr("disabled", "disabled");
                        } else
                        {
                            jQuery('#submit4', this).val('上传失败');
                            jQuery('#submit4', this).removeAttr("disabled");
                        }

                    } else
                    {
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请选择要上传扫描件</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                            }
                        });
                        return false;
                    }

                }
            });

            jQuery('#upload_form8').uploadProgress({
                progressURL: '/admin/Rechargemange/upload',
                displayFields: ['kb_uploaded', 'kb_average', 'est_sec'],
                success: function (data) {
                    if ($('#id8Card0').val() != '' || $('#id8Card1').val() != '' || $('#id8Card2').val() != '') {
                        if (data.displayFields[0]) {
                            jQuery('#submit8', this).val('上传成功');
                            jQuery('#submit8', this).attr("disabled", "disabled");
                        } else
                        {
                            jQuery('#submit8', this).val('上传失败');
                            jQuery('#submit8', this).removeAttr("disabled");
                        }
                    } else
                    {
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请选择要上传扫描件</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                            }
                        });
                        return false;
                    }

                }
            });
        });
    </script>
    <script>

        (function () {

            var sindex = 0, sindex = phoenix.util.getParam("tabIndex");
            sindex = sindex == null ? 1 : sindex;
            var isShowCell = false, typeStaus = 0, isLock = true,
                    group = new phoenix.SuperSearchGroup(),
                    option = {zIndex: 500},
                    box1 = new LightBox("CheckDetailPromp", option),
                    box2 = new LightBox("AppealGo", option),
                    
                    
                    box3 = new LightBox("ReviewOperDiv", option),
                    box4 = new LightBox("ThroughDiv2", option),
                    box5 = new LightBox("RefuseOperDiv2", option),
                    box6 = new LightBox("RefuseOperDiv3", option),
                    box7 = new LightBox("CheckWithDrawStatus", option);
                
            new phoenix.Tab({triggers: '.ui-tab-title2 li', panels: '.ui-tab-content',
                eventType: 'click', currPanelClass: 'ui-tab-content-current', index: sindex});

            //一、二级菜单选中样式加载	
            selectMenu('Menufunds', 'DrawArgueList');

            //获取select类型值
            var getSelectValue = function () {
                var me = this, li = me.dom.find('li.current'), v = '', result = {};
                if (li.size() > 0) {
                    v = li.eq(0).attr('data-select-id');
                    result[me.name] = v;
                } else {
                    //初始化给空
                    result[me.name] = '';
                }
                return result;
            };
            //获取两个input值
            var getTowInputValue = function () {
                var me = this, ipts = me.dom.find('input[type="text"]'), name = me.name, result = {};
                result[name + '1'] = ipts[0].value;
                result[name + '2'] = ipts[1].value;
                return result;
            };
            //允许输入数字和小数
            var allowNumber = function () {
                var me = this, v = me.value, index;
                me.value = v = v.replace(/^\.$/g, '');
                index = v.indexOf('.');
                if (index > 0) {
                    me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
                }
                me.value = v = v.replace(/[^\d|^\.]/g, '');
                me.value = v = v.replace(/^00/g, '0');
                if (v.split('.').length > 2) {
                    arguments.callee.call(me);
                }
            };

            if (sindex == 1)
            {
                UntreatedData();
            } else if (sindex == 2)
            {
                PendingReviewData();
            } else if (sindex == 3)
            {
                ProcessingData();
            } else if (sindex == 4)
            {
            	WaitData();
                //CompletedData();
            } else
            {
                AllData();
            }
            //---------------------------------------------------风险提现(全部)数据处理开始-------------------------------------------------------


            //全部数据查询
            $('#AllData').click(function () {
                typeStaus = 0;
                if (isLock)
                {
                    AllData();
                }
            });
            //未处理查询
            $('#UntreatedData').click(function () {
                typeStaus = 1;
                if (isLock)
                {
                    UntreatedData();
                }
            });
            //審核通過查询
            $('#PendingReviewData').click(function () {
                typeStaus = 2;
                if (isLock)
                {
                    PendingReviewData();
                }
            });
            //審核未通過查询
            $('#ProcessingData').click(function () {
                typeStaus = 3;
                if (isLock)
                {
                    ProcessingData();
                }
            });
            //待定查询
            $('#WaitData').click(function () {
                typeStaus = 4;
                if (isLock)
                {
                    WaitData();
                }
            });
            //已完查询 -- 貌似沒用到
            $('#CompletedData').click(function () {
                typeStaus = 4;
                if (isLock)
                {
                    CompletedData();
                }
            });

            function AllData() {

                //申请单号
                var drawserial = new phoenix.SuperSearch({
                    name: 'drawserial',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-serial',
                    group: group
                });

                //提现卡号
                var drawnumber = new phoenix.SuperSearch({
                    name: 'drawnumber',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawnumber',
                    group: group
                });

                //提现金额
                var drawmoney = new phoenix.SuperSearch({
                    name: 'drawmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawmoney',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                $('#J-input-drawmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input-drawmoney2').keyup(allowNumber).blur(allowNumber);

                //提现用户名
                var drawname = new phoenix.SuperSearch({
                    name: 'drawname',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawname',
                    group: group
                });

                //是否VIP
                var isvip = new phoenix.SuperSearch({
                    name: 'isvip',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-isvip',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });
                //用户申请提现时间
                var userApply = new phoenix.SuperSearch({
                    name: 'UserApplyTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userapply-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userapplyTime1 = $('#sp-input-userapply-1'), userapplyTime2 = $('#sp-input-userapply-2'), userapplyDt1, userapplyDt2;
                userapplyTime1.click(function () {
                    userapplyDt1 = new phoenix.DatePicker({
                        input: userapplyTime1,
                        isLockInputType: false
                    });
                    userapplyDt1.show();
                    userapplyDt1.addEvent('afterSetValue',
                            function () {
                                userapplyTime2.focus();
                                userapplyTime2.click();
                            })
                });
                userApply.addEvent('afterFocus',
                        function () {
                            userapplyTime1.click()
                        });
                userapplyTime2.click(function () {
                    userapplyDt2 = new phoenix.DatePicker({
                        input: userapplyTime2,
                        isLockInputType: false
                    }).show();
                });
                userApply.addEvent('afterBlur', function () {
                    if (userapplyDt1) {
                        userapplyDt1.hide();
                    }
                    if (userapplyDt2) {
                        userapplyDt2.hide();
                    }
                });
				
				
				//申诉发起时间
                var userArgue = new phoenix.SuperSearch({
                    name: 'UserArgueTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userargue-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userargueTime1 = $('#sp-input-userargue-1'), userargueTime2 = $('#sp-input-userargue-2'), userargueDt1, userargueDt2;
                userargueTime1.click(function () {
                    userargueDt1 = new phoenix.DatePicker({
                        input: userargueTime1,
                        isLockInputType: false
                    });
                    userargueDt1.show();
                    userargueDt1.addEvent('afterSetValue',
                            function () {
                                userargueTime2.focus();
                                userargueTime2.click();
                            })
                });
                userArgue.addEvent('afterFocus',
                        function () {
                            userargueTime1.click()
                        });
                userargueTime2.click(function () {
                    userargueDt2 = new phoenix.DatePicker({
                        input: userargueTime2,
                        isLockInputType: false
                    }).show();
                });
                userArgue.addEvent('afterBlur', function () {
                    if (userargueDt1) {
                        userargueDt1.hide();
                    }
                    if (userargueDt2) {
                        userargueDt2.hide();
                    }
                });
                //风险类型
                var isvip = new phoenix.SuperSearch({
                    name: 'riskstype',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-riskstype',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });

                //一审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'AuditOneAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-admin',
                    group: group
                });


                var AuditOneTime1 = $('#sp-input-AuditOneBegin-1'), AuditOneTime2 = $('#sp-input-AuditOneBegin-2'), AuditOneDt1, AuditOneDt2;
                AuditOneTime1.click(function () {
                    AuditOneDt1 = new phoenix.DatePicker({
                        input: AuditOneTime1,
                        isLockInputType: false
                    });
                    AuditOneDt1.show();
                    AuditOneDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneTime2.focus();
                                AuditOneTime2.click();
                            })
                });

                AuditOneTime2.click(function () {
                    AuditOneDt2 = new phoenix.DatePicker({
                        input: AuditOneTime2,
                        isLockInputType: false
                    }).show();
                });



                var AuditOneEndTime1 = $('#sp-input-AuditOneEnd-1'), AuditOneEndTime2 = $('#sp-input-AuditOneEnd-2'), AuditOneEndDt1, AuditOneEndDt2;
                AuditOneEndTime1.click(function () {
                    AuditOneEndDt1 = new phoenix.DatePicker({
                        input: AuditOneEndTime1,
                        isLockInputType: false
                    });
                    AuditOneEndDt1.show();
                    AuditOneEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneEndTime2.focus();
                                AuditOneEndTime2.click();
                            })
                });

                AuditOneEndTime2.click(function () {
                    AuditOneEndDt2 = new phoenix.DatePicker({
                        input: AuditOneEndTime2,
                        isLockInputType: false
                    }).show();
                });



                var AuditReviewEndTime1 = $('#sp-input-reviewEnd-1'), AuditReviewEndTime2 = $('#sp-input-reviewEnd-2'), AuditReviewEndDt1, AuditReviewEndDt2;
                AuditReviewEndTime1.click(function () {
                    AuditReviewEndDt1 = new phoenix.DatePicker({
                        input: AuditReviewEndTime1,
                        isLockInputType: false
                    });
                    AuditReviewEndDt1.show();
                    AuditReviewEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditReviewEndTime2.focus();
                                AuditReviewEndTime2.click();
                            })
                });

                AuditReviewEndTime2.click(function () {
                    AuditReviewEndDt2 = new phoenix.DatePicker({
                        input: AuditReviewEndTime2,
                        isLockInputType: false
                    }).show();
                });

                //复审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'ReviewAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-reviewAdmin',
                    group: group
                });

                //mow返回结果时间	 98

                var mowReturnTime1 = $('#sp-input-mowReturn-1'), mowReturnTime2 = $('#sp-input-mowReturn-2'), mowReturnDt1, mowReturnDt2;
                mowReturnTime1.click(function () {
                    mowReturnDt1 = new phoenix.DatePicker({
                        input: mowReturnTime1,
                        isLockInputType: false
                    });
                    mowReturnDt1.show();
                    mowReturnDt1.addEvent('afterSetValue',
                            function () {
                                mowReturnTime2.focus();
                                mowReturnTime2.click();
                            })
                });

                mowReturnTime2.click(function () {
                    mowReturnTime2 = new phoenix.DatePicker({
                        input: mowReturnTime2,
                        isLockInputType: false
                    }).show();
                });

                //状态
                var status = new phoenix.SuperSearch({
                    name: 'status',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-status',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });
                var Title = new phoenix.SuperSearch({
                    name: 'Title',
                    group: group,
                    expands: {getValue: function () {
                            return 0;
                        }}
                });

                SelectByWhereInfo("0");
                group.removeEvent('dataChange');
                //提交数据		
                group.addEvent('dataChange', function () {
                    SelectByWhereInfo("0");
                });
                //每页记录数更改事件
                $("#per_page").change(function () {
                    SelectByWhereInfo("0");
                });
                function SelectByWhereInfo(pages) {
                    //当前页数据行数
                    var per_page_number = parseInt($("#per_page").val());
                    //放入group对象中(当前页)		
                    var page = new phoenix.SuperSearch({
                        name: 'page',
                        group: group,
                        expands: {getValue: function () {
                                return pages;
                            }}
                    });
                    //放入group对象中(当前页行数)	
                    var perPageNum = new phoenix.SuperSearch({
                        name: 'perPageNum',
                        group: group,
                        expands: {getValue: function () {
                                return per_page_number;
                            }}
                    });

                    var formData = group.getFormData(), mask = phoenix.Mask.getInstance();
                    $("#J-table-data>tbody").html("");
                    $("#Pagination").hide();
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=loadappeal',
                        dataType: 'json',
                        method: 'post',
                        data: formData,
                        cache: false,
                        beforeSend: function () {
                            isLock = false;
                            TableStyle("showInfo", 19, 1, "查询中");
                        },
                        success: function (data) {
                            //debugger99
                            if (data.text.length > 0) {
                                $("#Pagination").show();
                                var resultAll = eval(data);
                                var re = resultAll.text;
                                var recordNum = 0;
                                recordNum = resultAll.count[0].recordNum;
                                //分页回调				 
                                $("#Pagination").pagination(recordNum, {
                                    num_edge_entries: 2,
                                    num_display_entries: 8,
                                    current_page: pages,
                                    items_per_page: per_page_number,
                                    callback: SelectByWhereInfo
                                });
                                var html = "";
                                //数据填充
                                $.each(re, function (i) {
                                    if (re[i].riskTypeNum == '3') {
                                        if (re[i].withdrawAmt1 >= 50000 && re[i].withdrawAmt1 < 100000) {
                                            html += "<tr style='color:blue;'>";
                                        } else if (re[i].withdrawAmt1 >= 100000) {
                                            html += "<tr style='color:red;'>";
                                        } else {
                                            html += "<tr>";
                                        }
                                    } else {
                                        html += "<tr>";
                                    }
                                    html += "<td>" + re[i].appealSn + "</td>";    //     
                                    html += "<td>" + re[i].userName + "</td>";
                                    html += "<td>" + re[i].withdrawAmt + "</td>";
                                    html += "<td>" + re[i].withdrawTime + "</td>";
									html += "<td>" + re[i].argueTime + "</td>";
                                    html += "<td>" + re[i].vipLvl + "</td>";
                                    html += "<td>" + re[i].appealStatus + "</td>";
                                    //  if($("#WITHDRAW_LOOKDETAIL").val()=='1'){
                                    html += "<td>" + "<a class='btn btn-small' href='javascript:void(0);' name='checkDetail' style='position:initial'>查看</a>" + "</td>";
                                    // }

                                    html += "<td>" + re[i].appealMemo + "</td>";
                                    html += "<td>" + re[i].appealAcct + "</td>";
                                    html += "<td>" + re[i].appealTime + "</td>";
                                });
                                $("#J-table-data>tbody").html(html);
                            } else {
                                $("#Pagination").hide();
                                TableStyle("J-table-data>tbody", 19, 2, "没有相应数据");
                            }
                        },
                        complete: function ()
                        {
                            isLock = true;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            TableStyle("J-table-data>tbody", 19, 2, "数据异常");
                        }
                    });
                }
            }
            function GetPostText(id)
            {
                var sName = id + " *";
                var sText = "";
                var sPost = "";
                $(sName).each(function (index, element) {
                    if ($(this).is("span"))
                    {

                        sText = $(this).attr("name") + "=" + $.trim($(this).text()) + "&";
                        sPost += sText;
                    }
                    if ($(this).is("textarea") || $(this).is("input"))
                    {
                        sText = $(this).attr("name") + "=" + $.trim($(this).val()) + "&";
                        sPost += sText;
                    }
                });
                return sPost;
            }
        

            $("#btnThroughDiv2").click(function () {
                if ($.trim($("#txtRemark4").val()) == "")
                {
                    $("#txtRemark4").focus();
                    msg.show({
                        mask: true,
                        content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请输入备注信息</h3><div style="height:30px;line-height:30px;"></div>',
                        confirmIsShow: 'true',
                        confirmText: '确定',
                        confirmFun: function () {
                            msg.hide();
                        }
                    });
                    return false;
                }
                var button = $("#btnThroughDiv2");
                button.val("确认通过");
                button.removeAttr("disabled", "disabled");
                var sdata = GetPostText("#ThroughDiv2") + "status=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=wd2',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    beforeSend: function () {
                        //禁用发送								
                        var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'], i = 0;
                        interval = setInterval(function () {
                            button.val(list[i]);
                            i += 1;
                            if (i >= list.length) {
                                i = 0;
                            }
                        }, 300);
                        button.attr("disabled", "disabled");
                        $('[name="closeDiv4"]').attr("disabled", "disabled");
                    },
                    success: function (data) {
                        if (data['status'] == 'ok') {
                            var des = "待复审通过成功";
                        } else {
                            var des = data['data'];
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data['status'] == 'ok') {
                                    box4.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            }
                        });
                    },
                    complete: function () {
                        clearInterval(interval);
                        button.val("确认通过");
                        button.removeAttr("disabled", "disabled");
                        $('[name="closeDiv4"]').removeAttr("disabled", "disabled");
                    }
                });
            }
            );
            $("#btnRefuseOperDiv2").click(function () {
                if ($.trim($("#txtRemark5").val()) == "")
                {
                    $("#txtRemark5").focus();
                    msg.show({
                        mask: true,
                        content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请输入备注信息</h3><div style="height:30px;line-height:30px;"></div>',
                        confirmIsShow: 'true',
                        confirmText: '确定',
                        confirmFun: function () {
                            msg.hide();
                        }
                    });
                    return false;
                }
                var button = $("#btnRefuseOperDiv2");
                button.val("确认拒绝");
                button.removeAttr("disabled", "disabled");
                var sdata = GetPostText("#RefuseOperDiv2") + "status=3";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=wd2',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    beforeSend: function () {
                        //禁用发送								
                        var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'], i = 0;
                        interval = setInterval(function () {
                            button.val(list[i]);
                            i += 1;
                            if (i >= list.length) {
                                i = 0;
                            }
                        }, 300);
                        button.attr("disabled", "disabled");
                        $('[name="closeDiv5"]').attr("disabled", "disabled");
                    },
                    success: function (data) {
                        if (data['status'] == 'ok') {
                            var des = "待复审审核拒绝成功";
                        } else {
                            var des = data['data'];
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data['status'] == 'ok') {
                                    box5.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            }
                        });
                    },
                    complete: function () {
                        clearInterval(interval);
                        button.val("确认拒绝");
                        button.removeAttr("disabled", "disabled");
                        $('[name="closeDiv5"]').removeAttr("disabled", "disabled");
                    }
                });
            }



            );

            $("#btnRefuseOperDiv3").click(function () {
                if ($.trim($("#txtRemark6").val()) == "")
                {
                    $("#txtRemark6").focus();
                    msg.show({
                        mask: true,
                        content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请输入备注信息</h3><div style="height:30px;line-height:30px;"></div>',
                        confirmIsShow: 'true',
                        confirmText: '确定',
                        confirmFun: function () {
                            msg.hide();
                        }
                    });
                    return false;
                }
                var button = $("#btnRefuseOperDiv3");
                button.val("确认退款");
                button.removeAttr("disabled", "disabled");
                var sdata = GetPostText("#RefuseOperDiv3") + "status=3";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=wd3',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    beforeSend: function () {
                        //禁用发送								
                        var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'], i = 0;
                        interval = setInterval(function () {
                            button.val(list[i]);
                            i += 1;
                            if (i >= list.length) {
                                i = 0;
                            }
                        }, 300);
                        button.attr("disabled", "disabled");
                        $('[name="closeDiv6"]').attr("disabled", "disabled");
                    },
                    success: function (data) {
                        if (data['status'] == 'ok') {
                            var des = "退款成功";
                        } else {
                            var des = data['data'];
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data['status'] == 'ok') {
                                    box6.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=3";
                                }
                            }
                        });
                    },
                    complete: function () {
                        clearInterval(interval);
                        button.val("确认退款");
                        button.removeAttr("disabled", "disabled");
                        $('[name="closeDiv6"]').removeAttr("disabled", "disabled");
                    }
                });
            }

            );

            //---------------------------------------------------(全部)数据处理结束------------------------------------------------------
            //---------------------------------------------------(未处理)数据处理开始------------------------------------------------------
            function UntreatedData() {
                //申訴单号
                var drawserial = new phoenix.SuperSearch({
                    name: 'drawserial',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-serial2',
                    group: group
                });

                //提现用户名
                var drawname = new phoenix.SuperSearch({
                    name: 'drawname',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawname2',
                    group: group
                });

                //提款金额
                var drawmoney = new phoenix.SuperSearch({
                    name: 'drawmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawmoney2',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });

                $('#J-input2-drawmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input2-drawmoney2').keyup(allowNumber).blur(allowNumber);

                //提现时间
                var userApply = new phoenix.SuperSearch({
                    name: 'UserApplyTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userapply2-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userapplyTime1 = $('#sp-input-userapply2-1'), userapplyTime2 = $('#sp-input-userapply2-2'), userapplyDt1, userapplyDt2;
                userapplyTime1.click(function () {
                    userapplyDt1 = new phoenix.DatePicker({
                        input: userapplyTime1,
                        isLockInputType: false
                    });
                    userapplyDt1.show();
                    userapplyDt1.addEvent('afterSetValue',
                            function () {
                                userapplyTime2.focus();
                                userapplyTime2.click();
                            })
                });
                userApply.addEvent('afterFocus',
                        function () {
                            userapplyTime1.click()
                        });
                userapplyTime2.click(function () {
                    userapplyDt2 = new phoenix.DatePicker({
                        input: userapplyTime2,
                        isLockInputType: false
                    }).show();
                });
                userApply.addEvent('afterBlur', function () {
                    if (userapplyDt1) {
                        userapplyDt1.hide();
                    }
                    if (userapplyDt2) {
                        userapplyDt2.hide();
                    }
                });
				//申诉发起时间
                var userArgue = new phoenix.SuperSearch({
                    name: 'UserArgueTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userargue2-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userargueTime1 = $('#sp-input-userargue2-1'), userargueTime2 = $('#sp-input-userargue2-2'), userargueDt1, userargueDt2;
                userargueTime1.click(function () {
                    userargueDt1 = new phoenix.DatePicker({
                        input: userargueTime1,
                        isLockInputType: false
                    });
                    userargueDt1.show();
                    userargueDt1.addEvent('afterSetValue',
                            function () {
                                userargueTime2.focus();
                                userargueTime2.click();
                            })
                });
                userArgue.addEvent('afterFocus',
                        function () {
                            userargueTime1.click()
                        });
                userargueTime2.click(function () {
                    userargueDt2 = new phoenix.DatePicker({
                        input: userargueTime2,
                        isLockInputType: false
                    }).show();
                });
                userArgue.addEvent('afterBlur', function () {
                    if (userargueDt1) {
                        userargueDt1.hide();
                    }
                    if (userargueDt2) {
                        userargueDt2.hide();
                    }
                });

                //是否VIP
                var isvip = new phoenix.SuperSearch({
                    name: 'isvip',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-isvip2',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });

                var Title = new phoenix.SuperSearch({
                    name: 'Title',
                    group: group,
                    expands: {getValue: function () {
                            return 1;
                        }}
                });

                SelectByWhereInfo("0");
                //提交数据
                var dataArea = $('#showInfo2');
                group.removeEvent('dataChange');
                group.addEvent('dataChange', function () {
                    SelectByWhereInfo("0");
                });
                //每页记录数更改事件
                $("#per_page").change(function () {
                    SelectByWhereInfo("0");
                });
                function SelectByWhereInfo(pages) {
                    //当前页数据行数
                    var per_page_number = parseInt($("#per_page").val());
                    //放入group对象中(当前页)		
                    var page = new phoenix.SuperSearch({
                        name: 'page',
                        group: group,
                        expands: {getValue: function () {
                                return pages;
                            }}
                    });
                    //放入group对象中(当前页行数)	
                    var perPageNum = new phoenix.SuperSearch({
                        name: 'perPageNum',
                        group: group,
                        expands: {getValue: function () {
                                return per_page_number;
                            }}
                    });
                    //99
                    var formData = group.getFormData(), mask = phoenix.Mask.getInstance();
                    $("#J-table-data2>tbody").html("");
                    $("#Pagination2").hide();
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=loadappeal',
                        dataType: 'json',
                        method: 'post',
                        data: formData,
                        beforeSend: function () {
                            isLock = false;
                            TableStyle("showInfo2", 19, 1, "查询中");
                        },
                        success: function (data) {
                            if (data.text.length > 0) {
                                $("#Pagination2").show();
                                var resultAll = eval(data);
                                var re = resultAll.text;
                                var recordNum = 0;
                                recordNum = resultAll.count[0].recordNum;
                                //分页回调				 
                                $("#Pagination2").pagination(recordNum, {
                                    num_edge_entries: 2,
                                    num_display_entries: 8,
                                    current_page: pages,
                                    items_per_page: per_page_number,
                                    callback: SelectByWhereInfo
                                });
                                var html = "";
                                //数据填充
                                $.each(re, function (i) {
                                    if (re[i].riskTypeNum == '3') {
                                    
                                        if (re[i].withdrawAmt1 >= 50000 && re[i].withdrawAmt1 < 100000) {
                                            html += "<tr style='color:blue;'>";
                                        } else if (re[i].withdrawAmt1 >= 100000) {
                                            html += "<tr style='color:red;'>";
                                        } else {
                                            html += "<tr>";
                                        }
                                    } else {
                                        html += "<tr>";
                                    }
                                    html += "<td>" + re[i].appealSn + "</td>";    //     
                                    html += "<td>" + re[i].userName + "</td>";
                                    html += "<td>" + re[i].withdrawAmt + "</td>";
                                    html += "<td>" + re[i].withdrawTime + "</td>";
									html += "<td>" + re[i].argueTime + "</td>";
                                    html += "<td>" + re[i].vipLvl + "</td>";
                                    html += "<td>" + re[i].appealStatus + "</td>";
									
                                    if(re[i].isCheck){
                                    	html += "<td>" + re[i].nowCheckUser+'操作中...'+ "</td>";
                                    }else{
                                        //  if($("#WITHDRAW_LOOKDETAIL").val()=='1'){
                                        html += "<td>" + "<a class='btn btn-small' href='javascript:void(0);' name='appealOK' style='position:initial'>审核</a>" + "</td>";
                                        // }
                                    }

                                    html += "<td>" + re[i].appealMemo + "</td>";

                                });
                                $("#J-table-data2>tbody").html(html);
                            } else {
                                $("#Pagination2").hide();
                                TableStyle("J-table-data2>tbody", 19, 2, "没有相应数据");

                            }
                        },
                        complete: function ()
                        {
                            isLock = true;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            TableStyle("J-table-data2>tbody", 19, 2, "数据异常");
                        }
                    });
                }
            }
            $("#btnSubmit2").click(function () {
                if ($.trim($("#txtRemark2").val()) == "")
                {
                    $("#txtRemark2").focus();
                    msg.show({
                        mask: true,
                        content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请输入备注信息</h3><div style="height:30px;line-height:30px;"></div>',
                        confirmIsShow: 'true',
                        confirmText: '确定',
                        confirmFun: function () {
                            msg.hide();
                        }
                    });
                    return false;
                }
                var button = $("#btnSubmit2");
                button.val("确认拒绝");
                button.removeAttr("disabled", "disabled");
                var sdata = GetPostText("#RefuseOperDiv") + "status=3";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=wd',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    beforeSend: function () {
                        //禁用发送								
                        var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'], i = 0;
                        interval = setInterval(function () {
                            button.val(list[i]);
                            i += 1;
                            if (i >= list.length) {
                                i = 0;
                            }
                        }, 300);
                        button.attr("disabled", "disabled");
                        $('[name="closeDiv2"]').attr("disabled", "disabled");
                    },
                    success: function (data) {
                        if (data['status'] == 'ok') {
                            var des = "审核拒绝成功";
                        } else {
                            var des = data['data'];
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data['status'] == 'ok') {
                                    box2.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                    $('#appealWait').show();
                                    $('#sp-li-appealTips2-wait').show();
                                }
                            }
                        });
                    },
                    complete: function () {
                        clearInterval(interval);
                        button.val("确认拒绝");
                        button.removeAttr("disabled", "disabled");
                        $('[name="closeDiv2"]').removeAttr("disabled", "disabled");
                    }
                });
            }
            );


            //---------------------------------------------------(未处理)数据处理结束------------------------------------------------------
            //---------------------------------------------------(审核通過)审核通過处理开始---------------------------------------------------
            function PendingReviewData() {
                //申请单号
                var drawserial = new phoenix.SuperSearch({
                    name: 'drawserial',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-serial3',
                    group: group
                });

                //提现卡号
                var drawnumber = new phoenix.SuperSearch({
                    name: 'drawnumber',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawnumber3',
                    group: group
                });

                //提现金额
                var drawmoney = new phoenix.SuperSearch({
                    name: 'drawmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawmoney3',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                $('#J-input3-drawmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input3-drawmoney2').keyup(allowNumber).blur(allowNumber);

                //提现用户名
                var drawname = new phoenix.SuperSearch({
                    name: 'drawname',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawname3',
                    group: group
                });

                //是否VIP
                var isvip = new phoenix.SuperSearch({
                    name: 'isvip',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-isvip3',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });
                //用户申请提现时间
                var userApply = new phoenix.SuperSearch({
                    name: 'UserApplyTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userapply3-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userapplyTime1 = $('#sp-input-userapply3-1'), userapplyTime2 = $('#sp-input-userapply3-2'), userapplyDt1, userapplyDt2;
                userapplyTime1.click(function () {
                    userapplyDt1 = new phoenix.DatePicker({
                        input: userapplyTime1,
                        isLockInputType: false
                    });
                    userapplyDt1.show();
                    userapplyDt1.addEvent('afterSetValue',
                            function () {
                                userapplyTime2.focus();
                                userapplyTime2.click();
                            })
                });
                userApply.addEvent('afterFocus',
                        function () {
                            userapplyTime1.click()
                        });
                userapplyTime2.click(function () {
                    userapplyDt2 = new phoenix.DatePicker({
                        input: userapplyTime2,
                        isLockInputType: false
                    }).show();
                });
                userApply.addEvent('afterBlur', function () {
                    if (userapplyDt1) {
                        userapplyDt1.hide();
                    }
                    if (userapplyDt2) {
                        userapplyDt2.hide();
                    }
                });
				//申诉发起时间
                var userArgue = new phoenix.SuperSearch({
                    name: 'UserArgueTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userargue3-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userargueTime1 = $('#sp-input-userargue3-1'), userargueTime2 = $('#sp-input-userargue3-2'), userargueDt1, userargueDt2;
                userargueTime1.click(function () {
                    userargueDt1 = new phoenix.DatePicker({
                        input: userargueTime1,
                        isLockInputType: false
                    });
                    userargueDt1.show();
                    userargueDt1.addEvent('afterSetValue',
                            function () {
                                userargueTime2.focus();
                                userargueTime2.click();
                            })
                });
                userArgue.addEvent('afterFocus',
                        function () {
                            userargueTime1.click()
                        });
                userargueTime2.click(function () {
                    userargueDt2 = new phoenix.DatePicker({
                        input: userargueTime2,
                        isLockInputType: false
                    }).show();
                });
                userArgue.addEvent('afterBlur', function () {
                    if (userargueDt1) {
                        userargueDt1.hide();
                    }
                    if (userargueDt2) {
                        userargueDt2.hide();
                    }
                });
                //风险类型
                var isvip = new phoenix.SuperSearch({
                    name: 'riskstype',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-riskstype3',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });

                //一审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'AuditOneAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-admin3',
                    group: group
                });

                var AuditOneTime1 = $('#sp-input-AuditOneBegin3-1'), AuditOneTime2 = $('#sp-input-AuditOneBegin3-2'), AuditOneDt1, AuditOneDt2;
                AuditOneTime1.click(function () {
                    AuditOneDt1 = new phoenix.DatePicker({
                        input: AuditOneTime1,
                        isLockInputType: false
                    });
                    AuditOneDt1.show();
                    AuditOneDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneTime2.focus();
                                AuditOneTime2.click();
                            })
                });

                AuditOneTime2.click(function () {
                    AuditOneDt2 = new phoenix.DatePicker({
                        input: AuditOneTime2,
                        isLockInputType: false
                    }).show();
                });

                var AuditOneEndTime1 = $('#sp-input-AuditOneEnd3-1'), AuditOneEndTime2 = $('#sp-input-AuditOneEnd3-2'), AuditOneEndDt1, AuditOneEndDt2;
                AuditOneEndTime1.click(function () {
                    AuditOneEndDt1 = new phoenix.DatePicker({
                        input: AuditOneEndTime1,
                        isLockInputType: false
                    });
                    AuditOneEndDt1.show();
                    AuditOneEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneEndTime2.focus();
                                AuditOneEndTime2.click();
                            })
                });

                AuditOneEndTime2.click(function () {
                    AuditOneEndDt2 = new phoenix.DatePicker({
                        input: AuditOneEndTime2,
                        isLockInputType: false
                    }).show();
                });

                var Title = new phoenix.SuperSearch({
                    name: 'Title',
                    group: group,
                    expands: {getValue: function () {
                            return 2;
                        }}
                });

                SelectByWhereInfo("0");
                //提交数据
                var dataArea = $('#showInfo3');
                group.removeEvent('dataChange');
                group.addEvent('dataChange', function () {
                    SelectByWhereInfo("0");
                });
                //每页记录数更改事件
                $("#per_page").change(function () {
                    SelectByWhereInfo("0");
                });
                function SelectByWhereInfo(pages) {
                    //当前页数据行数
                    var per_page_number = parseInt($("#per_page").val());
                    //放入group对象中(当前页)		
                    var page = new phoenix.SuperSearch({
                        name: 'page',
                        group: group,
                        expands: {getValue: function () {
                                return pages;
                            }}
                    });
                    //放入group对象中(当前页行数)	
                    var perPageNum = new phoenix.SuperSearch({
                        name: 'perPageNum',
                        group: group,
                        expands: {getValue: function () {
                                return per_page_number;
                            }}
                    });

                    var formData = group.getFormData(), mask = phoenix.Mask.getInstance();
                    $("#J-table-data3 >tbody").html("");
                    $("#Pagination3").hide();
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=loadappeal',
                        dataType: 'json',
                        method: 'post',
                        data: formData,
                        beforeSend: function () {
                            isLock = false;
                            TableStyle("showInfo3", 19, 1, "查询中");
                        },
                        success: function (data) {
                            if (data.text.length > 0) {
                                $("#Pagination3").show();
                                var resultAll = eval(data);
                                var re = resultAll.text;
                                var recordNum = 0;
                                recordNum = resultAll.count[0].recordNum;
                                //分页回调				 
                                $("#Pagination3").pagination(recordNum, {
                                    num_edge_entries: 2,
                                    num_display_entries: 8,
                                    current_page: pages,
                                    items_per_page: per_page_number,
                                    callback: SelectByWhereInfo
                                });
                                var html = "";
                                //数据填充
                                $.each(re, function (i) {
                                    if (re[i].riskTypeNum == '3') {
                                        if (re[i].withdrawAmt1 >= 50000 && re[i].withdrawAmt1 < 100000) {
                                            html += "<tr style='color:blue;'>";
                                        } else if (re[i].withdrawAmt1 >= 100000) {
                                            html += "<tr style='color:red;'>";
                                        } else {
                                            html += "<tr>";
                                        }
                                    } else {
                                        html += "<tr>";
                                    }
                                    html += "<td>" + re[i].appealSn + "</td>";    //     
                                    html += "<td>" + re[i].userName + "</td>";
                                    html += "<td>" + re[i].withdrawAmt + "</td>";
                                    html += "<td>" + re[i].withdrawTime + "</td>";
									html += "<td>" + re[i].argueTime + "</td>";
                                    html += "<td>" + re[i].vipLvl + "</td>";
                                    html += "<td>" + re[i].appealStatus + "</td>";

                                    //  if($("#WITHDRAW_LOOKDETAIL").val()=='1'){
                                    html += "<td>" + "<a class='btn btn-small' href='javascript:void(0);' name='checkDetail' style='position:initial'>查看</a>" + "</td>";
                                    // }      
                                    html += "<td>" + re[i].appealAcct + "</td>";
                                    html += "<td>" + re[i].appealTime + "</td>";
                                    html += "<td>" + re[i].appealMemo + "</td>";
                                });
                                $("#J-table-data3 >tbody").html(html);

                            } else {
                                $("#Pagination3").hide();
                                TableStyle("J-table-data3>tbody", 19, 2, "没有相应数据");
                            }
                        },
                        complete: function ()
                        {
                            isLock = true;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            TableStyle("J-table-data3>tbody", 19, 2, "数据异常");
                        }
                    });
                }
            }

            function ProcessingData() {
                //申请单号
                var drawserial = new phoenix.SuperSearch({
                    name: 'drawserial',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-serial4',
                    group: group
                });

                //提现卡号
                var drawnumber = new phoenix.SuperSearch({
                    name: 'drawnumber',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawnumber4',
                    group: group
                });

                //提现金额
                var drawmoney = new phoenix.SuperSearch({
                    name: 'drawmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawmoney4',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                $('#J-input4-drawmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input4-drawmoney2').keyup(allowNumber).blur(allowNumber);

                //提现用户名
                var drawname = new phoenix.SuperSearch({
                    name: 'drawname',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawname4',
                    group: group
                });

                //是否VIP
                var isvip = new phoenix.SuperSearch({
                    name: 'isvip',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-isvip4',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });
                //用户申请提现时间
                var userApply = new phoenix.SuperSearch({
                    name: 'UserApplyTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userapply4-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userapplyTime1 = $('#sp-input-userapply4-1'), userapplyTime2 = $('#sp-input-userapply4-2'), userapplyDt1, userapplyDt2;
                userapplyTime1.click(function () {
                    userapplyDt1 = new phoenix.DatePicker({
                        input: userapplyTime1,
                        isLockInputType: false
                    });
                    userapplyDt1.show();
                    userapplyDt1.addEvent('afterSetValue',
                            function () {
                                userapplyTime2.focus();
                                userapplyTime2.click();
                            })
                });
                userApply.addEvent('afterFocus',
                        function () {
                            userapplyTime1.click()
                        });
                
                userapplyTime2.click(function () {
                    userapplyDt2 = new phoenix.DatePicker({
                        input: userapplyTime2,
                        isLockInputType: false
                    }).show();
                });
                userApply.addEvent('afterBlur', function () {
                    if (userapplyDt1) {
                        userapplyDt1.hide();
                    }
                    if (userapplyDt2) {
                        userapplyDt2.hide();
                    }
                });
				//申诉发起时间
                var userArgue = new phoenix.SuperSearch({
                    name: 'UserArgueTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userargue4-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userargueTime1 = $('#sp-input-userargue4-1'), userargueTime2 = $('#sp-input-userargue4-2'), userargueDt1, userargueDt2;
                userargueTime1.click(function () {
                    userargueDt1 = new phoenix.DatePicker({
                        input: userargueTime1,
                        isLockInputType: false
                    });
                    userargueDt1.show();
                    userargueDt1.addEvent('afterSetValue',
                            function () {
                                userargueTime2.focus();
                                userargueTime2.click();
                            })
                });
                userArgue.addEvent('afterFocus',
                        function () {
                            userargueTime1.click()
                        });
                userargueTime2.click(function () {
                    userargueDt2 = new phoenix.DatePicker({
                        input: userargueTime2,
                        isLockInputType: false
                    }).show();
                });
                userArgue.addEvent('afterBlur', function () {
                    if (userargueDt1) {
                        userargueDt1.hide();
                    }
                    if (userargueDt2) {
                        userargueDt2.hide();
                    }
                });
                //风险类型
                var isvip = new phoenix.SuperSearch({
                    name: 'riskstype',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-riskstype4',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });

                //一审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'AuditOneAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-admin4',
                    group: group
                });


                var AuditOneTime1 = $('#sp-input-AuditOneBegin4-1'), AuditOneTime2 = $('#sp-input-AuditOneBegin4-2'), AuditOneDt1, AuditOneDt2;
                AuditOneTime1.click(function () {
                    AuditOneDt1 = new phoenix.DatePicker({
                        input: AuditOneTime1,
                        isLockInputType: false
                    });
                    AuditOneDt1.show();
                    AuditOneDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneTime2.focus();
                                AuditOneTime2.click();
                            })
                });

                AuditOneTime2.click(function () {
                    AuditOneDt2 = new phoenix.DatePicker({
                        input: AuditOneTime2,
                        isLockInputType: false
                    }).show();
                });


                var AuditOneEndTime1 = $('#sp-input-AuditOneEnd4-1'), AuditOneEndTime2 = $('#sp-input-AuditOneEnd4-2'), AuditOneEndDt1, AuditOneEndDt2;
                AuditOneEndTime1.click(function () {
                    AuditOneEndDt1 = new phoenix.DatePicker({
                        input: AuditOneEndTime1,
                        isLockInputType: false
                    });
                    AuditOneEndDt1.show();
                    AuditOneEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneEndTime2.focus();
                                AuditOneEndTime2.click();
                            })
                });

                AuditOneEndTime2.click(function () {
                    AuditOneEndDt2 = new phoenix.DatePicker({
                        input: AuditOneEndTime2,
                        isLockInputType: false
                    }).show();
                });

                var AuditReviewEndTime1 = $('#sp-input-reviewEnd4-1'), AuditReviewEndTime2 = $('#sp-input-reviewEnd4-2'), AuditReviewEndDt1, AuditReviewEndDt2;
                AuditReviewEndTime1.click(function () {
                    AuditReviewEndDt1 = new phoenix.DatePicker({
                        input: AuditReviewEndTime1,
                        isLockInputType: false
                    });
                    AuditReviewEndDt1.show();
                    AuditReviewEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditReviewEndTime2.focus();
                                AuditReviewEndTime2.click();
                            })
                });

                AuditReviewEndTime2.click(function () {
                    AuditReviewEndDt2 = new phoenix.DatePicker({
                        input: AuditReviewEndTime2,
                        isLockInputType: false
                    }).show();
                });

                //复审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'ReviewAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-reviewAdmin4',
                    group: group
                });
                var Title = new phoenix.SuperSearch({
                    name: 'Title',
                    group: group,
                    expands: {getValue: function () {
                            return 3;
                        }}
                });
                SelectByWhereInfo("0");
                group.removeEvent('dataChange');
                group.addEvent('dataChange', function () {
                    SelectByWhereInfo("0");
                });
                //每页记录数更改事件
                $("#per_page").change(function () {
                    SelectByWhereInfo("0");
                });
                function SelectByWhereInfo(pages) {
                    //当前页数据行数
                    var per_page_number = parseInt($("#per_page").val());
                    //放入group对象中(当前页)		
                    var page = new phoenix.SuperSearch({
                        name: 'page',
                        group: group,
                        expands: {getValue: function () {
                                return pages;
                            }}
                    });
                    //放入group对象中(当前页行数)	
                    var perPageNum = new phoenix.SuperSearch({
                        name: 'perPageNum',
                        group: group,
                        expands: {getValue: function () {
                                return per_page_number;
                            }}
                    });

                    var formData = group.getFormData(), mask = phoenix.Mask.getInstance();
                    $("#J-table-data4 >tbody").html("");
                    $("#Pagination4").hide();
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=loadappeal',
                        dataType: 'json',
                        method: 'post',
                        data: formData,
                        beforeSend: function () {
                            isLock = false;
                            TableStyle("showInfo4", 19, 1, "查询中");
                        },
                        success: function (data) {
                            //debugger
                            if (data.text.length > 0) {
                                $("#Pagination4").show();
                                var resultAll = eval(data);
                                var re = resultAll.text;
                                var recordNum = 0;
                                recordNum = resultAll.count[0].recordNum;
                                //分页回调				 
                                $("#Pagination4").pagination(recordNum, {
                                    num_edge_entries: 2,
                                    num_display_entries: 8,
                                    current_page: pages,
                                    items_per_page: per_page_number,
                                    callback: SelectByWhereInfo
                                });
                                var html = "";
                                //数据填充
                                $.each(re, function (i) {
                                    if (re[i].riskTypeNum == '3') {
                                        if (re[i].withdrawAmt1 >= 50000 && re[i].withdrawAmt1 < 100000) {
                                            html += "<tr style='color:blue;'>";
                                        } else if (re[i].withdrawAmt1 >= 100000) {
                                            html += "<tr style='color:red;'>";
                                        } else {
                                            html += "<tr>";
                                        }
                                    } else {
                                        html += "<tr>";
                                    }
                                    html += "<td>" + re[i].appealSn + "</td>";    //     
                                    html += "<td>" + re[i].userName + "</td>";
                                    html += "<td>" + re[i].withdrawAmt + "</td>";
                                    html += "<td>" + re[i].withdrawTime + "</td>";
									html += "<td>" + re[i].argueTime + "</td>";
                                    html += "<td>" + re[i].vipLvl + "</td>";
                                    html += "<td>" + re[i].appealStatus + "</td>";

                                    //  if($("#WITHDRAW_LOOKDETAIL").val()=='1'){
                                    html += "<td>" + "<a class='btn btn-small' href='javascript:void(0);' name='checkDetail' style='position:initial'>查看</a>" + "</td>";
                                    // }

                                    html += "<td>" + re[i].appealMemo + "</td>";
                                    html += "<td>" + re[i].appealAcct + "</td>";
                                    html += "<td>" + re[i].appealTime + "</td>";
                                    html += "<td>" + re[i].appealMemo + "</td>";

                                });
                                $("#J-table-data4>tbody").html(html);
                            } else {
                                $("#Pagination4").hide();
                                $("#J-table-data4>tbody").html("<tr><td colspan=\"18\" style='height:120px;text-align:center;color:red; font-size:14px;font-weight:600;'>没有相应数据</td></tr>");
                            }
                        },
                        complete: function ()
                        {
                            isLock = true;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            $("#J-table-data4>tbody").html("<tr><td colspan=\"19\" style='height:120px;text-align:center;color:red; font-size:14px;font-weight:600;'>数据异常</td></tr>");
                        }
                    });
                }
            }
            $("#btnSubmit3").click(function () {
                if ($.trim($("#txtRemark3").val()) == "")
                {
                    $("#txtRemark3").focus();
                    msg.show({
                        mask: true,
                        content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请输入备注信息</h3><div style="height:30px;line-height:30px;"></div>',
                        confirmIsShow: 'true',
                        confirmText: '确定',
                        confirmFun: function () {
                            msg.hide();
                        }
                    });
                    return false;
                }
                var button = $("#btnSubmit3");
                button.val("提交复审");
                button.removeAttr("disabled", "disabled");
                var sdata = GetPostText("#ReviewOperDiv") + "status=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=wd',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    beforeSend: function () {
                        //禁用发送								
                        var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'], i = 0;
                        interval = setInterval(function () {
                            button.val(list[i]);
                            i += 1;
                            if (i >= list.length) {
                                i = 0;
                            }
                        }, 300);
                        button.attr("disabled", "disabled");
                        $('[name="closeDiv3"]').attr("disabled", "disabled");
                    },
                    success: function (data) {
                        if (data['status'] == 'ok') {
                            var des = '审核待复审成功';
                        } else {
                            var des = data['data'];
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data['status'] == 'ok') {
                                    box3.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                }
                            }
                        });
                    },
                    complete: function () {
                        clearInterval(interval);
                        button.val("提交复审");
                        button.removeAttr("disabled", "disabled");
                        $('[name="closeDiv3"]').removeAttr("disabled", "disabled");
                    }
                });
            }
            );

            function CompletedData() {
/*
                //申请单号
                var drawserial = new phoenix.SuperSearch({
                    name: 'drawserial',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-serial5',
                    group: group
                });

                //提现卡号
                var drawnumber = new phoenix.SuperSearch({
                    name: 'drawnumber',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawnumber5',
                    group: group
                });

                //提现金额
                var drawmoney = new phoenix.SuperSearch({
                    name: 'drawmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawmoney5',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                $('#J-input5-drawmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input5-drawmoney2').keyup(allowNumber).blur(allowNumber);
                //实际提现金额
                var drawrealmoney = new phoenix.SuperSearch({
                    name: 'drawrealmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawrealmoney5',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                $('#J-input5-drawrealmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input5-drawrealmoney2').keyup(allowNumber).blur(allowNumber);

                //提现用户名
                var drawname = new phoenix.SuperSearch({
                    name: 'drawname',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawname5',
                    group: group
                });

                //是否VIP
                var isvip = new phoenix.SuperSearch({
                    name: 'isvip',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-isvip5',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });
                //用户申请提现时间
                var userApply = new phoenix.SuperSearch({
                    name: 'UserApplyTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userapply5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userapplyTime1 = $('#sp-input-userapply5-1'), userapplyTime2 = $('#sp-input-userapply5-2'), userapplyDt1, userapplyDt2;
                userapplyTime1.click(function () {
                    userapplyDt1 = new phoenix.DatePicker({
                        input: userapplyTime1,
                        isLockInputType: false
                    });
                    userapplyDt1.show();
                    userapplyDt1.addEvent('afterSetValue',
                            function () {
                                userapplyTime2.focus();
                                userapplyTime2.click();
                            })
                });
                userApply.addEvent('afterFocus',
                        function () {
                            userapplyTime1.click()
                        });
                userapplyTime2.click(function () {
                    userapplyDt2 = new phoenix.DatePicker({
                        input: userapplyTime2,
                        isLockInputType: false
                    }).show();
                });
                userApply.addEvent('afterBlur', function () {
                    if (userapplyDt1) {
                        userapplyDt1.hide();
                    }
                    if (userapplyDt2) {
                        userapplyDt2.hide();
                    }
                });
				
				//申诉发起时间
                var userArgue = new phoenix.SuperSearch({
                    name: 'UserArgueTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '/',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userargueTime1 = $('#sp-input-userargue5-1'), userargueTime2 = $('#sp-input-userargue5-2'), userargueDt1, userargueDt2;
                userargueTime1.click(function () {
                    userargueDt1 = new phoenix.DatePicker({
                        input: userargueTime1,
                        isLockInputType: false
                    });
                    userargueDt1.show();
                    userargueDt1.addEvent('afterSetValue',
                            function () {
                                userargueTime2.focus();
                                userargueTime2.click();
                            })
                });
                userArgue.addEvent('afterFocus',
                        function () {
                            userargueTime1.click()
                        });
                userargueTime2.click(function () {
                    userargueDt2 = new phoenix.DatePicker({
                        input: userargueTime2,
                        isLockInputType: false
                    }).show();
                });
                userArgue.addEvent('afterBlur', function () {
                    if (userargueDt1) {
                        userargueDt1.hide();
                    }
                    if (userargueDt2) {
                        userargueDt2.hide();
                    }
                });
                //风险类型
                var isvip = new phoenix.SuperSearch({
                    name: 'riskstype',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-riskstype5',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });

                //一审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'AuditOneAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-admin5',
                    group: group
                });

                //一审开始时间	
                var AuditOneBegin = new phoenix.SuperSearch({
                    name: 'AuditOneBeginTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-AuditOneBegin5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var AuditOneTime1 = $('#sp-input-AuditOneBegin5-1'), AuditOneTime2 = $('#sp-input-AuditOneBegin5-2'), AuditOneDt1, AuditOneDt2;
                AuditOneTime1.click(function () {
                    AuditOneDt1 = new phoenix.DatePicker({
                        input: AuditOneTime1,
                        isLockInputType: false
                    });
                    AuditOneDt1.show();
                    AuditOneDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneTime2.focus();
                                AuditOneTime2.click();
                            })
                });
                AuditOneBegin.addEvent('afterFocus',
                        function () {
                            AuditOneTime1.click()
                        });
                AuditOneTime2.click(function () {
                    AuditOneDt2 = new phoenix.DatePicker({
                        input: AuditOneTime2,
                        isLockInputType: false
                    }).show();
                });
                AuditOneBegin.addEvent('afterBlur', function () {
                    if (AuditOneDt1) {
                        AuditOneDt1.hide();
                    }
                    if (AuditOneDt2) {
                        AuditOneDt2.hide();
                    }
                });


                //一审结束时间	
                var AuditOneEnd = new phoenix.SuperSearch({
                    name: 'AuditOneEndTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-AuditOneEnd5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var AuditOneEndTime1 = $('#sp-input-AuditOneEnd5-1'), AuditOneEndTime2 = $('#sp-input-AuditOneEnd5-2'), AuditOneEndDt1, AuditOneEndDt2;
                AuditOneEndTime1.click(function () {
                    AuditOneEndDt1 = new phoenix.DatePicker({
                        input: AuditOneEndTime1,
                        isLockInputType: false
                    });
                    AuditOneEndDt1.show();
                    AuditOneEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditOneEndTime2.focus();
                                AuditOneEndTime2.click();
                            })
                });
                AuditOneEnd.addEvent('afterFocus',
                        function () {
                            AuditOneEndTime1.click()
                        });
                AuditOneEndTime2.click(function () {
                    AuditOneEndDt2 = new phoenix.DatePicker({
                        input: AuditOneEndTime2,
                        isLockInputType: false
                    }).show();
                });
                AuditOneEnd.addEvent('afterBlur', function () {
                    if (AuditOneEndDt1) {
                        AuditOneEndDt1.hide();
                    }
                    if (AuditOneEndDt2) {
                        AuditOneEndDt2.hide();
                    }
                });

                //复审结束时间	
                var AuditreviewEnd = new phoenix.SuperSearch({
                    name: 'ReviewEndTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-reviewEnd5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var AuditReviewEndTime1 = $('#sp-input-reviewEnd5-1'), AuditReviewEndTime2 = $('#sp-input-reviewEnd5-2'), AuditReviewEndDt1, AuditReviewEndDt2;
                AuditReviewEndTime1.click(function () {
                    AuditReviewEndDt1 = new phoenix.DatePicker({
                        input: AuditReviewEndTime1,
                        isLockInputType: false
                    });
                    AuditReviewEndDt1.show();
                    AuditReviewEndDt1.addEvent('afterSetValue',
                            function () {
                                AuditReviewEndTime2.focus();
                                AuditReviewEndTime2.click();
                            })
                });
                AuditreviewEnd.addEvent('afterFocus',
                        function () {
                            AuditReviewEndTime1.click()
                        });
                AuditReviewEndTime2.click(function () {
                    AuditReviewEndDt2 = new phoenix.DatePicker({
                        input: AuditReviewEndTime2,
                        isLockInputType: false
                    }).show();
                });
                AuditreviewEnd.addEvent('afterBlur', function () {
                    if (AuditReviewEndDt1) {
                        AuditReviewEndDt1.hide();
                    }
                    if (AuditReviewEndDt2) {
                        AuditReviewEndDt2.hide();
                    }
                });
                //复审管理员
                var drawmoney = new phoenix.SuperSearch({
                    name: 'ReviewAdmmin',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-reviewAdmin5',
                    group: group
                });

                //mow返回结果时间	
                var mowReturnTime = new phoenix.SuperSearch({
                    name: 'MowReturnTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-mowReturn5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var mowReturnTime1 = $('#sp-input-mowReturn5-1'), mowReturnTime2 = $('#sp-input-mowReturn5-2'), mowReturnDt1, mowReturnDt2;
                mowReturnTime1.click(function () {
                    mowReturnDt1 = new phoenix.DatePicker({
                        input: mowReturnTime1,
                        isLockInputType: false
                    });
                    mowReturnDt1.show();
                    mowReturnDt1.addEvent('afterSetValue',
                            function () {
                                mowReturnTime2.focus();
                                mowReturnTime2.click();
                            })
                });
                mowReturnTime.addEvent('afterFocus',
                        function () {
                            mowReturnTime1.click()
                        });
                mowReturnTime2.click(function () {
                    mowReturnTime2 = new phoenix.DatePicker({
                        input: mowReturnTime2,
                        isLockInputType: false
                    }).show();
                });
                mowReturnTime.addEvent('afterBlur', function () {
                    if (mowReturnDt1) {
                        mowReturnDt1.hide();
                    }
                    if (mowReturnDt2) {
                        mowReturnDt2.hide();
                    }
                });
                //状态
                var status = new phoenix.SuperSearch({
                    name: 'status',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-status5',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });
                var Title = new phoenix.SuperSearch({
                    name: 'Title',
                    group: group,
                    expands: {getValue: function () {
                            return 4;
                        }}
                });

                SelectByWhereInfo("0");
                group.removeEvent('dataChange');
                //提交数据		
                group.addEvent('dataChange', function () {
                    SelectByWhereInfo("0");
                });
                //每页记录数更改事件
                $("#per_page").change(function () {
                    SelectByWhereInfo("0");
                });
                function SelectByWhereInfo(pages) {
                    //当前页数据行数
                    var per_page_number = parseInt($("#per_page").val());
                    //放入group对象中(当前页)		
                    var page = new phoenix.SuperSearch({
                        name: 'page',
                        group: group,
                        expands: {getValue: function () {
                                return pages;
                            }}
                    });
                    //放入group对象中(当前页行数)	
                    var perPageNum = new phoenix.SuperSearch({
                        name: 'perPageNum',
                        group: group,
                        expands: {getValue: function () {
                                return per_page_number;
                            }}
                    });

                    var formData = group.getFormData(), mask = phoenix.Mask.getInstance();
                    $("#J-table-data5>tbody").html("");
                    $("#Pagination5").hide();
                    $.ajax({
                        url: '/admin/Rechargemange/index?parma=da',
                        dataType: 'json',
                        method: 'post',
                        data: formData,
                        beforeSend: function () {
                            isLock = false;
                            TableStyle("showInfo5", 19, 1, "查询中");
                        },
                        success: function (data) {
                            //debugger
                            if (data.text.length > 0) {
                                $("#Pagination5").show();
                                var resultAll = eval(data);
                                var re = resultAll.text;
                                var recordNum = 0;
                                recordNum = resultAll.count[0].recordNum;
                                //分页回调				 
                                $("#Pagination5").pagination(recordNum, {
                                    num_edge_entries: 2,
                                    num_display_entries: 8,
                                    current_page: pages,
                                    items_per_page: per_page_number,
                                    callback: SelectByWhereInfo
                                });
                                var html = "";
                                //数据填充
                                $.each(re, function (i) {
                                    if (re[i].riskTypeNum == '3') {
                                        if (re[i].withdrawAmt1 >= 50000 && re[i].withdrawAmt1 < 100000) {
                                            html += "<tr style='color:blue;'>";
                                        } else if (re[i].withdrawAmt1 >= 100000) {
                                            html += "<tr style='color:red;'>";
                                        } else {
                                            html += "<tr>";
                                        }
                                    } else {
                                        html += "<tr>";
                                    }
                                    html += "<td>" + re[i].sn + "</td>";    //     
                                    html += "<td>" + re[i].BankName + "</td>";
                                    html += "<td>" + re[i].bankAccount + "</td>";
                                    html += "<td>" + re[i].bankNumber + "</td>";
                                    html += "<td>" + re[i].withdrawAmt + "</td>";
                                    html += "<td>" + re[i].realWithDrawAmt + "</td>";
                                    html += "<td>" + re[i].applyAccount + "</td>";
                                    html += "<td>" + (re[i].isVip == "1" ? "是" : "否") + "</td>";
                                    html += "<td>" + re[i].ipAddr + "</td>";
                                    html += "<td>" + re[i].applyTime + "</td>";
                                    html += "<td>" + re[i].riskType + "</td>";
                                    html += "<td>" + re[i].attach + "</td>";			  //材料	
                                    html += "<td>" + re[i].apprAccount + "</td>";         //一审管理员
                                    html += "<td>" + re[i].beginApprTime + "</td>";       //一审开始时间
                                    html += "<td>" + re[i].apprTime + "</td>";            //一审结束时间 
                                    html += "<td>" + re[i].Appr2Time + "</td>";           //复审结束时间
                                    html += "<td>" + re[i].appr2Acct + "</td>";           //复审管理员
                                    html += "<td>" + re[i].mcNoticeTime + "</td>";		  //mow返回结果时间
                                    // 状态如下：
                                    html += "<td>" + re[i].status + "</td>";
                                    html += "<td>" + re[i].memo + "</td>";	      //备注	
                                    html += "<td>" + re[i].cancelAcct + "</td>";           //退款人	
                                    html += "<td>" + re[i].cancelTime + "</td>";           //退款时间 							
                                });
                                $("#J-table-data5>tbody").html(html);

                            } else {
                                $("#Pagination5").hide();
                                TableStyle("J-table-data5>tbody", 18, 2, "没有相应数据");
                            }
                        },
                        complete: function ()
                        {
                            isLock = true;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            TableStyle("J-table-data5>tbody", 19, 2, "数据异常");
                        }
                    });
                }*/
            }
            
            //---------------------------------------------------(待定)数据处理开始------------------------------------------------------
            function WaitData() {
                //申訴单号
                 $('#pagetype').val('4');
                var drawserial = new phoenix.SuperSearch({
                    name: 'drawserial',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-serial5',
                    group: group
                });

                //提现用户名
                var drawname = new phoenix.SuperSearch({
                    name: 'drawname',
                    keyCode: 'ctrl+83',
                    el: '#J-sp-drawname5',
                    group: group
                });

                //提款金额
                var drawmoney = new phoenix.SuperSearch({
                    name: 'drawmoney',
                    keyCode: 'ctrl+85',
                    el: '#J-sp-drawmoney5',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });

                $('#J-input5-drawmoney1').keyup(allowNumber).blur(allowNumber);
                $('#J-input5-drawmoney2').keyup(allowNumber).blur(allowNumber);

                //提现时间
                var userApply = new phoenix.SuperSearch({
                    name: 'UserApplyTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userapply5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userapplyTime1 = $('#sp-input-userapply5-1'), userapplyTime5 = $('#sp-input-userapply5-2'), userapplyDt1, userapplyDt2;
                userapplyTime1.click(function () {
                    userapplyDt1 = new phoenix.DatePicker({
                        input: userapplyTime1,
                        isLockInputType: false
                    });
                    userapplyDt1.show();
                    userapplyDt1.addEvent('afterSetValue',
                            function () {
                                userapplyTime5.focus();
                                userapplyTime5.click();
                            })
                });
                userApply.addEvent('afterFocus',
                        function () {
                            userapplyTime1.click()
                        });
                userapplyTime5.click(function () {
                    userapplyDt5 = new phoenix.DatePicker({
                        input: userapplyTime5,
                        isLockInputType: false
                    }).show();
                });
                userApply.addEvent('afterBlur', function () {
                    if (userapplyDt1) {
                        userapplyDt1.hide();
                    }
                    if (userapplyDt2) {
                        userapplyDt2.hide();
                    }
                });
				//申诉发起时间
                var userArgue = new phoenix.SuperSearch({
                    name: 'UserArgueTime',
                    keyCode: 'ctrl+68',
                    type: 'time',
                    el: '#J-sp-userargue5-time',
                    group: group,
                    expands: {getFormValue: getTowInputValue}
                });
                var userargueTime1 = $('#sp-input-userargue5-1'), userargueTime2 = $('#sp-input-userargue5-2'), userargueDt1, userargueDt2;
                userargueTime1.click(function () {
                    userargueDt1 = new phoenix.DatePicker({
                        input: userargueTime1,
                        isLockInputType: false
                    });
                    userargueDt1.show();
                    userargueDt1.addEvent('afterSetValue',
                            function () {
                                userargueTime2.focus();
                                userargueTime2.click();
                            })
                });
                userArgue.addEvent('afterFocus',
                        function () {
                            userargueTime1.click()
                        });
                userargueTime2.click(function () {
                    userargueDt2 = new phoenix.DatePicker({
                        input: userargueTime2,
                        isLockInputType: false
                    }).show();
                });
                userArgue.addEvent('afterBlur', function () {
                    if (userargueDt1) {
                        userargueDt1.hide();
                    }
                    if (userargueDt2) {
                        userargueDt2.hide();
                    }
                });

                //是否VIP
                var isvip = new phoenix.SuperSearch({
                    name: 'isvip',
                    keyCode: 'ctrl+66',
                    type: 'select',
                    isAutoWidth: true,
                    el: '#J-sp-isvip5',
                    group: group,
                    expands: {getFormValue: getSelectValue}
                });

                var Title = new phoenix.SuperSearch({
                    name: 'Title',
                    group: group,
                    expands: {getValue: function () {
                            return 4;
                        }}
                });

                SelectByWhereInfo("0");
                //提交数据
                var dataArea = $('#showInfo5');
                group.removeEvent('dataChange');
                group.addEvent('dataChange', function () {
                    SelectByWhereInfo("0");
                });
                //每页记录数更改事件
                $("#per_page").change(function () {
                    SelectByWhereInfo("0");
                });
                function SelectByWhereInfo(pages) {
                    //当前页数据行数
                    var per_page_number = parseInt($("#per_page").val());
                    //放入group对象中(当前页)		
                    var page = new phoenix.SuperSearch({
                        name: 'page',
                        group: group,
                        expands: {getValue: function () {
                                return pages;
                            }}
                    });
                    //放入group对象中(当前页行数)	
                    var perPageNum = new phoenix.SuperSearch({
                        name: 'perPageNum',
                        group: group,
                        expands: {getValue: function () {
                                return per_page_number;
                            }}
                    });
                    //99
                    var formData = group.getFormData(), mask = phoenix.Mask.getInstance();
                    $("#J-table-data5>tbody").html("");
                    $("#Pagination5").hide();
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=loadappeal',
                        dataType: 'json',
                        method: 'post',
                        data: formData,
                        beforeSend: function () {
                            isLock = false;
                            TableStyle("showInfo5", 19, 1, "查询中");
                        },
                        success: function (data) {
                            if (data.text.length > 0) {
                                $("#Pagination5").show();
                                var resultAll = eval(data);
                                var re = resultAll.text;
                                var recordNum = 0;
                                recordNum = resultAll.count[0].recordNum;
                                //分页回调				 
                                $("#Pagination5").pagination(recordNum, {
                                    num_edge_entries: 2,
                                    num_display_entries: 8,
                                    current_page: pages,
                                    items_per_page: per_page_number,
                                    callback: SelectByWhereInfo
                                });
                                var html = "";
                                //数据填充
                                $.each(re, function (i) {
                                    if (re[i].riskTypeNum == '3') {
                                    
                                        if (re[i].withdrawAmt1 >= 50000 && re[i].withdrawAmt1 < 100000) {
                                            html += "<tr style='color:blue;'>";
                                        } else if (re[i].withdrawAmt1 >= 100000) {
                                            html += "<tr style='color:red;'>";
                                        } else {
                                            html += "<tr>";
                                        }
                                    } else {
                                        html += "<tr>";
                                    }
                                    html += "<td>" + re[i].appealSn + "</td>";    //     
                                    html += "<td>" + re[i].userName + "</td>";
                                    html += "<td>" + re[i].withdrawAmt + "</td>";
                                    html += "<td>" + re[i].withdrawTime + "</td>";
									html += "<td>" + re[i].argueTime + "</td>";
                                    html += "<td>" + re[i].vipLvl + "</td>";
                                    html += "<td>" + re[i].appealStatus + "</td>";
									
                                    if(re[i].isCheck){
                                    	html += "<td>" + re[i].nowCheckUser+'操作中...'+ "</td>";
                                    }else{
                                        //  if($("#WITHDRAW_LOOKDETAIL").val()=='1'){
                                        html += "<td>" + "<a class='btn btn-small' href='javascript:void(0);' name='appealOK' style='position:initial'>审核</a>" + "</td>";
                                        // }
                                    }

                                    html += "<td>" + re[i].appealMemo + "</td>";

                                });
                                $("#J-table-data5>tbody").html(html);
                            } else {
                                $("#Pagination5").hide();
                                TableStyle("J-table-data5>tbody", 19, 2, "没有相应数据");

                            }
                        },
                        complete: function ()
                        {
                            isLock = true;
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            TableStyle("J-table-data5>tbody", 19, 2, "数据异常");
                        }
                    });
                }
            }
            $("#btnSubmit5").click(function () {
                if ($.trim($("#txtRemark5").val()) == "")
                {
                    $("#txtRemark5").focus();
                    msg.show({
                        mask: true,
                        content: '<h3 style="height:30px;line-height:30px;text-align:center; ">请输入备注信息</h3><div style="height:30px;line-height:30px;"></div>',
                        confirmIsShow: 'true',
                        confirmText: '确定',
                        confirmFun: function () {
                            msg.hide();
                        }
                    });
                    return false;
                }
                var button = $("#btnSubmit5");
                button.val("确认拒绝");
                button.removeAttr("disabled", "disabled");
                var sdata = GetPostText("#RefuseOperDiv") + "status=3";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=wd',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    beforeSend: function () {
                        //禁用发送								
                        var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'], i = 0;
                        interval = setInterval(function () {
                            button.val(list[i]);
                            i += 1;
                            if (i >= list.length) {
                                i = 0;
                            }
                        }, 300);
                        button.attr("disabled", "disabled");
                        $('[name="closeDiv2"]').attr("disabled", "disabled");
                    },
                    success: function (data) {
                        if (data['status'] == 'ok') {
                            var des = "审核拒绝成功";
                        } else {
                            var des = data['data'];
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data['status'] == 'ok') {
                                    box2.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=4";
                                    $('#appealWait').show();
                                    $('#sp-li-appealTips2-wait').show();
                                }
                            }
                        });
                    },
                    complete: function () {
                        clearInterval(interval);
                        button.val("确认拒绝");
                        button.removeAttr("disabled", "disabled");
                        $('[name="closeDiv2"]').removeAttr("disabled", "disabled");
                    }
                });
            }
            );


            //---------------------------------------------------(待定)数据处理结束------------------------------------------------------
            

            //通过
            $(document).on('click', '[name="ThroughOper"]', function (e) {
                var oTr = $(this).parent().parent();
                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=0" + "&Title=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box.OverLay.Color = "rgb(51, 51, 51)";
                            box.Over = true;
                            box.OverLay.Opacity = 50;
                            box.Fixed = true;
                            box.Center = true;
                            box.Show();
                            $("#sp-li-drawbank").text(data["data"]["BankName"]);
                            $("#sp-li-drawName").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID").val(data["data"]["id"]);
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                }
                            });
                        }
                    }
                });
            });
            //通过
            $(document).on('click', '[name="ThroughOper2"]', function (e) {
                var oTr = $(this).parent().parent();

                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=0" + "&Title=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box4.OverLay.Color = "rgb(51, 51, 51)";
                            box4.Over = true;
                            box4.OverLay.Opacity = 50;
                            box4.Fixed = true;
                            box4.Center = true;
                            box4.Show();
                            $("#sp-li-drawbank4").text(data["data"]["BankName"]);
                            $("#sp-li-drawName4").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber4").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney4").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName4").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType4").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID4").val(data["data"]["id"]);
                            $("#txtRemark4").val(data["data"]["memo"]);
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            });
                        }
                    }
                });
            });
            $(document).on('click', '[name="closeIcoDiv"]', function (e) {
                box.Close();

            });
            $(document).on('click', '[name="closeIcoDiv2"]', function (e) {
                box2.Close();
                $('#appealWait').show();
                $('#sp-li-appealTips2-wait').show();
            });
            $(document).on('click', '[name="closeIcoDiv3"]', function (e) {
                box3.Close();

            });
            $(document).on('click', '[name="closeIcoDiv4"]', function (e) {
                box4.Close();

            });
            $(document).on('click', '[name="closeIcoDiv5"]', function (e) {
                box5.Close();

            });
            $(document).on('click', '[name="closeIcoDiv6"]', function (e) {
                window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=3";
                box6.Close();

            });
            $(document).on('click', '[name="closeIcoDiv7"]', function (e) {
                var testStatus7 = $("#sp-li-drawstatus7").val();
                if (testStatus7 == "0" || testStatus7 == "1" || testStatus7 == "2") {
                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=3";
                }
                box7.Close();

            });
            $(document).on('click', '[name="closeIcoDiv8"]', function (e) {
                box8.Close();

            });

       
            $(document).on('click', '[name="closeDiv2"]', function (e) {
                var sdata = "id=" + $("#sp-li-drawbankID2").val() + "&title=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=opt4',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            var des = '操作成功';
                        } else {
                            var des = '操作失败';
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data.status == "ok") {
                                    box2.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                    $('#appealWait').show();
                                    $('#sp-li-appealTips2-wait').show();
                                }
                            }
                        });
                    }
                });
            });
            $(document).on('click', '[name="closeDiv3"]', function (e) {
                var sdata = "id=" + $("#sp-li-drawbankID3").val() + "&title=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=opt4',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            var des = '操作成功';
                        } else {
                            var des = '操作失败';
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data.status == "ok") {
                                    box3.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                }
                            }
                        });
                    }
                });
            });
        


            $(document).on('click', '[name="closeDiv4"]', function (e) {
                var sdata = "id=" + $("#sp-li-drawbankID4").val() + "&title=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=opt4',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            var des = '操作成功';
                        } else {
                            var des = '操作失败';
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data.status == "ok") {
                                    box4.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            }
                        });
                    }
                });
            });
            $(document).on('click', '[name="closeDiv5"]', function (e) {
                var sdata = "id=" + $("#sp-li-drawbankID5").val() + "&title=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=opt4',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            var des = '操作成功';
                        } else {
                            var des = '操作失败';
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data.status == "ok") {
                                    box5.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            }
                        });
                    }
                });
            });
            $(document).on('click', '[name="closeDiv6"]', function (e) {
                var sdata = "id=" + $("#sp-li-drawbankID6").val() + "&title=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=opt4',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            var des = '操作成功';
                        } else {
                            var des = '操作失败';
                        }
                        msg.show({
                            mask: true,
                            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + des + '</h3><div style="height:30px;line-height:30px;"></div>',
                            confirmIsShow: 'true',
                            confirmText: '确定',
                            confirmFun: function () {
                                msg.hide();
                                if (data.status == "ok") {
                                    box6.Close();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=3";
                                }
                            }
                        });
                    }
                });
            });


            //拒绝
            $(document).on('click', '[name="RefuseOper"]', function (e) {
                var oTr = $(this).parent().parent();
                //银行
                $("#sp-li-drawbank").text(oTr.find("td:eq(0)").text());

                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=1" + "&Title=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box2.OverLay.Color = "rgb(51, 51, 51)";
                            box2.Over = true;
                            box2.OverLay.Opacity = 50;
                            box2.Fixed = true;
                            box2.Center = true;
                            box2.Show();
                            $("#sp-li-drawbank2").text(data["data"]["BankName"]);
                            $("#sp-li-drawName2").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber2").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney2").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName2").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType2").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID2").val(data["data"]["id"]);
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                }
                            });
                        }
                    }
                });


            });

            //拒绝
            $(document).on('click', '[name="RefuseOper2"]', function (e) {
                var oTr = $(this).parent().parent();
                //银行
                $("#sp-li-drawbank").text(oTr.find("td:eq(0)").text());

                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=1" + "&Title=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box5.OverLay.Color = "rgb(51, 51, 51)";
                            box5.Over = true;
                            box5.OverLay.Opacity = 50;
                            box5.Fixed = true;
                            box5.Center = true;
                            box5.Show();
                            $("#sp-li-drawbank5").text(data["data"]["BankName"]);
                            $("#sp-li-drawName5").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber5").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney5").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName5").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType5").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID5").val(data["data"]["id"]);
                            $("#txtRemark5").val(data["data"]["memo"]);
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            });
                        }

                    }
                });


            });


            //退款
            $(document).on('click', '[name="RefuseOper3"]', function (e) {
                var oTr = $(this).parent().parent();
                //银行
                $("#sp-li-drawbank").text(oTr.find("td:eq(0)").text());

                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=1" + "&Title=2";

                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate2',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box6.OverLay.Color = "rgb(51, 51, 51)";
                            box6.Over = true;
                            box6.OverLay.Opacity = 50;
                            box6.Fixed = true;
                            box6.Center = true;
                            box6.Show();
                            $("#sp-li-drawbank6").text(data["data"]["BankName"]);
                            $("#sp-li-drawName6").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber6").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney6").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName6").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType6").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID6").val(data["data"]["id"]);
                            $("#txtRemark6").val(data["data"]["memo"]);
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=3";
                                }
                            });
                        }

                    }
                });


            });


            //待复审
            $(document).on('click', '[name="ReviewOper"]', function (e) {
                var oTr = $(this).parent().parent();
                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=2" + "&Title=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box3.OverLay.Color = "rgb(51, 51, 51)";
                            box3.Over = true;
                            box3.OverLay.Opacity = 50;
                            box3.Fixed = true;
                            box3.Center = true;
                            box3.Show();
                            $("#sp-li-drawbank3").text(data["data"]["BankName"]);
                            $("#sp-li-drawName3").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber3").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney3").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName3").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType3").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID3").val(data["data"]["id"]);
                            button.val("确认拒绝");
                            button.removeAttr("disabled", "disabled");
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=1";
                                }
                            });
                        }

                    }
                });

            });

            //待复审中的待復審btn
            $(document).on('click', '[name="ReviewOper2"]', function (e) {
                var oTr = $(this).parent().parent();
                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=2" + "&Title=1";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=displaywdoprate',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box8.OverLay.Color = "rgb(51, 51, 51)";
                            box8.Over = true;
                            box8.OverLay.Opacity = 50;
                            box8.Fixed = true;
                            box8.Center = true;
                            box8.Show();
                            $("#sp-li-drawbank8").text(data["data"]["BankName"]);
                            $("#sp-li-drawName8").text(data["data"]["bankAccount"]);
                            $("#sp-li-drawNumber8").text(data["data"]["bankNumber"]);
                            $("#sp-li-drawMoney8").text(data["data"]["withdrawAmt"]);
                            $("#sp-li-drawUserName8").text(data["data"]["applyAccount"]);
                            $("#sp-li-drawType8").text(data["data"]["riskType"]);
                            $("#sp-li-drawbankID8").val(data["data"]["id"]);
                            button.val("确认拒绝");
                            button.removeAttr("disabled", "disabled");
                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                                }
                            });
                        }

                    }
                });

            });

            $('#txtRemark1,#txtRemark2,#txtRemark3').keyup(function () {
                $('[name="spancount"]').html($('#txtRemark1')[0].value.length);
            });
            //下载报表	
            $('#J-Download-Report').click(function () {
                var data = group.getFormData();
                var param = '';
                var p = '';
                for (p in data) {
                    if (data.hasOwnProperty(p)) {
                        param += '&' + p + '=' + data[p];
                    }
                }
                window.open("/admin/Fundconfig/index?parma=exappeal" + param);
            });
            //查詢狀態	
            $(document).on('click', '[name="getNowStatus"]', function (e) {
                var oTr = $(this).parent().parent();
                var sdata = "sn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=1" + "&Title=2";
                $.ajax({
                    url: '/admin/Rechargemange/index?parma=cns',
                    dataType: 'json',
                    method: 'post',
                    data: sdata,
                    success: function (data) {
                        if (data.status == "ok") {
                            box7.OverLay.Color = "rgb(51, 51, 51)";
                            box7.Over = true;
                            box7.OverLay.Opacity = 50;
                            box7.Fixed = true;
                            box7.Center = true;
                            box7.Show();

                            $("#sp-li-drawstatus7").val(data["data"]["mowApiStatus"]);
                            $("#sp-li-drawbank7").text(data["data"]["mowApiStatusC"]);
                            $("#sp-li-drawName7").text(data["data"]["mowApiStatusCD"]);

                        } else if (data.status == "error") {
                            msg.show({
                                mask: true,
                                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + data['data'] + '</h3><div style="height:30px;line-height:30px;"></div>',
                                confirmIsShow: 'true',
                                confirmText: '确定',
                                confirmFun: function () {
                                    msg.hide();
                                    window.location = "/admin/Rechargemange/index?parma=sv3&tabIndex=3";
                                }
                            });
                        }

                    }

                });
                var me = this;
                $(me).prop("disabled", true);
                //$(me).css({"background":"#DDDDDD","color":"#DDDDDD"});	
                $(me).css({"color": "#DDDDDD"});
                setTimeout(function () {
                    $(me).css({"color": "#585858"});
                }, 13000);
                setTimeout(function () {
                    $(me).prop("disabled", false);
                }, 13000);

            });

            //查看
            $(document).on('click', '[name="checkDetail"]', function (e) {
                var oTr = $(this).parent().parent();
                var sdata = "appealSn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=1" + "&Title=0";
               
                $.ajax({
                 url:'/admin/Fundconfig/index?parma=appealdetail',				
                 dataType:'json',
                 method:'post',
                 data:sdata,					
                 success:function(data){
                 if(data.status=="ok"){	       
                 box1.OverLay.Color = "rgb(51, 51, 51)" ; 
                 box1.Over = true;   
                 box1.OverLay.Opacity = 50;  
                 box1.Fixed = true;	 
                 box1.Center = true;
                 box1.Show();		

               //回執單
                 $("#sp-li-drawBack").html("");
                 var imgHtml ="<a href='"+data.data.dynamicroot+"/upload/images/"+data.data.uploadImages[0].url+"' download>"
                 imgHtml+="<img src='"+data.data.dynamicroot+"/upload/images/"+data.data.uploadImages[0].url+"' width='42' height='42'/>";
                 imgHtml+="</a>";
                 $("#sp-li-drawBack").html(imgHtml);
                 $("#sp-li-appealCStatus").text(data["data"]["appealStatus"]);   //申訴狀態中文
                 $("#sp-li-appealSid").text(data["data"]["appealSn"]);               //申訴單號
                 $("#sp-li-userName").text(data["data"]["userName"]);              //提現用戶
                 $("#sp-li-drawTime").text(data["data"]["withdrawTime"]);          //提現時間
                 $("#sp-li-drawAmt").text(data["data"]["withdrawAmt"]);            //提現金額
                 $("#sp-li-drawBank").text(data["data"]["bankName"]);              //提現銀行
                 $("#sp-li-drawNumber").text(data["data"]["cardNumber"]);      //銀行卡號
                 $("#sp-li-drawCName").text(data["data"]["drawCName"]);      //提現用戶中文
                 if(data["data"]["appealTips"]!=null){
                     $("#sp-li-appealTips").text(data["data"]["appealTips"]);      //申訴提示                	 
                 }
                 $("#sp-li-txtRemark").html(data["data"]["appealMemo"]);      //備註
                 }
                 else if(data.status=="error"){
                 msg.show({
                 mask: true,
                 content: '<h3 style="height:30px;line-height:30px;text-align:center; ">'+data['data']+'</h3><div style="height:30px;line-height:30px;"></div>',
                 confirmIsShow: 'true',
                 confirmText: '确定',
                 confirmFun: function(){
                 msg.hide();
                 window.location="/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                 }
                 });
                 }
                 
                 }	
                 
                 });	

            });
            
            var showRedisCheck = function(){
    	        msg.show({
    	            mask: true,
    	            content: '<h3 style="height:30px;line-height:30px;text-align:center; ">' + '该数据正在被审核!' + '</h3><div style="height:30px;line-height:30px;"></div>',
    	            confirmIsShow: 'true',
    	            confirmText: '确定',
    	            confirmFun: function () {
    	                msg.hide();
    	            }
    	        });
    	    }
            
             //審核
            $(document).on('click', '[name="appealOK"]', function (e) {
                var oTr = $(this).parent().parent();
                var sdata = "appealSn=" + $.trim(oTr.find("td:eq(0)").text()) + "&type=1" + "&Title=0";
                $.ajax({
                 url:'/admin/Fundconfig/index?parma=appealdetail',				
                 dataType:'json',
                 method:'post',
                 data:sdata,					
                 success:function(data){
                 if(data.status=="ok" && !data.data.isCheck ){
                    box2.OverLay.Color = "rgb(51, 51, 51)" ; 
                    box2.Over = true;   
                    box2.OverLay.Opacity = 50;  
                    box2.Fixed = true;	 
                    box2.Center = true;
                    box2.Show();		
                    //回執單
                    $("#sp-li-drawBack2").html("");
                    var imgHtml ="<a href='"+data.data.dynamicroot+"/upload/images/"+data.data.uploadImages[0].url+"' download>"
                    imgHtml+="<img src='"+data.data.dynamicroot+"/upload/images/"+data.data.uploadImages[0].url+"' width='42' height='42'/>";
                    imgHtml+="</a>";
                    $("#sp-li-drawBack2").html(imgHtml);
                    $("#sp-li-appealCStatus2").text(data["data"]["appealStatus"]);   //申訴狀態中文
                    $("#sp-li-appealSid2").text(data["data"]["appealSn"]);               //申訴單號
                    $("#sp-li-userName2").text(data["data"]["userName"]);              //提現用戶
                    $("#sp-li-drawTime2").text(data["data"]["withdrawTime"]);          //提現時間
                    $("#sp-li-drawAmt2").text(data["data"]["withdrawAmt"]);            //提現金額
                    $("#sp-li-drawBank2").text(data["data"]["bankName"]);              //提現銀行
                    $("#sp-li-drawNumber2").text(data["data"]["cardNumber"]);      //銀行卡號
                    $("#sp-li-drawCName2").text(data["data"]["drawCName"]);      //提現用戶中文
                    $("#sp-li-txtRemark2").html(data["data"]["appealMemo"]);      //備註
                    $("#sp-li-userId").val(data["data"]["userId"]);      //userId
                    if(data["data"]["status"]=='4'){
						$('#appealWait').hide();
						$('#sp-li-appealTips2-wait').hide();
                    }
                 }else if(data.status == "ok" && data.data.isCheck){
                 	showRedisCheck();
                 }else if(data.status=="error"){
	                msg.show({
	                mask: true,
	                content: '<h3 style="height:30px;line-height:30px;text-align:center; ">'+data['data']+'</h3><div style="height:30px;line-height:30px;"></div>',
	                confirmIsShow: 'true',
	                confirmText: '确定',
	                confirmFun: function(){
	                msg.hide();
	                window.location="/admin/Rechargemange/index?parma=sv3&tabIndex=2";
                 }
                 });
                 }
                 
                 }	
                 
                 });	

            });
            
            $(document).on('click', '[name="closeIcoDiv1"]', function(e){
		box1.Close();
		 $("#sp-li-appealTips").text("");
	});
        
            $(document).on('click', '[name="appealStart"]', function (e) {
                    var appealSn = $('#sp-li-appealSid2').text();            
                    var appealMemo = $("#sp-li-txtRemark2").val();
                    var userId = $("#sp-li-userId").val();             
                    var appealTipsResult = $('[name="appealTips2"]:checked').val();
                    var pagetype = $('#pagetype').val();

                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=appealdo',
                        method: 'post',
                        dataType: 'json',
                        data: {appealSn:appealSn,appealStatus:2,appealTipsResult:appealTipsResult,appealMemo:appealMemo,userId:userId},
                        success: function (data) {
                            if (data.isUpdateState == "ok") {
                               alert("申訴審核通過");
                                window.location = "/admin/Fundconfig/index?parma=sv11&tabindex="+pagetype;
                            } else if (data.isUpdateState == "error") {
                                alert("申訴審核失敗");
                            }

                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                             alert("申訴審核很失敗");
                        }

                    });
                });

            	//待複審
            	$('#appealWait').bind('click',function(){
            		var appealSn = $('#sp-li-appealSid2').text();
                    var appealMemo = $("#sp-li-txtRemark2").val();   
                    var appealTipsResult = $('[name="appealTips2"]:checked').val();  
            		$.ajax({
                        url: '/admin/Fundconfig/index?parma=appealdo',
                        method: 'post',
                        dataType: 'json',
                        data: {appealSn:appealSn,appealStatus:4,appealMemo:appealMemo,appealTipsResult:appealTipsResult},
                        success: function (data) {
                            if (data.isUpdateState == "ok") {
                               alert("审核待定通過");
                                window.location = "/admin/Fundconfig/index?parma=sv11";
                            } else if (data.isUpdateState == "error") {
                                alert("审核待定失敗");
                            }
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                             alert("审核待定失敗");
                        }

                    });
                })
            
                 $('[name="appealStop"]').bind('click', function (e) {
                    var appealSn = $('#sp-li-appealSid2').text();                      
                    var appealTipsResult = $('[name="appealTips2"]:checked').val();
                    var userId = $("#sp-li-userId").val();             
                    var appealMemo = $("#sp-li-txtRemark2").val();
                    var pagetype = $('#pagetype').val();  
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=appealdo',
                        method: 'post',
                        dataType: 'json',
                        data: {appealSn:appealSn,appealStatus:3,appealTipsResult:appealTipsResult,appealMemo:appealMemo,userId:userId},
                        success: function (data) {
                            if (data.isUpdateState == "ok") {
                               alert("申訴審核不通過");
                                window.location = "/admin/Fundconfig/index?parma=sv11&tabindex="+pagetype;
                            } else if (data.isUpdateState == "error") {
                                alert("申訴審核失敗");
                            }
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                             alert("申訴審核很失敗");
                        }

                    });
                });
                
                  $('[name="appealTips2"]').bind('click',function(){
                      var eventType = $(this).attr('eventType');
                      if(eventType==0){
                          $('[name="appealStop"]').removeClass('btn-disable').attr('disabled',false);
                          $('[name="appealStart"]').removeClass('btn-disable').attr('disabled',true).addClass('btn-disable');
                           $('[name="appealWait"]').removeClass('btn-disable').attr('disabled',true).addClass('btn-disable');
                      }else if(eventType==1){
                          $('[name="appealStart"]').removeClass('btn-disable').attr('disabled',false);
                          $('[name="appealStop"]').removeClass('btn-disable').attr('disabled',true).addClass('btn-disable');
                          $('[name="appealWait"]').removeClass('btn-disable').attr('disabled',true).addClass('btn-disable');
                      }else{
                       	  $('[name="appealStart"]').removeClass('btn-disable').attr('disabled',true).addClass('btn-disable');
                          $('[name="appealStop"]').removeClass('btn-disable').attr('disabled',true).addClass('btn-disable');
                          $('[name="appealWait"]').removeClass('btn-disable').attr('disabled',false);
                      }
                      
                  });
                /*
                  $(document).on('click', '[name="appealWait"]', function (e) {
                    var appealSn = $('#sp-li-appealSid2').text();                    
                    var sdata = "appealSn=" + appealSn + "&appealStatus=1"+"&appealMemo=暂不处理";
                    $.ajax({
                        url: '/admin/Fundconfig/index?parma=appealdo',
                        method: 'post',
                        dataType: 'json',
                        data: sdata,
                        success: function (data) {
                            if (data.isUpdateState == "ok") {
                               alert("审核待定通過");
                                window.location = "/admin/Fundconfig/index?parma=sv11";
                            } else if (data.isUpdateState == "error") {
                                alert("审核待定失敗");
                            }

                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                             alert("审核待定很失敗");
                        }

                    });
                });*/


        })();
    </script>
{/literal}
</body>
</html>