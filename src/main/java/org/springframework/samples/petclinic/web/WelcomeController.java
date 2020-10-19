package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    

			List<Person> persons = new ArrayList<>();
			Person person1 = new Person();
			person1.setFirstName("Francisco José ");
			person1.setLastName("Borrego Caro");
			persons.add(person1);
			
			Person person2 = new Person();
			person2.setFirstName("Sergio ");
			person2.setLastName("Arias Ruda");
			persons.add(person2);

			Person person3 = new Person();
			person3.setFirstName("Antonio ");
			person3.setLastName("Funes Mejías");
			persons.add(person3);

			Person person4 = new Person();
			person4.setFirstName("Alejandro ");
			person4.setLastName("Morales García");
			persons.add(person4);
			
			Person person5 = new Person();
			person5.setFirstName("Jesús ");
			person5.setLastName("Vargas Zambrana");
			persons.add(person5);
			
			model.put("persons", persons);
			model.put("title", "Titulo de prueba");
			model.put("group", "Desarrolladores");  
		  
	    return "welcome";
	  }
}
