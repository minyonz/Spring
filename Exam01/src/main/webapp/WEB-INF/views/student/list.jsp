<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp" %>
<script src="/resources/date-script.js"></script>
<script>
$(document).ready(function() {
	// 등록완료
	var registMsg = "${registMsg}";
	if (registMsg == "success") {
		alert("신규 학생 등록완료");
	}
	
	// 수정완료
	var modifyMsg = "${modifyMsg}";
	if (modifyMsg == "success") {
		alert("학생 수정완료");
	}
	
	// 삭제완료
	var deleteMsg = "${deleteMsg}";
	if (deleteMsg == "success") {
		alert("학생 삭제완료");
	}
	
	$(".consult").click(function() {
		var sno = $(this).attr("data-sno");
		var url = "/consult/listConsult";
		var sendData = { "sno" : sno }
		$("#consultTable > tbody > tr").remove();
		$.get(url, sendData, function(receivedData) {
			var tr = "";
			$.each(receivedData, function() {
				tr += "<tr>";
				tr += "<td>" + this.c_no + "</td>";
				tr += "<td>" + this.sno + "</td>";
				tr += "<td>" + this.c_content + "</td>";
				tr += "<td>" + changeDate(this.c_date) + "</td>";
				tr += "</tr>";
				$("#consultTable > tbody").append(tr);
				tr = "";
			})
			$("#consultTable").show();
		});
	});
	
});
</script>
<title>학생 목록 리스트</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<div class="jumbotron">
					<h2>학생목록</h2>
					<p>
						<a class="btn btn-primary btn-large" href="/student/regist">학생등록</a>
					</p>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th>학번</th>
							<th>이름</th>
							<th>학년</th>
							<th>성별</th>
							<th>전공</th>
							<th>점수</th>
							<th>상담</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="vo" items="${list}">
							<tr>
								<td>${vo.sno}</td>
								<td><a href="/student/content?sno=${vo.sno}">${vo.sname}</a></td>
								<td>${vo.syear}</td>
								<c:choose>
									<c:when test="${vo.gender eq 'M'}">
										<td>남자</td>
									</c:when>
									<c:otherwise>
										<td>여자</td>
									</c:otherwise>
								</c:choose>
								<td>${vo.major}</td>
								<td>${vo.score}</td>
								<td><button type="button" class="btn btn-info btn-sm consult" 
								data-sno="${vo.sno}">내역</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table class="table" id="consultTable" style="display:none">
					<thead>
						<tr>
							<th>상담번호</th>
							<th>학번</th>
							<th>상담내용</th>
							<th>상담일</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>