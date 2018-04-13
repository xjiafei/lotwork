

(function(host, name, Game, undefined){
	var defConfig = {
		//游戏名称
		name:'LLN115',
		basePath:staticFileContextPath + '/static/js/game/lln115/lln115/',
		baseNamespace:'phoenix.Games.LLN115.' 
		
	},
	Games = host.Games,
	instance;
	
	var pros = {
		init:function(){
			var me = this;
			
			//初始化事件放在子类中执行，以确保dom元素加载完毕
			me.eventProxy();
			//获取服务器端配置数据
			me.getDynamicConfig();
		},
		getGameConfig:function(){
			return host.Games.LLN115.Config;
		},
		/*配置奖金组流程
		  1：查询cookice中actualBonusSet，用户在登录时创建此cookice值，
		  2：actualBonusSet为空时，执行查询可配置奖金组，（注：代理有多个奖金组可选，玩家单一不用再分配）
		  3：奖金组配置后刷新页面
		*/
		bonusGroupProce : function(){
			//匹配当前彩种是否有奖金组			
			var me = this,
				mask = phoenix.Mask.getInstance(),								
				Msg = Games.getCurrentGameMessage(),
				htlms = '',
				userid , 
				strArr = {},
				indexStr,
				isConBonus = false ,  //是否有奖金组
				gameTypeC =  Games.getCurrentGame().getName(),
				_lotteryId = Games.getCurrentGame().getGameConfig().getInstance().defConfig["lotteryId"],//当前彩种ID
				_awardGroups = Games.getCurrentGame().getGameConfig().getInstance().defConfig["awardGroups"],//当前奖金组状态
				_userLvl = Games.getCurrentGame().getGameConfig().getInstance().defConfig["userLvl"];	
			
			
			//检查初始化时此彩种是否有分配奖金组
			if(typeof(_awardGroups) != 'undefined'){
				_awardGroups = _awardGroups == '' ? '' : _awardGroups; 
				if(_awardGroups.length > 0){
					$.each(_awardGroups, function (i){
						if(_awardGroups[i].betType == 1){						
							isConBonus = true ;
							return;
						}					
					}); 
				}				
			}			
						
			//判断是否为总代
			if(_userLvl == 0){
				setTimeout(function(){
					Msg.show({
						mask:true,
						title:'温馨提示',
						content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>您目前是总代，没有权限投注</h4></div>",							
						confirmIsShow:true,
						hideClose: true,
						confirmFun:function(){						
							Games.getCurrentGameMessage().hide();	
							window.location.href = _logOut + '/index';
						}
					});
					return;
				}, 3000);	
				
				
			}else if(!isConBonus){	
				//根据彩种及用户查询可分配奖金组			
				$.ajax({
					url:Games.getCurrentGame().getGameConfig().getInstance().getQueryGameUserAwardGroupByLotteryIdUrl(), 
					dataType:'json',
					async:false,
					success:function(data){
						if(data.length > 0){
							$.each(data, function (i){
								if(data[i].lotteryId == _lotteryId){							
									htlms += '<label><input type="radio" class="radio" name="radionGourp" pro_awardGroupId='+ data[i].awardGroupId +' pro_lotterySeriesCode = '+ data[i].lotterySeriesCode+'>'+ data[i].awardName +'</label> &nbsp;';	
								}					
							}); 
							
							me.giveBonusGoup(htlms);	
							//Games.getCurrentGame().getCurrentGameMethod()	
						}else{//如果查询上级没有给可分配奖金组选择直接跳回用户中心首面
							setTimeout(function(){
								Msg.show({
									mask:true,
									title:'温馨提示',
									content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>没分配奖金组，请联系上一级或客服人员</h4></div>",							
									confirmIsShow:true,	
									hideClose: true,									
									confirmFun:function(){
										window.location.href = _logOut + '/index';
										//Games.getCurrentGameMessage().hide();	
									}
								});
								return;
							}, 3000);								
						}
					}
				});			
				
			}
		},		
		//进行弹窗奖金组配置
		giveBonusGoup:function(htlms){		
			var mask = phoenix.Mask.getInstance(),				
				Msg = Games.getCurrentGameMessage();
				setTimeout(function(){
					Msg.show({				
						mask:true,				
						title:'温馨提示',				
						content:'<div class="text-center"><p class="text-title">请选择一个奖金组，便于您投注时使用。</p><p class="radio-list"> ' + htlms+'</p><p class="text-note">(注：您投注时使用的奖金组一经设定，不可修改。<a href="'+_logOut+'/applycenter/querybonusdetails/" target="_Blank">查看奖金详情</a>)</p></div>',
						confirmIsShow:true,				
						cancelIsShow:true,
						cancelText : '返回首页',
						closeIsShow :false,
						hideClose: true,					
						confirmFun:function(){	
							//确认提交选择奖金组	
							var pro_awardgroupid = $("input:radio[name='radionGourp']:checked").attr('pro_awardgroupid');
								
							if(pro_awardgroupid == '' || pro_awardgroupid == undefined){ return false;}
							$.ajax({
								url:Games.getCurrentGame().getGameConfig().getInstance().getSaveProxyBetGameAwardGroupUrl() + '?awardGroupId='+pro_awardgroupid,
								dataType:'json',							
								//contentType: 'application/json',
								async:false,
								success:function(data){
									if(data['status'] == '1'){			
										Msg.show({
											mask:true,				
											title:'温馨提示',				
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-success"></i><h4 class="pop-text">奖金组配置成功!	<br>刷新当前游戏页面。</h4></div></div>',
											closeIsShow :true,	
											hideClose: true,
											closeFun:function(){ 
												 location.reload();                  
												Games.getCurrentGameMessage().hide();				
											}	
										});
									}else if(data['status'] == '2'){
										Msg.show({
											mask:true,				
											title:'温馨提示',				
											content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您已经选择过奖金组!<br>刷新当前游戏页面。</h4></div></div>',
											closeIsShow :true,	
											hideClose: true,
											closeFun:function(){ 
												 location.reload();
											}	
										});
									}else{
										Msg.show({
											mask:true,
											title:'温馨提示',
											content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>奖金组配置提交失败,<br />请检查网络并重新提交！</h4></div>",
											cancelIsShow:true,
											hideClose: true,
											cancelFun:function(){
												Games.getCurrentGameMessage().hide();	
											}
										});
									}	
									
								},
								error: function() {
									Msg.show({
										mask:true,
										title:'温馨提示',
										content:"<div class='pop-title'><i class='ico-error'></i><h4 class='pop-text'>奖金组配置提交失败,<br />请检查网络并重新提交！</h4></div>",
										cancelIsShow:true,
										hideClose: true,
										cancelFun:function(){
											Games.getCurrentGameMessage().hide();	
										}
									});
									
								} 
							});
						},				
						cancelFun:function(){        
							window.location.href = _logOut + '/index';           
							Games.getCurrentGameMessage().hide();				
						}					
					});	
				}, 3000);					
			
		},	
		//（现不用拆单提交）
		editSubmitData:function(data){
			var me = this,list = data['balls'],i = 0,len = list.length,type = '',singleList = [],balls = [],
				temp,len2,j;
			for(;i < len; i++){
				type = list[i]['type'];				
					balls.push(list[i]);				
			}
			balls = balls.concat(singleList);
			
			data['balls'] = balls;
			
			return data;
		},
		//整个彩种停售弹层（，玩法停售直接隐藏）
		isLotteryStopSale:function(){	
			var isture = Games.getCurrentGame().getGameConfig().getInstance().defConfig["isLotteryStopSale"];	
			if(isture){
				setTimeout(function(){
					phoenix.Games.getCurrentGameMessage().show({
					   type : 'lotteryClose',
					   hideClose: true,
					   data : {
						   tplData:{
								//开始购买时间
								msg : '您好，当前彩种已停售!',
								//提示彩票种类
								lotteryType : ' <dd><span class="pic"><img src="'+staticFileContextPath+'/static/images/game/tancenglogo/bjkl8.jpg"/></span><a class="btn" href="/gameBet/bjkl8">去投注</a></dd><dd><span class="pic"><img src="'+staticFileContextPath+'/static/images/game/tancenglogo/fc3d.jpg"/></span><a class="btn" href="/gameBet/fc3d">去投注</a></dd>'
						   }
					   }
					});
				}, 3000);			 
				
			}	
		}
	
	};
	
	var Main = host.Class(pros, Game);
		Main.defConfig = defConfig;
		//游戏控制单例
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host.Games[name] = Main;

	
})(phoenix, "LLN115", phoenix.Game);













(function(host, name, message, undefined){
	var defConfig = {
		
	},
	Games = host.Games,
	instance;

	var pros = {
		init: function(cfg){
			var me = this;
			Games.setCurrentGameMessage(me);
		}
	};
	
	var Main = host.Class(pros, message);
		Main.defConfig = defConfig;
		//游戏控制单例
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host.Games.LLN115[name] = Main;
	
})(phoenix, "Message", phoenix.GameMessage);












(function(host, name, message, undefined){
	var defConfig = {
		buttonDom : '.lottery-video',
		toolBarDomText : '.controlBar',
		loadingDom: '#J-loading-video',
		videoContainerDom : '#J-video-area',
		videoHtml : '<div class="video-action" style="display:block;"><span data-action class="refresh">refresh</span><span data-action class="close">close</span></div>' +
					'<div class="controlBar"></div>' +
					'<object width="100%" height="400px;">' +
					'<param name="movie" value="'+staticFileContextPath +'/static/js/bet/StrobeMediaPlayback.swf">' + 
					'<param name="flashvars" value="{{flashvars}}&autoPlay=true&initialBufferTime=0">' + 
					'<param name="allowFullScreen" value="true">' + 
					'<param name="allowscriptaccess" value="always">' + 
					'<param name="wmode" value="transparent"></param>' + 
					'<embed width="100%" height="100%" wmode="transparent" src="'+staticFileContextPath + '/static/js/bet/StrobeMediaPlayback.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" flashvars="{{flashvars}}&autoPlay=true&initialBufferTime=0"></object>'
	},
	Games = host.Games,
	animateTime = null,
	//视频周期计时
	roundNum = 0,
	//存储视频时间节点
	timeArray = [],
	instance;
	// var flashvars = {
	// 	'file':'streamer',
	// 	'streamer':'rtmp://fms2.lucky188.cn/live/livestream',
	// 	'type':'rtmp',
	// 	'rtmp.subscribe':'true',
	// 	'autostart':'true',
	// 	'controlbar':'none'
	// };
	// var params = {
	// 	'allowfullscreen':'true',
 //        'allowscriptaccess':'always',
 //        'bgcolor':'#000',
 //        'wmode':"transparent"
	// };
	// var attributes = {
	// 	'id':'player',
 //        'name':'player'
	// };

	//flashObject
	//var swfobject=function(){var D="undefined",r="object",S="Shockwave Flash",W="ShockwaveFlash.ShockwaveFlash",q="application/x-shockwave-flash",R="SWFObjectExprInst",x="onreadystatechange",O=window,j=document,t=navigator,T=false,U=[h],o=[],N=[],I=[],l,Q,E,B,J=false,a=false,n,G,m=true,M=function(){var aa=typeof j.getElementById!=D&&typeof j.getElementsByTagName!=D&&typeof j.createElement!=D,ah=t.userAgent.toLowerCase(),Y=t.platform.toLowerCase(),ae=Y?/win/.test(Y):/win/.test(ah),ac=Y?/mac/.test(Y):/mac/.test(ah),af=/webkit/.test(ah)?parseFloat(ah.replace(/^.*webkit\/(\d+(\.\d+)?).*$/,"$1")):false,X=!+"\v1",ag=[0,0,0],ab=null;if(typeof t.plugins!=D&&typeof t.plugins[S]==r){ab=t.plugins[S].description;if(ab&&!(typeof t.mimeTypes!=D&&t.mimeTypes[q]&&!t.mimeTypes[q].enabledPlugin)){T=true;X=false;ab=ab.replace(/^.*\s+(\S+\s+\S+$)/,"$1");ag[0]=parseInt(ab.replace(/^(.*)\..*$/,"$1"),10);ag[1]=parseInt(ab.replace(/^.*\.(.*)\s.*$/,"$1"),10);ag[2]=/[a-zA-Z]/.test(ab)?parseInt(ab.replace(/^.*[a-zA-Z]+(.*)$/,"$1"),10):0}}else{if(typeof O.ActiveXObject!=D){try{var ad=new ActiveXObject(W);if(ad){ab=ad.GetVariable("$version");if(ab){X=true;ab=ab.split(" ")[1].split(",");ag=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}}catch(Z){}}}return{w3:aa,pv:ag,wk:af,ie:X,win:ae,mac:ac}}(),k=function(){if(!M.w3){return}if((typeof j.readyState!=D&&j.readyState=="complete")||(typeof j.readyState==D&&(j.getElementsByTagName("body")[0]||j.body))){f()}if(!J){if(typeof j.addEventListener!=D){j.addEventListener("DOMContentLoaded",f,false)}if(M.ie&&M.win){j.attachEvent(x,function(){if(j.readyState=="complete"){j.detachEvent(x,arguments.callee);f()}});if(O==top){(function(){if(J){return}try{j.documentElement.doScroll("left")}catch(X){setTimeout(arguments.callee,0);return}f()})()}}if(M.wk){(function(){if(J){return}if(!/loaded|complete/.test(j.readyState)){setTimeout(arguments.callee,0);return}f()})()}s(f)}}();function f(){if(J){return}try{var Z=j.getElementsByTagName("body")[0].appendChild(C("span"));Z.parentNode.removeChild(Z)}catch(aa){return}J=true;var X=U.length;for(var Y=0;Y<X;Y++){U[Y]()}}function K(X){if(J){X()}else{U[U.length]=X}}function s(Y){if(typeof O.addEventListener!=D){O.addEventListener("load",Y,false)}else{if(typeof j.addEventListener!=D){j.addEventListener("load",Y,false)}else{if(typeof O.attachEvent!=D){i(O,"onload",Y)}else{if(typeof O.onload=="function"){var X=O.onload;O.onload=function(){X();Y()}}else{O.onload=Y}}}}}function h(){if(T){V()}else{H()}}function V(){var X=j.getElementsByTagName("body")[0];var aa=C(r);aa.setAttribute("type",q);var Z=X.appendChild(aa);if(Z){var Y=0;(function(){if(typeof Z.GetVariable!=D){var ab=Z.GetVariable("$version");if(ab){ab=ab.split(" ")[1].split(",");M.pv=[parseInt(ab[0],10),parseInt(ab[1],10),parseInt(ab[2],10)]}}else{if(Y<10){Y++;setTimeout(arguments.callee,10);return}}X.removeChild(aa);Z=null;H()})()}else{H()}}function H(){var ag=o.length;if(ag>0){for(var af=0;af<ag;af++){var Y=o[af].id;var ab=o[af].callbackFn;var aa={success:false,id:Y};if(M.pv[0]>0){var ae=c(Y);if(ae){if(F(o[af].swfVersion)&&!(M.wk&&M.wk<312)){w(Y,true);if(ab){aa.success=true;aa.ref=z(Y);ab(aa)}}else{if(o[af].expressInstall&&A()){var ai={};ai.data=o[af].expressInstall;ai.width=ae.getAttribute("width")||"0";ai.height=ae.getAttribute("height")||"0";if(ae.getAttribute("class")){ai.styleclass=ae.getAttribute("class")}if(ae.getAttribute("align")){ai.align=ae.getAttribute("align")}var ah={};var X=ae.getElementsByTagName("param");var ac=X.length;for(var ad=0;ad<ac;ad++){if(X[ad].getAttribute("name").toLowerCase()!="movie"){ah[X[ad].getAttribute("name")]=X[ad].getAttribute("value")}}P(ai,ah,Y,ab)}else{p(ae);if(ab){ab(aa)}}}}}else{w(Y,true);if(ab){var Z=z(Y);if(Z&&typeof Z.SetVariable!=D){aa.success=true;aa.ref=Z}ab(aa)}}}}}function z(aa){var X=null;var Y=c(aa);if(Y&&Y.nodeName=="OBJECT"){if(typeof Y.SetVariable!=D){X=Y}else{var Z=Y.getElementsByTagName(r)[0];if(Z){X=Z}}}return X}function A(){return !a&&F("6.0.65")&&(M.win||M.mac)&&!(M.wk&&M.wk<312)}function P(aa,ab,X,Z){a=true;E=Z||null;B={success:false,id:X};var ae=c(X);if(ae){if(ae.nodeName=="OBJECT"){l=g(ae);Q=null}else{l=ae;Q=X}aa.id=R;if(typeof aa.width==D||(!/%$/.test(aa.width)&&parseInt(aa.width,10)<310)){aa.width="310"}if(typeof aa.height==D||(!/%$/.test(aa.height)&&parseInt(aa.height,10)<137)){aa.height="137"}j.title=j.title.slice(0,47)+" - Flash Player Installation";var ad=M.ie&&M.win?"ActiveX":"PlugIn",ac="MMredirectURL="+O.location.toString().replace(/&/g,"%26")+"&MMplayerType="+ad+"&MMdoctitle="+j.title;if(typeof ab.flashvars!=D){ab.flashvars+="&"+ac}else{ab.flashvars=ac}if(M.ie&&M.win&&ae.readyState!=4){var Y=C("div");X+="SWFObjectNew";Y.setAttribute("id",X);ae.parentNode.insertBefore(Y,ae);ae.style.display="none";(function(){if(ae.readyState==4){ae.parentNode.removeChild(ae)}else{setTimeout(arguments.callee,10)}})()}u(aa,ab,X)}}function p(Y){if(M.ie&&M.win&&Y.readyState!=4){var X=C("div");Y.parentNode.insertBefore(X,Y);X.parentNode.replaceChild(g(Y),X);Y.style.display="none";(function(){if(Y.readyState==4){Y.parentNode.removeChild(Y)}else{setTimeout(arguments.callee,10)}})()}else{Y.parentNode.replaceChild(g(Y),Y)}}function g(ab){var aa=C("div");if(M.win&&M.ie){aa.innerHTML=ab.innerHTML}else{var Y=ab.getElementsByTagName(r)[0];if(Y){var ad=Y.childNodes;if(ad){var X=ad.length;for(var Z=0;Z<X;Z++){if(!(ad[Z].nodeType==1&&ad[Z].nodeName=="PARAM")&&!(ad[Z].nodeType==8)){aa.appendChild(ad[Z].cloneNode(true))}}}}}return aa}function u(ai,ag,Y){var X,aa=c(Y);if(M.wk&&M.wk<312){return X}if(aa){if(typeof ai.id==D){ai.id=Y}if(M.ie&&M.win){var ah="";for(var ae in ai){if(ai[ae]!=Object.prototype[ae]){if(ae.toLowerCase()=="data"){ag.movie=ai[ae]}else{if(ae.toLowerCase()=="styleclass"){ah+=' class="'+ai[ae]+'"'}else{if(ae.toLowerCase()!="classid"){ah+=" "+ae+'="'+ai[ae]+'"'}}}}}var af="";for(var ad in ag){if(ag[ad]!=Object.prototype[ad]){af+='<param name="'+ad+'" value="'+ag[ad]+'" />'}}aa.outerHTML='<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"'+ah+">"+af+"</object>";N[N.length]=ai.id;X=c(ai.id)}else{var Z=C(r);Z.setAttribute("type",q);for(var ac in ai){if(ai[ac]!=Object.prototype[ac]){if(ac.toLowerCase()=="styleclass"){Z.setAttribute("class",ai[ac])}else{if(ac.toLowerCase()!="classid"){Z.setAttribute(ac,ai[ac])}}}}for(var ab in ag){if(ag[ab]!=Object.prototype[ab]&&ab.toLowerCase()!="movie"){e(Z,ab,ag[ab])}}aa.parentNode.replaceChild(Z,aa);X=Z}}return X}function e(Z,X,Y){var aa=C("param");aa.setAttribute("name",X);aa.setAttribute("value",Y);Z.appendChild(aa)}function y(Y){var X=c(Y);if(X&&X.nodeName=="OBJECT"){if(M.ie&&M.win){X.style.display="none";(function(){if(X.readyState==4){b(Y)}else{setTimeout(arguments.callee,10)}})()}else{X.parentNode.removeChild(X)}}}function b(Z){var Y=c(Z);if(Y){for(var X in Y){if(typeof Y[X]=="function"){Y[X]=null}}Y.parentNode.removeChild(Y)}}function c(Z){var X=null;try{X=j.getElementById(Z)}catch(Y){}return X}function C(X){return j.createElement(X)}function i(Z,X,Y){Z.attachEvent(X,Y);I[I.length]=[Z,X,Y]}function F(Z){var Y=M.pv,X=Z.split(".");X[0]=parseInt(X[0],10);X[1]=parseInt(X[1],10)||0;X[2]=parseInt(X[2],10)||0;return(Y[0]>X[0]||(Y[0]==X[0]&&Y[1]>X[1])||(Y[0]==X[0]&&Y[1]==X[1]&&Y[2]>=X[2]))?true:false}function v(ac,Y,ad,ab){if(M.ie&&M.mac){return}var aa=j.getElementsByTagName("head")[0];if(!aa){return}var X=(ad&&typeof ad=="string")?ad:"screen";if(ab){n=null;G=null}if(!n||G!=X){var Z=C("style");Z.setAttribute("type","text/css");Z.setAttribute("media",X);n=aa.appendChild(Z);if(M.ie&&M.win&&typeof j.styleSheets!=D&&j.styleSheets.length>0){n=j.styleSheets[j.styleSheets.length-1]}G=X}if(M.ie&&M.win){if(n&&typeof n.addRule==r){n.addRule(ac,Y)}}else{if(n&&typeof j.createTextNode!=D){n.appendChild(j.createTextNode(ac+" {"+Y+"}"))}}}function w(Z,X){if(!m){return}var Y=X?"visible":"hidden";if(J&&c(Z)){c(Z).style.visibility=Y}else{v("#"+Z,"visibility:"+Y)}}function L(Y){var Z=/[\\\"<>\.;]/;var X=Z.exec(Y)!=null;return X&&typeof encodeURIComponent!=D?encodeURIComponent(Y):Y}var d=function(){if(M.ie&&M.win){window.attachEvent("onunload",function(){var ac=I.length;for(var ab=0;ab<ac;ab++){I[ab][0].detachEvent(I[ab][1],I[ab][2])}var Z=N.length;for(var aa=0;aa<Z;aa++){y(N[aa])}for(var Y in M){M[Y]=null}M=null;for(var X in swfobject){swfobject[X]=null}swfobject=null})}}();return{registerObject:function(ab,X,aa,Z){if(M.w3&&ab&&X){var Y={};Y.id=ab;Y.swfVersion=X;Y.expressInstall=aa;Y.callbackFn=Z;o[o.length]=Y;w(ab,false)}else{if(Z){Z({success:false,id:ab})}}},getObjectById:function(X){if(M.w3){return z(X)}},embedSWF:function(ab,ah,ae,ag,Y,aa,Z,ad,af,ac){var X={success:false,id:ah};if(M.w3&&!(M.wk&&M.wk<312)&&ab&&ah&&ae&&ag&&Y){w(ah,false);K(function(){ae+="";ag+="";var aj={};if(af&&typeof af===r){for(var al in af){aj[al]=af[al]}}aj.data=ab;aj.width=ae;aj.height=ag;var am={};if(ad&&typeof ad===r){for(var ak in ad){am[ak]=ad[ak]}}if(Z&&typeof Z===r){for(var ai in Z){if(typeof am.flashvars!=D){am.flashvars+="&"+ai+"="+Z[ai]}else{am.flashvars=ai+"="+Z[ai]}}}if(F(Y)){var an=u(aj,am,ah);if(aj.id==ah){w(ah,true)}X.success=true;X.ref=an}else{if(aa&&A()){aj.data=aa;P(aj,am,ah,ac);return}else{w(ah,true)}}if(ac){ac(X)}})}else{if(ac){ac(X)}}},switchOffAutoHideShow:function(){m=false},ua:M,getFlashPlayerVersion:function(){return{major:M.pv[0],minor:M.pv[1],release:M.pv[2]}},hasFlashPlayerVersion:F,createSWF:function(Z,Y,X){if(M.w3){return u(Z,Y,X)}else{return undefined}},showExpressInstall:function(Z,aa,X,Y){if(M.w3&&A()){P(Z,aa,X,Y)}},removeSWF:function(X){if(M.w3){y(X)}},createCSS:function(aa,Z,Y,X){if(M.w3){v(aa,Z,Y,X)}},addDomLoadEvent:K,addLoadEvent:s,getQueryParamValue:function(aa){var Z=j.location.search||j.location.hash;if(Z){if(/\?/.test(Z)){Z=Z.split("?")[1]}if(aa==null){return L(Z)}var Y=Z.split("&");for(var X=0;X<Y.length;X++){if(Y[X].substring(0,Y[X].indexOf("="))==aa){return L(Y[X].substring((Y[X].indexOf("=")+1)))}}}return""},expressInstallCallback:function(){if(a){var X=c(R);if(X&&l){X.parentNode.replaceChild(l,X);if(Q){w(Q,true);if(M.ie&&M.win){l.style.display="block"}}if(E){E(B)}}a=false}}}}();
	"undefined"==typeof jwplayer&&(jwplayer=function(d){if(jwplayer.api){return jwplayer.api.selectPlayer(d)}},jwplayer.version="6.6.3896",jwplayer.vid=document.createElement("video"),jwplayer.audio=document.createElement("audio"),jwplayer.source=document.createElement("source"),function(d){function a(b){return function(){return c(b)}}function k(b){return function(){b("Error loading file")}}function f(m,a,e,g){return function(){try{var c=m.responseXML;if(c&&c.firstChild){return e(m)}}catch(j){}(c=b.parseXML(m.responseText))&&c.firstChild?(m=b.extend({},m,{responseXML:c}),e(m)):g&&g(m.responseText?"Invalid XML":a)}}var h=document,e=window,j=navigator,b=d.utils=function(){};b.exists=function(b){switch(typeof b){case"string":return 0<b.length;case"object":return null!==b;case"undefined":return !1}return !0};b.styleDimension=function(b){return b+(0<b.toString().indexOf("%")?"":"px")};b.getAbsolutePath=function(a,e){b.exists(e)||(e=h.location.href);if(b.exists(a)){var c;if(b.exists(a)){c=a.indexOf("://");var g=a.indexOf("?");c=0<c&&(0>g||g>c)}else{c=void 0}if(c){return a}c=e.substring(0,e.indexOf("://")+3);var g=e.substring(c.length,e.indexOf("/",c.length+1)),j;0===a.indexOf("/")?j=a.split("/"):(j=e.split("?")[0],j=j.substring(c.length+g.length+1,j.lastIndexOf("/")),j=j.split("/").concat(a.split("/")));for(var f=[],t=0;t<j.length;t++){j[t]&&(b.exists(j[t])&&"."!=j[t])&&(".."==j[t]?f.pop():f.push(j[t]))}return c+g+"/"+f.join("/")}};b.extend=function(){var a=b.extend.arguments;if(1<a.length){for(var e=1;e<a.length;e++){b.foreach(a[e],function(e,g){try{b.exists(g)&&(a[0][e]=g)}catch(c){}})}return a[0]}return null};b.log=function(b,a){"undefined"!=typeof console&&"undefined"!=typeof console.log&&(a?console.log(b,a):console.log(b))};var c=b.userAgentMatch=function(b){return null!==j.userAgent.toLowerCase().match(b)};b.isIE=a(/msie/i);b.isFF=a(/firefox/i);b.isChrome=a(/chrome/i);b.isIOS=a(/iP(hone|ad|od)/i);b.isIPod=a(/iP(hone|od)/i);b.isIPad=a(/iPad/i);b.isSafari602=a(/Macintosh.*Mac OS X 10_8.*6\.0\.\d* Safari/i);b.isSafari=function(){return c(/safari/i)&&!c(/chrome/i)&&!c(/chromium/i)&&!c(/android/i)};b.isAndroid=function(b){return b?c(RegExp("android.*"+b,"i")):c(/android/i)};b.isMobile=function(){return b.isIOS()||b.isAndroid()};b.saveCookie=function(b,a){h.cookie="jwplayer."+b+"\x3d"+a+"; path\x3d/"};b.getCookies=function(){for(var b={},a=h.cookie.split("; "),e=0;e<a.length;e++){var g=a[e].split("\x3d");0==g[0].indexOf("jwplayer.")&&(b[g[0].substring(9,g[0].length)]=g[1])}return b};b.typeOf=function(b){var a=typeof b;return"object"===a?!b?"null":b instanceof Array?"array":a:a};b.translateEventResponse=function(a,e){var c=b.extend({},e);a==d.events.JWPLAYER_FULLSCREEN&&!c.fullscreen?(c.fullscreen="true"==c.message?!0:!1,delete c.message):"object"==typeof c.data?(c=b.extend(c,c.data),delete c.data):"object"==typeof c.metadata&&b.deepReplaceKeyName(c.metadata,["__dot__","__spc__","__dsh__","__default__"],["."," ","-","default"]);b.foreach(["position","duration","offset"],function(b,a){c[a]&&(c[a]=Math.round(1000*c[a])/1000)});return c};b.flashVersion=function(){if(b.isAndroid()){return 0}var a=j.plugins,c;try{if("undefined"!==a&&(c=a["Shockwave Flash"])){return parseInt(c.description.replace(/\D+(\d+)\..*/,"$1"))}}catch(f){}if("undefined"!=typeof e.ActiveXObject){try{if(c=new ActiveXObject("ShockwaveFlash.ShockwaveFlash")){return parseInt(c.GetVariable("$version").split(" ")[1].split(",")[0])}}catch(g){}}return 0};b.getScriptPath=function(b){for(var a=h.getElementsByTagName("script"),c=0;c<a.length;c++){var g=a[c].src;if(g&&0<=g.indexOf(b)){return g.substr(0,g.indexOf(b))}}return""};b.deepReplaceKeyName=function(a,c,e){switch(d.utils.typeOf(a)){case"array":for(var g=0;g<a.length;g++){a[g]=d.utils.deepReplaceKeyName(a[g],c,e)}break;case"object":b.foreach(a,function(b,g){var j;if(c instanceof Array&&e instanceof Array){if(c.length!=e.length){return}j=c}else{j=[c]}for(var f=b,h=0;h<j.length;h++){f=f.replace(RegExp(c[h],"g"),e[h])}a[f]=d.utils.deepReplaceKeyName(g,c,e);b!=f&&delete a[b]})}return a};var n=b.pluginPathType={ABSOLUTE:0,RELATIVE:1,CDN:2};b.getPluginPathType=function(a){if("string"==typeof a){a=a.split("?")[0];var c=a.indexOf("://");if(0<c){return n.ABSOLUTE}var e=a.indexOf("/");a=b.extension(a);return 0>c&&0>e&&(!a||!isNaN(a))?n.CDN:n.RELATIVE}};b.getPluginName=function(b){return b.replace(/^(.*\/)?([^-]*)-?.*\.(swf|js)$/,"$2")};b.getPluginVersion=function(b){return b.replace(/[^-]*-?([^\.]*).*$/,"$1")};b.isYouTube=function(b){return/^(http|\/\/).*(youtube\.com|youtu\.be)\/.+/.test(b)};b.youTubeID=function(b){try{return/v[=\/]([^?&]*)|youtu\.be\/([^?]*)|^([\w-]*)$/i.exec(b).slice(1).join("").replace("?","")}catch(a){return""}};b.isRtmp=function(b,a){return 0==b.indexOf("rtmp")||"rtmp"==a};b.foreach=function(a,c){var e,g;for(e in a){"function"==b.typeOf(a.hasOwnProperty)?a.hasOwnProperty(e)&&(g=a[e],c(e,g)):(g=a[e],c(e,g))}};b.isHTTPS=function(){return 0==e.location.href.indexOf("https")};b.repo=function(){var a="http://p.jwpcdn.com/"+d.version.split(/\W/).splice(0,2).join("/")+"/";try{b.isHTTPS()&&(a=a.replace("http://","https://ssl."))}catch(c){}return a};b.ajax=function(a,c,j){var g;0<a.indexOf("#")&&(a=a.replace(/#.*$/,""));var h;h=(h=a)&&0<=h.indexOf("://")&&h.split("/")[2]!=e.location.href.split("/")[2]?!0:!1;if(h&&b.exists(e.XDomainRequest)){g=new XDomainRequest,g.onload=f(g,a,c,j),g.onerror=k(j,a,g)}else{if(b.exists(e.XMLHttpRequest)){var d=g=new XMLHttpRequest,t=a;g.onreadystatechange=function(){if(4===d.readyState){switch(d.status){case 200:f(d,t,c,j)();break;case 404:j("File not found")}}};g.onerror=k(j,a)}else{j&&j()}}try{g.open("GET",a,!0),g.send(null)}catch(n){j&&j(a)}return g};b.parseXML=function(b){try{var a;if(e.DOMParser){a=(new DOMParser).parseFromString(b,"text/xml");try{if("parsererror"==a.childNodes[0].firstChild.nodeName){return}}catch(c){}}else{a=new ActiveXObject("Microsoft.XMLDOM"),a.async="false",a.loadXML(b)}return a}catch(g){}};b.filterPlaylist=function(a,c){for(var e=[],g=0;g<a.length;g++){var j=b.extend({},a[g]);j.sources=b.filterSources(j.sources);if(0<j.sources.length){for(var f=0;f<j.sources.length;f++){var h=j.sources[f];h.label||(h.label=f.toString())}e.push(j)}}if(c&&0==e.length){for(g=0;g<a.length;g++){if(j=b.extend({},a[g]),j.sources=b.filterSources(j.sources,!0),0<j.sources.length){for(f=0;f<j.sources.length;f++){h=j.sources[f],h.label||(h.label=f.toString())}e.push(j)}}}return e};b.filterSources=function(a,c){var e,g,j=b.extensionmap;if(a){g=[];for(var f=0;f<a.length;f++){var h=a[f].type,n=a[f].file;n&&n.trim&&(n=n.trim());h||(h=j.extType(b.extension(n)),a[f].type=h);c?d.embed.flashCanPlay(n,h)&&(e||(e=h),h==e&&g.push(b.extend({},a[f]))):b.canPlayHTML5(h)&&(e||(e=h),h==e&&g.push(b.extend({},a[f])))}}return g};b.canPlayHTML5=function(a){if(b.isAndroid()&&("hls"==a||"m3u"==a||"m3u8"==a)){return !1}a=b.extensionmap.types[a];return !!a&&!!d.vid.canPlayType&&d.vid.canPlayType(a)};b.seconds=function(a){a=a.replace(",",".");var b=a.split(":"),c=0;"s"==a.substr(-1)?c=Number(a.substr(0,a.length-1)):"m"==a.substr(-1)?c=60*Number(a.substr(0,a.length-1)):"h"==a.substr(-1)?c=3600*Number(a.substr(0,a.length-1)):1<b.length?(c=Number(b[b.length-1]),c+=60*Number(b[b.length-2]),3==b.length&&(c+=3600*Number(b[b.length-3]))):c=Number(a);return c};b.serialize=function(a){return null==a?null:"true"==a.toString().toLowerCase()?!0:"false"==a.toString().toLowerCase()?!1:isNaN(Number(a))||5<a.length||0==a.length?a:Number(a)}}(jwplayer),function(d){var a="video/",k=d.foreach,f={mp4:a+"mp4",vorbis:"audio/ogg",ogg:a+"ogg",webm:a+"webm",aac:"audio/mp4",mp3:"audio/mpeg",hls:"application/vnd.apple.mpegurl"},h={mp4:f.mp4,f4v:f.mp4,m4v:f.mp4,mov:f.mp4,m4a:f.aac,f4a:f.aac,aac:f.aac,mp3:f.mp3,ogv:f.ogg,ogg:f.vorbis,oga:f.vorbis,webm:f.webm,m3u8:f.hls,hls:f.hls},a="video",a={flv:a,f4v:a,mov:a,m4a:a,m4v:a,mp4:a,aac:a,f4a:a,mp3:"sound",smil:"rtmp",m3u8:"hls",hls:"hls"},e=d.extensionmap={};k(h,function(a,b){e[a]={html5:b}});k(a,function(a,b){e[a]||(e[a]={});e[a].flash=b});e.types=f;e.mimeType=function(a){var b;k(f,function(c,e){!b&&e==a&&(b=c)});return b};e.extType=function(a){return e.mimeType(h[a])}}(jwplayer.utils),function(d){var a=d.loaderstatus={NEW:0,LOADING:1,ERROR:2,COMPLETE:3},k=document;d.scriptloader=function(f){function h(){j=a.ERROR;c.sendEvent(b.ERROR)}function e(){j=a.COMPLETE;c.sendEvent(b.COMPLETE)}var j=a.NEW,b=jwplayer.events,c=new b.eventdispatcher;d.extend(this,c);this.load=function(){var c=d.scriptloader.loaders[f];if(c&&(c.getStatus()==a.NEW||c.getStatus()==a.LOADING)){c.addEventListener(b.ERROR,h),c.addEventListener(b.COMPLETE,e)}else{if(d.scriptloader.loaders[f]=this,j==a.NEW){j=a.LOADING;var m=k.createElement("script");m.addEventListener?(m.onload=e,m.onerror=h):m.readyState&&(m.onreadystatechange=function(){("loaded"==m.readyState||"complete"==m.readyState)&&e()});k.getElementsByTagName("head")[0].appendChild(m);m.src=f}}};this.getStatus=function(){return j}};d.scriptloader.loaders={}}(jwplayer.utils),function(d){d.trim=function(a){return a.replace(/^\s*/,"").replace(/\s*$/,"")};d.pad=function(a,d,f){for(f||(f="0");a.length<d;){a=f+a}return a};d.xmlAttribute=function(a,d){for(var f=0;f<a.attributes.length;f++){if(a.attributes[f].name&&a.attributes[f].name.toLowerCase()==d.toLowerCase()){return a.attributes[f].value.toString()}}return""};d.extension=function(a){if(!a||"rtmp"==a.substr(0,4)){return""}a=a.substring(a.lastIndexOf("/")+1,a.length).split("?")[0].split("#")[0];if(-1<a.lastIndexOf(".")){return a.substr(a.lastIndexOf(".")+1,a.length).toLowerCase()}};d.stringToColor=function(a){a=a.replace(/(#|0x)?([0-9A-F]{3,6})$/gi,"$2");3==a.length&&(a=a.charAt(0)+a.charAt(0)+a.charAt(1)+a.charAt(1)+a.charAt(2)+a.charAt(2));return parseInt(a,16)}}(jwplayer.utils),function(d){var a="touchmove",k="touchstart";d.touch=function(f){function h(b){b.type==k?(c=!0,m=j(l.DRAG_START,b)):b.type==a?c&&(p||(e(l.DRAG_START,b,m),p=!0),e(l.DRAG,b)):(c&&(p?e(l.DRAG_END,b):(b.cancelBubble=!0,e(l.TAP,b))),c=p=!1,m=null)}function e(a,b,c){if(n[a]&&(b.preventManipulation&&b.preventManipulation(),b.preventDefault&&b.preventDefault(),b=c?c:j(a,b))){n[a](b)}}function j(a,c){var e=null;c.touches&&c.touches.length?e=c.touches[0]:c.changedTouches&&c.changedTouches.length&&(e=c.changedTouches[0]);if(!e){return null}var j=b.getBoundingClientRect(),e={type:a,target:b,x:e.pageX-window.pageXOffset-j.left,y:e.pageY,deltaX:0,deltaY:0};a!=l.TAP&&m&&(e.deltaX=e.x-m.x,e.deltaY=e.y-m.y);return e}var b=f,c=!1,n={},m=null,p=!1,l=d.touchEvents;document.addEventListener(a,h);document.addEventListener("touchend",function(a){c&&p&&e(l.DRAG_END,a);c=p=!1;m=null});document.addEventListener("touchcancel",h);f.addEventListener(k,h);f.addEventListener("touchend",h);this.addEventListener=function(a,b){n[a]=b};this.removeEventListener=function(a){delete n[a]};return this}}(jwplayer.utils),function(d){d.touchEvents={DRAG:"jwplayerDrag",DRAG_START:"jwplayerDragStart",DRAG_END:"jwplayerDragEnd",TAP:"jwplayerTap"}}(jwplayer.utils),function(d){d.key=function(a){var k,f,h;this.edition=function(){return h&&h.getTime()<(new Date).getTime()?"invalid":k};this.token=function(){return f};d.exists(a)||(a="");try{a=d.tea.decrypt(a,"36QXq4W@GSBV^teR");var e=a.split("/");(k=e[0])?/^(free|pro|premium|ads)$/i.test(k)?(f=e[1],e[2]&&0<parseInt(e[2])&&(h=new Date,h.setTime(String(e[2])))):k="invalid":k="free"}catch(j){k="invalid"}}}(jwplayer.utils),function(d){var a=d.tea={};a.encrypt=function(h,e){if(0==h.length){return""}var j=a.strToLongs(f.encode(h));1>=j.length&&(j[1]=0);for(var b=a.strToLongs(f.encode(e).slice(0,16)),c=j.length,d=j[c-1],m=j[0],p,l=Math.floor(6+52/c),g=0;0<l--;){g+=2654435769;p=g>>>2&3;for(var q=0;q<c;q++){m=j[(q+1)%c],d=(d>>>5^m<<2)+(m>>>3^d<<4)^(g^m)+(b[q&3^p]^d),d=j[q]+=d}}j=a.longsToStr(j);return k.encode(j)};a.decrypt=function(h,e){if(0==h.length){return""}for(var j=a.strToLongs(k.decode(h)),b=a.strToLongs(f.encode(e).slice(0,16)),c=j.length,d=j[c-1],m=j[0],p,l=2654435769*Math.floor(6+52/c);0!=l;){p=l>>>2&3;for(var g=c-1;0<=g;g--){d=j[0<g?g-1:c-1],d=(d>>>5^m<<2)+(m>>>3^d<<4)^(l^m)+(b[g&3^p]^d),m=j[g]-=d}l-=2654435769}j=a.longsToStr(j);j=j.replace(/\0+$/,"");return f.decode(j)};a.strToLongs=function(a){for(var e=Array(Math.ceil(a.length/4)),j=0;j<e.length;j++){e[j]=a.charCodeAt(4*j)+(a.charCodeAt(4*j+1)<<8)+(a.charCodeAt(4*j+2)<<16)+(a.charCodeAt(4*j+3)<<24)}return e};a.longsToStr=function(a){for(var e=Array(a.length),j=0;j<a.length;j++){e[j]=String.fromCharCode(a[j]&255,a[j]>>>8&255,a[j]>>>16&255,a[j]>>>24&255)}return e.join("")};var k={code:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/\x3d",encode:function(a,e){var j,b,c,d,m=[],p="",l,g,q=k.code;g=("undefined"==typeof e?0:e)?f.encode(a):a;l=g.length%3;if(0<l){for(;3>l++;){p+="\x3d",g+="\x00"}}for(l=0;l<g.length;l+=3){j=g.charCodeAt(l),b=g.charCodeAt(l+1),c=g.charCodeAt(l+2),d=j<<16|b<<8|c,j=d>>18&63,b=d>>12&63,c=d>>6&63,d&=63,m[l/3]=q.charAt(j)+q.charAt(b)+q.charAt(c)+q.charAt(d)}m=m.join("");return m=m.slice(0,m.length-p.length)+p},decode:function(a,e){e="undefined"==typeof e?!1:e;var j,b,c,d,m,p=[],l,g=k.code;l=e?f.decode(a):a;for(var q=0;q<l.length;q+=4){j=g.indexOf(l.charAt(q)),b=g.indexOf(l.charAt(q+1)),d=g.indexOf(l.charAt(q+2)),m=g.indexOf(l.charAt(q+3)),c=j<<18|b<<12|d<<6|m,j=c>>>16&255,b=c>>>8&255,c&=255,p[q/4]=String.fromCharCode(j,b,c),64==m&&(p[q/4]=String.fromCharCode(j,b)),64==d&&(p[q/4]=String.fromCharCode(j))}d=p.join("");return e?f.decode(d):d}},f={encode:function(a){a=a.replace(/[\u0080-\u07ff]/g,function(a){a=a.charCodeAt(0);return String.fromCharCode(192|a>>6,128|a&63)});return a=a.replace(/[\u0800-\uffff]/g,function(a){a=a.charCodeAt(0);return String.fromCharCode(224|a>>12,128|a>>6&63,128|a&63)})},decode:function(a){a=a.replace(/[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g,function(a){a=(a.charCodeAt(0)&15)<<12|(a.charCodeAt(1)&63)<<6|a.charCodeAt(2)&63;return String.fromCharCode(a)});return a=a.replace(/[\u00c0-\u00df][\u0080-\u00bf]/g,function(a){a=(a.charCodeAt(0)&31)<<6|a.charCodeAt(1)&63;return String.fromCharCode(a)})}}}(jwplayer.utils),function(d){d.events={COMPLETE:"COMPLETE",ERROR:"ERROR",API_READY:"jwplayerAPIReady",JWPLAYER_READY:"jwplayerReady",JWPLAYER_FULLSCREEN:"jwplayerFullscreen",JWPLAYER_RESIZE:"jwplayerResize",JWPLAYER_ERROR:"jwplayerError",JWPLAYER_SETUP_ERROR:"jwplayerSetupError",JWPLAYER_MEDIA_BEFOREPLAY:"jwplayerMediaBeforePlay",JWPLAYER_MEDIA_BEFORECOMPLETE:"jwplayerMediaBeforeComplete",JWPLAYER_COMPONENT_SHOW:"jwplayerComponentShow",JWPLAYER_COMPONENT_HIDE:"jwplayerComponentHide",JWPLAYER_MEDIA_BUFFER:"jwplayerMediaBuffer",JWPLAYER_MEDIA_BUFFER_FULL:"jwplayerMediaBufferFull",JWPLAYER_MEDIA_ERROR:"jwplayerMediaError",JWPLAYER_MEDIA_LOADED:"jwplayerMediaLoaded",JWPLAYER_MEDIA_COMPLETE:"jwplayerMediaComplete",JWPLAYER_MEDIA_SEEK:"jwplayerMediaSeek",JWPLAYER_MEDIA_TIME:"jwplayerMediaTime",JWPLAYER_MEDIA_VOLUME:"jwplayerMediaVolume",JWPLAYER_MEDIA_META:"jwplayerMediaMeta",JWPLAYER_MEDIA_MUTE:"jwplayerMediaMute",JWPLAYER_MEDIA_LEVELS:"jwplayerMediaLevels",JWPLAYER_MEDIA_LEVEL_CHANGED:"jwplayerMediaLevelChanged",JWPLAYER_CAPTIONS_CHANGED:"jwplayerCaptionsChanged",JWPLAYER_CAPTIONS_LIST:"jwplayerCaptionsList",JWPLAYER_PLAYER_STATE:"jwplayerPlayerState",state:{BUFFERING:"BUFFERING",IDLE:"IDLE",PAUSED:"PAUSED",PLAYING:"PLAYING"},JWPLAYER_PLAYLIST_LOADED:"jwplayerPlaylistLoaded",JWPLAYER_PLAYLIST_ITEM:"jwplayerPlaylistItem",JWPLAYER_PLAYLIST_COMPLETE:"jwplayerPlaylistComplete",JWPLAYER_DISPLAY_CLICK:"jwplayerViewClick",JWPLAYER_CONTROLS:"jwplayerViewControls",JWPLAYER_USER_ACTION:"jwplayerUserAction",JWPLAYER_INSTREAM_CLICK:"jwplayerInstreamClicked",JWPLAYER_INSTREAM_DESTROYED:"jwplayerInstreamDestroyed",JWPLAYER_AD_TIME:"jwplayerAdTime",JWPLAYER_AD_ERROR:"jwplayerAdError",JWPLAYER_AD_CLICK:"jwplayerAdClicked",JWPLAYER_AD_COMPLETE:"jwplayerAdComplete",JWPLAYER_AD_IMPRESSION:"jwplayerAdImpression",JWPLAYER_AD_COMPANIONS:"jwplayerAdCompanions"}}(jwplayer),function(d){var a=jwplayer.utils;d.eventdispatcher=function(d,f){var h,e;this.resetEventListeners=function(){h={};e=[]};this.resetEventListeners();this.addEventListener=function(e,b,c){try{a.exists(h[e])||(h[e]=[]),"string"==a.typeOf(b)&&(b=(new Function("return "+b))()),h[e].push({listener:b,count:c})}catch(f){a.log("error",f)}return !1};this.removeEventListener=function(e,b){if(h[e]){try{for(var c=0;c<h[e].length;c++){if(h[e][c].listener.toString()==b.toString()){h[e].splice(c,1);break}}}catch(f){a.log("error",f)}return !1}};this.addGlobalListener=function(f,b){try{"string"==a.typeOf(f)&&(f=(new Function("return "+f))()),e.push({listener:f,count:b})}catch(c){a.log("error",c)}return !1};this.removeGlobalListener=function(f){if(f){try{for(var b=0;b<e.length;b++){if(e[b].listener.toString()==f.toString()){e.splice(b,1);break}}}catch(c){a.log("error",c)}return !1}};this.sendEvent=function(j,b){a.exists(b)||(b={});a.extend(b,{id:d,version:jwplayer.version,type:j});f&&a.log(j,b);if("undefined"!=a.typeOf(h[j])){for(var c=0;c<h[j].length;c++){try{h[j][c].listener(b)}catch(n){a.log("There was an error while handling a listener: "+n.toString(),h[j][c].listener)}h[j][c]&&(1===h[j][c].count?delete h[j][c]:0<h[j][c].count&&(h[j][c].count-=1))}}for(c=0;c<e.length;c++){try{e[c].listener(b)}catch(m){a.log("There was an error while handling a listener: "+m.toString(),e[c].listener)}e[c]&&(1===e[c].count?delete e[c]:0<e[c].count&&(e[c].count-=1))}}}}(jwplayer.events),function(d){var a={},k={};d.plugins=function(){};d.plugins.loadPlugins=function(f,h){k[f]=new d.plugins.pluginloader(new d.plugins.model(a),h);return k[f]};d.plugins.registerPlugin=function(f,h,e,j){var b=d.utils.getPluginName(f);a[b]||(a[b]=new d.plugins.plugin(f));a[b].registerPlugin(f,h,e,j)}}(jwplayer),function(d){d.plugins.model=function(a){this.addPlugin=function(k){var f=d.utils.getPluginName(k);a[f]||(a[f]=new d.plugins.plugin(k));return a[f]};this.getPlugins=function(){return a}}}(jwplayer),function(d){var a=jwplayer.utils,k=jwplayer.events;d.pluginmodes={FLASH:0,JAVASCRIPT:1,HYBRID:2};d.plugin=function(f){function h(){switch(a.getPluginPathType(f)){case a.pluginPathType.ABSOLUTE:return f;case a.pluginPathType.RELATIVE:return a.getAbsolutePath(f,window.location.href)}}function e(){p=setTimeout(function(){b=a.loaderstatus.COMPLETE;l.sendEvent(k.COMPLETE)},1000)}function j(){b=a.loaderstatus.ERROR;l.sendEvent(k.ERROR)}var b=a.loaderstatus.NEW,c,n,m,p,l=new k.eventdispatcher;a.extend(this,l);this.load=function(){if(b==a.loaderstatus.NEW){if(0<f.lastIndexOf(".swf")){c=f,b=a.loaderstatus.COMPLETE,l.sendEvent(k.COMPLETE)}else{if(a.getPluginPathType(f)==a.pluginPathType.CDN){b=a.loaderstatus.COMPLETE,l.sendEvent(k.COMPLETE)}else{b=a.loaderstatus.LOADING;var g=new a.scriptloader(h());g.addEventListener(k.COMPLETE,e);g.addEventListener(k.ERROR,j);g.load()}}}};this.registerPlugin=function(e,f,j,d){p&&(clearTimeout(p),p=void 0);m=f;j&&d?(c=d,n=j):"string"==typeof j?c=j:"function"==typeof j?n=j:!j&&!d&&(c=e);b=a.loaderstatus.COMPLETE;l.sendEvent(k.COMPLETE)};this.getStatus=function(){return b};this.getPluginName=function(){return a.getPluginName(f)};this.getFlashPath=function(){if(c){switch(a.getPluginPathType(c)){case a.pluginPathType.ABSOLUTE:return c;case a.pluginPathType.RELATIVE:return 0<f.lastIndexOf(".swf")?a.getAbsolutePath(c,window.location.href):a.getAbsolutePath(c,h())}}return null};this.getJS=function(){return n};this.getTarget=function(){return m};this.getPluginmode=function(){if("undefined"!=typeof c&&"undefined"!=typeof n){return d.pluginmodes.HYBRID}if("undefined"!=typeof c){return d.pluginmodes.FLASH}if("undefined"!=typeof n){return d.pluginmodes.JAVASCRIPT}};this.getNewInstance=function(a,b,c){return new n(a,b,c)};this.getURL=function(){return f}}}(jwplayer.plugins),function(d){var a=d.utils,k=d.events,f=a.foreach;d.plugins.pluginloader=function(h,e){function j(){m?g.sendEvent(k.ERROR,{message:p}):n||(n=!0,c=a.loaderstatus.COMPLETE,g.sendEvent(k.COMPLETE))}function b(){l||j();if(!n&&!m){var b=0,c=h.getPlugins();a.foreach(l,function(e){e=a.getPluginName(e);var f=c[e];e=f.getJS();var g=f.getTarget(),f=f.getStatus();if(f==a.loaderstatus.LOADING||f==a.loaderstatus.NEW){b++}else{if(e&&(!g||parseFloat(g)>parseFloat(d.version))){m=!0,p="Incompatible player version",j()}}});0==b&&j()}}var c=a.loaderstatus.NEW,n=!1,m=!1,p,l=e,g=new k.eventdispatcher;a.extend(this,g);this.setupPlugins=function(b,c,e){var g={length:0,plugins:{}},j=0,d={},n=h.getPlugins();f(c.plugins,function(f,h){var k=a.getPluginName(f),l=n[k],m=l.getFlashPath(),q=l.getJS(),p=l.getURL();m&&(g.plugins[m]=a.extend({},h),g.plugins[m].pluginmode=l.getPluginmode(),g.length++);try{if(q&&c.plugins&&c.plugins[p]){var A=document.createElement("div");A.id=b.id+"_"+k;A.style.position="absolute";A.style.top=0;A.style.zIndex=j+10;d[k]=l.getNewInstance(b,a.extend({},c.plugins[p]),A);j++;b.onReady(e(d[k],A,!0));b.onResize(e(d[k],A))}}catch(D){a.log("ERROR: Failed to load "+k+".")}});b.plugins=d;return g};this.load=function(){if(!(a.exists(e)&&"object"!=a.typeOf(e))){c=a.loaderstatus.LOADING;f(e,function(c){a.exists(c)&&(c=h.addPlugin(c),c.addEventListener(k.COMPLETE,b),c.addEventListener(k.ERROR,q))});var g=h.getPlugins();f(g,function(a,b){b.load()})}b()};var q=this.pluginFailed=function(){m||(m=!0,p="File not found",j())};this.getStatus=function(){return c}}}(jwplayer),function(){jwplayer.parsers={localName:function(d){return d?d.localName?d.localName:d.baseName?d.baseName:"":""},textContent:function(d){return d?d.textContent?d.textContent:d.text?d.text:"":""},getChildNode:function(d,a){return d.childNodes[a]},numChildren:function(d){return d.childNodes?d.childNodes.length:0}}}(jwplayer),function(d){var a=d.parsers;(a.jwparser=function(){}).parseEntry=function(k,f){for(var h=[],e=[],j=d.utils.xmlAttribute,b=0;b<k.childNodes.length;b++){var c=k.childNodes[b];if("jwplayer"==c.prefix){var n=a.localName(c);"source"==n?(delete f.sources,h.push({file:j(c,"file"),"default":j(c,"default"),label:j(c,"label"),type:j(c,"type")})):"track"==n?(delete f.tracks,e.push({file:j(c,"file"),"default":j(c,"default"),kind:j(c,"kind"),label:j(c,"label")})):(f[n]=d.utils.serialize(a.textContent(c)),"file"==n&&f.sources&&delete f.sources)}f.file||(f.file=f.link)}if(h.length){f.sources=[];for(b=0;b<h.length;b++){0<h[b].file.length&&(h[b]["default"]="true"==h[b]["default"]?!0:!1,h[b].label.length||delete h[b].label,f.sources.push(h[b]))}}if(e.length){f.tracks=[];for(b=0;b<e.length;b++){0<e[b].file.length&&(e[b]["default"]="true"==e[b]["default"]?!0:!1,e[b].kind=!e[b].kind.length?"captions":e[b].kind,e[b].label.length||delete e[b].label,f.tracks.push(e[b]))}}return f}}(jwplayer),function(d){var a=jwplayer.utils,k=a.xmlAttribute,f=d.localName,h=d.textContent,e=d.numChildren,j=d.mediaparser=function(){};j.parseGroup=function(b,c){var d,m,p=[];for(m=0;m<e(b);m++){if(d=b.childNodes[m],"media"==d.prefix&&f(d)){switch(f(d).toLowerCase()){case"content":k(d,"duration")&&(c.duration=a.seconds(k(d,"duration")));0<e(d)&&(c=j.parseGroup(d,c));k(d,"url")&&(c.sources||(c.sources=[]),c.sources.push({file:k(d,"url"),type:k(d,"type"),width:k(d,"width"),label:k(d,"label")}));break;case"title":c.title=h(d);break;case"description":c.description=h(d);break;case"guid":c.mediaid=h(d);break;case"thumbnail":c.image||(c.image=k(d,"url"));break;case"group":j.parseGroup(d,c);break;case"subtitle":var l={};l.file=k(d,"url");l.kind="captions";if(0<k(d,"lang").length){var g=l;d=k(d,"lang");var q={zh:"Chinese",nl:"Dutch",en:"English",fr:"French",de:"German",it:"Italian",ja:"Japanese",pt:"Portuguese",ru:"Russian",es:"Spanish"};d=q[d]?q[d]:d;g.label=d}p.push(l)}}}c.hasOwnProperty("tracks")||(c.tracks=[]);for(m=0;m<p.length;m++){c.tracks.push(p[m])}return c}}(jwplayer.parsers),function(d){function a(a){for(var c={},e=0;e<a.childNodes.length;e++){var h=a.childNodes[e],p=j(h);if(p){switch(p.toLowerCase()){case"enclosure":c.file=k.xmlAttribute(h,"url");break;case"title":c.title=f(h);break;case"guid":c.mediaid=f(h);break;case"pubdate":c.date=f(h);break;case"description":c.description=f(h);break;case"link":c.link=f(h);break;case"category":c.tags=c.tags?c.tags+f(h):f(h)}}}c=d.mediaparser.parseGroup(a,c);c=d.jwparser.parseEntry(a,c);return new jwplayer.playlist.item(c)}var k=jwplayer.utils,f=d.textContent,h=d.getChildNode,e=d.numChildren,j=d.localName;d.rssparser={};d.rssparser.parse=function(b){for(var c=[],f=0;f<e(b);f++){var d=h(b,f);if("channel"==j(d).toLowerCase()){for(var k=0;k<e(d);k++){var l=h(d,k);"item"==j(l).toLowerCase()&&c.push(a(l))}}}return c}}(jwplayer.parsers),function(d){d.playlist=function(a){var k=[];if("array"==d.utils.typeOf(a)){for(var f=0;f<a.length;f++){k.push(new d.playlist.item(a[f]))}}else{k.push(new d.playlist.item(a))}return k}}(jwplayer),function(d){var a=d.item=function(k){var f=jwplayer.utils,h=f.extend({},a.defaults,k);h.tracks=k&&f.exists(k.tracks)?k.tracks:[];0==h.sources.length&&(h.sources=[new d.source(h)]);for(var e=0;e<h.sources.length;e++){var j=h.sources[e]["default"];h.sources[e]["default"]=j?"true"==j.toString():!1;h.sources[e]=new d.source(h.sources[e])}if(h.captions&&!f.exists(k.tracks)){for(k=0;k<h.captions.length;k++){h.tracks.push(h.captions[k])}delete h.captions}for(e=0;e<h.tracks.length;e++){h.tracks[e]=new d.track(h.tracks[e])}return h};a.defaults={description:"",image:"",mediaid:"",title:"",sources:[],tracks:[]}}(jwplayer.playlist),function(d){var a=jwplayer,k=a.utils,f=a.events,h=a.parsers;d.loader=function(){function a(e){try{var j=e.responseXML.childNodes;e="";for(var k=0;k<j.length&&!(e=j[k],8!=e.nodeType);k++){}"xml"==h.localName(e)&&(e=e.nextSibling);if("rss"!=h.localName(e)){b("Not a valid RSS feed")}else{var l=new d(h.rssparser.parse(e));c.sendEvent(f.JWPLAYER_PLAYLIST_LOADED,{playlist:l})}}catch(g){b()}}function j(a){b(a.match(/invalid/i)?"Not a valid RSS feed":"")}function b(a){c.sendEvent(f.JWPLAYER_ERROR,{message:a?a:"Error loading file"})}var c=new f.eventdispatcher;k.extend(this,c);this.load=function(b){k.ajax(b,a,j)}}}(jwplayer.playlist),function(d){var a=jwplayer.utils,k={file:void 0,label:void 0,type:void 0,"default":void 0};d.source=function(f){var d=a.extend({},k);a.foreach(k,function(e){a.exists(f[e])&&(d[e]=f[e],delete f[e])});d.type&&0<d.type.indexOf("/")&&(d.type=a.extensionmap.mimeType(d.type));"m3u8"==d.type&&(d.type="hls");"smil"==d.type&&(d.type="rtmp");return d}}(jwplayer.playlist),function(d){var a=jwplayer.utils,k={file:void 0,label:void 0,kind:"captions","default":!1};d.track=function(d){var h=a.extend({},k);d||(d={});a.foreach(k,function(e){a.exists(d[e])&&(h[e]=d[e],delete d[e])});return h}}(jwplayer.playlist),function(d){var a=d.utils,k=d.events,f=!0,h=!1,e=document,j=d.embed=function(b){function c(b,c){a.foreach(c,function(a,c){"function"==typeof b[a]&&b[a].call(b,c)})}function n(){if(r.sitecatalyst){try{null!=s&&s.hasOwnProperty("Media")||l()}catch(e){l();return}}if("array"==a.typeOf(r.playlist)&&2>r.playlist.length&&(0==r.playlist.length||!r.playlist[0].sources||0==r.playlist[0].sources.length)){p()}else{if(!z){if("string"==a.typeOf(r.playlist)){var g=new d.playlist.loader;g.addEventListener(k.JWPLAYER_PLAYLIST_LOADED,function(a){r.playlist=a.playlist;z=h;n()});g.addEventListener(k.JWPLAYER_ERROR,function(a){z=h;p(a)});z=f;g.load(r.playlist)}else{if(y.getStatus()==a.loaderstatus.COMPLETE){for(g=0;g<r.modes.length;g++){if(r.modes[g].type&&j[r.modes[g].type]){var u=a.extend({},r),w=new j[r.modes[g].type](t,r.modes[g],u,y,b);if(w.supportsConfig()){return w.addEventListener(k.ERROR,m),w.embed(),c(b,u.events),b}}}if(r.fallback){var x="No suitable players found and fallback enabled";C=setTimeout(function(){q(x,f)},10);a.log(x);new j.download(t,r,p)}else{x="No suitable players found and fallback disabled",q(x,h),a.log(x),t.parentNode.replaceChild(v,t)}}}}}}function m(a){g(t,B+a.message)}function p(a){a&&a.message?g(t,"Error loading playlist: "+a.message):g(t,B+"No playable sources found")}function l(){g(t,"Adobe SiteCatalyst Error: Could not find Media Module")}function g(b,c){if(r.fallback){var e=b.style;e.backgroundColor="#000";e.color="#FFF";e.width=a.styleDimension(r.width);e.height=a.styleDimension(r.height);e.display="table";e.opacity=1;var e=document.createElement("p"),d=e.style;d.verticalAlign="middle";d.textAlign="center";d.display="table-cell";d.font="15px/20px Arial, Helvetica, sans-serif";e.innerHTML=c.replace(":",":\x3cbr\x3e");b.innerHTML="";b.appendChild(e);q(c,f)}else{q(c,h)}}function q(a,c){C&&(clearTimeout(C),C=null);b.dispatchEvent(k.JWPLAYER_SETUP_ERROR,{message:a,fallback:c})}var r=new j.config(b.config),t,u,v,w=r.width,x=r.height,B="Error loading player: ",y=d.plugins.loadPlugins(b.id,r.plugins),z=h,C=null;r.fallbackDiv&&(v=r.fallbackDiv,delete r.fallbackDiv);r.id=b.id;u=e.getElementById(b.id);r.aspectratio?b.config.aspectratio=r.aspectratio:delete b.config.aspectratio;t=e.createElement("div");t.id=u.id;t.style.width=0<w.toString().indexOf("%")?w:w+"px";t.style.height=0<x.toString().indexOf("%")?x:x+"px";u.parentNode.replaceChild(t,u);d.embed.errorScreen=g;y.addEventListener(k.COMPLETE,n);y.addEventListener(k.ERROR,function(a){g(t,"Could not load plugins: "+a.message)});y.load();return b}}(jwplayer),function(d){function a(a){if(a.playlist){for(var d=0;d<a.playlist.length;d++){a.playlist[d]=new h(a.playlist[d])}}else{var b={};f.foreach(h.defaults,function(c){k(a,b,c)});b.sources||(a.levels?(b.sources=a.levels,delete a.levels):(d={},k(a,d,"file"),k(a,d,"type"),b.sources=d.file?[d]:[]));a.playlist=[new h(b)]}}function k(a,d,b){f.exists(a[b])&&(d[b]=a[b],delete a[b])}var f=d.utils,h=d.playlist.item;(d.embed.config=function(e){var j={fallback:!0,height:270,primary:"html5",width:480,base:e.base?e.base:f.getScriptPath("jwplayer.js"),aspectratio:""};e=f.extend(j,d.defaults,e);var j={type:"html5",src:e.base+"../images/llssc/player.html5.js"},b={type:"flash",src:e.base+"../images/game/llssc/flash.swf"};e.modes="flash"==e.primary?[b,j]:[j,b];e.listbar&&(e.playlistsize=e.listbar.size,e.playlistposition=e.listbar.position,e.playlistlayout=e.listbar.layout);e.flashplayer&&(b.src=e.flashplayer);e.html5player&&(j.src=e.html5player);a(e);b=e.aspectratio;if("string"!=typeof b||!f.exists(b)){j=0}else{var c=b.indexOf(":");-1==c?j=0:(j=parseFloat(b.substr(0,c)),b=parseFloat(b.substr(c+1)),j=0>=j||0>=b?0:100*(b/j)+"%")}-1==e.width.toString().indexOf("%")?delete e.aspectratio:j?e.aspectratio=j:delete e.aspectratio;return e}).addConfig=function(e,d){a(d);return f.extend(e,d)}}(jwplayer),function(d){var a=d.utils,k=document;d.embed.download=function(d,h,e){function j(b,c){for(var e=k.querySelectorAll(b),d=0;d<e.length;d++){a.foreach(c,function(a,b){e[d].style[a]=b})}}function b(a,b,c){a=k.createElement(a);b&&(a.className="jwdownload"+b);c&&c.appendChild(a);return a}var c=a.extend({},h),n=c.width?c.width:480,m=c.height?c.height:320,p;h=h.logo?h.logo:{prefix:a.repo(),file:"logo.png",margin:10};var l,g,q,c=c.playlist,r,t=["mp4","aac","mp3"];if(c&&c.length){r=c[0];p=r.sources;for(c=0;c<p.length;c++){var u=p[c],v=u.type?u.type:a.extensionmap.extType(a.extension(u.file));u.file&&a.foreach(t,function(b){v==t[b]?(l=u.file,g=r.image):a.isYouTube(u.file)&&(q=u.file)})}l?(p=l,e=g,d&&(c=b("a","display",d),b("div","icon",c),b("div","logo",c),p&&c.setAttribute("href",a.getAbsolutePath(p))),c="#"+d.id+" .jwdownload",d.style.width="",d.style.height="",j(c+"display",{width:a.styleDimension(Math.max(320,n)),height:a.styleDimension(Math.max(180,m)),background:"black center no-repeat "+(e?"url("+e+")":""),backgroundSize:"contain",position:"relative",border:"none",display:"block"}),j(c+"display div",{position:"absolute",width:"100%",height:"100%"}),j(c+"logo",{top:h.margin+"px",right:h.margin+"px",background:"top right no-repeat url("+h.prefix+h.file+")"}),j(c+"icon",{background:"center no-repeat url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADwAAAA8CAYAAAA6/NlyAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAgNJREFUeNrs28lqwkAYB/CZqNVDDj2r6FN41QeIy8Fe+gj6BL275Q08u9FbT8ZdwVfotSBYEPUkxFOoks4EKiJdaDuTjMn3wWBO0V/+sySR8SNSqVRKIR8qaXHkzlqS9jCfzzWcTCYp9hF5o+59sVjsiRzcegSckFzcjT+ruN80TeSlAjCAAXzdJSGPFXRpAAMYwACGZQkSdhG4WCzehMNhqV6vG6vVSrirKVEw66YoSqDb7cqlUilE8JjHd/y1MQefVzqdDmiaJpfLZWHgXMHn8F6vJ1cqlVAkEsGuAn83J4gAd2RZymQygX6/L1erVQt+9ZPWb+CDwcCC2zXGJaewl/DhcHhK3DVj+KfKZrMWvFarcYNLomAv4aPRSFZVlTlcSPA5fDweW/BoNIqFnKV53JvncjkLns/n/cLdS+92O7RYLLgsKfv9/t8XlDn4eDyiw+HA9Jyz2eyt0+kY2+3WFC5hluej0Ha7zQQq9PPwdDq1Et1sNsx/nFBgCqWJ8oAK1aUptNVqcYWewE4nahfU0YQnk4ntUEfGMIU2m01HoLaCKbTRaDgKtaVLk9tBYaBcE/6Artdr4RZ5TB6/dC+9iIe/WgAMYADDpAUJAxjAAAYwgGFZgoS/AtNNTF7Z2bL0BYPBV3Jw5xFwwWcYxgtBP5OkE8i9G7aWGOOCruvauwADALMLMEbKf4SdAAAAAElFTkSuQmCC)"})):q?(h=q,d=b("embed","",d),d.src="http://www.youtube.com/v/"+a.youTubeID(h),d.type="application/x-shockwave-flash",d.width=n,d.height=m):e()}}}(jwplayer),function(d){var a=d.utils,k=d.events,f={};(d.embed.flash=function(e,j,b,c,n){function m(a,b,c){var e=document.createElement("param");e.setAttribute("name",b);e.setAttribute("value",c);a.appendChild(e)}function p(a,b,c){return function(){try{c&&document.getElementById(n.id+"_wrapper").appendChild(b);var e=document.getElementById(n.id).getPluginConfig("display");"function"==typeof a.resize&&a.resize(e.width,e.height);b.style.left=e.x;b.style.top=e.h}catch(d){}}}function l(b){if(!b){return{}}var c={},e=[];a.foreach(b,function(b,d){var g=a.getPluginName(b);e.push(b);a.foreach(d,function(a,b){c[g+"."+a]=b})});c.plugins=e.join(",");return c}var g=new d.events.eventdispatcher,q=a.flashVersion();a.extend(this,g);this.embed=function(){b.id=n.id;if(10>q){return g.sendEvent(k.ERROR,{message:"Flash version must be 10.0 or greater"}),!1}var d,h,u=n.config.listbar,v=a.extend({},b);if(e.id+"_wrapper"==e.parentNode.id){d=document.getElementById(e.id+"_wrapper")}else{d=document.createElement("div");h=document.createElement("div");h.style.display="none";h.id=e.id+"_aspect";d.id=e.id+"_wrapper";d.style.position="relative";d.style.display="block";d.style.width=a.styleDimension(v.width);d.style.height=a.styleDimension(v.height);if(n.config.aspectratio){var w=parseFloat(n.config.aspectratio);h.style.display="block";h.style.marginTop=n.config.aspectratio;d.style.height="auto";d.style.display="inline-block";u&&("bottom"==u.position?h.style.paddingBottom=u.size+"px":"right"==u.position&&(h.style.marginBottom=-1*u.size*(w/100)+"px"))}e.parentNode.replaceChild(d,e);d.appendChild(e);d.appendChild(h)}d=c.setupPlugins(n,v,p);0<d.length?a.extend(v,l(d.plugins)):delete v.plugins;"undefined"!=typeof v["dock.position"]&&"false"==v["dock.position"].toString().toLowerCase()&&(v.dock=v["dock.position"],delete v["dock.position"]);d=v.wmode?v.wmode:v.height&&40>=v.height?"transparent":"opaque";h="height width modes events primary base fallback volume".split(" ");for(u=0;u<h.length;u++){delete v[h[u]]}h=a.getCookies();a.foreach(h,function(a,b){"undefined"==typeof v[a]&&(v[a]=b)});h=window.location.href.split("/");h.splice(h.length-1,1);h=h.join("/");v.base=h+"/";f[e.id]=v;a.isIE()?(h='\x3cobject classid\x3d"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" " width\x3d"100%" height\x3d"100%"id\x3d"'+e.id+'" name\x3d"'+e.id+'" tabindex\x3d0""\x3e',h+='\x3cparam name\x3d"movie" value\x3d"'+j.src+'"\x3e',h+='\x3cparam name\x3d"allowfullscreen" value\x3d"true"\x3e\x3cparam name\x3d"allowscriptaccess" value\x3d"always"\x3e',h+='\x3cparam name\x3d"seamlesstabbing" value\x3d"true"\x3e',h+='\x3cparam name\x3d"wmode" value\x3d"'+d+'"\x3e',h+='\x3cparam name\x3d"bgcolor" value\x3d"#000000"\x3e',h+="\x3c/object\x3e",e.outerHTML=h,d=document.getElementById(e.id)):(h=document.createElement("object"),h.setAttribute("type","application/x-shockwave-flash"),h.setAttribute("data",j.src),h.setAttribute("width","100%"),h.setAttribute("height","100%"),h.setAttribute("bgcolor","#000000"),h.setAttribute("id",e.id),h.setAttribute("name",e.id),h.setAttribute("tabindex",0),m(h,"allowfullscreen","true"),m(h,"allowscriptaccess","always"),m(h,"seamlesstabbing","true"),m(h,"wmode",d),e.parentNode.replaceChild(h,e),d=h);n.config.aspectratio&&(d.style.position="absolute");n.container=d;n.setPlayer(d,"flash")};this.supportsConfig=function(){if(q){if(b){if("string"==a.typeOf(b.playlist)){return !0}try{var c=b.playlist[0].sources;if("undefined"==typeof c){return !0}for(var d=0;d<c.length;d++){if(c[d].file&&h(c[d].file,c[d].type)){return !0}}}catch(e){}}else{return !0}}return !1}}).getVars=function(a){return f[a]};var h=d.embed.flashCanPlay=function(d,f){if(a.isYouTube(d)||a.isRtmp(d,f)||"hls"==f){return !0}var b=a.extensionmap[f?f:a.extension(d)];return !b?!1:!!b.flash}}(jwplayer),function(d){var a=d.utils,k=a.extensionmap,f=d.events;d.embed.html5=function(h,e,j,b,c){function n(a,b,c){return function(){try{var d=document.querySelector("#"+h.id+" .jwmain");c&&d.appendChild(b);"function"==typeof a.resize&&(a.resize(d.clientWidth,d.clientHeight),setTimeout(function(){a.resize(d.clientWidth,d.clientHeight)},400));b.left=d.style.left;b.top=d.style.top}catch(e){}}}function m(a){p.sendEvent(a.type,{message:"HTML5 player not found"})}var p=this,l=new f.eventdispatcher;a.extend(p,l);p.embed=function(){if(d.html5){b.setupPlugins(c,j,n);h.innerHTML="";var g=d.utils.extend({},j);delete g.volume;g=new d.html5.player(g);c.container=document.getElementById(c.id);c.setPlayer(g,"html5")}else{g=new a.scriptloader(e.src),g.addEventListener(f.ERROR,m),g.addEventListener(f.COMPLETE,p.embed),g.load()}};p.supportsConfig=function(){if(d.vid.canPlayType){try{if("string"==a.typeOf(j.playlist)){return !0}for(var b=j.playlist[0].sources,c=0;c<b.length;c++){var e;var f=b[c].file,h=b[c].type;if(null!==navigator.userAgent.match(/BlackBerry/i)||a.isAndroid()&&("m3u"==a.extension(f)||"m3u8"==a.extension(f))||a.isRtmp(f,h)){e=!1}else{var l=k[h?h:a.extension(f)],m;if(!l||l.flash&&!l.html5){m=!1}else{var n=l.html5,p=d.vid;if(n){try{m=p.canPlayType(n)?!0:!1}catch(y){m=!1}}else{m=!0}}e=m}if(e){return !0}}}catch(z){}}return !1}}}(jwplayer),function(d){var a=d.embed,k=d.utils,f=k.extend(function(f){var e=k.repo(),j=k.extend({},d.defaults),b=k.extend({},j,f.config),c=f.config,n=b.plugins,m=b.analytics,p=e+"jwpsrv.js",l=e+"sharing.js",g=e+"related.js",q=e+"gapro.js",j=d.key?d.key:j.key,r=(new d.utils.key(j)).edition(),n=n?n:{};"ads"==r&&b.advertising&&(b.advertising.client.match(".js$|.swf$")?n[b.advertising.client]=b.advertising:n[e+b.advertising.client+".js"]=b.advertising);delete c.advertising;c.key=j;b.analytics&&(b.analytics.client&&b.analytics.client.match(".js$|.swf$"))&&(p=b.analytics.client);delete c.analytics;if("free"==r||!m||!1!==m.enabled){n[p]=m?m:{}}delete n.sharing;delete n.related;switch(r){case"premium":case"ads":b.related&&(b.related.client&&b.related.client.match(".js$|.swf$")&&(g=b.related.client),n[g]=b.related),b.ga&&(b.ga.client&&b.ga.client.match(".js$|.swf$")&&(q=b.ga.client),n[q]=b.ga),c.sitecatalyst&&new d.embed.sitecatalyst(f);case"pro":b.sharing&&(b.sharing.client&&b.sharing.client.match(".js$|.swf$")&&(l=b.sharing.client),n[l]=b.sharing),b.skin&&(c.skin=b.skin.replace(/^(beelden|bekle|five|glow|modieus|roundster|stormtrooper|vapor)$/i,k.repo()+"skins/$1.xml"))}c.plugins=n;return new a(f)},a);d.embed=f}(jwplayer),function(d){var a=jwplayer.utils;d.sitecatalyst=function(d){function f(b){c.debug&&a.log(b)}function h(a){a=a.split("/");a=a[a.length-1];a=a.split("?");return a[0]}function e(){if(!g){g=!0;var a=b.getPosition();f("stop: "+m+" : "+a);s.Media.stop(m,a)}}function j(){q||(e(),q=!0,f("close: "+m),s.Media.close(m),r=!0,l=0)}var b=d,c=a.extend({},b.config.sitecatalyst),n={onPlay:function(){if(!r){var a=b.getPosition();g=!1;f("play: "+m+" : "+a);s.Media.play(m,a)}},onPause:e,onBuffer:e,onIdle:j,onPlaylistItem:function(d){try{r=!0;j();l=0;var e;if(c.mediaName){e=c.mediaName}else{var g=b.getPlaylistItem(d.index);e=g.title?g.title:g.file?h(g.file):g.sources&&g.sources.length?h(g.sources[0].file):""}m=e;p=c.playerName?c.playerName:b.id}catch(f){a.log(f)}},onTime:function(){if(r){var a=b.getDuration();if(-1==a){return}q=g=r=!1;f("open: "+m+" : "+a+" : "+p);s.Media.open(m,a,p);f("play: "+m+" : 0");s.Media.play(m,0)}a=b.getPosition();if(3<=Math.abs(a-l)){var c=l;f("seek: "+c+" to "+a);f("stop: "+m+" : "+c);s.Media.stop(m,c);f("play: "+m+" : "+a);s.Media.play(m,a)}l=a},onComplete:j},m,p,l,g=!0,q=!0,r;a.foreach(n,function(a){b[a](n[a])})}}(jwplayer.embed),function(d){var a=[],k=d.utils,f=d.events,h=f.state,e=document,j=d.api=function(a){function c(a,b){return function(c){return b(a,c)}}function n(a,b){r[a]||(r[a]=[],p(f.JWPLAYER_PLAYER_STATE,function(b){var c=b.newstate;b=b.oldstate;if(c==a){var d=r[c];if(d){for(var e=0;e<d.length;e++){"function"==typeof d[e]&&d[e].call(this,{oldstate:b,newstate:c})}}}}));r[a].push(b);return g}function m(a,b){try{a.jwAddEventListener(b,'function(dat) { jwplayer("'+g.id+'").dispatchEvent("'+b+'", dat); }')}catch(c){k.log("Could not add internal listener")}}function p(a,b){q[a]||(q[a]=[],t&&u&&m(t,a));q[a].push(b);return g}function l(){if(u){for(var a=arguments[0],b=[],c=1;c<arguments.length;c++){b.push(arguments[c])}if("undefined"!=typeof t&&"function"==typeof t[a]){switch(b.length){case 4:return t[a](b[0],b[1],b[2],b[3]);case 3:return t[a](b[0],b[1],b[2]);case 2:return t[a](b[0],b[1]);case 1:return t[a](b[0]);default:return t[a]()}}return null}v.push(arguments)}var g=this,q={},r={},t=void 0,u=!1,v=[],w=void 0,x={},B={};g.container=a;g.id=a.id;g.getBuffer=function(){return l("jwGetBuffer")};g.getContainer=function(){return g.container};g.addButton=function(a,b,c,d){try{B[d]=c,l("jwDockAddButton",a,b,"jwplayer('"+g.id+"').callback('"+d+"')",d)}catch(e){k.log("Could not add dock button"+e.message)}};g.removeButton=function(a){l("jwDockRemoveButton",a)};g.callback=function(a){if(B[a]){B[a]()}};g.forceState=function(a){l("jwForceState",a);return g};g.releaseState=function(){return l("jwReleaseState")};g.getDuration=function(){return l("jwGetDuration")};g.getFullscreen=function(){return l("jwGetFullscreen")};g.getHeight=function(){return l("jwGetHeight")};g.getLockState=function(){return l("jwGetLockState")};g.getMeta=function(){return g.getItemMeta()};g.getMute=function(){return l("jwGetMute")};g.getPlaylist=function(){var a=l("jwGetPlaylist");"flash"==g.renderingMode&&k.deepReplaceKeyName(a,["__dot__","__spc__","__dsh__","__default__"],["."," ","-","default"]);return a};g.getPlaylistItem=function(a){k.exists(a)||(a=g.getPlaylistIndex());return g.getPlaylist()[a]};g.getPlaylistIndex=function(){return l("jwGetPlaylistIndex")};g.getPosition=function(){return l("jwGetPosition")};g.getRenderingMode=function(){return g.renderingMode};g.getState=function(){return l("jwGetState")};g.getVolume=function(){return l("jwGetVolume")};g.getWidth=function(){return l("jwGetWidth")};g.setFullscreen=function(a){k.exists(a)?l("jwSetFullscreen",a):l("jwSetFullscreen",!l("jwGetFullscreen"));return g};g.setMute=function(a){k.exists(a)?l("jwSetMute",a):l("jwSetMute",!l("jwGetMute"));return g};g.lock=function(){return g};g.unlock=function(){return g};g.load=function(a){l("jwLoad",a);return g};g.playlistItem=function(a){l("jwPlaylistItem",parseInt(a));return g};g.playlistPrev=function(){l("jwPlaylistPrev");return g};g.playlistNext=function(){l("jwPlaylistNext");return g};g.resize=function(a,b){if("flash"!=g.renderingMode){var c=document.getElementById(g.id);c.className=c.className.replace(/\s+aspectMode/,"");c.style.display="block";l("jwResize",a,b)}else{var c=e.getElementById(g.id+"_wrapper"),d=e.getElementById(g.id+"_aspect");d&&(d.style.display="none");c&&(c.style.display="block",c.style.width=k.styleDimension(a),c.style.height=k.styleDimension(b))}return g};g.play=function(a){"undefined"==typeof a?(a=g.getState(),a==h.PLAYING||a==h.BUFFERING?l("jwPause"):l("jwPlay")):l("jwPlay",a);return g};g.pause=function(a){"undefined"==typeof a?(a=g.getState(),a==h.PLAYING||a==h.BUFFERING?l("jwPause"):l("jwPlay")):l("jwPause",a);return g};g.stop=function(){l("jwStop");return g};g.seek=function(a){l("jwSeek",a);return g};g.setVolume=function(a){l("jwSetVolume",a);return g};g.loadInstream=function(a,b){return w=new j.instream(this,t,a,b)};g.getQualityLevels=function(){return l("jwGetQualityLevels")};g.getCurrentQuality=function(){return l("jwGetCurrentQuality")};g.setCurrentQuality=function(a){l("jwSetCurrentQuality",a)};g.getCaptionsList=function(){return l("jwGetCaptionsList")};g.getCurrentCaptions=function(){return l("jwGetCurrentCaptions")};g.setCurrentCaptions=function(a){l("jwSetCurrentCaptions",a)};g.getControls=function(){return l("jwGetControls")};g.getSafeRegion=function(){return l("jwGetSafeRegion")};g.setControls=function(a){l("jwSetControls",a)};g.destroyPlayer=function(){l("jwPlayerDestroy")};g.playAd=function(a){l("jwPlayAd",a)};var y={onBufferChange:f.JWPLAYER_MEDIA_BUFFER,onBufferFull:f.JWPLAYER_MEDIA_BUFFER_FULL,onError:f.JWPLAYER_ERROR,onSetupError:f.JWPLAYER_SETUP_ERROR,onFullscreen:f.JWPLAYER_FULLSCREEN,onMeta:f.JWPLAYER_MEDIA_META,onMute:f.JWPLAYER_MEDIA_MUTE,onPlaylist:f.JWPLAYER_PLAYLIST_LOADED,onPlaylistItem:f.JWPLAYER_PLAYLIST_ITEM,onPlaylistComplete:f.JWPLAYER_PLAYLIST_COMPLETE,onReady:f.API_READY,onResize:f.JWPLAYER_RESIZE,onComplete:f.JWPLAYER_MEDIA_COMPLETE,onSeek:f.JWPLAYER_MEDIA_SEEK,onTime:f.JWPLAYER_MEDIA_TIME,onVolume:f.JWPLAYER_MEDIA_VOLUME,onBeforePlay:f.JWPLAYER_MEDIA_BEFOREPLAY,onBeforeComplete:f.JWPLAYER_MEDIA_BEFORECOMPLETE,onDisplayClick:f.JWPLAYER_DISPLAY_CLICK,onControls:f.JWPLAYER_CONTROLS,onQualityLevels:f.JWPLAYER_MEDIA_LEVELS,onQualityChange:f.JWPLAYER_MEDIA_LEVEL_CHANGED,onCaptionsList:f.JWPLAYER_CAPTIONS_LIST,onCaptionsChange:f.JWPLAYER_CAPTIONS_CHANGED,onAdError:f.JWPLAYER_AD_ERROR,onAdClick:f.JWPLAYER_AD_CLICK,onAdImpression:f.JWPLAYER_AD_IMPRESSION,onAdTime:f.JWPLAYER_AD_TIME,onAdComplete:f.JWPLAYER_AD_COMPLETE,onAdCompanions:f.JWPLAYER_AD_COMPANIONS};k.foreach(y,function(a){g[a]=c(y[a],p)});var z={onBuffer:h.BUFFERING,onPause:h.PAUSED,onPlay:h.PLAYING,onIdle:h.IDLE};k.foreach(z,function(a){g[a]=c(z[a],n)});g.remove=function(){if(!u){throw"Cannot call remove() before player is ready"}v=[];j.destroyPlayer(this.id)};g.setup=function(a){if(d.embed){var b=e.getElementById(g.id);b&&(a.fallbackDiv=b);b=g;v=[];j.destroyPlayer(b.id);b=d(g.id);b.config=a;return new d.embed(b)}return g};g.registerPlugin=function(a,b,c,e){d.plugins.registerPlugin(a,b,c,e)};g.setPlayer=function(a,b){t=a;g.renderingMode=b};g.detachMedia=function(){if("html5"==g.renderingMode){return l("jwDetachMedia")}};g.attachMedia=function(a){if("html5"==g.renderingMode){return l("jwAttachMedia",a)}};g.dispatchEvent=function(a,b){if(q[a]){for(var c=k.translateEventResponse(a,b),d=0;d<q[a].length;d++){if("function"==typeof q[a][d]){try{a==f.JWPLAYER_PLAYLIST_LOADED&&k.deepReplaceKeyName(c.playlist,["__dot__","__spc__","__dsh__","__default__"],["."," ","-","default"]),q[a][d].call(this,c)}catch(e){k.log("There was an error calling back an event handler")}}}}};g.dispatchInstreamEvent=function(a){w&&w.dispatchEvent(a,arguments)};g.callInternal=l;g.playerReady=function(a){u=!0;t||g.setPlayer(e.getElementById(a.id));g.container=e.getElementById(g.id);k.foreach(q,function(a){m(t,a)});p(f.JWPLAYER_PLAYLIST_ITEM,function(){x={}});p(f.JWPLAYER_MEDIA_META,function(a){k.extend(x,a.metadata)});for(g.dispatchEvent(f.API_READY);0<v.length;){l.apply(this,v.shift())}};g.getItemMeta=function(){return x};g.isBeforePlay=function(){return t.jwIsBeforePlay()};g.isBeforeComplete=function(){return t.jwIsBeforeComplete()};return g};j.selectPlayer=function(b){var c;k.exists(b)||(b=0);b.nodeType?c=b:"string"==typeof b&&(c=e.getElementById(b));return c?(b=j.playerById(c.id))?b:j.addPlayer(new j(c)):"number"==typeof b?a[b]:null};j.playerById=function(b){for(var c=0;c<a.length;c++){if(a[c].id==b){return a[c]}}return null};j.addPlayer=function(b){for(var c=0;c<a.length;c++){if(a[c]==b){return b}}a.push(b);return b};j.destroyPlayer=function(b){for(var c=-1,d,f=0;f<a.length;f++){a[f].id==b&&(c=f,d=a[f])}0<=c&&(b=d.id,f=e.getElementById(b+("flash"==d.renderingMode?"_wrapper":"")),k.clearCss&&k.clearCss("#"+b),f&&("html5"==d.renderingMode&&d.destroyPlayer(),d=e.createElement("div"),d.id=b,f.parentNode.replaceChild(d,f)),a.splice(c,1));return null};d.playerReady=function(a){var c=d.api.playerById(a.id);c?c.playerReady(a):d.api.selectPlayer(a.id).playerReady(a)}}(jwplayer),function(d){var a=d.events,k=d.utils,f=a.state;d.api.instream=function(d,e,j,b){function c(a,b){l[a]||(l[a]=[],p.jwInstreamAddEventListener(a,'function(dat) { jwplayer("'+m.id+'").dispatchInstreamEvent("'+a+'", dat); }'));l[a].push(b);return this}function n(b,d){g[b]||(g[b]=[],c(a.JWPLAYER_PLAYER_STATE,function(a){var c=a.newstate,d=a.oldstate;if(c==b){var e=g[c];if(e){for(var f=0;f<e.length;f++){"function"==typeof e[f]&&e[f].call(this,{oldstate:d,newstate:c,type:a.type})}}}}));g[b].push(d);return this}var m=d,p=e,l={},g={};this.dispatchEvent=function(a,b){if(l[a]){for(var c=k.translateEventResponse(a,b[1]),d=0;d<l[a].length;d++){"function"==typeof l[a][d]&&l[a][d].call(this,c)}}};this.onError=function(b){return c(a.JWPLAYER_ERROR,b)};this.onFullscreen=function(b){return c(a.JWPLAYER_FULLSCREEN,b)};this.onMeta=function(b){return c(a.JWPLAYER_MEDIA_META,b)};this.onMute=function(b){return c(a.JWPLAYER_MEDIA_MUTE,b)};this.onComplete=function(b){return c(a.JWPLAYER_MEDIA_COMPLETE,b)};this.onTime=function(b){return c(a.JWPLAYER_MEDIA_TIME,b)};this.onBuffer=function(a){return n(f.BUFFERING,a)};this.onPause=function(a){return n(f.PAUSED,a)};this.onPlay=function(a){return n(f.PLAYING,a)};this.onIdle=function(a){return n(f.IDLE,a)};this.onClick=function(b){return c(a.JWPLAYER_INSTREAM_CLICK,b)};this.onInstreamDestroyed=function(b){return c(a.JWPLAYER_INSTREAM_DESTROYED,b)};this.play=function(a){p.jwInstreamPlay(a)};this.pause=function(a){p.jwInstreamPause(a)};this.destroy=function(){p.jwInstreamDestroy()};this.setText=function(a){p.jwInstreamSetText(a?a:"")};m.callInternal("jwLoadInstream",j,b?b:{})}}(jwplayer),function(d){var a=d.api,k=a.selectPlayer;a.selectPlayer=function(a){return(a=k(a))?a:{registerPlugin:function(a,e,f){d.plugins.registerPlugin(a,e,f)}}}}(jwplayer));
	
	var pros = {
		init: function(cfg){
			var me = this;

			me.selectedSourceIdx = 0;
			me.videoContainerDom = $(me.defConfig.videoContainerDom);
			me.sources=phoenix.Games.LLN115.Config.getInstance().defConfig.sourceList;
			//me.getSourceList();
			me.bindEvent();
		},
		getSourceList: function(){
			this.sources = [
				'src=rtmp://103.17.41.44/live/ll115livestream&streamType=live&configuration=rtmp://103.17.41.44/live/ll115livestream&initialBufferTime=5&idx=1',
				'src=rtmp://103.17.41.44/live/ll115livestream&streamType=live&configuration=rtmp://103.17.41.44/live/ll115livestream&initialBufferTime=5&idx=2',
				'src=rtmp://103.17.41.44/live/ll115livestream&streamType=live&configuration=rtmp://103.17.41.44/live/ll115livestream&initialBufferTime=5&idx=3'
			];
		},
		buildVideo: function(){
			var me = this,
				flashvars = me.sources[me.selectedSourceIdx] || 'src=rtmp://103.17.41.44/live/ll115livestream&streamType=live&configuration=rtmp://103.17.41.44/live/ll115livestream&initialBufferTime=5';
			videoHtml = me.defConfig.videoHtml.replace(/{{flashvars}}/g, flashvars);
			return me.videoContainerDom.html(videoHtml);
		},
		//绑定按钮事件
		bindEvent: function(){
			var me = this,
				videoHtml = me.defConfig.videoHtml,
				videoContainerDom = $(me.defConfig.videoContainerDom);

			$(me.defConfig.buttonDom).bind('click', function(){

				if(me.videoContainerDom.is(':hidden')){
					me.buildVideo().show();
				}else{
					me.videoContainerDom.html('').hide();
				}

				// if(me.videoContainerDom.is(':hidden')){
				// 	me.videoContainerDom.show();
				// }else{
				// 	me.videoContainerDom.hide();
				// }
			});

			me.videoContainerDom.delegate('span[data-action]', 'click', function(){
				if( $(this).hasClass('refresh') ){
					me.selectedSourceIdx += 1;
					if( me.selectedSourceIdx >= me.sources.length ){
						me.selectedSourceIdx = 0;
					}
					me.buildVideo();
				}else if( $(this).hasClass('close') ){
					me.videoContainerDom.html('').hide();
				}
			});

			/*me.videoContainerDom.bind('mousemove', function(){
				var toolBarDom =  $(this).find(me.defConfig.toolBarDomText);

				if(animateTime){
					clearTimeout(animateTime);
					animateTime = null;
				}else{
					toolBarDom.fadeIn(100);
				}

				animateTime = setTimeout(function(){
					toolBarDom.fadeOut(300);
					animateTime=null;
				}, 3000)
			});

			//暂停
			$(me.defConfig.toolBarDomText).on('click', '#stop_video', function(){
				
				if(jwplayer().getState() == 'PLAYING'){
					jwplayer().pause();
					$('#stop_video').hide();
					$('#start_video').show();
				}
			})

			//开始
			$(me.defConfig.toolBarDomText).on('click', '#start_video', function(){
				
				if(jwplayer().getState() == 'PAUSED'){
					jwplayer().play();
					$('#start_video').hide();
					$('#stop_video').show();
				}
			})*/
		},
		loadingShow: function() {
			var me = this,
				loadingDom = $(defConfig['loadingDom']);

				if(loadingDom.is(':hidden')){
            		loadingDom.show();
            	}
		},
		loadingHide: function() {
			var me = this,
				loadingDom = $(defConfig['loadingDom']);

            	if(!loadingDom.is(':hidden')){
            		loadingDom.hide();
            	}
		},
		startButtonShow: function() {

		},
		startButtonHide: function() {

		},
		//视频初始化
		videoInit: function(){
			var me = this,
				defConfig = me.defConfig;		

			// swfobject.embedSWF('../images/game/llssc/pl.swf','player','100%','100%','9.0.124','false',
			// 	flashvars,
			// 	params,
			// 	attributes,
			// 	me.flashReady()
			// 	);

		    jwplayer('player').setup({
		        file: 'rtmp://fms2.lucky188.cn/live/livestream',
		        width: '100%',
		        height: '100%',
		        primary: "flash",
			    controls: false,
		        autostart: 'true',
		        primary: 'flash',
		        events: {
		            //播放器准备就绪
		            onReady: function() {
		            	//

		            },
		            onPlay: function() {
		            	
		            	me.loadingHide();
		            },
		            onBuffer: function() {
		            	
		            	me.loadingShow();
		            },
		            onPause: function() {
		            	
		            	me.loadingHide();
		            },
		            onMeta : function(evt) {
		            	var bandwidth = 0;
		            	//判断带宽参数
		            	if(typeof evt['metadata'] != 'undefined' && typeof evt['metadata']['bandwidth'] != 'undefined'){
		            		bandwidth = Number(evt['metadata']['bandwidth']);

		            		//console.log(this.getState());
		            		//console.log(bandwidth);
		            		me.mathTimeRound(bandwidth);
		            	}
		            }
		         }
		    });
		},
		mathTimeRound: function(num) {
			var me = this;

			if(roundNum == 30){
				me.doBandWidthControl(num);
				roundNum = 0;
				return;
			}

			//循环计时
			roundNum++;

		},
		doBandWidthControl: function(num){
			var me = this;

			timeArray.push(num);

			if(timeArray.length == 9){
				timeArray.shift();				
			}

			//如果8段时间点都为同一网速
			//说明视频已经停滞应该初始化
			if(timeArray.length == 8 && me.arrayDataEqual(timeArray)){

				//停滞状态为播放
				if(jwplayer().getState() == 'PLAYING' || jwplayer().getState() == 'BUFFERING') {
					jwplayer().stop();
					timeArray= [];

					setTimeout(function(){
						jwplayer().play();
					},1000);
				}
			}
		},
		//判断内部数据是否都相等
		arrayDataEqual: function(arrayData) {
			var result;

			for (var i = arrayData.length - 1; i >= 0; i--) {
				var num = 0;
				for (var j = arrayData.length - 1; j >= 0; j--) {
					if(arrayData[i] == arrayData[j]){
						num++;
					}
				};
				if(num == arrayData.length) {
					return true;
				}
			};

			return false;
		}
	};
	
	var Main = host.Class(pros, message);
		Main.defConfig = defConfig;
		//游戏控制单例
		Main.getInstance = function(cfg){
			return instance || (instance = new Main(cfg));
		};
	host.Games.LLN115[name] = Main;
	
})(phoenix, "Video", phoenix.GameMessage);

$(function(){
	var video = new phoenix.Games.LLN115.Video();
});








(function(host, name, GameMethod, undefined){
	var defConfig = {
		name:'wuxing.zhixuan.danshi',
		//iframe编辑器
		editorobj: '.content-text-balls',
		//FILE上传按钮
		uploadButton: '#file',
		//单式导入号码示例
		exampleText: '12345 33456 87898 <br />12345 33456 87898 <br />12345 33456 87898 ',
		//玩法提示
		tips: '五星直选单式玩法提示',
		//选号实例
		exampleTip: '这是单式弹出层提示',
		//中文 全角符号  中文
		checkFont: /[\u4E00-\u9FA5]|[/\n]|[/W]/g,
		//过滤方法
		filtration: /[；|;]+|<br>+|[,|，]+/g,
		//验证是否纯数字
		checkNum: /^[0-9]*$/,
		//单式玩法提示
		normalTips : '<p style="color:#888;font-size:12px;line-height:170%;">' + ['说明：',
					'1、请参照"标准格式样本"格式录入或上传方案。',
					'2、每一注号码之间的间隔符支持 回车 逗号[,] 分号[;] 冒号[:] 竖线 [|] 每注内间隔使用空格即可。',
					'3、文件格式必须是.txt格式。',
					'4、文件较大时会导致上传时间较长，请耐心等待！',
					'5、将文件拖入文本框即可快速实现文件上传功能。',
					'6、导入文本内容后将覆盖文本框中现有的内容。'].join('<br>') + '</p>'
		
	},
	Games = host.Games,
	LLN115 = host.Games.LLN115;
	
	//定义方法
	var pros = {
		init:function(cfg){
			var me = this;

			//IE Range对象
			me.ieRange = '';
			//正确结果
			me.vData = [];
			//所有结果
			me.aData = [];
			
			me.tData = [];
			//出错提示记录
			me.errorData = [];
			//重复记录
			me.sameData = [];
			//初级触发
			me.firstfocus = true;
			//机选标记
			me.ranNumTag = false;
			//是否初次进行投注
			me.isFirstAdd = true;

			//在单式的情况下点击选择按钮
			//复位所有的彩种状态栏
			Games.getCurrentGameStatistics().addEvent('afterStatisReset', function(e, orderData){
				var that = this,
					cfg = that.defConfig;
					methodName = that.getGameMethodName();

				if(methodName == me.defConfig.name){
					that.multipleDom.setValue(cfg.multiple);
					that.moneyUnitDom.setValue(cfg.moneyUnit);
				}
			});
			
			Games.getCurrentGameOrder().addEvent('beforeAdd', function(e, orderData){
				var that = this,
					data = me.tData,
					html = '';

				if(orderData['type'] == me.defConfig.name){
					
					//使用去重后正确号码进行投注
					if(me.isFirstAdd){
						if(!me['ranNumTag']){
							orderData['lotterys'] = [];
							me.isFirstAdd = null;
							//重新输出去重后号码
							me.updateData();
							Games.getCurrentGameOrder().add(Games.getCurrentGameStatistics().getResultData());						
						}						
					}else{
						//如果存在重复和错误号进行提示
						if(me.errorData.join('') != '' || me.sameData.join('') != ''){
							me.ballsErrorTip();
						}
						//检测单式上传是否已经超过限注
						me.checkLimitBall(orderData);
						me.isFirstAdd = true;
					}
				}

			});
		},
		initFrame:function(){
			var me = this;
			me.win = me.container.find(me.defConfig.editorobj)[0].contentWindow;
			me.doc = me.win.document;
			
			me._bulidEditDom();

			//查看标准格式样本按钮
			var tip = host.Tip.getInstance();
			me.container.find('.balls-example-danshi-tip').click(function(e){
				e.preventDefault();
				var dom = $(this);
				tip.setText(me.getExampleText());
				tip.show(dom.outerWidth() + 10, 0, this);
			}).mouseout(function(){
				tip.hide();
			});
			
		},
		getExampleText:function(){
			return this.defConfig.exampleText;
		},
		rebuildData:function(){
			var me = this;
			me.balls = [
						[-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1]
						];
		},
		buildUI:function(){
			var me = this;
			me.container.html(me.getHTML());
		},
		//单式不能反选
		reSelect:function(){
			
		},
		//单式没有选球dom
		batchSetBallDom:function(){
			
		},
		//获取默认提示文案
		getNormalTips: function(){
			return this.defConfig.normalTips
		},
		//显示默认提示文案
		showNormalTips: function(){
			var me = this;
			me.replaceText(me.getNormalTips.call(me));
			me.firstfocus = true;
		},
		//建立可编辑的文字区域
		_bulidEditDom: function(){
			var me = this,
				headHTML =	'';

			me.doc.designMode = 'On';//可编辑
			me.doc.contentEditable = true;
			//但是IE与FireFox有点不同，为了兼容FireFox，所以必须创建一个新的document。
			me.doc.open();
			headHTML='<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />';
			headHTML=headHTML+'<style>*{margin:0;padding:0;font-size:14px;}</style>';
			headHTML=headHTML+'</head>';
			me.doc.writeln('<html>'+headHTML+'<body style="word-break: break-all">' + me.getNormalTips() + '</body></html>');
			me.doc.close();
			// //FOCUS光标	
			// if(!document.all){
			// 	me.win.focus();
			// }else{
			// 	me.doc.body.focus();
			// }
			//绑定事件
			me.bindPress();
			//IE回车输出<br> 与 FF 统一；
			if(document.all){
				me.doc.onkeypress = function(){
					return me._ieEnter()
				};
			};
		},
		//IE回车修改
		_ieEnter: function(){
			var me = this,
				e = me.win.event;
			if(e.keyCode == 13){
				this._saveRange();
				this._insert("<br/>");
				return false;
			}
		},
		//编辑器中插入文字
		_insert: function(text) {//插入替换字符串
			var me = this;
				
			if (!!me.ieRange) {
				me.ieRange.pasteHTML(text);
				me.ieRange.select();
				me.ieRange = false; //清空下range对象
			} else {//焦点不在html编辑器内容时
				me.win.focus();
				if (document.all) {
					me.doc.body.innerHTML += text; //IE插入在最后
				} else {//Firefox
					var sel = win.getSelection();
					var rng = sel.getRangeAt(0);
					var frg = rng.createContextualFragment(text);
					rng.insertNode(frg);
				}
			}
		},
		//IE下保存Range对象
		_saveRange: function(){
			if(!!document.all&&!me.ieRange){//是否IE并且判断是否保存过Range对象
				var sel = me.doc.selection;
				me.ieRange = sel.createRange();
				if(sel.type!='Control'){//选择的不是对象
					var p = me.ieRange.parentElement();//判断是否在编辑器内
					if(p.tagName=="INPUT"||p == document.body)me.ieRange=false;
				}
			}
		},
		//返回结果HTML
		getHtml: function(){
			var me = this;
			return me.doc ? $(me.doc.body).html() : '';
		},
		//返回结果text
		getText: function(){
			var me = this;
			return me.doc ? $(me.doc.body).text() : '';
		},
		//修改HTML
		//返回结果HTML
		replaceText: function(text){
			var me = this,
				text = text.replace(/\n/g, '<br>');
				
			if(me.doc && text){
				$(me.doc.body).html(text);
			}
		},
		//绑定IFRAME按钮PRESS
		bindPress: function(){
			var me = this,
				uploadButton = me.container.find(me.defConfig.uploadButton),
				agentValue = window.navigator.userAgent.toLowerCase();
			//绑定按钮事件
			$(me.doc).bind('input',function(){
				me.updateData();
			})
			//iE不支持INPUT事件
			//而且IE propertychange事件不能绑定该DOM类型
			if(agentValue.indexOf('msie')>0){
				$(me.doc.body).bind('keyup',function(){
					me.updateData();
				})
				$(me.doc.body).bind('blur',function(){
					me.updateData();
				})
			}
			$(me.doc).bind('focus',function(){
				if(me.firstfocus){
					me.replaceText(' ');
					me.firstfocus = false;
				}	
			})
			$(me.doc).bind('blur',function(){
				var content = me.getText();
				if($.trim(content) == ''){
					me.showNormalTips();
				}
			})
			$(me.doc.body).bind('focus',function(){
				if(me.firstfocus){
					me.replaceText(' ');
					me.firstfocus = false;
				}	
			})
			$(me.doc.body).bind('blur',function(){
				var content = me.getText();
				if($.trim(content) == ''){
					me.showNormalTips();
				}
			})
			//绑定用户上传按钮
			uploadButton.bind('change', function(){
				var form = $(this).parent();
				me.checkFile(this, form);
			})
		},
		//处理各种结果
		iterator: function(data, filtr) {
			var me= this,
				cfg = me.defConfig,
				result = data,
				count = [],
				breakNum = 0,
				current = '',
				offset = 0;
			for (var i = 0; i < data.length; i++) {
				if(filtr.test(data.charAt(i))){
					current = data.substr(breakNum, i - breakNum);
					if(current != ''){
						count.push(current);
					}
					breakNum = i+1;
				}
			};
			return count;
		},
		//检测结果重复
		checkResult: function(data, array){
			//检查重复
			for (var i = array.length - 1; i >= 0; i--) {
				if(array[i] == data){
					return false;
				}
			};
			return true;
		},
		//正则过滤输入框HTML
		//提取正确的投注号码
		filterLotters:function(data){
			var me = this,
				result = '';

			result = data.replace(/\s+/gi, ' ');
			result = result.replace(/<(?:"[^"]*"|'[^']*'|[^>'"]*)+>/g, ',');
			result = result.replace(me.defConfig.checkFont,'') +  ',';
			result = result.replace(/<br>+|&nbsp;+|[,;，；:：|]+/gi, ',');

			return result;
		},
		//检测单注球是否在选球范围[1 - 11]
		checkBallGroup: function(arr){

			for (var i = arr.length - 1; i >= 0; i--) {
				if(arr[i] < 1 && arr[i] > 11){
					return true;
				}
			};

			return false;
		},
		//检测数组内部是否有重复
		checkArrayInnerSame: function(arr){
			var hash = {};
		    for(var i in arr) {
		        if(hash[arr[i]])
		            return true;
		        hash[arr[i]] = true;
		    }
		    return false;
		},
		//检测单注号码是否通过
		checkSingleNum: function(lotteryNum){
			var me = this;

			return me.defConfig.checkNum.test(lotteryNum) && $.trim(lotteryNum).length == 2 && Number(lotteryNum) > 0 && Number(lotteryNum) < 12;
		},
		//检测选球是否完整，是否能形成有效的投注
		//并设置 isBallsComplete 
		checkBallIsComplete:function(data){
			var me = this,
				i = 0,
				current = 999,
				result = [];

				me.aData = [];
				me.vData = [];
				me.sameData = [];
				me.errorData = [];
				me.tData = [];

			//按规则进行拆分结果
			result = me.iterator(me.filterLotters(data), me.defConfig.filtration) || [];
			//判断结果
			for(i = 0;i < result.length;i++){
				//当前结果
				current = $.trim(result[i]);
		
				//判断单注规则
				if(me.checkSingleNum(current)){
					if(me.checkResult(current, me.tData)){
						//正确结果[已去重]
						me.tData.push(current);
					}else{
						if(me.checkResult(current, me.sameData)){
							//重复结果
							me.sameData.push(current);
						}
					}
					//正确结果[不去重]
					me.vData.push(current);
				}else{
					if(me.checkResult(current, me.errorData)){
						//错误结果[已去重]
						me.errorData.push(current);
					}else{
						//错误重复结果
						me.sameData.push(current);
					}
				}
				//所有结果[已去重]
				if(me.checkResult(current, me.aData)){
					me.aData.push(current);
				}
			}
			
			//校验
			if(me.tData.length > 0){
				me.isBallsComplete = true;
				if(me.isFirstAdd){
					return me.vData;
				}else{
					return me.tData;	
				}
			}else{
				me.isBallsComplete = false;
				return [];
			}
		},
		//返回正确的索引
		countInstances: function(mainStr, subStr){
			var count = [];
			var offset = 0;
			do{
				offset = mainStr.indexOf(subStr, offset);
				if(offset != -1){
					count.push(offset);
					offset += subStr.length;
				}
			}while(offset != -1)
			return count;
		},
		//三项操作提示
		//显示正确项
		//排除错误项
		removeOrderError: function(){
			var me= this, result = me.vData.length > 0 ? '' : ' ';
			if(me.firstfocus){
				return;
			}
			// for (var i = 0; i < me.vData.length; i++) {
			// 	result += me.vData[i].join('') + '&nbsp;';
			// };
			result = me.vData.join(',') + '&nbsp;';
			me.errorDataTips();
			if($.trim(result) == ''){
				me.showNormalTips();
				return;
			}
			me.replaceText(result);
			me.checkBallIsComplete(result);
		},
		//排除重复项
		removeOrderSame: function(){
			var me= this, result = me.aData.length > 0 ? '' : ' ';
			if(me.firstfocus){
				return;
			}
			// for (var i = 0; i < me.aData.length; i++) {
			// 	result += me.aData[i].join('') + '&nbsp;';
			// }
			result = me.aData.join(',') + '&nbsp;';
			me.sameDataTips();
			me.replaceText(result);
			me.checkBallIsComplete(result);
		},
		//清空选区
		removeOrderAll: function(){
			var me=this;
			if(me.firstfocus){
				return;
			}
			me.replaceText(' ');
			me.sameData = [];
			me.aData = [];
			me.tData = [];
			me.vData = [];
			//清空选号状态
			Games.getCurrentGameStatistics().reSet();
			me.showNormalTips();
		},
		//检测上传
		checkFile: function(dom, form){
			var result = dom.value,
				fileext=result.substring(result.lastIndexOf("."),result.length),
				fileext=fileext.toLowerCase();

			if(result == ''){
				return;
			}
			
			if (fileext != '.txt') {
				alert("对不起，导入数据格式必须是.txt格式文件哦，请您调整格式后重新上传，谢谢 ！");            
				return false;
			}
			form[0].submit();
		},
		//接收文件
		getFile: function(result){
			var me = this,
				resetDom = me.container.find(':reset');

			if(!result){return};
			me.replaceText(result);
			me.firstfocus = false;
			me.updateData();
			resetDom.click();
		},
		//出错提示
		//暂时搁置
		errorTip: function(html, data){
			var me = this,
				start, end,
				indexData = [];
		},
		sameDataTips: function(){
			var me = this,
				sameData = me.sameData,
				sameDataHtmlText = '',
				sameGroupText = '',
				msg = Games.getCurrentGameMessage(),
				indexData = [];

			if(sameData.join('') == ''){
				return;
			};
			sameDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码重复，已进行自动过滤</h4><p class="pop-text" style="display:block">' + sameData.join(', ') + '</p>';
			msg.show({
				mask: true,
				content : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">' + sameDataHtmlText + '</div>',
								'</div>',
							'</div>'].join(''),
				closeIsShow: true,
				closeFun: function(){
					this.hide();
				}
			});
		},
		errorDataTips: function(){
			var me = this,
				errorData = me.errorData,
				errorDataHtmlText = '',
				errorGroupText = '',
				msg = Games.getCurrentGameMessage(),
				indexData = [];

			if(errorData.join('') == ''){
				return;
			};
			errorDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码错误，已进行自动过滤</h4><p class="pop-text" style="display:block">' + errorData.join(', ') + '</p>';
			msg.show({
				mask: true,
				content : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">' + errorDataHtmlText + '</div>',
								'</div>',
							'</div>'].join(''),
				closeIsShow: true,
				closeFun: function(){
					this.hide();
				}
			});
		},
		//单式出错提示
		ballsErrorTip: function(html, data){
			var me = this,
				errorData = me.errorData,
				sameData = me.sameData,
				errorDataHtmlText = '',
				sameDataHtmlText = '',
				errorGroupText = '',
				sameGroupText = '',
				msg = Games.getCurrentGameMessage(),
				indexData = [];
		
			//重复号码
			if(sameData.join('') != ''){
				sameDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码重复，已进行自动过滤</h4><p class="pop-text" style="display:block">' + sameData.join(', ') + '</p>';
			}
			//错误号码
			if(errorData.join('') != ''){
				errorDataHtmlText = '<h4 class="pop-text" style="display:block;font-weight:bold">以下号码错误，已进行自动过滤</h4><p class="pop-text" style="display:block">' + errorData.join(', ') + '</p>';
			}

			msg.show({
				mask: true,
				content : ['<div class="bd text-center">',
								'<div class="pop-waring">',
									'<i class="ico-waring <#=icon-class#>"></i>',
									'<div style="display:inline-block;*zoom:1;*display:inline;vertical-align:middle">' + sameDataHtmlText + errorDataHtmlText + '</div>',
								'</div>',
							'</div>'].join(''),
				closeIsShow: true,
				closeFun: function(){
					this.hide();
				}
			});
		},
		//复位
		//单式需提到子类方法实现
		reSet:function(){
			var me = this;
			me.isBallsComplete = false;
			me.rebuildData();
			me.updateData();
			if(!me.ranNumTag){
				me.showNormalTips();
			};
			//重置机选标记
			me.removeRanNumTag();
		},
		//生成后端参数格式
		makePostParameter: function(data, order){
			var me = this,
				result = [],
				data = order['lotterys'],
				i = 0;
			for (; i < data.length; i++) {
				result = result.concat(data[i]);
			}
			return result.join(',');
		},
		getTdata : function(){
			return this.tData; 
		},
		//获取组合结果
		getLottery:function(){
			var me = this, data = me.getHtml();
			if(data == ''){
				return;
			}
			//返回投注
			return me.checkBallIsComplete(data);
		},
		//单组去重处理
		removeSameNum: function(data) {
			var i = 0, result, me = this,
				numLen = this.getBallData()[0].length;
				len = data.length;
			result = Math.floor(Math.random() * numLen);
			for(;i<data.length;i++){
				if(result == data[i]){
					return arguments.callee.call(me, data);
				}
			}
			return result;
		},
		//清空重复号码记录
		emptySameData: function(){
			this.sameData  = [];
		},
		//清空错误号码记录
		emptyErrorData: function(){
			this.errorData = [];
		},
		//增加单式机选标记
		addRanNumTag: function(){
			var me = this;
			me.ranNumTag = true;
			me.emptySameData();
			me.emptyErrorData();
		},
		//去除单式机选标记
		removeRanNumTag: function(){
			this.ranNumTag = false;
		},
		getOriginal:function(){
			var me = this,balls = me.getBallData(),
				len = balls.length,
				len2 = balls[0].length;
				i = 0,
				j = 0,
				row = [],
				tData = me.getTdata(),
				data = me.getHtml(),
				result = [];

			for(;i < len;i++){
				row = [];
				for(j = 0;j < len2;j++){
					if(balls[i][j] > 0){
						row.push(j);
					}
				}
				result.push(row);
			};
			if(tData.length > 0){
				result[0][0] = me.getTdata().join(',');
			}
			return result;
		},
		//生成单注随机数
		createRandomNum: function(){
			var me = this,
				current = [],
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length;
			//随机数
			for(var k=0;k < len; k++){
				var num = Math.ceil(Math.random() * (rowLen - 1));
				if(num < 10){
					num = '0' + num;
				}
				current[k] = [num];
			};	
			return current;
		},
		//限制随机投注重复
		checkRandomBets: function(hash,times){
			var me = this,
				allowTag = typeof hash == 'undefined' ? true : false,
				hash = hash || {},
				current = [],
				times = times || 0,
				len = me.getBallData().length,
				rowLen = me.getBallData()[0].length,
				order = Games.getCurrentGameOrder().getTotal()['orders'];

			//生成单注
			current = me.createRandomNum();

			//如果大于限制数量
			//则直接输出
			if(Number(times) > Number(me.getRandomBetsNum())){
				return current;
			}
			//建立索引
			if(allowTag){
				for (var i = 0; i < order.length; i++) {
					if(order[i]['type'] == me.defConfig.name){
						var name = order[i]['original'].join('');
						hash[name] = name;
					}
				};
			};
			//对比结果
			if(hash[current.join('')]){
				times++;
				return arguments.callee.call(me, hash, times);
			}
			return current;
		},
		//生成一个当前玩法的随机投注号码
		//该处实现复式，子类中实现其他个性化玩法
		//返回值： 按照当前玩法生成一注标准的随机投注单(order)
		randomNum:function(){
			var me = this,
				i = 0, 
				current = [], 
				currentNum, 
				ranNum,
				order = null,
				dataNum = me.getBallData(),
				name = me.defConfig.name,
				lotterys = [],
				original = [];
			
			//增加机选标记
			me.addRanNumTag();

			current  = me.checkRandomBets();
			original = current;
			lotterys = me.combination(original);
				
			//生成投注格式
			order = {
				'type':  Games.getCurrentGame().getCurrentGameMethod().getGameMethodName(),
				'original':original,
				'lotterys':lotterys,
				'moneyUnit': Games.getCurrentGameStatistics().getMoneyUnit(),
				'multiple': Games.getCurrentGameStatistics().getMultip(),
				'onePrice': Games.getCurrentGameStatistics().getOnePrice(),
				'num': lotterys.length
			};
			order['amountText'] = Games.getCurrentGameStatistics().formatMoney(order['num'] * order['moneyUnit'] * order['multiple'] * order['onePrice']);
			return order;		
		},
		getHTML:function(){
			//html模板
			var html_all = [];
				html_all.push('<div class="balls-import clearfix">');
					html_all.push('<form id="form1" name="form1" enctype="multipart/form-data" method="post" action="'+Games.getCurrentGame().getGameConfig().getInstance().getUploadPath()+'"  target="check_file_frame" style="position:relative;padding-bottom:10px;">');
					html_all.push('<input name="file" type="file" id="file" size="40" hidefocus="true" value="导入" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;position:absolute;top:0px; left:0px; width:107px; height:30px;z-index:1;background:#000" />');
					html_all.push('<input type="button" value="导入注单" class="balls-import-input" value="" onclick=document.getElementById("form1").file.click()><a class="balls-example-danshi-tip" href="#">查看标准格式样本</a>');
					html_all.push('<iframe src="" name="check_file_frame" style="display:none;"></iframe>');
					html_all.push('<input type="reset" style="outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;width:0px; height:0px;z-index:1;background:#000" />');
					html_all.push('</form>');
					html_all.push('<div class="panel-select" ><iframe style="width:100%;height:100%;border:0 none;background-color:#F9F9F9;" class="content-text-balls"></iframe></div>');
					html_all.push('<div class="panel-btn">');
					html_all.push('<a class="remove-error" id="" href="javascript:void(0);"><i></i>删除错误项</a>');
					html_all.push('<a class="remove-same" id="" href="javascript:void(0);"><i></i>删除重复项</a>');
					html_all.push('<a class="remove-all" id="" href="javascript:void(0);"><i></i>清空文本框</a>');
					html_all.push('</div>');
				html_all.push('</div>');
			return html_all.join('');
		}
	};


	
	var Main = host.Class(pros, GameMethod);
		Main.defConfig = defConfig;
		LLN115[name] = Main;
		
})(phoenix, 'Danshi', phoenix.GameMethod);
