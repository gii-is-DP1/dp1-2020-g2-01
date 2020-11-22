<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vehiculos">
    
    
    
    <jsp:body>
        <h2>Cita</h2>

        

        <form:form action="/citas/save" modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
            <!-- A la hora de añadir la cita al vehículo se pondrán aquí los datos del vehiculo (un desplegable) -->
            
            	<petclinic:inputField label="vehiculo" name="vehiculo.id"/>
                <petclinic:inputField label="fecha" name="fecha"/> 
               	<petclinic:inputField label="hora" name="hora"/>
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