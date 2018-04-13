package amber.queryfundreport.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import amber.queryfundreport.vo.BankCardVo;

public class UserBankDao extends BaseDao{

	public List<BankCardVo> queryUserBanks(String[]accounts) throws Exception{
		List<BankCardVo> lists = new ArrayList<BankCardVo>();
		logger.doLog("queryUserBanks start");
		try {
			getConnection();
			Statement statement = getStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("select uc.account,ub.bank_number from user_bank ub ");
			sql.append("join user_customer uc on uc.id = ub.user_id ");
			sql.append("where upper(uc.account) in (");
			sql.append("''");
			for(String account:accounts){
				sql.append(",").append("'").append(account.trim().toUpperCase()).append("'");
			}
			sql.append(")");
			logger.doLog(sql.toString());
			ResultSet rs = statement.executeQuery(sql.toString());
			while (rs.next()) {
				BankCardVo vo = new BankCardVo();
				vo.setAccount(rs.getString("account"));
				vo.setBankCardNumber(rs.getString("bank_number"));
				lists.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			throw e;
		} finally {
			closeDB();
		}
		logger.doLog("queryUserBanks end");
		return lists;
	}
	
}
