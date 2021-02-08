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
    <h3 class="panel-title">Resumen de la factura de recambios ${factura.id}</h3>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de pago: </label>${factura.fechaPago}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Precio total: </label><p>${factura.precioTotal}€</p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Recambio: </label><p>${factura.pedidoRecambio.recambio}</p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Cantidad: </label><p>${factura.pedidoRecambio.cantidad}</p>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Precio por unidad: </label><p>${factura.pedidoRecambio.precioPorUnidad}€</p>
  </div>
   <div class="panel-body">
    <label class="col-sm-4">Proveedor: </label><p>${factura.pedidoRecambio.proveedor.name}</p>
  </div>
</div>
</div>
</petclinic:layout>