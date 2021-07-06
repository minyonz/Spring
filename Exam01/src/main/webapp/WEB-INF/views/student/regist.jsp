<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script>
$(document).ready(function() {
	$("#btnRegist").click(function() {
		if ($("#sno").val().trim() == "") {
			alert("학번을 입력해 주세요");
			return false;
		} else if ($("#sname").val().trim() == "") {
			alert("이름을 입력해 주세요");
			return false;
		} else if($("input[name='gender']:checked").length < 1) {
			alert("성별을 선택해 주세요.");
			return false;
		} else if ($("#major").val().trim() == "") {
			alert("전공을 입력해 주세요.");
			return false;
		} else if ($("#score").val().trim() == "") {
			alert("점수를 입력해 주세요.");
			return false;
		} else if ($("#score").val() > 100) {
			alert("1~100사이의 점수를 입력해 주세요.");
			return false;
		} else {
			$("#frmStudent").submit();
		}
	}); // 등록

	$("#btnCheck").click(function() {
		var sno = $("#sno").val();
		var url = "/student/checkSno";
		var sendData = {
				"sno" : sno
		}
		$.get(url, sendData, function(receivedData) {
			if (receivedData == 1) {
				$("span").text("이미 등록된 학번입니다.");
			} else {
				$("span").text("등록가능한 학번입니다.");
			}
		});
	});

	
});
</script>
<title>신규 학생 등록</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>신규 학생 등록 폼</h2>
					<p>아래 항목에 맞게 정확하게 기입하여 주세요.</p>
				</div>
				<form role="form" id="frmStudent" action="/student/registRun" method="post">
					<div class="form-group">

						<label for="sno"> 학번 </label> 
						<button type="button" class="btn btn-info" id="btnCheck">중복체크</button>
						<span></span>
						<input type="text" class="form-control" id="sno" name="sno" />
					</div>
					<div class="form-group">

						<label for="sname"> 이름 </label> <input
							type="text" class="form-control" id="sname" name="sname"/>
					</div>
						<div class="form-group">

						<label for="syear"> 학년 </label> <br>
						<select class="form-control" name="syear">
							<option value=1 selected>1학년</option>
							<option value=2>2학년</option>
							<option value=3>3학년</option>
							<option value=4>4학년</option>
						</select>
					</div>
						<div class="form-group">

						<label for="sname"> 성별 </label> <br>
						<input type="radio" name="gender" value="M">남자
						<input type="radio" name="gender" value="F">여자
					</div>
						<div class="form-group">

						<label for="major"> 전공 </label> <input
							type="text" class="form-control" id="major" name="major"/>
					</div>
						<div class="form-group">

						<label for="score"> 점수 </label> <input
							type="number" class="form-control" id="score" name="score"/>
					</div>
				
					<button type="submit" class="btn btn-primary" id="btnRegist">등록</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>