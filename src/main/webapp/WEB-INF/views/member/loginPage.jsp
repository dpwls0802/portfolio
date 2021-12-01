<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.*"%> 
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<!-- css -->
<link rel="stylesheet" href="/css/main.css" type="text/css" />
<link rel="stylesheet" href="/css/navigationBar.css" type="text/css" />
<!-- 폰트 -->
<link
	href="https://fonts.googleapis.com/css2?family=Azeret+Mono:wght@300&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Roboto+Slab&display=swap"
	rel="stylesheet">

<script src="https://code.jquery.com/jquery-latest.min.js"></script>

<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!--links-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>
<!-- fontawesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css">
<!-- lato font -->
<link
	href="https://fonts.googleapis.com/css?family=Lato:400,700,900&display=swap"
	rel="stylesheet">

</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="/">Travel Record</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="/">Home</a></li>
					<li class="nav-item"><a class="nav-link"
						href="/blog/listPost?page=1&perPageNum=8">blog</a></li>
					<li class="nav-item">
						<sec:authorize access="isAnonymous()">
							<a class="nav-link" href="/member/loginPage">Login</a>
						</sec:authorize>
					</li>
					<li class="nav-item">
						<sec:authorize access="isAnonymous()">
							<a class="nav-link" href="/member/signUp">Sign up</a>
						</sec:authorize>
					</li>
					<li class="nav-item">
						<sec:authorize access="isAuthenticated()">
							<span class="nav-link" style="font-weight:bold;"><sec:authentication property="principal.customName" />님</span>
                        </sec:authorize>
                    </li>
					<li class="nav-item"><sec:authorize access="isAuthenticated()">
							<a class="nav-link" href="/member/logout">Logout</a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<section style="padding-bottom:50px;">
		<div class="container mt-5">
			<div class="row" id="form-inputs">
				<div class="col-md-8 mx-auto d-block" style="margin-top:47px;">
					<div class="card mb-3">
						<div class="row no-gutters">
							<div class="col-md-4">
								<%-- 큰 사진 --%>
								<img src="https://picsum.photos/200/300" height="100%" width="100%" alt="">
							</div>
							<div class="col-md-8">
								<div class="card-body">
									<%-- 프로필 사진 --%>
									<img src="https://img.icons8.com/color/96/000000/user-male-circle.png" class="mx-auto d-block">
									<%-- form 시작 --%>
									<form method="post" action="/member/loginPage" style="padding-top: 20px;">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

										<div class="form-group">
											<div class="row">
												<div class="col-md-1">
													<i class="fas fa-envelope pt-3"></i>
												</div>
												<div class="col-md-11">
													<input type="text" name="username" id="login"
														class="form-control border-top-0 border-left-0 border-right-0"
														placeholder="Name" required>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class="row">
												<div class="col-md-1">
													<i class="fas fa-key pt-3"></i>
												</div>
												<div class="col-md-11">
													<input type="password" name="password" id="password"
														class="form-control border-top-0 border-left-0 border-right-0"
														placeholder="Password" required>
												</div>
											</div>
										</div>

										<div class="form-group form-check text-center">
											<input type="checkbox" class="form-check-input"
												id="exampleCheck1"> <label class="form-check-label"
												for="exampleCheck1">Remember Me</label>
										</div>

										<div class="text-center">
											<button type="submit" class="btn btn-danger" style="width:180px; margin-bottom:10px; padding:8px 12px 8px 12px;">Login</button>
										</div>
										<div class="text-center">
										 	<a style="margin-bottom:15px" class="fadeIn first" href="/oauth2/authorization/kakao">
										 		<img src="/image/kakao_login_medium_narrow.png"> 
										 	</a>
										</div>
										<div class="text-center" style="margin-top:15px;">
											<a class="underlineHover" href="/member/signUp" style="color:black;">Go to Sign up -></a><br>
										</div>

									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</section>
 
	<footer class="pw-5 bg-dark" style="padding:13px 0px 13px 0px; positon:fixed; left:0px; bottom:0px; width:100%;"> 
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Yejin 2021</p>
		</div>
	</footer>
</body>
</html>