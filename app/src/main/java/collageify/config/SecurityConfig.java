package collageify.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private UserDetailsService uds;

    /**
     *
     * @param uds
     */

    public SecurityConfig(UserDetailsService uds){
        this.uds = uds;
    }
    /*
    new keyword added here, but it may cause issues...
    look here if slamming head into desk about errors....
    hehe you will....
    you will...
     */

    /**
     * TODO: Figure out what scheme like is it sha or something
     * calling the encode() method returns a hash and .matches takes 2 arguments...
     *
     * @return this returns the hash for a pasword using the BCryptPasswordEncoder
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    /**
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll());
        /*http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated());*/
        return http.build();
    }

}
