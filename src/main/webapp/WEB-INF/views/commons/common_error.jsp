<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/navigationBar.css" type="text/css" />

<title>Travel Record</title>
<style>
	section {
		margin-top: 10px;
	}
	h2{
		font-weight: bold;
	}
</style>
</head>
<body>
	<!-- 내비게이션 바 -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="#!">Travel Record</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="/blog/listPost">blog</a></li>
					<li class="nav-item"><a class="nav-link" href="#!">etc</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Login</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Sign up</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	
	<section class="content container-fluid">
		<h2 style="font-style:bold;">에러, 예외 페이지</h2>
		
		<h3>
			<i class="aa"></i>${exception.getMessage()}</h3>
		<ul>
			<c:forEach items="${exception.getStackTrace()}" var="stack">
				<li>${stack.toString()}</li>
			</c:forEach>
		</ul>
	</section>
</body>
</html>