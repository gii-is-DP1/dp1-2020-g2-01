<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="finalizar">
   
    <br>
    
    <div class="reparacion" align="center">
    
    	<h2>A continuaci�n se mostrar� la factura correspondiente: </h2>
    	<form:form modelAttribute="factura" class="form-horizontal" id="add-factura-form" action="/reparaciones/finalizarReparacion/${reparacion.id}">
    		<petclinic:inputField label="Fecha de Pago" name="fechaPago" type="text" readonly="true"/>
    		<label class="col-sm-2 control-label">Linea de Factura</label>
    		<div class="col-sm-10"> 
	    	<table id="FacturasTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Descripcion</th>
	            <th>Precio</th>
	            <th>Descuento</th>
	            <th>Precio Total</th>
	
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${factura.lineaFactura}" var="lineaFactura">
	            <tr>
	               <td>
	                    <c:out value="${lineaFactura.descripcion}"/>
	                </td>
	
	                <td>
	                    <c:out value="${lineaFactura.precioBase}"/>
	                </td>
	
	                <td>
	                   <c:out value="${lineaFactura.descuento}"/>
	                </td>
	
	
	                <td>
	                   <c:out value="${lineaFactura.precio}"/>
	                </td>
	
	                </tr>
	                </c:forEach>
	                </tbody>
	                </table>
                </div>
    		<petclinic:inputField label="Precio Base" name="precioTotal" type="text" readonly="true"/>
    		<petclinic:inputField label="Descuento" name="descuento" type="text"/>
    		<petclinic:inputField label="Precio Total" name="precioConDescuento" type="text" readonly="true"/>
    	<h4><strong>�Est� seguro de que desea dar por finalizada esta reparaci�n?</strong></h4>
    	<h5>Se sobreescribir� la fecha de finalizaci�n de esta reparaci�n.</h5>
    	<br>
    	
    	<spring:url value="/reparaciones/finalizarReparacion/{reparacionId}" var="reparacionUrl">
    					<spring:param name="reparacionId" value="${reparacion.id}"/>
    	</spring:url>		
    	<!-- <a href="${fn:escapeXml(reparacionUrl)}" class="btn btn-success btn-lg" type="submit">S�, estoy seguro</a>  -->
    	<button class="btn btn-success btn-lg" type="submit">S�, estoy seguro</button>
		<a href="/reparaciones/listadoReparaciones" class="btn btn-default btn-lg" role="button" style="border-width: 1px">No, volver al listado</a>
		<br><br>
		</form:form>
		<h5>Se enviar� un correo electr�nico al cliente de que su veh�culo ya est� reparado.</h5>
    	<h5>Esta acci�n no se puede deshacer.</h5>
    	
    	
    	
    </div>

</petclinic:layout>