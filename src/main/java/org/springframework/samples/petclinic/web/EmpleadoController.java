package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.service.EmpleadoService;
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
		model.addAttribute("empleado", new Empleado());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("empleado", empleado);
			vista = "empleados/editEmpleado";
		} else {
			empleadoService.saveEmpleado(empleado);
			model.addAttribute("message", "Empleado created successfully");
			vista = listadoEmpleados(model);
		}
		return vista;
	}
	
	
	@GetMapping(value = "/delete/{empleadoId}")
	public String borrarEmpleado(@PathVariable("empleadoId") int id, ModelMap model) {
		String vista;
		Optional<Empleado> op = empleadoService.findById(id);
		if(op.isPresent()) {
			empleadoService.delete(op.get());
			model.addAttribute("message", "Empleado deleted successfully");
		} else {
			model.addAttribute("message", "Empleado not found");
			
		}
		vista = listadoEmpleados(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{empleadoId}")
	public String editarEmpleado(@PathVariable("empleadoId") int id, ModelMap model) {
		String vista = "empleados/editEmpleado";
		Optional<Empleado> empleado = empleadoService.findById(id);
		if(!empleado.isPresent()) {
			model.addAttribute("message", "Empleado not found");
			vista = listadoEmpleados(model);
		} else {
			model.addAttribute("empleado", empleado.get());
		}
		return vista;
	}
	
	
	
}
