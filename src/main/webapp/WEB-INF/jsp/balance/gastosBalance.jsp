<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="balanceEconomico">
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
    
    <a href="/balanceEconomico?gastos=false" class="btn btn-success">Ver ingresos</a>
    
    
    <br/><br/>
    
    
    <table id="gastosTable" class="table table-striped">
        <thead>
	        <tr>
	            <th>Número de pedido</th>
	            <th>Fecha</th>
	            <th>Cantidad gastada</th>
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
		        		<c:out value="-${factura.precioTotal} €" />		        	
		        	</td>		        	
		        	
		        	<td>
						<spring:url value="/" var="solicitudUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(solicitudUrl)}">
	                    	<span class="helper glyphicon glyphicon-pencil"></span>
	                    </a>		        	
	                </td>
	                				
		        </tr>
      		</c:forEach>
        </tbody>
    </table>
</petclinic:layout>