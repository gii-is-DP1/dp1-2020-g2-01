<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="clientes">
    <h2 class="col-sm-2">Clientes</h2>
    

    	<spring:url value="/clientes/new" var="clienteUrl">
        </spring:url>
       	<a class="btn btn-success col-sm-offset-8 col-sm-2" href="${fn:escapeXml(clienteUrl)}"><span class="glyphicon glyphicon-plus"></span> Añadir cliente</a>

    <div class="col-sm-12" style="height:5px"></div>
    <table id="clientesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre</th>
            <th>Apellidos</th>
            <th>DNI</th>
            <th>E-mail</th>
            <th>Teléfono</th>
            <th>Facturas</th>
            <th>Detalles</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${clientes}" var="cliente">
            <tr>
               <td>
                    <c:out value="${cliente.nombre}"/>
                </td>
            
                <td>
                    <c:out value="${cliente.apellidos}"/>
                </td>
                
                <td>
                   <c:out value="${cliente.dni}"/>
                </td>
                <td>
                   <c:out value="${cliente.email}"/>
                </td>
                <td>
                   <c:out value="${cliente.telefono}"/>
                </td>
       			<td><spring:url value="/facturas/listadoFacturas/{username}" var="clienteUrl">
                        <spring:param name="username" value="${cliente.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    <span class="glyphicon glyphicon-list-alt"></span></a>
                </td>
                
                <td>
                <spring:url value="/clientes/clienteDetails/{username}" var="clienteUrl">
                        <spring:param name="username" value="${cliente.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
                	
                
                </td>
                
                <td>
                <spring:url value="/clientes/update/{username}" var="clienteUrl">
                        <spring:param name="username" value="${cliente.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-pencil"></span></a>
                	
                
                </td>
                
                
                 <td>
                	
                <spring:url value="/clientes/delete/{username}" var="clienteUrl">
                        <spring:param name="username" value="${cliente.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
						<span class="glyphicon glyphicon-trash"></span></a>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
