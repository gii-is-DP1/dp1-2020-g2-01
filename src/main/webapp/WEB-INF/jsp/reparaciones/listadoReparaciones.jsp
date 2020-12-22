<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="reparaciones">
    <h2>Reparaciones</h2>
        
    <table id="reparacionesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Tiempo estimado</th>
            <th>Fecha de finalización</th>
            <th>Fecha de entrega</th>
            <th>Fecha de recogida</th>
            <sec:authorize access="hasAuthority('admin')">
	            <th>Empleados</th>
	            <th>Cliente</th>
            </sec:authorize>
            <th>Vehiculo</th>
            <sec:authorize access="hasAuthority('admin')">
	            <th></th>
	            <th></th>
	            <th></th>
            </sec:authorize>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reparaciones}" var="reparacion">
            <tr>
               <td>
                    <c:out value="${reparacion.id}"/>
                </td>
                
                <td>
                    <c:out value="${reparacion.name}"/>
                </td>
                
                <td>
                   <c:out value="${reparacion.descripcion}"/>
                </td>
                
                
                <td>
                   <c:out value="${reparacion.tiempoEstimado}"/>
                </td>
                
                <td>
                   <c:out value="${reparacion.fechaFinalizacion}"/>
                </td>
                
                <td>
                   <c:out value="${reparacion.fechaEntrega}"/>
                </td>
                
                <td>
                   <c:out value="${reparacion.fechaRecogida}"/>
                </td>
                
                <sec:authorize access="hasAuthority('admin')">
	                <td>
	                  <c:forEach items="${reparacion.empleados}" var="empleado">
	                  	<c:out value="${empleado.nombre}"/>
	                  	<c:out value=" "/>
	                  	<c:out value="${empleado.apellidos}"/>
	                  	<br/>
	                  </c:forEach>
	                </td>
	                
	               	<td>
	                  <c:out value="${reparacion.cita.vehiculo.cliente.nombre}"/>
	                  <c:out value=" "/>
	                  <c:out value="${reparacion.cita.vehiculo.cliente.apellidos}"/>
	                </td>
               </sec:authorize>
               
                <td>
                  <c:out value="${reparacion.cita.vehiculo.modelo}"/>
                </td>
               
               
               
               <sec:authorize access="hasAuthority('admin')">
	                <td>
	    				<spring:url value="/reparaciones/finalizar/{reparacionId}" var="reparacionUrl">
	    					<spring:param name="reparacionId" value="${reparacion.id}"/>
	    				</spring:url>
	   					<a class="btn btn-success" href="${fn:escapeXml(reparacionUrl)}">Finalizar</a>
					</td>               
	                <td>
	                	<spring:url value="/reparaciones/update/{reparacionId}" var="reparacionUrl">
	                        <spring:param name="reparacionId" value="${reparacion.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(reparacionUrl)}">Update</a>
	                
	                </td>
	                
	                <td>
	                	<spring:url value="/reparaciones/delete/{reparacionId}" var="reparacionUrl">
	                        <spring:param name="reparacionId" value="${reparacion.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(reparacionUrl)}">Delete</a>
	                
	                </td>
                </sec:authorize>
                
           
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <sec:authorize access="hasAuthority('admin')">
    	<a href="/reparaciones/new">Crear una nueva reparación</a>
	</sec:authorize>

</petclinic:layout>
