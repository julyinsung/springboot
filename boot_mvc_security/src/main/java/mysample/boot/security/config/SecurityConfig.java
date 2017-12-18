package mysample.boot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		// Security configuration for H2 console access
		// !!!! You MUST NOT use this configuration for PRODUCTION site !!!!
		httpSecurity.authorizeRequests().antMatchers("/console/**").permitAll();
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();

		// static resources
		httpSecurity.authorizeRequests()
		.antMatchers("/css/**", "/js/**", "/images/**", "/resources/**", "/webjars/**").permitAll();

		
		// permitAll() 로 주지 않고 anonymous() 주었다. 
		// 이유는 permitAll()을 했을 경우 sign in 에 성공한 유저가 sign in form 페이지 URL 을 기억했다가 직접 access 할 경우 
		// sign in form 페이지가 보이게 된다. 이렇게 되는 것을 원치 않고 단지 sign in form 페이지는 authentication 이 없는 사용자들, 
		// 즉 sign in을 아직 하지 않은 사용자들만 보이게 하기 위해서다. 그리고 나머지 모든 페이지에 대해서는 anyRequest().authenticated() 로 설정했다.
		httpSecurity.authorizeRequests()
						.antMatchers("/login").permitAll()
						.antMatchers("/addUser").permitAll()
						.antMatchers("/addUserAction").permitAll()
						.antMatchers("/map").anonymous()
						.anyRequest().authenticated()
						.and()
					.formLogin()
						.loginPage("/login")
						//.loginProcessingUrl("/login/login.html")
						.failureUrl("/login?error")
						.usernameParameter("login_id")
						.passwordParameter("password")
						.defaultSuccessUrl("/user", true)
						.and()
					.logout()
						.logoutSuccessUrl("/login?logout");

		httpSecurity.exceptionHandling().accessDeniedPage("/admin/dashboard.html");
		httpSecurity.sessionManagement().invalidSessionUrl("/login");

	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
		
		// In case of password encryption - for production site
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}
