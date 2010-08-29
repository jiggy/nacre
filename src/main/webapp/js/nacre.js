var nacre = nacre?nacre:{};

nacre.addValidatorMethods = function() {
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
	$.validator.addMethod("number",
			function(value, element, mustBeNumber) {
				return !mustBeNumber || /^-?[\d\.]+$/.text(value);
			}, jQuery.format("Value must be a number"));
	$.validator.addMethod("totalDigits",
			function(value, element, totalDigits) {
				value.replace(new RegExp("[^\\d]","g"), "").length <= totalDigits
			}, jQuery.format("Value must have at most {0} digits"));
	$.validator.addMethod("fractionDigits",
			function(value, element, fractionDigits) {
				value.match(/\.(\d+)/)[1].length <= fractionDigits
			}, jQuery.format("Value must have at most {0} digits right of the decimal place"));

};

console.log("nacre!");
$().ready(function() {
	console.log("ready!");
	nacre.init();
});
nacre.init = function() {
	nacre.addValidatorMethods();
	$("#nacreForm").validate({'rules':rules});
	nacre.initHandlers();
}

nacre.initHandlers = function() {
	var pathToQuery = function(path) {
		return path.replace(new RegExp(/\[.+\]/g), "");
//		var query = "";
//		$.each(path.replace(new RegExp(/\[.+\]/g), "").split("/"), function(idx,token) {
//			if (token != '') {
//				if (query == "") {
//					query += "/~" + token;
//				} else {
//					query += "//" + token;
//				}
//			}
//		});
//		return query;
	};
	$(".datepicker").datepicker();

	$(".replicate-plus").click(function() {
		var containerId = $(this).val();
		console.log("path: " + containerId + ", query: " + pathToQuery(containerId));
		var container = $(this).parents(".repeater-container");
		console.log("add a " + containerId);
		var query = pathToQuery(containerId);
		console.log("query is " + query);
		$.ajax({
			url:"FormServlet?query=" + escape(query) + "&id=" + escape(nacre.nextId(containerId)),
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
	$(".choice-selector a").click(function() {
		var container = $(this).parents(".repeater-container");
		var containerId = container.attr("id");
		var query = pathToQuery(containerId + "/" + $(this).text());
		console.log($(this).text() + " chosen, container is " + containerId + " query is " + query);
		$.ajax({
			url:"FormServlet?query=" + query,
			success:function(data) {
				console.log(data);
				container.find(".choice-selector").replaceWith($(data));
			}
		});
		return false;
	});
};

var rules = {};

nacre.getField = function(selector) {
	selector = "#" + selector.replace(new RegExp("([/\\]\\[])","g"),"\\$1")
	console.log(selector);
	return $(selector);
};

nacre.nextId = function(containerId) {
	var i = 0;
	while (nacre.getField(containerId + "[" + i + "]").length > 0) i++;
	return containerId + "[" + i + "]";
}