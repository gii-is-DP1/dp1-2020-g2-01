<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">

    <h2>Información de cliente</h2>


    <table class="table table-striped">
        <tr>
            <th>Nombre y apellidos</th>
            <td><b><c:out value="${clientes.nombre} ${clientes.apellidos}"/></b></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${clientes.dni}"/></td>
        </tr>
        <tr>
            <th>Fecha de nacimiento</th>
            <td><c:out value="${clientes.fechaNacimiento}"/></td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${clientes.telefono}"/></td>
        </tr>
        <tr>
            <th>E-mail</th>
            <td><c:out value="${clientes.email}"/></td>
        </tr>
        <tr>
            <th>Teléfono</th>
            <td><c:out value="${clientes.telefono}"/></td>
        </tr>
        <tr>
            <th>Usuario</th>
            <td><c:out value="${clientes.user.username}"/></td>
        </tr>
    </table>
	<br>
    <spring:url value="/clientes/update/{username}" var="clienteUrl">
    	<spring:param name="username" value="${clientes.user.username}"/>
    </spring:url>
        <a href="${fn:escapeXml(clienteUrl)}" class="btn btn-default">Editar perfil</a>

    <spring:url value="/vehiculos/listadoVehiculos" var="addUrl">
       
    </spring:url>
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Ver mis vehículos</a>
	
	<!-- AÑADIR FACTURAS -->
    <br/>
    <br/>
    <br/>

</petclinic:layout>
