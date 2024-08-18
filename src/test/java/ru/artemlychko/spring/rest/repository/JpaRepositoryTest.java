package ru.artemlychko.spring.rest.repository;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ResourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.artemlychko.spring.rest.entity.Department;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringJUnitConfig(JpaRepositoryTest.Config.class)
@Testcontainers
@ComponentScan(basePackages = "org.example.repository")
public class JpaRepositoryTest {


        @Container
        public static PostgreSQLContainer<?> postgresContainer =  new PostgreSQLContainer<>("postgres:13.3")
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");

        @BeforeAll
        public static void start() {
            postgresContainer.start();
        }

        @AfterAll
        public static void teardown() {
            postgresContainer.stop();
        }

        @Configuration
        @EnableJpaRepositories(basePackages = "ru.artemlychko.spring.rest.repository")
        @PropertySource("classpath:testdb.properties")
        static class Config {
            private final Environment env;

            public Config(Environment env) {
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
                config.setConnectionInitSql(loadSql("classpath:initTestScript.sql"));
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

            @Bean
            DepartmentRepository departmentRepository(EntityManagerFactory emf) {
                return emf.unwrap(DepartmentRepository.class);
            }
        }

        @Autowired
        private DepartmentRepository departmentRepository;

    @Test
    void findById() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        departmentRepository = context.getBean(DepartmentRepository.class);

        Department foundDepartment = departmentRepository.findById(1L).orElse(null);

        assertThat(foundDepartment).isNotNull();
        assert foundDepartment != null;
        assertThat(foundDepartment.getName()).isEqualTo("Testing");

        context.close();
    }

    @Test
    void findAll(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        departmentRepository = context.getBean(DepartmentRepository.class);

        List<Department> departments = departmentRepository.findAll();

        Assertions.assertEquals(4, departments.size());

        context.close();
    }

    @Test
    void save() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        departmentRepository = context.getBean(DepartmentRepository.class);

        departmentRepository.save(new Department(1L, "Testing1"));

        Assertions.assertTrue(departmentRepository.findById(1L).isPresent());

        context.close();
    }

    @Test
    void delete() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        departmentRepository = context.getBean(DepartmentRepository.class);

        departmentRepository.deleteById(1L);

        Assertions.assertTrue(departmentRepository.findById(1L).isPresent());

        context.close();
    }
}
