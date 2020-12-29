<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reparaciones">
    <h2>Facturas</h2>

    <table id="FacturasTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Número de factura</th>
            <th>Cliente</th>
            <th>Reparaciones</th>
            <th>Fecha de Pago</th>
            <th>Cantidad a Pagar</th>
            <th></th>
            <th></th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reparaciones}" var="reparacion">
            <tr>
               <td>
                    <c:out value="${reparacion.id}"/>
                </td>

                <td>
                    <c:out value="${reparacion.name}"/>
                </td>

                <td>
                   <c:out value="${reparacion.descripcion}"/>
                </td>


                <td>
                   <c:out value="${reparacion.tiempoEstimado}"/>
                </td>

                <td>
                   <c:out value="${reparacion.fechaFinalizacion}"/>
                </td>

                <td>
                   <c:out value="${reparacion.fechaEntrega}"/>
                </td>

                <td>
                   <c:out value="${reparacion.fechaRecogida}"/>
                </td>

                <td>
                  <c:forEach items="${reparacion.empleados}" var="empleado">
                  	<c:out value="${empleado.nombre}"/>
                  	<c:out value=" "/>
                  	<c:out value="${empleado.apellidos}"/>
                  	<br/>
                  </c:forEach>
                </td>

               	<td>
                  <c:out value="${reparacion.cita.vehiculo.cliente.nombre}"/>
                  <c:out value=" "/>
                  <c:out value="${reparacion.cita.vehiculo.cliente.apellidos}"/>
                </td>

                <td>
                  <c:out value="${reparacion.cita.vehiculo.modelo}"/>
                </td>



                <td>
                	<spring:url value="/reparaciones/delete/{reparacionId}" var="reparacionUrl">
                        <spring:param name="reparacionId" value="${reparacion.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(reparacionUrl)}">Delete</a>

                </td>


                 <td>
                	<spring:url value="/reparaciones/update/{reparacionId}" var="reparacionUrl">
                        <spring:param name="reparacionId" value="${reparacion.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(reparacionUrl)}">Update</a>

                </td>



            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="/reparaciones/new">Crear una nueva reparación</a>


</petclinic:layout>