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

$().ready(function() {
	nacre.init();
});
nacre.init = function() {
	nacre.addValidatorMethods();
	$("#nacreForm").validate({'rules':rules});
	$("a#Save").click(function(e) {
		e.preventDefault();
		if ($("#nacreForm").valid()) {
			var doc = nacre.serializeForm();
			console.log(new XMLSerializer().serializeToString(doc));
			$.post("/nacre/XMLPostServlet", {xml:new XMLSerializer().serializeToString(doc)}, function(data,status,request) {
				console.log("status: " + status);
			});
		} else {
			alert("form not valid!");
		}
		return false;
	});

	nacre.initHandlers();
}

nacre.initHandlers = function() {
	var pathToQuery = function(path) {
		return path.replace(new RegExp(/\[.+\]/g), "");
	};
	$(".datepicker").datepicker({
		onSelect: function(dateText, inst) {
			var isodate = new Date(dateText).toISOString();
			console.log(isodate);
			$(this).siblings("input[name]").val(isodate);
		}
	});

	$(".replicate-plus").click(function() {
		var containerId = $(this).val();
		var container = $(this).parents(".repeater-container");
		var query = pathToQuery(containerId);
		$.ajax({
			url:"FormServlet?query=" + escape(query) + "&path=" + escape(nacre.nextId(containerId)),
			success:function(data) {
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
		$.ajax({
			url:"FormServlet?query=" + escape(query) + "&path=" + escape(nacre.nextId(containerId) + "/" + $(this).text()),
			success:function(data) {
				container.find(".choice-selector").replaceWith($(data));
			}
		});
		return false;
	});
};

var rules = {};

nacre.getField = function(selector) {
	return $("input#" + selector.replace(new RegExp("([@/\\]\\[])","g"),"\\$1"));
};

nacre.nextId = function(containerId) {
	containerId = containerId.replace(/\[\d+\]$/,"");
	var i = 0;
	while (nacre.getField(containerId + "[" + i + "]").length > 0) i++;
	return containerId + "[" + i + "]";
};

nacre.serializeForm = function() {
	var serializeAttribute = function(fld, parent) {
		var field = $(fld);
		var name = field.attr("id");
		$.each($(fld).childrenUntil("fieldset.instance"),function(i,inst) {
			var val = $(inst).find("input.nacre-input-field").val();
			parent.setAttribute(name, val);
		});
	};
	var serializeField = function(fld, parent) {
		var field = $(fld);
		var name = field.attr("id");
		var ns = field.children("input[name=namespace]").val();
		var type = field.children("input[name=type]").val();
		var hasAttributes = field.children("input[name=hasAttributes]").val();
		var doc = parent.ownerDocument;
		$.each($(fld).childrenUntil("fieldset.instance"),function(i,inst) {
			var instance = $(inst);
			var node = doc.createElementNS(ns, name);
			parent.appendChild(node);
			if (type == 'ComplexType') {
				$.each(instance.childrenUntil("fieldset.field"), function(j,fld) {
					serializeField(fld,node);
				});
			} else {
				var path = instance.find("input.fieldid").val();
				var val = instance.find("input[name=" + path + "]").val();
				node.appendChild(doc.createTextNode(val));
			}
			if (hasAttributes == 'true') {
				$.each(instance.childrenUntil("fieldset.attribute"),function(j,attr) {
					serializeAttribute(attr,node);
				});
			}
		});
	};
	var xdoc = document.implementation.createDocument("http://www.nacre.com/test","Article",null); // namespace, root node, doctype
	$.each($("#root").childrenUntil("fieldset.field"),function(i,e) {
		serializeField(e,xdoc.documentElement);
	});
	xdoc.documentElement.setAttributeNS('http://www.w3.org/2001/XMLSchema-instance',
			'xsi:schemaLocation', 'http://www.nacre.com/test')
	xdoc.documentElement.setAttributeNS('http://www.w3.org/2001/XMLSchema-instance',
			'xmlns:ns2', 'http://www.nacre.com/testns')
	return xdoc;
};
