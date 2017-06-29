package au.cogito.collab.service;

import org.springframework.scheduling.annotation.Async;

/**
 * Created by pavankumarjoshi on 19/06/2017.
 */

public interface CollabEmailServiceIF {

     public void sendEmail(String userEmail, String resetPasswordUrl);

}
