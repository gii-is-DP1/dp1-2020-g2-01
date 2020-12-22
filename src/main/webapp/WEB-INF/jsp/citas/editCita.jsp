<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vehiculos">

    <jsp:attribute name="customScript">
        <script>
        
            $(function () {
                $("#fecha").datepicker({dateFormat: 'dd/mm/yy', minDate: 1})
                $("#fecha").change(function(){
                	var fecha = $( "#fecha" ).datepicker( "getDate" );

                	//"${prueba['" + fecha + "']}";
    	        	document.getElementById("test").innerHTML = 3;
    	        })
            });
            function padLeft(n){
                return ("00" + n).slice(-2);
              }

              function formatDate(d){
                    dformat = [ padLeft(d.getDate()),
                                padLeft(d.getMonth()+1),
                                d.getFullYear()
                                ].join('/');
                 return dformat
              }
        </script>
    </jsp:attribute>      
    
    <jsp:body>
        <h2>
        <c:if test="${cita['new']}">A�adir </c:if> <c:if test="${ not cita['new']}">Editar </c:if> cita
    	</h2>
		<input type="hidden" id="citas" value="${citas}"/>
        <p id="test"></p>
        <form:form modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
              
            	<petclinic:selectVehiculo label="Veh�culos" name="vehiculo" items="${vehiculos}"/>
            	
                <petclinic:inputField label="Fecha" name="fecha"/> 
               	<!--<petclinic:inputField label="Hora" name="hora"/>
               	<input type="text" id="hora" value="12:00" />
               	<petclinic:selectFecha items="${vehiculos}" label="Fecha"></petclinic:selectFecha>-->
               	<petclinic:selectTipoCita label="Tipo de cita" name="tipoCita" items="${tipos}"/>
                <input type="hidden" name="id" value="${cita.id}"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                    <c:when test="${cita['new']}">
                        <button class="btn btn-default" type="submit">A�adir cita</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Editar cita</button>
                    </c:otherwise>
                </c:choose>
                </div>
            </div>
        </form:form>

    </jsp:body>

</petclinic:layout>