<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Core Scripts - Include with every page -->
<!-- <script src="/resources/js/jquery-1.10.2.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/plugins/metisMenu/jquery.metisMenu.js"></script> -->
<!-- autocomplete from jQuery Ui -->


<!-- <script src='{% static "js/jquery-1.11.3.min.js" %}'></script>
 -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<title>지도 생성하기</title>
<script>
	var myArray = {
		4 : "#eb3434",
		3 : "#ebdc34",
		2 : "#6eeb34",
		1 : "#34baeb"
	};
	var arrRectangle = new Array(); //사각형 정보를 저정할 배열
</script>
<style>
.map_wrap {
	position: relative;
	width: 100%;
	height: 100%;
}

.title {
	font-weight: bold;
	display: block;
}

.hAddr {
	position: absolute;
	left: 10px;
	top: 10px;
	border-radius: 2px;
	background: #fff;
	background: rgba(255, 255, 255, 0.8);
	z-index: 1;
	padding: 5px;
	font-size: 0.8em;
}

#centerAddr {
	display: block;
	margin-top: 2px;
	font-weight: normal;
	font-size: 0.5em;
}
    .wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
    .info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
    .info .close:hover {cursor: pointer;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;margin: 13px 0 0 90px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
/* .wrap {position: absolute;left: 0;bottom: 40px;width: 250px;height: 200px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 200px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 15px;font-weight: bold;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;padding: 5px 0 0 10px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;font-size: 10px;} */
</style>
</head>
<body style="height: 100%;">

	<div class="map_wrap">
		<div id="map"
			style="width: 100%; height: 100%; position: relative; overflow: hidden;"></div>
		<div class="hAddr">
			<span class="title">지도중심기준 행정동 주소정보</span> <span id="centerAddr"></span>
			
		</div>
		

	</div>
	
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=cce17312e9f86d40793d2b7978ca775f&libraries=services"></script>
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
	
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(37.5642135, 127.0016985), // 지도의 중심좌표
			level : 3
		// 지도의 초기 레벨
		};

		//지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);

		///////////////////현재위치정보 (안드로이드 불가)/////////////////////

		///////////////////////////////////////////////////////////
		map.setMinLevel(1); //지도의 최소 레벨
		map.setMaxLevel(8); //지도의 최대레벨
		map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC); //교통정보 오버레이 추가
		//map.setCenter(new kakao.maps.LatLng(37.5642135, 127.0016985));
		var LatLngBounds //지도 위치 변경 시 중점좌표를 가지고 온다.
		getCurrentArea(); //지도를 처음열때 area 표시하기

		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();
		var marker = new kakao.maps.Marker(); // 클릭한 위치를 표시할 마커입니다

		
		//지도 클릭시 상세정보를 띄울 오버레이
		var infoOverlay = new kakao.maps.CustomOverlay({
			clickable: true,
		    map: map    
		    
		});
		
		// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

			//클릭위치 상세정보창
			searchDetailAddrFromCoords(mouseEvent.latLng, function(result,
					status) {
				if (status === kakao.maps.services.Status.OK) {
					
					//구글차트 사용
					google.charts.load('current', {
						packages : [ 'corechart', 'bar' ]
					});
					
					
					var form = { 
							clickedAreaLat :Number( mouseEvent.latLng.getLat()),//클릭한점 위도
							clickedAreaLng : Number(mouseEvent.latLng.getLng()), //클릭한점 경도
							current_map_level : calculateLevel(map.getLevel()) //현재 레벨
					}
					
					var maleRatio,femaleRatio;
					$.ajax({
						url : "getClickedAreaInfo",
						type : "POST",
						data : form,
						success : function(data) {
							console.log("클릭한곳 id: " +data.id +", 여성비율 값: " +data.female +", 남성비율 값: " +data.male );
							maleRatio=Number(data.male);
							femaleRatio=Number(data.female);
							google.charts.setOnLoadCallback(drawMultSeries); //지도그리기
						},
						error : function() {
							alert("클릭한 사각형 가저오기 에러");
						}
					});
					
					//남녀성비 차트 그리는 함수
					function drawMultSeries() {
						var data = google.visualization.arrayToDataTable([
								[ '남녀성비', '남', '녀' ],
								[ '남녀성비', Number(maleRatio), Number(femaleRatio) ]]);
						var options = {
							isStacked: 'percent', //백분률
							legend: 'none', //범례제거
							hAxis: {textPosition : 'none'}, //가로축제거
							vAxis: {textPosition : 'none'}, //세로축제거
						
						};

						var chart = new google.visualization.BarChart(document
								.getElementById('chart_div'));
						chart.draw(data, options);
					}
					
					//도로명주소와 지번주소를 구하여 문자열로 저장
					var detailAddr = !!result[0].road_address ? '<div>도로명주소 : '
							+ result[0].road_address.address_name + '</div>'
							: '';
					detailAddr += '<div>지번 주소 : '
							+ result[0].address.address_name + '</div>';
					
					//오버레이에 출력할  content
					var content = '<div class="wrap">' + 
				         	 '    <div class="info">' + 
				          	'        <div class="title">' + 
				            '            상세정보' + 
				            '        </div>' + 
				            '        <div class="body">' + 
				            '            <div class="desc">' + 
				            '                <div class="ellipsis">'+
				            				detailAddr +
				            '				</div>' + 
				            '				<div id="chart_div" style="width:90%; height:30px;""></div>'+
				            '            </div>' + 
				            '        </div>' + 
				            '    </div>' +    
				            '</div>';
				    map.panTo(mouseEvent.latLng); //클릭위치로 부드럽게 이동하기
				    
					// 마커를 클릭한 위치에 표시합니다 
					marker.setPosition(mouseEvent.latLng);
					marker.setMap(map);
					
					//상세정보 오버레이를 클릭한 위치에 표시한다
			

					infoOverlay.setPosition(mouseEvent.latLng);
					infoOverlay.setContent(content)
					infoOverlay.setMap(map);
				}
				
			});
			
		});
		// 중심 좌표나 확대 수준이 변경됐을 때 
		kakao.maps.event.addListener(map, 'idle', function() { 
			searchAddrFromCoords(map.getCenter(), displayCenterInfo);	// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
			getCurrentArea(); //현제 스크린에 보이는 에어리어만 가져오기
		});
		
		//지도를 드래그 시작 시
		kakao.maps.event.addListener(map, 'dragstart', function() {
			infoOverlay.setMap(null); //켜저있는 infoOverlay 닫기	
			marker.setMap(null);
		});

		//현재 화면안의 에어리어 구하기
		function getCurrentArea() {
			LatLngBounds = map.getBounds(); // 지도 왼쪽 아래,오른쪽 위 좌표를 얻어옵니다 
			var current_sw_latitude = LatLngBounds.getSouthWest().getLat();//현재화면 왼쪽아래 점의 위도
			var current_sw_longitude = LatLngBounds.getSouthWest().getLng();//현재화면 왼쪽아래 점의 위도
			var current_ne_latitude = LatLngBounds.getNorthEast().getLat();//현재화면 왼쪽아래 점의 위도
			var current_ne_longitude = LatLngBounds.getNorthEast().getLng();//현재화면 왼쪽아래 점의 위도
			var current_map_level = map.getLevel();
			var area_scale;
			
			current_map_level=calculateLevel(current_map_level);
			
			var form = {
				sw_latitude : Number(current_sw_latitude),
				sw_longitude : Number(current_sw_longitude),
				ne_latitude : Number(current_ne_latitude),
				ne_longitude : Number(current_ne_longitude),
				map_level : current_map_level
			}

			$.ajax({
				url : "updateCurrentAreas",
				type : "POST",
				data : form,
				success : function(data) {
					$('#result').text(data);
			
					printCurrentAreas(data);
				},
				error : function() {
					alert("사각형그리기 에러 err");
				}
			});

		}

		//에어리어 출력
		function printCurrentAreas(data) {
			for (var i = 0; i < arrRectangle.length; i++) {
				arrRectangle[i].setMap(null); //맵 이동 전 맵에 그려져있는 사각형을 지운다
			}
			arrRectangle = []; //배열 null로 초기화

			var sw, ne; //사각형의 남서쪽과 북동쪽 모서리 좌표
			for (var i = 0; i < data.length; i++) {

				var c = Math.floor(Math.random() * 4) + 1; //테스트를 위한 임의의 색 선택

				sw_latitude = Number(data[i].sw_latitude); //data베이스에서 위도,경도 가져오기
				sw_longitude = Number(data[i].sw_longitude);
				ne_latitude = Number(data[i].ne_latitude);
				ne_longitude = Number(data[i].ne_longitude);

				sw = new kakao.maps.LatLng(sw_latitude, sw_longitude);//사각형의 남서쪽
				ne = new kakao.maps.LatLng(ne_latitude, ne_longitude);//사각형의 북서쪽
				var rectangleBounds = new kakao.maps.LatLngBounds(sw, ne);
				var rectangle = new kakao.maps.Rectangle({
					bounds : rectangleBounds, // 그려질 사각형의 영역정보입니다
					strokeOpacity : 0, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
					fillColor : myArray[c], // 채우기 색깔입니다
					fillOpacity : 0.2

				});
				arrRectangle[i] = rectangle; //맵 이동 시 사각형을 지우기 위해 사각형 객체를 저장하는 배열객체			
				arrRectangle[i].setMap(map); //사각형을 맵에 표시
			}
		}

		
		
		function searchAddrFromCoords(coords, callback) {
			// 좌표로 행정동 주소 정보를 요청합니다
			geocoder.coord2RegionCode(coords.getLng(), coords.getLat(),
					callback);
		}

		function searchDetailAddrFromCoords(coords, callback) {
			// 좌표로 법정동 상세 주소 정보를 요청합니다
			geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
		}

		// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
		function displayCenterInfo(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var infoDiv = document.getElementById('centerAddr');

				for (var i = 0; i < result.length; i++) {
					// 행정동의 region_type 값은 'H' 이므로
					if (result[i].region_type === 'H') {
						infoDiv.innerHTML = result[i].address_name;
						break;
					}
				}
			}
		}
		
		//지도레벨 1,2,3은 DB에서 레벨이 1인 데이터를 꺼냄
		//지도레벨 4 5 6은 DB에서 레벨이 3인 데이터를 꺼냄
		//지도레벨 7 8 은  DB에서 레벨이 5인 데이터를 꺼냄
		function calculateLevel(current_map_level){
			switch (current_map_level) { //레벨설정하기
				case 1:
				case 2:
				case 3:
					current_map_level = 1;
					break;
				case 4:
				case 5:
				case 6:
					current_map_level = 3;
					break;
				case 7:
				case 8:
					current_map_level = 5;
					break;
			}
			return current_map_level
		}

		
	</script>

</body>

</html>