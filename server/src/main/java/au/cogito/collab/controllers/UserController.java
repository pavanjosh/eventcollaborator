package au.cogito.collab.controllers;

import au.cogito.collab.model.repo.Event;
import au.cogito.collab.model.repo.UserDAO;
import au.cogito.collab.model.repo.UserEntity;
import au.cogito.collab.service.UserServiceIF;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by pavankumarjoshi on 15/05/2017.
 */
@Controller
public class UserController {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserServiceIF userService;

    @RequestMapping(method= RequestMethod.GET,value="/user/allpendingevents",name="getAllPendingEvents")
    public ResponseEntity<String> getAllPendingEvents(@RequestHeader String userEmail){

        List<Event> allPendingEvents = userService.getAllPendingEvents(userEmail);
        Gson gson = new Gson();

        String s = gson.toJson(allPendingEvents);
        return  new ResponseEntity<String>(s, HttpStatus.OK);
    }
    @RequestMapping(method= RequestMethod.GET,value="/user/allacceptedevents",name="getAllPendingEvents")
    public ResponseEntity<String> getAllAcceptedEvents(@RequestHeader String userEmail){

        List<Event> allAcceptedEvents = userService.getAllAcceptedEvents(userEmail);
        Gson gson = new Gson();

        String s = gson.toJson(allAcceptedEvents);
        return  new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET,value="/user/createdevents",name="getAllCreatedEvents")
    public ResponseEntity<String> getAllOwnedEvents(@RequestHeader String userEmail){

        List<Event> allPendingEvents = userService.getAllOwnedEvents(userEmail);
        Gson gson = new Gson();

        String s = gson.toJson(allPendingEvents);
        return  new ResponseEntity<String>(s,HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET,value="/user/allusers",name="getAllUsers")
    public ResponseEntity<String> getAllUsers(){

        List<UserEntity> users = userService.getAllUsers();
        Gson gson = new Gson();

        String s = gson.toJson(users);
        return  new ResponseEntity<String>(s,HttpStatus.OK);
    }
}
