$(document).ready(function(){
	 $('.menu-list li:eq(7)').attr("class","current");
	//分类显示隐藏
	var panel = $('#J-panel-sort'),
		switchCls = 'item-name-open',
		selector = '.item-rename, .item-del, .item-up, .item-down',
		minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
		mask = phoenix.Mask.getInstance();
		var obj='';
	minWindow.dom.css({
		width:360
	});
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterShow', function(){
		setTimeout(function(){
			$('#J-input-sortname').focus();
		},500);
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
		Sort.currentId = '';
		Sort.currentAction = '';
		Sort.currentName = '';
	});

	
	panel.find('.item-first-name').click(function(){
		var me = $(this);
		if(me.hasClass(switchCls)){
			me.removeClass(switchCls);
			me.siblings('.item-child').hide();
		}else{
			me.addClass(switchCls);
			me.siblings('.item-child').show();
		}
	});
	panel.find('.item, .item-child').hover(function(){
		var me = $(this);
		me.children(selector).css('visibility', 'visible');
	},function(){
		var me = $(this);
		me.children(selector).css('visibility', 'hidden');
	});
	
	
	//分类增改删
	var Sort = {
		currentId:'',
		currentAction:'',
		currentName:'',
		currentLevel:'',
		html_input:'<div><input id="J-input-sortname" type="text" value="<#=value#>" class="input"/>限16个字符</div>',
		html_control:'<div class="control"><a data-action="confirm" href="javascript:void(0);" class="btn">确定<b class="btn-inner"></b></a> <a data-action="cancel" href="javascript:void(0);" class="btn">取消<b class="btn-inner"></b></a</div>',
		add:function(id){
			var me = this,strArr = [],titleText = '添加一级类目';
			titleText = id < 1 ? titleText : '添加二级类目';
			var html = id < 1 ? me.html_input :me.html_input.replace('限16个字符','限30个字符');
			minWindow.setTitle(titleText);
			strArr.push(html.replace(/<#=value#>/, ''));
			strArr.push(me.html_control);
			minWindow.setContent(strArr.join(''));
			minWindow.show();
			me.currentId = id;
			me.currentAction = 'add';
			me.currentName = '';
		},
		del:function(id, sortname){
			var me = this,strArr = [];
			minWindow.setTitle('删除类目');
			strArr.push('<div style="line-height:200%;">请确认要删除“<span style="color:#F00;">'+ sortname +'</span>”类目，删除后此类目下的所有帮助内容将自动删除。</div>');
			strArr.push(me.html_control);
			minWindow.setContent(strArr.join(''));
			minWindow.show();
			
			me.currentId = id;
			me.currentAction = 'del';
			me.currentName = sortname;
		},
		edit:function(id, sortname,level){
			var me = this,strArr = [];
			minWindow.setTitle('修改名称');
			var html=level >1 ?me.html_input.replace('限16个字符','限30个字符'):me.html_input;
			strArr.push(html.replace(/<#=value#>/, sortname));
			strArr.push(me.html_control);
			minWindow.setContent(strArr.join(''));
			minWindow.show();
			
			me.currentId = id;
			me.currentAction = 'edit';
			me.currentName = sortname;
			me.currentLevel = level;
		}
	};
	
	$(".item-up").on('click',function(e){
		var el = e.target,action = el.getAttribute('data-action');
		var id = el.getAttribute('data-id');
		var number = $(this).parent().prev().children("span.item-up").attr("data-number");
		var id1 = $(this).parent().prev().children("span.item-up").attr("data-id");
		var number1 = el.getAttribute('data-number');
		obj = $(this);
		doEditNumber(id,number,id1,number1,'up');
	});
	
	$(".item-down").on('click',function(e){
		var el = e.target,action = el.getAttribute('data-action');
		var id = el.getAttribute('data-id');
		var number = $(this).parent().next().children("span.item-down").attr("data-number");
		var id1 = $(this).parent().next().children("span.item-down").attr("data-id");
		var number1 = el.getAttribute('data-number');
		obj = $(this);
		doEditNumber(id,number,id1,number1,'down');
	});
	
	$(".item-rename").on('click',function(e){
		obj = $(this);
	});
	
	$(".item-del").on('click',function(e){
		obj = $(this);
	});
	
	$(document).on('click', function(e){
		var el = e.target,action = el.getAttribute('data-action');
		var obj = $(this);
		switch(action){
			case 'add':
				Sort.add(el.getAttribute('data-id'));
			break;
			case 'edit':
				Sort.edit(el.getAttribute('data-id'), el.getAttribute('data-sortname'),el.getAttribute('data-level'));
			break;
			case 'del':
				Sort.del(el.getAttribute('data-id'), el.getAttribute('data-sortname'));
			break;
			case 'confirm':
				//to do
				var id = Sort.currentId,name = Sort.currentName,action = Sort.currentAction,level=Sort.currentLevel,v = $.trim($('#J-input-sortname').val());			
				switch(action){
					case 'add':
						doAdd(Sort,action);
					break;
					case 'edit':
						doEdit(Sort,action);
					break;
					case 'del':
						doDelete(Sort,action);
					break;
					default:
					break;
				}
				minWindow.hide();
			break;
			case 'cancel':
				minWindow.hide();
			break;
			default:
			break;
		}
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
	}  
	
	function doAdd(Sort,action){
		var id = Sort.currentId,name = Sort.currentName,action = Sort.currentAction,level=Sort.currentLevel,v = $.trim($('#J-input-sortname').val());
		if(v != name){
			//目录名称校验
			if(id < 1){
			  if(WidthCheck(v) < 1 || WidthCheck(v) > 16){
				alert('一级目录长度应为1-16个字符');
				return;
			  }
			}else if(WidthCheck(v) < 1 || WidthCheck(v) > 30){
				alert('二级目录长度应为1-30个字符');
				return;
			}
			var url = baseUrl + "/helpAdmin/addCategory";
			var level = 2;
			if(id < 1){
				level = 1;
			}
			jQuery.ajax({
				type: "post",
				url: url,
				dataType:'json',
				data : "parentId="+id+"&name="+v+"&num="+Math.random()+"&level="+level,
				success: function(data,textStatus){
					if(data.status == 1){
						window.location.href = baseUrl + "/helpAdmin/queryCategory?cate2Name="+cate2Name+"&parentId="+id;
						}else{
							alert(data.message);
					}
					
				},
				error: function(xhr,status,errMsg){
				alert("操作失败!");
				}
				});
		}
	}
	
	function doEdit(Sort,action){
		var id = Sort.currentId,name = Sort.currentName,action = Sort.currentAction,level=Sort.currentLevel,v = $.trim($('#J-input-sortname').val());
		var url = baseUrl + "/helpAdmin/editCategory";
		if(v != name){
			//目录名称校验
			if(level == 1 && (WidthCheck(v) < 1 || WidthCheck(v) > 16)){
				alert('一级目录长度应为1-16个字符');
				return;
			}else if(WidthCheck(v) < 1 || WidthCheck(v) > 30){
				alert('二级目录长度应为1-30个字符');
				return;
			}
			jQuery.ajax({
				type: "post",
				url: url,
				dataType:'json',
				data : "id="+id+"&name="+v+"&num="+Math.random()+"&action="+action,
				success: function(data,textStatus){
					if(data.status == 1){
					$(obj).prev().html(v).text();
					$(obj).attr("data-sortname",v);
					}else{
						alert(data.message);
					}
				},
				error: function(xhr,status,errMsg){
				alert("操作失败!");
				}
				});
			
		}
	}
	
	function doEditNumber(id,number,id1,number1,action){
		if(number == 'undefined' || number == undefined || number == ''	){
			return;
		}
		var url = baseUrl+"/helpAdmin/editCategoryNo";
		jQuery.ajax({
			type: "post",
			url: url,
			dataType:'json',
			data : "id="+id+"&number="+number+"&id1="+id1+"&number1="+number1+"&num="+Math.random()+"&action="+action,
			success: function(data,textStatus){
				if(data.status == 1){
					if(action == 'up'){
						var prev = $(obj).parent().prev();
						$(obj).parent().detach();
						$(obj).parent().insertBefore(prev);
					}
					if(action == 'down'){
						var next = $(obj).parent().next();
						$(obj).parent().detach();
						$(obj).parent().insertAfter(next);
					}
					window.location=window.location;
				}else{
					alert(data.message);
				}
				
			},
			error: function(xhr,status,errMsg){
			alert("操作失败!");
			}
			});
	}
	
	function doDelete(Sort,action){
		var id = Sort.currentId,name = Sort.currentName;
		if(name == '彩种知识'){
			return;
		}
		var url = baseUrl+"/helpAdmin/deleteCategory";
		jQuery.ajax({
			type: "post",
			url: url,
			dataType:'json',
			data : "id="+id+"&num="+Math.random(),
			success: function(data,textStatus){
				if(data.status == 1){
					$(obj).parent().remove();
				}else{
					alert(data.message);
				}
				
			},
			error: function(xhr,status,errMsg){
			alert("操作失败!");
			}
			});
	}
	
	$(document).keydown(function(e){
		if(e.keyCode == 13 && Sort.action != ''){
			$('.ui-alert-custom').find('[data-action="confirm"]').click();
		}
	});
});
