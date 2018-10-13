package com.ossjk.asset.base.dao;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.ossjk.asset.base.util.CommonUtil;

/**
 * Druid连接处
 * 
 * @author chair
 *
 */
public class DruidPool {

	private static DruidPool druidPool;
	private DruidDataSource dataSource;
	private Logger logger = Logger.getLogger(DruidPool.class);

	private DruidPool() {
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
			dataSource = new DruidDataSource();
			dataSource.setDriverClassName(prop.getProperty("datasource.driver-class-name"));
			dataSource.setUrl(prop.getProperty("datasource.url"));
			dataSource.setUsername(prop.getProperty("datasource.username"));// 用户名
			dataSource.setPassword(prop.getProperty("datasource.password"));// 密码
			dataSource.setInitialSize(CommonUtil.int2(prop.getProperty("datasource.druid.initialSize", "10")));
			dataSource.setMinIdle(CommonUtil.int2(prop.getProperty("datasource.druid.minIdle", "10")));
			dataSource.setMaxActive(CommonUtil.int2(prop.getProperty("datasource.druid.maxActive", "20")));
			dataSource.setMaxWait(CommonUtil.int2(prop.getProperty("datasource.druid.timeBetweenEvictionRunsMillis", "60000")));
			dataSource.setTimeBetweenEvictionRunsMillis(CommonUtil.int2(prop.getProperty("datasource.druid.maxWait", "3600000")));
			dataSource.setMinEvictableIdleTimeMillis(CommonUtil.int2(prop.getProperty("datasource.druid.minEvictableIdleTimeMillis", "300000")));
			dataSource.setValidationQuery(prop.getProperty("datasource.druid.validationQuery", "select 1 "));
			dataSource.setTestWhileIdle(CommonUtil.isEquals(prop.getProperty("datasource.druid.testWhileIdle", "true"), true));
			dataSource.setTestOnBorrow(CommonUtil.isEquals(prop.getProperty("datasource.druid.testOnBorrow", "true"), true));
			dataSource.setPoolPreparedStatements(CommonUtil.isEquals(prop.getProperty("datasource.druid.poolPreparedStatements", "true"), true));
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(CommonUtil.int2(prop.getProperty("datasource.druid.maxPoolPreparedStatementPerConnectionSize", "20")));
			logger.debug("初始化连接池。。。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("连接池初始化失败。。。");
		}
	}

	public static DruidPool getInstance() {
		if (CommonUtil.isBlank(druidPool)) {
			druidPool = new DruidPool();
		}
		return druidPool;
	}

	public DruidDataSource getDataSource() {
		return dataSource;
	}

	public static void main(String[] args) {
		DruidPool druidPool = new DruidPool();
	}
}
