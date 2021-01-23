package pl.sda.borat.projekt_koncowy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/add-new-meeting").authenticated()
                .antMatchers("/meeting/*/comment/add").authenticated()
                .antMatchers("/meeting/*/register-user-for-meeting").authenticated()
                .antMatchers("/meeting/*/unsubscribe-user-from-meeting").authenticated()
                .anyRequest().permitAll()
            .and()
                .formLogin()
                .loginPage("/login-user")
                .loginProcessingUrl("/login-user-submit-correct")
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/login-user-error")
                .defaultSuccessUrl("/")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout-user", "GET"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT u.email, u.password, 1 " +
                        "FROM users u WHERE u.email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, r.role_name " +
                        "FROM users u " +
                        "JOIN users_roles ur ON u.id = ur.user_entity_id " +
                        "JOIN roles r ON ur.roles_id = r.id " +
                        "WHERE u.email = ?")
                .passwordEncoder(passwordEncoder);
    }
}
