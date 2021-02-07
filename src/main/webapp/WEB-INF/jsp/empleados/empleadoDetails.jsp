<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
<%@ page contentType="text/html; charset=UTF-8" %>


<petclinic:layout pageName="empleado">
<style>
    .helper {
		display: inline-block;
		height: 100%;  	
    	vertical-align:middle;
		}
    </style>
<div class="col-sm-12" style="margin-bottom: 20px">
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Información de empleado</h3>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Nombre y apellidos: </label> ${empleado.nombre}&nbsp${empleado.apellidos}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">DNI: </label>${empleado.dni}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de nacimiento: </label>
    <fmt:parseDate value="${empleado.fechaNacimiento}" pattern="yyyy-MM-dd" 
	                             var="parsedDate" type="date" />
	
		<fmt:formatDate value="${parsedDate}" var="fecha" 
	                              type="date" pattern="dd/MM/yyyy" />
	    ${fecha}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Teléfono: </label>${empleado.telefono}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">E-mail: </label>${empleado.email}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Usuario: </label>${empleado.usuario.username}
  </div>
</div>

   <spring:url value="/empleados/update/{username}" var="empleadoUrl">
   		<spring:param name="username" value="${empleado.usuario.username}"/>
   </spring:url>
       	<a href="${fn:escapeXml(empleadoUrl)}" class="btn btn-success">Editar perfil</a>
</div>
<div class="col-sm-6">
<c:if test="${not empty reparaciones}">
	<div class="panel panel-success">
	  <div class="panel-heading">
	    <h3 class="panel-title">Reparaciones</h3>
	  </div>
		  <table id="reparacionesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Descripción</th>
            <th>Vehículo</th>
            <th>Cliente</th>
            <th></th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reparaciones}" var="reparacion">
            <tr>
            
                <td>
                	<c:out value="${reparacion.descripcion}"></c:out>
                </td>
                
                <td>
                   <c:out value="${reparacion.cita.vehiculo.modelo}"/>
                </td>
                
                <td>
                	<c:out value="${reparacion.cita.vehiculo.cliente.nombre}"/>
               		<spring:url value="/clientes/clienteDetails/{username}" var="clienteUrl">
                       <spring:param name="username" value="${reparacion.cita.vehiculo.cliente.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                   	<span class="glyphicon glyphicon-eye-open"></span></a>
               		
                   	<p><small><c:out value="${reparacion.cita.vehiculo.cliente.apellidos}"/></small></p>
                </td>
                
                <td>
                	<spring:url value="/reparaciones/update/{reparacionId}" var="reparacionUrl">
                        <spring:param name="reparacionId" value="${reparacion.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(reparacionUrl)}"><span class="glyphicon glyphicon-pencil"></span></a>
	                
                </td>
	             
	             
            	<td>
                	<spring:url value="/reparaciones/getReparacion/{reparacionId}" var="reparacionUrl">
                        <spring:param name="reparacionId" value="${reparacion.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(reparacionUrl)}"><span class="glyphicon glyphicon-eye-open"></span></a>
	                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
	</div>
</c:if>
</div>
</div>

</petclinic:layout>
