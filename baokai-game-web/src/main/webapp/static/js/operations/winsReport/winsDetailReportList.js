$(document).ready(function() {
	
	var st = $('#sortType');
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(6)').attr("class","current");
	// table header sorting start
	$('.table-toggle').children("a").click(function(){
		switchSortType(this, parseInt(this.id));
	});
	
	function switchSortType(e, n) {
		
		if(n == 0) {
			st.val(0);
		    $('#J-search-form').submit();
		}
		
		if (($(e).children("i").size() == 1) && (st.val() == 0)) {
		} else {
			var $down = $(e).children("i").filter('.ico-down-current');
		    if ($down.is(":visible")) {
		    }  else {
		       n++;
		    }
		    st.val(n);
		    $('#J-search-form').submit();
		}
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
		} else {
			$('.table-toggle').children("a").children("i").show();
		}
	}
	
	checkUpOrDown();
	// table header sorting end
});