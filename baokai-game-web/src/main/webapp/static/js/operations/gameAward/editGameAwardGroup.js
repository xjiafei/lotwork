(function() {
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#awardGroupMenu').attr("class","current");
	//弹窗
	var minWindow,mask,initFn,isture=true;	
		minWindow = new phoenix.MiniWindow();
		mask = phoenix.Mask.getInstance(),
		editConfirm = new phoenix.EditConfirm();
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
	
		//数字校验，自动矫正不符合数学规范的数学(除了奖金组命名)
	var inputs = $('input.input-money'),checkFn;					
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
			if(v.substring(index+1,v.length).length>2){
				me.value= v = v.substring(0, v.indexOf(".") + 3);
			}
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0'); 						
	};	
	var formatNum = function(){
		var me = this,v = me.value,index;
		me.value = formatMoney(v);	
	};
	inputs.keyup(checkFn);
	inputs.blur(formatNum);
	
    //-----------------------------------页面初始化计算返点与总利润------------------------------------
	var bonus={
		lotteryId:$('#lotteryId').val(),//彩种ID
		//直选返点对象
		oDirectRet:$('#J-point-general'),
		//直选返点默认值
		DirectRet :0,
		//不定位返点对象
		oBuDingWei:$('#J-point-budingwei'),
		//不定位返点默认值
		BuDingWei :0,
		//公司保留最低留水
		LotteryProfit: Number($('#miniLotteryProfit').val())/100
	};	   
	bonus.DirectRet=Number($('#J-point-general').val())/10000;
	bonus.oDirectRet.val(bonus.DirectRet);
	bonus.BuDingWei=bonus.oBuDingWei.length==0 ? 0:Number($('#J-point-budingwei').val())/10000;
	bonus.oBuDingWei.val(bonus.BuDingWei);
    var aList =bonus.oDirectRet.parent().find(".color-gray").find("label"),
        bList =bonus.oBuDingWei.parent().find(".color-gray").find("label"),
		tableDom = $('#J-data-table'+" tbody"),
		proxysDom = tableDom.find('.point-proxy'),
	    liushuiDom = tableDom.find('.point-liushui'),
		samsonDom = tableDom.find('[name="samson"]'),
		//直选理论奖金值例如：4000
		pointTheory = Number(tableDom.eq(0).find(".point-bonus").attr("id").split("_")[3])/10000,
		//直选实际奖金值 
		pointActual = Number(tableDom.eq(0).find(".point-bonus").html()),    
		//直选实际奖金占比例
		bonusProportion = Number(pointActual/pointTheory).toFixed(2),               
		//三星不定位理论奖金值
		pointTheorySamson = samsonDom.length==0 ? 0: Number(samsonDom.eq(0).attr("id").split("_")[3])/10000,
		//三星不定位实际奖金值                            
		pointActualSamson = samsonDom.eq(0).length==0 ? 0 : Number(samsonDom.eq(0).html()), 
		//三星不定位奖金比例
		bonusProportionSamson = Number(pointActualSamson/pointTheorySamson).toFixed(2),
		                                   
		//startNum=bonus.DirectRet,
		startNum=0.01,
		endNum = Number(1-bonusProportion-bonus.LotteryProfit).toFixed(2),
		//startNum2=bonus.BuDingWei,
		startNum2=0.01,
		endNum2=pointTheorySamson==0?0:Number(1-bonusProportionSamson-bonus.LotteryProfit).toFixed(2);	
		if(aList.length>0)
		{	
			aList.eq(0).html(startNum);
			aList.eq(1).html(endNum);
			aList.eq(2).html(startNum*100+"%");
			aList.eq(3).html(endNum*100+"%");
		}
		if(bList.length>0)
		{
			bList.eq(0).html(startNum2);
			bList.eq(1).html(endNum2);
			bList.eq(2).html(startNum2*100+"%");
			bList.eq(3).html(endNum2*100+"%");
		}
		       
	    tableDom.find("tr").each(function() {
			var me=$(this),tdLirn=me.find(".point-lirun"),tdProxy=me.find(".point-proxy"),tdLiushui=me.find(".point-liushui");
			if(tdLirn.attr("name")=="samson")
			{
				tdLirn.html(Number(1- bonusProportionSamson).toFixed(2));
			}
			else
			{
			    tdLirn.html(Number(1- bonusProportion).toFixed(2));
			}
			if(tdProxy.attr("name")=="samson")
			{
                 tdProxy.html(Number(bonus.BuDingWei));
			}
			else
			{
				 tdProxy.html(Number(bonus.DirectRet));
			}
            if(tdLiushui.attr("name")=="samson")
			{
				tdLiushui.html(Number(1- bonusProportionSamson-bonus.BuDingWei).toFixed(2));
			}
			else
			{
				tdLiushui.html(Number(1- bonusProportion-bonus.DirectRet).toFixed(2));
			} 	
        });	
	    //---end---
	
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
			var strat=Number(aList.eq(0).html()),end=Number(aList.eq(1).html());
			if(Number(me.value) <  strat){
				me.value = strat;
			}
			if(Number(me.value) > end){
				me.value = end;
			}
		}
		if(e.type == 'blur' && me.id == 'J-point-budingwei'){
			var strat=Number(bList.eq(0).html()),end=Number(bList.eq(1).html());
			if(Number(me.value) <  strat){
				me.value = strat;
			}
			if(Number(me.value) > end){
				me.value = end;
			}
		}
	};
	$('.input-decimal').keyup(checkFnN).blur(checkFnN);
		
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
		var end= Number(1- bonusProportion).toFixed(2);
		var end2=Number(1- bonusProportionSamson).toFixed(2);
        proxysDom.each(function() {
			if($(this).attr("name")=="samson")
			{
				 $(this).html(bonus.oBuDingWei.val());
			}else
			{
                $(this).html(bonus.oDirectRet.val());
			}
        });
		liushuiDom.each(function() {
			if($(this).attr("name")=="samson")
			{
				$(this).html((end2-Number(bonus.oBuDingWei.val()).toFixed(2)).toFixed(2));
			}else
			{
			   $(this).html((end-Number(bonus.oDirectRet.val()).toFixed(2)).toFixed(2));
			}
		});
	});
	
	//-------------------------------------保存修改操作------------------------------------
	$('#J-SaveChanges').click(function(e){	
		//CheckFron(".table :text");//验证是否为空
		//if(isture == false){ return false;}
		
		$('#J-SaveChanges').removeAttr("disabled");
		//检验成功事执行提交数据动作.
		//....			
		var arr = [];
		var assistArr = [];
		//检验成功事执行提交数据动作.
		var jsonStr = "";
		jsonStr += '{"lotteryId":';
		jsonStr += bonus.lotteryId;
		jsonStr += ',"awardName":';
		jsonStr += '"';
		jsonStr += $('#awardName').val();
		jsonStr += '"';
		jsonStr +=',"awardId":';
		jsonStr += $('#awardId').val();
		jsonStr += ',"directRet":';
		jsonStr += '"';
		jsonStr += Number($('#J-point-general').val()*10000);
		jsonStr += '"';
		jsonStr += ',"threeoneRet":';
		jsonStr += '"';
		jsonStr += Number($('#J-point-budingwei').val()*10000);
		jsonStr += '"';
		jsonStr += ',"updateType":';
		jsonStr += $('#status').val();
		jsonStr += ',"awardBonusStrucList":[';
		
		var aInputArray = $(".table").find(".point-bonus");
		aInputArray.each(function(i,val){
			arr = $(this).attr("id").split("_");
            if(arr.length==4){
				jsonStr += '{"gameGroupCode":';
				jsonStr += arr[0];
				jsonStr += ',"gameSetCode":';
				jsonStr += arr[1];
				jsonStr += ',"betMethodCode":';
				jsonStr += arr[2];
				jsonStr += ',"actualBonus":';
				//var str = $(this).next().val();
				//var tt = str.replace(/,/g, "");
				//jsonStr += Number(tt)*10000 ;
				var tt = Number($(this).html())*10000;
				jsonStr += tt;
				jsonStr += '}';
				if(i != (aInputArray.size()-1)){
				   jsonStr += ',';
			    }
			}
			/*else if(arr.length==6){
				var bonusArray = $(this).parent().parent().children("div");
				jsonStr += ',"assistBMBonusList":[';
				bonusArray.each(function(j,val){
					jsonStr += '{"methodType":';
					jsonStr += $(this).attr("name")
					jsonStr += ',"actualBonus":';
					var tt = arr[3];
					jsonStr += Number(tt)*10000 ;
					jsonStr += '}';
					if(j != (bonusArray.size()-1)){
						jsonStr += ',';
					}
				});
				jsonStr += ']}';
			}*/
			
			
		});
	
		jsonStr += ']}';
		var url = baseUrl + "/gameoa/editGameAwardGroup";
		var data = "editGameAwardGroupStr="+ jsonStr;
		
		sendUrl(url, data);
		
		
	});	 
	
	//ajax
	function sendUrl(url,data){
		
		editConfirm.isFlag = false;
		jQuery.ajax({
			type:  "post",
			url: url,
			data: data,
			success:function(data){
				if(data.status==1){
					fnDiv('DivSuccessful');
					setTimeout(function (){$("#DivSuccessful").css("display","none");},1500);
//					location.reload();
					location.href = baseUrl+'/gameoa/queryGameAwardGroupList?lotteryid='+ bonus.lotteryId + '&status=&awardId=';
				}else{
					fnDiv('DivFailed');
					$(".close,.btn").click(function(e){
						$("#DivFailed").css("display","none");
					});	
				}
			},
			error: function(){
				fnDiv('DivFailed');
				$(".close,.btn").click(function(e){
					$("#DivFailed").css("display","none");
				});	
			}
		});
	}
  
})();
