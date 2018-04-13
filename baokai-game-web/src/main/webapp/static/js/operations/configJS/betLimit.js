(function() {
		//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#betLimitMenu').attr("class","current");
	//弹窗
	var minWindow,mask,initFn_modify,initFn_audit,isture=false;	
		minWindow = new phoenix.MiniWindow();
		mask = phoenix.Mask.getInstance(),
		editConfirm = new phoenix.EditConfirm();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	var formatMoney = function(num){
		var num = Number(num),
			re = /(-?\d+)(\d{3})/;	
		if(Number.prototype.toFixed){
			num = (num).toFixed(2);
		}else{
			num = Math.round(num*100)/100
		}
		num  =  '' + num;
		while(re.test(num)){
			num = num.replace(re,"$1,$2");
		}
		return num;  
	};
	//数字校验，自动矫正不符合数学规范的数学
	var inputs = $('.table-info .input'),
		checkFn,
		limitMoney = $('#limitMoney');
		
	checkFn = function(){
		if(this.value != -1){
			var v = this.value.replace(/\D/g, '').replace(/^0/g, '');
			this.value = v;
		}
	};
	//inputs.keyup(checkFn);
	inputs.blur(checkFn);
	limitMoney.blur(checkFn);
	
	
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
	//检索文本框是否为空
	function CheckFron(){
		$(":text").each( 
			function () { 
			     if($(this).attr("id")=="limitMoney")
				{
					return;
				}
				if($(this).val() == ""){
					var obj=this;
					this.focus(); 					
					isture=false;	
					//您还有未填内容,提示弹层，再文本定位到空文本处.
					minWindow.setTitle('温馨提示');
					minWindow.setContent($('#DivUnfillContent').val());
					minWindow.show();	
					$('.j-ui-miniwindow').addClass("pop");//增加图标
					$('#CloseDf').click(function(e){
						minWindow.hide();
						obj.focus(); 
						window.location.hash = "#"+this;
					});		
					return false;						
				}		
				else{ isture = true;}		
		});	
	}
	//关闭弹窗
	$('#CloseDs,.close').click(function(e){
		$("#DivFailed").hide();
	});	
	function operationSuccess(){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
		location.reload();
	}
	function operationFailure(){
		fnDiv('DivFailed');
		$(".close,.btn").click(function(e){
			$("#DivFailed").css("display","none");
		});	
	}
	//-------------------------------------保存修改操作------------------------------
	$('#J-Modify-Butt1').click(function(e){			
		CheckFron();	
		if(isture == false){ return false;}				
		minWindow.setTitle('温馨提示');
		minWindow.setContent($('#DivReset').val());
		minWindow.show();	
		$('.j-ui-miniwindow').addClass("pop");//增加图标
		initFn_modify();		
	});		
	
	var lotteryid = $('#lotteryid').val();
	
	initFn_modify = function(){	
		$('#DivClose2').click(function(){
			minWindow.hide();
		});	
		
		$('#J-Submit-Butt2').click(function(){		
			
			minWindow.hide();	
			
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"betLimitList":[';
			var arr = [];
			var aInputArray = $('input:text[name!=assist]',document.forms[0]);
			
			$("input:text[name!=assist]", document.forms[0]).each(function(i,val){
				if(this.id=="limitMoney")
				{
					return;
				}
				arr = this.name.split("_");
				jsonStr += '{"gameGroupCode":';
				jsonStr += arr[0];
				jsonStr += ',';
				jsonStr += '"gameSetCode":';
				jsonStr += arr[1];
				jsonStr += ',';
				jsonStr += '"betMethodCode":';
				jsonStr += arr[2];
				jsonStr += ',';
				jsonStr += '"multiple":';
				jsonStr += this.value;
				jsonStr += ',';
				jsonStr += '"multipleIndex":';
				jsonStr += arr[4];
				jsonStr += '}';
				if(i != (aInputArray.size()-1)){
					jsonStr += ',';
				}
			}); 
			jsonStr += ']';
			jsonStr += ',"betAssist":[';
			var arrAssit = [];
			var aInputAssitArray = $('input[name=assist]',document.forms[0]);
			$("input[name=assist]", document.forms[0]).each(function(i,val){
				arrAssit = this.title.split("_");
				jsonStr += '{"id":';
				jsonStr += arrAssit[0];
				jsonStr += ',';
				jsonStr += '"maxMultiple_bak":';
				jsonStr += this.value;
				jsonStr += '}';
				if(i != (aInputAssitArray.size()-1)){
					jsonStr += ',';
				}
			}); 
			jsonStr += ']';
			jsonStr += '}';
			
			//执行操作接口
			var url = currentContextPath + "/gameoa/modifyBetLimit";
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toAuditBetLimit?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl);
		});		
	};	
	
	//-------------------------------------审批通过操作页面------------------------------
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		if(text){
			inputTip.setText(dom.attr('data-showtip'));
			inputTip.show(dom.outerWidth() + 12, dom.outerHeight()*-1 + 4, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});
	
	//-------------------------------------审批通过操作-------------------------------------
	$('#J-Audit-Butt1').click(function(e){		
		minWindow.setTitle('温馨提示');
		minWindow.setContent($('#DivReset').val());
		minWindow.show();	
		$('.j-ui-miniwindow').addClass("pop");//增加图标
		initFn_audit();		
	});		
	initFn_audit = function(){	
		$('#DivClose2').click(function(){
			minWindow.hide();
		});			
		$('#J-Submit-Butt2').click(function(){		
			minWindow.hide();	
			//执行操作接口
			var url = currentContextPath + "/gameoa/auditBetLimit";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"auditType":1';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toPublishBetLimit?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl);
		});		
	};	
	
	//-------------------------------------审批不通过操作-------------------------------------
	$('#J-Audit-Butt2').click(function(e){		
		minWindow.setTitle('温馨提示');
		minWindow.setContent($('#DivReset2').val());
		minWindow.show();	
		$('.j-ui-miniwindow').addClass("pop");//增加图标
		initFn_audit2();		
	});		
	initFn_audit2 = function(){	
		$('#DivClose22').click(function(){
			minWindow.hide();
		});			
		$('#J-Submit-Butt22').click(function(){		
			minWindow.hide();	
			//执行操作接口
			var url = currentContextPath + "/gameoa/auditBetLimit";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"auditType":2';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toBetLimit?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl);
		});		
	};	
	
	//-------------------------------------发布通过操作-----------------------------------
	$('#J-button-publish').click(function(e){
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确认要发布当前投注限制吗？</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a></div>');
		minWindow.show();
		
		$('#J-button-pop-confirm').click(function(){
			var url = currentContextPath + "/gameoa/publishBetLimit";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"publishType":1';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toBetLimit?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl);
		});
		$('#J-button-pop-cancel').click(function(){
			minWindow.hide();
		});
	});
	
	//-------------------------------------发布不通过操作-----------------------------------
	$('#J-button-publish2').click(function(e){
		minWindow.setTitle('温馨提示');
		minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">您确认要不通过当前投注限制发布吗？</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a><a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a></div>');
		minWindow.show();
		
		$('#J-button-pop-confirm').click(function(){
			var url = currentContextPath + "/gameoa/publishBetLimit";
			var jsonStr = "";
			jsonStr += '{"lotteryid":';
			jsonStr += lotteryid;
			jsonStr += ',"publishType":2';
			jsonStr += '}';
			var data = jsonStr;
			var toUrl = currentContextPath + "/gameoa/toBetLimit?lotteryid="+lotteryid;
			sendUrl(url, data, toUrl);
		});
		$('#J-button-pop-cancel').click(function(){
			minWindow.hide();
		});
	});
	
	
	function sendUrl(url, data, toUrl){
		
		editConfirm.isFlag = false;
		jQuery.ajax({
			type:  "post",
			url: url,
			dataType:'json', 
			contentType: "application/json; charset=utf-8",
			data: data,
			cache: false,
			success:function(data){
				if(data.status==1){
					fnDiv('DivSuccessful');
					setTimeout(function (){$("#DivSuccessful").css("display","none");location.href = toUrl;},3000);
				}else{
					operationFailure();
				}
			},
			error: function(){
				operationFailure();
			}
		});
	}
	
	//一键设置
	$('#limitButton').click(function(){
		
		if($.trim(limitMoney.val()) == ''){	
			minWindow.setTitle('温馨提示');
			minWindow.setContent('<div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">请输入有效的奖金限额！</h4></div><div class="pop-btn"><a href="javascript:void(0);" class="btn btn-important " id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a></div>');
			
			minWindow.show();	
			$('#J-button-pop-confirm').click(function(){
				minWindow.hide();				
			});
			return false;
		}		
		
		$(":input[pro_maxbonus]").each(function () {
			var bsNumber = 0,
				limitMoneys = Number($.trim(limitMoney.val())),
				maxbonus = Number($(this).attr('pro_maxbonus')),
				maxBonus = 0;
				
				if(maxbonus == 0){
					$(this).val(0);	
				}else{
					bsNumber = parseInt(limitMoneys / maxbonus);
					$(this).val(bsNumber);	
					maxBonus = formatMoney(bsNumber * maxbonus);
					$(this).parent('td').next('td').empty().append("<span class='price'><dfn>¥</dfn></span>"+maxBonus);					
				}		
		});			
	});	
	
	//失焦可能中奖金额变动
	$(":input[pro_maxbonus]").on('blur', function(e){
		var maxBonus = 0,
			bsNumber = Number($.trim($(this).val()));
			maxbonus = Number($(this).attr('pro_maxbonus'));
			
			if(maxbonus == 0){				
				$(this).parent('td').next('td').empty().append("<span class='price'><dfn>¥</dfn></span>0");	
			}else{
				maxBonus = formatMoney(bsNumber * maxbonus);				
				$(this).parent('td').next('td').empty().append("<span class='price'><dfn>¥</dfn></span>"+maxBonus);					
			}	
			e.preventDefault();
	});
})();