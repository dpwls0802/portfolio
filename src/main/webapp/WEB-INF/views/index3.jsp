<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<link rel="stylesheet" href="/css/navigationBar.css" type="text/css" />

<title>Travel Record</title>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="/js/mapdata.js"></script>
<script src="/js/worldmap.js"></script>
</head>
<body>

	<!-- 내비게이션 바 -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="#!">Travel Record</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="/blog/listPost?page=1&perPageNum=8">blog</a></li>
					<li class="nav-item"><a class="nav-link" href="#!">etc</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Login</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Sign up</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div>
		<div style="width: 100%; margin-top: 10px;">
			<!-- Keep map above fold -->
			<div id="map"></div>
			
			<c:forEach items="${listPost}" var="board">
				<a class="detailPost" href="/blog/detailPost?num=${board.num}" id="${board.title}" style="text-decoration:none;">
					<input type="hidden" id="writer" name="writer" value="${board.writer}">
					<input type="hidden" id="title" name="title" value="${board.title}">
					<input type="hidden" id="num" name="num" value="${board.num}">
					<input type="hidden" id="travelDate" name="travelDate" value="${board.travelDate}">
					<!-- <img id="countryMarker" src="/image/pin.png" width="20" height="20"> -->
				</a>
			</c:forEach>
		</div>
	</div>

	<script type="text/javascript">
		
	
	
		$(document).ready(function() {
			/* var country = $("#title");
			var writer = $("#writer");
			var travelDate = $("#travelDate");
			 */
			var aa = $(".detailPost").attr('id');
			var country = $("#title").attr('value');
			var writer = $("#writer").attr('value');
			var travelDate = $("#travelDate").attr('value');
			var num = $("#num").attr('value');
			var list = "${listPost}";
			var list2 = list.split("BoardDto");				
			
			 for (var key in simplemaps_worldmap_mapdata.state_specific) {
					var value = simplemaps_worldmap_mapdata.state_specific[key].name;
					
					
					for(i=0; i<list2.length; i++) {
						if(aa == value) {
							$(".detailPost").append("<img id='${board.title}' class='countryMarker' src='/image/pin.png' width='20' height='20'>");
						}
					}
			}
			 
			
			
			$(".countryMarker").on("click", function() {
				self.location = "/blog/detailPost?num="+num;

			});
			
	      
		});
	</script>
</body>
</html>



