<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="facturas">
    <h2>Facturas</h2>
    
    <td>
    	<spring:url value="/facturas/new" var="FacturasUrl">
        </spring:url>
       	<a href="${fn:escapeXml(FacturasUrl)}">Añadir facturas</a>
                
    </td>
    
    <table id="facturasTable" class="table table-striped">
        <thead>
        <tr>
            <th>Fecha de Pago</th>
            <th>Precio</th>
            <th>Descuento</th>
            <th>Precio Total</th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${facturas}" var="factura">
            <tr>
          
            
                <td>
                    <c:out value="${factura.fechaPago}"/>
                </td>
                
                  <td>
                    <c:out value="${factura.precioTotal}"/>
                </td>
                
                 <td>
                    <c:out value="${factura.porcentajeDescuento}"/>
                </td>
                
                <td>
                	<c:out value="${factura.precioConDescuento}"/>
                </td>
                
           
                <td>
                	<spring:url value="/facturas/delete/{facturaId}" var="facturaUrl">
                        <spring:param name="facturaId" value="${factura.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(facturaUrl)}">Delete</a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/facturas/update/{facturaId}" var="facturaUrl">
                        <spring:param name="facturaId" value="${factura.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(facturaUrl)}">Update</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
