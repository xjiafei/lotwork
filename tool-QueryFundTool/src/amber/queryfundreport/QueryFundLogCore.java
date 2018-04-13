package amber.queryfundreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import amber.queryfundreport.dao.FundChangeLogDao;
import amber.queryfundreport.dao.filter.FundChangeLogFilter;
import amber.queryfundreport.enums.FundType;
import amber.queryfundreport.util.ExcelUtil;
import amber.queryfundreport.util.LogUtil;
import amber.queryfundreport.vo.ExcelColumnVo;
import amber.queryfundreport.vo.FundChangeLogVo;

public class QueryFundLogCore {

	LogUtil log = new LogUtil(QueryFundLogCore.class);
	
	private int total = 0;
	
	private int done = 0;
	
	private QueryStatusListener listener;
	
	public QueryFundLogCore(){
		
	}
	
	public QueryFundLogCore(QueryStatusListener listener){
		this.listener = listener;
	}
	
	public void execute(String account,String startTime,String endTime,String... fundTypes) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
		Date startDate = sdf.parse(startTime);
		Date endDate = sdf.parse(endTime);
		int months = getMonthsDifference(startDate,endDate);
		total = months+1;
		done = 0;
		addUILog("=========Query Fund changeLog task start==========");
		for(int i=0;i<months;i++){
			Date date = DateUtils.addMonths(startDate, i);
			exportFundChangeLog(sdf2.format(date),new FundChangeLogFilter(account,startTime,endTime,FundType.getTypes(fundTypes)));
		}
		exportFundChangeLog(null,new FundChangeLogFilter(account,startTime,endTime,FundType.getTypes(fundTypes)));
	}
	
	private void exportFundChangeLog(final String tableDate,final FundChangeLogFilter filter){
		filter.setOrderby("a.gmt_created DESC");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					FundChangeLogDao dao = new FundChangeLogDao();
					List<FundChangeLogVo> result = dao.queryFundChangeLog(
							tableDate, filter);
					if(result.size()>0){
						ExcelUtil<FundChangeLogVo> util = new ExcelUtil<>();
	
						List<ExcelColumnVo> fields = new ArrayList<ExcelColumnVo>();
						fields.add(new ExcelColumnVo("交易流水号", "sn"));
						fields.add(new ExcelColumnVo("用户名", "account"));
						fields.add(new ExcelColumnVo("时间", "time"));
						fields.add(new ExcelColumnVo("摘要", "type"));
						fields.add(new ExcelColumnVo("收入金额", "amount"));
						fields.add(new ExcelColumnVo("冻结金额", "freezeAmount"));
						fields.add(new ExcelColumnVo("支出金额", "payAmount"));
						fields.add(new ExcelColumnVo("可用余额", "balanceAmount"));
						fields.add(new ExcelColumnVo("备注", "note"));
						String fileName = "";
						SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
						String now = sdf.format(new Date());
						if(tableDate!=null){
							fileName = "FUND_"+tableDate+"_"+ filter.getAccount()+"_"+now+ ".xls";
						}else{
							fileName =  "FUND_"+filter.getAccount()+"_"+now+".xls";
						}
						util.exportToExcel(fileName, fields, result);
						addUILog(fileName+":create finished,data size:"+result.size());
					}else{
						addUILog(tableDate+":no any data,do not need create file.");
					}
					done();
				} catch (Exception e) {
					log.doLog(tableDate+":"+e.getMessage());
					addUILog(tableDate+":table not exist,do not need create file.");
					done();
				}
			}
		}).start();
	}
	
	private synchronized void done(){
		done++;
		listener.updateProgress(total,done);
		addUILog("progress:"+done+"/"+total);
		if(done==total){
			addUILog("=========Query Fund changeLog task end==========");
		}
	}
	
	private synchronized void addUILog(String log){
		listener.doLog("QueryFundLog - "+log);
	}
	
	@SuppressWarnings("deprecation")
	private static final int getMonthsDifference(Date date1, Date date2) {
	    int m1 = date1.getYear() * 12 + date1.getMonth();
	    int m2 = date2.getYear() * 12 + date2.getMonth();
	    return m2 - m1 + 1;
	}

}
