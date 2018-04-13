//站内信模块
(function ($) {
    function arMessage() {
        this.list = {
            letter: [],
            message: []
        }
        this.mask = null;
        this.domLetter = null;
        this.domMsg = null;
    }
    arMessage.prototype = {
        init: function () {
            var me = this;

            this.createBox();
        },
        /**
         * 通过服务器获取消息
         */
        getMsg: function () {
            var cre = this;
            $.getJSON("/service2/hometipmessage",
                    function (data) {
                        cre.createList(data);
                    }
            );
        },
        /**
         * 创建窗口
         */
        createBox: function () {
            var _oMsg = {};
            var _this = this;
            _oMsg = this.getMsg();
            this.createList(_oMsg);  // 消息队列
        },
        /**
         * 创建消息队列
         * @param obj 服务器返回的消息对象
         * @param callback   成功回调
         */
        createList: function (msg) {
            var _oMsg = msg;
            if (!_oMsg)
                return; //如果没有消息 返回
            for (var o in _oMsg.message) {
                var _status = _oMsg.message[o].status;
                if (_status == "0") {
                    this.list.message.push(_oMsg.message[o]); // 把特殊消息推进队列
                } else {
                    this.list.letter.push(_oMsg.message[o]);  // 把站内信推进队列
                }
            }
            if (this.list.letter.length) {
                this.createLetter();
            }
            if (this.list.message.length) {
                this.createMsg();
            }
        },
        /**
         * 创建letter容器
         */
        createLetter: function () {
            if (!this.domLetter) {
                var domBox = $('<div class="ar-letter"><div class="ar-letter-title"><h3></h3><p></p><span' +
                        ' class="ar-count">5</span><span class="ar-close">x</span></div><div class="ar-letter-content"><div class="div_scroll"><div class="ar-leftter-message"></div></div></div><div class="ar-letter-footer"><a href="javascript:;">下一条</a></div></div>');

                var mask = phoenix.Mask.getInstance();
                this.mask = mask;
                this.domLetter = domBox;
                $('body').append(mask).append(domBox);
                this.positionFixd(this.domLetter);
            }
            this.readLetter();
        },
        /**
         * 创建msg容器
         */
        createMsg: function () {
            var domBox = $('<div class="ar-bot-box"><div class="ar-bot-msg"><div class="ar-msg-title"></div><div class="ar-msg-content"></div><div class="ar-msg-footer"><span>查看详情</span></div><div class="ar-msg-close">x</div></div></div>');
            $('body').append(domBox);
            if (!this.domMsg) {
                this.domMsg = domBox;
            }
            this.readMsg();
        },
        /**
         * 读取信息
         * @param obj
         */
        readLetter: function () {

            if (!this.domLetter) {
                this.createLetter();
            }
            this.mask.show();
            this.domLetter.show();
            var $box = this.domLetter;
            var letter = this.list.letter.shift();
            var _this = this;
            var count = 5;
            if (letter) {
                $box.find('.ar-letter-title h3').html(letter.title);
                $box.find('.ar-letter-title p').html(letter.author + "  " + letter.time);
                $box.find('.ar-leftter-message').html(letter.content);
                if (letter.content.length < 180) {
                    $box.addClass('ar-small');
                } else {
                    $box.removeClass('ar-small');
                }
                //$('.div_scroll').scroll_absolute(); //自定义滚动条 依赖 mousewheel.js easyscroll.js
                var $btn = $box.find('.ar-letter-footer a'),
                        $close = $box.find('.ar-close'),
                        $count = $box.find('.ar-count'),
                        $footer = $box.find('.ar-letter-footer');
                //console.log($btn, $close, $box);

                if (this.list.letter.length) {
                    if (letter.status == '2') { //强制阅读5秒
                        $btn.addClass('ar-disabled');
                        $count.hide();
                        $close.hide();
                        $btn.off('click');

                        var timer = setInterval(function () {
                            $btn.html("下一条(" + count + ")");
                            if (count-- == 0) {
                                clearInterval(timer);
                                $btn.removeClass('ar-disabled');
                                $btn.html("下一条");
                                $btn.click(function () {
                                    _this.readLetter($box);
                                    _this.markMsg(letter.mid);
                                });
                            }
                        }, 1000);
                    } else {
                        $count.hide();
                        $close.hide();
                        $btn.click(function () {
                            _this.readLetter($box);
                            _this.markMsg(letter.mid);
                        });
                    }
                } else {
                    $footer.hide();
                    $close.click(function () {
                        _this.closeLetter();
                    })

                    if (letter.status == '2') { //强制阅读5秒
                        $btn.addClass('ar-disabled');
                        $count.show();
                        $close.hide();
                        $btn.off('click');

                        var timer = setInterval(function () {
                            $count.html(count);
                            if (count-- == 0) {
                                clearInterval(timer);
                                $btn.removeClass('ar-disabled');
                                $count.hide();
                                $close.show().click(function () {
                                    _this.markMsg(letter.mid);
                                });
                                $btn.click(function () {
                                    _this.readLetter($box);
                                    _this.markMsg(letter.mid);
                                });
                            }
                        }, 1000);
                    } else {
                        $count.hide();
                        $close.show().click(function () {
                            _this.markMsg(letter.mid);
                        });
                        $btn.click(function () {
                            _this.readLetter($box);
                            _this.markMsg(letter.mid);
                        });
                    }
                }
            }

        },
        /**
         * 已阅读MARK
         */
        markMsg: function (mid) {
            mid = String(mid);
            $.ajax({
                type: 'POST',
                url: "/service2/marknoticeread",
                data: {id: mid},
                dataType: "json",
                success: function (data) {
                    //console.log(data);
                }
            });
        },
        /**
         * 读取特殊消息
         */
        readMsg: function () {
            var $box = this.domMsg;
            var msg = this.list.message.shift();
            var _this = this;
            var $btn = $box.find('.ar-msg-footer span'),
                    $close = $box.find('.ar-msg-close');
            if (msg) {
                $box.find('.ar-msg-title').html(msg.title);
                $box.find('.ar-msg-content').html(msg.content.substr(0, 38) + '...');
                $box.find('.ar-bot-msg').animate({marginTop: "0px"}, 'fast');
                $btn.off('click');
                $close.off('click');
                $btn.click(function (e) {
                    msg.status = '1';
                    _this.list.letter.push(msg);
                    $box.find('.ar-bot-msg').animate({marginTop: "140px"}, 'fast');
                    _this.readLetter();
                    _this.readMsg();
                })
                $close.click(function () {
                    $box.find('.ar-bot-msg').animate({marginTop: "140px"}, 'fast');
                    _this.readMsg();
                })

            }
            ;

        },
        /**
         * 关闭消息窗口
         */
        closeLetter: function () {
            this.mask.hide();
            this.domLetter.hide();
        },
        positionFixd: function (obj) {
            var $box = $(obj);

            function move() {
                var height = $box.height(),
                        width = $box.width(),
                        wH = $(window).height(),
                        wW = $(window).width(),
                        scroll = $(document).scrollTop();
                $box.css({left: (wW - width) / 2, top: scroll + (wH - height) / 2})

            }
            move();
            $(window).on('scroll', function () {
                move();
            })
            $(window).on('resize', function () {
                move();
            })

        }

    }
    var armessage = new arMessage();
    armessage.init();

})(jQuery);