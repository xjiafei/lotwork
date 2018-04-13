
(function(host, Danshi, undefined){
	var defConfig = {
		name:'p3sanxing.zhixuan.p3danshi',
		//玩法提示
		tips: '五星直选单式玩法提示',
		//选号实例
		exampleTip: '这是单式弹出层提示',
		exampleText: '264<br />184<br />270<br />629<br />235'
		
	},
	Games = host.Games,
	P5 = Games.P5.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			//建立编辑器DOM
			//防止绑定事件失败加入定时器
			setTimeout(function(){
				me.initFrame();
			},25);
		}
	};
	
	
	//继承Danshi
	var Main = host.Class(pros, Danshi);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器上
	P5[defConfig.name] = new Main();
	
	
	
})(phoenix, phoenix.Games.P5.Danshi);

