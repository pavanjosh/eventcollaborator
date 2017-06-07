package au.cogito.collab.controllers;

import au.cogito.collab.model.repo.*;
import au.cogito.collab.model.request.UserRegistrationRequest;
import au.cogito.collab.service.UserServiceIF;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by pavankumarjoshi on 26/04/2017.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginDAO loginDAO;

    @Autowired
    private UserDAO userDAO;

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


}
