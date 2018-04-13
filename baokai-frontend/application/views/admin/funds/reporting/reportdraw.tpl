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
						<!-- {if "FUND_REPORT_WITHDRAW_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
						<h3 class="ui-title"><a class="btn btn-small" style="float:left;"  href="javascript:void(0);" id="J-Download-Report">下载报表<b class="btn-inner"></b></a></h3>						<!-- {/if} -->
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
                                            <th id="J-sp-PlatformNumber" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">平台订单号</div>
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
											<th id="J-sp-MowNumber" class="sp-td">
                                             <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">mow订单号</div>
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
                                            										<th  id="J-sp-applyChannel" class="sp-td">
	                                            <div class="sp-td-cont sp-td-cont-b">
	                                                <div class="sp-td-title">提现渠道</div>
	                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
	                                                        <ul>
	                                                        	<!-- {foreach from=$aWithdrawMode item=data key=key} -->
	                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/foreach} -->
	                                                        </ul>
	                                                </div>
	                                                <span class="sp-filter-close"></span>
	                                            </div>
                                        	</th>
                                            
											 <th id="J-sp-OrderStatus" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">订单状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
														   <!-- 0 未处理、1 待复审、2 处理中、3 已拒绝、4 提现完全成功、5 提现部分成功、 6提现失败-->
														   {foreach from=$aStatus key=key item=data}
                                                           		<li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                                           {/foreach}
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>  
                                            
											<th id="J-sp-UserName" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">用户名</div>
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
											 <th id="J-sp-AgentName" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">所属总代</div>
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
											 <th>申请提现金额</th>						
											 <th class="sp-td" id="J-sp-td-ApplicationPeriod">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请提现时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-ApplicationPeriod-time-1"> - <input type="text" id="sp-input-ApplicationPeriod-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>     
                                            </th>                                           
											<th>申请提现银行</th>	
											<!-- {if "FUND_REPORT_WITHDRAW_LOOKDETAIL"|in_array:$smarty.session.datas.info.acls} -->
											<th>MOW回执单</th>
											<!-- {/if} -->										
											<th>申请渠道</th>	
											<th>申请平台</th>	
                                           <th class="sp-td" id="J-sp-td-SendMowTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">审核通过时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-SendMowTime-time-1"> - <input type="text" id="sp-input-SendMowTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>     
											<th class="sp-td" id="J-sp-td-BankRealTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">MOW接收订单时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-BankRealTime-time-1"> - <input type="text" id="sp-input-BankRealTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>
											<th>实际支付金额</th>	
                                            <th class="sp-td" id="J-sp-td-ResultMowTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">订单完成时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-ResultMowTime-time-1"> - <input type="text" id="sp-input-ResultMowTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>  
                                             <th id="J-sp-ApplyCards" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请提现卡</div>
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
                                            <th id="J-sp-ApplyUsername" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请提现户名</div>
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
                                            <th class="sp-td" id="J-sp-td-OperatingTime">                                    
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">DP操作时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-OperatingTime-time-1"> - <input type="text" id="sp-input-OperatingTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>
											<!-- {if "FUND_REPORT_WITHDRAW_LOOKDETAIL"|in_array:$smarty.session.datas.info.acls} -->
											<input type="hidden" id="WITHDRAW_LOOKDETAIL" value="1"/>
											<!-- {/if} -->	
										</tr>
									</thead>
									<tbody id="showInfo">
                                </tbody>                                
                                <tfoot>
                                    <tr>
                                        <td colspan="15">
                                              <div id="Pagination" class="pagination"></div>
                                        </td>
                                    </tr>
                                </tfoot>
                             </table>    
								
								
							</div>
						</div>
					</div>
				</div>
		 <!--查看详情-->
	  <div class="pop w-8" id="CheckWithDrawDetail" style="display:none;">
               <div class="hd">
        <i class="close" name="closeIcoDiv1"></i>
        <h3 align="center">查看详情</h3>
		</div>
		<div class="bd">
		<ul class="ui-form ui-form-small">
			<li>
				<label class="ui-label w-auto">平台提现订单号：</label>
				<input type="hidden"  id="sp-li-drawbankID7" name="drawbankID" value=""/>
				<span class="ui-info" id="sp-li-drawbank2" name="drawbank"></span>
			</li>
			<li>
				<label class="ui-label w-auto">MOW订单号：</label>
				<span class="ui-info" id="sp-li-drawName3" name="drawName"></span>
			</li>
			<li>
				<label class="ui-label w-auto">详情：</label>
				<span class="ui-info" id="sp-li-drawName4" name="drawName"></span>
				<br></br>
				<br></br>
				<br></br>
				<br></br>
			</li>
			<li>
				<label class="ui-label w-auto">状态：</label>
				<span class="ui-info" id="sp-li-drawName5" name="drawName"></span>
			</li>
			<li>
				<label class="ui-label w-auto">创建时间：</label>
				<span class="ui-info" id="sp-li-drawName6" name="drawName"></span>
			</li>
			<li>
				<label class="ui-label w-auto">返款种类：</label>
				<span class="ui-info" id="sp-li-drawName7"" name="drawName"></span>
			</li>
			<li>
				<div class="pop-btn ">
				<input class="btn" name="closeIcoDiv1" value="確定"  style="width:80px;"/>
				</div>
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
	//test99
	option = {zIndex:500},	
    box1=new LightBox("CheckWithDrawDetail",option);	
	
	var isture=false;	
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuReportDetail');
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
		}
		return result;
	};
	var group = new phoenix.SuperSearchGroup();	
	//平台订单号
	var PlatformNumber = new phoenix.SuperSearch({
		name: 'PlatformNumber',		
		el: '#J-sp-PlatformNumber',
		group: group
	});
	
	var MowNumber = new phoenix.SuperSearch({
		name: 'MowNumber',		
		el: '#J-sp-MowNumber',
		group: group
	});
        
                       //申请渠道
	 var applyChannel = new phoenix.SuperSearch({
		name: 'applyChannel',		
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-applyChannel',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	
	//状态
	 var OrderStatus = new phoenix.SuperSearch({
		name: 'OrderStatus',		
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-OrderStatus',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	//用户名
	var UserName = new phoenix.SuperSearch({
		name: 'UserName',		
		el: '#J-sp-UserName',
		group: group
	});
	var AgentName = new phoenix.SuperSearch({
		name: 'AgentName',		
		el: '#J-sp-AgentName',
		group: group
	});
	//申请提现时间
	var ApplicationPeriodtime = new phoenix.SuperSearch({
		name: 'ApplicationPeriodtime',		
		type: 'time',
		el: '#J-sp-td-ApplicationPeriod',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var ApplicationPeriodTime1 = $('#sp-input-ApplicationPeriod-time-1'),ApplicationPeriodTime2 = $('#sp-input-ApplicationPeriod-time-2'),ApplicationPeriodDt1,ApplicationPeriodDt2;
	ApplicationPeriodTime1.click(function() {
		ApplicationPeriodDt1 = new phoenix.DatePicker({
			input: ApplicationPeriodTime1,
			isLockInputType: false
		});
		ApplicationPeriodDt1.show();
		ApplicationPeriodDt1.addEvent('afterSetValue',
		function() {
			ApplicationPeriodTime2.focus();
			ApplicationPeriodTime2.click();
		})
	});
	ApplicationPeriodtime.addEvent('afterFocus',
	function() {
		ApplicationPeriodTime1.click()
	});
	ApplicationPeriodTime2.click(function() {
		ApplicationPeriodDt2 = new phoenix.DatePicker({
			input: ApplicationPeriodTime2,
			isLockInputType: false
		}).show();
	});
	ApplicationPeriodtime.addEvent('afterBlur',function() {
		if (ApplicationPeriodDt1) {
			ApplicationPeriodDt1.hide();
		}
		if (ApplicationPeriodDt2) {
			ApplicationPeriodDt2.hide();
		}
	});
	
   //银行实际支付时间
	var BankRealTimetime = new phoenix.SuperSearch({
		name: 'BankRealTime',		
		type: 'time',
		el: '#J-sp-td-BankRealTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var BankRealTimeTime1 = $('#sp-input-BankRealTime-time-1'),BankRealTimeTime2 = $('#sp-input-BankRealTime-time-2'),BankRealTimeDt1,BankRealTimeDt2;
	BankRealTimeTime1.click(function() {
		BankRealTimeDt1 = new phoenix.DatePicker({
			input: BankRealTimeTime1,
			isLockInputType: false
		});
		BankRealTimeDt1.show();
		BankRealTimeDt1.addEvent('afterSetValue',
		function() {
			BankRealTimeTime2.focus();
			BankRealTimeTime2.click();
		})
	});
	BankRealTimetime.addEvent('afterFocus',
	function() {
		BankRealTimeTime1.click()
	});
	BankRealTimeTime2.click(function() {
		BankRealTimeDt2 = new phoenix.DatePicker({
			input: BankRealTimeTime2,
			isLockInputType: false
		}).show();
	});
	BankRealTimetime.addEvent('afterBlur',function() {
		if (BankRealTimeDt1) {
			BankRealTimeDt1.hide();
		}
		if (BankRealTimeDt2) {
			BankRealTimeDt2.hide();
		}
	});
	
	//订单发给mow时间
	var SendMowTimetime = new phoenix.SuperSearch({
		name: 'SendMowTimetime',		
		type: 'time',
		el: '#J-sp-td-SendMowTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var SendMowTimeTime1 = $('#sp-input-SendMowTime-time-1'),SendMowTimeTime2 = $('#sp-input-SendMowTime-time-2'),SendMowTimeDt1,SendMowTimeDt2;
	SendMowTimeTime1.click(function() {
		SendMowTimeDt1 = new phoenix.DatePicker({
			input: SendMowTimeTime1,
			isLockInputType: false
		});
		SendMowTimeDt1.show();
		SendMowTimeDt1.addEvent('afterSetValue',
		function() {
			SendMowTimeTime2.focus();
			SendMowTimeTime2.click();
		})
	});
	SendMowTimetime.addEvent('afterFocus',
	function() {
		SendMowTimeTime1.click()
	});
	SendMowTimeTime2.click(function() {
		SendMowTimeDt2 = new phoenix.DatePicker({
			input: SendMowTimeTime2,
			isLockInputType: false
		}).show();
	});
	SendMowTimetime.addEvent('afterBlur',function() {
		if (SendMowTimeDt1) {
			SendMowTimeDt1.hide();
		}
		if (SendMowTimeDt2) {
			SendMowTimeDt2.hide();
		}
	});
	//订单发给mow时间
	var SendMowTimetime = new phoenix.SuperSearch({
		name: 'SendMowTimetime',		
		type: 'time',
		el: '#J-sp-td-SendMowTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var SendMowTimeTime1 = $('#sp-input-SendMowTime-time-1'),SendMowTimeTime2 = $('#sp-input-SendMowTime-time-2'),SendMowTimeDt1,SendMowTimeDt2;
	SendMowTimeTime1.click(function() {
		SendMowTimeDt1 = new phoenix.DatePicker({
			input: SendMowTimeTime1,
			isLockInputType: false
		});
		SendMowTimeDt1.show();
		SendMowTimeDt1.addEvent('afterSetValue',
		function() {
			SendMowTimeTime2.focus();
			SendMowTimeTime2.click();
		})
	});
	SendMowTimetime.addEvent('afterFocus',
	function() {
		SendMowTimeTime1.click()
	});
	SendMowTimeTime2.click(function() {
		SendMowTimeDt2 = new phoenix.DatePicker({
			input: SendMowTimeTime2,
			isLockInputType: false
		}).show();
	});
	SendMowTimetime.addEvent('afterBlur',function() {
		if (SendMowTimeDt1) {
			SendMowTimeDt1.hide();
		}
		if (SendMowTimeDt2) {
			SendMowTimeDt2.hide();
		}
	});
	//mow返回结果时间
	var ResultMowTime = new phoenix.SuperSearch({
		name: 'ResultMowTime',		
		type: 'time',
		el: '#J-sp-td-ResultMowTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var ResultMowTimeTime1 = $('#sp-input-ResultMowTime-time-1'),ResultMowTimeTime2 = $('#sp-input-ResultMowTime-time-2'),ResultMowTimeDt1,ResultMowTimeDt2;
	ResultMowTimeTime1.click(function() {
		ResultMowTimeDt1 = new phoenix.DatePicker({
			input: ResultMowTimeTime1,
			isLockInputType: false
		});
		ResultMowTimeDt1.show();
		ResultMowTimeDt1.addEvent('afterSetValue',
		function() {
			ResultMowTimeTime2.focus();
			ResultMowTimeTime2.click();
		})
	});
	ResultMowTime.addEvent('afterFocus',
	function() {
		ResultMowTimeTime1.click()
	});
	ResultMowTimeTime2.click(function() {
		ResultMowTimeDt2 = new phoenix.DatePicker({
			input: ResultMowTimeTime2,
			isLockInputType: false
		}).show();
	});
	ResultMowTime.addEvent('afterBlur',function() {
		if (ResultMowTimeDt1) {
			ResultMowTimeDt1.hide();
		}
		if (ResultMowTimeDt2) {
			ResultMowTimeDt2.hide();
		}
	});
	//申请提现卡
	var ApplyCards = new phoenix.SuperSearch({
		name: 'ApplyCards',		
		el: '#J-sp-ApplyCards',
		group: group
	});
	//申请提现户名
	var ApplyUsername = new phoenix.SuperSearch({
		name: 'ApplyUsername',		
		el: '#J-sp-ApplyUsername',
		group: group
	});

     //DP時間
    var OperatingTimetime = new phoenix.SuperSearch({
        name: 'OperatingTimetime',        
        type: 'time',
        el: '#J-sp-td-OperatingTime',
        group: group,
        expands:{getFormValue:getTowInputValue}
    });
    var OperatingTimetime1 = $('#sp-input-OperatingTime-time-1'),OperatingTimetime2 = $('#sp-input-OperatingTime-time-2'),OperatingTimeDt1,OperatingTimeDt2;
    OperatingTimetime1.click(function() {
        OperatingTimeDt1 = new phoenix.DatePicker({
            input: OperatingTimetime1,
            isLockInputType: false
        });
        OperatingTimeDt1.show();
        OperatingTimeDt1.addEvent('afterSetValue',
        function() {
            OperatingTimetime2.focus();
            OperatingTimetime2.click();
        })
    });
    OperatingTimetime.addEvent('afterFocus',
    function() {
        OperatingTimetime1.click()
    });
    OperatingTimetime2.click(function() {
        OperatingTimeDt2 = new phoenix.DatePicker({
            input: OperatingTimetime2,
            isLockInputType: false
        }).show();
    });
    OperatingTimetime.addEvent('afterBlur',function() {
        if (OperatingTimeDt1) {
            OperatingTimeDt1.hide();
        }
        if (OperatingTimeDt2) {
            OperatingTimeDt2.hide();
        }
    });
	group.addEvent('dataChange', function(){
                                             SelectByWhereInfo("0");				
	});
	SelectByWhereInfo("0");	
	function SelectByWhereInfo(pages) {		
		//当前页数据行数
		 var  per_page_number = 50;		
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
		$("#Pagination").hide();    
		$("#J-table-data>tbody").html("");				
		$.ajax({
			url:'/admin/Reporting/index?parma=vdtr',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){	
				TableStyle("showInfo",22,1,"查询中");
			},
			success:function(data){				
				if(data.text.length>0){					
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
					$.each(re, function (i) {	
							html += "<tr><td>" + re[i].sn + "</td>";	
							html += "<td>" + re[i].Msn + "</td>";
                                                                                                                                                              html += "<td>" + re[i].withdrawMode + "</td>";
							html += "<td>" + re[i].oddStatus + "</td>";
							html += "<td>" + re[i].userName + "</td>";
							html += "<td>" + re[i].group + "</td>";
							html += "<td>" + re[i].applyAmount + "</td>";
							html += "<td>" + re[i].applyTime + "</td>";
							html += "<td>" + re[i].bankName + "</td>";
							//查看详情
							if($("#WITHDRAW_LOOKDETAIL").val()=='1'){
								html += "<td>" + "<a href='javascript:void(0);' name='checkDeatil' style='position:initial'>查看详情</a>" + "</td>";						
							}													
							html += "<td>" + re[i].depositeMode + "</td>";
							html += "<td>" + re[i].apProject + "</td>";
							html += "<td>" + re[i].sendToMsnTime+ "</td>";
							html += "<td>" + re[i].mowRcvDate + "</td>";
							html += "<td>" + re[i].realWithDrawAmt + "</td>";
							html += "<td>" + re[i].mcnSuccTime+ "</td>";
							html += "<td>" + re[i].bankNumber + "</td>";
							html += "<td>" + re[i].bankAccount + "</td>";	
                            html += "<td>" + re[i].operatingTime + "</td></tr>";	
					});
					$("#J-table-data>tbody").html(html);					
						
				}else{
					 $("#Pagination").hide();      
					TableStyle("J-table-data>tbody",19,2,"没有相应数据");
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",19,2,"数据异常");  
			}
		});			
	}	
	//报表下载	
	$('#J-Download-Report').click( function () { 
		//$.get("/admin/Reporting/index?parma=drex",group.getFormData());
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Reporting/index?parma=drex"+param);
	});	
	
	//查看详情
	$(document).on('click', '[name="checkDeatil"]', function(e){
		var oTr=$(this).parent().parent();		
		var sdata="sn="+$.trim(oTr.find("td:eq(0)").text())+"&type=1"+"&Title=2";
		$.ajax({
			url:'/admin/Reporting/index?parma=sv521',				
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
					$("#sp-li-drawbank2").text(data["data"]["sn"]);
					$("#sp-li-drawName3").text(data["data"]["Msn"]);
					$("#sp-li-drawName4").text(data["data"]["mcMemo"]);
					$("#sp-li-drawName5").text(data["data"]["oddStatus"]);
					$("#sp-li-drawName6").text(data["data"]["mowRcvDate"]);
					$("#sp-li-drawName7").text(data["data"]["depositeMode"]);
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
	
	$(document).on('click', '[name="closeIcoDiv1"]', function(e){
		box1.Close();
			
	});
	
	
})();	
</script>
{/literal}
</body>
</html>
