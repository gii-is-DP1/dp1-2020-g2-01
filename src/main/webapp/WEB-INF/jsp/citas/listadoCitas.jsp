<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citas">
    <h2>Citas</h2>
    
    <table id="citasTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Vehículo_id</th>
            <th>Fecha</th>
            <th>Hora</th>
            <th>Tipo</th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cita}" var="citas">
            <tr>
               <td>
                    <c:out value="${cita.id}"/>
                </td>
                
            
                <td>
                    <c:out value="${cita.vehiculo}"/>
                </td>
                
                <td>
                   <c:out value="${cita.fecha}"/>
                </td>
                
                <td>
                   <c:out value="${cita.hora}"/>
                </td>
                
                <td>
                   <c:out value="${cita.tipoCita}"/>
                </td>
                
                <td>
                	<spring:url value="/citas/delete/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${cita.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">Delete</a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/citas/update/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${cita.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">Update</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
