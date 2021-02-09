<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="clientes">
    <h2 class="col-sm-2">Clientes</h2>
    
	<form action="" method="get" class="col-sm-4 col-sm-offset-2">
	<input type="text" id="apellidos" name="apellidos" placeholder="Apellidos"/>
	<input type="submit" value="Buscar" />
	</form>

   	<spring:url value="/clientes/new" var="clienteUrl">
    </spring:url>
   	<a class="btn btn-success col-sm-offset-2 col-sm-2" href="${fn:escapeXml(clienteUrl)}"><span class="glyphicon glyphicon-plus"></span> Añadir cliente</a>

    <div class="col-sm-12" style="height:5px"></div>
    <table id="clientesTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre</th>
            <th>Apellidos</th>
            <th>DNI</th>
            <th>E-mail</th>
            <th>Teléfono</th>
            <th>Detalles</th>
            <sec:authorize access="hasAuthority('cliente')">
            <th></th>
            <th></th>
            </sec:authorize>
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
                
                <td>
                <spring:url value="/clientes/clienteDetails/{username}" var="clienteUrl">
                        <spring:param name="username" value="${cliente.user.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
                	
                
                </td>
                
            	<sec:authorize access="hasAuthority('cliente')">
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
                </sec:authorize>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
