package com.winterframework.firefrog.help.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import com.winterframework.firefrog.help.dao.IHelpExcelReportDao;
import com.winterframework.firefrog.help.dao.vo.OperReportSumVO;
import com.winterframework.firefrog.help.dao.vo.OperVO;
import com.winterframework.firefrog.help.entity.Oper;
import com.winterframework.firefrog.help.entity.OperReportSum;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;


@Repository("helpExcelReportDaoImpl")
public class HelpExcelReportDaoImpl extends BaseIbatis3Dao<OperVO> implements IHelpExcelReportDao {
	


	@Override
	public Page<Oper> selectExcelByDate(PageRequest<String> operPageRequest) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizDate", operPageRequest.getSearchDo());	
	
		Number totalCount=this.sqlSessionTemplate.selectOne("getHelpExcelReportCount",map);
		
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<Oper>(0);
		}
		
		Page<Oper> page = new Page<Oper>(operPageRequest.getPageNumber(), operPageRequest.getPageSize(),
				totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", operPageRequest.getSortColumns());
		filters.putAll(map);
	
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		
		List<OperVO> volist = sqlSessionTemplate.selectList("getHelpExcelReportList", filters, rowBounds);
		
		page.setResult(this.toEntity(volist));
		
		return page;
	}

	
	
	@Override
	public OperReportSum selectExcelReportSum(String bizDate) {
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizDate", bizDate);	
		
		OperReportSumVO operReportSumVO= this.sqlSessionTemplate.selectOne("getReportExcelSum",map);
		
		OperReportSum operReportSum=new OperReportSum();
		operReportSum.setactiveUserCountAVG(operReportSumVO.getactiveUserAVG());
		operReportSum.setBetAmtSum(operReportSumVO.getBetAmtSum());
		operReportSum.setChargeAmtSum(operReportSumVO.getChargeAmtSum());
		operReportSum.setProfitSum(operReportSumVO.getProfitSum());
		operReportSum.setWithdrawAmtSum(operReportSumVO.getWithdrawAmtSum());
		
		return operReportSum;
	}
	
	
	private List<Oper> toEntity(List<OperVO> OperVOList){
		
		List<Oper> operList=new ArrayList<Oper>();
		for (OperVO OperVO : OperVOList) {
					
					operList.add(new Oper(getDate(OperVO.getBizDate()),OperVO.getActiveUserCount(),OperVO.getProfit(),
							OperVO.getBetAmt(),
							OperVO.getChargeAmt(),OperVO.getWithdrawAmt()));
				
				}
		return operList;
	}
	
	
	private String getDate(String timeStramp) {
			
			String ts=timeStramp == null ? null :timeStramp.substring(0,timeStramp.indexOf(".") );
		
			String [] timeArray=ts.split("-");
			
			String time = null;
			for(int i=0;i<timeArray.length;i++) {
				
				if(i==0) {
					time=timeArray[i];
				}else if(i>0) {
					String moth;
					int mm=new Integer(timeArray[i]).intValue();
					if(mm<=9) {
						 moth="0"+mm;
					}else {
						moth=String.valueOf(mm);
					}
					time+="-"+moth;
				}else {
					time+="-"+timeArray[i];
				}
				
			}	
			return time;
		}


}
