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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Autowired
    private AuthenticationFailureHandler customFailureHandler;

    @Bean
    public BCryptPasswordEncoder encodedPw() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/icon/**", "/img/**", "/js/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/", "/member/login-page", "/member/save", "/member/email-authentication", "/member/login",
                        "/debut/save-form", "/debut/update-form/**", "/debut/delete/**").permitAll()
                .antMatchers("/book/episode/**", "/book/book-save-form", "/book/episode-save-form", "/book/save-star").authenticated()
//                .antMatchers("/comment/**").authenticated()
                .antMatchers("/debutComment/**").authenticated()
//                .antMatchers("/wish/**").authenticated()
                .antMatchers("/box/**").authenticated()
//                .antMatchers("/debut/**").authenticated()
                .antMatchers("/history/**").authenticated()
                .antMatchers("/point/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/member/login-page")
                .loginProcessingUrl("/member/login")
//                .failureHandler(customFailureHandler)
                .failureUrl("/")
                .usernameParameter("loginId")
                .passwordParameter("memberPassword")
                .defaultSuccessUrl("/")
//                .failureUrl("/member/login-page")
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
