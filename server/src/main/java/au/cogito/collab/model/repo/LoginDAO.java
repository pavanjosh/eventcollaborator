package au.cogito.collab.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by pavankumarjoshi on 15/05/2017.
 */

@Repository
public interface LoginDAO extends CrudRepository<Login,String> {

    public Login findByEmailAndPassword(String userEmail, String password);
    public Login findByEmail(String userEmail);

}
