package sk.pga.animalshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	protected FormLoginConfigurer<HttpSecurity> formLoginConfigurer() {
		return new FormLoginConfigurer<>();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsServiceImpl();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/**
		 * URL restriction on User Roles
		 */
		
		// login
		http.authorizeRequests().antMatchers("/login*").permitAll();
		http.authorizeRequests().antMatchers("/register*").permitAll();
		http.authorizeRequests().antMatchers("/rest/saveUser*").permitAll();
		http.authorizeRequests().antMatchers("/rest/getProduct*").permitAll();
		http.authorizeRequests().antMatchers("/rest/getProductList*").permitAll();
		
		http.authorizeRequests().antMatchers("/admin", "/admin/**").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/**").authenticated();
		
		/**
		 * Other spring security settings
		 */
		http.apply(formLoginConfigurer()).loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/home").usernameParameter("username").passwordParameter("password");
		
		http.csrf().disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
