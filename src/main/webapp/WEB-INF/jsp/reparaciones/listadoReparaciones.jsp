<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<petclinic:layout pageName="reparaciones">
	<style>
		.helper {
			display: inline-block;
			height: 100%;  	
    		vertical-align:middle;
		}
	
		.textoTabla {
			min-width: 120px;
		}
		
		.fechaTabla {
			min-width: 80px;
		}
	
	
		th, td {
			text-align: center;
			
		}
		
	
	</style>



    <h2>Reparaciones</h2>
        
    <table id="reparacionesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Descripción</th>
            <th>Dia de la cita</th>
            <th>Vehiculo</th>
            <th>Fecha de finalización</th>
            <th>Fecha de recogida</th>
            <sec:authorize access="hasAuthority('admin') or hasAuthority('empleado')">
	            <th>Cliente</th>
            </sec:authorize>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reparaciones}" var="reparacion">
            <tr>
                
                <td class="textoTabla" style="text-align:left;">
                   <c:out value="${reparacion.descripcion}"/>
                </td>
                
                
                <td class="fechaTabla">
					<p class="text-center" style="font-size: 65%; margin: 0">${reparacion.cita.fecha.year}</p>
                	<c:set var = "monthParsed" value = "${fn:substring(reparacion.cita.fecha.month, 0, 3)}" />
                	<p class = "text-center" style="color: DarkGray"><strong><small>${monthParsed}</small></strong></p>
                	<p class="text-center"  style="margin: 0"><strong>${reparacion.cita.fecha.dayOfMonth}</strong></p>
                </td>
                
                <td class="textoTabla">
                    <p><c:out value="${reparacion.cita.vehiculo.modelo}"/></p>
                    <p><small><c:out value="${reparacion.cita.vehiculo.matricula}"/></small></p>
                </td>
                
                <td class="fechaTabla">
                   	<p class="text-center" style="font-size: 65%; margin: 0">${reparacion.fechaFinalizacion.year}</p>
                	<c:set var = "monthParsed" value = "${fn:substring(reparacion.fechaFinalizacion.month, 0, 3)}" />
                	<p class = "text-center" style="color: DarkGray"><strong><small>${monthParsed}</small></strong></p>
                	<p class="text-center"  style="margin: 0"><strong>${reparacion.fechaFinalizacion.dayOfMonth}</strong></p>
                </td>
                
                <td class="fechaTabla">
                   	<p class="text-center" style="font-size: 65%; margin: 0">${reparacion.fechaRecogida.year}</p>
                	<c:set var = "monthParsed" value = "${fn:substring(reparacion.fechaRecogida.month, 0, 3)}" />
                	<p class = "text-center" style="color: DarkGray"><strong><small>${monthParsed}</small></strong></p>
                	<p class="text-center"  style="margin: 0"><strong>${reparacion.fechaRecogida.dayOfMonth}</strong></p>
                </td>
                
                <sec:authorize access="hasAuthority('admin') or hasAuthority('empleado')">
	                <td class="textoTabla">
	               		<p><c:out value="${reparacion.cita.vehiculo.cliente.nombre}"/></p>
                    	<p><small><c:out value="${reparacion.cita.vehiculo.cliente.apellidos}"/></small></p>
	                </td>
					            
	            </sec:authorize>
					            
	                <td>
	                	<spring:url value="/reparaciones/getReparacion/{reparacionId}" var="reparacionUrl">
	                        <spring:param name="reparacionId" value="${reparacion.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(reparacionUrl)}">
	                    	<span class="helper glyphicon glyphicon-eye-open"></span>
	                    </a>
	                
	                </td>
					
                
                
           
            </tr>
        </c:forEach>
        </tbody>
    </table>
    

</petclinic:layout>
