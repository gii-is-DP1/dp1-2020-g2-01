<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="vehiculos">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'dd/mm/yy'});
            });
        </script>
    </jsp:attribute>
    
    
    <jsp:body>
        <h2>Cita</h2>

        

        <form:form action="/citas/save" modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
              
            	<petclinic:selectVehiculo label="Vehículos" name="vehiculo" items="${vehiculos}"/>
            	
                <petclinic:inputField label="Fecha" name="fecha"/> 
               	<petclinic:inputField label="Hora" name="hora"/>
               	
               	<petclinic:selectTipoCita label="Tipo de cita" name="tipoCita" items="${tipos}"/>
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