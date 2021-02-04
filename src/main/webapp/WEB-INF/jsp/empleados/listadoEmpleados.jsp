<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="empleados">
    <h2>Empleados</h2>
    
    <td>
    	<spring:url value="/empleados/new" var="empleadoUrl">
        </spring:url>
       	<a class="btn btn-success" href="${fn:escapeXml(empleadoUrl)}"><span class="glyphicon glyphicon-plus"></span> A�adir empleado</a>
                
    </td>
    
    <table id="empleadosTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Usuario</th>
            <th>DNI</th>
            <th>Fecha de nacimiento</th>
            <th>E-mail</th>
            <th>Tel�fono</th>
            <th>Fecha del inicio del contrato</th>
            <th>Fecha del fin del contrato</th>
            <th>N�mero seguridad social</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${empleado}" var="empleado">
            <tr>
          
            
                <td>
                    <c:out value="${empleado.nombre}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.apellidos}"/>
                </td>
                
                 <td>
                    <c:out value="${empleado.usuario.username}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.dni}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.fechaNacimiento}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.email}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.telefono}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.fecha_ini_contrato}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.fecha_fin_contrato}"/>
                </td>
                
                  <td>
                    <c:out value="${empleado.num_seg_social}"/>
                </td>
                
                
                 <td>
                	<spring:url value="/empleados/empleadoDetails/{username}" var="empleadoUrl">
                        <spring:param name="username" value="${empleado.usuario.username}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(empleadoUrl)}"><span class="glyphicon glyphicon-eye-open"></span></a>
                
                </td>
           
                <td>
                	<spring:url value="/empleados/delete/{empleadoId}" var="empleadoUrl">
                        <spring:param name="empleadoId" value="${empleado.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(empleadoUrl)}"><span class="glyphicon glyphicon-trash"></span></a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
