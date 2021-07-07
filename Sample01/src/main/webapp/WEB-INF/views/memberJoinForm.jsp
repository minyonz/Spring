<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>

<script>
$(document).ready(function() {
	// 아이디 중복체크 했는지 안했는지 여부
	var isCheckDupId = false;
	var checkId = "";
	// 폼전송
	$("#frmJoin").submit(function() {
		if ($("#user_pw").val() != $("#user_pw2").val()) {
			alert("비밀번호가 일치하지 않습니다.");
			$("#user_pw").focus();
			return false;
		}
		
		if (isCheckDupId == false) {
			alert("아이디 중복체크를 실행해 주세요.");
			$("#btnCheckDupId").focus();
			return false;
		}
	});
	// 아이디 중복 확인 버튼
	$("#btnCheckDupId").click(function() {
		var user_id = $("#user_id").val();
		var url = "/checkDupId";
		var sendData = { "user_id" : user_id };
		$.get(url, sendData, function(rData) {
			console.log(rData);
			if (rData == "true") {
				$("#checkDupIdResult").text("사용중인 아이디입니다.").css("color", "red");
			} else {
				$("#checkDupIdResult").text("사용가능한 아이디입니다.").css("color", "blue");
				isCheckDupId = true;
				// 체크아이디랑 유저아이디가 다를 경우 다시 체크하게 함
				checkId = user_id;
			}
		});
			
	});
});
</script>

<title>JoinForm.jsp</title>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>회원가입</h2>
				<p>아래 항목에 맞게 작성하여 주세요.</p>
				<p>
					<a class="btn btn-primary btn-large" href="/loginForm">로그인</a>
				</p>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<!-- file때문에 form전송을 multipart/form-data로 보내야함 -->
			<form role="form" id="frmJoin" action="/memberJoinRun" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="user_id"> 아이디 </label> 
					<button type="button" class="btn btn-sm btn-warning" id="btnCheckDupId">아이디 중복 확인</button>
					<span id="checkDupIdResult"></span>
					<input type="text" class="form-control" id="user_id" name="user_id" required />
				</div>
				<div class="form-group">
					<label for="user_pw"> 패스워드 </label> 
					<input type="password" class="form-control" id="user_pw" name="user_pw" required />
				</div>
				<div class="form-group">
					<label for="user_pw2"> 패스워드 확인 </label> 
					<input type="password" class="form-control" id="user_pw2" required />
				</div>
				<div class="form-group">
					<label for="user_name"> 이름 </label> 
					<input type="text" class="form-control" id="user_name" name="user_name" />
				</div>
				<div class="form-group">
					<label for="user_email"> 이메일 </label> 
					<input type="text" class="form-control" id="user_email" name="user_email" />
				</div>
				<div class="form-group">
					<label for="exampleInputFile"> 사진 </label> 
					<input type="file" class="form-control-file" id="file" name="file" />
				</div>
				<button type="submit" class="btn btn-primary">가입</button>
			</form>
		</div>
	</div>
</div>
<%@ include file="include/footer.jsp" %>