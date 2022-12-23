package com.example.mtservice.security.configuation

import com.example.mtservice.security.details.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val userDetailsService: UserDetailsService,
) {

    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        tokenRepository: PersistentTokenRepository,
    ): SecurityFilterChain {
        httpSecurity.csrf().disable()

        httpSecurity.formLogin()
            .loginPage("/signin")
            .defaultSuccessUrl("/profile", true)
            .failureUrl("/signin?error")
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()

        httpSecurity.rememberMe()
            .rememberMeParameter("remember-me")
            .tokenRepository(tokenRepository)
            .tokenValiditySeconds(60 * 60 * 24 * 7)

        httpSecurity.authorizeHttpRequests()
            .requestMatchers("/signin", "/signup", "/").permitAll()
            .anyRequest().authenticated()

        return httpSecurity.build()
    }

    @Autowired
    fun bindUserDetailsService(builder: AuthenticationManagerBuilder) {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun tokenRepository(dataSource: DataSource): PersistentTokenRepository {
        return JdbcTokenRepositoryImpl().also { it.setDataSource(dataSource) }
    }
}
