	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
		  <div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; {$title}</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div  class="ui-tab">	
                        	
  						<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
  						<!-- {if "FUND_RECHARGE_EXCEPTION_ALL"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="AllData"><li>全部</li></span>
                                <!--{else}-->
                                <span><li style="display:none;"></li></span>
                        <!-- {/if} -->
                        <!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="UntreatedData"><li >未处理</li></span>
                                  <!--{else}-->
                                <span><li style="display:none;"></li></span>
                        <!-- {/if} -->
                        <!-- {if "FUND_RECHARGE_EXCEPTION_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="PendingReviewData"><li>待复审</li></span>
                                  <!--{else}-->
                                <span><li style="display:none;"></li></span>
                        <!-- {/if} -->
                        <!-- {if "FUND_RECHARGE_EXCEPTION_HANDLING"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="ProcessingData"><li >处理中</li></span>
                                  <!--{else}-->
                                <span><li style="display:none;"></li></span>
                        <!-- {/if} -->
                        <!-- {if "FUND_RECHARGE_EXCEPTION_SOLVED"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="CompletedData"><li >已完成</li></span>
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
						 
						 
                        <!-- {if "FUND_RECHARGE_EXCEPTION_DISPLAYCOLS"|in_array:$smarty.session.datas.info.acls} -->                           
							<a type="button" id="showDiv" class="btn btn-small" style="float:right;"  >显示选项<b class="btn-inner"></b></a>
					    <!-- {/if} -->
						
						<!-- {if "FUND_RECHARGE_EXCEPTION_ALL_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
							<a class="btn btn-small " id="downloadReport" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
						<!-- {/if} -->	
						<!-- {if "FUND_RECHARGE_EXCEPTION_SOLVED_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
							<a class="btn btn-small " id="downloadReport1" style="float:right;display:none;">下载报表<b class="btn-inner"></b></a>
						<!-- {/if} -->
						
                        </ul>
                        <ul class="ui-form ui-tab-content"  name="DivRules">
                            <li>
								<!-- <h3 class="ui-title"><a class="btn btn-small " id="downloadReport" style="float:left;">下载报表<b class="btn-inner"></b></a></h3>-->
                                <table  id="J-table-data" class="table table-info table-function">
                                    <thead>
                                      <tr>
                                            <th id="J-sp-serial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">MOW异常订单号</div>
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
                                            <th>附言</th>                                               
                                            <th id="J-sp-Daozhang-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行到账时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang-time-1"> - <input type="text" id="sp-input-Daozhang-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                      </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                          <th id="J-sp-request-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-requestmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-requestmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
										</th>
                                        <th>手续费</th>
                                        <th>收款银行</th>                                               
                                        <th  id="J-sp-receivablesCard" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">收款卡</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                          <input type="text" id="receivablesCard" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
											</th>	                                               
											<th  id="J-sp-palyBankName" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">付款银行</div>
                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        	<!-- {foreach from=$bankArray item=data key=key} -->
                                                            	<li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                                                            <!-- {/foreach} -->
                                                        </ul>
                                                </div>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>			
                                        <th>付款卡</th>                                       
                                      	<th  id="J-sp-palyBankUserName" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">付款户名</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" id="palyBankUserName" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>		
                                        <th>充值渠道</th>
                                        <th>充值异常原因</th>
                                        <th>材料</th>                                               
                                        <th id="J-sp-AuditOne-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">一审时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne-1"> - <input type="text" id="sp-input-AuditOne-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>                                               
                                        <th  id="J-sp-auditAdmin" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">一审管理员</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                          <input type="text" id="auditAdmin" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                        </th>	
                                           
                                        <th id="J-sp-Status" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        <!-- {foreach from=$aRechargeStatus key=key item=data} -->
                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                                        <!-- {/foreach} --> 
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>      
                                        <th>备注</th>
                                        <th name="showType">充值用户</th>
                                        <th name="showType">所属总代</th>
                                        <th name="showType">开户行</th>
                                        <th name="showType">开户行地址</th>
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
						<!-------------------------未处理------------------------------------------>
						<ul class="ui-form ui-tab-content" name="DivRules">
							<li>								
								<table id="J-table-data2" class="table table-info table-function">
									<thead>
										<tr>											
                                            <th id="J-sp-serial2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">MOW异常订单号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>                
											<th>附言</th>
											 <th id="J-sp-Daozhang2-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行到账时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang2-time-1"> - <input type="text" id="sp-input-Daozhang2-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                      </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-request2-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-request2money-1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques2tmoney-2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                          </th>
											<th>手续费</th>
											<th>收款银行</th>
											 <th  id="J-sp-receivablesCard2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款卡</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                              <input type="text" id="receivablesCard2" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	        
											 <th  id="J-sp-palyBankName2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款银行</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        	{foreach from=$bankArray item=data key=key}
                                                            <li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                                                            {/foreach}
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>			
											<th>付款卡</th>											
                                            <th  id="J-sp-palyBankUserName2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="palyBankUserName2" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>			
											<th>充值渠道</th>
											<th>充值异常原因</th>
											<th style=" text-align:center">操作人</th>
											<th style=" text-align:center">操作</th>
											<th name='showType' >充值用户</th>
											<th name='showType' >所属总代</th>
											<th name='showType' >开户行</th>
											<th name='showType' >开户行地址</th>
											<!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_ADDCOINS"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="UNTREATED_ADDCOINS" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="UNTREATED_ADDCOINS" value="0"/>
											<!-- {/if} -->
											<!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_RETURNMONEY"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="UNTREATED_RETURNMONEY" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="UNTREATED_RETURNMONEY" value="0"/>
											<!-- {/if} -->
											<!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_UPDATEINFO"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="UNTREATED_UPDATEINFO" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="UNTREATED_UPDATEINFO" value="0"/>
											<!-- {/if} -->
											<!-- {if "FUND_RECHARGE_EXCEPTION_UNTREATED_CONFISCATE"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="UNTREATED_CONFISCATE" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="UNTREATED_CONFISCATE" value="0"/>
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
						<!-----------------------------------------------待复审---------------------------------------->
						<ul class="ui-form ui-tab-content" name="DivRules">							
							 <li>									
								<table id="J-table-data3" class="table table-info table-function">
									<thead>
										<tr>											
                                            <th id="J-sp-serial3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">MOW异常订单号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>           
                                            
											<th>附言</th>
											<th id="J-sp-Daozhang3-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行到账时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang3-time-1"> - <input type="text" id="sp-input-Daozhang3-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                      </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-request3-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-request3money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques3tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>
										    <th>手续费</th>
										    <th>收款银行</th>
										    <th  id="J-sp-receivablesCard3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款卡</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                              <input type="text" id="receivablesCard3" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	         
											<th  id="J-sp-palyBankName3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款银行</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        	{foreach from=$bankArray item=data key=key}
                                                            <li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                                                            {/foreach}
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                      		</th>			
											<th>付款卡</th>
											<th  id="J-sp-palyBankUserName3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="palyBankUserName3" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>		
											<th>充值渠道</th>
											<th>充值异常原因</th>
											<th>材料</th>
											<th id="J-sp-AuditOne3-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">一审时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne3-1"> - <input type="text" id="sp-input-AuditOne3-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>            
											 <th  id="J-sp-auditAdmin3" class="sp-td">
	                                            <div class="sp-td-cont sp-td-cont-b">
	                                                <div class="sp-td-title">一审管理员</div>
	                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                           
	                                                    <li>
	                                                        <div class="input-append">
	                                                          <input type="text" id="auditAdmin3" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
	                                                        </div>
	                                                    </li>
	                                                </ul>
	                                                <span class="sp-filter-close"></span>
	                                            </div>
                                        	</th>	
                                        	<th id="J-sp-Status3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
	                                                        <!-- {foreach from=$aAuitStatus key=key item=data} -->
	                                                        	<!-- {if $key eq '3' or $key eq '8' or $key eq '9'} -->
	                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/if} -->
	                                                        <!-- {/foreach} -->
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th> 
											<th>操作人</th>
											<th style="text-align:center">操作</th>
											<th>备注</th>
											<th name='showType'>充值用户</th>
											<th name='showType'>所属总代</th>
											<th name='showType'>开户行</th>
											<th name='showType'>开户行地址</th>
											<!-- {if "FUND_RECHARGE_EXCEPTION_REVIEW_REVIEW"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="REVIEW_OPRATE" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="REVIEW_OPRATE" value="0"/>
											<!-- {/if} -->
										</tr>
									</thead>
									<tbody id="showInfo3">
                                    </tbody>                                    
                                    <tfoot>
                                        <tr>
                                         <td colspan="24">
                                                <div id="Pagination3" class="pagination" ></div>
                                            </td>
                                        </tr>
                                    </tfoot>
                                </table>
							<li>									
						</ul>
						<!--处理中-->
						<ul class="ui-form ui-tab-content" name="DivRules">
							<li>								
								<table id="J-table-data4" class="table table-info table-function">
									<thead>
										<tr>
                                        	<th id="J-sp-serial4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">MOW异常订单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                       		 <input type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                                </div>
                                            </th>        
											<th>附言</th>
											<th id="J-sp-Daozhang4-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行到账时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang4-time-1"> - <input type="text" id="sp-input-Daozhang4-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                      </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-request4-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-request4money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques4tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>
											<th>手续费</th>
											<th>收款银行</th>
                                            <th  id="J-sp-receivablesCard4" class="sp-td">
                                               <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款卡</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                              <input type="text" id="receivablesCard4" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	      
											<th  id="J-sp-palyBankName4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款银行</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        	{foreach from=$bankArray item=data key=key}
                                                            <li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                                                            {/foreach}
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                      		</th>	                                            
											<th>付款卡</th>
											<th  id="J-sp-palyBankUserName4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="palyBankUserName4" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	
                                            
											<th>充值渠道</th>
											<th>充值异常原因</th>
											<th>材料</th>
											<th id="J-sp-AuditOne4-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">一审时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne4-1"> - <input type="text" id="sp-input-AuditOne4-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>           
											<th  id="J-sp-auditAdmin4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">一审管理员</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                              <input type="text" id="auditAdmin4" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	
											<th>备注</th>
											<th name='showType'>充值用户</th>
											<th name='showType'>所属总代</th>
											<th name='showType'>开户行</th>
											<th name='showType'>开户行地址</th>
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
							<li>	
						</ul>                   
                             
						<!-----------------------------------------------已完成---------------------------------------->
						<ul class="ui-form ui-tab-content" name="DivRules">						
							<li>
							
								<table id="J-table-data5" class="table table-info table-function">
									<thead>
										<tr>
                                        	<th id="J-sp-serial5" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">MOW异常订单号</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                    <li>
                                                        <div class="input-append">
                                                       		 <input type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                                </div>
                                            </th>        
											<th>附言</th>
											<th id="J-sp-Daozhang5-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行到账时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Daozhang5-time-1"> - <input type="text" id="sp-input-Daozhang5-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                      </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-request5-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-request5money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques5tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>
											<th id="J-sp-request6-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">实际支付金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                          
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-request6money1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-reques6tmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>
											<th>手续费</th>
											<th>收款银行</th>
                                            <th  id="J-sp-receivablesCard5" class="sp-td">
                                               <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款卡</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                              <input type="text" id="receivablesCard5" class="input w-2"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	      
											<th  id="J-sp-palyBankName5" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款银行</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        	{foreach from=$bankArray item=data key=key}
                                                            <li data-select-id="{$key}"><a href="#">{$data.name}</a></li>
                                                            {/foreach}
                                                        </ul>
                                                   </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                      		</th>	                                            
											<th>付款卡</th>
											<th  id="J-sp-palyBankUserName5" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="palyBankUserName5" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	
                                            
											<th>充值渠道</th>
											<th>充值异常原因</th>
											<th>材料</th>
											<th id="J-sp-AuditOne5-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">一审时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-AuditOne5-1"> - <input type="text" id="sp-input-AuditOne5-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>           
											<th  id="J-sp-auditAdmin5" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">一审管理员</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                           
                                                        <li>
                                                            <div class="input-append">
                                                              <input type="text" id="auditAdmin5" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	
                                             <th id="J-sp-Status5" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
	                                                        <!-- {foreach from=$aRechargeStatus key=key item=data} -->
	                                                        	<!-- {if $key eq '1' or $key eq '2' or $key eq '5' or $key eq '6' or $key eq '7'} -->
	                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/if} -->
	                                                        <!-- {/foreach} -->
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>  											
											<th>备注</th>
											<th name='showType'>充值用户</th>
											<th name='showType'>所属总代</th>
											<th name='showType'>开户行</th>
											<th name='showType'>开户行地址</th>
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
{include file='/admin/script-base.tpl'} 
{literal}
<script>
(function() {

	var msg = new phoenix.Message(),msg1 = new phoenix.Message();
	var isShowCell=false,group = new phoenix.SuperSearchGroup(),isLock=true;
	//Tab	
	var sindex=phoenix.util.getParam("tabIndex");
	sindex = sindex== null ? 1 : sindex;
	new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:sindex});
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRechargeAppreal');
	
	
	//Show显示与隐藏列操作
	if(isShowCell == false){		
		$('[name="showType"]').hide();
	}
	else{
		$('[name="showType"]').show();
	}	
	
	$('#showDiv').click(function(){
		var showName=$('#showInfo').parent('[name="showType"]').first();
		if($('#showDiv').val() == '显示选项'){
			$('#showDiv').val('隐藏选项');
			$('[name="showType"]').show();
			window.location.hash = '#'+showName;
			isShowCell=true;
		}
		else{
			$('#showDiv').val('显示选项');
			$('[name="showType"]').hide();			
			isShowCell=false;			
		}
	});
  
	//输入数据限制
	//允许输入英文字母和数字
	var allowWordAndNumber = function(v){
		return v.replace(/[^A-Za-z0-9]/g, '');
	};
	//允许输入数字和小数
	var allowNumber = function(){
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
	//获取两个input值
	var getTowInputValue = function(){
		var me = this,ipts = me.dom.find('input[type="text"]'),name = me.name,result = {};
		result[name+'1'] = ipts[0].value;
		result[name+'2'] = ipts[1].value;
		return result;
	};
	//获取select类型值
	var getSelectValue = function(){
		var me = this,li = me.dom.find('li.current'),v = '',result = {};
		if(li.size() > 0){
			v = li.eq(0).attr('data-select-id');
			result[me.name] = v;
		}else{
			//初始化给空
			result[me.name] = '';
		}
		return result;
	};
	if(sindex =='0'){
		AllData();
	} else if(sindex =='1'){
		UntreatedData();
	} else if(sindex =='2'){
		PendingReviewData();
	} else if(sindex =='3'){
		ProcessingData();	
	} else {
		CompletedData();
	} 
	//AllData();
	//全部数据查询
	$('#AllData').click(function(){
		AllData();
	});	
	//未处理查询
	$('#UntreatedData').click(function(){
		UntreatedData();		
	});
	//待复审查询
	$('#PendingReviewData').click(function(){
		PendingReviewData();		
	});	
	//处理查询
	$('#ProcessingData').click(function(){
		ProcessingData();		
	});	
	//已完查询
	$('#CompletedData').click(function(){
		CompletedData();		
	});
	
	
	//---------------------------------------------------异常充值处理(全部)数据处理开始-------------------------------------------------------
	function AllData(){
		$('#downloadReport').css("display","inline");
		$('#downloadReport1').css("display","none");
		//MOW订号
		var movnumber = new phoenix.SuperSearch({
			name: 'movnumber',
			keyCode: 'ctrl+83',
			 el: '#J-sp-serial',
			group: group
		});
		
		//银行到账时间
		var Daozhangtime = new phoenix.SuperSearch({
			name: 'Daozhangtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-Daozhang-time',
			expands:{getFormValue:getTowInputValue},
			group: group
			
		});
		var DaozhangTime1 = $('#sp-input-Daozhang-time-1'),DaozhangTime2 = $('#sp-input-Daozhang-time-2'),DaozhangDt1,DaozhangDt2;
		DaozhangTime1.click(function() {
			DaozhangDt1 = new phoenix.DatePicker({
				input: DaozhangTime1,
				isLockInputType: false
			});
			DaozhangDt1.show();
			DaozhangDt1.addEvent('afterSetValue',
			function() {
				DaozhangTime2.focus();
				DaozhangTime2.click();
			})
		});
		Daozhangtime.addEvent('afterFocus',
		function() {
			DaozhangTime1.click()
		});
		DaozhangTime2.click(function() {
			DaozhangDt2 = new phoenix.DatePicker({
				input: DaozhangTime2,
				isLockInputType: false
			}).show();
		});
		Daozhangtime.addEvent('afterBlur',function() {
			if (DaozhangDt1) {
				DaozhangDt1.hide();
			}
			if (DaozhangDt2) {
				DaozhangDt2.hide();
			}
		});
		//金额
		var requestMoney = new phoenix.SuperSearch({
			name: 'requestMoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-request-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-requestmoney1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-requestmoney2').keyup(allowNumber).blur(allowNumber);
		
		

		//收款方卡
		var receivablesCard = new phoenix.SuperSearch({
			name: 'receivablesCard',
			keyCode: 'ctrl+83',
			 el: '#J-sp-receivablesCard',
			group: group
		});	
		//付款银行
		var palyBankName = new phoenix.SuperSearch({
			name: 'palyBankName',
			keyCode: 'ctrl+83',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-palyBankName',
			group: group,
			expands:{getFormValue:getSelectValue}
		});	

		//付款户名
		var palyBankUserName = new phoenix.SuperSearch({
			name: 'palyBankUserName',
			keyCode: 'ctrl+83',
			 el: '#J-sp-palyBankUserName',
			group: group
		});	
		
		//一审时间	
		var AuditOne = new phoenix.SuperSearch({
			name: 'AuditOne',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-AuditOne-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var AuditOneTime1 = $('#sp-input-AuditOne-1'),AuditOneTime2 = $('#sp-input-AuditOne-2'),AuditOneDt1,AuditOneDt2;
		AuditOneTime1.click(function() {
			AuditOneDt1 = new phoenix.DatePicker({
				input: AuditOneTime1,
				isLockInputType: false
			});
			AuditOneDt1.show();
			AuditOneDt1.addEvent('afterSetValue',
			function() {
				AuditOneTime2.focus();
				AuditOneTime2.click();
			})
		});
		AuditOne.addEvent('afterFocus',
		function() {
			AuditOneTime1.click()
		});
		AuditOneTime2.click(function() {
			AuditOneDt2 = new phoenix.DatePicker({
				input: AuditOneTime2,
				isLockInputType: false
			}).show();
		});
		AuditOne.addEvent('afterBlur',function() {
			if (AuditOneDt1) {
				AuditOneDt1.hide();
			}
			if (AuditOneDt2) {
				AuditOneDt2.hide();
			}
		});
		//一审管理员
		var auditAdmin = new phoenix.SuperSearch({
			name: 'auditAdmin',
			keyCode: 'ctrl+83',
			 el: '#J-sp-auditAdmin',
			group: group
		});		
		//状态
		 var statusType = new phoenix.SuperSearch({
			name: 'statusType',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-Status',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		//数据处理状态标识(全部)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 0;	
				}}
		});
		
	
		SelectByWhereInfo("0");	
		//提交数据
		group.removeEvent('dataChange');
		group.addEvent('dataChange', function(){
			SelectByWhereInfo("0");				
		});	
		
		//每页记录数更改事件
		$("#per_page").change(function(){
			SelectByWhereInfo("0");			
		
		});		
		
		function SelectByWhereInfo(pages) {
			//当前页数据行数			
			 var  per_page_number = parseInt($("#per_page").val());
			 
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});
			
			 var dataArea = $('[name="DivRules"]'),formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data>tbody").html("");	
			 $("#Pagination").hide();		
			 $.ajax({
				url:'/admin/Rechargemange/index?parma=dv1',				
				method:'post',
				data:formData,
				beforeSend:function(){		
				     isLock=false;		
					 TableStyle("showInfo",17,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.text.length>0){
						$("#Pagination").show();
						var re = data.text;						
						var recordNum = 0;
						recordNum = data.count[0].recordNum;
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
							
								html += "<tr><td>" + re[i].mcSn + "</td>";
								html += "<td>" +getSubString(re[i].memo)  + "</td>";
								html += "<td>" + re[i].realChargeTime + "</td>";
								html += "<td>" + re[i].realChargeAmt + "</td>";
								html += "<td>" + re[i].mcBankFee + "</td>";							
								html += "<td>" + re[i].rcvBank + "</td>";
								html += "<td>" + re[i].rcvCardNumber + "</td>";
								html += "<td>" + re[i].bankInfoName + "</td>";							
								html += "<td>" + re[i].cardNumber + "</td>";							
								html += "<td>" + re[i].cardAcct + "</td>";
								html += "<td>" + re[i].mcChannel + "</td>";							
								html += "<td>附言违规</td>";
								html += "<td>" + re[i].attachment + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";
								html += "<td>" + re[i].applyAccount + "</td>";
								html += "<td>"+re[i].Status+"</td>";
								html += "<td>" + getSubString(re[i].apprMemo) + "</td>";							
								html += "<td name='showType' >" + re[i].userName + "</td>";
								html += "<td name='showType' >" + re[i].agentName + "</td>";
								html += "<td name='showType'>" + re[i].bankName + "</td>";
								html += "<td name='showType' >" + re[i].bankAddr + "</td></tr>";	
													
							
							
						});
						$("#J-table-data>tbody").html(html);
						if(isShowCell == false){//状态判定加载相关数据		
							$('[name="showType"]').hide();
						}
						else{
							$('[name="showType"]').show();
						}
							
					}else{
						 $("#Pagination").hide();  
						 TableStyle("J-table-data>tbody",17,2,"没有相应数据");
					}				
				}, 
				complete: function()
				{
					isLock=true;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data>tbody",17,2,"数据异常");
				}
			});			
		}	
	}
	
	
	//---------------------------------------------------(全部)数据处理结束------------------------------------------------------
	//---------------------------------------------------(未处理)数据处理开始-------------------------------------------------------
	function UntreatedData(){
		$('#downloadReport').css("display","none");
		$('#downloadReport1').css("display","none");
		//MOW订号
		var movnumber2 = new phoenix.SuperSearch({
			name: 'movnumber',
			keyCode: 'ctrl+83',
			 el: '#J-sp-serial2',
			group: group
		});
		//银行到账时间
		var Daozhang2time = new phoenix.SuperSearch({
			name: 'Daozhangtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-Daozhang2-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var Daozhang2Time1 = $('#sp-input-Daozhang2-time-1'),Daozhang2Time2 = $('#sp-input-Daozhang2-time-2'),Daozhang2Dt1,Daozhang2Dt2;
		Daozhang2Time1.click(function() {
			Daozhang2Dt1 = new phoenix.DatePicker({
				input: Daozhang2Time1,
				isLockInputType: false
			});
			Daozhang2Dt1.show();
			Daozhang2Dt1.addEvent('afterSetValue',
			function() {
				Daozhang2Time2.focus();
				Daozhang2Time2.click();
			})
		});
		Daozhang2time.addEvent('afterFocus',
		function() {
			Daozhang2Time1.click()
		});
		Daozhang2Time2.click(function() {
			Daozhang2Dt2 = new phoenix.DatePicker({
				input: Daozhang2Time2,
				isLockInputType: false
			}).show();
		});
		Daozhang2time.addEvent('afterBlur',function() {
			if (Daozhang2Dt1) {
				Daozhang2Dt1.hide();
			}
			if (Daozhang2Dt2) {
				Daozhang2Dt2.hide();
			}
		});	
		//金额
		var requestMoney2 = new phoenix.SuperSearch({
			name: 'requestMoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-request2-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-request2money-1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-request2money-2').keyup(allowNumber).blur(allowNumber);
		//收款方卡
		var receivablesCard2 = new phoenix.SuperSearch({
			name: 'receivablesCard',
			keyCode: 'ctrl+83',
			 el: '#J-sp-receivablesCard2',
			group: group
		});	
		//付款银行
		var palyBankName2 = new phoenix.SuperSearch({
			name: 'palyBankName',
			keyCode: 'ctrl+83',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-palyBankName2',
			group: group,
			expands:{getFormValue:getSelectValue}
		});	
		//付款户名
		var palyBankUserName2 = new phoenix.SuperSearch({
			name: 'palyBankUserName2',
			keyCode: 'ctrl+83',
			 el: '#J-sp-palyBankUserName2',
			group: group
		});		
		//数据处理状态标识(未处理)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 1;	
				}}
		});	
		//提交数据
		SelectByWhereInfo2("0");	
		group.removeEvent('dataChange');
		group.addEvent('dataChange', function(){
			SelectByWhereInfo2("0");				
		});	
		
		$("#per_page").change(function(){
			SelectByWhereInfo2("0");			
		
		});		
		
		
		function SelectByWhereInfo2(pages) {
			//当前页数据行数
			 var  per_page_number = parseInt($("#per_page").val());
			 
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group2对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});			
			 var dataArea2 = $('[name="DivRules"]'),formData2 = group.getFormData(),mask2 = phoenix.Mask.getInstance();			
			 $("#J-table-data2>tbody").html("");	
			 $("#Pagination2").hide(); 			
			 $.ajax({
				url:'/admin/Rechargemange/index?parma=dv1',
				dataType:'json',
				method:'post',
				data:formData2,
				beforeSend:function(){			
				    isLock=false;	
					TableStyle("showInfo2",19,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.text.length>0){
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
							callback: SelectByWhereInfo2
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {								
							html += "<tr><td>" + re[i].mcSn + "</td>";
							html += "<td>" + getSubString(re[i].memo) + "</td>";
							html += "<td>" + re[i].realChargeTime + "</td>";
							html += "<td>" + re[i].realChargeAmt + "</td>";
							html += "<td>" + re[i].mcBankFee + "</td>";							
							html += "<td>" + re[i].rcvBank + "</td>";
							html += "<td>" + re[i].rcvCardNumber + "</td>";
							html += "<td>" + re[i].bankInfoName + "</td>";							
							html += "<td>" + re[i].cardNumber + "</td>";							
							html += "<td>" + re[i].cardAcct + "</td>";
							html += "<td>" + re[i].mcChannel + "</td>";							
							html += "<td>附言违规</td>";	//re[i].isAbleOprate
							html += "<td>" + re[i].currApprer + "</td>";							
							if(re[i].isAbleOprate == true){
								if(re[i].myLocked == true){
									html += "<td>";
									if($("#UNTREATED_ADDCOINS").val()=='1'){
										html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=1&mcSn="+re[i].mcSn+"'><font color='#25a38a'>加游戏币</font><b class='btn-inner'></b></a>&nbsp;";
									}
									if($("#UNTREATED_RETURNMONEY").val()=='1'){
										html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=2&mcSn="+re[i].mcSn+"'><font color='#25a38a'>退 款</font><b class='btn-inner'></b></a>&nbsp;";
									}
									if($("#UNTREATED_CONFISCATE").val()=='1'){
										html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=5&mcSn="+re[i].mcSn+"'><font color='#25a38a'>没 收</font><b class='btn-inner'></b></a>";
									}
									html += "</td>";
								} else {
									html += "<td>";
									if($("#UNTREATED_ADDCOINS").val()=='1'){
										html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=1&mcSn="+re[i].mcSn+"'>加游戏币<b class='btn-inner'></b></a>&nbsp;";
									}
									if($("#UNTREATED_RETURNMONEY").val()=='1'){
										html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=2&mcSn="+re[i].mcSn+"'>退 款<b class='btn-inner'></b></a>&nbsp;";
									}
									//if($("#UNTREATED_UPDATEINFO").val()=='1'){
									//	html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=3&mcSn="+re[i].mcSn+"'>补充信息待复审<b class='btn-inner'></b></a>&nbsp;";
									//}
									if($("#UNTREATED_CONFISCATE").val()=='1'){
										html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&optionType=5&mcSn="+re[i].mcSn+"'>没 收<b class='btn-inner'></b></a>";
									}
									html += "</td>";
								}
											
							}else{
								html += "<td>";
								if($("#UNTREATED_ADDCOINS").val()=='1'){
									html += "<a class='btn btn-disable'>加游戏币<b class='btn-inner'></b></a>&nbsp;";
								}
								if($("#UNTREATED_RETURNMONEY").val()=='1'){
									html += "<a class='btn btn-disable'>退 款<b class='btn-inner'></b></a>&nbsp;";
								}
								//if($("#UNTREATED_UPDATEINFO").val()=='1'){
								//	html += "<a class='btn btn-disable' >补充信息待复审<b class='btn-inner'></b></a>&nbsp;";
								//}
								if($("#UNTREATED_CONFISCATE").val()=='1'){
									html += "<a class='btn btn-disable'>没 收<b class='btn-inner'></b></a>";
								}
								html += "</td>";
							}
							html += "<td name='showType' >" + re[i].userName + "</td>";
							html += "<td name='showType' >" + re[i].agentName + "</td>";
							html += "<td name='showType'>" + re[i].bankName + "</td>";
							html += "<td name='showType' >" + re[i].bankAddr + "</td></tr>";	
						});
						$("#J-table-data2>tbody").html(html);					
						if(isShowCell == false){//状态判定加载相关数据		
							$('[name="showType"]').hide();
						}
						else{
							$('[name="showType"]').show();
						}
							
					}else{
						 $("#Pagination2").hide();    
						 TableStyle("J-table-data2>tbody",17,2,"没有相应数据");
					}				
				}, 
				complete: function()
				{
					isLock=true;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data2>tbody",17,2,"数据异常");
				}
			});			
		}	
	}
	
	
	//---------------------------------------------------异常充值(未处理)数据处理结束------------------------------------------------------
	
	//---------------------------------------------------(待复审)数据处理开始-------------------------------------------------------
	function PendingReviewData(){
		$('#downloadReport').css("display","none");
		$('#downloadReport1').css("display","none");
		//MOW订号
		var movnumber3 = new phoenix.SuperSearch({
			name: 'movnumber',
			keyCode: 'ctrl+83',
			 el: '#J-sp-serial3',
			group: group
		});
		
		//银行到账时间
		var Daozhang3time = new phoenix.SuperSearch({
			name: 'Daozhangtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-Daozhang3-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var Daozhang3Time1 = $('#sp-input-Daozhang3-time-1'),Daozhang3Time2 = $('#sp-input-Daozhang3-time-2'),Daozhang3Dt1,Daozhang3Dt2;
		Daozhang3Time1.click(function() {
			Daozhang3Dt1 = new phoenix.DatePicker({
				input: Daozhang3Time1,
				isLockInputType: false
			});
			Daozhang3Dt1.show();
			Daozhang3Dt1.addEvent('afterSetValue',
			function() {
				Daozhang3Time2.focus();
				Daozhang3Time2.click();
			})
		});
		Daozhang3time.addEvent('afterFocus',
		function() {
			Daozhang3Time1.click()
		});
		Daozhang3Time2.click(function() {
			Daozhang3Dt2 = new phoenix.DatePicker({
				input: Daozhang3Time2,
				isLockInputType: false
			}).show();
		});
		Daozhang3time.addEvent('afterBlur',function() {
			if (Daozhang3Dt1) {
				Daozhang3Dt1.hide();
			}
			if (Daozhang3Dt2) {
				Daozhang3Dt2.hide();
			}
		});
		//金额
		var requestMoney3 = new phoenix.SuperSearch({
			name: 'requestMoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-request3-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-request3money-1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-request3money-2').keyup(allowNumber).blur(allowNumber);
		//收款方卡
		var receivablesCard3 = new phoenix.SuperSearch({
			name: 'receivablesCard',
			keyCode: 'ctrl+83',
			 el: '#J-sp-receivablesCard3',
			group: group
		});	
		//付款银行
		var palyBankName3 = new phoenix.SuperSearch({
			name: 'palyBankName',
			keyCode: 'ctrl+83',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-palyBankName3',
			group: group,
			expands:{getFormValue:getSelectValue}
		});	
		//付款户名<a href="../../../../../css">css</a>
		var palyBankUserName3 = new phoenix.SuperSearch({
			name: 'palyBankUserName',
			keyCode: 'ctrl+83',
			 el: '#J-sp-palyBankUserName3',
			group: group
		});	
		//一审时间	
		var AuditOne3 = new phoenix.SuperSearch({
			name: 'AuditOne',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-AuditOne3-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var AuditOne3Time1 = $('#sp-input-AuditOne3-1'),AuditOne3Time2 = $('#sp-input-AuditOne3-2'),AuditOne3Dt1,AuditOne3Dt2;
		AuditOne3Time1.click(function() {
			AuditOne3Dt1 = new phoenix.DatePicker({
				input: AuditOne3Time1,
				isLockInputType: false
			});
			AuditOne3Dt1.show();
			AuditOne3Dt1.addEvent('afterSetValue',
			function() {
				AuditOne3Time2.focus();
				AuditOne3Time2.click();
			})
		});
		AuditOne3.addEvent('afterFocus',
		function() {
			AuditOne3Time1.click()
		});
		AuditOne3Time2.click(function() {
			AuditOne3Dt2 = new phoenix.DatePicker({
				input: AuditOne3Time2,
				isLockInputType: false
			}).show();
		});
		AuditOne3.addEvent('afterBlur',function() {
			if (AuditOne3Dt1) {
				AuditOne3Dt1.hide();
			}
			if (AuditOne3Dt2) {
				AuditOne3Dt2.hide();
			}
		});
		//一审管理员
		var auditAdmin3 = new phoenix.SuperSearch({
			name: 'auditAdmin',
			keyCode: 'ctrl+83',
			 el: '#J-sp-auditAdmin3',
			group: group
		});		
		//状态
		 var statusType = new phoenix.SuperSearch({
			name: 'statusType',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-Status3',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		//数据处理状态标识(待复审)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 2;	
				}}
		});
		SelectByWhereInfo3("0");
		group.removeEvent('dataChange');
		group.addEvent('dataChange', function(){
			SelectByWhereInfo3("0");				
		});	
		
		$("#per_page").change(function(){
			SelectByWhereInfo3("0");			
		
		});	
		
		
		function SelectByWhereInfo3(pages) {
			//当前页数据行数
			var  per_page_number = parseInt($("#per_page").val());	
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group2对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});
			
			 var dataArea3 = $('[name="DivRules"]'),formData3 = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data3>tbody").html("");	
			 $("#Pagination3").hide(); 			
			 $.ajax({
				url:'/admin/Rechargemange/index?parma=dv1',
				dataType:'json',
				method:'post',
				data:formData3,
				beforeSend:function(){	
				    isLock=false;
					TableStyle("showInfo3",24,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.text.length>0){
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
							callback: SelectByWhereInfo3
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {	
							//if(re[i].Status=='2'){
								html += "<tr><td>" + re[i].mcSn + "</td>";
								html += "<td>" + getSubString(re[i].memo) + "</td>";
								html += "<td>" + re[i].realChargeTime + "</td>";
								html += "<td>" + re[i].realChargeAmt + "</td>";
								html += "<td>" + re[i].mcBankFee + "</td>";							
								html += "<td>" + re[i].rcvBank + "</td>";
								html += "<td>" + re[i].rcvCardNumber + "</td>";
								html += "<td>" + re[i].bankInfoName + "</td>";							
								html += "<td>" + re[i].cardNumber + "</td>";							
								html += "<td>" + re[i].cardAcct + "</td>";
								html += "<td>" + re[i].mcChannel + "</td>";						
								html += "<td>附言违规</td>";
								html += "<td>" + re[i].attachment + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";
								html += "<td>" + re[i].applyAccount + "</td>";
								html += "<td>" + re[i].Status + "</td>";
								html += "<td>" + re[i].currApprer + "</td>";
								if(re[i].isAbleOprate == true){
									html += "<td>";
									if(re[i].myLocked == true){
										if($("#REVIEW_OPRATE").val()=='1'){
											html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&mcSn=" + re[i].mcSn + "' style='position:initial' ><font color='#25a38a'>复 审</font><b class='btn-inner'></b></a>&nbsp;";
										}
									} else {
										if($("#REVIEW_OPRATE").val()=='1'){
											html += "<a class='btn btn-small' href='/admin/Rechargemange/index?parma=disauit&mcSn=" + re[i].mcSn + "' style='position:initial' >复 审<b class='btn-inner'></b></a>&nbsp;";
										}
									}
									html += "</td>";										
								}else{
									html += "<td>";
									if($("#REVIEW_OPRATE").val()=='1'){
										html += "<a class='btn btn-disable' style='position:initial' >复  审<b class='btn-inner'></b></a>&nbsp;";
									}
									html += "</td>";
								}
								html += "<td>" + getSubString(re[i].apprMemo)+ "</td>";							
								html += "<td name='showType' >" + re[i].userName + "</td>";
								html += "<td name='showType' >" + re[i].agentName + "</td>";
								html += "<td name='showType'>" + re[i].bankName + "</td>";
								html += "<td name='showType' >" + re[i].bankAddr + "</td></tr>";	
							//}	
						});
						$("#J-table-data3>tbody").html(html);
						
						if(isShowCell == false){//状态判定加载相关数据		
							$('[name="showType"]').hide();
						}
						else{
							$('[name="showType"]').show();
						}
							
					}else{
						 $("#Pagination3").hide();    
						 TableStyle("J-table-data3>tbody",24,2,"没有相应数据");  
					}				
				}, 
				complete: function()
				{
					isLock=true;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data3>tbody",24,2,"数据异常");  
				}
			});			
		}		
	}
	
	//---------------------------------------------------(待复审)数据处理结束-----------------------------------------------------
	//---------------------------------------------------(处理中)数据处理开始-----------------------------------------------------
	function ProcessingData(){
		$('#downloadReport').css("display","none");
		$('#downloadReport1').css("display","none");
		//MOW订号
		var movnumber4 = new phoenix.SuperSearch({
			name: 'movnumber',
			keyCode: 'ctrl+83',
			 el: '#J-sp-serial4',
			group: group
		});
		
		//银行到账时间
		var Daozhang4time = new phoenix.SuperSearch({
			name: 'Daozhangtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-Daozhang4-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var Daozhang4Time1 = $('#sp-input-Daozhang4-time-1'),Daozhang4Time2 = $('#sp-input-Daozhang4-time-2'),Daozhang4Dt1,Daozhang4Dt2;
		Daozhang4Time1.click(function() {
			Daozhang4Dt1 = new phoenix.DatePicker({
				input: Daozhang4Time1,
				isLockInputType: false
			});
			Daozhang4Dt1.show();
			Daozhang4Dt1.addEvent('afterSetValue',
			function() {
				Daozhang4Time2.focus();
				Daozhang4Time2.click();
			})
		});
		Daozhang4time.addEvent('afterFocus',
		function() {
			Daozhang4Time1.click()
		});
		Daozhang4Time2.click(function() {
			Daozhang4Dt2 = new phoenix.DatePicker({
				input: Daozhang4Time2,
				isLockInputType: false
			}).show();
		});
		Daozhang4time.addEvent('afterBlur',function() {
			if (Daozhang4Dt1) {
				Daozhang4Dt1.hide();
			}
			if (Daozhang4Dt2) {
				Daozhang4Dt2.hide();
			}
		});
		//金额
		var requestMoney4 = new phoenix.SuperSearch({
			name: 'requestMoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-request4-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-request4money-1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-request4money-2').keyup(allowNumber).blur(allowNumber);
		//收款方卡
		var receivablesCard4 = new phoenix.SuperSearch({
			name: 'receivablesCard',
			keyCode: 'ctrl+83',
			 el: '#J-sp-receivablesCard4',
			group: group
		});	
		//付款银行
		var palyBankName4 = new phoenix.SuperSearch({
			name: 'palyBankName',
			keyCode: 'ctrl+83',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-palyBankName4',
			group: group,
			expands:{getFormValue:getSelectValue}
		});	
		//付款户名
		var palyBankUserName4 = new phoenix.SuperSearch({
			name: 'palyBankUserName',
			keyCode: 'ctrl+83',
			 el: '#J-sp-palyBankUserName4',
			group: group
		});	
		//一审时间	
		var AuditOne4 = new phoenix.SuperSearch({
			name: 'AuditOne',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-AuditOne4-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var AuditOne4Time1 = $('#sp-input-AuditOne4-1'),AuditOne4Time2 = $('#sp-input-AuditOne4-2'),AuditOne4Dt1,AuditOne4Dt2;
		AuditOne4Time1.click(function() {
			AuditOne4Dt1 = new phoenix.DatePicker({
				input: AuditOne4Time1,
				isLockInputType: false
			});
			AuditOne4Dt1.show();
			AuditOne4Dt1.addEvent('afterSetValue',
			function() {
				AuditOne4Time2.focus();
				AuditOne4Time2.click();
			})
		});
		AuditOne4.addEvent('afterFocus',
		function() {
			AuditOne4Time1.click()
		});
		AuditOne4Time2.click(function() {
			AuditOne4Dt2 = new phoenix.DatePicker({
				input: AuditOne4Time2,
				isLockInputType: false
			}).show();
		});
		AuditOne4.addEvent('afterBlur',function() {
			if (AuditOne4Dt1) {
				AuditOne4Dt1.hide();
			}
			if (AuditOne4Dt2) {
				AuditOne4Dt2.hide();
			}
		});
		//一审管理员
		var auditAdmin4 = new phoenix.SuperSearch({
			name: 'auditAdmin',
			keyCode: 'ctrl+83',
			 el: '#J-sp-auditAdmin4',
			group: group
		});		
		//数据处理状态标识(处理中)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 3;	
				}}
		});
		SelectByWhereInfo4("0");	
		group.removeEvent('dataChange');
		group.addEvent('dataChange', function(){
			SelectByWhereInfo4("0");				
		});	
		
		$("#per_page").change(function(){
			SelectByWhereInfo4("0");			
		
		});	
		function SelectByWhereInfo4(pages) {
			//当前页数据行数
			var  per_page_number = parseInt($("#per_page").val());	
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group2对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});
			
			 var formData4 = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data4>tbody").html("");	
			 $("#Pagination4").hide();  			
			 $.ajax({
				url:'/admin/Rechargemange/index?parma=dv1',
				dataType:'json',
				method:'post',
				data:formData4,
				beforeSend:function(){	
				    isLock=false;
					TableStyle("showInfo4",17,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.text.length>0){
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
							callback: SelectByWhereInfo4
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {	
							//if(re[i].Status=='2'){
								html += "<tr><td>" + re[i].mcSn + "</td>";
								html += "<td>" + getSubString(re[i].memo) + "</td>";
								html += "<td>" + re[i].realChargeTime + "</td>";
								html += "<td>" + re[i].realChargeAmt + "</td>";
								html += "<td>" + re[i].mcBankFee + "</td>";							
								html += "<td>" + re[i].rcvBank + "</td>";
								html += "<td>" + re[i].rcvCardNumber + "</td>";
								html += "<td>" + re[i].bankInfoName + "</td>";							
								html += "<td>" + re[i].cardNumber + "</td>";							
								html += "<td>" + re[i].cardAcct + "</td>";
								html += "<td>" + re[i].mcChannel + "</td>";						
								html += "<td>附言违规</td>";
								html += "<td>" + re[i].attachment + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";
								html += "<td>" + re[i].applyAccount + "</td>";
								html += "<td>" + getSubString(re[i].apprMemo)+ "</td>";							
								html += "<td name='showType' >" + re[i].userName + "</td>";
								html += "<td name='showType' >" + re[i].agentName + "</td>";
								html += "<td name='showType'>" + re[i].bankName + "</td>";
								html += "<td name='showType' >" + re[i].bankAddr + "</td></tr>";	
							//}	
						});
						$("#J-table-data4>tbody").html(html);					
						if(isShowCell == false){//状态判定加载相关数据		
							$('[name="showType"]').hide();
						}
						else{
							$('[name="showType"]').show();
						}
							
					}else{
						 $("#Pagination4").hide();  
						 TableStyle("J-table-data4>tbody",17,2,"没有相应数据");
					}				
				}, 
				complete: function()
				{
					isLock=true;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data4>tbody",17,2,"数据异常");
				}
			});			
		}		
	}
	
	//---------------------------------------------------(处理中)数据处理结束-----------------------------------------------------
	//---------------------------------------------------(已完成)数据处理开始-----------------------------------------------------
	function CompletedData(){
		$('#downloadReport').css("display","none");
		$('#downloadReport1').css("display","inline");		
		
		//MOW订号
		var movnumber5 = new phoenix.SuperSearch({
			name: 'movnumber',
			keyCode: 'ctrl+83',
			 el: '#J-sp-serial5',
			group: group
		});
		
		//银行到账时间
		var Daozhang5time = new phoenix.SuperSearch({
			name: 'Daozhangtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-Daozhang5-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var Daozhang5Time1 = $('#sp-input-Daozhang5-time-1'),Daozhang5Time2 = $('#sp-input-Daozhang5-time-2'),Daozhang5Dt1,Daozhang5Dt2;
		Daozhang5Time1.click(function() {
			Daozhang5Dt1 = new phoenix.DatePicker({
				input: Daozhang5Time1,
				isLockInputType: false
			});
			Daozhang5Dt1.show();
			Daozhang5Dt1.addEvent('afterSetValue',
			function() {
				Daozhang5Time2.focus();
				Daozhang5Time2.click();
			})
		});
		Daozhang5time.addEvent('afterFocus',
		function() {
			Daozhang5Time1.click()
		});
		Daozhang5Time2.click(function() {
			Daozhang5Dt2 = new phoenix.DatePicker({
				input: Daozhang5Time2,
				isLockInputType: false
			}).show();
		});
		Daozhang5time.addEvent('afterBlur',function() {
			if (Daozhang5Dt1) {
				Daozhang5Dt1.hide();
			}
			if (Daozhang5Dt2) {
				Daozhang5Dt2.hide();
			}
		});
		//金额
		var requestMoney5 = new phoenix.SuperSearch({
			name: 'requestMoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-request5-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		//实际支付金额
		var requestMoney6 = new phoenix.SuperSearch({
			name: 'requestRealMoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-request6-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-request5money1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-request5money2').keyup(allowNumber).blur(allowNumber);
		$('#J-input-request6money1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-request6money2').keyup(allowNumber).blur(allowNumber);
		//收款方卡
		var receivablesCard5 = new phoenix.SuperSearch({
			name: 'receivablesCard',
			keyCode: 'ctrl+83',
			 el: '#J-sp-receivablesCard5',
			group: group
		});	
		//付款银行
		var palyBankName5 = new phoenix.SuperSearch({
			name: 'palyBankName',
			keyCode: 'ctrl+83',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-palyBankName5',
			group: group,
			expands:{getFormValue:getSelectValue}
		});	
		//付款户名
		var palyBankUserName5 = new phoenix.SuperSearch({
			name: 'palyBankUserName',
			keyCode: 'ctrl+83',
			 el: '#J-sp-palyBankUserName5',
			group: group
		});	
		//一审时间	
		var AuditOne5 = new phoenix.SuperSearch({
			name: 'AuditOne',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-AuditOne5-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var AuditOne5Time1 = $('#sp-input-AuditOne5-1'),AuditOne5Time2 = $('#sp-input-AuditOne5-2'),AuditOne5Dt1,AuditOne5Dt2;
		AuditOne5Time1.click(function() {
			AuditOne5Dt1 = new phoenix.DatePicker({
				input: AuditOne5Time1,
				isLockInputType: false
			});
			AuditOne5Dt1.show();
			AuditOne5Dt1.addEvent('afterSetValue',
			function() {
				AuditOne5Time2.focus();
				AuditOne5Time2.click();
			})
		});
		AuditOne5.addEvent('afterFocus',
		function() {
			AuditOne5Time1.click()
		});
		AuditOne5Time2.click(function() {
			AuditOne5Dt2 = new phoenix.DatePicker({
				input: AuditOne5Time2,
				isLockInputType: false
			}).show();
		});
		AuditOne5.addEvent('afterBlur',function() {
			if (AuditOne5Dt1) {
				AuditOne5Dt1.hide();
			}
			if (AuditOne5Dt2) {
				AuditOne5Dt2.hide();
			}
		});
		//一审管理员
		var auditAdmin5 = new phoenix.SuperSearch({
			name: 'auditAdmin',
			keyCode: 'ctrl+83',
			 el: '#J-sp-auditAdmin5',
			group: group
		});		
		//数据处理状态标识(已完成)
		 var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 4;	
				}}
		});
		SelectByWhereInfo5("0");	
		group.removeEvent('dataChange');
		group.addEvent('dataChange', function(){
			SelectByWhereInfo5("0");				
		});	
		
		//状态
		 var statusType = new phoenix.SuperSearch({
			name: 'statusType',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-Status5',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		
		$("#per_page").change(function(){
			SelectByWhereInfo5("0");			
		
		});	
		
		function SelectByWhereInfo5(pages) {
			//当前页数据行数
			var  per_page_number = parseInt($("#per_page").val());	
			//放入group对象中(当前页)		
			var page  = new phoenix.SuperSearch({
				name: 'page',    		
				group: group,			
				expands:{getValue:function(){
					return pages;	
				}}
			});		
			//放入group2对象中(当前页行数)	
			var perPageNum  = new phoenix.SuperSearch({
				name: 'perPageNum', 
				group: group,			
				expands:{getValue:function(){
					return per_page_number;	
				}}
			});
			
			 var formData5 = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data5>tbody").html("");	
			 $("#Pagination5").hide(); 			
			 $.ajax({
				url:'/admin/Rechargemange/index?parma=dv1',
				dataType:'json',
				method:'post',
				data:formData5,
				beforeSend:function(){	
				    isLock=false;
					TableStyle("showInfo5",19,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.text.length>0){
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
							callback: SelectByWhereInfo5
						});
						var html = "";
						//数据填充
						$.each(re, function (i) {	
							//if(re[i].Status=='2'){
								html += "<tr><td>" + re[i].mcSn + "</td>";
								html += "<td>" + getSubString(re[i].memo) + "</td>";
								html += "<td>" + re[i].realChargeTime + "</td>";
								html += "<td>" + re[i].realChargeAmt + "</td>";
								html += "<td>" + re[i].refundAmt + "</td>";
								html += "<td>" + re[i].mcBankFee + "</td>";							
								html += "<td>" + re[i].rcvBank + "</td>";
								html += "<td>" + re[i].rcvCardNumber + "</td>";
								html += "<td>" + re[i].bankInfoName + "</td>";							
								html += "<td>" + re[i].cardNumber + "</td>";							
								html += "<td>" + re[i].cardAcct + "</td>";
								html += "<td>" + re[i].mcChannel + "</td>";						
								html += "<td>附言违规</td>";
								html += "<td>" + re[i].attachment + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";
								html += "<td>" + re[i].applyAccount + "</td>";
								html += "<td>" + re[i].Status + "</td>";
								html += "<td>" + getSubString(re[i].apprMemo)+ "</td>";							
								html += "<td name='showType' >" + re[i].userName + "</td>";
								html += "<td name='showType' >" + re[i].agentName + "</td>";
								html += "<td name='showType'>" + re[i].bankName + "</td>";
								html += "<td name='showType' >" + re[i].bankAddr + "</td></tr>";	
							//}	
						});
						$("#J-table-data5>tbody").html(html);					
						if(isShowCell == false){//状态判定加载相关数据		
							$('[name="showType"]').hide();
						}
						else{
							$('[name="showType"]').show();
						}
							
					}else{
						 $("#Pagination5").hide();    
						 TableStyle("J-table-data5>tbody",17,2,"没有相应数据");
					}				
				}, 
				complete: function()
				{
					isLock=true;
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data5>tbody",17,2,"数据异常");
				}
			});			
		}		
	}	
	//---------------------------------------------------(已完成)数据处理结束------------------------------------------------------
	//下载报表	
	$('#downloadReport,#downloadReport1').click(function(){	   
		//$.get("/admin/Rechargemange/index?parma=ex",group.getFormData());
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Rechargemange/index?parma=ex"+param);
	});
})();	
</script>
{/literal}
</body>
</html>