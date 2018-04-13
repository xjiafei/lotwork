$(function(){
	
	// 彩系切换，新增
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
		        '<span class="title">详细玩法</span>' +
		        '<i data-action="close" class="close closeBtn">&times;</i>' +
		    '</div>' +
		    '<div class="bd">' +
				'<div class="wanfa-replace"></div>' +
				'<div class="wanfa-loading"><span>加载中...</span></div>' +
		    '</div>' +
		    '<input type="button" data-action="modify" class="btn btn-important" style="display:none;" value="修&nbsp;&nbsp;改" />&nbsp;&nbsp;&nbsp;&nbsp;' +
		    '<a data-action="close" href="javascript:void(0);" class="btn">关&nbsp;&nbsp;闭<b class="btn-inner"></b></a>' +
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

	if( typeof document.body.style.maxHeight == 'undefined' ){
		$wanfan.css('position', 'absolute');
	}

	if(baseUrl ==''){
		baseUrl= baseUrl+"/applycenter/queryusergameaward";
	}
	var $wanfaReplace = $wanfan.find('.wanfa-replace'),
		$wanfaLoading = $wanfan.find('.wanfa-loading').fadeOut(0),
		// ajax请求链接
		//ajaxurl = baseUrl+"/gameUserCenter/queryUserGameAward",
		ajaxurl = baseUrl,
		// 弹出层三角距left、top偏移量
		// 其值分别为：弹出层宽度的1/2、三角的高度
		wanfanOffset = {left: 335, top: 10};
	$('.label-like[data-wanfa]').on('click', function(){
		var $t = $(this), wanfa = $t.data('wanfa'),lotteryId = $t.data('lottery'),lotterySeriesCode = $t.data('lotteryseriescode'),awardGroupId=$t.data('awardgroup'),modifyprize=$t.data('modifyprize'),retstatus=$t.data('retstatus');
		if(modifyprize == '1'){
			$wanfan.find('[data-action="modify"]').show();
		} else {
			$wanfan.find('[data-action="modify"]').hide();
		}
		// 如果没有wanfa或者当前数据正在交互中，返回false
		if( !wanfa || $t.hasClass('onhandled') ) return false;

		$t.addClass('onhandled');

		// var offset = $t.offset();
		// var fix = {left: $t.outerWidth()*.75, top: $t.outerHeight()};
		// 获取玩法的数据，如果有
		var wanfaData = $t.data('wanfadata'),value=$t.data('value');
		// $wanfan.css({
		// 	left: offset.left + fix.left - wanfanOffset.left,
		// 	top: offset.top + fix.top + wanfanOffset.top
		// });
		if( $wanfan.is(':hidden') ){
			$wanfan.fadeIn();
		}

		// show loading
		$wanfaReplace.animate({opacity: 0.4});
		$wanfaLoading.fadeIn('fast');
		if(lotteryId == 88101 || lotteryId == 88102){
			var url =  location.search.split('&');
            var name = url[1].split('=');
			querySubWanfa(lotteryId,name[1],$t);
			return false
		}
		if( wanfaData!=1 ){
			loadWanfa( wanfaData, $t );
		}else{
			$.post(ajaxurl, {awardType: wanfa,type:$("#type").val(),lotteryId:lotteryId,lotterySeriesCode:lotterySeriesCode,sysAwardGroupId:awardGroupId,retValue:value,modifyprize:modifyprize,retstatus:retstatus,userId:$("#userId").val(),retType:$("#retType").val()}, function(resp){
				//resp = $.parseJSON(resp);
				if( resp.status == 'ok' ){
					loadWanfa( resp.html, $t );
				}
			});
		}
		return false;
	});
	
	function loadWanfa( resp, $t ){
		// setTimeout模拟ajax的loading效果，
		// 请去掉
		// hide loading
		$wanfaReplace.animate({opacity: 1});
		$wanfaLoading.fadeOut('fast');
		// replace html
		$wanfaReplace.html(resp);
		// 缓存数据
		$t.data('wanfaData', resp);
		// remove onhandled Class
		$t.removeClass('onhandled');
	}
	
	function GetInputPostText(){
		var sText="";
		var sPost="";
		var userId =$("#userId").val();
		var lotteryId =$("#data-lotteryId").val();
		var awardGroupId =$("#data-awardGroupId").val();
		var lotterySeriesCode =$("#data-lotterySeriesCode").val();
		var retstatus =$("#data-retstatus").val();
		var inputType ="form[name='modifyBonusForm'] input";
		$(inputType).each(function(index, element) {
			sText="bonusId[]="+$(element).attr("name")+"&";
			sText+="bonusValue[]="+$(element).val()+"&";
			 sPost+=sText;
        });
		sPost += 'id='+userId+"&"+'lotteryId='+lotteryId+"&"+'awardGroupId='+awardGroupId+"&"+'retstatus='+retstatus+"&"+'lotterySeriesCode='+lotterySeriesCode;
		return sPost;
	}

	// Close trigger
	$wanfan.find('[data-action="close"]').on('click', function(){
		$wanfan.fadeOut();
		return false;
	});
	$wanfan.find('[data-action="modify"]').on('click', function(){
		var sdata=GetInputPostText();
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/applycenter/modifybonusprize/',
			//按格式传入需要参数
			data:sdata,	
			beforeSend:function(){
				isLoading = true;
				//禁用发送								
				var list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
					interval=setInterval(function(){
						$wanfan.find('[data-action="modify"]').val(list[i]);
						i += 1;
						if(i >= list.length){
							i = 0;
						}
					}, 300);
					$wanfan.find('[data-action="modify"]').attr("disabled","disabled");
			},
			//返回生成链接数,各种提示						
			success:function(data){	
				
				if(data["status"]=="ok"){
					$("#divOperatSuccess h4").html(data['data']);
					fn("divOperatSuccess");
					setTimeout(function(){
						$('#divOperatSuccess').css("display","none");
						$wanfan.fadeOut();
						window.location.reload();
					},3000);
					
				} else {
					$("#divOperatFailure h4").html(data['data']);
					fn("divOperatFailure");
					setTimeout(function(){$('#divOperatFailure').css("display","none");},3000);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			},
			complete:function(){
				isLoading = false;
				clearInterval(interval);
				$wanfan.find('[data-action="modify"]').val("修 改");						 	
				$wanfan.find('[data-action="modify"]').removeAttr("disabled","disabled");
			}
		});
		return false;
	});

	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";			
		Idivdok.style.zIndex =2000;			
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";	
    }
	
	
	function querySubWanfa(lotteryId,name,g){
		var dt = 'lotteryId='+lotteryId;
		dt += '&account='+name;
		$.ajax({
			type:'post',
			dataType:'json',
			cache:false,
			url:'/user/sub/queryGameInfo/',
			//按格式传入需要参数
			data:dt,	
			beforeSend:function(){
			},
			//返回生成链接数,各种提示						
			success:function(data){	
				var html = "";
				if(data.body.result.retCode=="wgRetList"){
					var wgItem = data.body.result.wgRetList;
					
					html += '<div id="wgRetList" >';
					html += '<table class="table table-border">';
					html += '<thead>';
					html += '<tr>';
					html += '<th>游戏名称</th>';				
					html += '<th>返点</th>';
					html += '</tr>';
					html += '</thead>';
					html += '<tbody>';
					
					
					$.each(wgItem,function(k,v){
						html += '<tr>';
						html += '<td>'+this.gameName+'</td>';
						html += '<td>'+this.ret/100+'</td>';
						html += '</tr>';
					});
					
					html += '</tbody>';
					html += '</table>';
					html += '</div>';
					
					loadWanfa(html,g);
				} else if(data.body.result.retCode=="fhxRetList") {
					var fhxItem = data.body.result.fhxRetList;
					
					html += '<div id="wgRetList" >';
					html += '<table class="table table-border">';
					html += '<thead>';
					html += '<tr>';
					html += '<th>游戏名称</th>';				
					html += '<th>返点</th>';
					html += '</tr>';
					html += '</thead>';
					html += '<tbody>';
					
					
					$.each(fhxItem,function(k,v){
						html += '<tr>';
						html += '<td>'+this.gameName+'</td>';
						if(this.ret/100 ==0){
							html += '<td>0.0%</td>';
						}else{
							html += '<td>'+this.ret/100+'%</td>';
						}
						html += '</tr>';
					});
					
					html += '</tbody>';
					html += '</table>';
					html += '</div>';
					
					loadWanfa(html,g);
				}else{
					
					html += '<div id="wgRetList" >';
					html += '此玩家未登入X平台';
					html += '</div>';
					
					loadWanfa(html,g);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
			},
			complete:function(){
				isLoading = false;
				//clearInterval(interval);
				$wanfan.find('[data-action="modify"]').val("修 改");						 	
				$wanfan.find('[data-action="modify"]').removeAttr("disabled","disabled");
			}
		});
	}
	
});