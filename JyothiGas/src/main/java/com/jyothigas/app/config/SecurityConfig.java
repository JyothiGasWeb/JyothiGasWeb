package com.jyothigas.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.jyothigas.app.service.AuthenticationService;
import com.jyothigas.utils.UnauthorisedEntryPoint;



@Configuration
@EnableWebMvcSecurity
@ComponentScan(basePackages = {"com.jyothigas"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
 @Autowired
  private UnauthorisedEntryPoint unauthorisedEntryPoint;
  
  @Autowired
  private AuthenticationService authenticate;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorisedEntryPoint);
  }
  
  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticate);
  }
}
