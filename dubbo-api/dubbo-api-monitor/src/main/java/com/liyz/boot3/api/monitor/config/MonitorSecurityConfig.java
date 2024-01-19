package com.liyz.boot3.api.monitor.config;

import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/18 14:12
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MonitorSecurityConfig {

    @Bean
    public SecurityFilterChain monitorConfigure(HttpSecurity http) throws Exception {
        log.info("monitorConfigure init");
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        http
                .formLogin(login -> login.loginPage("/login").successHandler(successHandler))
                .logout(logout -> logout.logoutUrl("/logout"))
                .authorizeHttpRequests(ahr -> ahr
                        .requestMatchers(HttpMethod.OPTIONS, SecurityClientConstant.OPTIONS_PATTERNS).permitAll()
                        .requestMatchers(SecurityClientConstant.ACTUATOR_RESOURCES).permitAll()
                        .requestMatchers("/liyz/error", "/instances/**", "/assets/**").permitAll()
                        .requestMatchers("/login", "/logout").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(SecurityClientConstant.ACTUATOR_RESOURCES)
                        .ignoringRequestMatchers("/liyz/error", "/logout", "/instances/**", "/assets/**"));
        return http.build();
    }
}
