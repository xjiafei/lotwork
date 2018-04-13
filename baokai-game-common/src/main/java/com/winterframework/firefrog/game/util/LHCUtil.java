package com.winterframework.firefrog.game.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.enums.GameAwardBetType;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/**
 * 六合彩工具类
 * @author ibm
 * 2016年1月18日
 */
public class LHCUtil { 
	@PropertyConfig("lhc.def.odds")
	private static Long defOdds;
	
	public enum LhcCode{
		/**正码	平码	直选一码*/
		FLATCODE(null),
		/**特码	直选	直选一码*/
		DIRECT(null),
		/**特码	特肖	特肖(副肖)*/
		UNYEAR("副肖"),
		/**特码	特肖	特肖(主肖)*/
		ONYEAR("主肖"),
		/**特码	色波	色波(紅)*/
		RED("红"),
		/**特码	色波	色波(藍)*/
		BLUE("蓝"),
		/**特码	色波	色波(綠)*/
		GREEN("绿"),
		/**特码	色波	半波(紅雙)*/
		REDEVEN("红双"),
		/**特码	色波	半波(紅大)*/
		REDBIG("红大"),
		/**特码	色波	半波(紅小)*/
		REDSMALL("红小"),
		/**特码	色波	半波(紅單)*/
		REDODD("红单"),
		/**特码	色波	半波(藍大)*/
		BLUEBIG("蓝大"),
		/**特码	色波	半波(藍小)*/
		BLUESMALL("蓝小"),
		/**特码	色波	半波(藍單)*/
		BLUEODD("蓝单"),
		/**特码	色波	半波(藍雙)*/
		BLUEEVEN("蓝双"),
		/**特码	色波	半波(綠大)*/
		GREENBIG("绿大"),
		/**特码	色波	半波(綠小)*/
		GREENSMALL("绿小"),
		/**特码	色波	半波(綠單)*/
		GREENODD("绿单"),
		/**特码	色波	半波(綠雙)*/
		GREENEVEN("绿双"),
		/**特码	两面	两面*/
		TWOFACE("两面"),
		/**正特码	一肖	一肖(主肖)*/
		ONONEYEAR("主肖"),
		/**正特码	一肖	一肖(副肖)*/
		UNONEYEAR("副肖"),
		/**正特码	不中	四不中*/
		NOTIN4(null),
		/**正特码	不中	五不中*/
		NOTIN5(null),
		/**正特码	不中	六不中*/
		NOTIN6(null),
		/**正特码	不中	七不中*/
		NOTIN7(null),
		/**正特码	不中	八不中*/
		NOTIN8(null),
		/**正特码	连肖(中)	二连肖 (副肖)*/
		UNCONTINUEIN2("副肖"),
		/**正特码	连肖(中)	三连肖 (副肖)*/
		UNCONTINUEIN3("副肖"),
		/**正特码	连肖(中)	四连肖 (副肖)*/
		UNCONTINUEIN4("副肖"),
		/**正特码	连肖(中)	五连肖 (副肖)*/
		UNCONTINUEIN5("副肖"),
		/**正特码	连肖(中)	二连肖(主肖)*/
		ONCONTINUEIN2("主肖"),
		/**正特码	连肖(中)	三连肖(主肖)*/
		ONCONTINUEIN3("主肖"),
		/**正特码	连肖(中)	四连肖(主肖)*/
		ONCONTINUEIN4("主肖"),
		/**正特码	连肖(中)	五连肖(主肖)*/
		ONCONTINUEIN5("主肖"),
		/**正特码	连肖(不中)	二连肖 (副肖)*/
		UNCONTINUENOTIN2("副肖"),
		/**正特码	连肖(不中)	三连肖 (副肖)*/
		UNCONTINUENOTIN3("副肖"),
		/**正特码	连肖(不中)	四连肖 (副肖)*/
		UNCONTINUENOTIN4("副肖"),
		/**正特码	连肖(不中)	五连肖 (副肖)*/
		UNCONTINUENOTIN5("副肖"),
		/**正特码	连肖(不中)	二连肖(主肖)*/
		ONCONTINUENOTIN2("主肖"),
		/**正特码	连肖(不中)	三连肖(主肖)*/
		ONCONTINUENOTIN3("主肖"),
		/**正特码	连肖(不中)	四连肖(主肖)*/
		ONCONTINUENOTIN4("主肖"),
		/**正特码	连肖(不中)	五连肖(主肖)*/
		ONCONTINUENOTIN5("主肖"),
		/**三全中*/
		CONTINUECODE333(null),
		/**三中二(中二)*/
		CONTINUECODE322(null),
		/**三中二(中三*/
		CONTINUECODE323(null),
		/**二全中*/
		CONTINUECODE2Z(null),
		/**二中特(中二)*/
		CONTINUECODE22Z(null),
		/**二中特(中特)*/
		CONTINUECODE22T(null),
		/**特串*/
		CONTINUECODE2T(null);
		
		/**一定要符合的投注明細*/
		private String mustKeepingWith;
		
		LhcCode(String mustKeepingWith){
			this.mustKeepingWith = mustKeepingWith;
		}

		public String getMustKeepingWith() {
			return mustKeepingWith;
		}

		public void setMustKeepingWith(String mustKeepingWith) {
			this.mustKeepingWith = mustKeepingWith;
		}
		
		/**
		 * 依據 lhcCode取得輔助玩法中文名稱。
		 * @param lhcCode 
		 * @return lhcCode 簡中
		 */
		public static String getlhcCodeName(String lhcCode) {

			if("UNYEAR".equals(lhcCode)) {
				return UNYEAR.getMustKeepingWith();
			} else if("ONYEAR".equals(lhcCode)) {
				return ONYEAR.getMustKeepingWith();
			} else if("RED".equals(lhcCode)) {
				return RED.getMustKeepingWith();
			} else if("BLUE".equals(lhcCode)) {
				return BLUE.getMustKeepingWith();
			} else if("GREEN".equals(lhcCode)) {
				return GREEN.getMustKeepingWith();
			} else if("REDEVEN".equals(lhcCode)) {
				return REDEVEN.getMustKeepingWith();
			} else if("REDBIG".equals(lhcCode)) {
				return REDBIG.getMustKeepingWith();
			} else if("REDSMALL".equals(lhcCode)) {
				return REDSMALL.getMustKeepingWith();
			} else if("REDODD".equals(lhcCode)) {
				return REDODD.getMustKeepingWith();
			} else if("BLUEBIG".equals(lhcCode)) {
				return BLUEBIG.getMustKeepingWith();
			} else if("BLUESMALL".equals(lhcCode)) {
				return BLUESMALL.getMustKeepingWith();
			} else if("BLUEODD".equals(lhcCode)) {
				return BLUEODD.getMustKeepingWith();
			} else if("BLUEEVEN".equals(lhcCode)) {
				return BLUEEVEN.getMustKeepingWith();
			} else if("GREENBIG".equals(lhcCode)) {
				return GREENBIG.getMustKeepingWith();
			} else if("GREENSMALL".equals(lhcCode)) {
				return GREENSMALL.getMustKeepingWith();
			} else if("GREENODD".equals(lhcCode)) {
				return GREENODD.getMustKeepingWith();
			} else if("GREENEVEN".equals(lhcCode)) {
				return GREENEVEN.getMustKeepingWith();
			} else if("TWOFACE".equals(lhcCode)) {
				return TWOFACE.getMustKeepingWith();
			} else if("ONONEYEAR".equals(lhcCode)) {
				return ONONEYEAR.getMustKeepingWith();
			} else if("UNONEYEAR".equals(lhcCode)) {
				return UNONEYEAR.getMustKeepingWith();
			} else if("UNCONTINUEIN2".equals(lhcCode)) {
				return UNCONTINUEIN2.getMustKeepingWith();
			} else if("UNCONTINUEIN3".equals(lhcCode)) {
				return UNCONTINUEIN3.getMustKeepingWith();
			} else if("UNCONTINUEIN4".equals(lhcCode)) {
				return UNCONTINUEIN4.getMustKeepingWith();
			} else if("UNCONTINUEIN5".equals(lhcCode)) {
				return UNCONTINUEIN5.getMustKeepingWith();
			} else if("ONCONTINUEIN2".equals(lhcCode)) {
				return ONCONTINUEIN2.getMustKeepingWith();
			} else if("ONCONTINUEIN3".equals(lhcCode)) {
				return ONCONTINUEIN3.getMustKeepingWith();
			} else if("ONCONTINUEIN4".equals(lhcCode)) {
				return ONCONTINUEIN4.getMustKeepingWith();
			} else if("ONCONTINUEIN5".equals(lhcCode)) {
				return ONCONTINUEIN5.getMustKeepingWith();
			} else if("UNCONTINUENOTIN2".equals(lhcCode)) {
				return UNCONTINUENOTIN2.getMustKeepingWith();
			} else if("UNCONTINUENOTIN3".equals(lhcCode)) {
				return UNCONTINUENOTIN3.getMustKeepingWith();
			} else if("UNCONTINUENOTIN4".equals(lhcCode)) {
				return UNCONTINUENOTIN4.getMustKeepingWith();
			} else if("UNCONTINUENOTIN5".equals(lhcCode)) {
				return UNCONTINUENOTIN5.getMustKeepingWith();
			} else if("ONCONTINUENOTIN2".equals(lhcCode)) {
				return ONCONTINUENOTIN2.getMustKeepingWith();
			} else if("ONCONTINUENOTIN3".equals(lhcCode)) {
				return ONCONTINUENOTIN3.getMustKeepingWith();
			} else if("ONCONTINUENOTIN4".equals(lhcCode)) {
				return ONCONTINUENOTIN4.getMustKeepingWith();
			} else if("ONCONTINUENOTIN5".equals(lhcCode)) {
				return ONCONTINUENOTIN5.getMustKeepingWith();
			} else {
				return null;
			}
		}
	}
	
	public enum BetTypeCodeMapping {
		/**正码-平码-直选六码*/
		lhc_53_17_64("正码-平码-直选六码", GameAwardBetType.LHC_FLATCODE, 1L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcFlatcode();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.FLATCODE.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**特码-特肖-特肖*/
		lhc_54_18_82("特码-特肖-特肖", GameAwardBetType.LHC_YEAR, 1L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcYear();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				if(getSpecialFlag(gameSlip.getBetDetail(), configs)){
					//主肖
					return betTypeGameAwards.get(LhcCode.ONYEAR.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				} else {
					//副肖
					return betTypeGameAwards.get(LhcCode.UNYEAR.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				}
			}
		},
		/**特码-色波-色波*/
		lhc_54_19_84("特码-色波-色波", GameAwardBetType.LHC_COLOR, 1L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcColor();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				if(LhcCode.RED.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.RED.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.BLUE.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.BLUE.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.GREEN.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.GREEN.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else
					return Boolean.FALSE;
			}
		},
		/**特码-色波-半波*/
		lhc_54_19_85("特码-色波-半波", GameAwardBetType.LHC_HALFWAVE, 1L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcHalfwave();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				if(LhcCode.REDBIG.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.REDBIG.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.REDEVEN.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.REDEVEN.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.REDODD.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.REDODD.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.REDSMALL.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.REDSMALL.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if(LhcCode.BLUEBIG.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.BLUEBIG.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.BLUEEVEN.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.BLUEEVEN.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.BLUEODD.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.BLUEODD.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.BLUESMALL.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.BLUESMALL.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if(LhcCode.GREENBIG.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.GREENBIG.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.GREENEVEN.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.GREENEVEN.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.GREENODD.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.GREENODD.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else if (LhcCode.GREENSMALL.getMustKeepingWith().equals(gameSlip.getBetDetail()))
					return betTypeGameAwards.get(LhcCode.GREENSMALL.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				else
					return Boolean.FALSE;
			}
		},
		/**特码-两面-两面*/
		lhc_54_37_83("特码-两面-两面", GameAwardBetType.LHC_COLOR, 1L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcColor();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.TWOFACE.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**正特码-一肖-一肖*/
		lhc_55_38_86("正特码-一肖-一肖", GameAwardBetType.LHC_ONEYEAR, 1L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcOneyear();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				if(getSpecialFlag(gameSlip.getBetDetail(), configs)){
					//主肖
					return betTypeGameAwards.get(LhcCode.ONONEYEAR.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				} else {
					//副肖
					return betTypeGameAwards.get(LhcCode.UNONEYEAR.toString()).getActualBonus().equals(gameSlip.getSingleWin());
				}
			}
		},
		/**正特码-不中-四不中*/
		lhc_55_39_87("正特码-不中-四不中", GameAwardBetType.LHC_NOTIN, 4L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcNotin();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.NOTIN4.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**正特码-不中-五不中*/
		lhc_55_39_88("正特码-不中-五不中", GameAwardBetType.LHC_NOTIN, 5L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcNotin();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.NOTIN5.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**正特码-不中-六不中*/
		lhc_55_39_89("正特码-不中-六不中", GameAwardBetType.LHC_NOTIN, 6L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcNotin();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.NOTIN6.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**正特码-不中-七不中*/
		lhc_55_39_90("正特码-不中-七不中", GameAwardBetType.LHC_NOTIN, 7L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcNotin();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.NOTIN7.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**正特码-不中-八不中*/
		lhc_55_39_91("正特码-不中-八不中", GameAwardBetType.LHC_NOTIN, 8L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcNotin();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.NOTIN8.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**正特码-连肖(中)-二连肖*/
		lhc_55_40_92("正特码-连肖(中)-二连肖", GameAwardBetType.LHC_CONTINUEIN23, 2L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuein23();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(中)-三连肖*/
		lhc_55_40_93("正特码-连肖(中)-三连肖", GameAwardBetType.LHC_CONTINUEIN23, 3L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuein23();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(中)-四连肖*/
		lhc_55_40_94("正特码-连肖(中)-四连肖", GameAwardBetType.LHC_CONTINUEIN4, 4L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuein4();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(中)-五连肖*/
		lhc_55_40_95("正特码-连肖(中)-五连肖", GameAwardBetType.LHC_CONTINUEIN5, 5L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuein5();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(不中)-二连肖*/
		lhc_55_41_92("正特码-连肖(不中)-二连肖", GameAwardBetType.LHC_CONTINUENOTIN23, 2L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuenotin23();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(不中)-三连肖*/
		lhc_55_41_93("正特码-连肖(不中)-三连肖", GameAwardBetType.LHC_CONTINUENOTIN23, 3L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuenotin23();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(不中)-四连肖*/
		lhc_55_41_94("正特码-连肖(不中)-四连肖", GameAwardBetType.LHC_CONTINUENOTIN4, 4L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuenotin4();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**正特码-连肖(不中)-五连肖*/
		lhc_55_41_95("正特码-连肖(不中)-五连肖", GameAwardBetType.LHC_CONTINUENOTIN5, 5L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuenotin5();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**连码-连码-三全中*/
		lhc_56_42_13("连码-连码-三全中", GameAwardBetType.LHC_CONTINUECODE, 3L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuecode();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.CONTINUECODE333.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**连码-连码-三中二*/
		lhc_56_42_14("连码-连码-三中二", GameAwardBetType.LHC_CONTINUECODE, 3L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuecode();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**连码-连码-二全中*/
		lhc_56_42_15("连码-连码-二全中", GameAwardBetType.LHC_CONTINUECODE, 2L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuecode();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.CONTINUECODE2Z.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		},
		/**连码-连码-二中特*/
		lhc_56_42_16("连码-连码-二中特", GameAwardBetType.LHC_CONTINUECODE, 2L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuecode();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return gameSlip.getSingleWin().equals(0L);
			}
		},
		/**连码-连码-特串*/
		lhc_56_42_17("连码-连码-特串", GameAwardBetType.LHC_CONTINUECODE, 2L) {
			public Long getRetValue(GameUserAwardGroup gameUserAwardGroup) {return gameUserAwardGroup.getLhcContinuecode();}
			public Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
				return betTypeGameAwards.get(LhcCode.CONTINUECODE2T.toString()).getActualBonus().equals(gameSlip.getSingleWin());
			}
		};
		/**玩法描述*/
		private String content;
		/**獎金組明細投注類型*/
		private GameAwardBetType gameAwardBetType;
		/**最低選球數*/
		private Long minBalls;
		
		BetTypeCodeMapping(String content, GameAwardBetType gameAwardBetType, Long minBalls){
			this.content = content;
			this.gameAwardBetType = gameAwardBetType;
			this.minBalls = minBalls;
		}
		
		/**
		 * 取得返點
		 * @param gameUserAwardGroup
		 * @return
		 */
		public abstract Long getRetValue(GameUserAwardGroup gameUserAwardGroup);
		
		/**
		 * 取得該玩法所對應的賠率驗證
		 * @param betTypeGameAwards
		 * @return
		 */
		public abstract Boolean validSingleWinByMapping(final Map<String, GameAward> betTypeGameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs);
		
		/**
		 * 檢查玩法所對應的獎金明細賠率 是否符合投注內容
		 * @param betTypeCode
		 * @param gameAwards
		 * @param gameSlip
		 * @param configs
		 * @return
		 */
		public Boolean validSingleWin(final String betTypeCode, final List<GameAward> gameAwards, final GameSlip gameSlip, final List<GameNumberConfig> configs){
			BetTypeCodeMapping betTypeCodeMapping = findMappingBetType(betTypeCode);
			Map<String, GameAward> betTypeGameAwards = filterGameAwardsByBetTypeCode(betTypeCode, gameAwards);
			return betTypeCodeMapping.validSingleWinByMapping(betTypeGameAwards, gameSlip, configs);
		}
		
		/**
		 * 取得玩法所對應的獎金明細
		 * @param betTypeCode
		 * @param gameAwards
		 * @return
		 */
		public static Map<String, GameAward> filterGameAwardsByBetTypeCode(final String betTypeCode, final List<GameAward> gameAwards){
			List<GameAward> newAwards = new ArrayList<GameAward>(gameAwards);
			for(int i = 0 ; i < newAwards.size() ; i++){
				GameAward newAward = newAwards.get(i);
				if(betTypeCode.equals(newAward.getBetTypeCode())){
					
				} else {
					newAwards.remove(i);
					i--;
				}
			}
			
			Map<String, GameAward> tempGameAwards = new HashMap<String, GameAward>();
			for(GameAward newAward : newAwards){
				tempGameAwards.put(newAward.getLhcCode(), newAward);
			}
			
			return tempGameAwards;
		}
		
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public GameAwardBetType getGameAwardBetType() {
			return gameAwardBetType;
		}

		public void setGameAwardBetType(GameAwardBetType gameAwardBetType) {
			this.gameAwardBetType = gameAwardBetType;
		}
		/**
		 * 取得最低選球數。
		 * @return
		 */
		public Long getMinBalls() {
			return minBalls;
		}
		/**
		 * 依據傳入的 betTypeCode 取得對應的 Enum Object。
		 * @param betTypeCode 投注方式編碼
		 * @return
		 */
		public static BetTypeCodeMapping findMappingBetType(String betTypeCode){
			for(BetTypeCodeMapping mapping : BetTypeCodeMapping.values()){
				if(mapping.toString().indexOf(betTypeCode) > -1)
					return mapping;
			}
			return null;
		}
		
		/**
		 * 此 Enum Object 是否與傳入的 betTypeCode 相同
		 * @param betTypeCode 投注方式編碼
		 * @return true:相同、false:不同
		 */
		public static boolean isMappingBetType(String betTypeCode){
			for(BetTypeCodeMapping mapping : BetTypeCodeMapping.values()){
				if(mapping.toString().indexOf(betTypeCode) > -1)
					return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 生肖(生肖排序、簡中描述、英文譯音描述)
	 * @author Pogi.Lin
	 */
	public enum Zodiac {
		/**1, shu, 鼠*/
		MOUSE(1, "shu", "鼠"),
		/**2, niu, 牛*/
		COW(2, "niu", "牛"),
		/**3, hu, 虎*/
		TIGER(3, "hu", "虎"),
		/**4, tu, 兔*/
		RABBIT(4, "tu", "兔"),
		/**5, long, 龙*/
		DRAGON(5, "long", "龙"),
		/**6, she, 蛇*/
		SNAKE(6, "she", "蛇"),
		/**7, ma, 马*/
		HORSE(7, "ma", "马"),
		/**8, yang, 羊*/
		SHEEP(8, "yang", "羊"),
		/**9, hou, 猴*/
		MONKEY(9, "hou", "猴"),
		/**10, ji, 鸡*/
		CHICKEN(10, "ji", "鸡"),
		/**11, gou, 狗*/
		DOG(11, "gou", "狗"),
		/**12, zhu, 猪*/
		PIG(12, "zhu", "猪");
		
		/**生肖排序*/
		private int index;
		/**英文譯音描述*/
		private String enName;
		/**簡中描述*/
		private String cnName;
		
		Zodiac(int index, String enName, String cnName) {
			this.index = index;
			this.enName = enName;
			this.cnName = cnName;
		}
		
		/**
		 * 取得生肖排序。
		 * @return
		 */
		public int getIndex() {
			return index;
		}
		
		/**
		 * 依據 cnName 取得生肖排序。
		 * @param cnName 簡中描述
		 * @return 對應不到生肖回傳 -1
		 */
		public static int getIndex(String cnName) {
			if(MOUSE.getCnName().equals(cnName)) {
				return MOUSE.getIndex();
			} else if(COW.getCnName().equals(cnName)) {
				return COW.getIndex();
			} else if(TIGER.getCnName().equals(cnName)) {
				return TIGER.getIndex();
			} else if(RABBIT.getCnName().equals(cnName)) {
				return RABBIT.getIndex();
			} else if(DRAGON.getCnName().equals(cnName)) {
				return DRAGON.getIndex();
			} else if(SNAKE.getCnName().equals(cnName)) {
				return SNAKE.getIndex();
			} else if(HORSE.getCnName().equals(cnName)) {
				return HORSE.getIndex();
			} else if(SHEEP.getCnName().equals(cnName)) {
				return SHEEP.getIndex();
			} else if(MONKEY.getCnName().equals(cnName)) {
				return MONKEY.getIndex();
			} else if(CHICKEN.getCnName().equals(cnName)) {
				return CHICKEN.getIndex();
			} else if(DOG.getCnName().equals(cnName)) {
				return DOG.getIndex();
			} else if(PIG.getCnName().equals(cnName)) {
				return PIG.getIndex();
			} else {
				return -1;
			}
		}
		
		/**
		 * 取得英文譯音描述。
		 * @return
		 */
		public String getEnName() {
			return enName;
		}
		
		/**
		 * 依據 cnName 取得英文譯音描述。
		 * @param cnName 簡中描述
		 * @return 對應不到生肖回傳 null
		 */
		public static String getEnName(String cnName) {
			if(MOUSE.getCnName().equals(cnName)) {
				return MOUSE.getEnName();
			} else if(COW.getCnName().equals(cnName)) {
				return COW.getEnName();
			} else if(TIGER.getCnName().equals(cnName)) {
				return TIGER.getEnName();
			} else if(RABBIT.getCnName().equals(cnName)) {
				return RABBIT.getEnName();
			} else if(DRAGON.getCnName().equals(cnName)) {
				return DRAGON.getEnName();
			} else if(SNAKE.getCnName().equals(cnName)) {
				return SNAKE.getEnName();
			} else if(HORSE.getCnName().equals(cnName)) {
				return HORSE.getEnName();
			} else if(SHEEP.getCnName().equals(cnName)) {
				return SHEEP.getEnName();
			} else if(MONKEY.getCnName().equals(cnName)) {
				return MONKEY.getEnName();
			} else if(CHICKEN.getCnName().equals(cnName)) {
				return CHICKEN.getEnName();
			} else if(DOG.getCnName().equals(cnName)) {
				return DOG.getEnName();
			} else if(PIG.getCnName().equals(cnName)) {
				return PIG.getEnName();
			} else {
				return null;
			}
		}
		
		/**
		 * 取得簡中描述。
		 * @return
		 */
		public String getCnName() {
			return cnName;
		}
	}
	
	/**
	 * 判斷是否為主肖
	 * @param betXaio
	 * @param configs
	 * @return "Y" : 主肖, "N" : 副肖
	 */
	public static Boolean getSpecialFlag(final String betXaio, final List<GameNumberConfig> configs){
		String result = "N";
		for(GameNumberConfig config : configs){
			if(config.getNumType().equals(betXaio)){
				result = config.getSpecialFlag();
			}
		}
		return result.equals("Y");
	}
	
	/**
	 * 是否為六合彩的投注方式編碼
	 * @param betTypeCode
	 * @return
	 */
	public static boolean isLhcBetTypeCode(String betTypeCode){
		return BetTypeCodeMapping.isMappingBetType(betTypeCode);
	}
	
	public static BetTypeCodeMapping findLhcBetTypeCode(String betTypeCode){
		return BetTypeCodeMapping.findMappingBetType(betTypeCode);
	}
	
	/**
	 * 取得六合彩直選一碼的返點
	 * @param slipRet 注單明細的單注獎金
	 * @param awardRet 返點
	 * @return
	 */
	public static Long getZhixuanRet(Long slipRet,Long awardRet){
		Long odds = 40l;
		Long userRet = 0l;
		for (Long x = awardRet ; x >= 0; x -= 200) {
			if(odds == slipRet){
				userRet = x;
				break;
			}
			odds++;
		}
		return userRet;
	}
	
	/**
	 * 取得不重複的 betDetail 號球排列組合。
	 * @param betDetail 投注內容
	 * @param sizeCombinations 號碼組合最大數
	 * @return
	 */
	public static List<String> combinationsAllBet(String betDetail, Integer sizeCombinations){
		List<String> tempBetDetails = null;
		if(betDetail.indexOf("胆:") > -1){
			//胆拖  3
			//dan:42;tuo:04,16,28,40
			String danMa = betDetail.split(";")[0].replaceAll("胆:", "");
			String tuoMa = betDetail.split(";")[1].replaceAll("拖:", "");
			String[] danMas = danMa.split(",");
			String[] tuoMas = tuoMa.split(",");
			//展開投注內容  1,2,3
			tempBetDetails = CaculateUtil.getCombinations(Arrays.asList(tuoMas), sizeCombinations - danMas.length);
			List<String> danTuos = new ArrayList<String>();
			for (String tempBetDetail : tempBetDetails) {
				danTuos.add(danMa + "," + tempBetDetail);
			}
			tempBetDetails = danTuos;
		} else {
			//複式
			String[] bets = betDetail.split(",");
			tempBetDetails = CaculateUtil.getCombinations(Arrays.asList(bets), sizeCombinations);
		}
		return tempBetDetails;
	}
	
	/**
	 * 判斷是否為連肖(不中)玩法。
	 * @param betTypeCode 投注方式編碼
	 * @return 是:true、否:false
	 */
	public static boolean isUncontinueNotin(String betTypeCode) {
		if(BetTypeCodeMapping.lhc_55_41_92.name().contains(betTypeCode)||
		   BetTypeCodeMapping.lhc_55_41_93.name().contains(betTypeCode)||
		   BetTypeCodeMapping.lhc_55_41_94.name().contains(betTypeCode)||
		   BetTypeCodeMapping.lhc_55_41_95.name().contains(betTypeCode)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判斷是否為不中玩法。
	 * @param betTypeCode 投注方式編碼
	 * @return 是:true、否:false
	 */
	public static boolean isNotin(String betTypeCode) {
		if(BetTypeCodeMapping.lhc_55_39_87.name().contains(betTypeCode) ||
		   BetTypeCodeMapping.lhc_55_39_88.name().contains(betTypeCode) ||
		   BetTypeCodeMapping.lhc_55_39_89.name().contains(betTypeCode) ||
		   BetTypeCodeMapping.lhc_55_39_90.name().contains(betTypeCode) ||
		   BetTypeCodeMapping.lhc_55_39_91.name().contains(betTypeCode)) {
			return true;
		} else {
			return false;
		}
	}

	public static Set<String> tranferShenXiao(List<GameNumberConfig> gameNumberConfig,List<String> numberRecordList) {
		LinkedList<String> xiaos = new LinkedList<String>();
		Set<String> shengXiaos = new LinkedHashSet<String>();
		for(String number : numberRecordList){
			for(GameNumberConfig shenXiao : gameNumberConfig){
				if(shenXiao.getGameNumber().indexOf(number) >-1){
					xiaos.add(shenXiao.getNumType());
				}
			}
		}
		//將特碼開出的生肖放到最後一個，以利小工具比對
		for(int i=0;i<xiaos.size()-1 ; i++){
			if(xiaos.get(i).equals(xiaos.getLast())){
				
			}else{
				shengXiaos.add(xiaos.get(i));
			}
		}
		shengXiaos.add(xiaos.getLast());
		return shengXiaos;
	}
}
