<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="talleres">
    <h2>Talleres</h2>
    <div class="col-sm-12">
    
    <div class="col-sm-2"><a class="btn btn-success" href="/talleres/new"><span class="glyphicon glyphicon-plus"></span> Crear un nuevo taller</a></div>
    <div class="col-sm-3 col-sm-offset-7"><a style="float: right" class="btn btn-success" href="/talleres/contacto"><span class="glyphicon glyphicon-earphone"></span> Página de contacto para clientes</a></div>
	<div class="col-sm-12">
    <table id="talleresTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Nombre</th>
            <th>Teléfono</th>
            <th>Correo</th>
            <th>Ubicación</th>
            <th></th>
            <th></th>
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
                	<spring:url value="/talleres/update/{tallerId}" var="tallerUrl">
                        <spring:param name="tallerId" value="${taller.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(tallerUrl)}"><span class="glyphicon glyphicon-pencil"></span></a>
                
                </td>
                
                <td>
                	<spring:url value="/talleres/delete/{tallerId}" var="tallerUrl">
                        <spring:param name="tallerId" value="${taller.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(tallerUrl)}"><span class="glyphicon glyphicon-trash"></span></a>
                
                </td>
                
         		
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
	</div>

</petclinic:layout>
