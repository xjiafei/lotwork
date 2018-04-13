package com.winterframework.orm.dialect;

import org.apache.commons.lang3.StringUtils;

/**
 * @author paul
 */
public class OracleDialect extends Dialect {

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}

	@Override
	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = null;
		if (StringUtils.containsIgnoreCase(sql, "row_number()")) {
			pagingSelect=buildNewSql(sql, offset, offsetPlaceholder, limitPlaceholder,
					isForUpdate);
		} else {
			pagingSelect=buildSql(sql, offset, offsetPlaceholder, limitPlaceholder,
					isForUpdate);
		}

		return pagingSelect.toString();
	}

	private StringBuffer buildSql(String sql, int offset,
			String offsetPlaceholder, String limitPlaceholder,
			boolean isForUpdate) {
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect
				.append("select * from ( select row_.*, rownum rownum_ from ( ");

		pagingSelect.append(sql);
		StringBuffer buffer = new StringBuffer();
		if (offset > 0) {
			// int end = offset+limit;
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			buffer.append(" ) row_ ) where rownum_ <= " + endString
					+ " and rownum_ > " + offsetPlaceholder);
		} else {
			buffer.append(" ) row_ ) where rownum_ <= " + limitPlaceholder);
		}
		pagingSelect.append(buffer);
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect;
	}
	private StringBuffer buildNewSql(String sql, int offset,
			String offsetPlaceholder, String limitPlaceholder,
			boolean isForUpdate) {
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);	
		pagingSelect
				.append("select * from ( ");

		pagingSelect.append(sql);
		StringBuffer buffer = new StringBuffer();
		if (offset > 0) {
			// int end = offset+limit;
			String endString = offsetPlaceholder + "+" + limitPlaceholder;
			buffer.append(") where rownum_ <= " + endString
					+ " and rownum_ > " + offsetPlaceholder);
		} else {
			buffer.append(" ) where rownum_ <= " + limitPlaceholder);
		}
		pagingSelect.append(buffer);
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect;
	}
}
