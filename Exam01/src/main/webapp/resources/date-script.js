/**
 * 
 */

function changeDate(timestamp) {
	var d = new Date(timestamp);
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();
	var hours = d.getHours();
	var minutes = d.getMinutes();
	var seconds = d.getSeconds();
	return year + "년" + month + "월" + date + "일 " + hours + ":" + minutes + ":" + seconds;
}