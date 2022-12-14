<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div id="load">
	<img src="${path}/resources/images/load_img.gif" alt="loading">
</div>

<!-- nav bar stary -->
<header class="sticky-top">
	<nav class=" navbar navbar-expand-lg navbar-light bg-white">
		<div
			class="container-fluid flex-wrap flex-lg-nowrap gx-5 py-2 border-bottom">
			<!--logo-->
			<div class="logo-wrap">
				<a class="navbar-logo" href="${path}/main">STAYHERE</a>
			</div>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="${path}/search">FIND	STAY</a></li>
					<li class="nav-item"><a class="nav-link" href="about.html">PROMOTION</a></li>
					<li class="nav-item"><a class="nav-link" href="${path}/reviews/list.do">REVIEW</a></li>
					<li class="nav-item"><a class="nav-link" href="#">QNA</a></li>
					<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="navbarDropdownBlog" href="#"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">GUEST</a>
						<ul class="dropdown-menu dropdown-menu-end"
							aria-labelledby="navbarDropdownBlog">
							<c:if test="${sessionScope.userid == null}">
								<li><a class="dropdown-item" href="${path}/guest/login.do">로그인</a></li>
								<li><a class="dropdown-item" href="${path}/guest/join.do">회원가입</a></li>								
							</c:if>
							<c:if test="${sessionScope.userid != null}">
								<li><b class="dropdown-item">${sessionScope.name} 님</b></li>
								<li><hr class="dropdown-divider" /></li>
								<li><a class="dropdown-item"
									href="${path}/guest/guest_view/${sessionScope.userid}">프로필</a></li>
								<li><a class="dropdown-item" href="blog-home.html">예약정보</a></li>
								<li><a class="dropdown-item" href="blog-home.html">관심스테이</a></li>
								<li><a class="dropdown-item" href="${path}/guest/list.do">결제내역</a></li>
								<li><a class="dropdown-item" href="blog-home.html">문의하기</a></li>
								<li><hr class="dropdown-divider" /></li>
								<li><a class="dropdown-item" href="${path}/guest/logout.do">로그아웃</a></li>
							</c:if>
						</ul></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" id="navbarDropdownPortfolio"
						href="#" role="button" data-bs-toggle="dropdown"
						aria-expanded="false">HOST</a>
						<ul class="dropdown-menu dropdown-menu-end"
							aria-labelledby="navbarDropdownPortfolio">
							<c:if test="${sessionScope.h_userid == null}">
								<li><a class="dropdown-item" href="${path}/host/login.do">로그인</a></li>
								<li><a class="dropdown-item" href="${path}/host/join.do">호스트가입</a></li>

							</c:if>
							<c:if test="${sessionScope.h_userid != null}">
								<li><b class="dropdown-item">${sessionScope.h_name} 님</b></li>
								<li><hr class="dropdown-divider" /></li>
								<li><a class="dropdown-item"
									href="${path}/host/profile/${sessionScope.h_userid}">프로필</a></li>
								<li><a class="dropdown-item" href="portfolio-overview.html">숙소관리</a></li>
								<li><a class="dropdown-item" href="portfolio-overview.html">숙소등록</a></li>
								<li><a class="dropdown-item" href="portfolio-overview.html">숙소승인현황</a></li>
								<li><a class="dropdown-item" href="portfolio-overview.html">예약현황</a></li>
								<li><a class="dropdown-item" href="portfolio-overview.html">매출현황</a></li>
								<li><hr class="dropdown-divider" /></li>
								<li><a class="dropdown-item" href="${path}/host/logout.do">로그아웃</a></li>
							</c:if>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav>
	<!--search bar start -->
	<div class="d-flex justify-content-center">
		<div class="search-bar border ps-4">
			<form method="get" action="${path}/search" id="searchFrom">
				<div class="row">
					<div class="col-lg-4 d-flex align-items-center rounded-pill">
						<div class="form-floating form-group">
							<input class="form-control border-0 shadow-none" id="city"
								name="city" placeholder="" value="${param.city}"> <label
								for="city">도시를 입력해주세요</label>
						</div>
					</div>
					<div class="col-lg-3 d-flex align-items-center">
						<div class="form-floating form-group">
							<input class="form-control border-0 shadow-none" id="check-in"
								type="date" name="check_in" value="${param.check_in}"> <label
								for="check-in">체크인</label>
						</div>
					</div>
					<div class="col-lg-3 d-flex align-items-center">
						<div class="form-floating form-group">
							<input class="form-control border-0 shadow-none" id="check-out"
								type="date" name="check_out" value="${param.check_out}">
							<label for="check-out">체크아웃</label>
						</div>
					</div>
					<div class="col-lg-2 d-flex align-items-center">
						<button class="btn btn-custom rounded-pill" type="button"
							onclick="submitMainSearch(searchFrom)">
							<i class="bi-search"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- search bar end -->
</header>