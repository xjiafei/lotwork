;(function($){
    function F(t, o){
        this.opts = $.extend({
        	start     : true, // 是否有开始帧
        	end       : true, // 是否有结束帧
        	shadowImg : 'shadow.jpg', // 遮罩层背景图
        	imgPath   : global_path_url + '/images/indexv2/guide/', // 图片路径
        	imgPrefix : 'guide_', // 图片前缀名
        	imgFormat : '.png',
        	guideClass: 'guides_element',

        	// 图片文件名{start_img, guides: [img1, img2, ..], end_img}
        	// 如果对象为空，则按照{'guide_start.png', ['guide_1.png', 'guide_1.png', ..], 'guide_end.png'}计算
        	imgs      : null,
        	
        	// 图片引导元素属性
        	// [width, height, marginleft, top],水平方向以页面中心为起点
        	styles    : {
        		'start': [404,336,-202,184],
        		'guides': [
					[611,394,-90,94],
					[376,367,-145,30],
					[350,294,-598,0],
					[476,275,128,0],
        			[945,336,-611,480],
        			[392,308,218,480],
        			[819,595,-402,446]
        		],
        		'end': [403,336,-201,184]
        	},
        	scrollTops: {
        		'start': 0,
        		'guides': [0,0,0,0,200,280,290],
        		'end': 0
        	}, // 切换到当前帧后，页面滚动距离
        	topFix : 0, // 图片定位top修正值，考虑页面会有额外的层导致页面整体结构下移，比如顶部的消息提示
						// [width, height, left, top],以guide图为参照
        	buttons   : {
        		'start': [[118,36,98,213,'start']],
        		'guides':[
					[
						[38,38,15,295,'prev'], [38,38,275,315,'next']
					],
					[
						[38,38,11,268,'prev'], [38,38,271,288,'next']
					],
					[
						[38,38,41,194,'prev'], [38,38,301,214,'next']
					],
					[
						[38,38,11,176,'prev'], [38,38,271,196,'next']
					],

        			[
        				[38,38,634,236,'prev'], [38,38,896,257,'next']
        			],
        			[
        				[38,38,11,209,'prev'], [38,38,271,229,'next']
        			],
        			[
        				[38,38,510,41,'prev'], [38,38,770,61,'next']
        			]
        		],
        		'end': [
        			[140,36,116,211,'end'],
        			[30,30,76,215,'restart']
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
			var bgsrc = "#11141d url(" + opts.imgPath + "shadow.jpg" +　") center top no-repeat";
        	// create back shadow
        	me.$shadow = $("<div>").css({
        		width: "100%",
        		height: 1246,
        		position: "absolute",
        		left: 0,
        		top: opts.topFix ,
        		background: bgsrc,
        		zIndex: 99998
        	}).fadeIn().appendTo("body");

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
        	return $('<span>', {
						css: {
							width: button[0],
		        			height: button[1],
		        			left: button[2],
		        			top: button[3],
		        			position: 'absolute',
		        			cursor: 'pointer',
		        			textIndent: '-9999em',
		        			background: '#fff',
		        			opacity: 0
						},
						text: button[4],
						'data-action': button[4]
					});
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
					//去除隐藏属性
					$("body").css({
						"height":"100%",
						"overflow":"initial"
					})
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
			console.log(top)
        	var me = this, top = top || 0;
        	$('body').animate({
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