<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">
    <h2>Clientes</h2>
    
    <td>
    	<spring:url value="/clientes/new" var="clienteUrl">
        </spring:url>
       	<a class="btn btn-success" href="${fn:escapeXml(clienteUrl)}"><span class="glyphicon glyphicon-plus"></span> Añadir cliente</a>

                
    </td>
    
    <table id="clientesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre</th>
            <th>Apellidos</th>
            <th>DNI</th>
            <th>E-mail</th>
            <th>Fecha de Nacimiento</th>
            <th>Teléfono</th>
            <th>Vehículos</th>
            <th>Facturas</th>
            <th>Nombre de usuario</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clientes}" var="clientes">
            <tr>
               <td>
                    <c:out value="${clientes.nombre}"/>
                </td>
            
                <td>
                    <c:out value="${clientes.apellidos}"/>
                </td>
                
                <td>
                   <c:out value="${clientes.dni}"/>
                </td>
                <td>
                   <c:out value="${clientes.email}"/>
                </td>
                
                <td>
                   <c:out value="${clientes.fechaNacimiento}"/>
                </td>
                <td>
                   <c:out value="${clientes.telefono}"/>
                </td>
                <td><spring:url value="/vehiculos/listadoVehiculos/{username}" var="clienteUrl">
                        <spring:param name="username" value="${clientes.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    <span class="glyphicon glyphicon-eye-open"></span></a>
                </td>
       			<td><spring:url value="/facturas/listadoFacturas/{username}" var="clienteUrl">
                        <spring:param name="username" value="${clientes.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    <span class="glyphicon glyphicon-eye-open"></span></a>
                </td>
                <td>
                   <c:out value="${clientes.user.username}"/>
                </td>
                
                <td>
                <spring:url value="/clientes/update/{username}" var="clienteUrl">
                        <spring:param name="username" value="${clientes.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-pencil"></span></a>
                	
                
                </td>
                
                
                 <td>
                	
                <spring:url value="/clientes/delete/{username}" var="clienteUrl">
                        <spring:param name="username" value="${clientes.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
						<span class="glyphicon glyphicon-trash"></span></a>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
