

//树形菜单
(function(host, name, Event, $, undefined){
	var defConfig = {
		//数据容器
		//内容格式 {"id":"1","pid":"0","text":"文本内容","value":"1"}
		data:[],
		//是否显示文件夹图标
		isShowFolder:false,
		//图片路径
		path:'../images/common/tree/',
		
		
		//节点class
		clsNode:'ui-tree-node',
		//节点展开时的class
		clsOpen:'ui-tree-node-open',
		//节点关闭时的class
		clsClose:'ui-tree-node-close',
		//节点包class
		clsClip:'ui-tree-clip',
		//默认缩进class
		clsIndent:'ui-tree-indent',
		//末尾减号图标class
		clsMinusbottom:'ui-tree-minusbottom',
		//末尾加号图标class
		clsPlusbottom:'ui-tree-plusbottom',
		//减号图标class
		clsMinus:'ui-tree-minus',
		//加号图标class
		clsPlus:'ui-tree-plus',
		//文件夹图标class
		clsFolder:'ui-tree-folder',
		//末尾节点内容图标class
		clsContent:'ui-tree-content',
		//末尾加号图标
		imgPlusbottom:'plusbottom.gif',
		//加号图标
		imgPlus:'plus.gif',
		//末尾减号图标
		imgMinusbottom:'minusbottom.gif',
		//减号图标
		imgMinus:'minus.gif',
		//末尾节点图标
		imgContent:'content.gif',
		//展开文件夹图标
		imgFolderopen:'folderopen.gif',
		//文件夹图标
		imgFolder:'folder.gif',
		//上下连接线图标
		imgJoin:'join.gif',
		//末尾连接线图标
		imgJoinbottom:'joinbottom.gif',
		//空白图标
		imgEmpty:'empty.gif',
		//竖线连接线图标
		imgLine:'line.gif',


		//展开收缩节点的动作名
		actionOpen:'openNode',
		
		//基础节点模板
		//<#=#lines##>  <#=#childrenNodes##> 预留替换dom
		nodeTpl:'<div class="ui-tree-clip"><div data-id="<#=id#>" data-pid="<#=pid#>" class="ui-tree-node"><#=#indent##> <span class="ui-node-text"><#=text#></span></div><#=#childrenNodes##></div>'
		
	};

	
	var pros = {
		init:function(cfg){
			var me = this;
			me.dom = $('<div></div>');
			me.dom.appendTo($(cfg.dom));
			me.data = cfg['data'] || [];
			me.hasData = {};
			
			
			me.data = me.formatData();
			//在运算时标记深度和是否达到同级节点的末尾   如：[1,1,1,0] 表示在4个级别深度中，前3个已经达到末尾，不需要画线
			me.tempLines = [];
			//树形结构数据
			me.tree = me.buildTreeData(me.data);
			me.reHasTreeData();
			me.rebuildNode();
			
			
			me.initEvent();
			
			//节点点击
			me.addEvent('click', function(e, dom){
				var me = this,dom = $(dom),nodeDom = dom.parent().parent(),action = dom.attr('data-action'),
					clsClose = me.defConfig.clsClose,
					actionOpen = me.defConfig.actionOpen;
				if(action == actionOpen){
					if(nodeDom.hasClass(clsClose)){
						me.eventOpenNode(nodeDom, dom);
					}else{
						me.eventCloseNode(nodeDom, dom);
					}
				}
			});
			
		},
		getData:function(){
			return this.data;	
		},
		initEvent:function(){
			var me = this;
			me.dom.on('click', function(e){
				me.fireEvent('click', e.target);
			});
		},
		eventCloseNode:function(nodeDom, img){
			var me = this,cfg = me.defConfig,
				path = cfg.path,
				clsNode = cfg.clsNode,
				clsOpen = cfg.clsOpen,
				clsClose = cfg.clsClose,
				clsClip = cfg.clsClip,
				clsFolder = cfg.clsFolder,
				clsMinusbottom = cfg.clsMinusbottom,
				clsPlusbottom = cfg.clsPlusbottom,
				clsMinus = cfg.clsMinus,
				clsPlus = cfg.clsPlus,
				imgPlus = cfg.imgPlus,
				imgMinus = cfg.imgMinus,
				imgMinusbottom = cfg.imgMinusbottom,
				imgPlusbottom = cfg.imgPlusbottom,
				imgFolder = cfg.imgFolder,
				imgFolderopen = cfg.imgFolderopen;
				
			nodeDom.addClass(clsClose).removeClass(clsOpen);
			nodeDom.children('.' + clsClip).hide();
			nodeDom.children('.' + clsNode).children('.' + clsFolder).attr('src', path + imgFolder);
			if(img.hasClass(clsMinusbottom)){
				img.removeClass(clsMinusbottom).addClass(clsPlusbottom);
				img.attr('src', path + imgPlusbottom);
			}
			if(img.hasClass(clsMinus)){
				img.removeClass(clsMinus).addClass(clsPlus);
				img.attr('src', path + imgPlus);
			}
		},
		eventOpenNode:function(nodeDom, img){
			var me = this,cfg = me.defConfig,
				path = cfg.path,
				clsNode = cfg.clsNode,
				clsOpen = cfg.clsOpen,
				clsClose = cfg.clsClose,
				clsClip = cfg.clsClip,
				clsFolder = cfg.clsFolder,
				clsMinusbottom = cfg.clsMinusbottom,
				clsPlusbottom = cfg.clsPlusbottom,
				clsMinus = cfg.clsMinus,
				clsPlus = cfg.clsPlus,
				imgPlus = cfg.imgPlus,
				imgMinus = cfg.imgMinus,
				imgMinusbottom = cfg.imgMinusbottom,
				imgPlusbottom = cfg.imgPlusbottom,
				imgFolder = cfg.imgFolder,
				imgFolderopen = cfg.imgFolderopen;
				
			nodeDom.addClass(clsOpen).removeClass(clsClose);
			nodeDom.children('.' + clsClip).show();
			nodeDom.children('.' + clsNode).children('.' + clsFolder).attr('src', path + imgFolderopen);
			if(img.hasClass(clsPlusbottom)){
				img.removeClass(clsPlusbottom).addClass(clsMinusbottom);
				img.attr('src', path + imgMinusbottom);
			}
			if(img.hasClass(clsPlus)){
				img.removeClass(clsPlus).addClass(clsMinus);
				img.attr('src', path + imgMinus);
			}
		},
		//生成所有节点
		rebuildNode:function(node){
			var me = this,tree = me.tree,i = 0,len = tree.length,arr = [];
			for(;i < len;i++){
				arr.push(me.buildHTML(tree[i]));
			}
			me.dom.html(arr.join(''));
		},
		buildHTML:function(node){
			var me = this,
				i = 0,
				len,
				str = '',
				strArr = [],
				tpl = me.getNodeTpl(),
				template = host.util.template,
				indentStr = '';
			
			//每个节点的html
			str = template(tpl, node);
			//对node的html进行一些处理
			str = me.formatNodeHTML(str, node);
			//str = str.replace(/<#=#indent##>/g, node['indent'].join('-'));
			str = str.replace(/<#=#indent##>/g, me.getIndentHTML(node));
			if(node['children']){
				strArr = [];
				for(i = 0,len = node['children'].length;i < len;i++){
					strArr.push(arguments.callee.call(me, node['children'][i]));
				}
			}
			str = str.replace(/<#=#childrenNodes##>/g, strArr.join(''));

			return str;
		},
		formatNodeHTML:function(str){
			return str;
		},
		getIndentHTML:function(node){
			var me = this,indent = node['indent'],i = 0,len = indent.length,strArr = [],img = '',action = '',cfg = me.defConfig,cls = '',
				clsIndent = cfg.clsIndent,
				path = cfg.path,
				actionOpen = cfg.actionOpen,
				clsMinus = cfg.clsMinus,
				clsMinusbottom = cfg.clsMinusbottom,
				clsContent = cfg.clsContent,
				clsFolder = cfg.clsFolder,
				imgMinus = cfg.imgMinus,
				imgJoin = cfg.imgJoin,
				imgLine = cfg.imgLine,
				imgMinusbottom = cfg.imgMinusbottom,
				imgJoinbottom = cfg.imgJoinbottom,
				imgEmpty = cfg.imgEmpty,
				imgFolderopen = cfg.imgFolderopen,
				imgContent = cfg.imgContent,
				isShowFolder = cfg.isShowFolder;
				
				
			for(;i < len;i++){
				if(indent[i] == 0){
					if(i == (len - 1)){
						if(node['children']){
							img = imgMinus;
							cls = clsMinus;
							action = actionOpen;
						}else{
							img = imgJoin;
						}
					}else{
						img = imgLine;
					}
				}else{
					if(i == (len - 1)){
						if(node['children']){
							img = imgMinusbottom;
							cls = clsMinusbottom;
							action = actionOpen;
						}else{
							img = imgJoinbottom;
						}
					}else{
						img = imgEmpty;
					}
				}
				strArr.push('<img data-action="'+ action +'" class="'+ cls +'" src="'+ path + img + '" />');
				if(isShowFolder && i == (len - 1)){
					if(node['children']){
						strArr.push('<img class="'+ clsFolder +'" src="'+ path + imgFolderopen +'" />');
					}else{
						strArr.push('<img class="'+ clsContent +'" src="'+ path + imgContent +'" />');
					}
				}
			}
			return strArr.join('');
		},
		//数据以id为key进行哈希映射
		reHasTreeData:function(){
			var me = this,i = 0,len;
			for(i = 0,len = me.data.length;i < len;i++){
				me.hasData[me.data[i]['id']] = me.data[i];
			}
			return me.hasData;
		},
		//将数据列表构建成树状，子元素存放在children属性中
		//按照id大小排列父子关系
		buildTreeData:function(data, tree, level){
			var me = this,
				tree = tree || me.getRootData(data),
				i = 0,
				len = tree.length,
				j = 0,
				len2,
				_level,
				children = [];
			
			for(i = 0;i < len;i++){
				_level = (typeof level != 'undefined') ? (level + 1) : 0;
				tree[i]['level'] = _level;
				children = me.getChildById(tree[i]['id']);
				
				
				if(me.tempLines.length < len){
					me.tempLines[_level] = 0;
				}
				if(i == (len - 1)){
					me.tempLines[_level] = 1;
				}else{
					me.tempLines[_level] = 0;
				}
				//console.log(_level);
				for(j = 0,len2 = me.tempLines.length;j < len2;j++){
					if(typeof me.tempLines[j] == 'undefined'){
						me.tempLines[j] = 0;
					}
				}
				//console.log('['+me.tempLines.join()+']' + tree[i]['text'] + _level + '-' +(i == (len - 1)));
				tree[i]['indent'] = me.tempLines.slice(0, _level + 1);
				//console.log('['+ tree[i]['indent'].join() +']' + tree[i]['text'] + _level + '-' +(i == (len - 1)));
				
				if(children.length > 0){
					tree[i]['children'] = children;
					arguments.callee.call(me, children, children, _level);
				}
			}
			return tree;
		},
		//获取直隶子节点
		getChildById:function(id){
			var me = this,data = me.data,i = 0,len = data.length,
				arr = [];
			for(;i < len;i++){
				if(data[i]['pid'] == id){
					arr.push(data[i]);
				}
			}
			return arr;
		},
		//获取顶级节点
		//当前按照id从小到大作为层级关系
		getRootData:function(data){
			var i = 0,len = data.length,minId,arr = [];
			minId = len > 0 ? data[0]['pid'] : minId;
			for(;i < len;i++){
				minId = minId < data[i]['pid'] ? minId : data[i]['pid'];
			}
			for(i = 0;i < len;i++){
				if(data[i]['pid'] == minId){
					arr.push(data[i]);
				}
			}
			return arr;
		},
		//格式化数据，如增加属性、排序等
		formatData:function(){
			return this.data;
		},
		//根据id获得节点数据
		getDataById:function(id){
			return this.hasData[id];
		},
		getNodeTpl:function(){
			return this.defConfig.nodeTpl;
		}
	
	};
	
	
	var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	host[name] = Main;
	
})(phoenix, "Tree", phoenix.Event, jQuery);