package au.cogito.collab.service;

import au.cogito.collab.model.repo.Login;
import au.cogito.collab.model.repo.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by pavankumarjoshi on 31/05/2017.
 */
@Service
public class LoginService {

    @Autowired
    private LoginDAO loginDAO;

    public boolean login(String userName,String password){

        Login byEmailAndPassword = loginDAO.findByEmailAndPassword(userName, password);

        if(byEmailAndPassword!=null){
            return true;
        }
        return false;
    }
}
