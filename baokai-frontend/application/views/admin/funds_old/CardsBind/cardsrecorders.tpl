<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">			
            <div class="col-crumbs">
            <div class="crumbs">            
            <strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">银行卡管理</a> &gt; 银行卡绑定记录
            <a  href="/admin/Bankcardsmanage/index?parma=sv3" style='  background: -moz-linear-gradient(center top , #FFFFFF, #F0F0F0) repeat scroll 0 0 #F5F5F5;
            border: 1px solid #CACACA;border-radius: 3px 3px 3px 3px;box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);color: #555555;cursor: pointer;display: inline-block;
            font-size: 13px;font-weight: 600; height: 32px;line-height: 32px;margin-top: -8px;padding: 0 15px;position: relative;text-align: center;
            vertical-align: middle; float:right;'>银行卡绑定配置<b class="btn-inner"></b></a>
            </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<table id="J-table-data" class="table table-info table-function">
							<thead>
								<tr>	
                                	<th  id="J-sp-username" class="sp-td">
                                        <div class="sp-td-cont">
                                            <div class="sp-td-title">用户名</div>
                                            <ul class="sp-filter-cont sp-filter-cont-b">
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
                                            <span class="sp-filter-close" style="top: -5px;"></span>
                                        </div>                                            
                                    </th>
                                            							
                                   
                                    <th  id="J-sp-group-status" class="sp-td">
										<div class="sp-td-cont">
											<div class="sp-td-title">所属组</div>
											<div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select">
												<ul>
													<li data-select-id="1" ><a href="#" ><span style=" margin-left:-70px;">总代用户</span></a></li>
													<li data-select-id="2"><a href="#" ><span style=" margin-left:-70px;">全部用户</span></a></li>													
												</ul>
											</div>
											<span class="sp-filter-close" style="top: -5px;"></span>
										</div>
									
									</th>
                                    
									<th id="J-sp-binding-number" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">绑定数量</div>
											<ul class="sp-filter-cont sp-filter-cont-b">												
												<li>
													<div class="input-append">
														<input id="J-input-binding1" type="text" class="input w-1" size="10" maxlength="11"> - <input id="J-input-binding2" type="text" class="input w-1" size="10" maxlength="11"><a href="javascript:void(0);" class="btn sp-filter-submit"></a>
													</div>
												</li>
											</ul>
											<span class="sp-filter-close"></span>
										</div>
									
									
									</th>
									<th id="J-sp-binding" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b">
											<div class="sp-td-title">当前绑定</div>
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
									
									<th id="J-sp-request-Status" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">启动中</a></li>
                                                            <li data-select-id="2"><a href="#">锁定中</a></li>                                                                                                                     
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                    </th>
                                     <th id="J-sp-Operator" class="sp-td">
										<div class="sp-td-cont sp-td-cont-b"  >
											<div class="sp-td-title">操作人</div>
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
									
									<th >
										<div class="sp-td-cont">
											<div class="sp-td-title">启用状态</div>
										</div>
									</th>
								</tr>
							</thead>
							<tbody id="showInfo">
                            </tbody>
                            
                            <tfoot>
                                <tr>
                                    <td colspan="7">
                                        <div id="Pagination" class="pagination" ></div>
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
	selectMenu('Menufunds','MenuOpterators');	
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
	//所属组
    var groupstatus = new phoenix.SuperSearch({
        name: 'groupstatus',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-group-status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	//绑定数量
    var bindingnumber = new phoenix.SuperSearch({
        name: 'bindingnumber',
        keyCode: 'ctrl+85',
        el: '#J-sp-binding-number',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	$('#J-input-binding1').keyup(allowNumber).blur(allowNumber);
	$('#J-input-binding2').keyup(allowNumber).blur(allowNumber);
	//当前绑定	
	var binding = new phoenix.SuperSearch({
        name: 'binding',
        keyCode: 'ctrl+85',
        el: '#J-sp-binding',
        group: group
    });
	//状态	
	var requeststatus = new phoenix.SuperSearch({
        name: 'requeststatus',
        type: 'select',
        isAutoWidth: true,
        keyCode: 'ctrl+83',
        el: '#J-sp-request-Status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });
	//操作人	
	var Operator = new phoenix.SuperSearch({
        name: 'Operator',
        keyCode: 'ctrl+85',
        el: '#J-sp-Operator',
        group: group
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
			url:'/admin/Bankcardsmanage/index?parma=sv2 ',
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
					//var resultAll = jQuery.parseJSON    
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
						html += "<tr><td>" + re[i].account + "</td>";						
						html += "<td>总代</td>";							
						html += "<td>" + re[i].bindCount + "</td>";			
						html += "<td><sapn>" + re[i].bankName + "</span> - <sapn>" + re[i].bankAccount + "</span> - <sapn>" + re[i].bankNumber + "</span>- <sapn>时间暂没给出</span></td>";						
						if( re[i].status=="0")
						{
							html += "<td><span class='color-orange' id='stat"+i+"'>锁定中</span></td>";
							html += "<td>" + re[i].operator + "</td>";							
							html += "<td class='sele_"+i+"'><select name='sele_status' onchange='Updatestatus(this)' id='sele_"+i+"' pro='stat"+i+"'  pro_name='" + re[i].account + "'><option value='0' selected='true'>--选择操作--</option><option value='2'>启   动</option><option value='3'>历史记录</option></select></td></tr>";
						}
						else
						{
							html += "<td><span class='color-green' id='stat"+i+"'>启动中</span></td>";
							html += "<td>" + re[i].operator + "</td>";
							html += "<td class='sele_"+i+"'><select name='sele_status' onchange='Updatestatus(this)' id='sele_"+i+"'   pro='stat"+i+"' pro_name='" + re[i].account + "'><option value='0' selected='true'>--选择操作--</option><option value='1'>锁   定</option><option value='3'>历史记录</option></select></td></tr>";
						}						
						
						
					});
					$("#J-table-data>tbody").html(html);	
					
				}else{
					 $("#Pagination").hide();     
					 $('.ui-tab-content').removeClass();             
                    $("#J-table-data>tbody").html("<tr><td colspan=\"7\">没有相应数据</td></tr>");
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

//审核id,name
function Updatestatus(obj){					
	var udpatname=$(obj).attr("id"),htmls="";	//当前控件id
	var proid=$(obj).attr("pro"),pro_name=$(obj).attr("pro_name");//用户名
	var upid=$('#'+udpatname).val().trim();		
	if(upid=="1"){	htmls="您确定要锁定动？";}
	else if(upid=="2"){	htmls="您确定要启动？";}
	switch(upid){		
		case "1"://是锁定			
			Putstatus(upid,proid,"1");	
			$('#'+udpatname)[0].selectedIndex = 0;				
			break;
		case "2"://是启动			
			Putstatus(upid,proid,"2");	
			$('#'+udpatname)[0].selectedIndex = 0;				
			break;			
		case "3":				
			PutHistory(pro_name,obj);	
			$('#'+udpatname)[0].selectedIndex = 0;	//除当前选中行外，select都还原为待选择状态
			break;
		case "0":	
			$('#'+udpatname)[0].selectedIndex = 0;					
			break;
		default:
			$('#'+udpatname)[0].selectedIndex = 0;	
			alert('请合法操作,此动作已记录在数据库日志');				
			break;
	}	
	//锁定，启动
	function Putstatus(upid,proid,types){	
		if(window.confirm(htmls)){				
			if(upid!=''){
				var dataArea = $('#showInfo'),mask = phoenix.Mask.getInstance();	
				$.ajax({
					url:'/admin/Rechargemange/exceptaudit',
					dataType:'json',
					method:'post',
					data:'mowId='+types+'&opterID='+upid,
					beforeSend:function(){
						mask.dom.addClass('admin-mask-loading');
						mask.css({opacity:.9,backgroundColor:'#FFF'});
						mask.show(dataArea);
						mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
					},
					success:function(data){
						if(data.status=="1"){
							//成功刷新页面
							window.location.reload();	
							//不刷新页面，处理html控件				
							//if(upid=='1'){					
//								$('#'+proid).html('启动中').removeClass().addClass('color-green');					
//							}else if(upid=='0'){
//								$('#'+proid).html('锁定中').removeClass().addClass('color-orange');
//							}						
						}
						else{
							alert("审核失败,请核实您是否有此权限");	
							return false;
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
		else{
			$('#'+udpatname)[0].selectedIndex = 0;
			return false;
		}
	}
}
//查看历史记录
function PutHistory(pro_name,obj){
	var currentHide="",CurrentObj="",strHtml='';
	CurrentObj = jQuery(obj);
	if (currentHide != "") {
			currentHide.remove();	
			currentHide = "";				
			return false;			
	}	
	if(obj!='' && pro_name!=''){		
		var dataArea = $('#showInfo'),mask = phoenix.Mask.getInstance();
		//获取银行历史(10条)
		$.ajax({
			url:'/admin/Bankcardsmanage/index?parma=sv10',
			dataType:'json',
			method:'post',
			data:'username='+pro_name,
			beforeSend:function(){
				mask.dom.addClass('admin-mask-loading');
				mask.css({opacity:.9,backgroundColor:'#FFF'});
				mask.show(dataArea);
				mask.dom.css({top:mask.dom.offset().top + 2,height:mask.dom.height() - 2});
			},
			success:function(data){
				if(data.text){
				//解析数据加载展示
				var resultAll = eval(data);               
				var re = resultAll.text;                                  
				var html = "";					
				strHtml="<tr class='bg-none' ><td colspan='7' style='text-align:left;'><ul class='ui-form'><li> <span   style='top:-45px;font-size:16px; font-weight:700;'>历史记录</span>（显示最近十条）</li>";	
				$.each(re, function (i) {
					if(re[i].info=="1"){
						strHtml += "<li><label style='font-size:13px;color:green '>" + re[i].label + "</label> &nbsp; &nbsp;<label style='font-size:13px; '>" + re[i].labve + "</label> </li>";						
					}
					else{
						strHtml += "<li><label style='font-size:13px;color:red '>" + re[i].label + "</label> &nbsp; &nbsp;<label style='font-size:13px;'>" + re[i].labve + "</label> </li>";						
					}					
				});
				strHtml += "</ul></td></tr>";
				$('.bg-none').remove();
				CurrentObj.parent().parent().after(jQuery(strHtml));
				//CurrentObj.attr("disabled", "disabled");	
				currentHide = CurrentObj.parents("tr").next();				
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
	else{
		return false;	 
	}
	
}
</script>
{/literal}
</body>
</html>
</body>
</html>