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
		Iterable<Vehiculo> vehiculos = vehiculoService.findAll();
		model.put("vehiculos", vehiculos);
		return vista;
		
	}
	
	@GetMapping(value = { "/listadoVehiculos/{username}" })
	public String listadoVehiculosCliente(@PathVariable(value="username") String username, ModelMap model) {
		String vista = "vehiculos/listadoVehiculos";
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			List<Vehiculo> vehiculos = vehiculoService.findVehiculosCliente(cliente.get());
			model.put("vehiculos", vehiculos);
			return vista;
		}else {
			return "";
		}
		
		
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
	
	
	
	@GetMapping(value = {"/listadoVehiculos/{clienteId}"})
	public String listadoVehiculosParaCliente(@PathVariable("clienteId") int id, ModelMap model) {
		String vista = "vehiculos/listadoVehiculosCliente";
		Iterable<Vehiculo> vehiculos = vehiculoService.findByClienteId(id);
		model.put("clienteId", id);
		model.put("vehiculos", vehiculos);
		return vista;
		
	}
	
	
	
	
	@GetMapping(value = "/new/{clienteId}")
	public String crearVehiculoParaCliente(ModelMap model, @PathVariable("clienteId") int id) {
		String vista = "vehiculos/editVehiculoCliente";
		model.addAttribute("vehiculo", new Vehiculo());
		model.addAttribute("clienteId", id);
		return vista;
	}
	
	
	
	@PostMapping(value = "/save/{clienteId}")
	public String guardarVehiculoParaCliente(@Valid Vehiculo vehiculo, BindingResult result, ModelMap model, @PathVariable("clienteId") int id) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("vehiculo", vehiculo);
			vista = "vehiculos/editVehiculoCliente";
		} else {
			vehiculo.setCliente(clienteService.findClienteById(id).get());
			vehiculoService.saveVehiculo(vehiculo);
			model.addAttribute("message", "Vehiculo created successfully");
			vista = listadoVehiculosParaCliente(id, model);
		}
		
		return vista;
	}
	
	@GetMapping(value = "/update/{clienteId}/{vehiculoId}")
	public String editarVehiculoParaCliente(@PathVariable("clienteId") int clienteId, @PathVariable("vehiculoId") int vehiculoId, ModelMap model) {
		String vista = "vehiculos/editVehiculoCliente";
		Optional<Vehiculo> vehiculo = vehiculoService.findVehiculoById(vehiculoId);
		if(!vehiculo.isPresent()) {
			model.addAttribute("message", "Vehiculo not found");
			vista = listadoVehiculosParaCliente(clienteId, model);
		} else {
			model.addAttribute("clienteId", clienteId);
			model.addAttribute("vehiculo", vehiculo.get());
		}
		return vista;
	}
	
	
	@GetMapping(value = "/delete/{clienteId}/{vehiculoId}")
	public String borrarVehiculoParaCliente(@PathVariable("clienteId") int clienteId, @PathVariable("vehiculoId") int vehiculoId, ModelMap model) {
		String vista;
		Optional<Vehiculo> op = vehiculoService.findVehiculoById(vehiculoId);
		if(op.isPresent()) {
			vehiculoService.delete(op.get());
			model.addAttribute("message", "Vehiculo deleted successfully");
		} else {
			model.addAttribute("message", "Vehiculo not found");
		}
		vista = listadoVehiculosParaCliente(clienteId, model);
		return vista;
	}
	
	
	
	
	
}
