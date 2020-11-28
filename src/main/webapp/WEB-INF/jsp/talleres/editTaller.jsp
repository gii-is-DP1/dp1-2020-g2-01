<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="talleres">
    
    
    
    <jsp:body>
        <h2>Taller</h2>

        

        <form:form action="/talleres/save" modelAttribute="taller" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="Teléfono" name="telefono"/> 
               	<petclinic:inputField label="Correo" name="correo"/>
               	<petclinic:inputField label="Ubicación" name="ubicacion"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${taller.id}"/>
                    <button class="btn btn-default" type="submit">Guardar taller</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>