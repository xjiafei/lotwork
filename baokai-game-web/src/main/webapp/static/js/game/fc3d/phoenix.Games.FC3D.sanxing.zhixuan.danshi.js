
(function(host, Danshi, undefined){
	var defConfig = {
		name:'sanxing.zhixuan.danshi',
		//玩法提示
		tips: '五星直选单式玩法提示',
		//选号实例
		exampleTip: '这是单式弹出层提示',
		exampleText: '123<br />233<br />235<br />678'	
	},
	Games = host.Games,
	FC3D = Games.FC3D.getInstance();
	
	
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
	FC3D[defConfig.name] = new Main();
	
	
	
})(phoenix, phoenix.Games.FC3D.Danshi);

