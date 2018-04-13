/*
** Author: Jay
** 04/24/2014
** How to use
var rotate = new phoenix.Rotate({
	// 旋转的步调
	angleStep: 6,
	// 初始旋转度数
	initAngle: 0,
	// 旋转的频率（定时器频率）
	frequency: 1000,
	// Rotate Dom
	rotateDom: '#second-hand'
});
// 指定旋转到某个度数
rotate.rotationTo(angle);
// 停止旋转
rotate.stop();
// 开始旋转
rotate.play();
*/

//Rotate 类
(function(host, name, Event, $, undefined){
	var defConfig = {
		// 旋转的步调
		angleStep: 6,
		// 初始旋转度数
		initAngle: 0,
		// 旋转的频率（定时器频率）
		frequency: 1000,
		// Rotate Dom
		rotateDom: '#second-hand'
	},
	// 内部旋转定时器
	rotationTimer,
	// 内部旋转角度计数
	angle = 0;
	
	var pros = {
		init: function(){
			this.angle = this.defConfig.initAngle;
			this.play();
		},
		rotation: function (){
			var me = this, cfg = me.defConfig;
			$(cfg.rotateDom).rotate({
				angle: me.angle,
				duration: cfg.frequency
			});
		},
		rotationTo: function(angle){
			this.angle = angle || this.angle;
			this.stop();
			this.rotation(this.angle);
			this.play();
		},
		play: function(){
			var me = this, cfg = this.defConfig, angle = me.angle;
			me.rotationTimer = setInterval(function(){
				angle += cfg.angleStep;
				me.rotation();
				return me.angle = angle;
			}, cfg.frequency);
		},
		stop: function(){
			clearInterval(this.rotationTimer);
		}
	};

	
	Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	
	host[name] = Main;
	/*var Main = host.Class(pros, Event);
		Main.defConfig = defConfig;
	
	// 唯一单例
	host.util[name] = function(){
		return host[name].getInstance();
	};
	
	host.util[name].getInstance = function(){
		return instance || new Main(defConfig);	
	};*/
	
})(phoenix, 'Rotate', phoenix.Event, jQuery);










