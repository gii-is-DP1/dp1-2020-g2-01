<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">
    <h2>Clientes</h2>
    
    <table id="clientesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre</th>
            <th>Apellidos</th>
            <th>DNI</th>
            <th>Fecha de Nacimiento</th>
            <th>Teléfono</th>
            <th>Vehículos</th>
            <th>Usuario</th>
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
                   <c:out value="${clientes.fechaNacimiento}"/>
                </td>
                <td>
                   <c:out value="${clientes.telefono}"/>
                </td>
                <td>
                    <c:forEach var="vehiculo" items="${clientes.vehiculos}">
                        <c:out value="${vehiculo.modelo} "/>
                    </c:forEach>
                </td>
                <td>
                   <c:out value="${clientes.user.username}"/>
                </td>
                
                <td>
                	<spring:url value="/clientes/delete/{clienteId}" var="clienteUrl">
                        <spring:param name="clienteId" value="${clientes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">Delete</a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/clientes/update/{clienteId}" var="clienteUrl">
                        <spring:param name="clienteId" value="${clientes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">Update</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
