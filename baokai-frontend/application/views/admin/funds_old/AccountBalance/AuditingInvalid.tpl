<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">人工资金管理</a> &gt; 审核未过</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="/admin/Fundsmanage/accountbalance?swithval=1">账户余额加减</a></li>
									<li ><a href="/admin/Fundsmanage/accountbalance?swithval=2">等待审核</a></li>
									<li class="current"><a href="/admin/Fundsmanage/accountbalance?swithval=3">审核未过</a></li>
									<li><a href="/admin/Fundsmanage/accountbalance?swithval=4">处理成功</a></li>
									<li class="more"><a href="/admin/Fundsmanage/accountbalance?swithval=5">加减申请</a></li>
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
                                                                <input id="J-input-serial" type="text" class="input w-2" size="10" maxlength="25"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            
											<th  id="J-sp-username" class="sp-td">
                                                <div class="sp-td-cont  sp-td-cont-b">
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
                                                    <div class="sp-td-title">类型</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">活动加币</a></li>
                                                            <li data-select-id="2"><a href="#">管理员扣减</a></li>                                                                                                                                                                              
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
                                            <th id="J-sp-addminus-money" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">加减数量</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input id="J-input-addminusmoney1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-addminusmoney2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											
											 <th id="J-sp-created-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">创建时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-createdtime-1"> - <input type="text" id="sp-input-createdtime-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
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
                                                                                                                                                                               
                                                        </ul>
                                                    </div>
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
                                            
											<th  id="J-sp-applypeople" class="sp-td">
                                                <div class="sp-td-cont  sp-td-cont-b">
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
	var mowId,selectid;	//备注id,name获取
	(function() {
		 //一、二级菜单选中样式加载	
	    selectMenu('Menufunds','MenuFundConfig');
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
			keyCode: 'ctrl+85',
			el: '#J-sp-username',
			group: group
		});		
		//类型   	   
		var playtype = new phoenix.SuperSearch({
			name: 'playtype',
			type: 'select',
			isAutoWidth: true,
			keyCode: 'ctrl+83',
			el: '#J-sp-playmoney-Status',
			group: group,
			expands:{getFormValue:getSelectValue}
		});
		//加减数量
		var addminus = new phoenix.SuperSearch({
			name: 'addminus',
			keyCode: 'ctrl+83',
			el: '#J-sp-addminus-money',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		$('#J-input-addminusmoney1').keyup(allowNumber).blur(allowNumber);
		$('#J-input-addminusmoney2').keyup(allowNumber).blur(allowNumber);
		//创建时间
		//审核时间
		var createdtime = new phoenix.SuperSearch({
			name: 'createdtime',
			keyCode: 'ctrl+68',
			type: 'time',
			el: '#J-sp-created-time',
			group: group,
			expands:{getFormValue:getTowInputValue}
		});
		var createdTime1 = $('#sp-input-createdtime-1'),createdTime2 = $('#sp-input-createdtime-2'),createdDt1,createdDt2;
		createdTime1.click(function() {
			createdDt1 = new phoenix.DatePicker({
				input: createdTime1,
				isLockInputType: false
			});
			createdDt1.show();
			createdDt1.addEvent('afterSetValue',
			function() {
				createdTime2.focus();
				createdTime2.click();
			})
		});
		createdtime.addEvent('afterFocus',
		function() {
			createdTime1.click()
		});
		createdTime2.click(function() {
			createdDt2 = new phoenix.DatePicker({
				input: createdTime2,
				isLockInputType: false
			}).show();
		});
		createdtime.addEvent('afterBlur',function() {
			if (createdDt1) {
				createdDt1.hide();
			}
			if (createdDt2) {
				createdDt2.hide();
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
		
		 var formData = group.getFormData(),mask = phoenix.Mask.getInstance();
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
						if(re[i].status=="1"){
							html += "<tr><td>" + re[i].sn + "</td>";						
							html += "<td>" + re[i].applyAccount + "</td>";
							html += "<td>" + re[i].applyTime + "</td>";
							html += "<td>" + re[i].userBankStruc + "</td>";							
							html += "<td>" + re[i].bankName + "</td>";
							objString =(re[i].memo==""?"":re[i].memo.substring(0,5) + "...") ;	
							switch(re[i].status)
							{
								case "1":								
									html += "<td><span style='color:red'>审核未过</span></td>";	
									html += "<td>" + re[i].mcRemitTime + "</td>";							
									html += "<td></td>";
									html += "<td></td>";
									html += "<td>"+objString+"</td>";
									break;							
								default:
									html += "<td></td>";
									html += "<td></td>";
									html += "<td>/</td>";
									html += "<td></td>";
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
                    $("#J-table-data>tbody").html("<tr><td colspan=\"10\">没有相应数据</td></tr>");
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