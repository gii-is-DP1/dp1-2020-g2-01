<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="solicitud">

    <h2>
        <c:if test="${solicitud['new']}">Nueva </c:if> solicitud
    </h2>
    <form:form modelAttribute="solicitud" class="form-horizontal" id="add-solicitud-form">
        <div class="form-group has-feedback">
            <petclinic:select label="Recambio" name="recambio" items="${recambios}"/>
            <petclinic:inputField label="Cantidad" name="cantidad"/>
            <petclinic:select label="Empleado" name="empleado" items="${empleados}"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<input type="hidden" name="id" value="${solicitud.id}">
                <c:choose>
                    <c:when test="${solicitud['new']}">
                        <button class="btn btn-default" type="submit">Añadir solicitud</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar solicitud</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>

</petclinic:layout>