package org.springframework.samples.petclinic.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PedidoRecambio;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.PedidoRecambioService;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.RecambioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@ModelAttribute("proveedores")
	public List<Proveedor> listaProveedores(){
		return (List<Proveedor>) proveedorService.findAll();
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
		model.addAttribute("nombres", recambioService.findAll()); 
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
//			model.addAttribute("message", "Pedido Recambio created successfully");
//			vista = listadoPedidoRecambio(model);
			vista = "redirect:/pedidosRecambio/listadoPedidosRecambio";
		}
		return vista;
	}


}