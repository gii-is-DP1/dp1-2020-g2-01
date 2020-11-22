<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vehiculos">
    
    
    
    <jsp:body>
        <h2>Vehiculo</h2>

        

        <form:form action="/vehiculos/save" modelAttribute="vehiculo" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="matricula" name="matricula"/>
                <petclinic:inputField label="modelo" name="modelo"/> 
               	<petclinic:inputField label="numero de bastidor" name="numBastidor"/>
               	<petclinic:selectField name="tipoVehiculo" label="tipo de vehículo" names="${types}" size="5"/>
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