<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<petclinic:layout pageName="addRecambios">
<div class="col-sm-12" style="margin-bottom: 20px">
<div class="col-sm-4">
<form method="get" action="/reparaciones/addRecambio/${reparacion.id}/buscar" class="form-horizontal">
	<input class="col-sm-8" type="text" id="busqueda" name="busqueda" placeholder="Nombre del recambio"/>
	<input type="submit" class="col-sm-4" value="Buscar">
</form>
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Recambios</h3>
  </div>
  <table class="table table-striped">
       <thead>
       <tr>
      	   <th>Recambio</th>
      	   <th>Stock</th>
           <th>Tipo</th>
           <th></th>
       </tr>
       </thead>
    <tbody>
    <c:forEach items="${recambios}" var="recambio">
    <c:if test="${recambio.cantidadActual != 0}">
    
    	<tr>
    	<td>
    	<c:out value="${recambio.name}"></c:out></br><a target="_blank" href="/recambios/solicitud/new/${recambio.id}/${reparacion.id}">Solicitar</a>
    	</td>
    	<td>
    	<c:out value="${recambio.cantidadActual}"></c:out>
    	</td>
    	<td>
    	<c:out value="${recambio.tipoVehiculo.tipo}"></c:out>
    	</td>
    	<td>
    	<a href="/reparaciones/addRecambio/${reparacion.id}/${recambio.id}" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span></a>
    	</td>	
    </tr>
    </c:if>
    </c:forEach>
    </tbody>
  </table>
</div>
</div>
<div class="col-sm-8">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Líneas de factura</h3>
  </div>
	<table class="table table-striped">
       <thead>
       <tr>
      	   <th>Recambio</th>
           <th>Descripcion</th>
      	   <th>Cantidad</th>
           <th>Precio base</th>
           <th>Descuento</th>
           <th>Precio final</th>
           <th></th>
           <th></th>

       </tr>
       </thead>
    <tbody>
		<c:forEach var="ln" items="${reparacion.lineaFactura}">
		<tr>
		<c:choose>
		<c:when test="${ln.id == edit}">
		<form:form action="/reparaciones/addRecambio/${reparacion.id}/${ln.id}" modelAttribute="lineaFactura" class="form-horizontal">
			<td>${ln.recambio.name}</td>
			<td><input type="text" name="descripcion" value="${ln.descripcion}"/></td>
			<td><input class="col-sm-12" type="text" name="cantidad" value="${ln.cantidad}"/>/${ln.recambio.cantidadActual+ln.cantidad}</td>
			<td><input class="col-sm-12" type="text" name="precioBase" value="${ln.precioBase}"/>€</td>
			<td><input class="col-sm-12" type="text" name="descuento" value="${ln.descuento}"/>%</td>
			<td>${ln.precio}€</td>
			
			<td><button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-ok"></span></button></td>
			<td>
			<a href="/reparaciones/addRecambio/${reparacion.id}" class="btn btn-danger"><span class="glyphicon glyphicon-ban-circle"></span></a>
			</td>
			<input type="hidden" name="recambio" value="${ln.recambio.name}"/>
			<input type="hidden" name="reparacion" value="${ln.reparacion.id}">
			
		</form:form>
		</c:when>
		<c:otherwise>
			<td>${ln.recambio.name}</br><a target="_blank" href="/recambios/solicitud/new/${recambio.id}/${reparacion.id}">Solicitar</a></td>
			<td>${ln.descripcion}</td>
			<td>${ln.cantidad}/${ln.recambio.cantidadActual+ln.cantidad}</td>
			<td>${ln.precioBase}€</td>
			<td>${ln.descuento}%</td>
			<td>${ln.precio}€</td>
			<td><a href="/reparaciones/editRecambio/${reparacion.id}/${ln.id}" class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></a></td>
			<td><a href="/reparaciones/deleteRecambio/${reparacion.id}/${ln.id}" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span></a></td>
		</c:otherwise>
		</c:choose>
		
		</tr>
		</c:forEach>
	</tbody>
	</table>
</div>
<a href="/reparaciones/getReparacion/${reparacion.id}" class="col-sm-offset-9 col-sm-3 btn btn-success"><span class="glyphicon glyphicon-circle-arrow-left"></span> Volver a la reparación</a>
</div>

</div>
</petclinic:layout>