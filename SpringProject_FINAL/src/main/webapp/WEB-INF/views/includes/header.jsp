<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

<style>
	body {
		font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",
		Arial,"Noto Sans",sans-serif,"Apple Color Emoji",
		"Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
	}
	/* 전체화면 */
	div#wrpper {
		height: 100%;
	}
	
	/* 사이드 제외 전체 화면 */
	div#content {
		margin: 5px;
	}
	
	/* 맨 위 board 큰 글씨 */
	h1#boardLetter {
		color: #5a5c69;
		margin:0px;
	}
	
	/* 탑바 */
	#Topbar {
		height: 3.8rem;
	}
	
	.card-header {
		text-align: left;
	}
	/* '게시글 쓰기' 버튼 */
	button#regPost.btn.btn-xs.pull-right {
		background-color: rgb(215, 215, 215);
		color: black;
		font-weight: 450;
		align-items: right	
	}
	
	/* 회색 버튼들 (등록, 초기화, 수정)*/
	button.btn.btn-default {
		background-color: rgb(215, 215, 215);
		color: black;
		font-weight: 450;
	}
	
	h1.page-header {
		margin-bottom: 20px;
	}
	
	/* '등록'버튼 */
	input.btn.btn-default {
		background-color: rgb(215, 215, 215);
		color: black;
		font-weight: 450;
		margin-bottom: 15px;
	}
	
	/*'댓글 달기 버튼' */
	button#addReplyBtn {
		margin-left: 50px;
	}
	
	/* 검색 처리 */
	form#searchForm {
		width: fit-content;
		margin-top: 25px;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 15px;
	}
	
	#searchType {
		width: 183px;
		height: 30px;
		padding-left: 5px;
	}
	
	#searchInput {
		width: 183px;
		height: 31px;
		padding-left: 5px;
		font-size: 15px;
	}
	
	button.btn.btn-default1 {
		width: 54px;
		height: 33px;
		background-color: rgb(215, 215, 215);
		color: black;
		font-weight: 450;
		padding: 0px;
		margin-left: 0px;
	}
	
	/* 페이징처리 */
	ul.pagination {
		width: fit-content;
		margin-top: 20px;
		margin-left: auto;
		margin-right: auto;
	}
	
	
	li.paginate_button {
	    background-color: transparent;
	}
	
	if li.paginate_button {
		
	}
	/* 이전, 다음 버튼 */
	#pagebutton {
		color: #858796;
		border-color: #dddfeb;
	}
	
	li.paginate_button.active.page_link {
		background-color: #4e73df;
		color: #fff;
	}
	
	/*..*/
	div.panel-body {
		margin-bottom: 15px;
	}
	
	form#boardForm {
		margin-bottom: 15px;
	}
	
	footer.sticky-footer.bg-white {
		color: black;
		padding:0px;
		margin-top: -5px;
		margin-bottom: 10px;
		background-color: rgb(245, 245, 245);
	}
</style>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Board page</title>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- Custom fonts for this template -->
<link href="/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/resources/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="/resources/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="Sidebar">

			<!-- Sidebar - Brand -->
			<a class="sidebar-brand d-flex align-items-center justify-content-center" href="#">
				<div class="sidebar-brand-icon rotate-n-15">
					<i class="fas fa-laugh-wink"></i>
				</div>
				<div class="sidebar-brand-text mx-3"> 
					Yejin' s 
				</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">

			<!-- Nav Item - Home -->
			<li class="nav-item">
				<a class="nav-link" href="/">
					<i class="fas fa-fw fa-tachometer-alt"></i> 
					<span>Home</span>
				</a>
			</li>

			<!-- Nav Item - Board -->
			<li class="nav-item active">
				<a class="nav-link" href="/board/list"> 
					<i class="fas fa-fw fa-table"></i> 
						<span>Board</span>
				</a>
			</li>

			<!-- Divider -->
			<hr class="sidebar-divider d-none d-md-block">

			<!-- Sidebar Toggler (Sidebar) -->
			<div class="text-center d-none d-md-inline">
				<button class="rounded-circle border-0" id="sidebarToggle"></button>
			</div>

		</ul>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow" id="Topbar">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>

					<!-- Board  글씨 -->
					<h1 id="boardLetter">Board</h1>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none">
							<a class="nav-link dropdown-toggle" href="#" id="searchDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> 
								<i class="fas fa-search fa-fw"></i>
							</a> 
							
							<!-- Dropdown - Messages -->
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text"
											class="form-control bg-light border-0 small"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div>
							</li>

						<!-- Nav Item - Alerts -->
						<li class="nav-item dropdown no-arrow mx-1">
							<a class="nav-link dropdown-toggle" href="#" id="alertsDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> 
								<i class="fas fa-bell fa-fw"></i> 
								
								<!-- Counter - Alerts -->
								<span class="badge badge-danger badge-counter">1</span>
							</a> 
							
							<!-- Dropdown - Alerts -->
							<div
								class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="alertsDropdown">
								<h6 class="dropdown-header">Alerts Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="mr-3">
										<div class="icon-circle bg-primary">
											<i class="fas fa-file-alt text-white"></i>
										</div>
									</div>
									
									<div>
										<div class="small text-gray-500">February, 2020</div>
										<span class="font-weight-bold">
											A bulletin board was created!</span>
									</div>
								</a>
								
								<a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
							</div>
						</li>

						<!-- Nav Item - Messages -->
						<li class="nav-item dropdown no-arrow mx-1">
							<a class="nav-link dropdown-toggle" href="#" id="messagesDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> 
								<i class="fas fa-envelope fa-fw"></i>
							
								<!-- Counter - Messages --> 
								<span class="badge badge-danger badge-counter">3</span>
							</a>
						 
						<!-- Dropdown - Messages -->
							<div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="messagesDropdown">
								<h6 class="dropdown-header">Message Center</h6>
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="https://source.unsplash.com/fn_BT9fwg_E/60x60" alt="">
										<div class="status-indicator bg-success"></div>
									</div>
									<div class="font-weight-bold">
										<div class="text-truncate">Hello! The homepage has been created.</div>
										<div class="small text-gray-500">Yejin Song · 58m</div>
									</div>
								</a>
								 
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="https://source.unsplash.com/AU4VPcFN4LE/60x60" alt="">
										<div class="status-indicator"></div>
									</div>
									<div>
										<div class="text-truncate">This is my first web project made with spring.</div>
										<div class="small text-gray-500">Yejin Song · 1d</div>
									</div>
								</a> 
								
								<a class="dropdown-item d-flex align-items-center" href="#">
									<div class="dropdown-list-image mr-3">
										<img class="rounded-circle"
											src="https://source.unsplash.com/CS2uCrpNzJY/60x60" alt="">
										<div class="status-indicator bg-warning"></div>
									</div>
									<div>
										<div class="text-truncate">There are many things lack, but please look carefully :)</div>
										<div class="small text-gray-500">Yejin Song · 2d</div>
									</div>
								</a> 
								
								<a class="dropdown-item text-center small text-gray-500"
									href="#">Read More Messages</a>
							</div></li>

						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow">
						<a class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> 
							<span class="mr-2 d-none d-lg-inline text-gray-600 small">Yejin Song</span>
								<img src = "/resources/img/face.png" width="37" height="37">
						</a> 
						
						<!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<a class="dropdown-item" href="#"> 
									<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
								</a>
								
								<sec:authorize access="isAuthenticated()"> 
									<a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal"> 
										<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Logout
									</a>
								</sec:authorize>
								
								<sec:authorize access = "isAnonymous()">
									<a class="dropdown-item" href="/customLogin">
										<i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i> Login
									</a>
								</sec:authorize>
							</div>
						</li>
					</ul>
				</nav>
				<!-- End of Topbar -->
