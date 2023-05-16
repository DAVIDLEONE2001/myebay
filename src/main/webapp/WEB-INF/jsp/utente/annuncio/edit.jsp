<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
<jsp:include page="../header.jsp" />

<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />
<style>
.ui-autocomplete-loading {
	background: white url("../assets/img/jqueryUI/anim_16x16.gif") right
		center no-repeat;
}

.error_field {
	color: red;
}
</style>
<title>Inserisci nuovo</title>

</head>
<body class="d-flex flex-column h-100">
	<jsp:include page="../navbar.jsp" />

	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container">

			<%-- se l'attributo in request ha errori --%>
			<spring:hasBindErrors name="insert_annuncio_attr">
				<%-- alert errori --%>
				<div class="alert alert-danger " role="alert">Attenzione!!
					Sono presenti errori di validazione</div>
			</spring:hasBindErrors>

			<div
				class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }"
				role="alert">
				${errorMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>

			<div class='card'>
				<div class='card-header'>
					<h5>Inserisci nuovo elemento</h5>
				</div>
				<div class='card-body'>

					<h6 class="card-title">
						I campi con <span class="text-danger">*</span> sono obbligatori
					</h6>


					<form:form method="post" modelAttribute="insert_annuncio_attr"
						action="${pageContext.request.contextPath }/annuncio/utente/annuncio/executeEdit"
						novalidate="novalidate" class="row g-3">

						<div class="col-md-6">
							<label for="testoAnnuncio" class="form-label">Testo
								Annuncio <span class="text-danger">*</span>
							</label>
							<spring:bind path="testoAnnuncio">
								<input type="text" name="testoAnnuncio" id="testoAnnuncio"
									class="form-control ${status.error ? 'is-invalid' : ''}"
									placeholder="Inserire il titolo"
									value="${insert_annuncio_attr.testoAnnuncio }">
							</spring:bind>
							<form:errors path="testoAnnuncio" cssClass="error_field" />
						</div>

						<div class="col-md-6">
							<label for="prezzo" class="form-label">Prezzo <span
								class="text-danger">*</span></label>
							<spring:bind path="prezzo">
								<input type="text" name="prezzo" id="prezzo"
									class="form-control ${status.error ? 'is-invalid' : ''}"
									placeholder="Inserire il genere"
									value="${insert_annuncio_attr.prezzo }">
							</spring:bind>
							<form:errors path="prezzo" cssClass="error_field" />
						</div>

						




						<!-- 									<div class="col-md-6"> -->
						<!-- 										<label for="registaSearchInput" class="form-label">Regista: <span class="text-danger">*</span></label> -->
						<%-- 										<spring:bind path="regista"> --%>
						<%-- 											<input class="form-control ${status.error ? 'is-invalid' : ''}" type="text" id="registaSearchInput" --%>
						<%-- 												name="registaInput" value="${insert_annuncio_attr.regista.nome}${empty insert_annuncio_attr.regista.nome?'':' '}${insert_film_attr.regista.cognome}"> --%>
						<%-- 										</spring:bind> --%>
						<%-- 										<input type="hidden" name="regista.id" id="registaId" value="${insert_annuncio_attr.regista.id}"> --%>
						<%-- 										<form:errors  path="regista" cssClass="error_field" /> --%>
						<!-- 									</div> -->

						<!-- 									<div class="form-row">	 -->
						<!-- 										<div class="form-group col-md-6"> -->
						<!-- 											<label for="regista.id">Categorie</label> -->
						<!-- 										    <select class="form-control" id="regista.id" name="regista.id"> -->
						<!-- 										    	<option value="" selected> -- Selezionare una voce -- </option> -->
						<%-- 										      	<c:forEach items="${registi_list_attribute }" var="registaItem"> --%>
						<%-- 										      		<option value="${registaItem.id}" ${insert_film_attr.regista.id == registaItem.id?'selected':''} >${registaItem.nome } ${registaItem.cognome }</option> --%>
						<%-- 										      	</c:forEach> --%>
						<!-- 										    </select> -->
						<!-- 										</div> -->
						<!-- 									</div> -->
						<!-- 									<div class="col-12">	 -->
						<!-- 										<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button> -->
						<!-- 									</div> -->

						<%--  checkbox ruoli 	--%>
						<%-- facendolo con i tag di spring purtroppo viene un po' spaginato quindi aggiungo class 'a mano'	--%>
						<div class="col-md-6 form-check" id="idsCategorie">
							<p>Categorie:</p>
							<form:checkboxes path="idsCategorie" 
								items="${categorie_annuncio_list}" itemValue="id"
								itemLabel="descrizione" element="div class='form-check'"  />
						</div>
						<script>
							$(document).ready(function() {

								$("#idsCategorie :input").each(function() {
									$(this).addClass('form-check-input');
								});
								$("#idsCategorie label").each(function() {
									$(this).addClass('form-check-label');
								});

							});
						</script>
						<%-- fine checkbox ruoli 	--%>
						<input type="hidden" value="${insert_annuncio_attr.id }" name="id" id="id">
						<div class="col-12">
							<button type="submit" name="submit" value="submit" id="submit"
								class="btn btn-primary">Conferma</button>
							<input class="btn btn-outline-warning" type="reset"
								value="Ripulisci">
						</div>

					</form:form>

					<%-- FUNZIONE JQUERY UI PER AUTOCOMPLETE --%>
					<script>
						$("#registaSearchInput")
								.autocomplete(
										{
											source : function(request, response) {
												//quando parte la richiesta ajax devo ripulire registaId
												//altrimenti quando modifico il campo, cancellando
												//qualche carattere, mi rimarrebbe comunque valorizzato il 
												//'vecchio id'
												$('#registaId').val('');

												$
														.ajax({
															url : "${pageContext.request.contextPath}/regista/searchRegistiAjax",
															datatype : "json",
															data : {
																term : request.term,
															},
															success : function(
																	data) {
																response($
																		.map(
																				data,
																				function(
																						item) {
																					return {
																						label : item.label,
																						value : item.value
																					}
																				}))
															}
														});
											},
											//quando seleziono la voce nel campo deve valorizzarsi la descrizione
											focus : function(event, ui) {
												$("#registaSearchInput").val(
														ui.item.label);
												return false;
											},
											minLength : 2,
											//quando seleziono la voce nel campo hidden deve valorizzarsi l'id
											select : function(event, ui) {
												$('#registaId').val(
														ui.item.value);
												return false;
											},
											//questo serve in quanto se io imposto un regista e poi lo cancello
											//e faccio altro nella pagina, il valore che poi verrà inviato al 
											//controller deve essere resettato altrimenti non mi darebbe
											//l'errore di validazione di regista mancante
											change : function(event, ui) {
												if (!$("#registaSearchInput")
														.val()) {
													$('#registaId').val('');
													return false;
												}
											}
										});
					</script>
					<!-- end script autocomplete -->


					<!-- end card-body -->
				</div>
				<!-- end card -->
			</div>
			<!-- end container -->
		</div>

		<!-- end main -->
	</main>
	<jsp:include page="../footer.jsp" />

</body>
</html>