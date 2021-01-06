<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="cliente">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaNacimiento").datepicker({dateFormat: 'dd/mm/yy'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    <h2>
        <c:if test="${cliente['new']}">Nuevo </c:if> Cliente
    </h2>
    <form:form modelAttribute="cliente" class="form-horizontal" id="add-cliente-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Nombre" name="nombre"/>
            <petclinic:inputField label="Apellidos" name="apellidos"/>
            <petclinic:inputField label="DNI" name="dni"/>
            <petclinic:inputField label="E-mail" name="email"/>
            <petclinic:inputField label="Fecha de nacimiento" name="fechaNacimiento"/>
            <petclinic:inputField label="Tel�fono" name="telefono"/>
            <br>
            <c:choose>
                    <c:when test="${cliente['new']}">
                       <petclinic:inputField label="Usuario" name="user.username"/>
                    </c:when>
                    <c:otherwise>
                    	<petclinic:inputField label="Usuario" name="user.username" type="text" readonly="true"/>
                    </c:otherwise>
             </c:choose>
             <petclinic:inputField label="Contrase�a" name="user.password" type="password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<input type="hidden" name="id" value="${cliente.id }">
                <c:choose>
                    <c:when test="${cliente['new']}">
                        <button class="btn btn-default" type="submit">A�adir cliente</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar cliente</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>