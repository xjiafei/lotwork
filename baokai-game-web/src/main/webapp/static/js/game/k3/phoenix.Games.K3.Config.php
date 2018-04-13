
<?php


//直选复式
$data = array();
//{title:'标题',name:'名称', parent:'父类', childs:[子类], mode:'顶级父类'}


//节点和玩法的区别:
//节点一定有childs, 玩法没有
//玩法一定有顶级父类, 节点没有
//特殊玩法名称则带有 headline 属性，用于号码蓝的选球注单标题显示




//和值
$data[] = array('title' => '和值', 'name' => 'hezhi');
/*$data[] = array('title' => '和&nbsp;&nbsp;&nbsp;值', 'name' => 'zhixuan', 'parent' => 'hezhi');
$data[] = array('title' => '复式', 'name' => 'fushi', 'parent' => 'zhixuan', 'mode' => 'wuxing');*/

//三同号通选
$data[] = array('title' => '三同号通选', 'name' => 'santonghaotongxuan');
/*$data[] = array('title' => '直&nbsp;&nbsp;&nbsp;选', 'name' => 'zhixuan', 'parent' => 'sixing');
$data[] = array('title' => '复式', 'name' => 'fushi', 'parent' => 'zhixuan', 'mode' => 'sixing');*/

//三同号单选
$data[] = array('title' => '三同号单选', 'name' => 'santonghaodanxuan');
/*$data[] = array('title' => '直&nbsp;&nbsp;&nbsp;选', 'name' => 'zhixuan', 'parent' => 'housan');
$data[] = array('title' => '复式', 'name' => 'fushi', 'parent' => 'zhixuan', 'mode' => 'housan');*/

//三不同号
$data[] = array('title' => '三不同号', 'name' => 'sanbutonghao');
$data[] = array('title' => '标准玩法', 'name' => 'biaozhun', 'parent' => 'sanbutonghao');
$data[] = array('title' => '标准投注', 'name' => 'biaozhuntouzhu', 'parent' => 'biaozhun', 'mode' => 'sanbutonghao',  "headline" => array("三不同号","标准"));
$data[] = array('title' => '胆拖投注', 'name' => 'dantuotouzhu', 'parent' => 'biaozhun', 'mode' => 'sanbutonghao',  "headline" => array("三不同号","胆拖"));
/*$data[] = array('title' => '直&nbsp;&nbsp;&nbsp;选', 'name' => 'zhixuan', 'parent' => 'zhongsan');
$data[] = array('title' => '二码不定位', 'name' => 'ermabudingwei', 'parent' => 'budingwei', 'mode' => 'zhongsan');*/

//三连号通选
$data[] = array('title' => '三连号通选', 'name' => 'sanlianhaotongxuan');
/*$data[] = array('title' => '直&nbsp;&nbsp;&nbsp;选', 'name' => 'zhixuan', 'parent' => 'qiansan');
$data[] = array('title' => '二码不定位', 'name' => 'ermabudingwei', 'parent' => 'budingwei', 'mode' => 'qiansan');*/

//二同号复选
$data[] = array('title' => '二同号复选', 'name' => 'ertonghaofuxuan');
/*$data[] = array('title' => '直&nbsp;&nbsp;&nbsp;选', 'name' => 'zhixuan', 'parent' => 'houer');
$data[] = array('title' => '包胆', 'name' => 'baodan', 'parent' => 'zuxuan', 'mode' => 'houer');*/

//二同号单选
$data[] = array('title' => '二同号单选', 'name' => 'ertonghaodanxuan');
/*
$data[] = array('title' => '直&nbsp;&nbsp;&nbsp;选', 'name' => 'zhixuan', 'parent' => 'qianer');
$data[] = array('title' => '组&nbsp;&nbsp;&nbsp;选', 'name' => 'zuxuan', 'parent' => 'qianer');*/

//二不同号
$data[] = array('title' => '二不同号', 'name' => 'erbutonghao');
$data[] = array('title' => '标准玩法', 'name' => 'biaozhun', 'parent' => 'erbutonghao');
$data[] = array('title' => '标准投注', 'name' => 'biaozhuntouzhu', 'parent' => 'biaozhun', 'mode' => 'erbutonghao',  "headline" => array("二不同号"));
$data[] = array('title' => '胆拖投注', 'name' => 'dantuotouzhu', 'parent' => 'biaozhun', 'mode' => 'erbutonghao',  "headline" => array("二不同号"));

//猜一个号就中奖
$data[] = array('title' => '猜一个号就中奖', 'name' => 'yibutonghao');/*
$data[] = array('title' => '定位胆', 'name' => 'dingweidan', 'parent' => 'yixing');
$data[] = array('title' => '复式', 'name' => 'fushi', 'parent' => 'dingweidan', 'mode' => 'yixing');*/




//根据name查找其子类
//$data 数据列表
//$result 树型结果
//由于parent 属性并不唯一，只能手动循环两层
function findChilds($data){
	$i = 0;
	$len = count($data);
	$top = findTopNode($data);
	$j = 0;
	$tLen = count($top);
	$k = 0;
	$sLen;
	
	for($j = 0; $j < $tLen; $j++){
		//第一层节点
		for($i = 0; $i < $len; $i++){
			if($top[$j]['name'] == $data[$i]['parent'] && empty($data[$i]['mode'])){
				if(empty($top[$j]['childs'])){
					$top[$j]['childs'] = array();
				}
				$top[$j]['childs'][] = $data[$i];
			}
		}
		//第二层节点
		$sLen = count($top[$j]['childs']);
		if($sLen > 0){
			for($k = 0; $k < $sLen; $k++){
				for($i = 0; $i < $len; $i++){
					if($top[$j]['childs'][$k]['name'] == $data[$i]['parent'] && $data[$i]['mode'] && $data[$i]['mode'] == $top[$j]['childs'][$k]['parent']){
						if(empty($top[$j]['childs'][$k]['childs'])){
							$top[$j]['childs'][$k]['childs'] = array();
						}
						$top[$j]['childs'][$k]['childs'][] = $data[$i];
					}
				}
			}
		}
	}
	
	return $top;
}
//获得顶级节点
function findTopNode($data){
	$len = count($data);
	$result = array();
	$i = 0;
	for(;$i < $len;$i++){
		if(empty($data[$i]['parent'])){
			$result[] = $data[$i];
		}
	}
	return $result;
}

//最终输出结果
$result = array(
	//游戏英文名称缩写
	'gameType' => 'k3',
	//中文名称
	'gameTypeCn' => '快乐3',
	//默认启用的玩法
	'defaultMethod' => 'hezhi',
	//所有玩法列表
	'gameMethods' => findChilds($data),
	//游戏后台配置获取地址(倍数限制,期号等)
	'dynamicConfigUrl' => 'simulatedata/dynamicConfig.php',
	//单式上传地址
	'uploadPath' => './simulatedata/insertPreMoney.php'
	
);

$json = json_encode($result);

?>



(function(host, name, Event, undefined){
	var gameConfigData = <?=$json?>;
	var defConfig = {
		//当前彩种名称
		gameType : gameConfigData['gameType'],
		gameTypeCn : gameConfigData['gameTypeCn']
	},
	instance;
	

	var pros = {
		init:function(){
			var me = this;
			me.types = gameConfigData['gameMethods'];
		},
		//获取玩法类型
		getTypes:function(isFilterClose){
			return this.types;
		},
		getGameTypeCn:function(){
			return this.defConfig.gameTypeCn;
		},
		getDefaultMethod:function(){
			return gameConfigData['defaultMethod'];
		},
		getDynamicConfigUrl:function(){
			return gameConfigData['dynamicConfigUrl'];
		},
		getUploadPath:function(){
			return gameConfigData['uploadPath'];
		},
		//name  wuxing.zhixuan.fushi
		getTitleByName:function(name){
			var me = this,
				nameArr = name.split('.'),
				nameLen = nameArr.length,
				types = me.types,
				i = 0,
				len = types.length,
				i2,
				len2,
				i3,
				len3,
				tempArr = [],
				result = [];
			//循环一级
			for(;i < len;i++){
				if(types[i]['name'] == nameArr[0]){
					result.push(types[i]['title'].replace(/&nbsp;/g,''));
					if(nameLen > 1 && types[i]['childs'].length > 0){
						tempArr = types[i]['childs'];
						len2 = tempArr.length;
						//循环二级
						for(i2 = 0;i2 < len2;i2++){
							//console.log(tempArr[i2]['name']);
							if(tempArr[i2]['name'] == nameArr[1]){
								//result.push(tempArr[i2]['title'].replace(/&nbsp;/g,''));
								if(nameLen > 2 && tempArr[i2]['childs'].length > 0){
									tempArr = tempArr[i2]['childs'];
									len3 = tempArr.length;
									//循环三级
									for(i3 = 0;i3 < len3;i3++){
										if(tempArr[i3]['name'] == nameArr[2]){
											if(tempArr[i3]['headline']){
												return tempArr[i3]['headline'];
											}
											result.push(tempArr[i3]['title'].replace(/&nbsp;/g,''));
											return result;
										}
									}
								}else{
									return result;
								}
							}
						}
					}else{
						return result;
					}
				}
			}
			return '';
		}
		
	};
	
	var Main = host.Class(pros, Event);
	Main.defConfig = defConfig;
	Main.getInstance = function(cfg){
		return instance || (instance = new Main(cfg));
	};
	
	host.Games.k3[name] = Main;
	
})(phoenix, "Config", phoenix.Event);










