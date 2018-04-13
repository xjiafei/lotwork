<!-- //////////////头部公共页面////////////// -->
		{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
			<!-- //////////////头部公共页面////////////// -->
				{include file='/admin/funds/left.tpl'}
			<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">银行卡管理</a> &gt; 可疑银行卡
             <a  href="/admin/Bankcardsmanage/index?parma=sv6&swithval=2" style='  background: -moz-linear-gradient(center top , #FFFFFF, #F0F0F0) repeat scroll 0 0 #F5F5F5;
            border: 1px solid #CACACA;border-radius: 3px 3px 3px 3px;box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);color: #555555;cursor: pointer;display: inline-block;
            font-size: 13px;font-weight: 600; height: 32px;line-height: 32px;margin-top: -8px;padding: 0 15px;position: relative;text-align: center;
            vertical-align: middle; float:right;'>添加可疑银行卡<b class="btn-inner"></b></a>
            </div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">							
							<div class="ui-tab-content">
								<table id="J-table-data" class="table table-info table-function">
									<thead>
										<tr>
											<th id="J-bank-card" class="sp-td">
												<div class="sp-td-cont">
													<div class="sp-td-title">银行卡号</div>
													<ul class="sp-filter-cont sp-filter-cont-b">
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
											<th rowspan="2" id="J-sp-username" class="sp-td">
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
                                                    <span class="sp-filter-close"></span>
                                                </div>                                            
                                            </th>
                                            
                                             <th id="J-sp-add-time" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">加入时间</div>
                                                    <ul class="sp-filter-cont sp-filter-cont-b" style="width:270px;">
                                                        <li>
                                                            <a class="sp-link-sort" href="#">正序</a>
                                                            <a class="sp-link-sort" href="#">倒序</a>
                                                        </li>
                                                        <li>
                                                            <div class="input-append">
                                                                <input type="text" tabindex="1" class="input w-2" id="sp-input-addtime-1"> - <input type="text" id="sp-input-addtime-2" class="input w-2" size="10"><a href="javascript:void(0);" class="btn sp-filter-submit"><b class="btn-inner"></b></a>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											<th id="J-sp-bank-Status" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">银行卡状态</div>
                                                    <div class="sp-filter-cont sp-filter-cont-b sp-filter-cont-select" style="">
                                                        <ul>
                                                            <li data-select-id="1"><a href="#">可疑卡</a></li>
                                                            <li data-select-id="2"><a href="#">骗子卡</a></li>
                                                            <li data-select-id="3"><a href="#">重复付款</a></li>  
                                                            <li data-select-id="5"><a href="#">其他</a></li>                                                          
                                                        </ul>
                                                    </div>
                                                    <span class="sp-filter-close"></span>
                                                </div>
                                            </th>
											 <th  id="J-add-oper" class="sp-td">
                                                <div class="sp-td-cont sp-td-cont-b">
                                                    <div class="sp-td-title">添加人</div>
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
											
											<th class="sp-td">
												<div class="sp-td-cont">
													<div class="sp-td-title">操作</div>
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
                                            <td colspan="7">
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
	selectMenu('Menufunds','MenuAllOpterators');	
	//操作组	
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
	//银行卡号
	 var bankcard = new phoenix.SuperSearch({
        name: 'bankcard',
        keyCode: 'ctrl+85',
        el: '#J-bank-card',
        group: group
    });
	//用户名
    var username = new phoenix.SuperSearch({
        name: 'username',
        keyCode: 'ctrl+85',
        el: '#J-sp-username',
        group: group
    });
	//加入时间
    var addtime = new phoenix.SuperSearch({
        name: 'addtime',
        keyCode: 'ctrl+68',
        type: 'time',
        el: '#J-sp-add-time',
        group: group,
		expands:{getFormValue:getTowInputValue}
    });
	//银行状态
    var bankStatus = new phoenix.SuperSearch({
        name: 'bankStatus',
        keyCode: 'ctrl+66',
        type: 'select',
        isAutoWidth: true,
        el: '#J-sp-bank-Status',
        group: group,
		expands:{getFormValue:getSelectValue}
    });	
	//添加人
	var addoper = new phoenix.SuperSearch({
        name: 'addoper',
        keyCode: 'ctrl+83',
         el: '#J-add-oper',
        group: group		
    });
	//备注
	var spinfo = new phoenix.SuperSearch({
        name: 'spinfo',
        keyCode: 'ctrl+83',
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
			url:'/admin/Bankcardsmanage/index?parma=sv5',
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
						html += "<tr><td>" + re[i].cardNumber + "</td>";
						html += "<td>" + re[i].id + "</td>";
						html += "<td>" + re[i].gmtCreated + "</td>";
						html += "<td>状态没给</td>";
						html += "<td>" + re[i].creator + "</td>";
						
						html += "<td class='sele_"+i+"'><a name='sele_status' onclick='Updatestatus(this)' id='sele_"+i+"'  href='javascript:void(0)' pro_s='stu_"+i+"' >删除</a></td>";
						objString =(re[i].memo==""?"":re[i].memo.substring(0,15) + "...") ;						
						html += "<td class='table-tool'><span title='"+re[i].memo+"' pro_span='span_"+i+"'> "+objString+" </span><a name='span_"+i+"' class='ico-edit' id='"+re[i].sn+"' onclick=\"Updateedit(this)\"  title='编辑' href='javascript:void(0);'></a></td></tr>";	
						
					});
					$("#J-table-data>tbody").html(html);	
					$("#J-table-data>tbody").html(html);
					$('.ui-tab-content').removeClass(); 				
				}else{
					 $("#Pagination").hide();                 
                    $("#J-table-data>tbody").html("<tr><td colspan=\"7\">没有相应数据</td></tr>");
					$("#J-table-data>tbody").html(html);
					$('.ui-tab-content').removeClass(); 
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
//删除当前卡绑定关系
function Updatestatus(obj){		
	if(window.confirm('确定要删除？')){ 
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
						alert('删除成功&nbsp;');	
						window.location.reload();		
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
	}else{return false;}	
 }

</script>
{/literal}
</body>
</html>
</body>
</html>