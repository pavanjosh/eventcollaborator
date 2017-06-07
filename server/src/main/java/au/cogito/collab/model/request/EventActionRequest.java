package au.cogito.collab.model.request;

import java.io.Serializable;

/**
 * Created by pavankumarjoshi on 6/06/2017.
 */
public class EventActionRequest implements Serializable {

    private static final long serialVersionUID = 7318771172815352779L;
    private String eventName;
    private String eventActions;
    private String userEmail;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventActions() {
        return eventActions;
    }

    public void setEventActions(String eventActions) {
        this.eventActions = eventActions;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
