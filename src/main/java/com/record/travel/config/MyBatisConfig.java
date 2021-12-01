package com.record.travel.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/*
 * @ComponentScan(basePackages={"com.recoard.travel.service"})
 * 
 * @ComponentScan(basePackages={"com.recoard.travel.commons.aop"})
 * 
 * @EnableAspectJAutoProxy
 * 
 * @EnableTransactionManagement
 */

@MapperScan(basePackages = "com.record.travel.dao")
public class MyBatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setTypeAliasesPackage("com.record.travel.dto");
		
		/*
		 * Resource[] res = new PathMatchingResourcePatternResolver().getResources(
		 * "classpath:mappers/*Mapper.xml");
		 * 
		 * sqlSessionFactory.setMapperLocations(res);
		 */
		 
		return sqlSessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {

		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	
}
