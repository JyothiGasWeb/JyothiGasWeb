package com.jyothigas.app.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import com.jyothigas.app.config.SecurityConfig;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenStore(SecurityConfig.tokenStore).stateless(true);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				// .anonymous().disable().authorizeRequests().and()
				.requestMatchers().antMatchers("/api/user").and().authorizeRequests().antMatchers("/api/user")
				.permitAll().and().requestMatchers().antMatchers("/api/admin/**").and().authorizeRequests()
				.antMatchers("/api/admin/**").access("hasRole('Admin')").and().requestMatchers()
				.antMatchers("/api/dealer/**").and().authorizeRequests().antMatchers("/api/dealer/**")
				.access("hasRole('Dealer')").and().requestMatchers().antMatchers("/api/consumer/**").and()
				.authorizeRequests().antMatchers("/api/consumer/**").access("hasRole('Consumer')").and()
				.requestMatchers().antMatchers("/api/distributor/**").and().authorizeRequests()
				.antMatchers("/api/distributor/**").access("hasRole('Distributor')").and().requestMatchers()
				.antMatchers("/api/**").and().authorizeRequests().antMatchers("/api/**").permitAll().and()
				.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}