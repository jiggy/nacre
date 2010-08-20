console.log("nacre!");
$(document).ready(function() {
	console.log("ready!");
	$(".replicate-plus").click(function() {
		var containerId = $(this).parent().parent().attr("id");
		containerId = containerId.replace(/^nacredoc/,"").replace(/-/g,"");
		console.log("plus! " + containerId);
		$.ajax({
			url:"FormServlet?complexType=" + containerId,
			success:function(data) {
				console.log(data);
			}
		});
	});
	$(".replicate-minus").click(function() {
		console.log("minus!");
	});
});