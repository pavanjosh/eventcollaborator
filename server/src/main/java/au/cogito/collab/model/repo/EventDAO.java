package au.cogito.collab.model.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pavankumarjoshi on 2/05/2017.
 */
@Repository
public interface EventDAO extends CrudRepository<Event,String> {

    public Event findByEventName(String eventName);

    public List<Event> findEventByOwnerEmail(String userEmail);

    @Query(value = "SELECT * FROM event INNER JOIN invited_users on invited_users.event_name=event.event_name\n" +
            "WHERE invited_users.user_email=?1",nativeQuery = true)
    public List<Event> findPendingEventsForUser(String userEmail);

    @Query(value = "SELECT * FROM event INNER JOIN accepted_users on accepted_users.event_name=event.event_name\n" +
            "WHERE accepted_users.user_email=?1",nativeQuery = true)
    public List<Event> findAcceptedEventsForUser(String userEmail);
}
