/* jshint eqeqeq:false */
/*jshint -W120 */


(function (global, gameFactory) {
    if (typeof module === "object" && typeof module.exports === "object") {
        module.exports.game = gameFactory();
    } else {
        global.gameFactory = gameFactory;
    }

}(typeof window !== "undefined" ? window : this, function (window, noGlobal) {
//工具方法 开始******************************************
    var _ = function () {
        },
        nativeKeys = Object.keys,
        ObjProt = Object.prototype,
        hasOwnPropert = ObjProt.hasOwnProperty,
        property = function (key) {
            return function (obj) {
                return obj === null ? void 0 : obj[key];
            };
        },
        MAX_ARRAY_INDEX = Math.pow(2, 53) - 1,
        collectNonEnumProps = function (obj, keys) {
            var nonEnumIdx = nonEnumerableProps.length;
            var constructor = obj.constructor;
            var proto = _.isFunction(constructor) && constructor.prototype || ObjProto;

            // Constructor is a special case.
            var prop = 'constructor';
            if (_.has(obj, prop) && !_.contains(keys, prop)) keys.push(prop);

            while (nonEnumIdx--) {
                prop = nonEnumerableProps[nonEnumIdx];
                if (prop in obj && obj[prop] !== proto[prop] && !_.contains(keys, prop)) {
                    keys.push(prop);
                }
            }
        },
        optimizeCb = function (func, context, argCount) {
            if (context === void 0) return func;
            switch (argCount === null ? 3 : argCount) {
                case 1:
                    return function (value) {
                        return func.call(context, value);
                    };
                case 2:
                    return function (value, other) {
                        return func.call(context, value, other);
                    };
                case 3:
                    return function (value, index, collection) {
                        return func.call(context, value, index, collection);
                    };
                case 4:
                    return function (accumulator, value, index, collection) {
                        return func.call(context, accumulator, value, index, collection);
                    };
            }
            return function () {
                return func.apply(context, arguments);
            };
        },
        createAssigner = function (keysFunc, undefinedOnly) {
            return function (obj) {
                var length = arguments.length;
                if (length < 2 || obj === null) return obj;
                for (var index = 1; index < length; index++) {
                    var source = arguments[index],
                        keys = keysFunc(source),
                        l = keys.length;
                    for (var i = 0; i < l; i++) {
                        var key = keys[i];
                        if (!undefinedOnly || obj[key] === void 0) obj[key] = source[key];
                    }
                }
                return obj;
            };
        }, isArrayLike = function (collection) {
            var length = getLength(collection);
            return typeof length == 'number' && length >= 0 && length <= MAX_ARRAY_INDEX;
        }, getLength = property('length'), cb = function (value, context, argCount) {
            if (value === null) return _.identity;
            if (_.isFunction(value)) return optimizeCb(value, context, argCount);
            if (_.isObject(value)) return _.matcher(value);
            return _.property(value);
        };

    function createIndexFinder(dir, predicateFind, sortedIndex) {
        return function (array, item, idx) {
            var i = 0, length = getLength(array);
            if (typeof idx == 'number') {
                if (dir > 0) {
                    i = idx >= 0 ? idx : Math.max(idx + length, i);
                } else {
                    length = idx >= 0 ? Math.min(idx + 1, length) : idx + length + 1;
                }
            } else if (sortedIndex && idx && length) {
                idx = sortedIndex(array, item);
                return array[idx] === item ? idx : -1;
            }
            if (item !== item) {
                idx = predicateFind(slice.call(array, i, length), _.isNaN);
                return idx >= 0 ? idx + i : -1;
            }
            for (idx = dir > 0 ? i : length - 1; idx >= 0 && idx < length; idx += dir) {
                if (array[idx] === item) return idx;
            }
            return -1;
        };
    }

    function createPredicateIndexFinder(dir) {
        return function (array, predicate, context) {
            predicate = cb(predicate, context);
            var length = getLength(array);
            var index = dir > 0 ? 0 : length - 1;
            for (; index >= 0 && index < length; index += dir) {
                if (predicate(array[index], index, array)) return index;
            }
            return -1;
        };
    }

    _.matcher = _.matches = function (attrs) {
        attrs = _.extendOwn({}, attrs);
        return function (obj) {
            return _.isMatch(obj, attrs);
        };
    };

    _.identity = function (value) {
        return value;
    };
    _.filter = _.select = function (obj, predicate, context) {
        var results = [];
        predicate = cb(predicate, context);
        _.each(obj, function (value, index, list) {
            if (predicate(value, index, list)) results.push(value);
        });
        return results;
    };
    _.each = _.forEach = function (obj, iteratee, context) {
        iteratee = optimizeCb(iteratee, context);
        var i, length;
        if (isArrayLike(obj)) {
            for (i = 0, length = obj.length; i < length; i++) {
                iteratee(obj[i], i, obj);
            }
        } else {
            var keys = _.keys(obj);
            for (i = 0, length = keys.length; i < length; i++) {
                iteratee(obj[keys[i]], keys[i], obj);
            }
        }
        return obj;
    };

    _.findIndex = createPredicateIndexFinder(1);
    _.sortedIndex = function (array, obj, iteratee, context) {
        iteratee = cb(iteratee, context, 1);
        var value = iteratee(obj);
        var low = 0, high = getLength(array);
        while (low < high) {
            var mid = Math.floor((low + high) / 2);
            if (iteratee(array[mid]) < value) low = mid + 1; else high = mid;
        }
        return low;
    };
    _.indexOf = createIndexFinder(1, _.findIndex, _.sortedIndex);

    _.has = function (obj, key) {
        return obj !== null && hasOwnPropert.call(obj, key);
    };
    _.isFunction = function (obj) {
        return typeof obj == 'function' || false;
    };

    _.isObject = function (obj) {
        var type = typeof obj;
        return type === 'function' || type === 'object' && !!obj;
    };

    _.keys = function (obj) {
        if (!_.isObject(obj)) return [];
        if (nativeKeys) return nativeKeys(obj);
        var keys = [];
        for (var key in obj) if (_.has(obj, key)) keys.push(key);
        // Ahem, IE < 9.
        if (hasEnumBug) collectNonEnumProps(obj, keys);
        return keys;
    };
    _.extendOwn = _.assign = createAssigner(_.keys);
    _.values = function (obj) {
        var keys = _.keys(obj);
        var length = keys.length;
        var values = Array(length);
        for (var i = 0; i < length; i++) {
            values[i] = obj[keys[i]];
        }
        return values;
    };

    _.contains = function (obj, item, fromIndex, guard) {
        if (!isArrayLike(obj)) obj = _.values(obj);
        if (typeof fromIndex != 'number' || guard) fromIndex = 0;
        return _.indexOf(obj, item, fromIndex) >= 0;
    };
    _.compact = function (array) {
        return _.filter(array, _.identity);
    };


//获取多个数组间相同选号的个数,此方法用于组选和值
    _.intersection = function (array, arraySecond) {
        var result = 0;
        for (var i = 0; i < array.length; i++) {
            if (array[i] && arraySecond[i]) result++;
        }
        return result;
    };
    _.now = Date.now || function () {
            return new Date().getTime();
        };
    _.throttle = function (func, wait, options) {
        var context, args, result;
        var timeout = null;
        var previous = 0;
        if (!options) options = {};
        var later = function () {
            previous = options.leading === false ? 0 : _.now();
            timeout = null;
            result = func.apply(context, args);
            if (!timeout) context = args = null;
        };
        return function () {
            var now = _.now();
            if (!previous && options.leading === false) previous = now;
            var remaining = wait - (now - previous);
            context = this;
            args = arguments;
            if (remaining <= 0 || remaining > wait) {
                if (timeout) {
                    clearTimeout(timeout);
                    timeout = null;
                }
                previous = now;
                result = func.apply(context, args);
                if (!timeout) context = args = null;
            } else if (!timeout && options.trailing !== false) {
                timeout = setTimeout(later, remaining);
            }
            return result;
        };
    };

    _.debounce = function (func, wait, immediate) {
        var timeout, args, context, timestamp, result;

        var later = function () {
            var last = _.now() - timestamp;

            if (last < wait && last >= 0) {
                timeout = setTimeout(later, wait - last);
            } else {
                timeout = null;
                if (!immediate) {
                    result = func.apply(context, args);
                    if (!timeout) context = args = null;
                }
            }
        };

        return function () {
            context = this;
            args = arguments;
            timestamp = _.now();
            var callNow = immediate && !timeout;
            if (!timeout) timeout = setTimeout(later, wait);
            if (callNow) {
                result = func.apply(context, args);
                context = args = null;
            }

            return result;
        };
    };
//创造选号数组,模仿java
    function range(start, position) {
        var arr = [];
        for (; start < position; start++) arr.push(start);
        return arr;
    }

//创造号码记录数组
    function recordArray(start, position) {
        var arr = [];
        for (; start < position; start++) arr.push(false);
        return arr;
    }

//1.阶乘公式
    function factorial(num) {
        var total = 1;
        for (var i = 1; i <= num; i++) {
            total *= i;
        }
        return total;
    }

//2.组合公式
    function C(n, r) {
        if (r > n) return 0;
        if (r === undefined) throw Error("请传入每个参数");
        /*---数学公式=n!/r!(n-r)!----*/
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

//提取选号结果=>swichesArray是一个布尔系列数组.用于指定提取NumberArray数组的值
    function pluckChooseResult(NumberArray, swichesArray) {
        return NumberArray.filter(function (value, key) {
            return swichesArray[key];
        });
    }

    function hanlder(v, k, arr) {
        arr[k] = false;
    }

//给出一个多级字符串,根据它来提取值
    function pluckDeepValueAString() {
        //return new Function('options', 'return options.' + key + ';')(obj);
    }

//机选一个号码.
    function randomPluck(array, num) {
        var cacheIndex = [];

        function randomNum() {
            var n = Math.floor(Math.random() * array.length);
            if (cacheIndex.indexOf(n) > -1) n = randomNum();
            cacheIndex.push(n);
            return n;
        }

        if (!num) {
            var index = Math.floor(Math.random() * array.length);
            array[index] = true;

        } else {
            for (var i = 0; i < num; i++) array[randomNum()] = true;
        }
        return array;
    }

    function getNormalMethodName(type) {
        return type.replace(/\./g, '');
    }

//工具方法 结束*********************************************


//业务工具 开始*********************************************
//创建选球
    function CreateOneRowBall() {
        return {'': recordArray(0, 10)};
    }

    function CreateOneRowBallvesion2() {
        return {'二重号位': recordArray(0, 10)};
    }

    function CreatefiveRowBall() {
        return {
            '万位': recordArray(0, 10),
            '千位': recordArray(0, 10),
            '百位': recordArray(0, 10),
            '十位': recordArray(0, 10),
            '个位': recordArray(0, 10)
        };
    }

    function CreateFourRowBall() {
        return {
            '千位': recordArray(0, 10),
            '百位': recordArray(0, 10),
            '十位': recordArray(0, 10),
            '个位': recordArray(0, 10)
        };
    }

    function CreateThreeRowBall() {
        return {
            '万位': recordArray(0, 10),
            '千位': recordArray(0, 10),
            '百位': recordArray(0, 10)
        };
    }

    function CreateThreeRowBallversion2() {
        return {
            '千位': recordArray(0, 10),
            '百位': recordArray(0, 10),
            '十位': recordArray(0, 10)
        };
    }

    function CreateThreeRowBallversion3() {
        return {
            '百位': recordArray(0, 10),
            '十位': recordArray(0, 10),
            '个位': recordArray(0, 10)
        };
    }

    function CreateTwoRowBall() {
        return {
            '万位': recordArray(0, 10),
            '千位': recordArray(0, 10)
        };
    }

    function CreateTwoRowBallversion2() {
        return {
            '十位': recordArray(0, 10),
            '个位': recordArray(0, 10)
        };
    }

    function CreateChonghaoBall() {
        return {
            '二重号位': recordArray(0, 10),
            '单号位': recordArray(0, 10)
        };
    }

    function CreateChonghaoBallversion2() {
        return {
            '三重号位': recordArray(0, 10),
            '单号位': recordArray(0, 10)
        };
    }

    function CreateChonghaoBallversion3() {
        return {
            '三重号位': recordArray(0, 10),
            '二重号位': recordArray(0, 10)
        };
    }

    function CreateChonghaoBallversion4() {
        return {
            '四重号位': recordArray(0, 10),
            '单号位': recordArray(0, 10)
        };
    }

//设置大小单双,清除
    function daxiaodanshuangqing(arr, tag) {
        function setDaXiao(startIndex, endindex, array) {
            for (var i = 0; i < array.length; i++) {
                if (i >= startIndex && i < endindex) {
                    array[i] = true;
                } else {
                    array[i] = false;
                }
            }
        }

        function setDanShuang(tag, array) {
            function setFalse(arr, i) {
                arr[i] = false;
            }

            function setTrue(arr, i) {
                arr[i] = true;
            }

            for (var i = 0; i < array.length; i++) {
                if (tag == 'dan') {
                    if (i % 2 === 0) {
                        setFalse(array, i);
                    } else {
                        setTrue(array, i);
                    }
                } else {
                    if (i % 2 === 0) {
                        setTrue(array, i);
                    } else {
                        setFalse(array, i);

                    }

                }
            }
        }

        function qing(array) {
            array.some(hanlder);
        }

        function setquan(array) {
            for (var i = 0; i < array.length; i++) arr[i] = true;
        }

        switch (tag) {
            case 'da':
                setDaXiao(arr.length / 2, arr.length, arr);
                break;
            case 'xiao':
                setDaXiao(0, arr.length / 2, arr);
                break;
            case 'dan':
                setDanShuang('dan', arr);
                break;
            case 'shuang':
                setDanShuang('shuang', arr);
                break;
            case 'quan':
                setquan(arr);
                break;
            case 'qing':
                qing(arr);
                break;
        }

        return arr;
    }


//.普通计算投注数
    function generalCount(arr) {
        //由于这里传进来的球是没有通过处理的._.compact用于删除掉没有被选中的球,然后才能计算投注
        var result = 1;
        //简单判断是不是二维数组,如果是二维数组,计算投注原则就是:每行中选中的球相乘,如果不是二维数组,那么计算投注原则就是选中多少个球,就是多少投
        if (arr[0] instanceof Array) {
            //2._.compact用于删除掉没有被选中的球
            for (var i = 0; i < arr.length; i++) result = result * _.compact(arr[i]).length;
        } else {
            return _.compact(arr).length;
        }
        return result;
    }


    function getMyChoose(obj) {
        var funName = this.constructor.name;
        var ballOneTo26 = /^\w+san_2000zuxuanhezhi$/;
        var ballOneTo17 = /^\w+er_2000zuxuanhezhi$/;
        var startPos = ballOneTo26.test(funName) || ballOneTo17.test(funName) ? 1 : 0;

        var arr = _.values(this.checkBallArray);
        var rs = [];
        for (var i = 0; i < arr.length; i++) {
            rs[i] = pluckChooseResult(range(startPos, arr[i].length + startPos), arr[i]);
        }
        obj.result = rs;
        return obj;

    }

//一一一一一一一一一一一一,五星,四星组选投注数计算公式------------------------------


    /*五星组选120*/


    /*五星组选60*/


    /*五星组选30*/


    function wuxingbudingwei() {
        var count = this.constructor(_.compact(_.values(this.checkBallArray)[0]).length);
        return getMyChoose.call(this, {count: count});

    }

    function generGetData() {
        var count = this.constructor(_.values(this.checkBallArray));
        return getMyChoose.call(this, {count: count});

    }

    function zuxuanGenner() {
        var aArr = _.values(this.checkBallArray)[0];
        var bArr = _.values(this.checkBallArray)[1];
        var a = _.compact(aArr).length;
        var b = _.compact(bArr).length;
        var r = _.intersection(aArr, bArr);
        var count = this.constructor(a, b, r);
        return getMyChoose.call(this, {count: count});

    }

    function zhuhe() {
        var arr = _.compact(_.values(this.checkBallArray)[0]);
        var count = this.constructor(arr.length);
        return getMyChoose.call(this, {count: count});
    }

    function countN() {
        var count = 0;
        var result = pluckChooseResult(range(0, 28), _.values(this.checkBallArray)[0]);
        for (var i = 0; i < result.length; i++) {
            count += this.constructor(result[i]);
        }
        return getMyChoose.call(this, {count: count});

    }

    function sanzuxuanhezhi() {
        var count = 0;
        var result = pluckChooseResult(range(1, 27), _.values(this.checkBallArray)[0]);
        for (var i = 0; i < result.length; i++) {
            count += this.constructor(result[i]);
        }
        return getMyChoose.call(this, {count: count});
    }


    function runArray(ball) {
        for (var key in ball) {
            if (ball[key] instanceof Array) {
                ball[key].some(hanlder);
                randomPluck(ball[key]);
            }
        }
    }

    /*******************************业务业务业务业务业务****************************/
    function wuxingzhixuanfushi(arr) {
        return generalCount(arr);
    }

    wuxingzhixuanfushi.prototype.checkBallArray = CreatefiveRowBall();
    wuxingzhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);

        runArray(ball);
    };
    wuxingzhixuanfushi.prototype.getData = generGetData;
    wuxingzhixuanfushi.prototype.tip = '每位选1个号码为1注';

    function wuxingzuxuanzuxuan120(a) {
        return C(a, 5);
    }

    wuxingzuxuanzuxuan120.prototype.checkBallArray = CreateOneRowBall();
    wuxingzuxuanzuxuan120.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);


        ball[0].some(hanlder);
        randomPluck(ball[0], 5);
    };
    wuxingzuxuanzuxuan120.prototype.getData = zhuhe;

    wuxingzuxuanzuxuan120.prototype.tip = '任意5个号码为1注';

    function wuxingzuxuanzuxuan60(a, b, r) {
        return C(a, 1) * C(b, 3) - r * C(b - 1, 2);
    }


    wuxingzuxuanzuxuan60.prototype.checkBallArray = CreateChonghaoBall();
    wuxingzuxuanzuxuan60.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);

        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0]);
        randomPluck(ball[1], 3);

        if (this.getData().count === 0) {
            this.getramdom();
        }

    };
    wuxingzuxuanzuxuan60.prototype.getData = zuxuanGenner;
    wuxingzuxuanzuxuan60.prototype.tip = '1个二重号和3个单号为1注';


    function wuxingzuxuanzuxuan30(a, b, r) {
        return C(a, 2) * C(b, 1) - r * C(a - 1, 1);
    }

    wuxingzuxuanzuxuan30.prototype.checkBallArray = CreateChonghaoBall();
    wuxingzuxuanzuxuan30.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);

        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0], 2);
        randomPluck(ball[1]);
        if (this.getData().count === 0) {
            this.getramdom();
        }

    };
    wuxingzuxuanzuxuan30.prototype.getData = zuxuanGenner;
    wuxingzuxuanzuxuan30.prototype.tip = '2个二重号和1个单号为1注';

    /*五星组选20*/
    function wuxingzuxuanzuxuan20(a, b, r) {
        return C(a, 1) * C(b, 2) - r * C(b - 1, 1);
    }

    wuxingzuxuanzuxuan20.prototype.checkBallArray = CreateChonghaoBallversion2();
    wuxingzuxuanzuxuan20.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);

        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0]);
        randomPluck(ball[1], 2);
        if (this.getData().count === 0) {
            this.getramdom();
        }
    };
    wuxingzuxuanzuxuan20.prototype.getData = zuxuanGenner;
    wuxingzuxuanzuxuan20.prototype.tip = '1个三重号和2个单号为1注';


    /*五星组选10*/
    function wuxingzuxuanzuxuan10(a, b, r) {
        return C(a, 1) * C(b, 1) - r;
    }

    wuxingzuxuanzuxuan10.prototype.checkBallArray = CreateChonghaoBallversion3();
    wuxingzuxuanzuxuan10.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);

        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0]);
        randomPluck(ball[1]);
        if (this.getData().count === 0) {
            this.getramdom();
        }
    };
    wuxingzuxuanzuxuan10.prototype.getData = zuxuanGenner;
    wuxingzuxuanzuxuan10.prototype.tip = '1个三重号和1个二重号为1注';

    /*五星组选 5*/
    function wuxingzuxuanzuxuan5(a, b, r) {
        return wuxingzuxuanzuxuan10(a, b, r);
    }

    wuxingzuxuanzuxuan5.prototype.checkBallArray = CreateChonghaoBallversion4();
    wuxingzuxuanzuxuan5.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);

        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0]);
        randomPluck(ball[1]);
        if (this.getData().count === 0) {
            this.getramdom();
        }
    };
    wuxingzuxuanzuxuan5.prototype.getData = zuxuanGenner;
    wuxingzuxuanzuxuan5.prototype.tip = '1个四重号和1个单号为1注';

    function wuxingbudingweiyimabudingwei(arr) {
        return generalCount(arr);
    }

    wuxingbudingweiyimabudingwei.prototype.checkBallArray = CreateOneRowBall();
    wuxingbudingweiyimabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    wuxingbudingweiyimabudingwei.prototype.getData = generGetData;

    wuxingbudingweiyimabudingwei.prototype.tip = '任选1个号为1注';


    function wuxingbudingweiermabudingwei(b) {
        return C(b, 2);
    }

    wuxingbudingweiermabudingwei.prototype.checkBallArray = CreateOneRowBall();
    wuxingbudingweiermabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    wuxingbudingweiermabudingwei.prototype.getData = wuxingbudingwei;
    wuxingbudingweiermabudingwei.prototype.tip = '任选2个号为1注';

    function wuxingbudingweisanmabudingwei(b) {
        return C(b, 3);
    }

    wuxingbudingweisanmabudingwei.prototype.checkBallArray = CreateOneRowBall();
    wuxingbudingweisanmabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 3);
    };
    wuxingbudingweisanmabudingwei.prototype.getData = wuxingbudingwei;
    wuxingbudingweisanmabudingwei.prototype.tip = '任选3个号为1注';


    function wuxingquweiyifanfengshun(arr) {
        return generalCount(arr);
    }

    wuxingquweiyifanfengshun.prototype.checkBallArray = CreateOneRowBall();
    wuxingquweiyifanfengshun.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    wuxingquweiyifanfengshun.prototype.getData = generGetData;
    wuxingquweiyifanfengshun.prototype.tip = '任选1个号为1注';

    function wuxingquweihaoshichengshuang(arr) {
        return generalCount(arr);
    }

    wuxingquweihaoshichengshuang.prototype.checkBallArray = CreateOneRowBall();
    wuxingquweihaoshichengshuang.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    wuxingquweihaoshichengshuang.prototype.getData = generGetData;
    wuxingquweihaoshichengshuang.prototype.tip = '任选1个号为1注';


    function wuxingquweisanxingbaoxi(arr) {
        return generalCount(arr);
    }

    wuxingquweisanxingbaoxi.prototype.checkBallArray = CreateOneRowBall();
    wuxingquweisanxingbaoxi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    wuxingquweisanxingbaoxi.prototype.getData = generGetData;
    wuxingquweisanxingbaoxi.prototype.tip = '任选1个号为1注';

    function wuxingquweisijifacai(arr) {
        return generalCount(arr);
    }

    wuxingquweisijifacai.prototype.checkBallArray = CreateOneRowBall();
    wuxingquweisijifacai.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    wuxingquweisijifacai.prototype.getData = generGetData;
    wuxingquweisijifacai.prototype.tip = '任选1个号为1注';


    function sixing_2000zhixuanfushi(arr) {
        return generalCount(arr);
    }

    sixing_2000zhixuanfushi.prototype.checkBallArray = CreateFourRowBall();
    sixing_2000zhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        runArray(ball);
    };
    sixing_2000zhixuanfushi.prototype.getData = generGetData;
    sixing_2000zhixuanfushi.prototype.tip = '每位选1个号为1注';

    /*四星组选24*/
    function sixing_2000zuxuanzuxuan24(a) {
        return C(a, 4);
    }

    sixing_2000zuxuanzuxuan24.prototype.checkBallArray = CreateOneRowBall();
    sixing_2000zuxuanzuxuan24.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 4);
    };
    sixing_2000zuxuanzuxuan24.prototype.getData = zhuhe;
    sixing_2000zuxuanzuxuan24.prototype.tip = '任选4个号码为1注';

//四星组选12
    function sixing_2000zuxuanzuxuan12(a, b, r) {
        return C(a, 1) * C(b, 2) - r * C(b - 1, 1);
    }

    sixing_2000zuxuanzuxuan12.prototype.checkBallArray = CreateChonghaoBall();
    sixing_2000zuxuanzuxuan12.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0]);
        randomPluck(ball[1], 2);
        if (this.getData().count === 0) {
            this.getramdom();
        }

    };
    sixing_2000zuxuanzuxuan12.prototype.getData = zuxuanGenner;
    sixing_2000zuxuanzuxuan12.prototype.tip = '1个二重号和2个单号为1注';


//四星组选6
    function sixing_2000zuxuanzuxuan6(b) {
        return C(b, 2);
    }

    sixing_2000zuxuanzuxuan6.prototype.checkBallArray = CreateOneRowBallvesion2();
    sixing_2000zuxuanzuxuan6.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    sixing_2000zuxuanzuxuan6.prototype.getData = zhuhe;
    sixing_2000zuxuanzuxuan6.prototype.tip = '2个二重号为1注';


//四星组选4
    /**
     *
     * @param a
     * @param b
     * @param r
     * @returns {number}
     */
    function sixing_2000zuxuanzuxuan4(a, b, r) {
        return C(a, 1) * C(b, 1) - r;
    }

    sixing_2000zuxuanzuxuan4.prototype.checkBallArray = CreateChonghaoBallversion2();
    sixing_2000zuxuanzuxuan4.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        ball[1].some(hanlder);
        randomPluck(ball[0]);
        randomPluck(ball[1]);
        if (this.getData().count === 0) {
            this.getramdom();
        }

    };
    sixing_2000zuxuanzuxuan4.prototype.getData = zuxuanGenner;
    sixing_2000zuxuanzuxuan4.prototype.tip = '1个三重号码和1个单号为1注';


    function sixing_2000budingweiyimabudingwei(arr) {
        return generalCount(arr);
    }

    sixing_2000budingweiyimabudingwei.prototype.checkBallArray = CreateOneRowBall();
    sixing_2000budingweiyimabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    sixing_2000budingweiyimabudingwei.prototype.getData = generGetData;
    sixing_2000budingweiyimabudingwei.prototype.tip = '任选1个号码为1注';


    function sixing_2000budingweiermabudingwei(b) {
        return C(b, 2);
    }

    sixing_2000budingweiermabudingwei.prototype.checkBallArray = CreateOneRowBall();
    sixing_2000budingweiermabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    sixing_2000budingweiermabudingwei.prototype.getData = zhuhe;
    sixing_2000budingweiermabudingwei.prototype.tip = '任选2个号码为1注';

    /**
     *
     * @param arr
     * @returns {*}
     */
    function qiansanzhixuanfushi(arr) {
        return generalCount(arr);
    }

    qiansanzhixuanfushi.prototype.checkBallArray = CreateThreeRowBall();
    qiansanzhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        runArray(ball);
    };
    qiansanzhixuanfushi.prototype.getData = generGetData;

    qiansanzhixuanfushi.prototype.tip = '每位选1个号码为1注';


//前三直选和值
    /**
     *
     * @param n
     * @returns {number}
     */

    function qiansanzhixuanhezhi(n) {
        //公式:((n+1)*(n+2))/2,n≤9;((n+1)*(n+1))-(3*(n-8)*(n-9))/2,10<=n,n<=14;((29-n)*(28-n))/2,n>=15,n≤29;
        if (n <= 9) {
            return ((n + 1) * (n + 2)) / 2;
        } else if (n >= 10 && n <= 14) {
            return (((n + 1) * (n + 2) - (3 * (n - 8) * (n - 9)))) / 2;
        } else if (n >= 15 && n <= 17) {
            return {15: 73, 16: 69, 17: 63}[n];
        } else {
            return (29 - n) * (28 - n) / 2;
        }
    }

    qiansanzhixuanhezhi.prototype.checkBallArray = {'': recordArray(0, 28)};
    qiansanzhixuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qiansanzhixuanhezhi.prototype.getData = countN;
    qiansanzhixuanhezhi.prototype.tip = '任选1个号码';

//前三直选跨度
    /**
     *
     * @param n
     * @returns {*}
     */
    function qiansanzhixuankuadu(n) {
        return {"0": 10, "1": 54, "2": 96, "3": 126, "4": 144, "5": 150, "6": 144, "7": 126, "8": 96, "9": 54}[n];
    }

    qiansanzhixuankuadu.prototype.checkBallArray = CreateOneRowBall();
    qiansanzhixuankuadu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qiansanzhixuankuadu.prototype.getData = countN;

//前三组选和值
    /**
     *
     * @param n
     * @returns {*}
     */

    function qiansanzuxuanhezhi(n) {
        return {
            "1": 1,
            "2": 2,
            "3": 2,
            "4": 4,
            "5": 5,
            "6": 6,
            "7": 8,
            "8": 10,
            "9": 11,
            "10": 13,
            "11": 14,
            "12": 14,
            "13": 15,
            "14": 15,
            "15": 14,
            "16": 14,
            "17": 13,
            "18": 11,
            "19": 10,
            "20": 8,
            "21": 6,
            "22": 5,
            "23": 4,
            "24": 2,
            "25": 2,
            "26": 1
        }[n];
    }

    qiansanzuxuanhezhi.prototype.checkBallArray = {'': recordArray(0, 26)};
    qiansanzuxuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);

    };
    qiansanzuxuanhezhi.prototype.getData = sanzuxuanhezhi;


//前三组选组三
    /**
     *
     * @param b
     * @returns {number}
     */
    function qiansanzuxuanzusan(b) {
        return b < 2 ? 0 : C(b, 2) * 2;
    }

    qiansanzuxuanzusan.prototype.checkBallArray = CreateOneRowBall();
    qiansanzuxuanzusan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    qiansanzuxuanzusan.prototype.getData = zhuhe;

    /**
     *
     * @param b
     * @returns {*}
     */
    function qiansanzuxuanzuliu(b) {
        return C(b, 3);
    }

    qiansanzuxuanzuliu.prototype.checkBallArray = CreateOneRowBall();
    qiansanzuxuanzuliu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 3);

    };
    qiansanzuxuanzuliu.prototype.getData = zhuhe;

    /**
     *
     * @param arr
     * @returns {*}
     */
    function qiansanbudingweiyimabudingwei(arr) {
        return arr;
    }

    qiansanbudingweiyimabudingwei.prototype.checkBallArray = CreateOneRowBall();
    qiansanbudingweiyimabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qiansanbudingweiyimabudingwei.prototype.getData = wuxingbudingwei;

    /**
     *
     * @param b
     * @returns {*}
     */
    function qiansanbudingweiermabudingwei(b) {
        return C(b, 2);
    }

    qiansanbudingweiermabudingwei.prototype.checkBallArray = CreateOneRowBall();
    qiansanbudingweiermabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    qiansanbudingweiermabudingwei.prototype.getData = zhuhe;
    qiansanbudingweiermabudingwei.prototype.tip = '任选2个号码为1注';


    function zhongsan_2000zhixuanfushi(arr) {
        return generalCount(arr);
    }

    zhongsan_2000zhixuanfushi.prototype.checkBallArray = CreateThreeRowBallversion2();
    zhongsan_2000zhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        runArray(ball);
    };
    zhongsan_2000zhixuanfushi.prototype.getData = generGetData;
    zhongsan_2000zhixuanfushi.prototype.tip = '每位选1个号码为1注';


//中三直选和值
    function zhongsan_2000zhixuanhezhi(n) {
        return qiansanzhixuanhezhi(n);
    }

    zhongsan_2000zhixuanhezhi.prototype.checkBallArray = {'': recordArray(0, 28)};
    zhongsan_2000zhixuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    zhongsan_2000zhixuanhezhi.prototype.getData = countN;


//中三直选跨度
    /**
     *
     * @param n
     * @returns {*}
     */
    function zhongsan_2000zhixuankuadu(n) {
        return qiansanzhixuankuadu(n);
    }

    zhongsan_2000zhixuankuadu.prototype.checkBallArray = CreateOneRowBall();
    zhongsan_2000zhixuankuadu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    zhongsan_2000zhixuankuadu.prototype.getData = countN;


//中三组选和值
    function zhongsan_2000zuxuanhezhi(n) {
        return qiansanzuxuanhezhi(n);
    }

    zhongsan_2000zuxuanhezhi.prototype.checkBallArray = {'': recordArray(0, 26)};
    zhongsan_2000zuxuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    zhongsan_2000zuxuanhezhi.prototype.getData = sanzuxuanhezhi;


//中三组选组三
    function zhongsan_2000zuxuanzusan(n) {
        return qiansanzuxuanzusan(n);
    }

    zhongsan_2000zuxuanzusan.prototype.checkBallArray = CreateOneRowBall();
    zhongsan_2000zuxuanzusan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    zhongsan_2000zuxuanzusan.prototype.getData = zhuhe;

    /**
     *
     * @param b
     * @returns {*}
     */

    function zhongsan_2000zuxuanzuliu(b) {
        return qiansanzuxuanzuliu(b);
    }

    zhongsan_2000zuxuanzuliu.prototype.checkBallArray = CreateOneRowBall();
    zhongsan_2000zuxuanzuliu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 3);
    };
    zhongsan_2000zuxuanzuliu.prototype.getData = zhuhe;

    function zhongsan_2000budingweiyimabudingwei(arr) {
        return arr;
    }

    zhongsan_2000budingweiyimabudingwei.prototype.checkBallArray = CreateOneRowBall();
    zhongsan_2000budingweiyimabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    zhongsan_2000budingweiyimabudingwei.prototype.getData = wuxingbudingwei;

    /**
     *
     * @param b
     * @returns {*}
     */
    function zhongsan_2000budingweiermabudingwei(b) {
        return C(b, 2);
    }

    zhongsan_2000budingweiermabudingwei.prototype.checkBallArray = CreateOneRowBall();
    zhongsan_2000budingweiermabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    zhongsan_2000budingweiermabudingwei.prototype.getData = zhuhe;
    zhongsan_2000budingweiermabudingwei.prototype.tip = '任选2个号码为1注';


    function housan_2000zhixuanfushi(arr) {
        return generalCount(arr);
    }

    housan_2000zhixuanfushi.prototype.checkBallArray = CreateThreeRowBallversion3();
    housan_2000zhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        runArray(ball);
    };
    housan_2000zhixuanfushi.prototype.getData = generGetData;
    housan_2000zhixuanfushi.prototype.tip = '每位选1个号码为1注';


//后三直选和值
    function housan_2000zhixuanhezhi(n) {
        return qiansanzhixuanhezhi(n);
    }

    housan_2000zhixuanhezhi.prototype.checkBallArray = {'': recordArray(0, 28)};
    housan_2000zhixuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);

    };
    housan_2000zhixuanhezhi.prototype.getData = countN;


//后三直选跨度
    function housan_2000zhixuankuadu(n) {
        return qiansanzhixuankuadu(n);
    }

    housan_2000zhixuankuadu.prototype.checkBallArray = CreateOneRowBall();
    housan_2000zhixuankuadu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);

    };
    housan_2000zhixuankuadu.prototype.getData = countN;


//后三组选和值
    function housan_2000zuxuanhezhi(n) {
        return qiansanzuxuanhezhi(n);
    }

    housan_2000zuxuanhezhi.prototype.checkBallArray = {'': recordArray(0, 26)};
    housan_2000zuxuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);

    };
    housan_2000zuxuanhezhi.prototype.getData = sanzuxuanhezhi;

//后三组选组三
    function housan_2000zuxuanzusan(n) {
        return qiansanzuxuanzusan(n);
    }

    housan_2000zuxuanzusan.prototype.checkBallArray = CreateOneRowBall();
    housan_2000zuxuanzusan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    housan_2000zuxuanzusan.prototype.getData = zhuhe;


    function housan_2000zuxuanzuliu(b) {
        return qiansanzuxuanzuliu(b);
    }

    housan_2000zuxuanzuliu.prototype.checkBallArray = CreateOneRowBall();
    housan_2000zuxuanzuliu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 3);
    };
    housan_2000zuxuanzuliu.prototype.getData = zhuhe;


    function housan_2000budingweiyimabudingwei(arr) {
        return arr;
    }

    housan_2000budingweiyimabudingwei.prototype.checkBallArray = CreateOneRowBall();
    housan_2000budingweiyimabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    housan_2000budingweiyimabudingwei.prototype.getData = wuxingbudingwei;

    /**
     *
     * @param b
     * @returns {*}
     */
    function housan_2000budingweiermabudingwei(b) {
        return C(b, 2);
    }

    housan_2000budingweiermabudingwei.prototype.checkBallArray = CreateOneRowBall();
    housan_2000budingweiermabudingwei.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);

    };
    housan_2000budingweiermabudingwei.prototype.getData = zhuhe;
    housan_2000budingweiermabudingwei.prototype.tip = '任选2个号码为1注';


    function getbaodan() {
        var count = 54;
        var result = _.compact(_.values(this.checkBallArray)[0]).length;
        if (result !== 1) {
            count = 0;
        }
        return getMyChoose.call(this, {count: count});

    }

    function qiansanzuxuanbaodan() {
        return 54;
    }

    qiansanzuxuanbaodan.prototype.checkBallArray = CreateOneRowBall();
    qiansanzuxuanbaodan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);

    };
    qiansanzuxuanbaodan.prototype.getData = getbaodan;


    function zhongsan_2000zuxuanbaodan() {
        return qiansanzuxuanbaodan();
    }

    zhongsan_2000zuxuanbaodan.prototype.checkBallArray = CreateOneRowBall();
    zhongsan_2000zuxuanbaodan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    zhongsan_2000zuxuanbaodan.prototype.getData = getbaodan;


    function housan_2000zuxuanbaodan() {
        return qiansanzuxuanbaodan();
    }

    housan_2000zuxuanbaodan.prototype.checkBallArray = CreateOneRowBall();
    housan_2000zuxuanbaodan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    housan_2000zuxuanbaodan.prototype.getData = getbaodan;


    function qianerzhixuanfushi(arr) {
        return generalCount(arr);
    }

    qianerzhixuanfushi.prototype.checkBallArray = CreateTwoRowBall();
    qianerzhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        runArray(ball);
    };
    qianerzhixuanfushi.prototype.getData = generGetData;
    qianerzhixuanfushi.prototype.tip = '每们选1个号码为1注';

    function houer_2000zhixuanfushi(arr) {
        return generalCount(arr);
    }

    houer_2000zhixuanfushi.prototype.checkBallArray = CreateTwoRowBallversion2();
    houer_2000zhixuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        runArray(ball);
    };
    houer_2000zhixuanfushi.prototype.getData = generGetData;
    houer_2000zhixuanfushi.prototype.tip = '每们选1个号码为1注';


    function erzuxuanhezhi() {
        var count = 0;
        var result = pluckChooseResult(range(0, 19), _.values(this.checkBallArray)[0]);
        for (var i = 0; i < result.length; i++) {
            count += this.constructor(result[i]);
        }
        return getMyChoose.call(this, {count: count});
    }


//前二直选和值
    function qianerzhixuanhezhi(n) {
        //公式:n+1, n<=9; 19-n,n>9;
        return n <= 9 ? n + 1 : 19 - n;
    }

    qianerzhixuanhezhi.prototype.checkBallArray = {'': recordArray(0, 19)};
    qianerzhixuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qianerzhixuanhezhi.prototype.getData = erzuxuanhezhi;


//后二直选和值
    function houer_2000zhixuanhezhi(n) {
        return qianerzhixuanhezhi(n);
    }

    houer_2000zhixuanhezhi.prototype.checkBallArray = {'': recordArray(0, 19)};
    houer_2000zhixuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    houer_2000zhixuanhezhi.prototype.getData = erzuxuanhezhi;


//前二直选跨度
    function qianerzhixuankuadu(n) {
        return {"0": 10, "1": 18, "2": 16, "3": 14, "4": 12, "5": 10, "6": 8, "7": 6, "8": 4, "9": 2}[n];
    }

    qianerzhixuankuadu.prototype.checkBallArray = CreateOneRowBall();
    qianerzhixuankuadu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qianerzhixuankuadu.prototype.getData = countN;


    function houer_2000zhixuankuadu(n) {
        return qianerzhixuankuadu(n);
    }

    houer_2000zhixuankuadu.prototype.checkBallArray = CreateOneRowBall();
    houer_2000zhixuankuadu.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    houer_2000zhixuankuadu.prototype.getData = countN;

    /**
     *组选复式
     * @param b
     * @returns {*}
     */
    function qianerzuxuanfushi(b) {
        return C(b, 2);
    }

    qianerzuxuanfushi.prototype.checkBallArray = CreateOneRowBall();
    qianerzuxuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    qianerzuxuanfushi.prototype.getData = zhuhe;
    qianerzuxuanfushi.prototype.tip = '任选2个号码为1注';

    function houer_2000zuxuanfushi(b) {
        return qianerzuxuanfushi(b);
    }

    houer_2000zuxuanfushi.prototype.checkBallArray = CreateOneRowBall();
    houer_2000zuxuanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0], 2);
    };
    houer_2000zuxuanfushi.prototype.getData = zhuhe;
    houer_2000zuxuanfushi.prototype.tip = '任选2个号码为1注';


    function erzuxunhezhi() {
        var count = 0;
        var result = pluckChooseResult(range(1, 18), _.values(this.checkBallArray)[0]);
        for (var i = 0; i < result.length; i++) {
            count += this.constructor(result[i]);
        }
        return getMyChoose.call(this, {count: count});
    }

//前二组选和值
    function qianerzuxuanhezhi(n) {
        return n <= 9 ? Math.ceil(n / 2) : Math.ceil((18 - n) / 2);
    }

    qianerzuxuanhezhi.prototype.checkBallArray = {'': recordArray(1, 18)};
    qianerzuxuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qianerzuxuanhezhi.prototype.getData = erzuxunhezhi;

//后二组选和值
    function houer_2000zuxuanhezhi(n) {
        return qianerzuxuanhezhi(n);
    }

    houer_2000zuxuanhezhi.prototype.checkBallArray = {'': recordArray(1, 18)};
    houer_2000zuxuanhezhi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    houer_2000zuxuanhezhi.prototype.getData = erzuxunhezhi;


    function erbaodan() {
        var count = qianerzuxuanbaodan();
        var result = _.compact(_.values(this.checkBallArray)[0]).length;
        if (result !== 1) count = 0;
        return getMyChoose.call(this, {count: count});
    }

    /**
     *包胆
     * @returns {number}
     */

    function qianerzuxuanbaodan() {
        return 9;
    }

    qianerzuxuanbaodan.prototype.checkBallArray = CreateOneRowBall();
    qianerzuxuanbaodan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    qianerzuxuanbaodan.prototype.getData = erbaodan;

    function houer_2000zuxuanbaodan() {
        return qianerzuxuanbaodan();
    }

    houer_2000zuxuanbaodan.prototype.checkBallArray = CreateOneRowBall();
    houer_2000zuxuanbaodan.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        ball[0].some(hanlder);
        randomPluck(ball[0]);
    };
    houer_2000zuxuanbaodan.prototype.getData = erbaodan;

    function yixing_2000dingweidanfushi(arr) {
        var result = 0;
        for (var i = 0; i < arr.length; i++) result = result += _.compact(arr[i]).length;
        return result;
    }

    yixing_2000dingweidanfushi.prototype.checkBallArray = CreatefiveRowBall();
    yixing_2000dingweidanfushi.prototype.getramdom = function () {
        var ball = _.values(this.checkBallArray);
        var n = Math.floor(Math.random() * ball.length);
        for (var key in ball) {
            if (ball[key] instanceof Array) ball[key].some(hanlder);
        }
        randomPluck(ball[n]);
    };
    yixing_2000dingweidanfushi.prototype.getData = generGetData;


    /**
     * 单式
     */

    var regularDanshi = /[\s\,\，\;\；\：\:\+\-|\n\r]+|[^\d]+/g;

    function wuxingzhixuandanshi(str) {
        var self = this;
        var reg = this.reg;
        var data = this.data = str.replace(regularDanshi, ' ').trim().split(' ');
        this.repeatData = [];
        this.errorData = [];

        if(Array.isArray(reg)) {
            data.some(function (v, k, arr) {
                arr[k] = v.split('').sort().join('');
            });
        }
        for (var i = 0; i < data.length; i++) {
            var arr = data;
            var val = data[i]

            var isError = false;
            var lastIndex = arr.lastIndexOf(val);
            if (Array.isArray(reg)) {
                    isError = false;
                for (var k = 0; k < reg.length; k++) {
                    if (reg[k].test(val)) {
                        isError = true;

                    }
                }
            } else {
                isError = reg.test(val);
            }

            if (isError) {
                if (arr.indexOf(val) != lastIndex) {
                    self.repeatData.push(arr.splice(lastIndex, 1) + "");
                    i=-1;
                }
            }

            if(!isError){
                self.errorData.push(arr.splice(lastIndex, 1) + "");
                i=-1;
            }

        }
    }

    wuxingzhixuandanshi.prototype.reg = /^\d{5}$/;

    function sixing_2000zhixuandanshi(str) {
        wuxingzhixuandanshi.call(this, str);
    }


    sixing_2000zhixuandanshi.prototype.reg = /^\d{4}$/;


    var qiansanzhixuandanshi = zhongsan_2000zhixuandanshi = housan_2000zhixuandanshi = function (str) {
        wuxingzhixuandanshi.call(this, str);
    };
    qiansanzhixuandanshi.prototype.reg = /^\d{3}$/;

    var qiansanzuxuanzusandanshi = zhongsan_2000zuxuanzusandanshi = housan_2000zuxuanzusandanshi = function (str) {
        wuxingzhixuandanshi.call(this, str);
    };
    qiansanzuxuanzusandanshi.prototype.reg = [
        /^(\d)\1(?:(?!\1)\d)$/,//112 331
        ///^(\d)(?:(?!\1)\d)\1$/,//121 232  不会有这种号码选进来,但放在这也行.
        /^(\d)(?:(?!\1)(\d))\2$/];// 011 122


    var qiansanzuxuanzuliudanshi = zhongsan_2000zuxuanzuliudanshi = housan_2000zuxuanzuliudanshi = function (str) {
        wuxingzhixuandanshi.call(this, str);
    };
    qiansanzuxuanzuliudanshi.prototype.reg = [/^(?!.*(.).*\1)\d{3}$/];// or /^(?:(\d)(?!.*\1)){3}$/   have no repeat number


    var qiansanzuxuanhunhezuxuan = zhongsan_2000zuxuanhunhezuxuan = housan_2000zuxuanhunhezuxuan = function (str) {
        wuxingzhixuandanshi.call(this, str);
    };
    qiansanzuxuanhunhezuxuan.prototype.reg = [
        /^(\d)\1(?:(?!\1)\d)$/,
        /^(\d)(?:(?!\1)(\d))\2$/,
        /^(?!.*(.).*\1)\d{3}$/
    ];


    var qianerzhixuandanshi = houer_2000zhixuandanshi = function (str) {
        wuxingzhixuandanshi.call(this, str);
    };
    qianerzhixuandanshi.prototype.reg = /^\d{2}$/;

    var qianerzuxuandanshi = houer_2000zuxuandanshi = function (str) {
        wuxingzhixuandanshi.call(this, str);
    };
    qianerzuxuandanshi.prototype.reg = /^(?!.*(.).*\1)\d{2}$/;



    var gamelimit = {
        "housan_2000.zhixuan.fushi":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zhixuan.danshi":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zhixuan.hezhi":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zhixuan.kuadu":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.hezhi":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.zusan":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.zuliu":{"maxmultiple":1201,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.hunhezuxuan":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.baodan":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.zusandanshi":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.zuxuan.zuliudanshi":{"maxmultiple":1201,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.budingwei.yimabudingwei":{"maxmultiple":54794,"usergroupmoney":null,"maxmultiples":null},
		"housan_2000.budingwei.ermabudingwei":{"maxmultiple":11111,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zhixuan.fushi":{"maxmultiple":2000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zhixuan.danshi":{"maxmultiple":2000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zhixuan.hezhi":{"maxmultiple":2000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zhixuan.kuadu":{"maxmultiple":2000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zuxuan.fushi":{"maxmultiple":4000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zuxuan.danshi":{"maxmultiple":4000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zuxuan.hezhi":{"maxmultiple":4000,"usergroupmoney":null,"maxmultiples":null},
		"houer_2000.zuxuan.baodan":{"maxmultiple":4000,"usergroupmoney":null,"maxmultiples":null},
		"yixing_2000.dingweidan.fushi":{"maxmultiple":20000,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.zhixuan.fushi":{"maxmultiple":20,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.zhixuan.danshi":{"maxmultiple":20,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.zuxuan.zuxuan24":{"maxmultiple":480,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.zuxuan.zuxuan12":{"maxmultiple":240,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.zuxuan.zuxuan6":{"maxmultiple":120,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.zuxuan.zuxuan4":{"maxmultiple":80,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.budingwei.yimabudingwei":{"maxmultiple":69324,"usergroupmoney":null,"maxmultiples":null},
		"sixing_2000.budingwei.ermabudingwei":{"maxmultiple":19464,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zhixuan.fushi":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zhixuan.danshi":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zhixuan.hezhi":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zhixuan.kuadu":{"maxmultiple":200,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.hezhi":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.zusan":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.zuliu":{"maxmultiple":1200,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.hunhezuxuan":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.baodan":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.zusandanshi":{"maxmultiple":600,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.zuxuan.zuliudanshi":{"maxmultiple":1200,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.budingwei.yimabudingwei":{"maxmultiple":54570,"usergroupmoney":null,"maxmultiples":null},
		"zhongsan_2000.budingwei.ermabudingwei":{"maxmultiple":10911,"usergroupmoney":null,"maxmultiples":null}
    };


//业务工具 结束*********************************************
    _.range = range;
    _.pluckChooseResult = pluckChooseResult;
    _.randomPluck = randomPluck;
    _.recordArray = recordArray;
    _.daxiaodanshuangqing = daxiaodanshuangqing;
    _.daxiaodanshuangqing = daxiaodanshuangqing;
    var obj = {
        _: _,
        wuxingzhixuanfushi: wuxingzhixuanfushi,
        wuxingzuxuanzuxuan120: wuxingzuxuanzuxuan120,
        wuxingzuxuanzuxuan60: wuxingzuxuanzuxuan60,
        wuxingzuxuanzuxuan30: wuxingzuxuanzuxuan30,
        wuxingzuxuanzuxuan20: wuxingzuxuanzuxuan20,
        wuxingzuxuanzuxuan10: wuxingzuxuanzuxuan10,
        wuxingzuxuanzuxuan5: wuxingzuxuanzuxuan5,
        wuxingbudingweiyimabudingwei: wuxingbudingweiyimabudingwei,
        wuxingbudingweiermabudingwei: wuxingbudingweiermabudingwei,
        wuxingbudingweisanmabudingwei: wuxingbudingweisanmabudingwei,
        wuxingquweiyifanfengshun: wuxingquweiyifanfengshun,
        wuxingquweihaoshichengshuang: wuxingquweihaoshichengshuang,
        wuxingquweisanxingbaoxi: wuxingquweisanxingbaoxi,
        wuxingquweisijifacai: wuxingquweisijifacai,
        sixing_2000zhixuanfushi: sixing_2000zhixuanfushi,
        sixing_2000zuxuanzuxuan24: sixing_2000zuxuanzuxuan24,
        sixing_2000zuxuanzuxuan12: sixing_2000zuxuanzuxuan12,
        sixing_2000zuxuanzuxuan6: sixing_2000zuxuanzuxuan6,
        sixing_2000zuxuanzuxuan4: sixing_2000zuxuanzuxuan4,
        sixing_2000budingweiyimabudingwei: sixing_2000budingweiyimabudingwei,
        sixing_2000budingweiermabudingwei: sixing_2000budingweiermabudingwei,
        qiansanzhixuanfushi: qiansanzhixuanfushi,
        qiansanzhixuanhezhi: qiansanzhixuanhezhi,
        qiansanzhixuankuadu: qiansanzhixuankuadu,
        qiansanzuxuanhezhi: qiansanzuxuanhezhi,
        qiansanzuxuanzusan: qiansanzuxuanzusan,
        qiansanzuxuanzuliu: qiansanzuxuanzuliu,
        qiansanzuxuanbaodan: qiansanzuxuanbaodan,
        qiansanbudingweiyimabudingwei: qiansanbudingweiyimabudingwei,
        qiansanbudingweiermabudingwei: qiansanbudingweiermabudingwei,
        zhongsan_2000zhixuanfushi: zhongsan_2000zhixuanfushi,
        zhongsan_2000zhixuanhezhi: zhongsan_2000zhixuanhezhi,
        zhongsan_2000zhixuankuadu: zhongsan_2000zhixuankuadu,
        zhongsan_2000zuxuanhezhi: zhongsan_2000zuxuanhezhi,
        zhongsan_2000zuxuanzusan: zhongsan_2000zuxuanzusan,
        zhongsan_2000zuxuanzuliu: zhongsan_2000zuxuanzuliu,
        zhongsan_2000zuxuanbaodan: zhongsan_2000zuxuanbaodan,
        zhongsan_2000budingweiyimabudingwei: zhongsan_2000budingweiyimabudingwei,
        zhongsan_2000budingweiermabudingwei: zhongsan_2000budingweiermabudingwei,
        housan_2000zhixuanfushi: housan_2000zhixuanfushi,
        housan_2000zhixuanhezhi: housan_2000zhixuanhezhi,
        housan_2000zhixuankuadu: housan_2000zhixuankuadu,
        housan_2000zuxuanhezhi: housan_2000zuxuanhezhi,
        housan_2000zuxuanzusan: housan_2000zuxuanzusan,
        housan_2000zuxuanzuliu: housan_2000zuxuanzuliu,
        housan_2000zuxuanbaodan: housan_2000zuxuanbaodan,
        housan_2000budingweiyimabudingwei: housan_2000budingweiyimabudingwei,
        housan_2000budingweiermabudingwei: housan_2000budingweiermabudingwei,
        qianerzhixuanfushi: qianerzhixuanfushi,
        qianerzhixuanhezhi: qianerzhixuanhezhi,
        qianerzhixuankuadu: qianerzhixuankuadu,
        qianerzuxuanfushi: qianerzuxuanfushi,
        qianerzuxuanhezhi: qianerzuxuanhezhi,
        qianerzuxuanbaodan: qianerzuxuanbaodan,
        houer_2000zhixuanfushi: houer_2000zhixuanfushi,
        houer_2000zhixuanhezhi: houer_2000zhixuanhezhi,
        houer_2000zhixuankuadu: houer_2000zhixuankuadu,
        houer_2000zuxuanfushi: houer_2000zuxuanfushi,
        houer_2000zuxuanhezhi: houer_2000zuxuanhezhi,
        houer_2000zuxuanbaodan: houer_2000zuxuanbaodan,
        yixing_2000dingweidanfushi: yixing_2000dingweidanfushi,
        wuxingzhixuandanshi: wuxingzhixuandanshi,
        sixing_2000zhixuandanshi: sixing_2000zhixuandanshi,
        qiansanzhixuandanshi: qiansanzhixuandanshi,
        qiansanzuxuanhunhezuxuan: qiansanzuxuanhunhezuxuan,
        qiansanzuxuanzusandanshi: qiansanzuxuanzusandanshi,
        qiansanzuxuanzuliudanshi: qiansanzuxuanzuliudanshi,
        zhongsan_2000zhixuandanshi: zhongsan_2000zhixuandanshi,
        zhongsan_2000zuxuanhunhezuxuan: zhongsan_2000zuxuanhunhezuxuan,
        zhongsan_2000zuxuanzusandanshi: zhongsan_2000zuxuanzusandanshi,
        zhongsan_2000zuxuanzuliudanshi: zhongsan_2000zuxuanzuliudanshi,
        housan_2000zhixuandanshi: housan_2000zhixuandanshi,
        housan_2000zuxuanhunhezuxuan: housan_2000zuxuanhunhezuxuan,
        housan_2000zuxuanzusandanshi: housan_2000zuxuanzusandanshi,
        housan_2000zuxuanzuliudanshi: housan_2000zuxuanzuliudanshi,
        qianerzhixuandanshi: qianerzhixuandanshi,
        qianerzuxuandanshi: qianerzuxuandanshi,
        houer_2000zhixuandanshi: houer_2000zhixuandanshi,
        houer_2000zuxuandanshi: houer_2000zuxuandanshi
    };

    for (var key in gamelimit) {
        var type = getNormalMethodName(key);
        if (obj[type]) {
            obj[type].prototype.maxmultiple = gamelimit[key].maxmultiple;
        }
    }

    return obj;
}));


