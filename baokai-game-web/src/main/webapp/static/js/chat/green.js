/*! nanoScrollerJS - v0.8.0 - (c) 2014 James Florentino; Licensed MIT */
!function (a, b, c) {
    "use strict";
    var d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F;
    x = {
        paneClass: "nano-pane",
        sliderClass: "nano-slider",
        contentClass: "nano-content",
        iOSNativeScrolling: !1,
        preventPageScrolling: !1,
        disableResize: !1,
        alwaysVisible: !1,
        flashDelay: 1500,
        sliderMinHeight: 20,
        sliderMaxHeight: null,
        documentContext: null,
        windowContext: null
    }, s = "scrollbar", r = "scroll", k = "mousedown", l = "mousemove", n = "mousewheel", m = "mouseup", q = "resize", h = "drag", u = "up", p = "panedown", f = "DOMMouseScroll", g = "down", v = "wheel", i = "keydown", j = "keyup", t = "touchmove", d = "Microsoft Internet Explorer" === b.navigator.appName && /msie 7./i.test(b.navigator.appVersion) && b.ActiveXObject, e = null, B = b.requestAnimationFrame, w = b.cancelAnimationFrame, D = c.createElement("div").style, F = function () {
        var a, b, c, d, e, f;
        for (d = ["t", "webkitT", "MozT", "msT", "OT"], a = e = 0, f = d.length; f > e; a = ++e)if (c = d[a], b = d[a] + "ransform", b in D)return d[a].substr(0, d[a].length - 1);
        return !1
    }(), E = function (a) {
        return F === !1 ? !1 : "" === F ? a : F + a.charAt(0).toUpperCase() + a.substr(1)
    }, C = E("transform"), z = C !== !1, y = function () {
        var a, b, d;
        return a = c.createElement("div"), b = a.style, b.position = "absolute", b.width = "100px", b.height = "100px", b.overflow = r, b.top = "-9999px", c.body.appendChild(a), d = a.offsetWidth - a.clientWidth, c.body.removeChild(a), d
    }, A = function () {
        var a, c, d;
        return c = b.navigator.userAgent, (a = /(?=.+Mac OS X)(?=.+Firefox)/.test(c)) ? (d = /Firefox\/\d{2}\./.exec(c), d && (d = d[0].replace(/\D+/g, "")), a && +d > 23) : !1
    }, o = function () {
        function i(d, f) {
            this.el = d, this.options = f, e || (e = y()), this.$el = a(this.el), this.doc = a(this.options.documentContext || c), this.win = a(this.options.windowContext || b), this.$content = this.$el.children("." + f.contentClass), this.$content.attr("tabindex", this.options.tabIndex || 0), this.content = this.$content[0], this.previousPosition = 0, this.options.iOSNativeScrolling && null != this.el.style.WebkitOverflowScrolling ? this.nativeScrolling() : this.generate(), this.createEvents(), this.addEvents(), this.reset()
        }

        return i.prototype.preventScrolling = function (a, b) {
            if (this.isActive)if (a.type === f)(b === g && a.originalEvent.detail > 0 || b === u && a.originalEvent.detail < 0) && a.preventDefault(); else if (a.type === n) {
                if (!a.originalEvent || !a.originalEvent.wheelDelta)return;
                (b === g && a.originalEvent.wheelDelta < 0 || b === u && a.originalEvent.wheelDelta > 0) && a.preventDefault()
            }
        }, i.prototype.nativeScrolling = function () {
            this.$content.css({WebkitOverflowScrolling: "touch"}), this.iOSNativeScrolling = !0, this.isActive = !0
        }, i.prototype.updateScrollValues = function () {
            var a, b;
            a = this.content, this.maxScrollTop = a.scrollHeight - a.clientHeight, this.prevScrollTop = this.contentScrollTop || 0, this.contentScrollTop = a.scrollTop, b = this.contentScrollTop > this.previousPosition ? "down" : this.contentScrollTop < this.previousPosition ? "up" : "same", this.previousPosition = this.contentScrollTop, "same" !== b && this.$el.trigger("update", {
                position: this.contentScrollTop,
                maximum: this.maxScrollTop,
                direction: b
            }), this.iOSNativeScrolling || (this.maxSliderTop = this.paneHeight - this.sliderHeight, this.sliderTop = 0 === this.maxScrollTop ? 0 : this.contentScrollTop * this.maxSliderTop / this.maxScrollTop)
        }, i.prototype.setOnScrollStyles = function () {
            var a;
            z ? (a = {}, a[C] = "translate(0, " + this.sliderTop + "px)") : a = {top: this.sliderTop}, B ? this.scrollRAF || (this.scrollRAF = B(function (b) {
                return function () {
                    b.scrollRAF = null, b.slider.css(a)
                }
            }(this))) : this.slider.css(a)
        }, i.prototype.createEvents = function () {
            this.events = {
                down: function (a) {
                    return function (b) {
                        return a.isBeingDragged = !0, a.offsetY = b.pageY - a.slider.offset().top, a.pane.addClass("active"), a.doc.bind(l, a.events[h]).bind(m, a.events[u]), !1
                    }
                }(this), drag: function (a) {
                    return function (b) {
                        return a.sliderY = b.pageY - a.$el.offset().top - a.offsetY, a.scroll(), a.contentScrollTop >= a.maxScrollTop && a.prevScrollTop !== a.maxScrollTop ? a.$el.trigger("scrollend") : 0 === a.contentScrollTop && 0 !== a.prevScrollTop && a.$el.trigger("scrolltop"), !1
                    }
                }(this), up: function (a) {
                    return function () {
                        return a.isBeingDragged = !1, a.pane.removeClass("active"), a.doc.unbind(l, a.events[h]).unbind(m, a.events[u]), !1
                    }
                }(this), resize: function (a) {
                    return function () {
                        a.reset()
                    }
                }(this), panedown: function (a) {
                    return function (b) {
                        return a.sliderY = (b.offsetY || b.originalEvent.layerY) - .5 * a.sliderHeight, a.scroll(), a.events.down(b), !1
                    }
                }(this), scroll: function (a) {
                    return function (b) {
                        a.updateScrollValues(), a.isBeingDragged || (a.iOSNativeScrolling || (a.sliderY = a.sliderTop, a.setOnScrollStyles()), null != b && (a.contentScrollTop >= a.maxScrollTop ? (a.options.preventPageScrolling && a.preventScrolling(b, g), a.prevScrollTop !== a.maxScrollTop && a.$el.trigger("scrollend")) : 0 === a.contentScrollTop && (a.options.preventPageScrolling && a.preventScrolling(b, u), 0 !== a.prevScrollTop && a.$el.trigger("scrolltop"))))
                    }
                }(this), wheel: function (a) {
                    return function (b) {
                        var c;
                        if (null != b)return c = b.delta || b.wheelDelta || b.originalEvent && b.originalEvent.wheelDelta || -b.detail || b.originalEvent && -b.originalEvent.detail, c && (a.sliderY += -c / 3), a.scroll(), !1
                    }
                }(this)
            }
        }, i.prototype.addEvents = function () {
            var a;
            this.removeEvents(), a = this.events, this.options.disableResize || this.win.bind(q, a[q]), this.iOSNativeScrolling || (this.slider.bind(k, a[g]), this.pane.bind(k, a[p]).bind("" + n + " " + f, a[v])), this.$content.bind("" + r + " " + n + " " + f + " " + t, a[r])
        }, i.prototype.removeEvents = function () {
            var a;
            a = this.events, this.win.unbind(q, a[q]), this.iOSNativeScrolling || (this.slider.unbind(), this.pane.unbind()), this.$content.unbind("" + r + " " + n + " " + f + " " + t, a[r])
        }, i.prototype.generate = function () {
            var a, c, d, f, g, h;
            return f = this.options, g = f.paneClass, h = f.sliderClass, a = f.contentClass, this.$el.find("." + g).length || this.$el.find("." + h).length || this.$el.append('<div class="' + g + '"><div class="' + h + '" /></div>'), this.pane = this.$el.children("." + g), this.slider = this.pane.find("." + h), 0 === e && A() ? (d = b.getComputedStyle(this.content, null).getPropertyValue("padding-right").replace(/\D+/g, ""), c = {
                right: -14,
                paddingRight: +d + 14
            }) : e && (c = {right: -e}, this.$el.addClass("has-scrollbar")), null != c && this.$content.css(c), this
        }, i.prototype.restore = function () {
            this.stopped = !1, this.iOSNativeScrolling || this.pane.show(), this.addEvents()
        }, i.prototype.reset = function () {
            var a, b, c, f, g, h, i, j, k, l, m, n;
            return this.iOSNativeScrolling ? void(this.contentHeight = this.content.scrollHeight) : (this.$el.find("." + this.options.paneClass).length || this.generate().stop(), this.stopped && this.restore(), a = this.content, f = a.style, g = f.overflowY, d && this.$content.css({height: this.$content.height()}), b = a.scrollHeight + e, l = parseInt(this.$el.css("max-height"), 10), l > 0 && (this.$el.height(""), this.$el.height(a.scrollHeight > l ? l : a.scrollHeight)), i = this.pane.outerHeight(!1), k = parseInt(this.pane.css("top"), 10), h = parseInt(this.pane.css("bottom"), 10), j = i + k + h, n = Math.round(j / b * j), n < this.options.sliderMinHeight ? n = this.options.sliderMinHeight : null != this.options.sliderMaxHeight && n > this.options.sliderMaxHeight && (n = this.options.sliderMaxHeight), g === r && f.overflowX !== r && (n += e), this.maxSliderTop = j - n, this.contentHeight = b, this.paneHeight = i, this.paneOuterHeight = j, this.sliderHeight = n, this.slider.height(n), this.events.scroll(), this.pane.show(), this.isActive = !0, a.scrollHeight === a.clientHeight || this.pane.outerHeight(!0) >= a.scrollHeight && g !== r ? (this.pane.hide(), this.isActive = !1) : this.el.clientHeight === a.scrollHeight && g === r ? this.slider.hide() : this.slider.show(), this.pane.css({
                opacity: this.options.alwaysVisible ? 1 : "",
                visibility: this.options.alwaysVisible ? "visible" : ""
            }), c = this.$content.css("position"), ("static" === c || "relative" === c) && (m = parseInt(this.$content.css("right"), 10), m && this.$content.css({
                right: "",
                marginRight: m
            })), this)
        }, i.prototype.scroll = function () {
            return this.isActive ? (this.sliderY = Math.max(0, this.sliderY), this.sliderY = Math.min(this.maxSliderTop, this.sliderY), this.$content.scrollTop((this.paneHeight - this.contentHeight + e) * this.sliderY / this.maxSliderTop * -1), this.iOSNativeScrolling || (this.updateScrollValues(), this.setOnScrollStyles()), this) : void 0
        }, i.prototype.scrollBottom = function (a) {
            return this.isActive ? (this.$content.scrollTop(this.contentHeight - this.$content.height() - a).trigger(n), this.stop().restore(), this) : void 0
        }, i.prototype.scrollTop = function (a) {
            return this.isActive ? (this.$content.scrollTop(+a).trigger(n), this.stop().restore(), this) : void 0
        }, i.prototype.scrollTo = function (a) {
            return this.isActive ? (this.scrollTop(this.$el.find(a).get(0).offsetTop), this) : void 0
        }, i.prototype.stop = function () {
            return w && this.scrollRAF && (w(this.scrollRAF), this.scrollRAF = null), this.stopped = !0, this.removeEvents(), this.iOSNativeScrolling || this.pane.hide(), this
        }, i.prototype.destroy = function () {
            return this.stopped || this.stop(), !this.iOSNativeScrolling && this.pane.length && this.pane.remove(), d && this.$content.height(""), this.$content.removeAttr("tabindex"), this.$el.hasClass("has-scrollbar") && (this.$el.removeClass("has-scrollbar"), this.$content.css({right: ""})), this
        }, i.prototype.flash = function () {
            return !this.iOSNativeScrolling && this.isActive ? (this.reset(), this.pane.addClass("flashed"), setTimeout(function (a) {
                return function () {
                    a.pane.removeClass("flashed")
                }
            }(this), this.options.flashDelay), this) : void 0
        }, i
    }(), a.fn.nanoScroller = function (b) {
        return this.each(function () {
            var c, d;
            if ((d = this.nanoscroller) || (c = a.extend({}, x, b), this.nanoscroller = d = new o(this, c)), b && "object" == typeof b) {
                if (a.extend(d.options, b), null != b.scrollBottom)return d.scrollBottom(b.scrollBottom);
                if (null != b.scrollTop)return d.scrollTop(b.scrollTop);
                if (b.scrollTo)return d.scrollTo(b.scrollTo);
                if ("bottom" === b.scroll)return d.scrollBottom(0);
                if ("top" === b.scroll)return d.scrollTop(0);
                if (b.scroll && b.scroll instanceof a)return d.scrollTo(b.scroll);
                if (b.stop)return d.stop();
                if (b.destroy)return d.destroy();
                if (b.flash)return d.flash()
            }
            return d.reset()
        })
    }, a.fn.nanoScroller.Constructor = o
}(jQuery, window, document);

// EventEmitter.js
(function () {
    "use strict";
    function t() {
    }

    function i(t, n) {
        for (var e = t.length; e--;)if (t[e].listener === n)return e;
        return -1
    }

    function n(e) {
        return function () {
            return this[e].apply(this, arguments)
        }
    }

    var e = t.prototype, r = this, s = r.EventEmitter;
    e.getListeners = function (n) {
        var r, e, t = this._getEvents();
        if (n instanceof RegExp) {
            r = {};
            for (e in t)t.hasOwnProperty(e) && n.test(e) && (r[e] = t[e])
        } else r = t[n] || (t[n] = []);
        return r
    }, e.flattenListeners = function (t) {
        var e, n = [];
        for (e = 0; e < t.length; e += 1)n.push(t[e].listener);
        return n
    }, e.getListenersAsObject = function (n) {
        var e, t = this.getListeners(n);
        return t instanceof Array && (e = {}, e[n] = t), e || t
    }, e.addListener = function (r, e) {
        var t, n = this.getListenersAsObject(r), s = "object" == typeof e;
        for (t in n)n.hasOwnProperty(t) && -1 === i(n[t], e) && n[t].push(s ? e : {listener: e, once: !1});
        return this
    }, e.on = n("addListener"), e.addOnceListener = function (e, t) {
        return this.addListener(e, {listener: t, once: !0})
    }, e.once = n("addOnceListener"), e.defineEvent = function (e) {
        return this.getListeners(e), this
    }, e.defineEvents = function (t) {
        for (var e = 0; e < t.length; e += 1)this.defineEvent(t[e]);
        return this
    }, e.removeListener = function (r, s) {
        var n, e, t = this.getListenersAsObject(r);
        for (e in t)t.hasOwnProperty(e) && (n = i(t[e], s), -1 !== n && t[e].splice(n, 1));
        return this
    }, e.off = n("removeListener"), e.addListeners = function (e, t) {
        return this.manipulateListeners(!1, e, t)
    }, e.removeListeners = function (e, t) {
        return this.manipulateListeners(!0, e, t)
    }, e.manipulateListeners = function (r, t, i) {
        var e, n, s = r ? this.removeListener : this.addListener, o = r ? this.removeListeners : this.addListeners;
        if ("object" != typeof t || t instanceof RegExp)for (e = i.length; e--;)s.call(this, t, i[e]); else for (e in t)t.hasOwnProperty(e) && (n = t[e]) && ("function" == typeof n ? s.call(this, e, n) : o.call(this, e, n));
        return this
    }, e.removeEvent = function (e) {
        var t, r = typeof e, n = this._getEvents();
        if ("string" === r)delete n[e]; else if (e instanceof RegExp)for (t in n)n.hasOwnProperty(t) && e.test(t) && delete n[t]; else delete this._events;
        return this
    }, e.removeAllListeners = n("removeEvent"), e.emitEvent = function (r, o) {
        var e, i, t, s, n = this.getListenersAsObject(r);
        for (t in n)if (n.hasOwnProperty(t))for (i = n[t].length; i--;)e = n[t][i], e.once === !0 && this.removeListener(r, e.listener), s = e.listener.apply(this, o || []), s === this._getOnceReturnValue() && this.removeListener(r, e.listener);
        return this
    }, e.trigger = n("emitEvent"), e.emit = function (e) {
        var t = Array.prototype.slice.call(arguments, 1);
        return this.emitEvent(e, t)
    }, e.setOnceReturnValue = function (e) {
        return this._onceReturnValue = e, this
    }, e._getOnceReturnValue = function () {
        return this.hasOwnProperty("_onceReturnValue") ? this._onceReturnValue : !0
    }, e._getEvents = function () {
        return this._events || (this._events = {})
    }, t.noConflict = function () {
        return r.EventEmitter = s, t
    }, "function" == typeof define && define.amd ? define(function () {
        return t
    }) : "object" == typeof module && module.exports ? module.exports = t : r.EventEmitter = t
}).call(this);

window.Green = function (OldGreen) {
    var Green = {
        noConflict: noConflict
    };
    return Green;

    function noConflict() {
        if (window.Green === Green) {
            window.Green = OldGreen;
        }
        return Green;
    }
}(window.Green);

Green.log = function(){
   if(console==null){
       console = {log:function(){
          //nothing to do.
       }};
   }
   var log = function(msg){
       console.log && console.log(msg);
   };
   return log;
}();

// 视图类 完善中...
Green._View = function (window, document, Green, EventEmitter) {
    function View() {
        this._child = null;
        this._hasMultipleChildren = false;
    }

    View.prototype.add = function (child) {
        if (!(child instanceof View)) {
            return false;
        }
        var childNode = child;

        if (this._child instanceof Array) this._child.push(childNode);
        else if (this._child) {
            this._child = [this._child, childNode];
            this._hasMultipleChildren = true;
        }
        else this._child = childNode;

        return childNode;
    };

    View.prototype.commit = function () {
        var result = null;
        if (this._hasMultipleChildren) {
            var children = this._child;
            for (var i = 0; i < children.length; i++) {
                result[i] = children[i].commit();
            }
        }
        else if (this._child) result = this._child.commit();

        return result;
    };

    // View.prototype.create = function(class) {

    // };

    return View;

}(window, document, Green, EventEmitter);

Green._UIEventHandler = function(EventEmitter){
    return new EventEmitter();
}(EventEmitter);

Green._SendEventHandler = function(EventEmitter){
    return new EventEmitter();
}(EventEmitter);

Green._ReceiveEventHandler = function(EventEmitter){
    return new EventEmitter();
}(EventEmitter);

// 表情组件
Green._Expression = function (window, document, Green) {
    function Hashtable() {
        this._hash = new Object();
        this.put = function (key, value) {
            if (typeof (key) != "undefined") {
                if (this.containsKey(key) == false) {
                    this._hash[key] = typeof (value) == "undefined" ? null : value;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        this.remove = function (key) {
            delete this._hash[key];
        }
        this.size = function () {
            var i = 0;
            for (var k in this._hash) {
                i++;
            }
            return i;
        }
        this.get = function (key) {
            return this._hash[key];
        }
        this.containsKey = function (key) {
            return typeof (this._hash[key]) != "undefined";
        }
        this.clear = function () {
            for (var k in this._hash) {
                delete this._hash[k];
            }
        }
    }

    var emotions = new Array();
    var categorys = new Array();// 分组
    var uSinaEmotionsHt = new Hashtable();
    
    function init(url){
        var emotionCallback = function(res){
            var data = res.data;
            for (var i in data) {
                if (data[i].category == '') {
                    data[i].category = '默认';
                }
                if (emotions[data[i].category] == undefined) {
                    emotions[data[i].category] = new Array();
                    categorys.push(data[i].category);
                }
                emotions[data[i].category].push({
                    name: data[i].phrase,
                    icon: data[i].icon
                });
                uSinaEmotionsHt.put(data[i].phrase, data[i].icon);
            }
        };
        $.ajax({
            url:url,
            dataType:'jsonp',
            jsonpCallback:'emotionCallback',
            success:emotionCallback
        });
    }

    //替换
    function AnalyticEmotion(s) {
        if (typeof (s) != "undefined") {
            var sArr = s.match(/\[.*?\]/g);
            if (!sArr) {
                return s;
            }
            for (var i = 0; i < sArr.length; i++) {
                if (uSinaEmotionsHt.containsKey(sArr[i])) {
                    var reStr = "<img src=\"" + Green.resUrl+uSinaEmotionsHt.get(sArr[i]) + "\" height=\"22\" width=\"22\" />";
                    s = s.replace(sArr[i], reStr);
                }
            }
        }
        return s;
    }

    $.fn.SinaEmotion = function (target) {
        var cat_current;
        var cat_page;
        $(this).click(function (event) {
            event.stopPropagation();

            var width = target.parent().innerWidth();
            var eBottom = target.parent().outerHeight();
            var eLeft = target.parent().offset().left;

            if ($('#emotions .categorys')[0]) {
                $('#emotions').css({bottom: eBottom, left: eLeft});
                $('#emotions').toggle();
                return;
            }
            $('body').append('<div id="emotions"></div>');
            $('#emotions').css({bottom: eBottom, left: eLeft, width: width});
            $('#emotions').html('<div>正在加载，请稍候...</div>');
            $('#emotions').click(function (event) {
                event.stopPropagation();
            });

            $('#emotions').html('<div class="categorys"></div><div class="container"></div>');
            $('#emotions #prev').click(function () {
                showCategorys(cat_page - 1);
            });
            $('#emotions #next').click(function () {
                showCategorys(cat_page + 1);
            });
            //showCategorys();
            showEmotions();

        });
        $('body').click(function () {
            $('#emotions').remove();
        });
        $.fn.insertText = function (text) {
            this.each(function () {
                if (this.tagName !== 'INPUT' && this.tagName !== 'TEXTAREA') {
                    return;
                }
                if (document.selection) {
                    this.focus();
                    var cr = document.selection.createRange();
                    cr.text = text;
                    cr.collapse();
                    cr.select();
                } else if (this.selectionStart || this.selectionStart == '0') {
                    var
                        start = this.selectionStart,
                        end = this.selectionEnd;
                    this.value = this.value.substring(0, start) + text + this.value.substring(end, this.value.length);
                    this.selectionStart = this.selectionEnd = start + text.length;
                } else {
                    this.value += text;
                }
            });
            return this;
        };
        function showCategorys() {
            var page = arguments[0] ? arguments[0] : 0;
            if (page < 0 || page >= categorys.length / 5) {
                return;
            }
            $('#emotions .categorys').html('');
            cat_page = page;
            for (var i = page * 5; i < (page + 1) * 5 && i < categorys.length; ++i) {
                $('#emotions .categorys').append($('<a href="javascript:void(0);">' + categorys[i] + '</a>'));
            }
            $('#emotions .categorys a').click(function () {
                showEmotions($(this).text());
            });
            $('#emotions .categorys a').each(function () {
                if ($(this).text() == cat_current) {
                    $(this).addClass('current');
                }
            });
        }

        function showEmotions() {
            var category = arguments[0] ? arguments[0] : '默认';
            var page = arguments[1] ? arguments[1] - 1 : 0;
            $('#emotions .container').html('');
            $('#emotions .page').html('');
            cat_current = category;
            for (var i = page * 72; i < (page + 1) * 72 && i < emotions[category].length; ++i) {
                $('#emotions .container').append($('<a href="javascript:void(0);" title="' + emotions[category][i].name + '"><img src="' + Green.resUrl+emotions[category][i].icon + '" alt="' + emotions[category][i].name + '" width="22" height="22" /></a>'));
            }
            $('#emotions .container a').click(function () {
                target.insertText($(this).attr('title'));
                target.focus();
                $('#emotions').remove();
            });
            for (var i = 1; i < emotions[category].length / 72 + 1; ++i) {
                $('#emotions .page').append($('<a href="javascript:void(0);"' + (i == page + 1 ? ' class="current"' : '') + '>' + i + '</a>'));
            }
            $('#emotions .page a').click(function () {
                showEmotions(category, $(this).text());
            });
            $('#emotions .categorys a.current').removeClass('current');
            $('#emotions .categorys a').each(function () {
                if ($(this).text() == category) {
                    $(this).addClass('current');
                }
            });
        }
    };

    return {
        init:init,
        analytic: AnalyticEmotion
    };

}(window, document, Green);

Green._Utils = function (window, document, Green) {
    function isDifferentDay(prev, next) {
        return (
            (next.getDate() != prev.getDate()) ||
            (next.getFullYear() != prev.getFullYear()) ||
            (next.getMonth() != prev.getMonth())
        );
    };

    function tranTime(date) {
        return this.isDifferentDay(date, new Date()) ?
            ( parseInt(date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.toTimeString().substr(0, 5) ) :
            date.toTimeString().substr(0, 5);
    };

    function template(tpl, data) {
        var re = /<%([^%>]+)?%>/g,
            reExp = /(^( )?(if|for|else|switch|case|break|{|}))(.*)?/g,
            code = 'var r=[];\n',
            cursor = 0;

        var add = function (line, js) {
            js ? (code += line.match(reExp) ? line + '\n' : 'r.push(' + line + ');\n') :
                (code += line != '' ? 'r.push("' + line.replace(/"/g, '\\"') + '");\n' : '');
            return add;
        };

        while (match = re.exec(html)) {
            add(html.slice(cursor, match.index))(match[1], true);
            cursor = match.index + match[0].length;
        }

        add(html.substr(cursor, html.length - cursor));

        code += 'return r.join("");';

        return new Function(code.replace(/[\r\t\n]/g, '')).apply(options);
    };

    function freeTextarea($dom, maxHeight) {
        var ta = $dom,
            maxHeight = maxHeight || 70,
            css = (function () {
                var css = {}, i = 8,
                    z = 'width fontSize fontFamily lineHeight wordWrap wordBreak whiteSpace letterSpacing'.split(' ');

                while (i--) css[z[i]] = ta.css(z[i]);
                return $.extend(css, {position: 'absolute', left: -9999, top: 0});
            })(),

            _ta = ta.clone().css(css).attr({id: '', name: '', tabIndex: -1}),
            stCur, valCur, defHeight = ta.height(),
            both = $([ta[0], _ta[0]]),

            autoHeight = function () {
                valCur = ta.val();
                _ta.val(valCur).height(1).scrollTop(9999);

                stCur = Math.min(Math.max(defHeight, _ta.scrollTop()), maxHeight);

                both.height(stCur);
            };
    };

    function log(msg) {
        if (!Green.debug) return;

        $('.log').append('<p>' + this.tranTime(new Date()) + '：' + msg + '</p>');
    };
    var entityMap = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': '&quot;',
        "'": '&#39;',
        "/": '&#x2F;'
      };
    var escapeHtml = function(string) {
      return String(string).replace(/[&<>"'\/]/g, function (s) {
        return entityMap[s];
      });
    };

    return {
        isDifferentDay: isDifferentDay,
        tranTime: tranTime,
        template: template,
        freeTextarea: freeTextarea,
        log: log,
        escapeHtml:escapeHtml
    };

}(window, document, Green);


// 状态提示组件
Green._StatusView = function (window, document, Green, Utils) {
    var StatusView = {};

    StatusView.commit = function () {
        var render = '<div class="status-view"></div>';

        this.dom = $(render);

        return this.dom;
    };

    StatusView.msg = function (log) {
        Green.log && Green.log(log);
        this.dom.html(log).show();
    };

    StatusView.hide = function () {
        this.dom.hide();
    };

    return StatusView;

}(window, document, Green, EventEmitter);


// 联系人组件
Green._RosterView = function (window, document, Green, Utils, UIEventHandler) {
    function RosterView(roster) {
        this.roster = roster;
    }

    RosterView.prototype.commit = function render() {
        var render = '<div class="roster cf" peer="' + this.roster.id + '">' +
            '<span class="unread-dot" style="display:none;"></span>' +
            '<span class="unread-dots" style="display:none"></span>' +
            '<span class="chat-status ' + this.roster.onlineStatus + '"></span>' +
            '<div class="avatar-wrap">' +
            '<img class="avatar" src="'+Green.resUrl + this.roster.image + '">' +
            '</div>' +
            '<div class="info">' +
            '<div class="nick-name">' +
            '<div class="name" style="">' + this.roster.name + '</div>' +
            '</div>' +
            '</div>' +
            '</div>';

        this.dom = $(render);

        this.dom.click($.proxy(function () {
            UIEventHandler.trigger('rosterClick', [this]);
        }, this));

        return this.dom;
    };

    RosterView.prototype.updateUnread = function (num) {

        var unreadDom = this.dom.find(".unread-dot");

        this.roster.unreadCount = num;

        if (num == 0) {
            unreadDom.hide();
        }
        else {
            unreadDom.html(num).show();
        }
    };

    RosterView.prototype.updateOnlineStatus = function (status) {
        this.roster.onlineStatus = status;

        this.dom.find(".chat-status").removeClass().addClass('chat-status ' + status);
    };

    return RosterView;

}(window, document, Green, Green._Utils, Green._UIEventHandler);

//搜寻列表组件
Green._SearchView = function (window, document, Green, Utils, UIEventHandler,SendEventHandler) {
    function SearchView(rosterListView) {
        this.rosterListView = rosterListView;
    }
    SearchView.prototype.commit = function () {
        var render = '<div class="roster-search"><span class="chaticon-search"></span><input type="text" id="search"></div>';
        this.dom = $(render);
        _init.call(this);
        return this.dom;
    };
    SearchView.prototype.refreshUserListView = function(){
        var rosterListView = this.rosterListView;
        var rosterViews = rosterListView.rosterViews;
        var searchName = this.searchDom.val();
        this.searchName = searchName;
        if(searchName!=null){
            $.each(rosterViews,function (id,rosterView) {
                var roster = rosterView.roster;
                rosterView.dom.hide();
                if ((roster.name.indexOf(searchName) > -1)) {
                   rosterView.dom.show();
                }
            });
        }
    };
    function _init(){
        var rosterListView = this.rosterListView;
        this.searchDom = this.dom.find('#search');
        this.searchDom.on('input', function () {
            var self = this;
            SendEventHandler.trigger('queryUserList', [rosterListView,'refresh',$(self).val()]);
        });
    }
    return SearchView;
}(window, document, Green, Green._Utils, Green._UIEventHandler,Green._SendEventHandler);

// 联系人列表组件
Green._RosterListView = function (window, document, Green, Utils, UIEventHandler, SendEventHandler, RosterView,SearchView) {
    var RosterListView = {};
    var Status = ['offline','online'];
    
    RosterListView.commit = function (rosters,rosterIds) {
        var render = '<div class="contacts">' +
            '<span class="closechat chaticon-remove"></span><div class="title"><i class="chaticon-comment-alt">&nbsp;&nbsp;&nbsp;</i><span style="font-weight: bold; display:none">查看新消息&nbsp;(<b id="totalupdateUnreadele">0</b>)</span><span id="noUnreadTitle" style="font-weight: bold;">点击聊天</span></div>' +
            '<div class="roster-list"><div class="nano"><div class="active nano-content"></div></div></div>' +
            '<div class="loading cf"><img src="'+Green.resUrl+'/images/chat/image/loading-cir.gif"></div>'+
            '<div class="bottom cf"><div class="online-status"><div class="me-status"><span class="chat-status online"></span>' +
            '<div class="status-text">在线</div></div></div><div class="toggle-btn">' +
            '<div id="changestatus" class="dropdown clearfix">' +
            '<button class="btn btn-default dropdown-toggle chat-status online"type="button"id="dropdownMenu3"data-toggle="dropdown"aria-haspopup="true"aria-expanded="true"></button>' +
            '<ul class="dropdown-menu"aria-labelledby="dropdownMenu3">' +
            '<li class="thisisonline" data-val="1"><span class="chat-status online"></span>在线</li>' +
            '<li class="thisisoffline" data-val="0"><span class="chat-status offline"></span>隐身</li>' +
            '</ul></div>' +
            '<div class="backward" title="展开">&nbsp;<i class="chaticon-step-backward"></i></div><div class="forward" title="收缩"><i class=" chaticon-chevron-down"></i>&nbsp;<b>收缩</b>' +
            '</div></div></div>' +
            '</div>';

        this.status = Status[1];
        this.unreadCount = 0;
        this.dom = $(render);
        this.rosterViews = {};
        this.topRosterViews = {};
        this.canNextPage = true;
        this.nanoContentDom = this.dom.find('.active');
        
        this.initSearchView();
        
        this.initTopUserListView();
        
        this.refresh(rosters,rosterIds);
        
        this.refreshTop({},[]);

        // 精简模式 详细模式转换
        this.dom.find('.title, .toggle-btn').click(function () {
            UIEventHandler.trigger('rosterListViewToggle', [!RosterListView.dom.hasClass('contacts-minus')]);
        });
        
        this.dom.find('.closechat').click(function (){
            UIEventHandler.trigger('rosterListViewToggle', [!RosterListView.dom.hasClass('contacts-minus')]);
            UIEventHandler.trigger('chatboxRemove');
            Green.hide();
            return false;
        });
        
        this.dom.find('#changestatus').click(function (){
            $(this).toggleClass('open');
            return false;
        });
        
        this.dom.find('#changestatus li').click(function () {
            var statusIndex = $(this).data('val');
            if(status!=Status[statusIndex]){
                RosterListView.changeStatus(Status[statusIndex]);
                SendEventHandler.trigger('changeUserStatus',[statusIndex]);
            }
        });
        
        this.dom.find('.nano').bind("scrollend", function() {
            function queryNextPage(){
                RosterListView.from = RosterListView.from+RosterListView.limit;
                RosterListView.to = RosterListView.to+RosterListView.limit;
                SendEventHandler.trigger('queryUserList', [RosterListView,'append',RosterListView.searchView.searchDom.val()]);
            }
            function canQueryNext(){
                RosterListView.canNextPage = true;
            }
            if(RosterListView.canNextPage && RosterListView.searchView.searchName.length==0){
                RosterListView.dom.find(".loading").show();
                RosterListView.canNextPage = false;
                setTimeout(canQueryNext,500);
                queryNextPage();
            }
        });

        return this.dom;
    };
    
    RosterListView.initUI = function () {
        var titHeight = 40;
        var botHeight = 30;
        var offset = 62;
        var topHeight = 124;

        var listHeight = $(window).height() - offset - topHeight;
        var innerHeight = $(window).height() - offset - titHeight - botHeight - topHeight;

        this.height = listHeight;
        this.innerHeight = innerHeight;
        this.dom.find(".loading").hide();
    };
    
    RosterListView.initUserPage = function(){
        this.limit = 50;
        this.from = 1;
        this.to = this.from + this.limit;
    };

    RosterListView.get = function (id) {
        var roster = this.topRosterViews[id];
        if(roster==null)roster = this.rosterViews[id];
        return roster;
    };

    RosterListView.toggle = function (status) {
        if (status) {
            this.dom.addClass('contacts-minus');
        }
        else {
            this.dom.removeClass('contacts-minus');
        }
    };
    
    RosterListView.changeStatus = function (status){
        var dropdownMenu3 = this.dom.find('#dropdownMenu3');
        dropdownMenu3.removeClass(Status.join(' '));
        dropdownMenu3.addClass(status);
    };

    RosterListView.prepend = function (id) {
        this.get(id).dom.prependTo(this.dom.find('.active'));
    };

    RosterListView.hide = function () {
        this.dom.hide();
    };

    RosterListView.show = function () {
        this.dom.show();
    };
    
    RosterListView.refresh = function(rosters,rosterIds){
        this.nanoContentDom.empty();
        delete this.rosterViews;
        this.rosterViews = {};
        for(var i=0;i<rosterIds.length;i++){
            var id = rosterIds[i];
            var roster = rosters[id];
            RosterListView.addRosterView(id,roster);
        }
        var domNano = RosterListView.dom.find('.nano');
        domNano.css('height',  this.innerHeight + 'px');
        domNano.nanoScroller();
    };
    RosterListView.refreshTop = function(rosters,rosterIds){
        this.topContentDom.empty();
        delete this.topRosterViews;
        this.topRosterViews = {};
        for(var i=0;i<rosterIds.length;i++){
            var id = rosterIds[i];
            var roster = rosters[id];
            RosterListView.addTopRosterView(id,roster);
        }
    };
    
    RosterListView.addRosterView = function(id,roster){
        var rosterView = new RosterView(roster);

        var unreadCount = roster.unreadCount;

        RosterListView.rosterViews[id] = rosterView;

        this.nanoContentDom.append(rosterView.commit());

        if(unreadCount!=null){
            rosterView.updateUnread(unreadCount);
        }
    };
    
    RosterListView.addTopRosterView = function(id,roster){
        var rosterView = new RosterView(roster);

        var unreadCount = roster.unreadCount;

        RosterListView.topRosterViews[id] = rosterView;

        this.topContentDom.append(rosterView.commit());

        if(unreadCount!=null){
            rosterView.updateUnread(unreadCount);
        }
    };
    
    RosterListView.initSearchView = function(){
        var serachView = new SearchView(this);
        this.searchView = serachView;
        this.dom.find('.roster-list').prepend(serachView.commit());
    };
    
    RosterListView.initTopUserListView = function(){
        var view = $('<div class="roster-top"></div>');
        this.dom.find('.roster-search').after(view);
        this.topContentDom = this.dom.find('.roster-top');
    };
    
    RosterListView.addUnreadCount = function(count){
        this.unreadCount += count;
        if(this.unreadCount<0){
            this.unreadCount = 0;
        }
        if(this.unreadCount==0){
            this.dom.find('#totalupdateUnreadele').removeClass('myunread').text(this.unreadCount);
            this.dom.find('#totalupdateUnreadele').parent().hide();
            this.dom.find('#noUnreadTitle').show();
        }else{
            this.dom.find('#totalupdateUnreadele').addClass('myunread').text(this.unreadCount);
            this.dom.find('#totalupdateUnreadele').parent().show();
            this.dom.find('#noUnreadTitle').hide();
        }
    };

    return RosterListView;

}(window, document, Green, Green._Utils, Green._UIEventHandler , Green._SendEventHandler, Green._RosterView,Green._SearchView);

// 会话窗口组件
Green._ConverseView = function (window, document, Green, Utils, UIEventHandler, SendEventHandler,Expression) {
    function ConverseView(roster) {
        this.roster = roster;
    }

    ConverseView.prototype.commit = function () {
        var render = '<div class="nano" peer="' + this.roster.id + '">' +
            '<div class="nano-content messages">' +
            '<div class="no-more-history"><i class="chaticon-ban-circle"></i><b>无更多历史消息</b></div>' +
            '<div class="loadmore"><div class="linit"><i class="chaticon-time"></i><b>加载更多历史消息</b></div><div class="lloading"><img src="'+Green.resUrl+'/images/chat/image/loading-cir.gif">历史消息加载中...</div></div>' +
            '</div>' +
            '</div>';

        this.dom = $(render);

        _init.call(this);

        return this.dom;
    };

    function _init() {
        var domLoadmore = this.dom.find('.loadmore');
        domLoadmore.click($.proxy(function () {
            if (domLoadmore.hasClass('loadmore-loading')) {
                return false;
            }

            domLoadmore.addClass('loadmore-loading');

            var data = {
                convid: [Green.me.id, this.roster.id],
                timestamp: this.lastMessage.timestamp,
                id:this.lastMessage.id,
                limit: 20
            };

            SendEventHandler.trigger('getHistory', [data, this]);
        }, this));


        this.lastMessage = {};

        // 是否有未读的消息
        var unreadMessages = this.roster.unreadMessages;

        if (unreadMessages.length == 0) {
            this.lastMessage['timestamp'] = new Date().getTime();

            domLoadmore.trigger('click');// think think!!!!
        }
        else {
            this.lastMessage = unreadMessages[0];

            this.renderMessage(unreadMessages, 'receive');

            this.roster.unreadMessages = [];
        }
    };

    ConverseView.prototype.renderMessage = function (messages, mode) {
        if (!(messages instanceof Array))  messages = [messages];

        var renderStr = '';
        var date;
        var message;
        var sendStatus;
        for (var i = 0, len = messages.length; i < len; i++) {
            date = Utils.tranTime(new Date(messages[i].timestamp));
            var data = Expression.analytic(messages[i].data);
            if (messages[i].from == Green.me.id) {
                message = {
                    'pic': Green.resUrl+Green.me.image,
                    'name': Green.me.name,
                    'sign': 'me',
                    'message': data,
                    'date': date
                };
            }
            else {
                var roster = Green.rosters[messages[i].from]==null?Green.topRosters[messages[i].from]:Green.rosters[messages[i].from];
                message = {
                    'pic': Green.resUrl+roster.image,
                    'name': roster.name,
                    'sign': 'you',
                    'message': data,
                    'date': date
                };
            }

            sendStatus = '';
            if (mode == 'send') {
                sendStatus = '<div class="send-status"><img src="./image/send-loading.gif"><b>发送失败</b></div>';
            }

            renderStr += '<div class="cf chat-item ' + message.sign + '">' +
                '<div class="time">' + message.name + '：' + message.date + ' </div>' +
                '<div class="chat-item-content cf">' +
                '<img class="avatar" src="' + message.pic + '" title="' + message.name + '" alt="' + message.name + '">' +
                '<div class="cloud cloud-text">' +
                '<div class="cloud-pannel">' +
                '<div class="cloud-body">' +
                '<div class="cloud-content">' +
                '<pre style="white-space:pre-wrap;*white-space:pre;*word-wrap:break-word;">' + message.message + '</pre>' +
                '</div>' +
                '</div>' +
                '<div class="cloud-arrow"></div>' +
                '</div>' +
                '</div>' + sendStatus +
                '</div>' +
                '</div>';
        }

        var domMessage = $(renderStr);

        if (mode == 'history') {
            var nowPosition = this.getPosition();

            this.dom.find('.loadmore').after(domMessage);

            this.scroll();
            this.adjustPosition(nowPosition);
        }

        if (mode == 'receive' || mode == 'send') {
            this.dom.find('.messages').append(domMessage);

            this.scroll();
            this.scrollDown();
        }

        if (mode == 'notCurrent') {
            this.dom.find('.messages').append(domMessage);

            this.scroll();
        }

        return domMessage;
    };

    ConverseView.prototype.showLoadmore = function () {
        this.dom.find('.loadmore').removeClass('loadmore-loading').show().find('b').html('加载更多历史消息');
    };

    ConverseView.prototype.showHistoryFailed = function () {
        this.dom.find('.loadmore').removeClass('loadmore-loading').show().find('b').html('加载历史信息失败 点击重试');
    };

    ConverseView.prototype.showNoHistory = function () {
        this.dom.find('.loadmore').hide();

        this.dom.find('.no-more-history').show();
    };

    ConverseView.prototype.scrollDown = function () {
        // this.dom.nanoScroller({ scroll: 'bottom' });
        // 貌似视觉效果好些
        var domMessage = this.dom.find('.messages');
        domMessage.stop().animate({scrollTop: domMessage[0].scrollHeight});
    };

    ConverseView.prototype.adjustPosition = function (pre) {
        this.dom.nanoScroller({scrollTop: this.getPosition() - pre});
    };

    ConverseView.prototype.getPosition = function () {
        return this.dom.find('.messages')[0].scrollHeight;
    };

    ConverseView.prototype.scroll = function () {
        this.dom.nanoScroller({preventPageScrolling: true, alwaysVisible: true});
    };

    ConverseView.prototype.destroy = function () {
        this.dom.nanoScroller({destroy: true});
        this.dom.remove();

        delete this;
    };

    return ConverseView;

}(window, document, Green, Green._Utils, Green._UIEventHandler, Green._SendEventHandler,Green._Expression);

// 会话窗口列表管理组件
Green._ConverseListView = function (window, document, Green, Utils, UIEventHandler, ConverseView) {
    var ConverseListView = {};

    ConverseListView.commit = function () {
        var render = '<div class="convers"></div>';

        this.dom = $(render);

        this.converseListViews = {};

        return this.dom;
    };

    // 添加一个会话窗口
    ConverseListView.add = function (roster) {
        var view = this.get(roster.id);
        if (view) return view;

        var converseView = new ConverseView(roster);

        this.converseListViews[roster.id] = converseView;

        this.dom.append(converseView.commit());

        this.currentView = converseView;

        return converseView;
    };

    ConverseListView.get = function (id) {
        return this.converseListViews[id];
    };

    ConverseListView.front = function (id) {
        this.get(id).dom.css('zIndex', 100).siblings().css('zIndex', 99);
    };

    ConverseListView.show = function () {
        this.dom.show();
    };

    ConverseListView.hide = function () {
        this.dom.hide();
    };

    ConverseListView.remove = function (id) {
        this.get(id).destroy();

        delete this.converseListViews[id];
    };

    ConverseListView.removeAll = function () {
        $.each(this.converseListViews, function (k, v) {
            v.destroy();
        });

        this.converseListViews = {};

        this.currentView = null;
    };

    return ConverseListView;

}(window, document, Green, Green._Utils, Green._UIEventHandler, Green._ConverseView);


// 会话联系人组件
Green._ChatWithView = function (window, document, Green, Utils, UIEventHandler) {
    function ChatWithView(roster) {
        this.roster = roster;
    }

    ChatWithView.prototype.commit = function () {
        var render = '<div class="chat-with cf" peer="' + this.roster.id + '">' +
            '<div class="list-head-status"><span class="chat-status ' + this.roster.onlineStatus + '"></span></div>' +
            '<div class="chat-with-name">' + this.roster.name + '</div>' +
            '<a href="javascript:void(0);" class="chat-close"><i class="chaticon-remove"></i></a>' +
            '<a href="javascript:void(0);" class="msg-unread"><i class="chaticon-volume-down"></i></a>' +
            '</div>';

        this.dom = $(render);

        _init.call(this);

        return this.dom;
    };

    function _init() {
        this.dom.click($.proxy(function () {
            UIEventHandler.trigger('chatWithClick', [this]);
        }, this));

        this.dom.find('.chaticon-remove').click($.proxy(function () {
            UIEventHandler.trigger('chatWithRemove', [this]);
        }, this));
    };

    ChatWithView.prototype.updateUnread = function (num) {
        var unreadDom = this.dom.find(".msg-unread");

        this.roster.unreadCount = num;

        if (num == 0) {
            unreadDom.hide();
        }
        else {
            unreadDom.show();
        }
    };

    ChatWithView.prototype.updateOnlineStatus = function (status) {
        this.roster.onlineStatus = status;

        this.dom.find(".chat-status").removeClass().addClass('chat-status ' + status);
    };

    ChatWithView.prototype.active = function () {
        this.dom.addClass('active').siblings().removeClass('active');
    };

    ChatWithView.prototype.destroy = function () {
        this.dom.remove();

        delete this;
    };

    return ChatWithView;

}(window, document, Green, Green._Utils, Green._UIEventHandler);


// 会话联系人列表组件
Green._ChatWithListView = function (window, document, Green, Utils, ChatWithView, UIEventHandler) {
    var ChatWithListView = {};

    ChatWithListView.commit = function () {
        var render = '<div class="pannel">' +
            '<div class="c-tit chatusr-tit"><i class="chaticon-comments"></i>洽谈列表</div>' +
            '<div class="usr-list"><div class="nano"><div class="nano-content"></div></div></div>' +
            '</div>';

        this.dom = $(render);

        this.chatWithViews = [];

        return this.dom;
    };

    ChatWithListView.add = function (roster) {
        var view = this.get(roster.id);
        if (view) return view;

        var chatWithView = new ChatWithView(roster);

        this.chatWithViews.push(chatWithView);

        this.dom.find('.nano-content').append(chatWithView.commit());

        this.dom.find('.nano').nanoScroller();

        return chatWithView;
    };

    ChatWithListView.get = function (id) {
        var views = this.chatWithViews;

        for (var i = 0, len = views.length; i < len; i++) {
            if (views[i].roster.id == id) return views[i];
        }

        return false;
    };

    ChatWithListView.prepend = function (id) {
        this.get(id).dom.prependTo(this.dom.find('.nano-content'));
    };

    ChatWithListView.show = function () {
        this.dom.show();
    };

    ChatWithListView.hide = function () {
        this.dom.hide();
    };

    ChatWithListView.remove = function (chatWithView) {
        var index = $.inArray(chatWithView, this.chatWithViews);

        if (chatWithView.dom.hasClass('active')) {
            var next;

            if (index == this.chatWithViews.length - 1) {
                next = this.chatWithViews[index - 1];
            }
            else {
                next = this.chatWithViews[index + 1];
            }

            UIEventHandler.trigger('chatWithClick', [next]);
        }

        this.chatWithViews.splice(index, 1);

        chatWithView.destroy();

        this.dom.find('.nano').nanoScroller();
    };

    ChatWithListView.removeAll = function () {
        $.each(this.chatWithViews, function (k, v) {
            v.destroy();
        });

        this.chatWithViews = [];
    };

    return ChatWithListView;

}(window, document, Green, Green._Utils, Green._ChatWithView, Green._UIEventHandler);


// 聊天输入框组件
Green._InputView = function (window, document, Green, Utils, UIEventHandler, SendEventHandler, Expression) {
    var InputView = {};

    InputView.commit = function () {
        var render = '<div class="send cf">' +
            '<i class="chaticon-smile"></i>' +
            '<textarea  class="text" placeholder="按回车发送"></textarea>' +
            '<a href="javascript:void(0);" class="btn">发送</a>' +
            '</div>';

        this.dom = $(render);
        this.domBtn = this.dom.find('a');
        this.domIcon = this.dom.find('i');
        this.domText = this.dom.find('textarea');

        _init.call(this);

        return this.dom;
    };

    function _init() {
        this.dom.find('.btn').click(function () {
            _send.call(InputView);
            return false;
        });

        this.dom.find('.text').keydown(function (e) {
            if (e.which == 13 || e.which == 10) {
                _send.call(InputView);

                return false;
            }
            // else if (e.shiftKey && e.which==13 || e.which == 10) {
            //     SendEventHandler.trigger('sendMessageEnter');
            // }
        });

        this.dom.find('.chaticon-smile').SinaEmotion(this.dom.find('.text'));
    }

    InputView.init = function () {
        Utils.freeTextarea(this.dom.find('.text'));
    };

    InputView.reset = function () {
        this.dom.find('.text').eq(0).val('').focus();
    };
    
    InputView.setReply = function(flag) {
        if(!flag){
            this.domText.attr('disabled',true);
            this.domText.css('background','#ddd');
            this.domBtn.css('pointer-events','none');
            this.domBtn.css('background','#ddd');
            this.domIcon.css('pointer-events','none');
        }else{
            this.domText.attr('disabled',false);
            this.domText.css('background','');
            this.domBtn.css('pointer-events','');
            this.domBtn.css('background','');
            this.domIcon.css('pointer-events','');
        }
    };

    function _send() {
        var domInput = this.dom.find('.text'),
            text = domInput.val();

        if (text == '') {
            domInput.eq(0).focus();
            return false;
        }

        SendEventHandler.trigger('sendMessageEnter', [text]);
    }
    
    return InputView;

}(window, document, Green, Green._Utils, Green._UIEventHandler, Green._SendEventHandler, Green._Expression);


Green._ChatboxView = function (window, document, Green, Utils, UIEventHandler, ChatWithListView, ConverseListView, InputView) {
    var ChatboxView = {};

    ChatboxView.commit = function () {
        var render = '<div class="chatbox-wrap">' +
            '<div class="chatbox cf"><div class="c-tit cf"><div class="chat-with"><img src="" /><b class="chat-name"></b></div><div class="chat-opt"><i class="chaticon-minus"></i>&nbsp;<i class="chaticon-remove"></i>&nbsp;</div></div></div>' +
            '<div class="minus-pannel"><i class="chaticon-comments"></i><b class="cur-chat-name"></b></div>' +
            '</div>';

        this.dom = $(render);

        this.dom.prepend(ChatWithListView.commit());

        this.dom.find('.chatbox').append(ConverseListView.commit());

        this.dom.find('.chatbox').append(InputView.commit());

        _init.call(this);

        return this.dom;
    };

    function _init() {
        this.dom.find('.chaticon-minus').click(function () {
            ChatboxView.minifiy();
            return false;
        });

        this.dom.find('.minus-pannel').click(function () {
            UIEventHandler.trigger('chatboxMaxfiy');

            return false;
        });

        this.dom.find('.chaticon-remove').click(function () {
            ChatboxView.remove();
            return false;
        });
    };

    ChatboxView.get = function (id) {
        return this.rosterViews[id];
    };

    ChatboxView.isMinus = function () {
        return this.dom.hasClass('chatbox-minus');
    };

    ChatboxView.maxfiy = function () {
        this.dom.removeClass('chatbox-minus');

        if (this.dom.hasClass('chat-new-message')) {
            this.dom.removeClass('chat-new-message');
        }
    };

    ChatboxView.minifiy = function () {
        return this.dom.addClass('chatbox-minus');
    };

    ChatboxView.beMulti = function () {
        this.dom.addClass('multi-chatbox');
    };

    ChatboxView.cleanMulti = function () {
        this.dom.removeClass('multi-chatbox');
    };

    ChatboxView.hide = function () {
        return this.dom.hide();
    };

    ChatboxView.show = function () {
        return this.dom.show();
    };

    ChatboxView.remove = function () {
        ChatWithListView.removeAll();
        ConverseListView.removeAll();

        this.hide();
    };

    return ChatboxView;

}(window, document, Green, Green._Utils, Green._UIEventHandler, Green._ChatWithListView, Green._ConverseListView, Green._InputView);


window.ChatClient = function (OldChatClient,Utils) {
    var ChatClient = function(options) {
        if (window.ChatClient === ChatClient) {
            window.ChatClient = OldChatClient;
        }
        if(options.url)ChatClient.url = options.url;
        if(options.userId)ChatClient.userId = options.userId;
        if(options.debug)ChatClient.debug = options.debug;
    };
    var log = function (msg){
        if(console && console.log){
         console.log('ChatClient.'+msg);
        }
    };
    var Event = function (name,converter) {
        return {
            name:name,
            getName:function(data){
              return this.name;  
            },
            execute: function (data) {
                log(name+':'+data);
            },
            converter: converter,
            outerConverter:null
        };
    };
    var websocket = null;
    var DataConverter = {
        createUser:function (data) {
            var user = {
                id: data.id,
                image: data.image,
                name: data.name,
                watch: data.watch,
                status: data.status,
                unreadCount: data.unreadCount,
                canReply: data.canReply
            };
            return user;
        },
        createConversation:function(data){
            var conversation = {
                id:data.id,
                from: data.from,
                to: data.to,
                data: data.data,
                timestamp: data.timestamp
            };
            return conversation;
        }
    };
    var ReceiveEvents = {
        connect:new Event('/receive/connect',function(data){
            var executeData = data;
            return executeData;
        }),
        error:new Event('/receive/error',function(data){
            var executeData = data;
            return executeData;
        }),
        close:new Event('/receive/close',function(data){
            var executeData = data;
            return executeData;
        }),
        online:new Event('/receive/online',function(data){
            var executeData = {type:'online',user:{}};
            executeData.user = DataConverter.createUser(data.user);
            return executeData;
        }),
        onlines:new Event('/receive/onlines',function(data){
            var executeData = {type:'onlines',users:[]};
            var users = data.users;
            for(var i=0;i<users.length;i++){
                var user = DataConverter.createUser(users[i]);
                executeData.users.push(user);
            }
            return executeData;
        }),
        offline:new Event('/receive/offline',function(data){
            var executeData = {type:'offline',user:{}};
            executeData.user = DataConverter.createUser(data.user);
            return executeData;
        }),
        message:new Event('/receive/message',function(data){
            var executeData = {type:'message',conversation:{}};
            executeData.conversation = DataConverter.createConversation(data.conversation);
            return executeData;
        }),
        history:new Event('/receive/history',function(data){
            var executeData = {type:'history',from:'',to:'',conversations:[]};
            executeData.from = data.from;
            executeData.to = data.to;
            var conversations = data.conversations;
            for(var i=0;i<conversations.length;i++){
                var conversation = DataConverter.createConversation(conversations[i]);
                executeData.conversations.push(conversation);
            }
            return executeData;
        }),
        userlist:new Event('/receive/userlist',function(data){
            var executeData = {type:'userlist',event:'',me:{},users:[]};
            var users = data.users;
            executeData.event = data.event;
            executeData.me = DataConverter.createUser(data.me);
            for(var i=0;i<users.length;i++){
                var user = DataConverter.createUser(users[i]);
                executeData.users.push(user);
            }
            return executeData;
        }),
        topUserList:new Event('/receive/topUserList',function(data){
            var executeData = {type:'topUserList',event:'',me:{},users:[]};
            var users = data.users;
            executeData.event = data.event;
            executeData.me = DataConverter.createUser(data.me);
            for(var i=0;i<users.length;i++){
                var user = DataConverter.createUser(users[i]);
                executeData.users.push(user);
            }
            return executeData;
        })
    };
    var SendEvents = {
        online:new Event('/send/online',function(data){
            var sendData = {
                type:'online',
                timestamp:new Date().getTime()
            };
            return JSON.stringify(sendData);
        }),
        offline:new Event('/send/offline',function(data){
            var sendData = {
                type:'offline',
                timestamp:new Date().getTime()
            };
            return JSON.stringify(sendData);
        }),
        message:new Event('/send/message',function(data){
            var sendData = {
                type:'message',
                timestamp:new Date().getTime(),
                from:data.from,
                to:data.to,
                data:data.data
            };
            return JSON.stringify(sendData);
        }),
        history:new Event('/send/history',function(data){
            var sendData = {
                type:'history',
                timestamp:new Date().getTime(),
                from:data.from,
                to:data.to,
                lastTimestamp:data.timestamp,
                lastId:data.lastId,
                limit:data.limit
            };
            return JSON.stringify(sendData);
        }),
        userlist:new Event('/send/userlist',function(data){
            var sendData = {
                type:'userlist',
                timestamp:new Date().getTime(),
                event:data.event,
                name:data.name,
                from:data.from,
                to:data.to
            };
            return JSON.stringify(sendData);
        }),
        topUserList:new Event('/send/topUserList',function(data){
            var sendData = {
                type:'topUserList',
                timestamp:new Date().getTime(),
                event:data.event
            };
            return JSON.stringify(sendData);
        }),
        read:new Event('/send/read',function(data){
            var sendData = {
                type:'read',
                timestamp:new Date().getTime(),
                from:data.from
            };
            return JSON.stringify(sendData);
        }),
        connect:new Event('/send/connect',function(data){
            var sendData = {
                type:'connect',
                timestamp:new Date().getTime()
            };
            return JSON.stringify(sendData);
        })
    };
    
    var initWebsocket = function(){
        var userId = ChatClient.userId;
        var socket = new SockJS(ChatClient.url+'?userId='+userId);
        websocket = Stomp.over(socket);
        if(!ChatClient.debug){
            websocket.debug = null;
        }
        websocket.connect({userId:userId}, function(frame) {
            log('(已连接)');
            log('Connected: ' + frame);
            var subscribe = function(path,event){
                websocket.subscribe(path, function (result) {
                    var data = JSON.parse(result.body);
                    var event = ReceiveEvents[data.type];
                    var executeData = event.converter(data);
                    if (event.outerConverter != null) {
                        executeData = event.outerConverter(executeData);
                    }
                    event.execute(executeData);
                });
            };
            for(var key in ReceiveEvents) {
                var event = ReceiveEvents[key];
                subscribe('/user/'+userId+'/'+event.getName(userId),event);
            }
            websocket.send(SendEvents.connect.name,{},SendEvents.connect.converter(null));
        },function(){
            log('(连接发生错误)');
            ReceiveEvents.error.execute(null);
        });
    };
    
    ChatClient.prototype.on = function (eventName, event, dataConverter) {
        ReceiveEvents[eventName].execute = event;
        if(dataConverter!=null){
            ReceiveEvents[eventName].outerConverter = dataConverter;
        }
    };
    ChatClient.prototype.send = function (type, data) {
        var event = SendEvents[type];
        return new Promise(function (resolve) {
            var sendData = event.converter(data);
            websocket.send(event.getName(data), {}, sendData);
            resolve();
        });
    };
    ChatClient.prototype.open = function () {
        return new Promise(function (resolve) {
            initWebsocket();
            resolve();
        });
    };
    return ChatClient;
}(window.ChatClient,Green._Utils);


// 聊天服务器组件
Green._Server = function (window, document, Green, Utils, ReceiveEventHandler ) {
    var Server = {};
    var handler;

    var reconnectTimes = 0;
    var reconnectLimit = 0;
    var sendObject = {};
    
    Server.isOpen = false;

    Server.init = function () {
        handler = new ChatClient({url:this.url,userId:this.userId,debug:false});

        handler.on('connect', function (data) {
            _connect(data);
        });
        
        handler.on('close', function (data) {
            _onClose(data);
        });

        handler.on('message', function (data) {
            _onMessage(data);
        },function(data){
           return data.conversation;
        });

        handler.on('online', function (data) {
            _onOnline(data);
        },function(data){
           return [data.user];
        });

        handler.on('onlines', function (data) {
            _onOnline(data);
        },function(data){
           return data.users;
        });

        handler.on('offline', function (data) {
            _onOffline(data);
        },function(data){
           return [data.user.id];
        });

        handler.on('history', function (data) {
            _history(data);
        },function(data){
            return data;
        });

        handler.on('userlist', function (data) {
            _rosters(data);
        },function(data){
            var executeData = {
                type: 'userlist', 
                event: data.event, 
                me: data.me, 
                rosters: data.users
            };
            return executeData;
        });
        
        handler.on('topUserList', function (data) {
            _topRosters(data);
        },function(data){
            var executeData = {
                type: 'topUserList', 
                event: data.event, 
                me: data.me, 
                rosters: data.users
            };
            return executeData;
        });

        _open();
    };

    function _open() {
        if (!Server.isOpen && reconnectTimes == 0) ReceiveEventHandler.trigger('onConnectStart');
        handler.open().then(function () {
            Server.isOpen = true;
            reconnectTimes = 0;
        }, function (err) {
            ReceiveEventHandler.trigger('onConnectError');
        });
    }
    
    function _connect(data){
        ReceiveEventHandler.trigger('onConnectSuccess');
        if(Green.callbacks.afterConnect){
            Green.callbacks.afterConnect(data);
        }
    }

    function _onMessage(data) {
        // Green.log(data);//---------------------
        var message = data;
         message.data = message.data.replace('{$detailFrontUrl}', Green.gameUrl);

        ReceiveEventHandler.trigger('messageReceived', [message]);
        if(Green.callbacks.messageReceive){
            Green.callbacks.messageReceive(message);
        }
    }

    function _onClose(event) {
        Server.isOpen = false;

        if (reconnectTimes < reconnectLimit) {
            reconnectTimes++;

            ReceiveEventHandler.trigger('onReconnect', [reconnectTimes]);

            Server.init();
        }
        else {
            ReceiveEventHandler.trigger('onConnectClose');
        }
    }

    function _onOnline(users) {
        for (var i = 0, len = users.length; i < len; i++) {
            if (users[i].id == Green.me.id) continue;

            ReceiveEventHandler.trigger('online', [users[i]]);
        }
        if(Green.callbacks.userNoticeOnline){
            Green.callbacks.userNoticeOnline(users);
        }
    }

    function _onOffline(ids) {
        for (var i = 0, len = ids.length; i < len; i++) {
            if (ids[i] == Green.me.id) continue;
            ReceiveEventHandler.trigger('offline', [ids[i]]);
        }
        if(Green.callbacks.userNoticeOffline){
            Green.callbacks.userNoticeOffline(ids);
        }
    }
    
    function _history(data){
        var messages = [];
        var conversations = data.conversations;
        try {
            for (var i = 0, len = conversations.length; i < len; i++) {
                messages.push({
                    'id': conversations[i].id,
                    'from': conversations[i].from,
                    'to': conversations[i].to[0],
                    'data': conversations[i].data.replace('{$detailFrontUrl}', Green.gameUrl),
                    'timestamp': conversations[i].timestamp
                });
            }
        } catch (e) {
            messages = false;
        }
        var history = sendObject['history'+data.from+data.to];
        ReceiveEventHandler.trigger('getHistorySuccess', [history.param, history.converse, messages]);
    }
    
    function _rosters(msg){
        var data = msg;
        if (data.length == 0) return;

        window.currentChatUser = Green.me = data.me;
        Green.me.id = Green.me.id.toString();
        function appendRosters(){
            if(Green.rosters==null){
                Green.rosters = {};
                Green.rosterIds = [];
            }
            var rosters = Green.rosters;
            var rosterIds = Green.rosterIds;
            for (var i = 0, len = data.rosters.length; i < len; i++) {
                var id = data.rosters[i]['id'].toString();
                data.rosters[i]['id'] = id;
                rosters[id] = {
                    id: id,
                    name: data.rosters[i]['name'],
                    image: data.rosters[i]['image'],
                    unreadCount: data.rosters[i]['unreadCount'],
                    onlineStatus: 'offline',
                    canReply: data.rosters[i]['canReply'],
                    unreadMessages: [],
                    isTopUser:false
                };
                if($.inArray(id,rosterIds)==-1){
                    rosterIds.push(id);
                }
            }
        }
        switch(data.event){
            case 'append':
                appendRosters();
                ReceiveEventHandler.trigger('scrollRostersSuccess');
                break;
            case 'refresh':
            default:
                appendRosters();
                ReceiveEventHandler.trigger('getRostersSuccess');
                break;
        }
       
    }
    
    function _topRosters(msg){
        var data = msg;
        if (data.length == 0) return;
        Green.topUnreadCount = data.me.unreadCount;
        function appendRosters(){
            if(Green.topRosters==null){
                Green.topRosters = {};
                Green.topRosterIds = [];
            }
            var rosters = Green.topRosters;
            var rosterIds = Green.topRosterIds;
            for (var i = 0, len = data.rosters.length; i < len; i++) {
                var id = data.rosters[i]['id'].toString();
                data.rosters[i]['id'] = id;
                rosters[id] = {
                    id: id,
                    name: data.rosters[i]['name'],
                    image: data.rosters[i]['image'],
                    unreadCount: data.rosters[i]['unreadCount'],
                    onlineStatus: 'offline',
                    canReply: data.rosters[i]['canReply'],
                    unreadMessages: [],
                    isTopUser:true
                };
                if($.inArray(id,rosterIds)==-1){
                    rosterIds.push(id);
                }
            }
        }
        appendRosters();
        ReceiveEventHandler.trigger('getTopRostersSuccess');
    }

    Server.getHistory = function (param, converse) {
        sendObject['history'+param.convid[0]+param.convid[1]] = {param:param,converse:converse};
        var data = {
            from:param.convid[0],
            to:param.convid[1],
            timestamp:param.timestamp,
            lastId:param.id,
            limit:param.limit
        };
        handler.send('history',data).then(function () {
            Green.log('送出[取得历史记录]要求成功');
        }, function (err) {
            ReceiveEventHandler.trigger('getHistoryFailed', [param, converse]);
        });
    };

    Server.sendMessage = function (message, dom) {
        handler.send('message',message).then(function () {
            ReceiveEventHandler.trigger('sendMessageSuccess', [message, dom]);
            if(Green.callbacks.messageSend){
                Green.callbacks.messageSend(message);
            }
        }, function (err) {
            ReceiveEventHandler.trigger('sendMessageFailed', [message, dom]);
        });
    };
    
    Server.readMessage = function(data){
        handler.send('read',data).then(function () {
            Green.log('送出[已读]要求成功');
            if(Green.callbacks.messageRead){
                Green.callbacks.messageRead(data);
            }
        }, function (err) {
            Green.log('送出[已读]要求失败');
        });
    };
    
    Server.queryUserList = function(data){
        handler.send('userlist',data).then(function () {
            Green.log('送出[查询用户清单]要求成功');
        }, function (err) {
            Green.log('送出[查询用户清单]要求失败');
        });
    };
    
    Server.changeStatus = function(data){
        handler.send(data.type,data).then(function () {
            Green.log('送出[改变用户状态]要求成功');
        }, function (err) {
            Green.log('送出[改变用户状态]要求失败');
        });
    };
    
    Server.setUserId = function(userId){
        this.userId = userId;
    };
    
    Server.setUrl = function(url){
        this.url = url;
    };
    
    return Server;

}(window, document, Green, Green._Utils, Green._ReceiveEventHandler);


Green.init = function (window, document, Green, Utils, Expression, SendEventHandler, ReceiveEventHandler ,UIEventHandler, ChatboxView, RosterListView, ChatWithListView, ConverseListView, InputView, StatusView, Server) {
    Green.debug = true;

    Green.hide = function(){
    	this.dom.hide();
    };
    
    Green.show = function(){
    	this.dom.show();
    };
    
    function _commit() {
        var render = '<div class="greenchat"><div class="greenchat-inner"></div></div>';

        var dom = $(render);

        dom.append(StatusView.commit());

        dom.append(ChatboxView.commit());

        dom.append(RosterListView.commit({},[]));
        
        Green.dom = dom;
        
        return dom;
    }

    function _deploy() {
        $('body').append(_commit());
    };

    function init(options) {
        if (options.url) Server.setUrl(options.url);
        if (options.resUrl) Green.resUrl = options.resUrl;
        if (options.gameUrl) Green.gameUrl = options.gameUrl;
        if (options.userId) Server.setUserId(options.userId);
        if (options.iconUrl) Expression.init(options.iconUrl);
        if (options.callbacks){
            Green.callbacks ={
              messageSend:options.callbacks.messageSend,
              messageReceive:options.callbacks.messageReceive,
              messageRead:options.callbacks.messageRead,
              userNoticeOnline:options.callbacks.userNoticeOnline,
              userNoticeOffline:options.callbacks.userNoticeOffline,
              afterConnect:options.callbacks.afterConnect
            };
        }
        if(Green.callbacks==null){
            Green.callbacks = {};
        }
        _deploy();
        Server.init();
    }

    /* －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     * 事件管理
     */
    function initSendEvent(){

        SendEventHandler.on('sendMessageEnter', function (text) {
            if (!Server.isOpen) alert('连接已断开，刷新重试');

            InputView.reset();

            var converseView = ConverseListView.currentView;

            var message = {
                from: Green.me.id,
                to: converseView.roster.id,
                data: text,
                timestamp: new Date().getTime()
            };
            var renderMessage = {
                from: Green.me.id,
                to: converseView.roster.id,
                data: Utils.escapeHtml(text),
                timestamp: new Date().getTime()
            };
            var domMessage = converseView.renderMessage(renderMessage, 'send');

            Server.sendMessage(message, domMessage);
        });

        SendEventHandler.on('readMessage', function (chatWithView) {
            var roster = chatWithView.roster;
            RosterListView.get(roster.id).updateUnread(0);
            chatWithView.updateUnread(0);
            var data = {
              from:roster.id
            };
            Server.readMessage(data);
        });

        SendEventHandler.on('queryUserList', function (rosterListView,event,name) {
            var data = {
              event:event,
              from:rosterListView.from,
              to:rosterListView.to,
              name:name
            };
            Server.queryUserList(data);
        });

        SendEventHandler.on('changeUserStatus', function (statusIndex) {
            var status = ['offline','online'];
            var data = {
                type:status[statusIndex]
            };
            Server.changeStatus(data);
        });

        SendEventHandler.on('getHistory', function (param, converse) {
            if(converse.firstHistory==null){
                converse.firstHistory = true;
                param.limit = 3;
            }
            Server.getHistory(param, converse);
        });
    }
    
    function initReceiveEvent(){
        ReceiveEventHandler.on('onConnectStart', function () {
            StatusView.msg('聊天初始化中...');
        });

        ReceiveEventHandler.on('onConnectSuccess', function () {
            StatusView.hide();
        });
        
        ReceiveEventHandler.on('onConnectClose', function () {
            StatusView.msg('连接已断开，刷新重试');
        });

        ReceiveEventHandler.on('onReconnect', function (count) {
            StatusView.msg('连接失败，重连中(' + count + ')...');
        });

        ReceiveEventHandler.on('getRostersSuccess', function () {
            RosterListView.refresh(Green.rosters,Green.rosterIds);
            RosterListView.initUI();
            RosterListView.initUserPage();
            RosterListView.searchView.refreshUserListView();
            RosterListView.changeStatus(Green.me.status);
            RosterListView.addUnreadCount(Green.me.unreadCount);
            if(!InputView.isInit){
                InputView.init();
                InputView.isInit = true;
            }
            if(!StatusView.isInit){
                StatusView.isInit = true;
            }
        });
        
        ReceiveEventHandler.on('getTopRostersSuccess', function () {
            RosterListView.refreshTop(Green.topRosters,Green.topRosterIds);
            RosterListView.addUnreadCount(Green.topUnreadCount);
        });

        ReceiveEventHandler.on('scrollRostersSuccess', function () {
            RosterListView.refresh(Green.rosters,Green.rosterIds);
            RosterListView.initUI();
            RosterListView.searchView.refreshUserListView();
        });

        ReceiveEventHandler.on('online', function (user) {
            if(RosterListView.get(user.id)==null){
                var roster={
                    id: user.id,
                    name: user.name,
                    image: user.image,
                    unreadCount: 0,
                    onlineStatus: 'online',
                    canReply: user.canReply,
                    unreadMessages: [],
                    isTopUser: false
                };
                RosterListView.addRosterView(user.id,roster);
                Green.rosters[roster.id] = roster;
            }

            RosterListView.get(user.id).updateOnlineStatus('online');
            if(!RosterListView.get(user.id).roster.isTopUser){
                RosterListView.prepend(user.id);
            }

            var chatWithView = ChatWithListView.get(user.id);
            if (chatWithView) chatWithView.updateOnlineStatus('online');
        });
        
        ReceiveEventHandler.on('offline', function (id) {
            RosterListView.get(id).updateOnlineStatus('offline');

            var chatWithView = ChatWithListView.get(id);
            if (chatWithView) chatWithView.updateOnlineStatus('offline');
        });

        ReceiveEventHandler.on('sendMessageSuccess', function (message, domMessage) {
            domMessage.addClass('send-success');
        });

        ReceiveEventHandler.on('sendMessageFailed', function (message, domMessage) {
            domMessage.addClass('send-failed');
        });

        ReceiveEventHandler.on('getHistorySuccess', function (param, converseView, data) {
            if (data === false) {
                converseView.showHistoryFailed();
                return false;
            }

            if (!data || data.length == 0) {
                converseView.showNoHistory();
                return false;
            }

            data.reverse();

            converseView.lastMessage = data[0];

            converseView.renderMessage(data, 'history');

            if (!converseView.firstHistory&&data.length <= param.limit) {
                converseView.showNoHistory();
            }
            else {
                converseView.showLoadmore();
            }
        });

        ReceiveEventHandler.on('getHistoryFailed', function (param, converseView) {
            converseView.showHistoryFailed();
        });


        ReceiveEventHandler.on('messageReceived', function (message) {
            Green.show();
            // 多窗口信息同步
            if (message.from == Green.me.id) {
                if (ConverseListView.get(message.to)) {
                    var chatWithView = ChatWithListView.get(message.to);

                    UIEventHandler.trigger('chatWithClick', [chatWithView]);

                    ConverseListView.get(message.to).renderMessage(message, 'receive');

                    ChatWithListView.prepend(message.to);
                } else {
                    var rosterView = RosterListView.get(message.to);

                    rosterView.roster.unreadMessages.push(message);

                    UIEventHandler.trigger('rosterClick', [rosterView]);
                }

                RosterListView.prepend(message.to);

                return false;
            }

            var rosterView = RosterListView.get(message.from);
            if (!rosterView) return false;

            var unreadCount = rosterView.roster.unreadCount;
            unreadCount++;

            if(!RosterListView.get(message.from).roster.isTopUser){
                RosterListView.prepend(message.from);
            }
            
            if (ConverseListView.get(message.from)) {// 聊天窗口存在
                var chatWithView = ChatWithListView.get(message.from);
                var converseView = ConverseListView.get(message.from);
                var isCur = ConverseListView.currentView.roster.id == message.from;

                ChatWithListView.prepend(message.from);

                if (ChatboxView.isMinus()) {
                    ChatboxView.dom.addClass('chat-new-message');

                    chatWithView.updateUnread(unreadCount);
                    rosterView.updateUnread(unreadCount);
                    RosterListView.addUnreadCount(1);
                    
                    converseView.renderMessage(message, 'notCurrent');
                } else {
                    if (!isCur) {
                        chatWithView.updateUnread(unreadCount);
                        rosterView.updateUnread(unreadCount);
                        RosterListView.addUnreadCount(1);
                        
                        converseView.renderMessage(message, 'notCurrent');
                    } else {
                        SendEventHandler.trigger('readMessage',[chatWithView]);
                        converseView.renderMessage(message, 'receive');
                    }
                }

            } else {
                rosterView.updateUnread(unreadCount);
                RosterListView.addUnreadCount(1);
                
                rosterView.roster.unreadMessages.push(message);
            }
        });
    }
    
    function initUIEvent(){
        UIEventHandler.on('rosterListViewToggle', function (status) {
            RosterListView.toggle(status);
        });

        UIEventHandler.on('rosterClick', function (rosterView) {
            var roster = rosterView.roster;
            
            ChatboxView.show();

            InputView.setReply(roster.canReply);

            var chatWithView = ChatWithListView.add(roster);

            ChatboxView.cleanMulti();
            if (ChatWithListView.chatWithViews.length > 1) {
                ChatboxView.beMulti();
            }
            
            RosterListView.addUnreadCount(-1 * roster.unreadCount);
            ConverseListView.add(roster);

            UIEventHandler.trigger('chatWithClick', [chatWithView]);

        });

        UIEventHandler.on('chatWithClick', function (chatWithView) {
            var roster = chatWithView.roster;

            SendEventHandler.trigger('readMessage', [chatWithView]);

            chatWithView.active();

            InputView.setReply(roster.canReply);
            ConverseListView.front(roster.id);
            ConverseListView.currentView = ConverseListView.get(roster.id);
            ConverseListView.currentView.scroll();

            if (ChatboxView.isMinus) ChatboxView.maxfiy();

            ChatboxView.dom.find('.chat-with img').attr('src', Green.resUrl+roster.image);
            ChatboxView.dom.find('.chat-with .chat-name').html(roster.name);
            ChatboxView.dom.find('.cur-chat-name').html(roster.name);
        });


        UIEventHandler.on('chatWithRemove', function (chatWithView) {
            ChatWithListView.remove(chatWithView);

            ConverseListView.remove(chatWithView.roster.id);

            if (ChatWithListView.chatWithViews.length == 1) {
                ChatboxView.cleanMulti();
            }
        });


        UIEventHandler.on('chatboxMaxfiy', function () {
            var curId = ConverseListView.currentView.roster.id;

            RosterListView.get(curId).updateUnread(0);

            ChatboxView.maxfiy();
        });

        UIEventHandler.on('chatboxRemove', function () {
            ChatboxView.remove();
        });
    }
    
    initSendEvent();
    initReceiveEvent();
    initUIEvent();
    
    return init;

}(window, document, Green, Green._Utils,Green._Expression, Green._SendEventHandler, Green._ReceiveEventHandler, Green._UIEventHandler, Green._ChatboxView, Green._RosterListView, Green._ChatWithListView, Green._ConverseListView, Green._InputView, Green._StatusView, Green._Server);


/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD (Register as an anonymous module)
        define(['jquery'], factory);
    } else if (typeof exports === 'object') {
        // Node/CommonJS
        module.exports = factory(require('jquery'));
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {

    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            // If we can't parse the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
            return config.json ? JSON.parse(s) : s;
        } catch (e) {
        }
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function (key, value, options) {

        // Write

        if (arguments.length > 1 && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path ? '; path=' + options.path : '',
                options.domain ? '; domain=' + options.domain : '',
                options.secure ? '; secure' : ''
            ].join(''));
        }

        // Read

        var result = key ? undefined : {},
        // To prevent the for loop in the first place assign an empty array
        // in case there are no cookies at all. Also prevents odd result when
        // calling $.cookie().
            cookies = document.cookie ? document.cookie.split('; ') : [],
            i = 0,
            l = cookies.length;

        for (; i < l; i++) {
            var parts = cookies[i].split('='),
                name = decode(parts.shift()),
                cookie = parts.join('=');

            if (key === name) {
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        // Must not alter options, thus extending a fresh object...
        $.cookie(key, '', $.extend({}, options, {expires: -1}));
        return !$.cookie(key);
    };

}));