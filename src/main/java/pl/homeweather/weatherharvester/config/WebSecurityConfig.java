package pl.homeweather.weatherharvester.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;
import static pl.homeweather.weatherharvester.config.Roles.ADMIN_ROLE;
import static pl.homeweather.weatherharvester.config.Roles.USER_ROLE;

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

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String LOGOUT_ENDPOINT = "/logout";
    private static final String ADMIN_CHECK_ENDPOINT = "/admin-check";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/actuator/health").permitAll()
                .pathMatchers(ADMIN_CHECK_ENDPOINT).permitAll()
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers(HttpMethod.POST, LOGIN_ENDPOINT).permitAll()
                .pathMatchers(HttpMethod.POST, LOGOUT_ENDPOINT).permitAll()
                .pathMatchers(HttpMethod.POST).hasRole(ADMIN_ROLE)
                .pathMatchers(HttpMethod.PUT).hasRole(ADMIN_ROLE)
                .pathMatchers("/settings/**").hasRole(ADMIN_ROLE)
                .anyExchange().authenticated()
                .and().cors()
                .and().formLogin()
                .authenticationSuccessHandler((webFilterExchange, authentication) -> Mono.empty())
                .authenticationFailureHandler((webFilterExchange, e) -> Mono.error(e))
                .and().httpBasic().and().build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return createDelegatingPasswordEncoder();
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configurationBase = new CorsConfiguration();
        configurationBase.setAllowedOrigins(singletonList(corsAllowedOrigin));
        configurationBase.setAllowedMethods(singletonList("GET"));
        configurationBase.setAllowedHeaders(singletonList(AUTHORIZATION));

        CorsConfiguration configurationAdminCheck = new CorsConfiguration();
        configurationAdminCheck.setAllowedOrigins(singletonList(corsAllowedOrigin));
        configurationAdminCheck.setAllowedMethods(singletonList("GET"));
        configurationAdminCheck.setAllowCredentials(true);

        CorsConfiguration configurationSettings = new CorsConfiguration();
        configurationSettings.setAllowedOrigins(singletonList(corsAllowedOrigin));
        configurationSettings.setAllowedMethods(Arrays.asList("GET", "PUT"));
        configurationSettings.setAllowedHeaders(singletonList(HttpHeaders.CONTENT_TYPE));
        configurationSettings.setAllowCredentials(true);

        CorsConfiguration configurationLogin = new CorsConfiguration();
        configurationLogin.setAllowedOrigins(singletonList(corsAllowedOrigin));
        configurationLogin.setAllowedMethods(Arrays.asList("GET", "POST"));
        configurationLogin.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configurationBase);
        source.registerCorsConfiguration(ADMIN_CHECK_ENDPOINT, configurationAdminCheck);
        source.registerCorsConfiguration("/settings/**", configurationSettings);
        source.registerCorsConfiguration(LOGIN_ENDPOINT, configurationLogin);
        source.registerCorsConfiguration(LOGOUT_ENDPOINT, configurationLogin);

        return source;
    }
}
