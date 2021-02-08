<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="administrador">
    
    
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaNacimiento").datepicker({dateFormat: 'dd/mm/yy'});
            });
        </script>
       </jsp:attribute>
    <jsp:body>
    
    	
    	
        <h2>Administrador</h2>

        

        <form:form action="/administradores/save" modelAttribute="administrador" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="nombre"/>
                <petclinic:inputField label="Apellidos" name="apellidos"/> 
               	<petclinic:inputField label="DNI" name="dni"/>
               	<petclinic:inputField label="Fecha de nacimiento" name="fechaNacimiento"/>
               	<petclinic:inputField label="E-mail" name="email"/>
               	<petclinic:inputField label="Teléfono" name="telefono"/>
               	<petclinic:inputField label="Número seguridad social" name="num_seg_social"/>

               	<br>
            	<c:choose>
                    <c:when test="${administrador['new']}">
                       <petclinic:inputField label="Usuario" name="usuario.username"/>
                       <petclinic:inputField label="Contraseña" name="usuario.password" type="password"/>
                    </c:when>
                    <c:otherwise>
                    	<petclinic:inputField label="Usuario" name="usuario.username" type="text" readonly="true"/>
                    </c:otherwise>
             	</c:choose>
        	</div>
        	<div class="form-group">
            	<div class="col-sm-offset-2 col-sm-10">
            		<input type="hidden" name="id" value="${administrador.id}">
	                
	                        <button class="btn btn-default" type="submit">Actualizar administrador</button>
	                    </div>
        	</div>
        </form:form>

    </jsp:body>

</petclinic:layout>