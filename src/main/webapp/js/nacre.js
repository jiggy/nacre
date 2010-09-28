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
		console.log("saving...");
		e.preventDefault();
		nacre.serializeForm();
		return false;
	});

	nacre.initHandlers();
}

nacre.initHandlers = function() {
	var pathToQuery = function(path) {
		return path.replace(new RegExp(/\[.+\]/g), "");
	};
	$(".datepicker").datepicker();

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
		console.log($(this).text() + " chosen, container is " + containerId + " query is " + query);
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

nacre.getInstances = function(containerId) {
	var i = 0;
	var fields = [];
	while (nacre.getField(containerId + "["+i+"]").length > 0) {
		fields.push(nacre.getField(containerId + "["+i+"]"))
		i++;
	}
	return fields;
};

nacre.getAllInstances = function(path) {
	var tokens = path.split("/");
	var subpath = "";
	$.each(tokens,function(idx,token) {
		if (token == '') return;
		subpath += "/" + token;
		if (idx > 0) {
			var instances = nacre.getInstances(subpath);
			$.each(instances,function(x,i){console.log("inst:"+i.attr("id"));});
		}
	});
};

nacre.serializeForm = function() {
	var tree = {};
	console.log("Serializing...");
	$.each($(".fieldid"), function(idx,elem) {
		var xpath = $(elem).val();
		console.log("field: " + xpath);
		var field = nacre.getField(xpath);
		var path = xpath.substr(1); // strip leading "/"
		var containers = [];
		while (path.indexOf("/") > -1) { // another container
			var container = path.substr(0,path.indexOf("/"));
			path = path.substr(path.indexOf("/")+1);
			containers.push(container);
		}
		var node = tree;
		$.each(containers, function(i,n) {
			if (node[n] == undefined) node[n] = {};
			node = node[n];
		});
		if (path.indexOf("@") > -1) {
			// attribute
			var fieldName = path.substr(0,path.indexOf("@"));
			path = path.substr(path.indexOf("@")+1);
			if (node[fieldName] == undefined) node[fieldName] = {};
			node = node[fieldName];
			if (node['attributes'] == undefined) node['attributes'] = {};
			node['attributes'][path] = field.val();
		} else {
			node[path] = field.val();
		}
	});
	console.log(tree);
	var toString = function(doc) {
		return new XMLSerializer().serializeToString(doc);
	};
	var xdoc = document.implementation.createDocument("ns","root",null);
	var serialize = function(doc,obj) {
		console.log("doc is " + toString(doc));
		for (var i in obj) {
			if (i == 'attributes') {
				for (var a in obj[i]) {
					console.log("attribute " + a);
					doc.setAttribute(a, obj[i][a]);
				}
			} else {
				var tagName = i.replace(/\[\d+\]$/,"")
				console.log("adding [" + tagName + "]");
				var chld = xdoc.createElement(tagName);
				doc.appendChild(chld);
				if ($.isPlainObject(obj[i])) {
					serialize(chld,obj[i]);
				} else {
					chld.appendChild(xdoc.createTextNode(obj[i]));
				}
			}
		}
	};
	serialize(xdoc.getElementsByTagName("root")[0],tree);
	console.log(xdoc);
	console.log(toString(xdoc));
	return tree;
};