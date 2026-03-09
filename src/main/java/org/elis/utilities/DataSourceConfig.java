package org.elis.utilities;

import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceConfig {
private static DataSource dataSource;

static {
	Properties properties =new Properties();
	properties.setProperty("dataSourceClassName", "com.mysql.cj.jdbc.MysqlDataSource");
	properties.setProperty("dataSource.user","root");
	properties.setProperty("dataSource.password","root");
	properties.setProperty("dataSource.databaseName","taskrabbit");
	properties.setProperty("dataSource.serverName","localhost");
	
	properties.setProperty("maximumPoolSize","10");
	properties.setProperty("minimumIdle","5");
	properties.setProperty("idleTimeout","300000");
	properties.setProperty("connectionTimeout","20000");
HikariConfig config = new HikariConfig(properties);
dataSource=new HikariDataSource(config);
	
	
	
	
	
	
}

public static DataSource getDataSource() {
	return dataSource;
}

}
