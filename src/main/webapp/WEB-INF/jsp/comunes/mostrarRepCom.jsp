<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reparacionesComunes">

	<jsp:body>
	
		<h1><c:out value="${repCom.nombre}"/></h1>
		<c:out value = "${repCom.descripcion}"/>
	</jsp:body>


</petclinic:layout>