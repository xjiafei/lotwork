(function($){
	$('.ico-tab').css('margin-right','4px');
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(1)').attr("class","current");
	
	

	//多选框跳转页面
	$('#J-checkbox-switch').find('input[type="checkbox"]').click(function(){
		if(this.checked){
//			location.href = this.getAttribute('data-value1');
			var _value = $(this).attr('value');
			var _v = $('#issueType').val();
			
			$('#issueType').attr('value', parseInt(_value,10)+parseInt(_v,10));
			
			$('#J-form').submit();
			
		}else{
			var _v = $('#issueType').val();
			var _c = 0;
			if(parseInt(_v,10) >=3 ){
				var _value = $(this).attr('value');
				_c = parseInt(_v,10)-parseInt(_value,10);
			}
			$('#issueType').attr('value',_c);
            $('#J-form').submit();
		}
	});

//	//点击彩种
//	$('a[id=lottery]').each(function(e){
//		
//		$(this).click(function(e){
//			var _name = $(this).attr('name');
//			$('#lotteryId').attr('value',_name);
//			
////			location.href =  window.location.pathname;
//			$('#J-form').submit();
//		});
//	});
	
	$('a[id=dates]').each(function(e){
		$(this).click(function(e){
			var _name = $(this).attr('name');
			$('#dateType').attr('value',_name);
			$('#J-form').submit();
		});
	});
	
	
	
	
	//日期
	$('a[id=dates]').each(function(){
		
		var _name = $(this).attr('name');
		var dateType = $('#dateType').val();
		
		if(_name == dateType){
			$(this).parent().removeClass().addClass('ico-tab ico-tab-current');
		}
	});
	
	//类型
	var issueType = $('#issueType').val();
    if(issueType == 0) {
        $('#checkbox-a').attr('checked', false);
        $('#checkbox-b').attr('checked', false);
    }
	if(issueType == 1){
		$('#checkbox-a').attr('checked', true);
		$('#checkbox-b').attr('checked', false);
		setTimeout(function(){
			$('#J-form').submit();
		},5000);
	}else if(issueType ==2){
		$('#checkbox-b').attr('checked', true);
		$('#checkbox-a').attr('checked', false);
	}else if(issueType == 3){
		setTimeout(function(){
			$('#J-form').submit();
		},5000);
		$('#checkbox-a').attr('checked', true);
		$('#checkbox-b').attr('checked', true);
	}
	
	var Tip = phoenix.Tip.getInstance();
	//乐透期数提示
	$('#J-lotterys-panel').on('mouseover', '.ico-tab-item', function(){
		Tip.setText(this.getAttribute('data-number'));
		Tip.show(-14, Tip.dom.height()*-1-14, this);
	}).on('mouseout', '.ico-tab-item', function(){
		Tip.hide();
	});
	
	
	$('#J-data-table').on('click', '.tip-detail', function(e){
		var dom = $(this);
		Tip.setText(dom.parent().find('.tip-hidden').text());
		Tip.show(dom.width()*-1-10, dom.height()*-1-20, dom.parent());
	}).on('mouseout', '.tip-detail', function(){
		Tip.hide();
	});
	
	//table行选中跳入明细
	//$('table tr').click(function(){		
	//	
	//	var tt = $(this).attr("id");
	//	var v = tt.split("_");
	//	var url = baseUrl + "/gameoa/queryLotteryIssueWarnDetail?lotteryId="+v[0]+"&issueCode="+v[1];
	//	location.replace(url); 
	//});
	
	$('a[id=tipDetail]').each(function(e){
		
		$(this).click(function(){
			var tt = $(this).attr("name");
			var v = tt.split("_");
			var url = baseUrl + "/gameoa/queryLotteryIssueWarnDetail?lotteryId="+v[0]+"&issueCode="+v[1];
			location.replace(url); 
		});
	});
	
	$("table tr").hover(function(){$(this).addClass("tr_hover")},function(){ $(this).removeClass("tr_hover")});
	 
	var _result =jQuery.parseJSON($('#result').val());
	 
	for(var i = 0; i < _result.length; i++){
			
		var _lotteryId = _result[i].lotteryid;
		var _webIssueCode = _result[i].webIssueCode;
		var _status = _result[i].status;
		
		$('#'+_lotteryId).attr('data-number',_webIssueCode+"期");
		$('#'+_lotteryId).removeClass().addClass('ico-tab ico-tab-item ico-tab-current');
		if(_status == 2){
			$('#'+_lotteryId).append('<i class="ico-abnormal"></i>');
		}
			
	}
		
	
	//彩种样式及给值_gameType
	$('._gameType').each(function(){
		
		//第一次加载样式
		$(this).parent().removeClass('ico-tab-current');
		var _name = $(this).parent().attr('id');
		var lotteryId = $('#lotteryId').val() == 'undefined' ? '0' : $('#lotteryId').val();
		
		if(_name == 'undefined' ){
			$(this).parent().removeClass('ico-tab-current');
			return true;				
		}else if(_name == lotteryId ){			
			$(this).parent().removeClass('ico-tab-current').addClass('ico-tab-current');
		}else if(lotteryId == '0'){ //全部彩种名称选中
			$('#0').removeClass('ico-tab-current').addClass('ico-tab-current');
		}
	}); 
	
	//选中加载给<a>加name值 post数据
	$('._gameType').click(function(){	
		
		var _name = $(this).parent().attr('id');				
		$('#lotteryId').val(_name);
		$(this).attr('name',_name);
		$(this).parent().removeClass('ico-tab-current').addClass('ico-tab-current');
		$('#J-form').submit();
	});	
	
	setTimeout(function (){$('#J-form').submit();	},60*1000);
	
	
	
})(jQuery);