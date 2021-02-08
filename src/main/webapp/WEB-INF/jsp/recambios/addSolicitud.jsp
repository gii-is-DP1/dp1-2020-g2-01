<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>



<petclinic:layout pageName="solicitudes">
    
    
    
    <jsp:body>
        <h2>Solicitud de recambio</h2>

        <form:form action="/recambios/solicitud/save" modelAttribute="solicitud" class="form-horizontal">
            <div class="form-group has-feedback">
            	
            	<input type="hidden" name="terminada" value="false">
           		
           		<c:set var="readOnlyRecambio" value="${empty readOnlyRecambio ? 'false' : readOnlyRecambio}"></c:set>
               	<c:if test="${readOnlyRecambio}"><petclinic:inputField label="Recambio que se solicita" name="recambio" readonly="true" /></c:if>
               	<c:if test="${not readOnlyRecambio}"><petclinic:select label="Recambio que se solicita" name="recambio" items="${recambios}"/></c:if>
               	
                <petclinic:inputField label="Cantidad" name="cantidad"/>
                
                <c:set var="readOnlyEmpleado" value="${empty readOnlyEmpleado ? 'false' : readOnlyEmpleado}"></c:set>
               	<c:if test="${readOnlyEmpleado}"><petclinic:inputField label="Empleado que realiza la petición" name="empleado" readonly="true" /></c:if>
               	<c:if test="${not readOnlyEmpleado}"><petclinic:select label="Empleado que realiza la petición" name="empleado" items="${empleados}"/></c:if>
               	
               	<c:set var="readOnlyReparacion" value="${empty readOnlyReparacion ? 'false' : readOnlyReparacion}"></c:set>
               	<c:if test="${readOnlyReparacion}"><petclinic:inputField label="Reparación para que se solicita" name="reparacion" readonly="true" /></c:if>
               	<c:if test="${not readOnlyReparacion}"><petclinic:select label="Reparación para que se solicita" name="reparacion" items="${reparaciones}"/></c:if>
               	
               	
               	

            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${solicitud.id}"/>
                    <button class="btn btn-default" type="submit">Guardar solicitud</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>