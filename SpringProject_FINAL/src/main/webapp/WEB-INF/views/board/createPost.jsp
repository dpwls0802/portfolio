<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp" %>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">신규 게시글 등록</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<form role="form" action="insert.do" method="post">
					<div class="form-group">
						<label>제목 : </label>
						<input class="form-control" type="text" name="title">
					</div>
					<div class="form-group">
						<label>작성자 : </label>
						<input class="form-control" type="text" name="writer" 
							value='<sec:authentication property="principal.party.name"/>' readonly="readonly">
					</div>
					<div class="form-group">
						<label>내용 : </label>
						<textarea class="form-control" name="content" rows="4" cols="50"></textarea>
					</div>
					<input id='registPost' class="btn btn-default" type="submit" value="등록">
					<input class="btn btn-default" type="reset" value="초기화">
					
					<!-- CSRF 토큰 설정 -->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
			</div>
		</div>
	</div>
</div>

<%@include file="../fileUpload/attachFile.jsp" %>

<%@include file="../includes/footer.jsp" %>

<script>
$(document).ready(function() {
	var formObj = $("form[role='form']");

	var masterName = '<c:out value="${masterName}"/>';
	setOperationMode("create");

	$("#registPost").on("click", function(e){ //'등록'버튼을 누르면 이벤트 실행
		e.preventDefault();
		
		//첨부파일 등록
		var strRemainingAttaches = gatherRemainingAttaches(masterName);
		formObj.append(strRemainingAttaches).submit();
	});
});
</script>



















