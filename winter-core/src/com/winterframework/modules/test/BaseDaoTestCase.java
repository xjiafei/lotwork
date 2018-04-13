package com.winterframework.modules.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

import com.winterframework.modules.test.dbunit.DBUnitFlatXmlHelper;
import com.winterframework.modules.test.spring.SpringContextTestCase;

/**
 * 本基类主要为子类指定好要装载的spring配置文件 及在运行测试前通过dbunit插入测试数据在数据库中,运行完测试删除测试数据
 * 
 * @author abba 请设置好要装载的spring配置文件,一般开发数据库与测试数据库分开
 *         所以你要装载的资源文件应改为"classpath:/spring/*-test-resource.xml"
 */
@ContextConfiguration(locations = { "classpath:/spring/*-test-resource.xml", "classpath:/spring/*-test-dao.xml" })
public class BaseDaoTestCase extends SpringContextTestCase {
	@Resource
	private DataSource dataSource;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected DBUnitFlatXmlHelper dbUnitHelper = new DBUnitFlatXmlHelper();

	@BeforeTransaction
	public void onSetUpBeforeTransaction() throws Exception {
		String jdbcSchema = null; // set schema for oracle
		dbUnitHelper.setDataSource(dataSource, jdbcSchema);
		dbUnitHelper.insertTestDatas(getDbUnitDataFiles());
	}

	@AfterTransaction
	public void onTearDownAfterTransaction() throws Exception {
		dbUnitHelper.deleteTestDatas();
	}

	/** 得到要加载的dbunit文件 */
	protected String[] getDbUnitDataFiles() {
		return new String[] {};
	}
}
