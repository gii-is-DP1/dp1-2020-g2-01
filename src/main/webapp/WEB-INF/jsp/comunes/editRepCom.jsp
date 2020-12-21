<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="reparacionesComunes">
    
    <jsp:body>
    
    	
    	
        <h2>Reparación común</h2>

        

        <form:form action="/reparacionesComunes/save" modelAttribute="repCom" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="nombre"/>
               	<petclinic:inputField label="Descripción" name="descripcion"/>
            </div>  	

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${repCom.id}"/>
                    <button class="btn btn-default" type="submit">Guardar reparación común</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>