<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<petclinic:layout pageName="finalizar">
	<jsp:attribute name="customScript">
   		<script>
   			$("#descuento").focusout(function() {
   				var precio_base = document.getElementById("precioTotal").value
   				var descuento = document.getElementById("descuento").value
   				document.getElementById("precioConDescuento").value = precio_base*(1-descuento/100)
   			})
   		</script>
   	</jsp:attribute>
   	<jsp:body>
    <br>
    
    <div class="reparacion" align="center">
    
    	<h2>A continuación se mostrará la factura correspondiente: </h2>
    	<form:form modelAttribute="factura" class="form-horizontal" id="add-factura-form">
    		<petclinic:inputField label="Fecha de Pago" name="fechaPago" readonly="true" noglyphicon="true"/>
    		<label class="col-sm-2 control-label">Linea de Factura</label>
    		<div class="col-sm-10"> 
	    	<table id="FacturasTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th>Recambio</th>
	            <th>Precio</th>
	            <th>Descuento</th>
	            <th>Precio Total</th>
	
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${factura.lineaFactura}" var="lineaFactura">
	            <tr>
	               <td>
	                    <c:out value="${lineaFactura.recambio}"/>
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
	                <input style="display: none" name="lineaFactura" type="checkbox" value="${lineaFactura.id}" checked>
	                </c:forEach>
	                </tbody>
	                </table>
                </div>
    		<petclinic:inputField label="Precio Base" name="precioTotal" readonly="true" noglyphicon="true"/>
    		<petclinic:inputField label="Descuento" name="descuento" noglyphicon="true"/>
    		<petclinic:inputField label="Precio Total" name="precioConDescuento" readonly="true" noglyphicon="true"/>
    	<h4><strong>¿Está seguro de que desea generar la factura?</strong></h4>
    	<br>
    	
    	<spring:url value="/reparaciones/finalizarReparacion/{reparacionId}" var="reparacionUrl">
    					<spring:param name="reparacionId" value="${reparacion.id}"/>
    	</spring:url>		
    	<!-- <a href="${fn:escapeXml(reparacionUrl)}" class="btn btn-success btn-lg" type="submit">Sí, estoy seguro</a>  -->
    	<button class="btn btn-success btn-lg" type="submit">Sí, estoy seguro</button>
		<a href="/reparaciones/getReparacion/${reparacion.id}" class="btn btn-default btn-lg" role="button" style="border-width: 1px">No, volver a la reparación</a>
		</form:form>
    	
    	
    	
    </div>
</jsp:body>
</petclinic:layout>