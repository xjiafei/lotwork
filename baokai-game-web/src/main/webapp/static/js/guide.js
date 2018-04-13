;(function($){
    function F(t, o){
        this.opts = $.extend({
        	start     : true, // 是否有开始帧
        	end       : true, // 是否有结束帧
        	shadowImg : 'shadow.jpg', // 遮罩层背景图
        	imgPath   : 'images/', // 图片路径
        	imgPrefix : 'guide_', // 图片前缀名
        	imgFormat : '.png',
        	guideClass: 'guides_element',
            ajaxUrl   : 'guide.php', // Ajax 请求url

        	// 图片文件名{start_img, guides: [img1, img2, ..], end_img}
        	// 如果对象为空，则按照{'guide_start.png', ['guide_1.png', 'guide_1.png', ..], 'guide_end.png'}计算
        	imgs      : null,
        	
        	// 图片引导元素属性
        	// [width, height, marginleft, top],水平方向以页面中心为起点
        	styles    : {
        		'start': [400,210,-198,396],
        		'guides': [
        			[650,370,-507,0],
        			[430,370,-193,0],
        			[690,385,-189,128],
        			[950,350,-499,511],
        			[950,345,-499,714],
        			[950,375,-499,956],
        			[380,425,263,514],
        			[325,430,317,510],
        			[440,265,-503,1230]
        		],
        		'end': [400,250,-198,396]
        	},
        	scrollTops: {
        		'start': 240,
        		'guides': [0,0,0,380,640,900,280,280,1200],
        		'end': 240
        	}, // 切换到当前帧后，页面滚动距离
        	topFix : 0, // 图片定位top修正值，考虑页面会有额外的层导致页面整体结构下移，比如顶部的消息提示
        	buttons   : {
        		'start': [[120,38,114,144,'start']],
        		'guides':[
        			[
        				[42,42,330,260,'prev'], [42,42,590,278,'next']
        			],
        			[
        				[42,42,17,262,'prev'], [42,42,276,280,'next']
        			],
        			[
        				[42,42,13,134,'prev'], [42,42,273,154,'next']
        			],
        			[
        				[42,42,633,243,'prev'], [42,42,895,266,'next']
        			],
        			[
        				[42,42,633,43,'prev'], [42,42,895,62,'next']
        			],
        			[
        				[42,42,635,43,'prev'], [42,42,895,62,'next']
        			],
        			[
        				[42,42,66,318,'prev'], [42,42,326,338,'next']
        			],
        			[
        				[42,42,13,326,'prev'], [42,42,273,326,'next']
        			],
        			[
        				[42,42,124,44,'prev'], [42,42,384,63,'next']
        			]
        		],
        		'end': [
        			[142,38,112,146,'end'],
                    [38,38,70,146,'restart'],
                    [142,40,112,190,'link','/index/user']
        		]
        	}, // guide中的按钮[[width, height, left, top, name]]
        	debugs    : true // 是否支持debug [0/false, 1=>对象级输出, 2=>字符串级输出]
        }, o);  
        this.slides = [];
        this.$t = $(t);
        this.debugs = this.opts.debugs;
        // this.isIE6 = $.browser.msie && parseFloat($.browser.version) < 7;
        this.init();
    }

    F.prototype = {
        init: function(){
            this.createGuide();
            this.keyEnabled = false; // 键盘事件
            this.guideIdx = -1;
            this.guideMax = this.opts.styles['guides'].length;
            this.$guides = $('.'+this.opts.guideClass);

            this.slide('init');

            this.bindEvent();
        }
        , createGuide: function(){
        	var me = this, opts = me.opts;

        	// create back shadow
        	me.$shadow = $('<div>').css({
        		width: '100%',
        		height: 1546,
        		position: 'absolute',
        		left: 0,
        		top: opts.topFix,
        		background: '#11141d url(' +opts.imgPath + 'shadow.jpg' +　') center top no-repeat',
        		zIndex: 99998
        	}).fadeIn().appendTo('body');

        	// START
        	if( opts.start ){
        		var style = opts.styles['start'];
        		me.$start = me.createGuideElem(style);

        		// img
        		if( opts.imgs && opts.imgs['start'] ){
        			var img = opts.imgPath + opts.imgs['start'];
        		}else{
        			var img = opts.imgPath + (opts.imgPrefix ? opts.imgPrefix : '') + 'start' + opts.imgFormat;	
        		}        		
        		me.$start.append('<img src="' +img+ '">');

        		// buttons
        		$.each(opts.buttons['start'], function(i, v){
        			me.createGuideButton(v).appendTo(me.$start);
        		});
        		me.$start.appendTo('body');
        	}

        	// END
        	if( opts.end ){
        		var style = opts.styles['end'];
        		me.$end = me.createGuideElem(style);

        		// img
        		if( opts.imgs && opts.imgs['end'] ){
        			var img = opts.imgPath + opts.imgs['end'];
        		}else{
        			var img = opts.imgPath + (opts.imgPrefix ? opts.imgPrefix : '') + 'end' + opts.imgFormat;	
        		}        		
        		me.$end.append('<img src="' +img+ '">');

        		// buttons
        		$.each(opts.buttons['end'], function(i, v){
        			me.createGuideButton(v).appendTo(me.$end);
        		});
        		me.$end.appendTo('body');
        	}

        	// GUIDES
        	var guide_styles = opts.styles['guides'];
        	$.each(guide_styles, function(i, style){
        		var $guide = me.createGuideElem(style);     		

        		// img
        		if( opts.imgs && opts.imgs['guide'] && opts.imgs['guide'][i] ){
        			var img = opts.imgPath + opts.imgs['guide'][i];
        		}else{
        			var img = opts.imgPath + (opts.imgPrefix ? opts.imgPrefix : '') + (i+1) + opts.imgFormat;	
        		}        		
        		$guide.append('<img src="' +img+ '">');

        		// buttons
        		$.each(opts.buttons['guides'][i], function(k, v){
        			me.createGuideButton(v).appendTo($guide);
        		});
        		me.slides.push($guide);
        		$guide.appendTo('body');
        	});
        }
        , createGuideElem: function(style){
        	var me = this;
        	return $('<div>').css({
	        			width: style[0],
	        			height: style[1],
	        			marginLeft: style[2],
	        			top: style[3] + me.opts.topFix,
	        			left: '50%',
	        			position: 'absolute',
	        			display: 'none',
	        			zIndex: 99999
	        		}).fadeOut().addClass(me.opts.guideClass);
        }
        , createGuideButton: function(button){
            var type = button[4] || '',
                css = {
                    width: button[0],
                    height: button[1],
                    left: button[2],
                    top: button[3],
                    position: 'absolute',
                    cursor: 'pointer',
                    textIndent: '-9999em',
                    background: '#fff',
                    opacity: 0
                };
            if( type && type == 'link' ){
                var $html = $('<a>', {
                    css: css,
                    href: button[5] || '',
                    text: type,
                    target: 'blank'
                });
            }else{
                var $html = $('<span>', {
                    css: css,
                    text: type,
                    'data-action': type
                });
            }
        	return $html;
        }
        , completeRequest: function(){
            var me = this;
            $.post( me.opts.ajaxUrl );
        }
        , slide: function(action){
        	var me = this, stops = me.opts.scrollTops;
        	
        	if( !action || action == '' ) return false;

        	me.keyEnabled = false;
        	me.$guides.filter(':visible').fadeOut();

        	// 开始播放，第一帧
        	if( action == 'start' || action == 'restart' ){
        		me.showSlide('start');                
        	}
        	// 结束播放，显示真实页面
        	else if( action == 'end' ){
        		me.$shadow.fadeOut().remove();
        		me.$guides.remove();
        		me.keyEnabled = false;
                me.completeRequest();
        	}
        	// 准备播放，显示初始帧
        	else if( action == 'init' ){
        		me.showSlide('init');
        		me.keyEnabled = true;
        	}
        	else if( action == 'prev' || action == 'next' ){
        		var idx = me.guideIdx;
        		idx = ( action == 'prev' ) ? (idx - 1) : (idx + 1);

        		if( idx < 0 ){
        			me.showSlide('init');
        		}else if( idx >= me.guideMax ){
        			me.showSlide('end');
        		}else{
        			me.showSlide(idx);
        		}
        	}else{
                return false;
            }
        }
        , showSlide: function(type){
        	var me = this, stops = me.opts.scrollTops;
        	
        	me.$guides.filter(':visible').fadeOut();

        	if( type == 'start' ){
        		me.scrollTop(stops['guides'][0], function(){
        			me.slides[0].fadeIn();
        			me.guideIdx = 0;
        		});
        	}else if( type == 'end' ){
        		me.scrollTop(stops['end'], function(){
        			me.$end.fadeIn();
        			me.guideIdx = me.guideMax;
        		});
        	}else if( type == 'init' ){
        		me.scrollTop(stops['start'], function(){
        			me.$start.fadeIn();
	        		me.guideIdx = -1;
        		});
        	}else{
        		me.scrollTop(stops['guides'][type], function(){
        			me.slides[type].fadeIn();
        			me.guideIdx = type;
        		});
        	}
        }
        , scrollTop: function(top, callback){
        	var me = this, top = top || 0;
        	$('html, body').animate({
        		scrollTop: top
        	}, function(){
        		if( callback && Object.prototype.toString.call(callback) === '[object Function]' ){
        			callback();
        		}
        		me.keyEnabled = true;
        	});
        }
        , bindEvent: function(){
        	var me = this;
        	me.$guides.find('[data-action]').on('click', function(){
        		me.slide($(this).data('action'));
        	});
        	$(document).on('keydown', function(event){
        		if( me.keyEnabled ){
        			// prev
			        if( me.guideIdx >= 0 && (event.keyCode == 37 || event.keyCode == 38) ) {
			            me.slide('prev');
			        }
			        // next
			        else if( me.guideIdx < me.guideMax && (event.keyCode == 39 || event.keyCode == 40) ) {
			            me.slide('next');
			        }
			        // END / ESC
			        else if( event.keyCode == 27 ) {
			            me.slide('end');
			        }else{
                        return false;
                    }
		    	}
		    })
        }
        // debug
        , debug: function(){      
            this.debugs && window.console && console.log && console.log( '[pageGuide] ' + Array.prototype.join.call(arguments, ' ') );
        }
    }

    $.fn.pageGuide = function(o) {    
        var instance;
        this.each(function() {              
            instance = $.data( this, 'pageGuide' );
            // instance = $(this).data( 'pageGuide' );
            if ( instance ) {
                // instance.init();         
            } else {
                instance = $.data( this, 'pageGuide', new F(this, o) );          
            }
        });
        return instance;
    }
    // $('body').pageGuide();
})(jQuery);