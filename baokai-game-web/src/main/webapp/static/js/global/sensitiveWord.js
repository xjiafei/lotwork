$(document).ready(function(){
	$('.menu-list li:eq(1)').attr("class","current");
	//弹窗
	var Wd = phoenix.Message.getInstance();

	//搜索输入框默认文本
	var Input = new phoenix.Input({defText:"请输入敏感词",el:'#J-search-input'});
	
	
	//搜索表单提交
	var form = $('#J-search-form'),
		button = $('#J-search-submit');
	button.click(function(){
		var v = Input.getValue();
		if($.trim(v) == '' || v == Input.getDefText()){
			alert('搜索关键词不能为空');
			Input.dom.focus();
			return;
		}
		form.submit();
	});
	
	
	var sortPanel = $('#J-side-menu');
	//左侧分类修改和删除
	sortPanel.on('click', '.dropdown-btn', function(){
		var dom = $(this),panel = dom.parent().find('.dropdown-menu');
		panel.show();
	});
	sortPanel.on('click', '.sort-rename', function(){
		var dom = $(this),id = Number(dom.attr('data-id')),title = dom.attr('data-title');
		Wd.show({
			mask:true,
			confirmIsShow:true,
			confirmFun:function(){
				var input = $('#J-input-new-sort'),v = $.trim(input.val()),v2 = v.replace(/[^\x00-\xff]/g, 'xx');
				if(v2.length < 4 || v2.length > 20){
					alert('分类名称长度必须是4-20位字符');
					input.focus();
					return;
				}
				$.ajax({
					url:'data.php?action=edit&id=' + id,
					dataType:'json',
					method:'post',
					data:{title:v},
					success:function(data){
						//测试数据
						data = {isSuccess:1,type:'success',data:{id:1,title:$('#J-input-new-sort').val(),num:10}};
						
						if(Number(data['isSuccess']) == 1){
							var par = dom.parents('li'),textDom = par.children('a'),sortData = data['data'];
							textDom.text(sortData['title'] + ' ' + sortData['num'] + '个');
							par.find('.sort-rename').attr('data-id', sortData['id']).attr('data-title', sortData['title']);
							par.find('.sort-delete').attr('data-id', sortData['id']).attr('data-title', sortData['title']);
							Wd.hide();
							
							location.href = location.href;
						}else{
							alert(data['msg']);
						}
						updateSort();
					}
				});
				
			},
			cancelIsShow:true,
			cancelFun:function(){
				Wd.hide();
			},
			title:'重命名',
			content:$('#J-tpl-addsort').html(),
			callback:function(){
				$('#J-input-new-sort').val(title);
			}
		});
	});
	sortPanel.on('click', '.sort-delete', function(){
		var dom = $(this),li = dom.parents('li'),id = Number(dom.attr('data-id'));
		
		Wd.show({
			mask:true,
			title:'提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除分类 ['+ dom.attr('data-title') +'] 吗？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:'data.php?action=delete&id=' + id,
					dataType:'json',
					success:function(data){
						//测试数据
						data = {isSuccess:1,type:'success',data:{}};
								
						if(Number(data['isSuccess']) == 1){
							li.remove();
						}else{
							alert(data['msg']);
						}
						Wd.hide();
						updateSort();
						
						location.href = location.href;
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
		
	});
	
	
	
	
	
	//新建敏感词
	var addButton = $('#J-button-keyword-add');
	addButton.click(function(){
		Wd.show({
			confirmIsShow:true,
			confirmFun:function(){
				var input = $('#J-textarea-new-words'),arr = $.trim(input.val()).split('\n'),len = arr.length,i = 0,words = [],pid = $('#J-select-new-words').val(),prot=Wd.win.dom.find('.ui-prompt'),newprot=Wd.win.dom.find('#ui-prompt');
				for(;i < len;i++){
					if($.trim(arr[i]) != ''){
						words.push(arr[i]);
					}
				}
				if(words.length < 1){
					alert('敏感词不能为空');
					input.focus();
					return;
				}
				if($.trim(pid) == ''){
					alert('请选择一个分类');
					return;
				}
				$.post(baseUrl+"/globeAdmin/createSensitiveWord",{wordStr:input.val(),pid:pid},function(result){
					var obj=eval("("+result+")");
					if(obj["status"]=="0"){
						    prot.hide();
						    newprot.text("敏感词新建成功");
						    newprot.show();
							setTimeout(function(){location.href = baseUrl+"/globeAdmin/searchSensitiveWord";},1500);
							
						}else if(obj["status"]=="1"){
							prot.hide();
						    newprot.text("敏感词【"+obj.message+"】已存在");
						    newprot.show();
						}else if(obj["status"]=="2")
						{
							prot.hide();
						    newprot.text("敏感词"+obj.message+"失败");
						    newprot.show();
						}
					
				});
			},
			cancelIsShow:true,
			cancelFun:function(){
				Wd.hide();
			},
			title:'新建敏感词',
			content:$('#J-tpl-addkeyword').html(),
			callback:function(){
				$('#J-textarea-new-words').keyup(function(){
					/*$(this).val($(this).val().replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,''));*/
				}).focus(function(e) {
                    Wd.win.dom.find('#ui-prompt').hide();
					$(Wd.win.dom.find('.ui-prompt')[0]).show();
                });;
				Wd.win.dom.find('.ui-select').html($('#J-sort-select-data').html());
			}
		});
	});
	
	
	
	//新建分类
	var addSortButton = $('#J-button-keyword-addsort');
	addSortButton.click(function(){
		Wd.mask.css({opacity:.2});
		Wd.show({
			mask:true,
			confirmIsShow:true,
			confirmFun:function(){
				var input = $('#J-input-new-sort'),v = $.trim(input.val()),v2 = v.replace(/[^\x00-\xff]/g, 'xx');
				if(v2.length < 4 || v2.length > 20){
					alert('分类名称长度必须是4-20位字符');
					input.focus();
					return;
				}
				$.ajax({
					url:'data.php',
					dataType:'json',
					success:function(data){
						if(Number(data['isSuccess']) == 1){
							var id = data['data']['id'],
								html = $('#J-tpl-sort').html().replace(/<#=title#>/g, v).replace(/<#=num#>/g, '0').replace(/<#=id#>/g, id);
							$(html).insertBefore(addSortButton);
							
							Wd.hide();
							//location.href = location.href;
						}else{
							alert(data['msg']);
						}
						updateSort();
					}
				});
				
			},
			cancelIsShow:true,
			cancelFun:function(){
				Wd.hide();
			},
			title:'新建分类',
			content:$('#J-tpl-addsort').html()
		});
	});
	
	
	
	$('body').on('keyup', '#J-input-new-sort', function(){
		var dom = $(this),v = $.trim(dom.val());
		if(v.length > 20){
			dom.val(v.substr(0, 20));
		}
	});
	
	
	
	//新增、修改、删除分类后，获取最新的分类列表
	var updateSort = function(){
		var dataDom = $('#J-sort-select-data');
		$.ajax({
			url:'data.php',
			dataType:'json',
			success:function(data){
				if(Number(data['isSuccess']) == 1){
					var list = data['data'],arr = [];
					
					//测试数据
					list = [{'title':'哇哈哈','num':4,'id':1},{'title':'张学友','num':2,'id':2},{'title':'梁朝伟','num':1,'id':3}];
					
					$.each(list, function(){
						arr.push('<option value="'+ this.id +'">'+ this.title +'</option>');
					});
					dataDom.html(arr.join(''));
				}else{
					alert(data['msg']);
				}
			}
		});
	};
	
	updateSort();
	
	//全选
	$('#J-select-all').click(function(){
		var inputs = $('#J-table-list').find('input[type="checkbox"]');
		if(this.checked){
			inputs.each(function(){
				this.checked = true;
			});
		}else{
			inputs.each(function(){
				this.checked = false;
			});
		}
	});
	
	//批量删除
	$('#J-delete-all').click(function(){
		var inputs = $('#J-table-list').find('input[type="checkbox"]'),result = "";
		inputs.each(function(){
			if(this.checked){
				result = result + this.value + ",";
			}
		});
		if(result == ""){
			return;
		}
		Wd.show({
			mask:true,
			title:'提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除列表中所选的敏感词吗？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.post(baseUrl+"/globeAdmin/deleteSensitiveWord",{ids:result},function(result){
					location.href = baseUrl+"/globeAdmin/searchSensitiveWord";
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
	
	
	$('#J-table-list').on('click', '.word-rename', function(e){
		e.preventDefault();
		var dom = $(this),
			id = Number(dom.attr('data-id')),
			pid = Number(dom.attr('data-pid')),
			title = dom.attr('data-title');
		
		Wd.show({
			confirmIsShow:true,
			confirmFun:function(){
				var input = $('#J-textarea-new-words'),arr = $.trim(input.val()).split('\n'),len = arr.length,i = 0,words = [],pid = Wd.win.dom.find('.ui-select').val();
				for(;i < len;i++){
					if($.trim(arr[i]) != ''){
						words.push(arr[i]);
					}
				}
				if(words.length < 1){
					alert('敏感词不能为空');
					input.focus();
					return;
				}
				$.post(baseUrl+"/globeAdmin/reNameSensitiveWord",{word:input.val(),id:id},function(result){
					var obj=eval("("+result+")");
					if(obj["status"]=="0"){
						 alert("修改敏感词成功");
						 location.href = baseUrl+"/globeAdmin/searchSensitiveWord";
						}else if(obj["status"]=="1"){
							alert("敏感词【"+obj.message+"】已存在");
							/*prot.hide();
						    newprot.text("敏感词【"+obj.message+"】已存在");
						    newprot.show();*/
						}else if(obj["status"]=="2")
						{
							alert("敏感词"+obj.message+"失败");
							/*prot.hide();
						    newprot.text("敏感词"+obj.message+"失败");
						    newprot.show();*/
						}
					
				});
			},
			cancelIsShow:true,
			cancelFun:function(){
				Wd.hide();
			},
			title:'修改敏感词',
			content:$('#J-tpl-modifykeyword').html(),
			callback:function(){
				var selDom = Wd.win.dom.find('.ui-select');
				selDom.html($('#J-sort-select-data').html());
				setTimeout(function(){
					selDom.val(pid);
				},0);

				$('#J-textarea-new-words').val(title);
				$('#modify-id').val(id);
			}
		});
	});
	
	$('#J-table-list').on('click', '.word-delete', function(e){
		e.preventDefault();
		var dom = $(this),
			aDom = dom.parent().find('.word-rename').eq(0),
			id = Number(aDom.attr('data-id')),
			pid = Number(aDom.attr('data-pid')),
			title = aDom.attr('data-title');
			
		Wd.show({
			mask:true,
			title:'提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除 ['+ title +'] 敏感词吗？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.post(baseUrl+"/globeAdmin/deleteSensitiveWord",{ids:id},function(result){
					location.href = baseUrl+"/globeAdmin/searchSensitiveWord";
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
		
	});
	
	
})