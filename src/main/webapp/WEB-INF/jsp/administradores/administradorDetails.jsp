<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="administrador">
<style>
    .helper {
		display: inline-block;
		height: 100%;  	
    	vertical-align:middle;
		}
    </style>
<div class="col-sm-12" style="margin-bottom: 20px">
<div class="col-sm-6">
<div class="panel panel-success">
  <div class="panel-heading">
    <h3 class="panel-title">Información de administrador</h3>
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Nombre y apellidos: </label> ${administrador.nombre}&nbsp${administrador.apellidos}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">DNI: </label>${administrador.dni}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Fecha de nacimiento: </label>
    <fmt:parseDate value="${administrador.fechaNacimiento}" pattern="yyyy-MM-dd" 
	                             var="parsedDate" type="date" />
	
		<fmt:formatDate value="${parsedDate}" var="fecha" 
	                              type="date" pattern="dd/MM/yyyy" />
	    ${fecha}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Teléfono: </label>${administrador.telefono}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">E-mail: </label>${administrador.email}
  </div>
  <div class="panel-body">
    <label class="col-sm-4">Usuario: </label>${administrador.usuario.username}
  </div>
</div>
<sec:authorize access="hasAuthority('admin')">
   <spring:url value="/administradores/update/{username}" var="administradorUrl">
   		<spring:param name="username" value="${administrador.usuario.username}"/>
   </spring:url>
       	<a href="${fn:escapeXml(administradorUrl)}" class="btn btn-success">Editar perfil</a>
</sec:authorize>
</div>
</div>

</petclinic:layout>