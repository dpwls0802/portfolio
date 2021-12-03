//댓글 자바스크립트
console.log("Reply Module");

var replyService2 = (function() {
	// 댓글 등록
	function add(reply, callBack, error) {
		console.log("Add Reply...");

		$.ajax({
			type : 'post',
			url : '/reply/writeReply',
			data : JSON.stringify(reply), // reply를 json문자열로 변환
			contentType : "application/json;charset=utf-8",
			success : function(result, status, xhr) {
				if (callBack) {
					callBack(result);
				}
			},
			error : function(xhr, status, err) {
				if (error) {
					error(err);
				}
			}
		});
	}

	// 댓글 목록
	function getPagingReply(param, callBack, error) { // 함수 이름은 컨트롤러 서비스 이름과
														// 동일하게!
		var num = param.num;
		var page = param.page || 1;

		// REST 방식으로 서버 호출
		var restUrl = "/reply/replyListPaging/" + num + "/" + page;
		$.getJSON(

		// .json의 효과는 json 형태의 결과물로 주세요
		restUrl, function(data) {
			if (callBack) {
				callBack(data); // 댓글 목록만 가져오는 경우
			}
		}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});
	}

	// 댓글 삭제
	function remove(rnum, callBack, error) {
		console.log("Delete of Reply Module...");

		$.ajax({
			type : 'delete',
			url : '/reply/' + rnum,
			success : function(deleteResult, status, xhr) {
				if (callBack) {
					callBack(deleteResult);
				}
			},
			error : function(xhr, status, err) {
				if (error) {
					error(err);
				}
			}
		});
	}

	// 댓글 수정
	function update(reply, callBack, error) {
		console.log("Rnum: " + reply.rnum);

		$.ajax({
			type : 'put',
			url : '/reply/' + reply.rnum,
			data : JSON.stringify(reply),
			contentType : "application/json;charset=utf-8",
			success : function(result, status, xhr) {
				if (callBack) {
					callBack(result);
				}
			},
			error : function(xhr, status, err) {
				if (error) {
					error(err);
				}
			}
		});
	}

	//댓글 조회
	function get(rnum, callBack, error) {
		$.get("/reply/getReply/" + rnum, function(result){
			if(callBack) {
				callBack(result);
			}
		}).fail(function(xhr, status, err) {
			if(error) {
				error();
			}
		});
	}
	
	//ajax에서 데이터를 가져와서 html을 만들어 주는 부분에 적용
	function displayTime(timeValue) {
		var today = new Date();
		var gap = today.getTime() - timeValue;
		var dateObj = new Date(timeValue);
		var str = "";
		
		if (gap < (1000 * 60 * 60 * 24)) {
			//오늘 올라온 글이면 (시간으로 표시 - 09:00:01)
			var hh = dateObj.getHours();
			var mm = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			
			return [(hh > 9 ? '' : '0') + hh, ':',
				(mm > 9 ? '' : '0') + mm, ':',
				(ss > 9 ? '' : '0') + ss
				].join('');
		} else { //24시간이 지난 댓글은 날짜만 표시 (2019/12/30)
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();
			return [yy,'/', (mm>9?'':'0') + mm, '/',
				(dd>9?'':'0') + dd].join('');
		}
	}
	
	return {
		add : add,
		getPagingReply : getPagingReply,
		remove : remove,
		update : update,
		get : get,
		displayTime:displayTime
	};

})();

