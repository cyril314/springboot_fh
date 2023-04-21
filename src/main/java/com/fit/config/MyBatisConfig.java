package com.fit.config;

import com.fit.common.interceptor.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Properties;

/**
 * @className: MyBatisConfig
 * @description:
 * @author: Aim
 * @date: 2023/4/14
 **/
@Configuration
public class MyBatisConfig {

    @Value("${mybatis.mapper-locations}")
    private Resource[] MAPPER_LOCATIONS;
    @Value("${mybatis.type-aliases-package}")
    private String ALIASES_PACKAGE;

    @Bean
    public Interceptor pageInterceptor() {
        PageInterceptor pagePlugin = new PageInterceptor();
        Properties p = new Properties();
        p.put("dialect", "mysql");
        p.put("pageSqlId", ".*listPage.*");
        pagePlugin.setProperties(p);
        return pagePlugin;
    }

    /***
     *	这里定义了SqlSessionFactory，用到了HikariCP配置类里定义的数据源
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(HikariDataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setPlugins(new Interceptor[]{pageInterceptor()});
        factoryBean.setDataSource(dataSource);
        factoryBean.setVfs(SpringBootVFS.class);
        factoryBean.setTypeAliasesPackage(ALIASES_PACKAGE);
        factoryBean.setMapperLocations(MAPPER_LOCATIONS);
        return factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
