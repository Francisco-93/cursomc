package com.franciscoaguiar.cursomc.config;

import com.franciscoaguiar.cursomc.services.DBService;
import com.franciscoaguiar.cursomc.services.EmailService;
import com.franciscoaguiar.cursomc.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instatiateDatabaseBean() throws ParseException {
        if(strategy.equals("create")){
            dbService.getInstantiateDatabase();
            return true;
        }
        return false;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }

}
