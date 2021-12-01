console.log("Reply Module...");

var replyClientService = (
	function(){
		
		//댓글 조회
		function get(rno, callBack, error) {
			$.get(
				//.json의 효과는 json 형태의 결과물로 주세요
				"/reply/getReply/" + rno + ".json",
				function(data) {
					if (callBack) {
						callBack(data);
					}}
			).fail(
				function(xhr, status, er) {
					if (error) {
						error(er);
					}});
		}
		
		//댓글 등록
		function add(reply, callBack, error) {
			console.log("Add of Reply Module...");
			
			$.ajax({
				type : 'post',
				url : '/reply/createReply',
				data : JSON.stringify(reply), //reply를 json문자열로 변환
				contentType : "application/json;charset=utf-8",
				success : function(result, status, xhr) {
					if (callBack) {
						callBack(result);
					}},
				error : function (xhr, status, er) {
					if (error) {
						error(er);
					}}});
		}
		
		//댓글 수정
		function update(reply, callBack, error) {
			console.log("Update of Reply Module...");
			
			$.ajax({
				type : 'put',
				url : '/reply/updateReply',
				data : JSON.stringify(reply),
				contentType : "application/json;charset=utf-8",
				success : function(result, status, xhr) {
					if (callBack) {
						callBack(result);
					}},
				error : function (xhr, status, er) {
					if (error) {
						error(er);
					}}});
		}
		
		//댓글 삭제
		function remove(bno, rno, curUser, callBack, error) {
			console.log("Delete of Reply Module...");
			
			$.ajax({
				type : 'delete',
				url : '/reply/deleteReply/' + bno + '/' + rno + '/' + curUser,
				success : function(result, status, xhr) {
					if (callBack) {
						callBack(result);
						}},
				error : function (xhr, status, er) {
					if (error) {
						error(er);
					}}});
		}
		
		//댓글 목록, 페이징
		function getList(param, callBack, error) {
			var bno = param.bno;
			var page = param.page || 1;
			var pageSize = param.pageSize;
			
			//REST 방식으로 서버 호출
			var restUrl = "/reply/getPagingReply/" + bno + "/" 
							+ page +"/" + pageSize + ".json"; 
			$.getJSON(
				//.json의 효과는 json 형태의 결과물로 주세요
				restUrl,
				function(data) {
					if (callBack) {
						callBack(data); //댓글 목록만 가져오는 경우
					}}
			).fail(
				function(xhr, status, er) {
					if (error) {
						error(er);
					}});
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
			get:get,
			getList:getList,
			add:add,
			remove:remove,
			update:update,
			displayTime:displayTime};
	}
)();
