package org.springframework.orm.ibatis3;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.ibatis.exceptions.IbatisException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.ibatis3.IbatisSystemException;
import org.springframework.orm.ibatis3.SqlSessionCallback;
import org.springframework.orm.ibatis3.SqlSessionTemplate;
import org.springframework.orm.ibatis3.SqlSessionUtils;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

@SuppressWarnings("deprecation")
public class WinterSqlSessionTemplate extends SqlSessionTemplate{
	
	private static final Logger logger = LoggerFactory.getLogger(WinterSqlSessionTemplate.class);
	
	@Autowired
	private DataSourceTransactionManager transaction;
	
	@Override
	public <T> T execute(SqlSessionCallback<T> action, ExecutorType executorType) throws DataAccessException {
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(getSqlSessionFactory(), "No SqlSessionFactory specified");
		registerTransactionSyncrhonization();
		SqlSession sqlSession = SqlSessionUtils.getSqlSession(getSqlSessionFactory(), getDataSource(), executorType);
		
		try {
		//	checkTransactionTimeout();
			return action.doInSqlSession(sqlSession);
		} catch (Throwable t) {
			throw wrapException(t);
		} finally {
			SqlSessionUtils.closeSqlSession(sqlSession, getSqlSessionFactory());
		}
	}

	private DataAccessException wrapException(Throwable t) {
		if (t instanceof InvocationTargetException) {
			t = t.getCause();
		}
		if (t instanceof IbatisException) {
			Throwable t2 = ((IbatisException) t).getCause();

			if (t2 instanceof SQLException) {
				return getExceptionTranslator().translate("SqlSession operation", null, (SQLException) t2);
			} else {
				return new IbatisSystemException("SqlSession operation", t);
			}
		} else if (t instanceof DataAccessException) {
			return (DataAccessException) t;
		} else {
			return new IbatisSystemException("SqlSession operation", t);
		}
	}
	
	private void checkTransactionTimeout(){
		ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager
				.getResource(transaction.getDataSource());
		
		if(holder!=null && holder.getDeadline()!=null){
			
			if (holder.getDeadline() == null) {
				throw new IllegalStateException("No timeout specified for this resource holder");
			}
			long timeToLive = holder.getDeadline().getTime() - System.currentTimeMillis();
			if (timeToLive <= 0){
				transaction.setRollbackOnCommitFailure(true);
			}
			int seconds = holder.getTimeToLiveInSeconds();
			
			logger.debug("checkTransactionTimeout liveSec:"+seconds);
			Assert.isTrue(seconds > 0);
			
		}
	}

    private void registerTransactionSyncrhonization() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
	        logger.debug("registerTransactionSyncrhonization");
	        TransactionSynchronizationManager.registerSynchronization(new WinterTransactionSynchronizationAdapter());
		}
    }
    
    private class WinterTransactionSynchronizationAdapter extends TransactionSynchronizationAdapter{
		@Override
		public void beforeCommit(boolean readOnly) {
			checkTransactionTimeout();
		}
    }
}