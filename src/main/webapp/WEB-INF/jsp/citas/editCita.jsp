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
                $("#fecha").change(function() {
                	actualizarHoras()
                })
            });
		function actualizarHoras(updating){
			var fecha = $( "#fecha" ).datepicker( "getDate" );
        	horas = []
        	for(i = 0; i<cits.length; i++){
        		if(formatDate(fecha) == formatYearFirstToYearLast(cits[i]["fecha"])){
                	horas.push(cits[i]["hora"]);
        		}
        	}
        	mostrarHorasConCitas(horas);
			if(!updating){
				document.getElementsByName("hora")[0].value = '';
			}
			document.getElementById("ultimoBotonPulsado").value = null;
			$('#collapseFecha').collapse('show')
		}
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
        function activar(id, tipo, icono){
        	var label = document.getElementById("Tipo-" + id);
        	var numElegidos = parseInt(document.getElementById("tiposElegidos").innerHTML);
        	if(label.classList.contains("act")){
        		label.classList.remove("act");
        		label.innerHTML = '<input onClick="activar(' + id + ', \'' + tipo + '\', \'' + icono + '\')" style="opacity: 0" type="checkbox" autocomplete="off" name="tiposCita" value="' + id + 
        		'"><img src="/resources/images/' + icono + '" style="width: 32px; height: 32px"> ' + tipo;
        		document.getElementById("tiposElegidos").innerHTML = numElegidos - 1;
        		var buttons = document.getElementsByName("tiposCita")
        		for(i=0;i<buttons.length;i++){
        			buttons[i].parentElement.removeAttribute("disabled"); 
        		}
        	}else{
        		if(numElegidos < 3){
	    			document.getElementById("tiposElegidos").innerHTML = numElegidos + 1;
	        		label.classList.add("act");
	        		label.innerHTML = '<input onClick="activar(' + id + ', \'' + tipo + '\', \'' + icono + '\')" style="opacity: 0" type="checkbox" autocomplete="off" name="tiposCita" value="' + id + 
	        		'" checked><img src="/resources/images/' + icono + '" style="width: 32px; height: 32px"> ' + tipo;
        		}
        	}
        	if(parseInt(document.getElementById("tiposElegidos").innerHTML) == 3){
        		var buttons = document.getElementsByName("tiposCita")
        		for(i=0;i<buttons.length;i++){
        			if(!buttons[i].hasAttribute("checked")){
        				buttons[i].parentElement.setAttribute("disabled", ""); 
        			}
        		}
        	}
        }
        $("#Tipo-15").click(function() {
        	if(!document.getElementById("Tipo-15").hasAttribute("disabled")){
        		$("#collapseOtros").collapse("toggle")
        	}
        	})
        var tipos = [<c:forEach var="t" items="${cita.tiposCita}">
       	"${t.tipo}",
           </c:forEach>
           ]
   		for(i=0;i<tipos.length;i++){
   			if(tipos[i] == "OTROS"){
   				$("#collapseOtros").collapse("show")
   			}
   		}
        <c:if test="${not cita['new'] or not empty cita.fecha}">$(function() {
        	actualizarHoras(true)
        	var hora = ${cita.hora}
        	document.getElementById("ultimoBotonPulsado").value = hora;
			document.getElementById(hora).classList.add("btn-success");
			document.getElementsByName("hora")[0].value = hora;
        })</c:if>
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
               	<petclinic:selectTipoCita label="Tipo de cita" name="tiposCita" items="${tipos}"/>
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