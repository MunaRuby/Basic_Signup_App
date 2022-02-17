package com.nwabundo.mkobo.security;

import com.nwabundo.mkobo.filters.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private PasswordConfiguration encoder;
    private UserDetailsService userService;
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    public void SecurityConfiguration(final PasswordConfiguration encoder, final UserDetailsService userService,
                                      final JWTRequestFilter jwtRequestFilter) {
        this.encoder = encoder;
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/auth/**", "/swagger/**", "/v3/api-docs/**", "/user").permitAll()
                .anyRequest()
                .fullyAuthenticated();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/auth/**", "/create").and().ignoring()
                .antMatchers("/login").and().ignoring()
                .antMatchers("/swagger-ui-custom.html").and().ignoring()
                .antMatchers("/swagger/**", "/v3/api-docs/**");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }


}
