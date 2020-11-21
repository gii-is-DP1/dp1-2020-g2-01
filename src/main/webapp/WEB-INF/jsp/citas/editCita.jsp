<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vehiculos">
    
    
    
    <jsp:body>
        <h2>Cita</h2>

        

        <form:form action="/cita/save" modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="vehiculo" name="vehiculo"/>
                <petclinic:inputField label="fecha" name="fecha"/> 
               	<petclinic:inputField label="hora" name="hora"/>
               	<petclinic:inputField label="tipoCita" name="tipoCita"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${cita.id}"/>
                    <button class="btn btn-default" type="submit">Guardar cita</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>