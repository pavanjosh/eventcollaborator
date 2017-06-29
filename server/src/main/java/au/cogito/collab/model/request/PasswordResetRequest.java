package au.cogito.collab.model.request;

/**
 * Created by pavankumarjoshi on 29/06/2017.
 */
public class PasswordResetRequest {

    private String userEmail;
    private String password;

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
}
