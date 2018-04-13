package com.winterframework.orm.dal.ibatis3.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class YesNoTypeHandler extends BaseTypeHandler<Boolean> {
	public static final Integer YES = 1;
	public static final Integer NO = 0;

	public Object valueOf(int s) {
		return yesNoToBoolean(s);
	}

	private Boolean yesNoToBoolean(int s) {
		if (s == YES)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	private Boolean yesNoToBoolean(String ss) {
		Integer s=ss.equalsIgnoreCase("1")?YES:NO;
		if (s == YES)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	private Integer booleanToYesNo(Object b) {
		if (b == null)
			return NO;
		return (Boolean) b ? YES : NO;
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return yesNoToBoolean(rs.getInt(columnName));
	}

	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return yesNoToBoolean(cs.getString(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		setNonNullParameter(ps, i, parameter, jdbcType);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setInt(i, booleanToYesNo(parameter));
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return yesNoToBoolean(rs.getString(columnIndex));
	}

}
