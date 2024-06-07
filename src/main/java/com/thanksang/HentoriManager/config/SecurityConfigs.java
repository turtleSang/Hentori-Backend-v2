package com.thanksang.HentoriManager.config;


import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {

    private FilerSession filerSession;

    public SecurityConfigs(FilerSession filerSession) {
        this.filerSession = filerSession;
    }

    @Bean
    public SecurityFilterChain configsSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(s -> s.disable())
                .cors(s -> s.disable())
                .authorizeHttpRequests(author -> author
                        .requestMatchers("api/admin/login").permitAll()
                        .requestMatchers("api/admin/register").permitAll()
                        .requestMatchers("api/admin/confirm").permitAll()
                        .anyRequest().authenticated())
        .addFilterBefore(filerSession,  AuthorizationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
