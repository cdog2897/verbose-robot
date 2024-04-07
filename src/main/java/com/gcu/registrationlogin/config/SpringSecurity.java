package com.gcu.registrationlogin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    private static final Logger logger = LoggerFactory.getLogger(SpringSecurity.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        logger.info("Creating password encoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers("/register/**").permitAll()
                            .requestMatchers("/index").permitAll()
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/editSuccess/{id}").hasRole("ADMIN")
                            .requestMatchers("/edit/{id}").hasRole("ADMIN")
                            .requestMatchers("/create").hasRole("ADMIN")
                            .requestMatchers("/users").hasRole("ADMIN")
                            .requestMatchers("/posts").hasRole("ADMIN")
                            .requestMatchers("/delete/{id}").hasRole("ADMIN")
            ).formLogin(
                    form -> form
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/posts")
                            .permitAll()
            ).logout(
                    logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                            .permitAll()
            );
        logger.info("Security filter chain configured successfully");
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("Configuring global authentication");
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
        logger.info("Global authentication configured successfully");
    }
}
