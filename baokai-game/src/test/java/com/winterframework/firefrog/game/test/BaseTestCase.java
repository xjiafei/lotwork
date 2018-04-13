package com.winterframework.firefrog.game.test;
 

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.test.dbunit.DBUnit4OracleFlatXmlHelper;
import com.winterframework.modules.test.dbunit.DBUnitFlatXmlHelper;
import com.winterframework.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "classpath:applicationContext-dao.xml","classpath:applicationContext-resource.xml" })
@TestExecutionListeners({ TransactionalTestExecutionListener.class })
@Transactional
public class BaseTestCase extends SpringContextTestCase {
	protected DBUnitFlatXmlHelper dbUnitHelper = new DBUnit4OracleFlatXmlHelper();
	@Autowired
	private DataSource dataSource;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@BeforeTransaction
	public void onSetUpBeforeTransaction() throws Exception {
		String jdbcSchema = "logistics"; // set schema for oracle
		dbUnitHelper.setDataSource(dataSource, jdbcSchema);
		//dbUnitHelper.insertTestDatas(getDbUnitDataFiles());
		//logger.info(String.valueOf(dbUnitHelper.getTestDatas()));
	}

	@AfterTransaction
	public void onTearDownAfterTransaction() throws Exception {
		//dbUnitHelper.deleteTestDatas();
		//logger.info(String.valueOf(dbUnitHelper.getTestDatas()));
	}

	/** 
	 * 子类负责重写这个方法，把单元测试需要的数据文件名称放到这个方法里面返回
	 */
	protected String[] getDbUnitDataFiles() {
		return new String[] {};
	}
}
