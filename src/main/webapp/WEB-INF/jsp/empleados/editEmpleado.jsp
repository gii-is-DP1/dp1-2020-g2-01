<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="empleados">
    
    
    
    <jsp:body>
        <h2>Empleado</h2>

        

        <form:form action="/empleados/save" modelAttribute="empleado" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="nombre" name="nombre"/>
                <petclinic:inputField label="apellidos" name="apellidos"/> 
               	<petclinic:inputField label="dni" name="dni"/>
               	<petclinic:inputField label="fecha de nacimiento" name="fechaNacimiento"/>
               	<petclinic:inputField label="correo" name="correo"/>
               	<petclinic:inputField label="telefono" name="telefono"/>
               	<petclinic:inputField label="fecha inicio contrato" name="fecha_ini_contrato"/>
               	<petclinic:inputField label="fecha fin contrato" name="fecha_fin_contrato"/>
               	<petclinic:inputField label="numero seguridad social" name="num_seg_social"/>
               	<petclinic:inputField label="sueldo" name="sueldo"/> 	
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${empleado.id}"/>
                    <button class="btn btn-default" type="submit">Guardar empleado</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>