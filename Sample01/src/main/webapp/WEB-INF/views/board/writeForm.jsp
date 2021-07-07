<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!-- board/writeForm.jsp -->
<script src="/resources/js/my-script.js"></script>
<style>
#fileDrop {
	width: 80%;
	height: 100px;
	background-color: white;
	margin: 20px auto;
	border: 1px dashed gray;
}
.divUploaded {
	width : 150px;
	float : left;
}
	
</style>

<script>
$(document).ready(function() {
	$("#fileDrop").on("dragenter dragover", function(e){
		e.preventDefault();
	});
	
	$("#fileDrop").on("drop", function(e) {
		e.preventDefault();
		console.log(e);
		var file = e.originalEvent.dataTransfer.files[0];
		console.log(file);
		// 이미지파일(바이너리 파일)
		// <form enctype="multipart/form-data">
		// 		<input type="file"/>
		// </form>
		var formData = new FormData(); // <form>을 만들었따
		formData.append("file", file) // input type="file" -> 파일을 선택한 상태
		// 파일을 비동기 방식으로 전송
		// enctype="multipart/form-data"
		var url = "/uploadAjax"; // HomeController사용
		$.ajax({
			"processData" : false,
			"contentType" : false,
			"url" : url,
			"method" : "post",
			"data" : formData,
			"success" : function(receivedData) {
				console.log(receivedData);
				// _찾음(uuid가 안붙은 오리지널 파일이름 찾기)
// 				var arrStr = receivedData.split("_");
// 				var fileName = arrStr[2];
				var fileName = receivedData.substring(receivedData.lastIndexOf("_") + 1);
// 				$("#uploadedList").append("<img src='http://localhost/displayImage?fileName=" + receivedData + "'/>");
				var cloneDiv = $("#uploadedList").prev().clone();
				var img = cloneDiv.find("img");
				// 이미지인 경우
				if (isImage(fileName)) {
					img.attr("src", "http://localhost/displayImage?fileName=" + receivedData);
				}
				cloneDiv.find("span").text(fileName);
				cloneDiv.find(".a_times").attr("href", receivedData);
				// 파일 삭제가 이상해서 모달 걸었는데 여전히 이상함
// 				$("#modal-314544").trigger("click");
// 				setTimeout(function() {
// 					$("#modal-container-314544 .close").trigger("click");
// 				});
				$("#uploadedList").append(cloneDiv.show());					
			}
		});
		
	});
	
	// 첨부파일 삭제 링크
	$("#uploadedList").on("click", ".a_times", function(e) {
		e.preventDefault();
		var that = $(this);
		var fileName = $(this).attr("href");
		console.log(fileName);
		var url = "/deleteFile?fileName=" + fileName;
		$.get(url, function(rData) {
			if (rData == "success") {
				that.parent().remove();
			}
		});
	});
	
	// 폼 전송
	$("#frmWrite").submit(function() {
		var div = $("#uploadedList .divUploaded");
		// 버튼을 더블클릭할 수도 있기 때문에 사전에 방지해줌
		$(this).find("[names^=files]").remove();
		div.each(function(index) {
			var fileName = $(this).find(".a_times").attr("href"); // 파일명 얻어내기
			var html = "<input type='hidden' name='files["+index+"]' value='"+fileName+"'/>";
			$("#frmWrite").prepend(html);
		});
// 		return false;
	});
	
});
</script>
<!-- 파일 업로드 안내 모달 -->
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<a id="modal-314544" href="#modal-container-314544" role="button"
				class="btn" data-toggle="modal" style="display:none">Launch demo modal</a>

			<div class="modal fade" id="modal-container-314544" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="myModalLabel">Modal title</h5>
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">...</div>
						<div class="modal-footer">

							<button type="button" class="btn btn-primary">Save
								changes</button>
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="jumbotron">
				<h2>글쓰기</h2>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<form role="form" id="frmWrite" action="/board/writeRun" method="post">
				<div class="form-group">
<!-- 					<input type="hidden" name="user_id" value="kim" />  -->
					<label for="b_title"> 글제목 </label> <input type="text"
						class="form-control" id="b_title" name="b_title" />
				</div>
				<div class="form-group">

					<label for="b_content"> 글내용 </label>
					<textarea class="form-control" id="b_content" name="b_content"></textarea>
				</div>

				<!-- 첨부파일 -->
				<div>
					<label>첨부할 파일을 드래그 &amp; 드롭하세요.</label>
					<div id="fileDrop"></div>
				</div>


				<!-- // 첨부파일 -->
				<div style="display: none;" class="divUploaded">
					<img height="100" src="/resources/img/default_image.png"
						class="img-rounded" /><br /> <span>default</span> <a href="#"
						class="a_times">&times;</a>
				</div>
				<div id="uploadedList"></div>
				<div style="clear: both;">
					<button type="submit" class="btn btn-primary">작성완료</button>
				</div>
			</form>
		</div>
	</div>
</div>

<%@ include file="../include/footer.jsp"%>
