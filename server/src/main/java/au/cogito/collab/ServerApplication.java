package au.cogito.collab;

import au.cogito.collab.model.repo.Event;
import au.cogito.collab.model.repo.EventDAO;
import au.cogito.collab.model.repo.UserDAO;
import au.cogito.collab.model.repo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = { "au.cogito.collab"})
public class ServerApplication {


	public static void main(String[] args) {

		SpringApplication.run(ServerApplication.class, args);

	}

	@PostConstruct
	public void test(){
//		UserEntity userEntity1 = new UserEntity();
//		UserEntity userEntity2 = new UserEntity();
//
//		Event event1 = new Event();
//		Event event2 = new Event();
//
//		event1.setOwnerEmail("test1@test.com");
//		event1.setStartLocation("start1");
//		event1.setEventName("event1");
//		event1.setEndLocation("end1");
//
//		event2.setOwnerEmail("test2@test.com");
//		event2.setStartLocation("start2");
//		event2.setEventName("event2");
//		event2.setEndLocation("end2");
//
//		event1.getUserEntities().add(userEntity1);
//		event1.getUserEntities().add(userEntity2);
//
//		event2.getUserEntities().add(userEntity1);
//		event2.getUserEntities().add(userEntity2);
//
//		userEntity1.setEmail("test1@test.com");
//		userEntity1.setPassword("1234");
//		userEntity2.setEmail("test2@test.com");
//		userEntity2.setPassword("1234");
//
//		userEntity1.getEvents().add(event1);
//		userEntity1.getEvents().add(event2);
//
//		userEntity2.getEvents().add(event1);
//		userEntity2.getEvents().add(event2);
//
//		userDAO.save(userEntity1);
//		userDAO.save(userEntity2);
//
//		eventDAO.save(event1);
//		eventDAO.save(event2);



	}
}
