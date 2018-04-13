<?php defined('BASEPATH') OR exit('No direct script access allowed');

class ErrHandler {

	protected static $mNumStrings = '0123456789';

	function __construct()
	{
		
	}
	
	public static function getSysInfo($err_code, $is_json_encode = false)
	{
		$msg = array();
		$msg["messagetype"] = $err_code;
		switch($err_code)
		{
			case -97:
				$msg["content"] = "服务器暂时无法连接，请稍后再试，或洽客服人员";
				break;
			case -98:
				$msg["content"] = "连线逾时，请稍后再试";
				break;
			case -99:
				$msg["content"] = "资料格式错误";
				break;
			case -100:
				$msg["content"] = "无法解析资料";
				break;
			default:
				$msg["content"] = "未知错误";
		}
		if($is_json_encode)
		{
			return json_encode($msg);
		}
		else
		{
			return $msg;
		}
	}


	public static function getErrInfo($platform, $err_code, $is_json_encode = false)
	{
		$msg = array();
		$msg["messagetype"] = $err_code==null?"-999":$err_code;
		if($platform == PLATFORM_4)
		{
			switch($err_code)
			{
				case "7":
					$msg["content"] = "连线逾時，请重新登入";
					break;
				case "1000":
					$msg["content"] = "用户提交数据过于频繁";
					break;
				case "1065":
					$msg["content"] = "答案与问题不匹配";
					break;
				case "1072":
					$msg["content"] = "答案与问题不匹配";
					break;
				case "2003":
					$msg["content"] = "提现金额低于下限";
					break;
				case "2006":
					$msg["content"] = "提现次数超过当日次数上限";
					break;
				case "2007":
					$msg["content"] = "银行卡绑定功能被锁定";
					break;
				case "2008":
					$msg["content"] = "绑卡功能达到可绑定上限";
					break;
				case "2010":
					$msg["content"] = "银行卡已绑定";
					break;
				case "2012":
					$msg["content"] = "充值金额高于上限";
					break;
				case "100002":
					$msg["content"] = "用户已存在";
					break;
				case "101001":
					$msg["content"] = "密码错误";
					break;
				case "101002":
					$msg["content"] = "用户名重复";
					break;
				case "101003":
					$msg["content"] = "银行卡卡号不存在";
					break;
				case "101004":
					$msg["content"] = "用户不存在";
					break;
				case "101007":
					$msg["content"] = "密码长度不符合要求";
					break;
				case "102001":
					$msg["content"] = "安全密码错误";
					break;
				case "102002":
					$msg["content"] = "标记已读站内信失败";
					break;
				case "102003":
					$msg["content"] = "注册链接地址失效";
					break;
				case "102004":
					$msg["content"] = "用户已冻结";
					break;
				case "102005":
					$msg["content"] = "解冻失败";
					break;
				case "102006":
					$msg["content"] = "冻结失败";
					break;
				case "102007":
					$msg["content"] = "配额有误";
					break;
				case "102008":
					$msg["content"] = "邮箱已存在";
					break;
				case "102009":
					$msg["content"] = "IP限制";
					break;
				case "109780":
					$msg["content"] = "奖金组ID錯誤";
					break;
				case "109781":
					$msg["content"] = "开户类型为玩家，各彩种只能设置一个奖金组";
					break;
				case "109782":
					$msg["content"] = "玩家身分不支持返点设置功能";
					break;
				case "109783":
					$msg["content"] = "每个彩种至少选一个奖金组";
					break;
				case "109784":
					$msg["content"] = "开户类型为玩家，不支持快捷设置";
					break;
				case "109785":
					$msg["content"] = "奖金组不得为空";
					break;
				case "109786":
					$msg["content"] = "快捷返点范围错误";
					break;
				case "109787":
					$msg["content"] = "链接有效期错误";
					break;
				case "109887":
					$msg["content"] = "目前双色球只支援元模式投注";
					break;
				case "109987":
					$msg["content"] = "本日已敲过蛋";
					break;
				case "109988":
					$msg["content"] = "活动时间已过";
					break;
				case "109989":
					$msg["content"] = "奖金不符规定";
					break;
				case "109990":
					$msg["content"] = "帐号验证错误";
					break;
				case "109991":
					$msg["content"] = "奖金组已设定";
					break;
				case "109992":
					$msg["content"] = "此功能已经冻结";
					break;
				case "109993":
					$msg["content"] = "该银行卡已经绑定";
					break;
				case "109994":
					$msg["content"] = "银行卡已经被锁定";
					break;
				case "109995":
					$msg["content"] = "尚未绑定银行卡";
					break;
				case "109996":
					$msg["content"] = "验证失败";
					break;
				case "109997":
					$msg["content"] = "无法再次设定资金安全密码";
					break;
				case "109998":
					$msg["content"] = "绑卡验证失败";
					break;
				case "109999":
					$msg["content"] = "权限不足";
					break;
				case "110000":
				    $msg["content"] = "安全密码不能与登录密码相同";
				    break;
				case "201001":
					$msg["content"] = "奖期不存在";
					break;
				case "201002":
					$msg["content"] = "方案不存在";
					break;
				case "201003":
					$msg["content"] = "彩种不存在";
					break;
				case "201004":
					$msg["content"] = "当前奖期已停止销售";
					break;
				case "201005":
					$msg["content"] = "奖期状态有误";
					break;
				case "201006":
					$msg["content"] = "当前彩种已停售";
					break;
				case "202001":
					$msg["content"] = "投注金额有误";
					break;
				case "202002":
					$msg["content"] = "返点数据有误";
					break;
				case "202003":
					$msg["content"] = "投注注数错误";
					break;
				case "202004":
					$msg["content"] = "投注内容格式错误";
					break;
				case "202005":
					$msg["content"] = "投注倍数超出限制";
					break;
				case "202006":
					$msg["content"] = "存在相同奖金组名称";
					break;
				case "202007":
					$msg["content"] = "奖期状态有误";
					break;
				case "202008":
					$msg["content"] = "投注方式已暂停";
					break;
				case "203001":
					$msg["content"] = "设置的时间与现有特例奖期规则时间有重叠";
					break;
				case "203002":
					$msg["content"] = "分段奖期时间有重叠项";
					break;
				case "203003":
					$msg["content"] = "开始执行时间早于当前时间";
					break;
				case "203004":
					$msg["content"] = "结束执行时间早于开始执行时间";
					break;
				case "203005":
					$msg["content"] = "新增修改规则必须在当起始时间三天后";
					break;
				case "203006":
					$msg["content"] = "不能存在两条有效待发布常规规则";
					break;
				case "203007":
					$msg["content"] = "分段期间最后一期开奖时间要大于第一期开奖时间";
					break;
				case "203008":
					$msg["content"] = "查询结束时间不得早于开始时间";
					break;
				case "203009":
					$msg["content"] = "过去奖期查询结束时间不得晚于当前起始时间";
					break;
				case "203010":
					$msg["content"] = "未来奖期查询开始时间不得早于当前起始时间";
					break;
				case "203011":
					$msg["content"] = "等待开奖时间不能超过开奖周期";
					break;
				case "204001":
					$msg["content"] = "用户余额不足";
					break;
				case "204002":
					$msg["content"] = "总代不能投注";
					break;
				case "204003":
					$msg["content"] = "用户投注奖金组配置异常";
					break;
				case "204004":
					$msg["content"] = "未选择投注奖金组异常";
					break;
				case "205000":
					$msg["content"] = "注单封锁";
					break;
				case "205001":
					$msg["content"] = "用户级别未找到";
					break;
				case "301001":
					$msg["content"] = "超过订单允许撤销时间";
					break;
				case "901000":
					$msg["content"] = "服务器内部错误";
					break;
				case "902000":
					$msg["content"] = "客户端错误";
					break;
				case "902001":
					$msg["content"] = "参数错误";
					break;
				case "102209":
					$msg["content"] = "登录IP地址错误";
					break;
				default:
					$msg["content"] = "未知错误";
			}
		}
		if($is_json_encode)
		{
			return json_encode($msg);
		}
		else
		{
			return $msg;
		}
	}

}



/* End of file ErrHandler.php */
/* Location: ./application/libraries/ErrHandler.php */