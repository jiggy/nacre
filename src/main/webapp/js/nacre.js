console.log("nacre!");
$(document).ready(function() {
	console.log("ready!");
	$(".replicate-plus").click(function() {
		var containerId = $(this).parent().parent().attr("id");
		console.log("plus! " + containerId);
		containerId = containerId.replace(/-/g,"/");
		console.log("plus! " + containerId);
		containerId = containerId.replace("^/", "/type::").replace(new RegExp("(.)/","g"), "$1//element::");
		console.log("plus! " + containerId);
		$.ajax({
			url:"FormServlet?query=" + containerId,
			success:function(data) {
				console.log(data);
			}
		});
	});
	$(".replicate-minus").click(function() {
		console.log("minus!");
	});
});