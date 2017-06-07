package au.cogito.collab.model.request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavankumarjoshi on 3/05/2017.
 */
public class EventInvitation {

    private String eventName;

    private List<String> userEmails = new ArrayList<>();

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(List<String> userEmails) {
        this.userEmails = userEmails;
    }
}

