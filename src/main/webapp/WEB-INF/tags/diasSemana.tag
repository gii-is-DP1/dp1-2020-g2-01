<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="desde" required="true" rtexprvalue="true"
              description="Primer dia de la semana" %>
<style>
a.ds {
	width: 12.5%; 
	height: 3.2vh; 
	margin:0.3vh; 
	padding-left:0; 
	padding-right:0; 
	text-align:center
}
</style>
<a type="button" class="btn btn-info disabled ds" role="button">Lunes ${desde}</a>
<a type="button" class="btn btn-info disabled ds" role="button">Martes ${desde + 1}</a>
<a type="button" class="btn btn-info disabled ds" role="button">Miércoles ${desde + 2}</a>
<a type="button" class="btn btn-info disabled ds" role="button">Jueves ${desde + 3}</a>
<a type="button" class="btn btn-info disabled ds" role="button">Viernes ${desde + 4}</a>
<a type="button" class="btn btn-info disabled ds" role="button">Sábado ${desde + 5}</a>
<a type="button" class="btn btn-info disabled ds" role="button">Domingo ${desde + 6}</a>