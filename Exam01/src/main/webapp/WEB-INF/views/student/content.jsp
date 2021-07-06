<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<script>
$(document).ready(function() {
	// 수정버튼
	$("#btnModify").click(function() {
		$("#btnModifyFin").show(500);
		$(".readonly").attr("readonly", false);
		$(".disabled").attr("disabled", false);
	});
	
	// 삭제버튼
	$("#btnDelte").click(function() {
		if (confirm("삭제하시겠습니까?")) {
			location.href = "/student/deleteRun?sno=${studentVo.sno}";
		}
	});
});
</script>
<title>학생 상세보기</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>학생 상세보기</h2>
					<a class="btn btn-info" href="/student/list">목록</a>
				</div>
				<form role="form" action="/student/modifyRun" method="post">
					<div class="form-group">

						<label for="sno"> 학번 </label> <input type="text"
							class="form-control" id="sno" name="sno" value="${studentVo.sno}" readonly/>
					</div>
					<div class="form-group">

						<label for="sname"> 이름 </label> <input type="text"
							class="form-control readonly" id="sname" name="sname" value="${studentVo.sname}" readonly/>
					</div>
					<div class="form-group">

						<label for="syear"> 학년 </label> <br> 
						<select class="form-control disabled" name="syear" disabled>
							<option value=1 ${first}>1학년</option>
							<option value=2 ${second}>2학년</option>
							<option value=3 ${third}>3학년</option>
							<option value=4 ${fourth}>4학년</option>
						</select>
					</div>
					<div class="form-group">

						<label for="sname"> 성별 </label> <br> 
						<input class="disabled" type="radio" name="gender" value="M" ${male} disabled>남자
						<input class="disabled" type="radio" name="gender" value="F" ${female} disabled>여자
					</div>
					<div class="form-group">

						<label for="major"> 전공 </label> <input type="text"
							class="form-control readonly" id="major" name="major" value="${studentVo.major}" readonly/>
					</div>
					<div class="form-group">

						<label for="score"> 점수 </label> <input type="number"
							class="form-control readonly" id="score" name="score" value="${studentVo.score}" readonly/>
					</div>

					<button type="button" class="btn btn-primary" id="btnModify">수정</button>
					<button type="submit" class="btn btn-warning" id="btnModifyFin" style="display:none">수정완료</button>
					<button type="button" class="btn btn-danger" id="btnDelte">삭제</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>