package pl.homeweather.weatherharvester.controllers;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static pl.homeweather.weatherharvester.config.Roles.ADMIN_ROLE;

@RestController
public class UserController {

    @GetMapping("/admin-check")
    public Mono<Boolean> isAdmin() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.
                        getAuthentication()
                        .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_" + ADMIN_ROLE)));
    }
}
