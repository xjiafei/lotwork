;
(function($) {
    var imgLoadNum = 0,
        transTime = 1,
        allStep = 13,
        forwardStep = 0,
        currentStep = 0,
        wheelLock = true,
        clickLock = true,
		 keyLock = true,
        callbackFn = [],
        blurFn = [],
        domParent = $('#J-animate-show'),
        delStyle = 'hide prev next',
		currentAnimateDom = '',
        animateDomList = domParent.children('section');

    var halt = function(e) {
        e.preventDefault();
        e.stopPropagation();
    };

    var animate = function() {

        this.init();
    };

    $.extend(true, animate['prototype'], {

        init: function() {
            var me = this;

            me.bindEvent();
            domParent.css('height', $(window).height());
        },

        bindEvent: function() {
            var me = this;
			
			$(document).keydown(function(event) {
                var keyCode = event['keyCode'];

                //上
                if(keyCode == 38){
                    var num = currentStep-1;
                    if(keyLock || currentStep < 0){
                        return;
                    }
                    keyLock = true;
                    me.controlStep(num);

                    setTimeout(function() {
                        keyLock = false;
                    }, transTime * 1000);
                //下
                } else if(keyCode == 40){
                    var num = currentStep+1;
                    if(keyLock || currentStep > allStep){
                        return;
                    }
                    keyLock = true;
                    me.controlStep(num);
                //左
                } else if(keyCode == 37){
                    var dom = me.getAnimateDom().find('.cycle-slideshow');
                    if(keyLock || currentStep < 0){
                        return;
                    }
                    keyLock = true;
                    if(dom.size() > 0){
                        dom.cycle('prev');
                    }
                //右
                } else if(keyCode == 39){
                    var dom = me.getAnimateDom().find('.cycle-slideshow');
                    if(keyLock || currentStep < 0){
                        return;
                    }
                    keyLock = true;
                    if(dom.size() > 0){
                        dom.cycle('next');
                    }
                //home
                } else if(keyCode == 36){
                    if(keyLock || currentStep < 0){
                        return;
                    }
                    keyLock = true;
                    me.controlStep(0);
                //end
                } else if(keyCode == 35){
                    if(keyLock || currentStep < 0){
                        return;
                    }
                    keyLock = true;
                    me.controlStep(allStep);
                }

                setTimeout(function() {
                    keyLock = false;
                }, transTime * 1000);
            });

            $(document).mousewheel(function(event, delta, delyaX, delyaY) {
                var deltaSpeed = delta;

                me.controlMouse(this, event, delta);
                halt(event);
            });

            $('#J-page-control').on('click', 'li', function(){
                var step = $(this).index(),
                    stepNum = Number($(this).attr('data-step'));

                if(clickLock){
                    return;
                }
                clickLock = true;

                me.controlDomHide(step);
                me.controlStep(stepNum);

                setTimeout(function() {
                    clickLock = false;
                }, transTime * 1000);
            });

            $('header').on('click', 'a', function(){
                var step = Number($(this).attr('data-stepnum')),
                    stepNum = Number($(this).attr('data-step'));

                if(clickLock){
                    return;
                }
                clickLock = true;


                me.controlDomHide(step);
                me.controlStep(stepNum);

                setTimeout(function() {
                    clickLock = false;
                }, transTime * 1000);
            });
        },

        resizeBg : function(){
            var bgDom12 = $('.step-twelve-bg'),
                //bgDom13 = $('.step-thirteen-bg'),
                wWidth = $(window).width(),
                wHeight = $(window).height();

            if(wHeight < 701){
                bgDom12.height(wHeight);
                bgDom12.css('margin-top', -wHeight/2);
            }

            if(wHeight < 977){
                //bgDom13.height(wHeight);
                //bgDom13.css('margin-top', -wHeight/2);
            }

            if(wWidth < 1120){
                bgDom12.width(wWidth);
                bgDom12.css('margin-left', -wWidth/2);
            }
        },

        //场景切换动画
        //不能和步骤控制统一处理
        controlDomHide: function(step, callback) {
            var me = this,
                animateType = 'cubic-bezier(0.86, 0, 0.07, 1)';

            $.each(animateDomList, function(index, val) {
                var dom = $(this),
                    top = $(this).offset().top.toFixed(0),
                    domHeight = $(this).height(),
                    winHeight = $(window).height();

                if (index < step && top <= 0 && !$(this).hasClass('prev')) {

                    if(index == 1){
                        domHeight = 1501;
                    }

                    $(this).removeClass('next').translate3d({
                        y: -winHeight
                    }, transTime * 1000, animateType, function() {
                        dom.addClass('hide prev');
                        callback && callback();
                    });
                }

                if (index > step && top == 0 && !$(this).hasClass('next')) {
                    
                    $(this).removeClass('prev').translate3d({
                        y: winHeight
                    }, transTime * 1000, animateType, function() {
                        dom.addClass('hide next');
                        callback && callback();
                    });
                }

                if (index == step && top <= -winHeight || index == step && top >= winHeight) {
                     me.setAnimateDom($(this));
					//添加最层级
                    $(this).css('z-index', 20);
                    $(this).translate3d({
                        y: 0
                    }, transTime * 1000, animateType, function() {
                        //dom.css('top', 0);
                        callback && callback();
						
                    });
					 me.pageCotrol(step);
                    me.headerControl(step);
                }

                if(index != step){
                    //消除当前状态
                    //避免直接画面跳动
                    setTimeout(function() {
                        animateDomList.eq(index).removeClass('current');
                    }, 1000);
                    
                    //去除最高层级
                    $(this).css('z-index', 0);
                }

            });
        },

        controlMouse: function(dom, e, delta) {
            var me = this,
                domList = domParent.children('section'),
                nextStep = '';

            if (wheelLock || !delta || !dom || !e) {
                return;
            }

            //down
            if (delta <= 0) {
                if (currentStep + 1 > allStep) {
                    return;
                }
                nextStep = currentStep + 1;
                //up
            } else {
                if (currentStep - 1 < 0) {
                    return;
                }
                nextStep = currentStep - 1;
            }
            wheelLock = true;

            me.controlStep(nextStep);

            setTimeout(function() {
                wheelLock = false;
            }, 2000);
        },

        reloadBind: function(){
            var me = this;

            /*step two*/
            $('.step-2-next').on('click', function() {
                $('#focus-one-step').cycle('next');
            });

            $('.step-2-prev').on('click', function() {
                $('#focus-one-step').cycle('prev');
            });

            $('#focus-one-step').on('cycle-before', function(event, optionHash, outgoingSlideEl, incomingSlideEl, forwardFlag) {
                var currentNum = optionHash['slideNum'] - 1,
                    domList = $(this).parent().find('ul > li');

                domList.removeClass('current');
                domList.eq(currentNum).addClass('current');

            });

            /*step two-one*/
            $('.step-2-1-next').on('click', function() {
                $('#focus-step-2-1').cycle('next');
            });

            $('.step-2-1-prev').on('click', function() {
                $('#focus-step-2-1').cycle('prev');
            });

            $('#focus-step-2-1').on('cycle-before', function(event, optionHash, outgoingSlideEl, incomingSlideEl, forwardFlag) {
                var currentNum = optionHash['slideNum'] - 1,
                    domList = $(this).parent().find('.current-slide > li');

                domList.removeClass('current');
                domList.eq(currentNum).addClass('current');
            });

             /*step six*/
            $('.step-6-next').on('click', function() {
                $('#focus-step-six').cycle('next');
            });

            $('.step-6-prev').on('click', function() {
                $('#focus-step-six').cycle('prev');
            });

            $('#focus-step-six').on('cycle-before', function(event, optionHash, outgoingSlideEl, incomingSlideEl, forwardFlag) {
                var currentNum = optionHash['slideNum'] - 1,
                    parentDom = $(this).parent(),
                    bgDomList = parentDom.find('.step-bg'),
                    domList = $(this).parent().find('.current-slide > li');

                bgDomList.fadeOut(400).eq(currentNum).fadeIn(400);
            });

            //step
            $('.step-nine-next').on('click', function() {
                $('#focus-step-9').cycle('next');
            });

            $('.step-nine-prev').on('click', function() {
                $('#focus-step-9').cycle('prev');
            });

            //step
            $('.step-ten-next').on('click', function() {
                $('#focus-step-ten').cycle('next');
            });

            $('.step-ten-prev').on('click', function() {
                $('#focus-step-ten').cycle('prev');
            });

            //step
            $('.step-elven-next').on('click', function() {
                $('#focus-step-elven').cycle('next');
            });

            $('.step-elven-prev').on('click', function() {
                $('#focus-step-elven').cycle('prev');
            });
			 //step
            $('.step-14-next').on('click', function() {
                $('#focus-step-14').cycle('next');
            });

            $('.step-14-prev').on('click', function() {
                $('#focus-step-14').cycle('prev');
            });
        },

        isSupport: function(){
            var me = this;

            return me['support'] || (me['support'] = BrowserDetect['browser'] && BrowserDetect['version'] < 10)
        },

        //全局loading加载
        globaLoad: function(num) {
            var me = this;

            imgLoadNum++;
                
            if (num == imgLoadNum) {
                //图片静态资源加载完毕
                me.startAnimate();
            }
        },

        startAnimate: function(){
            var me = this,
				wHeight =  $(window).height(),
                nav = $('header');
				
            //头部
            $('header').html($('#J-header-dom').text());
            //内容
            domParent.html($('#J-page-dom').text());
            //内容
            $('#J-page-control').html($('#J-sidebar-dom').text());
            //重新寻找DOM
            animateDomList = domParent.children('section');
            //切换图初始化
            //$('.cycle-slideshow').cycle();
			if(!me.isSupport()){
                //切换图初始化
                $('.cycle-slideshow').cycle();
            }
            //重新绑定切换图按钮
            me.reloadBind();

            //隐藏loading
            setTimeout(function(){

                //me.resizeBg();
                
                //隐藏loading
                $('#J-globa-loading').fadeOut(500, function(){
                    //
                    me.controlStep(0);
                });
				  if(me.isSupport()){
                    //执行IE缩放
                    me.scaleImg(wHeight);
                }
                setTimeout(function(){
                    wheelLock = false;
                    clickLock = false;
					keyLock = false;
                }, 2000);

            }, 0.3 * 1000);
        },

        changeNavStyle: function(styleName){
            var me = this,
                navDom = $('header');

            navDom.removeClass().addClass(styleName);
        },

        //load动画
        loadProcess: function() {
            var me = this,
                imgList = ['images/manual/nav/logo.png'
                            ,'images/manual/nav/nav-bg.png'
                            //step-1
                            ,'images/manual/step-1/button.png'
                            ,'images/manual/step-1/desc.png'
                            ,'images/manual/step-1/process.png'
                            ,'images/manual/step-1/step-1-bg.jpg'
                            ,'images/manual/step-1/step-1-slogan.png'
                            ,'images/manual/step-1/ball-1.png'
                            ,'images/manual/step-1/ball-2.png'
                            ,'images/manual/step-1/ball-3.png'
                            ,'images/manual/step-1/ball-4.png'
                            ,'images/manual/step-1/ball-5.png'
                            ,'images/manual/step-1/ball-6.png'
                            ,'images/manual/step-1/ball-7.png'
                            //step-2
                            ,'images/manual/step-2/step-2-banner-1.png'
                            ,'images/manual/step-2/step-2-banner-2.png'
                            //step-3
                            ,'images/manual/step-3/step-3-lights.png'
                            ,'images/manual/step-3/computer.png'
                            ,'images/manual/step-3/step-3-bg.jpg'
                            ,'images/manual/step-3/step-3-slogan.png'
                            //step-4
                            
                            //step-4-1
                            ,'images/manual/step-4-1/slogan.png'
                            //step-5
                            ,'images/manual/step-5/step-5-bg.jpg'
                            ,'images/manual/step-5/slogan.png'
                            ,'images/manual/step-5/page.jpg'
                            ,'images/manual/step-5/change-bg.jpg'
                            //step-6
                            ,'images/manual/step-6/6-bg-1.jpg'
                            ,'images/manual/step-6/6-bg-2.jpg'
                            ,'images/manual/step-6/6-bg-3.jpg'
                            ,'images/manual/step-6/6-bg-4.jpg'
                            ,'images/manual/step-6/s-1.png'
                            ,'images/manual/step-6/s-2.png'
                            ,'images/manual/step-6/s-3.png'
                            ,'images/manual/step-6/s-4.png'
                            //step-7
                            ,'images/manual/step-7/bg.jpg'
                            //step-8
                            ,'images/manual/step-8/bg.png'
                            //step-9
                            ,'images/manual/step-9/bg1.png'
                            ,'images/manual/step-9/bg2.png'
                            ,'images/manual/step-9/bg3.png'
                            //step-10
                            ,'images/manual/step-10/bg1.png'
                            ,'images/manual/step-10/bg2.png'
                            ,'images/manual/step-10/bg3.png'
                            //step-11
                            ,'images/manual/step-11/bg.jpg'
                            ,'images/manual/step-11/bg1.jpg'
                            ,'images/manual/step-11/bg2.jpg'
                            ,'images/manual/step-11/bg3.jpg'
                            ,'images/manual/step-11/bg4.jpg'
                            //step-12
                            ,'images/manual/step-12/bg.png'
                            //step-13
                            ,'images/manual/step-13/bg.png'
                            //page-control
                            ,'images/manual/side-bar.png'
                            ,'images/manual/side-button-blur.png'
                            ,'images/manual/page-control.png'
                            ];

            for (var i = 0; i < imgList.length; i++) {

                (function(s) {
                    setTimeout(function() {

                        me.loadImg(imgList[s], me.globaLoad, imgList.length);
                    }, 10 * s);
                })(i);
            }
        },

		scaleImg: function(height){
            var me = this,
                imgList = [
                ['.step-one .plane', 0],
                ['.step-one .line', 0],
                ['.step-one .desc', 35],
                ['.step-one .process', 35],
                ['.step-2-title', 15],
                ['.step-three .computer', 15],
                ['.step-three .slogan', 15, 0],
                ['.step-three .mark-01', 0, 0],
                ['.step-three .mark-02', 0, 0],
                ['.step-three .mark-03', 0, 0],
                ['.step-three .mark-04', 0, 0],
                ['.step-five .slogan', 0, 0],
                ['.step-five .page-area', 0, 0]
            ],

            rules = [
                    [710, 0.85],
                    [690, 0.8],
                    [670, 0.7]
                    ];

            for (var i = rules.length - 1; i >= 0; i--) {

                //如果小于规定的高度
                if(height < rules[i][0]){

                    $('img').not('.bg-ie').each(function(index){
                        var doms = $(this),
                            src = this.src,
                            container = doms.parents('.cycle-slideshow').eq(0),
                            parent = doms.parent();

                        if(parent.hasClass('item')){

                            me.loadImg(src, function(num, img){
                                
                                if(!container.hasClass('step-2-slide')){
                                    doms.css({
                                        'width': img.width * rules[i][1],
                                        'height': img.height * rules[i][1],
                                        'margin-top': (parent.outerHeight() - img.height * rules[i][1]) / 2
                                    });
                                }else{
                                    doms.css({
                                        'width': img.width * rules[i][1],
                                        'height': img.height * rules[i][1]
                                    });
                                }

                            });
                        }
                    });

                    if(me.isSupport()){
                        //切换图初始化
                        $('.cycle-slideshow').cycle();
                    }

                    /*for (var j = 0; j < imgList.length; j++) {
                        
                        (function(s) {
                            var currentDom = $(imgList[s][0]),
                                backgroud = currentDom.css('background-image').match(/url\(.*?\)/),
                                src = backgroud[0].replace(/url[(]|[)]|"/g, ''),
                                imgDom = $('<img src='+src+'>');

                            currentDom.css('background','none');

                            me.loadImg(src, function(num, img){
                                 
                                imgDom.css({
                                    'height': '100%',
                                    'display':'block'
                                });

                                $(imgList[s][0]).append(imgDom);
                            }, '123');
                           
                        })(j);
                    }*/

                    break;
                }
            };           
        },
		
        delaySetTime: function(fn, time){
            var me = this;

            setTimeout(function(){
                fn && fn.call(me);
            }, time * 1000);
        },

       //头屏进度条
        //判断图片加载
        loadImg: function(src, callback, num) {
            var me = this,
                img = new Image();
                img.src = global_path_url+'/'+src;

            if (img.complete || img.width) {
                callback.call(me, num, img);
                return;
            }

            function get() {

                if (img.complete) {
                    callback.call(me, num, img);
                    //循环求值
                    if (getTimer) {
                        clearInterval(getTimer);
                        getTimer = null;
                    }
                } else if (img.error) {

                   callback.call(me, num, img);
                }
            }

            var getTimer = setInterval(get, 100);
        },

        getFullStep: function() {

        },

        getCurrentStep: function() {

        },

        setAnimateDom: function($dom){
            var me = this;

            currentAnimateDom = $dom;
        },

        getAnimateDom: function() {
            var me = this;

            return currentAnimateDom;
        },

        pageCotrol: function(step){
            var me = this,
                step = step || 0,
                pageDom = $('#J-page-control li');

            pageDom.removeClass('current');    
            pageDom.eq(step).addClass('current');
        },

		  //导航切换CURRENT样式
        headerControl: function(step){
            var me = this;
                step = step || 0,
                pageDom = $('nav li'),
                currentDom = '';


            switch(step){
                case 5:
                    step = 4;
                break;
                case 6:
                    step = 6;
                break;
                case 7:
                    step = 6;
                break;
                case 8:
                    step = 6;
                break;
                case 9:
                    step = 6;
                break;
            }

            currentDom = pageDom.find('a[data-stepnum="'+step+'"]')
            pageDom.removeClass('current');   
            currentDom.parent().addClass('current'); 
        },
		
		
        //控制当前步骤
        controlStep: function(step) {
            var me = this,
                domWarp = domParent.children('section'),
                current = domWarp.eq(step) || '',
                winHeight = $(window).height(),
                prevDom = current.prev('section'),
                nextDom = current.next('section');

            //记录上次编号
            forwardStep = currentStep;
            //记录当前
            currentStep = step;        

            if (callbackFn[step] && typeof callbackFn[step] == "function") {
                callbackFn[step].call(me, step, current);
            }

            $.each(domWarp, function(index, val) {
                if (index != step && blurFn[index] && typeof blurFn[index] == "function") {
                    blurFn[index].call(me, step, current);
                }
            });
        }
    });

    var delay = 0;
    //jquery 3D
    $.fn.translate3d = function(translations, speed, easing, complete) {
        var opt = $.speed(speed, easing, complete);
        opt.easing = opt.easing || 'ease';
        translations = $.extend({
            x: 0,
            y: 0,
            z: 0
        }, translations);

        if(animateExample.isSupport()){        
            this.each(function(){
                var me = $(this);
                me.animate({
                    'top': translations.y
                }, opt.duration, function(){
                    opt.complete();
                });
            });

            return;
        }

        return this.each(function() {
            var $this = $(this);

            $this.css({
                transitionDuration: opt.duration + 'ms',
                transitionTimingFunction: opt.easing,
                transform: 'translate3d(' + translations.x + 'px, ' + translations.y + 'px, ' + translations.z + 'px)'
            });

            setTimeout(function() {
                $this.css({
                    transitionDuration: '0s',
                    transitionTimingFunction: 'ease'
                });

                opt.complete();
            }, opt.duration + (delay || 0));
        });
    };

    //初始化动画
    var animateExample = new animate();

    /*动画控制*/
    (function(){
            //第一页
			callbackFn[0] = function(step, dom) {
				var me = this;
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				$('header').addClass('shrink');
				me.controlDomHide(0);
				setTimeout(function() {
					dom.find(".slogan").addClass('currentBall');
					
				}, 3000);
			};
		
			blurFn[0] = function(step, dom) {
					$('header').removeClass('shrink');
			};
			

			//第二页
			callbackFn[1] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				 dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(1);
			};
			//第三页
			callbackFn[2] = function(step, dom) {
				var me = this;
				me.changeNavStyle('black');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(2);
			}
			//第四页
			callbackFn[3] = function(step, dom) {
				var me = this;
				me.changeNavStyle('black');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(3);
			}
			//第五页
			callbackFn[4] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(4);
			}
			//第六页
			callbackFn[5] = function(step, dom) {
				var me = this;
				me.changeNavStyle('black');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(5);
			}
			//第七页
			callbackFn[6] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(6);
			}
			//第八页
			callbackFn[7] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(7);
			}
			//第九页
			callbackFn[8] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(8);
			}
			//第十页
			callbackFn[9] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(9);
			}
			//第十一页
			callbackFn[10] = function(step, dom) {
				var me = this;
				me.changeNavStyle('white');
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(10);
			}
			//第十二页
			callbackFn[11] = function(step, dom) {
				var me = this;
				me.changeNavStyle('black');
				
				dom.removeClass(delStyle);
                me.delaySetTime(function(){
                    dom.addClass('current')
                }, 0);
				me.controlDomHide(11);

				if (dom.offset().top == -546) {
					dom.translate3d({
						y: 0
					}, transTime * 1000);
				}

				if (forwardStep == 2) {
					setTimeout(function() {
						dom.find('.step-two-one').css({
							'height': 0,
							'overflow': 'hidden'
						});
					}, 500);
				} else {
					dom.find('.step-two-one').css({
						'height': 0,
						'overflow': 'hidden'
					});
				}
			};

			blurFn[11] = function(step, dom) {
				var me = this,
					dom = animateDomList.eq(1);
				if (step != 2) {
					dom.find('.step-two-one').css({
						'height': 0
					});
				}
			};
			
			
			
			callbackFn[12] = function(step, dom) {
				var me = this;
				me.changeNavStyle('black');
				dom = animateDomList.eq(11);
				dom.addClass('current').removeClass(delStyle);
				me.controlDomHide(11);
				dom.translate3d({
					y: -546
				}, transTime * 1000);
				dom.find('.step-two-one').css({
					'height': 546,
					'overflow': 'inherit'
				});
			}

			blurFn[12] = function(step, dom) {
				var me = this,
					dom = animateDomList.eq(11);
				if (step != 1) {
					dom.find('.step-two-one').css({
						'height': 0,
						'overflow': 'hidden'
					});
				}
			};
			
			//第十三页
			callbackFn[13] = function(step, dom) {
				var me = this;
				me.changeNavStyle('black');
				dom = animateDomList.eq(12);
				dom.removeClass(delStyle);
				//防止动画不显示
				setTimeout(function() {
					dom.addClass('current');
				}, 0);
				me.controlDomHide(12);
			}
    })();

    animateExample.loadProcess();
})(jQuery);