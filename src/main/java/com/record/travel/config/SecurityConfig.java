package com.record.travel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.record.travel.security.service.CustomOAuth2UserService;
import com.record.travel.security.service.MemberService2;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MemberService2 memberService2;
	
	@Autowired
    private CustomOAuth2UserService customAutowireConfigurer;

	@Bean // 패스워드 암호화. 인증 위해서 반드시 지정해야 함.
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // bcrypt 해시 함수 이용해 패스워드 암호화, 원래대로 복호화 불가능, 매번 값 다름(길이는 동일)
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/image/**");
	}

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	                    .antMatchers("/admin/**").hasRole("ADMIN")
	                    .antMatchers("/member/myinfo").hasRole("MEMBER")
	                    .antMatchers("/**", "/member/login", "/member/signUp","/blog/listPost").permitAll()
	                .and()
	                    .formLogin()
	                    .loginPage("/member/loginPage")
	                    .loginProcessingUrl("/member/loginPage")
	                    .defaultSuccessUrl("/")
	                    .permitAll()
	                .and()
	                    .csrf()
	                    .ignoringAntMatchers("/h2-console/**")
	                    .ignoringAntMatchers("/post/**")
	                    .ignoringAntMatchers("/admin/**")
	                    .ignoringAntMatchers("/video_board/**")
	                .and()
	                    .logout()
	                    .logoutUrl("/member/logout") /*로그아웃 url*/ 
						.logoutSuccessUrl("/") /*로그아웃 성공시 연결할 url*/ 
						.invalidateHttpSession(true)/*로그아웃시 세션 제거*/
						.deleteCookies("JSESSIONID")/*쿠키제거*/
						.clearAuthentication(true)/*권한정보 제거*/
	                    
	                .and()
	                    .exceptionHandling()
	                    .accessDeniedPage("/error")
	                .and()
	                    .oauth2Login()
	                        .userInfoEndpoint()
	                            .userService(customAutowireConfigurer);
	        
	        http.csrf().disable();
	        http.headers().frameOptions().disable();
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(memberService2).passwordEncoder(passwordEncoder());
	    }

}
