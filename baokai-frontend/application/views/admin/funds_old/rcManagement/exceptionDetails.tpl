<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">充值管理</a> &gt;  <a href="#">异常充值</a> &gt;等待审核</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
                                    <li ><a href="/admin/Rechargemange/exceptrecharge?swithval=1">异常处理</a></li>
									<li class="current"><a href="/admin/Rechargemange/exceptrecharge?swithval=2">等待审核</a></li>
									<li><a href="/admin/Rechargemange/exceptrecharge?swithval=3">审核未过</a></li>
									<li><a href="/admin/Rechargemange/exceptrecharge?swithval=4">处理成功</a></li>
									<li><a href="/admin/Rechargemange/exceptrecharge?swithval=5">退款失败</a></li>
									<!-- <li class="more"><a href="/admin/Rechargemange/dealingexp">发起异常处理</a></li> -->
								</ul>
							</div>
							</div>
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>                                        	
                                           <th  id="J-sp-serial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">MOW单号</div>
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
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-send-time-1"> - <input type="text" id="sp-input-send-time-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                    										
											<th id="J-sp-request-handletype" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">处理类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">发起退款</a></li>
                                                            <li data-select-id="2"><a href="#">加游戏币</a></li>
                                                            <li data-select-id="3"><a href="#">没收处理</a></li>                                                           
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>                                            
											<th id="J-sp-request-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">金额</div>
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
                                             <th  id="J-sp-receivablesinfo" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款方信息</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="receivablesinfo" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>											
											 <th >开户行地址</th>                                               
											 <th >开户行地址</th>                                             
											 <th  id="J-sp-initiatingmaterial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">发起材料</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" id="initiatingmaterial" class="input w-2" size="10" maxlength="20"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
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
                                            <th id="J-sp-end-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">结束时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-endtime-1"> - <input type="text" id="sp-input-endtime-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
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
                                                            <li data-select-id="3"><a href="#">退款成功</a></li>  
                                                            <li data-select-id="4"><a href="#" title="鼠标移动至该表单时，浮现出失败原因">退款失败</a></li>  
                                                            <li data-select-id="5"><a href="#">加币成功</a></li>  
                                                            <li data-select-id="6"><a href="#">已没收</a></li>                                                           
                                                        </ul>
                                                    </div>
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
	selectMenu('Menufunds','MenuRechargemangeConfig');
	
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
	

	//MOW订号
    var movnumber = new phoenix.SuperSearch({
        name: 'movnumber',
        keyCode: 'ctrl+83',
         el: '#J-sp-serial',
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
	
	//处理类型
    var handletype = new phoenix.SuperSearch({
        name: 'handletype',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-request-handletype',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	//金额
    var requestMoney = new phoenix.SuperSearch({
        name: 'requestMoney',
        keyCode: 'ctrl+85',
        el: '#J-sp-request-money',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	//用户名
    var username = new phoenix.SuperSearch({
        name: 'username',
        keyCode: 'ctrl+85',
        el: '#J-sp-username',
        group: group
    });
	//收款方信息
    var receivablesinfo = new phoenix.SuperSearch({
        name: 'receivablesinfo',
        keyCode: 'ctrl+83',
         el: '#J-sp-receivablesinfo',
        group: group
    });	
	//发起材料
    var initiatingmaterial = new phoenix.SuperSearch({
        name: 'initiatingmaterial',
        keyCode: 'ctrl+83',
         el: '#J-sp-initiatingmaterial',
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
    var audittimeTime1 = $('#sp-input-audittime-1'),audittimeTime2 = $('#sp-input-audittime-2'),audittimeDt1,audittimeDt2;
    audittimeTime1.click(function() {
        audittimeDt1 = new phoenix.DatePicker({
            input: audittimeTime1,
            isLockInputType: false
        });
        audittimeDt1.show();
        audittimeDt1.addEvent('afterSetValue',
        function() {
            audittimeTime2.focus();
            audittimeTime2.click();
        })
    });
    audittime.addEvent('afterFocus',
    function() {
        audittimeTime1.click()
    });
    audittimeTime2.click(function() {
        audittimeDt2 = new phoenix.DatePicker({
            input: audittimeTime2,
            isLockInputType: false
        }).show();
    });
    audittime.addEvent('afterBlur',function() {
		if (audittimeDt1) {
			audittimeDt1.hide();
		}
		if (audittimeDt2) {
			audittimeDt2.hide();
		}
	});
	//结束时间
    var endtime = new phoenix.SuperSearch({
        name: 'endtime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-end-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var endtimeTime1 = $('#sp-input-endtime-1'),endtimeTime2 = $('#sp-input-endtime-2'),endtimeDt1,endtimeDt2;
    endtimeTime1.click(function() {
        endtimeDt1 = new phoenix.DatePicker({
            input: endtimeTime1,
            isLockInputType: false
        });
        endtimeDt1.show();
        endtimeDt1.addEvent('afterSetValue',
        function() {
            endtimeTime2.focus();
            endtimeTime2.click();
        })
    });
    endtime.addEvent('afterFocus',
    function() {
        endtimeTime1.click()
    });
    endtimeTime2.click(function() {
        endtimeDt2 = new phoenix.DatePicker({
            input: endtimeTime2,
            isLockInputType: false
        }).show();
    });
    endtime.addEvent('afterBlur',function() {
		if (endtimeDt1) {
			endtimeDt1.hide();
		}
		if (endtimeDt2) {
			endtimeDt2.hide();
		}
	});
	//处理类型
    var statustype = new phoenix.SuperSearch({
        name: 'statustype',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-request-Status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });	
	//审核
    var audittype = new phoenix.SuperSearch({
        name: 'audittype',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-request-Audit',
        group: group,
		expands:{getFormValue:getSelectValue}
    });	
	//备注
    var info = new phoenix.SuperSearch({
        name: 'info',
        keyCode: 'ctrl+83',
         el: '#J-sp-info',
        group: group		
    });
	var formData = group.getFormData(),mask = phoenix.Mask.getInstance(),objString;	
	SelectByWhereInfo("0");	
	//提交数据
	var dataArea = $('#showInfo');
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0");				
	});	
	function SelectByWhereInfo(pages) {
		//当前页数据行数
		 var  per_page_number = 2;		
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
		
		 var dataArea = $('#showInfo'),formData = group.getFormData();		
		 $("#J-table-data>tbody").html("");				
		 $.ajax({
			url:'/admin/Rechargemange/index?parma=dv1',
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
						if(re[i].status=="0"){
							html += "<tr><td>" + re[i].mcSn + "</td>";
							html += "<td>" + re[i].mcExactTime + "</td>";
							html += "<td>" + re[i].TradeStatus + "</td>";
							html += "<td>" + re[i].realChargeAmt + "</td>";
							html += "<td>" + re[i].tradeUserAct + "</td>";
							html += "<td>" + re[i].bankId + "</td>";
							html += "<td>" + re[i].bankName + "</td>";
							html += "<td>" + re[i].bankAddr + "</td>";
							html += "<td><a herf='javascript(0)'>" + re[i].attachment + "</a></td>";
							html += "<td>" + re[i].applyTime + "</td>";
							html += "<td>" + re[i].mcNoticeTime + "</td>";	
							switch(re[i].status)
							{
								case "0":
									html += "<td><span id='stu_"+i+"'>等待审核</span></td>";
									html += "<td class='sele_"+i+"'><select name='sele_status' style=\"margin:-2px\" onchange='Updatestatus(this)' id='sele_"+i+"' pro='"+re[i].mcSn+"' pro_s='stu_"+i+"' ><option value='0' selected='true'>--选择操作--</option><option value='1'>加币成功</option><option value='2'>退款成功</option><option value='3'>没&nbsp;&nbsp;收</option><option ion value='4'>发起异常处理</option></select></td>";
									break;	
								default : 
									html += "<td></td>";
									html += "<td></td>";
									break;		
							}	
							objString =(re[i].memo==""?"":re[i].memo.substring(0,5) + "...") ;						
							html += "<td class='table-tool'><span title='"+re[i].memo+"' pro_span='span_"+i+"'>" + objString + "</span><a name='span_"+i+"' class='ico-edit' id='"+re[i].mcSn+"' onclick=\"Updateedit(this)\"  title='编辑' href='javascript:void(0);'></a></td></tr>";		
						}
					});
					$("#J-table-data>tbody").html(html);
					
				}else{
					 $("#Pagination").hide();                 
                    $("#J-table-data>tbody").html("<tr><td colspan=\"14\">没有相应数据</td></tr>");
				}				
			},
			complete:function(){
				mask.dom.fadeOut(300, function(){
					mask.hide();
				});
			}
		});			
	}	
	
	//隐藏备注层
	$('#CloseDiv2,#CloseDiv').click(function(e){	
		$('#TxtText').val('');
		$('#divupdata').dialog("close");			
		e.preventDefault(); 	
		return false;
	});
	
	//确定修改备注
	$('#chargeUpdate').click(function(e){		
		if($('#TxtText').val()!=""){
			if(mowId==''){	return false;}
			var exceptId = mowId;
			$.ajax({
				url:'/admin/Rechargemange/exceptrechargememo',
				dataType:'json',
				method:'post',
				data:'exceptId='+exceptId+'&applyMemo='+$('#TxtText').val().trim(),
				beforeSend:function(){
					mask.dom.addClass('admin-mask-loading');
					mask.css({opacity:.9,backgroundColor:'#FFF'});
					mask.show(dataArea);
					mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
				},
				success:function(data){
					if(data.stats=="1"){
						e.preventDefault(); 
						//成功后把TxtText内容给当前
						var inpo=($('#TxtText').val().trim()==""?"":$('#TxtText').val().trim().substring(0,5) + "...")							
						$("[pro_span='"+selectid+"']").html(inpo);	
						//清空层状态
						$('#TxtText').val('');						
						$('#divupdata').find('.ui-check').css('display', 'none');
						$('#divupdata').dialog("close");
					}
					else{
						alert("操作失败,请核实您是否有此权限");	
					}					
				},
				complete:function(){
					mask.dom.fadeOut(300, function(){
						mask.hide();
					});
				}
			});			
		}	
		else{
			$('#divupdata').find('.ui-check').css('display', 'inline');
			return false;
		}
	});	
	
})();	
	//备注层展示(获取参数)
	function Updateedit(obj){			
		mowId = $(obj).attr("id");	
		selectid=$(obj).attr("name");	
		//cleaninfo();
		$('#divupdata').dialog({  
			modal:false,		
			height: 270,
			width: 350			
		});	
		$('.ui-button').hide();	
	}
	
	//审核id,name
	function Updatestatus(obj){		
		var	updatid = $(obj).attr("pro");	//mow单号
		var udpatname=$(obj).attr("id");	//当前控件id
		var ups=$(obj).attr("pro_s");//获取状态id
		var upid=$('#'+udpatname).val().trim();		
		if((upid!=''&&updatid!='') || upid!='0')
		{
			var group = new phoenix.SuperSearchGroup(),dataArea = $('#showInfo'),mask = phoenix.Mask.getInstance();	
			$.ajax({
				url:'/admin/Rechargemange/exceptaudit',
				dataType:'json',
				method:'post',
				data:'mowId='+updatid+'&opterID='+upid,				
				success:function(data){
					if(data.status=="1"){
						//不刷新页面，处理html控件
						$('#'+udpatname).hide();	
						if(upid=='1'){
							$('.'+udpatname).append("<span>成功</span>");
							$('#'+ups).html('加币成功');
							//$('.'+udpatname).parents("tr").css("background-color","E9FBF8");
						}else if(upid=='2'){
							$('.'+udpatname).append("<span>成功</span>");
							$('#'+ups).html('退款成功');
							//$('#'+ups).parents("tr").css("background-color","E9FBF8");
						}
						else if(upid=='3'){
							$('.'+udpatname).append("<span>成功</span>");
							$('#'+ups).html('已没收');	
						}
						else if(upid=='4'){
							window.location.href="/admin/Rechargemange/sechandldetail?movnumber="+updatid; 
						}
						else{
							
							//$('#'+ups).parents("tr").css("background-color","E9FBF8");						
						}
					}
					else{
						alert("审核失败,请核实您是否有此权限");	
					}					
				}
				
				
			});	
		}
		else{ return false;}		
	 }
	
	

</script>
{/literal}
</body>
</html>