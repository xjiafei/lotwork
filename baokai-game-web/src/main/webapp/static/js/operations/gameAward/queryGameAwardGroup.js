(function() {
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#awardGroupMenu').attr("class","current");
	
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
			aList.eq(2).html(Number(startNum*100).toFixed(0)+"%");
			aList.eq(3).html(Number(endNum*100).toFixed(0)+"%");
		}
		if(bList.length>0)
		{
			bList.eq(0).html(startNum2);
			bList.eq(1).html(endNum2);
			bList.eq(2).html(Number(startNum2*100).toFixed(0)+"%");
			bList.eq(3).html(Number(endNum2*100).toFixed(0)+"%");
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
  
})();
