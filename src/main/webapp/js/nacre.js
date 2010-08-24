console.log("nacre!");
$().ready(function() {
	console.log("ready!");
	$("#nacreForm").validate({'rules':rules});
	$(".replicate-plus").click(function() {
		var containerId = $(this).val();
		var container = $("#"+containerId.replace(new RegExp("/","g"), "\\/"));
		console.log("add a " + containerId);
		var query = "/type::" + containerId.replace(new RegExp("/","g"), "//element::");
		console.log("query is " + query);
		$.ajax({
			url:"FormServlet?query=" + query,
			success:function(data) {
				console.log(data);
				container.after($(data));
			}
		});
		return false;
	});
	$(".replicate-minus").click(function() {
		console.log("minus!");
		return false;
	});
});

var rules = {};