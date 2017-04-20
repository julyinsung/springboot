package com.example;

import javax.sql.DataSource;

import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * datasource�� �����Ѵ�.
 * application.properties�� ������ �����ϰ� �̷��� @Congiguration�� �̿��Ͽ� �����ϴ�.
 * 
 * ����� �Ʒ��� ���� @Profile ������̼��� �̿��Ͽ� ������ �� �� �ִ�.
 * �̰�쿡 �������� ������ jar ����� spring.profiles.active �ɼ��� �̿��ϸ� �ش� �������� �������� ����ȴ�. (ex: java -jar path/abc.jar --spring.profiles.active=local)
 * @author july
 *
 */
@Configuration
public class DataSourceConfiguration {

	@Bean
	public DataSource dataSource(){
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(Driver.load());
		dataSource.setUrl("jdbc:h2:~/test");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
	
	/*
	@Beans
    @Profile("local")
    public DataSource dataSource(Environment environment) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl("jdbc:h2:mem:test;MODE=Oracle;");
        dataSource.setUsername("USERNAME_LOCAL");
        dataSource.setPassword("PASSWORD_LOCAL");

        return dataSource;
    }

    @Bean
    @Profile({"dev", "staging", "production"})
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("ORACLE_URL");
        dataSource.setUsername("USERNAME");
        dataSource.setPassword("PASSWORD");

        return dataSource;
    }
    */
	
	/*	DataSourceTransactionManager ���� �̿��ؼ� Ʈ����� ������ ���������� �Ѱܺ���. �׽�Ʈ�غ��� ����
	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
	    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);

	    return dataSourceTransactionManager;
	}
	*/
}
