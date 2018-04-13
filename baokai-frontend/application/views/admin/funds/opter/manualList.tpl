	<!-- //////////////头部公共页面////////////// -->
	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt;人工资金操作审核流程</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<div class="ui-tab">
							<ul class="ui-tab-title ui-tab-title2 clearfix" id="Tabs">
								<!-- {if "FUND_MANUAL_PROCEDURE_ALL"|in_array:$smarty.session.datas.info.acls} -->
							    <span id="AllData"><li>全部</li></span>
                                <!--{else}-->
                                <span><li style="display:none;"></li></span>
								<!-- {/if} -->
								<!-- {if "FUND_MANUAL_PROCEDURE_UNTREATED"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="UntreatedData"><li>未处理</li></span>
                                <!--{else}-->
                                <span><li style="display:none;"></li></span>
								<!-- {/if} -->
								<!-- {if "FUND_MANUAL_PROCEDURE_HANDLING"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="ProcessingData"><li>处理中</li></span>
                                <!--{else}-->
                                <span><li style="display:none;"></li></span>
								<!-- {/if} -->
								<!-- {if "FUND_MANUAL_PROCEDURE_RESOLVED"|in_array:$smarty.session.datas.info.acls} -->
                                <span id="CompletedData" ><li>已完成</li></span>
                                <!--{else}-->
                                <span><li style="display:none;"></li></span>
                                <!-- {/if} -->
                                <a>每页记录数:</a>
                                <select class="ui-select w-2" id="per_page">
                                    <option value="25">25</option>
                                    <option value="50" selected="">50</option>
                                    <option value="100">100</option>
                                    <option value="200">200</option>	
                                </select>
								<!-- {if "FUND_MANUAL_PROCEDURE_CREATE"|in_array:$smarty.session.datas.info.acls} -->
                                <li class="more"><a href="/admin/Opterators/index?parma=opter1">创建新人工单</a></li>
                                <!-- {/if} -->
							</ul>
							
						<ul class="ui-form ui-tab-content"  id="DivRules">
                        	<li>	
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
											<!--<th>人工单单号</th>-->
											<th id="J-sp-perseonserial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单单号</div>
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
											<!--<th>人工单类型</th>-->
											
                                            <th id="J-sp-persontype" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        	<!-- {foreach from=$_arrayType item=data key=key} -->
	                                                        	<!-- {if $key neq '0'} -->
	                                                            	<li data-select-id="{$key}"><a href="#">{$data}</a></li> 
	                                                            <!-- {/if} -->  
                                                            <!-- {/foreach} --> 
														    
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>                                    
											<!--<th>状态</th>-->
											<th id="J-sp-status" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                        <!-- {foreach from=$_arrayStatus item=data key=key} -->
                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                                        <!-- {/foreach} -->                                           
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th> 
											<th>涉及金额</th>
											<th>实际支付金额</th>
											<!--<th>涉及用户</th>-->
											<th id="J-sp-user" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">涉及用户</div>
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
											<!--<th>建单时间</th>-->
											<th id="J-sp-time" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">建单时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-time-1"> - <input type="text" id="sp-input-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th>     
											<!--<th>建单管理员</th>-->
											<th id="J-sp-createadmin" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">建单管理员</div>
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
											<th>材料</th>
											<th>建单原因</th>
											<!--<th>审核管理员</th>-->
											<th id="J-sp-admin" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">审核管理员</div>
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
											<!--<th>审核结束时间</th>-->
											<th id="J-sp-timeend" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">审核结束时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-timeEnd-1"> - <input type="text" id="sp-input-timeEnd-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th>
										</tr>
									</thead>
									<tbody id="showInfo">
									</tbody>
									 <tfoot>
											<tr>
											 <td colspan="23">
													<div id="Pagination" class="pagination"></div>
												</td>
											</tr>
                                    </tfoot>
								</table>
							</li>						
						</ul>
						
						<ul class="ui-form ui-tab-content" >
                        	<li>
								<table id="J-table-data2" class="table table-info table-function">
									<thead>
										<tr>
											<!--<th>人工单单号</th>-->
											<th id="J-sp-perseonserial2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单单号</div>
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
											<!--<th>人工单类型</th>-->
											<th id="J-sp-persontype2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <!-- {foreach from=$_arrayType item=data key=key} -->
	                                                        	<!-- {if $key neq '0'} -->
	                                                            	<li data-select-id="{$key}"><a href="#">{$data}</a></li> 
	                                                            <!-- {/if} -->  
                                                            <!-- {/foreach} -->                                                   
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th> 
											<th>涉及金额</th>
											<!--<th>涉及用户</th>-->
											<th id="J-sp-user2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">涉及用户</div>
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
											<!--<th>建单时间</th>-->
											<th id="J-sp-time2" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">建单时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-time2-1"> - <input type="text" id="sp-input-time2-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th>     
											<!--<th>建单管理员</th>-->
											<th id="J-sp-createadmin2" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">建单管理员</div>
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
											<th>材料</th>
											<th>建单原因</th>
											<th>操作</th>
											<!-- {if "FUND_MANUAL_PROCEDURE_UNTREATED_PASS"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="UNTREATED_PASS" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="UNTREATED_PASS" value="0"/>
											<!-- {/if} -->
											<!-- {if "FUND_MANUAL_PROCEDURE_UNTREATED_REFUSE"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="UNTREATED_REFUSE" value="1"/>
											<!-- {else} -->
											<input type="hidden" id="UNTREATED_REFUSE" value="0"/>
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
						
						<ul class="ui-form ui-tab-content" >
                        	<li>	
								<table id="J-table-data3" class="table table-info table-function">
									<thead>
										<tr>
											<!--<th>人工单单号</th>-->
											<th id="J-sp-perseonserial3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单单号</div>
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
											<!--<th>人工单类型</th>-->
											<th id="J-sp-persontype3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <!-- {foreach from=$_arrayType item=data key=key} -->
	                                                        	<!-- {if $key eq '1' or $key eq '2' or $key eq '9'} -->
	                                                            	<li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/if} -->  
                                                            <!-- {/foreach} -->                                                    
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th> 
											<th>涉及金额</th>
											<!--<th>涉及用户</th>-->
											<th id="J-sp-user3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">涉及用户</div>
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
											<!--<th>建单时间</th>-->
											<th id="J-sp-time3" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">建单时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-time3-1"> - <input type="text" id="sp-input-time3-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th>     
											<!--<th>建单管理员</th>-->
											<th id="J-sp-createadmin3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">建单管理员</div>
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
											<th>材料</th>
											<th>建单原因</th>
											<!--<th>审核管理员</th>-->
											<th id="J-sp-admin3" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">审核管理员</div>
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
											<!--<th>审核结束时间</th>-->
											<th id="J-sp-timeend3" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">审核结束时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-timeEnd3-1"> - <input type="text" id="sp-input-timeEnd3-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th> 
										</tr>
									</thead>
									<tbody id="showInfo3">
									</tbody>
									 <tfoot>
											<tr>
											 <td colspan="23">
													<div id="Pagination3" class="pagination" ></div>
												</td>
											</tr>
                                    </tfoot>
								</table>
							</li>						
						</ul>
						
						<ul class="ui-form ui-tab-content" >
                        	<li>	
                        	<!-- {if "FUND_MANUAL_PROCEDURE_RESOLVED_EXPORT"|in_array:$smarty.session.datas.info.acls} -->
							 <h3 class="ui-title"><a class="btn btn-small " id="J-Download-Report" href="javascript:void(0);"  style="float:left;">下载报表<b class="btn-inner"></b></a></h3>
							<!-- {/if} -->
								<table id="J-table-data4" class="table table-info table-function">
									<thead>
										<tr>
											<!--<th>人工单单号</th>-->
											<th id="J-sp-perseonserial4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单单号</div>
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
											<!--<th>人工单类型</th>-->
											<th id="J-sp-persontype4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">人工单类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <!-- {foreach from=$_arrayType item=data key=key} -->
	                                                        	<!-- {if $key neq '0'} -->
	                                                            	<li data-select-id="{$key}"><a href="#">{$data}</a></li> 
	                                                            <!-- {/if} -->  
                                                            <!-- {/foreach} -->                                                    
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th> 
											<!--<th>状态</th>-->
											<th id="J-sp-status4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <!-- {foreach from=$_arrayStatus item=data key=key} -->
	                                                            <!-- {if $key neq '0' and $key neq '1'} -->
	                                                            	<li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/if} -->
                                                        	<!-- {/foreach} -->                                                   
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th> 
											<th>涉及金额</th>
											<th>实际支付金额</th>
											<!--<th>涉及用户</th>-->
											<th id="J-sp-user4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">涉及用户</div>
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
											<!--<th>建单时间</th>-->
											<th id="J-sp-time4" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">建单时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-time4-1"> - <input type="text" id="sp-input-time4-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th>     
											<!--<th>建单管理员</th>-->
											<th id="J-sp-createadmin4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">建单管理员</div>
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
											<th>材料</th>
											<th>建单原因</th>
											<!--<th>审核管理员</th>-->
											<th id="J-sp-admin4" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">审核管理员</div>
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
											<!--<th>审核结束时间</th>-->
											<th id="J-sp-timeend4" class="sp-td">
                                            <div class="sp-td-cont sp-td-cont-b">
                                                <div class="sp-td-title">审核结束时间</div>
                                                <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                    <li>
                                                        <div class="input-append">
                                                            <input type="text" tabindex="1" class="input w-2" id="sp-input-timeEnd4-1"> - <input type="text" id="sp-input-timeEnd4-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                        </div>
                                                    </li>
                                                </ul>
                                                <span class="sp-filter-close"></span>
                                            </div>
                                            </th>  
										</tr>
									</thead>
									<tbody id="showInfo4">
									</tbody>
									 <tfoot>
											<tr>
											 <td colspan="23">
													<div id="Pagination4" class="pagination" ></div>
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
<style>
.ui-wd-funds-expired {
}
.ui-wd-funds-expired .wd-control-panel {
    padding: 20px;
    text-align: center;
}
.ui-wd-funds-expired table td {
    padding: 5px;
}
.ui-wd-funds-expired .wd-title {
    color: #f60;
    font-size: 14px;
    padding: 10px;
}
.ui-wd-funds-expired .wd-tip {
    color: #999;
}
.ui-wd-funds-expired .wd-td-bold {
}
</style>
<script type="text/tpl" id="J-template-checkstatus">
<div>
	<p class="wd-title">您好,人工充值订单收款银行信息:</p>
	<div>
		<table width="100%" border="0" cellspacing="10" cellpadding="0">
		  <tr>
			<td align="right" width="130" class="wd-td-bold">充值银行：</td>
			<td><#=payBankName#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">充值金额：</td>
			<td style="color:red"><#=chargeAmt#> 元</td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">收款银行：</td>
			<td style="color:red"><#=userBankName#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">收款账户名：</td>
			<td style="color:red"><#=rcvAccountName#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold"><#=rcvName#>：</td>
			<td style="color:red"><#=rcvMail#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">附言：</td>
			<td style="color:red"><#=remark#></td>
		  </tr>
		  <tr>
			<td align="right" class="wd-td-bold">开户行名称：</td>
			<td style="color:red"><#=rcvBankName#></td>
		  </tr>
	  </table>
		<p class="wd-control-panel">
			您还可以 &nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0);" class="btn btn-important" id="J-button-order-cancel" prevalue="<#=id#>" >关闭订单<b class="btn-inner"></b></a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/admin/Reporting/index?parma=sv51&Direction=<#=id#>" target="_blank">充值明细报表</a>
		</p>
		<p class="wd-tip">
			* 如客户已完成付款，请勿撤销，系统将尽快为客户处理。
		</p>
	</div>
</div>
</script>
<script type="text/tpl" id="J-template-cancel-confirm">
<div>
	<p class="wd-title" style="text-align:center;">您申请的人工充值订单已关闭！</p>
		<p class="wd-control-panel">
			<a href="#" class="btn btn-important closeBtn">确定<b class="btn-inner"></b></a>
	</p>
</div>
</script>
{literal}
<script>
(function() {	
	var dataArea = $('#DivRules'),minWindow,mask,initFn,isture=false;
		minWindow = new phoenix.MiniWindow({cls:'ui-wd-funds-expired'});
		mask = phoenix.Mask.getInstance();
		minWindow.addEvent('beforeShow', function(){
			mask.show();
		});
		minWindow.addEvent('afterHide', function(){
			mask.hide();
		});
    var isShowCell=false,group=new phoenix.SuperSearchGroup(),msg = new phoenix.Message(),msg1 = new phoenix.Message();
    var sindex=phoenix.util.getParam("tabIndex");
	sindex = sindex== null ? 1 : sindex;
	var tabObj=new phoenix.Tab({triggers:'.ui-tab-title2 li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:sindex});
		 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuOpterators');	
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
	//获取两个input值
	var getTowInputValue = function(){
		var me = this,ipts = me.dom.find('input[type="text"]'),name = me.name,result = {};
		result[name+'1'] = ipts[0].value;
		result[name+'2'] = ipts[1].value;
		return result;
	};		
	if(sindex==1) {
		UntreatedData();
	} else if(sindex==2) {
		ProcessingData();
	} else if(sindex==3) {
		CompletedData();
	} else {   
		AllData();
	}
    //全部数据查询
	$('#AllData').click(function(){
		AllData();
	});	
	//未处理查询
	$('#UntreatedData').click(function(){
		UntreatedData();		
	});
	//处理查询
	$('#ProcessingData').click(function(){
		ProcessingData();		
	});	
	//已完查询
	$('#CompletedData').click(function(){
		CompletedData();		
	});
	
	function AllData(){
		//group = new phoenix.SuperSearchGroup();
		
		//人工单单号
		var personserial = new phoenix.SuperSearch({
			name: 'personserial',
			keyCode: 'ctrl+83',
			 el: '#J-sp-perseonserial',
			group: group
		});
		
		//人工单类型
		 var persontype = new phoenix.SuperSearch({
			name: 'persontype',
			keyCode: 'ctrl+66',
			type: 'select',
			//isAutoWidth: true,
			el: '#J-sp-persontype',
			expands:{getFormValue:getSelectValue},
			group: group
			
		});
		
	   //状态
	   //debugger
		var personstatus = new phoenix.SuperSearch({
			name: 'personstatus',			
			type: 'select',
			isAutoWidth: true,
			 el: '#J-sp-status',			
			expands:{getFormValue:getSelectValue},
			group: group
		});

		//涉及用户
		var personuser = new phoenix.SuperSearch({
			name: 'personuser',
			keyCode: 'ctrl+83',
			 el: '#J-sp-user',
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
			expands:{getFormValue:getSelectValue}
		});
		 //建单时间
	     var createtime = new phoenix.SuperSearch({
			name: 'createtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var createTime1 = $('#sp-input-time-1'),createTime2 = $('#sp-input-time-2'),createDt1,createDt2;
		createTime1.click(function() {
			createDt1 = new phoenix.DatePicker({
				input: createTime1,
				isLockInputType: false
			});
			createDt1.show();
			createDt1.addEvent('afterSetValue',
			function() {
				createTime2.focus();
				createTime2.click();
			})
		});
		createtime.addEvent('afterFocus',
		function() {
			createTime1.click()
		});
		createTime2.click(function() {
			createDt2 = new phoenix.DatePicker({
				input: createTime2,
				isLockInputType: false
			}).show();
		});
		createtime.addEvent('afterBlur',function() {
			if (createDt1) {
				createDt1.hide();
			}
			if (createDt2) {
				createDt2.hide();
			}
		});
		
		
		 //建单管理员
		 var createadmin = new phoenix.SuperSearch({
		     name: 'createadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-createadmin',
			 group: group
		});
		
		//审核管理员
		 var personadmin = new phoenix.SuperSearch({
		     name: 'personadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-admin',
			 group: group
		});
		
	    //审核结束时间
	     var endtime = new phoenix.SuperSearch({
			name: 'timeend',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-timeend',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var endTime1 = $('#sp-input-timeEnd-1'),endTime2 = $('#sp-input-timeEnd-2'),endDt1,endDt2;
		endTime1.click(function() {
			endDt1 = new phoenix.DatePicker({
				input: endTime1,
				isLockInputType: false
			});
			endDt1.show();
			endDt1.addEvent('afterSetValue',
			function() {
				endTime2.focus();
				endTime2.click();
			})
		});
		endtime.addEvent('afterFocus',
		function() {
			endTime1.click()
		});
		endTime2.click(function() {
			endDt2 = new phoenix.DatePicker({
				input: endTime2,
				isLockInputType: false
			}).show();
		});
		endtime.addEvent('afterBlur',function() {
			if (endDt1) {
				endDt1.hide();
			}
			if (endDt2) {
				endDt2.hide();
			}
		});
		var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 0;	
				}}
		});
		
		SelectByWhereInfo("0");	
		group.removeEvent('dataChange');
		//提交数据
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
			 $("#J-table-data>tbody").html("");				
			  $("#Pagination").hide();   
			 $.ajax({
				url:'/admin/Opterators/index?parma=de',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){				
					TableStyle("showInfo",13,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.count[0].recordNum>0){
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
								if(re[i].typeId == '9' && re[i].status =='处理中'){
									html += "<tr><td><a href='javascript:void(0);' pretype='0' class='dealSNClass'>" + re[i].sn + "</a></td>";    
								} else {
									html += "<tr><td>" + re[i].sn + "</td>";    
								}   
								html += "<td>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].status + "</td>";
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].realWithdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";							
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";  						
								html += "<td>" + re[i].attach + "</td>";			
								html += "<td>" + re[i].memo + "</td>";               
								html += "<td>" + re[i].apprAccount + "</td>";        
								html += "<td>" + re[i].apprTime + "</td>";		  			
						});
						$("#J-table-data>tbody").html(html);
					}else{
						 $("#Pagination").hide();                
						 TableStyle("J-table-data>tbody",13,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data>tbody",13,2,"数据异常");  
				}
			});	
			 
			 
		}	
	}
	function UntreatedData(){
		//group = new phoenix.SuperSearchGroup();
		//人工单单号
		var personserial = new phoenix.SuperSearch({
			name: 'personserial',
			keyCode: 'ctrl+83',
			 el: '#J-sp-perseonserial2',
			group: group
		});
		
		//人工单类型
		 var persontype = new phoenix.SuperSearch({
			name: 'persontype',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-persontype2',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		//涉及用户
		var personuser = new phoenix.SuperSearch({
			name: 'personuser',
			keyCode: 'ctrl+83',
			 el: '#J-sp-user2',
			group: group
		});
		
		//是否VIP
		 var isvip = new phoenix.SuperSearch({
			name: 'isvip',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-isvip2',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		 //建单时间
	     var createtime = new phoenix.SuperSearch({
			name: 'createtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-time2',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var createTime1 = $('#sp-input-time2-1'),createTime2 = $('#sp-input-time2-2'),createDt1,createDt2;
		createTime1.click(function() {
			createDt1 = new phoenix.DatePicker({
				input: createTime1,
				isLockInputType: false
			});
			createDt1.show();
			createDt1.addEvent('afterSetValue',
			function() {
				createTime2.focus();
				createTime2.click();
			})
		});
		createtime.addEvent('afterFocus',
		function() {
			createTime1.click()
		});
		createTime2.click(function() {
			createDt2 = new phoenix.DatePicker({
				input: createTime2,
				isLockInputType: false
			}).show();
		});
		createtime.addEvent('afterBlur',function() {
			if (createDt1) {
				createDt1.hide();
			}
			if (createDt2) {
				createDt2.hide();
			}
		});
		
		
		 //建单管理员
		 var createadmin = new phoenix.SuperSearch({
		     name: 'createadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-createadmin2',
			 group: group
		});
		
	    var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 1;	
				}}
		});
		
		SelectByWhereInfo("0");	
		group.removeEvent('dataChange');
		//提交数据
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data2 >tbody").html("");	
			 $("#Pagination2").hide();   			
			 $.ajax({
				url:'/admin/Opterators/index?parma=de',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){	
					TableStyle("showInfo2",10,1,"查询中");
				},
				success:function(data){
					if(data.count[0].recordNum>0){
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
								html += "<tr><td><span style='display:none;'>"+re[i].id+"</span><span>" + re[i].sn + "</span></td>";    
								html += "<td><span style='display:none;'>"+re[i].typeId+"</span>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";							
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";  						
								html += "<td>" + re[i].attach + "</td>";			
								html += "<td>" + re[i].memo + "</td>";               
								html += " <td>";
								if($("#UNTREATED_PASS").val()=='1'){
									html += "<a class='btn btn-small' href='javascript:void(0);' name='ThroughOper'>通 过<b class='btn-inner'></b></a>&nbsp;&nbsp;";
								}
								if($("#UNTREATED_REFUSE").val()=='1'){
									html += "<a class='btn btn-small' href='javascript:void(0);' name='RefuseOper'>拒 绝<b class='btn-inner'></b></a>";
								}
								html += "</td>";			
						});
						$("#J-table-data2>tbody").html(html);
							
					}else{
						 $("#Pagination2").hide();   
						 TableStyle("J-table-data2>tbody",10,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data2>tbody",10,2,"数据异常");					
				}
			});			
		}	
	}
	
	function ProcessingData(){
		//group = new phoenix.SuperSearchGroup();
		//人工单单号
		var personserial = new phoenix.SuperSearch({
			name: 'personserial',
			keyCode: 'ctrl+83',
			 el: '#J-sp-perseonserial3',
			group: group
		});
		
		//人工单类型
		 var persontype = new phoenix.SuperSearch({
			name: 'persontype',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-persontype3',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		
		//涉及用户
		var personuser = new phoenix.SuperSearch({
			name: 'personuser',
			keyCode: 'ctrl+83',
			 el: '#J-sp-user3',
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
			expands:{getFormValue:getSelectValue}
		});
		 //建单时间
	     var createtime = new phoenix.SuperSearch({
			name: 'createtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-time3',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var createTime1 = $('#sp-input-time3-1'),createTime2 = $('#sp-input-time3-2'),createDt1,createDt2;
		createTime1.click(function() {
			createDt1 = new phoenix.DatePicker({
				input: createTime1,
				isLockInputType: false
			});
			createDt1.show();
			createDt1.addEvent('afterSetValue',
			function() {
				createTime2.focus();
				createTime2.click();
			})
		});
		createtime.addEvent('afterFocus',
		function() {
			createTime1.click()
		});
		createTime2.click(function() {
			createDt2 = new phoenix.DatePicker({
				input: createTime2,
				isLockInputType: false
			}).show();
		});
		createtime.addEvent('afterBlur',function() {
			if (createDt1) {
				createDt1.hide();
			}
			if (createDt2) {
				createDt2.hide();
			}
		});		
		 //建单管理员
		 var createadmin = new phoenix.SuperSearch({
		     name: 'createadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-createadmin3',
			 group: group
		});
		
		//审核管理员
		 var personadmin = new phoenix.SuperSearch({
		     name: 'personadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-admin3',
			 group: group
		});
		
	    //审核结束时间
	     var endtime = new phoenix.SuperSearch({
			name: 'timeend',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-timeend3',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var endTime1 = $('#sp-input-timeEnd3-1'),endTime2 = $('#sp-input-timeEnd3-2'),endDt1,endDt2;
		endTime1.click(function() {
			endDt1 = new phoenix.DatePicker({
				input: endTime1,
				isLockInputType: false
			});
			endDt1.show();
			endDt1.addEvent('afterSetValue',
			function() {
				endTime2.focus();
				endTime2.click();
			})
		});
		endtime.addEvent('afterFocus',
		function() {
			endTime1.click()
		});
		endTime2.click(function() {
			endDt2 = new phoenix.DatePicker({
				input: endTime2,
				isLockInputType: false
			}).show();
		});
		endtime.addEvent('afterBlur',function() {
			if (endDt1) {
				endDt1.hide();
			}
			if (endDt2) {
				endDt2.hide();
			}
		});
		var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 2;	
				}}
		});
		
		SelectByWhereInfo("0");	
		group.removeEvent('dataChange');
		//提交数据
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data3>tbody").html("");	
			  $("#Pagination3").hide();   			
			 $.ajax({
				url:'/admin/Opterators/index?parma=de',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){	
					TableStyle("showInfo3",12,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.count[0].recordNum>0){
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
								if(re[i].typeId == '9'){
									html += "<tr><td><a href='javascript:void(0);' pretype='2' class='dealSNClass'>" + re[i].sn + "</a></td>";    
								} else {
									html += "<tr><td>" + re[i].sn + "</td>";    
								}
								html += "<td>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";							
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";  						
								html += "<td>" + re[i].attach + "</td>";			
								html += "<td>" + re[i].memo + "</td>";               
								html += "<td>" + re[i].apprAccount + "</td>";        
								html += "<td>" + re[i].apprTime + "</td>";
						});
						$("#J-table-data3>tbody").html(html);
					}else{
						 $("#Pagination3").hide(); 
						 TableStyle("J-table-data3>tbody",12,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data3>tbody",12,2,"数据异常");  
				}
			});			
		}	
	}
	
	function CompletedData(){
		//group = new phoenix.SuperSearchGroup();
		//人工单单号
		var personserial = new phoenix.SuperSearch({
			name: 'personserial',
			keyCode: 'ctrl+83',
			 el: '#J-sp-perseonserial4',
			group: group
		});
		
		//人工单类型
		 var persontype = new phoenix.SuperSearch({
			name: 'persontype',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-persontype4',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		
	   //状态
		var personstatus = new phoenix.SuperSearch({
			name: 'personstatus',
			keyCode: 'ctrl+83',
			type: 'select',
			isAutoWidth: true,
		    el: '#J-sp-status4',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		
		//涉及用户
		var personuser = new phoenix.SuperSearch({
			name: 'personuser',
			keyCode: 'ctrl+83',
			 el: '#J-sp-user4',
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
			expands:{getFormValue:getSelectValue}
		});
		 //建单时间
	     var createtime = new phoenix.SuperSearch({
			name: 'createtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-time4',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var createTime1 = $('#sp-input-time4-1'),createTime2 = $('#sp-input-time4-2'),createDt1,createDt2;
		createTime1.click(function() {
			createDt1 = new phoenix.DatePicker({
				input: createTime1,
				isLockInputType: false
			});
			createDt1.show();
			createDt1.addEvent('afterSetValue',
			function() {
				createTime2.focus();
				createTime2.click();
			})
		});
		createtime.addEvent('afterFocus',
		function() {
			createTime1.click()
		});
		createTime2.click(function() {
			createDt2 = new phoenix.DatePicker({
				input: createTime2,
				isLockInputType: false
			}).show();
		});
		createtime.addEvent('afterBlur',function() {
			if (createDt1) {
				createDt1.hide();
			}
			if (createDt2) {
				createDt2.hide();
			}
		});
		
		
		 //建单管理员
		 var createadmin = new phoenix.SuperSearch({
		     name: 'createadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-createadmin4',
			 group: group
		});
		
		//审核管理员
		 var personadmin = new phoenix.SuperSearch({
		     name: 'personadmin',
			 keyCode: 'ctrl+83',
			 el: '#J-sp-admin4',
			 group: group
		});
		
	    //审核结束时间
	     var endtime = new phoenix.SuperSearch({
			name: 'timeend',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-timeend4',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var endTime1 = $('#sp-input-timeEnd4-1'),endTime2 = $('#sp-input-timeEnd4-2'),endDt1,endDt2;
		endTime1.click(function() {
			endDt1 = new phoenix.DatePicker({
				input: endTime1,
				isLockInputType: false
			});
			endDt1.show();
			endDt1.addEvent('afterSetValue',
			function() {
				endTime2.focus();
				endTime2.click();
			})
		});
		endtime.addEvent('afterFocus',
		function() {
			endTime1.click()
		});
		endTime2.click(function() {
			endDt2 = new phoenix.DatePicker({
				input: endTime2,
				isLockInputType: false
			}).show();
		});
		endtime.addEvent('afterBlur',function() {
			if (endDt1) {
				endDt1.hide();
			}
			if (endDt2) {
				endDt2.hide();
			}
		});
		
		var Title = new phoenix.SuperSearch({
			name: 'Title',			
			group: group,
			expands:{getValue:function(){
					return 3;	
				}}
		});
		
		SelectByWhereInfo("0");	
		group.removeEvent('dataChange');
		//提交数据
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
			
			 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();			
			 $("#J-table-data4>tbody").html("");
			  $("#Pagination4").hide();    				
			 $.ajax({
				url:'/admin/Opterators/index?parma=de',
				dataType:'json',
				method:'post',
				data:formData,
				beforeSend:function(){	
					TableStyle("showInfo4",13,1,"查询中");
				},
				success:function(data){
					//debugger
					if(data.count[0].recordNum>0){
						$("#Pagination4").show();
						//var resultAll = JSON.parse(data); //jQuery.parseJSON    
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
								html += "<tr><td>" + re[i].sn + "</td>";    
								html += "<td>" + re[i].typeName + "</td>"; 
								html += "<td>" + re[i].status + "</td>";
								html += "<td>" + re[i].withdrawAmt + "</td>";
								html += "<td>" + re[i].realWithdrawAmt + "</td>";
								html += "<td>" + re[i].rcvAccount + "</td>";							
								html += "<td>" + (re[i].isVip =="1" ?"是":"否") + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";	
								html += "<td>" + re[i].applyAccount + "</td>";  						
								html += "<td>" + re[i].attach + "</td>";			
								html += "<td>" + re[i].memo + "</td>";               
								html += "<td>" + re[i].apprAccount + "</td>";        
								html += "<td>" + re[i].apprTime + "</td>";
						});
						$("#J-table-data4>tbody").html(html);							
					}else{
						 $("#Pagination4").hide(); 
						 TableStyle("J-table-data4>tbody",13,2,"没有相应数据");
					}				
				}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data4>tbody",13,2,"数据异常");  
				}
			});			
		}	
	}
	
	//查看订单详情
	$(document).on('click', '.dealSNClass', function(e){			
		var sn=$(this).html();
		var pretype=$(this).attr('pretype');
		checkOrderStatus(sn,pretype);
	});
	
	var checkOrderStatus = function(sn,pretype){
		$.ajax({
			url:'/admin/Opterators/index?parma=chkoptcharge',
			type:"POST",
			dataType:'json',
			data:{sn:sn,type:pretype},
			async:false,
			beforeSend:function(){
				mask.dom.addClass('admin-mask-loading');
				mask.css({opacity:.9,backgroundColor:'#FFF'});
				mask.show();
				mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
			},
			success:function(data){
				//var data = {isSuccess:1, type:'', msg:'请求成功', data:{bankFrom:'充值银行名称', money:'充值金额', bankTo:'收款银行', name:'收款人', email:'收款Email地址', message:'附言', bankOpen:'开户行名称'}};
				if(Number(data['isSuccess']) == 1){
					var str = $('#J-template-checkstatus').html(),
						reg,
						p;
					for(p in data['data']){
						if(p == null){
							p = '';
						}
						reg = RegExp('<#=' + p + '#>', 'g');
						str = str.replace(reg, data['data'][p]);
					}
					minWindow.setTitle('订单详情');
					minWindow.setContent(str);
					minWindow.show();
					$('#J-button-order-cancel').click(function(e){
						minWindow.hide();
						e.preventDefault();
						$.ajax({
							url:'/admin/Opterators/index?parma=delCharge',
							dataType:'json',
							async:false,
							cache:false,
							method:'post',
							data:{id:$(this).attr('prevalue')},
							success:function(data){
								msg.show({
									mask: true,
									content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.data+'!</h3><div style="height:30px;line-height:30px;"></div>',
									confirmIsShow: 'true',
									confirmText: '确定',
									confirmFun: function(){
										if(Number(data['isSuccess']) == 1){
											minWindow.hide();
											window.location.href="/admin/Opterators/index?parma=sv1&tabIndex="+pretype;
										} else {
											msg.hide();
										}
									}
								});
							}
						});
					});
					return true;
				}else{
					msg.show({
						mask: true,
						content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.data+'!</h3><div style="height:30px;line-height:30px;"></div>',
						confirmIsShow: 'true',
						confirmText: '确定',
						confirmFun: function(){
							msg.hide();
						}
					});
					return false;
				}
			},
			complete:function(){
				mask.dom.fadeOut(300, function(){
					mask.hide();
				});
			}
		});
	};
	
	
	//通过
	$(document).on('click', '[name="ThroughOper"]', function(e){			
		var oTr=$(this).parent().parent();	
	    var sn=$.trim(oTr.find("td:eq(0) span:eq(1)").text());
	    var sid=$.trim(oTr.find("td:eq(0) span:eq(0)").text());
	    var typeId=$.trim(oTr.find("td:eq(1) span:eq(0)").text());
		var stext="单号："+sn+" 确定通过 ？";
		var sdata="id="+sid+"&status=1"+"&typeId="+typeId;
		
		msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+stext+'!</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
                                msg.win.doConfirm = function(){
                                    //console.log('cannot send again!')
                                };
				$.ajax({
					url:'/admin/Opterators/index?parma=sv2',				
					dataType:'json',
					method:'post',
					data:sdata,
					beforeSend: function()
					{
						isLock=false;
						//禁用发送								
						var button = $(".confirm"),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
						interval=setInterval(function(){
							button.html(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
						var me = this;
						me.doConfirm = function(){};	
					},
					success:function(data){
						msg.hide();
						msg1.show({
							mask: true,
							content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.data+'!</h3><div style="height:30px;line-height:30px;"></div>',
							confirmIsShow: 'true',
							confirmText: '确定',
							confirmFun: function(){
								if(data.status=='ok'){	
									window.location.href="/admin/Opterators/index?parma=sv1&tabIndex=1";
								} else {
									window.location.href="/admin/Opterators/index?parma=sv1&tabIndex=1";
								}
							}
						});
					},
					complete: function()
					{
						isLock=true;
						clearInterval(interval);
					}					
				});
			},
			cancelIsShow: 'true',
			cancelText: '取消',
			cancelFun: function(){
				msg.hide();
			}
		});
	});
	
    //拒绝
	$(document).on('click', '[name="RefuseOper"]', function(e){			
		var oTr=$(this).parent().parent();	
		var sn=$.trim(oTr.find("td:eq(0) span:eq(1)").text());
	    var sid=$.trim(oTr.find("td:eq(0) span:eq(0)").text());
	    var typeId=$.trim(oTr.find("td:eq(1) span:eq(0)").text());
	    var stext="单号："+sn+" 确定拒绝 ？";
		var sdata="id="+sid+"&status=2"+"&typeId="+typeId;
		
		msg.show({
			mask: true,
			content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+stext+'!</h3><div style="height:30px;line-height:30px;"></div>',
			confirmIsShow: 'true',
			confirmText: '确定',
			confirmFun: function(){
                                msg.win.doConfirm = function(){
                                    //console.log('cannot send again!')
                                };
				$.ajax({
					url:'/admin/Opterators/index?parma=sv2',				
					dataType:'json',
					method:'post',
					data:sdata,		
					beforeSend: function()
					{
						isLock=false;
						//禁用发送								
						var button = $(".confirm"),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
						interval=setInterval(function(){
							button.html(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
						var me = this;
						me.doConfirm = function(){};
					},
					success:function(data){
						msg.hide();
						msg1.show({
							mask: true,
							content: '<h3 style="height:30px;line-height:30px;text-align:center;">'+data.data+'!</h3><div style="height:30px;line-height:30px;"></div>',
							confirmIsShow: 'true',
							confirmText: '确定',
							confirmFun: function(){
								if(data.status=='ok'){
									window.location.href="/admin/Opterators/index?parma=sv1&tabIndex=1";
								} else {
									window.location.href="/admin/Opterators/index?parma=sv1&tabIndex=1";
								}
							}
						});
					},
					complete: function()
					{
						isLock=true;
						clearInterval(interval);
					}				
				});
			},
			cancelIsShow: 'true',
			cancelText: '取消',
			cancelFun: function(){
				msg.hide();
			}
		});
	});
	//下载报表	
	$('#J-Download-Report').click(function(){	   
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Opterators/index?parma=ex"+param);
	});
})();	
</script>
{/literal}
</body>
</html>