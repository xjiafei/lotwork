	// paging start
	function doPre(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(parseInt(currentPageNo)-1);
		$("form:first").submit();
	}
	
	function doNext(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(parseInt(currentPageNo)+1);
		$("form:first").submit();
	}
	
	function doForward(index){
		if($("#forwardPage").val()!=''){
	    if(index == -1){
			$("#pageNo").val(parseInt($("#forwardPage").val()));
	    }else{ 
	    	$("#pageNo").val(index);
	    } 
	    $("form:first").submit();}
	}
	
	function doCurrent(pageNo){
		$("#pageNo").val(pageNo);
		$("form:first").submit();
	}
	// paging end	