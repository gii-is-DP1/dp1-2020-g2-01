package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.Taller;
import org.springframework.samples.petclinic.model.TipoCita;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.TallerService;
import org.springframework.samples.petclinic.service.TipoCitaService;
import org.springframework.samples.petclinic.service.VehiculoService;
import org.springframework.samples.petclinic.service.exceptions.CitaSinPresentarseException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.samples.petclinic.util.LoggedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/citas")
public class CitaController {
	

	public static final String CREATE_OR_UPDATE_FORM = "citas/editCita";
	private static final String FORMULARIO_CITA_COVID = "citas/covid_confirmation";
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private TipoCitaService tipoCitaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private TallerService tallerService;
	
	@Autowired
	protected EntityManager em;
	
	@ModelAttribute("tipos")
	public List<TipoCita> tipos(){
		return tipoCitaService.findAll();
	}
	
	@ModelAttribute("citas")
	public List<Cita> citas(){
		return citaService.findCitasFuturas();
	}
	
	@ModelAttribute("citasPasadas")
	public List<Cita> citasPasadas(){
		List<Cita> citasPasadas = citaService.findCitasPasadas();
		return citasPasadas.subList(0, Math.min(10, citasPasadas.size()));
	}
	
	@ModelAttribute("citasHoy")
	public List<Cita> citasHoy(){
		return citaService.findCitasHoy();
	}
	
	@ModelAttribute("talleres")
	public List<Taller> talleres(){
		return (List<Taller>) tallerService.findAll();
	}
	
	@GetMapping(value="")
	public String listado(ModelMap model) {
		return "redirect:/citas/listadoCitas";
	}
	
	@GetMapping(value = { "/listadoCitas"})
	public String listadoCitas(ModelMap model) {
		String vista = "citas/listadoCitas";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			model.put("citas", citaService.findByCliente(cliente.get())); 
			
		}else {
			String ubicacion = "";
			Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
			if(empleado.isPresent()) {
				ubicacion = empleado.get().getTaller().getUbicacion();
				model.put("citas", citaService.findCitaByTallerUbicacion(ubicacion)); 
			}
		}
		return vista;
	}
	
	@PostMapping(value="/save/{citaId}")
	public String saveCita(@PathVariable("citaId") Integer id, @Valid Cita cita, BindingResult result, ModelMap model) throws DataAccessException {
		String vista;
		if(result.hasErrors()) {
			model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cita.getVehiculo().getCliente()));
			if(id != 0) {
				cita.setId(id);
			}
			model.addAttribute("cita", cita);
			vista = CREATE_OR_UPDATE_FORM;
		}else {
			if(id != 0) {
				cita.setId(id);
			}

			try {
				citaService.saveCita(cita, LoggedUser.getUsername());
				model.addAttribute("message", "Cita guardada successfully");
				
			}catch(EmpleadoYCitaDistintoTallerException e) {
				log.warn("Excepción: uno o más de los empleados está en un taller distinto a la de la cita, taller de la cita: " + cita.getTaller().getName()  
			+ "talleres de los empleados: " + cita.getEmpleados().stream().map(x->x.getTaller().getName()).toArray());
				
				model.addAttribute("message", "La cita y los empleados deben estar asignados al mismo taller");
				model.addAttribute("messageType", "danger");
				
			}catch(NotAllowedException e) { // Un usuario ha intentado usar un vehiculo que no le pertenece
				String username = LoggedUser.getUsername();
				log.warn("Excepción: el usuario con username " + username + " ha intentado usar un vehículo que no le pertenece");
				Cliente c = clienteService.findClientesByUsername(username).get();
				model.addAttribute("message", "El vehículo seleccionado no se encuentra");
				model.addAttribute("messageType", "danger");
				model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(c));
			}catch(CitaSinPresentarseException e) {
				log.warn("Excepción: el usuario con username " + LoggedUser.getUsername() +"ha sobrepasado el límite de citas sin asistir");
				model.addAttribute("message", "Ha sobrepasado el límite de citas sin asistir, por lo que no puede pedir más citas hasta dentro de 1 semana");
			}
			
			vista = listadoCitas(model);
		}
		return vista;
	}
	
	@GetMapping(value = "/new")
	public String crearCita(ModelMap model) {
		String vista = "citas/editCita";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cliente.get()));
			model.addAttribute("cita", new Cita());
		}else {
			model.addAttribute("message", "Debes haber iniciado sesión como cliente");
			model.addAttribute("messageType", "warning");
			vista = listadoCitas(model);			
		}
		return vista;
	}
	
	@GetMapping(value = "/new/{username}")
	public String crearCitaParaCliente(@PathVariable("username") String username, ModelMap model) {
		String vista = "citas/editCita";
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		if(cliente.isPresent()) {
			model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cliente.get()));
			model.addAttribute("cita", new Cita());
		}else {
			model.addAttribute("message", "El cliente debe estar registrado");
			model.addAttribute("messageType", "warning");
			vista = listadoCitas(model);			
		}
		return vista;
	}
	
	@GetMapping(value="/update/{citaId}")
	public String updateCita(@PathVariable("citaId") int id, ModelMap model) {
		Cita cita = null;
		try {
			cita = citaService.findCitaById(id);
		}catch(NotFoundException e) {
			model.addAttribute("message", "Cita not found");
			model.addAttribute("messageType", "warning");
			return listadoCitas(model);
		}
		
		if(cita.getFecha().isBefore(LocalDate.now())) {
			model.addAttribute("message", "No puedes modificar una cita que ya ha pasado");
			model.addAttribute("messageType", "warning");
			return listadoCitas(model);
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		
		if(cliente.isPresent()) {
			if(!cliente.get().equals(cita.getVehiculo().getCliente())) {
				model.addAttribute("message", "No puedes modificar una cita que no es tuya");
				model.addAttribute("messageType", "warning");
				return listadoCitas(model);
			}
		}
		
		model.addAttribute("vehiculos", vehiculoService.findVehiculosCliente(cita.getVehiculo().getCliente()));
		model.addAttribute("cita", cita);
		return "citas/editCita";
	}
  
	@GetMapping(value="/delete/{citaId}")
	public String deleteCita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = listadoCitas(model);
		Cita cita = null;
		try {
			cita = citaService.findCitaById(id);
		}catch(NotFoundException e) {
			model.addAttribute("message", "Cita not found");
			return vista;
		}
		
		if(cita.getFecha().isBefore(LocalDate.now())) {
			model.addAttribute("message", "No puedes modificar una cita que ya ha pasado");
			model.addAttribute("messageType", "warning");
			return vista;
		}
		
		citaService.delete(cita);
		model.addAttribute("message", "Cita borrado con éxito.");
		vista = listadoCitas(model);		
		
		return vista;
	}
	
	@GetMapping(value="/covid")
	public String initDeleteCitasCOVID(ModelMap model) {
		return FORMULARIO_CITA_COVID;
	}
	
	@GetMapping(value="/eliminarCitasCovid")
	public String processDeleteCitasCovid(ModelMap model) {
		String vista = "";
		try {
			citaService.deleteCOVID();
			model.addAttribute("message", "Citas canceladas correctamente");

		}catch(Exception e){
			model.addAttribute("message", "Error inesperado al cancelar las citas");
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoCitas(model);
	
		return vista;
	}
	

	@GetMapping(value="/atender/{citaId}")
	public String addEmpleadoACita(@PathVariable("citaId") int id, ModelMap model) {
		String vista = listadoCitas(model);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
		
		if(!empleado.isPresent()) {
			model.put("message", "No has iniciado sesión como empleado");
			model.put("messageType", "warning");
			return vista;
		}
		
		Cita c = null;
		try {
			c = citaService.findCitaById(id);
		}catch(NotFoundException e) {
			model.addAttribute("message", "Cita not found");
			return vista;
		}
		
		if(c.getEmpleados().contains(empleado.get())) {
			model.put("message", "Ya estás atendiendo a la cita");	
			model.put("messageType", "warning");
			return vista;
		}
		
		c.getEmpleados().add(empleado.get());
		try {
			citaService.saveCita(c, LoggedUser.getUsername());
		} catch (EmpleadoYCitaDistintoTallerException e) {
			model.put("message", "No puedes atender una cita de otro taller diferente al que trabajas");
			model.addAttribute("messageType", "danger");
			return vista;
		}catch(Exception e) {
			
		}
		
		model.put("message", "Te has unido correctamente");
		return vista;
	}
	
	@GetMapping(value="/noAtender/{citaId}")
	public String eliminarEmpleadoDeCita(@PathVariable("citaId") int id, ModelMap model) throws DataAccessException, EmpleadoYCitaDistintoTallerException, 
	NotAllowedException, CitaSinPresentarseException {
		String vista = listadoCitas(model);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Empleado> empleado = empleadoService.findEmpleadoByUsuarioUsername(username);
		if(!empleado.isPresent()) {
			model.put("message", "Empleado no encontrado");
			model.put("messageType", "warning");
			return vista;
		}
		
		Cita c = null;
		try {
			c = citaService.findCitaById(id);
		}catch(NotFoundException e) {
			model.addAttribute("message", "Cita not found");
			return vista;
		}
		
		if(!c.getEmpleados().contains(empleado.get())) {
			model.put("message", "No estabas atendiendo la cita");	
			model.put("messageType", "warning");		
			return vista;
		}
		
		c.getEmpleados().remove(empleado.get());
		citaService.saveCita(c, LoggedUser.getUsername());
		model.put("message", "Te has quitado correctamente");
		return vista;
	}
	
	@GetMapping(value="/confirmDelete/{citaId}")
	public String confirmDelete(@PathVariable("citaId") int id, ModelMap model) {
		model.addAttribute("id", id);
		return "citas/confirmar_borrado_cita";
	}
}
