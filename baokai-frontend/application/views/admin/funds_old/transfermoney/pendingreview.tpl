<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">人工资金管理</a> &gt;<a href="#">现金打款</a> &gt; 等待审核</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="/admin/Fundsmanage/currenttransfer?swithval=1">现金打款</a></li>
									<li class="current"><a href="/admin/Fundsmanage/currenttransfer?swithval=2">等待审核</a></li>
									<li ><a href="/admin/Fundsmanage/currenttransfer?swithval=3">审核未过</a></li>
									<li><a href="/admin/Fundsmanage/currenttransfer?swithval=4">处理成功</a></li>
									<li><a href="/admin/Fundsmanage/currenttransfer?swithval=5">打款失败</a></li>
									<li class="more"><a href="/admin/Fundsmanage/currenttransfer?swithval=6">发起打款申请</a></li>
								</ul>
							</div>
							<div class="ui-tab-content">
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
											<th id="J-sp-serial" class="sp-td">
												<div class="sp-td-cont">
													<div class="sp-td-title">流水交易号</div>
													<ul class="sp-filter-cont  sp-filter-cont-b ">
														<li>
															<a class="sp-link-sort" href="#">正序</a>
															<a class="sp-link-sort" href="#">倒序</a>
														</li>
														<li>
															<input type="text" class="input w-2" size="10">
															<a href="javascript:void(0);" class="btn btn-important">搜 索<b class="btn-inner"></b></a>
														</li>
													</ul>
													<span class="sp-filter-close"></span>
												</div>
											</th>
											<th  id="J-sp-username" class="sp-td">
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
                                            <th id="J-sp-playmoney-Status" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">打款类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">活动派奖</a></li>
                                                            <li data-select-id="2"><a href="#">人工提现</a></li>                                                                                                                                                                              
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                             
                                            <th  id="J-sp-bankinfo" class="sp-td">
                                                <div class="sp-td-cont">
                                                    <div class="sp-td-title">收款银行信息</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b ">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-bankinfo" type="text" class="input w-2" size="10" maxlength="16"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            
                                            </th>   

        									<th id="J-sp-give-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请打款金额</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-givemoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-givemoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            
                                             <th id="J-sp-Apply-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">申请时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-Applytime-1"> - <input type="text" id="sp-input-Applytime-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
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
                                                            <li data-select-id="3"><a href="#">等待付款</a></li>  
                                                            <li data-select-id="3"><a href="#">付款成功</a></li>  
                                                            <li data-select-id="4"><a href="#" title="鼠标移动至该表单时，浮现出失败原因">付款失败</a></li>                                                                                                                       
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            
											
                                            
											 <th  id="J-sp-applypeople" class="sp-td">
                                                <div class="sp-td-cont">
                                                    <div class="sp-td-title">申请人</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b ">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-bankinfo" type="text" class="input w-2" size="10" maxlength="16"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
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
								<!--<tbody>
										<tr>
											<td>2012061511</td>
											<td>gdhfth</td>
											<td>活动派奖</td>
											<td>招商银行&nbsp;&nbsp;张*&nbsp;&nbsp;**** **** **** 3400</td>
											<td>1,000.0000</td>
											<td>2013-02-02 10:20:30</td>
											<td class="color-orange">等待审核</td>
											<td>/</td>
											<td>/</td>
											<td>dsfg</td>
											<td>
												<a href="javascript:void(0);">通过</a>
												<a href="javascript:void(0);">拒绝</a>
											</td>
											<td><a href="javascript:void(0);">添加备注</a></td>
										</tr>
										<tr>
											<td>2012061511</td>
											<td>gdhfth</td>
											<td>活动派奖</td>
											<td>招商银行&nbsp;&nbsp;张*&nbsp;&nbsp;**** **** **** 3400</td>
											<td>1,000.0000</td>
											<td>2013-02-02 10:20:30</td>
											<td class="color-red">审核未过</td>
											<td>/</td>
											<td>/</td>
											<td>dsfg</td>
											<td>fgdfhgf</td>
											<td><a href="javascript:void(0);">添加备注</a></td>
										</tr>
										<tr>
											<td>2012061511</td>
											<td>gdhfth</td>
											<td>活动派奖</td>
											<td>招商银行&nbsp;&nbsp;张*&nbsp;&nbsp;**** **** **** 3400</td>
											<td>1,000.0000</td>
											<td>2013-02-02 10:20:30</td>
											<td class="color-green">付款成功</td>
											<td>/</td>
											<td>/</td>
											<td>dsfg</td>
											<td>fgdfhgf</td>
											<td><a href="javascript:void(0);">添加备注</a></td>
										</tr>
									</tbody>
								</table>-->
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
	     selectMenu('Menufunds','MenuWithdrawalsConfig');
		//操作组
		//debugger
		var group = new phoenix.SuperSearchGroup(),mask = phoenix.Mask.getInstance();
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
		//打款类型
   	    var playmoney = new phoenix.SuperSearch({
			name: 'playmoney',
			keyCode: 'ctrl+85',
			el: '#J-sp-playmoney-Status',
			group: group
    	});
		//收款银行信息		
		var bankinfo = new phoenix.SuperSearch({
			name: 'bankinfo',
			keyCode: 'ctrl+85',
			el: '#J-sp-bankinfo',
			group: group
		});
		//申请打款金额
		var givemoney = new phoenix.SuperSearch({
			name: 'givemoney',
			keyCode: 'ctrl+83',
			el: '#J-sp-give-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-givemoney1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-givemoney2').keyup(allowNumber).blur(allowNumber);
		
		//审核时间
		var Applytime = new phoenix.SuperSearch({
			name: 'Applytime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-Apply-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var Applytime1 = $('#sp-input-Applytime-1'),Applytime2 = $('#sp-input-Applytime-2'),ApplyDt1,ApplyDt2;
		Applytime1.click(function() {
			ApplyDt1 = new phoenix.DatePicker({
				input: Applytime1,
				isLockInputType: false
			});
			ApplyDt1.show();
			ApplyDt1.addEvent('afterSetValue',
			function() {
				Applytime2.focus();
				Applytime2.click();
			})
		});
		Applytime.addEvent('afterFocus',
		function() {
			Applytime1.click()
		});
		Applytime2.click(function() {
			ApplyDt2 = new phoenix.DatePicker({
				input: Applytime2,
				isLockInputType: false
			}).show();
		});
		Applytime.addEvent('afterBlur',function() {
			if (ApplyDt1) {
				ApplyDt1.hide();
			}
			if (ApplyDt2) {
				ApplyDt2.hide();
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
		

		//申请人
		var applypeople = new phoenix.SuperSearch({
			name: 'applypeople',
			keyCode: 'ctrl+83',
			el: '#J-sp-applypeople',
			group: group
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
			
			 var formData = group.getFormData();
			 $("#J-table-data>tbody").html("");				
			 $.ajax({
				url:'/admin/Rechargemange/customersdrawda',
				cache:false,
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
							if(re[i].status=="0"){
								html += "<tr><td>" + re[i].sn + "</td>";						
								html += "<td>" + re[i].applyAccount + "</td>";
								html += "<td>" + re[i].applyTime + "</td>";
								html += "<td>" + re[i].userBankStruc + "</td>";							
								html += "<td>" + re[i].bankName + "</td>";
								html += "<td>" + re[i].withdrawAmt + "</td>";
								
								objString =(re[i].memo==""?"":re[i].memo.substring(0,5) + "...") ;										
								switch(re[i].status)//0:等待审核，1：审核未过，2：等待付款，3：付款成功，4：付款失败
								{
									case "0":
										html += "<td><span id='stu_"+i+"' class='color-orange'>等待审核</span></td>";									
										html += "<td>/</td>";
										html += "<td class='sele_"+i+"'><select name='sele_status' onchange='Updatestatus(this)' id='sele_"+i+"'  pro_s='stu_"+i+"' ><option value='0' selected='true'>--选择操作--</option><option value='1'>通过</option><option value='2'>拒绝</option></select></td>";
										html += "<td class='table-tool'><span title='"+re[i].memo+"' pro_span='span_"+i+"'> "+objString+" </span><a name='span_"+i+"' class='ico-edit' id='"+re[i].sn+"' onclick=\"Updateedit(this)\"  title='编辑' href='javascript:void(0);'></a></td></tr>";	
										break;														
									default:
										html += "<td></td>";									
										html += "<td>/</td>";
										html += "<td>/</td>";
										html += "<td>"+objString+"</td>";
										break;							
								}			
							}							
						});
						$("#J-table-data>tbody").html(html);
						 $('.ui-tab-content').removeClass(); 
					}else{
						 $("#Pagination").hide();      
						 $('.ui-tab-content').removeClass();           
						$("#J-table-data>tbody").html("<tr><td colspan=\"13\">没有相应数据</td></tr>");
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
					cache:false,
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
		var	updatid = $(obj).attr("pro");	//流水号单号
		var udpatname=$(obj).attr("id");	//当前控件id
		var ups=$(obj).attr("pro_s");//获取状态id
		var upid=$('#'+udpatname).val().trim();		
		if((upid!=''&&updatid!='') || upid!='0')
		{
			var group = new phoenix.SuperSearchGroup(),dataArea = $('#showInfo'),formData = group.getFormData(),mask = phoenix.Mask.getInstance();	
			$.ajax({
				url:'/admin/Rechargemange/exceptaudit',
				cache:false,
				dataType:'json',
				method:'post',
				data:'mowId='+updatid+'&opterID='+upid,
				beforeSend:function(){
					mask.dom.addClass('admin-mask-loading');
					mask.css({opacity:.9,backgroundColor:'#FFF'});
					mask.show(dataArea);
					mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
				},
				success:function(data){
					if(data.status=="1"){
						//不刷新页面，处理html控件
						$('#'+udpatname).hide();	
						if(upid=='1'){
							$('.'+udpatname).append("<span>成功</span>");
							$('#'+ups).removeAttr('class').html('提现成功');
							//$('.'+udpatname).parents("tr").css("background-color","E9FBF8");
						}else if(upid=='2'){
							$('.'+udpatname).append("<span>拒绝</span>");
							$('#'+ups).html("<span style='color:red'>提现失败</span>");
							//$('#'+ups).parents("tr").css("background-color","E9FBF8");
						}						
					}
					else{
						alert("审核失败,请核实您是否有此权限");	
					}					
				},
				complete:function(){
					mask.dom.fadeOut(300, function(){
						mask.hide();
					});
				}
			});	
		}
		else{ return false;}		
	 }
</script>
{/literal}
</body>
</html>










