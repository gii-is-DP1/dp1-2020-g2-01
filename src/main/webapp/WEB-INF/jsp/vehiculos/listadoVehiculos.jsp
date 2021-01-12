<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="vehiculos">
	<style>
		.textoTabla {
			min-width: 120px;
		}
		
	</style>


    <h2 class="col-sm-2">Vehículos</h2>
    
	<sec:authorize access="hasAuthority('cliente')"><a class="btn btn-success col-sm-offset-8 col-sm-2" href="/vehiculos/new"><span class="glyphicon glyphicon-plus"></span> Añadir vehículo</a>
    </sec:authorize>
    <div class="col-sm-12" style="height:5px"></div>
    <petclinic:tablaVehiculos/>


</petclinic:layout>
