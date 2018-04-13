

//模拟下拉框组件
(function(host, name, Event, $, undefined){
	var defConfig = {
		//最外层添加的class样式
		cls:'ui-simulation-select',
		//是否同时能输入
		isInput:false,
		//对应的真实select
		realDom:'',
		//模拟select模板
		tpl:'<div class="choose-model"><div class="choose-list" style="display:none;"><#=loopItems#></div><span class="info"><input data-realvalue="<#=value#>" class="choose-input" disabled="disabled" type="text" value="<#=text#>" /></span><i></i></div>',
		//单行元素模板
		itemTpl:'<a data-value="<#=value#>" href="javascript:void(0);"><#=text#></a>'
	};
	
	var pros = {
		init:function(cfg){
			var me = this;
			me.realDom = $(cfg.realDom);
			me.realDom.hide();
			me.dom = null;
			me.listDom = null;
			me.buildSelect();
		},
		buildSelect:function(){
			var me = this,cfg = me.defConfig,tpl = cfg.tpl,itemTpl = cfg.itemTpl,items = me.getRealDom().options,len = items.length,i = 0,
				itemStrArr = [],
				currValue = '',
				currText = '';
			for(;i < len;i++){
				itemStrArr[i] = itemTpl.replace(/<#=value#>/g, items[i].value).replace(/<#=text#>/g, items[i].text);
				if(i == me.getRealDom().selectedIndex){
					currValue = items[i].value;
					currText = items[i].text;
				}
			}
			tpl = tpl.replace(/<#=text#>/g, currText).replace(/<#=loopItems#>/g, itemStrArr.join(''));
			me.dom = $(tpl);
			me.dom.addClass(cfg.cls);
			me.dom.insertBefore(me.getRealDom());
			
			me.dom.click(function(e){
				var el = e.target;
				//如果是选项点击
				if(!!el.getAttribute('data-value')){
					me.setValue(el.getAttribute('data-value'));
				}
				//ricahrdgong 2015-06-26 添加记录到cookie
				if ($(me.getRealDom()).data("moneyUnitDom-record")) {
					$.cookie("moneyUnitDom-record",el.getAttribute('data-value'))
				}
				me.getListDom().toggle();
			});
			
			$(document).mousedown(function(e){
				var el = e.target;
				if(!$.contains(me.dom.get(0), el)){
					me.getListDom().hide();
				}
			});
			
			if(cfg.isInput){
				me.getInput().removeAttr('disabled');
				me.inputEvent();
			}
			me.setValue(currValue);
		},
		getInput:function(){
			var me = this;
			return me.input || (me.input = me.dom.find('.choose-input'));
		},
		//input校验函数
		inputEvent:function(){
			
		},
		getListDom:function(){
			var me = this;
			return me.listDom || (me.listDom = me.dom.find('.choose-list'));
		},
		getRealDom:function(){
			return this.realDom.get(0);
		},
		setValue:function(value){
			var me = this,dom = me.getRealDom(),index = dom.selectedIndex,options = dom.options,len = options.length,i = 0,text = '';
			if($(me.getRealDom()).data("moneyUnitDom-record")){
				//debugger
			}
			for(;i < len;i++){
				if(value == options[i].value){
					options[i].selected = true;
					text = options[i].text;
				}else{
					options[i].selected = false;
				}
			}
			me.getInput().attr('data-realvalue', value);
			text = text == '' ? value : text;
			me.getInput().val(text);
			me.fireEvent('change', value, text);
		},
		getValue:function(){
			var me = this,dom = me.getRealDom(),index = dom.selectedIndex;
			if(me.defConfig.isInput){
				return me.getInput().attr('data-realvalue');
			}
			return dom.options[index].value;
		},
		getText:function(){
			var dom = this.getRealDom(),index = dom.selectedIndex;
			return dom.options[index].text;
		},
		show:function(){
			this.dom.show();
		},
		hide:function(){
			this.dom.hide();
		}

	};

	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;


})(phoenix, "Select", phoenix.Event, jQuery);








