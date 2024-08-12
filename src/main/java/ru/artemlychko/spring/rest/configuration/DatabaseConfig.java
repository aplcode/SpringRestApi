package ru.artemlychko.spring.rest.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableJpaRepositories("ru.artemlychko.spring.rest.repository")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("ru.artemlychko.spring.rest")
public class DatabaseConfig {
    private final Environment env;

    public DatabaseConfig(Environment env) {
        this.env = env;
    }
    private String loadSql(String path){
        String schemeSql;
        try(BufferedReader br = new BufferedReader(new FileReader(ResourceUtils.getFile(path).getAbsolutePath()))){
            schemeSql = br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new IllegalStateException("Could not load sql script", e);
        }
        return schemeSql;
    }

    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setDriverClassName(env.getProperty("db.driver"));
        config.setJdbcUrl(env.getProperty("db.url"));
        config.setUsername(env.getProperty("db.username"));
        config.setPassword(env.getProperty("db.password"));
        config.setConnectionInitSql(loadSql("classpath:initScript.sql"));
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("ru.artemlychko.spring.rest.entity");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
