console.log("nacre!");
$(document).ready(function() {
	console.log("ready!");
	$(".replicate-plus").click(function() {
		var container = $(this).parent().parent();
		var containerId = container.attr("id");
		console.log("plus! " + containerId);
		var query = "/type::" + containerId.replace(new RegExp("/","g"), "//element::");
		console.log("plus! " + query);
		$.ajax({
			url:"FormServlet?query=" + query,
			success:function(data) {
				console.log(data);
				container.after($(data));
			}
		});
	});
	$(".replicate-minus").click(function() {
		console.log("minus!");
	});
});