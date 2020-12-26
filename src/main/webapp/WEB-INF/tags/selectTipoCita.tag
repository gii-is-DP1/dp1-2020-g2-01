<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.List"
              description="" %>

<spring:bind path="${name}">
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
    	.act {
    		background-color: #5cb85c;    	
    	}
    </style>
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <p style='display:none' id="tiposElegidos">0</p>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label}</label>
		
        <div class="col-sm-10">
        	<div class="btn-group" style="margin-top: 1vh">
            	<c:forEach var="item" items="${items}">
            		<div class="col-sm-4" style="margin-bottom: 1vh">
            		<label class="btn btn-default col-sm-12 no-outline buttonStyle" id="Tipo-${item.id}">
				    <input onClick="activar(${item.id}, '${item.tipo}')" style="opacity: 0" name="tipo" type="checkbox" autocomplete="off" value="${item.id}">${item.tipo}
				  	</label>
				  	</div>
            	</c:forEach>
            	<div class="col-sm-4" style="margin-bottom: 1vh">
	            	<button type="button" class="btn btn-default col-sm-12 no-outline buttonStyle" data-toggle="collapse" data-target="#collapseOtros">
	            	OTROS <span class="caret"></span></button>
			  	</div>
			  	<div class="collapse col-sm-12" id="collapseOtros">	
			  	<form:input class="form-control" path="descripcion" placeholder="Añade una descripción de su problema"/>
				</div>
            </div>
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</spring:bind>