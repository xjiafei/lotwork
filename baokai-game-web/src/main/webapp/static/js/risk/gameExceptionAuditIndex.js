(function($){
	$('.ico-tab').css('margin-right','4px');
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(4)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	
	var temp = $("#statusValue").val();
	$("#status").find("option[value=" + temp + "]").attr("selected", true);
	
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
		}
	});
	
	$('#status').change(function(){
		$('#J-form').submit();
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
	if(issueType == 1){
		$('#checkbox-a').attr('checked', true);
		setTimeout(function(){
			$('#J-form').submit();
		},5000);
	}else if(issueType ==2){
		$('#checkbox-b').attr('checked', true);
	}else if(issueType == 3){
		setTimeout(function(){
			$('#J-form').submit();
		},5000);
		$('#checkbox-a').attr('checked', true);
		$('#checkbox-b').attr('checked', true);
	}
	
	
	
	$('#J-data-table').on('click', '.tip-detail', function(e){
		var dom = $(this);
		Tip.setText(dom.parent().find('.tip-hidden').text());
		Tip.show(dom.width()*-1-10, dom.height()*-1-20, dom.parent());
	}).on('mouseout', '.tip-detail', function(){
		Tip.hide();
	});
	
	//table行选中跳入明细
	$('.detail').click(function(){		
		
		var tt = $(this).attr("id");
		var v = tt.split("_");
		var url = baseUrl + "/gameRisk/queryGameExceptionAuditDetail?lotteryId="+v[0]+"&issueCode="+v[1]+"&status=1";
		location.replace(url); 
	});
	
	//table行选中跳入明细
	$('.deal').click(function(){		
		
		var tt = $(this).attr("id");
		var v = tt.split("_");
		var url = baseUrl + "/gameRisk/queryGameExceptionAuditDetail?lotteryId="+v[0]+"&issueCode="+v[1]+"&status=2";
		location.replace(url); 
	});
	
	
		
	
	//彩种样式及给值_gameType
	$('._gameType').each(function(){
		
		//第一次加载样式
		$(this).parent().removeClass('ico-tab-current');
		var _name = $(this).parent().attr('id');
		var lotteryId = $('#lotteryId').val() == 'undefined' ? '99101' : $('#lotteryId').val();
		
		if(_name == 'undefined' ){
			$(this).parent().removeClass('ico-tab-current');
			return true;				
		}else if(_name == lotteryId ){			
			$(this).parent().removeClass('ico-tab-current').addClass('ico-tab-current');
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
		
	
	
	
})(jQuery);