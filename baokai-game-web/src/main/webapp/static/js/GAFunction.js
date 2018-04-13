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
    return true
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