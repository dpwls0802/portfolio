<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sec:authentication property="principal.party.name"/>님 어서오세요</title>
</head>
<body>
 관리자만이 쓰세요
 	<p>principal : <sec:authentication property="principal"/></p>
 	<p>PartyVO : <sec:authentication property="principal.party"/></p>
 	<p>사용자 이름: <sec:authentication property="principal.party.name"/></p>
 	<p>사용자 아이디: <sec:authentication property="principal.party.userId"/></p>
 	<p>사용자 권한 목록: <sec:authentication property="principal.party.listAuth"/></p>
 	
 	<a href="/customLogout">Logout</a>
</body>
</html>