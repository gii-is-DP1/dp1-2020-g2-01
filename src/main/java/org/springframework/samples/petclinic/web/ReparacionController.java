package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Empleado;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.EmpleadoService;
import org.springframework.samples.petclinic.service.LineaFacturaService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.samples.petclinic.service.exceptions.CitaSinPresentarseException;
import org.springframework.samples.petclinic.service.exceptions.EmpleadoYCitaDistintoTallerException;
import org.springframework.samples.petclinic.service.exceptions.FechasReparacionException;
import org.springframework.samples.petclinic.service.exceptions.Max3ReparacionesSimultaneasPorEmpleadoException;
import org.springframework.samples.petclinic.service.exceptions.NotAllowedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reparaciones")
public class ReparacionController {

		
	private static final String FORMULARIO_REPARACION_FINALIZADA = "reparaciones/finalizar_confirmacion";
	private static final String FORMULARIO_REPARACION_RECOGIDA = "reparaciones/recoger_confirmacion";

//	@InitBinder("reparacion")
//	public void initReparacionBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new ReparacionValidator());
//	}
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}


	@Autowired
	private ReparacionService reparacionService;
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private RecambioService recambiosService;
	
	@Autowired
	private LineaFacturaService lineaFacturaService;
	
	@ModelAttribute("empleados")
	public List<Empleado> empleados() {
		return (List<Empleado>) this.empleadoService.findAll();
	}
	
	
	@ModelAttribute("citas")
	public List<Cita> citas() {
		return this.citaService.findCitaSinReparacion();
	}
	
	@ModelAttribute("reparaciones")
	public List<Reparacion> reparaciones() {
		return (List<Reparacion>) this.reparacionService.findAll();
	}
	
	@ModelAttribute("recambios")
	public List<Recambio> recambios() {
		return (List<Recambio>) this.recambiosService.findAll();
	}
	
	@GetMapping(value = "/new/{citaId}")
	public String crearReparacion(@PathVariable("citaId") int id, ModelMap model) {
		String vista = "reparaciones/editReparacion";
		try {
			Cita c = citaService.findCitaById(id);
			List<Cita> citas = citaService.findCitaSinReparacion();
			if(!citas.contains(c)) {
				model.addAttribute("message", "Esta cita ya tiene una reparación asociada");
				model.addAttribute("messageType", "warning");
				vista = listadoReparaciones(model);
			}else {
				Reparacion reparacion = new Reparacion();
				reparacion.setCita(c);
				model.addAttribute("reparacion", reparacion);
			}
		}catch(NotFoundException e) {
			log.warn("Excepción: cita no encontrada");
			model.addAttribute("message", "Cita no encontrada");
			model.addAttribute("messageType", "warning");
			vista = listadoReparaciones(model);
		}

		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarReparacion(@Valid Reparacion reparacion, BindingResult result, ModelMap model) throws DataAccessException, EmpleadoYCitaDistintoTallerException, NotAllowedException, CitaSinPresentarseException {
		String vista;

		if(result.hasErrors()) { 
//			if(horas==null) {
//				result.rejectValue("empleados", "Se debe escoger al menos un empleado", "Se debe escoger al menos un empleado");
//			}
			model.addAttribute("reparacion", reparacion);
			vista = "reparaciones/editReparacion";
		
		} else {
			try {
				
				if(reparacion.getId() != null) {
					vista = "redirect:/reparaciones/getReparacion/" + reparacion.getId().toString();
					reparacion.setHorasTrabajadas(reparacionService.findReparacionById(reparacion.getId()).get().getHorasTrabajadas());
					reparacionService.saveReparacion(reparacion);
				}else {
					reparacionService.saveReparacion(reparacion);
					Cita c = reparacion.getCita();
					c.setTieneReparacion(true);
					vista = verReparacion(reparacion.getId(), model);
				}
//				reparacionService.setEmpleadosAReparacion(horas, reparacion);
				
			
			} catch (FechasReparacionException e) {
				log.warn("Excepción: fechas incongruentes; fecha de entrega: " +reparacion.getFechaEntrega().toString(), 
						"; fecha de finalización: " + reparacion.getFechaFinalizacion().toString(),
						"; fecha de recogida: " + reparacion.getFechaRecogida().toString());
				result.rejectValue("fechaEntrega", "Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida.", 
						"Fechas incongruentes: la fecha de entrega debe ser anterior a la fecha de finalización y de recogida, y la fecha de finalización debe ser anterior a la de recogida.");
				return "reparaciones/editReparacion";
			} catch (Max3ReparacionesSimultaneasPorEmpleadoException e) {
				log.warn("Excepción: uno o más de los empleados asignados ya tiene 3 reparaciones simultáneas ");
				result.rejectValue("empleados", "Los empleados no pueden trabajar en más de 3 reparaciones simultáneas.", 
						"Los empleados no pueden trabajar en más de 3 reparaciones simultáneas.");
				return "reparaciones/editReparacion";
			}
			
			model.addAttribute("message", "Reparacion created successfully");
			
		}
		
		return vista;
	}
	
	@GetMapping(value = "/delete/{reparacionId}")
	public String borrarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista;
		Optional<Reparacion> op = reparacionService.findReparacionById(id);
		if(op.isPresent()) {
			reparacionService.delete(op.get());
			model.addAttribute("message", "Reparacion deleted successfully");
		} else {
			model.addAttribute("message", "Reparacion not found");
			
		}
		vista = listadoReparaciones(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{reparacionId}")
	public String editarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista = "reparaciones/editReparacion";
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.addAttribute("message", "Reparacion not found");
			vista = listadoReparaciones(model);
		} else {
			model.addAttribute("reparacion", reparacion.get());
		}
		return vista;
	}
	
	
	
	@GetMapping(value = { "/listadoReparaciones" })
	public String listadoReparaciones(ModelMap model) {
		String vista = "reparaciones/listadoReparaciones";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteService.findClientesByUsername(username);
		List<Reparacion> reparaciones = null;
		if(cliente.isPresent()) {
			reparaciones = reparacionService.findReparacionesCliente(cliente.get());
			model.put("reparaciones", reparaciones);
		}else { //como se obliga a loguearse, si no es cliente debe ser admin, luego poder ver todas las reparaciones
			reparaciones = (List<Reparacion>) reparacionService.findAll();
			model.put("reparaciones", reparaciones);
		}
		model.put("reparaciones", reparaciones);
		return vista;
	}
	
	@GetMapping(value="/recoger/{reparacionId}")
	public String initRecogidaReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		Reparacion reparacion = reparacionService.findReparacionById(id).get();
		model.addAttribute("reparacion", reparacion);
		return FORMULARIO_REPARACION_RECOGIDA;
	}
	
	@GetMapping(value="/recogerReparacion/{reparacionId}")
	public String processRecogidaReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista = "";
		Reparacion rep = this.reparacionService.findReparacionById(id).get();
		try {
			reparacionService.recoger(rep);
			model.addAttribute("message", "Reparación "+rep.getId()+" recogida correctamente");

		}catch(Exception e){
			log.warn("Excepción: error inesperado al recoger la reparación");
			model.addAttribute("message", "Error inesperado al recoger la reparación "+rep.getId() + " | " + e.getMessage());
			model.addAttribute("messageType", "danger");
		}
		vista=listadoReparaciones(model);
		return vista;
	}
	
	
	@GetMapping(value="/finalizar/{reparacionId}")
	public String initFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		Reparacion reparacion = reparacionService.findReparacionById(id).get();
		model.addAttribute("reparacion", reparacion);
		return FORMULARIO_REPARACION_FINALIZADA;
	}
	
	@GetMapping(value="/finalizarReparacion/{reparacionId}")
	public String processFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		String vista = "";
		Reparacion rep = this.reparacionService.findReparacionById(id).get();
		try {
			reparacionService.finalizar(rep);
			model.addAttribute("message", "Reparación "+rep.getId()+" finalizada correctamente");

		}catch(Exception e){
			log.warn("Excepción: error inesperado al finalizar la reparación"+ e.getMessage());
			model.addAttribute("message", "Error inesperado al finalizar la reparación "+rep.getId());
			model.addAttribute("messageType", "danger");
		}
		
		vista=listadoReparaciones(model);
	
		return vista;
	}
	
	@GetMapping(value="/getReparacion/{idReparacion}")
	public String verReparacion(@PathVariable("idReparacion") int id, ModelMap model) {
		String vista = "";
		Optional<Reparacion> rep = reparacionService.findReparacionById(id);
		if(rep.isPresent()) {
			model.put("reparacion", rep.get());
			model.addAttribute("empleados", rep.get().getHorasTrabajadas().stream()
					.map(x->x.getEmpleado()).distinct().collect(Collectors.toList()));
			vista = "reparaciones/datosReparacion";
		}else {
			model.put("message", "Reparación no encontrada");
			model.put("messageType", "warning");
			vista = listadoReparaciones(model);
		}
		return vista;
	}
	
	@GetMapping("/addRecambio/{reparacionId}")
	public String mostrarRecambios(@PathVariable("reparacionId") int id, ModelMap model, String busqueda) {
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.put("message", "Reparación no encontrada");
			model.put("messageType", "warning");
			return listadoReparaciones(model);
		}
		busqueda = busqueda == null ? "" : busqueda;
		List<Recambio> recambios = recambiosService.findRecambioNotInReparacion(reparacion.get(), busqueda);
		List<Recambio> recambiosDeReparacion = reparacion.get().getLineaFactura().stream()
				.map(x->x.getRecambio()).collect(Collectors.toList());
		
		model.addAttribute("recambios", recambios);
		model.addAttribute("recambiosDeReparacion", recambiosDeReparacion);
		model.addAttribute("reparacion", reparacion.get());
		return "reparaciones/addRecambio";
	}
	
	@GetMapping("/addRecambio/{reparacionId}/buscar")
	public String buscarRecambio(@PathVariable("reparacionId") int id, @RequestParam("busqueda") String busqueda, ModelMap model) {
		return mostrarRecambios(id, model, busqueda);
	}
	
	@GetMapping("/addRecambio/{reparacionId}/{recambioId}")
	public String addRecambio(@PathVariable("reparacionId") int repId, @PathVariable("recambioId") int recId, ModelMap model) {
		String vista = "redirect:/reparaciones/addRecambio/" + String.valueOf(repId);
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(repId);
		Optional<Recambio> recambio = recambiosService.findRecambioById(recId);
		
		if(!reparacion.isPresent()) {
			model.put("message", "Reparación no encontrada");
			model.put("messageType", "warning");
			return listadoReparaciones(model);
		}
		
		if(!recambio.isPresent()) {
			model.put("message", "Recambio no encontrado");
			model.put("messageType", "warning");
			return mostrarRecambios(repId, model, "");
		}
		
		LineaFactura ln = new LineaFactura();
		ln.setCantidad(0);
		ln.setDescripcion("Añade aqui la descripción");
		ln.setDescuento(0);
		ln.setPrecioBase(0.);
		ln.setRecambio(recambio.get());
		ln.setReparacion(reparacion.get());
		
		try {
			lineaFacturaService.saveLineaFactura(ln);
			reparacion.get().getLineaFactura().add(ln);
			reparacionService.saveReparacion(reparacion.get());
		}catch(Exception e) {
			model.put("message", "Fallo al añadir el recambio");
			model.put("messageType", "danger");
			return mostrarRecambios(repId, model, "");
		}
		
		return vista;
	}
	
	@PostMapping("/addRecambio/{reparacionId}/{lnId}")
	public String actualizarLineaFactura(@PathVariable("reparacionId") int id, @PathVariable("lnId") int lnId, @Valid LineaFactura ln, ModelMap model
			, BindingResult result) {
		String vista = "redirect:/reparaciones/addRecambio/" + String.valueOf(id);
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.put("message", "Reparación no encontrada");
			model.put("messageType", "warning");
			return listadoReparaciones(model);
		}
		
		if(result.hasErrors()) {
			model.put("message", "Introduce datos válidos");
			model.put("messageType", "warning");
			return mostrarRecambios(id, model, "");
		}

		Integer cantidadAnterior = lineaFacturaService.findLineaFacturaById(lnId).get().getCantidad();
		if(ln.getCantidad() > ln.getRecambio().getCantidadActual() + cantidadAnterior) {
			ln.setCantidad(ln.getRecambio().getCantidadActual() + cantidadAnterior);
		}
		
		try {
			ln.setId(lnId);
			lineaFacturaService.saveLineaFactura(ln);
			Recambio r = ln.getRecambio();
			r.setCantidadActual(r.getCantidadActual()-ln.getCantidad()+cantidadAnterior);
			recambiosService.saveRecambio(r);
		}catch(Exception e) {
			model.put("message", "Fallo al guardar los datos");
			model.put("messageType", "danger");
			return mostrarRecambios(id, model, "");
		}
		
		return vista;
	}
	
	@GetMapping("/editRecambio/{repId}/{lnId}")
	public String editRecambio(@PathVariable("repId") int id, @PathVariable("lnId") int lnId, ModelMap model) {
		model.addAttribute("edit", lnId);
		return mostrarRecambios(id, model, "");
	}

	@GetMapping("/deleteRecambio/{repId}/{lnId}")
	public String deleteRecambio(@PathVariable("repId") int id, @PathVariable("lnId") int lnId, ModelMap model) {
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		Optional<LineaFactura> ln = lineaFacturaService.findLineaFacturaById(lnId);
		
		if(!reparacion.isPresent()) {
			model.put("message", "Reparación no encontrada");
			model.put("messageType", "warning");
			return listadoReparaciones(model);
		}
		
		if(!ln.isPresent()) {
			model.put("message", "Linea de factura no encontrada");
			model.put("messageType", "warning");
			return mostrarRecambios(id, model, "");
		}
		
		try {
			lineaFacturaService.delete(ln.get());

			Recambio r = ln.get().getRecambio();
			r.setCantidadActual(r.getCantidadActual()+ln.get().getCantidad());
			recambiosService.saveRecambio(r);
		}catch(Exception e) {
			model.put("message", "Fallo al eliminar la linea de factura");
			model.put("messageType", "danger");
			return mostrarRecambios(id, model, "");
		}
		
		return "redirect:/reparaciones/addRecambio/" + String.valueOf(id);
	}
}
