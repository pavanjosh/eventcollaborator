package au.cogito.collab.service;

import au.cogito.collab.model.repo.Event;
import au.cogito.collab.model.repo.UserEntity;

import java.util.List;

/**
 * Created by pavankumarjoshi on 4/05/2017.
 */

public interface UserServiceIF {

    public List<Event> getAllPendingEvents(String userEmail);
    public List<Event> getAllAcceptedEvents(String userEmail);

    public List<Event> getAllOwnedEvents(String userEmail);

    public List<UserEntity> getAllUsers();

}
