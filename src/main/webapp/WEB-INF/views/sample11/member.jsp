<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>사용자만 접근 가능</h1>
	
	
<div sec:authorize="hasRole('USER')"> 사용자 권한 가짐</div>
<div sec:authorize="hasRole('MANAGER')"> 매니저 권한 가짐</div>
<div sec:authorize="hasRole('ADMIN')"> 관리자 권한 가짐</div>

<!-- <div sec:authorize = "isAuthenticated()"> -->
<div sec:authorize access="isAuthenticated()">
	인증받은 사용자만 이 문장을 볼 수 있습니다.
</div>

인증받은 사용자 이름 : <div sec:authentication="name"></div>
인증받은 사용자 권한 : <div sec:authentication="principal.authorities"></div>
</body>
</html>