package com.shgs.lodge.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * lodge-主数据源配置
     * @return
     */
    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * business-业务数据源配置
     * @return
     */
    @Bean(name = "businessDataSource")
    @Qualifier("businessDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource BusinessDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 显示数据库连接池信息
     * @param dataSource
     */
    public static void logDS(DataSource dataSource) {
        HikariDataSource hds = (HikariDataSource) dataSource;
        String info = "\n\n\tHikariCP连接池配置\n\t连接池名称：" + hds.getPoolName() + "\n\t最小空闲连接数：" + hds.getMinimumIdle() + "\n\t最大连接数：" + hds
                .getMaximumPoolSize() + "\n\t连接超时时间：" + hds.getConnectionTimeout() + "ms\n\t空闲连接超时时间：" + hds.getIdleTimeout()
                + "ms\n\t连接最长生命周期：" + hds.getMaxLifetime() + "ms\n";
        logger.info(info);
    }

}
