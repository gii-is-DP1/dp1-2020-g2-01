package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.FacturaRecambio;
import org.springframework.samples.petclinic.model.PedidoRecambio;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.model.Recambio;
import org.springframework.samples.petclinic.model.Solicitud;
import org.springframework.samples.petclinic.service.FacturaRecambioService;
import org.springframework.samples.petclinic.service.FacturaService;
import org.springframework.samples.petclinic.service.PedidoRecambioService;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pedidosRecambio")
public class PedidoRecambioController {


	@Autowired
	private PedidoRecambioService pedidoRecambioService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private RecambioService recambioService;
	
	@Autowired
	private FacturaRecambioService facturaRecambioService;
	
	@ModelAttribute("proveedores")
	public List<Proveedor> listaProveedores(){
		return (List<Proveedor>) proveedorService.findAll();
	}
	
	@ModelAttribute("recambios")
	public List<Recambio> listaRecambios(){
		return (List<Recambio>) recambioService.findAll();
	}
	
	@GetMapping(value = { "/listadoPedidosRecambio" })
	public String listadoPedidoRecambio(ModelMap model) {
		String vista = "pedidosRecambio/listadoPedidosRecambio";
		Iterable<PedidoRecambio> pedidoRecambio = pedidoRecambioService.findAll();
		model.put("pedidoRecambio", pedidoRecambio);
		return vista;
	}
	
	
	@GetMapping(value = "/new")
	public String crearPedidoRecambio(ModelMap model) {
		String vista = "pedidosRecambio/newPedidosRecambio";
		model.addAttribute("pedidosRecambio", new PedidoRecambio());
		return vista;
	}
	
	@PostMapping(value = "/save")
	public String guardarPedidosRecambio(@Valid PedidoRecambio pedidoRecambio, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("pedidosRecambio", pedidoRecambio);
			vista = "pedidosRecambio/newPedidosRecambio";
		} else {
			pedidoRecambioService.savePedidoRecambio(pedidoRecambio);
			LocalDate fecha = LocalDate.now();
//			Double precio = pedidoRecambio.getPrecio();
			FacturaRecambio facturaRecambio = new FacturaRecambio();
			facturaRecambio.setFechaPago(fecha);
			facturaRecambio.setPedidoRecambio(pedidoRecambio);
//			facturaRecambio.setPrecioTotal(precio);
			facturaRecambioService.saveFacturaRecambio(facturaRecambio);
			vista = "redirect:/pedidosRecambio/listadoPedidosRecambio";
		}
		return vista;
	}
	
	@GetMapping("/delete/{id}")
	public String borrarPedidosRecambio(@PathVariable("id") int id, ModelMap model) {
		String vista;
		Optional<PedidoRecambio> s = pedidoRecambioService.findById(id);
		if(!s.isPresent()) {
			model.addAttribute("message", "No existe la solicitud dada");
		} else {
			
			pedidoRecambioService.delete(s.get());
			model.addAttribute("message", "Pedido borrado con éxito");
		}
		vista = listadoPedidoRecambio(model);
		return vista;
		
	}
	
	

	@GetMapping(value = {"/factura/{id}"})
	public String verFacturaRecambio(@PathVariable("id") int id, ModelMap model) {
		String vista;
		Optional<FacturaRecambio> factura = facturaRecambioService.findFacturaRecambioById(id);
		if (!factura.isPresent()) {
			model.addAttribute("message", "No existe una factura con el identificador dado");
			vista = listadoPedidoRecambio(model);
		} else {
			model.addAttribute("factura", factura.get());
			vista = "facturasRecambio/details";
		}
		return vista;
	}


}