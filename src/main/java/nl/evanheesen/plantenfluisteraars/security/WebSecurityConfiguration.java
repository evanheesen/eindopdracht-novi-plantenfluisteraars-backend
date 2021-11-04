package nl.evanheesen.plantenfluisteraars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {



////        inMemoryAuthentication: alleen voor proefversie applicatie!! Daarna jdbcAuthentication instellen (zie EdHub)
//    @Override
//
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}password").roles("USER").and()
//                .withUser("employee").password("{noop}password").roles("EMPLOYEE").and()
//                .withUser("admin").password("{noop}password").roles("USER", "EMPLOYEE", "ADMIN");
//    }

private DataSource dataSource;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    WebSecurityConfiguration(DataSource dataSource, JwtRequestFilter jwtRequestFilter) {
        this.dataSource = dataSource;
        this.jwtRequestFilter = jwtRequestFilter;
    }
// jdbcAuthentication:
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

// Nog data sql bestand vullen met juiste user gegevens en authorities!!
        auth.jdbcAuthentication().dataSource(dataSource)
                // Dit niet noodzakelijk, maar is om meer duidelijkheid te verschaffen:
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities AS a WHERE username=?");

    }

// Activeren password encoder (sla de wachtwoorden in data sql op als Bcript (bcript-generator.com) gecodeerde wachtwoorden:
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



    // Authentication: endpoints en roles nog aanpassen!!
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(PATCH,"/users/{^[\\w]$}/password").authenticated()
                .antMatchers("/bewoners/**").hasRole("USER")
//                .antMatchers(GET,"/plantenfluisteraars/**").hasRole("EMPLOYEE")
                .antMatchers(GET,"/plantenfluisteraars/**").permitAll()
//                .antMatchers(POST,"/authenticate").permitAll()
                .antMatchers(GET,"/public").permitAll()
//                .anyRequest().denyAll()
                // permit all later verwijderen!
//                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//        Toevoegen RequestFilter aan Request chain:
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

}
