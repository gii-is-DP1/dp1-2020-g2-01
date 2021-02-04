package org.springframework.samples.petclinic.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}

	
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			int statusCode = Integer.parseInt(status.toString());
		
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				log.error("Error 404 - NOT FOUND");
				return "error-404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				log.error("Error 500 - SERVER ERROR");
				return "error-500";
			} else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				log.error("Error 403 - FORBIDDEN");
				return "error-403";
			}
		}
		
		return "error";
	}
	

}
