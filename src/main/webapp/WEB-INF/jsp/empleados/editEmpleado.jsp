<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="empleados">
    
    
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaNacimiento").datepicker({dateFormat: 'dd/mm/yy'});
                $("#fecha_ini_contrato").datepicker({dateFormat: 'dd/mm/yy'});
                $("#fecha_fin_contrato").datepicker({dateFormat: 'dd/mm/yy'});
            });
        </script>
       </jsp:attribute>
    <jsp:body>
    
    	
    	
        <h2>Empleado</h2>

        

        <form:form action="/empleados/save" modelAttribute="empleado" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Nombre" name="nombre"/>
                <petclinic:inputField label="Apellidos" name="apellidos"/> 
               	<petclinic:inputField label="DNI" name="dni"/>
               	<petclinic:inputField label="Fecha de nacimiento" name="fechaNacimiento"/>
               	<petclinic:inputField label="E-mail" name="email"/>
               	<petclinic:inputField label="Teléfono" name="telefono"/>
               	<petclinic:inputField label="Fecha inicio contrato" name="fecha_ini_contrato"/>
               	<petclinic:inputField label="Fecha fin contrato" name="fecha_fin_contrato"/>
               	<petclinic:inputField label="Número seguridad social" name="num_seg_social"/>
               	<petclinic:inputField label="Sueldo" name="sueldo"/>
               	<petclinic:select label="Taller" name="taller" items="${talleres}"/>
               	<br>
            	<c:choose>
                    <c:when test="${empleado['new']}">
                       <petclinic:inputField label="Usuario" name="usuario.username"/>
                    </c:when>
                    <c:otherwise>
                    	<petclinic:inputField label="Usuario" name="usuario.username" type="text" readonly="true"/>
                    </c:otherwise>
             	</c:choose>
             	<petclinic:inputField label="Contraseña" name="usuario.password" type="password"/>
        	</div>
        	<div class="form-group">
            	<div class="col-sm-offset-2 col-sm-10">
            		<input type="hidden" name="id" value="${empleado.id }">
	                <c:choose>
	                    <c:when test="${empleado['new']}">
	                        <button class="btn btn-default" type="submit">Añadir empleado</button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit">Actualizar empleado</button>
	                    </c:otherwise>
	                </c:choose>
            	</div>
        	</div>
        </form:form>

    </jsp:body>

</petclinic:layout>