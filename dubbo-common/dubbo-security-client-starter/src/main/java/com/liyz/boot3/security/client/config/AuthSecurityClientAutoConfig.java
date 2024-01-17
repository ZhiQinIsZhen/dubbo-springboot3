package com.liyz.boot3.security.client.config;

import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.security.client.filter.JwtAuthenticationTokenFilter;
import com.liyz.boot3.security.client.handler.JwtAuthenticationEntryPoint;
import com.liyz.boot3.security.client.handler.RestfulAccessDeniedHandler;
import com.liyz.boot3.security.client.user.impl.UserDetailsServiceImpl;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import com.liyz.boot3.service.auth.remote.RemoteJwtParseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 11:20
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthSecurityClientAutoConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        //do nothing
    }

    @Bean
    public AuthContext authContext() {
        return new AuthContext();
    }

    @Bean
    public AnonymousMappingConfig anonymousMappingConfig() {
        return new AnonymousMappingConfig();
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @DependsOn({"anonymousMappingConfig"})
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(eh -> eh
                        .accessDeniedHandler(new RestfulAccessDeniedHandler())
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(ahr -> ahr
                        .requestMatchers(HttpMethod.OPTIONS, SecurityClientConstant.OPTIONS_PATTERNS).permitAll()
                        .requestMatchers(HttpMethod.GET, SecurityClientConstant.KNIFE4J_IGNORE_RESOURCES).permitAll()
                        .requestMatchers(HttpMethod.GET, SecurityClientConstant.ACTUATOR_RESOURCES).permitAll()
                        .requestMatchers(AnonymousMappingConfig.getAnonymousMappings().toArray(new String[0])).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationTokenFilter(SecurityClientConstant.DEFAULT_TOKEN_HEADER_KEY),
                        UsernamePasswordAuthenticationFilter.class)
                .headers(hc -> hc.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                //如无需要请勿设置跨域
                .cors(cc -> cc.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(SecurityClientConstant.OPTIONS_PATTERNS, corsConfiguration);
        return source;
    }

    @DubboReference(group = SecurityClientConstant.DUBBO_AUTH_GROUP)
    private RemoteAuthService remoteAuthService;
    @DubboReference(group = SecurityClientConstant.DUBBO_AUTH_GROUP)
    private RemoteJwtParseService remoteJwtParseService;

    @Bean(SecurityClientConstant.AUTH_SERVICE_BEAN_NAME)
    public RemoteAuthService remoteAuthService() {
        return this.remoteAuthService;
    }

    @Bean(SecurityClientConstant.JWT_SERVICE_BEAN_NAME)
    public RemoteJwtParseService remoteJwtParseService() {
        return this.remoteJwtParseService;
    }
}
