package au.cogito.collab.security;

import au.cogito.collab.model.authentication.AuthenticationWithToken;
import au.cogito.collab.service.LoginService;
import au.cogito.collab.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Created by pavankumarjoshi on 31/05/2017.
 */
public class DomainUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginService loginService;

    private TokenService tokenService;

    public DomainUsernamePasswordAuthenticationProvider(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Override
    public AuthenticationWithToken authenticate(Authentication authentication)  {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        AuthenticationWithToken authenticationWithToken = new AuthenticationWithToken(username, password);
        if(loginService.login(username,password)){
            String newToken = tokenService.generateNewToken();
            authenticationWithToken.setAuthenticated(true);
            tokenService.store(newToken, authenticationWithToken);
            authenticationWithToken.setToken(newToken);
        }
        return authenticationWithToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
