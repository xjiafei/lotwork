<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">异常补款</a> &gt; 补款申请</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current">补款申请</li>
									<li><a href="/admin/EpReplenishment/index?parma=sv5">处理中</a></li>
									<li><a href="/admin/EpReplenishment/index?parma=sv6">补款成功</a></li>
									<li><a href="/admin/EpReplenishment/index?parma=sv4">补款失败</a></li>
									<li class="more"><a href="/admin/EpReplenishment/index?parma=sv1">发起补款申请</a></li>
								</ul>
							</div>
							<div class="ui-tab-content">
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>											
                                            <th id="J-sp-transactionsSrial" class="sp-td">
                                                <div class="sp-td-cont">
                                                    <div class="sp-td-title">交易流水号</div>
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
                                            <th id="J-sp-exceptionSerial" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">异常流水号</div>
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
											<th id="J-sp-replenishment-type" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">补款类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">异常充值</a></li>
                                                            <li data-select-id="2"><a href="#">在线提现</a></li>
                                                            <li data-select-id="3"><a href="#">人工提现</a></li>    
                                                            <li data-select-id="4"><a href="#">活动派奖</a></li>                                                                    
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>                                            
											<th  id="J-sp-beneficiary-bank" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">收款银行信息</div>
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
											<th id="J-sp-replenishment-amount" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">补款金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-replenishment1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-replenishment2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>
											 <th id="J-sp-launch-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">发起时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-launchtime-1"> - <input type="text" id="sp-input-launchtime-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-request-Status" class="sp-td">
                                                <div class="sp-td-cont">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">处理中</a></li>
                                                            <li data-select-id="2"><a href="#">补款成功</a></li>
                                                            <li data-select-id="3"><a href="#">补款失败</a></li>                                                                                                                   
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>	
                                            <th id="J-sp-failed-amount" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">失败金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-failedamount1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-failedamount2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>   
                                            </th>										
											<th id="J-sp-state-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-statetime1"> - <input type="text" id="sp-input-statetime2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>		
                                             <th  id="J-sp-Operator" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">操作</div>
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
                                            <td colspan="11">
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

(function() {
	//一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuRechargemangeConfig');
	
	//操作组
	//debugger
    var group = new phoenix.SuperSearchGroup(),objString;
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
    var transactionsSrial = new phoenix.SuperSearch({
        name: 'transactionsSrial',
        keyCode: 'ctrl+83',
         el: '#J-sp-transactionsSrial',
        group: group
    });	
	//异常流水号
    var exceptionSerial = new phoenix.SuperSearch({
        name: 'exceptionSerial',
        keyCode: 'ctrl+83',
         el: '#J-sp-exceptionSerial',
        group: group
    });
	//补款类型
    var replenishmenttype = new phoenix.SuperSearch({
        name: 'replenishmenttype',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-replenishment-type',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	
	//收款银行信息
    var beneficiarybank = new phoenix.SuperSearch({
        name: 'beneficiarybank',
        keyCode: 'ctrl+83',
         el: '#J-sp-beneficiary-bank',
        group: group
    });
	//补款金额
    var replenishmentMoney = new phoenix.SuperSearch({
        name: 'replenishmentMoney',
        keyCode: 'ctrl+85',
        el: '#J-sp-replenishment-amount',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	$('#J-input-replenishment1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-replenishment2').keyup(allowNumber).blur(allowNumber);
	//发起时间
    var launchtime = new phoenix.SuperSearch({
        name: 'launchtime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-launch-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var launchtimeTime1 = $('#sp-input-launchtime-1'),launchtimeTime2 = $('#sp-input-launchtime-2'),launchtimeDt1,launchtimeDt2;
    launchtimeTime1.click(function() {
        launchtimeDt1 = new phoenix.DatePicker({
            input: launchtimeTime1,
            isLockInputType: false
        });
        launchtimeDt1.show();
        launchtimeDt1.addEvent('afterSetValue',
        function() {
            launchtimeTime2.focus();
            launchtimeTime2.click();
        })
    });
    launchtime.addEvent('afterFocus',
    function() {
        launchtimeTime1.click()
    });
    launchtimeTime2.click(function() {
        launchtimeDt2 = new phoenix.DatePicker({
            input: launchtimeTime2,
            isLockInputType: false
        }).show();
    });
    launchtime.addEvent('afterBlur',function() {
		if (launchtimeDt1) {
			launchtimeDt1.hide();
		}
		if (launchtimeDt2) {
			launchtimeDt2.hide();
		}
	});
	
	//处理状态
    var statustype = new phoenix.SuperSearch({
        name: 'statustype',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-request-Status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });	
	//失败金额
    var failedMoney = new phoenix.SuperSearch({
        name: 'failedMoney',
        keyCode: 'ctrl+85',
        el: '#J-sp-failed-amount',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	$('#J-input-failedamount1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-failedamount2').keyup(allowNumber).blur(allowNumber);
	
	//状态时间
    var statetime = new phoenix.SuperSearch({
        name: 'statetime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-state-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
    var statetimeTime1 = $('#sp-input-statetime1'),statetimeTime2 = $('#sp-input-statetime2'),statetimeDt1,statetimeDt2;
    statetimeTime1.click(function() {
        statetimeDt1 = new phoenix.DatePicker({
            input: statetimeTime1,
            isLockInputType: false
        });
        statetimeDt1.show();
        statetimeDt1.addEvent('afterSetValue',
        function() {
            statetimeTime2.focus();
            statetimeTime2.click();
        })
    });
    statetime.addEvent('afterFocus',
    function() {
        statetimeTime1.click()
    });
    statetimeTime2.click(function() {
        statetimeDt2 = new phoenix.DatePicker({
            input: statetimeTime2,
            isLockInputType: false
        }).show();
    });
    statetime.addEvent('afterBlur',function() {
		if (statetimeDt1) {
			statetimeDt1.hide();
		}
		if (statetimeDt2) {
			statetimeDt2.hide();
		}
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
			url:'/admin/EpReplenishment/replenishment?parma=sv1',
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
							html += "<td>" + re[i].exSn + "</td>";
							html += "<td>" + re[i].extraType + "</td>";
							html += "<td>" + re[i].bankName + " " + re[i].cardName + " " + re[i].cardNo + "</td>";							
							html += "<td>" + re[i].bankBrach + "</td>";							
													
							html += "<td>" + re[i].startTime + "</td>";
							html += "<td>" + re[i].status + "</td>";	
							html += "<td>" + re[i].failMoney + "</td>";	
							html += "<td>" + re[i].statusTime + "</td>";	
							html += "<td>" + re[i].opter + "</td>";	
							
							objString =(re[i].memo==""?"":re[i].memo.substring(0,5) + "...") ;						
							html += "<td class='table-tool'><span title='"+re[i].memo+"' pro_span='span_"+i+"'>" + objString + "</span><a name='span_"+i+"' class='ico-edit' id='"+re[i].mcSn+"' onclick=\"Updateedit(this)\"  title='编辑' href='javascript:void(0);'></a></td></tr>";		
						
					});
					$("#J-table-data>tbody").html(html);
					$('.ui-tab-content').removeClass(); 
					
				}else{
					 $("#Pagination").hide();   
					 $('.ui-tab-content').removeClass();               
                     $("#J-table-data>tbody").html("<tr><td colspan=\"11\">没有相应数据</td></tr>");
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
	
	
</script>
{/literal}
</body>
</html>