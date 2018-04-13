function doPre(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(parseInt(currentPageNo)-1);
		$("#J-form").submit();
	}
	
	function doNext(){
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(parseInt(currentPageNo)+1);
		$("#J-form").submit();
	}
	
	function doForward(index){
		if($("#forwardPage").val()!=''){
        if(index == -1){
			$("#pageNo").val(parseInt($("#forwardPage").val()));
        }else{ 
        	$("#pageNo").val(index);
        } 
		$("#J-form").submit();
		}
	}
	
	function doCurrent(pageNo){
		$("#pageNo").val(pageNo);
		$("#J-form").submit();
	}