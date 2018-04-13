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
					<div class="main" >
						<!-- {if "FUND_REPORT_CHARGE_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
						<h3 class="ui-title"><a class="btn btn-small" style="float:left;"  href="javascript:void(0);" id="J-Download-Report">下载报表<b class="btn-inner"></b></a></h3>
						<!-- {/if} -->
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
										
                                            <th id="J-sp-TransactionNumber" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">平台订单号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">                                                         
                                                        <li>
                                                            <div class="input-append">
                                                             <input type="text" class="input w-2" size="10"  value="{$Direction}" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>											
                                             <th id="J-sp-OrderStatus" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">订单状态</div>
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
                                            <th class="sp-td" id="J-sp-td-ApplicationPeriod">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请时间</div>
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
											<th>申请金额</th>
											<th  id="J-sp-rcvBankName" class="sp-td">
	                                            <div class="sp-td-cont sp-td-cont-b">
	                                                <div class="sp-td-title">申请充值银行</div>
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
											<th  id="J-sp-applyChannel" class="sp-td">
	                                            <div class="sp-td-cont sp-td-cont-b">
	                                                <div class="sp-td-title">申请渠道</div>
	                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
	                                                        <ul>
	                                                        	<!-- {foreach from=$aDepositeMode item=data key=key} -->
	                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/foreach} -->
	                                                        </ul>
	                                                </div>
	                                                <span class="sp-filter-close"></span>
	                                            </div>
                                        	</th>
											<!-- <th>申请平台</th> -->
											<th  id="J-sp-system" class="sp-td">
	                                            <div class="sp-td-cont sp-td-cont-b">
	                                                <div class="sp-td-title">申請平台</div>
	                                                <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
	                                                        <ul>
	                                                        	<!-- {foreach from=$aSystem item=data key=key} -->
	                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
	                                                            <!-- {/foreach} -->
	                                                        </ul>
	                                                </div>
	                                                <span class="sp-filter-close"></span>
	                                            </div>
                                        	</th>
											<th>收款银行</th>											
											<th>收款卡账户名</th>											
                                             <th id="J-sp-CardReceivables" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款卡</div>
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
                                            
											<th>平台附言</th>
											<th>银行实际收款卡</th>											
                                            <th class="sp-td" id="J-sp-td-TradingTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">交易时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-TradingTime-time-1"> - <input type="text" id="sp-input-TradingTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>
											<th>金额</th>
											<th>手续费</th>	
											<th>实际付款银行</th>										
                                            <th id="J-sp-PaymentCards" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款卡</div>
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
                                             <th id="J-sp-PaymentName" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款户名</div>
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
                                            <th>玩家附言</th>	
											<th>mow返回结果时间</th>											
                                             <th class="sp-td" id="J-sp-td-AddGameCurrencyTime">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">加游戏币时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-AddGameCurrencyTime-time-1"> - <input type="text" id="sp-input-AddGameCurrencyTime-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>                                                        
                                            </th>
                                             <th class="sp-td" id="J-sp-td-OperatingTime">                                    
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">操作录入时间</div>
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
										</tr>
									</thead>
								<tbody id="showInfo">
                                </tbody>                                
                                <tfoot>
                                    <tr>
                                        <td colspan="33">
                                              <div id="Pagination" class="pagination" style=" margin-right:10px"></div>
                                        </td>
                                    </tr>
                                </tfoot>
                             </table>    
								
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
(function() {
	var isture=false;
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuReport');
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
	
	var TransactionNumber = new phoenix.SuperSearch({
		name: 'Direction',		
		el: '#J-sp-TransactionNumber',
		group: group
	});
	if($("#J-sp-TransactionNumber input").val()!=''){
		$('#J-sp-TransactionNumber').addClass('sp-td-havevalue');
		$('#J-sp-TransactionNumber').find('div.sp-td-title').html($("#J-sp-TransactionNumber input").val());
		$('#J-sp-TransactionNumber span.sp-filter-close').attr('display','inline');
	}
	//状态
	 var OrderStatus = new phoenix.SuperSearch({
		name: 'OrderStatus',		
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-OrderStatus',
		group: group,
		expands:{getFormValue:getSelectValue}
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
	//申请充值银行
	 var rcvBankName = new phoenix.SuperSearch({
		name: 'rcvBankName',		
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-rcvBankName',
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
	// 充值平台
	 var system = new phoenix.SuperSearch({
		name: 'system',		
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-system',
		group: group,
		expands:{getFormValue:getSelectValue}
	});
	
	//起始日期
	var ApplicationPeriodtime = new phoenix.SuperSearch({
		name: 'ApplicationPeriodtime',
		keyCode: 'ctrl+68',
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
	var CardReceivables = new phoenix.SuperSearch({
		name: 'CardReceivables',		
		el: '#J-sp-CardReceivables',
		group: group
	});
	//起始日期
	var TradingTimetime = new phoenix.SuperSearch({
		name: 'TradingTimetime',		
		type: 'time',
		el: '#J-sp-td-TradingTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var TradingTimeTime1 = $('#sp-input-TradingTime-time-1'),TradingTimeTime2 = $('#sp-input-TradingTime-time-2'),TradingTimeDt1,TradingTimeDt2;
	TradingTimeTime1.click(function() {
		TradingTimeDt1 = new phoenix.DatePicker({
			input: TradingTimeTime1,
			isLockInputType: false
		});
		TradingTimeDt1.show();
		TradingTimeDt1.addEvent('afterSetValue',
		function() {
			TradingTimeTime2.focus();
			TradingTimeTime2.click();
		})
	});
	TradingTimetime.addEvent('afterFocus',
	function() {
		TradingTimeTime1.click()
	});
	TradingTimeTime2.click(function() {
		TradingTimeDt2 = new phoenix.DatePicker({
			input: TradingTimeTime2,
			isLockInputType: false
		}).show();
	});
	TradingTimetime.addEvent('afterBlur',function() {
		if (TradingTimeDt1) {
			TradingTimeDt1.hide();
		}
		if (TradingTimeDt2) {
			TradingTimeDt2.hide();
		}
	});
	//付款卡
	var PaymentCards = new phoenix.SuperSearch({
		name: 'PaymentCards',		
		el: '#J-sp-PaymentCards',
		group: group
	});
	var PaymentName = new phoenix.SuperSearch({
		name: 'PaymentName',		
		el: '#J-sp-PaymentName',
		group: group
	});
	var AddGameCurrencyTimetime = new phoenix.SuperSearch({
		name: 'AddGameCurrencyTimetime',		
		type: 'time',
		el: '#J-sp-td-AddGameCurrencyTime',
		group: group,
		expands:{getFormValue:getTowInputValue}
	});
	var AddGameCurrencyTimeTime1 = $('#sp-input-AddGameCurrencyTime-time-1'),AddGameCurrencyTimeTime2 = $('#sp-input-AddGameCurrencyTime-time-2'),AddGameCurrencyTimeDt1,AddGameCurrencyTimeDt2;
	AddGameCurrencyTimeTime1.click(function() {
		AddGameCurrencyTimeDt1 = new phoenix.DatePicker({
			input: AddGameCurrencyTimeTime1,
			isLockInputType: false
		});
		AddGameCurrencyTimeDt1.show();
		AddGameCurrencyTimeDt1.addEvent('afterSetValue',
		function() {
			AddGameCurrencyTimeTime2.focus();
			AddGameCurrencyTimeTime2.click();
		})
	});
	AddGameCurrencyTimetime.addEvent('afterFocus',
	function() {
		AddGameCurrencyTimeTime1.click()
	});
	AddGameCurrencyTimeTime2.click(function() {
		AddGameCurrencyTimeDt2 = new phoenix.DatePicker({
			input: AddGameCurrencyTimeTime2,
			isLockInputType: false
		}).show();
	});
	AddGameCurrencyTimetime.addEvent('afterBlur',function() {
		if (AddGameCurrencyTimeDt1) {
			AddGameCurrencyTimeDt1.hide();
		}
		if (AddGameCurrencyTimeDt2) {
			AddGameCurrencyTimeDt2.hide();
		}
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
		
		 var mask = phoenix.Mask.getInstance();			
		 $("#J-table-data>tbody").html("");		
		 $("#Pagination").hide();    		
		 $.ajax({
			url:'/admin/Reporting/index?parma=vddaset',
			dataType:'json',
			method:'post',
			data: group.getFormData(),
			beforeSend:function(){		
				TableStyle("showInfo",33,1,"查询中");
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
							html += "<td>" + re[i].status + "</td>";
							html += "<td>" + re[i].userAct + "</td>";
							html += "<td>" + re[i].topVip + "</td>";
							html += "<td>" + re[i].applyTime + "</td>";
							html += "<td>" + re[i].applyAmt + "</td>";
							html += "<td>" + re[i].applyBank + "</td>";
							html += "<td>" + re[i].depositeMode + "</td>";
							html += "<td>" + re[i].system + "</td>";
							html += "<td>" + re[i].rcvBankId+ "</td>";
							html += "<td>" + re[i].rcvAcct+ "</td>";
							html += "<td>" + re[i].rcvBankNumber + "</td>";
							html += "<td>" + re[i].memo+ "</td>";
							html += "<td>" + re[i].realRcvCard + "</td>";
							html += "<td>" + re[i].chargeTime + "</td>";
							html += "<td>" + re[i].realChargeAmt + "</td>";
							html += "<td>" + re[i].fee + "</td>";
							html += "<td>" + re[i].realPayBank + "</td>";
							html += "<td>" + re[i].cardNumber + "</td>";
							html += "<td>" + re[i].cardAccount + "</td>";
							html += "<td>" + re[i].realMemo + "</td>";
							html += "<td>" + re[i].mowDate + "</td>";
							html += "<td>" + re[i].addCoinTime + "</td>";
                            html += "<td>" + re[i].operatingTime + "</td></tr>";
					});
					$("#J-table-data>tbody").html(html);					
						
				}else{
					 $("#Pagination").hide();  
					 TableStyle("J-table-data>tbody",33,2,"没有相应数据");
				}				
			}, 
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 TableStyle("J-table-data>tbody",33,2,"数据异常");  
				}
		});			
	}	
	//报表下载	
	$('#J-Download-Report').click( function () { 
		//$.get("/admin/Reporting/index?parma=rechageExcels",group.getFormData());
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Reporting/index?parma=chex"+param);
	});
})();	
</script>
{/literal}
</body>
</html>
