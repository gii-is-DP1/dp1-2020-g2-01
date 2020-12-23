<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="vehiculos">
    <h2>Vehículos</h2>
    
	<a class="btn btn-success" href="/vehiculos/new"><span class="glyphicon glyphicon-plus"></span> Añadir vehículo</a>
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
                   <c:out value="${vehiculos.tipoVehiculo.tipo}"/>
                </td>
                
                
                
                <td>
                	<spring:url value="/vehiculos/update/{vehiculoId}" var="vehiculoUrl">
                        <spring:param name="vehiculoId" value="${vehiculos.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vehiculoUrl)}"><span class="glyphicon glyphicon-pencil"></span></a>
                
                </td>
                <sec:authentication property="name" var="username"/>
                <td>
                	<spring:url value="/vehiculos/delete/${username}/{vehiculoId}" var="vehiculoUrl">
                        <spring:param name="vehiculoId" value="${vehiculos.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(vehiculoUrl)}"><span class="glyphicon glyphicon-trash"></span></a>
                
                </td>
                                
                
              	
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
