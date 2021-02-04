package org.springframework.samples.petclinic.web;


import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Proveedor;
import org.springframework.samples.petclinic.service.ProveedorService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedProveedorNifException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
	
	@Autowired
	private ProveedorService proveedorService;
	
	
	@GetMapping(value = { "/listadoProveedores" })
	public String listadoProveedores(ModelMap model) {
		String vista = "proveedores/listadoProveedores";
		Iterable<Proveedor> proveedores = proveedorService.findAll();
		model.put("proveedores", proveedores);
		return vista;
		
	}
	
	
	@GetMapping(value = "/new")
	public String crearProveedor(ModelMap model) {
		String vista = "proveedores/editProveedor";
		model.addAttribute("proveedor", new Proveedor());
		return vista;
	}

	@PostMapping(value = "/save")
	public String guardarProveedor(@Valid Proveedor proveedor, BindingResult result, ModelMap model) {
		String vista;
		
		if(result.hasErrors()) {
			model.addAttribute("proveedor", proveedor);
			vista = "proveedores/editProveedor";
		} else {
			try {
				proveedorService.saveProveedor(proveedor);
			} catch(DuplicatedProveedorNifException ex) {
				log.warn("Excepción: ya existe un proveedor con nif "+ proveedor.getNif());
				result.rejectValue("nif", "duplicate", "Ya existe un proveedor con el mismo nif");
				return "proveedores/editProveedor";
			}
			model.addAttribute("message", "Proveedor created successfully");
			vista = listadoProveedores(model);
		}
		
		return vista;
	}
	
	
	@GetMapping(value = "/delete/{proveedorId}")
	public String borrarProveedor(@PathVariable("proveedorId") int id, ModelMap model) {
		String vista;
		Optional<Proveedor> op = proveedorService.findProveedorById(id);
		if(op.isPresent()) {
			proveedorService.delete(op.get());
			model.addAttribute("message", "Proveedor deleted successfully");
		} else {
			model.addAttribute("message", "Proveedor not found");
			
		}
		vista = listadoProveedores(model);
		return vista;
	}
	
	
	@GetMapping(value = "/update/{proveedorId}")
	public String editarProveedor(@PathVariable("proveedorId") int id, ModelMap model) {
		String vista = "proveedores/editProveedor";
		Optional<Proveedor> proveedor = proveedorService.findProveedorById(id);
		if(!proveedor.isPresent()) {
			model.addAttribute("message", "Proveedor not found");
			vista = listadoProveedores(model);
		} else {
			model.addAttribute("proveedor", proveedor.get());
		}
		return vista;
	}
	
	
}
