<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp" %>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시글 수정</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<form id="boardForm" role="form" action="update.do" method="post">
					<div class="form-group">
						<label>Bno : </label>
						<input class="form-control" type="text" name="bno" 
						value='<c:out value="${board.bno}" />' readonly="readonly">
					</div>
					<div class="form-group">
						<label>제목 : </label>
						<input class="form-control" type="text" name="title" 
						value='<c:out value="${board.title}"/>' >
					</div>
					<div class="form-group">
						<label>작성자 : </label>
						<input class="form-control" type="text" name="writer" 
						value='<c:out value="${board.writer}" />' >
					</div>
					<div class="form-group">
						<label>내용 : </label>
						<textarea class="form-control" name="content" rows="4" cols="50">
							<c:out value="${board.content}"/>
						</textarea>
					</div>
					
					<!-- '수정, 삭제'를 하려면 로그인이 되어있어야 하며, 작성자와 수정, 삭제하는 사람이 같아야 한다. -->
					<sec:authentication property="principal" var="customUser"/>
					<sec:authorize access="isAuthenticated()">
						<c:if test="${customUser.party.name eq board.writer}">
							<button type="submit" data-oper='modify' class="btn btn-default">수정</button>
							<button type="submit" data-oper="remove" class="btn btn-danger">삭제</button>
						</c:if>
					</sec:authorize>

					<button type="submit" data-oper="list" class="btn btn-info">목록</button>
					
					<input type="hidden" name='pageNum' value='${pageMaker.pageNum}'>
					<input type="hidden" name='amount' value='${pageMaker.amount}'>
					<input type="hidden" name="searchType" value='<c:out value="${pageMaker.searchType}" />'>
					<input type="hidden" name="keyword" value='<c:out value="${pageMaker.keyword}" />'>
					
					<!-- CSRF 토큰 설정 -->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
				</form>
			</div>
		</div>
	</div>
</div>

<%@include file="../fileUpload/attachFile.jsp" %>

<%@include file="../includes/footer.jsp" %>
	
<script type="text/javascript">
$(document).ready(function(){
	setOperationMode("modify");
	var masterName = '<c:out value="${masterName}"/>';

	//Json Message를 EL 표현으로 해석하여 객체 생성 
	var jsonOfAttachList = ${board.jsonOfListAttach};

	showAttachFileList(jsonOfAttachList);

	var boardForm = $("#boardForm");
	$("#boardForm button").on("click", function(e){ //'수정', '삭제', '목록' 버튼을 누르면 
		e.preventDefault(); //이벤트 실행
		
		var oper = $(this).data("oper");
		
		if (oper === "remove") {
			boardForm.attr("action", "deletePosting");
		} else if (oper === "list") {
			boardForm.attr("action", "/board/list").attr("method", "get");
			
			//복사
			var inputPN = $("input[name='pageNum']").clone();
			var inputAmt = $("input[name='amount']").clone();
			var inputSt = $("input[name='searchType']").clone();
			var inputK = $("input[name='keyword']").clone();
			
			//비우기
			boardForm.empty();
			
			//다시 추가
			boardForm.append(inputPN);
			boardForm.append(inputAmt);
			boardForm.append(inputSt);
			boardForm.append(inputK);
		} else if (oper === "modify") {
			var strRemainingAttaches = gatherRemainingAttaches(masterName);
			boardForm.append(strRemainingAttaches);
		}
		boardForm.submit();
	});
});
</script>	
	
	
	
	
	
	
	
	
	
	