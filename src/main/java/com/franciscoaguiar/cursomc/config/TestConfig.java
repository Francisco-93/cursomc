package com.franciscoaguiar.cursomc.config;

import com.franciscoaguiar.cursomc.services.DBService;
import com.franciscoaguiar.cursomc.services.EmailService;
import com.franciscoaguiar.cursomc.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    DBService dbService;

    @Bean
    public boolean instatiateDatabaseBean() throws ParseException {
        dbService.getInstantiateDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }

}
