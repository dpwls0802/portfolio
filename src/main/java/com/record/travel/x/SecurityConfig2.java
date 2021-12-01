package com.record.travel.x;
/*
 * package com.record.travel.config;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.method.configuration.
 * EnableGlobalMethodSecurity; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import
 * org.springframework.security.config.annotation.web.builders.WebSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 * 
 * import com.record.travel.security.handler.MemberLoginSuccessHandler; import
 * com.record.travel.security.service.MemberService;
 * 
 * import lombok.extern.log4j.Log4j2;
 * 
 * @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
 * 
 * @EnableWebSecurity
 * 
 * @Configuration
 * 
 * @Log4j2 public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
 * 
 * @Autowired private MemberService memberService;
 * 
 * @Bean // 패스워드 암호화. 인증 위해서 반드시 지정해야 함. PasswordEncoder passwordEncoder() {
 * return new BCryptPasswordEncoder(); // bcrypt 해시 함수 이용해 패스워드 암호화, 원래대로 복호화
 * 불가능, 매번 값 다름(길이는 동일) }
 * 
 * @Override public void configure(WebSecurity web) throws Exception {
 * web.ignoring().antMatchers("/css/**", "/js/**", "/image/**", "/fonts/**",
 * "/templates/**"); }
 * 
 * @Override // 특정 url 접근 제한, **인가*8 protected void configure(HttpSecurity http)
 * throws Exception {
 * 
 * 
 * http.authorizeRequests(). antMatchers("/sample/all").permitAll().
 * antMatchers("/sample/member").hasRole("USER"); // 권한
 * 
 * http.authorizeRequests().antMatchers("/all").permitAll().antMatchers(
 * "/member").hasRole("USER") .antMatchers("/admin").hasRole("ADMIN"); // 권한 부여
 * 
 * http .formLogin() .loginPage("/member/loginPage")
 * .loginProcessingUrl("/member/loginPage") .defaultSuccessUrl("/") .permitAll()
 * .and() .csrf() .ignoringAntMatchers("/admin/**").
 * ignoringAntMatchers("/blog/listPost") .and() .logout()
 * .logoutRequestMatcher(new
 * AntPathRequestMatcher("/member/logout")).logoutSuccessUrl("/")
 * .invalidateHttpSession(true) .and() .exceptionHandling()
 * .accessDeniedPage("/error"); // 인가, 인증에 실패하는 경우 로그인 페이지 보여줌. 에러 페이지가 아니라 //
 * http.csrf().disable(); // CSRF 토큰 발행 안함. REST 방식에서는 매번 값을 알아내는 불편함 있어서. //
 * http.logout(); // 로그아웃 http.oauth2Login().successHandler(successHandler());
 * http.rememberMe().tokenValiditySeconds(60 * 60 * 24 *
 * 7).userDetailsService(memberService); // 7일간 자동 로그인 }
 * 
 * @Bean public MemberLoginSuccessHandler successHandler() { return new
 * MemberLoginSuccessHandler(passwordEncoder()); }
 * 
 * // MemberService가 빈으로 등록되어 UserDetailsService로 인식하기 때문에 임시 코드는 사용 안함
 * 
 * @Override // 암호화된 패스워드 사용하는 사용자 **인증** protected void protected void
 * configure(AuthenticationManagerBuilder auth) throws Exception {
 * //auth.inMemoryAuthentication().withUser("user1")
 * //.password("$2a$10$ODcZTmESA.1vdnVqSB9MauRltPqihSdsOv67E7fzWWl9OvRFQtYWq").
 * roles("USER");
 * 
 * auth.userDetailsService(memberService).passwordEncoder(passwordEncoder()); }
 * 
 * }
 */