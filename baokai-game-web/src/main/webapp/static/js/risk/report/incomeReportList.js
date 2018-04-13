$(document).ready(function() {
	
	//弹窗
	var Wd = phoenix.Message.getInstance(),
		table = $('#J-table'),
		panel = $('#J-search-panel'),
		Tip = phoenix.Tip.getInstance(),			
		selectLotteryId = $('#J-select-lotteryid'),
		selectIssueCode = $('#search-issueCode'),	
		selectTime = $('.date'),
		pageCount = $('#pageCount'),
		setTimeFn = null,
		setSelect = null;
		st = $('#sortType');
	
	setSelect = function(date) {
		$("a[class='date current']").removeClass('current');
		if(date==""){			
			$('#date').addClass('current');	
		}else if(date=="1"){
			$('#date1').addClass('current');	
		}else if(date=="3"){
			$('#date3').addClass('current');	
		}else if(date=="7"){
			$('#date7').addClass('current');	
		}
		
	}
	
	setSelect($('#dateType').val());	
	selectLotteryId.val($('#lotteryId').val());
	selectIssueCode.val($('#issueCode').val());
	
	selectTime.click(function(){	
		setSelect($("#"+this.id).attr("value"));		
	});
	
	$('#J-button-submit').click(function(){			
				
		$('#lotteryId').val(selectLotteryId.val());	
		$('#issueCode').val(selectIssueCode.val());		
		$('#dateType').val($('.current').attr("value"));		
		if(pageCount.val() != ''){		
			$('#pageSize').val(pageCount.val());
		}
		
		$('#J-search-form').submit();
	});

	
	$('.menu-list li:eq(4)').attr("class","current"); 
	$('.col-side .nav dd:eq(3)').attr("class","current");
	
	panel.on('keyup', '.input-money', function(){
		var v = this.value;
		v = v.replace(/[^\d^\.]/g, '').split('.');
		if(v.length > 2){
			v.pop();
		}
		v = v.join('.');
		this.value = v;
	}).on('blur', '.input-money', function(){
		var v = this.value.replace(/[^\d^\.]/g, '');
		if($.trim(v) != ''){
			this.value = phoenix.util.formatMoney(v);
		}
	}).on('keyup', '.input-num', function(){
		this.value = this.value.replace(/[^\d]/g, '');
	});
	
	// table header sorting start
	$('.table-toggle').children("a").click(function(){
		switchSortType(this, parseInt(this.id));
	});
	
	function switchSortType(e, n) {
		var $down = $(e).children("i").filter('.ico-down-current');
	    if ($down.is(":visible")) {
	    }  else {
	       n++;
	    }
	    st.val(n);
	    $('#J-search-form').submit();
	}
	
	function checkUpOrDown() {
		var sortTypeVale = parseInt(st.val());
		if (sortTypeVale != 0) {
			var b = sortTypeVale % 2;
			if (b == 1) {
				var a = sortTypeVale;
				$('.table-toggle').children("a").filter('#'+a+'').children("i").filter('.ico-down-current').hide();
			} else if (b == 0) {
				var a = sortTypeVale - 1;
				$('.table-toggle').children("a").filter('#'+a+'').children("i").filter('.ico-up-current').hide();
			}
		}
	}
	
	checkUpOrDown();
	// table header sorting end
	
	//table行选中跳入明细
	$('table tr').click(function(){	
		
		var tt = $(this).attr("id");
		var v = tt.split("_");
		var url = baseUrl + "/gameoa/queryWinsDetailReport?lotteryid="+v[0]+"&issueCode="+v[1];
		location.replace(url); 
		
	});
	 
	$("table tr").hover(function(){$(this).addClass("tr_hover")},function(){ $(this).removeClass("tr_hover")});

});