package au.cogito.collab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by pavankumarjoshi on 19/06/2017.
 */

@Service
public class CollabEmailService implements CollabEmailServiceIF {

    private static final Logger Log = LoggerFactory.getLogger(CollabEmailService.class);

    @Value("${reset.password.from.email}")
    private String fromEmail;

    @Value("${mail.server.password.reset.subject}")
    private String passwordResetSubject;

    @Value("${mail.server.password.reset.content}")
    private String passwordResetContent;

    @Value("${mail.server.email.signature}")
    private String mailSignature;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Override
    public void sendEmail(String userEmail, String resetPasswordUrl) {
        Log.debug("in sendEmail method of Email Service");

        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom(fromEmail);
        passwordResetEmail.setTo(userEmail);
        passwordResetEmail.setSubject(passwordResetSubject);
        passwordResetEmail.setText(passwordResetContent + "\n\n" + resetPasswordUrl +"\n\n" +
                " The token is valid for 4 hours only \n\n"+ mailSignature);

        Log.info("The SimpleMailMessage to be sent is {}", passwordResetEmail);
        javaMailSender.send(passwordResetEmail);
        Log.debug("The mail is sent successfully");
    }
}
