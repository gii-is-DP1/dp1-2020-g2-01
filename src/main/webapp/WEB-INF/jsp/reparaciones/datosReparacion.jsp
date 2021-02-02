<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="reparaciones">
<div class="col-sm-12" style="margin-bottom: 20px">
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Datos de la reparación<c:if test="${not empty reparacion.fechaFinalizacion}"> - Finalizada</c:if></h3>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Descripcion: </label>${reparacion.descripcion}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Cliente: </label><p>${reparacion.cita.vehiculo.cliente.nombre} </p>
    <p><small>${reparacion.cita.vehiculo.cliente.apellidos}</small></p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Cita: </label><p>
        <fmt:parseDate value="${reparacion.cita.fecha}" pattern="yyyy-MM-dd" 
	                             var="parsedDate" type="date" />
	
		<fmt:formatDate value="${parsedDate}" var="fecha" 
	                              type="date" pattern="dd/MM/yyyy" />
	    ${fecha}, 
	    <c:set var="i" value="0"/>
	    <c:forEach items="${reparacion.cita.tiposCita}" var="tipo">
	    <c:out value="${tipo.tipo}"/>
	    <c:set var="i" value="${i + 1}"/>
	    <c:if test="${ fn:length(reparacion.cita.tiposCita) > i}">, </c:if>
	    </c:forEach>
    </p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Empleados: </label>
    <p>
	    <c:set var="i" value="0"/>
	    <c:forEach items="${reparacion.empleados}" var="empleado">
	    <c:out value="${empleado.nombre} ${empleado.apellidos}"/>
	    <c:set var="i" value="${i + 1}"/>
	    <c:if test="${ fn:length(reparacion.empleados) > i}">, </c:if>
	    </c:forEach>
    </p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha estimada finalización: </label>
    <fmt:parseDate value="${reparacion.tiempoEstimado}" pattern="yyyy-MM-dd" 
                             var="parsedDate" type="date" />

	<fmt:formatDate value="${parsedDate}" var="fecha" 
                              type="date" pattern="dd/MM/yyyy" />
    ${fecha}
  </div>
    <div class="panel-body">
    <label class="col-sm-4">Fecha de finalización: </label>
    <fmt:parseDate value="${reparacion.fechaFinalizacion}" pattern="yyyy-MM-dd" 
                             var="parsedDate" type="date" />

	<fmt:formatDate value="${parsedDate}" var="fecha" 
                              type="date" pattern="dd/MM/yyyy" />
    ${fecha}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de entrega: </label>
    <fmt:parseDate value="${reparacion.fechaEntrega}" pattern="yyyy-MM-dd" 
                             var="parsedDate" type="date" />

	<fmt:formatDate value="${parsedDate}" var="fecha" 
                              type="date" pattern="dd/MM/yyyy" />
    ${fecha}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de recogida: </label>
    <fmt:parseDate value="${reparacion.fechaRecogida}" pattern="yyyy-MM-dd" 
                             var="parsedDate" type="date" />

	<fmt:formatDate value="${parsedDate}" var="fecha" 
                              type="date" pattern="dd/MM/yyyy" />
    ${fecha}
  </div>
</div>
<c:if test="${empty reparacion.fechaFinalizacion}"><a href="/reparaciones/update/${reparacion.id}" class="btn btn-success col-sm-4">Editar reparación</a>
<a href="/reparaciones/finalizar/${reparacion.id}" class="btn btn-success col-sm-offset-6 col-sm-2">Finalizar</a></c:if>
<c:if test="${not empty reparacion.fechaFinalizacion and not empty reparacion.lineaFactura[0].factura.id}"><a href="/facturas/ver/${reparacion.lineaFactura[0].factura.id}" class="btn btn-success col-sm-4">Ver factura</a></c:if>
<c:if test="${not empty reparacion.fechaFinalizacion and empty reparacion.lineaFactura[0].factura.id}"><a href="/facturas/generar/${reparacion.id}" class="btn btn-success col-sm-4">Generar Factura</a></c:if>
</div>
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Líneas de factura</h3>
  </div>
	<table class="table table-striped">
       <thead>
       <tr>
      	   <th>Recambio</th>
      	   <th>Cantidad</th>
           <th>Descripcion</th>
           <th>Precio base</th>
           <th>Descuento</th>
           <th>Precio final</th>
           <c:if test="${empty reparacion.fechaFinalizacion}"><th></th>
           <th></th></c:if>

       </tr>
       </thead>
    <tbody>
		<c:forEach var="lineaFactura" items="${reparacion.lineaFactura}">
		<tr>
			<td>${lineaFactura.ejemplarRecambio.recambio.name}</td>
			<td>${lineaFactura.cantidad}</td>
			<td>${lineaFactura.descripcion}</td>
			<td>${lineaFactura.precioBase}<span class="glyphicon glyphicon-eur"></span></td>
			<td>${lineaFactura.descuento}%</td>
			<td>${lineaFactura.precio}<span class="glyphicon glyphicon-eur"></span></td>
			<c:if test="${empty reparacion.fechaFinalizacion}"><td><span class="glyphicon glyphicon-pencil"></span></td>
			<td><span class="glyphicon glyphicon-trash"></span></td></c:if>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</div>
<c:if test="${empty reparacion.fechaFinalizacion}"><a href="/reparaciones/addRecambio" class="btn btn-success col-sm-offset-8 col-sm-4">Añadir recambios</a></c:if>

</div>
</div>
</petclinic:layout>