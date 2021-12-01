<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style>
	body {
		display: block;
		text-align: center;
		background-color: rgb(245, 245, 245);
		margin: 0px;
		font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",
		Arial,"Noto Sans",sans-serif,"Apple Color Emoji",
		"Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
	}
	
	h1 {
		font-weight: 400;
		font-size: 30px;
		margin-top: 320px;
	}
	
	button {
		background-color: rgb(60, 98, 210);
		width: auto;
		color: white;
		margin-top: 22px;
		padding: 14px;
		font-size: 18px;
		border-radius: 6px;
		border: 0;
		outline: 0;
		margin-bottom: 430px;
	}

</style>

<title>모두가 접근하는 페이지</title>
</head>
<body>
	<h1>All 페이지입니다.</h1>
	
	<button type="submit" onclick="location.href='/'">홈으로</button>
	<p>Copyright 2020. YEJIN SONG All Rights Reserved.</p>
<%-- 	
	<sec:authorize access="isAnonymous()">
		<a href="/customLogin">로그인</a>
	</sec:authorize>

	<sec:authorize access="isAuthenticated()">
		<a href="/customLogout">로그아웃</a>
	</sec:authorize> --%>

</body>
</html>