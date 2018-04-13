<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt; 充值明细</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<table id="J-table-data" class="table table-info table-function">
							<thead>
								<tr>
									<th rowspan="2" id="J-sp-serial" class="sp-td">
                                    	<input id="J-input-sortBy" type="hidden"  value="1">
										<div class="sp-td-cont">
											<div class="sp-td-title">交易流水号</div>
											<ul class="sp-filter-cont">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-serial" type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th rowspan="2" id="J-sp-username" class="sp-td">
										<div class="sp-td-cont">
											<div class="sp-td-title">用户名</div>
											<ul class="sp-filter-cont">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-username" type="text" class="input w-2" size="10" maxlength="16"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									
									</th>
									<th colspan="4">充值申请信息</th>
									<th colspan="5">实际到账信息</th>
									<th rowspan="2" id="J-sp-order-status" class="sp-td">
										<div class="sp-td-cont">
											<div class="sp-td-title">订单处理状态</div>
											<div class="sp-filter-cont sp-filter-cont-select">
												<ul>
													<li data-select-id="1" ><a href="#" ><span style=" margin-left:-70px;">等待审核</span></a></li>
													<li data-select-id="2"><a href="#" ><span style=" margin-left:-70px;">审核未过</span></a></li>
													<li data-select-id="3"><a href="#"><span style=" margin-left:-70px;">退款成功</span></a></li>
													<li data-select-id="4"><a href="#"><span style=" margin-left:-70px;">退款失败</span></a></li>
													<li data-select-id="5"><a href="#"><span style=" margin-left:-70px;">加币成功</span></a></li>
													<li data-select-id="6"><a href="#"><span style=" margin-left:-70px;">已没收</span></a></li>
												</ul>
											</div>
											<span class="sp-filter-close"></span>
										</div>
									
									</th>
								</tr>
									<th id="J-sp-request-time" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">申请时间</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input type="text" tabindex="1" class="input w-2" id="sp-input-time-1"> - <input type="text" id="sp-input-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-request-bank" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">申请充值银行</div>
											<div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
												<ul>
													<li data-select-id="1"><a href="#">招商银行</a></li>
													<li data-select-id="2"><a href="#">建设银行</a></li>
													<li data-select-id="3"><a href="#">农业银行</a></li>
													<li data-select-id="4"><a href="#">平安银行</a></li>
													<li data-select-id="5"><a href="#">浦发银行</a></li>
												</ul>
											</div>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-request-money" class="sp-td">

										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">申请金额</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-requestmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-requestmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									
									
									</th>
									<th id="J-sp-message" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">附言</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<div class="input-append">
														<input type="text" class="input w-2" size="10" maxlength="200"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-receive-time" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">到账时间</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input type="text" id="sp-input-receive-time-1" class="input w-2" tabindex="1" /> - <input type="text" id="sp-input-receive-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-account-name" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">账户名</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input type="text" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-card-number" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">付款卡号</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="width:210px;">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-cardnumber" type="text" class="input w-3" size="10" maxlength="30"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-receive-money" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">到账金额</div>
											<ul class="sp-filter-cont sp-filter-cont-b" style="">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-receivemoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-receivemoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									</th>
									<th id="J-sp-loading-money" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">客户充值手续费</div>
											<ul class="sp-filter-cont sp-filter-cont-b">
												<li>
													<a class="sp-link-sort" href="#">正序</a>
													<a class="sp-link-sort" href="#">倒序</a>
												</li>
												<li>
													<div class="input-append">
														<input id="J-input-loadingmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-loadingmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
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
                                    <td colspan="15">
                                        <div id="Pagination" class="pagination" style=" margin-left:600px"></div>
                                    </td>
                                </tr>
                            </tfoot>
                    	 </table>					
					</div>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
(function() {
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRechargemange');
	//操作组
	//debugger
    var group = new phoenix.SuperSearchGroup();
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
	
	
	//编号
    var serial = new phoenix.SuperSearch({
        name: 'serial',
        keyCode: 'ctrl+83',
        el: '#J-sp-serial',
        group: group
    });
	$('#J-input-serial').keyup(function(){
		this.value = allowWordAndNumber(this.value);
	}).blur(function(){
		this.value = allowWordAndNumber(this.value);
	});
	//用户名
    var username = new phoenix.SuperSearch({
        name: 'username',
        keyCode: 'ctrl+85',
        el: '#J-sp-username',
        group: group
    });
	$('#J-input-username').keyup(function(){
		this.value = allowWordAndNumber(this.value);
	}).blur(function(){
		this.value = allowWordAndNumber(this.value);
	});
	//申请时间
    var requesttime = new phoenix.SuperSearch({
        name: 'requesttime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-request-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var requestTime1 = $('#sp-input-time-1'),requestTime2 = $('#sp-input-time-2'),requestDt1,requestDt2;
    requestTime1.click(function() {
        requestDt1 = new phoenix.DatePicker({
            input: requestTime1,
            isLockInputType: false
        });
        requestDt1.show();
        requestDt1.addEvent('afterSetValue',
        function() {
            requestTime2.focus();
            requestTime2.click();
        })
    });
    requesttime.addEvent('afterFocus',
    function() {
        requestTime1.click()
    });
    requestTime2.click(function() {
        requestDt2 = new phoenix.DatePicker({
            input: requestTime2,
            isLockInputType: false
        }).show();
    });
    requesttime.addEvent('afterBlur',function() {
		if (requestDt1) {
			requestDt1.hide();
		}
		if (requestDt2) {
			requestDt2.hide();
		}
	});
	//申请充值银行
    var banks = new phoenix.SuperSearch({
        name: 'bank',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-request-bank',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	//申请金额
    var requestMoney = new phoenix.SuperSearch({
        name: 'requestMoney',
        keyCode: 'ctrl+85',
        el: '#J-sp-request-money',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	$('#J-input-requestmoney1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-requestmoney2').keyup(allowNumber).blur(allowNumber);
	//附言
    var message = new phoenix.SuperSearch({
        name: 'message',
        keyCode: 'ctrl+85',
        el: '#J-sp-message',
        group: group
    });
	//到账时间
    var receivetime = new phoenix.SuperSearch({
        name: 'receivetime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-receive-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var receiveTime1 = $('#sp-input-receive-time-1'),receiveTime2 = $('#sp-input-receive-time-2'),receiveDt1,receiveDt2;
    receiveTime1.click(function() {
        receiveDt1 = new phoenix.DatePicker({
            input: receiveTime1,
            isLockInputType: false
        });
        receiveDt1.show();
        receiveDt1.addEvent('afterSetValue',
        function() {
            receiveTime2.focus();
            receiveTime2.click();
        })
    });
    receivetime.addEvent('afterFocus',
    function() {
        receiveTime1.click()
    });
    receiveTime2.click(function() {
        receiveDt2 = new phoenix.DatePicker({
            input: receiveTime2,
            isLockInputType: false
        }).show();
    });
    receivetime.addEvent('afterBlur',function() {
		if (receiveDt1) {
			receiveDt1.hide();
		}
		if (receiveDt2) {
			receiveDt2.hide();
		}
	});
	//账户名
    var accountName = new phoenix.SuperSearch({
        name: 'accountName',
        keyCode: 'ctrl+83',
        el: '#J-sp-account-name',
        group: group
    });
	//付款卡号
    var cardnumber = new phoenix.SuperSearch({
        name: 'cardnumber',
        keyCode: 'ctrl+83',
        el: '#J-sp-card-number',
		isAutoWidth: true,
        group: group
    });
	$('#J-input-cardnumber').keyup(allowNumber).blur(allowNumber);
	//到账金额
    var receivemoney = new phoenix.SuperSearch({
        name: 'receivemoney',
        keyCode: 'ctrl+83',
        el: '#J-sp-receive-money',
        group: group
    });
	$('#J-input-receivemoney1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-receivemoney2').keyup(allowNumber).blur(allowNumber);
	//充值手续费
    var receivemoney = new phoenix.SuperSearch({
        name: 'loadingmoney',
        keyCode: 'ctrl+83',
        el: '#J-sp-loading-money',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	$('#J-input-loadingmoney1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-loadingmoney2').keyup(allowNumber).blur(allowNumber);
	//订单处理状态
    var orderstatus = new phoenix.SuperSearch({
        name: 'orderstatus',
        type: 'select',
        isAutoWidth: true,
        keyCode: 'ctrl+83',
        el: '#J-sp-order-status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	
	SelectByWhereInfo("0");
	
	//提交数据
	var dataArea = $('#showInfo');
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});
	
	function SelectByWhereInfo(pages) {
		//当前页数据行数
		 var  per_page_number = 15;//每页显示条数		
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
		 $.ajax({
			url:'/admin/Rechargemange/recharge',
			dataType:'json',
			method:'post',
			data:formData,
			beforeSend:function(){
				mask.dom.addClass('admin-mask-loading');
				mask.css({opacity:.9,backgroundColor:'#FFF'});
				mask.show(dataArea);
				mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
			},
			success:function(data){
				//debugger
				if(data.text){
					$("#Pagination").show();
					//var resultAll = JSON.parse(data); //jQuery.parseJSON    
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
						 html += "<tr><td>" + re[i].sn + "</td>";
						html += "<td>" + re[i].applyTime + "</td>";
						html += "<td>" + re[i].chargeTime + "</td>";
						html += "<td>" + re[i].applyAmt + "</td>";
						html += "<td>" + re[i].chargeAmt + "</td>";
						html += "<td>" + re[i].bankId + "</td>";
						html += "<td>" + re[i].status + "</td>";
						html += "<td>" + re[i].chargeMemo + "</td>";
						html += "<td>" + re[i].account + "</td>";
						html += "<td>" + re[i].userAct + "</td>";
						html += "<td>" + re[i].cardNumber + "</td>";
						html += "<td>" + re[i].mcFee + "</td></tr>";
					});
					$("#J-table-data>tbody").html(html);	
					
				}else{
					 $("#Pagination").hide();     
					 $('.ui-tab-content').removeClass();             
                    $("#J-table-data>tbody").html("<tr><td colspan=\"12\">没有相应数据</td></tr>");
				}
			},
			complete:function(){
				mask.dom.fadeOut(300, function(){
					mask.hide();
				});
			}
		});	
	}
	
	

})();
</script>
{/literal}
</body>
</html>