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
            if (currentTitle == '重庆时时彩') {
                GAEventArray = 
                {
                    'charButton'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'近期走势按钮'},
                    'menuShowButton'    :{'category':'wap重庆时时彩', 'limit':true, 'label':'更多按钮'},
                    'random' 			:{'category':'wap重庆时时彩', 'limit':true, 'label':'机选按钮'},
                    'shopCarMultiple'   :{'category':'wap重庆时时彩', 'limit':true, 'label':'倍投按钮'},
                    'ballBucketResult'	:{'category':'wap重庆时时彩', 'limit':true, 'label':'号码篮按钮'},
					'backGoPick'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'返回按钮'},
					'selfGoPick'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'自选号码'},
					'randomBall'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'+机选1注'},
					'cleanConfirm'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'清空'},
					'multipleClick'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'倍投'},
					'continuesBet'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'追号'},
					'gosubmitClick'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'投注'},
					'submitGoPick'		:{'category':'wap重庆时时彩', 'limit':true, 'label':'继续投注按钮'}
                };
            }
			
			if (currentTitle == '宝开彩票-国庆一起打鬼子') {
                GAEventArray = 
                {
                    'japan1':{'category':'点击日本兵', 'limit':false, 'label':'click(点击)'},
                    'japan2':{'category':'点击日本兵', 'limit':false, 'label':'click(点击)'},
                    'japan3':{'category':'点击日本兵', 'limit':false, 'label':'click(点击)'}
                };
            }
			
			if (currentTitle == 'RichMan') {
                GAEventArray = 
                {
                    'singbtn':{'category':'点击報名', 'limit':false, 'label':'click(点击)'},
                    'add_addr':{'category':'填写地址', 'limit':false, 'label':'click(点击)'},
                    'chargebtn':{'category':'立即充值', 'limit':false, 'label':'click(点击)'},
					'modify_addr':{'category':'修改地址', 'limit':false, 'label':'click(点击)'}
                };
            }

            memberAccount          = result.userAccount;
            memberRegisterMicrosec = result.registerDate;
            phpJsTimeParsec        = getParsecFromHost(result.currentHostTime);

            //設置userID
            if (IsParameterReady(memberAccount)) {
                ga('set', 'userId', memberAccount);
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
