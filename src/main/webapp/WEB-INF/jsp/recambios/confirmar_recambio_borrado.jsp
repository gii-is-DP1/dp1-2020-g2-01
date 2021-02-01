<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="recambios">
   
    <br>
    <div class="recambios" align="center">
    	<h4><strong>¿Está seguro de que desea borrar este recambio del inventario?</strong></h4>
    	
    	<spring:url value="/recambios/deleteRecambio/{recambioId}" var="recambioUrl">
    					<spring:param name="recambioId" value="${recambios.id}"/>
    	</spring:url>
    	<br>		
    	<a href="${fn:escapeXml(recambioUrl)}" class="btn btn-success btn-lg" role="button">Sí, estoy seguro</a>
		<a href="/recambios/listadoRecambios" class="btn btn-default btn-lg" role="button" style="border-width: 1px">No, volver al listado</a>
		<br><br>
    	<h5>Esta acción no se puede deshacer.</h5>
    </div>

</petclinic:layout>