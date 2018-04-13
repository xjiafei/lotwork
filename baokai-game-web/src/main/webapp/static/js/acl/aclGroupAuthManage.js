$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
	//弹窗
	var Wd = phoenix.Message.getInstance();
	var table = $('#J-table');	
	//树形菜单
	var findChild = function(id, arr){
			var arr = arr || [],trs,fn = arguments.callee,
			trs = table.find('tr[data-pid="'+ id +'"]');
			arr = arr.concat(trs);
			trs.each(function(i){
				arr = arr.concat(fn.call(null, this.getAttribute('data-id'), arr));
			});
			return arr;
		},
		findParent = function(id){
			return table.find('tr[data-id="'+ id +'"]');
		};
		
	table.on('click', '.row-control', function(e){
		e.preventDefault();
		var el = $(this),ctCls = 'row-control',closeCls = 'ico-unfold',openCls = 'ico-fold',
			id = el.parents('tr').attr('data-id'),
			childs;
		if(el.hasClass('ico-nochild')){
			return;
		}
		//收起
		if(el.hasClass(openCls)){
			el.removeClass().addClass(ctCls + ' ' + closeCls);
			childs = findChild(id);
			$.each(childs, function(){
				this.hide();
			});
		}else{
		//展开
			el.removeClass().addClass(ctCls + ' ' + openCls);
			childs = findChild(id);
			$.each(childs, function(){
				var me = this;
				this.each(function(){
					var dom = $(this),par = findParent(dom.attr('data-pid'));
					if(par.css('display') != 'none' && par.find('.ico-unfold').size() == 0){
						dom.show();
					}
				});
			});
			
		}
	});
	
	table.on('click','#showGroupAuth',function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),id = tr.attr('data-id'),pid = tr.attr('data-pid'),name = tr.attr('data-name');
		window.location.href=baseUrl + "/aclAdmin/queryAclByGroup?id="+id+"&pid="+pid+"&name="+ encodeURI(encodeURI(name))+"&num=" + Math.random();
	});
	
	table.on('click','#showGroupUser',function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),id = tr.attr('data-id');
		window.location.href=baseUrl +"/aclAdmin/searchUser?groupId=" + id+"&status=-1";
	});
	
	table.on('click','#copyGroup',function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),id = tr.attr('data-id'),name = tr.attr('data-name');
		window.location.href=baseUrl + "/aclAdmin/toCopyGroup?id="+id+"&name="+ encodeURI(encodeURI(name))+"&num=" + Math.random();
	});
	
	table.on('click','#updateGroup',function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),id = tr.attr('data-id'),name = tr.attr('data-name'),pid=tr.attr('data-pid'),inUser=tr.attr('data-inUser');
		window.location.href=baseUrl + "/aclAdmin/toUpdateGroup?id="+id+"&pid="+pid+"&name="+ encodeURI(encodeURI(name))+"&inUser="+inUser+"&num=" + Math.random()+"&userId=1";
	});
	
	table.on('click','#addSubGroup',function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),id = tr.attr('data-id'),name = tr.attr('data-name');
		window.location.href=baseUrl + "/aclAdmin/toSubAddGroup?pid="+id+"&pname="+ encodeURI(encodeURI(name))+"&num=" + Math.random();
	});
	
	//行操作动作
	table.on('click', '.row-action-close', function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),
			title = tr.find('.table-tool').text(),
			id = tr.attr('data-id'),pid=tr.attr('data-pid');
		Wd.show({
			mask:true,
			title:'禁用组提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要禁用组<b>['+ title +']</b>吗？禁用成功后该组的所有成员将不能登录管理后台。</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/aclAdmin/closeGroup?id="+id+"&inUser=0&userId=1"+"&num=" + Math.random()+"&pid="+pid,
					dataType:'json',
					success:function(data){
						if(data.status == 1){window.location.href=baseUrl + "/aclAdmin/queryAclGroup?userId=1";}
						else{alert(data.message);}
						Wd.hide();
					},
					error:function(xhr,status,errMsg){
						alert(errMsg);
						Wd.hide();
					}
				
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	table.on('click', '.row-action-open', function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),
			title = tr.find('.table-tool').text(),
			id = tr.attr('data-id'),pid=tr.attr('data-pid');
		Wd.show({
			mask:true,
			title:'启用组提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要启用组<b>['+ title +']</b>吗？启用成功后该组的所有成员将恢复正常使用。</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/aclAdmin/closeGroup?id="+id+"&inUser=1&userId=1"+"&num=" + Math.random()+"&pid="+pid,
					dataType:'json',
					success:function(data){
						if(data.status == 1){window.location.href=baseUrl + "/aclAdmin/queryAclGroup?userId=1";}
						else{alert(data.message);}
						Wd.hide();
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	table.on('click', '.row-action-delete', function(e){
		e.preventDefault();
		var tr = $(this).parent().parent(),
			title = tr.find('.table-tool').text(),
			id = tr.attr('data-id');
		Wd.show({
			mask:true,
			title:'删除组提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除组<b>['+ title +']</b>吗？删除后将不能恢复</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:baseUrl+"/aclAdmin/deleteGroup?id=" + id,
					dataType:'json',
					success:function(data){
						if(data.status == 1){
							window.location.href=baseUrl + "/aclAdmin/queryAclGroup?userId=1"+"&num=" + Math.random();
						}else if(data.status == 2){
							Wd.show({
								mask:true,
								title:'删除组提示',
								content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您不能删除该组，原因：该组中包含管理员，请先删除或转移管理员。</h4></div></div>',
								confirmIsShow:true,
								confirmFun:function(){
									Wd.hide();
								}
							});
						}else{alert(data.message);Wd.hide();}
						
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
		
	});
	
	
});