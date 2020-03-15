<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html>
<head>
<link rel="shortcut icon" href="favicon-yj.png.ico"/>
<link rel="icon" href="favicon-yj.png.ico"/>

<style type="text/css">
	body {
		background-color: rgb(245, 245, 245);
		margin: 0px;
	}
	
	ul { 
		list-style : none;
		text-align: right;
		background-color: rgb(60, 98, 210);
		padding-top: 20px;
		padding-bottom: 30px;
		padding-right: 30px;
	}

	ul li {
		display: inline;
		text-transform: uppercase;
		padding: 0 5px;
	}

	ul li a {
		text-decoration: none;
		font-family: sans-serif;
		letter-spacing: 5px;
		word-spacing: -1px;
		font-size: 15px;
		font-weight: 550;
		color: white;
	}

	ul li a:hover {
		text-decoration: underline;
	}
	
	h1 {
		height: 140px;
		font-size: 170px;
		text-align: center;
		font-family: -webkit-pictograph;
	}
	
	h1:after {
        content: "";
        display: block;
        width: 60px;
        border-bottom: 5px solid #bcbcbc;
        margin: 20px auto;
      }
      
	div p {
		text-align: center;
		font-size: 18px;
	}
	div p#p1{
		height: 330px;
		padding-left: 20px;
		padding-right: 20px;
		text-align: center;
		margin-bottom: 30px;
	}

	footer p{
		text-align: center;
		font-size: 14px;
	}

</style>

<title>My Portfolio Project</title>
</head>
<body>
	<ul>
		<li><a href="#">Home</a></li>
		<!-- board/list가 클릭이 되었을 때 BoardController에 들어있는 listAllBoard로 구동된다. -->
		<li><a href="board/list">Board</a></li>
		<li><a href="/contact">Contact</a>
		<li>
		<sec:authorize access="isAnonymous()">
			<a href="/customLogin">Login</a>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">
			<a href="/customLogout">Logout</a>
		</sec:authorize>
		</li>
	</ul>
	
	<div>
		<h1> Welcome!</h1>
		
		<p> This is my portfolio homepage. 
		The homepage is a bulletin board type. </p>
		<p>You must be logged in to register, edit, and delete  postings, comments, 
		and attachments. </p>
		<p id = "p1"> Thank you.    :)</p>
	</div>

	<footer>
		<p>Copyright 2020. YEJIN SONG All Rights Reserved.</p>
	</footer>
	


</body>
</html>
