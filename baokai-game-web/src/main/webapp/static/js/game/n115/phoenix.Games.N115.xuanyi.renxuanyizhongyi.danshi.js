
(function(host, Danshi, undefined){
	var defConfig = {
		name:'xuanyi.renxuanyizhongyi.danshi',
		//玩法提示
		tips: '选一任选一中一单式',
		//选号实例
		exampleTip: '选一任选一中一单式',
		//标准格式样本
		exampleText: '03<br />04<br />07 <br />09 <br />10'
	},
	Games = host.Games,
	N115 = Games.N115.getInstance();
	
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;
			//建立编辑器DOM
			//防止绑定事件失败加入定时器
			setTimeout(function(){
				me.initFrame();
			},25);
		},
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		}
	};
	
	
	//继承Danshi
	var Main = host.Class(pros, Danshi);
		Main.defConfig = defConfig;
	//将实例挂在游戏管理器上
	N115[defConfig.name] = new Main();
	
	
	
})(phoenix, phoenix.Games.N115.Danshi);

