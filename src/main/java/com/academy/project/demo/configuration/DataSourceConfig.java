package com.academy.project.demo.configuration;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

//    mysql://bfec0db850ce72:0e7ed4bb@us-cdbr-iron-east-02.cleardb.net/heroku_fca584e1e2b322c?reconnect=true
    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername("bfec0db850ce72");
        hikariConfig.setPassword("0e7ed4bb");
        hikariConfig.setJdbcUrl("jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/heroku_fca584e1e2b322c?reconnect=true&serverTimezone=UTC");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return hikariConfig;
    }

//    @Bean
//    public HikariConfig hikariConfig() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("root");
//        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/ticketService?createDatabaseIfNotExist=true&serverTimezone=UTC");
//        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        return hikariConfig;
//    }


    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }


    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPackagesToScan("com.academy.project.demo.entity");
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setDataSource(dataSource);
        bean.setPersistenceUnitName("project");
        return bean;
    }
}
