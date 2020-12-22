<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="label1" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="name1" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.List"
              description="" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:inputFieldNotAutocompleted label="${label}" name="${name}"/>
<spring:bind path="${name1}">
	<p style='display:none' id="ultimoBotonPulsado"></p>
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <style>
    	.buttonStyle {
    		width: 15.95%; 
    		height: 5vh; 
    		margin:0.3vh;
    		border: none;
    	}
    	.buttonStyle1 {
    		width: 13.57%; 
    		height: 5vh; 
    		margin:0.3vh;
    		border: none;
    	}
    	.buttonStyle:hover, .buttonStyle:focus, .buttonStyle1:hover, .buttonStyle1:focus {
    		background-color: #449d44;
    	}
    </style>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label1}</label>
		
        <div class="col-sm-10">
            <button type="button" class="btn btn-success" type="button" data-toggle="collapse" data-target="#collapseFecha">
            Elegir hora <span class="caret"></span></button>
            <div class="collapse" style="border: solid 1px #34302d" id="collapseFecha">       					
			</div>
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
    <input type="hidden" name="${name1}">
</spring:bind>

