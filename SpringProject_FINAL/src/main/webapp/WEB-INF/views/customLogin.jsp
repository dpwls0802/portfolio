<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>

<style type="text/css">
	body {
		display: flex;
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
    	margin-top: 100px;
	}
	
	h1 {
		font-weight: 400;
	}
	
	#username{
		width: 100%;
		height: auto;
		padding: 14px;
		font-size: 16px;
		border-radius: 3px;
		border: 1px solid #ced4da;
		outline: 0;
	}
	
	#password {
		width: 100%;
		height: auto; 
		padding: 14px;
		font-size: 16px;
		border-radius: 3px;
		border: 1px solid #ced4da;
		outline: 0;
		margin-bottom: 20px;
	}

	button {
		background-color: rgb(60, 98, 210);
		width: 100%;
		color: white;
		margin-top: 22px;
		padding: 14px;
		font-size: 18px;
		border-radius: 6px;
		border: 0;
		outline: 0;
	}
	
	footer p{
		margin-top: 400px;
		text-align: center;
		font-size: 14px;
	}
	
	
</style>

<title>Login page</title>
</head>
<body>
	
	<form class = "form-signin" method="post" action="/login">
		<img src = "/resources/img/login.png" width="72" height="72">  
		<h1>Please sign in</h1>
		
		<h2><c:out value="${err}"/></h2>
		<h2><c:out value="${logout}"/></h2>
		
		<label for="username"></label> 
		<input type="text" id="username" name="username" placeholder="Username">
				
		<label for="password"></label> 
		<input type="password" id="password" name="password" placeholder="Password">
		
		<div><input type="checkbox" name="remember-me"> 자동 로그인 </div>
		
		<button type="submit">Sign in</button>
		
		<!-- CSRF 토큰 설정 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		
		<footer>
			<p>Copyright 2020. YEJIN SONG All Rights Reserved.</p>
		</footer>
	</form>
	
	
	

	
</body>
</html>





