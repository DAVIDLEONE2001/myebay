<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100">
<head>

<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />

<style>
.error_field {
	color: red;
}
</style>

<title>Visualizza Elemento</title>

</head>
<body class="d-flex flex-column h-100">

	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>


	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container" style="max-width: 30rem;">

			<div class='card'>
				<div class='card-header'>
					<h5>Cambia Password</h5>
				</div>


				<div class='card-body'>
					<form:form class="form-signin" name='cambiaPassword'
						action="${pageContext.request.contextPath}/account/cambiaPassword"
						method='POST' novalidate="novalidate"
						modelAttribute="cambia_password_attr">

						<div style="text-align: center;"
							class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}"
							role="alert">${errorMessage}</div>

						<div
							class="alert alert-info alert-dismissible fade show ${infoMessage==null?'d-none': ''}"
							role="alert">${infoMessage}</div>


						<h1 class="h3 mb-3 fw-normal">Please sign in</h1>


						<div class="form-floating">
							<spring:bind path="password">
								<input type="password" name="password" id="password"
									placeholder="username"
									class="form-control ${status.error ? 'is-invalid' : ''}">
								<label for="password">Old Password</label>
							</spring:bind>
							<form:errors path="password" cssClass="error_field" />
						</div>
						<div class="form-floating">
							<spring:bind path="nuovaPassword">
								<input type="password" name="nuovaPassword" id="nuovaPassword"
									placeholder="username"
									class="form-control ${status.error ? 'is-invalid' : ''}">
								<label for="nuovaPassword">New Password</label>
							</spring:bind>
							<form:errors path="nuovaPassword" cssClass="error_field" />
						</div>
						<div class="form-floating">
							<spring:bind path="confermaPassword">
								<input type="password" name="confermaPassword"
									id="confermaPassword" placeholder="Password"
									class="form-control ${status.error ? 'is-invalid' : ''}">
								<label for="confermaPassword">Conferma Password</label>
							</spring:bind>
							<form:errors path="confermaPassword" cssClass="error_field" />
						</div><br>
						<input type="hidden" value="${show_utente_attr.id }" name="id"></input>


						<button class="w-100 btn btn-lg btn-primary" type="submit">Conferma
							</button>
						<p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>



					</form:form>



					<!-- end card body -->
				</div>

				<div class='card-footer'>
					<a href="${pageContext.request.contextPath}/utente"
						class='btn btn-outline-secondary' style='width: 80px'> <i
						class='fa fa-chevron-left'></i> Back
					</a>
				</div>
				<!-- end card -->
			</div>


			<!-- end container -->
		</div>

	</main>

	<!-- Footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>