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
            	
            	
           		<petclinic:radioBoolField label="¿Está terminada?" name="terminada" />
                <petclinic:inputField label="Cantidad" name="cantidad"/>
                <petclinic:select label="Recambio que se solicita" name="recambio" items="${recambios}"/>
               	<petclinic:select label="Empleado que realiza la petición" name="empleado" items="${empleados}"/>
               	

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