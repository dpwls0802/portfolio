<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />

<link rel="stylesheet" href="/css/navigationBar.css" type="text/css" />
<link rel="stylesheet" href="/css/modifyPost.css" type="text/css" />
<link rel="stylesheet" href="/css/lightbox.css" type="text/css" />

<title>Travel Record</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>

</head>
<body>
	<!-- 내비게이션 바 -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="/">Travel Record</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="/">Home</a></li>
					<li class="nav-item"><a class="nav-link"
						href="/blog/listPost?page=1&perPageNum=8">blog</a></li>
					<li class="nav-item"><sec:authorize access="isAnonymous()">
							<a class="nav-link" href="/member/loginPage">Login</a>
						</sec:authorize></li>
					<li class="nav-item"><sec:authorize access="isAnonymous()">
							<a class="nav-link" href="/member/signUp">Sign up</a>
						</sec:authorize></li>
					<li class="nav-item"><sec:authorize access="isAuthenticated()">
							<span class="nav-link" style="font-weight: bold;"><sec:authentication
									property="principal.customName" />님</span>
						</sec:authorize></li>
					<li class="nav-item"><sec:authorize access="isAuthenticated()">
							<a class="nav-link" href="/member/logout">Logout</a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</nav>



	<!-- 페이지 내용 -->
	<form role="form" action="/blog/modifyPost" method="post"
		id="modifyForm">
		<div class="form-group">
			<h3 style="margin-bottom: 20px;">여행기록 수정</h3>

			<input type="hidden" id=num name="num" value="${board.num}">

			<label class="labels">작성자 : </label> <input class="form-control"
				type="text" name="writer"
				value='<sec:authentication property="principal.customName"/>'>
		</div>
		<div class="form-group">
			<label class="labels">나라 : </label> <input class="form-control"
				type="text" name="title" placeholder="첫 글자는 대문자인 영어로만 작성해주세요"
				value="${board.title}">
		</div>
		<div class="form-group">
			<label class="labels">여행기간 : </label> <input class="form-control"
				type="text" name="travelDate"
				placeholder="0000-00-00~0000-00-00으로 입력해주세요"
				value="${board.travelDate}">
		</div>
		<div class="form-group">
			<label class="labels">내용 : </label>
			<textarea class="form-control" name="content" rows="2" cols="50">${board.content}</textarea>
		</div>
		<%-- 해시태그 --%>
		<div class="form-group" style="margin-bottom: 0px;">
			<p style="margin-bottom: 10px;">해시태그</p>
			<input type="hidden" value="" name="tags" id="rdTag" />
		</div>

		<ul id="tag-list" class="form-group"></ul>

		<div class="form-group">
			<input type="text" id="tag" class="form-control" size="7"
				placeholder="엔터로 해시태그를 등록해주세요." style="width: 300px;" />
		</div>
		<%-- 해시태그 --%>


		<%-- 첨부파일 영역 --%>
		<div class="form-group">
			<div class="fileDrop"
				style="border: 5px solid #ced4da; border-radius: 0.25rem;">
				<br> <br>
				<p class="text-center">📎첨부파일을 드래그해주세요</p>
				<br> <br>
			</div>
		</div>

		<div class="form-group" style="border: 0px;">
			<div>
				<hr style="color: white;">
			</div>
			<ul class="mailbox-attachments clearfix uploadedFileList"
				style="padding-left: 0;"></ul>
		</div>
		<%-- 첨부파일 영역 --%>
		<!-- 버튼들 -->
		<div class="form-group">
			<sec:authentication property="principal" var="pinfo" />
			<sec:authorize access="isAuthenticated()">
				<%--작성자일 경우에만  수정 등록, 초기화 버튼 보임 --%>
				<c:if test="${pinfo.customName eq board.writer}">
					<button class="btn" type="submit" id='modifyBtn'>수정 등록</button>
					<button class="btn" type="reset" id="resetBtn">초기화</button>
				</c:if>
			</sec:authorize>
			<button class="btn" data-oper="listPost" id='listBtn'>목록</button>
		</div>

		<input type="hidden" name='page' value='${criteria.page}'> <input
			type="hidden" name='perPageNum' value='${criteria.perPageNum}'>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}">
	</form>



	<!-- Footer-->
	<footer class="pw-5 bg-dark"
		style="padding: 13px 0px 13px 0px; positon: fixed; left: 0px; bottom: 0px; width: 100%;">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Yejin 2021</p>
		</div>
	</footer>

	<script type="text/javascript" src="/js/lightbox.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>


	<script id="fileTemplate" type="text/x-handlebars-template">
    	<li style="list-style:none; float:left; margin-right: 3px;">
        	<span class="mailbox-attachment-icon has-img">
            	<img src="{{imgSrc}}" alt="Attachment">
        	</span>
        <div class="mailbox-attachment-info" style="background-color:#ced4da; padding:1px 3px 3px 3px;">
            <a href="{{originalFileUrl}}" class="mailbox-attachment-name" style="color:black; text-decoration : none;" >
                <i class="fa fa-paperclip"></i> {{originalFileName}}
            </a>
            <a href="{{fullName}}" class="btn btn-default btn-xs pull-right delBtn" style="float:right; padding:0; margin-left:10px;">
                <img class="fa fa-fw fa-remove" src="/image/x-mark.png" width="15" height="15"></img>
            </a>
        </div>
    	</li>
	</script>

	<script type="text/javascript" src="/js/fileUpload.js"></script>
	<script type="text/javascript" src="/js/tag2.js"></script>

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>

	<script type="text/javascript">
		var num = "${board.num}";
		//첨부파일 csrf 토큰값 전달하도록 추가	
		var csrfHeaderName = "${_csrf.headerName}";
		var csrfTokenValue = "${_csrf.token}";

		$(document).ready(function() {
			var formObj = $("form");

			//목록 버튼 클릭 시 이벤트 실행
			$("button[data-oper='listPost']").on("click", function() {
				formObj.attr("action", "/blog/listPost");
				formObj.attr("method", "get");

				//수정페이지에서 수정 안하고 목록으로 돌아갈 때 원래 있던 페이지로 돌아가기
				var pageTag = $("input[name='page']").clone();
				var parPageNumTag = $("input[name='perPageNum']").clone(); //form 태그에서 필요힌 부분만 잠시 복사해 보관, 

				formObj.empty(); //form 태그의 모든 내용 지움

				formObj.append(pageTag);
				formObj.append(parPageNumTag); //필요한 태그들만 추가해 목록 호출하는 형태

				formObj.submit();

			});

			var num = "${board.num}"; //현재 게시글 번호
			//첨부파일 삭제 버튼 클릭 시 이벤트 처리
			$(document).on("click", ".delBtn", function(e) {
				e.preventDefault();
				if (confirm("삭제하시겠습니까? 삭제된 파일은 복구할 수 없습니다.")) {
					var that = $(this);
					deleteFileModPage(that, num);
				}
			});

		});
		//첨부파일 목록 호춯
		getFiles(num);

		$("#modifyForm").submit(function(e) {
			e.preventDefault();
			var that = $(this);
			filesSubmit(that);
		});
		
		//////////////////////////////////////////해시태그///////////////////////
		var tags = "${board.tags}";
		var tagSplit = tags.split(",");

		if (tags == "") {
			$("#tag-list").append("");
		} else {
			for (i = 0; i < tagSplit.length; i++) {
				$("#tag-list").append(
						"<li id='tag-list2' value='${board.tags}'>#"
								+ tagSplit[i] + "</li>");
			}
		}
	</script>


</body>
</html>