<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html>
<html>
<head>
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