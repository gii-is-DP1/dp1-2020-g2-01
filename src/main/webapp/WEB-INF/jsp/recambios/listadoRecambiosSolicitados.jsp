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
        
    <table id="listadoRecambiosSolicitadosTable" class="table table-striped">
        <thead>
	        <tr>
	            <th>Recambio</th>
	            <th>Reparación</th>
	            <th>Empleado</th>
	        </tr>
        </thead>
        <tbody>
        	<c:forEach items="${lineasFactura}" var="lineaFactura">
		        <tr>
		        	<td>
		        		<c:out value="${lineaFactura.ejemplarRecambio.recambio.name}" />
		        	</td>
		        	
		        	<td>
		        		<c:out value="${lineaFactura.reparacion.descripcion}" />
		        	</td>
		        	
		        	<td>
		        		<c:out value="${lineaFactura.empleado}" />		        	
		        	</td>
		        </tr>
      		</c:forEach>
        </tbody>
    </table>
    

</petclinic:layout>
