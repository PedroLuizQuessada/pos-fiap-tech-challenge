package com.example.tech_challenge.infraestructure.configs;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = JpaConfig.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password=password",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true"
})
@ActiveProfiles("jpa")
class JpaConfigTest {

    @Autowired
    private JpaConfig jpaConfig;

    @Test
    void shouldCreateDataSource() {
        DataSource dataSource = jpaConfig.dataSource();
        assertNotNull(dataSource);
    }

    @Test
    void shouldCreateJpaVendorAdapter() {
        assertNotNull(jpaConfig.jpaVendorAdapter());
    }

    @Test
    void shouldCreateEntityManagerFactory() {
        EntityManagerFactory factory = jpaConfig.entityManagerFactory();
        assertNotNull(factory);
        assertTrue(factory.isOpen());
    }

    @Test
    void shouldCreateTransactionManager() {
        JpaTransactionManager txManager = (JpaTransactionManager) jpaConfig.transactionManager();
        assertNotNull(txManager);
        assertNotNull(txManager.getEntityManagerFactory());
    }

    @Test
    void shouldCreateAdditionalProperties() {
        Properties props = jpaConfig.additionalProperties();
        assertEquals("create", props.getProperty("hibernate.hbm2ddl.auto"));
        assertEquals("true", props.getProperty("hibernate.show_sql"));
        assertEquals("true", props.getProperty("hibernate.format_sql"));
    }
}
