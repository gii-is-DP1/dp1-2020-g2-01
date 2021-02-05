<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<table id="vehiculosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Matrícula</th>
            <th>Modelo</th>
            <th>Tipo de vehículo</th>
            <c:if test="${empty sinNombre}">
            <sec:authorize access="hasAuthority('admin')">
            <th>Cliente</th>
            </sec:authorize>
            <sec:authorize access="hasAuthority('empleado')">
            <th>Cliente</th>
            </sec:authorize>
            </c:if>
            <th>Número de bastidor</th>
            <th></th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vehiculos}" var="vehiculo">
            <tr>
                <td>
                  <c:out value="${vehiculo.matricula}"/>
                </td>
                
                
                <td>
                   <c:out value="${vehiculo.modelo}"/>
                </td>
                
                
                <td>
                   <c:out value="${vehiculo.tipoVehiculo.tipo}"/>
                </td>
                <c:if test="${empty sinNombre}">
               	<sec:authorize access="hasAuthority('admin')">
	             	<td class="textoTabla">
	             		
	               		<c:out value="${vehiculo.cliente.nombre}"/>
	               		<spring:url value="/clientes/clienteDetails/{username}" var="clienteUrl">
                        <spring:param name="username" value="${vehiculo.cliente.user.username}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
	               		
                    	<p><small><c:out value="${vehiculo.cliente.apellidos}"/></small></p>
	                </td>
               	</sec:authorize>
               	<sec:authorize access="hasAuthority('empleado')">
	             	<td class="textoTabla">
	             		
	               		<c:out value="${vehiculo.cliente.nombre}"/>
	               		<spring:url value="/clientes/clienteDetails/{username}" var="clienteUrl">
                        <spring:param name="username" value="${vehiculo.cliente.user.username}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
	               		
                    	<p><small><c:out value="${vehiculo.cliente.apellidos}"/></small></p>
	                </td>
               	</sec:authorize>
               	</c:if>
                
                <td>
                   <c:out value="${vehiculo.numBastidor}"/>
                </td>
                
	                <td>
	                	<spring:url value="/vehiculos/update/{vehiculoId}" var="vehiculoUrl">
	                        <spring:param name="vehiculoId" value="${vehiculo.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(vehiculoUrl)}"><span class="glyphicon glyphicon-pencil"></span></a>
	                
	                </td>
	             
	             
	                <td>
	                	<spring:url value="/vehiculos/delete/{vehiculoId}" var="vehiculoUrl">
	                        <spring:param name="vehiculoId" value="${vehiculo.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(vehiculoUrl)}"><span class="glyphicon glyphicon-trash"></span></a>
	                
	                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>

