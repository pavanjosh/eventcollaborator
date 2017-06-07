package au.cogito.collab.controllers;

import au.cogito.collab.model.repo.Event;
import au.cogito.collab.model.repo.EventDAO;
import au.cogito.collab.model.repo.UserDAO;
import au.cogito.collab.model.repo.UserEntity;
import au.cogito.collab.model.request.EventActionRequest;
import au.cogito.collab.model.request.EventActions;
import au.cogito.collab.model.request.EventCreateRequest;
import au.cogito.collab.model.request.EventInvitation;
import au.cogito.collab.service.EventService;
import com.google.gson.Gson;
import oracle.jrockit.jfr.events.EventHandlerCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

/**
 * Created by pavankumarjoshi on 2/05/2017.
 */

@Controller
public class EventController {

    @Autowired
    private EventDAO eventDAO;


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventService eventService;

    @RequestMapping(name = "EventController", value="/event/create",method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createEvent(@RequestBody EventCreateRequest eventCreateRequest){

        Event one = eventDAO.findOne(eventCreateRequest.getEventName());
        UserEntity user = userDAO.findOne(eventCreateRequest.getUserEmail());
        if(one == null){
            Event event = new Event();
            event.setEventName(eventCreateRequest.getEventName());
            event.setStartLocation(eventCreateRequest.getStartLocation());
            event.setEndLocation(eventCreateRequest.getEndLocation());
            event.setOwnerEmail(user.getUserEmail());

            eventDAO.save(event);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @RequestMapping(name = "EventController", value="/event/invite/people",method = RequestMethod.POST)
    public ResponseEntity<String> invitePeopleForEvent(@RequestBody EventInvitation eventInvitation) throws Exception{

        String eventName = eventInvitation.getEventName();
        List<String> userEmails = eventInvitation.getUserEmails();
        eventService.addInvitees(eventName,userEmails);

        return new ResponseEntity<String>(HttpStatus.OK);
    }
    @RequestMapping(name = "EventController", value="/event/allevents",method = RequestMethod.GET)
    public ResponseEntity<String> getAllEvents(){


        Iterable<Event> all = eventDAO.findAll();
        Gson gson = new Gson();
        String s = gson.toJson(all);

        return new ResponseEntity<String>(s,HttpStatus.OK);
    }

    @RequestMapping(name = "EventController", value="/event/allevents/user",method = RequestMethod.GET)
    public ResponseEntity<String> getAllEventsForUser(@RequestHeader String userEmail){

        Iterable<Event> all = eventDAO.findAll();
        Gson gson = new Gson();
        String s = gson.toJson(all);

        return new ResponseEntity<String>(s,HttpStatus.OK);
    }

    @RequestMapping(name = "EventController", value="/event/action",method = RequestMethod.POST)
    public ResponseEntity<String> performActionOnEvent(@RequestBody EventActionRequest eventActionRequest){

        String eventName = eventActionRequest.getEventName();
        String eventActions = eventActionRequest.getEventActions();
        String userEmail = eventActionRequest.getUserEmail();
        Event byEventName = eventDAO.findByEventName(eventName);
        if(byEventName == null){
            return new ResponseEntity<String>("Event Does Not Exist",HttpStatus.NOT_FOUND);
        }
        if(eventActions.equalsIgnoreCase(EventActions.ACCEPT.toString())){

            UserEntity byUserEmail = userDAO.findByUserEmail(userEmail);
            byEventName.getAcceptedUserEntities().add(byUserEmail);
            byEventName.getInvitedUserEntities().remove(byUserEmail);
        }
        else if (eventActions.equalsIgnoreCase(EventActions.DECLINE.toString())){
            // This is a zombie event.
            UserEntity byUserEmail = userDAO.findByUserEmail(userEmail);
            byEventName.getInvitedUserEntities().remove(byUserEmail);
        }
        else if(eventActions.equalsIgnoreCase(EventActions.DELETE.toString())){
            eventDAO.delete(eventName);
        }
        Iterable<Event> all = eventDAO.findAll();
        Gson gson = new Gson();
        String s = gson.toJson(all);
        eventDAO.save(byEventName);
        return new ResponseEntity<String>(s,HttpStatus.OK);
    }

}
