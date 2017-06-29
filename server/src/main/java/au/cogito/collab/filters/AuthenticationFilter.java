package au.cogito.collab.filters;

import au.cogito.collab.controllers.ApiController;
import au.cogito.collab.security.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by pavankumarjoshi on 30/05/2017.
 */
public class AuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;
    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Optional<String> userName = Optional.ofNullable(httpRequest.getHeader("X-Auth-Username"));
        Optional<String> password = Optional.ofNullable(httpRequest.getHeader("X-Auth-Password"));
        Optional<String> token = Optional.ofNullable(httpRequest.getHeader("X-Auth-token"));

        LOG.debug("In Authentication Filter");

        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

        try{
            if(requestForUnauthrisedPath(resourcePath)){
                // log that this request needs no authentication
                LOG.debug("This URL requires no Authentication");
            }
            else{
                if (postToAuthenticate(httpRequest, resourcePath)) {
                    //logger.debug("Trying to authenticate user {} by X-Auth-Username method", userName.get());
                    if(!userName.isPresent() || !password.isPresent()){
                        throw new InternalAuthenticationServiceException("User Name and Password Missing in header");
                    }
                    LOG.debug("In Authentication Filter username {}, password {} ", userName.get(),password.get());
                    processUserNameAndPasswordAuthentication(httpResponse,userName.get(),password.get());
                    return;
                }

                if (token.isPresent()) {
                    //logger.debug("Trying to authenticate user by X-Auth-Token method. Token: {}", token.get());
                    LOG.debug("In Authentication Filter token {} ",token.get());
                    processTokenAuthentication(token.get());
                }

                else{
                    throw new BadCredentialsException("Please login and provide the token in request");
                }
            }


            addSessionContextToLogging();
            chain.doFilter(request, response);
        }
        catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            SecurityContextHolder.clearContext();
            LOG.error("Internal authentication service exception", internalAuthenticationServiceException);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (BadCredentialsException authenticationException) {
            SecurityContextHolder.clearContext();
            LOG.error("Bad credentials exception", authenticationException);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        } catch (AuthenticationException authenticationException) {
            SecurityContextHolder.clearContext();
            LOG.error("Authentication exception", authenticationException);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        } finally {
            MDC.remove(TOKEN_SESSION_KEY);
            MDC.remove(USER_SESSION_KEY);
        }
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest, String resourcePath) {
        return ApiController.AUTHENTICATE_URL.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equals("POST");
    }

    private boolean requestForUnauthrisedPath(String resourcePath){
         if(resourcePath.equals("/user/register")
                 || resourcePath.equals("/home")
                 || resourcePath.equals("/index")
                 ||resourcePath.equals("/user/forgot")
                 ||resourcePath.equals("/user/reset")){
             return true;
         }
        return false;
    }

    private void addSessionContextToLogging(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication != null) {
            String token = (String) authentication.getPrincipal().toString();
            String userValue = (String) authentication.getDetails().toString();

            MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
            String tokenValue = encoder.encodePassword(token, "random_salt");
            MDC.put(TOKEN_SESSION_KEY, tokenValue);
            MDC.put(USER_SESSION_KEY, userValue);
        }

    }
    private void processTokenAuthentication(String token){
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
        Authentication authenticate = authenticationManager.authenticate(requestAuthentication);
        if(authenticate==null ){
            throw new InternalAuthenticationServiceException("Unable to authenticate user");
        }
        else if(!authenticate.isAuthenticated()){
            throw new BadCredentialsException("Not a valid user");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
    private void processUserNameAndPasswordAuthentication(HttpServletResponse httpResponse, String userName, String password)
            throws IOException{

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(authenticate==null ){
            throw new InternalAuthenticationServiceException("Unable to authenticate user");
        }
        else if(!authenticate.isAuthenticated()){
            throw new BadCredentialsException("Not a valid user");
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        TokenResponse tokenResponse= new TokenResponse();
        tokenResponse.setToken(authenticate.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
