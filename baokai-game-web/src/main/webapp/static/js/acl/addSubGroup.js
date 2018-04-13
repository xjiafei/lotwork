$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
	var pgid=$("#pid").val(), groupName = false;
	$('#input-group-title').keyup(function(){
		var me=this, v=$.trim(me.value);
		me.value = v = v.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'');
	});
	function WidthCheck(str){  
			var w = 0;  
			var tempCount = 0; 
			for (var i=0; i<str.length; i++) {  
			   var c = str.charCodeAt(i);  
			   //单字节加1  
			   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			    w++;  
			  
			   }else {     
			    w+=2;
			   }  
			 }
			return w;
	};
	$('#input-group-title').blur(function(){
		var v=$.trim($(this).val());
		var content = v.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,''),
			tipsDom = $(this).next();
		$(this).val(content);
		if(content && WidthCheck(content) >= 2 && WidthCheck(content) <= 20){
			tipsDom.html('');
		}else{
			if(content == ''){
				tipsDom.html('<span style="color:#ff0000">请填写组名称</span>');
				return;
			}else{
				tipsDom.html('<span style="color:#ff0000">组名称长度应为2-20个字符</span>');
				return;
			}
		}
		var url = baseUrl +"/aclAdmin/checkByName";

		 $.ajax({
			url: url,
			dataType: 'json',
			type: 'post',
			data:"name="+$.trim(content),
			success:function(data){
				if(data.status == 1){
					groupName = true;
				}else{
					tipsDom.html('<span style="color:#ff0000">' + data.message +'</span>');
				}
			},
			error: function(xhr,status,errMsg){
				alert("名称校验失败："+errMsg);
			}
		});
	});
	
	
	
	var Tree,Tree2;
	var buildLeft = function(data){
			var treeData = data,
				imgStr = '<img src="'+staticFileContextPath+'/static/images/common/tree/blue/check.png" />',
				checkedClass = 'ui-tree-check-checked',
				cfg = {
					data:treeData,
					dom:'#J-tree-container',
					path:staticFileContextPath + '/static/images/common/tree/blue/',
					nodeTpl:'<div class="ui-tree-clip"><div data-action="check" data-id="<#=id#>" data-pid="<#=pid#>" class="ui-tree-node"><#=#indent##> <span class="ui-node-text"><#=text#></span><span class="ui-tree-check <#=#checkedclass##>"><#=#checkedimg##></span></div><#=#childrenNodes##></div>',
					expands:{
						checked:function(id){
							var me = this,dom = me.dom.find('[data-id="'+ id +'"]'),checkDom = dom.find('.ui-tree-check'),par,nodeData = me.getDataById(Number(id));
							checkDom.addClass(checkedClass);
							checkDom.html(imgStr);
							par = dom.parent().parent().children('[data-action="check"]');
							nodeData['isChecked'] = 1;
							if(par.size() > 0 && (par.get(0) != me.dom.get(0)) && (par.attr('data-action') == 'check')){
								arguments.callee.call(me, par.attr('data-id'));
							}
						},
						unchecked:function(id){
							var me = this,dom = me.dom.find('[data-id="'+ id +'"]'),checkDom = dom.find('.ui-tree-check'),nodeData = me.getDataById(Number(id)),i = 0,len,
								par,
								checkedNum = 0;
								
							nodeData['isChecked'] = 0;
							checkDom.removeClass(checkedClass);
							checkDom.html('');
							
							if(nodeData['children']){
								for(i = 0,len = nodeData['children'].length;i < len;i++){
									arguments.callee.call(me, Number(nodeData['children'][i]['id']));
								}
							}
							
							me.clearSingleLabel();
						},
						//清除空label节点
						clearSingleLabel:function(){
							var me = this,data = me.getData(),i = 0,len = data.length,children,dom,checkDom,num = 0;
							for(;i < len;i++){
								if((data[i]['isChecked'] == 1 && data[i]['type'] == 2) || (data[i]['level'] == 0)){
									if(data[i]['children']){
										children = data[i]['children'];
										num = 0;
										$.each(children, function(){
											if(this['isChecked'] == 1){
												num += 1;
											}
										});
										//console.log(data[i]['id'], data[i]['level'], data[i]['children'], num);
										if(num <= 0){
											
											data[i]['isChecked'] = 0;
											dom = me.dom.find('[data-id="'+ data[i]['id'] +'"]');
											checkDom = dom.find('.ui-tree-check');
											checkDom.removeClass(checkedClass);
											checkDom.html('');
										}
									}
								}
							}
						},
						getCheckData:function(){
							var me = this,data = me.getData(),i = 0,len = data.length,arr = [];
							for(;i < len;i++){
								if(data[i]['isChecked'] == 1){
									delete data[i]['children'];
									arr.push(data[i]);
								}
							}
							return arr;
						},
						formatNodeHTML:function(str, node){
							var img = (node['isChecked'] == 1) ? imgStr : '',
								cls = (img != '') ? checkedClass : '';
							return str.replace(/<#=#checkedimg##>/g, img).replace(/<#=#checkedclass##>/g, cls);
						}
					}
				};
			
			Tree = new phoenix.Tree(cfg);
			
			Tree.addEvent('click', function(e, el){
				var me = this,dom = $(el),action = dom.attr('data-action'),checkDom,id,node,par,childs,
				checkedNum = 0,pid=dom.attr('data-pid'),pidData=$("#J-tree-container").find("[data-id='"+pid+"']").attr("data-pid");
				if(action != 'check' || pid =='0' || pidData=='0' ){
					return;
				}
				id = Number(dom.attr('data-id'));
				checkDom = dom.find('.ui-tree-check');
				if(checkDom.hasClass('ui-tree-check-checked')){
					me.unchecked(id);
					
					par = me.getDataById(me.getDataById(id)['pid']);
					//label 类型子类不允许为空
					if(par && ((par['type'] == 2) || (par['level'] == 0))){
						childs = par['children'];
						if(childs.length > 0){
							$.each(childs, function(){
								checkedNum = (this['isChecked'] == 1) ? (checkedNum + 1) : checkedNum;
							});
							if(checkedNum <= 0){
								me.unchecked(par['id']);
							}
						}
					}
					
					
				}else{
					me.checked(id);
				}
				
				buildRight(me.getCheckData());
			});
			
			buildRight(Tree.getCheckData());
	};
	
	var buildRight = function(data){
			var treeData = data,
				cfg = {
					data:treeData,
					dom:'#J-tree-container2',
					path:staticFileContextPath + '/static/images/common/tree/blue/',
					nodeTpl:'<div class="ui-tree-clip"><div data-id="<#=id#>" data-pid="<#=pid#>" class="ui-tree-node"><#=#indent##> <span class="ui-tree-node-text"><#=text#></span><span data-id="<#=id#>" data-action="del" class="ui-tree-del"><img src="'+staticFileContextPath+'/static/images/common/tree/blue/del.png" class="ui-tree-img-del" /></span></div><#=#childrenNodes##></div>',
					expands:{
						getCheckedIds:function(){
							var me = this,data = me.getData(),i = 0,len = data.length,arr = [];
							for(;i < len;i++){
								if(data[i]['isChecked'] == 1){
									arr.push(data[i]['id']);
								}
							}
							return arr;
						}
					}
				};
			
			if(Tree2){
				Tree2.dom.remove();
			}
			
			Tree2 = new phoenix.Tree(cfg);
			
			Tree2.addEvent('click', function(e, el){
				var me = this,dom = $(el),action,id,par,childs,checkedNum = 0;
				dom = dom.hasClass('ui-tree-img-del') ? dom.parent() : dom;
				action = dom.attr('data-action')
				if(action != 'del'){
					return;
				}
				id = Number(dom.attr('data-id'));
				
				Tree.unchecked(id);
				
				par = Tree.getDataById(Tree.getDataById(id)['pid']);
				//label 类型子类不允许为空
				if(par && ((par['type'] == 2) || (par['level'] == 0))){
					childs = par['children'];
					if(childs.length > 0){
						$.each(childs, function(){
							checkedNum = (this['isChecked'] == 1) ? (checkedNum + 1) : checkedNum;
						});
						if(checkedNum <= 0){
							Tree.unchecked(par['id']);
						}
					}
				}
				
				buildRight(Tree.getCheckData());
				
			});
			
		
		$('#J-input-ids').val(Tree2.getCheckedIds().join(','));
		
	};
	
	
	var url = baseUrl+"/aclAdmin/buildAclTree";
    jQuery.ajax({
		type: "post",
		url: url,
		data : "id="+-1+"&pid="+pgid+"&num=" + Math.random(),
		dataType :"json",
		success: function(data){
			buildLeft(data.pdata);
		},
		error: function(xhr,status,errMsg){
		alert(errMsg);
		}
		});
	
	
	
	
	

	//表单提交
	$('#J-button-submit').click(function(){
		var titleDom = $('#input-group-title'),
			title = $.trim(titleDom.val()),
			ids = $.trim($('#J-input-ids').val()),
			tipsDom = titleDom.next();

		var url = baseUrl+"/aclAdmin/addGroup";
		var inUser=$("input[name='inUser']:checked").val();
		var data="&pid="+pgid+"&inUser="+inUser+"&name="+title+"&ids="+ids+"&userId=1"+"&num=" + Math.random();
		
		if(title.length > 20 || title.length < 2){
			tipsDom.html('<span style="color:#ff0000">组名称长度应为2-20个字符</span>');
			titleDom.focus();
			return;
		}
		if(ids.length < 1){
			alert('你还未选择任何权限');
			return;
		}
		
		if(!groupName){titleDom.focus(); return;}
		
		$.ajax({
			url:url,
			data:data,
			type:"POST",
			dataType:'json',
			success:function(data){
				if(data.status == 1){
					var successDom = $('#tip-success');
					phoenix.util.toViewCenter(successDom);
					successDom.show();
					setTimeout(function(){
						successDom.hide();
						window.location.href=baseUrl + "/aclAdmin/queryAclGroup?userId=1"+"&num=" + Math.random();
					}, 3000);
					
				}else{
					var failDom = $('#tip-fail');
					phoenix.util.toViewCenter(failDom);
					failDom.show();
					setTimeout(function(){
						failDom.hide();
					}, 3000);
				}
			},
			error:function(xhr,status,errMsg){
				alert(errMsg);
			}
		});			
	});
});