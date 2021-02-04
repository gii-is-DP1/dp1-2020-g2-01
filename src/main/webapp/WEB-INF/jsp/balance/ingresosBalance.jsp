<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>




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


    <h2>Ingresos</h2>
    
    <a href="/balanceEconomico?gastos=true" class="btn btn-success">Ver gastos</a>
    
    <form action="/balanceEconomico/filtrado" method="get" class="form-horizontal">
            <div class="form-group has-feedback">
                <label>Mes</label><select name="mes" id="mes">
                	<c:forEach var="mes" items="${meses}">
                		<option value="${mes}">${mes}</option>
        			</c:forEach>
                </select>
                
                <label>Año</label><select name="anyo" id="anyo">
                	<c:forEach var="anyo" items="${anyos}">
                		<option value="${anyo}">${anyo}</option>
        			</c:forEach>
                </select>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Filtrar</button>
                </div>
            </div>
     </form>
    
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
		        		<c:out value="${factura.precioConDescuento} €" />		        	
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
