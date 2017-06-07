package au.cogito.collab.service;

import au.cogito.collab.model.repo.Event;
import au.cogito.collab.model.repo.EventDAO;
import au.cogito.collab.model.repo.UserDAO;
import au.cogito.collab.model.repo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavankumarjoshi on 4/05/2017.
 */
@Service
public class UserService implements UserServiceIF {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EventDAO eventDAO;

    @Override
    public List<Event> getAllPendingEvents(String userEmail) {
       List<Event> pendingEventsForUserEvents = eventDAO.findPendingEventsForUser(userEmail);
       return pendingEventsForUserEvents;
    }
    @Override
    public List<Event> getAllAcceptedEvents(String userEmail) {
        List<Event> pendingEventsForUserEvents = eventDAO.findAcceptedEventsForUser(userEmail);
        return pendingEventsForUserEvents;
    }
    @Override
    public List<Event> getAllOwnedEvents(String userEmail){
        List<Event> ownedEvents = eventDAO.findEventByOwnerEmail(userEmail);
        return ownedEvents;
    }

    @Override
    public List<UserEntity> getAllUsers(){
        List<UserEntity> users = userDAO.findAllByActive(true);
        return users;
    }

}
