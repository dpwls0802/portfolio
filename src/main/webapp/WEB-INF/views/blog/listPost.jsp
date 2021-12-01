<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

<link rel="stylesheet" href="/css/navigationBar.css" type="text/css" />
<link rel="stylesheet" href="/css/list.css" type="text/css" />
<link rel="stylesheet" href="/css/listPost.css" type="text/css" />
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
					<li class="nav-item">
						<sec:authorize access="isAnonymous()">
							<a class="nav-link" href="/member/loginPage">Login</a>
						</sec:authorize>
					</li>
					<li class="nav-item">
						<sec:authorize access="isAnonymous()">
							<a class="nav-link" href="/member/signUp">Sign up</a>
						</sec:authorize>
					</li>
					<li class="nav-item">
						<sec:authorize access="isAuthenticated()">
							<span class="nav-link" style="font-weight:bold;"><sec:authentication property="principal.customName" />님</span>
                        </sec:authorize>
                    </li>
					<li class="nav-item"><sec:authorize access="isAuthenticated()">
							<a class="nav-link" href="/member/logout">Logout</a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Section-->
	<section class="py-5">
		<div class="container px-4 px-lg-5 mt-5">

			<h3 id="topName">여행기록 목록</h3>
			<button type="button" id="writeBtn">글쓰기</button>
			<div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
				<c:forEach items="${listPost}" var="board">
					<div class="col mb-5">
						<div class="card h-100">
							<%-- 첨부파일 --%>
							<a class="move uploadedFileList" href="${board.num}">
								<!-- <img class="card-img-top" src="https://dummyimage.com/450x300/dee2e6/6c757d.jpg" alt="..." /> -->
								<img class="fileList" src="/blog/file/display?fileName=${board.files}" />
								
							</a>
							<%-- 첨부파일 --%>
							
							<!-- 디테일-->
							<div class="card-body p-4">
								<div class="text-center">
									<!-- 나라 이름(title)-->
									<h5 class="fw-bolder">${board.title}</h5>
									<!-- 여행 기간(travel date)-->
									<p style="margin-bottom: 13px;">${board.travelDate}</p>
									<!-- 작성자(writer) -->
									<span style="margin-bottom: 3px; /* margin-right:42px; */">${board.writer}</span>
									<span class="badgeView">👁‍🗨 ${board.viewCnt} </span>
									<span class="badgeReply">💬 ${board.replyCnt}</span>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>

	<!-- 페이징 -->
	<div class='pull-right'>
		<ul class='pagination justify-content-center'>
			<c:if test="${pageMaker.hasPrev}">
				<li class='paginate_button previous'>
				<a href="${pageMaker.startPage - 1}" id="pagebutton" class="page-link">이전</a>
				</li>
			</c:if>
			<c:forEach var="idx" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
				<li class="paginate_button ${pageMaker.criteria.page == idx ? 'active' : ''}">
					<a href="${idx}" class="page-link">${idx}</a>
				</li>
			</c:forEach>
			<c:if test="${pageMaker.hasNext}">
				<li class='paginate_button next'>
					<a href="${pageMaker.endPage + 1}" id="pagebutton" class="page-link">다음</a>
				</li>
			</c:if>
		</ul>
	</div>


	<form id='actionForm' action="/blog/listPost" method="get">
		<input type="hidden" name='page' value='${pageMaker.criteria.page}'>
		<input type="hidden" name='perPageNum' value='${pageMaker.criteria.perPageNum}'>
		<<%-- input type="hidden" name="searchType"
			value='<c:out value="${pageMaker.searchType}" />'> <input
			type="hidden" name="keyword"
			value='<c:out value="${pageMaker.keyword}" />'> --%>
	</form>
	<!-- 페이징 끝 -->
	
	<!-- Footer-->
	<footer class="pw-5 bg-dark" style="padding:13px 0px 13px 0px; positon:fixed; left:0px; bottom:0px; width:100%;"> 
		<div class="container">
			<p class="m-0 text-center text-white" style=" text-align:center;">Copyright &copy; Yejin 2021</p>
		</div>
	</footer>
	<script type="text/javascript" src="/js/lightbox.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.0.11/handlebars.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>

	<script id="fileTemplate" type="text/x-handlebars-template">
        		<a data-src="{{fullName}}" href="{{originalFileUrl}}" class="mailbox-attachment-name">
            		<img src="{{imgSrc}}" alt="Attachment" style="margin-bottom:5px;">
        		</a>
	</script>
	
	<!-- Bootstrap core JS-->
	<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/js/fileUpload.js"></script> -->

	<script type="text/javascript" src="/js/fileUpload2.js"></script>
	
	<%-- script --%>
	<script type="text/javascript">
		$(document).ready(function() {
			var result = "${result}";
	        // 게시글 등록, 삭제 알림
	        if (result == "writeSuccess") {
	            alert("게시글이 등록되었습니다.");
	        } else if(result == "modifySuccess") {
	            alert("게시글이 수정되었습니다.");
	        } else if(result == "deleteSuccess") {
	            alert("게시글이 삭제되었습니다.");
	        }
			
			//글쓰기 버튼 클릭 시 이벤트 실행
			$("#writeBtn").on("click", function() {
				self.location = "/blog/writePost";
			});
		
			
				
			/////////////////////////////////페이징//////////////////////////////////////////////
			
			//paginate_button에서 클릭 이벤트로 해당 페이지 띄우기
			var actionForm = $("#actionForm");
			
			$('.paginate_button a').on('click', function(e){
				e.preventDefault();
				console.log('click');
				
				actionForm.find("input[name='page']").val($(this).attr("href"));
				actionForm.submit();
				
			});
			
			//그림 클릭 시 게시글 조회로 이동하도록 이벤트 실행
			$(".move").on("click", function(e){
				e.preventDefault();
				actionForm.append("<input type='hidden' name='num' value='"+$(this).attr("href")+"'>");
				actionForm.attr("action", "/blog/detailPost");
				actionForm.submit();
				
			});
			/////////////////////////////////첨부파일////////////////////
			
			/* var title = "${board.title}";
			if(title == "Hungary") {
				 $(".uploadedFileList").append("<img src='/blog/file/display?fileName=/2021/10/18/s_4a20615d-577a-4a77-a9da-405208820bf2_2017-08-13 02.00.48 1.jpg' />");
			} else {
				$(".uploadedFileList").append("<img class='card-img-top' src='https://dummyimage.com/450x300/dee2e6/6c757d.jpg' alt='...' />");
			} */
			
		});
		
		var num = "${board.num}";
		getFile(num);
		
		//목록 첨부파일 띄우기
		/* var num = "${board.num}";
		var files = "${board.files}";
		printFiles(files);
		getFiles2(num); */
		
		
	</script>

</body>
</html>