package macauyeah.personal.springbootdatajpa.entityone.database.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
// @formatter:off
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityOneEntityManagerFactory",
    transactionManagerRef = "entityOneTransactionManager",
    basePackages = {
        "macauyeah.personal.springbootdatajpa.entityone.database.entity",
        "macauyeah.personal.springbootdatajpa.entityone.database.repository"
    }
)
// @formatter:on
public class EntityOneDataSourceConfiguration {
    @Value("${spring.jpa.macauyeah.personal.entityone.hibernate.ddl-auto}")
    private String hibernateDdlAuto;
    @Value("${spring.jpa.macauyeah.personal.entityone.hibernate.format_sql}")
    private String hibernateFormatSql;
    @Value("${spring.jpa.macauyeah.personal.entityone.hibernate.dialect}")
    private String hibernateDialect;
    @Value("${spring.jpa.macauyeah.personal.entityone.hibernate.id.new_generator_mappings}")
    private String hibernateUseNewIdGeneratorMappings;

    @Bean(name = "entityOneDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.macauyeah.personal.entityone")
    public DataSource entityOneDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityOneTransactionManager")
    PlatformTransactionManager entityOneTransactionManager(
            @Qualifier("entityOneEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // @formatter:off
    @Bean(name = "entityOneEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityOneEntityManagerFactory(
        EntityManagerFactoryBuilder factoryBuilder,
        @Qualifier("entityOneDataSource") DataSource dataSource,
        @Qualifier("entityOneJpaVendorAdapter") JpaVendorAdapter vendorAdapter)
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = factoryBuilder
            .dataSource(dataSource)
            .properties(jpaProperties())
            .packages("macauyeah.personal.springbootdatajpa.entityone.database.entity")
            .build();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactoryBean;
    }
    // @formatter:on

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        if (StringUtils.hasLength(hibernateDdlAuto)) {
            props.put("hibernate.ddl-auto", hibernateDdlAuto);
        }
        if (StringUtils.hasLength(hibernateFormatSql)) {
            props.put("hibernate.format_sql", hibernateFormatSql);
        }
        if (StringUtils.hasLength(hibernateDialect)) {
            props.put("hibernate.dialect", hibernateDialect);
        }
        if (StringUtils.hasLength(hibernateUseNewIdGeneratorMappings)) {
            props.put("hibernate.id.new_generator_mappings", hibernateUseNewIdGeneratorMappings);
        }
        return props;
    }

    @Bean(name = "entityOneJdbcTemplate")
    public JdbcTemplate entityOneJdbcTemplate(@Qualifier("entityOneDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = { "entityOneJpaVendorAdapter" })
    @ConfigurationProperties(prefix = "spring.jpa.macauyeah.personal.entityone")
    public JpaVendorAdapter jpaVendorAdapter() {
        return (JpaVendorAdapter) new HibernateJpaVendorAdapter();
    }
}
