package com.pintoss.auth.api.support.security;

import com.pintoss.auth.api.support.filter.JwtFilter;
import com.pintoss.auth.api.support.filter.MdcLoggingFilter;
import com.pintoss.auth.api.support.security.jwt.JwtParser;
import com.pintoss.auth.api.support.security.jwt.JwtValidator;
import com.pintoss.auth.api.support.util.HttpServletUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtValidator jwtValidator;
    private final JwtParser jwtParser;
    private final HttpServletUtils servletUtils;

    public SecurityConfig(JwtValidator jwtValidator, JwtParser jwtParser, HttpServletUtils servletUtils) {
        this.jwtValidator = jwtValidator;
        this.jwtParser = jwtParser;
        this.servletUtils = servletUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .formLogin(form -> form.disable())
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .anonymous(anonymous -> anonymous.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtFilter(jwtParser, jwtValidator, servletUtils), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(new MdcLoggingFilter(), JwtFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

}
