<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="talleres">
    <h2>Talleres</h2>
    
    <table id="talleresTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Nombre</th>
            <th>Teléfono</th>
            <th>Correo</th>
            <th>Ubicación</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${taller}" var="taller">
            <tr>
               <td>
                    <c:out value="${taller.id}"/>
                </td>
                
            
                <td>
                    <c:out value="${taller.name}"/>
                </td>
                
                <td>
                   <c:out value="${taller.telefono}"/>
                </td>
                
                <td>
                   <c:out value="${taller.correo}"/>
                </td>
                
                <td>
                   <c:out value="${taller.ubicacion}"/>
                </td>
                
                <td>
                	<spring:url value="/talleres/delete/{tallerId}" var="tallerUrl">
                        <spring:param name="tallerId" value="${taller.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(tallerUrl)}">Delete</a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/talleres/update/{tallerId}" var="tallerUrl">
                        <spring:param name="tallerId" value="${taller.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(tallerUrl)}">Update</a>
                
                </td>
                
                <td>
                	<spring:url value="/talleres/show/{tallerId}" var="tallerUrl">
                        <spring:param name="tallerId" value="${taller.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(tallerUrl)}">Show</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
