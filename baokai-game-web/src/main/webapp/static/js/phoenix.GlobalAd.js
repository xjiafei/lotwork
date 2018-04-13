

//广告静态类
(function(host, name, Event, $, undefined){
	var defConfig = {
		//数据请求类型
		dataType:'jsonp',
		//所有广告位容器
		posdoms:'.j-ui-globalad-pos',
		//单个广告模板
		tplSingle:'<a  title="<#=title#>" target="_blank" href="<#=link#>" ><img src="<#=src#>" title="<#=title#>" alt="<#=title#>" /></a>',
		//切换广告容器class
		panelCls:'slider-pic',
		triggerCls:'slider-num',
		//切换广告模板
		tplMultiplePanel:'<ul class="<#=cls#> j-ui-slider-panel"><#=loop#></ul>',
		tplMultiplePanelRow:'<li style="width:<#=width#>px;height:<#=height#>px;"><a target="_blank" title="<#=title#>" href="<#=link#>"><img src="<#=src#>" title="<#=title#>" alt="<#=title#>" /></a></li>',
		tplMultipleTrigger:'<ul class="<#=cls#> j-ui-slider-trigger"><#=loop#></ul>',
		tplMultipleTriggerRow:'<li><#=index#></li>',
		//设置该广告图片的宽高
		width:0,
		height:0
	},
	//公共实例
	instance;
	
	var pros = {
		init:function(cfg){
			var me = this,url = cfg.url,callback = cfg.callback;
			me.data = [];
			$(function(){
				me.loadData(url, callback);
			});
		},
		//{"isSuccess":1, "msg":"请求成功", "data":[{"pageid":1,"posid":1,"list":[{"id":1,"src":"/images/login/zzz-1.png","link":"http://baidu.com/","target":"_blank","width":320,"height":120,"title":"哇哈哈"},{"id":1,"src":"/images/login/zzz-1.png","link":"http://baidu.com/","target":"_blank","width":320,"height":120,"title":"哇哈哈"}]}]}
		loadData:function(url, callback){
			var me = this;
			$(function(){
				$.ajax({
					url:url,
					cache:false,
					dataType:me.defConfig.dataType,
					jsonp: "callBack",
					success:function(data){
						if(Number(data['isSuccess']) == 1){
							me.data = data['data'];
							if(me.data.length > 0){
								me.build();
								if($.isFunction(callback)){
									callback.call(me, me.data);
								}
							}else{
								$(me.defConfig.posdoms).remove()
							}
						}else{
							//alert(data['msg']);
						}
					},
					error:function(xhr, type){
						//console.log(type);
					}
				});
			});
		},
		build:function(){
			var me = this,list = me.data,i = 0,len = list.length,listLen,ad,dom,width = me.defConfig.width,height = me.defConfig.height;
			
			for(;i < len;i++){
				dom = me.getPosDom(list[i]['name']);
				
				if(dom.size() > 0){
					listLen = list[i]['list'].length;
					if(listLen > 0){
						dom.css({width:width});
						if(listLen > 1){
							me.buildMultiple(list[i]['list'], dom);
						}else{
							me.buildSingle(list[i]['list'][0], dom);
						}
					}else{
						dom.remove();
					}
				}
			}
			
		},
		
		//处理单个广告
		//ad 广告数据
		//posid 广告位id
		//dom 广告容器
		buildSingle:function(ad, dom){
			var me = this,tpl = me.defConfig.tplSingle,html = "";
			ad = me.reBuildAd(ad);
			html = host.util.template(tpl, ad);
			dom.html(html);
		},
		//处理切换广告
		buildMultiple:function(list, dom){
			var me = this,i = 0,len = list.length,ad,panelStr = [],triggerStr = [],cfg = me.defConfig,
				tplp = cfg.tplMultiplePanelRow,
				tplt = cfg.tplMultipleTriggerRow,
				tplPanel = cfg.tplMultiplePanel,
				tplTrigger = cfg.tplMultipleTrigger,
				panelCls = cfg.panelCls,
				triggerCls = cfg.triggerCls,
				html = '';
			for(;i < len;i++){
				ad = me.reBuildAd(list[i]);
				ad['index'] = (i + 1);
				panelStr.push(host.util.template(tplp, ad));
				triggerStr.push(host.util.template(tplt, ad));
			}
			tplPanel = tplPanel.replace(/<#=cls#>/g, panelCls);
			tplTrigger = tplTrigger.replace(/<#=cls#>/g, triggerCls);
			html += tplPanel.replace(/<#=loop#>/g, panelStr.join(''));
			html += tplTrigger.replace(/<#=loop#>/g, triggerStr.join(''));
			dom.html(html);
		},
		//处理广告数据
		reBuildAd:function(ad){
			var me = this,width = me.defConfig.width,height = me.defConfig.height;
				ad['link'] = me.reBuildLink(ad['id'], ad['link']);
				ad['src'] = ad['src'];
				ad['width'] = width;
				ad['height'] = height;
			return ad;
		},
		//对链接进行处理
		reBuildLink:function(id, link){
			return link;
		},
		//获取广告位dom
		getPosDom:function(poskey){
			//console.log('#J-globalad-' + poskey);
			return $('#J-globalad-' + poskey);
		}
		

	};

	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;

})(phoenix, "GlobalAd", phoenix.Event, jQuery); 







