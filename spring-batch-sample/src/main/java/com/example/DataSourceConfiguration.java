package com.example;

import javax.sql.DataSource;

import org.h2.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * datasource를 설정한다.
 * application.properties에 설정도 가능하고 이렇게 @Congiguration을 이용하여 가능하다.
 * 
 * 참고로 아래와 같이 @Profile 어노테이션을 이용하여 관리할 수 도 있다.
 * 이경우에 프로파일 선택은 jar 실행시 spring.profiles.active 옵션을 이용하면 해당 프로파일 기준으로 실행된다. (ex: java -jar path/abc.jar --spring.profiles.active=local)
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
	
	/*	DataSourceTransactionManager 빈을 이용해서 트랜잭션 관리를 스프링에게 넘겨보자. 테스트해보지 않음
	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
	    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);

	    return dataSourceTransactionManager;
	}
	*/
}
