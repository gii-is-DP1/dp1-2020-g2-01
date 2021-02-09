<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="balance">
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


    <h2>Gastos ${mesElegido}&nbsp${anyoElegido}</h2>
    
    <a href="/balanceEconomico" class="btn btn-success col-sm-2">Ver balances mensuales</a>
    <a href="/balanceEconomico/sinFiltro?gastos=false" class="btn btn-success col-sm-2">Ver todos los ingresos</a>
    <a href="/balanceEconomico/sinFiltro?gastos=true" class="btn btn-success col-sm-2">Ver todos los gastos</a>
    
    <form action="/balanceEconomico/filtradoGastos" method="get" class="col-sm-6">
                <label class="col-sm-1 col-sm-offset-2">Mes</label><select name="mes" id="mes" class="col-sm-3">
                	<c:forEach var="mes" items="${meses}">
                		<option id="mes" value="${mes}">${mes}</option>
        			</c:forEach>
                </select>
                
                <label class="col-sm-1">Año</label><select name="anyo" id="anyo" class="col-sm-2">
                	<c:forEach var="anyo" items="${anyos}">
                		<option id="anyo" value="${anyo}">${anyo}</option>
        			</c:forEach>
                </select>
                <div class="col-sm-1"></div>
           <button class="btn btn-success col-sm-2" type="submit">Filtrar</button>


     </form>
    
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
						<spring:url value="/pedidosRecambio/factura/${factura.id}" var="facturaRecambioUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(facturaRecambioUrl)}">
	                    	<span class="glyphicon glyphicon-eye-open"></span>
	                    </a>		        	
	                </td>
	                				
		        </tr>
      		</c:forEach>
        </tbody>
    </table>
</petclinic:layout>