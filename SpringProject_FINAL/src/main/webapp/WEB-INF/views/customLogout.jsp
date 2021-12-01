<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style>
	
	body {
		text-align: center;
		background-color: rgb(245, 245, 245);
		margin: 0px;
		font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",
		Arial,"Noto Sans",sans-serif,"Apple Color Emoji",
		"Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
	}
	
	form.form-signin {
		width: 100%;
    	max-width: 330px;
    	padding: 15px;
    	margin: auto;
	}
	
	h1 {
		margin-top: 300px;
		font-weight: 400;
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

<title>Logout page</title>
</head>
<body>
	<h1>Do you want to logout?</h1>
	
	<div></div>
	<form method="post" action="/customLogout">
		<button type="submit">Yes</button>
		
		<!-- CSRF 토큰 설정 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<p>Copyright 2020. YEJIN SONG All Rights Reserved.</p>
	</form>
	
</body>
</html>