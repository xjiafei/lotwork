(function() {
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#awardGroupMenu').attr("class","current");
	//弹窗
	var minWindow,mask,initFn,isture=true,Wd = phoenix.Message.getInstance();	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	var formatMoney = function(num){
			if(num == ''){
				num = '0';
			}
			var num = num.replace(/,/g, ''),
				num = parseFloat(num),
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
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	   
	
	//公司最小留水
	var miniLotteryProfit = $('#miniLotteryProfit').val();
     
	//数字校验，自动矫正不符合数学规范的数学(除了奖金组命名)
	var inputs = $('input.input-money'),checkFn;					
	checkFn = function(){
		var me = this,v = me.value,index;
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');			
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');	
			if(v.substring(index,v.length).length>3){					
				me.value = v=v.substring(0,v.indexOf(".")+3);					
			}			
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');		
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}							
		/*me.value = v = v.replace(/[^\d]/g,'');
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/^-[0-9]*[1-9][0-9]*$/, '$1');
			if(v.substring(index+1,v.length).length>2){
				me.value= v = v.substring(0, v.indexOf(".") + 3);
			}
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0'); 	*/
		
							
	};	
	var formatNum = function(){
		var me = this,v = me.value,index;
		me.value = formatMoney(v);	
	};
	inputs.keyup(checkFn);
	inputs.blur(formatNum);
	
	
	var checkFnN = function(e){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
			if(v.substring(index+1,v.length).length>2){
				me.value= v = v.substring(0, v.indexOf(".") + 6);
			}
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0'); 	
		
		
		if(e.type == 'blur' && me.id == 'J-point-general'){
			if(Number(me.value) < 0.05){
				me.value = 0.05;
			}
			if(Number(me.value) > 0.06){
				me.value = 0.06;
			}
		}
		if(e.type == 'blur' && me.id == 'J-point-budingwei'){
			if(Number(me.value) < 0.05){
				me.value = 0.05;
			}
			if(Number(me.value) > 0.06){
				me.value = 0.06;
			}
		}
	};
	$('.input-decimal').keyup(checkFnN).blur(checkFnN);
	
	var lotteryId = $('#lotteryId').val();
	function operationSuccess(){
		fnDiv('DivSuccessful');
		setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
//		location.reload();
		location.href = baseUrl+'/gameoa/queryGameAwardGroupList?lotteryid='+ lotteryId + '&status=&awardId=';
	}
	
	function operationFailure(){
		fnDiv('DivFailed');
		$(".close,.btn").click(function(e){
			$("#DivFailed").css("display","none");
		});	
	}
		
	//关闭弹窗
	$('#CloseDs').click(function(e){
		$("#DivFailed").hide();
	});		
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
	//检索文本框是否为空
	function CheckFron(obj){
		$(obj).each( 
			function () { 
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
	//-------------------------------------一键设置操作------------------------------
	$('#J-button-setting').click(function(e){
		e.preventDefault();
		
		//公司最低留水
		var _miniLotteryProfit = Number(miniLotteryProfit)/100;
		
		var pointGeneral = Number($('#J-point-general').val()),
			pointBudingwei = Number($('#J-point-budingwei').val()),
			tableDom = $('#J-data-table'),
			jiangjinDom = tableDom.find('input:text'),
			lirunDom = tableDom.find('.point-lirun'),
			proxysDom = tableDom.find('.point-proxy'),
			liushuiDom = tableDom.find('.point-liushui');
			
			
		proxysDom.each(function(i){
			var dom = $(this);
			
			//根据后台公式计算利润和留水
			
			//1-返奖率=总利润
			//总利润=总代返点+公司留水
			var jj = jiangjinDom[i];
			
			var arr = $(jj).attr('name').split("_");
			//理论奖金
			var _theoryBonus = Number(arr[3]);

			//实际奖金
			var _actualBonus = $(jj).val();

			_actualBonus = _actualBonus.replace(",",'')
			_actualBonus = Number(_actualBonus)*10000;
			//返奖率
			//实际奖金/理论奖金=返奖率
			var _prize = Number(_actualBonus/_theoryBonus).toFixed(4);
			
			//总利润
			var totalProfit = Number(1-_prize).toFixed(4);
			
			//12,13 前三，后三
			//12 不定位
			//40 一码不定位
//			if(dom.hasClass('point-budingwei')){
			if((arr[0]==12 || arr[0]==13) && arr[1]==12 && arr[2]==40){
				//三星一码不定位
				//利润
//				lirunDom[i].innerHTML = Number(pointBudingwei - 0.02).toFixed(4);
				lirunDom[i].innerHTML = totalProfit;
				//留水
				//liushuiDom[i].innerHTML = Number(pointBudingwei - 0.01).toFixed(4);
				if(Number(totalProfit-pointBudingwei)<_miniLotteryProfit){
					//如果小于最低利润，则获取公司总利润。
					liushuiDom[i].innerHTML = Number(_miniLotteryProfit);
					//返点
					this.innerHTML = Number(totalProfit - _miniLotteryProfit).toFixed(4);
				}else{
					liushuiDom[i].innerHTML = Number(totalProfit-pointBudingwei).toFixed(4);
					//返点
					this.innerHTML = Number(pointBudingwei).toFixed(4);
				}
				
			}else{
				//返点
//				this.innerHTML = Number(pointGeneral).toFixed(4);
				//利润
//				lirunDom[i].innerHTML = Number(pointGeneral - 0.02).toFixed(4);
				//留水
//				liushuiDom[i].innerHTML = Number(pointGeneral - 0.01).toFixed(4);
				
				lirunDom[i].innerHTML = totalProfit;
				//留水
				//liushuiDom[i].innerHTML = Number(pointBudingwei - 0.01).toFixed(4);
				if(Number(totalProfit-pointGeneral)<_miniLotteryProfit){
					//如果小于最低利润，则获取公司总利润。
					liushuiDom[i].innerHTML = Number(_miniLotteryProfit);
					//返点
					this.innerHTML = Number(totalProfit - _miniLotteryProfit).toFixed(4);
				}else{
					liushuiDom[i].innerHTML = Number(totalProfit-pointGeneral).toFixed(4);
					//返点
					this.innerHTML = Number(pointGeneral).toFixed(4);
				}
			}
			
		});
		
	});	
	
	//-------------------------------------保存修改操作------------------------------------
	$('#J-SaveChanges').click(function(e){	
		CheckFron(".table :text");//验证是否为空
		if(isture == false){ return false;}
		
		var arr = [];
		var assistArr = [];
		//检验成功事执行提交数据动作.
		var jsonStr = "";
		jsonStr += '{"lotteryId":';
		jsonStr += lotteryId;
		jsonStr += ',"awardName":';
		jsonStr += '"';
		jsonStr += $('#awardName').val();
		jsonStr += '"';
		jsonStr += ',"directRet":';
		jsonStr += '"';
		jsonStr += Number(Number($('#J-point-general').val())*10000);
		jsonStr += '"';
		jsonStr += ',"threeoneRet":';
		jsonStr += '"';
		jsonStr += Number(Number($('#J-point-budingwei').val())*10000);
		jsonStr += '"';
		jsonStr += ',"awardBonusStrucList":[';
		
		var aInputArray = $(".table").find("input[name='eachMethod']");
		$(".table").find("input[name='eachMethod']").each(function(i,val){
			arr = $(this).val().split("_");

			jsonStr += '{"gameGroupCode":';
			jsonStr += arr[0];
			jsonStr += ',"gameSetCode":';
			jsonStr += arr[1];
			jsonStr += ',"betMethodCode":';
			jsonStr += arr[2];
			if(arr[3] == 0){
				jsonStr += ',"actualBonus":';
				var str = $(this).next().val();
				var tt = str.replace(/,/g, "");
				jsonStr += Number(tt)*10000 ;
				jsonStr += '}';
			}
			else{
				var bonusArray = $(this).next().children("div");
				jsonStr += ',"assistBMBonusList":[';
				
				 $(this).next().children("div").each(function(j,val){
					jsonStr += '{"methodType":';
					jsonStr += Number($(this).children(':text').attr("name").split("_")[4]);
					jsonStr += ',"actualBonus":';
					var str = $(this).children(':text').val();
					var tt = str.replace(/,/g, "");
					jsonStr += Number(tt)*10000 ;
					jsonStr += '}';
					if(j != (bonusArray.size()-1)){
						jsonStr += ',';
					}
				});
				jsonStr += ']}';
			}
			
			if(i != (aInputArray.size()-1)){
				jsonStr += ',';
			}
		});
	
		jsonStr += ']}';
		var url = baseUrl + "/gameoa/createGameAwardGroup";
		var data = "createGameAwardGroupStr="+ jsonStr;
		
		sendUrl(url, data);
		
	});	 
	
	//ajax
	function sendUrl(url,data){
		jQuery.ajax({
			type:  "post",
			url: url,
			data: data,
			success:function(data){
				if(data.status==1){
					operationSuccess();
				}else{
					//operationFailure();
					Wd.show({
						mask:true,
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">'+ data['message'] +'</h4></div></div>',
						cancelIsShow:true,
						cancelText:'关闭',
						cancelFun:function(){
							Wd.hide();
						}
					});
				}
			},
			error: function(){
				operationFailure();
			}
		});
	}
  
})();
