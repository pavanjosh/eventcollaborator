package au.cogito.collab.model.request;

import java.io.Serializable;

/**
 * Created by pavankumarjoshi on 15/05/2017.
 */

public class UserRegistrationRequest implements Serializable {

    private String userEmail;

    private String password;

    private String firstName;

    private String lastName;

    private long dob;


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }
}
