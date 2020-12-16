<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="reparaciones">
     <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaFinalizacion").datepicker({dateFormat: 'dd/mm/yy'});
                $("#tiempoEstimado").datepicker({dateFormat: 'dd/mm/yy'});
                $("#fechaEntrega").datepicker({dateFormat: 'dd/mm/yy'});
                $("#fechaRecogida").datepicker({dateFormat: 'dd/mm/yy'});
                
            });
        </script>
    </jsp:attribute>
    
    
    <jsp:body>
        <h2>Reparación</h2>


        <form:form action="/reparaciones/save" modelAttribute="reparacion" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Descripción" name="descripcion"/> 
                <petclinic:inputField label="Tiempo estimado" name="tiempoEstimado"/> 
               	<petclinic:inputField label="Fecha de finalización" name="fechaFinalizacion"/> 
               	<petclinic:inputField label="Fecha de entrega" name="fechaEntrega"/> 
               	<petclinic:inputField label="Fecha de recogida" name="fechaRecogida"/>
               	<petclinic:selectField label="Cita asociada" name="cita" size="10" names="${citas}"/>
               	<petclinic:selectFieldMultiple label="Empleados asignados (usar CTRL para seleccionar varios)" 
               	name="empleados" size="10"  names="${empleados}"/>

            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${reparacion.id}"/>
                    <button class="btn btn-default" type="submit">Guardar reparación</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>