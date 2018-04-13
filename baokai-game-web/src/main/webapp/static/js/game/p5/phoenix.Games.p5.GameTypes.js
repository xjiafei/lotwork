

(function(host, name, GameTypes, undefined){
	var defConfig = {
		navDom : '#J-play-select .play-select-title'
	},
	//渲染实例
	instance,
	//游戏实例
	Games = host.Games;
	
	//渲染方法
	var pros = {
		init:function(cfg){
			var me = this;
			
			setTimeout(function(){
				me.divideNav();
			},0);
		},
		//划分导航层级
		divideNav: function(){
			var me = this,
				navDom = $(me.defConfig.navDom);

			navDom.find('li.p3sanxing').before('<div class="superior pailie3">排列3：</div>');
			navDom.find('li.p5houer').before('<div class="superior pailie5">排列5：</div>');
		}

	};
	
	var Main = host.Class(pros, GameTypes);
		Main.defConfig = defConfig;
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host[name] = Main;
	
})(phoenix, "P5GameTypes", phoenix.GameTypes);










