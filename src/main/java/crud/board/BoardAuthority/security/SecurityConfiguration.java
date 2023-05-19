package crud.board.BoardAuthority.security;

import crud.board.BoardAuthority.security.authenticationManager.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailsService);

        return authenticationManagerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring()
                .antMatchers("/css/*", "/js/*")
                // 경로 잘 확인할 것. 정적 파일이 다 들어가지 않으면 통과된 정적파일이 URI로 들어가서 반환됨
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(a -> a
//                        .antMatchers("/login").permitAll()
                        .antMatchers("/test/user", "/account").hasRole("USER")
                        .antMatchers("/test/manager").hasRole("MANAGER")
                        .antMatchers("/test/admin", "/admin/**").hasRole("ADMIN")
//                        .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                                .antMatchers("/", "/thread", "/login").permitAll()
//                                .anyRequest().access("@authorizationDynamicHandler.isAuthorization(request, authentication)")
                                .anyRequest().permitAll()
                )
                .formLogin(f -> f
                        .loginPage("/login")
                        .defaultSuccessUrl("/thread")
                        .failureUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login-process")
                )
                .logout(l -> l
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID", "remember-me")
                )
                .sessionManagement(m -> m
//                        .sessionFixation().changeSessionId()
//                        .invalidSessionUrl("/invalid")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)
//                        .expiredUrl("expired")
                )
        ;

                return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
