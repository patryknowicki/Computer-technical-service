package pl.pi5518.aplikacja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pi5518.aplikacja.service.UserDetailsServiceImpl;

@Configuration
public class  WebSecurityConfig extends WebSecurityConfigurerAdapter {

   @Bean
   public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                //.antMatchers("/stepone").authenticated()
                .antMatchers("/steptwo").authenticated()
                .antMatchers("/stepthree").authenticated()
                .antMatchers("/stepfour").authenticated()

                .antMatchers("/h2").hasRole("ADMIN")
                .antMatchers("/newNotebook").hasRole("ADMIN")
                .antMatchers("/newPc").hasRole("ADMIN")
                .antMatchers("/newTablet").hasRole("ADMIN")

//                .antMatchers("/for-user").hasRole("USER")
                .and()
                .formLogin().defaultSuccessUrl("/after-log")
                .and()
                .logout().logoutSuccessUrl("/after-logout");
    }
}
