<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<!-- board/content.jsp -->
<!-- javaScript로 만들어놓은 Date형식?을 사용하기 위해 파일을 가져와줌 -->
<script src="/resources/js/my-script.js"></script> <!-- 스크립트는 생략형으로 쓰면 X <script/>안됨 -->
<script>
$(document).ready(function() {
	var msg = "${msg}";
	if (msg == "success") {
		alert("수정완료");
	}
	
	// 수정버튼
	$("#btnModify").click(function() {
		$("#btnModifyFinish").show(1000);
		// [name^=a] name속성의 값이 a로 시작하는
		// [name*=a] name속성의 값에 a가 들어있는
		// [name$=a] name속성의 값이 a로 끝나는
		// name속성에 b_로 시작하는 속성의 readonly를 false로 바꿈
		$("[name^=b_]").prop("readonly", false);
	});
	
	// 삭제버튼
	$("#btnDelete").click(function() {
		if (confirm("삭제하시겠습니까?")) {
			location.href = "/board/deleteRun?b_no=${boardVo.b_no}";
		}
	});
	
	// 목록버튼
	// page, perPage, ..같이 넘겨줘야함 (검색 후 들어와서 목록으로 돌아갈 때, listAll에서 넘겨받음)
	$("#btnList").click(function() {
		location.href = "/board/listAll?page=${pagingDto.page}&perPage=${pagingDto.perPage}&searchType=${pagingDto.searchType}&keyword=${pagingDto.keyword}";	
	});
	
	// 댓글 목록 버튼
	$("#btnCommentList").click(function() {
		// 비동기(Ajax)요청, $.ajax, $.get, $.post, $.getJSON
		// 간단하게 하나만 보낼때는 주소뒤에 붙여서 보낼 수 있음
		var url = "/comment/getCommentList/${boardVo.b_no}";
		// 댓글 목록 버튼을 눌렀을 때 계속 붙지 않게 -> 기존에 달려 있던 댓글들 모두 삭제 
		$("#commentTable > tbody > tr:gt(0)").remove();
		$.get(url, function(receivedData) {
// 			console.log(receivedData);
			$.each(receivedData, function() {
				var cloneTr = $("#commentTable > tbody > tr:first").clone();
				var td = cloneTr.find("td");
				td.eq(0).text(this.c_no);
				td.eq(1).text(this.c_content);
				td.eq(2).text(this.user_id);
				// 위에서 받아온 javaScript의 함수를 사용
				td.eq(3).text(changeDateString(this.c_regdate));
				// 삭제시 사용할 c_no를 data-cno에 넘겨줌
				td.eq(5).find("button").attr("data-cno", this.c_no);
// 				cloneTr.css("display", "block");				
				$("#commentTable > tbody").append(cloneTr);
				cloneTr.show("slow");
				if (this.c_no % 2 == 0) {
					cloneTr.attr("class", "table-success");
				}
			});
		});
	});
	
	// 댓글 입력 버튼
	$("#btnCommentInsert").click(function() {
		var c_content = $("#c_content").val();
		var b_no = parseInt("${boardVo.b_no}");
// 		var user_id = "hong";
		
		var url = "/comment/insertComment";
		var sendData = {
				"c_content" : c_content,
				"b_no" 		: b_no
		}
		// $.get, $.post의 원형, headers -> 어떤 데이터로 보내는지 명시 
		// JSON.stringify : json데이터를 문자열로 변환(javascript언어를 java로 보내야하니까 변환 필요해서)
		$.ajax({
			"url" 		: url,
			"headers" 	: { "Content-Type" : "application/json" },
			"method" 	: "post",
			"dataType" 	: "text",
			"data" 		: JSON.stringify(sendData),
			"success" 	: function(receivedData) {
						  console.log(receivedData);
						  // 처리가 잘 되었다면, 댓글 목록 버튼을 클릭시켜서 목록을 새로 얻음
						  if (receivedData == "success") {
							  $("#btnCommentList").trigger("click");
						  }
			}
		});
		
	});
	
	// 댓글 수정 버튼
	// 비동기 방식 등으로(html소스에 없는 상태, 위에서 테이블 clone한 뒤 .text해서 붙여서 만들었음)
	// 추가된 엘리먼트에 대해서 이벤트 처리는 처음 로딩된 상태에서 존재하는 엘리먼트에 설정한다.
	// $(".commentModify").click -> 반응 없음, #commentTable을 찾아서 해야함
	$("#commentTable").on("click", ".commentModify", function() {
		var c_no = $(this).parent().parent().find("td").eq(0).text();
		var c_content = $(this).parent().parent().find("td").eq(1).text();
		console.log(c_content);
		// 모달창 보이기 ( .c_content -> input type="text"에 클래스 추가한곳)
		$(".modal-body > .c_content").val(c_content);
		// 버튼클릭시 data-cno값 읽어옴
		$("#btnModalOk").attr("data-cno", c_no);
		$("#modal-221478").trigger("click");
	});
	
	// 모달 수정 완료 버튼
	$("#btnModalOk").click(function() {
		var c_no = $(this).attr("data-cno");
		var c_content = $(".modal-body > .c_content").val();
		var url = "/comment/updateComment";
		var sendData = {
				"c_no" 		: c_no,
				"c_content" : c_content
		}
		console.log(sendData);
		$.ajax({
			"url" 		: url,
			"headers" 	: { "Content-Type" : "application/json" },
			"method" 	: "post",
			"dataType" 	: "text",
			"data" 		: JSON.stringify(sendData),
			"success" 	: function(receivedData) {
						  console.log(receivedData);
							if (receivedData == "success") {
								$("#btnModalClose").trigger("click");
								$("#btnCommentList").trigger("click");
							}
			}
		});
	});
	
	// 댓글 삭제 버튼
	$("#commentTable").on("click", ".commentDelete", function() {
		var c_no = $(this).attr("data-cno");
		console.log(c_no);
		if (confirm("댓글을 삭제하시겠습니까?")) {
			var url = "/comment/deleteComment/" + c_no + "/${boardVo.b_no}";
			$.get(url, function(receivedData) {
				console.log(receivedData);
				if (receivedData == "success") {
					$("#btnCommentList").trigger("click");
				}
			});
		};
		
	})
	
	if ("${checkCount}" == 1) {
		$("#heart").removeClass("bi bi-heart").addClass("bi bi-heart-fill");
		$("#heartPath").removeAttr("d").attr("d","M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z");
	}

	
	// 좋아요
	$("#btnLike").click(function() {
		var url = "/board/like/${boardVo.b_no}";
		$.get(url, function(rData) {
				console.log(rData);
				console.log(rData.like);
			if (rData.like) {
				$("#heart").removeClass("bi bi-heart").addClass("bi bi-heart-fill");
				$("#heartPath").removeAttr("d").attr("d","M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z");
			} else {
				$("#heart").removeClass("bi bi-heart-fill").addClass("bi bi-heart");
				$("#heartPath").removeAttr("d").attr("d","m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z");
			}
			$("#spLike").text(rData.likeCount);
		});
	});
});
</script>
<!-- 모달 창-->
<div class="row">
	<div class="col-md-12">
		<a style="display:none;" id="modal-221478" href="#modal-container-221478" role="button"
			class="btn" data-toggle="modal"></a>
		<div class="modal fade" id="modal-container-221478" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="myModalLabel">댓글 수정</h5>
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body"><input type="text" class="form-control c_content"/></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="btnModalOk">수정완료</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="btnModalClose">닫기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- // 모달 -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>글 내용보기</h2>
				<!--<a href="/board/listAll" class="btn btn-primary">목록</a> -->
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<form role="form" action="/board/modifyRun" method="post">
				<div class="form-group">
					<input type="hidden" name="user_id" value="hong" /> <input
						type="hidden" name="b_no" value="${boardVo.b_no }" /> <label
						for="b_title"> 글제목 </label> <input type="text"
						class="form-control" id="b_title" name="b_title"
						value="${boardVo.b_title }" readonly />
				</div>
				<div class="form-group">
					<label for="b_content"> 글내용 </label>
					<textarea class="form-control" id="b_content" name="b_content"
						readonly>${boardVo.b_content}</textarea>
				</div>
				<button type="button" id="btnLike" style="border:none; background:none; padding: 0;">
				  <svg id="heart" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart heart" style="color:red" viewBox="0 0 16 16" >
				  <path id="heartPath" d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
				</svg>
				</button>
<!-- 				<button type="button" id="btnLike" style="border:none; background:none; padding: 0;"> -->
<!-- 				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" style="color:red; display:none" viewBox="0 0 16 16"> -->
<!-- 					<path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/> -->
<!-- 				</svg> -->
<!-- 				</svg> -->
<!-- 				</button> -->
				  <span id="spLike">${boardVo.like_count}</span>
				  <span style="margin-left:20px"></span>
				<button type="button" class="btn btn-primary btn-sm" id="btnModify">수정</button>
				<button type="submit" class="btn btn-success" style="display: none"
					id="btnModifyFinish">수정완료</button>
				<button type="button" class="btn btn-danger btn-sm" id="btnDelete">삭제</button>
				<button type="button" class="btn btn-warning btn-sm" id="btnList">목록</button>
				</svg>
			</form>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<hr />
			<button type="button" class="btn btn-info" id="btnCommentList">댓글목록</button>
			<hr />
		</div>
	</div>

	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<input type="text" class="form-control" placeholder="댓글을 입력하세요." id="c_content"/>
		</div>
		<div class="col-md-2">
			<button type="button" class="btn btn-primary" id="btnCommentInsert">입력</button>
			<hr/>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<table class="table" id="commentTable">
				<tbody>
					<tr style="display: none;">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><button type="button" class="btn btn-warning btn-sm commentModify">수정</button></td>
						<td><button type="button" class="btn btn-danger btn-sm commentDelete">삭제</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%@ include file="../include/footer.jsp" %>