<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vehiculos">
   
    <br>
    <div class="vehiculos" align="center">
    	<h4><strong>�Est� seguro de que desea borrar este veh�culo?</strong></h4>
    	
    	<spring:url value="/vehiculos/deleteVehiculo/{vehiculoId}" var="vehiculoUrl">
    					<spring:param name="vehiculoId" value="${vehiculos.id}"/>
    	</spring:url>
    	<br>		
    	<a href="${fn:escapeXml(vehiculoUrl)}" class="btn btn-success btn-lg" role="button">S�, estoy seguro</a>
		<a href="/clientes/listadoVehiculos" class="btn btn-default btn-lg" role="button" style="border-width: 1px">No, volver al listado</a>
		<br><br>
    	<h5>Esta acci�n no se puede deshacer.</h5>
    </div>

</petclinic:layout>