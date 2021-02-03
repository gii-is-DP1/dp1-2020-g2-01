<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="ingresos">
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


    <h2>Ingresos (AÑADIR FILTRADO TEMPORAL??)</h2>
    
    <a href="/balanceEconomico?gastos=true" class="btn btn-success">Ver gastos</a>
    
    
    <br/><br/>
    
    
    <table id="ingresosTable" class="table table-striped">
        <thead>
	        <tr>
	            <th>Número de factura</th>
	            <th>Fecha</th>
	            <th>Cantidad ingresada</th>

	            <th></th>

	        </tr>
        </thead>
        <tbody>
        	<c:forEach items="${facturas}" var="factura">
		        <tr>
		        
		        	<td>
		        		<c:out value="${factura.id}" />
		        	</td>
		        	
		        	<td>
		        		<c:out value="${factura.fechaPago}" />
		        	</td>
		        	
		        	<td>
		        		<c:out value="${factura.precioConDescuento}" />		        	
		        	</td>      	
		        	
		        	<td>
						<spring:url value="/" var="facturaUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(facturaUrl)}">
	                    	<span class="glyphicon glyphicon-eye-open"></span>
	                    </a>		        	
	                </td>
	            
					
		        </tr>
      		</c:forEach>
        </tbody>
    </table>
    

</petclinic:layout>
