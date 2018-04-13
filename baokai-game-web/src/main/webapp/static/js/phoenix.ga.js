(function (factory) {
    if (typeof define === 'function' && define.amd) {
        define(factory);
    } else if (typeof exports === 'object') {
        module.exports = factory();
    } else {
        var _OldCookies = window.Cookies;
        var api = window.Cookies = factory();
        api.noConflict = function () {
            window.Cookies = _OldCookies;
            return api;
        };
    }
}(function () {
    function extend () {
        var i = 0;
        var result = {};
        for (; i < arguments.length; i++) {
            var attributes = arguments[ i ];
            for (var key in attributes) {
                result[key] = attributes[key];
            }
        }
        return result;
    }

    function init (converter) {
        function api (key, value, attributes) {
            var result;

            // Write

            if (arguments.length > 1) {
                attributes = extend({
                    path: '/'
                }, api.defaults, attributes);

                if (typeof attributes.expires === 'number') {
                    var expires = Date.now();
                    attributes.expires += expires;
                    attributes.expires = new Date(attributes.expires);
                }

                try {
                    result = JSON.stringify(value);
                    if (/^[\{\[]/.test(result)) {
                        value = result;
                    }
                } catch (e) {}

                if (!converter.write) {
                    value = encodeURIComponent(String(value))
                        .replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g, decodeURIComponent);
                } else {
                    value = converter.write(value, key);
                }

                key = encodeURIComponent(String(key));
                key = key.replace(/%(23|24|26|2B|5E|60|7C)/g, decodeURIComponent);
                key = key.replace(/[\(\)]/g, escape);

                return (document.cookie = [
                    key, '=', value,
                    attributes.expires && '; expires=' + attributes.expires.toString(), // use expires attribute, max-age is not supported by IE
                    attributes.path    && '; path=' + attributes.path,
                    attributes.domain  && '; domain=' + attributes.domain,
                    attributes.secure ? '; secure' : ''
                ].join(''));
            }

            // Read

            if (!key) {
                result = {};
            }

            // To prevent the for loop in the first place assign an empty array
            // in case there are no cookies at all. Also prevents odd result when
            // calling "get()"
            var cookies = document.cookie ? document.cookie.split('; ') : [];
            var rdecode = /(%[0-9A-Z]{2})+/g;
            var i = 0;

            for (; i < cookies.length; i++) {
                var parts = cookies[i].split('=');
                var name = parts[0].replace(rdecode, decodeURIComponent);
                var cookie = parts.slice(1).join('=');

                if (cookie.charAt(0) === '"') {
                    cookie = cookie.slice(1, -1);
                }

                try {
                    cookie = converter.read ?
                        converter.read(cookie, name) : converter(cookie, name) ||
                        cookie.replace(rdecode, decodeURIComponent);

                    if (this.json) {
                        try {
                            cookie = JSON.parse(cookie);
                        } catch (e) {}
                    }

                    if (key === name) {
                        result = cookie;
                        break;
                    }

                    if (!key) {
                        result[name] = cookie;
                    }
                } catch (e) {}
            }

            return result;
        }

        api.get = api.set = api;
        api.getJSON = function () {
            return api.apply({
                json: true
            }, [].slice.call(arguments));
        };
        api.defaults = {};

        api.remove = function (key, attributes) {
            api(key, '', extend(attributes, {
                expires: -1
            }));
        };

        api.withConverter = init;

        return api;
    }

    return init(function () {});
}));

/**  
 * @subject 回傳今日剩餘時間 (micro sec)
 * @access  public
 * @return  int 今日剩餘時間 (micro sec)
 * @author  Alex.Lu
 * @edit    2016/3/26
 * @memo    none
**/
function getCookieParsec() {
    var cookieNowTime = new Date();
    var cookieNowDate = cookieNowTime.getDate();
    //設定cookie期限只到今天
    //cookieNowTime.setDate(cookieNowDate+1);
    cookieNowTime.setHours(23, 59, 59, 999);
    cookieExpireTime = cookieNowTime.getTime();

    cookieNowTime = Date.now();
    var cookieParsec = cookieExpireTime - cookieNowTime;

    cookieParsec = parseInt(cookieParsec, 10);
    return cookieParsec;
}

/**  
 * @subject 判斷並回傳是否為新會員
 * @access  public
 * @param   int 會員註冊時間 (micro sec)
 * @param   int 會員和伺服器時間的差距 (micro sec)
 * @return  boolean true:新會員 false: 參數錯誤或舊會員
 * @author  Alex.Lu
 * @edit    2016/3/26
 * @memo    none
**/
function IsNewMember(registerTime, parsec) {
    //檢查會員註冊時間
    if (!IsParameterReady(registerTime)) {
        return false;
    }
    registerTime = parseInt(registerTime, 10);
    if (isNaN(registerTime) || registerTime <= 0 ) {
        return false;
    }
    //檢查主機和會員時間的毫秒差
    if (!IsParameterReady(parsec)) {
        parsec = 0;
    }
    parsec = parseInt(parsec, 10);
    if (isNaN(parsec)) {
        parsec = 0;
    }
    //設定註冊多久後為舊會員(default: 7 days unit: micro second)
    var afterTime = 7*24*60*60*1000;
    var jsNowTime = Date.now();
    var memberRegisterAfterTime;
    //同步主機時間
    jsNowTime += parsec;
    memberRegisterAfterTime = registerTime + afterTime;

    if (jsNowTime >= memberRegisterAfterTime) {
        return false;
    } else {
        //新會員
        return true;
    }
}

/**  
 * @subject 檢查參數是否設定
 * @access  public
 * @param   int 任意JS變數
 * @return  boolean true:參數已設定 false:參數未設定
 * @author  Alex.Lu
 * @edit    2016/3/26
 * @memo    none
**/
function IsParameterReady(par) {
    if (typeof(par) == 'undefined' || !par) {
        return false;
    }
    return true;
}

/**  
 * @subject 取得本地和主機的時間差
 * @access  public
 * @param   int 主機時間 (unit: micro second)
 * @return  int 本地和主機的時間差 (unit: micro second)
 * @author  Alex.Lu
 * @edit    2016/3/28
 * @memo    none
**/
function getParsecFromHost(hostTime) {
    if(!IsParameterReady(hostTime)) {
        return 0;
    }
    hostTime = parseInt(hostTime, 10);
    if (isNaN(hostTime)) {
        hostTime = Date.now();
    }
    var jsMicrotime = Date.now();
    jsMicrotime = parseInt(jsMicrotime, 10);
    var phpJsTimeParsec = hostTime - jsMicrotime;
    phpJsTimeParsec = parseInt(phpJsTimeParsec, 10);
    return phpJsTimeParsec;
}

//UA code
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

var currentDomain          = window.location.host;
var currentTitle           = document.title;
var isLocalhost            = false;
var clickID                = '';
var GAEventArray           = '';
var GACookieName           = '';
var GACookieValue          = '';
var GAEventCategory        = '';
var GAEventAction          = '';
var GAEventLabel           = '';
var memberAccount          = '';
var memberRegisterMicrosec = '';
var phpJsTimeParsec        = '';

//判斷是否為開發環境 公司 GA: UA-63536434-1
var devDomain = ['dev', 'joy188', 'ffg2', 'ff2', 'localhost', '127.0.0.1'];
for (var devDomainKey in devDomain) {
    if (currentDomain.indexOf(devDomain[devDomainKey]) !== -1) {
        isLocalhost = true;
    }
}

if (isLocalhost) {
    //測試環境
    ga('create', 'UA-71901456-1', {'cookieDomain': 'none'});
} else {
    //正式環境
ga('create', 'UA-99334970-1', 'auto');
}

 ga('send', 'pageview');
//組合AJAX地址
var GAUrl = currentDomain.split('.');
if (currentDomain.indexOf('joy188')!== -1) {
    GAUrl[0] = 'www2';
} else {
    GAUrl[0] = 'www';
}
GAUrl = GAUrl.join('.');

$(document).ready(function() { 
    //取得會員資料
    $.ajax({
        url: '//'+GAUrl+'/login/getgainfo/',
        type:'get',
        dataType:'jsonp',
        jsonp: 'callback',
        data:{},
        success:function(result) {
            //登录页面
            if (currentTitle == '正常登录') {
                GAEventArray = 
                {
                    'custom-service':{'category':'登录页面', 'limit':false, 'label':'在线客服'},
                    'forgetPW'      :{'category':'登录页面', 'limit':false, 'label':'忘记密码'},
                    'J-form-submit' :{'category':'登录页面', 'limit':false, 'label':'登录'},
                    'aSafeLogin'    :{'category':'登录页面', 'limit':false, 'label':'安全登录'},
                    'focus'         :{'category':'登录页面', 'limit':false, 'label':'广告位'}
                };
            }
			//wap登录页面
            if (currentTitle == '普通登录') {
                GAEventArray = 
                {
					'forgetPwd'    :{'category':'普通登录', 'limit':false, 'label':'wap忘记密码'},
					'downloadApp'    :{'category':'普通登录', 'limit':false, 'label':'wap下载APP'}
                };
            }
            //安全登录页面
            if (currentTitle == '安全登录') {
                GAEventArray = 
                {
                    'J-form-submit' :{'category':'安全登录页面', 'limit':false, 'label':'登录'},
                    'normalLogin'   :{'category':'安全登录页面', 'limit':false, 'label':'普通登录'}
                };
            }
            //彩票首页
            if (currentTitle == '宝开彩票首页') {
                GAEventArray = 
                {
                    'doaminCheck'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'域名验证'},
                    'headerUsername'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'header用户名'},
                    'betRecord'                      :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'投注记录'},
                    'accountDetail'                  :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'账户明细'},
                    'safeCenter'                     :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'安全中心'},
                    'proxyCenter'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'代理中心'},
                    'queryReport'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'报表查询'},
                    'showAllBall'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'显示余额'},
                    'headerRecharge'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'header充值'},
                    'headerWithdraw'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'header提现'},
                    'appealRecharge'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'催到账'},
                    'headerHelp'                     :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'header帮助'},
                    'onlineChat'                     :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'在线客服'},
                    'headerLottery'                  :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'彩票'},
                    'headerTigerGame'                :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'老虎机'},
                    'interfaceAction'                :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'平台活动'},
                    'downloadCenter'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'下载中心'},
                    'mobileAPPdownload'              :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'手机版下载'},
                    'DCSafecenter'                   :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'下载中心_安全中心'},
                    'PTClientcenter'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'PT客户端中心'},
                    'expressLogin'                   :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'快速登录器'},
                    'focus'                          :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'广告轮播图'},
                    'userHead'                       :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'VIP头像'},
                    'upSafeLevel'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'提升安全等级'},
                    'centerRecharge'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'中央充值'},
                    'centerWithdraw'                 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'中央提现'},
                    'transfer'                       :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'转帐'},
                    'howToCharge'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'如何充值'},
                    'withdrawInfo'                   :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'提现须知'},
                    'J-globalad-index_banner_left'   :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'左侧活动位'},
                    'J-globalad-index_banner_center' :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'中央活动位'},
                    'J-globalad-index_banner_right'  :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'右侧活动位'},
                    'importantNotice'                :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'重要通知'},
                    'rewardNotice'                   :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'派奖信息'},
                    'interfaceMantain'               :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'平台维护'},
                    'J-button-qq'                    :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'QQ交谈'},
                    'insideLetter'                   :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'站内信'},
					'cqssc'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版重庆时时彩'},
					'slmmc'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版秒开时时彩'},
					'xjssc'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版新疆时时彩'},
					'hljssc'                    	 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版黑龙江时时彩'},
					'llssc'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版宝开时时彩'},
					'shssl'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版上海时时乐'},
					'jlffc'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版宝开1分彩'},
					'tjssc'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版天津时时彩'},
					'sd115'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版山东11选5'},
					'sl115'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版秒开11选5'},
					'jx115'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版江西11选5'},
					'll115'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版宝开11选5'},
					'gd115'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版广东11选5'},
					'bjkl8'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版北京快乐8'},
					'jsk3'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版江苏快三'},
					'ahk3'                    		 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版安徽快三'},
					'jsdice'                    	 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版江苏骰宝'},
					'fc3d'                    	 	 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版3D'},
					'p5'                    	 	 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版排列3/5'},
					'ssq'                    	 	 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版双色球'},
					'lhc'                    	 	 :{'category':'彩票首页', 'limit':true, 'cookieName':'lotteryIndex', 'label':'wap版六合彩'}
                };
            }
            //充值页面
            if (currentTitle == '我要充值') {
                GAEventArray = 
                {
                    'netRemit'         :{'category':'充值页面', 'limit':true, 'cookieName':'fundIndex', 'label':'网银汇款'},
                    'expressRecharge'  :{'category':'充值页面', 'limit':true, 'cookieName':'fundIndex', 'label':'快捷充值'},
                    'tenpayRecharge'   :{'category':'充值页面', 'limit':true, 'cookieName':'fundIndex', 'label':'财付通充值'},
                    'unionpayRecharge' :{'category':'充值页面', 'limit':true, 'cookieName':'fundIndex', 'label':'银联充值'}
                };
            }
            //安全中心
            if (currentTitle == '安全中心') {
                GAEventArray = 
                {
                    'changePWLink'                 :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改密码连结'},
                    'changePWBtn'                  :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改密码按钮'},
                    'changeSafePW'                 :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改安全密码'},
                    'getSafePW'                    :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'找回安全密码'},
                    'setSafePW'                    :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'设置安全密码'},
                    'changeSafeQuestion'           :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改安全问题'},
                    'setSafeQuestion'              :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'设置安全问题'},
                    'changeEmail'                  :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改邮箱'},
                    'setEmail'                     :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'绑定邮箱'},
                    'changePreVerifyInfo'          :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改预留验证信息'},
                    'setPreVerifyInfo'             :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'绑定预留验证信息'},
                    'bindPhoenixSafeCenter'        :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'绑定宝开安全中心'},
                    'changeBindPhoenixSafeCenter'  :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'修改绑定宝开安全中心'},
                    'unbindPhoenixSafeCenter'      :{'category':'安全中心', 'limit':true, 'cookieName':'safeCenterIndex', 'label':'解除绑定宝开安全中心'}
                };
            }
			
			//个人中心
            if (currentTitle == '个人中心') {
                GAEventArray = 
                {
                    'uptop' 		:{'category':'个人中心', 'limit':true, 'label':'wap充值'},
                    'withdraw'  	:{'category':'个人中心', 'limit':true, 'label':'wap提现'},
					'managerurl'  	:{'category':'个人中心', 'limit':true, 'label':'wap链接管理'},
					'chart'  		:{'category':'个人中心', 'limit':true, 'label':'wap走势图'},
					'logout'  		:{'category':'个人中心', 'limit':true, 'label':'wap退出登录'},
					'downloadApp'  	:{'category':'个人中心', 'limit':true, 'label':'wap下载APP'},
					'computer'  	:{'category':'个人中心', 'limit':true, 'label':'电脑完整版'}
                };
            }
            memberAccount          = result.userAccount;
            memberRegisterMicrosec = result.registerDate;
            phpJsTimeParsec        = getParsecFromHost(result.currentHostTime);

            //設置userID
            if (IsParameterReady(memberAccount)) {
                ga('set', 'userId', memberAccount);
				ga('set', 'dimension2', memberAccount);
            }
            ga('send', 'pageview');

            //get event id by click
           $('*').on('click', function(event) {
                clickID = '';
                clickID = $(this).prop('id');

                if (IsParameterReady(clickID)) {
                    if (IsParameterReady(GAEventArray[clickID]) &&
                        IsParameterReady(GAEventArray[clickID]['category']) &&
                        IsParameterReady(GAEventArray[clickID]['label'])
                    ) {
                        GAEventCategory = GAEventArray[clickID]['category'];
                        GAEventLabel    = GAEventArray[clickID]['label'];
                        GAEventAction   = '点击';

                        if (IsParameterReady(memberAccount) && 
                            IsParameterReady(memberRegisterMicrosec) &&
                            IsParameterReady(phpJsTimeParsec)
                        ) {
                            //有會員狀態限制
                            if (GAEventArray[clickID]['limit']) {
                                 GACookieName = GAEventArray[clickID]['cookieName']+"_"+memberAccount;
                                //檢查是否為新會員
                                if (IsNewMember(memberRegisterMicrosec, phpJsTimeParsec)) {
                                    //檢查cookie是否設定
                                    if (typeof(Cookies.get(GACookieName)) == 'undefined') {
                                        GACookieValue = clickID+'#';
                                        Cookies.set(GACookieName, GACookieValue, { expires: getCookieParsec() });
                                        ga('send', 'event', GAEventCategory, GAEventAction, GAEventLabel);
                                    } else {
                                        GACookieValue = Cookies.get(GACookieName);
                                        //檢查是否有特定的值
                                        if (GACookieValue.indexOf(clickID) === -1) {
                                            GACookieValue += clickID+'#';
                                            Cookies.set(GACookieName, GACookieValue, { expires: getCookieParsec() });
                                            ga('send', 'event', GAEventCategory, GAEventAction, GAEventLabel);
                                        }
                                    }
                                }
                            }
                        }
                        //無會員狀態限制 (ex: 登入頁面)
                        if (!GAEventArray[clickID]['limit']) {
                            ga('send', 'event', GAEventCategory, GAEventAction, GAEventLabel);
                        }
                    }
                }
            });
        },
        error:function(xhr, status, error) {
            ga('send', 'pageview');
            //console.log(error);
        }
    }); //end of ajax
}); //end of ready
