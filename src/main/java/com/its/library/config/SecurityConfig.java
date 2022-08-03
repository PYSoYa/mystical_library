package com.its.library.config;

import com.its.library.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public BCryptPasswordEncoder encodedPw() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/icon/**", "/img/**", "/js/**");
        web.ignoring().antMatchers("/member/email-authentication");
        web.ignoring().antMatchers("/member/save");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http    .authorizeRequests()
                    .antMatchers("/book/episode/**").authenticated()
                    .antMatchers("/book/book-save-form").access("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/book/episode-save-form").access("hasRole('ROLE_WRITER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/book/save-star").authenticated()
                    .antMatchers("/wish/**").authenticated()
                    .antMatchers("/box/**").authenticated()
                    .antMatchers("/debut/**").authenticated()
                    .antMatchers("/history/**").authenticated()
                    .antMatchers("/member/**").authenticated()
                    .antMatchers("/point/**").authenticated()
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().permitAll()
                    .and()
                .formLogin()
                    .loginProcessingUrl("/member/login")
                    .usernameParameter("loginId")
                    .passwordParameter("memberPassword")
                    .defaultSuccessUrl("/")
                    .failureUrl("/")
                    .and()
                .logout()
                    .logoutUrl("/member/logout").permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .invalidateHttpSession(true)
                    .deleteCookies("bye", "JSESSIONID")
                    .logoutSuccessUrl("/")
                    .and()
                .oauth2Login()
                    .userInfoEndpoint()
                    .userService(principalOauth2UserService);
    }
}
