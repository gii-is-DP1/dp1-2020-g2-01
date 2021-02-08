package org.springframework.samples.petclinic.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Factura;
import org.springframework.samples.petclinic.model.LineaFactura;
import org.springframework.samples.petclinic.model.Reparacion;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.LineaFacturaService;
import org.springframework.samples.petclinic.service.ReparacionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/facturas")
public class FacturaController {


	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private ReparacionService reparacionService;
	
	@Autowired
	private LineaFacturaService lineaFacturaService;
	
	@Autowired
	private ReparacionController reparacionController;
	
	@Autowired
	private ClienteRepository clienteRepository;


	@GetMapping(value = { "/listadoFacturas" })
	public String listadoFacturas(ModelMap model) {
		String vista = "facturas/listadoFacturas";
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Cliente> cliente = clienteRepository.findByUsername(username);
		Iterable<Factura> facturas = null;
		if(cliente.isPresent()) {
			facturas = facturaService.findFacturaByCliente(cliente.get());
		}else {
			facturas = facturaService.findAll();
		}
		
		model.put("facturas", facturas);
		return vista;
	}
	
	@PostMapping(value="/generar/{reparacionId}")
	public String processFinalizarReparacion(@PathVariable("reparacionId") int id, @Valid Factura factura, ModelMap model) {

		try {
			facturaService.saveFactura(factura);

			for(LineaFactura l: factura.getLineaFactura()) {
				l.setFactura(factura);
				lineaFacturaService.saveLineaFactura(l);
			}
			model.addAttribute("message", "Factura "+factura.getId()+" generada correctamente");

		}catch(Exception e){
			log.warn("Excepción: error inesperado al finalizar la reparacion con id " + id );
			model.addAttribute("message", "Error inesperado");
			model.addAttribute("messageType", "danger");
			return reparacionController.verReparacion(id, model);
		}
		

		return "redirect:/reparaciones/getReparacion/" + reparacionService.findReparacionById(id).get().getId().toString();
	}
	
	@GetMapping(value="/generar/{reparacionId}")
	public String initFinalizarReparacion(@PathVariable("reparacionId") int id, ModelMap model) {
		
		Optional<Reparacion> reparacion = reparacionService.findReparacionById(id);
		if(!reparacion.isPresent()) {
			model.addAttribute("message", "Reparacion not found");
			return reparacionController.listadoReparaciones(model);
		}
		
		List<LineaFactura> lineaFactura = reparacion.get().getLineaFactura().stream().collect(Collectors.toList());
		Factura factura = new Factura();
		factura.setLineaFactura(lineaFactura);
		try {
			facturaService.saveFactura(factura);
			for(LineaFactura l: factura.getLineaFactura()) {
				l.setFactura(factura);
				lineaFacturaService.saveLineaFactura(l);
			}
		}catch(Exception e){
			log.warn("Excepción: error inesperado al generar la factura: " + e.getMessage());
			model.addAttribute("message", "Error inesperado: " + e.getMessage());
			model.addAttribute("messageType", "danger");
			return reparacionController.verReparacion(id, model);
		}
		return "redirect:/reparaciones/getReparacion/" + String.valueOf(id);
	}
	
	@GetMapping("/marcarPagado/{facturaId}")
	public String marcarComoPagado(@PathVariable("facturaId") int id, ModelMap model) {
		Factura f = facturaService.findFacturaById(id).get();
		f.setFechaPago(LocalDate.now());
		facturaService.saveFactura(f);
		return "redirect:/reparaciones/getReparacion/" + f.getLineaFactura().get(0).getReparacion().getId().toString();
	}

	@GetMapping(value="/info/{facturaId}")
	public String getInfo(@PathVariable("facturaId") int id, ModelMap model) {
		String vista = "facturas/details";
		Optional<Factura> factura = facturaService.findFacturaById(id);
		
		if(!factura.isPresent()) {
			model.addAttribute("message", "Factura no encontrada");
			model.addAttribute("messageType", "warning");
			return listadoFacturas(model);
		}
		
		model.addAttribute("factura", factura.get());
		
		return vista;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/generarPDF/{facturaId}")
	public ResponseEntity generarPDF(@PathVariable("facturaId") int id, ModelMap model) throws FileNotFoundException, IOException {
		Factura factura = facturaService.findFacturaById(id).get();
		
		String archivo = facturaService.generarPDF(factura);
		Path path = Paths.get(archivo);
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ResponseEntity res =  ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
//		new File(archivo).delete();
		return res;
	}
	
	


}