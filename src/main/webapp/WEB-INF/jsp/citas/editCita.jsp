<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vehiculos">

    <jsp:attribute name="customScript">
        <script>

        let cits = [
        <c:forEach var="c" items="${citas}">
        	{"fecha": "${c.fecha}",
        	"hora": ${c.hora}},
        </c:forEach>
        ]
            $(function () {
                $("#fecha").datepicker({dateFormat: 'dd/mm/yy', minDate: 1})
                $("#fecha").change(function(){
                	var fecha = $( "#fecha" ).datepicker( "getDate" );
                	document.getElementById("test").innerHTML = ""
                	for(i = 0; i<cits.length; i++){
                		console.log(formatDate(fecha))
                		console.log(formatYearFirstToYearLast(cits[i]["fecha"]))
                		console.log(formatDate(fecha) == formatYearFirstToYearLast(cits[i]["fecha"]))
                		if(formatDate(fecha) == formatYearFirstToYearLast(cits[i]["fecha"])){

                        	document.getElementById("test").innerHTML += cits[i]["hora"]
                		}
                	}
    	        	
    	        })
            });

        function formatYearFirstToYearLast(d){
            dformat = d.split("-")[2] + "/" + d.split("-")[1] + "/" + d.split("-")[0]
         	return dformat
		}
        function formatYearLastToYearFirst(d){
          dformat = d.split("/")[2] + "-" + d.split("/")[1] + "-" + d.split("/")[0]
          return dformat
		}
        
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
        <c:if test="${cita['new']}">Añadir </c:if> <c:if test="${ not cita['new']}">Editar </c:if> cita
    	</h2>
        <p id="test"></p>
        <form:form modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
              
            	<petclinic:selectVehiculo label="Vehículos" name="vehiculo" items="${vehiculos}"/>
            	
                <petclinic:inputField label="Fecha" name="fecha"/> <!-- Falta en el js poner en rojo las horas que no estén disponibles
                y darle un formato para elegir las horas -->
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
                        <button class="btn btn-default" type="submit">Añadir cita</button>
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