<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<petclinic:layout pageName="pedidosRecambio">
    
    
    

    <jsp:body>
        <h2>Pedido</h2>

        

        <form:form action="/pedidosRecambio/save" modelAttribute="pedidosRecambio" class="form-horizontal">
            <div class="form-group has-feedback">
                <petclinic:select label="Nombre" name="name" items="${nombres}"/>
                <petclinic:inputField label="Precio Unitario" name="precioPorUnidad"/> 
               	<petclinic:inputField label="Cantidad" name="cantidad"/>
               	<petclinic:select label="Proveedor" name="proveedor" items="${proveedores}"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="id" value="${pedidosRecambio.id}"/>
                    <button class="btn btn-default" type="submit">Guardar pedido</button>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>