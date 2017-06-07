package au.cogito.collab.service;

import au.cogito.collab.model.repo.Event;

import java.util.List;

/**
 * Created by pavankumarjoshi on 4/05/2017.
 */
public interface EventServiceIF {

    public void addInvitees(String eventName, List<String> email) throws Exception;

    public List<Event> getAllPendingEvents(String userEmail);
}
