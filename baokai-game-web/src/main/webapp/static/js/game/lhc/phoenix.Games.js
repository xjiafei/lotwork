/**
 * Created by user on 16/3/22.
 */

//Games
(function(host, name, undefined){

    var Main = {};
    //缓存
    Main.cacheData = {};

    //当前游戏
    Main.currentGame = null;
    //玩法切换
    Main.currentGameTypes = null;
    //当前统计
    Main.currentGameStatistics = null;
    //当前号码篮
    Main.currentGameOrder = null;

    //投注按钮
    Main.currentGameSubmit = null;
    //当前游戏消息类
    Main.currentGameMessage = null;

    //当前游戏
    Main.getCurrentGame = function(){
        return Main.currentGame;
    };
    Main.setCurrentGame = function(game){
        Main.currentGame = game;
    };

    //玩法切换
    Main.getCurrentGameTypes = function(){
        return Main.currentGameTypes;
    };
    Main.setCurrentGameTypes = function(currentGameTypes){
        Main.currentGameTypes = currentGameTypes;
    };

    //号码篮
    Main.getCurrentGameOrder = function(){
        return Main.currentGameOrder;
    };
    Main.setCurrentGameOrder = function(currentGameOrder){
        Main.currentGameOrder = currentGameOrder;
    };


    //投注提交
    Main.getCurrentGameSubmit = function(){
        return Main.currentGameSubmit;
    };
    Main.setCurrentGameSubmit = function(currentGameSubmit){
        Main.currentGameSubmit = currentGameSubmit;
    };

    //消息提示
    Main.getCurrentGameMessage = function(){
        return Main.currentGameMessage;
    };
    Main.setCurrentGameMessage = function(currentGameMessage){
        Main.currentGameMessage = currentGameMessage;
    };
	
	//选号状态
		Main.getCurrentGameStatistics = function(){
			return Main.currentGameStatistics;
		};
		Main.setCurrentGameStatistics = function(gameStatistics){
			Main.currentGameStatistics = gameStatistics;
		};


    host[name] = Main;

})(phoenix, "Games");







