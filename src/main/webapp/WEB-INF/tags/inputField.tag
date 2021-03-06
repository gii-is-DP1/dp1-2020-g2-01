<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="name" required="true" rtexprvalue="true"
              description="Name of corresponding property in bean object" %>
<%@ attribute name="label" required="true" rtexprvalue="true"
              description="Label appears in red color if input is considered as invalid after submission" %>

<%@ attribute name="type" required="false" rtexprvalue="true"
              description="" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true"
              description="" %>
<%@ attribute name="noglyphicon" required="false" rtexprvalue="true"
              description="" %>
<%@ attribute name="autocomplete" required="false" rtexprvalue="true"
              description="" %>
                            
<spring:bind path="${name}">
    <c:set var="cssGroup" value="form-group ${status.error ? 'has-error' : '' }"/>
    <c:set var="valid" value="${not status.error and not empty status.actualValue}"/>
    <div class="${cssGroup}">
        <label class="col-sm-2 control-label">${label}</label>

        <div class="col-sm-10">
        	<c:set var="autocomplete" value="${(empty autocomplete) ? 'on' : autocomplete}" />
            <form:input autocomplete="${autocomplete}" type="${type}" readonly="${readonly}" class="form-control" path="${name}" id="${name}"/>
            <c:if test="${valid}">
            	<c:set var="icon" value="glyphicon glyphicon-ok"></c:set>
            	<c:if test="${noglyphicon}"><c:set var="icon" value=""></c:set></c:if>
                <span class="${icon} form-control-feedback" aria-hidden="true"></span>
            </c:if>
            <c:if test="${status.error}">
                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                <span class="help-inline">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</spring:bind>
