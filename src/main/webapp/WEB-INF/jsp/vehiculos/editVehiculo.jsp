<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="vehiculos">
    
    
    
    <jsp:body>
        <h2>Vehiculo</h2>

        <form:form action="/vehiculos/save" modelAttribute="vehiculo" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Matricula" name="matricula"/>
                <petclinic:inputField label="Modelo" name="modelo"/> 
               	<petclinic:inputField label="Numero de bastidor" name="numBastidor"/>
               	<petclinic:selectTipo label="Tipo de vehículo" name="tipoVehiculo" items="${types}"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${vehiculo.id}"/>
                    <button class="btn btn-default" type="submit">Guardar vehiculo</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>