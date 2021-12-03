package macauyeah.personal.springbootdatajpa.applicationRunner.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    entityManagerFactoryRef = "localOneEntityManagerFactory",
    transactionManagerRef = "localOneTransactionManager",
    basePackages = {
        "macauyeah.personal.springbootdatajpa.localone.database.entity",
        "macauyeah.personal.springbootdatajpa.localone.database.repository"
    }
)
// @formatter:on
public class LocalDummyJpaConfig { // won't work if no primary data defined, so create a dummy primary here
    @Bean(name = "localOneDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.macauyeah.personal.localone")
    public DataSource localOneDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "localOneTransactionManager")
    PlatformTransactionManager localOneTransactionManager(
            @Qualifier("localOneEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // @formatter:off
    @Bean(name = "localOneEntityManagerFactory")
    @Primary
    LocalContainerEntityManagerFactoryBean localOneEntityManagerFactory(
        EntityManagerFactoryBuilder factoryBuilder,
        @Qualifier("localOneDataSource") DataSource dataSource,
        @Qualifier("localOneJpaVendorAdapter") JpaVendorAdapter vendorAdapter)
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = factoryBuilder
            .dataSource(dataSource)
            .properties(jpaProperties())
            .packages("macauyeah.personal.springbootdatajpa.localone.database.entity")
            .build();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactoryBean;
    }
    // @formatter:on

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        return props;
    }

    @Bean(name = "localOneJdbcTemplate")
    @Primary
    public JdbcTemplate localOneJdbcTemplate(@Qualifier("localOneDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = { "localOneJpaVendorAdapter" })
    @Primary
    @ConfigurationProperties(prefix = "spring.jpa.macauyeah.personal.localone")
    public JpaVendorAdapter jpaVendorAdapter() {
        return (JpaVendorAdapter) new HibernateJpaVendorAdapter();
    }
}
