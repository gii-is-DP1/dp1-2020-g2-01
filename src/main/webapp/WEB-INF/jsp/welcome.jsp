<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
	<style> 
		.imagen1 {
		    width:300px;
		    height:300px;
		    float:left;
		    margin:10px 10px 0 0;
		}		
	
	</style>
	
    
   <div class="col-md-12">
        <spring:url value="/resources/images/logoCompletoSinFondo.png" htmlEscape="true" var="logo"/>
         <img class="img-responsive" src="${logo}"/>
   </div>
        
   
   	<div>
        <spring:url value="/resources/images/contacto.jpg" htmlEscape="true" var="contacto"/>
        <a href="/talleres/contacto">
         	<img class="img-responsive imagen1" src="${contacto}"/>
        </a>
   	</div>
   	
   	<div>
        <spring:url value="/resources/images/calendario2.jpg" htmlEscape="true" var="calendario"/>
        <a href="/citas/listadoCitas">
    		<img class="img-responsive imagen1" src="${calendario}"/>
    	</a>
   	</div>
   
	<div>
        <spring:url value="/resources/images/martillos.jpg" htmlEscape="true" var="reparacion"/>
        <a href="/reparaciones/listadoReparaciones">
        	<img class="img-responsive imagen1" src="${reparacion}"/>
        </a>
   	</div>
   	
   	<div>
        <spring:url value="/resources/images/vehiculo.jpg" htmlEscape="true" var="vehiculo"/>
        <a href="/vehiculos/listadoVehiculos">
        	<img class="img-responsive imagen1" src="${vehiculo}"/>
        </a>
   	</div>
   	
   	
   	<div>
        <spring:url value="/resources/images/destornillador.jpg" htmlEscape="true" var="reparacionComun"/>
        <a href="/comunes/listadoRepCom">
        	<img class="img-responsive imagen1" src="${reparacionComun}"/>
        </a>
   	</div>
   	
   	
   	<div>
        <spring:url value="/resources/images/engranaje.jpg" htmlEscape="true" var="inventario"/>
        <a href="/recambios/inventario">
        	<img class="img-responsive imagen1" src="${inventario}"/>
        </a>
   	</div>
   	
   <div>
        <spring:url value="/resources/images/proveedores.jpg" htmlEscape="true" var="proveedores"/>
        <a href="/proveedores/listadoProveedores">
        	<img class="img-responsive imagen1" src="${proveedores}"/>
        </a>
   	</div>
   	
   	
   	<div>
        <spring:url value="/resources/images/pedido.jpg" htmlEscape="true" var="pedido"/>
        <a href="/pedidosRecambio/listadoPedidosRecambio">
        	<img class="img-responsive imagen1" src="${pedido}"/>
        </a>
   	</div>
   	
   	
   	<div>
        <spring:url value="/resources/images/factura.jpg" htmlEscape="true" var="factura"/>
        <a href="/facturas/listadoFacturas">
        	<img class="img-responsive imagen1" src="${factura}"/>
        </a>
   	</div>
   	


</petclinic:layout>
