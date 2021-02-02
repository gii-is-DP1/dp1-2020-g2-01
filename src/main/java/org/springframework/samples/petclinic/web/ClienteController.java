package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private static final String FORMULARIO_CONFIRM_DELETE = "clientes/confirmar_delete";
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private CitaService citaService;

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
	
	@GetMapping(value = "/clienteDetails/{username}")
	public String mostrasDetalles(@PathVariable("username") String username, Model model) {
		Optional<Cliente> cliente = this.clienteService.findClientesByUsername(username);
		if(!cliente.isPresent()) {
			model.addAttribute("cliente", new Cliente());
			model.addAttribute("message", "Cliente no encontrado");
			model.addAttribute("messageType", "danger");
			return "clientes/clienteDetails";
		}
		String username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString();
		if(username.equals(username2) || auth.equals("admin")) {
			model.addAttribute("cliente", cliente.get());
			model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cliente.get()));
			model.addAttribute("sinNombre", "sinNombre");
			
			List<Cita> citas = citaService.findByCliente(cliente.get());
			Comparator<Cita> ordenarPorFechaYHora = Comparator.comparing(Cita::getFecha)
					.thenComparing(Comparator.comparing(Cita::getHora));
			citas = citas.stream().sorted(ordenarPorFechaYHora).collect(Collectors.toList());
			if(!citas.isEmpty()) {
				model.addAttribute("cita", citas.get(0));
			}
			return "clientes/clienteDetails";
		}else {
			return "redirect:/";
		}
	}
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/new")
	public String initNuevoCliente(ModelMap model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return FORMULARIO_ADD_UPDATE_CLIENTES;
	}

	@PostMapping(value = "/new")
	public String processNuevoCliente(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			return FORMULARIO_ADD_UPDATE_CLIENTES;
		}
		else {
			this.clienteService.saveCliente(cliente);
			
			return "redirect:/login";
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
		String username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		if(username.equals(username2)) {
			model.addAttribute("cliente", cliente);
			return FORMULARIO_ADD_UPDATE_CLIENTES;
		}else {
			return "redirect:/";
		}

		
	}

	@PostMapping(value = "/update/{username}")
	public String processUpdateClienteForm(@Valid Cliente cliente, BindingResult result,
			@PathVariable("username") String username, Model model) {
		if (result.hasErrors()) {
			return FORMULARIO_ADD_UPDATE_CLIENTES;
		}
		else {
			Cliente cliente1 = this.clienteService.findClientesByUsername(username).get();
			// Si se modifica el nombre de usuario en el form, no se valida y sigue, de momento he puesto la siguiente linea
			cliente.getUser().setUsername(username);
			cliente.setId(cliente1.getId());
			this.clienteService.saveCliente(cliente);
			
			return mostrasDetalles(username, model);
		}
	}
	
	@GetMapping(value = "/delete/{username}")
	public String initDeleteCliente(@PathVariable("username") String username, ModelMap model) {
		Cliente c = clienteService.findClientesByUsername(username).get();
		model.addAttribute("clientes", c);
		return FORMULARIO_CONFIRM_DELETE;

	}
	
	
	@GetMapping(value="/deleteCliente/{username}")
	public String processDeleteCliente(@PathVariable("username") String username, ModelMap model) {
		Cliente c = this.clienteService.findClientesByUsername(username).get();
		try {
			clienteService.delete(c);
			model.addAttribute("message", "Cliente borrado correctamente.");
			
		}catch(Exception e) {
			model.addAttribute("message", "Error inesperado al borrar al cliente");
			model.addAttribute("messageType", "danger");
		}
		return listadoClientes(model);
	}

	
}
