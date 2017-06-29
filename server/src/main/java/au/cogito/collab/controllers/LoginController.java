package au.cogito.collab.controllers;

import au.cogito.collab.model.repo.Login;
import au.cogito.collab.model.repo.LoginDAO;
import au.cogito.collab.model.repo.UserDAO;
import au.cogito.collab.model.repo.UserEntity;
import au.cogito.collab.model.request.PasswordResetRequest;
import au.cogito.collab.model.request.UserRegistrationRequest;
import au.cogito.collab.service.CollabEmailService;
import au.cogito.collab.service.CollabEmailServiceIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UrlPathHelper;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;


/**
 * Created by pavankumarjoshi on 26/04/2017.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginDAO loginDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CollabEmailServiceIF emailService;

    @Value("${collab.base.url}")
    private String collabBaseUrl;

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(name="LoginController",value = "/login",method= RequestMethod.POST)
    public ResponseEntity<String> getLogin(@RequestBody Login login){


        Login byUserEmailAndPassword = loginDAO.findByEmailAndPassword(login.getEmail()
                , login.getPassword());

        if(byUserEmailAndPassword!=null){
            return new ResponseEntity<String>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

    }
    @RequestMapping(name="LoginController",value = "/user/register",method= RequestMethod.POST)
    public ResponseEntity<String> createLogin(@RequestBody UserRegistrationRequest userRegistrationRequest){


        if(!loginDAO.exists(userRegistrationRequest.getUserEmail())){
        //if(loginDAO.findByEmail(userRegistrationRequest.getUserEmail())==null){
            UserEntity userEntity = new UserEntity();
            Login login = new Login();

            login.setEmail(userRegistrationRequest.getUserEmail());
            login.setPassword(userRegistrationRequest.getPassword());

            userEntity.setCreatedDate(new Date());
            userEntity.setDob(userRegistrationRequest.getDob());
            userEntity.setFirstName(userRegistrationRequest.getFirstName());
            userEntity.setLastName(userRegistrationRequest.getLastName());
            userEntity.setUserEmail(userRegistrationRequest.getUserEmail());

            login.setUserEntity(userEntity);

            userDAO.save(userEntity);
            loginDAO.save(login);
            return new ResponseEntity<String>("User Created Successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("User already present",HttpStatus.FORBIDDEN);
        }

    }

    /**
     *
     * @param userEmail
     * @param httpServletRequest
     * @return
     */

    @RequestMapping(name="LoginController",value = "/user/forgot",method= RequestMethod.POST)
    public ResponseEntity<String> forgotPassword(@RequestBody String userEmail,HttpServletRequest httpServletRequest){


        UserEntity byUserEmail = userDAO.findByUserEmail(userEmail);

        if(byUserEmail != null){
            // If user is found create a random token against the user. store it for furthur checking
            String token = UUID.randomUUID().toString();
            byUserEmail.setResetToken(token);
            userDAO.save(byUserEmail);

            UrlPathHelper urlPathHelper = new UrlPathHelper();

            String resetPasswordUrl = collabBaseUrl+ urlPathHelper.getContextPath(httpServletRequest)+
                    "/user/reset?token="+token;

            emailService.sendEmail(userEmail, resetPasswordUrl);

            return new ResponseEntity<String>("Forgot Mail sent successfully",HttpStatus.OK);
        }
        else{
            LOG.info("No user found by this email id {}",userEmail);
            return new ResponseEntity<String>("No user found by this email id ",HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/user/reset" , method = RequestMethod.GET)
    public ResponseEntity<String> resetPasswordForToken(@RequestParam String token){

        LOG.debug("token for reset password is " + token);
        UserEntity byResetToken = userDAO.findByResetToken(token);
        if(byResetToken!=null){
            LOG.debug("user for which the token generated is {}",byResetToken);
        }
        else{
            return new ResponseEntity<String>("User with the token not found or token expired",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("User found for the token",HttpStatus.OK);
    }

    @RequestMapping(value = "/user/reset" , method = RequestMethod.POST)
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){

        if(passwordResetRequest != null
                && passwordResetRequest.getUserEmail() !=null
                && passwordResetRequest.getPassword() != null) {

            LOG.debug("email id for which the password reset requested is  {}", passwordResetRequest.getUserEmail());
            Login byEmail = loginDAO.findByEmail(passwordResetRequest.getUserEmail());
            if (byEmail != null) {
               byEmail.setPassword(passwordResetRequest.getPassword());
               loginDAO.save(byEmail);
                return new ResponseEntity<String>("Password reset Success ", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("User with the email id not found ", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>("Improper request",HttpStatus.BAD_REQUEST);
    }
}
