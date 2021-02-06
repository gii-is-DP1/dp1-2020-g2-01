<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<petclinic:layout pageName="facturas">
<div class="col-sm-12" style="margin-bottom: 20px">
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Resumen de la factura ${factura.id}</h3>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de pago: </label>${factura.fechaPago}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Total: </label><p>${factura.precioTotal}€</p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Descuento: </label><p>${factura.descuento}%</p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Precio final: </label><p>${factura.precioConDescuento}€</p>
  </div>
</div>
<a href="/facturas/generarPDF/${factura.id}" class="btn btn-success col-sm-5" target="_blank"><span class="glyphicon glyphicon-save-file"></span> Descargar factura en PDF</a>
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

       </tr>
       </thead>
    <tbody>
		<c:forEach var="lineaFactura" items="${factura.lineaFactura}">
		<tr>
			<td>${lineaFactura.recambio.name}</td>
			<td>${lineaFactura.cantidad}</td>
			<td>${lineaFactura.descripcion}</td>
			<td>${lineaFactura.precioBase}€</td>
			<td>${lineaFactura.descuento}%</td>
			<td>${lineaFactura.precio}€</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</div>
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Horas</h3>
  </div>
	<table class="table table-striped">
       <thead>
       <tr>
      	   <th>Descripción</th>
      	   <th>Precio hora</th>
           <th>Horas</th>
           <th>Empleado</th>
           <th>Total</th>

       </tr>
       </thead>
    <tbody>
		<c:forEach var="horas" items="${factura.lineaFactura[0].reparacion.horasTrabajadas}">
		<tr>
			<td>${horas.trabajoRealizado}</td>
			<td>${horas.precioHora}€</td>
			<td>${horas.horasTrabajadas}</td>
			<td><c:out value="${horas.empleado.nombre} ${horas.empleado.apellidos}"/></td>
			<td>${horas.precioTotal}€</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
</div>
</div>
</div>
</petclinic:layout>