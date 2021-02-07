<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="covid">
    <h3 align="center">Si se ha dado un caso de COVID-19 en alg�n empleado de la empresa, siguiendo las
    normas sanitarias, se deben cancelar todas las citas.</h3>
    <br>
    <div class="covid" align="center">
    	<h4><strong>Para cancelar las citas de los pr�ximos 14 d�as, seleccione el taller donde se ha dado el caso positivo:</strong></h4>
    	<br>
    	
    	<form action="/citas/eliminarCitasCovid" method="get" class="col-sm-6">
                
                <label class="col-sm-2">Taller</label><select name="taller" id="taller" class="col-sm-6">
                	<c:forEach var="taller" items="${talleres}">
                		<option id="taller" value="${taller.ubicacion}">${taller.ubicacion}</option>
        			</c:forEach>
                </select>
                <div class="col-sm-1"></div>
           <button class="btn btn-success col-sm-8" type="submit">Cancelar citas para las pr�ximas dos semanas</button>


     </form>
		<a href="/citas/listadoCitas" class="btn btn-default" role="button" style="border-width: 1px">Volver al listado</a>
		<br><br>
		<h5>Se enviar� un correo electr�nico anunciado la cancelaci�n a todos los clientes afectados de forma autom�tica</h5>
    	<h5>Esta acci�n no se puede deshacer.</h5>
    </div>
    
    
   


</petclinic:layout>