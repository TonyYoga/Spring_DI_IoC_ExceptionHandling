package com.telran.springdiiocexceptionhandling.config;

import com.telran.springdiiocexceptionhandling.repository.UserRepository;
import com.telran.springdiiocexceptionhandling.repository.entity.RolesEntity;
import com.telran.springdiiocexceptionhandling.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Configuration
    class SecurityAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole(RolesEntity.Role.ADMIN.name())
                    .antMatchers(HttpMethod.GET, "/topic").permitAll()
                    .antMatchers("/topic/**","/comment/**").hasRole(RolesEntity.Role.USER.name())
                    .antMatchers("/profile").authenticated()
                    .antMatchers(HttpMethod.POST,"/user").permitAll()
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(email -> {
                UserEntity userEntity = userRepository.getUserByEmail(email);
                if (userEntity == null) {
                    throw new UsernameNotFoundException("No such email: " + email);
                }
                String[] roles = userRepository.getRoles(email);
                return User.builder()
                        .username(userEntity.getEmail())
                        .password(userEntity.getPassword())
                        .roles(roles)
                        .build();
            }).passwordEncoder(passwordEncoder());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
