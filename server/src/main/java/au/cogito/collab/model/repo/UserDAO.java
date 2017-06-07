package au.cogito.collab.model.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pavankumarjoshi on 26/04/2017.
 */
@Repository
public interface UserDAO extends CrudRepository<UserEntity,String>{

    public UserEntity findByUserEmail(String userEmail);

    public List<UserEntity> findAllByActive(boolean active);
}
