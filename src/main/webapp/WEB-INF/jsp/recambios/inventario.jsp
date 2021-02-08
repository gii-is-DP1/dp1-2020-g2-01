<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="recambios">
    <h2 class="col-sm-2">Inventario</h2>
        	
		<!-- <a href="/solicitud/new" class="btn btn-success">Nueva solicitud de producto</a> -->
		
    	<sec:authorize access="hasAuthority('admin')">
		<a href="/recambios/listadoRecambiosSolicitados" class="btn btn-success col-sm-offset-8 col-sm-2">Registro de solicitudes</a>
		</sec:authorize>
    <div class="col-sm-12" style="height:5px"></div>
    <table id="inventarioTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Nombre</th>
            <th>Tipo de vehículo</th>
            <th>Cantidad actual</th>
            <th>Proveedor</th>
            <th></th>


        </tr>
        </thead>
        <tbody>
        <c:forEach items="${recambios}" var="recambio">
            <tr>
               <td>
                    <c:out value="${recambio.name}"/>
                </td>
            
                <td>
                    <c:out value="${recambio.tipoVehiculo.tipo}"/>
                </td>
                
                <td>
                   <c:out value="${recambio.cantidadActual}"/>
                </td>
                <td>
                   <c:out value="${recambio.proveedor.name}"/>
                </td>
                <td><a href="/recambios/solicitud/new/${recambio.id}" class="btn btn-success">
	                    	<span class="helper glyphicon glyphicon-plus"></span> Solicitar</a></td>
                

              
       			
                
            </tr>
        </c:forEach>
        </tbody>
    </table>


</petclinic:layout>
