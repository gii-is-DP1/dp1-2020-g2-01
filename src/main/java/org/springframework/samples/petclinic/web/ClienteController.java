package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	private static final String FORMULARIO_ADD_UPDATE_CLIENTES = "clientes/createOrUpdateClienteForm";
	
	private final ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService, UserService userService, AuthoritiesService authoritiesService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping(value = { "/listadoClientes" })
	public String listadoClientes(ModelMap model) {
		String vista = "clientes/listadoClientes";
		Iterable<Cliente> clientes = clienteService.findAll();
		model.put("clientes", clientes);
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String anadirCliente(ModelMap model) {
		model.addAttribute("cliente", new Cliente());
		return FORMULARIO_ADD_UPDATE_CLIENTES;
	}
	
	@PostMapping(value = "/save")
	public String guardarCliente(@Valid Cliente cliente, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("cliente", cliente);
			vista = FORMULARIO_ADD_UPDATE_CLIENTES;
		} else {
			clienteService.saveCliente(cliente);
			model.addAttribute("message", "Vehiculo created successfully");
			vista = listadoClientes(model);
		}
		
		return vista;
	}
	
	@GetMapping(value = "/delete/{clienteId}")
	public String borrarCliente(@PathVariable("clienteId") int id, ModelMap model) {
		String vista;
		Optional<Cliente> op = clienteService.findClienteById(id);
		if(op.isPresent()) {
			clienteService.delete(op.get());
			model.addAttribute("message", "Cliente deleted successfully");
		} else {
			model.addAttribute("message", "Cliente not found");
			
		}
		vista = listadoClientes(model);
		return vista;
	}
	
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
//	
//	@GetMapping(value = "/new")
//	public String initCreationForm(Map<String, Object> model) {
//		Cliente cliente = new Cliente();
//		model.put("cliente", cliente);
//		return FORMULARIO_ADD_UPDATE_CLIENTES;
//	}
//
//	@PostMapping(value = "/new")
//	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
//		if (result.hasErrors()) {
//			return FORMULARIO_ADD_UPDATE_CLIENTES;
//		}
//		else {
//			//creating owner, user and authorities
//			this.clienteService.saveCliente(cliente);
//			
//			return "redirect:/clientes/" + cliente.getId();
//		}
//	}
//	
//	@GetMapping(value = "/clientes/find")
//	public String initFindForm(Map<String, Object> model) {
//		model.put("cliente", new Cliente());
//		return "clientes/findClientes";
//	}
	
	
	//FALTA POR CONSTRUIR LA FUNCION DE LA LINEA 86
//	@GetMapping(value = "/owners")
//	public String processFindForm(Cliente cliente, BindingResult result, Map<String, Object> model) {
//
//		// allow parameterless GET request for /clientes to return all records
//		if (cliente.getApellidos() == null) {
//			cliente.setApellidos(""); // empty string signifies broadest possible search
//		}
//
//		// find clientes by apellidos
//		Collection<Cliente> results = this.clienteService.findClienteByApellidos(cliente.getApellidos());
//		if (results.isEmpty()) {
//			// no clientes found
//			result.rejectValue("apellidos", "notFound", "not found");
//			return "clientes/findClientes";
//		}
//		else if (results.size() == 1) {
//			// 1 cliente found
//			cliente = results.iterator().next();
//			return "redirect:/clientes/" + cliente.getId();
//		}
//		else {
//			// multiple clientes found
//			model.put("selections", results);
//			return "clientes/clientesList";
//		}
//	}
	
//	@GetMapping("/clientes/{clienteId}")
//	public ModelAndView showCliente(@PathVariable("clienteId") int clienteId) {
//		ModelAndView mav = new ModelAndView("clientes/clienteDetails");
//		mav.addObject(this.clienteService.findClienteById(clienteId));
//		return mav;
//	}
//	
	
}
