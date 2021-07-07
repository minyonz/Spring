<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<script>
$(document).ready(function() {
	// 쪽지 삭제
	$("#btnDelete").click(function() {
		if (confirm("쪽지를 삭제하시겠습니까?")) {
			// 이렇게만 보내면 아무나 다 삭제할 수 있기 때문에 받은사람(로그인한 사용자)가 누군지 명시해 주어야 함
			location.href = "/message/deleteMessage?msg_no=${messageVo.msg_no}";
		}
		var msg_delete = "${msg_delete}";
		if (msg_delete == "true") {
			alert("쪽지를 삭제했습니다.");
		} else if (msg_delete == "false") {
			alert("쪽지 삭제에 실패하셨습니다.");
		}
	});
	
	$("#btnReply").click(function() {
		$("#msg_content").val("");
	});
	
	// 쪽지 답장(modal)
	$("#btnReplyMsg").click(function() {
		var msg_content = $("#msg_content").val();
		var msg_receiver = $(this).attr("data-msg_receiver");
		// 굳이 url안만들고 sendMessage로 보내면 되는거였다..ㅋ(/message/sendMessage)
		var url = "/message/replyMessage";
		var sendData = {
			"msg_content" 	: msg_content,
			"msg_receiver" 	: msg_receiver
		}
		$.post(url, sendData, function(rData) {
			if (rData == "success") {
				$("#btnReplyCancel").trigger("click");
				alert("전송완료");
			}
		});
	});
});
</script>
<!-- 모달 -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
<!-- 			<a id="modal-95025" href="#modal-container-95025" role="button" -->
<!-- 				class="btn" data-toggle="modal">Launch demo modal</a> -->
			<div class="modal fade" id="modal-container-95025" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="myModalLabel">답장하기</h5>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">
							<input type="text" class="form-control" id="msg_content">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" id="btnReplyMsg" 
							data-msg_receiver="${messageVo.msg_sender}">보내기</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal" id="btnReplyCancel">취소</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 모달 -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<table class="table">
				<thead>
				</thead>
				<tbody>
					<tr>
						<th>#</th>
						<td>${messageVo.msg_no}</td>
					</tr>
					<tr>
						<td>쪽지내용</td>
						<td>${messageVo.msg_content}</td>
					</tr>
					<tr>
						<td>보낸사람</td>
						<td>${messageVo.msg_sender}</td>
					</tr>
					<tr>
						<td>보낸날짜</td>
						<td>${messageVo.msg_senddate}</td>
					</tr>
					<tr>
						<td>읽은날짜</td>
						<td>${messageVo.msg_opendate}</td>
					</tr>
				</tbody>
			</table>
		<button type="button" class="btn btn-warning" id="btnDelete">삭제</button>
		<a class="btn btn-info" href="#modal-container-95025" data-toggle="modal" id="btnReply">답장</a>
		<a class="btn btn-primary" href="/message/message_receive_list">목록</a>
		</div>
	</div>
</div>
<%@ include file="../include/footer.jsp"%>