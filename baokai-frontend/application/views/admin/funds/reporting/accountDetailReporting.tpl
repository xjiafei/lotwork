	<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
	<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
	<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main" id ="test1">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="/admin/Rechargemange/">资金中心</a> &gt; {$title}</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<!-- {if "FUND_REPORT_FUNDDETAIL_DOWNLOAD"|in_array:$smarty.session.datas.info.acls} -->
						<h3 class="ui-title"><a class="btn btn-small" style="float:left;"  href="javascript:void(0);" id="J-Download-Report">下载报表<b class="btn-inner"></b></a></h3>
						<!-- {/if} -->
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
                                            <th id="J-sp-PlatformNumber" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">交易流水号</div>
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
											<th id="J-sp-UserName" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">用户名</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style='width:auto;'>                                                         
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" class="input w-2" size="10" maxlength="20" value="{$UserName}"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                                <input type="checkbox" class="input w-auto" name='UserChild' id='usrchild' value='1'><label for='usrchild'>包含下级</label>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            <th class="sp-td" id="J-sp-td-ApplicationPeriod">									
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">                                                           
                                                  <li>
                                                    <div class="input-append">
                                                        <input type="text" tabindex="1" class="input w-2" id="sp-input-ApplicationPeriod-time-1" value=''> - <input type="text" id="sp-input-ApplicationPeriod-time-2" class="input w-2" size="10" value=''><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                    </div>
                                                   </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>     
                                            </th>
                                            <th id="J-sp-td-OrderType" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">摘要</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
														   <!-- {foreach from=$aType key=key item=data} -->
                                                            <li data-select-id="{$key}"><a href="#">{$data}</a></li>
                                                            <!-- {/foreach} -->
                                                      </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            <th>收入金额</th>
                                            <th>冻结金额</th>
                                            <th>支出金额</th>
                                            <th>可用余额</th>
											<th class='dynamicNote'>备注</th> 
											<div id='dynamicNoteOption' style="display:none;">{foreach from=$aNote item=data}{$data},{/foreach}</div>
										</tr>
									</thead>
									<tbody id="showInfo">
                                </tbody>                                
                                <tfoot>
                                    <tr>
                                        <td colspan="18">
                                              <div id="Pagination" class="pagination"></div>
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

function stringToTime(string){
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
    parseInt(d[0], 10) || null,
    (parseInt(d[1], 10) || 1)-1,
    parseInt(d[2], 10) || null,
    parseInt(t[0], 10) || null,
    parseInt(t[1], 10) || null,
    parseInt(t[2], 10) || null
    )).getTime();
} 

(function onLoadEvent() {
	var isture=false;	
    var d = new Date( (( Date.parse( new Date() ) / 1000 ) - ( 15 * 24 * 60 * 60 )) * 1000); // 15天前的日期
    var dp = new phoenix.DatePicker();
    var def_start_datetime = dp.formatDateTime(d.getFullYear(),d.getMonth()+1, d.getDate(), 0,0,0  );

	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuAccountDetail');	
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
	//获取select类型值
	var getMultiSelectValue = function(){
		var me = this,li = me.dom.find('li.current'),v = '', result = {};

		result[me.name] = [];

		if(li.size() > 0){
			$.each(li, function(index, val) {
				v = li.eq(index).attr('data-select-id');
				result[me.name][index] = v;	 
			});
			
		}

		result[me.name] = result[me.name].join();

		return result;
	};
	var group = new phoenix.SuperSearchGroup();	
	//平台订单号
	var PlatformNumber = new phoenix.SuperSearch({
		name: 'sn',		
		el: '#J-sp-PlatformNumber',
		group: group
	});
	
	//摘要
	 var OrderType = new phoenix.SuperSearch({
		name: 'OrderType',	
		keyCode: 'ctrl+83',	
		type: 'multi',
		isAutoWidth: true,
		titleLimit: 5,
		el: '#J-sp-td-OrderType',
		group: group,
		expands:{getFormValue:getMultiSelectValue}
	});
	 var direction = new phoenix.SuperSearch({
			name: 'direction',
			keyCode: 'ctrl+66',
			type: 'select',
			isAutoWidth: true,
			el: '#J-sp-td-Direction',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
	//用户名
	var UserName = new phoenix.SuperSearch({
		name: 'UserName',		
		el: '#J-sp-UserName',
		group: group
	});
	//備註
	var note = new phoenix.SuperSearch({
		name: 'note',		
		type: 'select',
		isAutoWidth: true,
		el: '#J-sp-td-Note',
		group: group,
		expands:{getFormValue:getSelectValue}
	});

	if($("#J-sp-UserName input").val()!=''){
		$('#J-sp-UserName').addClass('sp-td-havevalue');
		$('#J-sp-UserName').find('div.sp-td-title').html($("#J-sp-UserName input").val());
		$('#J-sp-UserName span.sp-filter-close').attr('display','inline');
	}
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
			isLockInputType: false,
			isShowTime : true
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
			isLockInputType: false,
			isShowTime : true
        });
        ApplicationPeriodDt2.show();
        ApplicationPeriodDt2.addEvent('afterSetValue',
            function() {
            	var date1 = stringToTime(ApplicationPeriodTime1.val());
                var date2 = stringToTime(ApplicationPeriodTime2.val());
         		
                if (date2 - date1 > 1000 * 60 * 60 * 24 * 32){
                	  alert('您选的日期超过30天');	

                }
                if ( ApplicationPeriodTime1.val() > ApplicationPeriodTime2.val() ) {
                    alert('结束日期不能小于开始日期。');
                    ApplicationPeriodTime2.focus();
                    ApplicationPeriodTime2.click();
                }
            })
	});
	ApplicationPeriodtime.addEvent('afterBlur',function() {
		if (ApplicationPeriodDt1) {
			ApplicationPeriodDt1.hide();
		}
		if (ApplicationPeriodDt2) {
			ApplicationPeriodDt2.hide();
		}
	});
	//確認備註欄位是否有選項
	var haveNoteOption = 0;
	group.addEvent('dataChange', function(){
		SelectByWhereInfo("0", '2');				
	});
	//SelectByWhereInfo("0", '1');	
    // def 1: 预设查询 2:条件查询
	function SelectByWhereInfo(pages, def) {		
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
		//放入group对象中(包含下级)	
		var perPageNum  = new phoenix.SuperSearch({
			name: 'UserChild', 
			group: group,			
			expands:{getValue:function(){
				return $("#usrchild").prop('checked') ? '1' : '0';	
			}}
		});	
        var data = group.getFormData();
     	
        if (data.ApplicationPeriodtime1 != '' && data.ApplicationPeriodtime2 != ''){
       		var date1 = stringToTime (data.ApplicationPeriodtime1);
        	var date2 = stringToTime (data.ApplicationPeriodtime2);
			var account = $("#J-sp-UserName input").val();
			var note    = $("#J-sp-td-Note div div :first").html();
			if(note == '备注'|| note == '' || note === null || note == undefined){
				if(account == '' && (date2 - date1 > 1000 * 60 * 60 * 24)){
					alert('您选的日期超过1天');
					return false;
				}else if(date2 - date1 > 1000 * 60 * 60 * 24 * 32){
					alert('您选的日期超过30天');
					return false;
				}
			}else{ //備註有選擇的情況				
				if(account == '' && (date2 - date1 > 1000 * 60 * 60 * 24 * 10)){
					alert('您选的日期超过10天');
					return false;
				}else if(date2 - date1 > 1000 * 60 * 60 * 24 * 32){
					alert('您选的日期超过30天');
					return false;
				}
			}

    	}
		//動態生成備註選單
		var dynamicNoteOption = $('#dynamicNoteOption').html();
		if(data.OrderType.indexOf('PGXX') > -1 ){
			if(dynamicNoteOption!=null && haveNoteOption==0 ){
				haveNoteOption = 1;
				var noteOptions = dynamicNoteOption.split(",");
				noteOptions.pop();
				var htmlOptions=""
				for(var key in noteOptions){
					htmlOptions+="<li data-select-id='"+noteOptions[key]+"'><a href='#'>"+noteOptions[key]+"</a></li>";
				}
				var html="<th id='J-sp-td-Note' class='sp-td dynamicNote'><div class='sp-td-cont sp-td-cont-b'><div class='sp-td-title' style='width:150px;'>备注";
				html+="</div><div class='sp-filter-cont sp-filter-cont-b sp-filter-cont-select' style='width:auto;'><ul>";
				html+=htmlOptions;
				html+="</ul></div><span class='sp-filter-close'></span></div></th>";
				$('.dynamicNote').replaceWith(html);
				//備註欄位重新綁定事件
				var note = new phoenix.SuperSearch({
					name: 'note',		
					type: 'select',
					isAutoWidth: true,
					el: '#J-sp-td-Note',
					group: group,
					expands:{getFormValue:getSelectValue}
				});
			}
		}else{
			haveNoteOption = 0;
			$('.dynamicNote').replaceWith("<th class='dynamicNote'>备注</th>");
		}

                	
   //     if ( data.ApplicationPeriodtime1 != '' && def_start_datetime > data.ApplicationPeriodtime1 )
   //     {
   //        alert('查询开始日期不得大于15天以上');
   //         return false;
   //     }
        if ( data.OrderType == '' && data.UserName == '' && def == '2') {
            alert('请输入用户名或选择任一帐变类型');
            return false;
        }else if(data.UserName == '' && data.OrderType == '' && data.ApplicationPeriodtime1 != '' && data.ApplicationPeriodtime2 != '' && def =='2'){
			alert('请选择日期并选择任一帐变类型');
			return false;
		}

		$("#Pagination").hide();    
		$("#J-table-data>tbody").html("");				
		$.ajax({
			url:'/admin/Reporting/index?parma=adda',
			dataType:'json',
			method:'post',
			data: data,
			beforeSend:function(){	
				TableStyle("showInfo",18,1,"查询中");
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
							if(re[i].exCode !='' && re[i].summary != '提现退回'){
								html += "<tr><td><a href='/gameoa/queryOrderDetail?orderCode=" + re[i].exCode + "'>" + re[i].sn + "</a></td>";
							} else if(re[i].planCode !='' && re[i].summary != '提现退回') {
								html += "<tr><td><a href='/gameoa/queryPlanDetail?planCode=" + re[i].planCode + "'>" + re[i].sn + "</a></td>";
							} else {
								html += "<tr><td>" + re[i].sn + "</td>";
							}
							html += "<td>" + re[i].account + "</td>";
							html += "<td>" + re[i].gmtCreated + "</td>";
							html += "<td>" + re[i].summary + "</td>";
                            // 收入金额
							if(re[i].change == 1){
								html += "<td style='color:green;text-align:right;'>" + re[i].inBal + "</td>";
							} else {
								html += "<td style='text-align:right;'>" + re[i].inBal + "</td>";
							}
                            // 冻结金额
							if(re[i].change == -1){
								html += "<td  style='color:red;text-align:right;'>" + re[i].frozeAmt + "</td>";
							} else {
								html += "<td style='text-align:right;'>" + re[i].frozeAmt + "</td>";
							}
                            // 支出金额
							if(re[i].change == -1){
								html += "<td  style='color:red;text-align:right;'>" + re[i].outBal + "</td>";
							} else {
								html += "<td style='text-align:right;'>" + re[i].outBal + "</td>";
							}
                            // 可用余额
							html += "<td style='text-align:right;'>" + re[i].ctBal + "</td>";
							html += "<td>" + re[i].note+ "</td>";
					});
                    // 本页小计
                    html += "<tr><td COLSPAN=4>&nbsp;&nbsp;&nbsp;本页小计</td>";
                    html += "<td style='text-align:right;'>" + resultAll.page_count.inBal + "</td>";
                    html += "<td style='text-align:right;'>" + resultAll.page_count.frozeAmt + "</td>";
                    html += "<td style='text-align:right;'>" + resultAll.page_count.outBal + "</td>";
                    html += "<td colspan=2>&nbsp;</td></tr>";
                    // 总计
                    html += "<tr><td COLSPAN=4>&nbsp;&nbsp;&nbsp;总计</td>";
                    html += "<td style='text-align:right;'>" + resultAll.all_count.inBal + "</td>";
                    html += "<td style='text-align:right;'>" + resultAll.all_count.frozeAmt + "</td>";
                    html += "<td style='text-align:right;'>" + resultAll.all_count.outBal + "</td>";
                    html += "<td colspan=2>&nbsp;</td></tr>";
					$("#J-table-data>tbody").html(html);					
						
				}else{
					 $("#Pagination").hide();      
					TableStyle("J-table-data>tbody",18,2,"没有相应数据");
				}				
			}, 
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 TableStyle("J-table-data>tbody",18,2,"数据异常");  
			}
		});			
	}
	TableStyle("J-table-data>tbody", 18, 2, "没有相应数据");
	//报表下载	
	$('#J-Download-Report').click( function () {
	
		var data = group.getFormData();

		if (data.ApplicationPeriodtime1 != '' && data.ApplicationPeriodtime2 != ''){
       		var date1 = stringToTime (data.ApplicationPeriodtime1);
        	var date2 = stringToTime (data.ApplicationPeriodtime2);
			var account = $("#J-sp-UserName input").val();
			var note    = $("#J-sp-td-Note div div :first").html();
			if(note == '备注'|| note == '' || note === null || note == undefined){
				if(account == '' && (date2 - date1 > 1000 * 60 * 60 * 24)){
					alert('您选的日期超过1天');
					return false;
				}else if(date2 - date1 > 1000 * 60 * 60 * 24 * 32){
					alert('您选的日期超过30天');
					return false;
				}
			}else{ //備註有選擇的情況				
				if(account == '' && (date2 - date1 > 1000 * 60 * 60 * 24 * 10)){
					alert('您选的日期超过10天');
					return false;
				}else if(date2 - date1 > 1000 * 60 * 60 * 24 * 32){
					alert('您选的日期超过30天');
					return false;
				}
			}

    	}

        if ( data.OrderType == '' && data.UserName == '') {
            alert('请输入用户名或选择任一帐变类型');
            return false;
        }
	
		//$.get("/admin/Reporting/index?parma=drex",group.getFormData());
		var data = group.getFormData();
		var param='';
		var p='';
		for(p in data){
			if(data.hasOwnProperty(p)){
				param += '&'+p+'='+ data[p];
			}
		}
		window.open("/admin/Reporting/index?parma=dadda"+param);
	});	
})();	
</script>
{/literal}
</body>
</html>
