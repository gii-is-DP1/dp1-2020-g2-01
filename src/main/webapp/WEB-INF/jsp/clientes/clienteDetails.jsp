<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="cliente">
<style>
    .helper {
		display: inline-block;
		height: 100%;  	
    	vertical-align:middle;
		}
    </style>
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
<c:if test="${not empty cita}">
	<div class="col-sm-6">
	<div class="panel panel-primary">
	  <div class="panel-heading">
	    <h3 class="panel-title">Próxima cita</h3>
	  </div>
		  <div class="panel-body" style="padding: 0;padding-top:1%">
		  	<div class="col-sm-2">
	  			<p class="text-center" style="font-size: 65%; margin: 0">${cita.fecha.year}</p>
	           	<c:set var = "monthParsed" value = "${fn:substring(cita.fecha.month, 0, 3)}" />
	           	<p class = "text-center" style="color: DarkGray"><strong><small>${monthParsed}</small></strong></p>
	           	<p class="text-center"  style="margin: 0"><strong>${cita.fecha.dayOfMonth}</strong></p>
	      	</div>
	      	<div class="col-sm-5">
	      		<span class="helper"></span>
	      		<p>${cita.taller.ubicacion}</p>
	      	</div>
	      	<div class="col-sm-3">
	      		<p><c:out value="${cita.vehiculo.modelo}"/></p>
                <p><small><c:out value="${cita.vehiculo.matricula}"/></small></p>
	      	</div>
	      	<div class="col-sm-1">
                    <a href="/citas/listadoCitas">
                    	<span class="helper glyphicon glyphicon-eye-open"></span>
                    </a>
	      	</div>
	      	<div class="col-sm-1">
	      			<spring:url value="/citas/update/{citaId}" var="citaUrl">
                    <spring:param name="citaId" value="${cita.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
                    	<span class="helper glyphicon glyphicon-pencil"></span>
                    </a>
                    
                	<spring:url value="/citas/delete/{citaId}" var="citaUrl">
                    <spring:param name="citaId" value="${cita.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
						<span class="helper glyphicon glyphicon-trash"></span>
					</a>
	      	</div>
		  </div>
	</div>
	</div>
</c:if>
</div>
	
	
	<!-- AÑADIR FACTURAS -->

</petclinic:layout>
