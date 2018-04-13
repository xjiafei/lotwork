package amber.genbatis.dao;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import amber.genbatis.util.LogUtil;

public class BaseDao {

	
	protected LogUtil logger = new LogUtil(getClass());
	
	private Connection connection;
	
	private Statement statement;
	
	private DatabaseMetaData dbMetaData;
	
	private String driver;
	
	private String dbUrl;
	
	private String userName;
	
	private String passwd;
	
	public BaseDao() {
		Properties properties = new Properties();
		try {
			URL url = ClassLoader.getSystemResource("db.properties");
			properties.load(url.openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = properties.getProperty("db.driver");
		String dbUrl = properties.getProperty("db.url");
		String userName = properties.getProperty("db.username");
		String passwd = properties.getProperty("db.passwd");
		initDBSetting(dbUrl, userName, passwd);
	}
	
	protected void initDBSetting(String dbUrl,String userName,String password){
		this.dbUrl = dbUrl;
		this.userName = userName;
		this.passwd = password;
		logger.doLog("DB URL:"+dbUrl+",UserName:"+userName);
	}

	protected final void getConnection() throws Exception{
		logger.doLog("getConnection...");
		if (connection == null) {
			Class.forName(driver);
			connection = DriverManager.getConnection(dbUrl,userName,passwd);
		}
	}
	
	protected final Statement getStatement() throws Exception{
		logger.doLog("getStatement...");
		if(connection!=null){
			statement = connection.createStatement();
			return statement;
		}else{
			this.getConnection();
			statement = connection.createStatement();
			return statement;
		}
	}
	
	protected final DatabaseMetaData getMetaData() throws Exception{
		logger.doLog("getMetaData...");
		if(connection!=null){
			dbMetaData = connection.getMetaData();
			return dbMetaData;
		}else{
			this.getConnection();
			dbMetaData = connection.getMetaData();
			return dbMetaData;
		}
	}
	
	protected final void closeDB(){
		try {
			if(connection!=null){
				logger.doLog("close connection...");
				connection.close();
				connection = null;
			}
			if(statement!=null){
				logger.doLog("close statement...");
				statement.close();
				statement = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected String getDbUserName() {
		return userName;
	}
	
}
