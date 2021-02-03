<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>



<petclinic:layout pageName="citas">
	<style>
    .helper {
		display: inline-block;
		height: 100%;  	
    	vertical-align:middle;
		}
    </style>
    <script>
    $(function () {
    	  $('[data-toggle="popover"]').popover()
    })
    </script>
	
	<div class="col-sm-12">
    <div class="col-sm-2"><h2>Citas</h2></div>
    <sec:authorize access="hasAuthority('admin')">
    <spring:url value="/citas/covid" var="citaUrl">
    </spring:url>
    <a class="btn btn-success col-sm-offset-7 col-sm-3" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-asterisk"></span> Cancelar citas por COVID</a>
	</sec:authorize>
	<sec:authorize access="hasAuthority('cliente')">
    <spring:url value="/citas/new" var="citaUrl">
    </spring:url>
    <a class="btn btn-success col-sm-offset-8 col-sm-2" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-plus"></span> Añadir cita</a>

    </sec:authorize>
    <div class="col-sm-12" style="height:5px"></div>
    <table id="citasTable" class="table table-striped">
        <thead>
        <tr>
            <th class = "text-center">Fecha</th>
            <th>Hora</th>
        	<sec:authorize access="hasAuthority('admin')">
                <th>Nombre</th>
			</sec:authorize>
            <th>Modelo</th>
            <th>Tipo de cita</th>
			<th>Taller</th>
        	<sec:authorize access="hasAuthority('admin')">
                <th></th>
			</sec:authorize>
			<sec:authorize access="hasAuthority('admin')">
                <th></th>
			</sec:authorize>
            <th></th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citas}" var="cita">
            <tr>
                
                <td>
                <p class="text-center" style="font-size: 65%; margin: 0">${cita.fecha.year}</p>
                <c:set var = "monthParsed" value = "${fn:substring(cita.fecha.month, 0, 3)}" />
                <p class = "text-center" style="color: DarkGray"><strong><small>${monthParsed}</small></strong></p>
                <p class="text-center"  style="margin: 0"><strong>${cita.fecha.dayOfMonth}</strong></p>
                </td>
                
                <td>
                <span class="helper"></span>
                   <p><strong>${cita.hora}:00</strong></p>
                </td>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                    <c:out value="${cita.vehiculo.cliente.nombre}"/>
	               		<spring:url value="/clientes/clienteDetails/{username}" var="clienteUrl">
                        <spring:param name="username" value="${cita.vehiculo.cliente.user.username}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(clienteUrl)}">
                    	<span class="glyphicon glyphicon-eye-open"></span></a>
                    <p><small><c:out value="${cita.vehiculo.cliente.apellidos}"/></small></p>
                </td>
				</sec:authorize>
            
                <td>
                    <p><c:out value="${cita.vehiculo.modelo}"/></p>
                    <p><small><c:out value="${cita.vehiculo.matricula}"/></small></p>
                </td>
                
                <td>
                <span class="helper"></span>
                   <p>
                   <c:set var="i" value="0"/>
                   <c:forEach items="${cita.tiposCita}" var="tipo">
                   <c:out value="${tipo.tipo}"/>
                   <c:set var="i" value="${i + 1}"/>
                   <c:if test="${ fn:length(cita.tiposCita) > i}">, </c:if>
                   </c:forEach>
                   </p>
                </td>
                <td>
                <span class="helper"></span>
                	<p><c:out value="${cita.taller.ubicacion}"/></p>
                </td>
                <sec:authorize access="hasAuthority('admin')">
                <td>
					<sec:authentication property="name" var="username"/>
					<c:set var="empleadoEnCita" value="false"/>
					<c:forEach var="empleado" items="${cita.empleados}">
						<c:if test="${empleado.usuario.username == username}">
							<c:set var="empleadoEnCita" value="true"/>							
						</c:if>
					</c:forEach>
					<c:choose>
						<c:when test="${empleadoEnCita}">
							<strong>Atiendes esta cita</strong></br>
							<a href="/citas/noAtender/${cita.id}">No puedo atender</a>	
						</c:when>
						<c:otherwise>
							<a href="/citas/atender/${cita.id}">Atender la cita</a>
						</c:otherwise>
					</c:choose>
                </td>
				</sec:authorize>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                	<a href="/reparaciones/new/${cita.id}">Crear reparación</a>
                </td>
                </sec:authorize>
                <td>
                	<spring:url value="/citas/update/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${cita.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
                    	<span class="helper glyphicon glyphicon-pencil"></span>
                    </a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/citas/confirmDelete/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${cita.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
						<span class="helper glyphicon glyphicon-trash"></span>
					</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>



</petclinic:layout>
