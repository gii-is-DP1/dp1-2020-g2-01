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
        <h2>
        <c:if test="${cita['new']}">Añadir </c:if> <c:if test="${ not cita['new']}">Editar </c:if> cita
    	</h2>

        

        <form:form modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
              
            	<petclinic:selectVehiculo label="Vehículos" name="vehiculo" items="${vehiculos}"/>
            	
                <!-- <petclinic:inputField label="Fecha" name="fecha"/> 
               	<petclinic:inputField label="Hora" name="hora"/> -->
               	
               	<petclinic:selectFecha label="Fecha" items="${vehiculos}"/>
               	
               	<petclinic:selectTipoCita label="Tipo de cita" name="tipoCita" items="${tipos}"/>
                <input type="hidden" name="id" value="${cita.id}"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                    <c:when test="${cita['new']}">
                        <button class="btn btn-default" type="submit">Añadir cita</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Editar cita</button>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>