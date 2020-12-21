<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="finalizar">
   
    <br>
    <div class="reparacion" align="center">
    	<h4><strong>�Est� seguro de que desea dar por finalizada esta reparaci�n?</strong></h4>
    	<h5>Se sobreescribir� la fecha de finalizaci�n de esta reparaci�n.</h5>
    	<br>
    	
    	<spring:url value="/reparaciones/finalizarReparacion/{reparacionId}" var="reparacionUrl">
    					<spring:param name="reparacionId" value="${reparacion.id}"/>
    	</spring:url>		
    	<a href="${fn:escapeXml(reparacionUrl)}" class="btn btn-success btn-lg" role="button">S�, estoy seguro</a>
		<a href="/reparaciones/listadoReparaciones" class="btn btn-default btn-lg" role="button" style="border-width: 1px">No, volver al listado</a>
		<br><br>
		<h5>Se enviar� un correo electr�nico al cliente de que su veh�culo ya est� reparado.</h5>
    	<h5>Esta acci�n no se puede deshacer.</h5>
    </div>

</petclinic:layout>