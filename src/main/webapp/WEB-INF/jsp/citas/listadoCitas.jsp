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
    
	
	<div class="col-sm-12">
	<div class="col-sm-10">
	<!-- No debe dejarte ver citas de otros clientes a menos que seas admin o empleado -->
    <div class="col-sm-2"><h2>Citas</h2></div>
    <div class="col-sm-3 col-sm-offset-5">
    <sec:authorize access="hasAuthority('admin')">
    <spring:url value="/citas/covid" var="citaUrl">
    </spring:url>
    <a class="btn btn-success" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-asterisk"></span> Cancelar citas por COVID</a>
	</sec:authorize>
	</div>
	<sec:authorize access="isAuthenticated()">
	<div class="col-sm-2">
	<sec:authentication property="name" var="username"/>
    <spring:url value="/citas/new/${username}" var="citaUrl">
    </spring:url>
    <a class="btn btn-success" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-plus"></span> Añadir cita</a>
    </div>
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
            <th></th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citas}" var="citas">
            <tr>
                
                <td>
                <p class="text-center" style="font-size: 65%; margin: 0">${citas.fecha.year}</p>
                <c:set var = "monthParsed" value = "${fn:substring(citas.fecha.month, 0, 3)}" />
                <p class = "text-center" style="color: DarkGray"><strong><small>${monthParsed}</small></strong></p>
                <p class="text-center"  style="margin: 0"><strong>${citas.fecha.dayOfMonth}</strong></p>
                </td>
                
                <td>
                <span class="helper"></span>
                   <p><strong>${citas.hora}:00</strong></p>
                </td>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                    <p><c:out value="${citas.vehiculo.cliente.nombre}"/></p>
                    <p><small><c:out value="${citas.vehiculo.cliente.apellidos}"/></small></p>
                </td>
				</sec:authorize>
            
                <td>
                    <p><c:out value="${citas.vehiculo.modelo}"/></p>
                    <p><small><c:out value="${citas.vehiculo.matricula}"/></small></p>
                </td>
                
                <td>
                <span class="helper"></span>
                   <p>
                   <c:set var="i" value="0"/>
                   <c:forEach items="${citas.tiposCita}" var="tipo">
                   <c:out value="${tipo.tipo}"/>
                   <c:set var="i" value="${i + 1}"/>
                   <c:if test="${ fn:length(citas.tiposCita) > i}">, </c:if>
                   </c:forEach>
                   </p>
                </td>
                
                <td>
                	<spring:url value="/citas/update/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${citas.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
                    	<span class="helper glyphicon glyphicon-pencil"></span>
                    </a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/citas/delete/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${citas.id}"/>
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
    </div>



</petclinic:layout>
