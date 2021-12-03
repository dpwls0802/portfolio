<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/jquery-jvectormap-2.0.5.css"
	type="text/css" media="screen" />
	
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- <script src="jquery.js"></script> -->
<script src="/js/jvectormap.js"></script>
<script src="/js/jquery-jvectormap-2.0.5.min.js"></script>
<script src="/js/jquery-jvectormap-world-mill.js"></script>

<title>Travel Record</title>
</head>
<body>
	<h1 style="text-align: center;">Travel Record</h1>
	
	<div id="world-map" style="width: 600px; height: 400px"></div>
	<script>
		$(function() {
			$('#world-map').vectorMap({
				map : 'world_mill_en'
			});
		});
	</script>
</body>
</html>