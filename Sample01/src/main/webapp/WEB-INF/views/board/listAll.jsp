<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../include/header.jsp"%>
<!-- view/board/listAll.jsp -->
<!-- C태그 사용할수있게 해주는 파일 이미 pom.xml에 등록돼있었음~~! -->
<script>
$(document).ready(function() {
// 	var msg = "${msg}";
// 	if (msg == "success") {
// 		alert("글 등록 완료");
// 	}
	
	var msgDelete = "${msgDelete}";
	if (msgDelete == "success") {
		alert("삭제 완료");
	}
	
	// page부분의 a태그 찾기 . 페이지 번호
	$(".pagination > li > a").click(function(e) {
		e.preventDefault(); // 페이지 이동 막기
		// a태그의 속성값 중 href를 들고와서 page로 설정
		var page = $(this).attr("href");
		console.log(page);
		var frmPaging = $("#frmPaging");
		frmPaging.find("[name=page]").val(page);
		frmPaging.submit();
		// 주소창 -> http://localhost/board/listAll?page=1&perPage=10&searchType=&keyword= 
	});
	
	// 검색 옵션 선택
	$(".searchType").click(function(e) {
		e.preventDefault();
		var searchType = $(this).attr("href");
		$("#frmPaging > input[name=searchType]").val(searchType);
		$("#spanSearchType").text($(this).text());
	});
	
	// 검색
	$("#btnSearch").click(function() {
		var searchType = $("#frmPaging > input[name=searchType]").val();
		if (searchType == "") {
			alert("검색 옵션을 선택해 주세요.");
			return;
		}
		
		var keyword = $("#txtSearch").val().trim();
		if ($("#txtSearch").val().trim() == "") {
			alert("검색어를 입력해 주세요.");
			return;
		}
		
		$("#frmPaging > input[name=keyword]").val(keyword);
		// 제목4검색하고 3페이지 넘어간 후에 제목5 검색했을 때 페이지가 뜨지 않는 경우(제목5의 글 수가 3페이지까지 없는경우)를 대비해 만들어줌
		// 검색버튼 눌렀을 때 1페이지부터 보여주게 함
		$("#frmPaging > input[name=page]").val("1");
		$("#frmPaging").submit();
	});
	
	// 글 제목(눌렀을 때 페이지랑, 검색값 같은것도 같이 get방식으로 보냄. 글 상세보기에서 목록으로 다시 돌아올 때를 위해)
	$(".a_title").click(function(e) {
		// 페이지 이동을 막아놓음
		e.preventDefault();
		// $(this) -> .a_title이 10개가 있는데 그 중에서 클릭한 .a_title
		var b_no = $(this).attr("data-bno");
		$("#frmPaging > input[name=b_no]").val(b_no);
		$("#frmPaging").attr("action", "/board/content");
		$("#frmPaging").submit();
	});
	
	// 쪽지보내기
	$(".sendMessage").click(function() {
		var user_id = $(this).attr("data-user_id");
		$("#btnSendMessage").attr("data-msg_receiver", user_id);
	});
	
	// 쪽지 모달 보내기 버튼
	$("#btnSendMessage").click(function() {
// 		var msg_sender = "hong";
		var msg_content = $("#msg_content").val();
		var msg_receiver = $(this).attr("data-msg_receiver");
		var sendData = {
// 				"msg_sender"	: msg_sender,
				"msg_receiver"	: msg_receiver,
				"msg_content"	: msg_content
		}
		console.log(sendData);
		var url = "/message/sendMessage";
// 		$.post(url, sendData, function(receivedData) {
// 			console.log(receivedData); -> 이렇게 보냈더니 415에러 뜸
//			MemberDaoImpl에서 전송할 때 updatePoint할 때 map에 담을 때 object로 담아서 그런 것?같음
// 		});
		$.ajax({
			"url" 		: url,
			"method" 	: "post",
			"dataType" 	: "text",
			"headers" 	: { "Content-Type" : "application/json" },
			"data" 		: JSON.stringify(sendData),
			"success" 	: function(receivedData) {
				console.log(receivedData)
				if (receivedData == "success") {
					$("#btnSendClose").trigger("click");
				}
			}
		});
	});
	
});
</script>
<!-- // href = 이렇게 직접 입력해도 됨 /board/listAll?page=${v}... -->
<!-- 검색 후에도 값을 유지하기 위해 value = {pagingDto.page~~} 값을 넣어줌 -->
<form id="frmPaging" action="/board/listAll" method="get">
	<input type="hidden" name="page" value="${pagingDto.page}"/>
	<input type="hidden" name="perPage" value="${pagingDto.perPage}"/>
	<input type="hidden" name="searchType" value="${pagingDto.searchType}"/>
	<input type="hidden" name="keyword" value="${pagingDto.keyword}"/>
	<input type="hidden" name="b_no"/>
</form>

<!-- 쪽지 보내기 모달창 -->
<div class="row">
	<div class="col-md-12">
<!-- 		<a id="modal-267223" href="#modal-container-267223" role="button" -->
<!-- 			class="btn" data-toggle="modal">Launch demo modal</a> -->
		<div class="modal fade" id="modal-container-267223" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="myModalLabel">쪽지 보내기</h5>
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">×</span>
						</button>
					</div>
					<div class="modal-body">
						<input type="text" class="form-control" id="msg_content"/>
					</div>
					<div class="modal-footer">

						<button type="button" class="btn btn-primary" id="btnSendMessage">전송</button>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="btnSendClose">닫기</button>
					</div>
				</div>

			</div>

		</div>

	</div>
</div>
<!-- // 쪽지 보내기 모달창 -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>글 목록</h2>
				<p>
					<a class="btn btn-primary btn-large" href="/board/writeForm">글쓰기</a>
				</p>
			</div>
		</div>
	</div>
	<!-- 검색 -->
	<div class="row">
		<div class="col-md-12">
			<div class="dropdown">
				<button class="btn btn-default dropdown-toggle" type="button"
					id="dropdownMenuButton" data-toggle="dropdown">검색옵션</button> 
					<span id="spanSearchType" style="color:#FBC02D">
					<c:choose>
						<c:when test="${pagingDto.searchType == 't'}">제목</c:when>
						<c:when test="${pagingDto.searchType == 'c'}">내용</c:when>
						<c:when test="${pagingDto.searchType == 'u'}">작성자</c:when>
						<c:when test="${pagingDto.searchType == 'tc'}">제목+내용</c:when>
						<c:when test="${pagingDto.searchType == 'tcu'}">제목+내용+작성자</c:when>
					</c:choose>
					
					</span>
				<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
					<a class="dropdown-item searchType" href="t">제목</a> 
					<a class="dropdown-item searchType" href="c">내용</a> 
					<a class="dropdown-item searchType" href="u">작성자</a> 
					<a class="dropdown-item searchType" href="tc">제목+내용</a> 
					<a class="dropdown-item searchType" href="tcu">제목+내용+작성자</a>
				</div>
				<form
					class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
					<div class="input-group">
						<input type="text" class="form-control bg-light border-0 small"
							placeholder="검색어를 입력하세요" aria-label="Search"
							aria-describedby="basic-addon2" id="txtSearch" value="${pagingDto.keyword}">
						<div class="input-group-append">
							<button class="btn btn-primary" type="button" id="btnSearch">
								<i class="fas fa-search fa-sm"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<br>
	<!-- //검색 -->
	<!-- 데이터 목록 -->
	<div class="row">
		<div class="col-md-12">
			<table class="table">
				<thead>
					<tr>
						<th>글번호</th>
						<th>글제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="boardVo" items="${list}">
						<tr>
							<td>${boardVo.b_no }</td>
							<td><a class="a_title" href="#" data-bno="${boardVo.b_no}">${boardVo.b_title}</a>
								<span class="badge badge-info">${boardVo.comment_cnt}</span></td>
							<td>
								<!-- 아이디에 쪽지보내기, 포인트선물 추가 -->
								<div class="dropdown">
									<button class="btn btn-default" type="button"
										id="dropdownMenuButton" data-toggle="dropdown">
										${boardVo.user_id}
									</button>
									<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
										<a class="dropdown-item sendMessage" data-toggle="modal" href="#modal-container-267223" 
										data-user_id="${boardVo.user_id}">쪽지보내기</a>
										<a class="dropdown-item" href="#">포인트 선물하기</a>
									</div>
								</div>
							</td>
							<td>${boardVo.b_regdate}</td>
							<td>${boardVo.b_viewcnt}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!-- // 데이터목록 -->
	<!-- <div style="padding: 10px; margin: 0 auto; width: 300px; height: auto;"></div> -->
	<!-- 페이징 -->
	<div class="row">
		<div class="col-md-12">
			<nav>
				<ul class="pagination justify-content-center">
					<c:if test="${pagingDto.startPage != 1}">
						<li class="page-item"><a class="page-link"
							href="${pagingDto.startPage - 1}">&laquo;</a></li>
					</c:if>
					<c:forEach var="v" begin="${pagingDto.startPage}"
						end="${pagingDto.endPage}">
						<li
						<c:choose>
							<c:when test="${pagingDto.page == v}">
								class="page-item active"
							</c:when>
							<c:otherwise>
								class="page-item"
							</c:otherwise>
						</c:choose>
						>
							<a class="page-link" href="${v}">${v}</a>
						</li>
					</c:forEach>
					<c:if test="${pagingDto.endPage < pagingDto.totalPage}">
						<li class="page-item"><a class="page-link"
							href="${pagingDto.endPage + 1}">&raquo;</a></li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>
</div>
<!-- // 페이징 -->
<%@ include file="../include/footer.jsp"%>