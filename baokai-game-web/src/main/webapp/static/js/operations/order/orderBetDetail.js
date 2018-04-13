$(function(){
	
	// �m�t��?�A�s�W
	new phoenix.Tab({
		par : '.lottery-switcher',
		triggers : '.lottery-tabs > a',
		panels : '.lottery-switch',
		eventType : 'click',
		currPanelClass: 'lottery-switch-current'
	});
	var markup = 
		'<div class="wanfa-pop pop">' +
			'<div class="hd">' +
		        // '<span class="wanfa_caret"><span></span></span>' +
		        '<span class="title">玩法金額分布</span>' +
		        '<i data-action="close" class="close closeBtn"></i>' +
		    '</div>' +
		    '<div class="bd">' +
				'<div class="wanfa-replace"></div>' +
				'<div class="wanfa-loading"><span>加載中...</span></div>' +
		    '</div>' +
		    '<a data-action="close" href="javascript:void(0);" class="btn">關閉<b class="btn-inner"></b></a>' +
		'</div>';
	var $wanfan = $(markup).css({
		width: '90%',
		height: '90%',
		left: '5%',
		top: '5%',
		position: 'fixed',
		overflowY: 'auto',
		zIndex: 100
	}).fadeOut(0).appendTo('body');
	if(slip){
		$('.col-side .nav dd:eq(4)').removeAttr("class","current");
		$('.col-side .nav dd:eq(6)').attr("class","current");
	}

	if( typeof document.body.style.maxHeight == 'undefined' ){
		$wanfan.css('position', 'absolute');
	}

	//if($('#webIssueCode').val()==0){

	//	$('#J-button-betDetail').hide();
	//}
	$('#J-select-lotteryid').change(function() {
     if(($('#J-select-lotteryid').val() == '99602' || $('#J-select-lotteryid').val() == '99603') && $('#webIssueCode').val() != 0){
		 $('#J-button-betDetail').show();
	 }else{
		//  $('#J-button-betDetail').hide();
	 }
    });
	
	$('#webIssueCode').change(function() {
     if(($('#J-select-lotteryid').val() == '99602' || $('#J-select-lotteryid').val() == '99603') && $('#webIssueCode').val() != 0){
		 $('#J-button-betDetail').show();
	 }else{
		//  $('#J-button-betDetail').hide();
	 }
    });
	
	var $wanfaReplace = $wanfan.find('.wanfa-replace'),
		$wanfaLoading = $wanfan.find('.wanfa-loading').fadeOut(0),
		ajaxurl = baseUrl+"/gameoa/queryBetDetaiByIssudeCode",
		wanfanOffset = {left: 335, top: 10};
	$('#J-button-betDetail').on('click', function(){
		var issueCodeValue;
		if($('#webIssueCode').val() == 0  && $('#webIssueCodeValue').val() != ''){
			issueCodeValue= $('#webIssueCodeValue').val();
		}else {
			issueCodeValue=$('#webIssueCode').val();
		}
		if($('#webIssueCode').val() == 0  && $('#webIssueCodeValue').val() == 0){
			alert('請選擇獎期');
			return false;
		}
		if($('#J-select-lotteryid').val() != '99602' && $('#J-select-lotteryid').val() != '99603' ){
			alert('此功能僅提供吉利骰寶');
			return false;
		}
		var $t = $(this), wanfa = $t.data('wanfa');

		var wanfaData = $t.data('wanfadata'),value=$t.data('value') ,issueCode=issueCodeValue ,selectLotteryId = $('#J-select-lotteryid').val();
		if( $wanfan.is(':hidden') ){
			$wanfan.fadeIn();
		}

		$wanfaReplace.animate({opacity: 0.4});
		$wanfaLoading.fadeIn('fast');
		$.post(ajaxurl, {issueCode: issueCode ,lotteryId:selectLotteryId}, function(resp){
				//resp = $.parseJSON(resp);
				if( resp.status == 'ok' ){
					loadWanfa( resp.html, $t );
				}
			});
		
		return false;
	});
	
	function loadWanfa( resp, $t ){
		$wanfaReplace.animate({opacity: 1});
		$wanfaLoading.fadeOut('fast');
		// replace html
		$wanfaReplace.html(resp);
		$t.data('wanfaData', resp);
		// remove onhandled Class
		$t.removeClass('onhandled');
	}
	
	// Close trigger
	$wanfan.find('[data-action="close"]').on('click', function(){
		$wanfan.fadeOut();
		return false;
	});

	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		Idivdok.style.zIndex =2000;			
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
    }
	
});