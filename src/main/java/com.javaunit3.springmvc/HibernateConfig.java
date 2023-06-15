package com.javaunit3.springmvc;

import com.javaunit3.springmvc.Model.MovieEntity;
import com.javaunit3.springmvc.Model.VoteEntity;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//Hibernate Configuration file
@Configuration
public class HibernateConfig {
    //method to return factory
    @Bean
    public SessionFactory getFactory(){
        SessionFactory factory = new org.hibernate.cfg.Configuration()
                //add the configuration file from root called hibernate.cfg.xml
                .configure("hibernate.cfg.xml")
                //adding a table(class)
                .addAnnotatedClass(MovieEntity.class)
                .addAnnotatedClass(VoteEntity.class)
                .buildSessionFactory();

        return factory;
    }


}
