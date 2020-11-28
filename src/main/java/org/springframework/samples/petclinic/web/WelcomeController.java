package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.samples.petclinic.model.Persona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    

			List<Persona> persons = new ArrayList<>();
			Persona person1 = new Persona();
			person1.setNombre("Francisco José ");
			person1.setApellidos("Borrego Caro");
			persons.add(person1);
			
			Persona person2 = new Persona();
			person2.setNombre("Sergio ");
			person2.setApellidos("Arias Ruda");
			persons.add(person2);

			Persona person3 = new Persona();
			person3.setNombre("Antonio ");
			person3.setApellidos("Funes Mejías");
			persons.add(person3);

			Persona person4 = new Persona();
			person4.setNombre("Alejandro ");
			person4.setApellidos("Morales García");
			persons.add(person4);
			
			Persona person5 = new Persona();
			person5.setNombre("Jesús ");
			person5.setApellidos("Vargas Zambrana");
			persons.add(person5);
			
			model.put("persons", persons);
			model.put("title", "Titulo de prueba");
			model.put("group", "Desarrolladores");  
		  
	    return "welcome";
	  }
}
