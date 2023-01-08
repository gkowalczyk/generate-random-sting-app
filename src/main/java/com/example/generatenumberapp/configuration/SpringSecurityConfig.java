package com.example.generatenumberapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Arrays;

@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager get() {
        UserDetails user = User.withUsername("user")
                .password(getBcryptPasswordEncoder()
                        .encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(getBcryptPasswordEncoder()
                        .encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(Arrays.asList(user, admin));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception { //aplikacja zanim obsłuży żądania sprawdza uprawnienia
        httpSecurity.httpBasic().and()
                .authorizeRequests((autz) -> autz  // w momencie żądania wymaga się ..
                        .antMatchers(HttpMethod.GET,"/v1/run").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.GET,"/v1/getAll").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.POST,"/v1/addTask").hasAnyRole("USER", "ADMIN")
                        .antMatchers(HttpMethod.DELETE,"/v1/deleteAll").hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/swagger-ui/index.html").permitAll()
                .anyRequest().hasRole("ADMIN")) // każde inne żadanie wymaga roli ADMIN
                .formLogin((formLogin) -> formLogin.permitAll()) // dodanie formularza logowania dostarczonego ze spring security
                .logout()
                .logoutSuccessUrl("/bye").permitAll()
                .and()
                .csrf().disable();
        return httpSecurity.build();
    }
}

   /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("ADMIN");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/v1/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().httpBasic();
        return httpSecurity.build();*/



