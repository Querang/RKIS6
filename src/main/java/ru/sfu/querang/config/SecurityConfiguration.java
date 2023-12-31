package ru.sfu.querang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sfu.querang.services.ShopUserDetailsService;

/**
 * Класс конфигурации для настройки SpringSecurity в приложении
 */
@Configuration
@ComponentScan("ru.sfu.querang")
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    private final ShopUserDetailsService shopUserDetailsService;

    /**
     * Конструктор для SecurityConfiguration
     *
     * @param shopUserDetailsService Пользовательский сервис деталей пользователя для аутентификации
     */
    @Autowired
    public SecurityConfiguration(ShopUserDetailsService shopUserDetailsService) {
        this.shopUserDetailsService = shopUserDetailsService;
    }

    /**
     * Конфигурирует менеджер аутентификации с пользовательским сервисом деталей и кодировщиком пароля
     *
     * @param http Конфигурация безопасности HTTP
     * @return Сконфигурированный бин AuthenticationManager
     * @throws Exception Если происходит ошибка во время конфигурации
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(shopUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    /**
     * Предоставляет бин BCryptPasswordEncoder для кодирования пароля
     *
     * @return Бин BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Конфигурирует цепочку фильтров безопасности для различных HTTP-запросов и формной аутентификации/выхода
     *
     * @param http Конфигурация безопасности HTTP
     * @return Сконфигурированный бин SecurityFilterChain
     * @throws Exception Если происходит ошибка во время конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/login", "/error", "/auth/register", "/auth/process_reg").permitAll()
                        .requestMatchers("/cars/*/edit").hasRole("ADMIN")
                        .requestMatchers("/cars/new").hasRole("ADMIN")
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/process_login")
                                .defaultSuccessUrl("/cars", true)
                                .failureUrl("/auth/login?error")
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/auth/login"));
        return http.build();
    }

}
