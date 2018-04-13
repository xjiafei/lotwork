$(document).ready(function(){
	$('.menu-list li:eq(8)').attr("class","current");
		var inputTimeStart = $('#timeStart'),
			inputTimeEnd = $('#timeEnd');

		inputTimeStart.click(function(){
			var Dt = new phoenix.DatePicker({input:this,isShowTime:true});
				Dt.show();
		});	
		inputTimeEnd.click(function(){
			var Dt = new phoenix.DatePicker({input:this,isShowTime:true});
				Dt.show();
		});	
		
		new phoenix.Input({el:'#J-search-username',defText:$('#J-hidden-account').val()});
		
		$('#J-search-ip').keyup(function(){
			this.value = this.value.replace(/[^?:(\.|\d)]/g, '');
		});
		function check() { 
			var num="(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";  
		    var reg = new RegExp("^"+num+"\\."+num+"\\."+num+"\\."+num+"$"); 
			 alert(reg.test(t1.value)) 
		}
		$("#J-search-ip").blur(function(){
			var num="(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";  
		    var reg = new RegExp("^"+num+"\\."+num+"\\."+num+"\\."+num+"$"); 
			if(!reg.test($(this).val()))
			{
				alert("请输入正确格式的IP地址");
				$(this).val("");
			}
		});
		
		$('#J-button-submit').click(function(){
			$('#J-form').submit();
		});
		
	})