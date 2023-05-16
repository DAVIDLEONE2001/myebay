<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<header>
	<!-- Fixed navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-primary"
		aria-label="Eighth navbar example">
		<div class="container">
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarsExample07"
				aria-controls="navbarsExample07" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarsExample07">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item" ><a href="${pageContext.request.contextPath}/home">
						<button class="btn btn-outline-light" type="button">
							<span>Home</span>
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-house" viewBox="0 0 16 16">
<path
									d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L2 8.207V13.5A1.5 1.5 0 0 0 3.5 15h9a1.5 1.5 0 0 0 1.5-1.5V8.207l.646.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5ZM13 7.207V13.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V7.207l5-5 5 5Z" />
</svg>
						</button></a>
					</li>
					<li class="nav-item"><a href="${pageContext.request.contextPath}/home">
						<button class="btn btn-outline-success" type="button" style="color: black">
							<span>Cerca</span>
							<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
</svg>
						</button></a>
					</li>
					
					
					
					<sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
					<c:if test="${isAutenticato}">
						<li class="nav-item"><a
							href="${pageContext.request.contextPath}/utente/areaPersonale"
							class="btn btn-outline-warning">Area Personale</a></li>
					</c:if>
					<sec:authorize access="hasRole('ADMIN')">
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" id="dropdown01"
							data-bs-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">Gestione Utenze</a>
							<div class="dropdown-menu" aria-labelledby="dropdown01">
								<a class="dropdown-item"
									href="${pageContext.request.contextPath}/utente/search">Ricerca
									Utenti</a> <a class="dropdown-item"
									href="${pageContext.request.contextPath}/utente/insert">Inserisci
									Utente</a>
							</div></li>
					</sec:authorize>
				</ul>
			</div>

			<sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
			<c:choose>
				<c:when test="${isAutenticato}">
					<div class="col-md text-end">
						<h6 class="navbar-text">
							<a href="${pageContext.request.contextPath}/logout"
								class="text-decoration-none text-white fw-bold">Logout <svg
									xmlns="http://www.w3.org/2000/svg" width="16" height="16"
									fill="currentColor" class="bi bi-door-open-fill"
									viewBox="0 0 16 16">
  <path
										d="M1.5 15a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2.5A1.5 1.5 0 0 0 11.5 1H11V.5a.5.5 0 0 0-.57-.495l-7 1A.5.5 0 0 0 3 1.5V15H1.5zM11 2h.5a.5.5 0 0 1 .5.5V15h-1V2zm-2.5 8c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z" />
</svg></a>
						</h6>
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-md text-end">
						<h6 class="navbar-text">
							<a href="${pageContext.request.contextPath}/login"
								class="text-decoration-none text-white fw-bold">Login <svg
									xmlns="http://www.w3.org/2000/svg" width="16" height="16"
									fill="currentColor" class="bi bi-door-open" viewBox="0 0 16 16">
  <path
										d="M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z" />
  <path
										d="M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z" />
</svg></a>
						</h6>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</nav>


</header>