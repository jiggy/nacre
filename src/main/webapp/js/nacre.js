console.log("nacre!");
$().ready(function() {
	console.log("ready!");
	$.validator.addMethod("pattern",
		function(value, element, pattern) {
			return pattern.test(value);
		}, jQuery.format("Failed to match pattern {0}"));
	$.validator.addMethod("minExclusive",
			function(value, element, min) {
				return value > min;
			}, jQuery.format("Value must be more than {0}"));
	$.validator.addMethod("maxExclusive",
			function(value, element, max) {
				return value < max;
			}, jQuery.format("Value must be less than {0}"));
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