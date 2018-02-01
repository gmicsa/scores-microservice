package ro.micsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ScoresSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                /* TODO: For security reasons, please re-enable CSRF for your production app.
                    For client apps that uses the REST service (example Angular apps),
                    see solution that includes CSRF token as a HTTP header in the response:
                    https://www.codesandnotes.be/2015/02/05/spring-securitys-csrf-protection-for-rest-services-the-client-side-and-the-server-side/
                */
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/scores/**").permitAll()
                .pathMatchers("/actuator/**").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and().httpBasic().and().build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin2018")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
}
