<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reparacionesComunes">
    
    
    <h2>Reparaciones comunes</h2>
    <a href="/reparacionesComunes/new">Añadir una reparación común</a>
<<<<<<< Upstream, based on origin/master
    <form:form modelAttribute="repCom" class="form-horizontal"
=======
    <form:form modelAttribute="repCom" action="/reparacionesComunes" class="form-horizontal"
>>>>>>> f60e883 h04
               id="search-repCom-form">
        <div class="form-group">
            <div class="control-group" id="nombre">
                <label class="col-sm-2 control-label">Nombre </label>
                <div class="col-sm-10">
                    <form:input class="form-control" path="nombre" size="30" maxlength="80"/>
                    <span class="help-inline"><form:errors path="*"/></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Buscar reparación común</button>
            </div>
        </div>

    </form:form>
    <table id="comunesTable" class="table table-striped">
        <thead>
        <tr>
            <th>Nombre</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${comunes}" var="comunes">
            <tr>
             
               
                <td>
                	<spring:url value="/reparacionesComunes/show/{repComId}" var="repComUrl">
                      	  <spring:param name="repComId" value="${comunes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(repComUrl)}"><c:out value="${comunes.nombre}"/></a>
                
                </td>
                
                <td>
                	<spring:url value="/reparacionesComunes/update/{repComId}" var="repComUrl">
                        <spring:param name="repComId" value="${comunes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(repComUrl)}">Actualizar</a>
                
                </td>
                
                 <td>
                	<spring:url value="/reparacionesComunes/delete/{repComId}" var="repComUrl">
                        <spring:param name="repComId" value="${comunes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(repComUrl)}">Eliminar</a>
                
                </td>
                
               
                
         		
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    


</petclinic:layout>
