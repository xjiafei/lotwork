package amber.genbatis.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import amber.genbatis.vo.TableColumnVO;

public class GetTableDao extends BaseDao{

	public List<TableColumnVO> queryTableAttributes(String tableName) throws Exception{
		logger.doLog("queryTableAttributes start");
		List<TableColumnVO> attributes = new ArrayList<TableColumnVO>();
		try {
			getConnection();
			DatabaseMetaData dbMetaData = getMetaData();
			ResultSet rs = dbMetaData.getColumns(null, getDbUserName().toUpperCase(),
					tableName.toUpperCase(), null);
			while (rs.next()) {
				String name = rs.getString("COLUMN_NAME");
				String type = rs.getString("TYPE_NAME");
				Integer size = rs.getInt("COLUMN_SIZE");
				Integer scale = rs.getInt("DECIMAL_DIGITS");
				TableColumnVO attribute = new TableColumnVO(name,type,size,scale);
				attributes.add(attribute);
			}
			rs.close();
		} catch (Exception e) {
			throw e;
		} finally {
			closeDB();
		}
		logger.doLog("queryTableAttributes end");
		return attributes;
	}
	
}
