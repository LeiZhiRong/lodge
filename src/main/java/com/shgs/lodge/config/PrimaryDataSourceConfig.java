package com.shgs.lodge.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory", //实体工厂
        transactionManagerRef = "primaryTransactionManager", //事务
        basePackages = { PrimaryDataSourceConfig.REPO_PACKAGE } // 设置 Repository 所在位置
)
public class PrimaryDataSourceConfig {
    static final String REPO_PACKAGE = "com.shgs.lodge.primary.dao";
    static final String ENTITY_PACKAGE = "com.shgs.lodge.primary.entity";

    @Resource(name = "primaryDataSource")
    private DataSource primaryDataSource;

    @Resource
    private JpaProperties jpaProperties;

    @Resource
    private HibernateProperties properties;

    @Primary
    @Bean(name = "primaryEntityManager")
    public EntityManager primaryEntityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(primaryEntityManagerFactory(builder).getObject()).createEntityManager();
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        DataSourceConfig.logDS(primaryDataSource);
        return builder.dataSource(primaryDataSource)
                .properties(properties.determineHibernateProperties(jpaProperties.getProperties(), new
                        HibernateSettings()))
                .persistenceUnit("primaryPersistenceUnit")
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(primaryEntityManagerFactory(builder).getObject()));
    }

}
