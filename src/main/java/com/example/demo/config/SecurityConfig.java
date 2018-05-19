package com.example.demo.config;

import com.example.demo.service.IUserService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private IUserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .headers().frameOptions().disable().and()
            .authorizeRequests()
            .antMatchers("/index",
                "/").permitAll()
            .antMatchers("/denied").hasAnyRole("USER")
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/login").permitAll()
            .defaultSuccessUrl("/index", true)
            .and().logout().permitAll();

        http.logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutUrl("/logout")
            .logoutSuccessUrl("/index")
            .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
