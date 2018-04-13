(function() {
		//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#awardGroupMenu').attr("class","current");
	//弹窗
	var minWindow,mask,initFn,isture=true,
		minWindow = new phoenix.MiniWindow();
		mask = phoenix.Mask.getInstance(),
		editConfirm = new phoenix.EditConfirm();
		
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});	  
	
	var miniLotteryProfit = $('#miniLotteryProfit').val();
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 
	

	//-------------------------------审核通过-----------------------------------------------
	var lotteryId = $('#lotteryId').val();
	$('#J-Submit-Button1').click(function(e){
		//验证是否为值				
		if(isture == false){ return false;}
		//检验成功事执行提交数据动作.
					
		e.preventDefault();		
		minWindow.setTitle('温馨提示');
		minWindow.setContent($('#DivTip').val());
		minWindow.show();
		$('.j-ui-miniwindow').addClass("pop");//增加图标
		
		$('#J-Submit-Button1').removeAttr("disabled");
		initFn();	
		
		
	});
	
	initFn = function(){	
		//关闭弹窗
		$('#closeDs').click(function(e){
			minWindow.hide();
		});
		//提审核操作
		$('#J-Button1').click(function(e){			
			minWindow.hide();	
			
			//检验成功事执行提交数据动作.
			var jsonStr = "";
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr +=',"awardId":';
			jsonStr += $('#awardId').val();
			jsonStr += ',"checkType":2';	
			jsonStr += ',"checkResult":1';
			jsonStr += '}';
			var url = baseUrl + "/gameoa/auditGameAwardGroup";
			var data = "auditGameAwardGroupStr="+ jsonStr;
			
			sendUrl(url,data);
			
		});		
	};
	
	
	//-------------------------------审核不通过-----------------------------------------------
	var lotteryId = $('#lotteryId').val();
	$('#J-Submit-Button2').click(function(e){
		//验证是否为值				
		if(isture == false){ return false;}
		//检验成功事执行提交数据动作.
					
		e.preventDefault();		
		minWindow.setTitle('温馨提示');
		minWindow.setContent($('#DivTip2').val());
		minWindow.show();
		$('.j-ui-miniwindow').addClass("pop");//增加图标
		
		$('#J-Submit-Button2').removeAttr("disabled");
		initFn2();	
		
		
	});
	
	initFn2 = function(){	
		//关闭弹窗
		$('#closeDs2').click(function(e){
			minWindow.hide();
		});
		//提审核操作
		$('#J-Button2').click(function(e){			
			minWindow.hide();	
			//检验成功事执行提交数据动作.
			var jsonStr = "";
			jsonStr += '{"lotteryId":';
			jsonStr += lotteryId;
			jsonStr +=',"awardId":';
			jsonStr += $('#awardId').val();
			jsonStr += ',"checkType":2';	
			jsonStr += ',"checkResult":2';
			jsonStr += '}';
			var url = baseUrl + "/gameoa/auditGameAwardGroup";
			var data = "auditGameAwardGroupStr="+ jsonStr;
			
			sendUrl(url,data);
			
		});		
	};
	
	var inputTip = new phoenix.Tip.getInstance();
	$('body').on('mouseover', '.input-mark', function(){
		var dom = $(this),text = dom.attr('data-showtip');
		if(text){
			inputTip.setText(dom.attr('data-showtip'));
			inputTip.show(dom.width() + 12, dom.outerHeight()*-1 + 4, this);
		}
	}).on('mouseout', '.input-mark', function(){
		var text = this.getAttribute('data-showtip');
		if(text){
			inputTip.hide();
		}
	});
	
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
					location.href = baseUrl+'/gameoa/queryGameAwardGroupList?lotteryid='+ lotteryId + '&status=&awardId=';
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
