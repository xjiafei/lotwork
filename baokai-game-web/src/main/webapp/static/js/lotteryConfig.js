//系统彩种下拉显示列表采取文件配置，减低服务器压力
var lotteryConfig={
	lotterys:[
				{lotteryId:99101,lotteryName:'重庆时时彩'},
				{lotteryId:99102,lotteryName:'江西时时彩'},
				{lotteryId:99103,lotteryName:'新疆时时彩'},
				{lotteryId:99104,lotteryName:'天津时时彩'},
				{lotteryId:99105,lotteryName:'黑龙江时时彩'},
				{lotteryId:99106,lotteryName:'宝开时时彩'},
				{lotteryId:99107,lotteryName:'上海时时乐'},
				{lotteryId:99111,lotteryName:'宝开一分彩'},
				{lotteryId:99112,lotteryName:'秒开时时彩'},
//				{lotteryId:99113,lotteryName:'超级2000秒秒彩（APP版）'},
				{lotteryId:99114,lotteryName:'腾讯分分彩'},
				{lotteryId:99108,lotteryName:'3D'},
				{lotteryId:99109,lotteryName:'排列5'},
				{lotteryId:99301,lotteryName:'山东11选5'},
				{lotteryId:99302,lotteryName:'江西11选5'},
				{lotteryId:99303,lotteryName:'广东11选5'},
				{lotteryId:99304,lotteryName:'重庆11选5'},
				{lotteryId:99305,lotteryName:'宝开11选5'},
				{lotteryId:99306,lotteryName:'秒开11选5'},
				{lotteryId:99201,lotteryName:'北京快乐8'},
				{lotteryId:99401,lotteryName:'双色球'},
				{lotteryId:99701,lotteryName:'六合彩'},
				{lotteryId:99501,lotteryName:'江苏快三'},
				{lotteryId:99502,lotteryName:'安徽快三'},
				{lotteryId:99601,lotteryName:'江苏骰宝'},
				{lotteryId:99602,lotteryName:'高频骰宝(娱乐厅)'},
				{lotteryId:99603,lotteryName:'高频骰宝(至尊厅)'}
             ],
    lotterysSelect:function(){
    	
    	var ss='<select class="ui-select" name="lotteryId" id="lotteryId">';
	     ss+='<option value="0">全部彩种</option>';
    	var d=lotteryConfig.lotterys;
        for(var i in d){    
        	 ss+='<option value="'+d[i].lotteryId+'">'+d[i].lotteryName+'</option>';
        }   
        ss+='</select>'
        return ss;
    }
}
$(document).ready(function(){
$('#sel').append(lotteryConfig.lotterysSelect());
});