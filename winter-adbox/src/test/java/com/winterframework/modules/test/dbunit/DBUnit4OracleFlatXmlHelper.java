package com.winterframework.modules.test.dbunit;

import javax.sql.DataSource;

import com.winterframework.modules.test.dbunit.DBUnitFlatXmlHelper;

public class DBUnit4OracleFlatXmlHelper extends DBUnitFlatXmlHelper {
	@Override
	public void setDataSource(DataSource dataSource, String jdbcSchema) throws Exception {
		super.setDataSource(dataSource, jdbcSchema);

		/*DatabaseConnection databaseConnection = (new DatabaseConnection(dataSource.getConnection(), jdbcSchema));
		databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new MySqlDataTypeFactory());
		new Oracle10DataTypeFactory());
		super.setDatabaseConnection(databaseConnection);*/
	}
}
