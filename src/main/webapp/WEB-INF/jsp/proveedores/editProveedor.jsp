<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="proveedores">
    
    
    
    <jsp:body>
        <h2>Proveedor</h2>

        

        <form:form action="/proveedores/save" modelAttribute="proveedor" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="name"/>
                <petclinic:inputField label="NIF" name="nif"/> 
               	<petclinic:inputField label="Tel�fono" name="telefono"/>
               	<petclinic:inputField label="Email" name="email"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${proveedor.id}"/>
                    <button class="btn btn-default" type="submit">Guardar proveedor</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>