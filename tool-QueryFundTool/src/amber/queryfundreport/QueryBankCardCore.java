package amber.queryfundreport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import amber.queryfundreport.dao.UserBankDao;
import amber.queryfundreport.util.ExcelUtil;
import amber.queryfundreport.util.LogUtil;
import amber.queryfundreport.vo.BankCardVo;
import amber.queryfundreport.vo.ExcelColumnVo;

public class QueryBankCardCore {

	LogUtil log = new LogUtil(QueryBankCardCore.class);
	
	private int total = 1;
	
	private int done = 0;
	
	private QueryStatusListener listener;
	
	public QueryBankCardCore(){
		
	}
	
	public QueryBankCardCore(QueryStatusListener listener){
		this.listener = listener;
	}
	
	public void execute(String[] accounts) throws Exception{
		done = 0;
		addUILog("=========Query bank card task start==========");
		UserBankDao dao = new UserBankDao();
		List<BankCardVo> result = dao.queryUserBanks(accounts);
		if(result.size()>0){
			ExcelUtil<BankCardVo> util = new ExcelUtil<>();
			
			List<ExcelColumnVo> fields = new ArrayList<ExcelColumnVo>();
			fields.add(new ExcelColumnVo("用户名", "account"));
			fields.add(new ExcelColumnVo("卡号", "bankCardNumber"));
			String fileName = "";
			SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
			String now = sdf.format(new Date());
			fileName =  "BANK_"+now+".xls";
			util.exportToExcel(fileName, fields, result);
			addUILog(fileName+":export finsihed");
		}else{
			addUILog("No data,do not need create file.");
		}
		done();
	}
	
	private void done(){
		done++;
		listener.updateProgress(total,done);
		addUILog("progress:"+done+"/"+total);
		if(done==total){
			addUILog("=========Query bank card task end==========");
		}
	}
	
	private void addUILog(String log){
		listener.doLog("QueryBankCard - "+log);
	}

}
