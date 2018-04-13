$(document).ready(function(){
    $('.menu-list li:eq(8)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
	var Tree,Tree2,gid=$("#id").val(),pgid=$("#pid").val();
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
			
			buildRight(Tree.getCheckData());
	};
	
	var buildRight = function(data){
			var treeData = data,
				cfg = {
					data:treeData,
					dom:'#J-tree-container2',
					path:staticFileContextPath +'/static/images/common/tree/blue/',
					nodeTpl:'<div class="ui-tree-clip"><div data-id="<#=id#>" data-pid="<#=pid#>" class="ui-tree-node"><#=#indent##> <span class="ui-tree-node-text"><#=text#></span></div><#=#childrenNodes##></div>',
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
		data : "id="+gid+"&pid="+pgid+"&num=" + Math.random(),
		dataType :"json",
		success: function(data){
			buildLeft(data.pdata);
		},
		error: function(xhr,status,errMsg){
		alert(errMsg);
		}
		}); 
	
});