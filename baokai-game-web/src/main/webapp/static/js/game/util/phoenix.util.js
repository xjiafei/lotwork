

(function(host, name, $, undefined){
	
	var Main = function(){};
		Main.win = $(window);
		Main.doc = $(document);
		Main.isIE = !!document.all;
		Main.isIE6 = window.ActiveXObject && navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1] == 6.0 ? true : false;
		
	

		//使某个元素定位到视图中心
		Main.toViewCenter = function(el){
			Main.toViewCenter.fn(el);

			Main.win.bind('resize', function(){
				Main.toViewCenter.fn(el);
			})
		};

		Main.toViewCenter.fn = function(el){
			var el = $(el),w = el.width(),h = el.height(),allw = Main.win.width(),allh = Main.win.height(),
				scrollWidth = Main.isIE6 ? Main.win.scrollLeft() : 0,
				scrollHeight = Main.isIE6 ? Main.win.scrollTop() : 0;
			el.css({left:allw/2 - w/2 + scrollWidth, top:allh/2 - h/2 + scrollHeight});
		};

		
		//让某个元素始终保持在相对的固定位置(不随滚动条的滚动而变化)
		Main.startFixed = function(el, time){
			var el = $(el),fn,time = time || 500,
				top = parseInt(el.css('top')),sTop = Main.win.scrollTop(),_sTop = sTop,
				left = parseInt(el.css('left')),sLeft = Main.win.scrollLeft(),_sLeft = sLeft;
				
				fn = function(){
					var h = el.height(),w = el.width(),allw = Main.win.width(),allh = Main.win.height();
					_sTop = Main.win.scrollTop();
					_sLeft = Main.win.scrollLeft();
					el.stop();
					el.animate({top:allh/2 - h/2 + _sTop}, 50);
					sTop = _sTop;
					el.animate({left:allw/2 - w/2 + _sLeft}, 50);
					sLeft = _sLeft;
				};
				//注意这里从代码层面上看，Timer 和 util形成了相互依赖 
				return new host['Timer']({time:time,fn:fn});
		};
		
		//计算字符长度，中文算2个
		Main.getByteLen = function(str){
			return str.replace(/[^\x00-\xff]/g, 'xx').length;
		};
		
		//获取url参数
		Main.getParam = function(name){
			var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");  
			if (reg.test(location.href)){
				return unescape(RegExp.$2.replace(/\+/g, " "));
			}else{
				return null;
			}
		};
			/**
		 * 和PHP一样的时间戳格式化函数
		 * @param  {string} format    格式'Y-m-d' || 'Y-m-d H:i:s'
		 * @param  {int}    timestamp 要格式化的时间 默认为当前时间
		 * @return {string}           格式化的时间字符串
		 */
		Main.UnixToDate = function(format, timestamp){
			var a, jsdate=((timestamp) ? new Date(timestamp*1000) : new Date());
			var pad = function(n, c){
				if((n = n + "").length < c){
					return new Array(++c - n.length).join("0") + n;
				} else {
					return n;
				}
			};
			var txt_weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
			var txt_ordin = {1:"st", 2:"nd", 3:"rd", 21:"st", 22:"nd", 23:"rd", 31:"st"};
			var txt_months = ["", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
			var f = {
				// Day
				d: function(){return pad(f.j(), 2)},
				D: function(){return f.l().substr(0,3)},
				j: function(){return jsdate.getDate()},
				l: function(){return txt_weekdays[f.w()]},
				N: function(){return f.w() + 1},
				S: function(){return txt_ordin[f.j()] ? txt_ordin[f.j()] : 'th'},
				w: function(){return jsdate.getDay()},
				z: function(){return (jsdate - new Date(jsdate.getFullYear() + "/1/1")) / 864e5 >> 0},
			   
				// Week
				W: function(){
					var a = f.z(), b = 364 + f.L() - a;
					var nd2, nd = (new Date(jsdate.getFullYear() + "/1/1").getDay() || 7) - 1;
					if(b <= 2 && ((jsdate.getDay() || 7) - 1) <= 2 - b){
						return 1;
					} else{
						if(a <= 2 && nd >= 4 && a >= (6 - nd)){
							nd2 = new Date(jsdate.getFullYear() - 1 + "/12/31");
							return date("W", Math.round(nd2.getTime()/1000));
						} else{
							return (1 + (nd <= 3 ? ((a + nd) / 7) : (a - (7 - nd)) / 7) >> 0);
						}
					}
				},
			   
				// Month
				F: function(){return txt_months[f.n()]},
				m: function(){return pad(f.n(), 2)},
				M: function(){return f.F().substr(0,3)},
				n: function(){return jsdate.getMonth() + 1},
				t: function(){
					var n;
					if( (n = jsdate.getMonth() + 1) == 2 ){
						return 28 + f.L();
					} else{
						if( n & 1 && n < 8 || !(n & 1) && n > 7 ){
							return 31;
						} else{
							return 30;
						}
					}
				},
			   
				// Year
				L: function(){var y = f.Y();return (!(y & 3) && (y % 1e2 || !(y % 4e2))) ? 1 : 0},
				//o not supported yet
				Y: function(){return jsdate.getFullYear()},
				y: function(){return (jsdate.getFullYear() + "").slice(2)},
			   
				// Time
				a: function(){return jsdate.getHours() > 11 ? "pm" : "am"},
				A: function(){return f.a().toUpperCase()},
				B: function(){
					// peter paul koch:
					var off = (jsdate.getTimezoneOffset() + 60)*60;
					var theSeconds = (jsdate.getHours() * 3600) + (jsdate.getMinutes() * 60) + jsdate.getSeconds() + off;
					var beat = Math.floor(theSeconds/86.4);
					if (beat > 1000) beat -= 1000;
					if (beat < 0) beat += 1000;
					if ((String(beat)).length == 1) beat = "00"+beat;
					if ((String(beat)).length == 2) beat = "0"+beat;
					return beat;
				},
				g: function(){return jsdate.getHours() % 12 || 12},
				G: function(){return jsdate.getHours()},
				h: function(){return pad(f.g(), 2)},
				H: function(){return pad(jsdate.getHours(), 2)},
				i: function(){return pad(jsdate.getMinutes(), 2)},
				s: function(){return pad(jsdate.getSeconds(), 2)},
				//u not supported yet
			   
				// Timezone
				//e not supported yet
				//I not supported yet
				O: function(){
					var t = pad(Math.abs(jsdate.getTimezoneOffset()/60*100), 4);
					if (jsdate.getTimezoneOffset() > 0) t = "-" + t; else t = "+" + t;
					return t;
				},
				P: function(){var O = f.O();return (O.substr(0, 3) + ":" + O.substr(3, 2))},
				//T not supported yet
				//Z not supported yet
			   
				// Full Date/Time
				c: function(){return f.Y() + "-" + f.m() + "-" + f.d() + "T" + f.h() + ":" + f.i() + ":" + f.s() + f.P()},
				//r not supported yet
				U: function(){return Math.round(jsdate.getTime()/1000)}
			};
			   
			return format.replace(/[\\]?([a-zA-Z])/g, function(t, s){
				if( t!=s ){
					// escaped
					ret = s;
				} else if( f[s] ){
					// a date function exists
					ret = f[s]();
				} else{
					// nothing special
					ret = s;
				}
				return ret;
			});
		};
		
		//Base64加密
		Main.Base64Method = function(){		
			_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="; 			
			this.encode = function (input) {  
				var output = "";
				var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
				var i = 0;
				input = _utf8_encode(input);
				while (i < input.length) {
					chr1 = input.charCodeAt(i++);
					chr2 = input.charCodeAt(i++);
					chr3 = input.charCodeAt(i++);
					enc1 = chr1 >> 2;
					enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
					enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
					enc4 = chr3 & 63;
					if (isNaN(chr2)) {
						enc3 = enc4 = 64;
					} else if (isNaN(chr3)) {
						enc4 = 64;
					}
					output = output +
					_keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
					_keyStr.charAt(enc3) + _keyStr.charAt(enc4);
				}
				return output;
			} 			
			this.decode = function (input) {  
				var output = "";
				var chr1, chr2, chr3;
				var enc1, enc2, enc3, enc4;
				var i = 0;
				input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
				while (i < input.length) {
					enc1 = _keyStr.indexOf(input.charAt(i++));
					enc2 = _keyStr.indexOf(input.charAt(i++));
					enc3 = _keyStr.indexOf(input.charAt(i++));
					enc4 = _keyStr.indexOf(input.charAt(i++));
					chr1 = (enc1 << 2) | (enc2 >> 4);
					chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
					chr3 = ((enc3 & 3) << 6) | enc4;
					output = output + String.fromCharCode(chr1);
					if (enc3 != 64) {
						output = output + String.fromCharCode(chr2);
					}
					if (enc4 != 64) {
						output = output + String.fromCharCode(chr3);
					}
				}
				output = _utf8_decode(output);
				return output;
			} 			
			_utf8_encode = function (string) {
				string = string.replace(/\r\n/g,"\n");
				var utftext = "";
				for (var n = 0; n < string.length; n++) {
					var c = string.charCodeAt(n);
					if (c < 128) {
						utftext += String.fromCharCode(c);
					} else if((c > 127) && (c < 2048)) {
						utftext += String.fromCharCode((c >> 6) | 192);
						utftext += String.fromCharCode((c & 63) | 128);
					} else {
						utftext += String.fromCharCode((c >> 12) | 224);
						utftext += String.fromCharCode(((c >> 6) & 63) | 128);
						utftext += String.fromCharCode((c & 63) | 128);
					}
		 
				}
				return utftext;
			}			
			_utf8_decode = function (utftext) {
				var string = "";
				var i = 0;
				var c = c1 = c2 = 0;
				while ( i < utftext.length ) {
					c = utftext.charCodeAt(i);
					if (c < 128) {
						string += String.fromCharCode(c);
						i++;
					} else if((c > 191) && (c < 224)) {
						c2 = utftext.charCodeAt(i+1);
						string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
						i += 2;
					} else {
						c2 = utftext.charCodeAt(i+1);
						c3 = utftext.charCodeAt(i+2);
						string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
						i += 3;
					}
				}
				return string;
			}
		};
		
		
		//BASE64解密
		(function(){
			var END_OF_INPUT = -1;
			var base64Chars = new Array("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "/");
			var reverseBase64Chars = new Array();
			for (var i = 0; i < base64Chars.length; i++) {
				reverseBase64Chars[base64Chars[i]] = i
			}
			var base64Str;
			var base64Count;
			function setBase64Str(a) {
				base64Str = a;
				base64Count = 0
			}
			function readReverseBase64() {
				if (!base64Str) {
					return END_OF_INPUT
				}
				while (true) {
					if (base64Count >= base64Str.length) {
						return END_OF_INPUT
					}
					var a = base64Str.charAt(base64Count);
					base64Count++;
					if (reverseBase64Chars[a]) {
						return reverseBase64Chars[a]
					}
					if (a == "A") {
						return 0
					}
				}
				return END_OF_INPUT
			}
			function ntos(a) {
				a = a.toString(16);
				if (a.length == 1) {
					a = "0" + a
				}
				a = "%" + a;
				return unescape(a)
			}
			function decodeBase64(d) {
				setBase64Str(d);
				var a = "";
				var c = new Array(4);
				var b = false;
				while (!b && (c[0] = readReverseBase64()) != END_OF_INPUT && (c[1] = readReverseBase64()) != END_OF_INPUT) {
					c[2] = readReverseBase64();
					c[3] = readReverseBase64();
					a += ntos((((c[0] << 2) & 255) | c[1] >> 4));
					if (c[2] != END_OF_INPUT) {
						a += ntos((((c[1] << 4) & 255) | c[2] >> 2));
						if (c[3] != END_OF_INPUT) {
							a += ntos((((c[2] << 6) & 255) | c[3]))
						} else {
							b = true
						}
					} else {
						b = true
					}
				}
				return a
			}
			
			Main.decodeBase64 = decodeBase64;
			
		})();
		
		//根据模板和数据对象生成模板内容字符串
		//模板格式 <#=xxx#>
		Main.template = function(tpl, data){
			var me = this,o = data,p,reg;
			o['global_path_url'] = global_path_url;
			for(p in o){
				if(o.hasOwnProperty(p)){
					reg = RegExp('<#=' + p + '#>', 'g');
					tpl = tpl.replace(reg, o[p]);
				}
			}
			return tpl;
		};	
		
		
		//将数字保留两位小数并且千位使用逗号分隔
		Main.formatMoney = function(num){
			var num = Number(num),
				re = /(-?\d+)(\d{3})/;
				
			if(Number.prototype.toFixed){
				num = (num).toFixed(2);
			}else{
				num = Math.round(num*100)/100
			}
			num  =  '' + num;
			while(re.test(num)){
				num = num.replace(re,"$1,$2");
			}
			return num;  
		};
		
		//将被格式的金额返回成float型
		Main.ToForamtmoney = function(num){		
			return parseFloat(num.replace(/[^\d\.-]/g, ""));
		} 
		
		//格式化浮点数形式(只能输入正浮点数，且小数点后只能跟四位,总体数值不能大于999999999999999共15位:数值999兆)
		Main.formatFloat =function( num ){
			num = num.toString().replace(/^[^\d]/g,'');
			num = num.replace(/[^\d.]/g,'');
			num = num.replace(/\.{2,}/g,'.');
			num = num.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			if( num.indexOf(".") != -1 ){
				var data = num.split('.');
				num = (data[0].substr(0,15))+'.'+(data[1].substr(0,2));
			}else{
				num = num.substr(0,15);
			}
			return num;
		}
		
		
	host[name] = Main;
	
})(phoenix, "util", jQuery);


/*
* JSON2.JS
* 场景：IE6.7没有默认的JSON标准对象
 */
if (typeof JSON !== 'object') {
    JSON = {};
}

(function () {
    'use strict';

    function f(n) {
        // Format integers to have at least two digits.
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function () {

            return isFinite(this.valueOf())
                ? this.getUTCFullYear()     + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate())      + 'T' +
                    f(this.getUTCHours())     + ':' +
                    f(this.getUTCMinutes())   + ':' +
                    f(this.getUTCSeconds())   + 'Z'
                : null;
        };

        String.prototype.toJSON      =
            Number.prototype.toJSON  =
            Boolean.prototype.toJSON = function () {
                return this.valueOf();
            };
    }

    var cx,
        escapable,
        gap,
        indent,
        meta,
        rep;


    function quote(string) {

// If the string contains no control characters, no quote characters, and no
// backslash characters, then we can safely slap some quotes around it.
// Otherwise we must also replace the offending characters with safe escape
// sequences.

        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string'
                ? c
                : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }


    function str(key, holder) {

// Produce a string from holder[key].

        var i,          // The loop counter.
            k,          // The member key.
            v,          // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];

// If the value has a toJSON method, call it to obtain a replacement value.

        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }

// If we were called with a replacer function, then call the replacer to
// obtain a replacement value.

        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }

// What happens next depends on the value's type.

        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':

// JSON numbers must be finite. Encode non-finite numbers as null.

            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':

// If the value is a boolean or null, convert it to a string. Note:
// typeof null does not produce 'null'. The case is included here in
// the remote chance that this gets fixed someday.

            return String(value);

// If the type is 'object', we might be dealing with an object or an array or
// null.

        case 'object':

// Due to a specification blunder in ECMAScript, typeof null is 'object',
// so watch out for that case.

            if (!value) {
                return 'null';
            }

// Make an array to hold the partial results of stringifying this object value.

            gap += indent;
            partial = [];

// Is the value an array?

            if (Object.prototype.toString.apply(value) === '[object Array]') {

// The value is an array. Stringify every element. Use null as a placeholder
// for non-JSON values.

                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }

// Join all of the elements together, separated with commas, and wrap them in
// brackets.

                v = partial.length === 0
                    ? '[]'
                    : gap
                    ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']'
                    : '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }

// If the replacer is an array, use it to select the members to be stringified.

            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    if (typeof rep[i] === 'string') {
                        k = rep[i];
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {

// Otherwise, iterate through all of the keys in the object.

                for (k in value) {
                    if (Object.prototype.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }

// Join all of the member texts together, separated with commas,
// and wrap them in braces.

            v = partial.length === 0
                ? '{}'
                : gap
                ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}'
                : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }

// If the JSON object does not yet have a stringify method, give it one.

    if (typeof JSON.stringify !== 'function') {
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        };
        JSON.stringify = function (value, replacer, space) {

// The stringify method takes a value and an optional replacer, and an optional
// space parameter, and returns a JSON text. The replacer can be a function
// that can replace values, or an array of strings that will select the keys.
// A default replacer method can be provided. Use of the space parameter can
// produce text that is more easily readable.

            var i;
            gap = '';
            indent = '';

// If the space parameter is a number, make an indent string containing that
// many spaces.

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

// If the space parameter is a string, it will be used as the indent string.

            } else if (typeof space === 'string') {
                indent = space;
            }

// If there is a replacer, it must be a function or an array.
// Otherwise, throw an error.

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                    typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

// Make a fake root object containing our value under the key of ''.
// Return the result of stringifying the value.

            return str('', {'': value});
        };
    }


// If the JSON object does not yet have a parse method, give it one.

    if (typeof JSON.parse !== 'function') {
        cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        JSON.parse = function (text, reviver) {

// The parse method takes a text and an optional reviver function, and returns
// a JavaScript value if the text is a valid JSON text.

            var j;

            function walk(holder, key) {

// The walk method is used to recursively walk the resulting structure so
// that modifications can be made.

                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }


// Parsing happens in four stages. In the first stage, we replace certain
// Unicode characters with escape sequences. JavaScript handles many characters
// incorrectly, either silently deleting them, or treating them as line endings.

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }

// In the second stage, we run the text against regular expressions that look
// for non-JSON patterns. We are especially concerned with '()' and 'new'
// because they can cause invocation, and '=' because it can cause mutation.
// But just to be safe, we want to reject all unexpected forms.

// We split the second stage into 4 regexp operations in order to work around
// crippling inefficiencies in IE's and Safari's regexp engines. First we
// replace the JSON backslash pairs with '@' (a non-JSON character). Second, we
// replace all simple value tokens with ']' characters. Third, we delete all
// open brackets that follow a colon or comma or that begin the text. Finally,
// we look to see that the remaining characters are only whitespace or ']' or
// ',' or ':' or '{' or '}'. If that is so, then the text is safe for eval.

            if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

// In the third stage we use the eval function to compile the text into a
// JavaScript structure. The '{' operator is subject to a syntactic ambiguity
// in JavaScript: it can begin a block or an object literal. We wrap the text
// in parens to eliminate the ambiguity.

                j = eval('(' + text + ')');

// In the optional fourth stage, we recursively walk the new structure, passing
// each name/value pair to a reviver function for possible transformation.

                return typeof reviver === 'function'
                    ? walk({'': j}, '')
                    : j;
            }

// If the text is not JSON parseable, then a SyntaxError is thrown.

            throw new SyntaxError('JSON.parse');
        };
    }
}());







