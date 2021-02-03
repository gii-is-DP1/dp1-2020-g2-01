<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="pedidosEmpleado">
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



    <h2>Listado de recambios solicitados</h2>
    
	<a href="/recambios/solicitud/new" class="btn btn-success">Crear solicitud</a>
	<br/><br/>

    <a href="/recambios/listadoRecambiosSolicitados" class="btn btn-success">Todas las solicitudes</a>
    <a href="/recambios/listadoRecambiosSolicitados?terminadas=true" class="btn btn-success">Solicitudes terminadas</a>
    <a href="/recambios/listadoRecambiosSolicitados?terminadas=false" class="btn btn-success">Solicitudes no terminadas</a>
    
    
    <br/><br/>
    
    
    <table id="listadoRecambiosSolicitadosTable" class="table table-striped">
        <thead>
	        <tr>
	            <th>Recambio</th>
	            <th>Cantidad</th>
	            <th>Terminada</th>
	            <th>Empleado que realizó la solicitud</th>
	            <th>Reparacion</th>
	            <th></th>
	            <th></th>
	            <th></th>
	        </tr>
        </thead>
        <tbody>
        	<c:forEach items="${solicitudes}" var="solicitud">
		        <tr>
		        
		        	<td>
		        		<c:out value="${solicitud.recambio.name}" />
		        	</td>
		        	
		        	<td>
		        		<c:out value="${solicitud.cantidad}" />
		        	</td>
		        	
		        	<td>
		        		<c:out value="${solicitud.terminada}" />		        	
		        	</td>
		        	
		        	<td>
		        		<c:out value="${solicitud.empleado.nombre} ${solicitud.empleado.apellidos}" />	        	
		        	</td>
		        	
		        	<td>
		        		<c:out value="${solicitud.reparacion.id}" />	        	
		        	</td>
		        	
		        	
		        	<td>
						<spring:url value="/recambios/solicitud/update/${solicitud.id}" var="solicitudUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(solicitudUrl)}">
	                    	<span class="helper glyphicon glyphicon-pencil"></span>
	                    </a>		        	
	                </td>
	                
	                
		        	<td>
						<spring:url value="/recambios/solicitud/delete/${solicitud.id}" var="solicitudUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(solicitudUrl)}">
	                    	<span class="helper glyphicon glyphicon-trash"></span>
	                    </a>		        	
	                </td>
		        	
		        	
		        	<td>
			        	<c:if test="${solicitud.terminada==false}">
							<a href="/recambios/solicitud/terminarSolicitud/${solicitud.id}" class="btn btn-success">Terminar</a>
			        	</c:if>
		        	</td>
		        	
	
					
		        </tr>
      		</c:forEach>
        </tbody>
    </table>
    

</petclinic:layout>
