<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>
<%@ attribute name="items" required="true" rtexprvalue="true" type="java.util.List"
              description="" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    <p style='display:none' id="tiposElegidos">${fn:length(cita.tiposCita)}</p>
    <c:set var="max" value=""/>
    <c:if test="${ fn:length(cita.tiposCita) >= 3}"><c:set var="max" value="disabled"/></c:if>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label}</label>
		
        <div class="col-sm-10">
        	<div class="btn-group" style="margin-top: 1vh">
            	<c:forEach var="item" items="${items}">
            		<div class="col-sm-4" style="margin-bottom: 1vh">
            		<c:set var="contains" value="false"/>
            		<c:forEach var="element" items="${cita.tiposCita}">
            			<c:if test="${element.tipo eq item.tipo}">
							<c:set var="contains" value="true" />
						</c:if>
            		</c:forEach>
            		<c:choose>
            		
            		<c:when test="${contains}">
	            		<label class="btn btn-default col-sm-12 no-outline buttonStyle act" id="Tipo-${item.id}">
					    <input onClick="activar(${item.id}, '${item.tipo}', '${item.icono}')" style="opacity: 0" name="${name}" type="checkbox" autocomplete="off" value="${item.id}" checked>
					    <img src="/resources/images/${item.icono}" style="width: 32px; height: 32px">
					    ${item.tipo}
					  	</label>
					</c:when>
					<c:otherwise>
						<label class="btn btn-default col-sm-12 no-outline buttonStyle" id="Tipo-${item.id}" ${max}>
					    <input onClick="activar(${item.id}, '${item.tipo}', '${item.icono}')" style="opacity: 0" name="${name}" type="checkbox" autocomplete="off" value="${item.id}">
					    <img src="/resources/images/${item.icono}" style="width: 32px; height: 32px">
					    ${item.tipo}
					  	</label>
					</c:otherwise>
					</c:choose>
				  	</div>
            	</c:forEach>
            <c:if test="${status.error}">
                <span class="form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">Tipo de cita: ${status.errorMessage}</span>
            </c:if>
			  	<div class="collapse col-sm-12" id="collapseOtros">	
			  	<form:input class="form-control" path="descripcion" placeholder="Añade una descripción de su problema"/>
				</div>
            </div>
        </div>
    </div>
</spring:bind>