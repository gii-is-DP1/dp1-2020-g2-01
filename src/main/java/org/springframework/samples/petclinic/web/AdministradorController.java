package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Administrador;
import org.springframework.samples.petclinic.service.AdministradorService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administradores")
public class AdministradorController {

	@Autowired
	private AdministradorService adminService;
	
//	@GetMapping(value = "/listadoAdministradores")
//	public String listadoAdministradores(ModelMap m) {
//		String vista= "/administradores/listadoAdministradores";
//		Iterable<Administrador> admins = adminService.findAll();
//      m.addAttribute("administradores", admins);
//		return vista;
//	}
//		
//	@GetMapping(value = "/new")
//	public String crearAdministrador(ModelMap model) {
//		String vista = "administradores/editAdmin";
//		model.addAttribute("administrador", new Administrador());
//		return vista;
//	}
//
	@PostMapping(value = "/save")
	public String guardarAdministrador(@Valid Administrador admin, BindingResult result, ModelMap model) {		
		if(result.hasErrors()) {
			model.addAttribute("administrador", admin);
			return  "administradores/editAdministrador";
		} else {
			adminService.saveAdministrador(admin);
			model.addAttribute("message", "Administrador actualizado con éxito");
			String username = admin.getUsuario().getUsername();
			return mostrasDetalles(username, model);
		}

	}
	
	@GetMapping(value = "/administradorDetails/{username}")
	public String mostrasDetalles(@PathVariable("username") String username, ModelMap model) {
		Optional<Administrador> admin = this.adminService.findAdministradorByUsuarioUsername(username);
		if(!admin.isPresent()) {
			model.addAttribute("message", "Administrador no encontrado");
			model.addAttribute("messageType", "danger");
			return "/";
		}
		String username2 = SecurityContextHolder.getContext().getAuthentication().getName();
		String auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString();
		if(username.equals(username2) || auth.equals("admin")) {
			model.addAttribute("administrador", admin.get());

			return "administradores/administradorDetails";
		}else {
			return "redirect:/";
		}
	}
//	
//	
//	@GetMapping(value = "/delete/{adminId}")
//	public String borrarAdministrador(@PathVariable("adminId") int id, ModelMap model) {
//		String vista;
//		Optional<Administrador> op = adminService.findById(id);
//		if(op.isPresent()) {
//			adminService.delete(op.get());
//			model.addAttribute("message", "Administrador borrado con éxito");
//		} else {
//			model.addAttribute("message", "Administrador no encontrado");
//			
//		}
//		vista = listadoAdministradores(model);
//		return vista;
//	}
	
	
	@GetMapping(value = "/update/{username}")
	public String editarAdministrador(@PathVariable("username") String username, ModelMap model) {
		String vista = "administradores/editAdministrador";
		Optional<Administrador> admin = adminService.findAdministradorByUsuarioUsername(username);
		if(!admin.isPresent()) {
			model.addAttribute("message", "Administrador no encontrado");
			vista = "/";
		} else {
			admin.get().getUsuario().setPassword("");
			model.addAttribute("administrador", admin.get());
		}
		return vista;
	}
	
	
	
}
