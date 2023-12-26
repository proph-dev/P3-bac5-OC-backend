package com.openclassrooms.rentals.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.openclassrooms.rentals.security.JwtTokenFilter;
import com.openclassrooms.rentals.security.JwtTokenProvider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity  
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(userDetailsService, jwtTokenProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/swagger-ui/**", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
            .antMatchers("/api/auth/login", "/api/auth/register", "/backend/img/**").permitAll()
            .antMatchers("/api/auth/me").authenticated()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Utilisez la méthode jwtTokenFilter ici
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}