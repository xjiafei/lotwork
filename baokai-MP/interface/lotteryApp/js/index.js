
$(function(){
	// Inview
	// https://github.com/protonet/jquery.inview
	$('.intro, .example').on('inview', function(event, isInView){
		var $this = $(this);
		if( isInView ){
			$this.addClass('inview');
			var $number = $this.find('.ui_animate_number');
			if( $number.length && !$number.hasClass('done') ){
				animateNumber($number);
				$number.addClass('done');
			}
		}else{
			// $this.removeClass('inview');
			$this.off('inview');
		}
	});

	
	function animateNumber($number){
		// 必须是6位数
		var start = Math.round( Math.random() * 400000 + 100000 );
		// 保证end的值不会大于6位数
		var end = start + Math.round( Math.random() * 400000 + 100000 );
		$number.prop('number', start)
			.animateNumber({
				number: end
			}, 5000);
	}
	// animateNumber();
	// var numberTimer = setInterval(animateNumber, 4000);
	

	/*var $dialogs = $('.dialog');
	var top1 = -600, top2 = 150;
	// init
	$dialogs.animate({
		top: top1,
		opacity: 0
	}, 0);

	$('#main .buttons a').on('click', function(){
		var idx = $(this).index();
		$dialogs.filter(':visible').stop().animate({
			top: top1,
			opacity: 0
		}).end().eq(idx).animate({
			top: top2,
			opacity: .98
		});
		return false;
	});

	$dialogs.each(function(){
		$(this).find('.dialog_title a').on('click', function(){
			if( $(this).hasClass('active') ) return false;
			var idx = $(this).index();
			$(this).addClass('active').siblings('.active').removeClass('active');
			var $panels = $(this).parent().siblings('.dialog_body').find('.panel');
			$panels.filter(':visible').hide(0).end()
					.eq(idx).show();
			return false;
		}).eq(0).trigger('click');
	});

	$dialogs.find('[data-action="close"]').on('click', function(){
		$(this).parents('.dialog').eq(0).stop().animate({
			top: top1,
			opacity: 0
		});
		return false;
	});*/

	// 飘窗广告
	/*var $click_me = $('<div id="click_me"> \
						<a href="http://www.fenghuang158.com/newuser/" target="_blank"> \
							<img src="images/clickme.gif" alt=""> \
						</a> \
						<span class="closeme">&times;</span> \
					</div>');
	$click_me.css('display', 'block').animate({
		opacity: 1
	}, 600).find('.closeme').on('click', function(){
		$('#click_me').fadeOut();
	}).end().appendTo('body');*/
});