package org.springframework.samples.petclinic.web;


import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {
	
	@Autowired
	private VehiculoService vehiculoService;

	
	@GetMapping(value = { "/listadoVehiculos" })
	public String listadoVehiculos(ModelMap model) {
		String vista = "vehiculos/listadoVehiculos";
		Iterable<Vehiculo> vehiculos = vehiculoService.findAll();
		model.put("vehiculos", vehiculos);
		return vista;
		
	}
	
	
	@GetMapping(value = "/new")
	public String crearVehiculo(ModelMap model) {
		String vista = "vehiculos/editVehiculo";
		model.addAttribute("vehiculo", new Vehiculo());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarVehiculo(@Valid Vehiculo vehiculo, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("vehiculo", vehiculo);
			vista = "vehiculos/editVehiculo";
		} else {
			vehiculoService.saveVehiculo(vehiculo);
			model.addAttribute("message", "Vehiculo created successfully");
			vista = listadoVehiculos(model);
		}
		
		return vista;
	}
	
	
	@GetMapping(value = "/delete/{vehiculoId}")
	public String borrarVehiculo(@PathVariable("vehiculoId") int id, ModelMap model) {
		String vista;
		Optional<Vehiculo> op = vehiculoService.findVehiculoById(id);
		if(op.isPresent()) {
			vehiculoService.delete(op.get());
			model.addAttribute("message", "Vehiculo deleted successfully");
		} else {
			model.addAttribute("message", "Vehiculo not found");
			
		}
		vista = listadoVehiculos(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{vehiculoId}")
	public String editarVehiculo(@PathVariable("vehiculoId") int id, ModelMap model) {
		String vista = "vehiculos/editVehiculo";
		Optional<Vehiculo> vehiculo = vehiculoService.findVehiculoById(id);
		if(!vehiculo.isPresent()) {
			model.addAttribute("message", "Vehiculo not found");
			vista = listadoVehiculos(model);
		} else {
			model.addAttribute("vehiculo", vehiculo.get());
		}
		return vista;
	}
	
}
