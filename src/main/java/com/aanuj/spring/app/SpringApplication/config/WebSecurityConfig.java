package com.aanuj.spring.app.SpringApplication.config;

import com.aanuj.spring.app.SpringApplication.entities.Enum.Role;
import com.aanuj.spring.app.SpringApplication.filters.JwtAuthFilter;
import com.aanuj.spring.app.SpringApplication.handlers.Oauth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.aanuj.spring.app.SpringApplication.entities.Enum.Permission.*;
import static com.aanuj.spring.app.SpringApplication.entities.Enum.Role.ADMIN;
import static com.aanuj.spring.app.SpringApplication.entities.Enum.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private static final String[] publicRoutes = {
            "/errors", "/auth/**", "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> auth
                .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/posts/**")
                        .hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers(HttpMethod.POST, "/posts/**")
                        .hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.GET, "/posts/**")
                        .hasAnyAuthority(POST_VIEW.name())
                        .requestMatchers(HttpMethod.PUT, "/posts/**")
                        .hasAnyAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/posts/**")
                        .hasAnyAuthority(POST_DELETE.name())
                .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(
                        sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauthToConfig -> oauthToConfig
                        .failureUrl("/login?error=true")
                        .successHandler(oauth2SuccessHandler));
//                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    /*@Bean
    UserDetailsService myInMemoryuserDetailService(){
        UserDetails normalUsers = User.withUsername("aanuj")
                .password(passwordEncoder().encode("Aanuj@1234"))
                .roles("USER")
                .build();

        UserDetails adminUser = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(normalUsers, adminUser);
    }*/


}
