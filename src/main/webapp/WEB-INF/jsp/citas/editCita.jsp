<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citas">     
    <jsp:attribute name="customScript">
<script>
		<c:if test="${cita['new']}"> 
        let cits = [
        <c:forEach var="c" items="${citas}">
        	{"fecha": "${c.fecha}",
        	"hora": ${c.hora}},
        </c:forEach>
        ]
		</c:if>
        <c:if test="${ not cita['new']}">
        let cits = [
            <c:forEach var="c" items="${citas}">
            <c:if test="${ not (c.hora == cita.hora && c.fecha == cita.fecha)}">
            	{"fecha": "${c.fecha}",
            	"hora": ${c.hora}},
            </c:if>
            </c:forEach>
            ]
        </c:if>
            $(function () {
                $("#fecha").datepicker({dateFormat: 'dd/mm/yy', minDate: 1})
                $("#fecha").change(function(){
                	var fecha = $( "#fecha" ).datepicker( "getDate" );
                	horas = []
                	for(i = 0; i<cits.length; i++){
                		console.log(formatDate(fecha))
                		console.log(formatYearFirstToYearLast(cits[i]["fecha"]))
                		console.log(formatDate(fecha) == formatYearFirstToYearLast(cits[i]["fecha"]))
                		if(formatDate(fecha) == formatYearFirstToYearLast(cits[i]["fecha"])){
                        	horas.push(cits[i]["hora"]);
                		}
                	}
                	mostrarHorasConCitas(horas);
    				document.getElementsByName("hora")[0].value = '';
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
        function mostrarHorasConCitas(horas){
        	var res = '';
        	for(i = 9; i<21; i++){
        		var type = 'default';
        		var disabled='';
        		if(horas.includes(i)){
        			type = 'danger';
        			disabled='disabled="disabled"';
        		}
        		var but = '<button ' + disabled + ' type="button" id="' + i + '" onClick="elegirHora(' + i + ')" class="col-sm-2 btn btn-' + type + ' buttonStyle" type="button">' + i + ':00</button>'
        		res += but;
        		if(i==14){
        			res += "</br>";
        		}
        	}
        	document.getElementById("collapseFecha").innerHTML = res;
        }
        function elegirHora(hora) {
			var lastId = document.getElementById("ultimoBotonPulsado").value;
			if(lastId!=null){
				document.getElementById(lastId).classList.remove("btn-success");
				document.getElementById("ultimoBotonPulsado").value = null;
			}
			if(lastId != hora){
				document.getElementById("ultimoBotonPulsado").value = hora;
				document.getElementById(hora).classList.add("btn-success");
				document.getElementsByName("hora")[0].value = hora;
			}
		}

</script>
</jsp:attribute>
    <jsp:body>
        <h2>
        <c:if test="${cita['new']}">Añadir </c:if> <c:if test="${ not cita['new']}">Editar </c:if> cita
    	</h2>
        <form:form modelAttribute="cita" class="form-horizontal">
            <div class="form-group has-feedback">
              
            	<petclinic:selectVehiculo label="Vehículos" name="vehiculo" items="${vehiculos}"/>
               	<petclinic:selectFecha items="${citas}" label="Fecha" name="fecha" name1="hora"></petclinic:selectFecha>
               	<petclinic:selectTipo label="Tipo de cita" name="tipoCita" items="${tipos}"/>
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