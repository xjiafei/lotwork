<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>奥运活动</title>
	<meta name="description" content="奥运活动">
	<meta name="keywords" content="奥运活动">
	<link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/august/aoyun/index.css">
</head>
<body>
    <div class="container">
        <div class="preload"></div>
		  <div class="cover">                        
            <div class="ok">
                <div class="confirm"></div>
            </div>
        </div>
        <div class="page">
            <div class="head" id="main">
                <div class="total uptop">{$chargeAmount}</div>
                <div class="total sales">{$betAmount}</div>
                <div class="money">{$prize}</div>
                <div class="gold ten"></div>
                <div class="gold bit"></div>
				<input type="hidden" value="{$medals}" id="medals">
				<input type="hidden" value="{$path_img}" id="path_img">
				<input type="hidden" value="{$status}" id="status">
                <input type="hidden" value="{$result}" id="result">
                <div class="rule"></div>
				<div class="enter"></div>   
            </div>
            <div class="head curtion">
                <div class="back"></div>
                <div class="cbox">
                    <div class="txt t1">{$chargeAmount}</div>
                    <div class="txt t2">{$RebateRatio}</div>
                    <div class="txt t3">100%</div>
                    <div class="txt t4">{$plusRatio}</div>
                    <div class="txt t5">{$medals}</div>
                    <div class="txt t6">{$prize}</div>
                </div>
            </div>
            <div class="activity">
                <h1>活动内容</h1>
                <p>每日充值≥500元的用户，完成活动要求后，奖金根据当日中国队新增金牌数量加量计算</p>
                <table border="0" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                            <th>项目</th>
                            <th>当日累计充值金额</th>
                            <th>充值返利比例</th>
                            <th>中国队当日每增加一枚奥运金牌奖金加奖</th>
                            <th class="last">要求</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="odd">
                            <td rowspan="5">所有彩种</td>
                        </tr>
                        <tr>
                            <td class="even bd"> ≥500 </td>
                            <td class="bd odd">8%</td>
                            <td class="bd even">1%</td>
                            <td rowspan="5" class="odd pd25">当日彩票类游戏销量大于等于充值金额10倍</td>
                        </tr>
                        <tr>
                            <td class="even bd"> ≥5,000 </td>
                            <td class="bd odd">10%</td>
                            <td class="bd even">2%</td>
                        </tr>
                        <tr>
                            <td class="even bd"> ≥50,000 </td>
                            <td class="bd odd">12%</td>
                            <td class="bd even">3%</td>
                        </tr>
                        <tr>
                            <td class="even"> ≥500,000 </td>
                            <td class="odd">15%</td>
                            <td class="even">5%</td>
                        </tr>
                    </tbody>
                </table>
                <br/>
                <p>活动资格：当日彩票类销量/当日累计充值金额≥10</p>
                <p>活动奖金=当日累积充值金额*返利比例*（100%+加奖百分比*当日中国队金牌数量）</p>
                <p>例：用户A当日累计充值6,000元，彩票类游戏销量为60,000，则用户A当日的奖金为600元，中国队当日新增8枚金牌，则用户A的奖金为：600*（100%+2%*8）=696</p>
            </div>
            <div class="activity arule">
                <h1>活动规则</h1>
                <p>1　活动返奖，参与游戏只限于宝开彩票旗舰版及宝开彩票专业版平台彩票类游戏。</p>
                <p>2　投注必须为已开奖注单，视为有效投注。活动销量两个平台分别独立计算。</p>
                <p>3　每日金牌数量以中国奥委会官方公布为准，更新时间以北京时间为准。</p>
                <p>4　活动统计用户当日充值时间为00:00:00-23:00:00，销量统计时间为当日00:00:00-23:59:59.</p>
                <p>5　本次活动采取报名制，用户需要点击“立即报名”后才能获取活动奖金。注：VIP3（包含VIP3）以上用户无需报名即可参加。</p>
                <p>6　本次活动包含上下级充值。</p>
                <p>7　骰宝大小单双玩法不计入活动销量。</p>
                <p>8　超级2000玩法按实际投注额的80%进行结算。如：用户超级2000玩法中投注额为1,000，则结算销量为1,000*80%=800.</p>
                <p>9　活动奖金于次日18:00点前派发。</p>
                <p>10　活动期间禁止任何作弊行为，一经发现平台将取消参与活动资格，严重者将被冻结账号处理。</p>
                <p>11　宝开平台保留活动最终解释权。</p>
            </div>
            <div class="bottoms">&copy;2003-2016 宝开彩票 All Rights Reserved</div>
        </div>
    </div>
    <!--<script src="{$path_img}/js/activity/august/aoyun/zepto.min.js" type="text/javascript"></script>-->
    <!--<script src="{$path_img}/images/activity/august/aoyun/index.js" type="text/javascript"></script>-->
    <script src="{$path_img}/js/activity/august/aoyun/jquery-1.8.3.min.js" type="text/javascript"></script>
	{literal}
    <script type="text/javascript">
   $(function(){
	
		//var global_path_url="{$path_img}";
		var global_path_url=document.getElementById('path_img').value;
		var status=document.getElementById('status').value; 
		var result=document.getElementById('result').value;
		var medals = document.getElementById('medals').value;      //number类型或string类型
		//var medals = "{$medals}"; 
		
		
		//$('.ten').css('backgroundImage','url('+ global_path_url+ '/images/activity/august/aoyun/'+Math.floor(parseInt(medals)/10)+'.png');
		//$('.bit').css('backgroundImage','url('+ global_path_url+ '/images/activity/august/aoyun/'+parseInt(medals)%10+'.png');
        var divs = document.getElementsByTagName('div');
        for(var i = 0; i < divs.length; i++){
            if(divs[i].className == 'gold ten'){
                divs[i].style.backgroundImage = 'url('+ global_path_url+ '/images/activity/august/aoyun/'+Math.floor(parseInt(medals) / 10)+'.png)';
            }else if(divs[i].className == 'gold bit'){
                divs[i].style.background = 'url('+ global_path_url+ '/images/activity/august/aoyun/'+Math.floor(parseInt(medals) % 10)+'.png)';
            }
        }
		$('.rule').bind('click',function(evt){
			$('.preload').css('visibility','visible');
			$('#main').hide();
			$('.curtion').show();
		});
		$('.back').bind('click',function(evt){
			$('.preload').css('visibility','hidden');
			$('.curtion').hide();
			$('#main').show();
		});	

		if (result=='1' && status == '0' ) {
            $('.enter').show(); 
        } else {
            $('.enter').hide();
        }

		$('.cover').height($('body').height() + 'px');
		$('.ok').css({'left': (window.innerWidth - 420) / 2 + 'px','top':'334px'});
		
		$('.enter').bind('click',function(evt){
			//alert('asd')
		    $.ajax({
             url: '/vip/aoyunsign',              //更新数据库字段
             type:'get',
			  dataType:'json',
             beforeSend: function () {
                 //$('.login_tips span').html('').hide();
            },
             success: function (data) {
				//console.log(JSON.parse(data['status']));				 // 数据格式 {status:1,msg:'成功',msg:'xxxx'} status:1表示成功,0表示失败,msg用来做信息提示
                if (JSON.parse(data['status']) == 1) {
                     $('.enter').hide();
                     $('.cover').show();
                 } else if (JSON.parse(data['status']) == 0) {
                     alert('报名未成功');
                 }
             },
            complete: function () {
        
             }
         })
			
			
		});
		 //报名成功后点击确定
		$('.confirm').bind('click',function(evt){
			$('.enter').hide();
			$('.cover').hide();
		});
	})
	</script>	
	{/literal}
</body>
</html>