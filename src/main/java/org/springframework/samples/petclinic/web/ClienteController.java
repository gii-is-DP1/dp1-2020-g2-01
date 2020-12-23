package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	private static final String FORMULARIO_ADD_UPDATE_CLIENTES = "clientes/createOrUpdateClienteForm";
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UserService userService;
	

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
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/new")
	public String anadirCliente(ModelMap model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return FORMULARIO_ADD_UPDATE_CLIENTES;
	}

	@PostMapping(value = "/new")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return FORMULARIO_ADD_UPDATE_CLIENTES;
		}
		else {
			this.clienteService.saveCliente(cliente);
			
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("cliente", new Cliente());
		return "clientes/findClientes";
	}
	
	@PostMapping(value="")
	public String processFindForm(Cliente cliente, BindingResult result, Map<String, Object> model) {

		if(result.hasErrors()) {
			return "/clientes/listadoClientes";
		}
		
		// allow parameterless GET request for /clientes to return all records
		if (cliente.getApellidos() == null||cliente.getApellidos()=="") {
			model.put("clientes", clienteService.findAll());
			return "clientes/listadoClientes";
		}

		// find clientes by apellidos
		Collection<Cliente> results = this.clienteService.findClientesByApellidos(cliente.getApellidos());
		if (results.isEmpty()) {
			// no clientes found
//			result.rejectValue("apellidos", "notFound", "not found");
			return "clientes/findClientes";
		}
		
		else {
			// multiple clientes found
			model.put("clientes", results);
			return "clientes/listadoClientes";
		}
	}
	
	@GetMapping(value = "/update/{username}")
	public String initUpdateClienteForm(@PathVariable("username") String username, Model model) {
		Cliente cliente = this.clienteService.findClientesByUsername(username).get();
		model.addAttribute("cliente", cliente);
		return FORMULARIO_ADD_UPDATE_CLIENTES;
	}

	@PostMapping(value = "/update/{username}")
	public String processUpdateClienteForm(@Valid Cliente cliente, BindingResult result,
			@PathVariable("username") String username) {
		if (result.hasErrors()) {
			return FORMULARIO_ADD_UPDATE_CLIENTES;
		}
		else {
			Cliente cliente1 = this.clienteService.findClientesByUsername(username).get();
			// Si se modifica el nombre de usuario en el form, no se valida y sigue, de momento he puesto la siguiente linea
			cliente.getUser().setUsername(username);
			cliente.setId(cliente1.getId());
			this.clienteService.saveCliente(cliente);
			
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "/delete/{clienteId}")
	public String deleteCliente(@PathVariable("clienteId") int id, ModelMap model) {
		Cliente c = clienteService.findClienteById(id).get();
		clienteService.delete(c);
		model.addAttribute("message", "El cliente se ha borrado con éxito");
		return "clientes/listadoClientes";
	}

	
}
