<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">

				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Inicio</span>
				</petclinic:menuItem>
				
				<sec:authorize access="hasAuthority('empleado')">
				<petclinic:menuItem active="${name eq 'clientes'}" url="/clientes/listadoClientes"
					title="find clientes">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Buscar Clientes</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<!-- Vehiculos -->
				<sec:authorize access="hasAuthority('cliente')">
					<petclinic:menuItem active="${name eq 'vehiculos'}" url="/vehiculos/listadoVehiculos"
						title="vehiculos">
						<span class="glyphicon glyphicon-bed" aria-hidden="true"></span>
						<span>Mis Veh�culos</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('empleado') or hasAuthority('cliente')">
					<petclinic:menuItem active="${name eq 'reparaciones'}" url="/reparaciones/listadoReparaciones"
						title="reparaciones">
						<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
						<span>Reparaciones</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				
				
				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'proveedores'}" url="/proveedores/listadoProveedores"
					title="proveedores">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					<span>Proveedores</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('empleado')">
					<petclinic:menuItem active="${name eq 'recambios'}" url="/recambios/listadoRecambios"
						title="recambios">
						<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
						<span>Recambios</span>
					</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'citas'}" url="/citas/listadoCitas"
					title="citas">
					<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
					<span>Citas</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('admin')">
				<petclinic:menuItem active="${name eq 'inventario'}" url="/recambios/listadoRecambios"
					title="stock">
					<span class="glyphicon glyphicon-tags" aria-hidden="true"></span>
					<span>Stock</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('empleado')">
				<petclinic:menuItem active="${name eq 'citas'}" url="/citas/listadoCitas"
					title="citas">
					<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
					<span>Citas</span>
				</petclinic:menuItem>
				</sec:authorize>
				

				<sec:authorize access="hasAuthority('cliente')">
					<petclinic:menuItem active="${name eq 'citas'}" url="/citas/listadoCitas"
						title="citas">
						<span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
						<span>Mis citas</span>
					</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('admin')">
					<petclinic:menuItem active="${name eq 'balance'}" url="/balanceEconomico"
						title="balance">
						<span class="glyphicon glyphicon-euro" aria-hidden="true"></span>
						<span>Balance</span>
					</petclinic:menuItem>
				</sec:authorize>

			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/clientes/new" />">Register</a></li>
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">
					<sec:authorize access="hasAuthority('cliente')">
					<li class="dropdown ${name eq 'cliente' ? 'active' : ''}"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>�
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<sec:authentication property="name" var="username"/>
												<a href="<c:url value="/clientes/clienteDetails/${username}"/>"
													class="btn btn-primary btn-block btn-sm">Ver perfil</a>

											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>

						</ul></li>
					</sec:authorize>	
					<sec:authorize access="hasAuthority('admin')">
					<li class="dropdown ${name eq 'administrador' ? 'active' : ''}"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>�
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<sec:authentication property="name" var="username"/>
												<a href="<c:url value="/administradores/administradorDetails/${username}"/>"
													class="btn btn-primary btn-block btn-sm">Ver perfil</a>

											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						
					</ul></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('empleado')">
					<li class="dropdown ${name eq 'empleado' ? 'active' : ''}"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>�
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<sec:authentication property="name" var="username"/>
												<a href="<c:url value="/empleados/empleadoDetails/${username}"/>"
													class="btn btn-primary btn-block btn-sm">Ver perfil</a>

											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						
					</ul></li>
					</sec:authorize>
					
				</sec:authorize>
				<sec:authorize access="hasAuthority('cliente') or !isAuthenticated()">
					<petclinic:menuItem active="${name eq 'contacto'}" url="/talleres/contacto"
						title="contacto">
						<span class="glyphicon glyphicon-earphone" aria-hidden="true"></span>
						<span>contacto</span>
					</petclinic:menuItem>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
