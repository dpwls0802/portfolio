<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
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
<link rel="stylesheet" href="/css/detailPost.css" type="text/css" />
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
	<div class="content-wrapper">
		<div class="container mt-5">
			<div class="row">
				<div class="col-lg-8">
					<!--게시글 -->
					<article>
						<header class="mb-4">
							<div class="text-muted mb-2" id="country">나라 :
								${board.title}</div>
							<div class="text-muted mb-2">작성자 : ${board.writer}</div>
							<div class="text-muted mb-2">여행기간 : ${board.travelDate}</div>
							<div class="text-muted mb-2" id="writeDate">
								작성일 :
								<fmt:formatDate value="${board.writeDate}"
									pattern="yyyy-MM-dd HH:mm" />
							</div>
							<%-- 해시태그 --%>
							<ul id="tag-list" class="text-muted mb-2" style="margin-left: 0;">
								<%-- <li id='tag-list2' value="${board.tags}">#${board.tags}</li> --%>
							</ul>
							<!-- 
							<a class="badge bg-secondary text-decoration-none link-light"
								href="#!">Web Design</a> <a
								class="badge bg-secondary text-decoration-none link-light"
								href="#!">Freebies</a> -->
							<%-- 해시태그 --%>
						</header>

						<%--엄로드 파일 정보 영역 --%>
						<figure class="mb-4">
							<div class="form-group uploadFiles">
								<ul class="mailbox-attachments clearfix uploadedFileList"
									style="padding-left: 0;">
								</ul>
							</div>
						</figure>
						<%--엄로드 파일 정보 영역 --%>

						<section class="mb-5">
							<p class="fs-5 mb-4">${board.content}</p>
						</section>
					</article>

					<form id="operform" action="/blog/modifyPost" method="get">
						<input type="hidden" id="num" name="num" value="${board.num}">
						<input type="hidden" name='page' value='${criteria.page}'>
						<input type="hidden" name='perPageNum'
							value='${criteria.perPageNum}'>
					</form>

					<form role="form" method="post" id="pagingForm">
						<input type="hidden" id="num" name="num" value="${board.num}">
						<input type="hidden" name='page' value='${criteria.page}'>
						<input type="hidden" name='perPageNum'
							value='${criteria.perPageNum}'>
					</form>
					<!-- 버튼들 -->
					<div class="form-group">
						<sec:authentication property="principal" var="pinfo" />
						<sec:authorize access="isAuthenticated()">
							<%--작성자일 경우에만  수정, 삭제 버튼 보임 --%>
							<c:if test="${pinfo.customName eq board.writer}">
								<button class="btn" data-oper="modifyPost" id='modifyBtn'>수정</button>
								<button class="btn" data-oper="removePost" id="removeBtn">삭제</button>
							</c:if>
						</sec:authorize>
						<button class="btn" data-oper="listPost" id='listBtn'>목록</button>
					</div>

					<%-- 댓글 부분--%>
					<section class="mb-5">
						<div class="card bg-light">
							<div class="card-body" style="padding: 10px 16px 10px 16px;">
								<!-- 댓글 작성 부분-->
								<form class="mb-4">
									<div>
										<p style="margin-bottom: 3px; font-weight: bold;">✏댓글 작성</p>

										<sec:authorize access="isAuthenticated()">
											<input id="newReplyWriter2" readonly="readonly"
												value='<sec:authentication property="principal.customName"/>'>
										</sec:authorize>
										<%-- 로그인만 멤버만 댓글 달 수 있다 --%>
										<sec:authorize access="isAuthenticated()">
											<button type="button" style="margin-bottom: 5px;"
												id="writeReplyBtn" class='btn btn-primary btn-xs pull-right'>댓글
												등록</button>
										</sec:authorize>
									</div>
									<textarea class="form-control" id="newReplyText2" rows="3"
										placeholder="댓글을 입력해주세요"></textarea>
								</form>

								<!-- 댓글 목록 부분 -->
								<p style="margin-bottom: 10px; font-weight: bold;">📄댓글 목록
									${pageMaker.totalCnt}</p>
								<ul class="replyList"
									style="padding-left: 1px; list-style: none;">
									<%-- <li class="left clearfix" data-rnum="1">
									<div>
									<span> 
										<strong id="replyWriter" style="margin-right: 410px;">작성자 이름: ${reply.replyWriter}</strong> 
										<a onclick="modifyReplyBtn" style="font-size: 15px;">수정</a> 
										<a onclick="deleteReplyBtn" style="font-size: 15px;">삭제</a>
									</span>
									</div>
										<div>작성일 :${reply.writeDate}</div>
										<div>댓글 내용 예시 : ${reply.replyText}</div>
										<hr>
									</li> --%>
								</ul>
								<!-- 댓글 목록 끝 -->

								<!-- 댓글 페이징 -->
								<div class='pull-right'>
									<ul class='pagination justify-content-center'>
										<c:if test="${pageMaker.hasPrev}">
											<li class='paginate_button previous'><a
												href="${pageMaker.startPage - 1}" id="pagebutton"
												class="page-link">이전</a></li>
										</c:if>
										<c:forEach var="idx" begin="${pageMaker.startPage}"
											end="${pageMaker.endPage}">
											<li
												class="paginate_button ${pageMaker.criteria.page == idx ? 'active' : ''}">
												<a href="${idx}" class="page-link">${idx}</a>
											</li>
										</c:forEach>
										<c:if test="${pageMaker.hasNext}">
											<li class='paginate_button next'><a
												href="${pageMaker.endPage + 1}" id="pagebutton"
												class="page-link">다음</a></li>
										</c:if>
									</ul>
								</div>
							</div>
							<!-- 댓글 끝 -->
							<div id='replyPageFooter'></div>
						</div>
					</section>
				</div>

				<!-- Side widgets-->
				<div class="col-lg-4">
					<!-- Search widget-->
					<div class="card mb-4">
						<div class="card-header">Search</div>
						<div class="card-body">
							<div class="input-group">
								<input class="form-control" type="text"
									placeholder="Enter search term..."
									aria-label="Enter search term..."
									aria-describedby="button-search" />
								<button class="btn btn-primary" id="button-search" type="button">Go!</button>
							</div>
						</div>
					</div>
					<!-- Categories widget-->
					<div class="card mb-4">
						<div class="card-header">Categories</div>
						<div class="card-body">
							<div class="row">
								<div class="col-sm-6">
									<ul class="list-unstyled mb-0">
										<li><a href="#!">Web Design</a></li>
										<li><a href="#!">HTML</a></li>
										<li><a href="#!">Freebies</a></li>
									</ul>
								</div>
								<div class="col-sm-6">
									<ul class="list-unstyled mb-0">
										<li><a href="#!">JavaScript</a></li>
										<li><a href="#!">CSS</a></li>
										<li><a href="#!">Tutorials</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<!-- Side widget-->
					<div class="card mb-4">
						<div class="card-header">Side Widget</div>
						<div class="card-body">You can put anything you want inside
							of these side widgets. They are easy to use, and feature the
							Bootstrap 5 card component!</div>

					</div>
				</div>
			</div>
		</div>
	</div>
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
    	<li data-src="{{fullName}}" style="list-style:none; float:left; margin-right: 5px;">
        	<a href="{{originalFileUrl}}" class="mailbox-attachment-name">
            	<img src="{{imgSrc}}" alt="Attachment" style="margin-bottom:5px;">
        	</a>
    	</li>
	</script>

	<!-- <div class="mailbox-attachment-info" style="background-color:#ced4da; padding:1px 3px 3px 3px;">
           	 <a href="{{originalFileUrl}}" class="mailbox-attachment-name" style="color:black; text-decoration : none;" >
               		<i class="fa fa-paperclip"></i> {{originalFileName}}
            </a>
        </div> -->
	<%-- 첨부파일 JS --%>
	<script type="text/javascript" src="/js/fileUpload.js"></script>
	<script type="text/javascript" src="/js/tag.js"></script>

	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>

	<!-- js 시작 -->
	<script>
	
		//////////////////////////////////////////////게시글//////////////////////////////////////////////////
		$(document).ready(function() {
			var operform = $("#operform");
			//수정 버튼 클릭 시 이벤트 실행
			$("button[data-oper='modifyPost']").on("click", function() {
				operform.attr("action", "/blog/modifyPost");
				operform.submit();
			});
			
			//삭제 버튼 클릭 시 이벤트 실행  + 댓글이 달린 게시글은 삭제 X, 첨부파일 삭제
			$("button[data-oper='removePost']").on("click", function() {
				//댓글이 달린 게시글 삭제처리 방지
				var replyCnt = $(".replyList").html().replace(/[^0-9]/g,"");
				if(replyCnt >= 1) {
					alert("댓글이 달린 게시글은 삭제할 수 업습니다.");
					return;
				}
				
				//첨부파일명들 배열에 저장
				var arr = [];
				$(".uploadedFileList li").each(function() {
					arr.push($(this).attr("data-src"));
				});
				
				//첨부파일 삭제 요청
				if(arr.length > 0){
					$.post("/blog/file/deleteAll", {files:arr}, function() {
					});
				}
				
				//삭제처리
				operform.attr("action", "/blog/removePost");
				operform.attr("method", "post");
				operform.submit();
			});

			//목록 버튼 클릭 시 이벤트 실행
			$("button[data-oper='listPost']").on("click", function() {
				operform.find("#num").remove();
				operform.attr("action", "/blog/listPost");
				operform.submit();
			});
		});
		var num = "${board.num}";
		
		getFiles(num); //첨부파일 목록
		
		//////////////////////////////////////////해시태그///////////////////////
		var tags = "${board.tags}";
		var tagSplit = tags.split(",");
		
		if(tags == "") {
			$("#tag-list").append("");
		} else {
			for(i=0; i<tagSplit.length; i++) {
				$("#tag-list").append("<li id='tag-list2' value='${board.tags}'>#"+tagSplit[i]+"</li>");
			}
		}

		/////////////////////////////////////댓글////////////////////////
		//댓글 csrf 토큰값 전달하도록 추가
		/* var csrfHeaderName = "${_csrf.headerName}";
		var csrfTokenValue = "${_csrf.token}";
		
		$(document).ajaxSend(function(e, xhr, options){
			xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
		}); */
		
		
		
		$(document).ready(function(){
			showReplyList(); //댓글 목록
		});

		//댓글 목록
		function showReplyList(){
			//var num = param.num;
			var numValue = ${board.num};
			var page = ${criteria.page} || 1;
		
			var url = "/reply/replyListPaging/" + numValue + "/" + page;
			var paramData = {"num" : "${board.num}", "page" : "${criteria.page}"};
			$.ajax({
            	type: 'get',
            	url: url,
            	data: paramData,
            	dataType: 'json',
            	success: function(result) {
               		var str = "";
				if(result.length < 1){
						
				} else {
					$(result).each(function(){
						str += "<li class='left clearfix' id='rnum" + this.rnum +"'>";
						str += "	<div><span><strong id='replyWriter'>"+ this.replyWriter +"</strong>";
						str += "	<a href='javascript:void(0)' id='modifyReplyBtn' onclick='fnmodifyReply(" + this.rnum + ", \"" + this.replyWriter + "\", \"" + this.replyText + "\");' style='padding-right:5px'>수정</a>";
						str += "	<a href='javascript:void(0)' id='deleteReplyBtn' onclick='fndeleteReply(" + this.rnum + ");'>삭제</a></span></div>";
						str += "	<div id='writeDate'>"+ this.writeDate +"</div>";
						str += "	<div id='replyText'>"+ this.replyText +"</div><hr></li>";
					
					});
				}
				
				$(".replyList").html(str);
            }
		});//ajax end
	}
	//댓글 삭제는 로그인한 사용자와 같을 때만 삭제 가능(211018 추가)
	var replyWriter = "${board.writer}";
	$("#deleteReplyBtn").on("click", function() {
		if(!replyWriter) {
			alert("로그인한 사용자만 삭제가 가능합니다.");
			return;
		}
		
		var originalWriter = "${reply.replyWriter}";
		
		if(replyWriter != originalWriter) {
			alert("자신이 작성한 댓글만 삭제가 가능합니다.");
			return;
		}
	});
	
	//댓글 페이징(추가)
	//paginate_button에서 클릭 이벤트로 해당 페이지 띄우기
			var pagingForm = $("pagingForm");
			
			$('.paginate_button a').on('click', function(e){
				e.preventDefault();
				console.log('click');
				
				pagingForm.find("input[name='page']").val($(this).attr("href"));
				pagingForm.submit();
				
			});
	
	//댓글 등록
	$(document).on("click", "#writeReplyBtn", function(){
		var replyWriter = $("#newReplyWriter2").val();
		var replyText = $("#newReplyText2").val();
		var numValue = ${board.num};
		
		var reply= {replyWriter:replyWriter, replyText:replyText, num:numValue};
		
		$.ajax({
			type : 'post',
			url : '/reply/writeReply',
			data : JSON.stringify(reply), // reply를 json문자열로 변환
			contentType : "application/json;charset=utf-8",
			success : function(result) {
				alert("댓글이 등록되었습니다.");
				$("#newReplyWriter2").val("");
				$("#newReplyText2").val("");
				showReplyList(1);
			},
			error : function(error) {
				console.log("에러 : " + error);
			}
		});
		
	});
			
			//댓글 수정 창
			 function fnmodifyReply(rnum, replyWriter, replyText) {
				var str="";
				
				str += "<li class='left clearfix' id='rnum" + rnum +"'>";
				str += "	<div><span><strong id='replyWriter'>" + replyWriter + "</strong>";
				str += "	<a href='javascript:void(0)' id='modifyReplyBtn' onclick='fnmodifySaveReply(" + rnum + ", \"" + replyWriter + "\")' style='padding-right:5px'>저장</a>";
				str += "	<a href='javascript:void(0)' id='deleteReplyBtn' onclick='showReplyList()'>취소</a></span></div>";
				str += "	<textarea class='form-control' id='editText' rows='3'>"+replyText+"</textarea><hr></li>";
			
				$("#rnum" + rnum).replaceWith(str);
				$("#rnum" + rnum + "#editText").focus();
			} 
			
			
			
			//진짜 수정
			function fnmodifySaveReply(rnum, replyWriter) {
				var editText = $('#editText').val();
				var reply= {rnum:rnum, replyText:editText};
				
				$.ajax({
					type : 'put',
					url : '/reply/' + rnum,
					data : JSON.stringify(reply),
					contentType : "application/json;charset=utf-8",
					success : function(result) {
						console.log(result);
						alert("댓글이 수정되었습니다.");
						showReplyList(1);
					},
					error : function(error) {
						console.log("에러 : " + error);
					}
				});
			}
			
			
			//댓글 삭제
			function fndeleteReply(rnum, replyWriter) {
				var paramData = {"rnum": rnum, "replyWriter":replyWriter};
				//var replyNum = ${reply.rnum};
				
				$.ajax({
					type : 'delete',
					url : '/reply/' + rnum,
					data : paramData,
					success : function(result) {
						alert("댓글이 삭제되었습니다.");
						showReplyList(1);
					},
					error : function(error) {
						console.log("에러 : " + error);
					}
				});
				
			} 
			
		////////////////////////////////////////첨부파일//////////////////////////////////////////	
	
	</script>

</body>
</html>