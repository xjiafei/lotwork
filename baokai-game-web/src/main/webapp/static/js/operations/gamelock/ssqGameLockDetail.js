(function($){
	var msg = new phoenix.Message();
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#lockMenu').attr("class","current");
	
	var formatMoney = function(num){

		if($.trim(num) == ''){
			num = '0';
		}else{
			if(typeof num == 'number'){
				num = num.toString();
			}

			num.replace(/\D/g, '');
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

	//封锁数据
	riskData = {
		//红5蓝1
		r5b1: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,
			//奖金
			Bonus: 3500
		},
		//红5
		r5: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,	
			//奖金
			Bonus: 300
		},
		//红4蓝1
		r4b1: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,		
			//奖金
			Bonus: 300
		},
		//红4蓝1
		r4: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,		
			//奖金
			Bonus: 18
		},
		//红3蓝一
		r3b1: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,		
			//奖金
			Bonus: 18
		},
		//红2蓝1
		r2b1: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,		
			//奖金
			Bonus: 10
		},
		//红1蓝1
		r1b1: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,		
			//奖金
			Bonus: 10
		},
		//红1蓝1
		b1: {
			//中奖注数
			betLength: 0,
			//奖项风险值
			RiskNum: 0,	
			//奖金
			Bonus: 10	
		},
		//最大风险值
		maxnum: 0
	}		

	//从dom获取奖金
	var getBonus = function(){

		//r5b1
		riskData['r5b1']['Bonus'] = $('#lv3Bonus').text().replace(/,|[.].*?$/g, '');
		//r5
		riskData['r5']['Bonus'] = $('#lv41Bonus').text().replace(/,|[.].*?$/g, '');
		//r4b1
		riskData['r4b1']['Bonus'] = $('#lv42Bonus').text().replace(/,|[.].*?$/g, '');
		//r4
		riskData['r4']['Bonus'] = $('#lv51Bonus').text().replace(/,|[.].*?$/g, '');
		//r3b1
		riskData['r3b1']['Bonus'] = $('#lv52Bonus').text().replace(/,|[.].*?$/g, '');
		//r2b1
		riskData['r2b1']['Bonus'] = $('#lv61Bonus').text().replace(/,|[.].*?$/g, '');
		//r1b1
		riskData['r1b1']['Bonus'] = $('#lv62Bonus').text().replace(/,|[.].*?$/g, '');
		//b1
		riskData['b1']['Bonus'] = $('#lv63Bonus').text().replace(/,|[.].*?$/g, '');
	}

	var lastModifyed = '',
		firstModify = '',
		lastRed = '',
		lastBlue = '',
		redBall = $('#J-red-ball'),
		blueBall = $('#J-blue-ball');

		redBall.bind('focus', function(){
			firstModify = this.value;
			lastRed = this.value;
			this.value = '';
		})

		blueBall.bind('focus', function(){
			firstModify = this.value;
			blueBall = this.value;
			this.value = '';
		})

		redBall.bind('input', function(){

			if($.trim(this.value)!= ''){
				mathNum();
			}
		});

		blueBall.bind('input', function(){
			if($.trim(this.value)!= ''){
				mathNum();
			}
		});

		redBall.bind('propertychange', function(){
			var info = this.value;


			if(lastModifyed == info) {
				//没有改动
			} else {
				if($.trim(this.value)!= ''){
					mathNum();
				}
			}
			
			lastModifyed = info;
		});

		blueBall.bind('propertychange', function(){
			var info = this.value;

			if(lastModifyed == info) {
				//没有改动
			} else {
				if($.trim(this.value)!= ''){
					mathNum();
				}
			}
			
			lastModifyed = info;
		});
		
		checkFn = function(){
				var v = this.value.replace(/\D/g, '').replace(/^0/g, '');
				this.value = v;
		};
		redBall.keyup(checkFn);
		blueBall.keyup(checkFn);

		redBall.bind('blur', function(){
		
			if($.trim(this.value) == ''){
				$(this).val(firstModify);
				return;
			}

			$(this).val(formatMoney(this.value));
		});

		blueBall.bind('blur', function(){
			if($.trim(this.value) == ''){
				$(this).val(firstModify);
				return;
			}

			$(this).val(formatMoney(this.value));
		});
		

	var mathNum = function(){
		var redBall = $('#J-red-ball'),
			blueBall = $('#J-blue-ball'),
			x = parseInt(redBall[0].value.replace(/,|[.].*?$/g, '')),
			y = parseInt(blueBall[0].value.replace(/,|[.].*?$/g, '')),
			minNum = Math.min(x, y);

		//r5b1
		riskData['r5b1']['betLength'] = minNum;
		riskData['r5b1']['RiskNum'] = minNum * riskData['r5b1']['Bonus'];
		//r5
		riskData['r5']['betLength'] = x;
		riskData['r5']['RiskNum'] = x * riskData['r5']['Bonus'];
		//r4b1
		riskData['r4b1']['betLength'] = Math.min(x*28, y);
		riskData['r4b1']['RiskNum'] = Math.min(x*28, y) * riskData['r4b1']['Bonus'];
		//r4
		riskData['r4']['betLength'] = x*28;
		riskData['r4']['RiskNum'] = x*28* riskData['r4']['Bonus'];
		//r3b1
		riskData['r3b1']['betLength'] = Math.min(x*28*27, y);
		riskData['r3b1']['RiskNum'] = Math.min(x*28*27, y) * riskData['r3b1']['Bonus'];
		//r2b1
		riskData['r2b1']['betLength'] = Math.min(x*28*27*26, y);
		riskData['r2b1']['RiskNum'] = Math.min(x*28*27*26, y) * riskData['r2b1']['Bonus'];
		//r1b1
		riskData['r1b1']['betLength'] = Math.min(x*28*27*26*25, y);
		riskData['r1b1']['RiskNum'] = Math.min(x*28*27*26*25, y) * riskData['r1b1']['Bonus'];
		//b1
		riskData['b1']['betLength'] = y;
		riskData['b1']['RiskNum'] = y * riskData['b1']['Bonus'];
		//maxNum
		riskData['maxNum'] = (minNum * riskData['r5b1']['Bonus']) + (Math.min(x*28, y) * riskData['r4b1']['Bonus']) +  (Math.min(x*28*27, y) * riskData['r3b1']['Bonus']) + (Math.min(x*28*27*26, y) * riskData['r2b1']['Bonus'])  +  (Math.min(x*28*27*26*25, y) * riskData['r1b1']['Bonus']) + (y * riskData['b1']['Bonus']);

		/*if(!$('.tips-bonus').is('hide')){
			$('.tips-bonus').hide();	
		}*/
	
		inputNum();
	}

	//输出数值
	var inputNum = function(){

		$('#lv3bets').text(formatMoney(riskData['r5b1']['betLength'])),  $('#lv3').text(formatMoney(riskData['r5b1']['RiskNum']));
		$('#lv41bets').text(formatMoney(riskData['r5']['betLength'])),  $('#lv41').text(formatMoney(riskData['r5']['RiskNum']));
		$('#lv42bets').text(formatMoney(riskData['r4b1']['betLength'])),  $('#lv42').text(formatMoney(riskData['r4b1']['RiskNum']));
		$('#lv51bets').text(formatMoney(riskData['r4']['betLength'])),  $('#lv51').text(formatMoney(riskData['r4']['RiskNum']));
		$('#lv52bets').text(formatMoney(riskData['r3b1']['betLength'])),  $('#lv52').text(formatMoney(riskData['r3b1']['RiskNum']));
		$('#lv61bets').text(formatMoney(riskData['r2b1']['betLength'])),  $('#lv61').text(formatMoney(riskData['r2b1']['RiskNum']));
		$('#lv62bets').text(formatMoney(riskData['r1b1']['betLength'])),  $('#lv62').text(formatMoney(riskData['r1b1']['RiskNum']));
		$('#lv63bets').text(formatMoney(riskData['b1']['betLength'])),  $('#lv63').text(formatMoney(riskData['b1']['RiskNum']));
		$('#maxnum').text(formatMoney(riskData['maxNum']));
		$('#maxRNum').text(formatMoney(riskData['maxNum']));
		
	}
	
	$(function(){
		getBonus();
		mathNum();
	});
})(jQuery);