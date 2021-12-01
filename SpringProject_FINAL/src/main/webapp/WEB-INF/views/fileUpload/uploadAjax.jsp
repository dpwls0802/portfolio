<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>첨부파일 올리기</title>
	<style>
		#uploadResult{
			width:100%;
			background-color:gray;
		}
		#uploadResult ul{
			display:flex;
			flex-flow:row;
			justify-content:center;
			align-items:center;
		}
		#uploadResult ul li{
			list-style:none;
			padding:10px;
		}
		#uploadResult ul li img{
			width:20px;
		}
		#uploadResult ul li span{
			color:white;
		}
		.bigPicWrapper {
			position: absolute;
			display: none;
			justify-content: center;
			align-items: center;
			top:0%;
			width: 100%;
			height: 100%;
			background-color: gray;
			z-index: 100;
			background: rgba(255, 255, 255, 0.5);
		}
		.bigPic {
			position: relative;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		.bigPic img {
			width: 600px;
		}
	</style>
</head>
<body>
	<h1>Upload with Ajax</h1>
	
 	<!-- 파일 업로드 -->
	<div class='uploadDiv'>
		<input type='file' name='uploadFile' multiple>
	</div>
	<button id='btnUpload'>Upload</button>
	
	<!-- 파일 이름 출력 -->
	<div id='uploadResult'> 
		<ul>
		</ul>
	</div>
	
	<!-- 이미지 첨부 파일 원본 보기 -->
	<div class="bigPicWrapper">
		<div class="bigPic">
		</div>
	</div>
</body>
</html>

<!-- jQuery 이용 -->
<script src="https://code.jquery.com/jquery-latest.min.js" integrity="sha384-UM1JrZIpBwVf5jj9dTKVvGiiZPZTLVoq4sfdvIe9SBumsvCuv6AHDNtEiIb5h1kU" crossorigin="anonymous"></script>

<script>
$(document).ready(function() {
	//$:입력의 끝 부분
	//*: 0회 이상 연속으로 반복
	//? :  0 또는 1
	var forbiddenFileExts = new RegExp("(.*?)\.(exe|sh|zip|alz)$"); //위 확장자는 업로드 제한
	var MAX_FILE_SIZE = 5242880;	//파일 최대 크기 :5MB
	
	var htmlLoadStatus = $(".uploadDiv").clone(); //아무 내용 없는 <input type='file'~ 객체 복사
	var uploadResultUl = $("#uploadResult ul"); //파일 이름 출력 변수
	
	//파일 확장자와 크기 검사하는 함수
	function checkExt(fileName, fileSize) {
		if (fileSize > MAX_FILE_SIZE) {
			alert("업로드 대상 파일 " + fileName +" 크기는 5MB를 초과 할 수 없습니다.");
			return false;
		}
		
		if (forbiddenFileExts.test(fileName)) {
			alert(fileName +"은 업로드 할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	//첨부파일 등록 버튼 클릭 시 FormData를 이용해 필요한 파라미터를 담아서 전송
	$("#btnUpload").on("click", function(e) {
		var formData = new FormData(); //첨부파일 데이터
		var fileInput = $("input[name='uploadFile']");
		var selectedFiles = fileInput[0].files;
		
		console.log(selectedFiles);
		
		//선택된 파일의 확장자와 크기가 만족하지 않으면 업로드 X, 만족하면 파일 추가
		for (var i = 0; i < selectedFiles.length; i++) {
			if (!checkExt(selectedFiles[i].name, selectedFiles[i].size)) {
				return false;
			}
			formData.append("uploadFile", selectedFiles[i]);
		}
		
		//결과 데이터가 반환된 정보를 처리하도록 수정
		$.ajax({
			url:'/fileUpload/uploadAjaxAction',
			processData:false,
			contentType:false,
			data:formData,
			type:'post',
			dataType:'json', //ajax 호출했을 때 결과 타입은 json
			success:function(result) {
				alert("Uploaded!");
				
				$(".uploadDiv").html(htmlLoadStatus.html());  //업로드 후 복사된 객체를 다시 추가해서 첨부파일 부분 초기화 -> 첨부파일 추가 후 다시 추가 할 수 있음
				showUploadedFile(result); // 파일 이름 출력 함수 호출 
			}
		});
	});
	
	//json 데이터를 받아서 해당 파일의 이름 추가
	function showUploadedFile(uploadResultArr) {
		var liHtmls = "";
		
		$(uploadResultArr).each(function(i, obj){
			
			//URI호출에 문제가 없는 문자열을 생성해서 인코딩 처리
			var fileCallPath = encodeURIComponent(obj.showBackFileName);
			
			if (obj.image) {
				var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
				//g : 문자열 내의 모든 패턴을 검색한다.
				originPath = originPath.replace(new RegExp(/\\/g), "/");
				
				liHtmls += "<li>";
				liHtmls += "    <a href=\"javascript:showImage(\'" + originPath + "\')\">";
				liHtmls += "        <img src='display?fileName=" + fileCallPath + "'>";
				liHtmls += "    </a>";
				liHtmls += "    <span data-file=\'" + originPath + "\' data-type=\'image\'>";
				liHtmls += "    X</span>";
				liHtmls += "</li>";
			} else { //일반 파일인 경우
				var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");

				liHtmls += "<li><div>";
				liHtmls += "    <a href='/fileUpload/download?fileName=" + fileCallPath + "'>";
				liHtmls += "        <image src='/resources/img/attachFileIcon.png'>";
				liHtmls += "            " + obj.fileName;
				liHtmls += "    </a>";
				liHtmls += "    <span data-file=\'" + fileLink + "\' data-type=\'file\'>";
				liHtmls += "    X</span>";
				liHtmls += "</div></li>";
			}
		});
		uploadResultUl.append(liHtmls);
	}

	//삭제하기
	$("#uploadResult").on("click", "span", function(e){
		var targetFile = $(this).data("file");
		var fileType = $(this).data("type");
		var li = $(this).closest("li");
		$.ajax({
			url:'/fileUpload/deleteFile',
			data:{fileName:targetFile, type:fileType},
			dataType:'text',
			type:'POST',
			success: function(result) {
				alert(result);
				li.remove();
			}
		});
	});

});

//원본 이미지 보여주기 (바깥쪽에 작성하는 이유는 나중에 <a> 태그에서 직접 함수 호출하는 방식으로 작성하기 위해)
function showImage(fileCallPath) {
	$(".bigPicWrapper").css("display", "flex").show();
	$(".bigPic").html("<img src='/fileUpload/display?fileName=" + encodeURI(fileCallPath) + "'>")
	.animate({width:'100%', height:'100%'}, 1000); //섬네일을 클릭하면 1초동안 원본 이미지가 화면에서 열리는 효과.
}

//원본 이미지 다시 한번 클릭하면 사라지는 이벤트
$(".bigPicWrapper").on("click", function(e){
	$(".bigPic").animate({width:'0%', height:'0%'}, 1000);
	setTimeout(function(){
		$(".bigPicWrapper").hide();
	}, 1000);
});
</script>



	










