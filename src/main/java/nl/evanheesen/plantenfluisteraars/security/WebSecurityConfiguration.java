package nl.evanheesen.plantenfluisteraars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


private DataSource dataSource;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    WebSecurityConfiguration(@Lazy DataSource dataSource, @Lazy JwtRequestFilter jwtRequestFilter) {
        this.dataSource = dataSource;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities AS a WHERE username=?");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(POST,"/authenticate").permitAll()
                .antMatchers(PATCH,"/users/{^[\\w]$}/password").authenticated()
                .antMatchers(GET, "/users/getusername/**").permitAll()
                .antMatchers(GET,"/users").hasRole("USER")
                .antMatchers(GET,"/users/user/**").hasRole("USER")
                .antMatchers(POST,"/users/**").hasRole("USER")
                .antMatchers(PUT,"/users/**").hasRole("USER")
                .antMatchers(PATCH,"/users/**").hasRole("USER")
                .antMatchers(DELETE,"/users/**").hasRole("USER")
                .antMatchers(POST,"/customers").permitAll()
                .antMatchers("/customers/**").hasRole("USER")
                .antMatchers(GET,"/employees/**").hasRole("USER")
                .antMatchers(DELETE,"/employees/**").hasRole("ADMIN")
                .antMatchers(PATCH,"/employees/**").hasRole("USER")
                .antMatchers(POST,"/employees/**").permitAll()
                .antMatchers("/gardens/**").hasRole("USER")
                .antMatchers(PATCH,"/gardens/admin/**").hasRole("ADMIN")
                .antMatchers(POST,"/files/**").permitAll()
                .antMatchers(GET,"/files/**").hasRole("USER")
                .anyRequest().denyAll()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
