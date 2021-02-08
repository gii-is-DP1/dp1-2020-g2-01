<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<petclinic:layout pageName="pedidosRecambio">
    <h2>Pedidos de los Recambios</h2>
    
    <a class="btn btn-success" href="/pedidosRecambio/new"><span class="glyphicon glyphicon-plus"></span> Crear un nuevo pedido</a>
    <table id="pedidosRecambioTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Nombre</th>
            <th>Precio Unitario</th>
            <th>Cantidad</th>
            <th>Precio Total</th>
            <th>¿Ha llegado?</th>
            <th>Factura</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pedidoRecambio}" var="pedidoRecambio">
            <tr>
               <td>
                    <c:out value="${pedidoRecambio.id}"/>
                </td>
                
            
                <td>
                    <c:out value="${pedidoRecambio.recambio.name}"/>
                </td>
                

                
                <td>
                   <c:out value="${pedidoRecambio.precioPorUnidad}"/>
                </td>
                
                <td>
                   <c:out value="${pedidoRecambio.cantidad}"/>
                </td>
                
                
                <td>
                   <c:out value="${pedidoRecambio.precio}"/>
                </td>
               
                <td>
                	<c:if test="${pedidoRecambio.seHaRecibido == false}">
	                	<spring:url value="/recambios/update/{id}" var="pedidoRecambioUrl">
	                        <spring:param name="id" value="${pedidoRecambio.id}"/>
	                    </spring:url>
                    	<a href="${fn:escapeXml(pedidoRecambioUrl)}"><span class="glyphicon glyphicon-ok"></span></a>
                    </c:if>
                    <c:if test="${pedidoRecambio.seHaRecibido == true}">
                    	<p> Entregado </p>
                    </c:if>
                </td>
                
                
                 <td>
                	<spring:url value="/pedidosRecambio/factura/${pedidoRecambio.facturaRecambio.id}" var="pedidoRecambioUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(pedidoRecambioUrl)}">
	                    	<span class="glyphicon glyphicon-eye-open"></span>
	                    </a>
                </td>
                
                <td>
                	<spring:url value="/pedidosRecambio/delete/${pedidoRecambio.id}" var="pedidoRecambioUrl">
	                    </spring:url>
	                    <a href="${fn:escapeXml(pedidoRecambioUrl)}">
	                    	<span class="glyphicon glyphicon-trash"></span>
	                    </a>
                </td>
            </tr>
          
            
        </c:forEach>
        </tbody>
    </table>
    


</petclinic:layout>