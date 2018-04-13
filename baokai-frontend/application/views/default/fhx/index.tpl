<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>{$title|default:"宝开彩票"}</title>
    <!--<link href="{$path_img}/images/common/base.css" rel="stylesheet">-->
    <!--<link href="{$path_img}/images/indexv2/index.css" rel="stylesheet">-->
	<link href="{$path_img}/images/fhx/base.css" rel="stylesheet">
	<link href="{$path_img}/images/fhx/jb.css" rel="stylesheet">
    <link rel="shortcut icon" href="{$path_img}/images/common/favicon.ico">
	<link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.css" />
	<link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/helpers/jquery.fancybox-buttons.css" />
    <script type="text/javascript">
        //banner script
        var userName = "{$smarty.session.datas.info.account}";              {* user name *}
        var indexServer ="";                                                {* php server url 首頁 *}
        var userServer = indexServer;                                       {* user server url *}
        var gameServer = "{$game_server}";                                  {* game server url *}
        var ptgameServer = "{$ptgame_server}";                              {* PTgame server url *}
        var wagameServer = "{$wggame_server}/game/wg/openGame";             {* wagame server url *}
        var fhxgameServer = indexServer + "/fhx/index";                     {* fhxgame server url *}
        var userbal = "/index/getuserbal";                                  {* get userbal url 餘額查詢 *}
        var permisssion = "";                                               {* get available user menu 使用者選單 *}
        var support_service_link_a = "{$smarty.session.datas.info.account}";//{* get user accunt for support_service *}
        var support_service_link_aid = "{$smarty.session.datas.info.id}";   //{* get user id for support_service *}
        var support_service_link_l = "{$smarty.session.datas.info.vipLvl}"; //{* get user vipLvl for support_service *}
        var displayAvailBal = "{$displayAvailBal}";                         {* displayAvailBal *}
        var bannerHeadClass = "g_34";                                       {* <div class="'+bannerHeadClass+'"> banner head class *}
        var bannerlogotitle='';                                             {* banner logo attribute : title Banner logo的文字 *}
        var bannerlogohref=userServer + "/index";                           {* banner logo attribute : href  Banner logo的超連結 *}
        var userClassId = "J-user-center";                                  {* user panel class id 使用者選單 *}
        var msgClassId = "J-top-user-message";                              {* message panel class id 站內信 *}
        var betHistory = gameServer + "/gameUserCenter/queryOrders?userId={$smarty.session.datas.info.id}&time=7";{* bet history 投注記錄查詢 *}
        var quickloginTarget = 'target="_blank"';                           {* quick login attribute : target 快速登入超連結屬性 target *}
        var frontpageLink="";
        var projectName = "彩票";

var toolbar =
'                <span style="display: none;">FF4</span>                                                                                                                '+
'                    <a href="'+indexServer+'/index" class="back-top-home" target="'+frontpageLink+'" >首页</a>                                                          '+
'                    <div id="J-top-game-menu" class="game-menu" id="ffrog">                                                                                             '+
'                        <span class="game-menu-text dropdown-menu-btn">'+projectName+'</span>                                                                           '+
'                        <div class="game-menu-panel">                                                                                                                   '+
'                                <span class="triangle game-menu-triangle"><span></span></span>                                                                          '+
'                                <div class="game-menu-inner">                                                                                                           '+
'                                        <div class="game-menu-box">                                                                                                     '+
'                                                <div class="game-menu-title">时时彩</div>                                                                                '+
'                                                <div class="game-menu-list">                                                                                            '+
'                                                    <a class="hot-game" href="'+gameServer+'/gameBet/cqssc" >重庆时时彩<i></i></a>                                       '+
'                                                    <a href="'+gameServer+'/gameBet/hljssc" >黑龙江时时彩</a>                                                            '+
'                                                    <a href="'+gameServer+'/gameBet/xjssc" >新疆时时彩</a>                                                               '+
'                                                    <a href="'+gameServer+'/gameBet/shssl" >上海时时乐</a>                                                               '+
'                                                    <a href="'+gameServer+'/gameBet/llssc" >宝开时时彩</a>                                                               '+
'                                                    <a href="'+gameServer+'/gameBet/tjssc" >天津时时彩</a>                                                               '+
'                                                    <a href="'+gameServer+'/gameBet/jlffc" >宝开1分彩</a>                                                               '+
'                                                    <a href="'+gameServer+'/gameBet/txffc" >腾讯分分彩</a>                                                               '+
'                                                    <a class="new-game" href="'+gameServer+'/gameBet/slmmc" >秒开时时彩<i></i></a>                                       '+
'                                                </div>                                                                                                                  '+
'                                        </div>                                                                                                                          '+
'                                        <div class="game-menu-box">                                                                                                      '+
'                                                <div class="game-menu-title">11选5</div>                                                                                 '+
'                                                <div class="game-menu-list">                                                                                             '+
'                                                        <a href="'+gameServer+'/gameBet/sd115" >山东11选5</a>                                                             '+
'                                                        <a href="'+gameServer+'/gameBet/jx115" >江西11选5</a>                                                            '+
'                                                        <a href="'+gameServer+'/gameBet/gd115" >广东11选5</a>                                                            '+
'                                                        <a href="'+gameServer+'/gameBet/ll115" >宝开11选5</a>                                                            '+
'                                                        <a class="new-game" href="'+gameServer+'/gameBet/sl115" >秒开11选5<i></i></a>                                    '+
'                                                </div>                                                                                                                   '+
'                                        </div>                                                                                                                           '+
'                                        <div class="game-menu-box">                                                                                                      '+
'                                                <div class="game-menu-title">快乐彩</div>                                                                                '+
'                                                <div class="game-menu-list">                                                                                             '+
'                                                        <a href="'+gameServer+'/gameBet/bjkl8" >北京快乐8</a>                                                            '+
'                                                </div>                                                                                                                   '+
'                                        </div>                                                                                                                           '+
'                                        <div class="game-menu-box">                                                                                                      '+
'                                                <div class="game-menu-title">快三</div>                                                                                  '+
'                                                <div class="game-menu-list">                                                                                             '+
'                                                        <a href="'+gameServer+'/gameBet/jsk3" >江苏快三</a>                                                               '+
'                                                        <a href="'+gameServer+'/gameBet/ahk3" >安徽快三</a>                                                               '+
'                                                        <a href="'+gameServer+'/gameBet/jsdice" >江苏骰宝</a>                                                             '+
'                                                </div>                                                                                                                   '+
'                                        </div>                                                                                                                           '+
'                                        <div class="game-menu-box">                                                                                                      '+
'                                                <div class="game-menu-title">低频</div>                                                                                  '+
'                                                <div class="game-menu-list">                                                                                             '+
'                                                        <a href="'+gameServer+'/gameBet/fc3d" >3D</a>                                                                    '+
'                                                        <a href="'+gameServer+'/gameBet/p5" >排列3/5</a>                                                                  '+
'                                                        <a href="'+gameServer+'/gameBet/ssq" >双色球</a>                                                                 '+
'                                                        <a href="'+gameServer+'/gameBet/lhc" >六合彩</a>                                                                '+
'                                                </div>                                                                                                                    '+
'                                        </div>                                                                                                                            '+
'                                </div>                                                                                                                                    '+
'                        </div>                                                                                                                                            '+
'                    </div>                                                                                                                                                '+
'                    <a href="'+ptgameServer+'/pt/index/" class="back-top-home">老虎机</a>                                                                                  '+
'                    <a href="'+fhxgameServer+'" class="back-top-home">宝开游艺</a>                                                                                         '+
'       </div>                                                                                                                                                              '
;
    </script>
<style>
.fancyboxVid .videoTag {
	display:none;
}
.fancybox-wrap {
	width: 100%;
	height: 856px;
}
video {
	background:#000;
	display:block;
}
</style>
	
</head>
<body >
    
	<div class="toolbar">
		<div class="g_33">
			
			<div class="game">
				<a href="/index/" class="home"></a>
				
				<div id="J-top-game-menu" class="game-menu" id="ffrog">                                                                                            
                                <a href="" class="lottery"></a>                                                         
                        <div class="game-menu-panel">                                                                                                              
                                <span class="triangle game-menu-triangle"><span></span></span>                                                                     
                                <div class="game-menu-inner">                                                                                                      
                                        <div class="game-menu-box">                                                                                                
                                                <div class="game-menu-title">时时彩</div>                                                                          
                                                <div class="game-menu-list">                                                                                       
                                                    <a class="hot-game" href="{$game_server}/gameBet/cqssc" >重庆时时彩<i></i></a>                                 
                                                    <a href="{$game_server}/gameBet/hljssc" >黑龙江时时彩</a>                                                      
                                                    <a href="{$game_server}/gameBet/xjssc" >新疆时时彩</a>                                                         
                                                    <a href="{$game_server}/gameBet/shssl" >上海时时乐</a>                                                         
                                                    <a href="{$game_server}/gameBet/llssc" >宝开时时彩</a>                                                         
                                                    <a href="{$game_server}/gameBet/tjssc" >天津时时彩</a>                                                         
                                                    <a href="{$game_server}/gameBet/jlffc" >宝开1分彩</a>                                                         
                                                    <a href="{$game_server}/gameBet/txffc" >腾讯分分彩</a>                                                         
                                                    <a class="new-game" href="{$game_server}/gameBet/slmmc" >秒开时时彩<i></i></a>                                 
                                                </div>                                                                                                             
                                        </div>                                                                                                                     
                                        <div class="game-menu-box">                                                                                                
                                                <div class="game-menu-title">11选5</div>                                                                           
                                                <div class="game-menu-list">                                                                                       
                                                        <a href="{$game_server}/gameBet/sd115" >山东11选5</a>                                                      
                                                        <a href="{$game_server}/gameBet/jx115" >江西11选5</a>                                                      
                                                        <a href="{$game_server}/gameBet/gd115" >广东11选5</a>                                                      
                                                        <a href="{$game_server}/gameBet/ll115" >宝开11选5</a>                                                      
                                                        <a class="new-game" href="'+gameServer+'/gameBet/sl115" >秒开11选5<i></i></a>                              
                                                </div>                                                                                                             
                                        </div>                                                                                                                     
                                        <div class="game-menu-box">                                                                                                
                                                <div class="game-menu-title">快乐彩</div>                                                                          
                                                <div class="game-menu-list">                                                                                       
                                                        <a href="{$game_server}/gameBet/bjkl8" >北京快乐8</a>                                                      
                                                </div>                                                                                                             
                                        </div>                                                                                                                     
                                        <div class="game-menu-box">                                                                                                
                                                <div class="game-menu-title">快三</div>                                                                            
                                                <div class="game-menu-list">                                                                                       
                                                        <a href="{$game_server}/gameBet/jsk3" >江苏快三</a>                                                        
                                                        <a href="{$game_server}/gameBet/ahk3" >安徽快三</a>                                                        
                                                        <a href="{$game_server}/gameBet/jsdice" >江苏骰宝</a>                                                      
                                                </div>                                                                                                             
                                        </div>                                                                                                                     
                                        <div class="game-menu-box">                                                                                                
                                                <div class="game-menu-title">低频</div>                                                                            
                                                <div class="game-menu-list">                                                                                       
                                                        <a href="{$game_server}/gameBet/fc3d" >3D</a>                                                              
                                                        <a href="{$game_server}/gameBet/p5" >排列3/5</a>                                                           
                                                        <a href="{$game_server}/gameBet/ssq" >双色球</a> 													
                                                </div>                                                                                                             
                                        </div> 
                                        <!-- {if $smarty.session.datas.info.lhcStatus == '1'} -->
                                        <div class="game-menu-box">                                                                                                
                                                <div class="game-menu-title">趣味彩</div>                                                                          
                                                <div class="game-menu-list">                                                                                       
                                                        <a class="new-game" href="{$game_server}/gameBet/lhc" >六合彩<i></i></a>                                                   
                                                </div>                                                                                       
                                        </div>  
                                        <!--{/if}-->    
                                </div>                                                                                                                             
                        </div>                                                                                                                                     
                    </div>  
				<a href="{$ptgame_server}/pt/index/" class="slot"></a>
				<a href="./index/" class="leisure"></a>
			</div>
			<ul class="menu">
				<li class="user" id="J-top-userinfo">
					您好，{$smarty.session.datas.info.account}<i class="tri"></i>
					
				</li>
				<li class="msg" id="J-top-user-message">
				
				</li>
				<li class="balance">
					<span id="hiddBall" style="display:none">余额：<span id="spanBall" name="spanBall">{$displayAvailBal}</span><i class="refreshBall"></i></span>
					<span id="hiddenBall" style="display: inline;">余额已隐藏 <a  id="showAllBall">显示</a></span>
				</li>
				<li class="recharge"><a href="/fund/">充值</a></li>
				<li><a class="fancybox" href="#jp-explain" style="padding:2px 5px;background-color:yellow;">元宝彩金说明</a></li>
				 <li><a  href="ttps://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" >客服</a></li> 
			
			</ul>
		</div>
	</div>
	
	

        <iframe id="my_iframe" scrolling="no" style="width:100%;padding:0px;border:0px;margin:0px;background-color:white;height:1390px;" src="{$x_landing_page}"></iframe>

	
	
	
 
   <span style="display: none">
        <span id="jp-explain">
            <center>
                <img src="{$path_img}/images/indexv2/fhx/x-jp.jpg"></img>
                <h1 style="font-size:50px;color:#19BBFC;">元 宝 彩 金 说 明</h1>
                <span style="font-size:20px;color:#eeeeee;">
                元宝彩金是宝开游艺独家推出的大彩池。<br>
                提供四层超级彩金(白金元宝、金元宝、银元宝、铜元宝彩金)，<br>
                只要参与游戏即有机会拉中超级大奖。<br>
                彩金金额统一以人民币显示。
                </span>
            </center>
        </span>
    </span>

   
	
	<div class="fancyboxVid" style="position:fixed;right:10px;bottom:10px;">
	
	
   </div>

   
	
	

    <script type="text/javascript" src="{$path_js}/js/base-all.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.ga.js"></script>
    <link rel="stylesheet" href="{$path_js}/js/fancybox/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
    <script type="text/javascript" src="{$path_js}/js/fancybox/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.pack.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/helpers/jquery.fancybox-buttons.js"></script>
	<script type="text/javascript" src="{$path_js}/js/index/index.js"></script>

    <script>
    global_path_url="{$path_img}";
    dynamicroot ="{$dynamicroot}";
    gameserver = "{$game_server}";
    hjUserData= "{$hjUserData}";
    path = "{$path_js}";
    global_userName="{$smarty.session.datas.info.account}";
    global_userID="{$smarty.session.datas.info.id}";
    global_params_notice = "all,ad_top,agent_first_page";
    var sEndTime = {$gameResult[$playgameid]['saleEndTime']|default:0};
    var sNowTime = {$nowTime|default:0};
    (function(){
        function async_load(){
            var s = document.createElement('script');
            s.type = 'text/javascript';
            s.async = true;
            s.src = "http://www.26hn.com/web/code/code.jsp?c=1&s={if $smarty.session.datas.info.vipLvl >= '1'}20{else}17{/if}";
            var x = document.getElementsByTagName('script')[0];
            x.parentNode.insertBefore(s, x);
        }
        if (window.attachEvent){
            window.attachEvent('onload', async_load);
        } else {
            window.addEventListener('load', async_load, false);
        }
        //
        $(".fancybox").fancybox({
			
            autoSize:false,
            width:1250,
            autoHeight:true,
            minHeight:20,
			
            tpl:{
                wrap:'<div class="fancybox-wrap" tabIndex="-1"><img src="{$path_img}/images/indexv2/fhx/x-bar.jpg"></img><div class="fancybox-skin"><div class="fancybox-outer"><div class="fancybox-inner"></div></div></div><img src="{$path_img}/images/indexv2/fhx/x-bar.jpg"></img></div>'
            }
		
        });
 
    })();
	
	function setIframeSize(size) {

            var  h = size;
            var frm = document.getElementById("my_iframe");
            frm.style.height = h + "px";

            setTimeout(function () {

               // alert("Resize IFrame to " + h + "!");

            }, 100);
	}
	


    </script>

    <script type="text/javascript" src="{$path_js}/js/index/index.js"></script>
    {literal}
    <script>
	var fancyboxModule = (function($, undefined) {
	indexVid = function() {
		var vidOpener = $(".vidOpener"),
			videoTag = vidOpener.siblings(".videoTag").html(),
			video;
		
		if (vidOpener.length) {
			//console.log('test4');
			vidOpener.fancybox({
				type: 'iframe',
				width: 856,
				//width: 800,
				height: 480,
				content: videoTag,
				padding:0,
				fitToView: false,
				iframe: { preload : false },
			    helpers: {
			        overlay : {
			            locked : false,
			            css : {
			                'background' : 'rgba(0, 0, 0, 0.8)'
			            }
			        }
			    },
			    beforeShow: function() {
			    	video = $('.fancybox-inner').find("video").get(0);
					console.log(video);
			    	video.load();
			    	video.play();
			    }
				
			});
		}
	}
	
	return {
		init: function() {
            indexVid();
		}
	}
})(jQuery)
    $(document).ready(function(){
		
		fancyboxModule.init();
        //var target = document.getElementById("jsheader");
        //複寫html
        //target.innerHTML = toolbar;
        // 顶部彩种菜单
        new phoenix.Hover({
            triggers:'#J-top-game-menu',
            panels:'#J-top-game-menu .game-menu-panel',
            hoverDelayOut:300,
            currClass:'game-menu-current'
        });
		
    });
    </script>
    {/literal}   
    <style>
    body {
        background: #181818;
    }
    .icon-user {
        background:transparent;
    }
    .footer-help .footer-m {
        background: rgba(0, 0, 0, 0) url("{$path_img}/images/indexv2/fhx/x-bg-sprite.jpg") no-repeat scroll center -982px;
    }
    .fancybox-wrap, .fancybox-skin {
        background-color: #000000;
    }
    </style>
</body>
</html>

