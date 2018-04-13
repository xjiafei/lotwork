package com.winterframework.modules.test;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.winterframework.modules.test.dbunit.DBUnitFlatXmlHelper;
import com.winterframework.modules.test.spring.SpringContextTestCase;

/**
 * 
 * 本基类主要为子类指定好要装载的spring配置文件
 * 及在运行测试前通过dbunit插入测试数据在数据库中,运行完测试删除测试数据
 *
 * @author paul
 * 请设置好要装载的spring配置文件,一般开发数据库与测试数据库分开
 * 所以你要装载的资源文件应改为"classpath:/spring/*-test-resource.xml"
 */
@ContextConfiguration(locations = { "classpath:/spring/*-test-resource.xml", "classpath:/spring/*-test-dao.xml" })
public class BaseManagerTestCase extends SpringContextTestCase {
	protected DBUnitFlatXmlHelper dbUnitHelper = new DBUnitFlatXmlHelper();

	@Autowired
	protected DataSource dataSource;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Before
	public void onSetUpBeforeTransaction() throws Exception {
		String jdbcSchema = null; // set schema for oracle
		dbUnitHelper.setDataSource(dataSource, jdbcSchema);
		dbUnitHelper.insertTestDatas(getDbUnitDataFiles());
		logger.info("插入测试数据：" + dbUnitHelper.getTestDatas());
	}

	@After
	public void onTearDownAfterTransaction() throws Exception {
		dbUnitHelper.deleteTestDatas();
		logger.info("删除测试数据：" + dbUnitHelper.getTestDatas());
	}

	/** 得到要加载的dbunit文件 */
	protected String[] getDbUnitDataFiles() {
		return new String[] {};
	}
}
