package pl.homeweather.weatherharvester.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig implements WebFluxConfigurer {

    @Value("${credentials.user}")
    private String userName;

    @Value("${credentials.admin}")
    private String adminName;

    @Value("${credentials.user_password}")
    private String userPass;

    @Value("${credentials.admin_password}")
    private String adminPass;

    @Value("${cors.allowed-origin}")
    private String corsAllowedOrigin;

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/actuator/health").permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/settings/**").permitAll()
                .pathMatchers(HttpMethod.POST).hasRole(ADMIN_ROLE)
                .pathMatchers(HttpMethod.PUT).hasRole(ADMIN_ROLE)
                .pathMatchers("/settings/**").hasRole(ADMIN_ROLE)
                .anyExchange().authenticated()
                .and().formLogin()
                .and().httpBasic().and().build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername(userName)
                .passwordEncoder(passwordEncoder::encode)
                .password(userPass)
                .roles(USER_ROLE)
                .build();

        UserDetails admin = User.withUsername(adminName)
                .passwordEncoder(passwordEncoder::encode)
                .password(adminPass)
                .roles(ADMIN_ROLE)
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST")
                .allowedOrigins(corsAllowedOrigin);
        registry.addMapping("/settings/cron")
                .allowedMethods("GET", "PUT")
                .allowedOrigins(corsAllowedOrigin);
    }
}
