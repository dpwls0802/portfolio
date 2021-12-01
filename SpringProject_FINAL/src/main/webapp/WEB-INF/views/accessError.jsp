<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page import="java.util.*" %>    
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
	
	img {
		margin-top: 320px;
		margin-bottom: 7px;
	}
	
	h1 {
		font-weight: 400;
		font-size: 30px;
	}
	
	button {
		background-color: rgb(60, 98, 210);
		width: auto;
		color: white;
		margin-top: 22px;
		margin-bottom: 280px;
		padding: 14px;
		font-size: 18px;
		border-radius: 6px;
		border: 0;
		outline: 0;
	}
	
</style>


<title>Access Error Page</title>
</head>
<body>
	<img src = "/resources/img/error.png" width="85" height="85">
	<h1><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}"/></h1>
	<h1><c:out value="${msg}"/></h1>
	<button type="submit" onclick="location.href='/'">Home</button>
	<footer>
		<p>Copyright 2020. YEJIN SONG All Rights Reserved.</p>
	</footer>
	
</body>
</html>