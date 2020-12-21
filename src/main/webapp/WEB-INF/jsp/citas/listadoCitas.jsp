<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="citas">

    <h2>Citas</h2>
    

	<!-- No debe dejarte ver citas de otros clientes a menos que seas admin o empleado -->
	<sec:authorize access="isAuthenticated()">
	<sec:authentication property="name" var="username"/>
    <spring:url value="/citas/new/${username}" var="citaUrl">
    </spring:url>
    <a class="btn btn-success" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-plus"></span> A�adir cita</a>
    </sec:authorize>
    <sec:authorize access="hasAuthority('admin')">
    <div style="float:right">
    <spring:url value="/citas/covid" var="citaUrl">
    </spring:url>
    <a class="btn btn-success" href="${fn:escapeXml(citaUrl)}"><span class="glyphicon glyphicon-asterisk"></span> Cancelar citas por COVID</a>
	</div>
	</sec:authorize>
    <table id="citasTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
        	
        	
        	<sec:authorize access="hasAuthority('admin')">
                <th>Nombre</th>
			</sec:authorize>
        	<sec:authorize access="hasAuthority('admin')">
                <th>Apellidos</th>
			</sec:authorize>
            <th>Modelo</th>
            <th>Fecha</th>
            <th>Hora</th>
            <th>Tipo de cita</th>
            <th></th>
            <th></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citas}" var="citas">
            <tr>
               <td>
                    <c:out value="${citas.id}"/>
                </td>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                    <c:out value="${citas.vehiculo.cliente.nombre}"/>
                </td>
				</sec:authorize>
                <sec:authorize access="hasAuthority('admin')">
                <td>
                    <c:out value="${citas.vehiculo.cliente.apellidos}"/>
                </td>
				</sec:authorize>
            
                <td>
                    <c:out value="${citas.vehiculo.modelo}"/>
                </td>
                
                <td>
                   <c:out value="${citas.fecha}"/>
                </td>
                
                <td>
                   <c:out value="${citas.hora}"/>
                </td>
                
                <td>
                   <c:out value="${citas.tipoCita.tipo}"/>
                </td>
                
                <td>
                	<spring:url value="/citas/update/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${citas.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
                    	<span class="glyphicon glyphicon-pencil"></span>
                    </a>
                
                </td>
                
                
                 <td>
                	<spring:url value="/citas/delete/{citaId}" var="citaUrl">
                        <spring:param name="citaId" value="${citas.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(citaUrl)}">
						<span class="glyphicon glyphicon-trash"></span>
					</a>
                
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>



</petclinic:layout>
