package au.cogito.collab.model.repo;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by pavankumarjoshi on 1/05/2017.
 */

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @NotNull
    private String eventName;


    @ManyToMany
    @JoinTable(name="INVITED_USERS",joinColumns = @JoinColumn(name="EVENT_NAME")
            ,inverseJoinColumns = @JoinColumn(name = "USER_EMAIL") )
    private List<UserEntity> invitedUserEntities = new ArrayList<>();


    @ManyToMany
    @JoinTable(name="ACCEPTED_USERS",joinColumns = @JoinColumn(name="EVENT_NAME")
            ,inverseJoinColumns = @JoinColumn(name = "USER_EMAIL") )
    private List<UserEntity> acceptedUserEntities = new ArrayList<>();


    private String startLocation;

    private String endLocation;

    private String ownerEmail;


    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }


    public List<UserEntity> getInvitedUserEntities() {
        return invitedUserEntities;
    }

    public void setInvitedUserEntities(List<UserEntity> invitedUserEntities) {
        this.invitedUserEntities = invitedUserEntities;
    }

    public List<UserEntity> getAcceptedUserEntities() {
        return acceptedUserEntities;
    }

    public void setAcceptedUserEntities(List<UserEntity> acceptedUserEntities) {
        this.acceptedUserEntities = acceptedUserEntities;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


}
