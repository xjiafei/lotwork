package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.common.wincaculate.ILotterySlipNumCaculatorContext;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinCaculatorFactory;
import com.winterframework.firefrog.common.wincaculate.ILotteryWinSlipNumCaculator;
import com.winterframework.firefrog.common.wincaculate.IWinResultBean;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.firefrog.game.dao.vo.GameSlipAssist;
import com.winterframework.firefrog.game.exception.GameException;
import com.winterframework.firefrog.game.service.IGameDrawResultService;
import com.winterframework.firefrog.game.service.IGameSlipAssistService;
import com.winterframework.firefrog.game.service.IGameSlipDrawService;
import com.winterframework.firefrog.game.service.IGameSlipService;
import com.winterframework.firefrog.game.service.utils.GameSlipUtilsEnum;
import com.winterframework.firefrog.game.service.utils.GameWinPropertyBean;
import com.winterframework.firefrog.game.service.wincaculate.amount.LotteryWinAmountCaculator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.wincaculate.config.LotterySlipNumCaculatorContext;
import com.winterframework.modules.spring.exetend.PropertyConfig;


/**
 * 注单开奖服务类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月10日
 */
@Service("gameSlipDrawServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
public  class GameSlipDrawServiceImpl implements IGameSlipDrawService {

	private static final Logger log = LoggerFactory.getLogger(GameSlipDrawServiceImpl.class);

	@Resource(name = "gameSlipServiceImpl")
	private IGameSlipService gameSlipService;
	@Resource(name = "gameDrawResultServiceImpl")
	private IGameDrawResultService drawResultService;
	@Resource(name = "lotteryWinSlipNumCaculatorFactory")
	private ILotteryWinCaculatorFactory<ILotteryWinSlipNumCaculator> factory;
	@PropertyConfig(value = "key.seperator")
	private String seperator;
	@Resource(name = "lotteryWinAmountCaculator")
	private LotteryWinAmountCaculator lotteryWinAmountCaculator;
	@Resource(name = "gameSlipAssistServiceImpl")
	private IGameSlipAssistService gameSlipAssistService;
	
	@Override
	public boolean doBusiness(GameContext ctx, GameOrder order,GameSlip slip) throws Exception {
		/**
		 * 1.判断是否中奖
		 * 2.中奖逻辑
		 * 2.不中奖逻辑
		 */
		if(order==null || slip==null){
			throw new GameException("Parameter is null.");
		}; 
		boolean isWin=this.isWin(ctx,order,slip);
		if(isWin){
			this.win(ctx, order, slip);
		}else{
			this.unwin(ctx, order,slip);
		} 		
		return isWin;
	}    
	public boolean isWin(GameContext ctx, GameOrder order, GameSlip slip)
			throws Exception {
		if(order==null || slip==null)  return false; 
		
		Long lotteryId=order.getLotteryid();
		Long issueCode=order.getIssueCode();
		
		log.error("开奖号码没有则获取开奖号码（开奖调用时肯定有值）");
		String winNumber=(String)ctx.get("winNumber");
		if(winNumber==null){
			winNumber=drawResultService.getnumberRecordByLotteryIdAndIssueCode(lotteryId, issueCode);
		}
		
		boolean isWin=false; 
		String[] contents=new String[]{""};
		//获取投注内容
		contents = GameSlipUtilsEnum.INSTANSE.getBetDetai(ctx, order, slip, contents);

		if(contents!=null && contents.length>0){
			//4判断是否中奖。
			//isWin 0 不中奖，大于0为中奖注数
			IWinResultBean winResultBean = null;  
			//20140107 edit 修改支持单式上传投注开奖。
			for (int i = 0; i < contents.length; i++) {
				LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
						slip.getGameGroupCode(), slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
				ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
				context.setKeyGenerator(keyGenerator);
				winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], winNumber, context);

				if (winResultBean!=null && winResultBean.getIsWin()) {
					isWin = true;  
					break;
				}
			}
		} 
		return isWin;
	}
	 
	private int win(GameContext ctx, GameOrder order, GameSlip slip)
			throws Exception {
		//计奖
		doWin(ctx,order,slip);
		/*？？？need？？？GameSlipUtilsEnum.INSTANSE.getWinAmount中已经处理，后续抽出来
		 * //注单辅助
		List<GameSlipAssist> slipAssistList=this.gameSlipAssistService.getBySlipId(ctx, slip.getId());
		if(slipAssistList!=null && slipAssistList.size()>0){
			for(GameSlipAssist slipAssist:slipAssistList){ 
				this.gameSlipAssistService.win(ctx, slip, slipAssist);
			}
		}*/
				
		/*？？？need？？？GameSlipUtilsEnum.INSTANSE.getWinAmount中已经处理，后续抽出来
		 * //更新：中奖注数、注单总奖金、中奖级别、状态
		slip.setWinNumber(winNumber);
		slip.setWinLevel(winLevel);
		slip.setEvaluateWin(evaluateWin);*/
		slip.setStatus(GameSlip.Status.WIN.getValue());
		this.gameSlipService.save(ctx, slip);
		return 0;
	} 
	private int unwin(GameContext ctx,GameOrder order,GameSlip slip)
			throws Exception {
		if(slip==null) return 0;
		//注单辅助
		List<GameSlipAssist> slipAssistList=this.gameSlipAssistService.getBySlipId(ctx, slip.getId());
		if(slipAssistList!=null && slipAssistList.size()>0){
			for(GameSlipAssist slipAssist:slipAssistList){ 
				this.gameSlipAssistService.unwin(ctx, slip, slipAssist);
			}
		}
				
		slip.setEvaluateWin(0L);
		slip.setWinNumber(0L);
		slip.setWinLevel(0L);
		slip.setStatus(GameSlip.Status.UN_WIN.getValue());
		this.gameSlipService.save(ctx, slip); 
		return 0;
	}
	private void doWin(GameContext ctx,GameOrder order,GameSlip slip)	throws Exception {
		String winNumber=(String)ctx.get("winNumber");
		if(winNumber==null){
			throw new GameException("No win number on the game context.");
		}
		GameWinPropertyBean propertyBean = new GameWinPropertyBean(); //注单中奖金额、订单中奖总金额、中奖标识 
		String[] contents = new String[] { "" };//投注内容

		//获取投注内容
		contents = GameSlipUtilsEnum.INSTANSE.getBetDetai(ctx, order, slip, contents); 
		if(contents==null || contents.length==0){
			throw new GameException("the bet content is empty.");
		}
		//4判断是否中奖。
		//isWin 0 不中奖，大于0为中奖注数
		IWinResultBean winResultBean = null;
		for (int i = 0; i < contents.length; i++) {
			LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(slip.getLotteryid(),
					slip.getGameGroupCode(), slip.getGameSetCode(), slip.getBetMethodCode(), seperator);
			ILotterySlipNumCaculatorContext context = new LotterySlipNumCaculatorContext();
			context.setKeyGenerator(keyGenerator);
			winResultBean = factory.getObject(keyGenerator).getWinSlipNum(contents[i], winNumber, context);

			if (winResultBean.getIsWin()) {	//中奖处理逻辑，这里必定ture
				//winResultBean该变量下面处理中仅仅借用信息 
				GameSlipUtilsEnum.INSTANSE.getWinAmount(propertyBean, lotteryWinAmountCaculator, slip, contents[i],
						winResultBean, winNumber);
				//如果是多奖金模式，在开奖时，会将实际奖金设置到该字段中，方便以后直接取用。
				slip.setEvaluateWin(propertyBean.getWinAmout()); 
			}
		}   
	}
}
