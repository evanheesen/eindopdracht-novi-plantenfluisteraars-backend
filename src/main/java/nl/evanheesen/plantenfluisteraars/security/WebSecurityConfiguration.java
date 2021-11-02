//package nl.evanheesen.plantenfluisteraars.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import static org.springframework.http.HttpMethod.*;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private DataSource dataSource;
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    WebSecurityConfiguration(DataSource dataSource, JwtRequestFilter jwtRequestFilter) {
//        this.dataSource = dataSource;
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//    //    inMemoryAuthentication: alleen voor proefversie applicatie!! Daarna jdbcAuthentication instellen (zie EdHub)
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER").and()
//                .withUser("admin").password("password").roles("USER", "ADMIN");
//    }
//
//// jdbcAuthentication:
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////
////        auth.jdbcAuthentication().dataSource(dataSource)
////                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
////                .authoritiesByUsernameQuery("SELECT username, authority FROM authorities AS a WHERE username=?");
////
////    }
//
//    //    Activeren password encoder:
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return super.userDetailsServiceBean();
//    }
//
//
//
//    // Authentication: endpoints en roles nog aanpassen!!
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                .httpBasic()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers(PATCH,"/users/{^[\\w]$}/password").authenticated()
//                .antMatchers("/users/**").hasRole("ADMIN")
//                .antMatchers("/customers/**").hasRole("USER")
//                .antMatchers(POST,"/authenticate").permitAll()
//                .antMatchers(GET,"/public").permitAll()
//                .anyRequest().denyAll()
//                .and()
//                .csrf().disable()
//                .formLogin().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
////        Toevoegen RequestFilter aan Request chain:
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//}
