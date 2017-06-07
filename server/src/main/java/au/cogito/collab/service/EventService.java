package au.cogito.collab.service;

import au.cogito.collab.model.repo.Event;
import au.cogito.collab.model.repo.EventDAO;
import au.cogito.collab.model.repo.UserDAO;
import au.cogito.collab.model.repo.UserEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.management.Query;
import java.util.List;
import java.util.Set;

/**
 * Created by pavankumarjoshi on 4/05/2017.
 */
@Service
public class EventService implements EventServiceIF {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public void addInvitees(String eventName, List<String> userEmailIds)
        throws Exception{
        Event event = eventDAO.findOne(eventName);
        if(event == null){
            throw new Exception("No event found for this event Name");
        }
        List<UserEntity> invitedUserEntities = event.getInvitedUserEntities();

        if(!CollectionUtils.isEmpty(userEmailIds)){
            for(String email : userEmailIds){
                UserEntity one = userDAO.findOne(email);
                if(one!=null){
                    invitedUserEntities.add(one);
                }
                else{
                    // TODO
                    // If the user is not there already in the database
                    // A mail has to be sent to the user asking him to download the app and register with the mail
                    // address. Once the mail address is registered, if the users syas get all the events,
                    // It will scan this user address in all the event mails and fetch the events.
                }

            }
        }

        eventDAO.save(event);
    }

    @Override
    public List<Event> getAllPendingEvents(String userEmail) {

        List<Event> pendingEventsForUser = eventDAO.findPendingEventsForUser(userEmail);

        return pendingEventsForUser;
    }
}

