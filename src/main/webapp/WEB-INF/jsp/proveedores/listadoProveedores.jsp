<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="proveedores">
    <h2>Proveedores</h2>
    
    <a class="btn btn-success col-sm-3" href="/proveedores/new"><span class="glyphicon glyphicon-plus"></span> Crear un nuevo proveedor</a>
    <a class="btn btn-success col-sm-3 col-sm-offset-6" href="/pedidosRecambio/listadoPedidosRecambio"><span class="glyphicon glyphicon-th-list "></span> Listado de Pedidos a Proveedores</a>
    <table id="proveedoresTable" class="table table-striped">
        <thead>
        <tr>
        	<th>Id</th>
            <th>Nombre</th>
            <th>NIF</th>
            <th>Teléfono</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${proveedores}" var="proveedor">
            <tr>
               <td>
                    <c:out value="${proveedor.id}"/>
                </td>
                
            
                <td>
                    <c:out value="${proveedor.name}"/>
                </td>
                

                
                <td>
                   <c:out value="${proveedor.nif}"/>
                </td>
                
                <td>
                   <c:out value="${proveedor.telefono}"/>
                </td>
                
                
                <td>
                   <c:out value="${proveedor.email}"/>
                </td>
                
                
                 <td>
                	<spring:url value="/proveedores/update/{proveedorId}" var="proveedorUrl">
                        <spring:param name="proveedorId" value="${proveedor.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(proveedorUrl)}"><span class="glyphicon glyphicon-pencil"></span></a>
                
                </td>
                
                <td>
                	<spring:url value="/proveedores/delete/{proveedorId}" var="proveedorUrl">
                        <spring:param name="proveedorId" value="${proveedor.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(proveedorUrl)}"><span class="glyphicon glyphicon-trash"></span></a>
                
                </td>
                
            </tr>
          
            
        </c:forEach>
        </tbody>
    </table>
    


</petclinic:layout>
           