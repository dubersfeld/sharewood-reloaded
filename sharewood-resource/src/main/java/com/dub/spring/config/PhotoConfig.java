package com.dub.spring.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dub.spring.services.PhotoServices;
import com.dub.spring.services.PhotoServicesImpl;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "photoEntityManagerFactory",
        transactionManagerRef = "photoTransactionManager",
        basePackages = { "com.dub.spring.photo" }
)
public class PhotoConfig {

    @Bean(name = "photoDataSource")
    @ConfigurationProperties(prefix = "photo.datasource")
    public DataSource dataSource() {
        DataSource enclume = DataSourceBuilder.create().build();
        System.out.println("photoDataSource built");
        return enclume;
        
    }
 
    @Bean(name = "photoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean 
            photoEntityManagerFactory(
                    EntityManagerFactoryBuilder builder,
                    @Qualifier("photoDataSource") DataSource dataSource) {
    
        LocalContainerEntityManagerFactoryBean forge =    
             builder
             .dataSource(dataSource)
             .packages("com.dub.spring.photo.entities")
             .persistenceUnit("photoDB")
             .build();
        System.out.println("photoEntityManagerFactory built");
        return forge;
    }

    @Bean(name = "photoTransactionManager")
    public PlatformTransactionManager 
        photoTransactionManager(
                @Qualifier("photoEntityManagerFactory") EntityManagerFactory
                photoEntityManagerFactory) {
        
        PlatformTransactionManager fume =
         new JpaTransactionManager(photoEntityManagerFactory);
        System.out.println("photoTransactionManager built");
        return fume;
    }
    
    @Bean
    public PhotoServices photoService() {
        return new PhotoServicesImpl();
    }
}