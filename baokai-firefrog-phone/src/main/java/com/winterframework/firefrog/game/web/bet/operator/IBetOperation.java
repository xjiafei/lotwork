package com.winterframework.firefrog.game.web.bet.operator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;


//import com.winterframework.firefrog.game.web.bet.entity.LastNumber;
import com.winterframework.firefrog.game.web.dto.DynamicConfig;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GetAwardResultDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsResultDTO;
import com.winterframework.firefrog.game.web.dto.NumberFrequencyCell;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.PlansStrucForUI;

/**
 * @Title 投注操作接口
 * @Description 定义投注操作所需方法
 * 
 * @author bob
 *
 */
public interface IBetOperation {
	
	/**
	 * 显示投注页面（需加载必要的页面对象）
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String show(Model model) throws Exception;
	
	/**
	 * 加载玩法群、玩法组、投注方式列表,以及彩种基本配置信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getLotteryConfig(Model model) throws Exception;
	
	/**
	 * 加载动态配置
	 * 
	 * @return
	 * @throws Exception
	 */
	public DynamicConfig getDynamicConfig() throws Exception;
	
	/**
	 * 查询用户余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getUserBal() throws Exception;

	/**
	 * 加载首页“我的方案”
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<OrdersStrucDTO> getUserOrders() throws Exception;
	
	/**
	 * 加载首页“我的追号”
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PlansStrucForUI> getUserPlans() throws Exception;
		
	/**
	 * 单式上传
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Object betFile(MultipartFile file) throws Exception;
	
	/**
	 * 获取撤销订单所需撤单手续费
	 * 
	 * @param amount 订单金额
	 * @return
	 * @throws Exception
	 */
	public GameBetJsonResultStruc getCancelOrderCharge(String amount) throws Exception;
	
	/**
	 * 保存订单（追号）
	 * 
	 * @param data
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public GameBetJsonResultStruc save(String data, HttpServletRequest request) throws Exception;
	
	/**
	 * 获取走势辅助图
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public HistoryBallsResultDTO getTrendChart(String type) throws Exception;
	
	/**
	 * @return lotteryCode
	 */
	public String getLotteryCode();

	/**
	 * 
	* @Title: getBetAward 
	* @Description: 查询玩法描述和默认冷热球及用户投注方式奖金
	* @param request
	* @return
	* @throws Exception
	 */
	public GetAwardResultDTO getBetAward(String type, String extent, Integer line, Integer lenth) throws Exception;

	/**
	 * 
	* @Title: frequency 
	* @Description: 查询冷热遗漏号码
	* @param request
	* @return
	* @throws Exception
	 */
	public List<List<NumberFrequencyCell>> frequency(String gameMode, String extent, String frequencyType,
			Integer line, Integer lenth) throws Exception;

	/** 
	* @Title: indexInit 
	* @Description: 登录页面初始化数据使用
	* @return
	 * @throws Exception 
	*/
	public Object indexInit() throws Exception;

	/** 
	* @Title: queryGameUserAwardGroupByLotteryId 
	* @Description:投注进入时查询代理可用奖金组 
	*/
	public Object queryGameUserAwardGroupByLotteryId() throws Exception;

	/** 
	* @Title: saveProxyBetGameAwardGroup 
	* @Description: 保存代理投注奖金组 
	* @param awardGroupId
	* @return
	*/
	public Object saveProxyBetGameAwardGroup(Long awardGroupId) throws Exception;

	public GameBetJsonResultStruc saveMMC(String data, HttpServletRequest request,Long userId, String account) throws Exception;

	/** 
	* @Title: lastNumber 
	* @Description: 获取最新开奖号码 
	* @return
	 * @throws Exception 
	*/
//	public LastNumber lastNumber() throws Exception;

	

}
