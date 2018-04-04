package com.baomidou.mybatisplus.test.oracle.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2017/7/4
 */
@Configuration
@EnableTransactionManagement
public class DruidOracleDBConfig {

    @Bean
    public DataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:oracle:thin:@192.168.10.169:1521:orcl");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

}
