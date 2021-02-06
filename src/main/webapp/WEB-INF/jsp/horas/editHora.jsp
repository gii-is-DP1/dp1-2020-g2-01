<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<petclinic:layout pageName="horas">
    
    
    
    <jsp:body>
        <h2>Horas</h2>
        <form:form action="/horas/save/${reparacion.id}" modelAttribute="horaTrabajada" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Trabaja realizado" name="trabajoRealizado"/>
                <petclinic:inputField label="Horas Trabajadas" name="horasTrabajadas"/> 
               	<petclinic:inputField label="Precio hora" name="precioHora"/>
               	<petclinic:select label="Empleado" name="empleado" items="${empleados}"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${horaTrabajada.id}"/>
                    <button class="btn btn-default" type="submit">Añadir horas</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>