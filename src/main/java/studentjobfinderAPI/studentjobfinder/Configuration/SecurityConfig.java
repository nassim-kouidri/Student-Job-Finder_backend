package studentjobfinderAPI.studentjobfinder.Configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source =
				new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(false);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
     @Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Override @Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http = http.cors().and().csrf().disable();
		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}).and();

		http.authorizeRequests()

		//public endpoints
		.antMatchers("/chat/**").permitAll()
		.antMatchers("/chat.html").permitAll()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/favicon.ico").permitAll()
		.antMatchers("/api/public/**").permitAll()
		//private endpoints
		.antMatchers("/api/admin/**").hasRole("ADMIN")
		.antMatchers("/api/student/**").hasRole("STUDENT")
				.antMatchers("/api/company/**").hasRole("COMPANY")
		.antMatchers("/api/user/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		;

		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF");
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder()
				;
	}

}