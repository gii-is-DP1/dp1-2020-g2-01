package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.TallerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private TallerService tallerService;
	
	@GetMapping(value = "/listadoEmpleados")
	public String listadoEmpleados(ModelMap m) {
		
		String vista= "/empleados/listadoEmpleados";
		Iterable<Empleado> empleados = empleadoService.findAll();
        m.addAttribute("empleado", empleados);
		return vista;
	}
		
	@GetMapping(value = "/new")
	public String crearEmpleado(ModelMap model) {
		String vista = "empleados/editEmpleado";
		model.addAttribute("talleres", tallerService.findAll());
		model.addAttribute("empleado", new Empleado());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, ModelMap model) {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("empleado", empleado);
			model.addAttribute("talleres", tallerService.findAll());
			vista = "empleados/editEmpleado";
		} else {
			empleadoService.saveEmpleado(empleado);
			model.addAttribute("message", "Empleado creado con éxito.");
			String username = empleado.getUsuario().getUsername();
			vista = mostrarDetalles(username, model);
		}
		return vista;
	}
	
	@GetMapping(value = "/empleadoDetails/{username}")
	public String mostrarDetalles(@PathVariable("username") String username, ModelMap model) {
		Optional<Empleado> empleado = this.empleadoService.findEmpleadoByUsuarioUsername(username);
		if(!empleado.isPresent()) {
			model.addAttribute("message", "Cliente no encontrado");
			model.addAttribute("messageType", "danger");
			return "/";
		}
		String username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString();
		if(username.equals(username2) || auth.equals("admin")) {
			model.addAttribute("empleado", empleado.get());

			return "empleados/empleadoDetails";
		}else {
			return "redirect:/";
		}
	}
	
	
	@GetMapping(value = "/delete/{empleadoId}")
	public String borrarEmpleado(@PathVariable("empleadoId") int id, ModelMap model) {
		String vista;
		Optional<Empleado> op = empleadoService.findById(id);
		if(op.isPresent()) {
			empleadoService.delete(op.get());
			model.addAttribute("message", "Empleado borrado con éxito.");
		} else {
			model.addAttribute("message", "Empleado no encontrado.");
			
		}
		vista = listadoEmpleados(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{username}")
	public String editarEmpleado(@PathVariable("username") String username, ModelMap model) {
		String vista = "empleados/editEmpleado";
		String username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
		if(!empleado.isPresent()) {
			model.addAttribute("message", "Empleado no encontrado.");
			vista = "/";
		} else {
			String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString();
			if(username.equals(username2) || auth.equals("admin")) {
				model.addAttribute("talleres", tallerService.findAll());
				model.addAttribute("empleado", empleado.get());
			}else {
				vista="/";
			}
		}
		return vista;
	}
	
	
	
}
