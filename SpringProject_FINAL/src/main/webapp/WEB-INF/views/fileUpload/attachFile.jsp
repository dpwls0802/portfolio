<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!-- 첨부 파일 기능이 첨부자의 등록,조회,수정 기능에서 반복적으로 복제,재생산되는 현상을 막기 위하여  별도 파일로 분리. 재사용성을 강화하였습니다. -->
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
		
		div.panel-heading {
			margin-bottom: 10px;
		}
		
		div.form-group.uploadDiv label{
			background-color: rgb(215, 215, 215);
			color: black;
			font-weight: 450;
		}
	</style>

<!-- 이미지 첨부 파일 원본 보기 -->
<div class="bigPicWrapper">
	<div class="bigPic">
	</div>
</div>

<!-- 첨부파일 선택 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">첨부 파일</div>
			<div class="panel-body">
				<div class="form-group uploadDiv">
					<!-- 신규 첨부 파일 선택하기 -->
					<input id = "file-choice" type='file' name='uploadFile' multiple>
				</div>
				<!-- 첨부 파일 목록 보여주기 -->
				<div id='uploadResult'>
					<ul>
					
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var operationMode;
function setOperationMode(caller) {
	//create, modify, showDetail
	operationMode = caller;
	if (caller) {
		if (caller == "showDetail") {
			//첨부 파일 추가 기능 막기. 이유는 ReadOnly 상태로 상세 조회 화면 띄우기 때문
			$("input[type='file']").hide();
		}
	}
}

//readOnly : 삭제용 버튼 활성화 여부. create, modify - 활성화, showDetail - 비활성화
//fromTable : 참일 때는 li에 별도의 마킹을 하여 첨부 테이블에서도 첨부 파일과 함께 삭제토록 관리할 것임
//ajax 호출 후 업로드된 결과를 처리하는 함수
function showAttachFileList(list) {
	if (!list || list.length == 0) {
		return;
	}
	
	var uploadResultUl = $("#uploadResult ul");
	
	var liHtmls = "";
	
	$(list).each(function(i, obj){
		//URI를 만들어 주세요
		var fileCallPath = encodeURIComponent(obj.showBackFileName);
		
		//이미지 타입인 경우
		if (obj.fileType) {
			var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
			//g : 문자열 내의 모든 패턴을 검색한다.
			originPath = originPath.replace(new RegExp(/\\/g), "/");
			//data-fileName 이런식으로 대문자 중간에 들어가 있을 때 읽지 못하는 현상이 발생합니다. 주의 요함

			liHtmls += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType + "' data-mastername='" + obj.masterName + "' data-masterid='" + obj.masterId +"'>";
			liHtmls += "<div>";
			//원본 이미지 보여주기
			liHtmls += "    <a href=\"javascript:showImage(\'" + originPath + "\')\">";
			liHtmls += "        <img src='/fileUpload/display?fileName=" + fileCallPath + "'>";
			liHtmls += "    </a>";
			liHtmls += "    <span >" + obj.fileName + "</span>";
			//첨부 취소
			if (operationMode == "create" || operationMode == "modify") {
				liHtmls += "    <button data-file=\'" + originPath + "\' data-type=\'image\' type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			}
			liHtmls += "</div></li>";
		} else {
			var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");

			liHtmls += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType + "' data-mastername='" + obj.masterName +"' data-masterid='" + obj.masterId +"'>";
			liHtmls += "<div>";
			liHtmls += "    <a href='/fileUpload/download?fileName=" + fileCallPath + "'>";
			liHtmls += "        <image src='/resources/img/attachFileIcon.png'>";
			liHtmls += "    </a>";
			liHtmls += "    <span >" + obj.fileName + "</span>";
			//첨부 취소
			if (operationMode == "create" || operationMode == "modify") {
				liHtmls += "    <button data-file=\'" + fileLink + "\' data-type=\'file\' type='button' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
			}
			liHtmls += "</div></li>";
		}
	});
	uploadResultUl.append(liHtmls);
}

//게시글 첨부정보를 listAttach에 담아서 boardVO에 첨부
function gatherRemainingAttaches(masterName) {
	var strRemainingAttaches = "";
	$("#uploadResult ul li").each(function(idx, obj){
		var liObj = $(obj);
		//data-path data-uuid data-fileName data-type
		//게시글 등록은 <form>태그를 통해 이루어지므로 이미 업로드 된 첨부파일 정보는 <input type='hidden'>으로 처리
		strRemainingAttaches += "<input type='hidden' name='listAttach[" + idx + "].fileName' value='" + liObj.data("filename")+ "'>";
		strRemainingAttaches += "<input type='hidden' name='listAttach[" + idx + "].uuid' value='" + liObj.data("uuid")+ "'>";
		strRemainingAttaches += "<input type='hidden' name='listAttach[" + idx + "].uploadPath' value='" + liObj.data("path")+ "'>";
		strRemainingAttaches += "<input type='hidden' name='listAttach[" + idx + "].fileType' value='" + liObj.data("type")+ "'>";
		strRemainingAttaches += "<input type='hidden' name='listAttach[" + idx + "].masterName' value=" + masterName + ">";
	});
	return strRemainingAttaches;
}

//원본 이미지 보여주기
function showImage(fileCallPath) {
	$(".bigPicWrapper").css("display", "flex").show();
	$(".bigPic").html("<img src='/fileUpload/display?fileName=" + encodeURI(fileCallPath) + "'>")
	.animate({width:'100%', height:'100%'}, 1000);
}



$(document).ready(function() {
	//Security 적용 이후 Ajax의 post에서 정상 작동을 위해 csrf 추가해 줌
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}";

	//$:입력의 끝 부분
	//*: 0회 이상 연속으로 반복
	//? :  0 또는 1
	var forbiddenFileExts = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	var MAX_FILE_SIZE = 5242880;	//5MB

	var formObj = $("form[role='form']");
	
	//신규 첨부
	$("input[type='file']").change(function(e){
		//내부적으로 post 처리 시 보낼 정보 집합
		var formData = new FormData();
		var fileInput = $("input[name='uploadFile']");
		//배열에서 첫번째 요소를 지정하는 이유는 Type 선택자로 구동되기 때문. 즉 여러 개가 선택될 수 있다.
		var selectedFiles = fileInput[0].files;
		
		console.log(selectedFiles);
		
		//선택된 파일의 확장자와 크기가 만족하지 않으면 업로드 X, 만족하면 파일 추가
		for (var i = 0; i < selectedFiles.length; i++) {
			if (!checkExt(selectedFiles[i].name, selectedFiles[i].size)) {
				return false;
			}
			formData.append("uploadFile", selectedFiles[i]);
		}
		
		$.ajax({
			url:'/fileUpload/uploadAjaxAction',
			processData:false,
			contentType:false,
			data:formData,
			type:'post',
			dataType:'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success:function(result) {
				alert("Uploaded!");
				showAttachFileList(result);
			}
		});
	});
	
	//삭제하기
	$("#uploadResult").on("click", "button", function(e){
		var targetFile = $(this).data("file");
		var fileType = $(this).data("type");
		var isImage = fileType == 'image';
		var li = $(this).closest("li");
		var uuid = $(li).data("uuid");
		var mastername = $(li).data("mastername");
		var masterid = $(li).data("masterid");
		
		$.ajax({
			url:'/fileUpload/deleteFile',
			data:{fileName:targetFile, fileType:isImage, 
				masterName:mastername, uuid:uuid, masterId:masterid},
			dataType:'text',
			type:'POST',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
			},
			success: function(result) {
				alert(result);
				li.remove();
			}
		});
	});
	
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
	
	//원본 이미지 다시 클릭하면 사라지는 이벤트
	$(".bigPicWrapper").on("click", function(e){
		$(".bigPic").animate({width:'0%', height:'0%'}, 1000);
		setTimeout(function(){
			$(".bigPicWrapper").hide();
		}, 1000);
	});

});
</script>
