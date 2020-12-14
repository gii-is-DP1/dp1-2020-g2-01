<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vehiculos">
    <h2>Vehículos</h2>
    
    <table id="vehiculosTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Matrícula</th>
            <th>Número de bastidor</th>
            <th>Modelo</th>
            <th>Tipo de vehículo</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vehiculos}" var="vehiculos">
            <tr>
               <td>
                    <c:out value="${vehiculos.id}"/>
                </td>
                
            
                <td>
                    <c:out value="${vehiculos.matricula}"/>
                </td>
                
                <td>
                   <c:out value="${vehiculos.numBastidor}"/>
                </td>
                
                <td>
                   <c:out value="${vehiculos.modelo}"/>
                </td>
                
                
                <td>
                   <c:out value="${vehiculos.tipoVehiculo.name}"/>
                </td>
                
                <td>
                	<spring:url value="/vehiculos/delete/{clienteId}/{vehiculoId}" var="vehiculoUrl">
                		<spring:param name="clienteId" value="${clienteId}"/>
                        <spring:param name="vehiculoId" value="${vehiculos.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vehiculoUrl)}">Delete</a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/vehiculos/update/{clienteId}/{vehiculoId}" var="vehiculoUrl">
                		<spring:param name="clienteId" value="${clienteId}"/>
                        <spring:param name="vehiculoId" value="${vehiculos.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vehiculoUrl)}">Update</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>

	<spring:url value="/vehiculos/new/{clienteId}" var="vehiculoUrl">
    	<spring:param name="clienteId" value="${clienteId}"/>
   	</spring:url>
   	<a href="${fn:escapeXml(vehiculoUrl)}">Añadir nuevo vehículo</a>

</petclinic:layout>
