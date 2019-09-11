package com.aboo.movie.springcloud.config;

import com.aboo.movie.springcloud.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @description:
 * @author: zhegong
 * @create: 2019-09-10 11:03
 **/
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserService customUserService;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                //允许所有用户访问"/","/jquery/**","/semantic/**","/css/**","/js/**","/images/**"
                .antMatchers("/",
                        "/core/**",
                        "/jquery/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/layui/**",
                        "/treegrid/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout()
                .permitAll();

       /* http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                .key("")
                .and()
                .logout()
                //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/custom-logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("")
                .permitAll();*/
    }

}
