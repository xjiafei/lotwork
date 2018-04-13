package amber.queryfundreport.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import amber.queryfundreport.dao.filter.FundChangeLogFilter;
import amber.queryfundreport.enums.FundType;
import amber.queryfundreport.vo.FundChangeLogVo;

public class FundChangeLogDao extends BaseDao{

	public List<FundChangeLogVo> queryFundChangeLog(String month,FundChangeLogFilter filter) throws Exception{
		logger.doLog("queryFundChangeLog start");
		String tableName = "fund_change_log";
		if(month!=null){
			tableName+="_"+month;
		}
		List<FundChangeLogVo> lists = new ArrayList<FundChangeLogVo>();
		try {
			getConnection();
			Statement statement = getStatement();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.sn AS sn\n");
			sql.append(",\n");
			sql.append("b.account AS account\n");
			sql.append(",\n");
			sql.append("TO_CHAR (a.gmt_created, 'YYYY/MM/DD HH24:MI') AS time\n");
			sql.append(",\n");
			sql.append("case substr(a.reason,4,4)\n");
			sql.append("when 'ADAL' then '充值'\n");
			sql.append("when 'ADML' then '充值'\n");
			sql.append("when 'AAXX' then '管理员增'\n");
			sql.append("when 'BDRX' then '撤销派奖'\n");
			sql.append("when 'BIRX' then '转入'\n");
			sql.append("when 'CEXX' then '客户理赔'\n");
			sql.append("when 'CFCX' then '撤单费用'\n");
			sql.append("when 'CRVC' then '投注退款'\n");
			sql.append("when 'CWCR' then '提现退回'\n");
			sql.append("when 'CWCS' then '提现'\n");
			sql.append("when 'CWTF' then '提现'\n");
			sql.append("when 'CWTR' then '提现退回'\n");
			sql.append("when 'CWTS' then '提现'\n");
			sql.append("when 'DAXX' then '管理员减'\n");
			sql.append("when 'DVCB' then '投注扣款'\n");
			sql.append("when 'DVCN' then '投注扣款'\n");
			sql.append("when 'IPXX' then '平台奖励'\n");
			sql.append("when 'MDAX' then '充值'\n");
			sql.append("when 'PDXX' then '奖金派送'\n");
			sql.append("when 'PGXX' then '活动礼金'\n");
			sql.append("when 'RBRC' then '充值让利'\n");
			sql.append("when 'RHAX' then '投注返点'\n");
			sql.append("when 'RRHA' then '撤销返点'\n");
			sql.append("when 'RRSX' then '撤销返点'\n");
			sql.append("when 'RRXX' then '转入'\n");
			sql.append("when 'RSXX' then '投注返点'\n");
			sql.append("when 'SCDX' then '小额扣减'\n");
			sql.append("when 'SCRX' then '小额接收'\n");
			sql.append("when 'SOSX' then '转出'\n");
			sql.append("when 'WPXX' then '转出'\n");
			sql.append("when 'TPXX' then '来自旧平台'\n");
			sql.append("end AS type\n");
			sql.append(",\n");
			sql.append("CASE\n");
			sql.append("WHEN (a.CT_BAL - a.BEFOR_BAL) > 0 THEN (a.CT_BAL - a.BEFOR_BAL) / 10000\n");
			sql.append("WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 THEN NULL\n");
			sql.append("END AS amount\n");
			sql.append(",\n");
			sql.append("(a.CT_DAMT - a.BEFORE_DAMT)/ 10000 AS freezeAmount\n");
			sql.append(",\n");
			sql.append("CASE\n");
			sql.append("WHEN (a.CT_BAL - a.BEFOR_BAL) > 0 THEN NULL\n");
			sql.append("WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 AND (a.CT_DAMT - a.BEFORE_DAMT) < 0 THEN (a.CT_DAMT - a.BEFORE_DAMT) / 10000\n");
			sql.append("WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 AND (a.CT_DAMT - a.BEFORE_DAMT) = 0 AND (a.CT_BAL - a.BEFOR_BAL) != 0 THEN (a.CT_BAL - a.BEFOR_BAL) / 10000\n");
			sql.append("WHEN (a.CT_BAL - a.BEFOR_BAL) < 0 AND (a.CT_DAMT - a.BEFORE_DAMT) = 0 AND (a.CT_BAL - a.BEFOR_BAL) = 0 THEN NULL\n");
			sql.append("END AS payAmount\n");
			sql.append(",\n");
			sql.append("a.CT_BAL/ 10000 AS balanceAmount\n");
			sql.append(",\n");
			sql.append("a.NOTE AS note\n");
			sql.append("FROM "+tableName+" a, user_customer b\n");
			sql.append("WHERE a.user_id = b.id\n");
			if(filter.getAccount()!=null){
				sql.append("AND b.account = '"+filter.getAccount()+"'\n");
			}
			if(filter.getStartDate()!=null){
				sql.append("AND a.gmt_created >= to_date('"+filter.getStartDate()+"','yyyy/mm/dd hh24:mi:ss')\n");
			}
			if(filter.getEndDate()!=null){
				sql.append("AND a.gmt_created <= to_date('"+filter.getEndDate()+"','yyyy/mm/dd hh24:mi:ss')\n");
			}
			if(filter.getTypes()!=null){
				sql.append("AND substr(a.reason,4,4) in(\n");
				List<FundType> types = filter.getTypes();
				for(int i=0;i<types.size();i++){
					String type = types.get(i).name();
					sql.append("'"+type+"'");
					if(i<types.size()-1){
						sql.append(",\n");
					}
				}
				sql.append(")\n");
			}
			if(filter.getOrderby()!=null){
				sql.append("ORDER BY "+filter.getOrderby()+"\n");
			}
			logger.doLog(sql.toString());
			ResultSet rs = statement.executeQuery(sql.toString());
			while (rs.next()) {
				FundChangeLogVo vo = new FundChangeLogVo();
				vo.setAccount(rs.getString("account"));
				vo.setAmount(rs.getDouble("amount"));
				vo.setBalanceAmount(rs.getDouble("balanceAmount"));
				vo.setFreezeAmount(rs.getDouble("freezeAmount"));
				vo.setPayAmount(rs.getDouble("payAmount"));
				vo.setSn(rs.getString("sn"));
				vo.setTime(rs.getString("time"));
				vo.setType(rs.getString("type"));
				vo.setNote(rs.getString("note"));
				lists.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			throw e;
		} finally {
			closeDB();
		}
		logger.doLog("queryFundChangeLog end");
		return lists;
	}
	
}
