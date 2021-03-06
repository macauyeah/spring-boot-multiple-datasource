package macauyeah.personal.springbootdatajpa.entitytwo.database.configuration;

import java.util.Map;
import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
// @formatter:off
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityTwoEntityManagerFactory",
    transactionManagerRef = "entityTwoTransactionManager",
    basePackages = {
        "macauyeah.personal.springbootdatajpa.entitytwo.database.entity",
        "macauyeah.personal.springbootdatajpa.entitytwo.database.repository"
    },
    repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
// @formatter:on
public class EntityTwoDataSourceConfiguration {
    @Bean(name = "entityTwoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.macauyeah.personal.entitytwo")
    public DataSource entityTwoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityTwoTransactionManager")
    PlatformTransactionManager entityTwoTransactionManager(
            @Qualifier("entityTwoEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // @formatter:off
    @Bean(name = "entityTwoEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityTwoEntityManagerFactory(
        EntityManagerFactoryBuilder factoryBuilder,
        @Qualifier("entityTwoDataSource") DataSource dataSource,
        @Qualifier("entityTwoJpaVendorAdapter") JpaVendorAdapter vendorAdapter)
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = factoryBuilder
            .dataSource(dataSource)
            .properties(jpaProperties())
            .packages("macauyeah.personal.springbootdatajpa.entitytwo.database.entity")
            .build();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactoryBean;
    }
    // @formatter:on

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        return props;
    }

    @Bean(name = "entityTwoJdbcTemplate")
    public JdbcTemplate entityTwoJdbcTemplate(@Qualifier("entityTwoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = { "entityTwoJpaVendorAdapter" })
    @ConfigurationProperties(prefix = "spring.jpa.macauyeah.personal.entitytwo")
    public JpaVendorAdapter jpaVendorAdapter() {
        return (JpaVendorAdapter) new HibernateJpaVendorAdapter();
    }
}
