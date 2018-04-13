package com.winterframework.orm.dal.ibatis3.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.winterframework.modules.web.util.JsonMapper;

public class JsonTypeHandler extends BaseTypeHandler<Jsonable> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Jsonable parameter, JdbcType jdbcType)
			throws SQLException {
		if (parameter != null)
			ps.setString(i, JsonMapper.nonDefaultMapper().toJson(parameter));
	}

	@Override
	public Jsonable getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String str = rs.getString(columnName);
		if (str == null) {
			return null;
		}
		return JsonMapper.nonDefaultMapper().fromJson(str, Jsonable.class);
	}

	@Override
	public Jsonable getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String str = rs.getString(columnIndex);
		if (str == null) {
			return null;
		}
		return JsonMapper.nonDefaultMapper().fromJson(str, Jsonable.class);
	}

	@Override
	public Jsonable getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String str = cs.getString(columnIndex);
		if (str == null) {
			return null;
		}
		return JsonMapper.nonDefaultMapper().fromJson(str, Jsonable.class);
	}

}