package org.springframework.samples.petclinic.web;



import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.TipoVehiculo;
import org.springframework.samples.petclinic.model.Vehiculo;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedMatriculaException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {
	
	private static final String FORM_CONFIRM_DELETE = "vehiculos/confirmar_vehiculo_borrado";

	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private ClienteService clienteService;
	
	//En las vistas se puede usar ${types} para mostrar todos los tipos de vehiculo disponibles
	@ModelAttribute("types")
	public List<TipoVehiculo> populateVehiculoTypes() {
		return this.vehiculoService.findVehiculoTypes();
	}

	
	@GetMapping(value = { "/listadoVehiculos" })
	public String listadoVehiculos(ModelMap model) {
		String vista = "vehiculos/listadoVehiculos";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		List<Vehiculo> vehiculos = null;
		if(cliente.isPresent()) {
			vehiculos = vehiculoService.findVehiculosCliente(cliente.get());
			model.put("vehiculos", vehiculos);
		} else {
			vehiculos = vehiculoService.findAll();
			model.put("vehiculos", vehiculos);
		}
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
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(result.hasErrors()) {
			model.addAttribute("vehiculo", vehiculo);
			vista = "vehiculos/editVehiculo";
		} else {
			
			//preguntar esta parte, ahora mismo no deja añadir o editar
			
			if(cliente.isPresent()) {  
				vehiculo.setCliente(cliente.get());
			} else {
				model.addAttribute("message", "Ha habido un error con el cliente");
				vista = listadoVehiculos(model);
				return vista;
			} 
			
			//
			
			
			try { //comprobar que la matrícula no está duplicada
				vehiculoService.saveVehiculo(vehiculo);
			} catch(DuplicatedMatriculaException ex) {
				result.rejectValue("matricula", "Ya existe un vehículo con la misma matrícula", "Ya existe un vehículo con la misma matrícula");
				return "vehiculos/editVehiculo";
			}
			model.addAttribute("message", "Vehiculo created successfully");
			vista = listadoVehiculos(model);
		}
		
		return vista;
	}
	
	@GetMapping(value = "/delete/{vehiculoId}")
	public String initDeleteVehiculo(@PathVariable ("vehiculoId") int id, ModelMap model) {
		String vista;
		Vehiculo v = vehiculoService.findVehiculoById(id).get();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String propietario = v.getCliente().getUser().getUsername();
		if(username.equals(propietario)) {
			if(!v.getCitas().isEmpty()) {
				model.addAttribute("message", "No se puede borrar un vehículo con una cita asociada");
				model.addAttribute("messageType", "danger");
				vista = listadoVehiculos(model);
			} else {
				model.addAttribute("vehiculos", v);
				vista = FORM_CONFIRM_DELETE;
			}
		}else {
			model.addAttribute("message", "Vehiculo no encontrado");
			vista = listadoVehiculos(model);
		}
		return vista;
	}
	
	@GetMapping(value = "/deleteVehiculo/{vehiculoId}")
	public String processDeleteVehiculo(@PathVariable("vehiculoId") int id, ModelMap model) {
		String vista;
		Vehiculo v = vehiculoService.findVehiculoById(id).get();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String propietario = v.getCliente().getUser().getUsername();
		if(username.equals(propietario)) {
			try {
				vehiculoService.delete(v);
				model.addAttribute("message", "Vehiculo borrado correctamente.");
			}catch(Exception e) {
				model.addAttribute("message", "Error inesperado al borrar el vehículo.");
				model.addAttribute("messageType", "danger");
			}
			vista=listadoVehiculos(model);
		}else {
			model.addAttribute("message", "Vehiculo no encontrado");
			vista = listadoVehiculos(model);
		}

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
