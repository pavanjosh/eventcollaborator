package au.cogito.collab.model.repo;

import javax.persistence.*;

/**
 * Created by pavankumarjoshi on 15/05/2017.
 */

@Entity
@Table(name = "login_table")
public class Login {

    @Id
    private String email;

    private String password;



    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
