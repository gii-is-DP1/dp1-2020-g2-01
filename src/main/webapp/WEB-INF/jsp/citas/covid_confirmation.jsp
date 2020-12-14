<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="covid">
    <h3 align="center">Si se ha dado un caso de COVID-19 en algún empleado de la empresa, siguiendo las
    normas sanitarias, se deben cancelar todas las citas.</h3>
    <br>
    <div class="covid" align="center">
    	<h4><strong>¿Está seguro de que desea cancelar todas las citas previstas para las próximas dos semanas?</strong></h4>
    	<br>
    	<a href="/citas/eliminarCitasCovid" class="btn btn-success btn-lg active" role="button">Sí, estoy seguro</a>
		<a href="/citas/listadoCitas" class="btn btn-default btn-lg active" role="button">No, volver al listado</a>
		<br><br>
		<h5>Se enviará un correo electrónico anunciado la cancelación a todos los clientes afectados de forma automática</h5>
    	<h5>Esta acción no se puede deshacer.</h5>
    </div>
    
    
   


</petclinic:layout>