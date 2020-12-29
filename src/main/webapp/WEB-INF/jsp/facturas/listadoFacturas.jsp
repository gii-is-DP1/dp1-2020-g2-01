<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="facturas">
    <h2>Facturas</h2>

    <table id="facturasTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Número de Factura </th>
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
          			<c:out value="${factura.id}"/>

                <td>
                    <c:out value="${factura.fechaPago}"/>

                </td>

                  <td>
                    <c:out value="${factura.precioTotal}"/>
                    <span class="glyphicon glyphicon-euro" style="font-size: 12px"> </span>
                </td>

                 <td>
                    <c:out value="${factura.porcentajeDescuento}"/>
                </td>

                <td>
                	<c:out value="${factura.precioConDescuento}"/>
                    <span class="glyphicon glyphicon-euro" style="font-size: 12px"> </span>
                </td>


                <td>
                	<spring:url value="/facturas/info/{facturaId}" var="facturaUrl">
                        <spring:param name="facturaId" value="${factura.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(facturaUrl)}">Más Información</a>

                </td>



            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>