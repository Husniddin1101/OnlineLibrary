package com.example.online_library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {
    private final String[] whitelist = new String[]
            {
                    "/",
                    "/sign-in" ,
                    "/sign-up",
                    "/phoneNumber",
                    "/user/phoneNumber",
                    "/user/create",
                    "/css/**"
            };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        registry -> registry
                                .requestMatchers(whitelist)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        loginConfig -> loginConfig
                                .loginPage("/sign-in")
                                .defaultSuccessUrl("/", true)
                                .loginProcessingUrl("/sign-in")
                                .usernameParameter("email")
                                .passwordParameter("password")
                ).logout(
                        logoutConfig -> logoutConfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("/sign-out"))
                                .logoutSuccessUrl("/sign-in")
                ).rememberMe(
                        rememberMeConfig -> rememberMeConfig
                                .rememberMeCookieName("rememberMe")
                                .tokenValiditySeconds(3 * 60 * 60 * 24)
                                .rememberMeParameter("rememberMe")
                ).build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
