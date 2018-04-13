	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">客户提现</a> &gt; 处理失败</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="/admin/Rechargemange/customersdraw?swithval=1">客户提现</a></li>
									<li ><a href="/admin/Rechargemange/customersdraw?swithval=2">等待审核</a></li>
									<li ><a href="/admin/Rechargemange/customersdraw?swithval=3">审核未过</a></li>
									<li ><a href="/admin/Rechargemange/customersdraw?swithval=4">处理成功</a></li>
									<li class="current"><a href="/admin/Rechargemange/customersdraw?swithval=5">付款失败</a></li>
								</ul>
							</div>
							<div class="ui-tab-content">
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
                                        	<th  id="J-sp-serial" class="sp-td">
                                                <input id="J-input-sortBy" type="hidden"  value="1">
                                                <div class="sp-td-cont">
                                                    <div class="sp-td-title">交易流水号</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b ">
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
                                                    <ul class="sp-filter-cont sp-filter-cont-b ">
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
                                            <th id="J-sp-send-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">发起时间</div>
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
											                                   	
                                           <th  id="J-sp-bankinfo" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">提现银行信息</div>
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
                                            <th  id="J-sp-bankname" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">支行名称</div>
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
											<th id="J-sp-put-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请提现金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-putmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-putmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											 <th  id="J-sp-ip" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">IPV4地址</div>
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
                                            <th  id="J-sp-risktpye" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">风险类型</div>
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
                                             <th id="J-sp-audit-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">审核时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-audittime-1"> - <input type="text" id="sp-input-audittime-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            										
											<th id="J-sp-request-Status" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">等待审核</a></li>
                                                            <li data-select-id="2"><a href="#">审核未过</a></li>
                                                            <li data-select-id="3"><a href="#">处理成功</a></li>  
                                                            <li data-select-id="4"><a href="#" title="鼠标移动至该表单时，浮现出失败原因">付款失败</a></li>                                                                                                                       
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                             <th id="J-sp-palymoney-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">付款时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-palymoney-1"> - <input type="text" id="sp-input-palymoney-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-request-Audit" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">审核</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">成功<a></li>
                                                            <li data-select-id="2"><a href="#">拒绝</a></li>                                                           
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											 <th  id="J-sp-info" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">备注</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="sp-info" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
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
                                                <div id="Pagination" class="pagination" style="margin-left:600px"></div>
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
     <div id="divupdata" class="pop pop-search" >
            <div class="hd">
            <i id="CloseDiv" class="close" style="display: inline;"></i>
            备注
            </div>
      
            <div  class="search-textarea">
            <label class="label">
                <textarea id="TxtText" class="textarea" style="width:280px; height: 100px;" name="content"></textarea>      
            </label>
              <label class="label">
                <div class="ui-check">
                    <i class="error"></i>
                    请输入备注，长度不能为空.
                </div>   
            </label>
            <li class="label" style=" margin-top:10px">
                <input  type="button" value="确定" class="btn" id="chargeUpdate"/>&nbsp;&nbsp;	<input  type="button" value="取消" class="btn btn-link" id="CloseDiv2"/>      
            </li>
            </div>
            </div>
        </div>
{include file='/admin/script-base.tpl'}
{literal}	
<script>
var mowId,selectid;	//备注id,name获取
(function() {
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRemark');
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
	

//交易流水号
    var sortBy = new phoenix.SuperSearch({
        name: 'sortBy',
        keyCode: 'ctrl+83',
        el: '#J-sp-serial',
        group: group
    });
	
	//用户名
    var username = new phoenix.SuperSearch({
        name: 'username',
        keyCode: 'ctrl+83',
        el: '#J-sp-username',
        group: group
    });
	//发起时间
    var sendtime = new phoenix.SuperSearch({
        name: 'sendtime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-send-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var sendTime1 = $('#sp-input-send-time-1'),sendTime2 = $('#sp-input-send-time-2'),sendDt1,sendDt2;
    sendTime1.click(function() {
        sendDt1 = new phoenix.DatePicker({
            input: sendTime1,
            isLockInputType: false
        });
        sendDt1.show();
        sendDt1.addEvent('afterSetValue',
        function() {
            sendTime2.focus();
            sendTime2.click();
        })
    });
    sendtime.addEvent('afterFocus',
    function() {
        sendTime1.click()
    });
    sendTime2.click(function() {
        sendDt2 = new phoenix.DatePicker({
            input: sendTime2,
            isLockInputType: false
        }).show();
    });
    sendtime.addEvent('afterBlur',function() {
		if (sendDt1) {
			sendDt1.hide();
		}
		if (sendDt2) {
			sendDt2.hide();
		}
	});
	
	//提一银行信息
    var bankinfo = new phoenix.SuperSearch({
        name: 'bankinfo',
        keyCode: 'ctrl+85',
        el: '#J-sp-bankinfo',
        group: group
    });
	//支行各称
    var bankname = new phoenix.SuperSearch({
        name: 'bankname',
        keyCode: 'ctrl+85',
        el: '#J-sp-bankname',
        group: group
    });
	//申请提现金额
    var putmoney = new phoenix.SuperSearch({
        name: 'putmoney',
        keyCode: 'ctrl+83',
        el: '#J-sp-put-money',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	$('#J-input-putmoney1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-putmoney2').keyup(allowNumber).blur(allowNumber);
	//IPV4地址
    var ipinfo = new phoenix.SuperSearch({
        name: 'ipinfo',
        keyCode: 'ctrl+83',
        el: '#J-sp-ip',
        group: group
    });
	//风险类型
    var risktpye = new phoenix.SuperSearch({
        name: 'risktpye',
        keyCode: 'ctrl+85',
        el: '#J-sp-risktpye',
        group: group
    });
	
	//审核时间
    var audittime = new phoenix.SuperSearch({
        name: 'audittime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-audit-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var auditTime1 = $('#sp-input-audittime-1'),auditTime2 = $('#sp-input-audittime-2'),auditDt1,auditDt2;
    auditTime1.click(function() {
        auditDt1 = new phoenix.DatePicker({
            input: auditTime1,
            isLockInputType: false
        });
        auditDt1.show();
        auditDt1.addEvent('afterSetValue',
        function() {
            auditTime2.focus();
            auditTime2.click();
        })
    });
    audittime.addEvent('afterFocus',
    function() {
        auditTime1.click()
    });
    auditTime2.click(function() {
        auditDt2 = new phoenix.DatePicker({
            input: auditTime2,
            isLockInputType: false
        }).show();
    });
    audittime.addEvent('afterBlur',function() {
		if (auditDt1) {
			auditDt1.hide();
		}
		if (auditDt2) {
			auditDt2.hide();
		}
	});
	
	//付款时间
    var requestStatus = new phoenix.SuperSearch({
        name: 'requestStatus',
        keyCode: 'ctrl+85',
        el: '#J-sp-request-Status',
        group: group
    });
//付款时间
    var palymoneytime = new phoenix.SuperSearch({
        name: 'palymoneytime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-palymoney-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var palymoneyTime1 = $('#sp-input-palymoney-1'),palymoneyTime2 = $('#sp-input-palymoney-2'),palymoneyDt1,palymoneyDt2;
    palymoneyTime1.click(function() {
        palymoneyDt1 = new phoenix.DatePicker({
            input: palymoneyTime1,
            isLockInputType: false
        });
        palymoneyDt1.show();
        palymoneyDt1.addEvent('afterSetValue',
        function() {
            palymoneyTime2.focus();
            palymoneyTime2.click();
        })
    });
    palymoneytime.addEvent('afterFocus',
    function() {
        palymoneyTime1.click()
    });
    palymoneyTime2.click(function() {
        palymoneyDt2 = new phoenix.DatePicker({
            input: palymoneyTime2,
            isLockInputType: false
        }).show();
    });
    palymoneytime.addEvent('afterBlur',function() {
		if (palymoneyDt1) {
			palymoneyDt1.hide();
		}
		if (palymoneyDt2) {
			palymoneyDt2.hide();
		}
	});
	//状态
    var requestStatus = new phoenix.SuperSearch({
        name: 'requestStatus',
        type: 'select',
        isAutoWidth: true,
        keyCode: 'ctrl+83',
        el: '#J-sp-request-Status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	//审核
	 var auditcon = new phoenix.SuperSearch({
        name: 'audittype',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-request-Audit',
        group: group,
		expands:{getFormValue:getSelectValue}
    });	
	//备注
    var spinfo = new phoenix.SuperSearch({
        name: 'spinfo',
        keyCode: 'ctrl+85',
        el: '#J-sp-info',
        group: group
    });
	var objString;	
	SelectByWhereInfo("0");	
	//提交数据
	var dataArea = $('#showInfo');
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});	
	function SelectByWhereInfo(pages) {
		//当前页数据行数
		 var  per_page_number = 15;		
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
			url:'/admin/Rechargemange/customersdrawda',
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
					var html = "",objString;
					//数据填充
					$.each(re, function (i) {	
						if(re[i].status=="4"){
							html += "<tr><td>" + re[i].sn + "</td>";						
							html += "<td>" + re[i].applyAccount + "</td>";
							html += "<td>" + re[i].applyTime + "</td>";
							html += "<td>" + re[i].userBankStruc + "</td>";							
							html += "<td>" + re[i].bankName + "</td>";
							html += "<td>" + re[i].withdrawAmt + "</td>";
							html += "<td>" + re[i].ipAddr + "</td>";							
							html += "<td>" + re[i].riskType + "</td>";//风险类型
							html += "<td>" + re[i].apprTime + "</td>";	//审核时间										
							switch(re[i].status)
							{
								case "1":
									html += "<td><span id='stu_"+i+"' style='color:red'>付款失败</span></td>";		
									html += "<td >" + re[i].applyTime + "</td>";						
									html += "<td ></td>";
									break;											
								default:
									html += "<td><span  style='color:red'>付款失败</span></td>";								
									html += "<td>" + re[i].applyTime + "</td>";
									html += "<td ></td>";
									break;							
							}			
							objString =(re[i].ipAddr==""?"":re[i].ipAddr.substring(0,5) + "...") ;														
							html += "<td class='table-tool'><span title='"+re[i].ipAddr+"' pro_span='span_"+i+"'> "+re[i].ipAddr+" </span></td></tr>";	
						}	
						
					});
					$("#J-table-data>tbody").html(html);
					$('.ui-tab-content').removeClass(); 
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