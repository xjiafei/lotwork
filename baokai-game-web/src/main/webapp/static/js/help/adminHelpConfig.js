$(document).ready(function(){
	 $('.menu-list li:eq(7)').attr("class","current");
var pros,UpdateInput,defConfig,checkHelp,addButton = $('#J-button-knowledge-add');
    Wd = phoenix.Message.getInstance();
	defConfig = {
		//ajax请求地址
		url:'',
		dataType:'json',
		data:''
	};
	pros = {
		init:function(cfg){
			var me = this;
			me.dom = $(cfg.dom);
			me.type = me.dom.attr('data-type');
			me.dom.focus(function(e){
				var v = $.trim(this.value);
				if(v != ''){
					me.value = v;
				}
				
			});
			me.dom.blur(function(){
				var v = $.trim(this.value);
				if(v == me.value){
					return;
				}
				me.send();
			});
		},
		send:function(){
			var me = this,param = me.getParam();
			if(!me.check(param)){
				return;
			}
			me.config(param);
			var url = UpdateInput.defConfig.url;
			var dataStr = UpdateInput.defConfig.data;
			 $.ajax({
				url:url,
				dataType:'json',
				method:'post',
				//如果id为空，则为添加新彩种知识目录
				//该处有个逻辑问题，当先设置排序时，能不能增加，应视为无效请求
				data:dataStr,
				success:(function(){return function(data){
					me.success(data);
					me.callBackAdd(data);
				}})(),
				error:(function(){return function(xhr, type){
					me.error(xhr, type);
					window.location.reload();
				}})()
			});
		},
		setMessage:function(msg){
			var me = this;
			me.dom.parent().find('.ui-check').html('<i></i>' + msg).show();
		},
		hdieMessage:function(){
			var me = this;
			me.dom.parent().find('.ui-check').hide();
		},
		check:function(param){
			var me = this;
			return checkHelp[this.type].call(me, param);
		},
		getParam:function(){
			var me = this;
			return {value:$.trim(me.dom.val()),id:me.dom.attr('data-id'),type:me.dom.attr('data-type')};
		},
		config:function(param){
			var me = this;
			config[this.type].call(me, param);
		},
		callBackAdd:function(data){
			if(data.id != undefined  && data.id !='undefined' && data.id !=''){
				var me = this;
				 me.dom.attr("data-id",data.id);
				 me.dom.val(data.name);
				 me.dom.parent().next().find("input").val(data.no);
				 me.dom.parent().next().find("input").attr("data-id",data.id);
			}
		},
		success:function(data){
			var me = this;
			if(data.status == 1){
				me.hdieMessage();
			}else if(data.status == 0){
				me.setMessage(data.message);
			}
			
		},
		error:function(xhr, type){
			alert(type);
		}
		
	};
	UpdateInput = phoenix.Class(pros, phoenix.Event);
	UpdateInput.defConfig = defConfig;
	
	config = {
			'keyword':function(param){
				var v = param.value,no=param.id;
				UpdateInput.defConfig.url=baseUrl +"/helpAdmin/modifyKeyword";
				UpdateInput.defConfig.data="keyword="+v+"&no="+no;
			},
			'knowledge':function(param){
				var v = param.value, id= param.id;
				UpdateInput.defConfig.url=baseUrl +"/helpAdmin/addLotteryKnowledgeCate";
				if(id==''||id==null){
					UpdateInput.defConfig.data="name="+v;
				}else{
					UpdateInput.defConfig.url=baseUrl +"/helpAdmin/editLotteryKnowledgeCate";
					UpdateInput.defConfig.data="id="+id+"&name="+v;
				}
			},
			'knowledgeSort':function(param){
				var me = this,v = param.value ,id= param.id;
				UpdateInput.defConfig.url=baseUrl +"/helpAdmin/editLotteryKnowledgeCate";
				UpdateInput.defConfig.data="id="+id+"&no="+v;
			},
			'lotterySort':function(param){
				var me = this,v = param.value ,id= param.id;
				UpdateInput.defConfig.url=baseUrl +"/helpAdmin/changeHotLotteryNo";
				UpdateInput.defConfig.data="id="+id+"&no="+v;
			}
	};
	
	//数据检测校验
	checkHelp = {
		'keyword':function(param){
			var me = this,v = param.value;
			if(v.length < 1 || v.length > 4){
				me.setMessage('关键字必须是1-4个字');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			me.dom.val(v);
			return true;
		},
		'knowledge':function(param){
			var me = this,v = param.value;
			if(v.length < 1 || v.length > 6){
				me.setMessage('彩种知识目录必须是1-6个字');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			return true;
		},
		'knowledgeSort':function(param){
			var me = this,v = param.value;
			if(!(/^\d+$/g).test(v)){
				me.setMessage('排序必须是数字');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			if(Number(v) > 1000000){
				me.setMessage('排序数字不能大于1000000');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			var booleanDate = true;
			me.dom.parent().parent().siblings().each(function(){
				var testV = $(this).find('[data-type="knowledgeSort"]').val();
				if(testV == v){
					booleanDate = false;
				}
			});
			if(!booleanDate){
				me.setMessage('排序数字不能重复');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			return true;
		},
		'lotterySort':function(param){
			var me = this,v = param.value;
			if(!(/^\d+$/g).test(v)){
				me.setMessage('排序必须是数字');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			if(Number(v) > 1000000){
				me.setMessage('排序数字不能大于1000000');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			var booleanDate = true;
			me.dom.parent().parent().siblings().each(function(){
				var testV = $(this).find('[data-type="lotterySort"]').val();
				if(testV == v){
					booleanDate = false;
				}
			});
			if(!booleanDate){
				me.setMessage('排序数字不能重复');
				me.dom.focus();
				return false;
			}else{
				me.hdieMessage();
			}
			return true;
		}
	};
	
	$('#J-panel-help-setting').find('input[data-id]').each(function(){
		new UpdateInput({dom:this});
	});
	
	addButton.click(function(e){
		e.preventDefault();
		var tbody = $('#J-table-knowledge').find('tbody'),inputText,inputSort,tr;
		tbody.prepend($('#J-knowledge-tpl').html());
		setTimeout(function(){
			tr = tbody.find('tr').eq(0);
			inputText = tr.find('input').eq(0);
			inputSort = tr.find('input').eq(1);
			new UpdateInput({dom:inputText});
			new UpdateInput({dom:inputSort});
		},300);
		
	}); 
		
	$("#J-table-knowledge").find('a').bind("click",function(){
	    var me = $(this);
		var id=me.parent().prev().find('input').attr("data-id");
		var url = baseUrl + "/helpAdmin/deleteLotteryKnowledgeCate";
		var dataStr = "id="+id+"&num="+Math.random();
		var tr = $(this).parent().parent();
        if(!id || id == ''){
			me.parent().parent().remove();
			return;
		}
		Wd.show({
			mask:true,
			title:'温馨提示',
			content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">你确定要删除该行数据吗？</h4></div></div>',
			confirmIsShow:true,
			cancelIsShow:true,
			confirmFun:function(){
				$.ajax({
					url:url,
					dataType:'json',
					method:'post',
					//如果id为空，则为添加新彩种知识目录
					//该处有个逻辑问题，当先设置排序时，能不能增加，应视为无效请求
					data:dataStr,
					success:function(data){
						//测试数据		
						if(Number(data['status']) == 1){
							me.parent().parent().remove();
						}else{
							alert(data['message']);
						}
						Wd.hide();
						location.href = location.href;
					}
				});
			},
			cancelFun:function(){
				Wd.hide();
			}
		});
	});
});