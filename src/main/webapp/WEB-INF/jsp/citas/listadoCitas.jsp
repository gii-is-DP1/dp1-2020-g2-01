<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citas">
    <h2>Citas</h2>
    

    <td>
    	<spring:url value="/citas/new" var="citaUrl">
        </spring:url>
       	<a href="${fn:escapeXml(citaUrl)}">Añadir cita</a>     
    </td>
   <br> 
    <th>
        <spring:url value="/citas/covid" var="citaUrl">
        </spring:url>
       	<a href="${fn:escapeXml(citaUrl)}">Cancelar citas por COVID</a>
    </th>

    <table id="citasTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Modelo</th>
            <th>Fecha</th>
            <th>Hora</th>
            <th>Tipo de cita</th>
            <th></th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citas}" var="citas">
            <tr>
               <td>
                    <c:out value="${citas.id}"/>
                </td>
            
                <td>
                    <c:out value="${citas.vehiculo.modelo}"/>
                </td>
                
                <td>
                   <c:out value="${citas.fecha}"/>
                </td>
                
                <td>
                   <c:out value="${citas.hora}"/>
                </td>
                
                <td>
                   <c:out value="${citas.tipoCita.tipo}"/>
                </td>
                
                <td>
                	<spring:url value="/citas/update/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${citas.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
                    	<span class="glyphicon glyphicon-pencil"></span>
                    </a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/citas/delete/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${citas.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
						<span class="glyphicon glyphicon-trash"></span>
					</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <spring:url value="/citas/new" var="citaUrl">
    </spring:url>
    <a class="btn btn-success" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-plus"></span> Añadir cita</a>



</petclinic:layout>
