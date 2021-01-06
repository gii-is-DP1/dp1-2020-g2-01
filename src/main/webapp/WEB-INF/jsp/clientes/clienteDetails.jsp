<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="cliente">
<div class="col-sm-12">
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Información de cliente</h3>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Nombre y apellidos: </label>${cliente.nombre} ${cliente.apellidos}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">DNI: </label>${cliente.dni}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de nacimiento: </label>
    <fmt:parseDate value="${cliente.fechaNacimiento}" pattern="yyyy-MM-dd" 
	                             var="parsedDate" type="date" />
	
		<fmt:formatDate value="${parsedDate}" var="fecha" 
	                              type="date" pattern="dd/MM/yyyy" />
	    ${fecha}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Teléfono: </label>${cliente.telefono}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">E-mail: </label>${cliente.email}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Usuario: </label>${cliente.user.username}
  </div>
</div>
   <spring:url value="/clientes/update/{username}" var="clienteUrl">
   		<spring:param name="username" value="${cliente.user.username}"/>
   </spring:url>
       	<a href="${fn:escapeXml(clienteUrl)}" class="btn btn-success">Editar perfil</a>
</div>
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Vehículos</h3>
  </div>
  <petclinic:tablaVehiculos/>
</div>
</div>
</div>
	
	
	<!-- AÑADIR FACTURAS -->

</petclinic:layout>
