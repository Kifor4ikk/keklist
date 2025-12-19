package ru.kifor.kek.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import ru.kifor.kek.security.auth.AuthProvider;
import ru.kifor.kek.security.details.AuthenticationManagerCustom;
import ru.kifor.kek.security.details.UserDetailsServiceCustom;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private AuthProvider authProvider;

  @Autowired
  private UserDetailsServiceCustom userDetailsServiceCustom;

  @Autowired
  private AuthenticationManagerCustom authenticationManager;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/registration").permitAll()
            .requestMatchers("/account/register").anonymous()
            .anyRequest().authenticated()
        )
        .authenticationManager(authenticationManager)
        .authenticationProvider(authProvider)
        .userDetailsService(userDetailsServiceCustom)
        .formLogin(form -> form
            .loginPage("/login")
            .successHandler((request, response, authentication) -> {
              response.setStatus(HttpServletResponse.SC_OK);
              response.getWriter().write(authentication.getName());
            })
            .failureHandler((request, response, exception) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getWriter().write("Login failed");
              System.out.println("Не, нихуя");
            })
            .successForwardUrl("/zdarova")
            .permitAll()
        );
    return http.build();
  }
}
