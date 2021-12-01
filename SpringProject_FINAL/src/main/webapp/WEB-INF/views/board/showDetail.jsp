<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">게시글 상세</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="form-group">
					<label>Bno : </label> <input class="form-control" type="text"
						name="bno" value='<c:out value="${board.bno}" />' readonly="readonly">
				</div>
				<div class="form-group">
					<label>제목 : </label> <input class="form-control" type="text"
						name="title" value='<c:out value="${board.title}"/>' readonly="readonly">
				</div>
				<div class="form-group">
					<label>작성자 : </label> <input class="form-control" type="text"
						name="writer" value='<c:out value="${board.writer}" />' readonly="readonly">
				</div>
				<div class="form-group">
					<label>내용 : </label>
						<textarea class="form-control" name="content" rows="4" cols="50">
							<c:out value="${board.content}" />
						</textarea>
				</div>
				
				<!-- '수정'을 하려면 로그인이 되어있어야 하며, 작성자와 수정하는 사람이 같아야 한다. -->
				<sec:authentication property="principal" var="customUser"/>
				<sec:authorize access="isAuthenticated()">
					<c:if test="${customUser.party.name eq board.writer}">
						<button data-oper='modify' class="btn btn-default">수정</button>
					</c:if>
				</sec:authorize>
				
				<button data-oper='list' class="btn btn-info">목록</button>

				<!-- 264쪽 : 나중에 다양한 상황처리 -->
				<form id="operForm" method="get">
					<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}" />'> 
					<input type="hidden" name='pageNum' value='${pageMaker.pageNum}'>
					<input type="hidden" name='amount' value='${pageMaker.amount}'>
					<input type="hidden" name="searchType" value='<c:out value="${pageMaker.searchType}" />'> 
					<input type="hidden" name="keyword" value='<c:out value="${pageMaker.keyword}" />'>
				</form>
			</div>
		</div>

<!-- 첨부파일 -->		
<%@include file="../fileUpload/attachFile.jsp" %>
		
		<div class="panel panel-default">
		
			<!-- 댓글 조회 수정 삭제 -->
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw">Reply</i>
				
				<!-- 댓글을 달려면 로그인이 되어있어야  한다. -->
				<sec:authorize access="isAuthenticated()">
					<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>댓글달기</button>
				</sec:authorize>
				
				<!-- 댓글 폼 부분 -->
				<div data-rno="" data-bno="" id="replyForm" style="display:none">
					<div class="modal-body">
						<div class="form-group">
							<label>Reply</label> <input class="form-control" name='reply'
								value='New Reply!!!!'>
						</div>
						<div class="form-group">
							<label>Replyer</label> <input class="form-control" name='replyer'
								value='replyer' readonly="readonly">
						</div>
						<div class="form-group">
							<label>Reply Date</label> <input class="form-control"
								name='replyDate' value=''>
						</div>
					</div>
					
					<div class="modal-footer">
						<button id='modalModBtn' type="button" class="btn btn-warning">수정</button>
						<button id='modalRemoveBtn' type="button" class="btn btn-danger">삭제</button>
						<button id='modalRegisterBtn' type="button" class="btn btn-primary">등록</button>
						<button id='modalCloseBtn' type="button" class="btn btn-default">닫기</button>
					</div>
				</div>
			</div>
			<!-- End of 댓글 조회 수정 삭제 -->

			<!-- 댓글 목록 보여주기 -->
			<div class="panel-body">
				<ul id="replyListDisplay">
					<!-- 
					<li class="left clear-fix" data-rno='12' data-bno = '' >
						<div>
							<div class="header">
								<strong class="primary-font">홍길동</strong>
								<small class="pull-right text-muted">2019-01-01</small>
							</div>
							<p>나 댓글</p>
						</div>
					</li>
				 -->
				</ul>
			</div>
			<!-- End of 댓글 목록 보여주기 -->
		</div>
		<div id='replyPageFooter'></div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>

<!-- ?20191202 변경 시 재로드를 위하여 -->
<script type="text/javascript" src="/resources/js/replyClientService.js?2019120201" ></script>

<script type="text/javascript" charset="UTF-8">
var csrfHeaderName;
var csrfTokenValue;

$(document).ajaxSend(function(e, xhr, options){
	xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
});

	$(document).ready(function() {
		setOperationMode("showDetail"); 

		//Json Message를 EL 표현으로 해석하여 객체 생성 
		var jsonOfAttachList = ${board.jsonOfListAttach};

		showAttachFileList(jsonOfAttachList);

		var bnoVal = '<c:out value="${board.bno}" />';
		var ul4List = $("#replyListDisplay");
		var PAGE_SIZE = 5;

		var curUser = null;
						
		<sec:authorize access="isAuthenticated()">
			curUser = '<c:out value="${customUser.party.name}" />';
		</sec:authorize>
		
		//Security 적용 이후 Ajax의 post에서 정상 작동을 위해 csrf 추가해 줌
		csrfHeaderName = "${_csrf.headerName}";
		csrfTokenValue = "${_csrf.token}";
						
		var curPageNum = 1;
		showReplyList(curPageNum);

		function showReplyList(targetPage) {
			replyClientService
					.getList(
							{	//call argument - 인자
								bno : bnoVal,
								page : targetPage || 1,
								pageSize : PAGE_SIZE
							},
							function(listWithTotCnt) {
								var totCnt = listWithTotCnt.first;
								var list = listWithTotCnt.second;
								var liHtml = "";
								
								//-1은 마지막 페이지 목록 조회를 처리하기
								if (targetPage == -1) {
									targetPage = Math.ceil(totCnt / (PAGE_SIZE * 1.0));
									showReplyList(targetPage);
									return;
								}

								for (var i = 0, len = list.length || 0; i < len; i++) {
									liHtml += "<li class='left clear-fix' data-rno='" + list[i].rno + "' data-bno='" + list[i].bno + "' data-replyer='" + list[i].replyer + "'>";
									liHtml += "<div><div class='header'><strong class='primary-font'>";
									liHtml += list[i].replyer;
									liHtml += "</strong><small class='pull-right text-muted'>";
									liHtml += replyClientService
											.displayTime(list[i].updatedate);
									liHtml += "</small></div><p>";
									liHtml += list[i].content;
									liHtml += "</p></div></li>";
								}
								ul4List.html(liHtml);
								showReplyPageFooter(totCnt);
							}, function(er) {
								alert("Error : " + er);
							});
		}
		
		//댓글 페이징 네이터 처리하기
		var replyPageFooter = $("#replyPageFooter");
		function showReplyPageFooter(totCnt) {
			var endPage = Math.ceil(curPageNum / (PAGE_SIZE * 1.0)) * PAGE_SIZE;
			var startPage = endPage - PAGE_SIZE + 1 ;
			var realEnd = Math.ceil(totCnt / (PAGE_SIZE * 1.0));
			endPage = realEnd < endPage ? realEnd : endPage;
			var hasPrev = startPage > 1;
			var hasNext = realEnd > endPage;
			
			var str4PageNav = "<ul class='pagination pull-right'>";
			if (hasPrev) {
				str4PageNav +="<li class='page-item'><a class='page-link' href='" + (startPage - 1) + "'>Prev</a></li>"; 
			}
			
			for (var i = startPage; i <= endPage; i++) {
				var isActive = curPageNum == i ? "active" : "";
				str4PageNav +="<li class='page-item " + isActive + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>"; 
			}
			
			if (hasNext) {
				str4PageNav +="<li class='page-item'><a class='page-link' href='" + (endPage + 1) + "'>Next</a></li>"; 
			}
			
			str4PageNav += "</ul>";
			
			replyPageFooter.html(str4PageNav);
		}

		//댓글의 페이징에서 클릭하여 해당 페이지로 가기 기능
		replyPageFooter.on("click", "li a", function(e){
			e.preventDefault();

			curPageNum = $(this).attr("href");
			showReplyList(curPageNum);
		});
		
		var operForm = $("#operForm");

		//게시글 수정하기
		$("button[data-oper='modify']").on("click",
				function(e) {
					operForm.attr("action", "/board/modify");
					operForm.submit();
				});

		//게시글 목록으로 돌아가기
		$("button[data-oper='list']").on("click", function(e) {
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list");
			operForm.submit();
		});
		
		//댓글 입력 창 띄우기
		var replyDiv = $("#replyForm");
		var modalInputReplyDate = replyDiv.find("input[name='replyDate']");
		var modalRegisterBtn = $("#modalRegisterBtn");
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalCloseBtn = $("#modalCloseBtn");
		var modalReplyer = $("input[name='replyer']");
		$("#addReplyBtn").on("click", function(e) {
			replyDiv.find("input").val("");	//청소작업
			modalInputReplyDate.closest("div").hide();
			replyDiv.find("button[id != 'modalCloseBtn']").hide();
			modalReplyer.val(curUser);
			
			modalRegisterBtn.show();

			$("#replyForm").show();
		});
		
		var modalInputReplyContent = replyDiv.find("input[name='reply']");
		var modalInputReplyer = replyDiv.find("input[name='replyer']");
		
		//댓글 등록
		modalRegisterBtn.on("click", function(e){
			var reply = {
					bno:bnoVal,
					content:modalInputReplyContent.val(),
					replyer:modalInputReplyer.val()
			};
			replyClientService.add(
					reply,
					function(result) {
						alert("Result : " + result);
						
						replyDiv.find("input").val("");	//청소작업
						replyDiv.hide();
						//추가한 결과를 목록으로 확인할 수 있도록 재조회
						showReplyList(-1);
					},
					function(er) {
						alert("Error : " + er);
					});
		});
		
		//댓글 수정 삭제 상세조회를 위한 창 띄우기
		$("#replyListDisplay").on("click", "li", function(e){
			var rno = $(this).data("rno");
			var tblReplyer = $(this).data("replyer");
			
			replyClientService.get(
					rno,
					function(data) {
						//수정하고자하는 댓글을 클릭하면 정보를 DB에서 읽어 각 속성들을 채우고
						modalInputReplyContent.val(data.content);
						modalInputReplyer.val(data.replyer).attr("readonly", "readonly");;
						modalInputReplyDate.val(replyClientService.displayTime(data.regdate)).attr("readonly", "readonly");
						replyDiv.data("rno", data.rno);
						replyDiv.data("bno", data.bno);
						// 조작용 버튼을 활성화
						replyDiv.find("button[id != 'modalCloseBtn']").hide();
						if (curUser == tblReplyer) {
							modalModBtn.show();
							modalRemoveBtn.show();
						}

						replyDiv.show();
					},
					function(er) {
						alert("Error : " + er);
					});
		});
		
		//상세 조회 창 닫기
		modalCloseBtn.on("click", function(e){
			replyDiv.hide();
		});
		
		//댓글 수정 처리
		modalModBtn.on("click", function(e){
			var reply = {
					rno:replyDiv.data("rno"),
					content:modalInputReplyContent.val(),
					replyer:curUser
			};
			replyClientService.update(
					reply,
					function(result) {
						replyDiv.hide();
						showReplyList(curPageNum);
					},
					function(er) {
						alert("Error : " + er);
					});
		});
		
		//댓글 삭제 처리
		modalRemoveBtn.on("click", function(e){
			var rno = replyDiv.data("rno");
			var bno = replyDiv.data("bno");
			replyClientService.remove(
					bno, rno, curUser,
					function(msg) {
						replyDiv.hide();
						showReplyList(curPageNum);
					},
					function(er) {
						alert("Error : " + er);
					});
		});
	});
</script>

<!-- ajax 연동 테스트를 위한 것이었음
	
	console.log(replyClientService);
	var bnoVal = '<c:out value="${board.bno}" />';
	replyClientService.add(
		{bno:bnoVal, content:"이 글 좋은데...", replyer:"최길동"},
		function(result) {
			alert("Result : " + result);
		},
		function(er) {
			alert("Error : " + er);
		});
	
	replyClientService.get(
			19,
			function(data) {
				console.log(data);
			},
			function(er) {
				alert("Error : " + er);
			});

	replyClientService.getList(
		{bno:bnoVal, page:1},
		function(list) {
			for (var i = 0, len = list.length || 0; i < len; i++) {
				console.log(list[i]);
			}
		},
		function(er) {
			alert("Error : " + er);
		});

	replyClientService.remove(
		15,
		function(msg) {
			if (msg == "success") {
				alert("삭제 성공");
			}
		},
		function(er) {
			alert("Error : " + er);
		});

	replyClientService.update(
			{rno:19, content:"이 글 엄청 좋은데..."},
			function(result) {
				alert("수정 완료");
			},
			function(er) {
				alert("Error : " + er);
			});


 -->









