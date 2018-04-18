package com.dub.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.dub.spring.services.UserService;
import com.dub.spring.utils.MyAuthenticationSuccessHandler;
import com.dub.spring.utils.MyExceptionTranslationFilter;


@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true, order = 0, mode = AdviceMode.PROXY,
        proxyTargetClass = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		  
	@Value("${gateway-url}")
	private String gatewayUrl;
	
	
	@Autowired
	private UserService userService;
	
	@Bean
	public SecurityContextLogoutHandler mySecurityContextLogoutHolder() {
		
		SecurityContextLogoutHandler handler 
			= new SecurityContextLogoutHandler();
		handler.setInvalidateHttpSession(true);
		return handler;
	}
	
	@Bean
	public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
	    	return new DefaultWebSecurityExpressionHandler();
	}
	
	@Bean 
	public ExceptionTranslationFilter myExceptionTranslationFilter() {
		return new MyExceptionTranslationFilter(myAuthenticationEntryPoint());
	}

	@Bean
	public AuthenticationEntryPoint myAuthenticationEntryPoint() {
		return new LoginUrlAuthenticationEntryPoint(gatewayUrl + "/login");	
	}
	
	@Bean
	public SimpleUrlAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
		
		SimpleUrlAuthenticationSuccessHandler handler 
				= new MyAuthenticationSuccessHandler();
			
		handler.setDefaultTargetUrl(gatewayUrl + "/index");
		
		return handler;
	}
	
	@Bean
	public AuthenticationFailureHandler myAuthenticationFailureHandler() {
		
		SimpleUrlAuthenticationFailureHandler handler 	
				= new SimpleUrlAuthenticationFailureHandler(gatewayUrl + "/login?loginFailed");
		
		return handler;
	}
	
	@Bean
	public CsrfTokenRepository csrfTokenRepository() {
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	    repository.setHeaderName("X-XSRF-TOKEN");
	    return repository;
	}
	
	
	@Lazy
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{    
		return super.authenticationManagerBean();
	}
	

    @Bean
    protected SessionRegistry sessionRegistryImpl() {
        return new SessionRegistryImpl();
        
      
    }

    
    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
    	auth
      		.userDetailsService(this.userService)     
      		.passwordEncoder(new BCryptPasswordEncoder())
      		.and()
      		.eraseCredentials(true);  
	}

    
    @Override
    protected void configure(HttpSecurity security) 
    		throws Exception {
        security
        		.addFilterAt(myExceptionTranslationFilter(), 
        							ExceptionTranslationFilter.class)
                
        		.authorizeRequests()         	
                    .antMatchers("/login").permitAll()
                    .antMatchers("/login/**").permitAll()
                    .antMatchers("/logout").permitAll()    
                    .antMatchers("/**").fullyAuthenticated()//.hasRole("CUSTOMER_USER")
                    
                .and()
                    .formLogin()
                    .successHandler(myAuthenticationSuccessHandler())	
                    .failureHandler(myAuthenticationFailureHandler())
                    .loginPage("/login")		
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()
                    
                .and()
                	.logout()
                    .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                    .permitAll()
                    //.logoutSuccessHandler(myLogoutSuccessHandler())
                .and().exceptionHandling()
                    		.authenticationEntryPoint(myAuthenticationEntryPoint())
                
               
               // .and().csrfTokenRepository(csrfTokenRepository())
                    		
                .and()
                	.sessionManagement()
                    .sessionFixation().changeSessionId()
                    .maximumSessions(1).maxSessionsPreventsLogin(false)
                    .sessionRegistry(this.sessionRegistryImpl())
        
        .and().and()
    	.csrf().csrfTokenRepository(csrfTokenRepository());
		
    }            
}

