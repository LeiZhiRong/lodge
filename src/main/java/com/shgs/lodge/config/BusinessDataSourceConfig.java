package com.shgs.lodge.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "businessEntityManagerFactory", //实体工厂
        transactionManagerRef = "businessTransactionManager", //事务
        basePackages = {BusinessDataSourceConfig.REPO_PACKAGE} // 设置 Repository 所在位置
)
public class BusinessDataSourceConfig {
    static final String REPO_PACKAGE = "com.shgs.lodge.business.dao";
    static final String ENTITY_PACKAGE = "com.shgs.lodge.business.entity";

    @Resource(name = "businessDataSource")
    private DataSource businessDataSource;

    @Resource
    private JpaProperties jpaProperties;

    @Resource
    private HibernateProperties properties;

    @Bean(name = "businessEntityManager")
    public EntityManager businessEntityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(businessEntityManagerFactory(builder).getObject()).createEntityManager();
    }

    @Bean(name = "businessEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean businessEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        DataSourceConfig.logDS(businessDataSource);
        return builder.dataSource(businessDataSource)
                .properties(properties.determineHibernateProperties(jpaProperties.getProperties(), new
                        HibernateSettings()))
                .persistenceUnit("businessPersistenceUnit")
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Bean(name = "businessTransactionManager")
    public PlatformTransactionManager businessTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(businessEntityManagerFactory(builder).getObject()));
    }
}
