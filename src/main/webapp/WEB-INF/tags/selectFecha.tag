<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.List"
              description="" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
   
      
<c:set var="id" value="5"/> <!-- prueba -->
<c:set var="fecha" value="'29/11/2020'"/>
<c:set var="id1" value="6"/> <!-- prueba -->
<c:set var="fecha1" value="'30/11/2020'"/>
			<script>
			function elegirFechaYHora(id, hora, fecha) {
					var lastId = document.getElementById("ultimoBotonPulsado").value;
					if(lastId!=null){
						document.getElementById(lastId).classList.remove("btn-warning");
					}
					document.getElementById("ultimoBotonPulsado").value = id;
					document.getElementById(id).classList.add("btn-warning");
					document.getElementsByName("hora")[0].value = hora;
					document.getElementsByName("fecha")[0].value = fecha;
			}
			</script>
<spring:bind path="fecha">
	<p style="display:none" id="ultimoBotonPulsado"></p>
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <c:set var="margin" value="0.3vh"/>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label}</label>
		
        <div class="col-sm-10">
            <button type="button" class="btn btn-success" type="button" data-toggle="collapse" data-target="#collapseFecha">
            Elegir fecha <span class="caret"></span></button>
            <div class="collapse" style="border: solid 1px #34302d" id="collapseFecha">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="false">           					
				<div class="carousel-inner" role="listbox">
    				<div class="item active" style="width:70%; margin-left:15%;">
    					<petclinic:diasSemana desde="7"/><br>
			           	<c:forEach var="hora" begin="9" end="21">
						<c:forEach var="dia" begin="7" end="13">
						<button type="button" onclick="elegirFechaYHora('${hora} + ${dia}', ${hora}, ${fecha})" id="${hora} + ${dia}" class="btn btn-success" type="button" style="width: 12.5%; height: 5vh; margin:${margin}">${hora}:00</button>
			           	</c:forEach>
			           	</c:forEach>
			        </div>
					<div class="item" style="width:70%; margin-left:15%;">
						<petclinic:diasSemana desde="14"/><br>
						<c:forEach var="hora" begin="9" end="21">
						<c:forEach var="dia" begin="14" end="20">
						<button type="button" onclick="elegirFechaYHora('${hora} + ${dia}', ${hora}, ${fecha})" id="${hora} + ${dia}" class="btn btn-success" type="button" style="width: 12.5%; height: 5vh; margin:${margin}">${hora}:00</button>
			           	</c:forEach>
			           	</c:forEach>
					</div>
				</div>
				<!-- Controles -->
				<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
				  <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				</a>
				<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
				  <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				</a>
			</div>
			</div>
			<input type="hidden" name="fecha" value=""/> <!-- Aquí se pone el valor de la fecha -->
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</spring:bind>
<spring:bind path="hora">
<input type="hidden" name="hora" value=""/> <!-- Aquí se pone el valor de la hora -->
</spring:bind>

