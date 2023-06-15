/*
package collageify.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Implement your custom authentication logic here
        if (passwordIsValid(password, userDetails.getPassword())) {
            // Return an authenticated authentication object
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            // Throw an AuthenticationException for failed authentication
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        // Return true if this provider supports the provided authentication type
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean passwordIsValid(String rawPassword, String encodedPassword) {
        // Implement your password validation logic here
        // You may use a PasswordEncoder to validate the password
        // For example: return passwordEncoder.matches(rawPassword, encodedPassword);

    }
}
*/
