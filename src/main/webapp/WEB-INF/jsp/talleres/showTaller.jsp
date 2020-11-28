<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="talleres">

	<jsp:body>
		<h2>Contacto</h2>
		<p>Puede contactar con nosotros para resolver sus dudas a través de:</p>
		<ul>
			<li>Teléfono: <c:out value="${taller.telefono}"/> (Lunes a viernes, 9:00 - 20:00, excepto festivos)</li>
			<li>Correo electrónico: <c:out value="${taller.correo}"/> 
			(contestamos en un plazo de 24 horas de lunes a viernes excepto festivos)</li>
		</ul>
		
		<p>Todos nuestros talleres:</p>
		<ul>
			<li><c:out value="${taller.name}"/></li>
			<li>Ubicación: <c:out value="${taller.ubicacion}"/></li>
		</ul>
		
	
	</jsp:body>



</petclinic:layout>