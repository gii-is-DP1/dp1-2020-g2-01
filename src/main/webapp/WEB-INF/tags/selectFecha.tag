<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="name1" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.List"
              description="" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<spring:bind path="${name1}">
	<p style='display:none' id="ultimoBotonPulsado"></p>
    <c:set var="cssGroup" value="form-group"/>
    <c:set var="error" value="${status.error ? 'danger' : 'success' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <style>
    	.buttonStyle {
    		height: 5vh;
    		border: none;
    		border: 1px white solid;
    	}
    	.buttonStyle:hover{
    		background-color: #5cb85c;
    	}
    	.buttonStyle:focus{
    		background-color: #5cb85c;
    		border: 1px white solid;
    		outline: none;
    	}
    	.no-outline:focus{
    		outline: none;
    	}
    </style>
    <div class="${cssGroup}">
		<petclinic:inputFieldNotAutocompleted label="${label}" name="${name}"/>
        <div class="col-sm-7">
            <button type="button" class="col-sm-3 btn btn-${error} no-outline" type="button" data-toggle="collapse" data-target="#collapseFecha">
            Elegir hora <span class="caret"></span></button>
            <div class="collapse col-sm-9" id="collapseFecha">       <!-- Terminar de poner bien cuando dé error -->					
			</div>
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline"> Tienes que seleccionar una hora</span>
            </c:if>
        </div>
    </div>
    <input type="hidden" name="${name1}">
</spring:bind>

